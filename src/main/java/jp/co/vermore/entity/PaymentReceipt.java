package jp.co.vermore.entity;

import java.util.List;

/**
 * PaymentReceipt
 * Created by Wangyan.
 * <p>
 * DateTime: 2018/04/16 16:30
 * Copyright: sLab, Corp
 */

public class PaymentReceipt {

    private String orderID;

    private String forward;

    private String method;

    private String approve;

    private String AccessID;

    private String AccessPass;

    private String tranID;

    private String tranDate;

    private String checkString;

    private String goodsID;

    private String userID;

    private Byte paymentType;

    private int amount;

    private List<PaymentErrList> errList;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getApprove() {
        return approve;
    }

    public void setApprove(String approve) {
        this.approve = approve;
    }

    public String getTranID() {
        return tranID;
    }

    public void setTranID(String tranID) {
        this.tranID = tranID;
    }

    public String getTranDate() {
        return tranDate;
    }

    public void setTranDate(String tranDate) {
        this.tranDate = tranDate;
    }

    public String getCheckString() {
        return checkString;
    }

    public void setCheckString(String checkString) {
        this.checkString = checkString;
    }

    public String getGoodsID() {
        return goodsID;
    }

    public void setGoodsID(String goodsID) {
        this.goodsID = goodsID;
    }

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getAccessID() {
        return AccessID;
    }

    public void setAccessID(String accessID) {
        AccessID = accessID;
    }

    public String getAccessPass() {
        return AccessPass;
    }

    public void setAccessPass(String accessPass) {
        AccessPass = accessPass;
    }

    public Byte getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Byte paymentType) {
        this.paymentType = paymentType;
    }


    public List<PaymentErrList> getErrList() {
        return errList;
    }

    public void setErrList(List<PaymentErrList> errList) {
        this.errList = errList;
    }
}
