package jp.co.vermore.common.mvc;

import jp.co.vermore.common.JsonStatus;

/**
 * APIException
 * Created by xieyoujun.
 * <p>
 * DateTime: 2018/03/20 10:44
 * Copyright: sLab, Corp
 */

public class APIException extends Exception{

    public JsonStatus getStatus() {
        return status;
    }

    public void setStatus(JsonStatus status) {
        this.status = status;
    }

    private JsonStatus status;

    public APIException(JsonStatus status) {
        this.status =  status;
    }
}
