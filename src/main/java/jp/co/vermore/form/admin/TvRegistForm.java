package jp.co.vermore.form.admin;

import jp.co.vermore.common.mvc.BaseForm;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class TvRegistForm extends BaseForm {

    private Long id;

    private Long tvId;

    private Long tvDetailId;

    private String title;

    private String office;

    private Byte tvType;

    private Byte type;

    private Byte category;

    private Byte status;

    private String sortScore;

    private String date;

    private String desc1;

    private String publishStart;

    private String publishEnd;

    private MultipartFile picFile;

    private MultipartFile videoFileFirst;

    private MultipartFile videoFileLast;

    private String youTubeVideoUrl;

    private String videoUrlFirst;

    private String videoUrlLast;

    private String picUrl;

    private List<String> picUrl1;

    private List<String> picUrl2;

    public Long getTvId() {
        return tvId;
    }

    public void setTvId(Long tvId) {
        this.tvId = tvId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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

    public Byte getTvType() {
        return tvType;
    }

    public void setTvType(Byte tvType) {
        this.tvType = tvType;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getSortScore() {
        return sortScore;
    }

    public void setSortScore(String sortScore) {
        this.sortScore = sortScore;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc1() {
        return desc1;
    }

    public void setDesc1(String desc1) {
        this.desc1 = desc1;
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

    public MultipartFile getPicFile() {
        return picFile;
    }

    public void setPicFile(MultipartFile picFile) {
        this.picFile = picFile;
    }

    public MultipartFile getVideoFileFirst() {
        return videoFileFirst;
    }

    public void setVideoFileFirst(MultipartFile videoFileFirst) {
        this.videoFileFirst = videoFileFirst;
    }

    public MultipartFile getVideoFileLast() {
        return videoFileLast;
    }

    public void setVideoFileLast(MultipartFile videoFileLast) {
        this.videoFileLast = videoFileLast;
    }

    public String getVideoUrlFirst() {
        return videoUrlFirst;
    }

    public void setVideoUrlFirst(String videoUrlFirst) {
        this.videoUrlFirst = videoUrlFirst;
    }

    public String getVideoUrlLast() {
        return videoUrlLast;
    }

    public void setVideoUrlLast(String videoUrlLast) {
        this.videoUrlLast = videoUrlLast;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public List<String> getPicUrl1() {
        return picUrl1;
    }

    public void setPicUrl1(List<String> picUrl1) {
        this.picUrl1 = picUrl1;
    }

    public List<String> getPicUrl2() {
        return picUrl2;
    }

    public void setPicUrl2(List<String> picUrl2) {
        this.picUrl2 = picUrl2;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYouTubeVideoUrl() {
        return youTubeVideoUrl;
    }

    public void setYouTubeVideoUrl(String youTubeVideoUrl) {
        this.youTubeVideoUrl = youTubeVideoUrl;
    }

    public Long getTvDetailId() {
        return tvDetailId;
    }

    public void setTvDetailId(Long tvDetailId) {
        this.tvDetailId = tvDetailId;
    }

    public Byte getCategory() {
        return category;
    }

    public void setCategory(Byte category) {
        this.category = category;
    }


}