package jp.co.vermore.kit.test.java.pdf.kit;

import jp.co.vermore.form.PdfForm;
import lombok.Data;

import java.util.List;

/**
 * Created by fgm on 2017/4/17.
 */
@Data
public class TemplateBO {

    private String templateName;

    private String freeMarkerUrl;

    private String ITEXTUrl;

    private String JFreeChartUrl;

    private List<PdfForm> scores;

    private String imageUrl;

    private String picUrl;

    private String scatterUrl;

    private String time;
    private String shopName;
    private String yyyy;
    private String mm;
    private String totlePrice;
    private String tax;
    private String subtotal;



    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getFreeMarkerUrl() {
        return freeMarkerUrl;
    }

    public void setFreeMarkerUrl(String freeMarkerUrl) {
        this.freeMarkerUrl = freeMarkerUrl;
    }

    public String getITEXTUrl() {
        return ITEXTUrl;
    }

    public void setITEXTUrl(String ITEXTUrl) {
        this.ITEXTUrl = ITEXTUrl;
    }

    public String getJFreeChartUrl() {
        return JFreeChartUrl;
    }

    public void setJFreeChartUrl(String JFreeChartUrl) {
        this.JFreeChartUrl = JFreeChartUrl;
    }

    public List<PdfForm> getScores() {
        return scores;
    }

    public void setScores(List<PdfForm> scores) {
        this.scores = scores;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getScatterUrl() {
        return scatterUrl;
    }

    public void setScatterUrl(String scatterUrl) {
        this.scatterUrl = scatterUrl;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
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

    public String getTotlePrice() {
        return totlePrice;
    }

    public void setTotlePrice(String totlePrice) {
        this.totlePrice = totlePrice;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }
}
