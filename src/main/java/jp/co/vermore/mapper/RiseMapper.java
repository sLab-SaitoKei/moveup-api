package jp.co.vermore.mapper;

import java.util.List;

import jp.co.vermore.entity.Rise;

public interface RiseMapper {

    int insertRise(Rise rise);

    List<Rise> getRiseAllForAdmin();

    List<Rise> getRiseAll(int limit, int offset);

    List<Rise> getRiseForTop();

    List<Rise> getRiseByEntity(Rise entity);

    List<Rise> getRiseAllByCategory(String category,int limit, int offset);

}