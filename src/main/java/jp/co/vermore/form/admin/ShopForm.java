package jp.co.vermore.form.admin;

import jp.co.vermore.common.mvc.BaseForm;

import java.util.Date;

/**
 * ShopForm
 * Created by wubin.
 *
 * DateTime: 2018/03/19 14:46
 * Copyright: sLab, Corp
 */

public class ShopForm extends BaseForm {

    private Long id;

    private String uuid;

    private Long shopListId;

    private Integer now5;

    private Integer now4;

    private String name;

    private Integer category;

    private String title;

    private String tel;

    private String address;

    private String genre;

    private Date publishStart;

    private Date publishEnd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public Integer getCategory() { return category; }

    public void setCategory(Integer category) { this.category = category; }

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

    public Integer getNow5() {
        return now5;
    }

    public void setNow5(Integer now5) {
        this.now5 = now5;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNow4() {
        return now4;
    }

    public void setNow4(Integer now4) {
        this.now4 = now4;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Date getPublishStart() {
        return publishStart;
    }

    public void setPublishStart(Date publishStart) {
        this.publishStart = publishStart;
    }

    public Date getPublishEnd() {
        return publishEnd;
    }

    public void setPublishEnd(Date publishEnd) {
        this.publishEnd = publishEnd;
    }
}
