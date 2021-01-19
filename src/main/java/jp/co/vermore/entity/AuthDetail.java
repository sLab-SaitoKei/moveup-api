package jp.co.vermore.entity;

import jp.co.vermore.common.mvc.BaseEntity;

import java.util.Date;

public class AuthDetail extends BaseEntity {

    private Long id;

    private Integer foreignType;

    private String uuid;

    private String nickname;

    private String mail;

    private Integer gender;

    private Date birthday;

    private Date create_datetime;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getForeignType() {
        return foreignType;
    }

    public void setForeignType(Integer foreignType) {
        this.foreignType = foreignType;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getCreate_datetime() {
        return create_datetime;
    }

    public void setCreate_datetime(Date create_datetime) {
        this.create_datetime = create_datetime;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

}
