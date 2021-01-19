package jp.co.vermore.jsonparse;

import jp.co.vermore.common.mvc.BaseJsonParse;
import jp.co.vermore.entity.News;

import java.util.List;

/**
 * NewsDetailJsonParse
 * Created by wubin.
 *
 * DateTime: 2018/03/13 16:52
 * Copyright: sLab, Corp
 */

public class NewsDetailJsonParse extends BaseJsonParse {

    private Long newsId;

    private String entry;

    private String date;

    private String typeStr;

    private int type;

    private String color;

    private String title;

    private String detail;

    private List<String> topPic;

    private List<String> footPic;

    private List<NewsJsonParse> newsPre;

    private List<NewsJsonParse> newsNext;

    public String getDate() { return date; }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<NewsJsonParse> getNewsPre() {
        return newsPre;
    }

    public void setNewsPre(List<NewsJsonParse> newsPre) {
        this.newsPre = newsPre;
    }

    public List<NewsJsonParse> getNewsNext() {
        return newsNext;
    }

    public void setNewsNext(List<NewsJsonParse> newsNext) {
        this.newsNext = newsNext;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public List<String> getTopPic() {
        return topPic;
    }

    public void setTopPic(List<String> topPic) {
        this.topPic = topPic;
    }

    public List<String> getFootPic() {
        return footPic;
    }

    public void setFootPic(List<String> footPic) {
        this.footPic = footPic;
    }
}
