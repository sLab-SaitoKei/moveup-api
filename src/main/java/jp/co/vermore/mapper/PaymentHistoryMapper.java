package jp.co.vermore.mapper;

import jp.co.vermore.entity.PaymentHistory;
import jp.co.vermore.form.admin.GoodsPurchaseHistoryListForm;

import java.util.List;

public interface PaymentHistoryMapper {

    int insertSelective(PaymentHistory record);

    PaymentHistory getPaymentHistoryById(Long id);

    PaymentHistory getPaymentHistoryByArea(String orderId);

    List<PaymentHistory> getPaymentHistory(String orderId);

    List<PaymentHistory> getPaymentHistoryByUserId(Long userId);

    List<PaymentHistory> getPaymentHistoryByCondition(GoodsPurchaseHistoryListForm form);

    int getPaymentHistoryCountByCondition(GoodsPurchaseHistoryListForm form);

    int getPaymentHistoryCount();

    int updatePaymentHistory(String orderId,int status);

}