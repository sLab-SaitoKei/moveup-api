package jp.co.vermore.form;

import jp.co.vermore.common.mvc.BaseForm;

/**
 * Created by xieyoujun on 2017/11/28.
 */
public class DemoForm extends BaseForm {

    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
