package jp.co.vermore.controller;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.JsonStatus;
import jp.co.vermore.entity.*;
import jp.co.vermore.service.EntryService;
import jp.co.vermore.service.PicService;
import jp.co.vermore.service.WidgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.vermore.jsonparse.NewsDetailJsonParse;
import jp.co.vermore.common.JsonObject;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.jsonparse.NewsJsonParse;
import jp.co.vermore.service.NewsService;

import javax.servlet.http.HttpServletRequest;

/**
 * NewsController
 * Created by wubin.
 * <p>
 * DateTime: 2018/03/03 11:13
 * Copyright: sLab, Corp
 */
@Controller
public class NewsController extends BaseController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private EntryService entryService;

    @Autowired
    private PicService picService;

    @Autowired
    private WidgetService widgetService;

    @Value(value = "${hosturl}")
    private String hosturl;

    //eg: http://localhost:8081/moveup_war/api/news/list/0/1/0/
    @RequestMapping(value = "/api/news/list/{type}/{limit}/{offset}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getNewsList(@PathVariable int type,@PathVariable int limit, @PathVariable int offset) {
        List<News> list = newsService.getNewsAll(type,limit, offset);
        List<News> countlist = newsService.getNewsAllByType(type);
        List<NewsJsonParse> ejpList = new ArrayList<>();
        ejpList = list(ejpList, list);
        Map<String,Object> map = new HashMap<>();
        map.put("newsList",ejpList);
        map.put("count",countlist.size());
        jsonObject.setResultList(map);
        return jsonObject;
    }

    //eg:http://localhost:8081/moveup_war/api/news/list/0/1/1/0/
    @RequestMapping(value = "/api/news/list/{type1}/{type2}/{limit}/{offset}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getNewsEventList(@PathVariable int type1,@PathVariable int type2,@PathVariable int limit, @PathVariable int offset) {
        List<News> list = newsService.getNewsEventAll(type1,type2,limit, offset);
        List<NewsJsonParse> ejpList = new ArrayList<>();
        ejpList = list(ejpList, list);
        Map<String,Object> map = new HashMap<>();
        map.put("newsList",ejpList);
        map.put("count",ejpList.size());
        jsonObject.setResultList(map);
        return jsonObject;
    }

    //eg:http://localhost:8081/moveup_war/api/news/detail/4hIZRgPJFu/
    @RequestMapping(value = "/api/news/detail/{uuid}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getNewsDetailList(@PathVariable String uuid) {
        News news = newsService.getNewsByUuid(uuid);
        List<NewsDetailJsonParse> ejpList = new ArrayList<>();
        List<NewsDetail> list = newsService.getNewsDetailAll(news.getId());
        NewsDetailJsonParse ejp = new NewsDetailJsonParse();
        if(list.size()>0){
            for (NewsDetail ed: list) {
                ejp.setNewsId(ed.getNewsId());
                ejp.setTitle(ed.getTitle());
                ejp.setDate(DateUtil.dateToStringyyyy_MM_dd(ed.getDate()));
                ejp.setTypeStr(widgetService.getNewsType(ed.getType()));
                ejp.setType(ed.getType());
                ejp.setColor(widgetService.getNewsColor(ed.getType()));
                ejp.setDetail(ed.getDetail());

                Pic topPic = new Pic();
                List<Pic> topPicList = picService.getPic(ed.getNewsId(), Constant.EVENT_PIC_TYPE.NEWS_TOP);
                List<String> topList = new ArrayList<String>();
                for(Pic pic:topPicList){
                    topList.add(pic.getPicUrl());
                }
                ejp.setTopPic(topList);

                List<Pic> footPicList = picService.getPic(ed.getNewsId(),Constant.EVENT_PIC_TYPE.NEWS_FOOT);
                List<String> footList = new ArrayList<String>();
                for(Pic pic:footPicList){
                    footList.add(pic.getPicUrl());
                }
                ejp.setFootPic(footList);
                List<News> listPre = newsService.getNewsPre(ed.getDate());
                List<News> listNext = newsService.getNewsNext(ed.getDate());
                List<NewsJsonParse> ejpListPre = new ArrayList<>();
                List<NewsJsonParse> ejpListNext = new ArrayList<>();
                ejpListPre = list(ejpListPre, listPre);
                ejpListNext = list(ejpListNext, listNext);
                if(listPre.size()>0){
                    ejpListPre.get(0).setColor(widgetService.getNewsDetailColor(listPre.get(0).getType()));
                }
                if(listNext.size()>0){
                    ejpListNext.get(0).setColor(widgetService.getNewsDetailColor(listNext.get(0).getType()));
                }
                ejp.setNewsPre(ejpListPre);
                ejp.setNewsNext(ejpListNext);
                ejpList.add(ejp);
            }

            int type =0;
            if(news.getType() == Constant.NEWS_TYPE.EVENT){
                type =  Constant.NEWS_TYPE.MOVEUP;
            }else if(news.getType() == Constant.NEWS_TYPE.MOVEUP){
                type = Constant.NEWS_TYPE.EVENT;
            }

            EntryMail entryMail = entryService.getEntryMailByEntryIdAndType(news.getId(),type);
            if(entryMail != null){
                Date startTime = entryMail.getPublishStart();
                Date endTime = entryMail.getPublishEnd();
                Date nowTime = new Date(System.currentTimeMillis());
                if(nowTime.getTime() >= startTime.getTime() && nowTime.getTime() <= endTime.getTime()){
                    ejp.setEntry("1");//応募可能
                }else{
                    ejp.setEntry(null);
                }
            }else {
                ejp.setEntry(null);
            }
            jsonObject.setResultList(ejpList);
        }else{
            jsonObject.setResultList(null);
        }
        return jsonObject;
    }

    private List<NewsJsonParse> list(List<NewsJsonParse> jpList, List<News> list) {

        for (News nd: list) {
            NewsJsonParse njp = new NewsJsonParse();
            njp.setUuid(nd.getUuid());
            njp.setTitle(nd.getTitle());
            njp.setDate(DateUtil.dateToStringyyyy_MM_dd(nd.getDate()));
            njp.setType(widgetService.getNewsType(nd.getType()));
            njp.setColor(widgetService.getNewsColor(nd.getType()));
            njp.setExcerpt(nd.getExcerpt());
            jpList.add(njp);
        }
        return jpList;
    }

    // News detail for sns
    //eg:http://localhost:8081/moveup_war/sns/newsDetail/4hIZRgPJFu/
    @RequestMapping(value = "/sns/newsDetail/{uuid}/", method = RequestMethod.GET)
    public Object getNewsSNSDetail(@PathVariable String uuid, Model model, HttpServletRequest hsr) {

        News news = newsService.getNewsByUuid(uuid);
        List<NewsDetail> newsDetailList = newsService.getNewsDetailAll(news.getId());
        if(newsDetailList.size()>0){
            NewsDetail newsDetail = newsDetailList.get(0);

            model.addAttribute("title", newsDetail.getTitle());
            model.addAttribute("url", "https://www.japanmoveupwest.com" + "/newsDetail/" + news.getUuid() + "/");
            model.addAttribute("desc",  news.getExcerpt());
            model.addAttribute("image",  "");
        }
        
        String userAgent = hsr.getHeader("User-Agent");
        logger.debug("-------user-agent=" + userAgent);

        String regex = "facebookexternalhit|Facebot|Twitterbot|Pinterest|Google.*snippet";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(userAgent);
        if (m.find()) {
            logger.debug("-------tosns");
            return "sns";
        } else {
            logger.debug("-------tourl");
            return "redirect:"+ hosturl + "/newsDetail/" + uuid + "/";
        }
    }

    // News detail for sns
    //eg:http://localhost:8081/moveup_war/api/sns/newsDetail/app/4hIZRgPJFu/
    @RequestMapping(value = "/api/sns/newsDetail/app/{uuid}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getNewsDetailSNSForApp(@PathVariable String uuid) {

        Map<String, Object> urlMap = new HashMap<String, Object>();
        urlMap.put("twitter","https://twitter.com/share?url="+hosturl+"/newsDetail/"+uuid+"/");
        urlMap.put("facebook","https://www.facebook.com/sharer/sharer.php?u="+hosturl+"/newsDetail/"+uuid+"/");

        jsonObject.setResultList(urlMap);
        jsonObject.setStatus(JsonStatus.SUCCESS);
        return jsonObject;
    }
}