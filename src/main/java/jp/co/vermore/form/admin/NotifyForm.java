package jp.co.vermore.form.admin;


import jp.co.vermore.common.mvc.BaseForm;

import java.util.List;

/**
 * Created by wubin.
 * <p>
 * DateTime: 2018/03/03 11:13
 * Copyright: sLab, Corp
 */


public class NotifyForm extends BaseForm {

    private int userType;

    private int itemId;

    private int sendType;

    private String userList;

    private byte platform;

    private byte push;

    private String title;

    private String itemUrl;

    private String content;

    private String pushTime;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte getPlatform() {
        return platform;
    }

    public void setPlatform(byte platform) {
        this.platform = platform;
    }

    public byte getPush() {
        return push;
    }

    public void setPush(byte push) {
        this.push = push;
    }

    public String getUserList() {
        return userList;
    }

    public void setUserList(String userList) {
        this.userList = userList;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getSendType() {
        return sendType;
    }

    public void setSendType(int sendType) {
        this.sendType = sendType;
    }

    public String getPushTime() {
        return pushTime;
    }

    public void setPushTime(String pushTime) {
        this.pushTime = pushTime;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

}
