package jp.co.vermore.form.admin;

import jp.co.vermore.entity.FreePaperDetail;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * FreePaperForm
 * Created by wubin.
 *
 * DateTime: 2018/04/12 16:49
 * Copyright: sLab, Corp
 */

public class FreePaperForm {

    private Long id;

    private String uuid;

    private String date;

    private Integer volume;

    private String picUrl;

    private String picUrl1;

    private MultipartFile picFil1;

    private MultipartFile[] picFil;

    private String title;

    private Integer sortScore;

    private String picUrlDetail;

    private  List<String> urlList;

    private String publishStart;

    private String publishEnd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
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

    public Integer getSortScore() {
        return sortScore;
    }

    public void setSortScore(Integer sortScore) {
        this.sortScore = sortScore;
    }

    public MultipartFile[] getPicFil() {
        return picFil;
    }

    public void setPicFil(MultipartFile[] picFil) {
        this.picFil = picFil;
    }

    public String getPicUrlDetail() {
        return picUrlDetail;
    }

    public void setPicUrlDetail(String picUrlDetail) {
        this.picUrlDetail = picUrlDetail;
    }

    public List<String> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<String> urlList) {
        this.urlList = urlList;
    }

    public String getPublishStart() {
        return publishStart;
    }

    public void setPublishStart(String publishStart) {
        this.publishStart = publishStart;
    }

    public String getPublishEnd() {
        return publishEnd;
    }

    public void setPublishEnd(String publishEnd) {
        this.publishEnd = publishEnd;
    }

    public String getPicUrl1() {
        return picUrl1;
    }

    public void setPicUrl1(String picUrl1) {
        this.picUrl1 = picUrl1;
    }


    public MultipartFile getPicFil1() {
        return picFil1;
    }

    public void setPicFil1(MultipartFile picFil1) {
        this.picFil1 = picFil1;
    }
}
