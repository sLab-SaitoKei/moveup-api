package jp.co.vermore.jsonparse;


import jp.co.vermore.common.mvc.BaseJsonParse;

/**
 * FreePaperJsonParse
 * Created by wubin.
 *
 * DateTime: 2018/03/20 9:32
 * Copyright: sLab, Corp
 */

public class FreePaperJsonParse extends BaseJsonParse {

    private Long id;

    private String uuid;

    private String year;

    private Integer ranking;

    private String picUrl;

    private String title;

    private String weekDay;

    private String date;

    private boolean favorite;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
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

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String week) {
        this.weekDay = week;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
