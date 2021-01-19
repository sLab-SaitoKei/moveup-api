package jp.co.vermore.mapper;

import jp.co.vermore.entity.PaymentCvsHistory;

import java.util.List;

public interface PaymentCvsHistoryMapper {

    int insertSelective(PaymentCvsHistory record);

    List<PaymentCvsHistory> getPaymentCvsHistoryByOrderId(int paymentStatus);

    int updateByOrderId(PaymentCvsHistory record);
}