package jp.co.vermore.jsonparse;

import jp.co.vermore.common.mvc.BaseJsonParse;
import jp.co.vermore.entity.Event;

import java.util.List;

/**
 * EventJsonParse
 * Created by xieyoujun.
 * <p>
 * DateTime: 2018/03/01 12:35
 * Copyright: sLab, Corp
 */

public class EventDetailJsonParse extends BaseJsonParse {

    private Long eventId;

    private String entry;

    private String holdDate;

    private String title;

    private String gustName;

    private String hallName;

    private List<String> starPic;

    private List<String> commentPic;

    private String desc1;

    private String videoUrl1;

    private String comment;

    private Event eventPre;

    private Event eventNext;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc1() {
        return desc1;
    }

    public void setDesc1(String desc1) {
        this.desc1 = desc1;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getVideoUrl1() {
        return videoUrl1;
    }

    public void setVideoUrl1(String videoUrl1) {
        this.videoUrl1 = videoUrl1;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public String getHoldDate() {
        return holdDate;
    }

    public void setHoldDate(String holdDate) {
        this.holdDate = holdDate;
    }

    public Event getEventPre() {
        return eventPre;
    }

    public void setEventPre(Event eventPre) {
        this.eventPre = eventPre;
    }

    public Event getEventNext() {
        return eventNext;
    }

    public void setEventNext(Event eventNext) {
        this.eventNext = eventNext;
    }

    public List<String> getStarPic() {
        return starPic;
    }

    public void setStarPic(List<String> starPic) {
        this.starPic = starPic;
    }

    public List<String> getCommentPic() {
        return commentPic;
    }

    public void setCommentPic(List<String> commentPic) {
        this.commentPic = commentPic;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

}
