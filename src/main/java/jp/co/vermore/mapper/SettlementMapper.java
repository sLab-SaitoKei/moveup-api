package jp.co.vermore.mapper;

import jp.co.vermore.entity.Settlement;
import jp.co.vermore.form.admin.SettlementEditForm;
import jp.co.vermore.form.admin.SettlementListForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SettlementMapper {


    Long insert(Settlement record);

    List<SettlementListForm> getSettlementAllByMonth(SettlementListForm form);

    int getSettlementCountByMonth(SettlementListForm form);

    int getSettlementCount();

    List<SettlementEditForm> getSettlementEditByMonth(SettlementEditForm form);

    List<SettlementEditForm> getSettlementEditByMonth2(Long shopId , int month);

    List<SettlementListForm> getSettlementAllByMonthForNotify(int month);
}