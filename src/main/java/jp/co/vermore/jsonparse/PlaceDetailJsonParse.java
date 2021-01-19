package jp.co.vermore.jsonparse;

import jp.co.vermore.common.mvc.BaseJsonParse;
import jp.co.vermore.entity.Pic;
import jp.co.vermore.entity.Place;

import java.util.List;

public class PlaceDetailJsonParse extends BaseJsonParse {

    private Long placeListId;

    private String name;

    private String uuid2;

    private String location;

    private Boolean favorite;

    private String station;

    private List<PlaceJsonParse> placeList;

    private List<Pic> picList;

    private List<Place> placeList2;

    private String walk;

    private String time;

    private String holiday;

    private String picUrl;

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

    private String picUrl12;

    private String flicUrl;

    private String videoUrl;

    private String title1;

    private String desc1;

    private String tel;

    private String address;

    private double coordinate1;

    private double coordinate2;

    public String getUserSetting() {
        return userSetting;
    }

    public void setUserSetting(String userSetting) {
        this.userSetting = userSetting;
    }

    private String userSetting;

    public int getFavType() {
        return favType;
    }

    public void setFavType(int favType) {
        this.favType = favType;
    }

    private int favType;

    public double getCoordinate1() {
        return coordinate1;
    }

    public void setCoordinate1(double coordinate1) {
        this.coordinate1 = coordinate1;
    }

    public double getCoordinate2() {
        return coordinate2;
    }

    public void setCoordinate2(double coordinate2) {
        this.coordinate2 = coordinate2;
    }

    public Long getPlaceListId() {
        return placeListId;
    }

    public void setPlaceListId(Long placeListId) {
        this.placeListId = placeListId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getWalk() {
        return walk;
    }

    public void setWalk(String walk) {
        this.walk = walk;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHoliday() {
        return holiday;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
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

    public String getFlicUrl() {
        return flicUrl;
    }

    public void setFlicUrl(String flicUrl) {
        this.flicUrl = flicUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public List<Place> getPlaceList2() {
        return placeList2;
    }

    public void setPlaceList2(List<Place> placeList2) {
        this.placeList2 = placeList2;
    }

    public void setPicList(List<Pic> picList) {
        this.picList = picList;
    }

    public List<Pic> getPicList() {
        return picList;
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

    public List<PlaceJsonParse> getPlaceList() {
        return placeList;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public void setPlaceList(List<PlaceJsonParse> placeList) {
        this.placeList = placeList;
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

    public String getUuid2() {
        return uuid2;
    }

    public void setUuid2(String uuid2) {
        this.uuid2 = uuid2;
    }

}