package jp.co.vermore.form.admin;

import java.util.Date;

/**
 * Created by li.
 * <p>
 * DateTime: 2018/04/17 11:13
 * Copyright: sLab, Corp
 */


public class FreePaperListForm extends DatatablesBaseForm{

    private Date date;

    private Integer volume;

    private String title;

    private Integer sortScore;

    private Date publishStart;

    private Date publishEnd;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSortScore() {
        return sortScore;
    }

    public void setSortScore(Integer sortScore) {
        this.sortScore = sortScore;
    }

    public Date getPublishStart() {
        return publishStart;
    }

    public void setPublishStart(Date publishStart) {
        this.publishStart = publishStart;
    }

    public Date getPublishEnd() {
        return publishEnd;
    }

    public void setPublishEnd(Date publishEnd) {
        this.publishEnd = publishEnd;
    }
}
