package jp.co.vermore.mapper;

import jp.co.vermore.entity.Report;
import jp.co.vermore.form.admin.ReportListForm;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ReportMapper {

    int insertReport(Report report);

    int deleteReport(Report report);

    int updateReport(Report report);

    Report getReportByUuid(String uuid);

    List<Report> getReportAll();
    
    List<Report> getReportAllForTop( String nowMin,String nextMin);

    List<Report> getReportJsonAll(int type,String nowMin,String nextMin,int limit, int offset);

    List<Report> getReportJsonAllByType(int type,String nowMin,String nextMin);

    Report getReportByIdAndType(long id,int type);

    List<Report> getReportEventAll(int type1,int type2,String tomorrow,String today,int limit, int offset);

    List<Report> getReportList(long id);

    List<Report> getReportPre(Date date, String nowMin,String nextMin);

    List<Report> getReportNext(Date date,String nowMin,String nextMin);

    List<Report> getReportCategory(int type,int limit,int offset);

    List<Report> getReportAllByCondition(ReportListForm form);

    int getReportCountByCondition(ReportListForm form);

    int getReportCount();

    List<Report> getStudioReportList(int type, int sortScore, String tomorrow,String today);

    List<Report> getStudioReportListAll(Byte type, int limit, int offset);

    List<Report> getStudioReportALL(int type);

    List<Report> getStudioAllByCondition(ReportListForm form);

    int getStudioCountByCondition(ReportListForm form);

    int getStudioCount();

    Report getReportById(@Param("id") Long id);

}