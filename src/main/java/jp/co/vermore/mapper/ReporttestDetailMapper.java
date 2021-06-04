package jp.co.vermore.mapper;

import jp.co.vermore.entity.ReporttestDetail;

import java.util.List;

public interface ReporttestDetailMapper {

    int insertDetailReporttest(ReporttestDetail reporttestDetail);

    int deleteDetailReporttest(ReporttestDetail reporttestDetail);

    int updateDetailReporttest(ReporttestDetail reporttestDetail);

    String getReporttestDetail(long id);

    List<ReporttestDetail> getReporttestDetailAll(Long id);

    ReporttestDetail getStudioReporttestDetail(Long id);
}