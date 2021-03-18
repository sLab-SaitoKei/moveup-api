package jp.co.vermore.mapper;

import jp.co.vermore.entity.ReportDetail;

import java.util.List;

public interface ReportDetailMapper {

    int insertDetailReport(ReportDetail reportDetail);

    int deleteDetailReport(ReportDetail reportDetail);

    int updateDetailReport(ReportDetail reportDetail);

    String getReportDetail(long id);

    List<ReportDetail> getReportDetailAll(Long id);

    ReportDetail getStudioReportDetail(Long id);
}