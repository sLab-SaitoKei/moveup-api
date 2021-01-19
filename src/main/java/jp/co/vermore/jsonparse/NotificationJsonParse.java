package jp.co.vermore.jsonparse;

import jp.co.vermore.common.mvc.BaseJsonParse;



public class NotificationJsonParse extends BaseJsonParse {

    private Long id;

    private int status;

    private Long notifiContentId;

    private String title;

    private String content;

    private String date;

    private String time;

    private String picUrl;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getNotifiContentId() {
        return notifiContentId;
    }

    public void setNotifiContentId(Long notifiContentId) {
        this.notifiContentId = notifiContentId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

}
