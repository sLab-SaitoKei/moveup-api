package jp.co.vermore.jsonparse;

import jp.co.vermore.common.mvc.BaseJsonParse;

import java.util.Date;
import java.util.List;

/**
 * NewsJsonParse
 * Created by wubin.
 * <p>
 * DateTime: 2018/03/07 13:25
 * Copyright: sLab, Corp
 */

public class RecruitJsonParse extends BaseJsonParse {

    private String uuid;

    private Long shopId;

    private boolean favorite;

    private String shopName;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    private String recruitName;

    private String title;

    private String picUrl1;

    private String salary;

    private String address;

    private String station;

    private String workingTime;

    private Date publishStart;

    private Date publishEnd;

    private Date createDatetime;

    private Date updateDatetime;

    private Boolean delFlg;

    private String note;

    /**
     * 雇用形態
     */
    private List<String> modes;

    /**
     * こだわり
     */
    private List<String> details;

    private List<String> career;

    private List<String> workPeriod;

    private List<String> workTime;

    private List<String> capacity;

    private List<String> workWay;

    private List<String> special;

    private List<String> environment;

    private List<String> treatmentNew;

    private String workingTimeStart;

    private String workingTimeEnd;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getRecruitName() {
        return recruitName;
    }

    public void setRecruitName(String recruitName) {
        this.recruitName = recruitName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicUrl1() {
        return picUrl1;
    }

    public void setPicUrl1(String picUrl1) {
        this.picUrl1 = picUrl1;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
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

    public String getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(String workingTime) {
        this.workingTime = workingTime;
    }

    public Date getPublishStart() {
        return publishStart;
    }

    public void setPublishStart(Date publishStart) {
        this.publishStart = publishStart;
    }

    public Date getPublishEnd() {
        return publishEnd;
    }

    public void setPublishEnd(Date publishEnd) {
        this.publishEnd = publishEnd;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public Boolean getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Boolean delFlg) {
        this.delFlg = delFlg;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<String> getModes() {
        return modes;
    }

    public void setModes(List<String> modes) {
        this.modes = modes;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    public String getWorkingTimeStart() {
        return workingTimeStart;
    }

    public void setWorkingTimeStart(String workingTimeStart) {
        this.workingTimeStart = workingTimeStart;
    }

    public String getWorkingTimeEnd() {
        return workingTimeEnd;
    }

    public void setWorkingTimeEnd(String workingTimeEnd) {
        this.workingTimeEnd = workingTimeEnd;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public List<String> getCareer() {
        return career;
    }

    public void setCareer(List<String> career) {
        this.career = career;
    }

    public List<String> getWorkPeriod() {
        return workPeriod;
    }

    public void setWorkPeriod(List<String> workPeriod) {
        this.workPeriod = workPeriod;
    }

    public List<String> getWorkTime() {
        return workTime;
    }

    public void setWorkTime(List<String> workTime) {
        this.workTime = workTime;
    }

    public List<String> getCapacity() {
        return capacity;
    }

    public void setCapacity(List<String> capacity) {
        this.capacity = capacity;
    }

    public List<String> getWorkWay() {
        return workWay;
    }

    public void setWorkWay(List<String> workWay) {
        this.workWay = workWay;
    }

    public List<String> getSpecial() {
        return special;
    }

    public void setSpecial(List<String> special) {
        this.special = special;
    }

    public List<String> getEnvironment() {
        return environment;
    }

    public void setEnvironment(List<String> environment) {
        this.environment = environment;
    }

    public List<String> getTreatmentNew() {
        return treatmentNew;
    }

    public void setTreatmentNew(List<String> treatmentNew) {
        this.treatmentNew = treatmentNew;
    }

}
