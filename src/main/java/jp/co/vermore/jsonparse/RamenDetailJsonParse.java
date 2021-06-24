package jp.co.vermore.jsonparse;

import jp.co.vermore.common.mvc.BaseJsonParse;

import java.util.List;

/**
 * RamenDetailJsonParse
 * Created by saito.
 *
 * DateTime: 2022/06/13 12:11
 * Copyright: sLab, Corp
 */

public class RamenDetailJsonParse extends BaseJsonParse {

    private Long ramenId;

    private String entry;

    private String date;

    private String typeStr;

    private int type;

    private String color;

    private String title;

    private String detail;

    private List<String> topPic;

    private List<String> footPic;

    private List<RamenJsonParse> ramenPre;

    private List<RamenJsonParse> ramenNext;

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

    public List<RamenJsonParse> getRamenPre() {
        return ramenPre;
    }

    public void setRamenPre(List<RamenJsonParse> ramenPre) {
        this.ramenPre = ramenPre;
    }

    public List<RamenJsonParse> getRamenNext() {
        return ramenNext;
    }

    public void setRamenNext(List<RamenJsonParse> ramenNext) {
        this.ramenNext = ramenNext;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public Long getRamenId() {
        return ramenId;
    }

    public void setRamenId(Long ramenId) {
        this.ramenId = ramenId;
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
