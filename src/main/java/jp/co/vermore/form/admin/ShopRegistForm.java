package jp.co.vermore.form.admin;

import jp.co.vermore.common.mvc.BaseForm;
import jp.co.vermore.entity.AdminCustomer;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * NewsRegistForm
 * Created by wubin.
 *
 * DateTime: 2018/03/20 15:54
 * Copyright: sLab, Corp
 */

public class ShopRegistForm extends BaseForm {

    private AdminCustomer customer;

    private int adminShop;

    private String searchId;

    private Long role;

    private String loginName;

    private String mail;

    private Integer privilege;

    private Long id;

    private Long shopId;

    private Long shopListId;

    private String coordinate1;

    private String coordinate2;

    private int now5;

    private String uuid;

    private String name;

    private String title;

    private Byte type;

    private String excerpt;

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

    private String pic360;

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

    private String flicUrl;

    private String videoUrl2;

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

    private String desc41;

    private String desc42;

    private String desc43;

    private String tel;

    private String videoStatus;

    private String flicStatus;

    private String address;

    private int nightPrice;

    private int walkTime;

    private int dayOpenTime;

    private int dayCloseTime;

    private int dayLastOrder;

    private int nightOpenTime;

    private int nightCloseTime;

    private int nightLastOrder;

    private int openTime;

    private int closeTime;

    private int lastOrder;

    private int dayPrice;

    private List<ShopRestForm> stock;

    private List<Integer> restDates;

    private List<Integer> restWeek;

    private List<Integer> restWeekDay;

    private List<Integer> restWeeksList;

    private List<Integer> restWeekDaysList;

    private List<Integer> restDateList;

    private int area;

    private int station;

    private int shopType;

    private int firstMenu;

    private int genre;

    private List<Integer> secondMenu;

    private int scene;

    private int object;

    private int ceremony;

    private int place;

    private int dayPriceLow;

    private int dayPriceHigh;

    private int nightPriceLow;

    private int nightPriceHigh;

    private int priceLow;

    private int priceHigh;

    private int averagePriceLow;

    private int averagePriceHigh;

    private int seat;

    private int massageTime;

    private int headCount;

    private int roomType;

    private int roomNum;

    private int contactTime;

    private int foodPrice;

    private int drinkPrice;

    private int corkagePrice;

    private int averagePrice;

    private int restRadio;

    private String publishStart;

    private String publishEnd;

    private List<Integer> areaList;

    private List<Integer> stationList;

    private List<Integer> firstMenuList;

    private List<Integer> secondMenuList;

    private List<Integer> dayPriceList;

    private List<Integer> nightPriceList;

    private List<Integer> openTimeList;

    private List<Integer> weekDayList;

    private List<Integer> characterList;

    private List<Integer> contentList;

    private List<Integer> brandList;

    private List<Integer> genreList;

    private List<Integer> sceneList;

    private List<Integer> objectList;

    public List<Integer> getObjectList() {
        return objectList;
    }

    public void setObjectList(List<Integer> objectList) {
        this.objectList = objectList;
    }

    public List<Integer> getGenreList() {
        return genreList;
    }

    public void setGenreList(List<Integer> genreList) {
        this.genreList = genreList;
    }

    public List<Integer> getSceneList() {
        return sceneList;
    }

    public void setSceneList(List<Integer> sceneList) {
        this.sceneList = sceneList;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getId() {
        return id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
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

    public void setShopListId(Long shopListId) {
        this.shopListId = shopListId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public int getGenre() {
        return genre;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
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

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
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

    public MultipartFile getPicFile() {
        return picFile;
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

    public void setPicFile(MultipartFile picFile) {
        this.picFile = picFile;
    }

    public List<Integer> getRestWeekDay() {
        return restWeekDay;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoUrl2() {
        return videoUrl2;
    }

    public void setVideoUrl2(String videoUrl2) {
        this.videoUrl2 = videoUrl2;
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

    public String getDesc40() {
        return desc40;
    }

    public void setDesc40(String desc40) {
        this.desc40 = desc40;
    }

    public String getDesc41() {
        return desc41;
    }

    public void setDesc41(String desc41) {
        this.desc41 = desc41;
    }

    public String getDesc42() {
        return desc42;
    }

    public void setDesc42(String desc42) {
        this.desc42 = desc42;
    }

    public String getDesc43() {
        return desc43;
    }

    public void setDesc43(String desc43) {
        this.desc43 = desc43;
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

    public List<Integer> getContentList() {
        return contentList;
    }

    public void setContentList(List<Integer> contentList) {
        this.contentList = contentList;
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

    public int getShopType() {
        return shopType;
    }

    public void setShopType(int shopType) {
        this.shopType = shopType;
    }

    public List<Integer> getSecondMenu() {
        return secondMenu;
    }

    public void setSecondMenu(List<Integer> secondMenu) {
        this.secondMenu = secondMenu;
    }

    public int getDayPriceLow() {
        return dayPriceLow;
    }

    public void setDayPriceLow(int dayPriceLow) {
        this.dayPriceLow = dayPriceLow;
    }

    public int getNightPriceLow() {
        return nightPriceLow;
    }

    public void setNightPriceLow(int nightPriceLow) {
        this.nightPriceLow = nightPriceLow;
    }

    public int getDayPriceHigh() {
        return dayPriceHigh;
    }

    public void setDayPriceHigh(int dayPriceHigh) {
        this.dayPriceHigh = dayPriceHigh;
    }

    public int getNightPriceHigh() {
        return nightPriceHigh;
    }

    public void setNightPriceHigh(int nightPriceHigh) {
        this.nightPriceHigh = nightPriceHigh;
    }

    public int getDayPrice() {
        return dayPrice;
    }

    public void setDayPrice(int dayPrice) {
        this.dayPrice = dayPrice;
    }

    public int getNightPrice() {
        return nightPrice;
    }

    public void setNightPrice(int nightPrice) {
        this.nightPrice = nightPrice;
    }

    public int getWalkTime() {
        return walkTime;
    }

    public void setWalkTime(int walkTime) {
        this.walkTime = walkTime;
    }

    public int getDayOpenTime() {
        return dayOpenTime;
    }

    public void setDayOpenTime(int dayOpenTime) {
        this.dayOpenTime = dayOpenTime;
    }

    public int getDayCloseTime() {
        return dayCloseTime;
    }

    public void setDayCloseTime(int dayCloseTime) {
        this.dayCloseTime = dayCloseTime;
    }

    public int getDayLastOrder() {
        return dayLastOrder;
    }

    public void setDayLastOrder(int dayLastOrder) {
        this.dayLastOrder = dayLastOrder;
    }

    public int getNightOpenTime() {
        return nightOpenTime;
    }

    public void setNightOpenTime(int nightOpenTime) {
        this.nightOpenTime = nightOpenTime;
    }

    public int getNightCloseTime() {
        return nightCloseTime;
    }

    public void setNightCloseTime(int nightCloseTime) {
        this.nightCloseTime = nightCloseTime;
    }

    public int getNightLastOrder() {
        return nightLastOrder;
    }

    public void setNightLastOrder(int nightLastOrder) {
        this.nightLastOrder = nightLastOrder;
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

    public List<Integer> getOpenTimeList() {
        return openTimeList;
    }

    public void setOpenTimeList(List<Integer> openTimeList) {
        this.openTimeList = openTimeList;
    }

    public List<Integer> getWeekDayList() {
        return weekDayList;
    }

    public void setWeekDayList(List<Integer> weekDayList) {
        this.weekDayList = weekDayList;
    }

    public List<Integer> getCharacterList() {
        return characterList;
    }

    public void setCharacterList(List<Integer> characterList) {
        this.characterList = characterList;
    }

    public List<Integer> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Integer> areaList) {
        this.areaList = areaList;
    }

    public List<Integer> getStationList() {
        return stationList;
    }

    public void setStationList(List<Integer> stationList) {
        this.stationList = stationList;
    }

    public List<Integer> getFirstMenuList() {
        return firstMenuList;
    }

    public void setFirstMenuList(List<Integer> firstMenuList) {
        this.firstMenuList = firstMenuList;
    }

    public List<Integer> getSecondMenuList() {
        return secondMenuList;
    }

    public void setSecondMenuList(List<Integer> secondMenuList) {
        this.secondMenuList = secondMenuList;
    }

    public List<Integer> getDayPriceList() {
        return dayPriceList;
    }

    public void setDayPriceList(List<Integer> dayPriceList) {
        this.dayPriceList = dayPriceList;
    }

    public List<Integer> getNightPriceList() {
        return nightPriceList;
    }

    public void setNightPriceList(List<Integer> nightPriceList) {
        this.nightPriceList = nightPriceList;
    }

    public int getScene() {
        return scene;
    }

    public void setScene(int scene) {
        this.scene = scene;
    }

    public int getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(int averagePrice) {
        this.averagePrice = averagePrice;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public int getMassageTime() {
        return massageTime;
    }

    public void setMassageTime(int massageTime) {
        this.massageTime = massageTime;
    }

    public int getHeadCount() {
        return headCount;
    }

    public void setHeadCount(int headCount) {
        this.headCount = headCount;
    }

    public int getRoomType() {
        return roomType;
    }

    public void setRoomType(int roomType) {
        this.roomType = roomType;
    }

    public int getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    public int getContactTime() {
        return contactTime;
    }

    public void setContactTime(int contactTime) {
        this.contactTime = contactTime;
    }

    public int getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(int foodPrice) {
        this.foodPrice = foodPrice;
    }

    public  List<Integer>  getBrandList() {
        return brandList;
    }

    public void setBrandList(List<Integer> brandList) {
        this.brandList = brandList;
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

    public int getObject() {
        return object;
    }

    public void setObject(int object) {
        this.object = object;
    }

    public int getOpenTime() {
        return openTime;
    }

    public void setOpenTime(int openTime) {
        this.openTime = openTime;
    }

    public int getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(int closeTime) {
        this.closeTime = closeTime;
    }

    public int getPriceLow() {
        return priceLow;
    }

    public void setPriceLow(int priceLow) {
        this.priceLow = priceLow;
    }

    public int getPriceHigh() {
        return priceHigh;
    }

    public void setPriceHigh(int priceHigh) {
        this.priceHigh = priceHigh;
    }

    public int getCeremony() {
        return ceremony;
    }

    public void setCeremony(int ceremony) {
        this.ceremony = ceremony;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
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

    public int getNow5() {
        return now5;
    }

    public void setNow5(int now5) {
        this.now5 = now5;
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
    public String getPicUrl11() {
        return picUrl11;
    }

    public void setPicUrl11(String picUrl11) {
        this.picUrl11 = picUrl11;
    }

    public MultipartFile getPicFile11() {
        return picFile11;
    }

    public void setPicFile11(MultipartFile picFile11) {
        this.picFile11 = picFile11;
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

    public Integer getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Integer privilege) {
        this.privilege = privilege;
    }

    public List<Integer> getRestDates() {
        return restDates;
    }

    public void setRestDates(List<Integer> restDates) {
        this.restDates = restDates;
    }


    public void setRestWeek(List<Integer> restWeek) {
        this.restWeek = restWeek;
    }

    public void setRestWeekDay(List<Integer> restWeekDay) {
        this.restWeekDay = restWeekDay;
    }

    public List<Integer> getRestWeek() {
        return restWeek;
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

    public List<ShopRestForm> getStock() {
        return stock;
    }

    public void setStock(List<ShopRestForm> stock) {
        this.stock = stock;
    }

    public String getPic360() {
        return pic360;
    }

    public void setPic360(String pic360) {
        this.pic360 = pic360;
    }

    public int getRestRadio() {
        return restRadio;
    }

    public void setRestRadio(int restRadio) {
        this.restRadio = restRadio;
    }

    public String getFlicUrl() {
        return flicUrl;
    }

    public void setFlicUrl(String flicUrl) {
        this.flicUrl = flicUrl;
    }

    public MultipartFile getPicFile12() {
        return picFile12;
    }

    public void setPicFile12(MultipartFile picFile12) {
        this.picFile12 = picFile12;
    }
    public String getPicUrl12() {
        return picUrl12;
    }

    public void setPicUrl12(String picUrl12) {
        this.picUrl12 = picUrl12;
    }

    public String getVideoStatus() {
        return videoStatus;
    }

    public void setVideoStatus(String videoStatus) {
        this.videoStatus = videoStatus;
    }

    public String getFlicStatus() {
        return flicStatus;
    }

    public void setFlicStatus(String flicStatus) {
        this.flicStatus = flicStatus;
    }

    public int getLastOrder() {
        return lastOrder;
    }

    public void setLastOrder(int lastOrder) {
        this.lastOrder = lastOrder;
    }

    public int getAveragePriceLow() {
        return averagePriceLow;
    }

    public void setAveragePriceLow(int averagePriceLow) {
        this.averagePriceLow = averagePriceLow;
    }

    public int getAveragePriceHigh() {
        return averagePriceHigh;
    }

    public void setAveragePriceHigh(int averagePriceHigh) {
        this.averagePriceHigh = averagePriceHigh;
    }

}
