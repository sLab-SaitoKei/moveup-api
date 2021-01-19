package jp.co.vermore.jsonparse;

import jp.co.vermore.common.mvc.BaseJsonParse;

import java.util.Date;

public class TopCommentJsonParse extends BaseJsonParse {

    private Long itemId;

    private String desc;

    private String createDatetime;

    public Long getItemId() {
        return itemId;
    }
    public void setItemId(Long itemId) {
        this.itemId = itemId;
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
}