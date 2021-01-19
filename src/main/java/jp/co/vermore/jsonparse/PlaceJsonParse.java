package jp.co.vermore.jsonparse;

import jp.co.vermore.common.mvc.BaseJsonParse;

public class PlaceJsonParse extends BaseJsonParse {

    private String uuid;

    private String name;

    private String introduce;

    private String address;

    private Integer area;

    private Integer placeCount;

    private String picUrl;

    private String flicUrl;

    private double coordinate1;

    private double coordinate2;

    public Integer getPlaceCount() {
        return placeCount;
    }

    public void setPlaceCount(Integer placeCount) {
        this.placeCount = placeCount;
    }

    public double getCoordinate1() {
        return coordinate1;
    }

    public void setCoordinate1(double coordinate1) {
        this.coordinate1 = coordinate1;
    }

    public double getCoordinate2() {
        return coordinate2;
    }

    public void setCoordinate2(double coordinate2) {
        this.coordinate2 = coordinate2;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getFlicUrl() {
        return flicUrl;
    }

    public void setFlicUrl(String flicUrl) {
        this.flicUrl = flicUrl;
    }


}