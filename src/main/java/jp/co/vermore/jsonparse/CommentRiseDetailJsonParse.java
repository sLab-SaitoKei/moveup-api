package jp.co.vermore.jsonparse;

import jp.co.vermore.common.mvc.BaseJsonParse;

public class CommentRiseDetailJsonParse extends BaseJsonParse {

    private String desc;

    private String createDatetime;

    private String picUrl1;

    private String picUrl2;

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

    public String getPicUrl1() {
        return picUrl1;
    }

    public void setPicUrl1(String picUrl1) {
        this.picUrl1 = picUrl1;
    }

    public String getPicUrl2() {
        return picUrl2;
    }

    public void setPicUrl2(String picUrl2) {
        this.picUrl2 = picUrl2;
    }
}