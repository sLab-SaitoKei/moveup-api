//package jp.co.vermore.controller;
//
//import jp.co.vermore.common.Constant;
//import jp.co.vermore.common.JsonObject;
//import jp.co.vermore.common.mvc.BaseController;
//import jp.co.vermore.common.util.DateUtil;
//import jp.co.vermore.common.util.StringUtil;
//import jp.co.vermore.entity.*;
//import jp.co.vermore.form.GoodsPurchaseMailForm;
//import jp.co.vermore.form.PdfForm;
//import jp.co.vermore.form.PurchaseGoodsInfoForm;
//import jp.co.vermore.form.PushForm;
//import jp.co.vermore.form.admin.SettlementEditForm;
//import jp.co.vermore.form.admin.SettlementListForm;
//import jp.co.vermore.service.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Controller;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.TransactionDefinition;
//import org.springframework.transaction.TransactionStatus;
//import org.springframework.transaction.support.DefaultTransactionDefinition;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.text.DecimalFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//
//import static jp.co.vermore.common.util.DateUtil.getDaysOfMonth;
//
///**
// * RiseController
// * Created by Wangyan.
// * <p>
// * DateTime: 2018/02/28 17:44
// * Copyright: sLab, Corp
// */
//@Controller
//public class BatchController extends BaseController {
//
//    @Value(value = "${hosturl}")
//    private String hosturl;
//
//    @Autowired
//    private BatchService batchService;
//
//    @Autowired
//    private ShopCouponService shopCouponService;
//
//    @Autowired
//    private ShopNowgoService shopNowgoService;
//
//    @Autowired
//    private ShopService shopService;
//
//    @Autowired
//    private AdminService adminService;
//
//    @Autowired
//    private PersonService personService;
//
//    @Autowired
//    private NotificationService notificationService;
//
//    @Autowired
//    private SettlementService settlementService;
//
//    @Autowired
//    private FavDetailService favDetailService;
//
//    @Autowired
//    private RandomShopService randomShopService;
//
//    @Autowired
//    private PlaceService placeService;
//
//    @Autowired
//    private RecruitService recruitService;
//
//    @Autowired
//    private AuthService authService;
//
//    @Autowired
//    private PaymentService paymentService;
//
//    @Autowired
//    private GoodsService goodsService;
//
//    @Autowired
//    private AWSService awsService;
//
//    @Autowired
//    private SystemConfService systemConfService;
//
//    @Autowired
//    PlatformTransactionManager txManager;
//
//
//    @RequestMapping(value = "/batch/settlement/shop/coupon/", method = RequestMethod.GET)
//    @ResponseBody
//    public JsonObject batchSettlementShopCoupon() throws Exception {
//        int amount = 0;
//        boolean status = queryBatchWorkingState(Constant.BATCH_TYPE.COUPON);
//        if (status) {
//            BatchStatus batch = new BatchStatus();
//            batch.setBatchType(Constant.BATCH_TYPE.COUPON);
//            batch.setStatus(Constant.BATCH_STATUS.NOT_BEGINNING);
//            Long id = batchService.insertBatch(batch);
//            batch.setId(id);
//            logger.info("Coupon settlement batch is ready to start work! batch id=" + id);
//            batchStart(batch);
//            //coupon price 500
//            SystemConf systemConf = systemConfService.getSystemConfByKey(Constant.SYS_CONF.COUPON_PRICE_DAY);
//            if(systemConf != null){
//                amount = systemConf.getValue();
//                logger.debug("----1----amount=" + amount);
//            }
//            //get lastmonth year month day
//            String lastMonth = DateUtil.getLastDate(DateUtil.getYyyyMMdd());
//            logger.debug("----1----lastMonth=" + lastMonth);
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            int dayNumLastMonth = DateUtil.getDaysOfMonth(sdf.parse(lastMonth+"-"+"01"));
//            logger.debug("----1----dayNumLastMonth=" + dayNumLastMonth);
//            for(int i = 1 ; i <= dayNumLastMonth ; i ++){
//                List<Long> shopIdList = shopCouponService.getShopIdListForBatch(lastMonth+"-"+i);
//                logger.info("A total of (" + shopIdList.size() + ") data have been obtained , Start generating settlement!");
//                if (shopIdList.size() > 0) {
//                    for (Long shopId : shopIdList) {
//                        List<ShopCoupon> couponList = shopCouponService.getShopCouponListForBatch(shopId,lastMonth+"-"+i);
//                        logger.debug("----2----couponList=" + couponList.size());
//                        int index = 0;
//                        for (ShopCoupon coupon : couponList) {
//                            Settlement settlement = new Settlement();
//                            List<AdminCustomer> adminCustomer = adminService.selectByShopIdAndType(shopId,Constant.CUSTOMER.CUSTOMER_SHOP);
//                            if (adminCustomer.size() > 0) {
//                                settlement.setCustomerId(adminCustomer.get(0).getId());
//                            } else {
//                                continue;
//                            }
//                            settlement.setItemType(Constant.SETTLEMENT_ITEM_TYPE.SHOP);
//                            settlement.setItemId(shopId);
//                            settlement.setType(Constant.SETTLEMENT_TYPE.COUPON);
//                            if (index == 0) {
//                                settlement.setAmount(0);
//                            } else {
//                                settlement.setAmount(amount);
//                            }
//                            int yearMonth ;
////                            String yyyymmdd = DateUtil.dateToStringyyyyMMddForSerialNumber(new Date(System.currentTimeMillis()));
////                            String yyyymm = DateUtil.dateToStringyyyyMM(new Date(System.currentTimeMillis()));
////                            if(yyyymmdd.charAt(6)=='0' && yyyymmdd.charAt(7)=='1'){
////                                if (yyyymm.charAt(4) == '0' && yyyymm.charAt(5) == '1') {
////                                    yearMonth = Integer.valueOf(yyyymm) - 89;
////                                } else {
////                                    yearMonth = Integer.valueOf(yyyymm) - 1;
////                                }
////                                settlement.setYyyymm(Integer.valueOf(yearMonth));
////                            }else{
////                                settlement.setYyyymm(Integer.valueOf(lastMonth));
////                            }
//                            Date creatDate = DateUtil.stringToDateyyyy_MM_dd(lastMonth+"-"+i);
//                            settlement.setYyyymm(Integer.valueOf(lastMonth.substring(0,4)+lastMonth.substring(5,7)));
//                            settlement.setStatus(Constant.SETTLEMENT_STATUS.UNSETTLED);
//                            settlement.setMethod(Constant.SETTLEMENT_METHOD.UNSETTLED);
//                            settlement.setTitle(coupon.getTitle());
//                            settlement.setContent(coupon.getDesc());
//                            settlement.setUpdateDatetime(creatDate);
//                            Long settlementId = settlementService.insertSettlement(settlement);
//                            logger.info("Coupon settlement has been generated! settlementId=" + settlementId +
//                                    ", CustomerId=" + settlement.getCustomerId() +
//                                    ", ShopId=" + settlement.getItemId() +
//                                    ", Amount=" + settlement.getAmount() +
//                                    ", YearMonth=" + settlement.getYyyymm()
//                            );
//                            index++;
//                        }
//                    }
//                }
//            }
//            batchFinish(batch);
//            logger.debug("----20----batchFinish" + id);
//        }
//        return jsonObject;
//    }
//
//
//    @RequestMapping(value = "/batch/settlement/shop/monthly/", method = RequestMethod.GET)
//    @ResponseBody
//    public JsonObject batchSettlementShopMonthlyExpenses() throws Exception {
//        int amount = 0;
//        boolean status = queryBatchWorkingState(Constant.BATCH_TYPE.SHOP_MONTHLY_EXPENSES);
//        if (status) {
//            BatchStatus batch = new BatchStatus();
//            batch.setBatchType(Constant.BATCH_TYPE.SHOP_MONTHLY_EXPENSES);
//            batch.setStatus(Constant.BATCH_STATUS.NOT_BEGINNING);
//            Long id = batchService.insertBatch(batch);
//            batch.setId(id);
//            logger.info("Shop monthly expenses settlement batch is ready to start work! batch id=" + id);
//            batchStart(batch);
//
//            SystemConf systemConf = systemConfService.getSystemConfByKey(Constant.SYS_CONF.COUPON_PRICE_MONTH);
//            if(systemConf != null){
//                amount = systemConf.getValue();
//                logger.debug("----1----amount=" + amount);
//            }
//            List<Shop> shopList = shopService.getShopForSettlementBatch();
//            logger.info("A total of (" + shopList.size() + ") data have been obtained , Start generating settlement!");
//            if (shopList.size() > 0) {
//                for (Shop shop : shopList) {
//                    Settlement settlement = new Settlement();
//                    List<AdminCustomer> adminCustomer = adminService.selectByShopIdAndType(shop.getId(),Constant.CUSTOMER.CUSTOMER_SHOP);
//                    if (adminCustomer.size() > 0) {
//                        settlement.setCustomerId(adminCustomer.get(0).getId());
//                    } else {
//                        continue;
//                    }
//                    settlement.setItemType(Constant.SETTLEMENT_ITEM_TYPE.SHOP);
//                    settlement.setItemId(shop.getId());
//                    settlement.setType(Constant.SETTLEMENT_TYPE.MONTHLY_EXPENSES);
//                    settlement.setAmount(amount);
//                    String yyyymm = DateUtil.dateToStringyyyyMM(new Date(System.currentTimeMillis()));
//                    int yearMonth;
//                    if (yyyymm.charAt(4) == '0' && yyyymm.charAt(5) == '1') {
//                        yearMonth = Integer.valueOf(yyyymm) - 89;
//                    } else {
//                        yearMonth = Integer.valueOf(yyyymm) - 1;
//                    }
//                    settlement.setYyyymm(yearMonth);
//                    settlement.setStatus(Constant.SETTLEMENT_STATUS.UNSETTLED);
//                    settlement.setMethod(Constant.SETTLEMENT_METHOD.UNSETTLED);
//                    settlement.setTitle("月間利用料金");
//                    settlement.setContent("月間利用料金");
//                    settlement.setUpdateDatetime(new Date(System.currentTimeMillis()));
//                    Long settlementId = settlementService.insertSettlement(settlement);
//                    logger.info("Shop monthly expenses settlement has been generated! settlementId=" + settlementId +
//                            ", CustomerId=" + settlement.getCustomerId() +
//                            ", ShopId=" + settlement.getItemId() +
//                            ", Amount=" + settlement.getAmount() +
//                            ", YearMonth=" + settlement.getYyyymm()
//                    );
//                }
//            }
//            //PLACE
//            List<Place> placeList = placeService.getPlaceForSettlementBatch();
//            logger.info("A total of (" + placeList.size() + ") data have been obtained , Start generating settlement!");
//            if (placeList.size() > 0) {
//                for (Place place : placeList) {
//                    Settlement settlement = new Settlement();
//                    List<AdminCustomer> adminCustomer = adminService.selectByShopIdAndType(place.getId(),Constant.CUSTOMER.CUSTOMER_PLACE);
//                    if (adminCustomer.size() > 0) {
//                        settlement.setCustomerId(adminCustomer.get(0).getId());
//                    } else {
//                        continue;
//                    }
//                    settlement.setItemType(Constant.SETTLEMENT_ITEM_TYPE.PLACE);
//                    settlement.setItemId(place.getId());
//                    settlement.setType(Constant.SETTLEMENT_TYPE.MONTHLY_EXPENSES);
//                    settlement.setAmount(amount);
//                    String yyyymm = DateUtil.dateToStringyyyyMM(new Date(System.currentTimeMillis()));
//                    int yearMonth;
//                    if (yyyymm.charAt(4) == '0' && yyyymm.charAt(5) == '1') {
//                        yearMonth = Integer.valueOf(yyyymm) - 89;
//                    } else {
//                        yearMonth = Integer.valueOf(yyyymm) - 1;
//                    }
//                    settlement.setYyyymm(yearMonth);
//                    settlement.setStatus(Constant.SETTLEMENT_STATUS.UNSETTLED);
//                    settlement.setMethod(Constant.SETTLEMENT_METHOD.UNSETTLED);
//                    settlement.setTitle("月間利用料金");
//                    settlement.setContent("月間利用料金");
//                    Long settlementId = settlementService.insertSettlement(settlement);
//                    logger.info("Shop monthly expenses settlement has been generated! settlementId=" + settlementId +
//                            ", CustomerId=" + settlement.getCustomerId() +
//                            ", ShopId=" + settlement.getItemId() +
//                            ", Amount=" + settlement.getAmount() +
//                            ", YearMonth=" + settlement.getYyyymm()
//                    );
//                }
//            }
//            //RECRUIT
//            List<Recruit> recruitList = recruitService.getRecruitForSettlementBatch();
//            logger.info("A total of (" + recruitList.size() + ") data have been obtained , Start generating settlement!");
//            if (recruitList.size() > 0) {
//                for (Recruit recruit : recruitList) {
//                    Settlement settlement = new Settlement();
//                    List<AdminCustomer> adminCustomer = adminService.selectByShopIdAndType(recruit.getId(),Constant.CUSTOMER.CUSTOMER_RECRUIT);
//                    if (adminCustomer.size() > 0) {
//                        settlement.setCustomerId(adminCustomer.get(0).getId());
//                    } else {
//                        continue;
//                    }
//                    settlement.setItemType(Constant.SETTLEMENT_ITEM_TYPE.RECRUIT);
//                    settlement.setItemId(recruit.getId());
//                    settlement.setType(Constant.SETTLEMENT_TYPE.MONTHLY_EXPENSES);
//                    settlement.setAmount(amount);
//                    String yyyymm = DateUtil.dateToStringyyyyMM(new Date(System.currentTimeMillis()));
//                    int yearMonth;
//                    if (yyyymm.charAt(4) == '0' && yyyymm.charAt(5) == '1') {
//                        yearMonth = Integer.valueOf(yyyymm) - 89;
//                    } else {
//                        yearMonth = Integer.valueOf(yyyymm) - 1;
//                    }
//                    settlement.setYyyymm(yearMonth);
//                    settlement.setStatus(Constant.SETTLEMENT_STATUS.UNSETTLED);
//                    settlement.setMethod(Constant.SETTLEMENT_METHOD.UNSETTLED);
//                    settlement.setTitle("月間利用料金");
//                    settlement.setContent("月間利用料金");
//                    Long settlementId = settlementService.insertSettlement(settlement);
//                    logger.info("Shop monthly expenses settlement has been generated! settlementId=" + settlementId +
//                            ", CustomerId=" + settlement.getCustomerId() +
//                            ", ShopId=" + settlement.getItemId() +
//                            ", Amount=" + settlement.getAmount() +
//                            ", YearMonth=" + settlement.getYyyymm()
//                    );
//                }
//            }
//            batchFinish(batch);
//            logger.debug("----20----batchFinish" + id);
//        }
//        return jsonObject;
//    }
//
//    //settlement notify for admin app
//    @RequestMapping(value = "/batch/settlement/notify/admin/", method = RequestMethod.GET)
//    @ResponseBody
//    public JsonObject batchSettlementNotify(HttpServletRequest request) throws Exception {
//
//        boolean status = queryBatchWorkingState(Constant.BATCH_TYPE.SETTLEMENT_NOTIFY);
//        if (status) {
//            BatchStatus batch = new BatchStatus();
//            batch.setBatchType(Constant.BATCH_TYPE.SETTLEMENT_NOTIFY);
//            batch.setStatus(Constant.BATCH_STATUS.NOT_BEGINNING);
//            Long id = batchService.insertBatch(batch);
//            batch.setId(id);
//            logger.info("Settlement Notify  batch is ready to start work! batch id=" + id);
//            batchStart(batch);
//
//            //get settment all by yyyymm
//            String yyyymm = DateUtil.dateToStringyyyyMM(new Date(System.currentTimeMillis()));
//            int yearMonth;
//            if (yyyymm.charAt(4) == '0' && yyyymm.charAt(5) == '1') {
//                yearMonth = Integer.valueOf(yyyymm) - 89;
//            } else {
//                yearMonth = Integer.valueOf(yyyymm) - 1;
//            }
//            String yyyy = Integer.toString(yearMonth).substring(0, 4);
//            String mm = Integer.toString(yearMonth).substring(4);
//            List<SettlementListForm> settlementList = settlementService.getSettlementAllByMonthForNotify(Integer.valueOf(yearMonth));
//
//            // insert notification content data
//            if (settlementList != null && settlementList.size() > 0) {
//                logger.debug("----1----settlementList=" + settlementList.size());
//                DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
//                txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//                TransactionStatus txStatus = txManager.getTransaction(txDefinition);
//
//                try {
//                    Date pushDate = new Date(System.currentTimeMillis());
//                    logger.debug("----4----push time=" + pushDate);
//                    // foreach insert notification data
//                    List<String> tokens = new ArrayList<String>();
//                    for (SettlementListForm st : settlementList) {
//                        String items= "";
//                        String amount= "";
//                        String box = null;
//                        int totlePrice = 0;
//                        Shop shop = shopService.getShopByUuid(st.getShopUUID());
//                        String shopName = shopService.getShopNameByShopIdShop(shop.getId());
//                        List<SettlementEditForm> dataList = settlementService.getSettlementEditByMonth2(shop.getId(), yearMonth);
//                        List<PdfForm> scores = new ArrayList<PdfForm>();
//                        for(SettlementEditForm se :dataList ){
//                            PdfForm pdfForm = new PdfForm();
//                            pdfForm.setTitle(se.getTitle());
//                            pdfForm.setMonth(String.valueOf(yearMonth));
//                            if (se.getType() == 1) {
//                                pdfForm.setType("日");
//                            } else {
//                                pdfForm.setType("月");
//                            }
//                            box = se.getShopName();
//                            amount = String.valueOf(se.getAmount());
//                            pdfForm.setAmount(amount);
//                            scores.add(pdfForm);
//                            amount = StringUtil.getPrice(se.getAmount());
//                            totlePrice = totlePrice + se.getAmount();
//                            items +=  "<tr>"+
//                                            "<td align='center'>"+se.getTitle()+"</td>"+
//                                            "<td align='center'>1</td>"+
//                                            "<td align='center'>"+pdfForm.getType()+"</td>"+
//                                            "<td align='center'>￥"+amount+"</td>"+
//                                            "<td align='center'>&emsp;</td>"+
//                                      "</tr>";
//                        }
//                        String name = box + "_" + yearMonth;
//                        int tax =((int)(totlePrice * 0.08));
//                        String totlePriceStr = StringUtil.getPrice(totlePrice);
//                        String taxStr =  StringUtil.getPrice(tax);
//                        String subtotal = StringUtil.getPrice((totlePrice + tax));
//
//
//                        String title = yearMonth +"ご利用明細";
//                        String content = "<html>" +
//                                "<body>" +
//                                "<div id='bro' style='border: 1px;'>"+
//                                    "<div style='height: 100px;'>"+
//                                        "<div id='tab' style='width: 100%;text-align:left;'>"+
//                                            "<span style='width: 20%;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>"+
//                                            "<span style='text-align:left;'>"+
//                                                "<b>宛名</b>"+
//                                            "</span>&nbsp;&nbsp;"+
//                                            "<span style='font-size: 14px;'>"+shopName+"御中</span>"+
//                                        "</div>"+
//                                        "<hr style='width: 70%;text-align: center;'/>"+
//                                        "<div id='tab' style='width: 100%;text-align:left;'>"+
//                                            "<span style='width: 20%;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>"+
//                                            "<span style='text-align:left;'>"+
//                                                "<b>金額</b>"+
//                                            "</span>&nbsp;&nbsp;"+
//                                            "<span style='font-size: 14px;'>￥"+subtotal+"</span>"+
//                                        "</div>"+
//                                        "<hr style='width: 70%;text-align: center;'/>"+
//                                        "<div id='tab' style='width: 100%;text-align:left;'>"+
//                                            "<span style='width: 20%;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>"+
//                                            "<span style='text-align:left;'>"+
//                                                "<b>但し</b>"+
//                                            "</span>&nbsp;&nbsp;"+
//                                            "<span style='font-size: 14px;'>JAPAN MOVE UP WEST WEB"+yyyy+"年"+mm+"月分 利用料</span>"+
//                                        "</div>"+
//                                        "<hr style='width: 70%;text-align: center;'/>"+
//                                    "</div>"+
//                                    "<p style=' text-align:right;margin-top: 110px;margin-right: 10px;'><h5 align='right'>株式会社HEADLINE WEST&emsp;</h5></p>"+
//                                    "<p style='text-align:right;margin-top: 10px;font-size:12px;'>岡山県岡山市北区大元上町12-14&emsp;</p>"+
//                                    "<p style='text-align:right;margin-top: 10px;font-size:12px;'>Lee building1F&emsp;</p>"+
//                                    "<p style='text-align:right;margin-top: 10px;font-size:12px;'>TEL:086-250-8089／FAX:086-246-0588&emsp;</p>"+
//                                    "<hr style='width: 98%;text-align: center;'/>"+
//                                    "<h4>&emsp;《ご利用明細》</h4>"+
//                                    "<table border='1px' width='100%' align='center' cellspacing='0'  cellpadding='0'>"+
//                                        "<tr>"+
//                                            "<td align='center' width='30%'>項目</td>"+
//                                            "<td align='center'>数量</td>"+
//                                            "<td align='center'>単位</td>"+
//                                            "<td align='center'>金額</td>"+
//                                            "<td align='center'>備考</td>"+
//                                        "</tr>"+items+
//
//                                        "<tr>"+
//                                            "<td border-bottom='0px' colspan='2'></td>"+
//                                            "<td align='center'>小計</td>"+
//                                            "<td align='center'>￥"+totlePriceStr+"</td>"+
//                                            "<td align='center'>&emsp;</td>"+
//                                        "</tr>"+
//                                        "<tr>"+
//                                            "<td border='0px'' colspan='2'></td>"+
//                                            "<td align='center'>消費税(8%)</td>"+
//                                            "<td align='center'>￥"+taxStr+"</td>"+
//                                            "<td align='center'>&emsp;</td>"+
//                                        "</tr>"+
//                                        "<tr>"+
//                                            "<td border='0px' colspan='2' ></td>"+
//                                            "<td align='center'>合計金額</td>"+
//                                            "<td align='center'>￥"+subtotal+"</td>"+
//                                            "<td align='center'>&emsp;</td>"+
//                                        "</tr>"+
//                                    "</table>"+
//                                "<p style='text-align:left;margin-top: 10px;font-size:12px;'>※この領収書は株式会社HEADLINE WESTが電子的に保持している領収情報を画面表示したものです。</p>"+
//                                "<p style='text-align:left;margin-top: 10px;font-size:12px;'>・お引き落とし日：当月利用料⇒翌月23日となります。（土日祝の場合は翌営業日のお引き落としとなります）</p>"+
//                                "</div>"+
//                                "<a href='https://www.japanmoveupwest.website/admin/quote/pdf/"+st.getShopUUID()+"/"+yearMonth+"/'>ダウンロード</a>" +
//                                "</body>" +
//                                "</html>";
//                        Long notifyContentId = notificationService.insertNotificationContent(title,content);
//                        long notificationId = 0L;
//                        if(notifyContentId != null){
//                            logger.debug("----5----notifyContentId=" + notifyContentId);
//                             notificationId = notificationService.insertNotification(st.getCustomerId(), Constant.NOTIFICATION.USER_TYPE_ADMIN, Constant.NOTIFY_ITEM.SETTLEMENT, notifyContentId,
//                                    Constant.NOTIFICATION.NOTIFY_TYPE_PUSH, Constant.NOTIFICATION.NOTIFY_DEVICE_ALL, pushDate);
//                            logger.info("Settlement Notify regist customerId=" + st.getCustomerId());
//                        }
//                        NotificationDevice notificationDeviceForIos = notificationService.getNotificationDeviceByUserId(st.getCustomerId(),Constant.NOTIFICATION.USER_TYPE_ADMIN,Constant.NOTIFICATION.NOTIFY_DEVICE_IOS);
//                        NotificationDevice notificationDeviceForAnd = notificationService.getNotificationDeviceByUserId(st.getCustomerId(),Constant.NOTIFICATION.USER_TYPE_ADMIN,Constant.NOTIFICATION.NOTIFY_DEVICE_ANDROID);
//                        NotificationContent nfc = notificationService.getNotificationContent(notifyContentId);
//                        PushForm pushForm = new PushForm();
//                        pushForm.setId(notificationId);
//                        pushForm.setItemId(0); //0:お知らせ　13:WEB Url other:item uuid
//                        pushForm.setDate(DateUtil.dateToStringyyyymmdd(nfc.getCreateDatetime()));
//                        pushForm.setTitle(yearMonth +"ご利用明細");
//                        pushForm.setContent(content);
//                        pushForm.setShopType(0);
//                        pushForm.setUuid("");
//                        pushForm.setUserId(st.getCustomerId());
//                        pushForm.setUserType(Constant.NOTIFICATION.USER_TYPE_ADMIN);
//                        pushForm.setContentId(notifyContentId);
//                        pushForm.setType(Constant.NOTIFICATION.PUSH_ADMIN);
//                        if (notificationDeviceForIos != null) {
//                            logger.debug("----6----NotificationDevice id=" + notificationDeviceForIos.getId());
//                            String deviceToken = notificationDeviceForIos.getToken();
//                            logger.debug("----7----ios deviceToken=" + deviceToken);
//                            pushForm.setDeviceToken(deviceToken);
//                            int result = notificationService.notifyPush(request,pushForm);
//                            logger.debug("----8----ios settlement push result=" + result);
//                            if(result == 0){
//                                notificationService.updateNotificationPushStatus(notificationId,Constant.NOTIFICATION.PUSH_STATUS_SENT);
//                                logger.debug("----9----update push status notification id=" + notificationId);
//                            }
//                        }
//                        if(notificationDeviceForAnd != null){
//                            logger.debug("----10----NotificationDevice id=" + notificationDeviceForAnd.getId());
//                            String deviceToken = notificationDeviceForAnd.getToken();
//                            logger.debug("----11----android deviceToken=" + deviceToken);
//                            pushForm.setDeviceToken(deviceToken);
//                            int result = notificationService.pushFCMNotification(pushForm);
//                            logger.debug("----12----android settlement push result=" + result);
//                            if(result == 0){
//                                notificationService.updateNotificationPushStatus(notificationId,Constant.NOTIFICATION.PUSH_STATUS_SENT);
//                                logger.debug("----13----update push status notification id=" + notificationId);
//                            }
//                        }
//                    }
//                    txManager.commit(txStatus);
//                } catch (Exception ex) {
//                    txManager.rollback(txStatus);
//                    logger.error(" admin Settlement Notify failed, error=" + ex.getMessage());
//                    logger.error(" admin Settlement Notify, error=" + ex.toString());
//                    ex.printStackTrace();
//                }
//            }
//            batchFinish(batch);
//            logger.debug("----20----batchFinish" + id);
//        }
//        return jsonObject;
//    }
//
//
//    // now4 nofify for web
//    @RequestMapping(value = "/batch/notify/now4/", method = RequestMethod.GET)
//    @ResponseBody
//    public JsonObject batchNotifyNow4(HttpServletRequest request) throws Exception {
//
//        boolean status = queryBatchWorkingState(Constant.BATCH_TYPE.NOTIFY_NOW4);
//        if (status) {
//            BatchStatus batch = new BatchStatus();
//            batch.setBatchType(Constant.BATCH_TYPE.NOTIFY_NOW4);
//            batch.setStatus(Constant.BATCH_STATUS.NOT_BEGINNING);
//            Long id = batchService.insertBatch(batch);
//            batch.setId(id);
//            logger.info("Notify now4 batch is ready to start work! batch id=" + id);
//            batchStart(batch);
//
//            //get user all
//            List<Long> userIdAllList = new ArrayList<Long>();
//            List<Person> getPersonAll = personService.getPersonAll();
//            if (getPersonAll.size() > 0) {
//                for (Person person : getPersonAll) {
//                    userIdAllList.add(person.getUserId());
//                }
//            }
//            logger.debug("----1----all user size=" + userIdAllList.size());
//
//            //get user by setting
//            List<Long> getUserBySetting = new ArrayList<Long>();
//            String settingKey = Constant.USER_SETTING.NOW4;
//            String settingValue = Constant.USER_SETTING.OFF;
//
//            logger.debug("----2----now4 off user size=" + getUserBySetting.size());
//
//            //get user for now4
//            List<Long> getUserForNow4 = new ArrayList<Long>();
//
//            for (Long userAllId : userIdAllList) {
//                if (!getUserBySetting.contains(userAllId)) {
//                    getUserForNow4.add(userAllId);
//                }
//            }
//
//            logger.debug("----3----notify now4 user size=" + getUserForNow4.size());
//
//            // insert notification content data
//            if (getUserForNow4 != null && getUserForNow4.size() > 0) {
//                logger.debug("----4----getUserForNow4=" + getUserForNow4.size());
//                DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
//                txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//                TransactionStatus txStatus = txManager.getTransaction(txDefinition);
//
//                try {
//                    List<ShopCoupon> now4List = shopCouponService.getShopCouponListForNotify();
//                    logger.debug("----5----now4List=" + now4List.size());
//                    Date pushDate = new Date(System.currentTimeMillis());
//                    logger.debug("----6----push time=" + pushDate);
//                    // foreach insert notification data
//                    for (ShopCoupon sc : now4List) {
//                        for (Long userId : getUserForNow4) {
//                            logger.debug("----7----userId=" + userId);
//                            FavDetail fd = favDetailService.getFavDetailByUserId(userId, sc.getShopId(), Constant.FAV_TYPE.SHOP);
//                            UserSetting us = notificationService.getUserSettingBySettingKeyAndSettingValueAndByItemId(userId, Constant.USER_SETTING.NOW4, Constant.USER_SETTING.OFF, sc.getShopId());
//                            if(fd != null && us == null) {
//                                logger.debug("----8----FavDetail id=" + fd.getId());
//                                NotificationDevice notificationDeviceForIos = notificationService.getNotificationDeviceByUserId(userId, Constant.NOTIFICATION.USER_TYPE_USER, Constant.NOTIFICATION.NOTIFY_DEVICE_IOS);
//                                NotificationDevice notificationDeviceForAnd = notificationService.getNotificationDeviceByUserId(userId, Constant.NOTIFICATION.USER_TYPE_USER, Constant.NOTIFICATION.NOTIFY_DEVICE_ANDROID);
//                                Shop s = shopService.getShopById(sc.getShopId());
//                                ShopCoupon shopCoupon = shopCouponService.selectCouponById(sc.getId());
//                                String alert = "「" + s.getName() + "」" + "のNOW4情報が投稿されました！";
//                                PushForm pushForm = new PushForm();
//                                pushForm.setId(0L);
//                                pushForm.setItemId(Constant.NOTIFY_ITEM.NOW4); //0:お知らせ　13:WEB Url other:item uuid
//                                pushForm.setDate(DateUtil.dateToStringyyyymmdd(shopCoupon.getUpdateDatetime()));
//                                pushForm.setTitle(alert);
//                                pushForm.setContent("");
//                                pushForm.setShopType(s.getShopType());
//                                pushForm.setUuid(s.getUuid());
//                                pushForm.setUserId(userId);
//                                pushForm.setUserType(Constant.NOTIFICATION.USER_TYPE_USER);
//                                pushForm.setType(Constant.NOTIFICATION.PUSH_NOW4);
//                                if (notificationDeviceForIos != null) {
//                                    logger.debug("----9----NotificationDevice id=" + notificationDeviceForIos.getId());
//                                    String deviceToken = notificationDeviceForIos.getToken();
//                                    logger.debug("----10----deviceToken=" + deviceToken);
//                                    pushForm.setDeviceToken(deviceToken);
//                                    int result = notificationService.notifyPush(request, pushForm);
//                                    if (result == 0) {
//                                        NotifyNow45 notifyNow45 = new NotifyNow45();
//                                        notifyNow45.setUserId(userId);
//                                        notifyNow45.setItemId(Long.valueOf(sc.getId()));
//                                        notifyNow45.setPushType(Constant.NOTIFY_NOW45.NOTIFY_TYPE_PUSH);
//                                        notifyNow45.setNotifyType(Constant.NOTIFY_NOW45.now4);
//                                        notifyNow45.setPushStatus(Constant.NOTIFY_NOW45.PUSH_STATUS_SENT);
//                                        notifyNow45.setUpdateDatetime(new Date(System.currentTimeMillis()));
//                                        notificationService.updateNotifyNow45(notifyNow45);
//                                        logger.debug("----12----update push status itemId=" + sc.getId());
//                                    }
//                                }
//                                if (notificationDeviceForAnd != null) {
//                                    logger.debug("----13----NotificationDevice id=" + notificationDeviceForAnd.getId());
//                                    String deviceToken = notificationDeviceForAnd.getToken();
//                                    logger.debug("----14----deviceToken=" + deviceToken);
//                                    pushForm.setDeviceToken(deviceToken);
//                                    int result = notificationService.pushFCMNotification(pushForm);
//                                    logger.debug("----16----Android result=" + result);
//                                    if (result == 0) {
//                                        NotifyNow45 notifyNow45 = new NotifyNow45();
//                                        notifyNow45.setUserId(userId);
//                                        notifyNow45.setItemId(Long.valueOf(sc.getId()));
//                                        notifyNow45.setPushType(Constant.NOTIFY_NOW45.NOTIFY_TYPE_PUSH);
//                                        notifyNow45.setNotifyType(Constant.NOTIFY_NOW45.now4);
//                                        notifyNow45.setPushStatus(Constant.NOTIFY_NOW45.PUSH_STATUS_SENT);
//                                        notifyNow45.setUpdateDatetime(new Date(System.currentTimeMillis()));
//                                        notificationService.updateNotifyNow45(notifyNow45);
//                                        logger.debug("----17----update push status itemId=" + sc.getId());
//                                    }
//                                }
//                            }
//                        }
//                    }
//                    txManager.commit(txStatus);
//                } catch (Exception ex) {
//                    txManager.rollback(txStatus);
//                    logger.error(" now4 notification push failed, error=" + ex.getMessage());
//                    logger.error(" now4 notification push, error=" + ex.toString());
//                    ex.printStackTrace();
//                }
//            }
//            batchFinish(batch);
//            logger.debug("----20----batchFinish" + id);
//        }
//        return jsonObject;
//    }
//
//    // now4 nofify for web
//    @RequestMapping(value = "/batch/notify/now5/", method = RequestMethod.GET)
//    @ResponseBody
//    public JsonObject batchNotifyNow5(HttpServletRequest request) throws Exception {
//
//        boolean status = queryBatchWorkingState(Constant.BATCH_TYPE.NOTIFY_NOW5);
//        if (status) {
//            BatchStatus batch = new BatchStatus();
//            batch.setBatchType(Constant.BATCH_TYPE.NOTIFY_NOW5);
//            batch.setStatus(Constant.BATCH_STATUS.NOT_BEGINNING);
//            Long id = batchService.insertBatch(batch);
//            batch.setId(id);
//            logger.info("Notify now5 batch is ready to start work! batch id=" + id);
//            batchStart(batch);
//
//            //get user all
//            List<Long> userIdAllList = new ArrayList<Long>();
//            List<Person> getPersonAll = personService.getPersonAll();
//            if (getPersonAll.size() > 0) {
//                for (Person person : getPersonAll) {
//                    userIdAllList.add(person.getUserId());
//                }
//            }
//            logger.debug("----1----all user size=" + userIdAllList.size());
//
//            //get user by setting
//            List<Long> getUserBySetting = new ArrayList<Long>();
//            String settingKey = Constant.USER_SETTING.NOW5;
//            String settingValue = Constant.USER_SETTING.OFF;
//
//            logger.debug("----2----now5 off user size=" + getUserBySetting.size());
//
//            //get user for now5
//            List<Long> getUserForNow5 = new ArrayList<Long>();
//
//            for (Long userAllId : userIdAllList) {
//                if (!getUserBySetting.contains(userAllId)) {
//                    getUserForNow5.add(userAllId);
//                }
//            }
//            logger.debug("----3----notify now5 user size=" + getUserForNow5.size());
//
//            // insert notification content data
//            if (getUserForNow5 != null && getUserForNow5.size() > 0) {
//                DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
//                txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//                TransactionStatus txStatus = txManager.getTransaction(txDefinition);
//
//                try {
//                    List<ShopNowgo> now5List = shopNowgoService.getShopNowgoListForNotify();
//                    Date pushDate = new Date(System.currentTimeMillis());
//                    logger.debug("----4----push time=" + pushDate);
//                    // foreach insert notification data
//                    if (now5List.size() > 0) {
//                        logger.debug("----5----now5List=" + now5List.size());
//                        for (ShopNowgo sn : now5List) {
//                            for (Long userId : getUserForNow5) {
//                                FavDetail fd = favDetailService.getFavDetailByUserId(userId, sn.getShopId(), Constant.FAV_TYPE.SHOP);
//                                UserSetting us = notificationService.getUserSettingBySettingKeyAndSettingValueAndByItemId(userId, Constant.USER_SETTING.NOW5, Constant.USER_SETTING.OFF, sn.getShopId());
//                                if(fd != null && us == null){
//                                    NotificationDevice notificationDeviceForIos = notificationService.getNotificationDeviceByUserId(userId,Constant.NOTIFICATION.USER_TYPE_USER,Constant.NOTIFICATION.NOTIFY_DEVICE_IOS);
//                                    NotificationDevice notificationDeviceForAnd = notificationService.getNotificationDeviceByUserId(userId,Constant.NOTIFICATION.USER_TYPE_USER,Constant.NOTIFICATION.NOTIFY_DEVICE_ANDROID);
//                                    Shop s = shopService.getShopById(sn.getShopId());
//                                    String alert =  "「"+s.getName()+"」"+"のNOW5情報が投稿されました！";
//                                    PushForm pushForm = new PushForm();
//                                    pushForm.setId(0L);
//                                    pushForm.setItemId(Constant.NOTIFY_ITEM.NOW5); //0:お知らせ　13:WEB Url other:item uuid
//                                    pushForm.setDate(DateUtil.dateToStringyyyymmdd(sn.getUpdateDatetime()));
//                                    pushForm.setTitle(alert);
//                                    pushForm.setContent("");
//                                    pushForm.setShopType(s.getShopType());
//                                    pushForm.setUuid(s.getUuid());
//                                    pushForm.setUserId(userId);
//                                    pushForm.setUserType(Constant.NOTIFICATION.USER_TYPE_USER);
//                                    pushForm.setType(Constant.NOTIFICATION.PUSH_NOW5);
//                                    if (notificationDeviceForIos != null) {
//                                        logger.debug("----6----NotificationDevice id=" + notificationDeviceForIos.getId());
//                                        String deviceToken = notificationDeviceForIos.getToken();
//                                        logger.debug("----7----deviceToken=" + deviceToken);
//                                        pushForm.setDeviceToken(deviceToken);
//                                        int result = notificationService.notifyPush(request,pushForm);
//                                        logger.debug("----9----ios now5 push result=" + result);
//                                        if(result == 0){
//                                            NotifyNow45 notifyNow45 = new NotifyNow45();
//                                            notifyNow45.setUserId(userId);
//                                            notifyNow45.setItemId(Long.valueOf(sn.getId()));
//                                            notifyNow45.setPushType(Constant.NOTIFY_NOW45.NOTIFY_TYPE_PUSH);
//                                            notifyNow45.setNotifyType(Constant.NOTIFY_NOW45.now5);
//                                            notifyNow45.setPushStatus(Constant.NOTIFY_NOW45.PUSH_STATUS_SENT);
//                                            notifyNow45.setUpdateDatetime(new Date(System.currentTimeMillis()));
//                                            notificationService.updateNotifyNow45(notifyNow45);
//                                            logger.debug("----10----update push status NotifyNow45 itemId=" + sn.getId());
//                                        }
//                                    }
//                                    if(notificationDeviceForAnd != null) {
//                                        logger.debug("----11----NotificationDevice id=" + notificationDeviceForAnd.getId());
//                                        String deviceToken = notificationDeviceForAnd.getToken();
//                                        logger.debug("----12----deviceToken=" + deviceToken);
//                                        pushForm.setDeviceToken(deviceToken);
//                                        int result = notificationService.pushFCMNotification(pushForm);
//                                        logger.debug("----14----Android now5 push result=" + result);
//                                        if (result == 0) {
//                                            NotifyNow45 notifyNow45 = new NotifyNow45();
//                                            notifyNow45.setUserId(userId);
//                                            notifyNow45.setItemId(Long.valueOf(sn.getId()));
//                                            notifyNow45.setPushType(Constant.NOTIFY_NOW45.NOTIFY_TYPE_PUSH);
//                                            notifyNow45.setNotifyType(Constant.NOTIFY_NOW45.now5);
//                                            notifyNow45.setPushStatus(Constant.NOTIFY_NOW45.PUSH_STATUS_SENT);
//                                            notifyNow45.setUpdateDatetime(new Date(System.currentTimeMillis()));
//                                            notificationService.updateNotifyNow45(notifyNow45);
//                                            logger.debug("----15----update push status NotifyNow45 itemId=" + sn.getId());
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                    txManager.commit(txStatus);
//                } catch (Exception ex) {
//                    txManager.rollback(txStatus);
//                    logger.error(" now5 notification push failed, error=" + ex.getMessage());
//                    logger.error(" now5 notification push, error=" + ex.toString());
//                    ex.printStackTrace();
//                }
//            }
//            batchFinish(batch);
//            logger.debug("----20----batchFinish" + id);
//        }
//        return jsonObject;
//    }
//
//    //admin push notify
//    @RequestMapping(value = "/batch/notify/push/", method = RequestMethod.GET)
//    @ResponseBody
//    public JsonObject batchNotifyPush(HttpServletRequest request) throws Exception {
//
//        boolean status = queryBatchWorkingState(Constant.BATCH_TYPE.NOTIFY_PUSH);
//        if (status) {
//            BatchStatus batch = new BatchStatus();
//            batch.setBatchType(Constant.BATCH_TYPE.NOTIFY_PUSH);
//            batch.setStatus(Constant.BATCH_STATUS.NOT_BEGINNING);
//            Long id = batchService.insertBatch(batch);
//            batch.setId(id);
//            logger.info("Notify push batch is ready to start work! batch id=" + id);
//            batchStart(batch);
//            logger.debug("----0----batchStart" + id);
//
//            //ios push notification content data
//            List<Notification> notifyForIosList = notificationService.getNotifyForIosByPushStatus();
//            logger.debug("----1----ios プッシュ未送信=" + notifyForIosList.size());
//            //and push notification content data
//            List<Notification> notifyForAndList = notificationService.getNotifyForAndByPushStatus();
//            logger.debug("----2----Android プッシュ未送信=" + notifyForAndList.size());
//
//            //ios push notification
//            if ( notifyForIosList.size() > 0) {
//                DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
//                txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//                TransactionStatus txStatus = txManager.getTransaction(txDefinition);
//                try {
//                    Date pushDate = new Date(System.currentTimeMillis());
//                    Date date = DateUtil.stringToDateyyyy_MM_dd_HH_mm_ss("1970-01-01 00:00:01");
//                    for (Notification nf:notifyForIosList){
//                        Long publishStartTime = nf.getPublishStart().getTime();
//                        Long publishEndTime = nf.getPublishEnd().getTime();
//                        Long pushTime = pushDate.getTime();
//                        Long nowTime = date.getTime();
//                        logger.debug("----3----Notification id=" + nf.getId());
//                        if(publishStartTime<=pushTime && pushTime <= publishEndTime){
//                            NotificationContent nfc = notificationService.getNotificationContent(nf.getNotifiContentId());
//                            NotificationDevice notificationDeviceForUser = notificationService.getNotificationDeviceByUserId(nf.getUserId(),Constant.NOTIFICATION.USER_TYPE_USER,Constant.NOTIFICATION.NOTIFY_DEVICE_IOS);
//                            NotificationDevice notificationDeviceForAdmin = notificationService.getNotificationDeviceByUserId(nf.getUserId(),Constant.NOTIFICATION.USER_TYPE_ADMIN,Constant.NOTIFICATION.NOTIFY_DEVICE_IOS);
//                            PushForm pushForm = new PushForm();
//                            pushForm.setId(nf.getId());
//                            pushForm.setItemId(nf.getItemId()); //0:お知らせ　13:WEB Url other:item uuid
//                            pushForm.setDate(DateUtil.dateToStringyyyymmdd(nfc.getCreateDatetime()));
//                            pushForm.setTitle(nfc.getTitle());
//                            pushForm.setContent(nfc.getContent());
//                            pushForm.setShopType(0);
//                            pushForm.setUuid("");
//                            pushForm.setUserId(nf.getUserId());
//                            pushForm.setUserType(nf.getUserType());
//                            pushForm.setContentId(nfc.getId());
//                            pushForm.setType(Constant.NOTIFICATION.PUSH_ADMIN);
//
//                            //ios push notification for user
//                            if (notificationDeviceForUser != null && nfc != null) {
//                                logger.debug("----4----NotificationContent title=" + nfc.getTitle());
//                                logger.debug("----5----NotificationDevice id=" + notificationDeviceForUser.getId());
//                                String deviceToken = notificationDeviceForUser.getToken();
//                                logger.debug("----6----deviceToken=" + deviceToken);
//                                pushForm.setDeviceToken(deviceToken);
//                                int result = notificationService.notifyPush( request,pushForm);
//                                logger.debug("----7----ios push result=" + result);
//                                if(result == 0){
//                                    notificationService.updateNotificationPushStatus(nf.getId(),Constant.NOTIFICATION.PUSH_STATUS_SENT);
//                                    logger.debug("----8----update push status notification id=" + nf.getId());
//                                } else {
//                                    notificationService.updateNotificationDeviceByUserId(notificationDeviceForUser.getUserId(),notificationDeviceForUser.getUserType(),notificationDeviceForUser.getDeviceId(),notificationDeviceForUser.getOs(),notificationDeviceForUser.getToken(),true);
//                                    logger.debug("----16----ios delete  notification device =" + notificationDeviceForUser.getId());
//                                    notificationService.deleteNotification(nf.getId());
//                                    logger.debug("----17----ios  delete  notification  =" + nf.getId());
//                                }
//                            //ios push notification for admin
//                            }else if(notificationDeviceForAdmin != null && nfc != null){
//                                logger.debug("----9----NotificationContent title=" + nfc.getTitle());
//                                logger.debug("----10----NotificationDevice id=" + notificationDeviceForAdmin.getId());
//                                String deviceToken = notificationDeviceForAdmin.getToken();
//                                logger.debug("----11----deviceToken=" + deviceToken);
//                                pushForm.setDeviceToken(deviceToken);
//                                int result = notificationService.notifyPush( request,pushForm);
//                                logger.debug("----12----ios push result=" + result);
//                                if(result == 0){
//                                    notificationService.updateNotificationPushStatus(nf.getId(),Constant.NOTIFICATION.PUSH_STATUS_SENT);
//                                    logger.debug("----13----update push status notification id=" + nf.getId());
//                                } else {
//                                    notificationService.updateNotificationDeviceByUserId(notificationDeviceForAdmin.getUserId(),notificationDeviceForAdmin.getUserType(),notificationDeviceForAdmin.getDeviceId(),notificationDeviceForAdmin.getOs(),notificationDeviceForAdmin.getToken(),true);
//                                    logger.debug("----14----ios admin delete  notification device =" + notificationDeviceForAdmin.getId());
//                                    notificationService.deleteNotification(nf.getId());
//                                    logger.debug("----15----ios admin delete  notification  =" + nf.getId());
//                                }
//                            }
//                        }
//                    }
//                    txManager.commit(txStatus);
//                } catch (Exception ex) {
//                    txManager.rollback(txStatus);
//                    logger.error(" admin ios notification push failed, error=" + ex.getMessage());
//                    logger.error(" admin ios notification push, error=" + ex.toString());
//                    ex.printStackTrace();
//                }
//            }
//
//            //android push notification
//            if ( notifyForAndList.size() > 0) {
//                DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
//                txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//                TransactionStatus txStatus = txManager.getTransaction(txDefinition);
//                try {
//                    Date pushDate = new Date(System.currentTimeMillis());
//                    Date date = DateUtil.stringToDateyyyy_MM_dd_HH_mm_ss("1970-01-01 00:00:01");
//                    logger.debug("----2----push time=" + pushDate);
//                    for (Notification nf:notifyForAndList){
//                        Long publishStartTime = nf.getPublishStart().getTime();
//                        Long publishEndTime = nf.getPublishStart().getTime();
//                        Long pushTime = pushDate.getTime();
//                        Long nowTime = date.getTime();
//                        logger.debug("----3----Notification id=" + nf.getId());
//                        if(publishStartTime<=pushTime &&  pushTime<= publishEndTime) {
//                            NotificationContent nfc = notificationService.getNotificationContent(nf.getNotifiContentId());
//                            NotificationDevice notificationDeviceForUser = notificationService.getNotificationDeviceByUserId(nf.getUserId(), Constant.NOTIFICATION.USER_TYPE_USER, Constant.NOTIFICATION.NOTIFY_DEVICE_ANDROID);
//                            NotificationDevice notificationDeviceForAdmin = notificationService.getNotificationDeviceByUserId(nf.getUserId(), Constant.NOTIFICATION.USER_TYPE_ADMIN, Constant.NOTIFICATION.NOTIFY_DEVICE_ANDROID);
//                            PushForm pushForm = new PushForm();
//                            pushForm.setId(nf.getId());
//                            pushForm.setItemId(nf.getItemId()); //0:お知らせ　13:WEB Url other:item uuid
//                            pushForm.setDate(DateUtil.dateToStringyyyymmdd(nfc.getCreateDatetime()));
//                            pushForm.setTitle(nfc.getTitle());
//                            pushForm.setContent(nfc.getContent());
//                            pushForm.setShopType(0);
//                            pushForm.setUuid("");
//                            pushForm.setUserId(nf.getUserId());
//                            pushForm.setUserType(nf.getUserType());
//                            pushForm.setContentId(nfc.getId());
//                            pushForm.setType(Constant.NOTIFICATION.PUSH_ADMIN);
//
//                            //android push notification for user
//                            if (notificationDeviceForUser != null && nfc != null) {
//                                logger.debug("----4----NotificationContent title=" + nfc.getTitle());
//                                logger.debug("----5----NotificationDevice id=" + notificationDeviceForUser.getId());
//                                String deviceToken = notificationDeviceForUser.getToken();
//                                logger.debug("----6----deviceToken=" + deviceToken);
//                                pushForm.setDeviceToken(deviceToken);
//                                int result = notificationService.pushFCMNotification(pushForm);
//                                logger.debug("----7----android push result=" + result);
//                                if (result == 0) {
//                                    notificationService.updateNotificationPushStatus(nf.getId(), Constant.NOTIFICATION.PUSH_STATUS_SENT);
//                                    logger.debug("----8----update push status notification id=" + nf.getId());
//                                }else {
//                                    notificationService.updateNotificationDeviceByUserId(notificationDeviceForUser.getUserId(),notificationDeviceForUser.getUserType(),notificationDeviceForUser.getDeviceId(),notificationDeviceForUser.getOs(),notificationDeviceForUser.getToken(),true);
//                                    logger.debug("----15----android admin delete  notification device =" + notificationDeviceForUser.getId());
//                                    notificationService.deleteNotification(nf.getId());
//                                    logger.debug("----16----android  delete  notification  =" + nf.getId());
//                                }
//                                //android push notification for admin
//                            } else if (notificationDeviceForAdmin != null && nfc != null) {
//                                logger.debug("----9----NotificationContent title=" + nfc.getTitle());
//                                logger.debug("----10----NotificationDevice id=" + notificationDeviceForAdmin.getId());
//                                String deviceToken = notificationDeviceForAdmin.getToken();
//                                logger.debug("----11----deviceToken=" + deviceToken);
//                                pushForm.setDeviceToken(deviceToken);
//                                int result = notificationService.pushFCMNotification(pushForm);
//                                logger.debug("----12----android push result=" + result);
//                                if (result == 0) {
//                                    notificationService.updateNotificationPushStatus(nf.getId(), Constant.NOTIFICATION.PUSH_STATUS_SENT);
//                                    logger.debug("----13----update push status notification id=" + nf.getId());
//                                }else {
//                                    notificationService.updateNotificationDeviceByUserId(notificationDeviceForAdmin.getUserId(),notificationDeviceForAdmin.getUserType(),notificationDeviceForAdmin.getDeviceId(),notificationDeviceForAdmin.getOs(),notificationDeviceForAdmin.getToken(),true);
//                                    logger.debug("----14----android admin delete  notification device =" + notificationDeviceForAdmin.getId());
//                                    notificationService.deleteNotification(nf.getId());
//                                    logger.debug("----17----android  admin delete  notification  =" + nf.getId());
//                                }
//                            }
//                        }
//                    }
//                    txManager.commit(txStatus);
//                } catch (Exception ex) {
//                    txManager.rollback(txStatus);
//                    logger.error(" admin android notification push failed, error=" + ex.getMessage());
//                    logger.error(" admin android notification push, error=" + ex.toString());
//                    logger.error(" admin notification push failed, error=" + ex.getMessage());
//                    logger.error(" admin notification push, error=" + ex.toString());
//                    ex.printStackTrace();
//                }
//            }
//            batchFinish(batch);
//            logger.debug("----20----batchFinish" + id);
//        }
//        return jsonObject;
//    }
//
//
//    // random shop one
//    @RequestMapping(value = "/batch/random/shop/one/", method = RequestMethod.GET)
//    @ResponseBody
//    public JsonObject batchRandomShop() throws Exception {
//
//        boolean status = queryBatchWorkingState(Constant.BATCH_TYPE.RANDOM_SHOP_ONE);
//        if (status) {
//            BatchStatus batch = new BatchStatus();
//            batch.setBatchType(Constant.BATCH_TYPE.RANDOM_SHOP_ONE);
//            batch.setStatus(Constant.BATCH_STATUS.NOT_BEGINNING);
//            Long id = batchService.insertBatch(batch);
//            batch.setId(id);
//            logger.info("Random shop one batch is ready to start work! batch id=" + id);
//            batchStart(batch);
//
//            //get shop list
//            List<Shop> shopList = shopService.getShopAllForRandom();
//            List<Place> placeList = placeService.getPlaceAllForRandom();
//            List<Recruit> recruitList = recruitService.getRecruitAllForRandom();
//            logger.debug("----1----shop list size=" + shopList.size());
//            if (shopList.size() > 0) {
//                Collections.shuffle(shopList);
//                List<RandomShopOne> randomList = randomShopService.getRandomShopOneAll();
//                logger.debug("----2----random list size=" + randomList.size());
//                if (randomList.size() > 0) {
//                    List<Long> randomIdList = new ArrayList<Long>();
//                    for (RandomShopOne rso : randomList) {
//                        randomIdList.add(rso.getId());
//                    }
//                    randomShopService.deleteRandomShopOne(randomIdList);
//                    logger.debug("----3----random list size=" + randomList.size());
//                }
//                DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
//                txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//                TransactionStatus txStatus = txManager.getTransaction(txDefinition);
//
//                try {
//                    if (shopList.size() > 0) {
//                        for (Shop sp : shopList) {
//                            RandomShopOne randomShop = new RandomShopOne();
//                            randomShop.setShopType((byte) sp.getShopType().intValue());
//                            randomShop.setUuid(sp.getUuid());
//                            randomShop.setArea((byte) 0);
//                            if (sp.getNow4() != null) {
//                                randomShop.setNow4((byte) sp.getNow4().intValue());
//                            } else {
//                                randomShop.setNow4((byte) 0);
//                            }
//                            if (sp.getNow5() != null) {
//                                randomShop.setNow5((byte) sp.getNow5().intValue());
//                            } else {
//                                randomShop.setNow5((byte) 0);
//                            }
//                            randomShop.setPublishStart(sp.getPublishStart());
//                            randomShop.setPublishEnd(sp.getPublishEnd());
//                            randomShop.setCreateDatetime(new Date(System.currentTimeMillis()));
//                            randomShop.setUpdateDatetime(new Date(System.currentTimeMillis()));
//                            randomShop.setDelFlg(Boolean.FALSE);
//                            randomShop.setNote(Constant.EMPTY_STRING);
//                            randomShopService.insertRandomShopOne(randomShop);
//                        }
//                    }
//                    if (placeList.size() > 0) {
//                        for (Place sp : placeList) {
//                            RandomShopOne randomShop = new RandomShopOne();
//                            randomShop.setShopType((byte) 7);
//                            randomShop.setUuid(sp.getUuid());
//                            randomShop.setArea((byte) sp.getArea().intValue());
//                            randomShop.setNow4((byte) 0);
//                            randomShop.setNow5((byte) 0);
//                            randomShop.setPublishStart(sp.getPublishStart());
//                            randomShop.setPublishEnd(sp.getPublishEnd());
//                            randomShop.setCreateDatetime(new Date(System.currentTimeMillis()));
//                            randomShop.setUpdateDatetime(new Date(System.currentTimeMillis()));
//                            randomShop.setDelFlg(Boolean.FALSE);
//                            randomShop.setNote(Constant.EMPTY_STRING);
//                            randomShopService.insertRandomShopOne(randomShop);
//                        }
//                    }
//                    if (recruitList.size() > 0) {
//                        for (Recruit sp : recruitList) {
//                            RandomShopOne randomShop = new RandomShopOne();
//                            randomShop.setShopType((byte) 2);
//                            randomShop.setUuid(sp.getUuid());
//                            randomShop.setArea((byte) 0);
//                            randomShop.setNow4((byte) 0);
//                            randomShop.setNow5((byte) 0);
//                            randomShop.setPublishStart(sp.getPublishStart());
//                            randomShop.setPublishEnd(sp.getPublishEnd());
//                            randomShop.setCreateDatetime(new Date(System.currentTimeMillis()));
//                            randomShop.setUpdateDatetime(new Date(System.currentTimeMillis()));
//                            randomShop.setDelFlg(Boolean.FALSE);
//                            randomShop.setNote(Constant.EMPTY_STRING);
//                            randomShopService.insertRandomShopOne(randomShop);
//                        }
//                    }
//                    txManager.commit(txStatus);
//                } catch (Exception ex) {
//                    txManager.rollback(txStatus);
//                    logger.error(" random shop one list regist failed, error=" + ex.getMessage());
//                    logger.error(" random shop one list regist, error=" + ex.toString());
//                    ex.printStackTrace();
//                }
//            }
//            batchFinish(batch);
//            logger.debug("----20----batchFinish" + id);
//        }
//        return jsonObject;
//    }
//
//    // random shop one
//    @RequestMapping(value = "/batch/random/shop/two/", method = RequestMethod.GET)
//    @ResponseBody
//    public JsonObject batchRandomShopTwo() throws Exception {
//
//        boolean status = queryBatchWorkingState(Constant.BATCH_TYPE.RANDOM_SHOP_TWO);
//        if (status) {
//            BatchStatus batch = new BatchStatus();
//            batch.setBatchType(Constant.BATCH_TYPE.RANDOM_SHOP_TWO);
//            batch.setStatus(Constant.BATCH_STATUS.NOT_BEGINNING);
//            Long id = batchService.insertBatch(batch);
//            batch.setId(id);
//            logger.info("Random shop two batch is ready to start work! batch id=" + id);
//            batchStart(batch);
//
//            //get shop list
//            List<Shop> shopList = shopService.getShopAllForRandom();
//            List<Place> placeList = placeService.getPlaceAllForRandom();
//            List<Recruit> recruitList = recruitService.getRecruitAllForRandom();
//            logger.debug("----1----shop list size=" + shopList.size());
//            if (shopList.size() > 0) {
//                Collections.shuffle(shopList);
//                List<RandomShopTwo> randomList = randomShopService.getRandomShopTwoAll();
//                logger.debug("----2----random list size=" + randomList.size());
//                if (randomList.size() > 0) {
//                    List<Long> randomIdList = new ArrayList<Long>();
//                    for (RandomShopTwo rso : randomList) {
//                        randomIdList.add(rso.getId());
//                    }
//                    randomShopService.deleteRandomShopTwo(randomIdList);
//                }
//                DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
//                txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//                TransactionStatus txStatus = txManager.getTransaction(txDefinition);
//
//                try {
//                    if (shopList.size() > 0) {
//                        for (Shop sp : shopList) {
//                            RandomShopTwo randomShop = new RandomShopTwo();
//                            randomShop.setShopType((byte) sp.getShopType().intValue());
//                            randomShop.setUuid(sp.getUuid());
//                            if (sp.getNow4() != null) {
//                                randomShop.setNow4((byte) sp.getNow4().intValue());
//                            } else {
//                                randomShop.setNow4((byte) 0);
//                            }
//                            if (sp.getNow5() != null) {
//                                randomShop.setNow5((byte) sp.getNow5().intValue());
//                            } else {
//                                randomShop.setNow5((byte) 0);
//                            }
//                            randomShop.setPublishStart(sp.getPublishStart());
//                            randomShop.setPublishEnd(sp.getPublishEnd());
//                            randomShop.setCreateDatetime(new Date(System.currentTimeMillis()));
//                            randomShop.setUpdateDatetime(new Date(System.currentTimeMillis()));
//                            randomShop.setDelFlg(Boolean.FALSE);
//                            randomShop.setNote(Constant.EMPTY_STRING);
//                            randomShopService.insertRandomShopTwo(randomShop);
//                        }
//                    }
//                    if (placeList.size() > 0) {
//                        for (Place sp : placeList) {
//                            RandomShopTwo randomShop = new RandomShopTwo();
//                            randomShop.setShopType((byte) 7);
//                            randomShop.setUuid(sp.getUuid());
//                            randomShop.setArea((byte) sp.getArea().intValue());
//                            randomShop.setNow4((byte) 0);
//                            randomShop.setNow5((byte) 0);
//                            randomShop.setPublishStart(sp.getPublishStart());
//                            randomShop.setPublishEnd(sp.getPublishEnd());
//                            randomShop.setCreateDatetime(new Date(System.currentTimeMillis()));
//                            randomShop.setUpdateDatetime(new Date(System.currentTimeMillis()));
//                            randomShop.setDelFlg(Boolean.FALSE);
//                            randomShop.setNote(Constant.EMPTY_STRING);
//                            randomShopService.insertRandomShopTwo(randomShop);
//                        }
//                    }
//                    if (recruitList.size() > 0) {
//                        for (Recruit sp : recruitList) {
//                            RandomShopTwo randomShop = new RandomShopTwo();
//                            randomShop.setShopType((byte) 2);
//                            randomShop.setUuid(sp.getUuid());
//                            randomShop.setArea((byte) 0);
//                            randomShop.setNow4((byte) 0);
//                            randomShop.setNow5((byte) 0);
//                            randomShop.setPublishStart(sp.getPublishStart());
//                            randomShop.setPublishEnd(sp.getPublishEnd());
//                            randomShop.setCreateDatetime(new Date(System.currentTimeMillis()));
//                            randomShop.setUpdateDatetime(new Date(System.currentTimeMillis()));
//                            randomShop.setDelFlg(Boolean.FALSE);
//                            randomShop.setNote(Constant.EMPTY_STRING);
//                            randomShopService.insertRandomShopTwo(randomShop);
//                        }
//                    }
//                    txManager.commit(txStatus);
//                } catch (Exception ex) {
//                    txManager.rollback(txStatus);
//                    logger.error(" random shop one list regist failed, error=" + ex.getMessage());
//                    logger.error(" random shop one list regist, error=" + ex.toString());
//                    ex.printStackTrace();
//                }
//            }
//            batchFinish(batch);
//            logger.debug("----20----batchFinish" + id);
//        }
//        return jsonObject;
//    }
//
//    // random shop now4
//    @RequestMapping(value = "/batch/update/shop/now4/", method = RequestMethod.GET)
//    @ResponseBody
//    public JsonObject batchUpdateShopNow4() throws Exception {
//
//        boolean status = queryBatchWorkingState(Constant.BATCH_TYPE.UPDATE_SHOP_NOW4);
//        if (status) {
//            BatchStatus batch = new BatchStatus();
//            batch.setBatchType(Constant.BATCH_TYPE.UPDATE_SHOP_NOW4);
//            batch.setStatus(Constant.BATCH_STATUS.NOT_BEGINNING);
//            Long id = batchService.insertBatch(batch);
//            batch.setId(id);
//                logger.info(" update shop now4 batch is ready to start work! batch id=" + id);
//            batchStart(batch);
//
//            //get invalid ShopCoupon list
//            List<ShopCoupon> invalidCouponList = shopCouponService.getShopCouponForInvalid();
//            logger.debug("----1----invalid shopCoupon list size=" + invalidCouponList.size());
//            List<ShopCoupon> validCouponList = shopCouponService.getShopCouponForValid();
//            logger.debug("----2----valid shopCoupon list size=" + validCouponList.size());
//
//            List<Long> validShopIdList = new ArrayList<Long>();
//            List<Long> invalidShopIdList = new ArrayList<Long>();
//            if(invalidCouponList.size()>0){
//                for(ShopCoupon sc :invalidCouponList){
//                    invalidShopIdList.add(sc.getShopId());
//                }
//            }
//            logger.debug("----3----invalid shopCoupon IDlist size=" + invalidShopIdList.size());
//            if(validCouponList.size()>0){
//                for(ShopCoupon sc :validCouponList){
//                    validShopIdList.add(sc.getShopId());
//                }
//            }
//            logger.debug("----4----valid shopCoupon IDlist size=" + validShopIdList.size());
//
//
//            DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
//            txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//            TransactionStatus txStatus = txManager.getTransaction(txDefinition);
//
//            try {
//                if(invalidShopIdList.size()>0){
//                    shopService.updateShopForNow4ByIdList(invalidShopIdList,false);
//                }
//                if(validShopIdList.size()>0){
//                    shopService.updateShopForNow4ByIdList(validShopIdList,true);
//                }
//
//                txManager.commit(txStatus);
//            } catch (Exception ex) {
//                txManager.rollback(txStatus);
//                logger.error(" update shop now4 failed, error=" + ex.getMessage());
//                logger.error(" update shop now4, error=" + ex.toString());
//                ex.printStackTrace();
//            }
//            batchFinish(batch);
//            logger.debug("----20----batchFinish" + id);
//        }
//        return jsonObject;
//    }
//
//    // random shop now4
//    @RequestMapping(value = "/batch/update/shop/now5/", method = RequestMethod.GET)
//    @ResponseBody
//    public JsonObject batchUpdateShopNow5() throws Exception {
//
//        boolean status = queryBatchWorkingState(Constant.BATCH_TYPE.UPDATE_SHOP_NOW5);
//        if (status) {
//            BatchStatus batch = new BatchStatus();
//            batch.setBatchType(Constant.BATCH_TYPE.UPDATE_SHOP_NOW5);
//            batch.setStatus(Constant.BATCH_STATUS.NOT_BEGINNING);
//            Long id = batchService.insertBatch(batch);
//            batch.setId(id);
//            logger.info(" update shop now5 batch is ready to start work! batch id=" + id);
//            batchStart(batch);
//
//            //get invalid ShopCoupon list
//            List<ShopNowgo> invalidNowGoList = shopNowgoService.getShopNowgoForInvalid();
//            logger.debug("----1----invalid shopNowGo list size=" + invalidNowGoList.size());
//            List<ShopNowgo> validNowGoList = shopNowgoService.getShopNowgoForValid();
//            logger.debug("----2----valid shopNowGo list size=" + validNowGoList.size());
//
//            List<Long> validShopIdList = new ArrayList<Long>();
//            List<Long> invalidShopIdList = new ArrayList<Long>();
//            if(invalidNowGoList.size()>0){
//                for(ShopNowgo sc :invalidNowGoList){
//                    invalidShopIdList.add(sc.getShopId());
//                }
//            }
//            logger.debug("----3----invalid shopNowGo IDlist size=" + invalidShopIdList.size());
//            if(validNowGoList.size()>0){
//                for(ShopNowgo sc :validNowGoList){
//                    validShopIdList.add(sc.getShopId());
//                }
//            }
//            logger.debug("----4----valid shopNowGo IDlist size=" + validShopIdList.size());
//
//
//            DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
//            txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//            TransactionStatus txStatus = txManager.getTransaction(txDefinition);
//
//            try {
//                if(invalidShopIdList.size()>0){
//                    shopService.updateShopForNow5ByIdList(invalidShopIdList,false);
//                }
//                if(validShopIdList.size()>0){
//                    shopService.updateShopForNow5ByIdList(validShopIdList,true);
//                }
//
//                txManager.commit(txStatus);
//            } catch (Exception ex) {
//                txManager.rollback(txStatus);
//                logger.error(" update shop now5 failed, error=" + ex.getMessage());
//                logger.error(" update shop now5, error=" + ex.toString());
//                ex.printStackTrace();
//            }
//            batchFinish(batch);
//            logger.debug("----20----batchFinish" + id);
//        }
//        return jsonObject;
//    }
//
//
//    // random shop one
//    @RequestMapping(value = "/batch/update/person/mail/", method = RequestMethod.GET)
//    @ResponseBody
//    public JsonObject batchupdatePersonMail() throws Exception {
//
//        boolean status = queryBatchWorkingState(Constant.BATCH_TYPE.PERSON_MAIL);
//        if (status) {
//            BatchStatus batch = new BatchStatus();
//            batch.setBatchType(Constant.BATCH_TYPE.PERSON_MAIL);
//            batch.setStatus(Constant.BATCH_STATUS.NOT_BEGINNING);
//            Long id = batchService.insertBatch(batch);
//            batch.setId(id);
//            logger.info("update person mail batch is ready to start work! batch id=" + id);
//            batchStart(batch);
//
//            List<Person> personList = personService.getPersonByMailNull();
//            List<Long> userIdList = new ArrayList<Long>();
//            List<UserForeign> userForeignList = new ArrayList<UserForeign>();
//            if (personList.size() > 0) {
//                for (Person person : personList) {
//                    userIdList.add(person.getUserId());
//                }
//            }
//            if (userIdList.size() > 0) {
//                userForeignList = authService.getUserForeignByUserIdList(userIdList);
//            }
//
//            DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
//            txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//            TransactionStatus txStatus = txManager.getTransaction(txDefinition);
//
//            try {
//                for (Person p : personList) {
//                    for (UserForeign uf : userForeignList) {
//                        if (p.getUserId().equals(uf.getUserId())) {
//                            if (uf.getForeignType() == Constant.USER_FOREIGN_TYPE.MAIL) {
//                                Person person = new Person();
//                                person.setUserId(p.getUserId());
//                                person.setMail(uf.getForeignId());
//                                personService.updatePerson(person);
//                            }
//                        }
//                    }
//                }
//
//                txManager.commit(txStatus);
//            } catch (Exception ex) {
//                txManager.rollback(txStatus);
//                logger.error(" update person mail failed, error=" + ex.getMessage());
//                logger.error("  update person mail regist, error=" + ex.toString());
//                ex.printStackTrace();
//            }
//            batchFinish(batch);
//            logger.debug("----20----batchFinish" + id);
//        }
//        return jsonObject;
//    }
//
//    // random shop one
//    @RequestMapping(value = "/batch/update/notify/item/", method = RequestMethod.GET)
//    @ResponseBody
//    public JsonObject batchupdatenotifyItem() throws Exception {
//
//            DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
//            txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//            TransactionStatus txStatus = txManager.getTransaction(txDefinition);
//
//            try {
//                notificationService.updateNotifyItem();
//                txManager.commit(txStatus);
//            } catch (Exception ex) {
//                txManager.rollback(txStatus);
//                logger.error(" update notify item failed, error=" + ex.getMessage());
//                logger.error("  update notify item failed, error=" + ex.toString());
//                ex.printStackTrace();
//            }
//        return jsonObject;
//    }
//
//    // random shop one
//    @RequestMapping(value = "/batch/payment/cvs/", method = RequestMethod.GET)
//    @ResponseBody
//    public JsonObject batchPaymentCvs() throws Exception {
//
//        boolean status = queryBatchWorkingState(Constant.BATCH_TYPE.PEYMENT_CVS);
//        if (status) {
//            BatchStatus batch = new BatchStatus();
//            batch.setBatchType(Constant.BATCH_TYPE.PEYMENT_CVS);
//            batch.setStatus(Constant.BATCH_STATUS.NOT_BEGINNING);
//            Long id = batchService.insertBatch(batch);
//            batch.setId(id);
//            logger.info("payment cvs batch is ready to start work! batch id=" + id);
//            batchStart(batch);
//
//            List<PaymentCvsHistory> list = paymentService.getPaymentCvsHistoryByOrderId(Constant.PURCHASE_STATUS.CONVENIENCE_STORE_TO_BE_PAID);
//
//            DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
//            txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//            TransactionStatus txStatus = txManager.getTransaction(txDefinition);
//
//            try {
//                for (PaymentCvsHistory pch : list) {
//                    PaymentCvsHistory paymentCvsHistory = new PaymentCvsHistory();
//                    GoodsPurchase goodsPurchase = new GoodsPurchase();
//                    String paymentStatus = paymentService.SearchTradeCvsForBatch(pch.getOrderId());
//                    if (paymentStatus.equals("PAYSUCCESS")) {
//                        paymentCvsHistory.setOrderId(pch.getOrderId());
//                        paymentCvsHistory.setPaymentStatus(Constant.PURCHASE_STATUS.PURCHASE_SUCCESS);
//                        paymentService.updateByOrderId(paymentCvsHistory);
//
//                        goodsPurchase.setSerialNumber(pch.getOrderId());
//                        goodsPurchase.setStatus(Constant.PURCHASE_STATUS.PURCHASE_SUCCESS);
//                        goodsService.updateBySerialNumberSelective(goodsPurchase);
//
//                        Person person = personService.getPersonById(pch.getUserId());
//                        GoodsPurchaseMailForm goodsPurchaseMailForm = new GoodsPurchaseMailForm();
//                        goodsPurchaseMailForm.setMail(person.getMail());
//                        goodsPurchaseMailForm.setName(person.getFirstName() + " " + person.getSecondName());
//                        goodsPurchaseMailForm.setZipcode(person.getZipcode());
//                        goodsPurchaseMailForm.setAddress(person.getAddress());
//                        goodsPurchaseMailForm.setSerialNumber(pch.getOrderId());
//                        goodsPurchaseMailForm.setPurchaseType(Constant.PURCHASE_TYPE.CVS);
//
//                        PurchaseInfo purchaseInfo = goodsService.selectBySerialNumber(pch.getOrderId());
//                        goodsPurchaseMailForm.setName2(purchaseInfo.getFirstName1() + " " + purchaseInfo.getLastName1());
//                        goodsPurchaseMailForm.setZipcode2(purchaseInfo.getZipCode());
//                        goodsPurchaseMailForm.setAddress2(purchaseInfo.getAddress1());
//                        goodsPurchaseMailForm.setTel(purchaseInfo.getTel());
//                        goodsPurchaseMailForm.setTel2(purchaseInfo.getTel());
//                        goodsPurchaseMailForm.setDate(DateUtil.dateToStringyyyyMMddS(purchaseInfo.getCreateDatetime()));
//
//                        List<GoodsPurchase> purchaseList = goodsService.getGoodsPurchaseHistory(pch.getOrderId());
//                        List<PurchaseGoodsInfoForm> goodsInfoForms = new ArrayList<>();
//                        double totalPrice = 700;
//                        for (GoodsPurchase gp : purchaseList) {
//                            PurchaseGoodsInfoForm pgif = new PurchaseGoodsInfoForm();
//                            Goods goods = goodsService.getGoodsById(gp.getGoodsId());
//                            if (goods != null) {
//                                String goodsName = goods.getTitle();
//                                pgif.setGoodsName(goodsName);
//                            }
//                            String color;
//                            if (gp.getColors() == Constant.GOODS_COLOR.BLACK) {
//                                color = "黒い";
//                            } else if (gp.getColors() == Constant.GOODS_COLOR.WHITE) {
//                                color = "白い";
//                            } else {
//                                color = "色なし";
//                            }
//                            pgif.setColor(color);
//
//                            String size;
//                            if (gp.getSize() == Constant.GOODS_SIZE.XS) {
//                                size = "XS";
//                            } else if (gp.getSize() == Constant.GOODS_SIZE.S) {
//                                size = "S";
//                            } else if (gp.getSize() == Constant.GOODS_SIZE.M) {
//                                size = "M";
//                            } else if (gp.getSize() == Constant.GOODS_SIZE.L) {
//                                size = "L";
//                            } else if (gp.getSize() == Constant.GOODS_SIZE.XL) {
//                                size = "XL";
//                            } else {
//                                size = "フリーサイズ";
//                            }
//                            pgif.setSize(size);
//
//                            String price = comdify(String.valueOf((int) (gp.getPrice() * 1.08)));
//                            pgif.setPrice(price);
//
//                            String tax = comdify(String.valueOf((int) (gp.getPrice() * 0.08)));
//                            pgif.setTax(tax);
//                            pgif.setQuantity(gp.getQuantity());
//                            goodsInfoForms.add(pgif);
//
//                            totalPrice = totalPrice + (int) (gp.getPrice() * 1.08);
//                        }
//
//                        String totalPriceStr = comdify(String.valueOf((int) totalPrice));
//                        goodsPurchaseMailForm.setTotalPrice(totalPriceStr);
//                        goodsPurchaseMailForm.setDeliveryExpense(700);
//                        goodsPurchaseMailForm.setUrl1(hosturl + "/contact");
//                        goodsPurchaseMailForm.setUrl2(hosturl + "/purchaseHistory");
//                        goodsPurchaseMailForm.setGoodsInfoForms(goodsInfoForms);
//
//                        try {
//                            awsService.sendGoodsPurchaseCvsMail(person.getMail(), goodsPurchaseMailForm);
//                            logger.debug("GOODS sendMail success serialNumber=" + pch.getOrderId() + "userId=" + pch.getUserId());
//                        } catch (IOException e) {
//                            logger.error("GOODS sendMail fail serialNumber=" + pch.getOrderId() + "userId=" + pch.getUserId());
//                        }
//                    } else {
//                        continue;
//                    }
//                }
//                txManager.commit(txStatus);
//            } catch (Exception ex) {
//                txManager.rollback(txStatus);
//                logger.error(" payment cvs failed, error=" + ex.getMessage());
//                logger.error("  payment cvs regist, error=" + ex.toString());
//                ex.printStackTrace();
//            }
//            batchFinish(batch);
//            logger.debug("----20----batchFinish" + id);
//        }
//        return jsonObject;
//    }
//
//    public boolean queryBatchWorkingState(Byte batchType) {
//        boolean batchStatus;
//        List<BatchStatus> status = batchService.getBatchStatus(batchType, Constant.BATCH_STATUS.WORKING);
//        if (status.size() > 0) {
//            batchStatus = false;
//            logger.info("This type of batch is already in work! This batch is not working! batch type=" + batchType);
//        } else {
//            batchStatus = true;
//            logger.info("This type of batch is not in a working state! batch type=" + batchType);
//        }
//        return batchStatus;
//    }
//
//    public void batchStart(BatchStatus batch) {
//        batch.setStatus(Constant.BATCH_STATUS.WORKING);
//        batchService.UpdateBatch(batch);
//        logger.info("This type of batch begins to work! batch type=" + batch.getBatchType() + ", batch id=" + batch.getId());
//    }
//
//    public void batchFinish(BatchStatus batch) {
//        batch.setStatus(Constant.BATCH_STATUS.FINISH);
//        batchService.UpdateBatch(batch);
//        logger.info("This type of batch processing has been completed! batch type=" + batch.getBatchType() + ", batch id=" + batch.getId());
//    }
//
//
//    private static String comdify(String value) {
//        DecimalFormat df = null;
//        if (value.indexOf(".") > 0) {
//            int i = value.length() - value.indexOf(".") - 1;
//            switch (i) {
//                case 0:
//                    df = new DecimalFormat("###,##0");
//                    break;
//                case 1:
//                    df = new DecimalFormat("###,##0.0");
//                    break;
//                case 2:
//                    df = new DecimalFormat("###,##0.00");
//                    break;
//                case 3:
//                    df = new DecimalFormat("###,##0.000");
//                    break;
//                case 4:
//                    df = new DecimalFormat("###,##0.0000");
//                    break;
//                default:
//                    df = new DecimalFormat("###,##0.00000");
//                    break;
//            }
//        } else {
//            df = new DecimalFormat("###,##0");
//        }
//        double number = 0.0;
//        try {
//            number = Double.parseDouble(value);
//        } catch (Exception e) {
//            number = 0.0;
//        }
//        return df.format(number);
//    }
//
//}
//
