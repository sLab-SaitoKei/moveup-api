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

public class ShopBridalDetailJsonParse extends BaseJsonParse {

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

    private String desc10;

    private String desc11;

    private String desc12;

    private String desc13;

    private String desc14;

    private String desc15;

    private String desc16;

    private String desc17;

    private String desc18;

    private String desc19;

    private String desc20;

    private String desc21;

    private String desc22;

    private String desc23;

    private String desc24;

    private String desc25;

    private String desc26;

    private String desc27;

    private String desc28;

    private String desc29;

    private String desc30;

    private String desc31;

    private String desc32;

    private String desc33;

    private String desc34;

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

    private String contactTime;

    private int foodPrice;

    private int drinkPrice;

    private int corkagePrice;

    private int headCount;

    private String shopOpenTime;

    private String shopCloseTime;

    private double coord1;

    private double coord2;

    private String uuid2;

    private List<Recruit> recruitList;

    private List<ShopNow5JsonParse> now5;

    private List<Map<String,Object>> couponList;

    private List<Map<String,Object>> nowGoList;

    private List<Map<String,String>> contentList;

    private List<ShopCoupon> scList;

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

    public String getDesc12() {
        return desc12;
    }

    public void setDesc12(String desc12) {
        this.desc12 = desc12;
    }

    public String getDesc13() {
        return desc13;
    }

    public void setDesc13(String desc13) {
        this.desc13 = desc13;
    }

    public String getDesc40() {
        return desc40;
    }

    public void setDesc40(String desc40) {
        this.desc40 = desc40;
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

    public void setNow5(List<ShopNow5JsonParse> now5) {
        this.now5 = now5;
    }

    public List<Map<String,Object>> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<Map<String,Object>> couponList) {
        this.couponList = couponList;
    }

    public String getContactTime() {
        return contactTime;
    }

    public void setContactTime(String contactTime) {
        this.contactTime = contactTime;
    }

    public int getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(int foodPrice) {
        this.foodPrice = foodPrice;
    }

    public int getDrinkPrice() {
        return drinkPrice;
    }

    public void setDrinkPrice(int drinkPrice) {
        this.drinkPrice = drinkPrice;
    }

    public int getCorkagePrice() {
        return corkagePrice;
    }

    public void setCorkagePrice(int corkagePrice) {
        this.corkagePrice = corkagePrice;
    }

    public int getHeadCount() {
        return headCount;
    }

    public void setHeadCount(int headCount) {
        this.headCount = headCount;
    }

    public List<Map<String, String>> getContentList() {
        return contentList;
    }

    public void setContentList(List<Map<String, String>> contentList) {
        this.contentList = contentList;
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

    public String getDesc14() {
        return desc14;
    }

    public void setDesc14(String desc14) {
        this.desc14 = desc14;
    }

    public String getDesc15() {
        return desc15;
    }

    public void setDesc15(String desc15) {
        this.desc15 = desc15;
    }

    public String getDesc16() {
        return desc16;
    }

    public void setDesc16(String desc16) {
        this.desc16 = desc16;
    }

    public String getDesc17() {
        return desc17;
    }

    public void setDesc17(String desc17) {
        this.desc17 = desc17;
    }

    public String getDesc18() {
        return desc18;
    }

    public void setDesc18(String desc18) {
        this.desc18 = desc18;
    }

    public String getDesc19() {
        return desc19;
    }

    public void setDesc19(String desc19) {
        this.desc19 = desc19;
    }

    public String getDesc20() {
        return desc20;
    }

    public void setDesc20(String desc20) {
        this.desc20 = desc20;
    }

    public String getDesc21() {
        return desc21;
    }

    public void setDesc21(String desc21) {
        this.desc21 = desc21;
    }

    public String getDesc22() {
        return desc22;
    }

    public void setDesc22(String desc22) {
        this.desc22 = desc22;
    }

    public String getDesc23() {
        return desc23;
    }

    public void setDesc23(String desc23) {
        this.desc23 = desc23;
    }

    public String getDesc24() {
        return desc24;
    }

    public void setDesc24(String desc24) {
        this.desc24 = desc24;
    }

    public String getDesc25() {
        return desc25;
    }

    public void setDesc25(String desc25) {
        this.desc25 = desc25;
    }

    public String getDesc26() {
        return desc26;
    }

    public void setDesc26(String desc26) {
        this.desc26 = desc26;
    }

    public String getDesc27() {
        return desc27;
    }

    public void setDesc27(String desc27) {
        this.desc27 = desc27;
    }

    public String getDesc28() {
        return desc28;
    }

    public void setDesc28(String desc28) {
        this.desc28 = desc28;
    }

    public String getDesc29() {
        return desc29;
    }

    public void setDesc29(String desc29) {
        this.desc29 = desc29;
    }

    public String getDesc30() {
        return desc30;
    }

    public void setDesc30(String desc30) {
        this.desc30 = desc30;
    }

    public String getDesc31() {
        return desc31;
    }

    public void setDesc31(String desc31) {
        this.desc31 = desc31;
    }

    public String getDesc32() {
        return desc32;
    }

    public void setDesc32(String desc32) {
        this.desc32 = desc32;
    }

    public String getDesc33() {
        return desc33;
    }

    public void setDesc33(String desc33) {
        this.desc33 = desc33;
    }

    public String getDesc34() {
        return desc34;
    }

    public void setDesc34(String desc34) {
        this.desc34 = desc34;
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

    public String getDesc39() {
        return desc39;
    }

    public void setDesc39(String desc39) {
        this.desc39 = desc39;
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

    public List<ShopNow5JsonParse> getNow5() {
        return now5;
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

    private List<Map<String,Object>> couponList2;

    public List<Map<String, Object>> getCouponList2() {
        return couponList2;
    }

    public void setCouponList2(List<Map<String, Object>> couponList2) {
        this.couponList2 = couponList2;
    }

    public String getUuid2() {
        return uuid2;
    }

    public void setUuid2(String uuid2) {
        this.uuid2 = uuid2;
    }

    public List<Recruit> getRecruitList() {
        return recruitList;
    }

    public void setRecruitList(List<Recruit> recruitList) {
        this.recruitList = recruitList;
    }

    public String getPicUrl12() {
        return picUrl12;
    }

    public void setPicUrl12(String picUrl12) {
        this.picUrl12 = picUrl12;
    }

}
