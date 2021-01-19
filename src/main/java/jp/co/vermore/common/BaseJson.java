package jp.co.vermore.common;

import java.util.List;

/**
 * BseeJson
 * Created by xieyoujun.
 * <p>
 * DateTime: 2018/03/01 10:29
 * Copyright: sLab, Corp
 */

public class BaseJson {

    String statusCode;
    String message;
    Object resultList;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResultList() {
        return resultList;
    }

    public void setResultList(Object resultList) {
        this.resultList = resultList;
    }
}
