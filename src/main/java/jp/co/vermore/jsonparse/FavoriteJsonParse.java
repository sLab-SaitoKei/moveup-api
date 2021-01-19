package jp.co.vermore.jsonparse;

import java.util.Date;

/**
 * FavoriteJsonParse
 * Created by wbin.
 *
 * DateTime: 2018/04/12 13:00
 * Copyright: sLab, Corp
 */

public class FavoriteJsonParse {

    private String uuid;

    private Integer type;

    private Integer shopType;

    private Integer tvType;

    private Integer status;

    private String userSetting;

    private String name;

    private String picUrl;

    private String createDatetime;

    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getShopType() {
        return shopType;
    }

    public void setShopType(Integer shopType) {
        this.shopType = shopType;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(String createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getUserSetting() {
        return userSetting;
    }

    public void setUserSetting(String userSetting) {
        this.userSetting = userSetting;
    }

    public Integer getTvType() {
        return tvType;
    }

    public void setTvType(Integer tvType) {
        this.tvType = tvType;
    }
}
