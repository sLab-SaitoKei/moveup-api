package jp.co.vermore.jsonparse;

import jp.co.vermore.common.mvc.BaseJsonParse;


public class NotificationContentJsonParse extends BaseJsonParse {

    private String title;

    private String content;

    private String date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



}
