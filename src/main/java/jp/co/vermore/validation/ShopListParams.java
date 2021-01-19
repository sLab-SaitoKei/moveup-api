package jp.co.vermore.validation;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * ShopListParams
 * Created by wubin.
 *
 * DateTime: 2018/04/25 12:26
 * Copyright: sLab, Corp
 */

public class ShopListParams {
    @Min(value = 1, message = "Limit can't be less than 1")
    private Integer limit;

    @Min(value = 0, message = "Offset can't be less than 0")
    private Integer offset;

    private List<Integer> area;

    private List<Integer> character;

    private List<Integer> brand;

    private Integer dayPriceLow;

    private Integer nightPriceLow;

    private Integer dayPriceHigh;

    private Integer nightPriceHigh;

    private Integer shopTime;

    private Integer scene;

    private Integer mainMenu;

    private Integer seat;

    private Integer massageTime;

    private Integer headCount;

    private Integer roomType;

    private Integer roomNum;

    private Integer shopType;

    private Integer now4;

    private Integer now5;

    private String keyWord;

    private List<Long> idList;

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    private String today;

    private String yesterday;

    private String tomorrow;

    //By wangyu ↓
    private Integer business;

    private Integer restDate;

    private Integer restWeek;

    public Integer getBusiness() {return business; }

    public void setBusiness(Integer business){this.business = business;}

    public Integer getRestDate(){return restDate;}

    public Integer getRestWeek(){return restWeek;}

    public void setRestDate(Integer restDate){this.restDate = restDate;}

    public void setRestWeek(Integer restWeek){this.restWeek = restWeek;}

    //By wangyu ↑

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public List<Integer> getArea() {
        return area;
    }

    public void setArea(List<Integer> area) {
        this.area = area;
    }

    public List<Integer> getCharacter() {
        return character;
    }

    public void setCharacter(List<Integer> character) {
        this.character = character;
    }

    public List<Integer> getBrand() {
        return brand;
    }

    public void setBrand(List<Integer> brand) {
        this.brand = brand;
    }

    public Integer getDayPriceLow() {
        return dayPriceLow;
    }

    public void setDayPriceLow(Integer dayPriceLow) {
        this.dayPriceLow = dayPriceLow;
    }

    public Integer getNightPriceLow() {
        return nightPriceLow;
    }

    public void setNightPriceLow(Integer nightPriceLow) {
        this.nightPriceLow = nightPriceLow;
    }

    public Integer getDayPriceHigh() {
        return dayPriceHigh;
    }

    public void setDayPriceHigh(Integer dayPriceHigh) {
        this.dayPriceHigh = dayPriceHigh;
    }

    public Integer getNightPriceHigh() {
        return nightPriceHigh;
    }

    public void setNightPriceHigh(Integer nightPriceHigh) {
        this.nightPriceHigh = nightPriceHigh;
    }

    public Integer getShopTime() {
        return shopTime;
    }

    public void setShopTime(Integer shopTime) {
        this.shopTime = shopTime;
    }

    public Integer getScene() {
        return scene;
    }

    public void setScene(Integer scene) {
        this.scene = scene;
    }

    public Integer getMainMenu() {
        return mainMenu;
    }

    public void setMainMenu(Integer mainMenu) {
        this.mainMenu = mainMenu;
    }

    public Integer getSeat() {
        return seat;
    }

    public void setSeat(Integer seat) {
        this.seat = seat;
    }

    public Integer getMassageTime() {
        return massageTime;
    }

    public void setMassageTime(Integer massageTime) {
        this.massageTime = massageTime;
    }

    public Integer getHeadCount() {
        return headCount;
    }

    public void setHeadCount(Integer headCount) {
        this.headCount = headCount;
    }

    public Integer getRoomType() {
        return roomType;
    }

    public void setRoomType(Integer roomType) {
        this.roomType = roomType;
    }

    public Integer getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(Integer roomNum) {
        this.roomNum = roomNum;
    }

    public Integer getShopType() {
        return shopType;
    }

    public void setShopType(Integer shopType) {
        this.shopType = shopType;
    }

    public Integer getNow4() {
        return now4;
    }

    public void setNow4(Integer now4) {
        this.now4 = now4;
    }

    public Integer getNow5() {
        return now5;
    }

    public void setNow5(Integer now5) {
        this.now5 = now5;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }

    public String getYesterday() {
        return yesterday;
    }

    public void setYesterday(String yesterday) {
        this.yesterday = yesterday;
    }

    public String getTomorrow() {
        return tomorrow;
    }

    public void setTomorrow(String tomorrow) {
        this.tomorrow = tomorrow;
    }

}
