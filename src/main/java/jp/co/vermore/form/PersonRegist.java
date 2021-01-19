package jp.co.vermore.form;

import java.util.Date;

/**
 * PersonRegist
 * Created by wubin.
 *
 * DateTime: 2018/04/10 17:21
 * Copyright: sLab, Corp
 */

public class PersonRegist {

    private Long id;

    private Long userId;

    private String nickname;

    private String firstName;

    private String secondName;

    private String firstNameKana;

    private String secondNameKana;

    private Integer gender;

    private String mailAddress;

    private Date birthday;

    private Integer area;

    private String thumbnailUrl;

    private Integer career;

    private Integer zipcode;

    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getFirstNameKana() {
        return firstNameKana;
    }

    public void setFirstNameKana(String firstNameKana) {
        this.firstNameKana = firstNameKana;
    }

    public String getSecondNameKana() {
        return secondNameKana;
    }

    public void setSecondNameKana(String secondNameKana) {
        this.secondNameKana = secondNameKana;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Integer getCareer() {
        return career;
    }

    public void setCareer(Integer career) {
        this.career = career;
    }

    public Integer getZipcode() {
        return zipcode;
    }

    public void setZipcode(Integer zipcode) {
        this.zipcode = zipcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
