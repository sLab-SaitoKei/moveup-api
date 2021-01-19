package jp.co.vermore.jsonparse;

import jp.co.vermore.common.mvc.BaseJsonParse;
import jp.co.vermore.entity.Pic;
import jp.co.vermore.entity.Recruit;

import java.util.Date;
import java.util.List;

/**
 * NewsDetailJsonParse
 * Created by wubin.
 *
 * DateTime: 2018/03/13 16:52
 * Copyright: sLab, Corp
 */

public class RecruitDetailJsonParse extends BaseJsonParse {

    private Long id;

    private boolean favorite;

    private Long recruitId;

    private Long shopId;

    private String shopName;

    private String recruitName;

    private String title1;

    private String title2;

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

    private String videoUrl1;

    private String content;

    private String salary;

    private String address;

    private String station;

    private String workingTime;

    private String salaryFull;

    private String capacity;

    private String workingDate;

    private String vacation;

    private String treatment;

    private String desc;

    private String tel;

    private String spot;

    private String workDay;

    private String workPeriod;

    private String executive;

    private String acceptance;

    private Date publishStart;

    private Date publishEnd;

    private Date createDatetime;

    private Date updateDatetime;

    private Boolean delFlg;

    private String note;

    /**
     * 雇用形態
     */
    private List<String> modeList;

    /**
     * こだわり
     */
    private List<String> detailList;

    private String workingTimeStart;

    private String workingTimeEnd;

    private List<String> featureList;

    private List<String> treatmentList;

    private List<String> workPeriodList;

    private List<String> workTimeList;

    private List<String> capacityList;

    private List<String> workWayList;

    private List<String> specialList;

    private List<String> environmentList;

    private List<String> treatmentNewList;

    private List<Pic> picList;

    private List<Recruit> recruitList;

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

    public Long getRecruitId() {
        return recruitId;
    }

    public void setRecruitId(Long recruitId) {
        this.recruitId = recruitId;
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

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
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

    public String getVideoUrl1() {
        return videoUrl1;
    }

    public void setVideoUrl1(String videoUrl1) {
        this.videoUrl1 = videoUrl1;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column recruit_detail.working_time
     *
     * @param workingTime the value for recruit_detail.working_time
     *
     * @mbggenerated Thu Mar 15 15:19:21 JST 2018
     */
    public void setWorkingTime(String workingTime) {
        this.workingTime = workingTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column recruit_detail.salary_full
     *
     * @return the value of recruit_detail.salary_full
     *
     * @mbggenerated Thu Mar 15 15:19:21 JST 2018
     */
    public String getSalaryFull() {
        return salaryFull;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column recruit_detail.salary_full
     *
     * @param salaryFull the value for recruit_detail.salary_full
     *
     * @mbggenerated Thu Mar 15 15:19:21 JST 2018
     */
    public void setSalaryFull(String salaryFull) {
        this.salaryFull = salaryFull;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column recruit_detail.capacity
     *
     * @return the value of recruit_detail.capacity
     *
     * @mbggenerated Thu Mar 15 15:19:21 JST 2018
     */
    public String getCapacity() {
        return capacity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column recruit_detail.capacity
     *
     * @param capacity the value for recruit_detail.capacity
     *
     * @mbggenerated Thu Mar 15 15:19:21 JST 2018
     */
    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column recruit_detail.working_date
     *
     * @return the value of recruit_detail.working_date
     *
     * @mbggenerated Thu Mar 15 15:19:21 JST 2018
     */
    public String getWorkingDate() {
        return workingDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column recruit_detail.working_date
     *
     * @param workingDate the value for recruit_detail.working_date
     *
     * @mbggenerated Thu Mar 15 15:19:21 JST 2018
     */
    public void setWorkingDate(String workingDate) {
        this.workingDate = workingDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column recruit_detail.vacation
     *
     * @return the value of recruit_detail.vacation
     *
     * @mbggenerated Thu Mar 15 15:19:21 JST 2018
     */
    public String getVacation() {
        return vacation;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column recruit_detail.vacation
     *
     * @param vacation the value for recruit_detail.vacation
     *
     * @mbggenerated Thu Mar 15 15:19:21 JST 2018
     */
    public void setVacation(String vacation) {
        this.vacation = vacation;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column recruit_detail.treatment
     *
     * @return the value of recruit_detail.treatment
     *
     * @mbggenerated Thu Mar 15 15:19:21 JST 2018
     */
    public String getTreatment() {
        return treatment;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column recruit_detail.treatment
     *
     * @param treatment the value for recruit_detail.treatment
     *
     * @mbggenerated Thu Mar 15 15:19:21 JST 2018
     */
    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column recruit_detail.desc
     *
     * @return the value of recruit_detail.desc
     *
     * @mbggenerated Thu Mar 15 15:19:21 JST 2018
     */
    public String getDesc() {
        return desc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column recruit_detail.desc
     *
     * @param desc the value for recruit_detail.desc
     *
     * @mbggenerated Thu Mar 15 15:19:21 JST 2018
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column recruit_detail.tel
     *
     * @return the value of recruit_detail.tel
     *
     * @mbggenerated Thu Mar 15 15:19:21 JST 2018
     */
    public String getTel() {
        return tel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column recruit_detail.tel
     *
     * @param tel the value for recruit_detail.tel
     *
     * @mbggenerated Thu Mar 15 15:19:21 JST 2018
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column recruit_detail.publish_start
     *
     * @return the value of recruit_detail.publish_start
     *
     * @mbggenerated Thu Mar 15 15:19:21 JST 2018
     */
    public Date getPublishStart() {
        return publishStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column recruit_detail.publish_start
     *
     * @param publishStart the value for recruit_detail.publish_start
     *
     * @mbggenerated Thu Mar 15 15:19:21 JST 2018
     */
    public void setPublishStart(Date publishStart) {
        this.publishStart = publishStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column recruit_detail.publish_end
     *
     * @return the value of recruit_detail.publish_end
     *
     * @mbggenerated Thu Mar 15 15:19:21 JST 2018
     */
    public Date getPublishEnd() {
        return publishEnd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column recruit_detail.publish_end
     *
     * @param publishEnd the value for recruit_detail.publish_end
     *
     * @mbggenerated Thu Mar 15 15:19:21 JST 2018
     */
    public void setPublishEnd(Date publishEnd) {
        this.publishEnd = publishEnd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column recruit_detail.create_datetime
     *
     * @return the value of recruit_detail.create_datetime
     *
     * @mbggenerated Thu Mar 15 15:19:21 JST 2018
     */
    public Date getCreateDatetime() {
        return createDatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column recruit_detail.create_datetime
     *
     * @param createDatetime the value for recruit_detail.create_datetime
     *
     * @mbggenerated Thu Mar 15 15:19:21 JST 2018
     */
    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column recruit_detail.update_datetime
     *
     * @return the value of recruit_detail.update_datetime
     *
     * @mbggenerated Thu Mar 15 15:19:21 JST 2018
     */
    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column recruit_detail.update_datetime
     *
     * @param updateDatetime the value for recruit_detail.update_datetime
     *
     * @mbggenerated Thu Mar 15 15:19:21 JST 2018
     */
    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column recruit_detail.del_flg
     *
     * @return the value of recruit_detail.del_flg
     *
     * @mbggenerated Thu Mar 15 15:19:21 JST 2018
     */
    public Boolean getDelFlg() {
        return delFlg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column recruit_detail.del_flg
     *
     * @param delFlg the value for recruit_detail.del_flg
     *
     * @mbggenerated Thu Mar 15 15:19:21 JST 2018
     */
    public void setDelFlg(Boolean delFlg) {
        this.delFlg = delFlg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column recruit_detail.note
     *
     * @return the value of recruit_detail.note
     *
     * @mbggenerated Thu Mar 15 15:19:21 JST 2018
     */
    public String getNote() {
        return note;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column recruit_detail.note
     *
     * @param note the value for recruit_detail.note
     *
     * @mbggenerated Thu Mar 15 15:19:21 JST 2018
     */
    public void setNote(String note) {
        this.note = note;
    }

    public List<String> getModeList() {
        return modeList;
    }

    public void setModeList(List<String> modeList) {
        this.modeList = modeList;
    }

    public List<String> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<String> detailList) {
        this.detailList = detailList;
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

    public List<String> getFeatureList() {
        return featureList;
    }

    public void setFeatureList(List<String> featureList) {
        this.featureList = featureList;
    }

    public List<String> getTreatmentList() {
        return treatmentList;
    }

    public void setTreatmentList(List<String> treatmentList) {
        this.treatmentList = treatmentList;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public List<Recruit> getRecruitList() {
        return recruitList;
    }

    public void setRecruitList(List<Recruit> recruitList) {
        this.recruitList = recruitList;
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

    public List<String> getWorkPeriodList() {
        return workPeriodList;
    }

    public void setWorkPeriodList(List<String> workPeriodList) {
        this.workPeriodList = workPeriodList;
    }

    public List<String> getWorkTimeList() {
        return workTimeList;
    }

    public void setWorkTimeList(List<String> workTimeList) {
        this.workTimeList = workTimeList;
    }

    public List<String> getCapacityList() {
        return capacityList;
    }

    public void setCapacityList(List<String> capacityList) {
        this.capacityList = capacityList;
    }

    public List<String> getWorkWayList() {
        return workWayList;
    }

    public void setWorkWayList(List<String> workWayList) {
        this.workWayList = workWayList;
    }

    public List<String> getSpecialList() {
        return specialList;
    }

    public void setSpecialList(List<String> specialList) {
        this.specialList = specialList;
    }

    public List<String> getEnvironmentList() {
        return environmentList;
    }

    public void setEnvironmentList(List<String> environmentList) {
        this.environmentList = environmentList;
    }

    public List<String> getTreatmentNewList() {
        return treatmentNewList;
    }

    public void setTreatmentNewList(List<String> treatmentNewList) {
        this.treatmentNewList = treatmentNewList;
    }

    public String getSpot() {
        return spot;
    }

    public void setSpot(String spot) {
        this.spot = spot;
    }

    public String getWorkDay() {
        return workDay;
    }

    public void setWorkDay(String workDay) {
        this.workDay = workDay;
    }

    public String getWorkPeriod() {
        return workPeriod;
    }

    public void setWorkPeriod(String workPeriod) {
        this.workPeriod = workPeriod;
    }

    public String getExecutive() {
        return executive;
    }

    public void setExecutive(String executive) {
        this.executive = executive;
    }

    public String getAcceptance() {
        return acceptance;
    }

    public void setAcceptance(String acceptance) {
        this.acceptance = acceptance;
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

}
