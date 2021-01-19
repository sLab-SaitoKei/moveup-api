package jp.co.vermore.entity;

import jp.co.vermore.common.mvc.BaseEntity;

import java.util.Date;

public class ShopForList extends BaseEntity {

    private Long id;

    private Integer shopType;

    private String name;

    private String tel;

    private String address;

    private Integer now4;

    private Integer now5;

    private Date publishStart;

    private Date publishEnd;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getShopType() {
        return shopType;
    }

    public void setShopType(Integer shopType) {
        this.shopType = shopType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
