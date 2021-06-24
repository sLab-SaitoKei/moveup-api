package jp.co.vermore.service;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.common.util.StringUtil;
import jp.co.vermore.entity.Ramen;
import jp.co.vermore.entity.RamenDetail;
import jp.co.vermore.form.admin.RamenForm;
import jp.co.vermore.form.admin.RamenListForm;
import jp.co.vermore.mapper.RamenDetailMapper;
import jp.co.vermore.mapper.RamenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * RamenService
 * Created by wubin.
 * <p>
 * DateTime: 2018/03/03 11:13
 * Copyright: sLab, Corp
 */

@Service

public class RamenService {

    @Autowired
    private RamenMapper ramenMapper;

    public Ramen getRamenByUuid(String uuid) {
        Ramen entity = ramenMapper.getRamenByUuid(uuid);
        return entity;
    }

    public List<Ramen> getRamenAll() {
        List<Ramen> ramenList = ramenMapper.getRamenAll();
        return ramenList;
    }

    public List<Ramen> getRamenAllForTop() {
        String nowMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(0);
        String nextMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(1);
//        String now= DateUtil.dateToStringyyyy_MM_dd_HH_mm(new Date(System.currentTimeMillis()));
        List<Ramen> ramenList = ramenMapper.getRamenAllForTop(nowMin,nextMin);
        return ramenList;
    }

    public List<Ramen> getRamenCategory(int type,int limit,int offset) {
        List<Ramen> ramenList = ramenMapper.getRamenCategory(type,limit,offset);
        return ramenList;
    }

    public List<Ramen>getRamenPre(Date date) {
        String nowMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(0);
        String nextMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(1);
        List<Ramen> ramen = ramenMapper.getRamenPre(date,nowMin,nextMin);
        return ramen;
    }

    public List<Ramen> getRamenNext(Date date) {
        String nowMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(0);
        String nextMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(1);
        List<Ramen> ramen = ramenMapper.getRamenNext(date,nowMin,nextMin);
        return ramen;
    }

    public List<Ramen> getRamenAll(int type,int limit,int offset) {
        String nowMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(0);
        String nextMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(1);
        List<Ramen> ramenList = ramenMapper.getRamenJsonAll(type,nowMin,nextMin,limit, offset);
        return ramenList;
    }

    public List<Ramen> getRamenAllByType(int type) {
        String nowMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(0);
        String nextMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(1);
        List<Ramen> ramenList = ramenMapper.getRamenJsonAllByType(type,nowMin,nextMin);
        return ramenList;
    }

    public Ramen getRamenByIdAndType(long id,int type) {
        Ramen ramen = ramenMapper.getRamenByIdAndType(id,type);
        return ramen;
    }

    public List<Ramen> getRamenEventAll(int type1,int type2,int limit,int offset) {
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        List<Ramen> ramenList = ramenMapper.getRamenEventAll(type1,type2,tomorrow,today,limit, offset);
        return ramenList;
    }

    private List<Ramen> convertTo(List<Ramen> demoList) {
        List<Ramen> resultList = new LinkedList<Ramen>();
        for (Ramen entity : demoList) {
            resultList.add(entity);
        }
        return resultList;
    }

    @Autowired
    private RamenMapper addRamenMapper;
    //TODO
    public long insertRamen(RamenForm ramenForm) {
        Ramen ramen = new Ramen();
        String uuid = "";
        int flagUuid = 0;
        int cntSelect = 0;
        while (flagUuid != 1 && cntSelect < 100){
            uuid = StringUtil.getUuid();
            if (getRamenByUuid(uuid) == null){
                flagUuid = 1;
            }
            cntSelect++;
        }

        ramen.setUuid(uuid);
        String date = ramenForm.getDate();
        ramen.setDate(DateUtil.stringToDateyyyy_MM_dd_HH_mm(date.replace("T"," ")));
        ramen.setTitle(ramenForm.getTitle());
        ramen.setType(ramenForm.getType());
        ramen.setSortScore(ramenForm.getSortScore());
        ramen.setExcerpt(ramenForm.getExcerpt());
        if(ramenForm.getPublishStart() == null || "".equals(ramenForm.getPublishStart())){
            ramen.setPublishStart(DateUtil.getDefaultDate());
        }else{
            ramen.setPublishStart(DateUtil.stringToDateyyyy_MM_dd_HH_mm(ramenForm.getPublishStart().replace("T"," ")));
        }
        if(ramenForm.getPublishEnd() == null || "".equals(ramenForm.getPublishEnd())){
            ramen.setPublishEnd(DateUtil.getDefaultPublishEnd());
        }else{
            ramen.setPublishEnd(DateUtil.stringToDateyyyy_MM_dd_HH_mm(ramenForm.getPublishEnd().replace("T"," ")));
        }
        ramen.setCreateDatetime(new Date(System.currentTimeMillis()));
        ramen.setDelFlg(Boolean.FALSE);
        ramen.setNote(Constant.EMPTY_STRING);
        addRamenMapper.insertRamen(ramen);
        return ramen.getId();
    }

    public long insertStudioRamen(RamenForm ramenForm) {
        Ramen ramen = new Ramen();
        String uuid = "";
        int flagUuid = 0;
        int cntSelect = 0;
        while (flagUuid != 1 && cntSelect < 100){
            uuid = StringUtil.getUuid();
            if (getRamenByUuid(uuid) == null){
                flagUuid = 1;
            }
            cntSelect++;
        }

        ramen.setUuid(uuid);
        String date = ramenForm.getDate();
        ramen.setDate(DateUtil.stringToDateyyyy_MM_dd(date));
        ramen.setTitle(ramenForm.getTitle());
        ramen.setType(ramenForm.getType());
        ramen.setSortScore(ramenForm.getSortScore());
        ramen.setExcerpt(ramenForm.getExcerpt());
        if(ramenForm.getPublishStart() == null || "".equals(ramenForm.getPublishStart())){
            ramen.setPublishStart(DateUtil.getDefaultDate());
        }else{
            ramen.setPublishStart(DateUtil.stringToDateyyyy_MM_dd(ramenForm.getPublishStart()));
        }
        if(ramenForm.getPublishEnd() == null || "".equals(ramenForm.getPublishEnd())){
            ramen.setPublishEnd(DateUtil.getDefaultPublishEnd());
        }else{
            ramen.setPublishEnd(DateUtil.stringToDateyyyy_MM_dd(ramenForm.getPublishEnd()));
        }
        ramen.setCreateDatetime(new Date(System.currentTimeMillis()));
        ramen.setDelFlg(Boolean.FALSE);
        ramen.setNote(Constant.EMPTY_STRING);
        addRamenMapper.insertRamen(ramen);
        return ramen.getId();
    }

    @Autowired
    private RamenDetailMapper ramenDetailMapper;

    public long insertDetailRamen(RamenForm ramenForm,long ramenId) {
        RamenDetail ramenDetail = new RamenDetail();
        ramenDetail.setRamenId(ramenId);
        String date = ramenForm.getDate();
        ramenDetail.setDate(DateUtil.stringToDateyyyy_MM_dd_HH_mm(date.replace("T"," ")));
        ramenDetail.setTitle(ramenForm.getTitle());
        ramenDetail.setType(ramenForm.getType());
        ramenDetail.setDetail(ramenForm.getDetail());
        ramenDetail.setCreateDatetime(new Date(System.currentTimeMillis()));
        ramenDetail.setDelFlg(Boolean.FALSE);
        ramenDetail.setNote(Constant.EMPTY_STRING);
        ramenDetailMapper.insertDetailRamen(ramenDetail);
        return ramenDetail.getId();
    }

    public long insertDetailStudioRamen(RamenForm ramenForm,long ramenId) {
        RamenDetail ramenDetail = new RamenDetail();
        ramenDetail.setRamenId(ramenId);
        String date = ramenForm.getDate();
        ramenDetail.setDate(DateUtil.stringToDateyyyy_MM_dd(date));
        ramenDetail.setTitle(ramenForm.getTitle());
        ramenDetail.setType(ramenForm.getType());
        ramenDetail.setDetail(ramenForm.getDetail());
        ramenDetail.setCreateDatetime(new Date(System.currentTimeMillis()));
        ramenDetail.setDelFlg(Boolean.FALSE);
        ramenDetail.setNote(Constant.EMPTY_STRING);
        ramenDetailMapper.insertDetailRamen(ramenDetail);
        return ramenDetail.getId();
    }

    public int deleteRamen(RamenForm ramenForm) {
        Ramen ramen = new Ramen();
        ramen.setId(ramenForm.getId());
        ramen.setDelFlg(Boolean.TRUE);
        int count = ramenMapper.deleteRamen(ramen);
        System.out.println(count);
        return count;
    }

    public int deleteDetailRamen(RamenForm ramenForm) {
        RamenDetail ramenDetail = new RamenDetail();
        ramenDetail.setRamenId(ramenForm.getId());
        ramenDetail.setDelFlg(Boolean.TRUE);
        int count = ramenDetailMapper.deleteDetailRamen(ramenDetail);
        return count;
    }

    public int updateRamen(RamenForm ramenForm) {
        Ramen ramen = new Ramen();
        ramen.setId(ramenForm.getId());
        String date = ramenForm.getDate();
        ramen.setDate(DateUtil.stringToDateyyyy_MM_dd_HH_mm(date.replace("T"," ")));
        ramen.setTitle(ramenForm.getTitle());
        ramen.setType(ramenForm.getType());
        ramen.setSortScore(ramenForm.getSortScore());
        ramen.setExcerpt(ramenForm.getExcerpt());
        if(ramenForm.getPublishStart() == null || "".equals(ramenForm.getPublishStart())){
            ramen.setPublishStart(DateUtil.getDefaultDate());
        }else{
            ramen.setPublishStart(DateUtil.stringToDateyyyy_MM_dd_HH_mm(ramenForm.getPublishStart().replace("T"," ")));
        }
        if(ramenForm.getPublishEnd() == null || "".equals(ramenForm.getPublishEnd())){
            ramen.setPublishEnd(DateUtil.getDefaultPublishEnd());
        }else{
            ramen.setPublishEnd(DateUtil.stringToDateyyyy_MM_dd_HH_mm(ramenForm.getPublishEnd().replace("T"," ")));
        }
        ramen.setUpdateDatetime(new Date(System.currentTimeMillis()));
        ramen.setDelFlg(Boolean.FALSE);
        ramen.setNote(Constant.EMPTY_STRING);
        int count = ramenMapper.updateRamen(ramen);
        return count;
    }

    public int updateStudioRamen(RamenForm ramenForm) {
        Ramen ramen = new Ramen();
        ramen.setId(ramenForm.getId());
        String date = ramenForm.getDate();
        ramen.setDate(DateUtil.stringToDateyyyy_MM_dd(date));
        ramen.setTitle(ramenForm.getTitle());
        ramen.setType(ramenForm.getType());
        ramen.setSortScore(ramenForm.getSortScore());
        ramen.setExcerpt(ramenForm.getExcerpt());
        if(ramenForm.getPublishStart() == null || "".equals(ramenForm.getPublishStart())){
            ramen.setPublishStart(DateUtil.getDefaultDate());
        }else{
            ramen.setPublishStart(DateUtil.stringToDateyyyy_MM_dd(ramenForm.getPublishStart()));
        }
        if(ramenForm.getPublishEnd() == null || "".equals(ramenForm.getPublishEnd())){
            ramen.setPublishEnd(DateUtil.getDefaultPublishEnd());
        }else{
            ramen.setPublishEnd(DateUtil.stringToDateyyyy_MM_dd(ramenForm.getPublishEnd()));
        }
        ramen.setUpdateDatetime(new Date(System.currentTimeMillis()));
        ramen.setDelFlg(Boolean.FALSE);
        ramen.setNote(Constant.EMPTY_STRING);
        int count = ramenMapper.updateRamen(ramen);
        return count;
    }

    public int updateDetailRamen(RamenForm ramenForm) {
        RamenDetail ramenDetail = new RamenDetail();
        ramenDetail.setRamenId(ramenForm.getId());
        String date = ramenForm.getDate();
        ramenDetail.setDate(DateUtil.stringToDateyyyy_MM_dd_HH_mm(date.replace("T"," ")));
        ramenDetail.setTitle(ramenForm.getTitle());
        ramenDetail.setType(ramenForm.getType());
        ramenDetail.setDetail(ramenForm.getDetail());
        ramenDetail.setUpdateDatetime(new Date(System.currentTimeMillis()));
        ramenDetail.setDelFlg(Boolean.FALSE);
        ramenDetail.setNote(Constant.EMPTY_STRING);
        int count = ramenDetailMapper.updateDetailRamen(ramenDetail);
        return count;
    }

    public int updateDetailStudioRamen(RamenForm ramenForm) {
        RamenDetail ramenDetail = new RamenDetail();
        ramenDetail.setRamenId(ramenForm.getId());
        String date = ramenForm.getDate();
        ramenDetail.setDate(DateUtil.stringToDateyyyy_MM_dd(date));
        ramenDetail.setTitle(ramenForm.getTitle());
        ramenDetail.setType(ramenForm.getType());
        ramenDetail.setDetail(ramenForm.getDetail());
        ramenDetail.setUpdateDatetime(new Date(System.currentTimeMillis()));
        ramenDetail.setDelFlg(Boolean.FALSE);
        ramenDetail.setNote(Constant.EMPTY_STRING);
        int count = ramenDetailMapper.updateDetailRamen(ramenDetail);
        return count;
    }

    public List<Ramen> getRamenList(long id) {
        List<Ramen> ramenList = ramenMapper.getRamenList(id);
        return ramenList;
    }

    public String getRamenDetail(long id) {
        String detail = ramenDetailMapper.getRamenDetail(id);
        return detail;
    }

    public List<RamenDetail> getRamenDetailAll(Long id) {
        List<RamenDetail> ramenDetail = ramenDetailMapper.getRamenDetailAll(id);
        List<RamenDetail> resultList = convertToDetail(ramenDetail);
        return resultList;
    }

    private List<RamenDetail> convertToDetail(List<RamenDetail> demoList) {
        List<RamenDetail> resultList = new LinkedList<RamenDetail>();
        for (RamenDetail entity : demoList) {
            resultList.add(entity);
        }
        return resultList;
    }

    public List<Ramen> getRamenAllByCondition(RamenListForm form) {
        List<Ramen> ramen = ramenMapper.getRamenAllByCondition(form);
        return ramen;
    }

    public int getRamenCountByCondition(RamenListForm form) {
        return ramenMapper.getRamenCountByCondition(form);
    }

    public int getRamenCount() {
        return ramenMapper.getRamenCount();
    }

    public List<Ramen> getStudioRamenALL(int type) {
        List<Ramen> ramen = ramenMapper.getStudioRamenALL(type);
        return ramen;
    }
}