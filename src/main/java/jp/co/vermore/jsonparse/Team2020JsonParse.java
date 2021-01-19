package jp.co.vermore.jsonparse;

import jp.co.vermore.common.mvc.BaseEntity;
import jp.co.vermore.common.mvc.BaseJsonParse;

import java.util.List;

public class Team2020JsonParse extends BaseJsonParse {

    private Long id;

    private String name;

    private List<String> pic;


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

    public List<String> getPic() {
        return pic;
    }

    public void setPic(List<String> pic) {
        this.pic = pic;
    }
}