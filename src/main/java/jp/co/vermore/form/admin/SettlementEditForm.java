package jp.co.vermore.form.admin;

import java.util.Date;

public class SettlementEditForm extends DatatablesBaseForm {
    private String shopUUID;
    private String mon;
    private Integer month;
    private String customerName;
    private Integer type;
    private Integer amount;
    private Integer status;
    private String shopName;
    private Integer method;
    private String title;
    private String content;
    private Date createDatetime;
    private Date updateDatetime;
    private Long shopID;
    private String yyyy;
    private String mm;
    private String time;
    private double totlePrice;
    private double tax;
    private double subtotal;

    public Long getShopID() {
        return shopID;
    }

    public void setShopID(Long shopID) {
        this.shopID = shopID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Integer getMethod() {
        return method;
    }

    public void setMethod(Integer method) {
        this.method = method;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getShopUUID() {
        return shopUUID;
    }

    public void setShopUUID(String shopUUID) {
        this.shopUUID = shopUUID;
    }

    public String getMon() {
        return mon;
    }

    public void setMon(String mon) {
        this.mon = mon;
    }

    public String getYyyy() {
        return yyyy;
    }

    public void setYyyy(String yyyy) {
        this.yyyy = yyyy;
    }

    public String getMm() {
        return mm;
    }

    public void setMm(String mm) {
        this.mm = mm;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getTotlePrice() {
        return totlePrice;
    }

    public void setTotlePrice(double totlePrice) {
        this.totlePrice = totlePrice;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

}
