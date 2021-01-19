package jp.co.vermore.form.admin;

import jp.co.vermore.common.mvc.BaseForm;

/**
 * RoleFrom
 * Created by wubin.
 *
 * DateTime: 2018/05/04 13:30
 * Copyright: sLab, Corp
 */

public class RoleFrom extends BaseForm {

    private Long id;

    private Long role;

    private String name;

    private String shopUuid;

    private byte type;

    private Integer action;

    private Integer privilege;

    private String loginName;

    private String showName;

    private String password;

    private String mail;

    private byte status;

    private byte fcType;

    private Long fcAdminId;

    private byte customerType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRole() {
        return role;
    }

    public void setRole(Long role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public Integer getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Integer privilege) {
        this.privilege = privilege;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public byte getFcType() {
        return fcType;
    }

    public void setFcType(byte fcType) {
        this.fcType = fcType;
    }

    public Long getFcAdminId() {
        return fcAdminId;
    }

    public void setFcAdminId(Long fcAdminId) {
        this.fcAdminId = fcAdminId;
    }

    public byte getCustomerType() {
        return customerType;
    }

    public void setCustomerType(byte customerType) {
        this.customerType = customerType;
    }

    public String getShopUuid() {
        return shopUuid;
    }

    public void setShopUuid(String shopUuid) {
        this.shopUuid = shopUuid;
    }

}
