package jp.co.vermore.jsonparse;

import jp.co.vermore.common.mvc.BaseJsonParse;

/**
 * EventJsonParse
 * Created by xieyoujun.
 * <p>
 * DateTime: 2018/03/01 12:35
 * Copyright: sLab, Corp
 */

public class EventJsonParse extends BaseJsonParse {

    private String uuid;

    private String title;

    private String excerpt;

    private String guestName;

    private String hallName;

    private String holdDate;

    private String picUrl;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getHoldDate() {
        return holdDate;
    }

    public void setHoldDate(String holdDate) {
        this.holdDate = holdDate;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }
}
