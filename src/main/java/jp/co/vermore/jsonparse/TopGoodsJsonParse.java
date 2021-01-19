package jp.co.vermore.jsonparse;

import jp.co.vermore.common.mvc.BaseJsonParse;

import java.util.List;

/**
 * TopJsonParse
 * Created by Wangyan.
 * <p>
 * DateTime: 2018/03/16 9:21
 * Copyright: sLab, Corp
 */

public class TopGoodsJsonParse extends BaseJsonParse {

    private String uuid;

    private String picUrl;

    private String title;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
