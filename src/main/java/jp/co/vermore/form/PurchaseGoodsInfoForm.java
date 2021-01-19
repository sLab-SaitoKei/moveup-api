package jp.co.vermore.form;

import jp.co.vermore.common.mvc.BaseForm;

import java.util.List;

/**
 * Created by xieyoujun on 2017/11/28.
 */
public class PurchaseGoodsInfoForm extends BaseForm {

    private String goodsName;

    private String color;

    private String size;

    private String price;

    private int quantity;

    private String tax;


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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }
}
