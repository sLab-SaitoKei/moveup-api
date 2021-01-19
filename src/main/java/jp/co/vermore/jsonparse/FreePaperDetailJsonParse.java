package jp.co.vermore.jsonparse;

import jp.co.vermore.common.mvc.BaseJsonParse;

/**
 * FreePaperDetailJsonParse
 * Created by wubin.
 *
 * DateTime: 2018/03/20 11:09
 * Copyright: sLab, Corp
 */

public class FreePaperDetailJsonParse extends BaseJsonParse {

    private long freePaperId;

    private String picUrl;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public long getFreePaperId() {
        return freePaperId;
    }

    public void setFreePaperId(long freePaperId) {
        this.freePaperId = freePaperId;
    }

}
