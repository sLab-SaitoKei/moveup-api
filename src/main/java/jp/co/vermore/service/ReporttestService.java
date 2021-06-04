package jp.co.vermore.service;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.common.util.StringUtil;
import jp.co.vermore.entity.Reporttest;
import jp.co.vermore.entity.ReporttestDetail;
import jp.co.vermore.form.admin.ReporttestForm;
import jp.co.vermore.form.admin.ReporttestListForm;
import jp.co.vermore.mapper.ReporttestDetailMapper;
import jp.co.vermore.mapper.ReporttestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * ReporttestService
 * Created by wubin.
 * <p>
 * DateTime: 2018/03/03 11:13
 * Copyright: sLab, Corp
 */

@Service

public class ReporttestService {

    @Autowired
    private ReporttestMapper reporttestMapper;

    public Reporttest getReporttestByUuid(String uuid) {
        Reporttest entity = reporttestMapper.getReporttestByUuid(uuid);
        return entity;
    }

    public List<Reporttest> getReporttestAll() {
        List<Reporttest> reporttestList = reporttestMapper.getReporttestAll();
        return reporttestList;
    }

    public List<Reporttest> getReporttestAllForTop() {
        String nowMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(0);
        String nextMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(1);
//        String now= DateUtil.dateToStringyyyy_MM_dd_HH_mm(new Date(System.currentTimeMillis()));
        List<Reporttest> reporttestList = reporttestMapper.getReporttestAllForTop(nowMin,nextMin);
        return reporttestList;
    }

    public List<Reporttest> getReporttestCategory(int type,int limit,int offset) {
        List<Reporttest> reporttestList = reporttestMapper.getReporttestCategory(type,limit,offset);
        return reporttestList;
    }

    public List<Reporttest>getReporttestPre(Date date) {
        String nowMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(0);
        String nextMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(1);
        List<Reporttest> reporttest = reporttestMapper.getReporttestPre(date,nowMin,nextMin);
        return reporttest;
    }

    public List<Reporttest> getReporttestNext(Date date) {
        String nowMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(0);
        String nextMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(1);
        List<Reporttest> reporttest = reporttestMapper.getReporttestNext(date,nowMin,nextMin);
        return reporttest;
    }

    public List<Reporttest> getReporttestAll(int type,int limit,int offset) {
        String nowMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(0);
        String nextMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(1);
        List<Reporttest> reporttestList = reporttestMapper.getReporttestJsonAll(type,nowMin,nextMin,limit, offset);
        return reporttestList;
    }

    public List<Reporttest> getReporttestAllByType(int type) {
        String nowMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(0);
        String nextMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(1);
        List<Reporttest> reporttestList = reporttestMapper.getReporttestJsonAllByType(type,nowMin,nextMin);
        return reporttestList;
    }

    public Reporttest getReporttestByIdAndType(long id,int type) {
        Reporttest reporttest = reporttestMapper.getReporttestByIdAndType(id,type);
        return reporttest;
    }

    public List<Reporttest> getReporttestEventAll(int type1,int type2,int limit,int offset) {
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        List<Reporttest> reporttestList = reporttestMapper.getReporttestEventAll(type1,type2,tomorrow,today,limit, offset);
        return reporttestList;
    }

    private List<Reporttest> convertTo(List<Reporttest> demoList) {
        List<Reporttest> resultList = new LinkedList<Reporttest>();
        for (Reporttest entity : demoList) {
            resultList.add(entity);
        }
        return resultList;
    }

    @Autowired
    private ReporttestMapper addReporttestMapper;
    //TODO
    public long insertReporttest(ReporttestForm reporttestForm) {
        Reporttest reporttest = new Reporttest();
        String uuid = "";
        int flagUuid = 0;
        int cntSelect = 0;
        while (flagUuid != 1 && cntSelect < 100){
            uuid = StringUtil.getUuid();
            if (getReporttestByUuid(uuid) == null){
                flagUuid = 1;
            }
            cntSelect++;
        }

        reporttest.setUuid(uuid);
        String date = reporttestForm.getDate();
        reporttest.setDate(DateUtil.stringToDateyyyy_MM_dd_HH_mm(date.replace("T"," ")));
        reporttest.setTitle(reporttestForm.getTitle());
        reporttest.setType(reporttestForm.getType());
        reporttest.setSortScore(reporttestForm.getSortScore());
        reporttest.setExcerpt(reporttestForm.getExcerpt());
        if(reporttestForm.getPublishStart() == null || "".equals(reporttestForm.getPublishStart())){
            reporttest.setPublishStart(DateUtil.getDefaultDate());
        }else{
            reporttest.setPublishStart(DateUtil.stringToDateyyyy_MM_dd_HH_mm(reporttestForm.getPublishStart().replace("T"," ")));
        }
        if(reporttestForm.getPublishEnd() == null || "".equals(reporttestForm.getPublishEnd())){
            reporttest.setPublishEnd(DateUtil.getDefaultPublishEnd());
        }else{
            reporttest.setPublishEnd(DateUtil.stringToDateyyyy_MM_dd_HH_mm(reporttestForm.getPublishEnd().replace("T"," ")));
        }
        reporttest.setCreateDatetime(new Date(System.currentTimeMillis()));
        reporttest.setDelFlg(Boolean.FALSE);
        reporttest.setNote(Constant.EMPTY_STRING);
        addReporttestMapper.insertReporttest(reporttest);
        return reporttest.getId();
    }

    public long insertStudioReporttest(ReporttestForm reporttestForm) {
        Reporttest reporttest = new Reporttest();
        String uuid = "";
        int flagUuid = 0;
        int cntSelect = 0;
        while (flagUuid != 1 && cntSelect < 100){
            uuid = StringUtil.getUuid();
            if (getReporttestByUuid(uuid) == null){
                flagUuid = 1;
            }
            cntSelect++;
        }

        reporttest.setUuid(uuid);
        String date = reporttestForm.getDate();
        reporttest.setDate(DateUtil.stringToDateyyyy_MM_dd(date));
        reporttest.setTitle(reporttestForm.getTitle());
        reporttest.setType(reporttestForm.getType());
        reporttest.setSortScore(reporttestForm.getSortScore());
        reporttest.setExcerpt(reporttestForm.getExcerpt());
        if(reporttestForm.getPublishStart() == null || "".equals(reporttestForm.getPublishStart())){
            reporttest.setPublishStart(DateUtil.getDefaultDate());
        }else{
            reporttest.setPublishStart(DateUtil.stringToDateyyyy_MM_dd(reporttestForm.getPublishStart()));
        }
        if(reporttestForm.getPublishEnd() == null || "".equals(reporttestForm.getPublishEnd())){
            reporttest.setPublishEnd(DateUtil.getDefaultPublishEnd());
        }else{
            reporttest.setPublishEnd(DateUtil.stringToDateyyyy_MM_dd(reporttestForm.getPublishEnd()));
        }
        reporttest.setCreateDatetime(new Date(System.currentTimeMillis()));
        reporttest.setDelFlg(Boolean.FALSE);
        reporttest.setNote(Constant.EMPTY_STRING);
        addReporttestMapper.insertReporttest(reporttest);
        return reporttest.getId();
    }

    @Autowired
    private ReporttestDetailMapper reporttestDetailMapper;

    public long insertDetailReporttest(ReporttestForm reporttestForm,long reporttestId) {
        ReporttestDetail reporttestDetail = new ReporttestDetail();
        reporttestDetail.setReporttestId(reporttestId);
        String date = reporttestForm.getDate();
        reporttestDetail.setDate(DateUtil.stringToDateyyyy_MM_dd_HH_mm(date.replace("T"," ")));
        reporttestDetail.setTitle(reporttestForm.getTitle());
        reporttestDetail.setType(reporttestForm.getType());
        reporttestDetail.setDetail(reporttestForm.getDetail());
        reporttestDetail.setCreateDatetime(new Date(System.currentTimeMillis()));
        reporttestDetail.setDelFlg(Boolean.FALSE);
        reporttestDetail.setNote(Constant.EMPTY_STRING);
        reporttestDetailMapper.insertDetailReporttest(reporttestDetail);
        return reporttestDetail.getId();
    }

    public long insertDetailStudioReporttest(ReporttestForm reporttestForm,long reporttestId) {
        ReporttestDetail reporttestDetail = new ReporttestDetail();
        reporttestDetail.setReporttestId(reporttestId);
        String date = reporttestForm.getDate();
        reporttestDetail.setDate(DateUtil.stringToDateyyyy_MM_dd(date));
        reporttestDetail.setTitle(reporttestForm.getTitle());
        reporttestDetail.setType(reporttestForm.getType());
        reporttestDetail.setDetail(reporttestForm.getDetail());
        reporttestDetail.setCreateDatetime(new Date(System.currentTimeMillis()));
        reporttestDetail.setDelFlg(Boolean.FALSE);
        reporttestDetail.setNote(Constant.EMPTY_STRING);
        reporttestDetailMapper.insertDetailReporttest(reporttestDetail);
        return reporttestDetail.getId();
    }

    public int deleteReporttest(ReporttestForm reporttestForm) {
        Reporttest reporttest = new Reporttest();
        reporttest.setId(reporttestForm.getId());
        reporttest.setDelFlg(Boolean.TRUE);
        int count = reporttestMapper.deleteReporttest(reporttest);
        System.out.println(count);
        return count;
    }

    public int deleteDetailReporttest(ReporttestForm reporttestForm) {
        ReporttestDetail reporttestDetail = new ReporttestDetail();
        reporttestDetail.setReporttestId(reporttestForm.getId());
        reporttestDetail.setDelFlg(Boolean.TRUE);
        int count = reporttestDetailMapper.deleteDetailReporttest(reporttestDetail);
        return count;
    }

    public int updateReporttest(ReporttestForm reporttestForm) {
        Reporttest reporttest = new Reporttest();
        reporttest.setId(reporttestForm.getId());
        String date = reporttestForm.getDate();
        reporttest.setDate(DateUtil.stringToDateyyyy_MM_dd_HH_mm(date.replace("T"," ")));
        reporttest.setTitle(reporttestForm.getTitle());
        reporttest.setType(reporttestForm.getType());
        reporttest.setSortScore(reporttestForm.getSortScore());
        reporttest.setExcerpt(reporttestForm.getExcerpt());
        if(reporttestForm.getPublishStart() == null || "".equals(reporttestForm.getPublishStart())){
            reporttest.setPublishStart(DateUtil.getDefaultDate());
        }else{
            reporttest.setPublishStart(DateUtil.stringToDateyyyy_MM_dd_HH_mm(reporttestForm.getPublishStart().replace("T"," ")));
        }
        if(reporttestForm.getPublishEnd() == null || "".equals(reporttestForm.getPublishEnd())){
            reporttest.setPublishEnd(DateUtil.getDefaultPublishEnd());
        }else{
            reporttest.setPublishEnd(DateUtil.stringToDateyyyy_MM_dd_HH_mm(reporttestForm.getPublishEnd().replace("T"," ")));
        }
        reporttest.setUpdateDatetime(new Date(System.currentTimeMillis()));
        reporttest.setDelFlg(Boolean.FALSE);
        reporttest.setNote(Constant.EMPTY_STRING);
        int count = reporttestMapper.updateReporttest(reporttest);
        return count;
    }

    public int updateStudioReporttest(ReporttestForm reporttestForm) {
        Reporttest reporttest = new Reporttest();
        reporttest.setId(reporttestForm.getId());
        String date = reporttestForm.getDate();
        reporttest.setDate(DateUtil.stringToDateyyyy_MM_dd(date));
        reporttest.setTitle(reporttestForm.getTitle());
        reporttest.setType(reporttestForm.getType());
        reporttest.setSortScore(reporttestForm.getSortScore());
        reporttest.setExcerpt(reporttestForm.getExcerpt());
        if(reporttestForm.getPublishStart() == null || "".equals(reporttestForm.getPublishStart())){
            reporttest.setPublishStart(DateUtil.getDefaultDate());
        }else{
            reporttest.setPublishStart(DateUtil.stringToDateyyyy_MM_dd(reporttestForm.getPublishStart()));
        }
        if(reporttestForm.getPublishEnd() == null || "".equals(reporttestForm.getPublishEnd())){
            reporttest.setPublishEnd(DateUtil.getDefaultPublishEnd());
        }else{
            reporttest.setPublishEnd(DateUtil.stringToDateyyyy_MM_dd(reporttestForm.getPublishEnd()));
        }
        reporttest.setUpdateDatetime(new Date(System.currentTimeMillis()));
        reporttest.setDelFlg(Boolean.FALSE);
        reporttest.setNote(Constant.EMPTY_STRING);
        int count = reporttestMapper.updateReporttest(reporttest);
        return count;
    }

    public int updateDetailReporttest(ReporttestForm reporttestForm) {
        ReporttestDetail reporttestDetail = new ReporttestDetail();
        reporttestDetail.setReporttestId(reporttestForm.getId());
        String date = reporttestForm.getDate();
        reporttestDetail.setDate(DateUtil.stringToDateyyyy_MM_dd_HH_mm(date.replace("T"," ")));
        reporttestDetail.setTitle(reporttestForm.getTitle());
        reporttestDetail.setType(reporttestForm.getType());
        reporttestDetail.setDetail(reporttestForm.getDetail());
        reporttestDetail.setUpdateDatetime(new Date(System.currentTimeMillis()));
        reporttestDetail.setDelFlg(Boolean.FALSE);
        reporttestDetail.setNote(Constant.EMPTY_STRING);
        int count = reporttestDetailMapper.updateDetailReporttest(reporttestDetail);
        return count;
    }

    public int updateDetailStudioReporttest(ReporttestForm reporttestForm) {
        ReporttestDetail reporttestDetail = new ReporttestDetail();
        reporttestDetail.setReporttestId(reporttestForm.getId());
        String date = reporttestForm.getDate();
        reporttestDetail.setDate(DateUtil.stringToDateyyyy_MM_dd(date));
        reporttestDetail.setTitle(reporttestForm.getTitle());
        reporttestDetail.setType(reporttestForm.getType());
        reporttestDetail.setDetail(reporttestForm.getDetail());
        reporttestDetail.setUpdateDatetime(new Date(System.currentTimeMillis()));
        reporttestDetail.setDelFlg(Boolean.FALSE);
        reporttestDetail.setNote(Constant.EMPTY_STRING);
        int count = reporttestDetailMapper.updateDetailReporttest(reporttestDetail);
        return count;
    }

    public List<Reporttest> getReporttestList(long id) {
        List<Reporttest> reporttestList = reporttestMapper.getReporttestList(id);
        return reporttestList;
    }

    public String getReporttestDetail(long id) {
        String detail = reporttestDetailMapper.getReporttestDetail(id);
        return detail;
    }

    public List<ReporttestDetail> getReporttestDetailAll(Long id) {
        List<ReporttestDetail> reporttestDetail = reporttestDetailMapper.getReporttestDetailAll(id);
        List<ReporttestDetail> resultList = convertToDetail(reporttestDetail);
        return resultList;
    }

    private List<ReporttestDetail> convertToDetail(List<ReporttestDetail> demoList) {
        List<ReporttestDetail> resultList = new LinkedList<ReporttestDetail>();
        for (ReporttestDetail entity : demoList) {
            resultList.add(entity);
        }
        return resultList;
    }

    public List<Reporttest> getReporttestAllByCondition(ReporttestListForm form) {
        List<Reporttest> reporttest = reporttestMapper.getReporttestAllByCondition(form);
        return reporttest;
    }

    public int getReporttestCountByCondition(ReporttestListForm form) {
        return reporttestMapper.getReporttestCountByCondition(form);
    }

    public int getReporttestCount() {
        return reporttestMapper.getReporttestCount();
    }

    public List<Reporttest> getStudioReporttestALL(int type) {
        List<Reporttest> reporttest = reporttestMapper.getStudioReporttestALL(type);
        return reporttest;
    }
}