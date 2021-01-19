package jp.co.vermore.form.admin;

import jp.co.vermore.common.mvc.BaseForm;
import jp.co.vermore.entity.Entry;
import jp.co.vermore.entity.News;
import jp.co.vermore.entity.Person;

import java.util.Date;

/**
 * ApplicationForm
 * Created by wubin.
 *
 * DateTime: 2018/05/07 18:44
 * Copyright: sLab, Corp
 */

public class AdminEntryForm extends BaseForm {

    private long id;

    private long userId;

    private long entryId;

    private int entryType;

    private String mail;

    private String title;

    private String content;

    private Person person;

    private Entry entry;

    private News news;

    private String career;

    private int age;

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getEntryId() {
        return entryId;
    }

    public void setEntryId(Long entryId) {
        this.entryId = entryId;
    }

    public int getEntryType() {
        return entryType;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setEntryType(int entryType) {
        this.entryType = entryType;
    }

    public void setEntryId(long entryId) {
        this.entryId = entryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
