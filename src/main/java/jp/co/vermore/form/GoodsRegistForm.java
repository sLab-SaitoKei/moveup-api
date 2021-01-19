package jp.co.vermore.form;

import jp.co.vermore.common.Constant;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * GoodsRegistForm
 * Created by Wangyan.
 * <p>
 * DateTime: 2018/04/09 18:11
 * Copyright: sLab, Corp
 */

public class GoodsRegistForm {

    private Long id;

    private Long goodsId;

    private String name;

    private String brand;

    private String goodsType;

    private String title;

    private int sortScore;

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

    private String desc1;

    private String desc2;

    private String desc3;

    private Integer price;

    private List<StockRegistForm> stock;

    private List<GoodsColorForm> colors;

    private String publishStart;

    private String publishEnd;

    public String getGoodsType() { return goodsType; }

    public void setGoodsType(String goodsType) { this.goodsType = goodsType; }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
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

    public List<StockRegistForm> getStock() {
        return stock;
    }

    public void setStock(List<StockRegistForm> stock) {
        this.stock = stock;
    }

    public int getSortScore() {
        return sortScore;
    }

    public void setSortScore(int sortScore) {
        this.sortScore = sortScore;
    }

    public List<GoodsColorForm> getColors() {
        return colors;
    }

    public void setColors(List<GoodsColorForm> colors) {
        this.colors = colors;
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
}
