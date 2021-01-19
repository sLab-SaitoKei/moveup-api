package jp.co.vermore.entity;

import jp.co.vermore.common.mvc.BaseEntity;

/**
 * Created by xieyoujun on 2017/11/20.
 */
public class Demo extends BaseEntity {

    private Long id;

    private String name;

    public Demo(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Demo(long i, String s) {
        id = i;
        name = s;
    }

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
