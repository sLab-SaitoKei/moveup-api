package jp.co.vermore.form.admin;

import jp.co.vermore.common.mvc.BaseForm;
import jp.co.vermore.entity.AdminCustomer;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * Created by wubin.
 * <p>
 * DateTime: 2018/03/03 11:13
 * Copyright: sLab, Corp
 */
public class RecruitForm extends BaseForm {

    private Long id;

    private String uuid;

    private Long recruitId;

    private Long shopId;

    private Integer shopType;

    private String recruitName;

    private String companyName;

    private String shopUuid;

    private String spot;

    private String workDay;

    private String executive;

    private String acceptance;

    private String title1;

    private String title2;

    private MultipartFile picFile;

    private String picUrl;

    private Integer area;

    private Long role;

    private AdminCustomer customer;

    private int adminShop;

    private String searchId;

    private String mail;

    private String loginName;

    private MultipartFile picFile1;

    private String picUrl1;

    private MultipartFile picFile2;

    private String picUrl2;

    private MultipartFile picFile3;

    private String picUrl3;

    private MultipartFile picFile4;

    private String picUrl4;

    private MultipartFile picFile5;

    private String picUrl5;

    private MultipartFile picFile6;

    private MultipartFile picFile7;

    private MultipartFile picFile8;

    private MultipartFile picFile9;

    private MultipartFile picFile10;

    private String picUrl6;

    private String picUrl7;

    private String picUrl8;

    private String picUrl9;

    private String picUrl10;

    private String videoUrl1;

    private String content;

    private String salary;

    private String address;

    private String station;

    private String workingTime;

    private String workingTimeEnd;

    private String salaryFull;

    private String capacity;

    private String workingDate;

    private String vacation;

    private String treatment;

    private String desc;

    private String tel;

    private String publishStart;

    private String publishEnd;

    private Date createDatetime;

    private Date updateDatetime;

    private Boolean delFlg;

    private String note;

    private List<Integer> modeList;

    private List<Integer> detailList;

    private String workingTimeStart;

    private List<Integer> featureList;

    private List<Integer> treatmentList;//待遇

    private List<Integer> workPeriod;//勤務時間

    private List<Integer> workTimeList;//勤務期間

    private List<Integer>  capacityList;//経験・資格

    private List<Integer> workWayList;//働き方

    private List<Integer> allowanceSpecialList;//給与の特徴

    private List<Integer> workEnvironmentList;//職場環境

    private int carerr;

    private String uuid1;

    public String getUuid1() {
        return uuid1;
    }

    public void setUuid1(String uuid1) {
        this.uuid1 = uuid1;
    }

    public int getCarerr() {
        return carerr;
    }

    public void setCarerr(int carerr) {
        this.carerr = carerr;
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

    public String getShopUuid() {
        return shopUuid;
    }

    public void setShopUuid(String shopUuid) {
        this.shopUuid = shopUuid;
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

    public MultipartFile getPicFile10() {
        return picFile10;
    }

    public void setPicFile10(MultipartFile picFile10) {
        this.picFile10 = picFile10;
    }

    public String getPicUrl10() {
        return picUrl10;
    }

    public void setPicUrl10(String picUrl10) {
        this.picUrl10 = picUrl10;
    }

    public String getPicUrl1() {
        return picUrl1;
    }

    public void setPicUrl1(String picUrl1) {
        this.picUrl1 = picUrl1;
    }

    public MultipartFile getPicFile1() {
        return picFile1;
    }

    public void setPicFile1(MultipartFile picFile1) {
        this.picFile1 = picFile1;
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

    public void setWorkingTime(String workingTime) {
        this.workingTime = workingTime;
    }

    public String getSalaryFull() {
        return salaryFull;
    }

    public void setSalaryFull(String salaryFull) {
        this.salaryFull = salaryFull;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getWorkingDate() {
        return workingDate;
    }

    public void setWorkingDate(String workingDate) {
        this.workingDate = workingDate;
    }

    public String getVacation() {
        return vacation;
    }

    public void setVacation(String vacation) {
        this.vacation = vacation;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
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

    public List<Integer> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<Integer> detailList) {
        this.detailList = detailList;
    }

    public List<Integer> getModeList() {
        return modeList;
    }

    public void setModeList(List<Integer> modeList) {
        this.modeList = modeList;
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

    public List<Integer> getFeatureList() {
        return featureList;
    }

    public void setFeatureList(List<Integer> featureList) {
        this.featureList = featureList;
    }

    public List<Integer> getTreatmentList() {
        return treatmentList;
    }

    public void setTreatmentList(List<Integer> treatmentList) {
        this.treatmentList = treatmentList;
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

    public MultipartFile getPicFile() {
        return picFile;
    }

    public void setPicFile(MultipartFile picFile) {
        this.picFile = picFile;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public MultipartFile getPicFile6() {
        return picFile6;
    }

    public void setPicFile6(MultipartFile picFile6) {
        this.picFile6 = picFile6;
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

    public Long getRole() {
        return role;
    }

    public void setRole(Long role) {
        this.role = role;
    }

    public AdminCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(AdminCustomer customer) {
        this.customer = customer;
    }

    public int getAdminShop() {
        return adminShop;
    }

    public void setAdminShop(int adminShop) {
        this.adminShop = adminShop;
    }

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Integer getShopType() {
        return shopType;
    }

    public void setShopType(Integer shopType) {
        this.shopType = shopType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public List<Integer> getWorkPeriod() {
        return workPeriod;
    }

    public void setWorkPeriod(List<Integer> workPeriod) {
        this.workPeriod = workPeriod;
    }

    public List<Integer> getWorkTimeList() {
        return workTimeList;
    }

    public void setWorkTimeList(List<Integer> workTimeList) {
        this.workTimeList = workTimeList;
    }

    public List<Integer> getCapacityList() {
        return capacityList;
    }

    public void setCapacityList(List<Integer> capacityList) {
        this.capacityList = capacityList;
    }

    public List<Integer> getWorkWayList() {
        return workWayList;
    }

    public void setWorkWayList(List<Integer> workWayList) {
        this.workWayList = workWayList;
    }

    public List<Integer> getAllowanceSpecialList() {
        return allowanceSpecialList;
    }

    public void setAllowanceSpecialList(List<Integer> allowanceSpecialList) {
        this.allowanceSpecialList = allowanceSpecialList;
    }

    public List<Integer> getWorkEnvironmentList() {
        return workEnvironmentList;
    }

    public void setWorkEnvironmentList(List<Integer> workEnvironmentList) {
        this.workEnvironmentList = workEnvironmentList;
    }

}
