package jp.co.vermore.jsonparse.shopjsonparse;

import jp.co.vermore.common.mvc.BaseJsonParse;

import java.util.List;
import java.util.Map;

/**
 * ShopJsonParse
 * Created by wubin.
 *
 * DateTime: 2018/03/12 12:18
 * Copyright: sLab, Corp
 */

public class ShopFastionJsonParse extends BaseJsonParse {

//    private Long id;
//
//    private String name;
//
//    private String address;
//
//    private String excerpt;
//
//    private String picUrl;

    private int shopCount;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    private String area;

    private List<Map<String,Object>> contentList;

    private List<Map<String, Object>> areaList;

    private List<Map<String,Object>> budgetList;

    private List<Map<String,Object>> shopTimeList;

    private List<Map<String,Object>> sceneList;

    private List<Map<String,Object>> detailSearchList;

    private List<Map<String,Object>> brandList;

    private List<Map<String,Object>> mainMenu;

    public int getShopCount() { return shopCount; }

    public void setShopCount(int shopCount) { this.shopCount = shopCount; }

    public List<Map<String,Object>> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Map<String,Object>> areaList) {
        this.areaList = areaList;
    }

    public List<Map<String,Object>> getBudgetList() {
        return budgetList;
    }

    public void setBudgetList(List<Map<String,Object>> budgetList) {
        this.budgetList = budgetList;
    }

    public List<Map<String,Object>> getSceneList() {
        return sceneList;
    }

    public void setSceneList(List<Map<String,Object>> sceneList) {
        this.sceneList = sceneList;
    }

    public List<Map<String,Object>> getDetailSearchList() {
        return detailSearchList;
    }

    public void setDetailSearchList(List<Map<String,Object>> detailSearchList) {
        this.detailSearchList = detailSearchList;
    }

    public List<Map<String,Object>> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<Map<String,Object>> brandList) {
        this.brandList = brandList;
    }

    public List<Map<String,Object>> getMainMenu() {
        return mainMenu;
    }

    public void setMainMenu(List<Map<String,Object>> mainMenu) {
        this.mainMenu = mainMenu;
    }

    public List<Map<String, Object>> getshopTimeList() {
        return shopTimeList;
    }

    public void setshopTimeList(List<Map<String, Object>> shopTimeList) {
        this.shopTimeList = shopTimeList;
    }

    public List<Map<String, Object>> getContentList() {
        return contentList;
    }

    public void setContentList(List<Map<String, Object>> contentList) {
        this.contentList = contentList;
    }
}
