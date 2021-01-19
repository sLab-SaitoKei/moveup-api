package jp.co.vermore.form.admin;

/**
 * GoodsPurchaseHistotyForm
 * Created by Wangyan.
 * <p>
 * DateTime: 2018/04/12 16:42
 * Copyright: sLab, Corp
 */

public class GoodsPurchaseHistotyDetailForm {

    private String color;

    private String size;

    private Integer quantity;

    private Integer price;

    private Integer tax;

    private String goodsName;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getTax() {
        return tax;
    }

    public void setTax(Integer tax) {
        this.tax = tax;
    }
}
