package jp.co.vermore.jsonparse;


import jp.co.vermore.common.mvc.BaseJsonParse;

import java.util.List;

/**
 * TopJsonParse
 * Created by Wangyan.
 * <p>
 * DateTime: 2018/03/16 9:21
 * Copyright: sLab, Corp
 */

public class TopJsonParse extends BaseJsonParse {

    private String videoUrl;

    private String picUrlLeft;

    private String picUrlRight1;

    private String picUrlRight2;

    private String picUrlRight3;

    private byte type;

    private byte itemTypeLeft;

    private byte itemTypeRight0;

    private byte itemTypeRight1;

    private byte itemTypeRight2;

    private byte itemTypeRight3;

    private String linkUrlLeft;

    private String linkUrlRight1;

    private String linkUrlRight2;

    private String linkUrlRight3;

    private List<TopEventJsonParse> event;

    private List<TopNewsJsonParse> news;

    private List<TopRiseJsonParse> rise;

    private List<TopTvJsonParse> tv;

    private List<TopGoodsJsonParse> goods;

    private List<TopCommentJsonParse> comment;

    private List<FreePaperJsonParse> freePaper;

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getPicUrlLeft() {
        return picUrlLeft;
    }

    public void setPicUrlLeft(String picUrlLeft) {
        this.picUrlLeft = picUrlLeft;
    }

    public String getPicUrlRight1() {
        return picUrlRight1;
    }

    public void setPicUrlRight1(String picUrlRight1) {
        this.picUrlRight1 = picUrlRight1;
    }

    public String getPicUrlRight2() {
        return picUrlRight2;
    }

    public void setPicUrlRight2(String picUrlRight2) {
        this.picUrlRight2 = picUrlRight2;
    }

    public String getPicUrlRight3() {
        return picUrlRight3;
    }

    public void setPicUrlRight3(String picUrlRight3) {
        this.picUrlRight3 = picUrlRight3;
    }

    public String getLinkUrlLeft() {
        return linkUrlLeft;
    }

    public void setLinkUrlLeft(String linkUrlLeft) {
        this.linkUrlLeft = linkUrlLeft;
    }

    public String getLinkUrlRight1() {
        return linkUrlRight1;
    }

    public void setLinkUrlRight1(String linkUrlRight1) {
        this.linkUrlRight1 = linkUrlRight1;
    }

    public String getLinkUrlRight2() {
        return linkUrlRight2;
    }

    public void setLinkUrlRight2(String linkUrlRight2) {
        this.linkUrlRight2 = linkUrlRight2;
    }

    public String getLinkUrlRight3() {
        return linkUrlRight3;
    }

    public void setLinkUrlRight3(String linkUrlRight3) {
        this.linkUrlRight3 = linkUrlRight3;
    }

    public List<TopEventJsonParse> getEvent() {
        return event;
    }

    public void setEvent(List<TopEventJsonParse> event) {
        this.event = event;
    }

    public List<TopNewsJsonParse> getNews() {
        return news;
    }

    public void setNews(List<TopNewsJsonParse> news) {
        this.news = news;
    }

    public List<TopRiseJsonParse> getRise() {
        return rise;
    }

    public void setRise(List<TopRiseJsonParse> rise) {
        this.rise = rise;
    }

    public List<TopGoodsJsonParse> getGoods() {
        return goods;
    }

    public void setGoods(List<TopGoodsJsonParse> goods) {
        this.goods = goods;
    }

    public List<TopCommentJsonParse> getComment() {
        return comment;
    }

    public void setComment(List<TopCommentJsonParse> comment) {
        this.comment = comment;
    }

    public List<FreePaperJsonParse> getFreePaper() {
        return freePaper;
    }

    public void setFreePaper(List<FreePaperJsonParse> freePaper) {
        this.freePaper = freePaper;
    }

    public List<TopTvJsonParse> getTv() {
        return tv;
    }

    public void setTv(List<TopTvJsonParse> tv) {
        this.tv = tv;
    }

    public byte getItemTypeLeft() {
        return itemTypeLeft;
    }

    public void setItemTypeLeft(byte itemTypeLeft) {
        this.itemTypeLeft = itemTypeLeft;
    }

    public byte getItemTypeRight0() {
        return itemTypeRight0;
    }

    public void setItemTypeRight0(byte itemTypeRight0) {
        this.itemTypeRight0 = itemTypeRight0;
    }

    public byte getItemTypeRight1() {
        return itemTypeRight1;
    }

    public void setItemTypeRight1(byte itemTypeRight1) {
        this.itemTypeRight1 = itemTypeRight1;
    }

    public byte getItemTypeRight2() {
        return itemTypeRight2;
    }

    public void setItemTypeRight2(byte itemTypeRight2) {
        this.itemTypeRight2 = itemTypeRight2;
    }

    public byte getItemTypeRight3() {
        return itemTypeRight3;
    }

    public void setItemTypeRight3(byte itemTypeRight3) {
        this.itemTypeRight3 = itemTypeRight3;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }
}
