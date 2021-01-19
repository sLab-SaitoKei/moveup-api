package jp.co.vermore.form.admin;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

public class CustomerListForm extends DatatablesBaseForm {

    private Integer foreignType;

    private String nickname;

    private Integer gender;

    private Date birthdayFrom;

    private Date birthdayTo;

    private Date createDatetimeFrom;

    private Date createDatetimeTo;

    private String uuid;

    private String mail;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getForeignType() {
        return foreignType;
    }

    public void setForeignType(Integer foreignType) {
        this.foreignType = foreignType;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getBirthdayFrom() {
        return birthdayFrom;
    }

    public void setBirthdayFrom(Date birthdayFrom) {
        this.birthdayFrom = birthdayFrom;
    }

    public Date getBirthdayTo() {
        return birthdayTo;
    }

    public void setBirthdayTo(Date birthdayTo) {
        this.birthdayTo = birthdayTo;
    }

    public Date getCreateDatetimeFrom() {
        return createDatetimeFrom;
    }

    public void setCreateDatetimeFrom(Date createDatetimeFrom) {
        this.createDatetimeFrom = createDatetimeFrom;
    }

    public Date getCreateDatetimeTo() {
        return createDatetimeTo;
    }

    public void setCreateDatetimeTo(Date createDatetimeTo) {
        this.createDatetimeTo = createDatetimeTo;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

}
