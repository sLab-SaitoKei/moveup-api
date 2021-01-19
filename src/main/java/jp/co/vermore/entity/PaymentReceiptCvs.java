package jp.co.vermore.entity;

import java.util.List;

/**
 * PaymentReceipt
 * Created by Wangyan.
 * <p>
 * DateTime: 2018/04/16 16:30
 * Copyright: sLab, Corp
 */

public class PaymentReceiptCvs {

    private String orderID;

    private String accessId;

    private String accessPass;

    private int paymentStatus;

    private String convenience;

    private String confNo;

    private String receiptNo;

    private String paymentTerm;

    private String receiptUrl;

    private String tranDate;

    private String checkString;

    private String goodsID;

    private String userID;

    private int amount;

    private List<PaymentErrList> errList;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getConvenience() {
        return convenience;
    }

    public void setConvenience(String convenience) {
        this.convenience = convenience;
    }

    public String getConfNo() {
        return confNo;
    }

    public void setConfNo(String confNo) {
        this.confNo = confNo;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getReceiptUrl() {
        return receiptUrl;
    }

    public void setReceiptUrl(String receiptUrl) {
        this.receiptUrl = receiptUrl;
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


    public List<PaymentErrList> getErrList() {
        return errList;
    }

    public void setErrList(List<PaymentErrList> errList) {
        this.errList = errList;
    }

    public String getAccessId() {
        return accessId;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public String getAccessPass() {
        return accessPass;
    }

    public void setAccessPass(String accessPass) {
        this.accessPass = accessPass;
    }

    public int getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(int paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(String paymentTerm) {
        this.paymentTerm = paymentTerm;
    }
}
