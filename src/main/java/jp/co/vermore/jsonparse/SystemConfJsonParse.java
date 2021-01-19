package jp.co.vermore.jsonparse;

import jp.co.vermore.common.mvc.BaseJsonParse;

/**
 * EventJsonParse
 * Created by xieyoujun.
 * <p>
 * DateTime: 2018/03/29 12:35
 * Copyright: sLab, Corp
 */

public class SystemConfJsonParse extends BaseJsonParse {

    private String setting;

    private String value;

    public String getSetting() {
        return setting;
    }

    public void setSetting(String setting) {
        this.setting = setting;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
