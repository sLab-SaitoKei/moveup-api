package jp.co.vermore.controller;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.JsonObject;
import jp.co.vermore.common.JsonStatus;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.entity.EntryMail;
import jp.co.vermore.entity.Pic;
import jp.co.vermore.entity.Ramen;
import jp.co.vermore.entity.RamenDetail;
import jp.co.vermore.jsonparse.RamenDetailJsonParse;
import jp.co.vermore.jsonparse.RamenJsonParse;
import jp.co.vermore.service.EntryService;
import jp.co.vermore.service.PicService;
import jp.co.vermore.service.RamenService;
import jp.co.vermore.service.WidgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * RamenController
 * Created by saito.
 * <p>
 * DateTime: 2022/06/13 12:02
 * Copyright: sLab, Corp
 */
@Controller
public class RamenController extends BaseController {

    @Autowired
    private RamenService ramenService;

    @Autowired
    private EntryService entryService;

    @Autowired
    private PicService picService;

    @Autowired
    private WidgetService widgetService;

    @Value(value = "${hosturl}")
    private String hosturl;

    //eg: http://localhost:8081/moveup_war/api/ramen/list/0/1/0/
    @RequestMapping(value = "/api/ramen/list/{type}/{limit}/{offset}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getRamenList(@PathVariable int type,@PathVariable int limit, @PathVariable int offset) {
        List<Ramen> list = ramenService.getRamenAll(type,limit, offset);
        List<Ramen> countlist = ramenService.getRamenAllByType(type);
        List<RamenJsonParse> ejpList = new ArrayList<>();
        ejpList = list(ejpList, list);
        Map<String,Object> map = new HashMap<>();
        map.put("ramenList",ejpList);
        map.put("count",countlist.size());
        jsonObject.setResultList(map);
        return jsonObject;
    }

    //eg:http://localhost:8081/moveup_war/api/ramen/list/0/1/1/0/
    @RequestMapping(value = "/api/ramen/list/{type1}/{type2}/{limit}/{offset}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getRamenEventList(@PathVariable int type1,@PathVariable int type2,@PathVariable int limit, @PathVariable int offset) {
        List<Ramen> list = ramenService.getRamenEventAll(type1,type2,limit, offset);
        List<RamenJsonParse> ejpList = new ArrayList<>();
        ejpList = list(ejpList, list);
        Map<String,Object> map = new HashMap<>();
        map.put("ramenList",ejpList);
        map.put("count",ejpList.size());
        jsonObject.setResultList(map);
        return jsonObject;
    }

    //eg:http://localhost:8081/moveup_war/api/ramen/detail/VfWfbJc3z2/  1111111111111111
    @RequestMapping(value = "/api/ramen/detail/{uuid}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getRamenDetailList(@PathVariable String uuid) {
        Ramen ramen = ramenService.getRamenByUuid(uuid);
        List<RamenDetailJsonParse> ejpList = new ArrayList<>();
        List<RamenDetail> list = ramenService.getRamenDetailAll(ramen.getId());
        RamenDetailJsonParse ejp = new RamenDetailJsonParse();
        if(list.size()>0){
            for (RamenDetail ed: list) {
                ejp.setRamenId(ed.getRamenId());
                ejp.setTitle(ed.getTitle());
                ejp.setDate(DateUtil.dateToStringyyyy_MM_dd(ed.getDate()));
                ejp.setTypeStr(widgetService.getRamenType(ed.getType()));
                ejp.setType(ed.getType());
                ejp.setColor(widgetService.getRamenColor(ed.getType()));
                ejp.setDetail(ed.getDetail());

                Pic topPic = new Pic();
                List<Pic> topPicList = picService.getPic(ed.getRamenId(), Constant.EVENT_PIC_TYPE.RAMEN_TOP);
                List<String> topList = new ArrayList<String>();
                for(Pic pic:topPicList){
                    topList.add(pic.getPicUrl());
                }
                ejp.setTopPic(topList);

                List<Pic> footPicList = picService.getPic(ed.getRamenId(),Constant.EVENT_PIC_TYPE.RAMEN_FOOT);
                List<String> footList = new ArrayList<String>();
                for(Pic pic:footPicList){
                    footList.add(pic.getPicUrl());
                }
                ejp.setFootPic(footList);
                List<Ramen> listPre = ramenService.getRamenPre(ed.getDate());
                List<Ramen> listNext = ramenService.getRamenNext(ed.getDate());
                List<RamenJsonParse> ejpListPre = new ArrayList<>();
                List<RamenJsonParse> ejpListNext = new ArrayList<>();
                ejpListPre = list(ejpListPre, listPre);
                ejpListNext = list(ejpListNext, listNext);
                if(listPre.size()>0){
                    ejpListPre.get(0).setColor(widgetService.getRamenDetailColor(listPre.get(0).getType()));
                }
                if(listNext.size()>0){
                    ejpListNext.get(0).setColor(widgetService.getRamenDetailColor(listNext.get(0).getType()));
                }
                ejp.setRamenPre(ejpListPre);
                ejp.setRamenNext(ejpListNext);
                ejpList.add(ejp);
            }

            int type =0;
            if(ramen.getType() == Constant.RAMEN_TYPE.EVENT){
                type =  Constant.RAMEN_TYPE.MOVEUP;
            }else if(ramen.getType() == Constant.RAMEN_TYPE.MOVEUP){
                type = Constant.RAMEN_TYPE.EVENT;
            }

            EntryMail entryMail = entryService.getEntryMailByEntryIdAndType(ramen.getId(),type);
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

    private List<RamenJsonParse> list(List<RamenJsonParse> jpList, List<Ramen> list) {

        for (Ramen nd: list) {
            RamenJsonParse njp = new RamenJsonParse();
            njp.setUuid(nd.getUuid());
            njp.setTitle(nd.getTitle());
            njp.setDate(DateUtil.dateToStringyyyy_MM_dd(nd.getDate()));
            njp.setType(widgetService.getRamenType(nd.getType()));
            njp.setColor(widgetService.getRamenColor(nd.getType()));
            njp.setExcerpt(nd.getExcerpt());
            jpList.add(njp);
        }
        return jpList;
    }

    // Ramen detail for sns
    //eg:http://localhost:8081/moveup_war/sns/ramenDetail/4hIZRgPJFu/
    @RequestMapping(value = "/sns/ramenDetail/{uuid}/", method = RequestMethod.GET)
    public Object getRamenSNSDetail(@PathVariable String uuid, Model model, HttpServletRequest hsr) {

        Ramen ramen = ramenService.getRamenByUuid(uuid);
        List<RamenDetail> ramenDetailList = ramenService.getRamenDetailAll(ramen.getId());
        if(ramenDetailList.size()>0){
            RamenDetail ramenDetail = ramenDetailList.get(0);

            model.addAttribute("title", ramenDetail.getTitle());
            model.addAttribute("url", "https://www.japanmoveupwest.com" + "/ramenDetail/" + ramen.getUuid() + "/");
            model.addAttribute("desc",  ramen.getExcerpt());
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
            return "redirect:"+ hosturl + "/ramenDetail/" + uuid + "/";
        }
    }

    // Ramen detail for sns
    //eg: http://localhost:8081/moveup_war/api/sns/ramenDetail/app/4hIZRgPJFu/
    @RequestMapping(value = "/api/sns/ramenDetail/app/{uuid}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getRamenDetailSNSForApp(@PathVariable String uuid) {

        Map<String, Object> urlMap = new HashMap<String, Object>();
        urlMap.put("twitter","https://twitter.com/share?url="+hosturl+"/ramenDetail/"+uuid+"/");
        urlMap.put("facebook","https://www.facebook.com/sharer/sharer.php?u="+hosturl+"/ramenDetail/"+uuid+"/");

        jsonObject.setResultList(urlMap);
        jsonObject.setStatus(JsonStatus.SUCCESS);
        return jsonObject;
    }
}