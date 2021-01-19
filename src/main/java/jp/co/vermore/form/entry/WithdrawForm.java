package jp.co.vermore.form.entry;

import jp.co.vermore.common.mvc.BaseForm;
import jp.co.vermore.validation.GroupOrder1;
import jp.co.vermore.validation.GroupOrder2;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by xieyoujun on 2017/11/28.
 */
public class WithdrawForm extends BaseForm {

    @NotEmpty(groups={GroupOrder1.class},message="・メールを入力してください。")
    @Size(max=64,groups={GroupOrder2.class},message="・メールアドレス{max}文字までを入力してください。")
    @Pattern(regexp="^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$",groups={GroupOrder2.class},message="・正しいメールアドレスを入力してください。")
    private String mail;

    private String miss;

    private String errorMessage;

    private String message;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMiss() {
        return miss;
    }

    public void setMiss(String miss) {
        this.miss = miss;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
