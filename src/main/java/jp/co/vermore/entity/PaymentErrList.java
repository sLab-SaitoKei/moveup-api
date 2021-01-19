package jp.co.vermore.entity;


/**
 * PaymentReceipt
 * Created by Wangyan.
 * <p>
 * DateTime: 2018/04/16 16:30
 * Copyright: sLab, Corp
 */

public class PaymentErrList {

    private String errCode;

    private String errInfo;


    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrInfo() {
        return errInfo;
    }

    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
    }
}
