package jp.co.vermore.controller.admin;

import java.io.IOException;
import java.util.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import jp.co.vermore.jsonparse.AdminNotificationJsonParse;
import jp.co.vermore.validation.NotificationRegisterParams;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.JsonObject;
import jp.co.vermore.common.JsonStatus;
import jp.co.vermore.common.mvc.APIException;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.common.util.StringUtil;
import jp.co.vermore.entity.*;
import jp.co.vermore.jsonparse.AdminRiseFeedJsonParse;
import jp.co.vermore.jsonparse.AdminShopFeedJsonParse;
import jp.co.vermore.service.*;

/**
 * AdminController
 * Created by Xieyoujun.
 * <p>
 * DateTime: 2018/03/23 14:13
 * Copyright: sLab, Corp
 */

@Controller
public class AdminAppController extends BaseController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ShopCouponService shopCouponService;

    @Autowired
    private ShopNowgoService shopNowgoService;

    @Autowired
    private InquireService inquireService;

    @Autowired
    private AWSService awsService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopDetailService shopDetailService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private RandomShopService randomShopService;

    @Autowired
    private FavDetailService favDetailService;

    @Autowired
    PlatformTransactionManager txManager;

    // Admin Auth mail login
    @RequestMapping(value = "/api/admin/auth/login/", method = RequestMethod.POST)
    @ResponseBody
    public JsonObject adminAuthLogin(@RequestBody MultiValueMap<String, String> formData, HttpServletResponse response, HttpServletRequest hsr) throws APIException {
        logger.debug("--------" + hsr.getHeader("Content-Type"));
        logger.debug("--------" + formData);
        logger.debug("--------" + formData.getFirst("login_name"));
        logger.debug("--------" + formData.getFirst("password"));

        String loginName = formData.getFirst("login_name");
        String password = formData.getFirst("password");

        AdminCustomer adminCustomer = adminService.getAdminCustomerByLoginNameAndPassword(loginName, StringUtil.sha256(password));

        if (adminCustomer == null) throw new APIException(JsonStatus.DATA_NOT_FOUND);

        String sessionId = StringUtil.getSessionId();
        authService.insertSessionIdByUuidAndSessionId(loginName, sessionId);

        Cookie sidCookie = new Cookie(Constant.SESSION.ADMIN_SESSIONID, sessionId);
        sidCookie.setPath("/");
        response.addCookie(sidCookie);

        // Notification count
        int unreadCount = notificationService.getNotifyUnreadCountByCondition(adminCustomer.getId(), Constant.NOTIFICATION.USER_TYPE_ADMIN);

        AdminLoginJsonParse adminLoginJsonParse = new AdminLoginJsonParse();
        adminLoginJsonParse.setAdminType(String.valueOf(adminCustomer.getCustomerType()));
        adminLoginJsonParse.setUnreadCount(unreadCount);

        List<AdminLoginJsonParse> laljp = new ArrayList<>();
        laljp.add(adminLoginJsonParse);
        jsonObject.setResultList(laljp);

        return jsonObject.withStatus(JsonStatus.SUCCESS);

    }

    private class AdminLoginJsonParse {
        String adminType;
        int unreadCount;

        public String getAdminType() {
            return adminType;
        }

        public void setAdminType(String adminType) {
            this.adminType = adminType;
        }

        public int getUnreadCount() {
            return unreadCount;
        }

        public void setUnreadCount(int unreadCount) {
            this.unreadCount = unreadCount;
        }
    }

    // Admin Rise timeline
    @RequestMapping(value = "/api/admin/rise/timeline/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject adminRiseTimeLine(HttpServletRequest hsr) throws APIException {

        Long adminId = adminService.getAdminUserId(hsr);
        // todo: connect admin to setrise id
        // todo: long id
        int riseId = 1;
        List<Comment> commentList = commentService.getCommentByItem(riseId, Constant.COMMENT_TYPE.RISE);
        List<AdminRiseFeedJsonParse> acrdjpList = new ArrayList<>();
        for (Comment cd : commentList) {
            AdminRiseFeedJsonParse acrdjp = new AdminRiseFeedJsonParse();
            acrdjp.setId(cd.getId().toString());
            acrdjp.setCreateDatetime(DateUtil.dateToStringyyyy_MM_dd(cd.getCreateDatetime()));
            acrdjp.setDesc(cd.getDesc());
            acrdjp.setPicUrl1(cd.getPicUrl1());
            acrdjp.setPicUrl2(cd.getPicUrl2());
            acrdjpList.add(acrdjp);
        }
        jsonObject.setResultList(acrdjpList);
        return jsonObject;
    }

    // Admin Shop timeline
    @RequestMapping(value = "/api/admin/shop/timeline/{limit}/{offset}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject adminShopTimeLine(@PathVariable @Min(value = 1, message = "Limit can't be less than 1") int limit,
                                        @PathVariable @Min(value = 0, message = "Offset can't be less than 0") int offset,
                                        HttpServletRequest hsr) throws APIException {

        AdminCustomer adminCustomer = adminService.getAdminCustomer(hsr);
        Long shopId = adminCustomer.getShopId();
        List<ShopCoupon> shopCouponsList = shopCouponService.getListByShopId(shopId, limit, offset);
        List<Object> asfjpList = new ArrayList<Object>();

        for (ShopCoupon sc : shopCouponsList) {
            AdminShopFeedJsonParse asfjp = new AdminShopFeedJsonParse();
            asfjp.setId(sc.getId().toString());
            asfjp.setCreateDatetime(DateUtil.dateToStringyyyy_MM_dd(sc.getCreateDatetime()));
            asfjp.setTitle(sc.getTitle());
            asfjp.setDesc(sc.getDesc());
            asfjp.setPicUrl(sc.getPicUrl1());
            asfjp.setPublishStart(DateUtil.dateToStringyyyy_MM_dd_HH_mm(sc.getPublishStart()));
            asfjp.setPublishEnd(DateUtil.dateToStringyyyy_MM_dd_HH_mm(sc.getPublishEnd()));
            asfjp.setDelFlg(sc.getDelFlg());
            asfjpList.add(asfjp);
        }

        Shop shop = shopService.getShopById(shopId);
        ShopDetail shopDetail = shopDetailService.getShopDetailByShopId(shopId);
        Map<String, Object> m = new HashMap<>();
        if (shop == null || shopDetail == null) {
            m.put("isNow5", false);
            m.put("now5Desc", "未設定");
        } else {
            if(shop.getNow5() == 1){
                m.put("isNow5", true);
                m.put("now5Desc", shopDetail.getNow5());
            }else{
                m.put("isNow5", false);
                m.put("now5Desc", "未設定");
            }
        }
        m.put("couponList", asfjpList);
        jsonObject.setResultList(m);
        return jsonObject;
    }

    // Admin Rise Feed
    @RequestMapping(value = "/api/admin/rise/feed/", method = RequestMethod.POST)
    @ResponseBody
    public JsonObject adminRiseFeed(
            @RequestParam("rise_pic1") MultipartFile risePic1,
            @RequestParam("rise_pic2") MultipartFile risePic2,
            @RequestParam("rise_constant") String riseConstant,
            HttpServletRequest hsr) throws APIException, IOException {

        Long adminId = adminService.getAdminUserId(hsr);

        // todo: connect admin to setrise id
        // todo: long id
        long riseId = 1;

        Comment comment = new Comment();
        comment.setPicUrl1(awsService.postFile(risePic1));
        comment.setPicUrl2(awsService.postFile(risePic2));
        comment.setDesc(riseConstant);
        comment.setCommentType((byte) Constant.COMMENT_TYPE.RISE);
        comment.setItemId(riseId);
        comment.setSortScore(Constant.ZERO);
        commentService.addComment(comment);

        return jsonObject.withStatus(JsonStatus.SUCCESS);

    }

    // Admin Rise Update
    @RequestMapping(value = "/api/admin/rise/update/", method = RequestMethod.POST)
    @ResponseBody
    public JsonObject adminRiseUpdate(
            @RequestParam("rise_pic1") MultipartFile risePic1,
            @RequestParam("rise_pic2") MultipartFile risePic2,
            @RequestParam("rise_constant") String riseConstant,
            @RequestParam("comment_id") int commentId,
            HttpServletRequest hsr) throws APIException, IOException {

        Long adminId = adminService.getAdminUserId(hsr);
        // todo: connect admin to setrise id
        // todo: long id
        long riseId = 1;

        Comment comment = commentService.getById(commentId);

        if (!risePic1.isEmpty()) comment.setPicUrl1(awsService.postFile(risePic1));
        if (!risePic2.isEmpty()) comment.setPicUrl2(awsService.postFile(risePic2));
        comment.setDesc(riseConstant);
        comment.setUpdateDatetime(DateUtil.getNowDate());
        commentService.updateCommentByEntity(comment);

        return jsonObject.withStatus(JsonStatus.SUCCESS);
    }

    // Admin Rise Delete
    @RequestMapping(value = "/api/admin/rise/delete/", method = RequestMethod.POST)
    @ResponseBody
    public JsonObject adminRiseDelete(
            @RequestParam("comment_id") int commentId,
            HttpServletRequest hsr) throws APIException, IOException {

        Long adminId = adminService.getAdminUserId(hsr);

        // todo: connect admin to setrise id
        // todo: long id
        long riseId = 1;

        Comment comment = commentService.getById(commentId);
        if (comment == null) throw new APIException(JsonStatus.DATA_NOT_FOUND);
        commentService.deleteCommentById((int) comment.getId().longValue());

        return jsonObject.withStatus(JsonStatus.SUCCESS);
    }

    // Admin Now4 Feed
    @RequestMapping(value = "/api/admin/shop/now4/feed/", method = RequestMethod.POST)
    @ResponseBody
    public JsonObject adminNow4Feed(
            @RequestParam("coupon_pic") MultipartFile multipartFile,
            @RequestParam("coupon_title") String couponTitle,
            @RequestParam("coupon_content") String couponContent,
            @RequestParam("publish_start") String publishStart,
            @RequestParam("publish_end") String publishEnd,
            HttpServletRequest hsr) throws APIException, IOException {
        logger.debug("----1----");
        logger.debug("----2----coupon_pic=" + multipartFile.getContentType());
        logger.debug("----3----coupon_pic=" + multipartFile.getOriginalFilename());
        logger.debug("----4----coupon_pic=" + multipartFile.getSize());
        logger.debug("----5----coupon_title=" + couponTitle);
        logger.debug("----6----coupon_content=" + couponContent);
        AdminCustomer adminCustomer = adminService.getAdminCustomer(hsr);
        logger.debug("----7----customerId=" + adminCustomer.getId());
        // todo: connect admin to set shop id
        long shopId = adminCustomer.getShopId();
        Shop shop = new Shop();
        shop = shopService.getShopById(shopId);
        if (shop == null) throw new APIException(JsonStatus.DATA_NOT_FOUND);
        ShopCoupon shopCoupon = new ShopCoupon();
        shopCoupon.setShopId(shopId);
        shopCoupon.setTitle(couponTitle);
        shopCoupon.setDesc(couponContent);
        logger.debug("----8----");
        shopCoupon.setPicUrl1(awsService.postFile(multipartFile));
        if(shop!= null){
            shopCoupon.setCategory((byte)shop.getShopType().intValue());
        }else{
            shopCoupon.setCategory((byte)0);
        }
        shopCoupon.setPublishStart(DateUtil.stringToDateyyyy_MM_dd_HH_mm(publishStart));
        shopCoupon.setPublishEnd(DateUtil.stringToDateyyyy_MM_dd_HH_mm(publishEnd));
        shopCoupon.setUpdateDatetime(DateUtil.getNowDate());
        shopCoupon.setCreateDatetime(DateUtil.getNowDate());
        logger.debug("----9----");

        long cnt = shopCouponService.add(shopCoupon);

        //insert notify now4
        List<FavDetail> favList =  favDetailService.getFavByShopId(shopId);
        if(favList.size() > 0){
            logger.debug("----10---favList size-"+favList.size());
            for(FavDetail fd : favList){
                NotifyNow45 notifyNow45 = new NotifyNow45();
                notifyNow45.setUserId(fd.getUserId());
                if(shop != null){
                    notifyNow45.setItemId(cnt);
                }
                notifyNow45.setPushType(Constant.NOTIFY_NOW45.NOTIFY_TYPE_PUSH);
                notifyNow45.setNotifyType(Constant.NOTIFY_NOW45.now4);
                notifyNow45.setNotifyStatus(Constant.NOTIFY_NOW45.NOTIFY_STATUS_UNOPENED);
                notifyNow45.setPushStatus(Constant.NOTIFY_NOW45.PUSH_STATUS_UNSENT);
                notifyNow45.setPublishStart(DateUtil.stringToDateyyyy_MM_dd_HH_mm(publishStart));
                notifyNow45.setPublishEnd(DateUtil.stringToDateyyyy_MM_dd_HH_mm(publishEnd));
                notifyNow45.setCreateDatetime(DateUtil.getNowDate());
                notifyNow45.setUpdateDatetime(DateUtil.getNowDate());
                notifyNow45.setDelFlg(false);
                notifyNow45.setNote("");
                notificationService.insertNotifyNow45(notifyNow45);
                logger.debug("----11---user id =---"+fd.getUserId());
            }
        }

        //update shop now4
        String datenow = DateUtil.dateToStringYyyy_MM_dd_hhmm(new Date());
        Date date = DateUtil.stringToDateyyyy_MM_dd_HH_mm(datenow);
        Date publishStartDate = DateUtil.stringToDateyyyy_MM_dd_HH_mm(publishStart);
        Date publishEndDate =DateUtil.stringToDateyyyy_MM_dd_HH_mm(publishEnd);

        if(publishStartDate.getTime()<= date.getTime() && publishEndDate.getTime() >= date.getTime()){
            shop.setNow4(1);
        }else{
            shop.setNow4(0);
        }
        logger.debug("----12---now4-"+shop.getNow4());
        shop.setId(shopId);
        shopService.updateShopByEntity(shop);
        logger.debug("----13----shopid"+shop.getId());

        //update random shop now4
        Shop shop2 = new Shop();
        shop2 = shopService.getShopById(shopId);
        if (shop2 == null) throw new APIException(JsonStatus.DATA_NOT_FOUND);
        logger.debug("----14----shop2 id=" + shop2.getId());
        if(shop2 != null){
            RandomShopOne rso = new RandomShopOne();
            if(publishStartDate.getTime()<=date.getTime() && publishEndDate.getTime() >= date.getTime()){
                rso.setNow4((byte)1);
            }else{
                rso.setNow4((byte)0);
            }
            rso.setShopType((byte)shop2.getShopType().intValue());
            rso.setUuid(shop2.getUuid());
            rso.setUpdateDatetime(new Date(System.currentTimeMillis()));
            randomShopService.updateRandomShopOne(rso);

            RandomShopTwo rst = new RandomShopTwo();
            if(publishStartDate.getTime()<=date.getTime() && publishEndDate.getTime() >= date.getTime()){
                rst.setNow4((byte)1);
            }else{
                rst.setNow4((byte)0);
            }
            rst.setShopType((byte)shop2.getShopType().intValue());
            rst.setUuid(shop2.getUuid());
            rst.setUpdateDatetime(new Date(System.currentTimeMillis()));
            randomShopService.updateRandomShopTwo(rst);
        }
        logger.debug("----15----cnt=" + cnt);
        return jsonObject.withStatus(JsonStatus.SUCCESS);
    }

    // Admin Now4 Update
    @RequestMapping(value = "/api/admin/shop/now4/update/", method = RequestMethod.POST)
    @ResponseBody
    public JsonObject adminNow4Update(
            @RequestParam("coupon_pic") MultipartFile multipartFile,
            @RequestParam("coupon_title") String couponTitle,
            @RequestParam("coupon_content") String couponContent,
            @RequestParam("coupon_id") long couponId,
            @RequestParam("publish_start") String publishStart,
            @RequestParam("publish_end") String publishEnd,
            HttpServletRequest hsr) throws APIException, IOException {
        logger.debug("----1----");
        if(!multipartFile.isEmpty()){
            logger.debug("----2----coupon_pic=" + multipartFile.getContentType());
            logger.debug("----3----coupon_pic=" + multipartFile.getOriginalFilename());
            logger.debug("----4----coupon_pic=" + multipartFile.getSize());
        }else{
            logger.debug("----2-4 ----Not uploaded coupon_pic");
        }
        logger.debug("----5----coupon_title=" + couponTitle);
        logger.debug("----6----coupon_content=" + couponContent);
        logger.debug("----7----coupon_id=" + couponId);
        AdminCustomer adminCustomer = adminService.getAdminCustomer(hsr);
        logger.debug("----7----customerId=" + adminCustomer.getId());
        Long shopId = adminCustomer.getShopId();
        ShopCoupon shopCoupon = shopCouponService.getCouponById(couponId);

        if (shopCoupon == null) {
            logger.warn("Shop coupon data not found, coupon_id=" + couponId);
            return jsonObject.withStatus(JsonStatus.DATA_NOT_FOUND);
        }

        if ((DateUtil.getNowUnixTime() - DateUtil.dateToUnixTime(shopCoupon.getCreateDatetime()) > 3600 * 1000)) {
            logger.warn("Shop coupon update date is expired, coupon_id=" + couponId);
            return jsonObject.withStatus(JsonStatus.COUPON_DATE_EXPIRED);
        }

        logger.debug("----9----");
        if (!multipartFile.isEmpty()) shopCoupon.setPicUrl1(awsService.postFile(multipartFile));
        shopCoupon.setTitle(couponTitle);
        shopCoupon.setDesc(couponContent);
        shopCoupon.setPublishStart(DateUtil.stringToDateyyyy_MM_dd_HH_mm(publishStart));
        shopCoupon.setPublishEnd(DateUtil.stringToDateyyyy_MM_dd_HH_mm(publishEnd));
        shopCoupon.setUpdateDatetime(DateUtil.getNowDate());

        long cnt = shopCouponService.update(shopCoupon);
        logger.debug("----10----cnt=" + cnt);

        String datenow = DateUtil.dateToStringYyyy_MM_dd_hhmm(new Date());
        Date date = DateUtil.stringToDateyyyy_MM_dd_HH_mm(datenow);
        Date publishStartDate = DateUtil.stringToDateyyyy_MM_dd_HH_mm(publishStart);
        Date publishEndDate =DateUtil.stringToDateyyyy_MM_dd_HH_mm(publishEnd);
        Shop shop = new Shop();
        if(publishStartDate.getTime()<=date.getTime() && publishEndDate.getTime() >= date.getTime()){
            shop.setNow4(1);
        }else{
            shop.setNow4(0);
        }
        logger.debug("----11---now4-"+shop.getNow4());
        shop.setId(shopId);
        shopService.updateShopByEntity(shop);
        Shop shop2 = new Shop();
        if(shopId != null){
            shop2 = shopService.getShopById(shopId);
        }
        if (shop2 == null) throw new APIException(JsonStatus.DATA_NOT_FOUND);
        if(shop2 != null){
            RandomShopOne rso = new RandomShopOne();
            if(publishStartDate.getTime()<=date.getTime() && publishEndDate.getTime() >= date.getTime()){
                rso.setNow4((byte)1);
            }else{
                rso.setNow4((byte)0);
            }
            rso.setShopType((byte)shop2.getShopType().intValue());
            rso.setUuid(shop2.getUuid());
            rso.setUpdateDatetime(new Date(System.currentTimeMillis()));
            randomShopService.updateRandomShopOne(rso);

            RandomShopTwo rst = new RandomShopTwo();
            if(publishStartDate.getTime()<=date.getTime() && publishEndDate.getTime() >= date.getTime()){
                rst.setNow4((byte)1);
            }else{
                rst.setNow4((byte)0);
            }
            rst.setShopType((byte)shop2.getShopType().intValue());
            rst.setUuid(shop2.getUuid());
            rst.setUpdateDatetime(new Date(System.currentTimeMillis()));
            randomShopService.updateRandomShopTwo(rst);
        }
        jsonObject.setResultList(null);
        return jsonObject.withStatus(JsonStatus.SUCCESS);
    }

    // Admin Now4 Update
    @RequestMapping(value = "/api/admin/shop/now4/delete/", method = RequestMethod.POST)
    @ResponseBody
    public JsonObject adminNow4Delete(
            @RequestParam("coupon_id") long couponId,
            HttpServletRequest hsr) throws APIException, IOException {
        logger.debug("----1----");
        logger.debug("----2----coupon_id=" + couponId);
//            AdminCustomer adminCustomer = adminService.getAdminCustomer(hsr);
//        logger.debug("----7----customerId=" + adminCustomer.getId());

        ShopCoupon shopCoupon = shopCouponService.getCouponById(couponId);

//        if ((DateUtil.getNowUnixTime() - DateUtil.dateToUnixTime(shopCoupon.getCreateDatetime()) > 3600 * 1000)) {
//            logger.warn("Shop coupon update date is expired, coupon_id=" + couponId);
//            return jsonObject.withStatus(JsonStatus.COUPON_DATE_EXPIRED);
//        }
        
        shopCoupon.setUpdateDatetime(DateUtil.getNowDate());
        shopCoupon.setDelFlg(Boolean.TRUE);
        long cnt = shopCouponService.update(shopCoupon);
        logger.debug("----3----cnt=" + cnt);
        Shop shop = new Shop();
        shop.setNow4(0);
        shop.setId(shopCoupon.getShopId());
        shopService.updateShopByEntity(shop);

        NotifyNow45 notifyNow45 = new NotifyNow45();
        Shop shop3 = shopService.getShopById(shopCoupon.getShopId());
        if(shop3 != null){
            notifyNow45.setItemId(cnt);
        }
        notifyNow45.setDelFlg(true);
        notifyNow45.setNotifyType(Constant.NOTIFY_NOW45.now4);
        notifyNow45.setUpdateDatetime(DateUtil.getNowDate());
        notificationService.deleteNotifyNow45(notifyNow45);

        Shop shop2 = new Shop();
        shop2 = shopService.getShopById(shopCoupon.getShopId());
        if (shop2 == null) throw new APIException(JsonStatus.DATA_NOT_FOUND);
        logger.debug("----3----shop2 id =" + shop2.getId());
        if(shop2 != null){
            RandomShopOne rso = new RandomShopOne();
            rso.setNow4((byte)0);
            rso.setShopType((byte)shop2.getShopType().intValue());
            rso.setUuid(shop2.getUuid());
            rso.setUpdateDatetime(new Date(System.currentTimeMillis()));
            randomShopService.updateRandomShopOne(rso);

            RandomShopTwo rst = new RandomShopTwo();
            rst.setNow4((byte)0);
            rst.setShopType((byte)shop2.getShopType().intValue());
            rst.setUuid(shop2.getUuid());
            rst.setUpdateDatetime(new Date(System.currentTimeMillis()));
            randomShopService.updateRandomShopTwo(rst);
        }

        return jsonObject.withStatus(JsonStatus.SUCCESS);
    }

    // Admin Now5 Feed
    @RequestMapping(value = "/api/admin/shop/now5/feed/", method = RequestMethod.POST)
    @ResponseBody
    public JsonObject adminNow5Feed(
            @RequestParam("now5") Boolean now5,
            @RequestParam("now5_content") String shopContent,
            HttpServletRequest hsr) throws APIException, IOException {

        logger.debug("--1--now5=" + now5);
        logger.debug("--2--now5_content=" + shopContent);

        AdminCustomer adminCustomer = adminService.getAdminCustomer(hsr);
        // todo: connect admin to set shop id
        Long shopId = adminCustomer.getShopId();
        logger.debug("--3--shopId=" + shopId);
        Shop shop = shopService.getShopById(shopId);
        if (shop == null) throw new APIException(JsonStatus.DATA_NOT_FOUND);
        List<ShopDetail> shopDetailList = shopDetailService.getShopDetailAll(shopId);
//        ShopDetail shopDetail = shopDetailService.getShopDetailByShopId(shop.getId());
//        if (shopDetail == null) throw new APIException(JsonStatus.DATA_NOT_FOUND);

        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try {
            long nowgoId =0L;
            if(shop != null){
                shop.setNow5(now5 ? 1 : 0);
                shop.setUpdateDatetime(new Date(System.currentTimeMillis()));
                shopService.updateShopByEntity(shop);
                ShopDetail shopDetail = shopDetailList.get(0);
                shopDetail.setNow5(shopContent);
                shopDetailService.updateShopDetailByEntity(shopDetail);
                ShopNowgo nowgo = shopNowgoService.getShopNowgoByShopId(shop.getId());

                if(now5){
                    if (nowgo == null ){
                        ShopNowgo nowgoNew = new ShopNowgo();
                        nowgoNew.setShopId(shop.getId());
                        nowgoNew.setCategory((byte)shop.getShopType().intValue());
                        nowgoNew.setTitle("");
                        nowgoNew.setPicUrl1("");
                        nowgoNew.setDesc(shopContent);
                        nowgoNew.setPublishStart(DateUtil.getDefaultDate());
                        nowgoNew.setPublishEnd(DateUtil.getDefaultPublishEnd());
                        nowgoNew.setUpdateDatetime(new Date(System.currentTimeMillis()));
                        nowgoNew.setCreateDatetime(new Date(System.currentTimeMillis()));
                        nowgoId = shopNowgoService.insertShopNowgo(nowgoNew);

                        //insert notify now5
                        List<FavDetail> favList =  favDetailService.getFavByShopId(shopId);
                        if(favList.size() > 0){
                            logger.debug("----10---favList size-"+favList.size());
                            for(FavDetail fd : favList){
                                NotifyNow45 notifyNow45 = new NotifyNow45();
                                notifyNow45.setUserId(fd.getUserId());
                                if(shop != null){
                                    notifyNow45.setItemId(nowgoId);
                                }
                                notifyNow45.setPushType(Constant.NOTIFY_NOW45.NOTIFY_TYPE_PUSH);
                                notifyNow45.setNotifyType(Constant.NOTIFY_NOW45.now5);
                                notifyNow45.setNotifyStatus(Constant.NOTIFY_NOW45.NOTIFY_STATUS_UNOPENED);
                                notifyNow45.setPushStatus(Constant.NOTIFY_NOW45.PUSH_STATUS_UNSENT);
                                notifyNow45.setPublishStart(DateUtil.getNowDate());
                                notifyNow45.setPublishEnd(DateUtil.getNowDate());
                                notifyNow45.setCreateDatetime(DateUtil.getNowDate());
                                notifyNow45.setUpdateDatetime(DateUtil.getNowDate());
                                notifyNow45.setDelFlg(false);
                                notifyNow45.setNote("");
                                notificationService.insertNotifyNow45(notifyNow45);
                                logger.debug("----11---user id =---"+fd.getUserId());
                            }
                        }
                    }else {
                        nowgo.setDesc(shopContent);
                        nowgo.setUpdateDatetime(new Date(System.currentTimeMillis()));
                        nowgoId = shopNowgoService.updateShopNowgoByEntity(nowgo);
                    }
                }
            }

            Shop shop2 = new Shop();
            if(shopId != null){
                shop2 = shopService.getShopById(shopId);
            }
            logger.debug("--4--shop2=" + shop2.getId());
            if(shop2 != null ){
                RandomShopOne rso = new RandomShopOne();
                rso.setNow5((byte)(now5 ? 1 : 0));
                rso.setShopType((byte)shop2.getShopType().intValue());
                rso.setUuid(shop2.getUuid());
                rso.setUpdateDatetime(new Date(System.currentTimeMillis()));
                randomShopService.updateRandomShopOne(rso);

                RandomShopTwo rst = new RandomShopTwo();
                rst.setNow5((byte)(now5 ? 1 : 0));
                rst.setShopType((byte)shop2.getShopType().intValue());
                rst.setUuid(shop2.getUuid());
                rst.setUpdateDatetime(new Date(System.currentTimeMillis()));
                randomShopService.updateRandomShopTwo(rst);
            }
            txManager.commit(txStatus);
        } catch (Exception e) {
            txManager.rollback(txStatus);
            logger.error("Admin app now5 feed failed, error=" + e.getMessage());
            logger.error("Admin app now5 feed, error=" + e.toString());
            e.printStackTrace();
            throw new APIException(JsonStatus.UNKNOWN_ERROR);
        }
        jsonObject.setResultList(null);
        return jsonObject.withStatus(JsonStatus.SUCCESS);
    }

    // Admin Now4 Update
    @RequestMapping(value = "/api/admin/shop/now5/delete/", method = RequestMethod.POST)
    @ResponseBody
    public JsonObject adminNow5Delete(
            @RequestParam("now5") Boolean now5,
            @RequestParam("now5_content") String shopContent,
            HttpServletRequest hsr) throws APIException, IOException {

        logger.debug("----1----now5=" + now5);
        AdminCustomer adminCustomer = adminService.getAdminCustomer(hsr);
        logger.debug("----2----customerId=" + adminCustomer.getId());
        ShopNowgo nowgo = shopNowgoService.getShopNowgoByShopId(adminCustomer.getShopId());
        logger.debug("----3----shopid="+nowgo.getShopId());
        nowgo.setUpdateDatetime(DateUtil.getNowDate());
        nowgo.setDelFlg(Boolean.TRUE);
        long cnt = shopNowgoService.updateShopNowgoByEntity(nowgo);
        logger.debug("----4----cnt=" + cnt);
        Shop shop = new Shop();
        shop.setNow5(0);
        shop.setId(nowgo.getShopId());
        shopService.updateShopByEntity(shop);

        NotifyNow45 notifyNow45 = new NotifyNow45();
        Shop shop3 = shopService.getShopById(nowgo.getShopId());
        if(shop3 != null){
            notifyNow45.setItemId(cnt);
        }
        notifyNow45.setDelFlg(true);
        notifyNow45.setNotifyType(Constant.NOTIFY_NOW45.now5);
        notifyNow45.setUpdateDatetime(DateUtil.getNowDate());
        notificationService.deleteNotifyNow45(notifyNow45);

        Shop shop2 = new Shop();
        shop2 = shopService.getShopById(nowgo.getShopId());
        if(shop2 != null){
            RandomShopOne rso = new RandomShopOne();
            rso.setNow5((byte)0);
            rso.setShopType((byte)shop2.getShopType().intValue());
            rso.setUuid(shop2.getUuid());
            rso.setUpdateDatetime(new Date(System.currentTimeMillis()));
            randomShopService.updateRandomShopOne(rso);

            RandomShopTwo rst = new RandomShopTwo();
            rst.setNow5((byte)0);
            rst.setShopType((byte)shop2.getShopType().intValue());
            rst.setUuid(shop2.getUuid());
            rst.setUpdateDatetime(new Date(System.currentTimeMillis()));
            randomShopService.updateRandomShopTwo(rst);
        }
        return jsonObject.withStatus(JsonStatus.SUCCESS);
    }

    // Admin inquire post
    @RequestMapping(value = "/api/admin/inquire/", method = RequestMethod.POST)
    @ResponseBody
    public JsonObject adminInquire(@RequestBody MultiValueMap<String, String> formData, HttpServletRequest hsr) throws APIException {

        String contents = formData.getFirst("contents");
        AdminCustomer adminCustomer = adminService.getAdminCustomer(hsr);

        Inquire inquire = new Inquire();
        inquire.setUserId(adminCustomer.getId());
        inquire.setMail(adminCustomer.getMail());
        inquire.setContents(contents);
        inquire.setStatus((byte)Constant.INQUIRE_STATUS.INQUIRE_STATUS_UNFINISH);
        inquire.setType((byte) Constant.INQUIRE_TYPE.ADMIN);
        inquireService.addInquire(inquire);

        return jsonObject.withStatus(JsonStatus.SUCCESS);

    }

    // Admin notification list
    @RequestMapping(value = "/api/admin/notification/list/{limit}/{offset}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject adminNotificationList(@PathVariable @Min(value = 1, message = "Limit can't be less than 1") int limit,
                                            @PathVariable @Min(value = 0, message = "Offset can't be less than 0") int offset,
                                            HttpServletRequest hsr) throws APIException {
        AdminCustomer adminCustomer = adminService.getAdminCustomer(hsr);

        List<NotificationEntire> dataList = notificationService.getNotifyEntireAllByCondition(adminCustomer.getId(), Constant.NOTIFICATION.USER_TYPE_ADMIN, limit, offset);
        List<AdminNotificationJsonParse> resultList = new ArrayList<>();
        for (NotificationEntire notification : dataList) {
            AdminNotificationJsonParse jsonParse = new AdminNotificationJsonParse();
            jsonParse.setId(notification.getId());
            jsonParse.setTitle(notification.getTitle());
            jsonParse.setContent(notification.getContent());
            jsonParse.setNotifyStatus(notification.getNotifyStatus());
            jsonParse.setCreateDatetime(notification.getCreateDatetime());
            resultList.add(jsonParse);
        }

        int unreadCount = notificationService.getNotifyUnreadCountByCondition(adminCustomer.getId(), Constant.NOTIFICATION.USER_TYPE_ADMIN);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("data", resultList);
        resultMap.put("unreadCount", unreadCount);

        jsonObject.setResultList(resultMap);
        return jsonObject.withStatus(JsonStatus.SUCCESS);

    }

    // Admin notification read
    @RequestMapping(value = "/api/admin/notification/read/", method = RequestMethod.POST)
    @ResponseBody
    public JsonObject adminNotificationRead(
            @RequestParam("notification_id") int notificationId,
            HttpServletRequest hsr) throws APIException, IOException {

        AdminCustomer adminCustomer = adminService.getAdminCustomer(hsr);

        Notification notification = notificationService.getById(notificationId);
        if (notification == null) throw new APIException(JsonStatus.DATA_NOT_FOUND);
        notificationService.updateNotificationReadStatus(notification.getId(), Constant.NOTIFICATION.NOTIFY_STATUS_OPENED);

        int unreadCount = notificationService.getNotifyUnreadCountByCondition(adminCustomer.getId(), Constant.NOTIFICATION.USER_TYPE_ADMIN);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("unreadCount", unreadCount);

        jsonObject.setResultList(resultMap);
        return jsonObject.withStatus(JsonStatus.SUCCESS);
    }

    //Add notification device info
    @RequestMapping(value = "/api/admin/notification/register/", method = RequestMethod.POST)
    @ResponseBody
    public JsonObject registerNotificationDevice(@Valid NotificationRegisterParams params, HttpServletRequest hsr) throws APIException {
//        if (result.hasErrors()) {
//            throw new APIException(JsonStatus.PARAMETER_ERROR);
//        }
        AdminCustomer adminCustomer = adminService.getAdminCustomer(hsr);
        if(adminCustomer != null){
            logger.debug("----1----adminCustomer id=" + adminCustomer.getId());
            NotificationDevice nfdForIos = notificationService.getNotificationDeviceByUserId(adminCustomer.getId(),Constant.NOTIFICATION.USER_TYPE_ADMIN,Constant.NOTIFICATION.NOTIFY_DEVICE_IOS);
            NotificationDevice nfdForAnd = notificationService.getNotificationDeviceByUserId(adminCustomer.getId(),Constant.NOTIFICATION.USER_TYPE_ADMIN,Constant.NOTIFICATION.NOTIFY_DEVICE_ANDROID);
            if(nfdForIos == null && nfdForAnd == null){
                long newId = notificationService.insertNotificationDevice(adminCustomer.getId(), Constant.NOTIFICATION.USER_TYPE_ADMIN, params.getDeviceId(), params.getOs(), params.getToken());
                logger.debug("----2--NotificationDevice id = "+ newId);
            }else if(nfdForIos != null && nfdForAnd == null){
                logger.debug("----3--ios NotificationDevice id = "+ nfdForIos.getId());
                long newId = notificationService.updateNotificationDeviceByUserId(adminCustomer.getId(), Constant.NOTIFICATION.USER_TYPE_ADMIN, params.getDeviceId(), params.getOs(), params.getToken(),false);
                logger.debug("----4----userId=" + newId);
            }else if(nfdForIos == null && nfdForAnd != null){
                logger.debug("----5--Android NotificationDevice id = "+ nfdForAnd.getId());
                long newId = notificationService.updateNotificationDeviceByUserId(adminCustomer.getId(), Constant.NOTIFICATION.USER_TYPE_ADMIN, params.getDeviceId(), params.getOs(), params.getToken(),false);
                logger.debug("----6----userId=" + newId);
            }
        }
        jsonObject.setResultList(null);
        return jsonObject.withStatus(JsonStatus.SUCCESS);
    }

}
