package jp.co.vermore.service;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.entity.CorporateInfoDetail;
import jp.co.vermore.entity.Shop;
import jp.co.vermore.form.admin.CorporateInfoForm;
import jp.co.vermore.form.admin.CorporateInfoListForm;
import jp.co.vermore.mapper.CorporateInfoDetailMapper;
import jp.co.vermore.mapper.ShopMapper;

import jp.co.vermore.validation.CorporateInfoListParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * CorporateInfoService
 * Created by wubin.
 *
 * DateTime: 2018/04/16 20:11
 * Copyright: sLab, Corp
 */
@Service
public class CorporateInfoService {

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private CorporateInfoDetailMapper corporateInfoDetailMapper;

    public CorporateInfoDetail getDetailById(Long id) {
        return corporateInfoDetailMapper.getDetailById(id);
    }

    public CorporateInfoDetail getDetailByShopId(Long id) {
        return corporateInfoDetailMapper.getDetailByShopId(id);
    }

    public Long getCorporateId(Long shopListId){
        Long id = corporateInfoDetailMapper.getCorporateDetailId(shopListId);
        return id;
    }

    public int getCorporateType(Long shopListId){
        int shopType = corporateInfoDetailMapper.getCorporateType(shopListId);
        return shopType;
    }

    public Long getCorporateListId(Long id){
        Long shopListId = corporateInfoDetailMapper.getCorporateListId(id);
        return  shopListId;
    }

    public List<CorporateInfoDetail> getCorporateInfoDetailAll(Long shopListId) {
        List<CorporateInfoDetail> CorporateDetailList = corporateInfoDetailMapper.getCorporateInfoDetailAll(shopListId);
        return CorporateDetailList;
    }

    public List<CorporateInfoDetail> getCorporateInfoDetailList(List<Long> idList) {
        List<CorporateInfoDetail> list = corporateInfoDetailMapper.getCorporateInfoDetailList(idList);
        return list;
    }

    public Long getShopListId(Long id){
        Long shopListId = corporateInfoDetailMapper.getShopListId(id);
        return  shopListId;
    }

    public List<Shop> getCorporateInfoList(CorporateInfoListParams params){
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        params.setTomorrow(tomorrow);
        params.setToday(today);

        List<Shop> shopList = shopMapper.getCorporateInfoList(params);
        return shopList;
    }

    public List<CorporateInfoDetail> getCorporateInfoDetailAdmin() {
        List<CorporateInfoDetail> list = corporateInfoDetailMapper.getCorporateInfoDetailAdmin();
        return list;
    }

    public Long insertCorporateInfoDetail(CorporateInfoForm form, Long shopListId) {
        CorporateInfoDetail detail = new CorporateInfoDetail();
        detail.setShopType(Constant.SHOP_TAG.CORPORATE_INFO);
        detail.setShopListId(shopListId);
        detail.setName(form.getName());
        detail.setTitle(form.getTitle());
        detail.setDesc1(form.getDesc1());
        detail.setTel(form.getTel());
        detail.setAddress(form.getAddress());
        if(form.getDesc2() == null || form.getDesc2().equals("")){
            detail.setDesc2("");
        }else{
            detail.setDesc2(form.getDesc2());
        }
        if(form.getDesc3() == null || form.getDesc3().equals("")){
            detail.setDesc3("");
        }else{
            detail.setDesc3(form.getDesc3());
        }
        if(form.getDesc4() == null || form.getDesc4().equals("")){
            detail.setDesc4("");
        }else{
            detail.setDesc4(form.getDesc4());
        }
        if(form.getDesc5() == null || form.getDesc5().equals("")){
            detail.setDesc5("");
        }else{
            detail.setDesc5(form.getDesc5());
        }
        if(form.getDesc6() == null || form.getDesc6().equals("")){
            detail.setDesc6("");
        }else{
            detail.setDesc6(form.getDesc6());
        }
        if(form.getDesc7() == null || form.getDesc7().equals("")){
            detail.setDesc7("");
        }else{
            detail.setDesc7(form.getDesc7());
        }
        if(form.getDesc8() == null || form.getDesc8().equals("")){
            detail.setDesc8("");
        }else{
            detail.setDesc8(form.getDesc8());
        }
        if(form.getDesc9() == null || form.getDesc9().equals("")){
            detail.setDesc9("");
        }else{
            detail.setDesc9(form.getDesc9());
        }
        if(form.getDesc10() == null || form.getDesc10().equals("")){
            detail.setDesc10("");
        }else{
            detail.setDesc10(form.getDesc10());
        }
        if(form.getDesc11() == null || form.getDesc11().equals("")){
            detail.setDesc11("");
        }else{
            detail.setDesc11(form.getDesc11());
        }
        if(form.getPicUrl1() == null || form.getPicUrl1().equals("")){
            detail.setPicUrl1("");
        }else{
            detail.setPicUrl1(form.getPicUrl1());
        }
        if(form.getPicUrl2() == null || form.getPicUrl2().equals("")){
            detail.setPicUrl2("");
        }else{
            detail.setPicUrl2(form.getPicUrl2());
        }
        if(form.getPicUrl3() == null || form.getPicUrl3().equals("")){
            detail.setPicUrl3("");
        }else{
            detail.setPicUrl3(form.getPicUrl3());
        }
        if(form.getPicUrl4() == null || form.getPicUrl4().equals("")){
            detail.setPicUrl4("");
        }else{
            detail.setPicUrl4(form.getPicUrl4());
        }
        if(form.getPicUrl5() == null || form.getPicUrl5().equals("")){
            detail.setPicUrl5("");
        }else{
            detail.setPicUrl5(form.getPicUrl5());
        }
        if(form.getPicUrl6() == null || form.getPicUrl6().equals("")){
            detail.setPicUrl6("");
        }else{
            detail.setPicUrl6(form.getPicUrl6());
        }
        if(form.getVideoUrl() == null || form.getVideoUrl().equals("")){
            detail.setVideoUrl("");
        }else{
            detail.setVideoUrl(form.getVideoUrl());
        }
        detail.setCreateDatetime(new Date(System.currentTimeMillis()));
//        detail.setUpdateDatetime(new Date(System.currentTimeMillis()));
        detail.setDelFlg(Boolean.FALSE);
        detail.setNote(Constant.EMPTY_STRING);
        corporateInfoDetailMapper.insertCorporateDetail(detail);
        return detail.getId();
    }

    public Long updateCorporateInfoDetail(CorporateInfoForm form) {
        CorporateInfoDetail detail = new CorporateInfoDetail();
        detail.setId(form.getId());
        detail.setShopListId(form.getShopListId());
        detail.setName(form.getName());
        detail.setTitle(form.getTitle());
        detail.setTel(form.getTel());
        detail.setAddress(form.getAddress());
        detail.setDesc1(form.getDesc1());

        if(form.getDesc2() == null || form.getDesc2().equals("")){
            detail.setDesc2("");
        }else{
            detail.setDesc2(form.getDesc2());
        }
        if(form.getDesc3() == null || form.getDesc3().equals("")){
            detail.setDesc3("");
        }else{
            detail.setDesc3(form.getDesc3());
        }
        if(form.getDesc4() == null || form.getDesc4().equals("")){
            detail.setDesc4("");
        }else{
            detail.setDesc4(form.getDesc4());
        }
        if(form.getDesc5() == null || form.getDesc5().equals("")){
            detail.setDesc5("");
        }else{
            detail.setDesc5(form.getDesc5());
        }
        if(form.getDesc6() == null || form.getDesc6().equals("")){
            detail.setDesc6("");
        }else{
            detail.setDesc6(form.getDesc6());
        }
        if(form.getDesc7() == null || form.getDesc7().equals("")){
            detail.setDesc7("");
        }else{
            detail.setDesc7(form.getDesc7());
        }
        if(form.getDesc8() == null || form.getDesc8().equals("")){
            detail.setDesc8("");
        }else{
            detail.setDesc8(form.getDesc8());
        }
        if(form.getDesc9() == null || form.getDesc9().equals("")){
            detail.setDesc9("");
        }else{
            detail.setDesc9(form.getDesc9());
        }
        if(form.getDesc10() == null || form.getDesc10().equals("")){
            detail.setDesc10("");
        }else{
            detail.setDesc10(form.getDesc10());
        }
        if(form.getDesc11() == null || form.getDesc11().equals("")){
            detail.setDesc11("");
        }else{
            detail.setDesc11(form.getDesc11());
        }
        if(form.getPicUrl1() == null || form.getPicUrl1().equals("")){
            detail.setPicUrl1("");
        }else{
            detail.setPicUrl1(form.getPicUrl1());
        }
        if(form.getPicUrl2() == null || form.getPicUrl2().equals("")){
            detail.setPicUrl2("");
        }else{
            detail.setPicUrl2(form.getPicUrl2());
        }
        if(form.getPicUrl3() == null || form.getPicUrl3().equals("")){
            detail.setPicUrl3("");
        }else{
            detail.setPicUrl3(form.getPicUrl3());
        }
        if(form.getPicUrl4() == null || form.getPicUrl4().equals("")){
            detail.setPicUrl4("");
        }else{
            detail.setPicUrl4(form.getPicUrl4());
        }
        if(form.getPicUrl5() == null || form.getPicUrl5().equals("")){
            detail.setPicUrl5("");
        }else{
            detail.setPicUrl5(form.getPicUrl5());
        }
        if(form.getPicUrl6() == null || form.getPicUrl6().equals("")){
            detail.setPicUrl6("");
        }else{
            detail.setPicUrl6(form.getPicUrl6());
        }
        if(form.getVideoUrl() == null || form.getVideoUrl().equals("")){
            detail.setVideoUrl("");
        }else{
            detail.setVideoUrl(form.getVideoUrl());
        }

        detail.setCreateDatetime(new Date(System.currentTimeMillis()));
        detail.setUpdateDatetime(new Date(System.currentTimeMillis()));
        detail.setDelFlg(Boolean.FALSE);
        detail.setNote(Constant.EMPTY_STRING);
        corporateInfoDetailMapper.updateCorporateDetail(detail);
        return detail.getShopListId();
    }

    public int deleteCorporateInfoDetail( Long id) {
        return  corporateInfoDetailMapper.deleteCorporateDetail(id);
    }

    public int deleteDetailVideo( Long shopId) {
        corporateInfoDetailMapper.deleteDetailVideo(shopId);
        return 0;
    }

    public int deleteDetailFlic( Long shopId) {
        corporateInfoDetailMapper.deleteDetailFlic(shopId);
        return 0;
    }

    public List<CorporateInfoDetail> getCorporateInfoAllByCondition(CorporateInfoListForm form) {
        List<CorporateInfoDetail> corporateInfo = corporateInfoDetailMapper.getCorporateInfoAllByCondition(form);
        return corporateInfo;
    }

    public int getCorporateInfoCountByCondition(CorporateInfoListForm form) {
        return corporateInfoDetailMapper.getCorporateInfoCountByCondition(form);
    }

    public int getCorporateInfoCount() {
        return corporateInfoDetailMapper.getCorporateInfoCount();
    }

}
