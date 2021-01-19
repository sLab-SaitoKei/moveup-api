package jp.co.vermore.form.admin;

import jp.co.vermore.common.mvc.BaseForm;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by wangnannan
 * <p>
 * DateTime: 2018/05/23 11:13
 * Copyright: sLab, Corp
 */
public class Team2020Form extends BaseForm {

    private Long id;

    private String uuid;

    private String name;

    private String pseudonym;

    private Byte category;

    private String picUrl;

    private MultipartFile[] picFil;

    private List<String> urlList;

    public String getPseudonym() {
        return pseudonym;
    }

    public void setPseudonym(String pseudonym) {
        this.pseudonym = pseudonym;
    }

    public List<String> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<String> urlList) {
        this.urlList = urlList;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public MultipartFile[] getPicFil() {
        return picFil;
    }

    public void setPicFil(MultipartFile[] picFil) {
        this.picFil = picFil;
    }

    public Byte getCategory() {
        return category;
    }

    public void setCategory(Byte category) {
        this.category = category;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
