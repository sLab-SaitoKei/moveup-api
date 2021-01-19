package jp.co.vermore.service;

import java.util.Date;
import java.util.List;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.common.util.StringUtil;
import jp.co.vermore.form.admin.FreePaperForm;
import jp.co.vermore.form.admin.FreePaperListForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jp.co.vermore.entity.FreePaper;
import jp.co.vermore.entity.FreePaperDetail;
import jp.co.vermore.mapper.FreePaperDetailMapper;
import jp.co.vermore.mapper.FreePaperMapper;

/**
 * FreePaperService
 * Created by wubin.
 *
 * DateTime: 2018/03/20 9:22
 * Copyright: sLab, Corp
 */
@Service
public class FreePaperService {

    @Autowired
    private FreePaperMapper freePaperMapper;

    @Autowired
    private FreePaperDetailMapper freePaperDetailMapper;

    //freePaper list
    public List<FreePaper> getFreePaperAll(int limit, int offset) {
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        List<FreePaper> freePaperList = freePaperMapper.getFreePaperJsonAll(tomorrow,today,limit, offset);
        return freePaperList;
    }

    public List<FreePaper> getFreePaper() {
        List<FreePaper> freePaperList = freePaperMapper.getFreePaperAll();
        return freePaperList;
    }

    public FreePaper getFreePaperById(Long id) {
        FreePaper entity = freePaperMapper.getFreePaperById(id);
        return entity;
    }

    public FreePaper getFreePaperByUuid(String uuid) {
        FreePaper entity = freePaperMapper.getFreePaperByUuid(uuid);
        return entity;
    }

    //freePaper list by year
    public List<FreePaper> getFreePaperByYear(String year, int limit, int offset) {
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        List<FreePaper> freePaperList = freePaperMapper.getFreePaperByYear(year,tomorrow,today, limit, offset);
        return freePaperList;
    }

    //freePaper list for top
    public List<FreePaper> getFreePaperForTop() {
        List<FreePaper> freePaperList = freePaperMapper.getFreePaperForTop();
        return freePaperList;
    }

    //freePaper detail
    public List<FreePaperDetail> getFreePaperDetailAll(Long freePaperId, Byte picType) {
        List<FreePaperDetail> freePaperDetailList = freePaperDetailMapper.getFreePaperDetailJsonAll(freePaperId,picType);
        return freePaperDetailList;
    }

    //freePaper detail
    public List<FreePaperDetail> getFreePaperDetailAll() {
        List<FreePaperDetail> freePaperDetailList = freePaperDetailMapper.getFreePaperDetailAll();
        return freePaperDetailList;
    }


    public long insertFreePaper(FreePaperForm form) {
        FreePaper entity = new FreePaper();
        String uuid = "";
        int flagUuid = 0;
        int cntSelect = 0;
        while (flagUuid != 1 && cntSelect < 100){
            uuid = StringUtil.getUuid();
            if (getFreePaperByUuid(uuid) == null){
                flagUuid = 1;
            }
            cntSelect++;
        }

        entity.setUuid(uuid);
        entity.setSortScore(form.getSortScore());
        entity.setDate(DateUtil.stringToDateyyyy_MM_dd(form.getDate()));
        entity.setTitle(form.getTitle());
        entity.setVolume(form.getVolume());
        entity.setPicUrl(form.getPicUrl());
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
        entity.setCreateDatetime(new Date(System.currentTimeMillis()));
        entity.setUpdateDatetime(new Date(System.currentTimeMillis()));
        entity.setDelFlg(Boolean.FALSE);
        entity.setNote(Constant.EMPTY_STRING);
        freePaperMapper.insertFreePaper(entity);
        return entity.getId();
    }

    public long insertFreePaperDetail(FreePaperForm form,long id,Byte picType) {
        FreePaperDetail entity = new FreePaperDetail();
        entity.setFreePaperId(id);
        entity.setPicType(picType);
        entity.setPicUrl(form.getPicUrlDetail());
        entity.setCreateDatetime(new Date(System.currentTimeMillis()));
        entity.setUpdateDatetime(new Date(System.currentTimeMillis()));
        entity.setDelFlg(Boolean.FALSE);
        entity.setNote(Constant.EMPTY_STRING);
        freePaperDetailMapper.insertFreePaperDetail(entity);
        return entity.getId();
    }

    public long insertFreePaperDetail(FreePaperDetail freePaperDetail) {
        freePaperDetailMapper.insertFreePaperDetail(freePaperDetail);
        return freePaperDetail.getId();
    }

    public long updateFreePaper(FreePaperForm form) {
        FreePaper entity = new FreePaper();
        entity.setId(form.getId());
        entity.setVolume(form.getVolume());
        entity.setDate(DateUtil.stringToDateyyyy_MM_dd(form.getDate()));
        entity.setTitle(form.getTitle());
        entity.setSortScore(form.getSortScore());
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
        entity.setCreateDatetime(new Date(System.currentTimeMillis()));
        entity.setUpdateDatetime(new Date(System.currentTimeMillis()));
        entity.setDelFlg(Boolean.FALSE);
        entity.setNote(Constant.EMPTY_STRING);
        freePaperMapper.updateFreePaper(entity);
        return entity.getId();
    }

    public int deleteFreePaper(Long id) {
        return freePaperMapper.deleteFreePaper(id);
    }

    public int deleteFreePaperDetail(Long id) {
        return freePaperDetailMapper.deleteFreePaperDetail(id);
    }

    public List<FreePaper> getFreePaperAllByCondition(FreePaperListForm form) {
        List<FreePaper> freepaper = freePaperMapper.getFreePaperAllByCondition(form);
        return freepaper;
    }

    public int getFreePaperCountByCondition(FreePaperListForm form) {
        return freePaperMapper.getFreePaperCountByCondition(form);
    }

    public int getFreePaperCount() {
        return freePaperMapper.getFreePaperCount();
    }
}
