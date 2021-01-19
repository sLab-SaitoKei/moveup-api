package jp.co.vermore.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.entity.RiseDetail;
import jp.co.vermore.mapper.RiseDetailMapper;
import jp.co.vermore.common.Constant;
import jp.co.vermore.entity.Rise;
import jp.co.vermore.form.RiseRegistForm;
import jp.co.vermore.mapper.RiseMapper;



/**
 * RiseService
 * Created by Wangyan.
 * <p>
 * DateTime: 2018/02/28 17:48
 * Copyright: sLab, Corp
 */
@Service
public class RiseService {
    @Autowired
    private RiseMapper riseMapper;

    @Autowired
    private RiseDetailMapper riseDetailMapper;
    public int insertRiseDetail(RiseRegistForm riseRegistForm, long riseId) throws ParseException {

        RiseDetail riseDetail = new RiseDetail();
        riseDetail.setRiseId(riseId);
        riseDetail.setName1(riseRegistForm.getName1());
        riseDetail.setName2(riseRegistForm.getName2());
        riseDetail.setHonor(riseRegistForm.getHonor());
        riseDetail.setCategory(riseRegistForm.getCategory());
        riseDetail.setTopTitle(riseRegistForm.getTopTitle());
        riseDetail.setTitle1(riseRegistForm.getTitle1());
        riseDetail.setTitle2(riseRegistForm.getTitle2());
        riseDetail.setTitle3(riseRegistForm.getTitle3());
        riseDetail.setBirthday(DateUtil.stringToDateyyyy_MM_dd(String.valueOf(riseRegistForm.getBirthday())));
        riseDetail.setHometown(riseRegistForm.getHometown());
        riseDetail.setTopPicUrl(riseRegistForm.getTopPicUrl());
        riseDetail.setPicUrl1(riseRegistForm.getPicUrl1());
        riseDetail.setPicUrl2(riseRegistForm.getPicUrl2());
        riseDetail.setPicUrl3(riseRegistForm.getPicUrl3());
        riseDetail.setPicUrl4(riseRegistForm.getTopPicUrl4());
        riseDetail.setVideoUrl1(riseRegistForm.getVideoUrl1());
        riseDetail.setDesc1(riseRegistForm.getDesc1());
        riseDetail.setDesc2(riseRegistForm.getDesc2());
        riseDetail.setDesc3(riseRegistForm.getDesc3());
        riseDetail.setCreateDatetime(new Date(System.currentTimeMillis()));
        riseDetail.setDelFlg(Boolean.FALSE);
        riseDetail.setNote(Constant.EMPTY_STRING);
        riseDetailMapper.insertRiseDetail(riseDetail);

        int count=1;

        return count;
    }

    public RiseDetail getRiseDetail(int riseId){
        RiseDetail entity = riseDetailMapper.getRiseDetail(riseId);
        return entity;
    }


    public List<Rise> getRiseAllForAdmin(){
        List<Rise> riseList = riseMapper.getRiseAllForAdmin();
        return riseList;
    }

    public List<Rise> getRiseAll(int limit, int offset){
        List<Rise> riseList = riseMapper.getRiseAll(limit, offset);
        return riseList;
    }

    public List<Rise> getRiseForTop(){
        List<Rise> riseList = riseMapper.getRiseForTop();
        return riseList;
    }

    public List<Rise> getRiseByEntity(Rise entity) {
        List<Rise> riseList = riseMapper.getRiseByEntity(entity);
        return riseList;
    }

    public List<Rise> getRiseAllByCategory(String category,int limit, int offset) {
        List<Rise> riseList = riseMapper.getRiseAllByCategory(category,limit,offset);
        List<Rise> resultList = convertTo(riseList);
        return resultList;
    }

    public long insertRise(RiseRegistForm riseRegistForm){
        Rise rise = new Rise();
        rise.setName1(riseRegistForm.getName1());
        rise.setName2(riseRegistForm.getName2());
        rise.setOffice(riseRegistForm.getOffice());
        rise.setSortScore(riseRegistForm.getSortScore());
        rise.setPicUrl(riseRegistForm.getTopPicUrl4());
        rise.setCategory(riseRegistForm.getCategory());
        rise.setCreateDatetime(new Date(System.currentTimeMillis()));
        rise.setDelFlg(Boolean.FALSE);
        rise.setNote(Constant.EMPTY_STRING);
        riseMapper.insertRise(rise);
        return rise.getId();
    }

    private List<Rise> convertTo(List<Rise> demoList) {
        List<Rise> resultList = new LinkedList<Rise>();
        for (Rise entity : demoList) {
            resultList.add(entity);
        }
        return resultList;
    }
}
