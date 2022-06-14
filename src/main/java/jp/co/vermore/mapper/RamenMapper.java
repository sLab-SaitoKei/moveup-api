package jp.co.vermore.mapper;

import jp.co.vermore.entity.Ramen;
import jp.co.vermore.form.admin.RamenListForm;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface RamenMapper {

    int insertRamen(Ramen ramen);

    int deleteRamen(Ramen ramen);

    int updateRamen(Ramen ramen);

    Ramen getRamenByUuid(String uuid);

    List<Ramen> getRamenAll();

    List<Ramen> getRamenAllForTop( String nowMin,String nextMin);

    List<Ramen> getRamenJsonAll(int type,String nowMin,String nextMin,int limit, int offset);

    List<Ramen> getRamenJsonAllByType(int type,String nowMin,String nextMin);

    Ramen getRamenByIdAndType(long id,int type);

    List<Ramen> getRamenEventAll(int type1,int type2,String tomorrow,String today,int limit, int offset);

    List<Ramen> getRamenList(long id);

    List<Ramen> getRamenPre(Date date, String nowMin,String nextMin);

    List<Ramen> getRamenNext(Date date,String nowMin,String nextMin);

    List<Ramen> getRamenCategory(int type,int limit,int offset);

    List<Ramen> getRamenAllByCondition(RamenListForm form);

    int getRamenCountByCondition(RamenListForm form);

    int getRamenCount();

    List<Ramen> getStudioRamenList(int type, int sortScore, String tomorrow,String today);

    List<Ramen> getStudioRamenListAll(Byte type, int limit, int offset);

    List<Ramen> getStudioRamenALL(int type);

    List<Ramen> getStudioAllByCondition(RamenListForm form);

    int getStudioCountByCondition(RamenListForm form);

    int getStudioCount();

    Ramen getRamenById(@Param("id") Long id);

}