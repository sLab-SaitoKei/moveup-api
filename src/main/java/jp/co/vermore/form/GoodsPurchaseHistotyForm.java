package jp.co.vermore.form;

import jp.co.vermore.form.admin.GoodsPurchaseHistotyDetailForm;

import java.util.List;

/**
 * GoodsPurchaseHistotyForm
 * Created by Wangyan.
 * <p>
 * DateTime: 2018/04/12 16:42
 * Copyright: sLab, Corp
 */

public class GoodsPurchaseHistotyForm {

    private List<GoodsPurchaseHistotyDetailForm> goodsPurchaseHistotyDetailFormList;

    private String statusContent;

    private int status;

    private String serialNumber;

    private int totalPrice;

    private String name;

    private String name2;

    private String date;

    private int deliveryExpense;

    private String zipcode;

    private String address;

    private String mail;

    private String zipcode2;

    private String address2;

    private String tel;

    private String tel2;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusContent() {
        return statusContent;
    }

    public void setStatusContent(String statusContent) {
        this.statusContent = statusContent;
    }

    public List<GoodsPurchaseHistotyDetailForm> getGoodsPurchaseHistotyDetailFormList() {
        return goodsPurchaseHistotyDetailFormList;
    }

    public void setGoodsPurchaseHistotyDetailFormList(List<GoodsPurchaseHistotyDetailForm> goodsPurchaseHistotyDetailFormList) {
        this.goodsPurchaseHistotyDetailFormList = goodsPurchaseHistotyDetailFormList;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDeliveryExpense() {
        return deliveryExpense;
    }

    public void setDeliveryExpense(int deliveryExpense) {
        this.deliveryExpense = deliveryExpense;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getZipcode2() {
        return zipcode2;
    }

    public void setZipcode2(String zipcode2) {
        this.zipcode2 = zipcode2;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }
}
