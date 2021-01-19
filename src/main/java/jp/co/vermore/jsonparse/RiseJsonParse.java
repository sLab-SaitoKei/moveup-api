package jp.co.vermore.jsonparse;


import jp.co.vermore.common.mvc.BaseJsonParse;

public class RiseJsonParse extends BaseJsonParse {

    private Long id;

    private String name1;

    private String name2;

    private String picUrl;

    private String category;

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

    public String getName2() {
        return this.name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getPicUrl() {
        return this.picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}