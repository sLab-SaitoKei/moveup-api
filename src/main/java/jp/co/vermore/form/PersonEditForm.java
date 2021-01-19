package jp.co.vermore.form;

import jp.co.vermore.entity.Person;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Settingform
 * Created by wubin.
 *
 * DateTime: 2018/04/11 19:04
 * Copyright: sLab, Corp
 */

public class PersonEditForm {

    private String nickname;

    private String firstName;

    private String secondName;

    private String firstNameKana;

    private String secondNameKana;

    private Integer gender;

    private String mail;

    private Integer birthyear;

    private Integer birthmonth;

    private Integer birthday;

    private Integer area;

    private MultipartFile thumbnail;

    private String thumbnailUrl;

    private Integer career;

    private String zipcode;

    private String address;

    private String address2;

    private String address3;

    private String password;

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    private List<Person> settingList;

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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getBirthyear() {
        return birthyear;
    }

    public void setBirthyear(Integer birthyear) {
        this.birthyear = birthyear;
    }

    public Integer getBirthmonth() {
        return birthmonth;
    }

    public void setBirthmonth(Integer birthmonth) {
        this.birthmonth = birthmonth;
    }

    public Integer getBirthday() {
        return birthday;
    }

    public void setBirthday(Integer birthday) {
        this.birthday = birthday;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public MultipartFile getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(MultipartFile thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Integer getCareer() {
        return career;
    }

    public void setCareer(Integer career) {
        this.career = career;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Person> getSettingList() {
        return settingList;
    }

    public void setSettingList(List<Person> settingList) {
        this.settingList = settingList;
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

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

}
