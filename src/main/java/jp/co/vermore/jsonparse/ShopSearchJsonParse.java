package jp.co.vermore.jsonparse;

import jp.co.vermore.common.mvc.BaseJsonParse;

import java.util.List;
import java.util.Map;

/**
 * ShopSearchJsonParse
 * Created by wubin.
 *
 * DateTime: 2018/03/28 17:44
 * Copyright: sLab, Corp
 */

public class ShopSearchJsonParse extends BaseJsonParse {

    private List<Map<String,Object>> contentList;

    private List<Map<String,Object>> couponList;

    private List<Map<String,Object>> now5List;

    private  int shopCount;

    private String area;

    public int getShopCount() { return shopCount; }

    public void setShopCount(int shopCount) { this.shopCount = shopCount; }

    public List<Map<String, Object>> getContentList() {
        return contentList;
    }

    public void setContentList(List<Map<String, Object>> contentList) {
        this.contentList = contentList;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public List<Map<String, Object>> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<Map<String, Object>> couponList) {
        this.couponList = couponList;
    }

    public List<Map<String, Object>> getNow5List() {
        return now5List;
    }

    public void setNow5List(List<Map<String, Object>> now5List) {
        this.now5List = now5List;
    }

}
