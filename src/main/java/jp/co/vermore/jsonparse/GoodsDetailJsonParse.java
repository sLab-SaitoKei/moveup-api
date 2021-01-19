package jp.co.vermore.jsonparse;

import java.util.List;

import jp.co.vermore.common.mvc.BaseJsonParse;

public class GoodsDetailJsonParse extends BaseJsonParse {

    private Long id;

    private List<StockJsonParse> stockList;

    private String title;

    private String brand;

    private List<String> colorPicUrl;

    private List<String> descPicUrl;

    private String desc1;

    private String desc2;

    private String desc3;

    private Integer price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public List<StockJsonParse> getStockList() {
        return stockList;
    }

    public void setStockList(List<StockJsonParse> stockList) {
        this.stockList = stockList;
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

    public List<String> getColorPicUrl() {
        return colorPicUrl;
    }

    public void setColorPicUrl(List<String> colorPicUrl) {
        this.colorPicUrl = colorPicUrl;
    }

    public List<String> getDescPicUrl() {
        return descPicUrl;
    }

    public void setDescPicUrl(List<String> descPicUrl) {
        this.descPicUrl = descPicUrl;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}