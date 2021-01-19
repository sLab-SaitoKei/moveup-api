package jp.co.vermore.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.vermore.common.Constant;
import jp.co.vermore.entity.ShopDetail;
import jp.co.vermore.form.admin.ShopRegistForm;
import jp.co.vermore.mapper.ShopDetailMapper;

/**
 * ShopDetailService
 * Created by wubin.
 *
 * DateTime: 2018/03/23 10:00
 * Copyright: sLab, Corp
 */

@Service
public class ShopDetailService {

    @Autowired
    private ShopDetailMapper shopDetailMapper;

    public ShopDetail getShopDetailById(Long id) {
        return shopDetailMapper.getShopDetailById(id);
    }

    public ShopDetail getShopDetailByShopId(long shopId) {
        return shopDetailMapper.getShopDetailByShopId(shopId);
    }

    public List<ShopDetail> getShopDetailByShopIdList(List<Long> shopIdList) {
        return shopDetailMapper.getShopDetailByShopIdList(shopIdList);
    }

    public List<ShopDetail> getShopDetailAll(Long shopListId) {
        List<ShopDetail> shopDetailList = shopDetailMapper.getShopDetailJsonAll(shopListId);
        return shopDetailList;
    }

    public List<ShopDetail> getShopDetail() {
        List<ShopDetail> shopDetailList = shopDetailMapper.getShopDetailAll();
        return shopDetailList;
    }

    public Long getShopDetailId(Long shopListId){
        Long shopId = shopDetailMapper.getShopDetailId(shopListId);
        return shopId;
    }

    public List<Long> getShopIdList (List<Long> idList){
        List<Long> getShopId = shopDetailMapper.getShopDetailIdList(idList);
        return getShopId;
    }

    public int getShopType(Long shopListId){
        int shopType = shopDetailMapper.getShopType(shopListId);
        return shopType;
    }

    public Long getShopListId(Long id){
        Long shopListId = shopDetailMapper.getShopListId(id);
        return  shopListId;
    }

    public List<ShopDetail> getShopListAdmin() {
        List<ShopDetail> shopListAdmin = shopDetailMapper.getShopListAdmin();
        return shopListAdmin;
    }

    public List<ShopDetail> getShopDetailByNowGo() {
        List<ShopDetail> list = shopDetailMapper.getShopDetailByNowGo();
        return list;
    }

    public Long insertShopDetail(ShopRegistForm shopRegistForm, Long shopListId) {
        ShopDetail shopDetail = new ShopDetail();
        shopDetail.setShopType(shopRegistForm.getShopType());
        shopDetail.setShopListId(shopListId);
        shopDetail.setName(shopRegistForm.getName());
        shopDetail.setTitle(shopRegistForm.getTitle());
        shopDetail.setDesc1(shopRegistForm.getDesc1());
        shopDetail.setDesc2(shopRegistForm.getDesc2());
        shopDetail.setDesc3(shopRegistForm.getDesc3());
        shopDetail.setDesc4(shopRegistForm.getDesc4());
        shopDetail.setDesc5(shopRegistForm.getDesc5());
        shopDetail.setDesc6(shopRegistForm.getDesc6());
        shopDetail.setDesc7(shopRegistForm.getDesc7());
        shopDetail.setDesc8(shopRegistForm.getDesc8());
        shopDetail.setDesc9(shopRegistForm.getDesc9());
        shopDetail.setDesc10(shopRegistForm.getDesc10());
        shopDetail.setDesc11(shopRegistForm.getDesc11());
        shopDetail.setDesc12(shopRegistForm.getDesc12());
        shopDetail.setDesc13(shopRegistForm.getDesc13());
        shopDetail.setDesc14(shopRegistForm.getDesc14());
        shopDetail.setDesc15(shopRegistForm.getDesc15());
        shopDetail.setDesc16(shopRegistForm.getDesc16());
        shopDetail.setDesc17(shopRegistForm.getDesc17());
        shopDetail.setDesc18(shopRegistForm.getDesc18());
        shopDetail.setDesc19(shopRegistForm.getDesc19());
        shopDetail.setDesc20(shopRegistForm.getDesc20());
        shopDetail.setDesc21(shopRegistForm.getDesc21());
        shopDetail.setDesc22(shopRegistForm.getDesc22());
        shopDetail.setDesc23(shopRegistForm.getDesc23());
        shopDetail.setDesc24(shopRegistForm.getDesc24());
        shopDetail.setDesc25(shopRegistForm.getDesc25());
        shopDetail.setDesc26(shopRegistForm.getDesc26());
        shopDetail.setDesc27(shopRegistForm.getDesc27());
        shopDetail.setDesc28(shopRegistForm.getDesc28());
        shopDetail.setDesc29(shopRegistForm.getDesc29());
        shopDetail.setDesc30(shopRegistForm.getDesc30());
        shopDetail.setDesc31(shopRegistForm.getDesc31());
        shopDetail.setDesc32(shopRegistForm.getDesc32());
        shopDetail.setDesc33(shopRegistForm.getDesc33());
        shopDetail.setDesc34(shopRegistForm.getDesc34());
        shopDetail.setDesc35(shopRegistForm.getDesc35());
        shopDetail.setDesc36(shopRegistForm.getDesc36());
        shopDetail.setDesc37(shopRegistForm.getDesc37());
        shopDetail.setDesc38(shopRegistForm.getDesc38());
        shopDetail.setDesc39(shopRegistForm.getDesc39());

        if(shopRegistForm.getDesc40() != null){
            shopDetail.setDesc40(shopRegistForm.getDesc40());
        }else if(shopRegistForm.getDesc41() != null){
            shopDetail.setDesc40(shopRegistForm.getDesc41());
        }else if(shopRegistForm.getDesc42() != null){
            shopDetail.setDesc40(shopRegistForm.getDesc42());
        }else if(shopRegistForm.getDesc43() != null){
            shopDetail.setDesc40(shopRegistForm.getDesc43());
        }
        shopDetail.setTel(shopRegistForm.getTel());
        shopDetail.setAddress(shopRegistForm.getAddress());
        if(shopRegistForm.getPicUrl1() != null && shopRegistForm.getPicUrl1() !=""){
            shopDetail.setPicUrl1(shopRegistForm.getPicUrl1());
        }

        if(shopRegistForm.getPicUrl2() != null && shopRegistForm.getPicUrl2() !=""){
            shopDetail.setPicUrl2(shopRegistForm.getPicUrl2());
        }

        if(shopRegistForm.getPicUrl3() != null && shopRegistForm.getPicUrl3() !=""){
            shopDetail.setPicUrl3(shopRegistForm.getPicUrl3());
        }

        if(shopRegistForm.getPicUrl4() != null && shopRegistForm.getPicUrl4() !=""){
            shopDetail.setPicUrl4(shopRegistForm.getPicUrl4());
        }

        if(shopRegistForm.getPicUrl5() != null && shopRegistForm.getPicUrl5() !=""){
            shopDetail.setPicUrl5(shopRegistForm.getPicUrl5());
        }

        if(shopRegistForm.getPicUrl6() != null && shopRegistForm.getPicUrl6() !=""){
            shopDetail.setPicUrl6(shopRegistForm.getPicUrl6());
        }

        if(shopRegistForm.getVideoUrl() != null && shopRegistForm.getVideoUrl() !=""){
            shopDetail.setVideoUrl(shopRegistForm.getVideoUrl());
        }

        shopDetail.setCreateDatetime(new Date(System.currentTimeMillis()));
        shopDetail.setUpdateDatetime(new Date(System.currentTimeMillis()));
        shopDetail.setDelFlg(Boolean.FALSE);
        shopDetail.setNote(Constant.EMPTY_STRING);
        shopDetailMapper.insertShopDetail(shopDetail);
        return shopDetail.getId();
    }

    public Long updateShopDetail(ShopRegistForm shopRegistForm) {
        ShopDetail shopDetail = new ShopDetail();
        shopDetail.setId(shopRegistForm.getId());
        shopDetail.setShopListId(shopRegistForm.getShopListId());
        shopDetail.setName(shopRegistForm.getName());
        shopDetail.setTitle(shopRegistForm.getTitle());
        shopDetail.setDesc1(shopRegistForm.getDesc1());
        shopDetail.setDesc2(shopRegistForm.getDesc2());
        shopDetail.setDesc3(shopRegistForm.getDesc3());
        shopDetail.setDesc4(shopRegistForm.getDesc4());
        shopDetail.setDesc5(shopRegistForm.getDesc5());
        shopDetail.setDesc6(shopRegistForm.getDesc6());
        shopDetail.setDesc7(shopRegistForm.getDesc7());
        shopDetail.setDesc8(shopRegistForm.getDesc8());
        shopDetail.setDesc9(shopRegistForm.getDesc9());
        shopDetail.setDesc10(shopRegistForm.getDesc10());
        shopDetail.setDesc11(shopRegistForm.getDesc11());
        shopDetail.setDesc12(shopRegistForm.getDesc12());
        shopDetail.setDesc13(shopRegistForm.getDesc13());
        shopDetail.setDesc14(shopRegistForm.getDesc14());
        shopDetail.setDesc15(shopRegistForm.getDesc15());
        shopDetail.setDesc16(shopRegistForm.getDesc16());
        shopDetail.setDesc17(shopRegistForm.getDesc17());
        shopDetail.setDesc18(shopRegistForm.getDesc18());
        shopDetail.setDesc19(shopRegistForm.getDesc19());
        shopDetail.setDesc20(shopRegistForm.getDesc20());
        shopDetail.setDesc21(shopRegistForm.getDesc21());
        shopDetail.setDesc22(shopRegistForm.getDesc22());
        shopDetail.setDesc23(shopRegistForm.getDesc23());
        shopDetail.setDesc24(shopRegistForm.getDesc24());
        shopDetail.setDesc25(shopRegistForm.getDesc25());
        shopDetail.setDesc26(shopRegistForm.getDesc26());
        shopDetail.setDesc27(shopRegistForm.getDesc27());
        shopDetail.setDesc28(shopRegistForm.getDesc28());
        shopDetail.setDesc29(shopRegistForm.getDesc29());
        shopDetail.setDesc30(shopRegistForm.getDesc30());
        shopDetail.setDesc31(shopRegistForm.getDesc31());
        shopDetail.setDesc32(shopRegistForm.getDesc32());
        shopDetail.setDesc33(shopRegistForm.getDesc33());
        shopDetail.setDesc34(shopRegistForm.getDesc34());
        shopDetail.setDesc35(shopRegistForm.getDesc35());
        shopDetail.setDesc36(shopRegistForm.getDesc36());
        shopDetail.setDesc37(shopRegistForm.getDesc37());
        shopDetail.setDesc38(shopRegistForm.getDesc38());
        shopDetail.setDesc39(shopRegistForm.getDesc39());
        shopDetail.setDesc40(shopRegistForm.getDesc40());
//        shopDetail.setDesc41(shopRegistForm.getDesc41());
//        shopDetail.setDesc42(shopRegistForm.getDesc42());
//        shopDetail.setDesc43(shopRegistForm.getDesc43());
        shopDetail.setTel(shopRegistForm.getTel());
        shopDetail.setAddress(shopRegistForm.getAddress());
        if(shopRegistForm.getPicUrl1() != null && shopRegistForm.getPicUrl1() !=""){
            shopDetail.setPicUrl1(shopRegistForm.getPicUrl1());
        }

        if(shopRegistForm.getPicUrl2() != null && shopRegistForm.getPicUrl2() !=""){
            shopDetail.setPicUrl2(shopRegistForm.getPicUrl2());
        }

        if(shopRegistForm.getPicUrl3() != null && shopRegistForm.getPicUrl3() !=""){
            shopDetail.setPicUrl3(shopRegistForm.getPicUrl3());
        }

        if(shopRegistForm.getPicUrl4() != null && shopRegistForm.getPicUrl4() !=""){
            shopDetail.setPicUrl4(shopRegistForm.getPicUrl4());
        }

        if(shopRegistForm.getPicUrl5() != null && shopRegistForm.getPicUrl5() !=""){
            shopDetail.setPicUrl5(shopRegistForm.getPicUrl5());
        }

        if(shopRegistForm.getPicUrl6() != null && shopRegistForm.getPicUrl6() !=""){
            shopDetail.setPicUrl6(shopRegistForm.getPicUrl6());
        }

        if(shopRegistForm.getVideoUrl() != null && shopRegistForm.getVideoUrl() !=""){
            shopDetail.setVideoUrl(shopRegistForm.getVideoUrl());
        }
        shopDetail.setCreateDatetime(new Date(System.currentTimeMillis()));
        shopDetail.setUpdateDatetime(new Date(System.currentTimeMillis()));
        shopDetail.setDelFlg(Boolean.FALSE);
        shopDetail.setNote(Constant.EMPTY_STRING);
        shopDetailMapper.updateShopDetail(shopDetail);
        return shopDetail.getId();
    }

    public int updateShopDetailByEntity(ShopDetail shopDetail) {
        return shopDetailMapper.updateShopDetail(shopDetail);
    }

    public int deleteShopDetail( Long id) {
        shopDetailMapper.deleteShopDetail(id);
        return 0;
    }

    public int deleteShopDetailVideo( Long id) {
        shopDetailMapper.deleteShopDetailVideo(id);
        return 0;
    }

    public int deleteShopDetailFlic( Long id) {
        shopDetailMapper.deleteShopDetailFlic(id);
        return 0;
    }

}
