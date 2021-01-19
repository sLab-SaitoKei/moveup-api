package jp.co.vermore.entity;

/**
 * PaymentEntryTran
 * Created by Wangyan.
 * <p>
 * DateTime: 2018/04/16 15:53
 * Copyright: sLab, Corp
 */

public class PaymentExecTranCvs {

    private String accessID;

    private String accessPass;

    private String orderID;

    private String goodsID;

    private String customerName;

    private String customerKana;

    private String telNo;

    private String mail;

    public String getAccessID() {
        return accessID;
    }

    public void setAccessID(String accessID) {
        this.accessID = accessID;
    }

    public String getAccessPass() {
        return accessPass;
    }

    public void setAccessPass(String accessPass) {
        this.accessPass = accessPass;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getGoodsID() {
        return goodsID;
    }

    public void setGoodsID(String goodsID) {
        this.goodsID = goodsID;
    }


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerKana() {
        return customerKana;
    }

    public void setCustomerKana(String customerKana) {
        this.customerKana = customerKana;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
