package jp.co.vermore.form;

import jp.co.vermore.common.mvc.BaseForm;

import java.util.List;

/**
 * Created by xieyoujun on 2017/11/28.
 */
public class GoodsPurchaseMailForm extends BaseForm {

    private String name;

    private String serialNumber;

    private String date;

    private List<PurchaseGoodsInfoForm> goodsInfoForms;

    private String purchaseType;

    private int deliveryExpense;

    private String totalPrice;

    private String zipcode;

    private String address;

    private String mail;

    private String name2;

    private String zipcode2;

    private String address2;

    private String tel;

    private String tel2;

    private String url1;

    private String url2;

    private int status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<PurchaseGoodsInfoForm> getGoodsInfoForms() {
        return goodsInfoForms;
    }

    public void setGoodsInfoForms(List<PurchaseGoodsInfoForm> goodsInfoForms) {
        this.goodsInfoForms = goodsInfoForms;
    }

    public int getDeliveryExpense() {
        return deliveryExpense;
    }

    public void setDeliveryExpense(int deliveryExpense) {
        this.deliveryExpense = deliveryExpense;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
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

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
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

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
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

    public String getUrl1() {
        return url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
