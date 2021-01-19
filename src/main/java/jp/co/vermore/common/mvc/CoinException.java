package jp.co.vermore.common.mvc;

/**
 * APIException
 * Created by xieyoujun.
 * <p>
 * DateTime: 2018/03/20 10:44
 * Copyright: sLab, Corp
 */

public class CoinException extends Exception{

    String MESSAGE;
    public CoinException() {
        MESSAGE = "Excess Coin";
    }
    public String getMessage() {
        return MESSAGE;
    }
    public void setMessage(String message) {
        this.MESSAGE = message;
    }
}
