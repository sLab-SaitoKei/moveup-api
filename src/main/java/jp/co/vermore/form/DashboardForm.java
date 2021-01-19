package jp.co.vermore.form;

import jp.co.vermore.common.mvc.BaseForm;

import java.util.List;
import java.util.Map;

/**
 * LoginForm
 * Created by Wangyan.
 * <p>
 * DateTime: 2018/03/23 12:57
 * Copyright: sLab, Corp
 */

public class DashboardForm extends BaseForm {

    private String mau;

    private String shopAmount;

    private String risePV;

    private String goodsAmount;

    private Map careerAmount;

    private Map careerRatio;

    private Map age;

    private Map gender;

    private String news;

    private String event;

    private String freepaper;

    public String getFreepaper() {
        return freepaper;
    }

    public void setFreepaper(String freepaper) {
        this.freepaper = freepaper;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getMau() {
        return mau;
    }

    public void setMau(String mau) {
        this.mau = mau;
    }

    public String getShopAmount() {
        return shopAmount;
    }

    public void setShopAmount(String shopAmount) {
        this.shopAmount = shopAmount;
    }

    public String getRisePV() {
        return risePV;
    }

    public void setRisePV(String risePV) {
        this.risePV = risePV;
    }

    public String getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(String goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public Map getCareerAmount() {
        return careerAmount;
    }

    public void setCareerAmount(Map careerAmount) {
        this.careerAmount = careerAmount;
    }

    public Map getCareerRatio() {
        return careerRatio;
    }

    public void setCareerRatio(Map careerRatio) {
        this.careerRatio = careerRatio;
    }

    public Map getAge() {
        return age;
    }

    public void setAge(Map age) {
        this.age = age;
    }

    public Map getGender() {
        return gender;
    }

    public void setGender(Map gender) {
        this.gender = gender;
    }
}
