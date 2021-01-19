package jp.co.vermore.service;

import java.util.Date;
import java.util.List;

import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.common.util.StringUtil;
import jp.co.vermore.entity.ShopForList;
import jp.co.vermore.form.admin.CorporateInfoForm;
import jp.co.vermore.form.admin.ShopListForm;
import jp.co.vermore.validation.ShopListParams;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import jp.co.vermore.common.Constant;
import jp.co.vermore.form.admin.ShopRegistForm;
import jp.co.vermore.mapper.ShopCouponMapper;
import jp.co.vermore.entity.Shop;
import jp.co.vermore.mapper.ShopMapper;

/**
 * ShopService
 * Created by wubin.
 * DateTime: 2018/03/12 11:18
 * Copyright: sLab, Corp
 */

@Service
public class ShopService {

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private ShopCouponMapper shopCouponMapper;

    public List<Shop> getShopAll(){
        List<Shop> shopList = shopMapper.getShopAll();
        return shopList;
    }

    public List<Shop> getShopForSettlementBatch(){
        String monthOfFirst = DateUtil.getMonthStartYyyyMMdd();
        String LastmonthFirst = DateUtil.getLastmonthStartYyyyMMdd();
        List<Shop> shopList = shopMapper.getShopForSettlementBatch(monthOfFirst,LastmonthFirst);
        return shopList;
    }

    public List<Shop> getShopAllForRandom(){
        List<Shop> shopList = shopMapper.getShopAllForRandom();
        return shopList;
    }

    public List<Shop> getShopAll(int shopType,int limit, int offset) {
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        List<Shop> shopList = shopMapper.getShopJsonAll(shopType,tomorrow,today,limit,offset);
        return shopList;
    }

    public List<Shop> getShopAllCount(int shopType) {
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        return shopMapper.getShopAllCount(shopType,tomorrow,today);
    }

    public List<Shop> getCorporate() {
        List<Shop> shopList = shopMapper.getCorporate();
        return shopList;
    }

    public List<Shop> getShopByCoupon(int shopType,int limit, int offset) {
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        List<Shop> shopList = shopMapper.getShopByCoupon(shopType,tomorrow,today,limit,offset);
        return shopList;
    }

    public List<Shop> getShopByCoupon2(List<Long> idList,int shopType,int limit, int offset) {
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        List<Shop> shopList = shopMapper.getShopByCoupon2(idList,shopType,tomorrow,today,limit,offset);
        return shopList;
    }

    public List<Shop> getShopByCoupon3(List<Long> idList,int shopType) {
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        List<Shop> shopList = shopMapper.getShopByCoupon3(idList,shopType,tomorrow,today);
        return shopList;
    }

    public List<Shop> getShopAllByCoupon(int shopType) {
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        List<Shop> shopList = shopMapper.getShopAllByCoupon(shopType,tomorrow,today);
        return shopList;
    }

    public List<Shop> getShopByNow5(int shopType,String tomorrow,String today,int limit, int offset) {
        List<Shop> shopList = shopMapper.getShopByNow5(shopType,tomorrow,today,limit,offset);
        return shopList;
    }

    public List<Shop> getShopByNow5All(int shopType) {
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        List<Shop> shopList = shopMapper.getShopByNow5All(shopType,tomorrow,today);
        return shopList;
    }

    public Shop getShopById(Long shopListId) {
        return shopMapper.getShopById(shopListId);
    }

    public List<Shop> getShopByIdList(List<Long> shopIdList) {
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        List<Shop> list = shopMapper.getShopByIdList(shopIdList,tomorrow,today);
        return list;
    }

    public List<Long> getShopId(Integer shopType){
        List<Long> shopIdList = shopMapper.getShopId(shopType);
        return shopIdList;
    }

    public List<Long> getShopIdByKeyword(Integer shopType,String Keyword) {
        List<Long> idList = shopMapper.getShopIdByKeyword(shopType,Keyword);
        return idList;
    }

    public List<Long> getShopIdByNow4(Integer shopType,Integer now4) {
        List<Long> idList = shopMapper.getShopIdByNow4(shopType,now4);
        return idList;
    }

    public List<Long> getShopIdByNow5(Integer shopType,Integer now5) {
        List<Long> idList = shopMapper.getShopIdByNow5(shopType,now5);
        return idList;
    }

    public Shop getShopByUuid(String uuid) {
        Shop shop = shopMapper.getShopByUuid(uuid);
        return shop;
    }

    public List<Shop> getShopByUuidList(List<String> list) {
        List<Shop> shoplist = shopMapper.getShopByUuidList(list);
        return shoplist;
    }

    public Shop getShopByIdAndType(Long shopListId,int shopType) {
        Shop shop = shopMapper.getShopByIdAndType(shopListId,shopType);
        return shop;
    }

    public List<Shop> getShopList(ShopListParams params){
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        params.setToday(today);
        params.setTomorrow(tomorrow);
        List<Shop> shopList = shopMapper.getShopList(params);
        return shopList;
    }

    public Shop insertShop(ShopRegistForm shopRegistForm) {
        Shop shop = new Shop();
        String uuid = "";
        int flagUuid = 0;
        int cntSelect = 0;
        while (flagUuid != 1 && cntSelect < 100){
            uuid = StringUtil.getUuid();
            if (getShopByUuid(uuid) == null){
                flagUuid = 1;
            }
            cntSelect++;
        }

        shop.setUuid(uuid);
        shop.setShopType(shopRegistForm.getShopType());
        shop.setName(shopRegistForm.getName());
        shop.setCoordinate1(shopRegistForm.getCoordinate1());
        shop.setCoordinate2(shopRegistForm.getCoordinate2());
        shop.setAddress(shopRegistForm.getAddress());
        shop.setExcerpt(shopRegistForm.getExcerpt());
        if(shopRegistForm.getPicUrl() == null || shopRegistForm.getPicUrl().equals("")){
            shop.setPicUrl("");
        }else{
            shop.setPicUrl(shopRegistForm.getPicUrl());
        }
        shop.setNow4(0);
        shop.setNow5(0);
        if(shopRegistForm.getPublishStart() == null || "".equals(shopRegistForm.getPublishStart())){
            shop.setPublishStart(DateUtil.getDefaultDate());
        }else{
            shop.setPublishStart(DateUtil.stringToDateyyyy_MM_dd(shopRegistForm.getPublishStart()));
        }
        if(shopRegistForm.getPublishEnd() == null || "".equals(shopRegistForm.getPublishEnd())){
            shop.setPublishEnd(DateUtil.getDefaultPublishEnd());
        }else{
            shop.setPublishEnd(DateUtil.stringToDateyyyy_MM_dd(shopRegistForm.getPublishEnd()));
        }
        shop.setCreateDatetime(new Date(System.currentTimeMillis()));
        shop.setUpdateDatetime(new Date(System.currentTimeMillis()));
        shop.setDelFlg(Boolean.FALSE);
        shop.setNote(Constant.EMPTY_STRING);
        shopMapper.insertShop(shop);
        return shop;
    }

    public Shop updateShop(ShopRegistForm shopRegistForm) {
        Shop shop = new Shop();
        shop.setId(shopRegistForm.getShopListId());
        shop.setName(shopRegistForm.getName());
        shop.setCoordinate1(shopRegistForm.getCoordinate1());
        shop.setCoordinate2(shopRegistForm.getCoordinate2());
        shop.setAddress(shopRegistForm.getAddress());
        shop.setExcerpt(shopRegistForm.getExcerpt());
        if(shopRegistForm.getPicUrl() == null || shopRegistForm.getPicUrl().equals("")){
            shop.setPicUrl("");
        }else{
            shop.setPicUrl(shopRegistForm.getPicUrl());
        }
        shop.setNow5(shopRegistForm.getNow5());
        if(shopRegistForm.getPublishStart() == null || "".equals(shopRegistForm.getPublishStart())){
            shop.setPublishStart(DateUtil.getDefaultDate());
        }else{
            shop.setPublishStart(DateUtil.stringToDateyyyy_MM_dd(shopRegistForm.getPublishStart()));
        }
        if(shopRegistForm.getPublishEnd() == null || "".equals(shopRegistForm.getPublishEnd())){
            shop.setPublishEnd(DateUtil.getDefaultPublishEnd());
        }else{
            shop.setPublishEnd(DateUtil.stringToDateyyyy_MM_dd(shopRegistForm.getPublishEnd()));
        }
        shop.setCreateDatetime(new Date(System.currentTimeMillis()));
        shop.setUpdateDatetime(new Date(System.currentTimeMillis()));
        shop.setDelFlg(Boolean.FALSE);
        shop.setNote(Constant.EMPTY_STRING);
        shopMapper.updateShop(shop);
        return shop;
    }

    public Shop insertCorporateList(CorporateInfoForm form) {
        Shop shop = new Shop();
        String uuid = "";
        int flagUuid = 0;
        int cntSelect = 0;
        while (flagUuid != 1 && cntSelect < 100){
            uuid = StringUtil.getUuid();
            if (getShopByUuid(uuid) == null){
                flagUuid = 1;
            }
            cntSelect++;
        }

        shop.setUuid(uuid);
        shop.setShopType( Constant.SHOP_TAG.CORPORATE_INFO);
        shop.setName(form.getName());
        shop.setAddress(form.getAddress());
        shop.setExcerpt(form.getExcerpt());
        shop.setCoordinate1(form.getCoordinate1());
        shop.setCoordinate2(form.getCoordinate2());
        if(form.getPicUrl1() == null || form.getPicUrl1().equals("")){
            shop.setPicUrl("");
        }else{
            shop.setPicUrl(form.getPicUrl1());
        }
        if(form.getPublishStart() == null || "".equals(form.getPublishStart())){
            shop.setPublishStart(DateUtil.getDefaultDate());
        }else{
            shop.setPublishStart(DateUtil.stringToDateyyyy_MM_dd(form.getPublishStart()));
        }
        if(form.getPublishEnd() == null || "".equals(form.getPublishEnd())){
            shop.setPublishEnd(DateUtil.getDefaultPublishEnd());
        }else{
            shop.setPublishEnd(DateUtil.stringToDateyyyy_MM_dd(form.getPublishEnd()));
        }
        shop.setCreateDatetime(new Date(System.currentTimeMillis()));
        shop.setUpdateDatetime(new Date(System.currentTimeMillis()));
        shop.setDelFlg(Boolean.FALSE);
        shop.setNote(Constant.EMPTY_STRING);
        shopMapper.insertShop(shop);
        return shop;
    }

    public Shop updateCorporateList(CorporateInfoForm form) {
        Shop shop = new Shop();
        shop.setId(form.getShopListId());
        shop.setName(form.getName());
        shop.setAddress(form.getAddress());
        shop.setExcerpt(form.getExcerpt());
        shop.setCoordinate1(form.getCoordinate1());
        shop.setCoordinate2(form.getCoordinate2());
        if(form.getPicUrl1() == null || form.getPicUrl1().equals("")){
            shop.setPicUrl("");
        }else{
            shop.setPicUrl(form.getPicUrl1());
        }
        if(form.getPublishStart() == null || "".equals(form.getPublishStart())){
            shop.setPublishStart(DateUtil.getDefaultDate());
        }else{
            shop.setPublishStart(DateUtil.stringToDateyyyy_MM_dd(form.getPublishStart()));
        }
        if(form.getPublishEnd() == null || "".equals(form.getPublishEnd())){
            shop.setPublishEnd(DateUtil.getDefaultPublishEnd());
        }else{
            shop.setPublishEnd(DateUtil.stringToDateyyyy_MM_dd(form.getPublishEnd()));
        }

        shop.setUpdateDatetime(new Date(System.currentTimeMillis()));
        shop.setDelFlg(Boolean.FALSE);
        shop.setNote(Constant.EMPTY_STRING);
        shopMapper.updateShop(shop);
        return shop;
    }

    public int updateShopByEntity(Shop shop) {
        return shopMapper.updateShop(shop);
    }

    public int updateShopForNow4ByIdList(List<Long> idList,boolean now4) {
        return shopMapper.updateShopForNow4ByIdList(idList,now4);
    }

    public int updateShopForNow5ByIdList(List<Long> idList,boolean now5) {
        return shopMapper.updateShopForNow5ByIdList(idList,now5);
    }

    public Long deleteShop(Long id) {
        Shop shop = new Shop();
        shop.setId(id);
        shop.setPublishEnd(new Date(System.currentTimeMillis()));
        shop.setUpdateDatetime(new Date(System.currentTimeMillis()));
        return shopMapper.deleteShop(shop);
    }

    public int getIntCoordinate(double coordinate){
        int cd = (int)(coordinate*1000000000);
        return cd;
    }

    public double getDoubleCoordinate(int coordinate){
        double cd = (double)coordinate/(double)1000000000;
        return cd;
    }

    public long countShop() { return shopMapper.count();}

    public List<Shop> getShopAllByIdListAndCondition(List<Long> idList, String name, int shopType) {
        return shopMapper.getShopAllByIdListAndCondition(idList, name, shopType);
    }

    public List<ShopForList> getShopAllByCondition(ShopListForm form) {
        List<ShopForList> shop = shopMapper.getShopAllByCondition(form);
        return shop;
    }

    public int getShopCountByCondition(ShopListForm form) {
        return shopMapper.getShopCountByCondition(form);
    }

    public int getShopCount() {
        return shopMapper.getShopCount();
    }

//    By wangyu ↓
    public List<Long> getIdBybusiness(Integer shopType,Integer tagType,Integer content){
        List<Long> busInessList = shopMapper.getIdBybusiness(shopType,tagType,content);
        return busInessList;
    }

    public List<Shop> getCoordinate(){
        List<Shop> getCoordinate = shopMapper.getCoordinate();
        return  getCoordinate;
    }
    public String getShopUuidByShopIdShop(Long id) {
        String uuid2 = shopMapper.getShopUuidByShopIdShop(id);
        return uuid2;
    }

    public String getShopNameByShopIdShop(Long id) {
        String name = shopMapper.getShopNameByShopIdShop(id);
        return name;
    }

    public List<Shop> getShopListCount(ShopListParams params) {
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        params.setToday(today);
        params.setTomorrow(tomorrow);
        List<Shop> shopList = shopMapper.getShopListCount(params);
        return shopList;
    }
}
