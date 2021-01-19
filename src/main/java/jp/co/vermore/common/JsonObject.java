package jp.co.vermore.common;

import java.util.List;

/**
 * JsonObject
 * Created by xieyoujun.
 * <p>
 * DateTime: 2018/03/01 10:26
 * Copyright: sLab, Corp
 */

public class JsonObject extends BaseJson{

    public JsonObject(JsonStatus status) {
        setStatus(status);
    }

    public void setStatus(JsonStatus status){
        super.setStatusCode(status.statusCode());
        super.setMessage(status.getMessage());
    }

    public JsonObject withStatus(JsonStatus status){
        super.setStatusCode(status.statusCode());
        super.setMessage(status.getMessage());
        return this;
    }
}



