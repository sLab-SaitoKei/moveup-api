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

import jp.co.vermore.jsonparse.ReporttestDetailJsonParse;
import jp.co.vermore.common.JsonObject;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.jsonparse.ReporttestJsonParse;
import jp.co.vermore.service.ReporttestService;
import javax.servlet.http.HttpServletRequest;

/**
 * ReporttestController
 * Created by wubin.
 * <p>
 * DateTime: 2018/03/03 11:13
 * Copyright: sLab, Corp
 */
@Controller
public class ReporttestController extends BaseController {


    @Autowired
    private ReporttestService reporttestService;

    @Autowired
    private EntryService entryService;

    @Autowired
    private PicService picService;

    @Autowired
    private WidgetService widgetService;

    @Value(value = "${hosturl}")
    private String hosturl;

    //eg: http://localhost:8081/moveup_war/api/reporttest/list/0/1/0/
    @RequestMapping(value = "/api/reporttest/list/{type}/{limit}/{offset}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getReporttestList(@PathVariable int type,@PathVariable int limit, @PathVariable int offset) {
        List<Reporttest> list = reporttestService.getReporttestAll(type,limit, offset);
        List<Reporttest> countlist = reporttestService.getReporttestAllByType(type);
        List<ReporttestJsonParse> ejpList = new ArrayList<>();
        ejpList = list(ejpList, list);
        Map<String,Object> map = new HashMap<>();
        map.put("reporttestList",ejpList);
        map.put("count",countlist.size());
        jsonObject.setResultList(map);
        return jsonObject;
    }

    //eg:http://localhost:8081/moveup_war/api/reporttest/list/0/1/1/0/
    @RequestMapping(value = "/api/reporttest/list/{type1}/{type2}/{limit}/{offset}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getReporttestEventList(@PathVariable int type1,@PathVariable int type2,@PathVariable int limit, @PathVariable int offset) {
        List<Reporttest> list = reporttestService.getReporttestEventAll(type1,type2,limit, offset);
        List<ReporttestJsonParse> ejpList = new ArrayList<>();
        ejpList = list(ejpList, list);
        Map<String,Object> map = new HashMap<>();
        map.put("reporttestList",ejpList);
        map.put("count",ejpList.size());
        jsonObject.setResultList(map);
        return jsonObject;
    }

    //eg:http://localhost:8081/moveup_war/api/reporttest/detail/4hIZRgPJFu/
    @RequestMapping(value = "/api/reporttest/detail/{uuid}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getReporttestDetailList(@PathVariable String uuid) {
        Reporttest reporttest = reporttestService.getReporttestByUuid(uuid);
        List<ReporttestDetailJsonParse> ejpList = new ArrayList<>();
        List<ReporttestDetail> list = reporttestService.getReporttestDetailAll(reporttest.getId());
        ReporttestDetailJsonParse ejp = new ReporttestDetailJsonParse();
        if(list.size()>0){
            for (ReporttestDetail ed: list) {
                ejp.setReporttestId(ed.getReporttestId());
                ejp.setTitle(ed.getTitle());
                ejp.setDate(DateUtil.dateToStringyyyy_MM_dd(ed.getDate()));
                ejp.setTypeStr(widgetService.getReporttestType(ed.getType()));
                ejp.setType(ed.getType());
                ejp.setColor(widgetService.getReporttestColor(ed.getType()));
                ejp.setDetail(ed.getDetail());

                Pic topPic = new Pic();
                List<Pic> topPicList = picService.getPic(ed.getReporttestId(), Constant.EVENT_PIC_TYPE.NEWS_TOP);
                List<String> topList = new ArrayList<String>();
                for(Pic pic:topPicList){
                    topList.add(pic.getPicUrl());
                }
                ejp.setTopPic(topList);

                List<Pic> footPicList = picService.getPic(ed.getReporttestId(),Constant.EVENT_PIC_TYPE.NEWS_FOOT);
                List<String> footList = new ArrayList<String>();
                for(Pic pic:footPicList){
                    footList.add(pic.getPicUrl());
                }
                ejp.setFootPic(footList);
                List<Reporttest> listPre = reporttestService.getReporttestPre(ed.getDate());
                List<Reporttest> listNext = reporttestService.getReporttestNext(ed.getDate());
                List<ReporttestJsonParse> ejpListPre = new ArrayList<>();
                List<ReporttestJsonParse> ejpListNext = new ArrayList<>();
                ejpListPre = list(ejpListPre, listPre);
                ejpListNext = list(ejpListNext, listNext);
                if(listPre.size()>0){
                    ejpListPre.get(0).setColor(widgetService.getReporttestDetailColor(listPre.get(0).getType()));
                }
                if(listNext.size()>0){
                    ejpListNext.get(0).setColor(widgetService.getReporttestDetailColor(listNext.get(0).getType()));
                }
                ejp.setReporttestPre(ejpListPre);
                ejp.setReporttestNext(ejpListNext);
                ejpList.add(ejp);
            }

            int type =0;
            if(reporttest.getType() == Constant.NEWS_TYPE.EVENT){
                type =  Constant.NEWS_TYPE.MOVEUP;
            }else if(reporttest.getType() == Constant.NEWS_TYPE.MOVEUP){
                type = Constant.NEWS_TYPE.EVENT;
            }

            EntryMail entryMail = entryService.getEntryMailByEntryIdAndType(reporttest.getId(),type);
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

    private List<ReporttestJsonParse> list(List<ReporttestJsonParse> jpList, List<Reporttest> list) {

        for (Reporttest nd: list) {
            ReporttestJsonParse njp = new ReporttestJsonParse();
            njp.setUuid(nd.getUuid());
            njp.setTitle(nd.getTitle());
            njp.setDate(DateUtil.dateToStringyyyy_MM_dd(nd.getDate()));
            njp.setType(widgetService.getReporttestType(nd.getType()));
            njp.setColor(widgetService.getReporttestColor(nd.getType()));
            njp.setExcerpt(nd.getExcerpt());
            jpList.add(njp);
        }
        return jpList;
    }

    // Reporttest detail for sns
    //eg:http://localhost:8081/moveup_war/sns/reporttestDetail/4hIZRgPJFu/
    @RequestMapping(value = "/sns/reporttestDetail/{uuid}/", method = RequestMethod.GET)
    public Object getReporttestSNSDetail(@PathVariable String uuid, Model model, HttpServletRequest hsr) {

        Reporttest reporttest = reporttestService.getReporttestByUuid(uuid);
        List<ReporttestDetail> reporttestDetailList = reporttestService.getReporttestDetailAll(reporttest.getId());
        if(reporttestDetailList.size()>0){
            ReporttestDetail reporttestDetail = reporttestDetailList.get(0);

            model.addAttribute("title", reporttestDetail.getTitle());
            model.addAttribute("url", "https://www.japanmoveupwest.com" + "/reporttestDetail/" + reporttest.getUuid() + "/");
            model.addAttribute("desc",  reporttest.getExcerpt());
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
            return "redirect:"+ hosturl + "/reporttestDetail/" + uuid + "/";
        }
    }

    // Reporttest detail for sns
    //eg:http://localhost:8081/moveup_war/api/sns/reporttestDetail/app/4hIZRgPJFu/
    @RequestMapping(value = "/api/sns/reporttestDetail/app/{uuid}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getReporttestDetailSNSForApp(@PathVariable String uuid) {

        Map<String, Object> urlMap = new HashMap<String, Object>();
        urlMap.put("twitter","https://twitter.com/share?url="+hosturl+"/reporttestDetail/"+uuid+"/");
        urlMap.put("facebook","https://www.facebook.com/sharer/sharer.php?u="+hosturl+"/reporttestDetail/"+uuid+"/");

        jsonObject.setResultList(urlMap);
        jsonObject.setStatus(JsonStatus.SUCCESS);
        return jsonObject;
    }
}