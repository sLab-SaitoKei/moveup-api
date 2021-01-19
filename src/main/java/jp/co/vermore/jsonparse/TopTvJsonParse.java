package jp.co.vermore.jsonparse;

import jp.co.vermore.common.mvc.BaseJsonParse;
import jp.co.vermore.entity.TvVideo;

import java.util.List;


/**
 * TopJsonParse
 * Created by wangnannan.
 * <p>
 * DateTime: 2018/06/08 15:32
 * Copyright: sLab, Corp
 */

public class TopTvJsonParse extends BaseJsonParse {

    private String uuid;

    private Integer sortScore;

    private String title;

    private String office;

    private String url;

    private String date;

    private String tvType;

    private String typeColor;

    private String tvPic;

    private Byte type;

    private List<TvVideo> tvVideos;

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public List<TvVideo> getTvVideos() {
        return tvVideos;
    }

    public void setTvVideos(List<TvVideo> tvVideos) {
        this.tvVideos = tvVideos;
    }

    public String getTypeColor() {
        return typeColor;
    }

    public void setTypeColor(String typeColor) {
        this.typeColor = typeColor;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getSortScore() {
        return sortScore;
    }

    public void setSortScore(Integer sortScore) {
        this.sortScore = sortScore;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTvType() {
        return tvType;
    }

    public void setTvType(String tvType) {
        this.tvType = tvType;
    }

    public String getTvPic() {
        return tvPic;
    }

    public void setTvPic(String tvPic) {
        this.tvPic = tvPic;
    }

}
