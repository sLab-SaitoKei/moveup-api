package jp.co.vermore.form.admin;

import jp.co.vermore.common.mvc.BaseForm;
import jp.co.vermore.entity.AdminCustomer;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * PlaceForm
 * Created by wubin.
 *
 * DateTime: 2018/04/18 16:10
 * Copyright: sLab, Corp
 */

public class PlaceForm extends BaseForm {

    private AdminCustomer customer;

    private Long role;

    private int adminShop;

    private Long searchId;

    private String mail;

    private Long id;

    private String uuid;

    private String name;

    private String introduce;

    private String address;

    private Integer area;

    private String picUrl;

    private String flicUrl;

    private Long placeListId;

    private String location;

    private String station;

    private String walk;

    private String time;

    private int restRadio;

    private String holiday;

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

    private String videoUrl;

    private List<Integer> restWeeksList;

    private List<Integer> restWeekDaysList;

    private List<Integer> restDateList;

    private List<ShopRestForm> stock;

    private List<Integer> restDates;

    private List<Integer> restWeek;

    private List<Integer> restWeekDay;

    private MultipartFile picFile;

    private MultipartFile picFile1;

    private MultipartFile picFile2;

    private MultipartFile picFile3;

    private MultipartFile picFile4;

    private MultipartFile picFile5;

    private MultipartFile picFile6;

    private MultipartFile picFile7;

    private MultipartFile picFile8;

    private MultipartFile picFile9;

    private MultipartFile picFile10;

    private MultipartFile picFile11;

    private MultipartFile picFile12;

    private String title;

    private String desc;

    private String tel;

    private String coordinate1;

    private String coordinate2;

    private String publishStart;

    private String publishEnd;

    public String getPublishStart() {
        return publishStart;
    }

    public void setPublishStart(String publishStart) {
        this.publishStart = publishStart;
    }

    public String getPublishEnd() {
        return publishEnd;
    }

    public void setPublishEnd(String publishEnd) {
        this.publishEnd = publishEnd;
    }

    public String getCoordinate1() {
        return coordinate1;
    }

    public void setCoordinate1(String coordinate1) {
        this.coordinate1 = coordinate1;
    }

    public String getCoordinate2() {
        return coordinate2;
    }

    public void setCoordinate2(String coordinate2) {
        this.coordinate2 = coordinate2;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getFlicUrl() {
        return flicUrl;
    }

    public void setFlicUrl(String flicUrl) {
        this.flicUrl = flicUrl;
    }

    public Long getPlaceListId() {
        return placeListId;
    }

    public void setPlaceListId(Long placeListId) {
        this.placeListId = placeListId;
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

    public MultipartFile getPicFile() {
        return picFile;
    }

    public void setPicFile(MultipartFile picFile) {
        this.picFile = picFile;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public MultipartFile getPicFile1() {
        return picFile1;
    }

    public void setPicFile1(MultipartFile picFile1) {
        this.picFile1 = picFile1;
    }

    public MultipartFile getPicFile2() {
        return picFile2;
    }

    public void setPicFile2(MultipartFile picFile2) {
        this.picFile2 = picFile2;
    }

    public MultipartFile getPicFile3() {
        return picFile3;
    }

    public void setPicFile3(MultipartFile picFile3) {
        this.picFile3 = picFile3;
    }

    public MultipartFile getPicFile4() {
        return picFile4;
    }

    public void setPicFile4(MultipartFile picFile4) {
        this.picFile4 = picFile4;
    }

    public MultipartFile getPicFile5() {
        return picFile5;
    }

    public void setPicFile5(MultipartFile picFile5) {
        this.picFile5 = picFile5;
    }

    public MultipartFile getPicFile6() {
        return picFile6;
    }

    public void setPicFile6(MultipartFile picFile6) {
        this.picFile6 = picFile6;
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

    public MultipartFile getPicFile7() {
        return picFile7;
    }

    public void setPicFile7(MultipartFile picFile7) {
        this.picFile7 = picFile7;
    }

    public MultipartFile getPicFile8() {
        return picFile8;
    }

    public void setPicFile8(MultipartFile picFile8) {
        this.picFile8 = picFile8;
    }

    public MultipartFile getPicFile9() {
        return picFile9;
    }

    public void setPicFile9(MultipartFile picFile9) {
        this.picFile9 = picFile9;
    }

    public MultipartFile getPicFile10() {
        return picFile10;
    }

    public void setPicFile10(MultipartFile picFile10) {
        this.picFile10 = picFile10;
    }

    public MultipartFile getPicFile11() {
        return picFile11;
    }

    public void setPicFile11(MultipartFile picFile11) {
        this.picFile11 = picFile11;
    }

    public List<Integer> getRestDates() {
        return restDates;
    }

    public void setRestDates(List<Integer> restDates) {
        this.restDates = restDates;
    }

    public List<Integer> getRestWeek() {
        return restWeek;
    }

    public void setRestWeek(List<Integer> restWeek) {
        this.restWeek = restWeek;
    }

    public List<Integer> getRestWeekDay() {
        return restWeekDay;
    }

    public void setRestWeekDay(List<Integer> restWeekDay) {
        this.restWeekDay = restWeekDay;
    }

    public List<ShopRestForm> getStock() {
        return stock;
    }

    public void setStock(List<ShopRestForm> stock) {
        this.stock = stock;
    }

    public List<Integer> getRestWeeksList() {
        return restWeeksList;
    }

    public void setRestWeeksList(List<Integer> restWeeksList) {
        this.restWeeksList = restWeeksList;
    }

    public List<Integer> getRestWeekDaysList() {
        return restWeekDaysList;
    }

    public void setRestWeekDaysList(List<Integer> restWeekDaysList) {
        this.restWeekDaysList = restWeekDaysList;
    }

    public List<Integer> getRestDateList() {
        return restDateList;
    }

    public void setRestDateList(List<Integer> restDateList) {
        this.restDateList = restDateList;
    }

    public int getRestRadio() {
        return restRadio;
    }

    public void setRestRadio(int restRadio) {
        this.restRadio = restRadio;
    }

    public AdminCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(AdminCustomer customer) {
        this.customer = customer;
    }

    public Long getRole() {
        return role;
    }

    public void setRole(Long role) {
        this.role = role;
    }

    public int getAdminShop() {
        return adminShop;
    }

    public void setAdminShop(int adminShop) {
        this.adminShop = adminShop;
    }

    public Long getSearchId() {
        return searchId;
    }

    public void setSearchId(Long searchId) {
        this.searchId = searchId;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPicUrl12() {
        return picUrl12;
    }

    public void setPicUrl12(String picUrl12) {
        this.picUrl12 = picUrl12;
    }

    public MultipartFile getPicFile12() {
        return picFile12;
    }

    public void setPicFile12(MultipartFile picFile12) {
        this.picFile12 = picFile12;
    }

}
