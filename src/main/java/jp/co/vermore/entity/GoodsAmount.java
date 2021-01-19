package jp.co.vermore.entity;

import jp.co.vermore.common.mvc.BaseEntity;

import java.util.Date;

public class GoodsAmount extends BaseEntity {

    private Long goodsId;

    private String name;

    private String gtype;

    private Integer price;

    private Integer amount;

    private Date publishStart;

    private Date publishEnd;

    public String getGtype() { return gtype; }

    public void setGtype(String gtype) { this.gtype = gtype; }

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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
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
