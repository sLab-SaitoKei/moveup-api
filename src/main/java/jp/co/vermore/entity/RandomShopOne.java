package jp.co.vermore.entity;

import java.util.Date;

public class RandomShopOne {

    private Long id;

    private Byte shopType;

    private String uuid;

    private Byte area;

    private Byte now4;

    private Byte now5;

    private Date publishStart;

    private Date publishEnd;

    private Date createDatetime;

    private Date updateDatetime;

    private Boolean delFlg;

    private String note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getShopType() {
        return shopType;
    }

    public void setShopType(Byte shopType) {
        this.shopType = shopType;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public Boolean getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Boolean delFlg) {
        this.delFlg = delFlg;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Byte getArea() {
        return area;
    }

    public void setArea(Byte area) {
        this.area = area;
    }

    public Byte getNow4() {
        return now4;
    }

    public void setNow4(Byte now4) {
        this.now4 = now4;
    }

    public Byte getNow5() {
        return now5;
    }

    public void setNow5(Byte now5) {
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