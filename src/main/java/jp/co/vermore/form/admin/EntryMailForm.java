package jp.co.vermore.form.admin;

import jp.co.vermore.common.mvc.BaseForm;
import java.util.Date;

public class EntryMailForm extends BaseForm {

    private Long id;

    private Long entryId;

    private Byte type;

    private String typeStr;

    private String title;

    private String subject;

    private String detail;

    private String publishStart;

    private String publishEnd;

    private Date createDatetime;

    private Date updateDatetime;

    private Boolean delFlg;

    private String note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEntryId() {
        return entryId;
    }

    public void setEntryId(Long entryId) {
        this.entryId = entryId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public Boolean getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Boolean delFlg) {
        this.delFlg = delFlg;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}