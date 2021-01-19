package jp.co.vermore.jsonparse.shopjsonparse;

import jp.co.vermore.common.mvc.BaseJsonParse;
import jp.co.vermore.entity.Recruit;

import java.util.List;
import java.util.Map;

/**
 * ShopAllJsonParse
 * Created by admin.
 *
 * DateTime: 2018/04/16 18:26
 * Copyright: sLab, Corp
 */

public class ShopAllJsonParse extends BaseJsonParse {

//    private Long id;
//
//    private String uuid;
//
//    private String name;
//
//    private String address;
//
//    private String excerpt;
//
//    private String picUrl;

    private int shopCount;

    private List<Map<String,Object>> contentList;

    private List<Map<String,Object>> areaList;

    private List<Map<String,Object>> budgetList;

    private List<Map<String,Object>> shopTimeList;

    public int getShopCount() { return shopCount; }
    public List<Recruit> getRecruitList() {
        return recruitList;
    }

    public void setRecruitList(List<Recruit> recruitList) {
        this.recruitList = recruitList;
    }

    private List<Recruit> recruitList;

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
