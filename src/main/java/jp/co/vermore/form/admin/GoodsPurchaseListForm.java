package jp.co.vermore.form.admin;


import java.util.Date;

/**
 * Created by li.
 * <p>
 * DateTime: 2018/04/23 11:13
 * Copyright: sLab, Corp
 */


public class GoodsPurchaseListForm extends DatatablesBaseForm{

    private String goodsName;

    private Integer status;

    private String serialNumber;

    private Date updateDatetime;

    private Long id;

    private Long userId;

    private Integer price;

    private Integer coin;

    private Integer quantity;

    private String sizeContent;

    private String colorsContent;

    private String statusContent;

    private Integer amount;

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getCoin() {
        return coin;
    }

    public void setCoin(Integer coin) {
        this.coin = coin;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSizeContent() {
        return sizeContent;
    }

    public void setSizeContent(String sizeContent) {
        this.sizeContent = sizeContent;
    }

    public String getColorsContent() {
        return colorsContent;
    }

    public void setColorsContent(String colorsContent) {
        this.colorsContent = colorsContent;
    }

    public String getStatusContent() {
        return statusContent;
    }

    public void setStatusContent(String statusContent) {
        this.statusContent = statusContent;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
