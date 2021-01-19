package jp.co.vermore.service;

import java.util.Date;
import java.util.List;

import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.common.util.StringUtil;
import jp.co.vermore.entity.RecruitTag;
import jp.co.vermore.form.admin.RecruitListForm;
import jp.co.vermore.mapper.RecruitTagMapper;
import jp.co.vermore.validation.RecruitSearchParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.vermore.common.Constant;
import jp.co.vermore.entity.Recruit;
import jp.co.vermore.entity.RecruitDetail;
import jp.co.vermore.form.admin.RecruitForm;
import jp.co.vermore.mapper.RecruitDetailMapper;
import jp.co.vermore.mapper.RecruitMapper;
import org.springframework.util.MultiValueMap;

/**
 * RecruitService
 * Created by wubin.
 * <p>
 * DateTime: 2018/03/03 11:13
 * Copyright: sLab, Corp
 */

@Service
public class RecruitService {

    @Autowired
    private RecruitMapper recruitMapper;

    @Autowired
    private RecruitDetailMapper recruitDetailMapper;

    @Autowired
    private RecruitTagMapper recruitTagMapper;

    @Autowired
    private TagDetailService tagDetailService;

    public List<Integer> getContent(long recruitId ,int tagType){
        return recruitTagMapper.getContent(recruitId,tagType);
    }

    public List<String> getDescByRecruit(long recruitId ,int tagType){
        return recruitTagMapper.getDescByRecruit(recruitId,tagType);
    }

    public List<Recruit> getRecruitAllByKeyword(String keyWord) {
        return recruitMapper.getRecruitAllByKeyword(keyWord);
    }

    public int getRecruitId(int tagType,int content){
        List<Integer> recruitId = recruitTagMapper.getRecruitId(tagType,content);
        return content;
    }

    public List<RecruitTag> getRecruitTagList(List<Long> idList , List<Integer> tagList ){
        List<RecruitTag> list = recruitTagMapper.getRecruitTagList(idList,tagList);
        return list;
    }

    public int deleteTags(long recruitId, int tagType) {
        return recruitTagMapper.deleteTags(recruitId, tagType);
    }

    public int deleteDetailVideo( Long recruitId) {
        recruitDetailMapper.deleteDetailVideo(recruitId);
        return 0;
    }

    public int deleteTag(long recruitId, int tagType, int content) {
        return recruitTagMapper.deleteTag(recruitId, tagType, content);
    }

    public int insertTag(long recruitId, int tagType, int content) {
        RecruitTag recruitTag = new RecruitTag();
        recruitTag.setRecruitId(recruitId);
        recruitTag.setTagType((byte) tagType);
        recruitTag.setContent(content);
        recruitTag.setCreateDatetime(new Date(System.currentTimeMillis()));
        recruitTag.setUpdateDatetime(new Date(System.currentTimeMillis()));
        recruitTag.setDelFlg(Boolean.FALSE);
        recruitTag.setNote(Constant.EMPTY_STRING);
        return recruitTagMapper.insertSelective(recruitTag);
    }

    public int insertTags(long recruitId, int tagType, List<Integer> contentList) {
        int cnt = 0;
        for (Integer content:contentList) {
            RecruitTag recruitTag = new RecruitTag();
            recruitTag.setRecruitId(recruitId);
            recruitTag.setTagType((byte) tagType);
            recruitTag.setContent(content);
            recruitTag.setCreateDatetime(new Date(System.currentTimeMillis()));
            recruitTag.setUpdateDatetime(new Date(System.currentTimeMillis()));
            recruitTag.setDelFlg(Boolean.FALSE);
            recruitTag.setNote(Constant.EMPTY_STRING);
            cnt += recruitTagMapper.insertSelective(recruitTag);
        }
        return cnt;
    }

    public Recruit getRecruit(Long id) {
        Recruit entity = recruitMapper.getRecruit(id);
        return entity;
    }

    public Recruit getRecruitByUuid(String uuid) {
        Recruit entity = recruitMapper.getRecruitByUuid(uuid);
        return entity;
    }

    public List<Recruit> getRecruitByShopId(long shopId) {
        List<Recruit> list = recruitMapper.getRecruitByShopId(shopId);
        return list;
    }

    public List<Recruit> getRecruitAll() {
        List<Recruit> recruitList = recruitMapper.getRecruitAll();
        return recruitList;
    }

    public List<Recruit> getRecruitAllForRandom(){
        String yesterday = DateUtil.getYesterday();
        String tomorrow = DateUtil.getTomorrow();
        List<Recruit> placeList = recruitMapper.getRecruitAllForRandom();
        return placeList;
    }

    public List<Recruit> getRecruitAll(int limit, int offset) {
        List<Recruit> recruitList = recruitMapper.getAppRecruitAll(limit, offset);
        return recruitList;
    }

    public List<Recruit> getRecruitByUuidList(List<String> list) {
        List<Recruit> recruitlist = recruitMapper.getRecruitByUuidList(list);
        return recruitlist;
    }

    public Recruit insertRecruit(RecruitForm recruitForm) {
        Recruit recruit = new Recruit();
        String uuid = "";
        int flagUuid = 0;
        int cntSelect = 0;
        while (flagUuid != 1 && cntSelect < 100){
            uuid = StringUtil.getUuid();
            if (getRecruitByUuid(uuid) == null){
                flagUuid = 1;
            }
            cntSelect++;
        }

        recruit.setUuid(uuid);
        recruit.setTitle1(recruitForm.getTitle1());
        if(recruitForm.getPicUrl() == null || recruitForm.getPicUrl().equals("")){
            recruit.setPicUrl1("");
        }else{
            recruit.setPicUrl1(recruitForm.getPicUrl());
        }
        recruit.setRecruitName(recruitForm.getRecruitName());
        recruit.setCompanyName(recruitForm.getCompanyName());
        recruit.setSalary(recruitForm.getSalary());
        recruit.setStation(recruitForm.getStation());
        recruit.setAddress(recruitForm.getAddress());
        recruit.setWorkingTime(recruitForm.getWorkingTime());
        if(recruitForm.getShopId() != null){
            recruit.setShopId(recruitForm.getShopId());
        }else{
            recruit.setShopId(0L);
        }

        if(recruitForm.getShopType() != null){
            recruit.setShopType(recruitForm.getShopType());
        }else{
            recruit.setShopType(0);
        }

        if(recruitForm.getPublishStart() == null || "".equals(recruitForm.getPublishStart())){
            recruit.setPublishStart(DateUtil.getDefaultDate());
        }else{
            recruit.setPublishStart(DateUtil.stringToDateyyyy_MM_dd(recruitForm.getPublishStart()));
        }
        if(recruitForm.getPublishEnd() == null || "".equals(recruitForm.getPublishEnd())){
            recruit.setPublishEnd(DateUtil.getDefaultPublishEnd());
        }else{
            recruit.setPublishEnd(DateUtil.stringToDateyyyy_MM_dd(recruitForm.getPublishEnd()));
        }
        recruit.setCreateDatetime(new Date(System.currentTimeMillis()));
        recruit.setUpdateDatetime(new Date(System.currentTimeMillis()));
        recruit.setDelFlg(Boolean.FALSE);
        recruit.setNote(Constant.EMPTY_STRING);
        recruitMapper.insertSelective(recruit);
        return recruit;
    }

    public Long insertRecruitDetail(RecruitForm recruitForm,long recruitId) {
        RecruitDetail recruitDetail = new RecruitDetail();
        recruitDetail.setRecruitId(recruitId);
        recruitDetail.setRecruitName(recruitForm.getRecruitName());
        recruitDetail.setTitle1(recruitForm.getTitle1());
        recruitDetail.setTitle2(recruitForm.getTitle2());
        recruitDetail.setCapacity(recruitForm.getCapacity());
        recruitDetail.setAddress(recruitForm.getAddress());
        recruitDetail.setSalary(recruitForm.getSalary());

        if(recruitForm.getPicUrl1() == null || recruitForm.getPicUrl1().equals("")){
            recruitDetail.setPicUrl1("");
        }else{
            recruitDetail.setPicUrl1(recruitForm.getPicUrl1());
        }

        if(recruitForm.getPicUrl2() == null || recruitForm.getPicUrl2().equals("")){
            recruitDetail.setPicUrl2("");
        }else{
            recruitDetail.setPicUrl2(recruitForm.getPicUrl2());
        }

        if(recruitForm.getPicUrl3() == null || recruitForm.getPicUrl3().equals("")){
            recruitDetail.setPicUrl3("");
        }else{
            recruitDetail.setPicUrl3(recruitForm.getPicUrl3());
        }

        if(recruitForm.getPicUrl4() == null || recruitForm.getPicUrl4().equals("")){
            recruitDetail.setPicUrl4("");
        }else{
            recruitDetail.setPicUrl4(recruitForm.getPicUrl4());
        }

        if(recruitForm.getPicUrl5() == null || recruitForm.getPicUrl5().equals("")){
            recruitDetail.setPicUrl5("");
        }else{
            recruitDetail.setPicUrl5(recruitForm.getPicUrl5());
        }

        if(recruitForm.getVideoUrl1() == null || recruitForm.getVideoUrl1().equals("")){
            recruitDetail.setVideoUrl1("");
        }else{
            recruitDetail.setVideoUrl1(recruitForm.getVideoUrl1());
        }

        if(recruitForm.getShopId() != null){
            recruitDetail.setShopId(recruitForm.getShopId());
        }else{
            recruitDetail.setShopId(0L);
        }
        if(recruitForm.getShopType() != null){
            recruitDetail.setShopType(recruitForm.getShopType());
        }else{
            recruitDetail.setShopType(0);
        }
        if(recruitForm.getContent() != null){
            recruitDetail.setContent(recruitForm.getContent());
        }else{
            recruitDetail.setContent("");
        }
        if(recruitForm.getSalaryFull() != null){
            recruitDetail.setSalaryFull(recruitForm.getSalaryFull());
        }else{
            recruitDetail.setSalaryFull("");
        }
        if(recruitForm.getTreatment() != null){
            recruitDetail.setTreatment(recruitForm.getTreatment());
        }else{
            recruitDetail.setTreatment("");
        }
        if(recruitForm.getVacation() != null){
            recruitDetail.setVacation(recruitForm.getVacation());
        }else{
            recruitDetail.setVacation("");
        }
        if(recruitForm.getCapacity() != null){
            recruitDetail.setCapacity(recruitForm.getCapacity());
        }else{
            recruitDetail.setCapacity("");
        }
        if(recruitForm.getWorkingDate() != null){
            recruitDetail.setWorkingDate(recruitForm.getWorkingDate());
        }else{
            recruitDetail.setWorkingDate("");
        }
        if(recruitForm.getDesc() != null){
            recruitDetail.setDesc(recruitForm.getDesc());
        }else{
            recruitDetail.setDesc("");
        }

        recruitDetail.setStation(recruitForm.getStation());
        recruitDetail.setTel(recruitForm.getTel());
        recruitDetail.setCompanyName(recruitForm.getCompanyName());
        recruitDetail.setAcceptance(recruitForm.getAcceptance());
        recruitDetail.setExecutive(recruitForm.getExecutive());
        recruitDetail.setSpot(recruitForm.getSpot());
        recruitDetail.setWorkDay(recruitForm.getWorkDay());
        recruitDetail.setWorkingTime(recruitForm.getWorkingTime());
        recruitDetail.setCreateDatetime(new Date(System.currentTimeMillis()));
        recruitDetail.setUpdateDatetime(new Date(System.currentTimeMillis()));
        recruitDetail.setDelFlg(Boolean.FALSE);
        recruitDetail.setNote(Constant.EMPTY_STRING);
        recruitDetailMapper.insertSelective(recruitDetail);
        return recruitDetail.getId();
    }
    public int deleteRecruit(RecruitForm recruitForm) {
        Recruit recruit = new Recruit();
        recruit.setId(recruitForm.getId());
        recruit.setDelFlg(Boolean.TRUE);
        int count = recruitMapper.deleteRecruit(recruit);
        System.out.println(count);
        return count;

    }

    public int deleteRecruitDetail(RecruitForm recruitForm) {
        RecruitDetail recruitDetail = new RecruitDetail();
        recruitDetail.setRecruitId(recruitForm.getId());
        recruitDetail.setDelFlg(Boolean.TRUE);
        int count = recruitDetailMapper.deleteRecruitDetail(recruitDetail);
        return count;
    }

    public Recruit updateRecruit(RecruitForm recruitForm) {
        Recruit recruit = new Recruit();
        recruit.setId(recruitForm.getRecruitId());
        recruit.setTitle1(recruitForm.getTitle1());
        if(recruitForm.getPicUrl() == null || recruitForm.getPicUrl().equals("")){
            recruit.setPicUrl1("");
        }else{
            recruit.setPicUrl1(recruitForm.getPicUrl());
        }
        recruit.setRecruitName(recruitForm.getRecruitName());
        recruit.setCompanyName(recruitForm.getCompanyName());
        recruit.setSalary(recruitForm.getSalary());
        recruit.setStation(recruitForm.getStation());
        recruit.setAddress(recruitForm.getAddress());
        recruit.setWorkingTime(recruitForm.getWorkingTime());
        recruit.setShopId(recruitForm.getShopId());
        if(recruitForm.getShopType() != null){
            recruit.setShopType(recruitForm.getShopType());
        }else{
            recruit.setShopType(0);
        }
        if(recruitForm.getPublishStart() == null || "".equals(recruitForm.getPublishStart())){
            recruit.setPublishStart(DateUtil.getDefaultDate());
        }else{
            recruit.setPublishStart(DateUtil.stringToDateyyyy_MM_dd(recruitForm.getPublishStart()));
        }
        if(recruitForm.getPublishEnd() == null || "".equals(recruitForm.getPublishEnd())){
            recruit.setPublishEnd(DateUtil.getDefaultPublishEnd());
        }else{
            recruit.setPublishEnd(DateUtil.stringToDateyyyy_MM_dd(recruitForm.getPublishEnd()));
        }
        recruit.setUpdateDatetime(new Date(System.currentTimeMillis()));
        recruit.setDelFlg(recruitForm.getDelFlg());
        recruit.setNote(recruitForm.getNote());
        recruitMapper.updateByPrimaryKeySelective(recruit);
        return recruit;
    }

    public int updateRecruitDetail(RecruitForm recruitForm) {
        RecruitDetail recruitDetail = new RecruitDetail();
        recruitDetail.setRecruitId(recruitForm.getRecruitId());
        recruitDetail.setRecruitName(recruitForm.getRecruitName());
        recruitDetail.setTitle1(recruitForm.getTitle1());
        recruitDetail.setTitle2(recruitForm.getTitle2());
        if(recruitForm.getPicUrl1() == null || recruitForm.getPicUrl1().equals("")){
            recruitDetail.setPicUrl1("");
        }else{
            recruitDetail.setPicUrl1(recruitForm.getPicUrl1());
        }

        if(recruitForm.getPicUrl2() == null || recruitForm.getPicUrl2().equals("")){
            recruitDetail.setPicUrl2("");
        }else{
            recruitDetail.setPicUrl2(recruitForm.getPicUrl2());
        }

        if(recruitForm.getPicUrl3() == null || recruitForm.getPicUrl3().equals("")){
            recruitDetail.setPicUrl3("");
        }else{
            recruitDetail.setPicUrl3(recruitForm.getPicUrl3());
        }

        if(recruitForm.getPicUrl4() == null || recruitForm.getPicUrl4().equals("")){
            recruitDetail.setPicUrl4("");
        }else{
            recruitDetail.setPicUrl4(recruitForm.getPicUrl4());
        }

        if(recruitForm.getPicUrl5() == null || recruitForm.getPicUrl5().equals("")){
            recruitDetail.setPicUrl5("");
        }else{
            recruitDetail.setPicUrl5(recruitForm.getPicUrl5());
        }

        if(recruitForm.getVideoUrl1() == null || recruitForm.getVideoUrl1().equals("")){
            recruitDetail.setVideoUrl1("");
        }else{
            recruitDetail.setVideoUrl1(recruitForm.getVideoUrl1());
        }
        recruitDetail.setCapacity(recruitForm.getCapacity());
        recruitDetail.setAddress(recruitForm.getAddress());
        recruitDetail.setSalary(recruitForm.getSalary());
        recruitDetail.setStation(recruitForm.getStation());
        recruitDetail.setTel(recruitForm.getTel());
        recruitDetail.setShopId(recruitForm.getShopId());

        if(recruitForm.getShopId() != null){
            recruitDetail.setShopId(recruitForm.getShopId());
        }
        if(recruitForm.getShopType() != null){
            recruitDetail.setShopType(recruitForm.getShopType());
        }
        if(recruitForm.getContent() != null){
            recruitDetail.setContent(recruitForm.getContent());
        }else{
            recruitDetail.setContent("");
        }
        if(recruitForm.getSalaryFull() != null){
            recruitDetail.setSalaryFull(recruitForm.getSalaryFull());
        }else{
            recruitDetail.setSalaryFull("");
        }
        if(recruitForm.getTreatment() != null){
            recruitDetail.setTreatment(recruitForm.getTreatment());
        }else{
            recruitDetail.setTreatment("");
        }
        if(recruitForm.getVacation() != null){
            recruitDetail.setVacation(recruitForm.getVacation());
        }else{
            recruitDetail.setVacation("");
        }
        if(recruitForm.getCapacity() != null){
            recruitDetail.setCapacity(recruitForm.getCapacity());
        }else{
            recruitDetail.setCapacity("");
        }
        if(recruitForm.getWorkingDate() != null){
            recruitDetail.setWorkingDate(recruitForm.getWorkingDate());
        }else{
            recruitDetail.setWorkingDate("");
        }
        if(recruitForm.getDesc() != null){
            recruitDetail.setDesc(recruitForm.getDesc());
        }else{
            recruitDetail.setDesc("");
        }

        recruitDetail.setCompanyName(recruitForm.getCompanyName());
        recruitDetail.setAcceptance(recruitForm.getAcceptance());
        recruitDetail.setExecutive(recruitForm.getExecutive());
        recruitDetail.setSpot(recruitForm.getSpot());
        recruitDetail.setWorkDay(recruitForm.getWorkDay());
        recruitDetail.setWorkingTime(recruitForm.getWorkingTime());
        recruitDetail.setUpdateDatetime(new Date(System.currentTimeMillis()));
        recruitDetail.setDelFlg(recruitForm.getDelFlg());
        recruitDetail.setNote(recruitForm.getNote());
        int count = recruitDetailMapper.updateByRecruitSelective(recruitDetail);
        return count;
    }

    public RecruitDetail getRecruitDetail(int id){
        RecruitDetail detail = recruitDetailMapper.getRecruitDetail(id);
        return detail;
    }

    public RecruitDetail getRecruitDetailByRecruit(Long id){
        RecruitDetail detail = recruitDetailMapper.getRecruitDetailByRecruit(id);
        return detail;
    }

    public List<Recruit> getRecruitList(int id) {
        List<Recruit> recruitList = recruitMapper.getRecruitList(id);
        return recruitList;
    }

    public List<RecruitDetail> getRecruitDetailAll(int id) {
        List<RecruitDetail> recruitDetail = recruitDetailMapper.getRecruitDetailAll(id);
        return recruitDetail;
    }

    public List<RecruitDetail> getRecruitDetailAllByCondition(RecruitListForm form) {
        List<RecruitDetail> recruitDetail = recruitDetailMapper.getRecruitDetailAllByCondition(form);
        return recruitDetail;
    }

    public int getRecruitDetailCountByCondition(RecruitListForm form) {
        return recruitDetailMapper.getRecruitDetailCountByCondition(form);
    }

    public int getRecruitDetailCount() {
        return recruitDetailMapper.getRecruitDetailCount();
    }

    public List<Recruit> getRecruitAllByShopIdList(List<Long> shopIdList) {
        return recruitMapper.getRecruitAllByShopIdList(shopIdList);
    }

    public List<RecruitTag> getRecruitTagAllByRecruitIdListAndCondition(List<Long> recruitIdList, int tagType, List<Integer> contentList) {
        return recruitTagMapper.getRecruitTagAllByRecruitIdListAndCondition(recruitIdList, tagType, contentList);
    }

    public List<Recruit> getRecruitAllByIdListAndCondition(List<Long> resultIdList, int limit, int offset) {
        return recruitMapper.getRecruitAllByIdListAndCondition(resultIdList, limit, offset);
    }

    public List<RecruitTag> getRecruitTagAllByRecruitIdListAndConditionTimeStart(List<Long> recruitIdList, int tagType, int workingTimeStart) {
        return recruitTagMapper.getRecruitTagAllByRecruitIdListAndConditionTimeStart(recruitIdList, tagType, workingTimeStart);
    }

    public List<RecruitTag> getRecruitTagAllByRecruitIdListAndConditionTimeEnd(List<Long> recruitIdList, int tagType, int workingTimeEnd) {
        return recruitTagMapper.getRecruitTagAllByRecruitIdListAndConditionTimeEnd(recruitIdList, tagType, workingTimeEnd);
    }

    public List<Recruit> findAllByTime(){
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        return recruitMapper.findAllByTime(tomorrow,today);
    }

    public String findUuidByTypeAndId(int type,long shopId){
        return recruitMapper.findUuidByTypeAndId(type,shopId);
    }

    public List<Recruit> findRecruitByUuidList(List<String> uuid){
        List<Recruit> findByUuidArea = recruitMapper.findRecruitByUuidList(uuid);
        return  findByUuidArea;
    }

    public List<Recruit> getRecruitForSettlementBatch(){
        String monthOfFirst = DateUtil.getMonthStartYyyyMMdd();
        String LastmonthFirst = DateUtil.getLastmonthStartYyyyMMdd();
        List<Recruit> recruitList = recruitMapper.getRecruitForSettlementBatch(monthOfFirst,LastmonthFirst);
        return recruitList;
    }
}