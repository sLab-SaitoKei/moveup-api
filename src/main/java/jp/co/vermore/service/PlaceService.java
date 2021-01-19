package jp.co.vermore.service;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.common.util.StringUtil;
import jp.co.vermore.entity.Place;
import jp.co.vermore.entity.PlaceDetail;
import jp.co.vermore.entity.Shop;
import jp.co.vermore.form.admin.PlaceForm;
import jp.co.vermore.form.admin.PlaceListForm;
import jp.co.vermore.mapper.PlaceDetailMapper;
import jp.co.vermore.mapper.PlaceMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * PlaceService
 * Created by Wangyan.
 * <p>
 * DateTime: 2018/03/20 16:36
 * Copyright: sLab, Corp
 */
@Service
public class PlaceService {

    @Autowired
    private PlaceMapper placeMapper;

    @Autowired
    private PlaceDetailMapper placeDetailMapper;

    public Place selectById(Long id) {
        Place entity = placeMapper.selectById(id);
        return entity;
    }

    public Place selectByUuid(String uuid) {
        Place entity = placeMapper.selectByUuid(uuid);
        return entity;
    }
    public PlaceDetail selectByPlaceListId(Long id) {
        PlaceDetail entity = placeDetailMapper.selectByPlaceListId(id);
        return entity;
    }

    public List<Place> getPlaceAll(int limit, int offset ,int area) {
        List<Place> placeList = placeMapper.getPlaceAll(limit, offset ,area);
        return placeList;
    }

    public List<Place> getPlaceAllForRandom(){
        String yesterday = DateUtil.getYesterday();
        String tomorrow = DateUtil.getTomorrow();
        List<Place> placeList = placeMapper.getPlaceAllForRandom();
        return placeList;
    }

    public List<Place> getPlaceByUuidList(List<String> list) {
        List<Place> placelist = placeMapper.getPlaceByUuidList(list);
        return placelist;
    }

    public List<Place> getPlaceAllByArea(int area, int limit, int offset) {
        List<Place> placeList = placeMapper.getPlaceAllByArea(area, limit, offset);
        return placeList;
    }

    public List<Place> getPlaceAllCount(int area) {
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        List<Place> placeList = placeMapper.getPlaceAllCount(area,tomorrow,today);
        return placeList;
    }

    public List<Place> getPlaceForSettlementBatch(){
        String monthOfFirst = DateUtil.getMonthStartYyyyMMdd();
        String LastmonthFirst = DateUtil.getLastmonthStartYyyyMMdd();
        List<Place> placeList = placeMapper.getPlaceForSettlementBatch(monthOfFirst,LastmonthFirst);
        return placeList;
    }

    public List<PlaceDetail> getPlaceDetailAllByCondition(PlaceListForm form) {
        List<PlaceDetail> detail = placeDetailMapper.getPlaceDetailAllByCondition(form);
        return detail;
    }

    public int getPlaceDetailCountByCondition(PlaceListForm form) {
        return placeDetailMapper.getPlaceDetailCountByCondition(form);
    }

    public int getPlaceDetailCount() {
        return placeDetailMapper.getPlaceDetailCount();
    }

    public Place insertPlace(PlaceForm placeForm) {
        Place place = new Place();
        String uuid = "";
        int flagUuid = 0;
        int cntSelect = 0;
        while (flagUuid != 1 && cntSelect < 100){
            uuid = StringUtil.getUuid();
            if (selectByUuid(uuid) == null){
                flagUuid = 1;
            }
            cntSelect++;
        }

        place.setUuid(uuid);
        place.setName(placeForm.getName());
        place.setIntroduce(placeForm.getIntroduce());
        place.setArea(placeForm.getArea());
        if(placeForm.getPicUrl() == null || placeForm.getPicUrl().equals("")){
            place.setPicUrl("");
        }else{
            place.setPicUrl(placeForm.getPicUrl());
        }
        if(placeForm.getFlicUrl() == null || placeForm.getFlicUrl().equals("")){
            place.setFlicUrl("");
        }else{
            place.setFlicUrl(placeForm.getFlicUrl());
        }
        place.setAddress(placeForm.getAddress());
        place.setCreateDatetime(new Date(System.currentTimeMillis()));
        place.setUpdateDatetime(new Date(System.currentTimeMillis()));
        place.setDelFlg(Boolean.FALSE);
        place.setNote(Constant.EMPTY_STRING);
        place.setCoordinate1(placeForm.getCoordinate1());
        place.setCoordinate2(placeForm.getCoordinate2());

        if(placeForm.getPublishStart() == null || "".equals(placeForm.getPublishStart())){
            place.setPublishStart(DateUtil.getDefaultDate());
        }else{
            place.setPublishStart(DateUtil.stringToDateyyyy_MM_dd(placeForm.getPublishStart()));
        }
        if(placeForm.getPublishEnd() == null || "".equals(placeForm.getPublishEnd())){
            place.setPublishEnd(DateUtil.getDefaultPublishEnd());
        }else{
            place.setPublishEnd(DateUtil.stringToDateyyyy_MM_dd(placeForm.getPublishEnd()));
        }
        placeMapper.insertPlace(place);
        return place;
    }

    public Long insertPlaceDetail(PlaceForm placeForm, Long placeListId) {
        PlaceDetail placeDetail = new PlaceDetail();
        placeDetail.setPlaceListId(placeListId);
        placeDetail.setName(placeForm.getName());
        placeDetail.setLocation(placeForm.getLocation());
        placeDetail.setStation(placeForm.getStation());
        placeDetail.setWalk(placeForm.getWalk());
        placeDetail.setTime(placeForm.getTime());
        placeDetail.setHoliday(placeForm.getHoliday());

        if(placeForm.getPicUrl1() == null || placeForm.getPicUrl1().equals("")){
            placeDetail.setPicUrl("");
        }else{
            placeDetail.setPicUrl(placeForm.getPicUrl1());
        }
        if(placeForm.getPicUrl2() == null || placeForm.getPicUrl2().equals("")){
            placeDetail.setPicUrl1("");
        }else{
            placeDetail.setPicUrl1(placeForm.getPicUrl2());
        }
        if(placeForm.getPicUrl3() == null || placeForm.getPicUrl3().equals("")){
            placeDetail.setPicUrl2("");
        }else{
            placeDetail.setPicUrl2(placeForm.getPicUrl3());
        }
        if(placeForm.getPicUrl4() == null || placeForm.getPicUrl4().equals("")){
            placeDetail.setPicUrl3("");
        }else{
            placeDetail.setPicUrl3(placeForm.getPicUrl4());
        }
        if(placeForm.getPicUrl5() == null || placeForm.getPicUrl5().equals("")){
            placeDetail.setPicUrl4("");
        }else{
            placeDetail.setPicUrl4(placeForm.getPicUrl5());
        }
        if(placeForm.getFlicUrl() == null || placeForm.getFlicUrl().equals("")){
            placeDetail.setFlicUrl("");
        }else{
            placeDetail.setFlicUrl(placeForm.getFlicUrl());
        }
        if(placeForm.getVideoUrl() == null || placeForm.getVideoUrl().equals("")){
            placeDetail.setVideoUrl("");
        }else{
            placeDetail.setVideoUrl(placeForm.getVideoUrl());
        }

        placeDetail.setTitle(placeForm.getTitle());
        placeDetail.setDesc(placeForm.getDesc());
        placeDetail.setTel(placeForm.getTel());
        placeDetail.setAddress(placeForm.getAddress());
        placeDetail.setCreateDatetime(new Date(System.currentTimeMillis()));
        placeDetail.setUpdateDatetime(new Date(System.currentTimeMillis()));
        placeDetail.setCoordinate1(placeForm.getCoordinate1());
        placeDetail.setCoordinate2(placeForm.getCoordinate2());
        if(placeForm.getPublishStart() == null || "".equals(placeForm.getPublishStart())){
            placeDetail.setPublishStart(DateUtil.getDefaultDate());
        }else{
            placeDetail.setPublishStart(DateUtil.stringToDateyyyy_MM_dd(placeForm.getPublishStart()));
        }
        if(placeForm.getPublishEnd() == null || "".equals(placeForm.getPublishEnd())){
            placeDetail.setPublishEnd(DateUtil.getDefaultPublishEnd());
        }else{
            placeDetail.setPublishEnd(DateUtil.stringToDateyyyy_MM_dd(placeForm.getPublishEnd()));
        }
        placeDetail.setDelFlg(Boolean.FALSE);
        placeDetail.setNote(Constant.EMPTY_STRING);
        placeDetailMapper.insertPlaceDetail(placeDetail);
        return placeDetail.getId();
    }

    public Place updatePlace(PlaceForm placeForm) {
        Place place = new Place();
        place.setId(placeForm.getPlaceListId());
        place.setName(placeForm.getName());
        place.setIntroduce(placeForm.getIntroduce());
        place.setArea(placeForm.getArea());
        place.setPicUrl(placeForm.getPicUrl());
        if(placeForm.getPicUrl() == null || placeForm.getPicUrl().equals("")){
            place.setPicUrl("");
        }else{
            place.setPicUrl(placeForm.getPicUrl());
        }
        if(placeForm.getFlicUrl() == null || placeForm.getFlicUrl().equals("")){
            place.setFlicUrl("");
        }else{
            place.setFlicUrl(placeForm.getFlicUrl());
        }
        place.setAddress(placeForm.getAddress());
        place.setCreateDatetime(new Date(System.currentTimeMillis()));
        place.setUpdateDatetime(new Date(System.currentTimeMillis()));
        place.setDelFlg(Boolean.FALSE);
        place.setNote(Constant.EMPTY_STRING);
        place.setCoordinate1(placeForm.getCoordinate1());
        place.setCoordinate2(placeForm.getCoordinate2());
        if(placeForm.getPublishStart() == null || "".equals(placeForm.getPublishStart())){
            place.setPublishStart(DateUtil.getDefaultDate());
        }else{
            place.setPublishStart(DateUtil.stringToDateyyyy_MM_dd(placeForm.getPublishStart()));
        }
        if(placeForm.getPublishEnd() == null || "".equals(placeForm.getPublishEnd())){
            place.setPublishEnd(DateUtil.getDefaultPublishEnd());
        }else{
            place.setPublishEnd(DateUtil.stringToDateyyyy_MM_dd(placeForm.getPublishEnd()));
        }
        placeMapper.updatePlace(place);
        return place;
    }

    public Long updatePlaceDetail(PlaceForm placeForm) {
        PlaceDetail placeDetail = new PlaceDetail();
        placeDetail.setPlaceListId(placeForm.getPlaceListId());
        placeDetail.setName(placeForm.getName());
        placeDetail.setLocation(placeForm.getLocation());
        placeDetail.setStation(placeForm.getStation());
        placeDetail.setWalk(placeForm.getWalk());
        placeDetail.setTime(placeForm.getTime());
        placeDetail.setHoliday(placeForm.getHoliday());
        if(placeForm.getPicUrl1() == null || placeForm.getPicUrl1().equals("")){
            placeDetail.setPicUrl("");
        }else{
            placeDetail.setPicUrl(placeForm.getPicUrl1());
        }
        if(placeForm.getPicUrl2() == null || placeForm.getPicUrl2().equals("")){
            placeDetail.setPicUrl1("");
        }else{
            placeDetail.setPicUrl1(placeForm.getPicUrl2());
        }
        if(placeForm.getPicUrl3() == null || placeForm.getPicUrl3().equals("")){
            placeDetail.setPicUrl2("");
        }else{
            placeDetail.setPicUrl2(placeForm.getPicUrl3());
        }
        if(placeForm.getPicUrl4() == null || placeForm.getPicUrl4().equals("")){
            placeDetail.setPicUrl3("");
        }else{
            placeDetail.setPicUrl3(placeForm.getPicUrl4());
        }
        if(placeForm.getPicUrl5() == null || placeForm.getPicUrl5().equals("")){
            placeDetail.setPicUrl4("");
        }else{
            placeDetail.setPicUrl4(placeForm.getPicUrl5());
        }
        if(placeForm.getFlicUrl() == null || placeForm.getFlicUrl().equals("")){
            placeDetail.setFlicUrl("");
        }else{
            placeDetail.setFlicUrl(placeForm.getFlicUrl());
        }
        if(placeForm.getVideoUrl() == null || placeForm.getVideoUrl().equals("")){
            placeDetail.setVideoUrl("");
        }else{
            placeDetail.setVideoUrl(placeForm.getVideoUrl());
        }
        placeDetail.setTitle(placeForm.getTitle());
        placeDetail.setDesc(placeForm.getDesc());
        placeDetail.setTel(placeForm.getTel());
        placeDetail.setAddress(placeForm.getAddress());
        placeDetail.setCreateDatetime(new Date(System.currentTimeMillis()));
        placeDetail.setUpdateDatetime(new Date(System.currentTimeMillis()));
        placeDetail.setCoordinate1(placeForm.getCoordinate1());
        placeDetail.setCoordinate2(placeForm.getCoordinate2());
        if(placeForm.getPublishStart() == null || "".equals(placeForm.getPublishStart())){
            placeDetail.setPublishStart(DateUtil.getDefaultDate());
        }else{
            placeDetail.setPublishStart(DateUtil.stringToDateyyyy_MM_dd(placeForm.getPublishStart()));
        }
        if(placeForm.getPublishEnd() == null || "".equals(placeForm.getPublishEnd())){
            placeDetail.setPublishEnd(DateUtil.getDefaultPublishEnd());
        }else{
            placeDetail.setPublishEnd(DateUtil.stringToDateyyyy_MM_dd(placeForm.getPublishEnd()));
        }
        placeDetail.setDelFlg(Boolean.FALSE);
        placeDetail.setNote(Constant.EMPTY_STRING);
        placeDetailMapper.updatePlaceDetail(placeDetail);
        return placeDetail.getId();
    }

    public int deletePlace(PlaceForm form) {
        Place place = new Place();
        place.setId(form.getId());
        place.setDelFlg(Boolean.TRUE);
        int count = placeMapper.deletePlace(place);
        return count;
    }

    public int deletePlaceDetail(PlaceForm form) {
        PlaceDetail detail = new PlaceDetail();
        detail.setPlaceListId(form.getId());
        detail.setDelFlg(Boolean.TRUE);
        int count = placeDetailMapper.deletePlaceDetail(detail);
        return count;
    }

    public int deleteDetailVideo( Long placeId) {
        placeDetailMapper.deleteDetailVideo(placeId);
        return 0;
    }

    public int deleteDetailFlic( Long placeId) {
        placeDetailMapper.deleteDetailFlic(placeId);
        return 0;
    }

    public List<Place> findByUuidArea(String uuid){
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        List<Place> findByUuidArea = placeMapper.findByUuidArea(uuid,tomorrow,today);
        return  findByUuidArea;
    }

    public List<Place> findPlaceByUuidList(List<String> uuid){
        List<Place> findByUuidArea = placeMapper.findPlaceByUuidList(uuid);
        return  findByUuidArea;
    }


    public String getShopUuidByShopIdPlace(Long id) {
        String uuid2 = placeMapper.getShopUuidByShopIdPlace(id);
        return uuid2;
    }
    public Place getPlace(Long shopId) {
        return placeMapper.getPlace(shopId);

    }

    public List<String> getPlaceDetail() {
        return placeDetailMapper.getPlaceDetail();
    }


}
