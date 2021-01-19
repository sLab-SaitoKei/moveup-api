package jp.co.vermore.form.admin;

/**
 * ShopCustomerListForm
 * Created by wubin.
 *
 * DateTime: 2018/05/03 10:30
 * Copyright: sLab, Corp
 */

public class RoleListForm extends DatatablesBaseForm{

    private String loginName;

    private Byte status;

    private Byte fcType;

    private Byte userType;

    private Long fcAdminId;

    private Byte customerType;

    private String password;

    private String showName;

    private String mail;

    private String shopUuid;

    private Byte role;

    public Byte getUserType() {
        return userType;
    }

    public void setUserType(Byte userType) {
        this.userType = userType;
    }
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getFcType() {
        return fcType;
    }

    public void setFcType(Byte fcType) {
        this.fcType = fcType;
    }

    public Long getFcAdminId() {
        return fcAdminId;
    }

    public void setFcAdminId(Long fcAdminId) {
        this.fcAdminId = fcAdminId;
    }

    public Byte getCustomerType() {
        return customerType;
    }

    public void setCustomerType(Byte customerType) {
        this.customerType = customerType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Byte getRole() {
        return role;
    }

    public void setRole(Byte role) {
        this.role = role;
    }

    public String getShopUuid() {
        return shopUuid;
    }

    public void setShopUuid(String shopUuid) {
        this.shopUuid = shopUuid;
    }

}
