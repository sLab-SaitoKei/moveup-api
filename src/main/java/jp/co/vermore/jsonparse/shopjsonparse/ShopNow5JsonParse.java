package jp.co.vermore.jsonparse.shopjsonparse;

import jp.co.vermore.common.mvc.BaseJsonParse;

/**
 * ShopNow5JsonParse
 * Created by wubin.
 *
 * DateTime: 2018/04/20 12:51
 * Copyright: sLab, Corp
 */

public class ShopNow5JsonParse extends BaseJsonParse {

    private String time ;

    private String content;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
