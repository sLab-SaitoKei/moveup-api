package jp.co.vermore.form;

import jp.co.vermore.common.mvc.BaseForm;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class EventRegistForm extends BaseForm {

    private long eventId;

    private long eventDetailId;

    private String sortScore;

    private String title;

    private String gustName;

    private String hallName;

    private String holdDate;

    private Integer picListNo;

    private String picUrl;

    private List<String> picUrl1;

    private List<String> picUrl2;

    private MultipartFile picFile;

    private MultipartFile[] picFile1;

    private MultipartFile[] picFile2;

    private String videoUrl1;

    private String videoUrl2;

    private String excerpt;

    private String desc1;

    private String desc2;

    private String desc3;

    private String comment;

    private String publishStart;

    private String publishEnd;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHoldDate() {
        return holdDate;
    }

    public void setHoldDate(String holdDate) {
        this.holdDate = holdDate;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Integer getPicListNo() {
        return picListNo;
    }

    public void setPicListNo(Integer picListNo) {
        this.picListNo = picListNo;
    }

    public String getVideoUrl1() {
        return videoUrl1;
    }

    public void setVideoUrl1(String videoUrl1) {
        this.videoUrl1 = videoUrl1;
    }

    public String getVideoUrl2() {
        return videoUrl2;
    }

    public void setVideoUrl2(String videoUrl2) {
        this.videoUrl2 = videoUrl2;
    }

    public String getDesc1() {
        return desc1;
    }

    public void setDesc1(String desc1) {
        this.desc1 = desc1;
    }

    public String getDesc2() {
        return desc2;
    }

    public void setDesc2(String desc2) {
        this.desc2 = desc2;
    }

    public String getDesc3() {
        return desc3;
    }

    public void setDesc3(String desc3) {
        this.desc3 = desc3;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public long getEventDetailId() {
        return eventDetailId;
    }

    public void setEventDetailId(long eventDetailId) {
        this.eventDetailId = eventDetailId;
    }

    public String getSortScore() {
        return sortScore;
    }

    public void setSortScore(String sortScore) {
        this.sortScore = sortScore;
    }

    public String getGustName() {
        return gustName;
    }

    public void setGustName(String gustName) {
        this.gustName = gustName;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public MultipartFile getPicFile() {
        return picFile;
    }

    public void setPicFile(MultipartFile picFile) {
        this.picFile = picFile;
    }

    public MultipartFile[] getPicFile1() {
        return picFile1;
    }

    public void setPicFile1(MultipartFile[] picFile1) {
        this.picFile1 = picFile1;
    }

    public MultipartFile[] getPicFile2() {
        return picFile2;
    }

    public void setPicFile2(MultipartFile[] picFile2) {
        this.picFile2 = picFile2;
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
}