package jp.co.vermore.form.admin;


import java.util.Date;

/**
 * Created by wubin.
 * <p>
 * DateTime: 2018/03/03 11:13
 * Copyright: sLab, Corp
 */


public class NotifyListForm extends DatatablesBaseForm{

    private Long userId;
    private Integer userType;
    private Integer notifyType;
    private Integer notifiedDevice;
    private Integer notifyStatus;
    private Integer pushStatus;
    private String createDatetime;
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(Integer notifyType) {
        this.notifyType = notifyType;
    }

    public Integer getNotifiedDevice() {
        return notifiedDevice;
    }

    public void setNotifiedDevice(Integer notifiedDevice) {
        this.notifiedDevice = notifiedDevice;
    }

    public Integer getNotifyStatus() {
        return notifyStatus;
    }

    public void setNotifyStatus(Integer notifyStatus) {
        this.notifyStatus = notifyStatus;
    }

    public Integer getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(Integer pushStatus) {
        this.pushStatus = pushStatus;
    }

    public String getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(String createDatetime) {
        this.createDatetime = createDatetime;
    }
}
