package jp.co.vermore.jsonparse;


import jp.co.vermore.common.mvc.BaseJsonParse;

public class TopRiseJsonParse extends BaseJsonParse {

    private Long id;

    private Integer sortScore;

    private String name1;

    private String picUrl;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getPicUrl() {
        return this.picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Integer getSortScore() {
        return sortScore;
    }

    public void setSortScore(Integer sortScore) {
        this.sortScore = sortScore;
    }
}