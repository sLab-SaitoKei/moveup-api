//package jp.co.vermore.service;
//
//import com.gmo_pg.g_pay.client.common.Method;
//import com.gmo_pg.g_pay.client.common.PaymentException;
//import com.gmo_pg.g_pay.client.impl.PaymentClientImpl;
//import com.gmo_pg.g_pay.client.input.*;
//import com.gmo_pg.g_pay.client.output.*;
//import jp.co.vermore.common.Constant;
//import jp.co.vermore.common.mvc.APIException;
//import jp.co.vermore.common.mvc.BaseController;
//import jp.co.vermore.entity.*;
//import jp.co.vermore.form.admin.GoodsPurchaseHistoryListForm;
//import jp.co.vermore.mapper.PaymentCvsHistoryMapper;
//import jp.co.vermore.mapper.PaymentHistoryMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import javax.servlet.ServletException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
///**
// * PayService
// * Created by Wangyan.
// * <p>
// * DateTime: 2018/04/16 15:45
// * Copyright: sLab, Corp
// */
//@Service
//public class PaymentService extends BaseController {
//
//    @Value(value = "${payment.shop.id}")
//    private String shopID;
//
//    @Value(value = "${payment.shop.pass}")
//    private String shopPass;
//
//    @Value(value = "${payment.shop.mail}")
//    private String shopMail;
//
//    @Value(value = "${payment.td.tenant.name}")
//    private String tdTenantName;
//
//    @Value(value = "${payment.job.cd}")
//    private String jobCd;
//
//    @Value(value = "${payment.client.field.flag}")
//    private String clientFieldFlag;
//
//    @Value(value = "${payment.term.day}")
//    private int termDay;
//
//    @Value(value = "${payment.convenience}")
//    private String convenience;
//
//    @Autowired
//    private PaymentHistoryMapper paymentHistoryMapper;
//
//    @Autowired
//    private PaymentCvsHistoryMapper paymentCvsHistoryMapper;
//
//    public PaymentExecTran doPaymentEntryTran(PaymentEntryTran paymentEntryTran){
//        PaymentExecTran paymentExecTran = new PaymentExecTran();
////        PaymentClientImpl client = new PaymentClientImpl();
////        EntryTranInput input = new EntryTranInput();
////        EntryTranOutput output = null;
////
////        input.setShopId(shopID);
////        input.setShopPass(shopPass);
////        input.setTdTenantName(tdTenantName);
////        input.setJobCd(jobCd);
////        input.setOrderId(paymentEntryTran.getOrderID());
////        input.setAmount(paymentEntryTran.getAmount() == null
////                ? null : Integer.valueOf(paymentEntryTran.getAmount()));
////        input.setTax(paymentEntryTran.getTax() == null
////                ? null : Integer.valueOf(paymentEntryTran.getTax()));
////        logger.debug("PaymentEntryTranデータの積載完了,番号=" + paymentEntryTran.getOrderID());
////        try {
////            output = client.doEntryTran(input);
////            logger.debug("PaymentEntryTran実行している,情報のフィードバック,番号=" + paymentEntryTran.getOrderID());
////            paymentExecTran.setAccessID(output.getAccessId());
////            paymentExecTran.setAccessPass(output.getAccessPass());
////            paymentExecTran.setOrderID(paymentEntryTran.getOrderID());
////            logger.debug("PaymentExecTranデータの積載完了,番号=" + paymentEntryTran.getOrderID());
////        } catch (PaymentException e) {
////            logger.error("支払いが問題になる,番号=" + paymentEntryTran.getOrderID());
////        }
//        return paymentExecTran;
//    }
//
//    public PaymentReceipt doPaymentExecTran(PaymentExecTran paymentExecTran){
//        PaymentReceipt paymentReceipt = new PaymentReceipt();
////        PaymentClientImpl client = new PaymentClientImpl();
////        ExecTranInput input = new ExecTranInput();
////        ExecTranOutput output = null;
////
////
////        input.setHttpAccept(paymentExecTran.getHttpAccept());
////        input.setHttpUserAgent(paymentExecTran.getHttpUserAgent());
////        input.setAccessId(paymentExecTran.getAccessID());
////        input.setAccessPass(paymentExecTran.getAccessPass());
////        input.setOrderId(paymentExecTran.getOrderID());
////        input.setMethod(Method.IKKATU);
////        input.setCardNo(paymentExecTran.getCardNo());
////        input.setExpire(paymentExecTran.getExpire());
////        input.setSecurityCode(paymentExecTran.getSecurityCode());
////        input.setClientField1(paymentExecTran.getGoodsID());
////        input.setClientField2(paymentExecTran.getName());
////        input.setClientFieldFlag(clientFieldFlag);
////        logger.debug("PaymentExecTranデータの積載完了,番号=" + paymentExecTran.getOrderID());
////        try {
////            output = client.doExecTran(input);
////            logger.debug("PaymentExecTran実行している,情報のフィードバック,番号=" + paymentExecTran.getOrderID());
////            paymentReceipt.setOrderID(output.getOrderId());
////            paymentReceipt.setMethod(output.getMethod());
////            paymentReceipt.setForward(output.getForward());
////            paymentReceipt.setApprove(output.getApprove());
////            paymentReceipt.setAccessID(paymentExecTran.getAccessID());
////            paymentReceipt.setAccessPass(paymentExecTran.getAccessPass());
////            paymentReceipt.setTranID(output.getTranId());
////            paymentReceipt.setTranDate(output.getTranDate());
////            paymentReceipt.setCheckString(output.getCheckString());
////            paymentReceipt.setGoodsID(output.getClientField1());
////            paymentReceipt.setErrList(output.getErrList());
////            logger.debug("PaymentReceiptデータの積載完了,番号=" + paymentExecTran.getOrderID());
////        } catch (PaymentException e) {
////            logger.error("支払いが問題になる,番号=" + paymentExecTran.getOrderID());
////        }
//        return paymentReceipt;
//    }
//
//    public PaymentReceipt SearchTrade(String orderId){
//        PaymentReceipt paymentReceipt = new PaymentReceipt();
////        PaymentClientImpl client = new PaymentClientImpl();
////        SearchTradeInput input = new SearchTradeInput();
////        SearchTradeOutput output = null;
////
////        input.setShopId(shopID);
////        input.setShopPass(shopPass);
////        input.setOrderId(orderId);
////        try {
////            output = client.doSearchTrade(input);
////            paymentReceipt.setOrderID(output.getOrderId());
////            paymentReceipt.setAccessID(output.getAccessId());
////            paymentReceipt.setAccessPass(output.getAccessPass());
////            paymentReceipt.setErrList(output.getErrList());
////            logger.debug("検索終了,番号=" + orderId);
////        } catch (PaymentException e) {
////            logger.debug("注文は存在しません,番号=" + orderId);
////            PaymentErrList err = new PaymentErrList();
////            err.setErrCode("NULL");
////            err.setErrInfo("NULL");
////            List<PaymentErrList> errList = new ArrayList<>();
////            errList.add(err);
////            paymentReceipt.setErrList(errList);
////        }
//
//        return paymentReceipt;
//    }
//
//    public List<PaymentHistory> getPaymentHistory(String orderId){
//        List<PaymentHistory> paymentHistory = paymentHistoryMapper.getPaymentHistory(orderId);
//        return paymentHistory;
//    }
//
//    public List<PaymentHistory> getPaymentHistoryByUserId(Long userId){
//        List<PaymentHistory> paymentHistory = paymentHistoryMapper.getPaymentHistoryByUserId(userId);
//        return paymentHistory;
//    }
//
//    public PaymentHistory doAlterTran(String orderId){
//        PaymentHistory paymentHistory = paymentHistoryMapper.getPaymentHistoryByArea(orderId);
////        PaymentClientImpl client = new PaymentClientImpl();
////        AlterTranInput input = new AlterTranInput();
////        AlterTranOutput output = null;
////
////        // パラメータの設定
////        input.setShopId(shopID);
////        input.setShopPass(shopPass);
////        input.setAccessId(paymentHistory.getAccessID());
////        input.setAccessPass(paymentHistory.getAccessPass());
////        input.setJobCd("VOID");
////        logger.debug("AlterTranデータの積載完了,番号=" + orderId);
////        try {
////            // API呼び出し
////            output = client.doAlterTran(input);
////            logger.debug("AlterTran実行している,情報のフィードバック,番号=" + orderId);
////            paymentHistory.setAccessID(output.getAccessId());
////            paymentHistory.setAccessPass(output.getAccessPass());
////            paymentHistory.setForward(output.getForward());
////            paymentHistory.setApprove(output.getApprove());
////            paymentHistory.setTranId(output.getTranId());
////            paymentHistory.setTranDate(output.getTranDate());
////            logger.debug("AlterTranデータの積載完了,番号=" + orderId);
////        } catch (PaymentException e) {
////            //
////            // [注意事項]
////            // エラーが発生した場合は、お手数ですが加盟店様側でエラー処理を行っていただくようお願い致します。
////            //
////            e.printStackTrace();
////            logger.error("返品する問題になる,番号=" + orderId);
////        }
//        return paymentHistory;
//    }
//
//    public PaymentExecTranCvs doPaymentEntryTranCvs(PaymentEntryTran paymentEntryTran){
//        PaymentExecTranCvs paymentExecTranCvs = new PaymentExecTranCvs();
////        PaymentClientImpl client = new PaymentClientImpl();
////        EntryTranCvsInput input = new EntryTranCvsInput();
////        EntryTranCvsOutput output = null;
////
////        // パラメータの設定
////        input.setShopId(shopID);
////        input.setShopPass(shopPass);
////        input.setOrderId(paymentEntryTran.getOrderID());
////        input.setAmount(
////                paymentEntryTran.getAmount() == null
////                        ? null : Integer.valueOf(paymentEntryTran.getAmount()));
////        input.setTax(
////                paymentEntryTran.getTax() == null
////                        ? null : Integer.valueOf(paymentEntryTran.getTax()));
////        logger.debug("PaymentEntryTranデータの積載完了,番号=" + paymentEntryTran.getOrderID());
////        try {
////            // API呼び出し
////            output = client.doEntryTranCvs(input);
////            logger.debug("PaymentEntryTran実行している,情報のフィードバック,番号=" + paymentEntryTran.getOrderID());
////            paymentExecTranCvs.setAccessID(output.getAccessId());
////            paymentExecTranCvs.setAccessPass(output.getAccessPass());
////            paymentExecTranCvs.setOrderID(paymentEntryTran.getOrderID());
////            logger.debug("PaymentExecTranCvsデータの積載完了,番号=" + paymentEntryTran.getOrderID());
////        } catch (PaymentException e) {
////            //
////            // [注意事項]
////            // エラーが発生した場合は、お手数ですが加盟店様側でエラー処理を行っていただくようお願い致します。
////            //
////            e.printStackTrace();
////            logger.error("支払いが問題になる,番号=" + paymentExecTranCvs.getOrderID());
////        }
//        return paymentExecTranCvs;
//    }
//
//    public PaymentReceiptCvs doPaymentExecTranCvs(PaymentExecTranCvs paymentExecTranCvs){
//        PaymentReceiptCvs paymentReceiptCvs = new PaymentReceiptCvs();
////        PaymentClientImpl client = new PaymentClientImpl();
////        ExecTranCvsInput input = new ExecTranCvsInput();
////        ExecTranCvsOutput output = null;
////
////        // パラメータの設定
////        input.setAccessId(paymentExecTranCvs.getAccessID());
////        input.setAccessPass(paymentExecTranCvs.getAccessPass());
////        input.setOrderId(paymentExecTranCvs.getOrderID());
////        input.setShopMailAddress(shopMail);
////        input.setConvenience(convenience);
////        input.setCustomerName(paymentExecTranCvs.getCustomerName());
////        input.setCustomerKana(paymentExecTranCvs.getCustomerKana());
////        input.setTelNo(paymentExecTranCvs.getTelNo());
////        input.setPaymentTermDay(termDay);
////        input.setMailAddress(paymentExecTranCvs.getMail());
////        input.setReceiptsDisp11("お問合せ先");
////        input.setReceiptsDisp12("9012345678");
////        input.setReceiptsDisp13("09:00-18:00");
////        input.setClientField1(paymentExecTranCvs.getGoodsID());
////        input.setClientFieldFlag(clientFieldFlag);
////        logger.debug("PaymentExecTranCvsデータの積載完了,番号=" + paymentExecTranCvs.getOrderID());
////        try {
////            // API呼び出し
////            output = client.doExecTranCvs(input);
////            logger.debug("PaymentExecTranCvs実行している,情報のフィードバック,番号=" + paymentExecTranCvs.getOrderID());
////            paymentReceiptCvs.setOrderID(output.getOrderId());
////            paymentReceiptCvs.setAccessId(paymentExecTranCvs.getAccessID());
////            paymentReceiptCvs.setAccessPass(paymentExecTranCvs.getAccessPass());
////            paymentReceiptCvs.setConvenience(output.getConvenience());
////            paymentReceiptCvs.setConfNo(output.getConfNo());
////            paymentReceiptCvs.setPaymentTerm(output.getPaymentTerm());
////            paymentReceiptCvs.setReceiptNo(output.getReceiptNo());
////            paymentReceiptCvs.setReceiptUrl(output.getReceiptUrl());
////            paymentReceiptCvs.setTranDate(output.getTranDate());
////            paymentReceiptCvs.setGoodsID(output.getClientField1());
////            paymentReceiptCvs.setCheckString(output.getCheckString());
////            paymentReceiptCvs.setErrList(output.getErrList());
////            logger.debug("PaymentReceiptCvsデータの積載完了,番号=" + paymentExecTranCvs.getOrderID());
////        } catch (PaymentException e) {
////            //
////            // [注意事項]
////            // エラーが発生した場合は、お手数ですが加盟店様側でエラー処理を行っていただくようお願い致します。
////            //
////            e.printStackTrace();
////            logger.error("支払いが問題になる,番号=" + paymentExecTranCvs.getOrderID());
////        }
//        return paymentReceiptCvs;
//    }
//
//
//    public String SearchTradeCvsForBatch(String orderId){
////        PaymentReceiptCvs paymentReceiptCvs = new PaymentReceiptCvs();
//        PaymentClientImpl client = new PaymentClientImpl();
//        SearchTradeMultiInput input = new SearchTradeMultiInput();
//        SearchTradeMultiOutput output = null;
//        String status = "";
//
//        input.setShopId(shopID);
//        input.setShopPass(shopPass);
//        input.setOrderId(orderId);
//        input.setPayType("3"); // 3：コンビニ
//        try {
//            output = client.doSearchTradeMulti(input);
//            status = output.getStatus();
//            logger.debug("検索終了,番号=" + orderId);
//        } catch (PaymentException e) {
//            logger.debug("注文は存在しません,番号=" + orderId);
////            PaymentErrList err = new PaymentErrList();
////            err.setErrCode("NULL");
////            err.setErrInfo("NULL");
////            List<PaymentErrList> errList = new ArrayList<>();
////            errList.add(err);
////            paymentReceiptCvs.setErrList(errList);
//        }
//        return status;
//    }
//
//
//
//    public Long insertPaymentHistory (PaymentReceipt receipt){
//        PaymentHistory entity = new PaymentHistory();
////        entity.setAmount(receipt.getAmount());
////        entity.setUserId(Long.valueOf(receipt.getUserID()));
////        entity.setGoodsId(Long.valueOf(receipt.getGoodsID()));
////        entity.setPaymentType(receipt.getPaymentType());
////        entity.setForward(receipt.getForward());
////        entity.setMethod(receipt.getMethod());
////        entity.setApprove(receipt.getApprove());
////        entity.setAccessID(receipt.getAccessID());
////        entity.setAccessPass(receipt.getAccessPass());
////        entity.setOrderId(receipt.getOrderID());
////        entity.setTranId(receipt.getTranID());
////        entity.setTranDate(receipt.getTranDate());
////        entity.setCheckString(receipt.getCheckString());
////        entity.setUpdateDatetime(new Date(System.currentTimeMillis()));
////        try{
////            paymentHistoryMapper.insertSelective(entity);
////        }catch (Exception e){
////            logger.error("支払いを成功させ、メッセージの保存エラー,番号=" + receipt.getOrderID());
////        }
//        return entity.getId();
//    }
//
//    public Long insertPaymentHistoryCvs (PaymentReceiptCvs receipt){
//        PaymentCvsHistory entity = new PaymentCvsHistory();
////        entity.setAmount(receipt.getAmount());
////        entity.setUserId(Long.valueOf(receipt.getUserID()));
////        entity.setGoodsId(Long.valueOf(receipt.getGoodsID()));
////        entity.setOrderId(receipt.getOrderID());
////        entity.setAccessId(receipt.getAccessId());
////        entity.setAccessPass(receipt.getAccessPass());
////        entity.setPaymentStatus(receipt.getPaymentStatus());
////        entity.setConvenience(receipt.getConvenience());
////        entity.setConfNo(receipt.getConfNo());
////        entity.setReceiptNo(receipt.getReceiptNo());
////        entity.setReceiptUrl(receipt.getReceiptUrl());
////        entity.setTranDate(receipt.getTranDate());
////        entity.setPaymentTerm(receipt.getPaymentTerm());
////        entity.setCheckString(receipt.getCheckString());
////        paymentCvsHistoryMapper.insertSelective(entity);
//        return entity.getId();
//    }
//
//    public List<PaymentHistory> getPaymentHistoryByCondition (GoodsPurchaseHistoryListForm form){
//        return paymentHistoryMapper.getPaymentHistoryByCondition(form);
//    }
//
//    public int getPaymentHistoryCountByCondition (GoodsPurchaseHistoryListForm form){
//        return paymentHistoryMapper.getPaymentHistoryCountByCondition(form);
//    }
//
//    public int getPaymentHistoryCount (){
//        return paymentHistoryMapper.getPaymentHistoryCount();
//    }
//
//    public PaymentHistory getPaymentHistoryByCondition (Long id){
//        return paymentHistoryMapper.getPaymentHistoryById(id);
//    }
//
//    public int updatePaymentHistory (String orderId,int status){
//        return paymentHistoryMapper.updatePaymentHistory(orderId,status);
//    }
//
//    public List<PaymentCvsHistory> getPaymentCvsHistoryByOrderId(int paymentStatus){
//        return paymentCvsHistoryMapper.getPaymentCvsHistoryByOrderId(paymentStatus);
//    }
//
//    public int updateByOrderId (PaymentCvsHistory entity){
//        return paymentCvsHistoryMapper.updateByOrderId(entity);
//    }
//
//}
