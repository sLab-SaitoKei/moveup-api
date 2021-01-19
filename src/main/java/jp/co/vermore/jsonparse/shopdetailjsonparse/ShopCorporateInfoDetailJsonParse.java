package jp.co.vermore.jsonparse.shopdetailjsonparse;

import jp.co.vermore.common.mvc.BaseJsonParse;
import jp.co.vermore.entity.Pic;
import jp.co.vermore.entity.Recruit;

import java.util.List;
import java.util.Map;

/**
 * ShopDetailJsonParse
 * Created by wubin.
 * <p>
 * DateTime: 2018/03/12 15:00
 * Copyright: sLab, Corp
 */

public class ShopCorporateInfoDetailJsonParse extends BaseJsonParse {

    private Long id;

    private Long shopListID;

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

    private String desc10;

    private String desc11;

    private String picUrl12;

    private String tel;

    private String address;

    private String station;

    private int walkTime;

    private Double coordinate1;

    private Double coordinate2;

    private List<Pic> picList;

    private Boolean favorite;

    private String uuid2;

    private int shopType;

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

    private List<Recruit> recruitList;

    private List<Map<String,Object>> couponList;

    private List<Map<String,Object>> couponList2;

    private List<Map<String,Object>> nowGoList;

    public Long getShopListID() {
        return shopListID;
    }

    public void setShopListID(Long shopListID) {
        this.shopListID = shopListID;
    }

    public List<Pic> getPicList() {
        return picList;
    }

    public void setPicList(List<Pic> picList) {
        this.picList = picList;
    }

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

    public String getDesc10() {
        return desc10;
    }

    public void setDesc10(String desc10) {
        this.desc10 = desc10;
    }

    public String getDesc11() {
        return desc11;
    }

    public void setDesc11(String desc11) {
        this.desc11 = desc11;
    }

    public Double getCoordinate1() {
        return coordinate1;
    }

    public void setCoordinate1(Double coordinate1) {
        this.coordinate1 = coordinate1;
    }

    public Double getCoordinate2() {
        return coordinate2;
    }

    public void setCoordinate2(Double coordinate2) {
        this.coordinate2 = coordinate2;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public String getUuid2() {
        return uuid2;
    }

    public void setUuid2(String uuid2) {
        this.uuid2 = uuid2;
    }

    public int getShopType() {
        return shopType;
    }

    public void setShopType(int shopType) {
        this.shopType = shopType;
    }

    public List<Map<String, Object>> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<Map<String, Object>> couponList) {
        this.couponList = couponList;
    }

    public List<Map<String, Object>> getNowGoList() {
        return nowGoList;
    }

    public void setNowGoList(List<Map<String, Object>> nowGoList) {
        this.nowGoList = nowGoList;
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

    public String getPicUrl12() {
        return picUrl12;
    }

    public void setPicUrl12(String picUrl12) {
        this.picUrl12 = picUrl12;
    }

    public List<Recruit> getRecruitList() {
        return recruitList;
    }

    public void setRecruitList(List<Recruit> recruitList) {
        this.recruitList = recruitList;
    }

    public List<Map<String, Object>> getCouponList2() {
        return couponList2;
    }

    public void setCouponList2(List<Map<String, Object>> couponList2) {
        this.couponList2 = couponList2;
    }

}
