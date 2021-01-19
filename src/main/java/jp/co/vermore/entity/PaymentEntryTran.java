package jp.co.vermore.entity;

/**
 * PaymentEntryTran
 * Created by Wangyan.
 * <p>
 * DateTime: 2018/04/16 15:53
 * Copyright: sLab, Corp
 */

public class PaymentEntryTran {

    private String orderID;

    private String amount;

    private String tax;


    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }
}
