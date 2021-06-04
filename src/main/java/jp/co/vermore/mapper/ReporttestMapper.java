package jp.co.vermore.mapper;

import jp.co.vermore.entity.Reporttest;
import jp.co.vermore.form.admin.ReporttestListForm;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ReporttestMapper {

    int insertReporttest(Reporttest reporttest);

    int deleteReporttest(Reporttest reporttest);

    int updateReporttest(Reporttest reporttest);

    Reporttest getReporttestByUuid(String uuid);

    List<Reporttest> getReporttestAll();

    List<Reporttest> getReporttestAllForTop( String nowMin,String nextMin);

    List<Reporttest> getReporttestJsonAll(int type,String nowMin,String nextMin,int limit, int offset);

    List<Reporttest> getReporttestJsonAllByType(int type,String nowMin,String nextMin);

    Reporttest getReporttestByIdAndType(long id,int type);

    List<Reporttest> getReporttestEventAll(int type1,int type2,String tomorrow,String today,int limit, int offset);

    List<Reporttest> getReporttestList(long id);

    List<Reporttest> getReporttestPre(Date date, String nowMin,String nextMin);

    List<Reporttest> getReporttestNext(Date date,String nowMin,String nextMin);

    List<Reporttest> getReporttestCategory(int type,int limit,int offset);

    List<Reporttest> getReporttestAllByCondition(ReporttestListForm form);

    int getReporttestCountByCondition(ReporttestListForm form);

    int getReporttestCount();

    List<Reporttest> getStudioReporttestList(int type, int sortScore, String tomorrow,String today);

    List<Reporttest> getStudioReporttestListAll(Byte type, int limit, int offset);

    List<Reporttest> getStudioReporttestALL(int type);

    List<Reporttest> getStudioAllByCondition(ReporttestListForm form);

    int getStudioCountByCondition(ReporttestListForm form);

    int getStudioCount();

    Reporttest getReporttestById(@Param("id") Long id);

}