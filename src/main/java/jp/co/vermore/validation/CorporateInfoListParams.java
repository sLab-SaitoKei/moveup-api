package jp.co.vermore.validation;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * CorporateInfoListParams
 * Created by wubin.
 *
 * DateTime: 2018/04/24 20:07
 * Copyright: sLab, Corp
 */

public class CorporateInfoListParams {

    @Min(value = 1, message = "Limit can't be less than 1")
    private Integer limit;

    @Min(value = 0, message = "Offset can't be less than 0")
    private Integer offset;

    private Integer shopType;

    private List<Integer> area;

    private Integer mainMenu;

    private String keyWord;

    private String today;

    private String yesterday;

    private String tomorrow;

    private List<Long> idList;

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

    private Integer now4;

    private Integer now5;

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

    public Integer getShopType() {
        return shopType;
    }

    public void setShopType(Integer shopType) {
        this.shopType = shopType;
    }

    public List<Integer> getArea() {
        return area;
    }

    public void setArea(List<Integer> area) {
        this.area = area;
    }

    public Integer getMainMenu() {
        return mainMenu;
    }

    public void setMainMenu(Integer mainMenu) {
        this.mainMenu = mainMenu;
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

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
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
