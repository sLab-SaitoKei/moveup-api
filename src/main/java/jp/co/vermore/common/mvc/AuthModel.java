package jp.co.vermore.common.mvc;

import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;

/**
 * AuthModel
 * Created by xieyoujun.
 * <p>
 * DateTime: 2018/03/24 1:18
 * Copyright: sLab, Corp
 */

public class AuthModel {

    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
