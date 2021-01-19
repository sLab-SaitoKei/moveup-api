package jp.co.vermore.jsonparse;

import jp.co.vermore.common.mvc.BaseJsonParse;

/**
 * AdminRiseFeedJsonParse
 * Created by xieyoujun.
 * <p>
 * DateTime: 2018/04/04 15:11
 * Copyright: sLab, Corp
 */

public class AdminShopFeedJsonParse extends BaseJsonParse {

    private String id;

    private String title;

    private String desc;

    private String createDatetime;

    private String picUrl;

    private String publishStart;

    private String publishEnd;

    private Boolean delFlg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(String createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Boolean getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Boolean delFlg) {
        this.delFlg = delFlg;
    }

    public String getPublishStart() {
        return publishStart;
    }

    public void setPublishStart(String publishStart) {
        this.publishStart = publishStart;
    }

    public String getPublishEnd() {
        return publishEnd;
    }

    public void setPublishEnd(String publishEnd) {
        this.publishEnd = publishEnd;
    }
}