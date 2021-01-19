package jp.co.vermore.form;

import jp.co.vermore.common.mvc.BaseForm;

/**
 * Created by xieyoujun on 2017/11/28.
 */
public class PdfForm extends BaseForm {

    private String title;

    private String month;

    private String type;

    private String amount;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
