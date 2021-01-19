package jp.co.vermore.form.admin;

import jp.co.vermore.common.mvc.BaseForm;

import java.util.Date;

/**
 * InquireForm
 * Created by wubin.
 *
 * DateTime: 2018/04/23 17:34
 * Copyright: sLab, Corp
 */

public class InquireListForm extends DatatablesBaseForm {

    private Byte type;

    private String mail;

    private String contents;

    private String date;

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
