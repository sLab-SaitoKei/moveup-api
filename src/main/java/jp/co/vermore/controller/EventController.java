package jp.co.vermore.controller;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.JsonObject;
import jp.co.vermore.common.mvc.APIException;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.entity.EntryMail;
import jp.co.vermore.entity.Event;
import jp.co.vermore.entity.EventDetail;
import jp.co.vermore.entity.Pic;
import jp.co.vermore.jsonparse.EventDetailJsonParse;
import jp.co.vermore.jsonparse.EventJsonParse;
import jp.co.vermore.jsonparse.EventListJsonParse;
import jp.co.vermore.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * EventController
 * Created by xieyoujun.
 * <p>
 * DateTime: 2018/02/28 17:34
 * Copyright: sLab, Corp
 */

@Controller
public class EventController extends BaseController {

    @Autowired
    private EventService eventService;

    @Autowired
    private EntryService entryService;

    @Autowired
    private PicService picService;

    @Value(value = "${hosturl}")
    private String hosturl;


    // Event list
    @RequestMapping(value = "/api/event/list/{year}/{limit}/{offset}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getEventList(@PathVariable String year,@PathVariable Long limit, @PathVariable Long offset, HttpServletRequest hsr) throws APIException {

        List<Event> list = eventService.getEventAll1();
        List<EventJsonParse> ejpLis = new ArrayList<>();
        EventListJsonParse eljp = new EventListJsonParse();
//        if (list.size() == 0) throw new APIException(JsonStatus.DATA_NOT_FOUND);
        if(year.equals("0")){
            for (Event ed: list) {
                EventJsonParse ejp = new EventJsonParse();
                ejp.setUuid(ed.getUuid());
                ejp.setTitle(ed.getTitle());
                ejp.setExcerpt(ed.getExcerpt());
                ejp.setGuestName(ed.getGuestName());
                ejp.setHallName(ed.getHallName());
                ejp.setHoldDate(DateUtil.dateToStringyyyymmdd(ed.getHoldDate()));
                ejp.setPicUrl(ed.getPicUrl());
                ejpLis.add(ejp);
            }
        }else{
            for (Event ed: list) {
                String holdDate = DateUtil.dateToStringyyyymmdd(ed.getHoldDate());
                String yyyy = holdDate.substring(0,4);
                if(year.equals(yyyy)){
                    EventJsonParse ejp = new EventJsonParse();
                    ejp.setUuid(ed.getUuid());
                    ejp.setTitle(ed.getTitle());
                    ejp.setExcerpt(ed.getExcerpt());
                    ejp.setGuestName(ed.getGuestName());
                    ejp.setHallName(ed.getHallName());
                    ejp.setHoldDate(DateUtil.dateToStringyyyymmdd(ed.getHoldDate()));
                    ejp.setPicUrl(ed.getPicUrl());
                    ejpLis.add(ejp);
                }else {
                    continue;
                }
            }
        }

        List<EventJsonParse> ejpList = new ArrayList<>();
        for(long i=offset;i<ejpLis.size()&&i<offset+limit;i++){
            ejpList.add(ejpLis.get((int)i));
        }

        List<Event> list2 = eventService.getEvent();
        List<EventJsonParse> ejpList2 = new ArrayList<>();
        if(year.equals("0")){
            for (Event ed: list2) {
                EventJsonParse ejp = new EventJsonParse();
                ejp.setUuid(ed.getUuid());
                ejp.setTitle(ed.getTitle());
                ejp.setExcerpt(ed.getExcerpt());
                ejp.setGuestName(ed.getGuestName());
                ejp.setHallName(ed.getHallName());
                ejp.setHoldDate(DateUtil.dateToStringyyyymmdd(ed.getHoldDate()));
                ejp.setPicUrl(ed.getPicUrl());
                ejpList2.add(ejp);
            }
        }else{
            for (Event ed: list2) {
                String holdDate = DateUtil.dateToStringyyyymmdd(ed.getHoldDate());
                String yyyy = holdDate.substring(0,4);
                if(year.equals(yyyy)){
                    EventJsonParse ejp = new EventJsonParse();
                    ejp.setUuid(ed.getUuid());
                    ejp.setTitle(ed.getTitle());
                    ejp.setExcerpt(ed.getExcerpt());
                    ejp.setGuestName(ed.getGuestName());
                    ejp.setHallName(ed.getHallName());
                    ejp.setHoldDate(DateUtil.dateToStringyyyymmdd(ed.getHoldDate()));
                    ejp.setPicUrl(ed.getPicUrl());
                    ejpList2.add(ejp);
                }else {
                    continue;
                }
            }
        }
        eljp.setEventList(ejpList);
        eljp.setCount(ejpList2.size());
        jsonObject.setResultList(eljp);
        return jsonObject;
    }

    // Event detail.
    @RequestMapping(value = "/api/event/{uuid}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getEventDetail(@PathVariable String uuid) {

        Event event = eventService.getEventByUuid(uuid);
        EventDetail eventDetail = eventService.getEventDetailByEventId(event.getId());

        List<EventDetailJsonParse> edjpList = new ArrayList<>();
        EventDetailJsonParse edjp = new EventDetailJsonParse();

        edjp.setEventId(eventDetail.getEventId());
        edjp.setHoldDate(DateUtil.dateToStringyyyymmdd(eventDetail.getHoldDate()));
        edjp.setTitle(eventDetail.getTitle());
        edjp.setGustName(eventDetail.getGuestName());
        edjp.setHallName(eventDetail.getHallName());
        List<Pic> starPicList = picService.getPic(event.getId(), Constant.EVENT_PIC_TYPE.STAR);
        List<String> starList = new ArrayList<String>();
        for(Pic pic:starPicList){
            starList.add(pic.getPicUrl());
        }
        edjp.setStarPic(starList);
        List<Pic> commentPicList = picService.getPic(event.getId(), Constant.EVENT_PIC_TYPE.COMMENT);
        List<String> commentList = new ArrayList<String>();
        for(Pic pic:commentPicList){
            commentList.add(pic.getPicUrl());
        }
        edjp.setCommentPic(commentList);
        edjp.setDesc1(eventDetail.getDesc1());
        edjp.setVideoUrl1(eventDetail.getVideoUrl1());
        edjp.setComment(eventDetail.getComment());
        Event listPre = eventService.getEventPre(eventDetail.getHoldDate());
        Event listNext = eventService.getEventNext(eventDetail.getHoldDate());
        if(listPre != null){
            String preDate = DateUtil.dateToStringyyyymmdd(listPre.getHoldDate());
            listPre.setDateStr(preDate);
        }
       if(listNext != null){
           String nextDate = DateUtil.dateToStringyyyymmdd(listNext.getHoldDate());
           listNext.setDateStr(nextDate);
       }
        edjp.setEventPre(listPre);
        edjp.setEventNext(listNext);
        edjpList.add(edjp);

        EntryMail entryMail = entryService.getEntryMailByEntryIdAndType(event.getId(),3);
        if(entryMail != null){
            Date startTime = entryMail.getPublishStart();
            Date endTime = entryMail.getPublishEnd();
            Date nowTime = new Date(System.currentTimeMillis());
            if(nowTime.getTime() >= startTime.getTime() && nowTime.getTime() <= endTime.getTime()){
                edjp.setEntry("1");
            }else{
                edjp.setEntry(null);
            }
        }

        jsonObject.setResultList(edjpList);
        return jsonObject;
    }

    // Event detail for sns
    @RequestMapping(value = "/sns/eventDetail/{uuid}/", method = RequestMethod.GET)
    public Object getEventSNSDetail(@PathVariable String uuid, Model model, HttpServletRequest hsr) {

        Event event = eventService.getEventByUuid(uuid);
        EventDetail eventDetail = eventService.getEventDetailByEventId(event.getId());

        model.addAttribute("title", eventDetail.getTitle());
//        model.addAttribute("url", hosturl + "/eventDetail/" + event.getUuid() + "/");
        model.addAttribute("url", "https://www.japanmoveupwest.com" + "/eventDetail/" + event.getUuid() + "/");
        model.addAttribute("desc",  event.getExcerpt());
        model.addAttribute("image",  eventDetail.getPicUrl1());

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
            return "redirect:"+ hosturl + "/eventDetail/" + uuid + "/";
        }

    }

    // Event detail for sns
    @RequestMapping(value = "/eventDetail/sns/{uuid}/", method = RequestMethod.GET)
    public Object getEventSNS2Detail(@PathVariable String uuid, Model model) {

        Event event = eventService.getEventByUuid(uuid);
        EventDetail eventDetail = eventService.getEventDetailByEventId(event.getId());

        model.addAttribute("title", eventDetail.getTitle());
        model.addAttribute("url", hosturl + "/eventDetail/" + event.getUuid() + "/");
        model.addAttribute("desc",  event.getExcerpt());
        model.addAttribute("image",  eventDetail.getPicUrl1());
        return "sns";
    }

}
