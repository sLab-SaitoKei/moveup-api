package jp.co.vermore.jsonparse;

import jp.co.vermore.common.mvc.BaseJsonParse;

import java.util.Date;

/**
 * NewsJsonParse
 * Created by wubin.
 * <p>
 * DateTime: 2018/03/07 13:25
 * Copyright: sLab, Corp
 */

public class TopNewsJsonParse extends BaseJsonParse {

    private String uuid;

    private String date;

    private String type;

    private String title;

    private String typeColor;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDate() { return date; }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getTypeColor() {
        return typeColor;
    }

    public void setTypeColor(String typeColor) {
        this.typeColor = typeColor;
    }
}
