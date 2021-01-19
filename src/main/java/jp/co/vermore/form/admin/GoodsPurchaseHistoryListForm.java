package jp.co.vermore.form.admin;

import java.util.Date;

/**
 * GoodsListForm
 * Created by Wangyan.
 * <p>
 * DateTime: 2018/04/09 17:00
 * Copyright: sLab, Corp
 */

public class GoodsPurchaseHistoryListForm extends DatatablesBaseForm{

    private Long id;

    private String orderId;

    private Integer amount;

    private Date updateDatetime;

    private Integer paymentType;

    private String paymentTypeStr;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentTypeStr() {
        return paymentTypeStr;
    }

    public void setPaymentTypeStr(String paymentTypeStr) {
        this.paymentTypeStr = paymentTypeStr;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
