package jp.co.vermore.form;

import jp.co.vermore.common.mvc.BaseForm;

/**
 * LoginForm
 * Created by Wangyan.
 * <p>
 * DateTime: 2018/03/23 12:57
 * Copyright: sLab, Corp
 */

public class LoginForm extends BaseForm {

    private String loginName;

    private String password;

    private String link;

    public String getUsername() {
        return loginName;
    }

    public void setUsername(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
