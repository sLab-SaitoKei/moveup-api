package jp.co.vermore.jsonparse.shopdetailjsonparse;

import jp.co.vermore.common.mvc.BaseJsonParse;
import jp.co.vermore.entity.Recruit;
import jp.co.vermore.entity.ShopCoupon;
import jp.co.vermore.jsonparse.shopjsonparse.ShopCouponJsonParse;
import jp.co.vermore.jsonparse.shopjsonparse.ShopNow5JsonParse;

import java.util.List;
import java.util.Map;

/**
 * ShopDetailJsonParse
 * Created by wubin.
 *
 * DateTime: 2018/03/12 15:00
 * Copyright: sLab, Corp
 */

public class ShopFastionDetailJsonParse extends BaseJsonParse {

    private Long id;

    private int shopType;

    private String name;

    private String picUrl1;

    private String picUrl2;

    private String picUrl3;

    private String picUrl4;

    private String picUrl5;

    private String picUrl6;

    private String picUrl7;

    private String picUrl8;

    private String picUrl9;

    private String picUrl10;

    private String picUrl11;

    public String getPicUrl12() {
        return picUrl12;
    }

    public void setPicUrl12(String picUrl12) {
        this.picUrl12 = picUrl12;
    }

    private String picUrl12;

    private String videoUrl;

    private String title;

    private String desc1;

    private String desc2;

    private String desc3;

    private String desc4;

    private String desc5;

    private String desc6;

    private String desc7;

    private String desc8;

    private String desc9;

    private String desc13;

    private String desc15;

    private String desc35;

    private String desc36;

    private String desc37;

    private String desc38;

    private String desc39;

    private String desc40;

    private String tel;

    private String address;

    private String station;

    private int walkTime;

    private int dayPriceLow;

    private int dayPriceHigh;

    private String genre;

    private String shopOpenTime;

    private String shopCloseTime;

    private double coord1;

    private double coord2;

    private List<Recruit> recruitList;

    private List<ShopNow5JsonParse> now5List;

    private List<Map<String,Object>> couponList;

    private List<Map<String,Object>> nowGoList;

    private List<Map<Integer,String>> brandStringList;

    private List<ShopCoupon> scList;

    private String uuid2;

    public int getFavType() {
        return favType;
    }

    public void setFavType(int favType) {
        this.favType = favType;
    }

    private int favType;


    public String getUserSetting() {
        return userSetting;
    }

    public void setUserSetting(String userSetting) {
        this.userSetting = userSetting;
    }

    private String userSetting;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicUrl1() {
        return picUrl1;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setPicUrl1(String picUrl1) {
        this.picUrl1 = picUrl1;
    }

    public String getPicUrl2() {
        return picUrl2;
    }

    public void setPicUrl2(String picUrl2) {
        this.picUrl2 = picUrl2;
    }

    public String getPicUrl3() {
        return picUrl3;
    }

    public void setPicUrl3(String picUrl3) {
        this.picUrl3 = picUrl3;
    }

    public String getPicUrl4() {
        return picUrl4;
    }

    public void setPicUrl4(String picUrl4) {
        this.picUrl4 = picUrl4;
    }

    public String getPicUrl5() {
        return picUrl5;
    }

    public void setPicUrl5(String picUrl5) {
        this.picUrl5 = picUrl5;
    }

    public String getPicUrl6() {
        return picUrl6;
    }

    public void setPicUrl6(String picUrl6) {
        this.picUrl6 = picUrl6;
    }

    public String getDesc39() {
        return desc39;
    }

    public void setDesc39(String desc39) {
        this.desc39 = desc39;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc1() {
        return desc1;
    }

    public void setDesc1(String desc1) {
        this.desc1 = desc1;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public int getWalkTime() {
        return walkTime;
    }

    public void setWalkTime(int walkTime) {
        this.walkTime = walkTime;
    }

    public int getDayPriceLow() {
        return dayPriceLow;
    }

    public void setDayPriceLow(int dayPriceLow) {
        this.dayPriceLow = dayPriceLow;
    }

    public int getDayPriceHigh() {
        return dayPriceHigh;
    }

    public void setDayPriceHigh(int dayPriceHigh) {
        this.dayPriceHigh = dayPriceHigh;
    }

    public String getShopOpenTime() {
        return shopOpenTime;
    }

    public void setShopOpenTime(String shopOpenTime) {
        this.shopOpenTime = shopOpenTime;
    }

    public String getShopCloseTime() {
        return shopCloseTime;
    }

    public void setShopCloseTime(String shopCloseTime) {
        this.shopCloseTime = shopCloseTime;
    }

    public List<ShopNow5JsonParse> getNow5List() {
        return now5List;
    }

    public void setNow5List(List<ShopNow5JsonParse> now5List) {
        this.now5List = now5List;
    }

    public List<Map<String,Object>> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<Map<String,Object>> couponList) {
        this.couponList = couponList;
    }

    public List<Map<Integer,String>> getBrandStringList() {
        return brandStringList;
    }

    public void setBrandStringList(List<Map<Integer,String>> brandStringList) {
        this.brandStringList = brandStringList;
    }

    public String getDesc2() {
        return desc2;
    }

    public void setDesc2(String desc2) {
        this.desc2 = desc2;
    }

    public String getDesc3() {
        return desc3;
    }

    public void setDesc3(String desc3) {
        this.desc3 = desc3;
    }

    public String getDesc4() {
        return desc4;
    }

    public void setDesc4(String desc4) {
        this.desc4 = desc4;
    }

    public String getDesc5() {
        return desc5;
    }

    public void setDesc5(String desc5) {
        this.desc5 = desc5;
    }

    public String getDesc6() {
        return desc6;
    }

    public void setDesc6(String desc6) {
        this.desc6 = desc6;
    }

    public String getDesc7() {
        return desc7;
    }

    public void setDesc7(String desc7) {
        this.desc7 = desc7;
    }

    public String getDesc8() {
        return desc8;
    }

    public void setDesc8(String desc8) {
        this.desc8 = desc8;
    }

    public String getDesc9() {
        return desc9;
    }

    public void setDesc9(String desc9) {
        this.desc9 = desc9;
    }

    public String getDesc13() {
        return desc13;
    }

    public void setDesc13(String desc13) {
        this.desc13 = desc13;
    }

    public String getDesc15() {
        return desc15;
    }

    public void setDesc15(String desc15) {
        this.desc15 = desc15;
    }

    public String getDesc35() {
        return desc35;
    }

    public void setDesc35(String desc35) {
        this.desc35 = desc35;
    }

    public String getDesc36() {
        return desc36;
    }

    public void setDesc36(String desc36) {
        this.desc36 = desc36;
    }

    public String getDesc37() {
        return desc37;
    }

    public void setDesc37(String desc37) {
        this.desc37 = desc37;
    }

    public String getDesc38() {
        return desc38;
    }

    public void setDesc38(String desc38) {
        this.desc38 = desc38;
    }

    public String getDesc40() {
        return desc40;
    }

    public void setDesc40(String desc40) {
        this.desc40 = desc40;
    }

    public double getCoord1() {
        return coord1;
    }

    public void setCoord1(double coord1) {
        this.coord1 = coord1;
    }

    public double getCoord2() {
        return coord2;
    }

    public void setCoord2(double coord2) {
        this.coord2 = coord2;
    }

    public String getPicUrl7() {
        return picUrl7;
    }

    public void setPicUrl7(String picUrl7) {
        this.picUrl7 = picUrl7;
    }

    public String getPicUrl8() {
        return picUrl8;
    }

    public void setPicUrl8(String picUrl8) {
        this.picUrl8 = picUrl8;
    }

    public String getPicUrl9() {
        return picUrl9;
    }

    public void setPicUrl9(String picUrl9) {
        this.picUrl9 = picUrl9;
    }

    public String getPicUrl10() {
        return picUrl10;
    }

    public void setPicUrl10(String picUrl10) {
        this.picUrl10 = picUrl10;
    }

    public String getPicUrl11() {
        return picUrl11;
    }

    public void setPicUrl11(String picUrl11) {
        this.picUrl11 = picUrl11;
    }

    public List<ShopCoupon> getScList() {
        return scList;
    }

    public void setScList(List<ShopCoupon> scList) {
        this.scList = scList;
    }

    public int getShopType() {
        return shopType;
    }

    public void setShopType(int shopType) {
        this.shopType = shopType;
    }

    public List<Map<String, Object>> getNowGoList() {
        return nowGoList;
    }

    public void setNowGoList(List<Map<String, Object>> nowGoList) {
        this.nowGoList = nowGoList;

    }

    public String getUuid2() {
        return uuid2;
    }

    public void setUuid2(String uuid2) {
        this.uuid2 = uuid2;
    }

    private List<Map<String,Object>> couponList2;

    public List<Map<String, Object>> getCouponList2() {
        return couponList2;
    }

    public void setCouponList2(List<Map<String, Object>> couponList2) {
        this.couponList2 = couponList2;
    }

    public List<Recruit> getRecruitList() {
        return recruitList;
    }

    public void setRecruitList(List<Recruit> recruitList) {
        this.recruitList = recruitList;
    }

}
