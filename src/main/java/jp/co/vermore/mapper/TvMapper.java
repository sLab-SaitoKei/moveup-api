package jp.co.vermore.mapper;


import jp.co.vermore.entity.Tv;
import jp.co.vermore.form.admin.TVListForm;

import java.util.List;

public interface TvMapper {

    List<Tv> getTvForTop(String nowMin,String nextMin);

    List<Tv> getTvListAll(int type, String nowMin,String nextMin, int limit, int offset);

    List<Tv> getTvListAllByType(int type, String nowMin,String nextMin);

    List<Tv> getTvList(TVListForm form);

    int getTotalCountFiltered(TVListForm form);

    int getTotalCount();

    Tv getTvByuuid(String uuid);

    Tv getTvFavorite(Long tvId);

    void insert(Tv entity);

    Tv getById(Long id);

    void updateTV(Tv entity);

    void deleteTv(Tv entity);
}