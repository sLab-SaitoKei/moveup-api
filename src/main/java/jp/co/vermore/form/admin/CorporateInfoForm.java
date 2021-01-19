package jp.co.vermore.form.admin;

import jp.co.vermore.common.mvc.BaseForm;
import jp.co.vermore.entity.AdminCustomer;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * CorporateInfoForm
 * Created by wubin.
 *
 * DateTime: 2018/04/17 16:35
 * Copyright: sLab, Corp
 */

public class CorporateInfoForm extends BaseForm {

    private Long id;

    private Long shopId;

    private Long role;

    private AdminCustomer customer;

    private int adminShop;

    private String searchId;

    private String mail;

    private String uuid;

    private String loginName;

    private Integer shopType;

    private Long shopListId;

    private String name;

    private String excerpt;

    private int area;

    private int station;

    private int firstMenu;

    private int walkTime;

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

    private String coordinate1;

    private String coordinate2;

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

    private String tel;

    private String address;

    private String category;

    private String publishStart;

    private String publishEnd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getShopListId() {
        return shopListId;
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

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle1(String title) {
        this.title = title;
    }

    public String getDesc1() {
        return desc1;
    }

    public void setDesc1(String desc1) {
        this.desc1 = desc1;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getShopType() {
        return shopType;
    }

    public void setShopType(Integer shopType) {
        this.shopType = shopType;
    }

    public void setShopListId(Long shopListId) {
        this.shopListId = shopListId;
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

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getStation() {
        return station;
    }

    public void setStation(int station) {
        this.station = station;
    }

    public int getFirstMenu() {
        return firstMenu;
    }

    public void setFirstMenu(int firstMenu) {
        this.firstMenu = firstMenu;
    }

    public int getWalkTime() {
        return walkTime;
    }

    public void setWalkTime(int walkTime) {
        this.walkTime = walkTime;
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

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public MultipartFile getPicFile() {
        return picFile;
    }

    public void setPicFile(MultipartFile picFile) {
        this.picFile = picFile;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
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
