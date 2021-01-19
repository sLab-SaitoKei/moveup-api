package jp.co.vermore.form.entry;

import jp.co.vermore.common.mvc.BaseForm;
import jp.co.vermore.validation.GroupOrder1;
import jp.co.vermore.validation.GroupOrder2;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by xieyoujun on 2017/11/28.
 */
public class MailForm extends BaseForm {

    @NotEmpty(groups={GroupOrder1.class},message="・メールを入力してください。")
    @Size(max=64,groups={GroupOrder2.class},message="・メールアドレス{max}文字までを入力してください。")
    @Pattern(regexp="^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$",groups={GroupOrder2.class},message="・正しいメールアドレスを入力してください。")
    private String mail;

    private String dup;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDup() {
        return dup;
    }

    public void setDup(String dup) {
        this.dup = dup;
    }
}
