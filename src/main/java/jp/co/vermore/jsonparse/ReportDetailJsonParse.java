package jp.co.vermore.jsonparse;

import jp.co.vermore.common.mvc.BaseJsonParse;

import java.util.List;

/**
 * ReportDetailJsonParse
 * Created by wubin.
 *
 * DateTime: 2018/03/13 16:52
 * Copyright: sLab, Corp
 */

public class ReportDetailJsonParse extends BaseJsonParse {

    private Long reportId;

    private String entry;

    private String date;

    private String typeStr;

    private int type;

    private String color;

    private String title;

    private String detail;

    private List<String> topPic;

    private List<String> footPic;

    private List<ReportJsonParse> reportPre;

    private List<ReportJsonParse> reportNext;

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

    public List<ReportJsonParse> getReportPre() {
        return reportPre;
    }

    public void setReportPre(List<ReportJsonParse> reportPre) {
        this.reportPre = reportPre;
    }

    public List<ReportJsonParse> getReportNext() {
        return reportNext;
    }

    public void setReportNext(List<ReportJsonParse> reportNext) {
        this.reportNext = reportNext;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
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
