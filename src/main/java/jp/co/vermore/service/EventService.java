package jp.co.vermore.service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.common.util.StringUtil;
import jp.co.vermore.entity.EventDetail;
import jp.co.vermore.entity.News;
import jp.co.vermore.form.EventRegistForm;
import jp.co.vermore.form.admin.DatatablesBaseForm;
import jp.co.vermore.form.admin.EventListForm;
import jp.co.vermore.mapper.EventDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.vermore.entity.Event;
import jp.co.vermore.mapper.EventMapper;

/**
 * EventService
 * Created by xieyoujun.
 * <p>
 * DateTime: 2018/03/01 10:45
 * Copyright: sLab, Corp
 */
@Service
public class EventService {

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private EventDetailMapper eventDetailMapper;

    public List<Event> getEventAll(Long limit, Long offset) {
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        List<Event> eventList = eventMapper.getEventAll(tomorrow,today,limit, offset);
        return eventList;
    }

    public List<Event> getEventAll1() {
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        List<Event> eventList = eventMapper.getEventAll1(tomorrow,today);
        return eventList;
    }

    public List<Event> getEvent() {
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        List<Event> eventList = eventMapper.getEvent(tomorrow,today);
        return eventList;
    }

    public Event getById(Long id) {
        Event entity = eventMapper.selectByPrimaryKey(id);
        return entity;
    }

    public List<Event> getEventForTop() {
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        List<Event> eventList = eventMapper.getEventForTop(tomorrow,today);
        return eventList;
    }

    public Event getEventByUuid(String uuid) {
        Event entity = eventMapper.getEventByUuid(uuid);
        return entity;
    }

    public Event getEventById(long id) {
        Event entity = eventMapper.getEventById(id);
        return entity;
    }

    public Event getEventPre(Date date) {
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        Event entity = eventMapper.getEventPre(date,tomorrow,today);
        return entity;
    }

    public Event getEventNext(Date date) {
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        Event entity = eventMapper.getEventNext(date,tomorrow,today);
        return entity;
    }

    public long insertEvent(EventRegistForm form) {
        Event entity = new Event();
        String uuid = "";
        int flagUuid = 0;
        int cntSelect = 0;
        while (flagUuid != 1 && cntSelect < 100){
            uuid = StringUtil.getUuid();
            if (getEventByUuid(uuid) == null){
                flagUuid = 1;
            }
            cntSelect++;
        }

        entity.setUuid(uuid);
        entity.setSortScore(Integer.parseInt(form.getSortScore()));
        entity.setHoldDate(DateUtil.stringToDateyyyy_MM_dd(form.getHoldDate()));
        entity.setPicListNo(0);
        entity.setPicUrl(form.getPicUrl());
        entity.setTitle(form.getTitle());
        entity.setGuestName(form.getGustName());
        entity.setHallName(form.getHallName());
        entity.setExcerpt(form.getExcerpt());
        if(form.getPublishStart() == null || "".equals(form.getPublishStart())){
            entity.setPublishStart(DateUtil.getDefaultDate());
        }else{
            entity.setPublishStart(DateUtil.stringToDateyyyy_MM_dd(form.getPublishStart()));
        }
        if(form.getPublishEnd() == null || "".equals(form.getPublishEnd())){
            entity.setPublishEnd(DateUtil.getDefaultPublishEnd());
        }else{
            entity.setPublishEnd(DateUtil.stringToDateyyyy_MM_dd(form.getPublishEnd()));
        }

        eventMapper.insert(entity);
        return entity.getId();
    }

    public Event updateEvent(EventRegistForm form) {
        Event entity = new Event();
        entity.setId(form.getEventId());
        entity.setHoldDate(DateUtil.stringToDateyyyy_MM_dd(form.getHoldDate()));
        entity.setPicListNo(0);
        entity.setPicUrl(form.getPicUrl());
        entity.setSortScore(Integer.valueOf(form.getSortScore()));
        entity.setTitle(form.getTitle());
        entity.setGuestName(form.getGustName());
        entity.setHallName(form.getHallName());
        entity.setExcerpt(form.getExcerpt());
        if(form.getPublishStart() == null || "".equals(form.getPublishStart())){
            entity.setPublishStart(DateUtil.getDefaultDate());
        }else{
            entity.setPublishStart(DateUtil.stringToDateyyyy_MM_dd(form.getPublishStart()));
        }
        if(form.getPublishEnd() == null || "".equals(form.getPublishEnd())){
            entity.setPublishEnd(DateUtil.getDefaultPublishEnd());
        }else{
            entity.setPublishEnd(DateUtil.stringToDateyyyy_MM_dd(form.getPublishEnd()));
        }

        Long count = eventMapper.updateByPrimaryKeySelective(entity);
        return entity;
    }

    public Long deleteEvent(Long id) {
        Event entity = new Event();
        entity.setId(id);
        entity.setDelFlg(Boolean.TRUE);
        Long count = eventMapper.updateByPrimaryKeySelective(entity);
        return count;
    }

    private List<Event> convertTo(List<Event> eventList) {
        List<Event> resultList = new LinkedList<Event>();
        for (Event entity : eventList) {
            resultList.add(entity);
        }
        return resultList;
    }

    public EventDetail getEventDetailByEventId(Long eventId) {
        EventDetail entity = eventDetailMapper.selectByEventId(eventId);
        return entity;
    }

    public Long insertEventDetail(EventRegistForm form, Long eventId) {
        EventDetail entity = new EventDetail();
        entity.setEventId(eventId);
        entity.setTitle(form.getTitle());
        entity.setGuestName(form.getGustName());
        entity.setHallName(form.getHallName());
        entity.setHoldDate(DateUtil.stringToDateyyyy_MM_dd(form.getHoldDate()));
        entity.setPicUrl1("");
        entity.setPicUrl2("");
        entity.setPicUrl3("");
        entity.setPicUrl4("");
        entity.setPicUrl5("");
        entity.setPicUrl6("");
        entity.setPicUrl7("");
        entity.setPicUrl8("");
        entity.setPicUrl9("");
        entity.setPicUrl10("");
        entity.setVideoUrl1(form.getVideoUrl1());
        entity.setVideoUrl2("");
        entity.setDesc1(form.getDesc1());
        entity.setDesc2("");
        entity.setDesc3("");
        entity.setComment(form.getComment());

        Long count = eventDetailMapper.insert(entity);
        return count;
    }

    public Long updateEventDetail(EventRegistForm form) {
        EventDetail entity = new EventDetail();
        entity.setId(form.getEventDetailId());
        entity.setTitle(form.getTitle());
        entity.setGuestName(form.getGustName());
        entity.setHallName(form.getHallName());
        entity.setHoldDate(DateUtil.stringToDateyyyy_MM_dd(form.getHoldDate()));
        entity.setVideoUrl1(form.getVideoUrl1());
        entity.setVideoUrl2("");
        entity.setDesc1(form.getDesc1());
        entity.setDesc2("");
        entity.setDesc3("");
        entity.setComment(form.getComment());

        Long count = eventDetailMapper.updateByPrimaryKeySelective(entity);
        return count;
    }

    public Long deleteEventDetail(long id) {
        EventDetail entity = new EventDetail();
        entity.setId(id);
        entity.setDelFlg(Boolean.TRUE);
        Long count = eventDetailMapper.updateByPrimaryKeySelective(entity);
        return count;
    }


    public List<Event> getEventAllByCondition(EventListForm form) {
        List<Event> event = eventMapper.getEventAllByCondition(form);
        return event;
    }

    public int getEventCountByCondition(EventListForm form) {
        return eventMapper.getEventCountByCondition(form);
    }

    public int getEventCount() {
        return eventMapper.getEventCount();
    }

}
