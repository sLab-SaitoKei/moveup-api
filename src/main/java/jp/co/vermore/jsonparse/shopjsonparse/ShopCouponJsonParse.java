package jp.co.vermore.jsonparse.shopjsonparse;

import jp.co.vermore.common.mvc.BaseJsonParse;
import jp.co.vermore.entity.ShopCoupon;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ShopCouponJsonParse
 * Created by wubin.
 *
 * DateTime: 2018/04/20 9:15
 * Copyright: sLab, Corp
 */

public class ShopCouponJsonParse extends BaseJsonParse {

    private int shopCount;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    private String area;

    private List<Map<String,Object>> couponList;

    private List<Map<String,Object>> nowGoList;

    private List<Map<String,Object>> contentList;

    public List<Map<String, Object>> getFoodMainMenuList() {
        return foodMainMenuList;
    }

    public void setFoodMainMenuList(List<Map<String, Object>> foodMainMenuList) {
        this.foodMainMenuList = foodMainMenuList;
    }

    public List<Map<String, Object>> getFastionMainMenuList() {
        return fastionMainMenuList;
    }

    public void setFastionMainMenuList(List<Map<String, Object>> fastionMainMenuList) {
        this.fastionMainMenuList = fastionMainMenuList;
    }

    public List<Map<String, Object>> getHealthMainMenuList() {
        return healthMainMenuList;
    }

    public void setHealthMainMenuList(List<Map<String, Object>> healthMainMenuList) {
        this.healthMainMenuList = healthMainMenuList;
    }

    public List<Map<String, Object>> getPlayMainMenuList() {
        return playMainMenuList;
    }

    public void setPlayMainMenuList(List<Map<String, Object>> playMainMenuList) {
        this.playMainMenuList = playMainMenuList;
    }

    public List<Map<String, Object>> getBridalMainMenuList() {
        return bridalMainMenuList;
    }

    public void setBridalMainMenuList(List<Map<String, Object>> bridalMainMenuList) {
        this.bridalMainMenuList = bridalMainMenuList;
    }

    public List<Map<String, Object>> getDriveMainMenuList() {
        return driveMainMenuList;
    }

    public void setDriveMainMenuList(List<Map<String, Object>> driveMainMenuList) {
        this.driveMainMenuList = driveMainMenuList;
    }

    public List<Map<String, Object>> getInfoMainMenuList() {
        return infoMainMenuList;
    }

    public void setInfoMainMenuList(List<Map<String, Object>> infoMainMenuList) {
        this.infoMainMenuList = infoMainMenuList;
    }

    public List<Map<String, Object>> getLearnMainMenuList() {
        return learnMainMenuList;
    }

    public void setLearnMainMenuList(List<Map<String, Object>> learnMainMenuList) {
        this.learnMainMenuList = learnMainMenuList;
    }

    public List<Map<String, Object>> getFacilityMainMenuList() {
        return facilityMainMenuList;
    }

    public void setFacilityMainMenuList(List<Map<String, Object>> facilityMainMenuList) {
        this.facilityMainMenuList = facilityMainMenuList;
    }

    public List<Map<String, Object>> getLifeMainMenuList() {
        return lifeMainMenuList;
    }

    public void setLifeMainMenuList(List<Map<String, Object>> lifeMainMenuList) {
        this.lifeMainMenuList = lifeMainMenuList;
    }

    private List<Map<String,Object>> foodMainMenuList;

    private List<Map<String,Object>> fastionMainMenuList;

    private List<Map<String,Object>> healthMainMenuList;

    private List<Map<String,Object>> playMainMenuList;

    private List<Map<String,Object>> bridalMainMenuList;

    private List<Map<String,Object>> driveMainMenuList;

    private List<Map<String,Object>> infoMainMenuList;

    private List<Map<String,Object>> learnMainMenuList;

    private List<Map<String,Object>> facilityMainMenuList;

    private List<Map<String,Object>> lifeMainMenuList;


    private List<Map<String,Object>> areaList;

    public List<Map<String, Object>> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<Map<String, Object>> couponList) {
        this.couponList = couponList;
    }

    public int getShopCount() {
        return shopCount;
    }

    public void setShopCount(int shopCount) {
        this.shopCount = shopCount;
    }

    public List<Map<String, Object>> getContentList() {
        return contentList;
    }

    public void setContentList(List<Map<String, Object>> contentList) {
        this.contentList = contentList;
    }

    public List<Map<String, Object>> getNowGoList() {
        return nowGoList;
    }

    public void setNowGoList(List<Map<String, Object>> nowGoList) {
        this.nowGoList = nowGoList;
    }

    public List<Map<String, Object>> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Map<String, Object>> areaList) {
        this.areaList = areaList;
    }



}
