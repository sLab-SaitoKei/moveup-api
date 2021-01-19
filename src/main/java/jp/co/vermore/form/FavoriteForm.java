package jp.co.vermore.form;

import jp.co.vermore.common.mvc.BaseForm;

import java.util.Date;

/**
 * FavoriteForm
 * Created by wubin.
 *
 * DateTime: 2018/05/17 15:00
 * Copyright: sLab, Corp
 */

public class FavoriteForm extends BaseForm {

    private Long id;

    private Long favId;

    private Integer type;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFavId() {
        return favId;
    }

    public void setFavId(Long favId) {
        this.favId = favId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
