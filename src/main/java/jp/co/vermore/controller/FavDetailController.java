package jp.co.vermore.controller;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.JsonObject;
import jp.co.vermore.common.JsonStatus;
import jp.co.vermore.common.mvc.APIException;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.entity.*;
import jp.co.vermore.form.FavoriteForm;
import jp.co.vermore.jsonparse.FavoriteJsonParse;
import jp.co.vermore.jsonparse.NotificationJsonParse;
import jp.co.vermore.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * FavDetailController
 * Created by wubin.
 * <p>
 * DateTime: 2018/04/12 11:10
 * Copyright: sLab, Corp
 */
@Controller
public class FavDetailController extends BaseController {

    @Autowired
    private FavDetailService favDetailService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private PlaceService placeService;

    @Autowired
    private EventService eventService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private FreePaperService freePaperService;

    @Autowired
    private RecruitService recruitService;

    @Autowired
    private TvDetailService tvDetailService;

    @Autowired
    private TvService tvService;

    @Autowired
    private PersonService personService;

    @Autowired
    private CoinService coinService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AuthService authService;

    @Autowired
    private ShopNowgoService shopNowgoService;

    @Autowired
    private ShopCouponService shopCouponService;

    @Autowired
    PlatformTransactionManager txManager;

    @RequestMapping(value = "/api/favorite/{type}/{limit}/{offset}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getFavoriteList(@PathVariable byte type, @PathVariable int limit, @PathVariable int offset, HttpServletRequest hsr) throws APIException {

        long userId = authService.getUserId(hsr);
        logger.debug("----1----userId" + userId);
        List<FavDetail> list = favDetailService.getFavDetail(userId, type, limit, offset);
        logger.debug("----2----favList" + list.size());
        List<FavDetail> list2 = favDetailService.getFavDetailAll(userId, type);
        logger.debug("----3----favList" + list2.size());
        List<FavoriteJsonParse> fjpList = new ArrayList<>();

        for (FavDetail fd : list) {
            FavoriteJsonParse fjp = new FavoriteJsonParse();
            if (fd.getType() == Constant.FAV_TYPE.EVENT) {
                Event event = eventService.getById(fd.getShopId());
                if (event != null) {
                    fjp.setUuid(event.getUuid());
                    fjp.setName(event.getTitle());
                    fjp.setType(Constant.FAV_TYPE.EVENT);
                    fjp.setStatus(Constant.FAV_TYPE.EVENT);
                    fjp.setPicUrl(event.getPicUrl());
                    String date = DateUtil.format(fd.getCreateDatetime(), "yyyy年MM月dd日");
                    fjp.setCreateDatetime(date);
                    fjpList.add(fjp);
                }
            } else if (fd.getType() == Constant.FAV_TYPE.GOODS) {
                Goods goods = goodsService.getGoodsById(fd.getShopId());
                if (goods != null) {
                    fjp.setUuid(goods.getUuid());
                    fjp.setName(goods.getTitle());
                    fjp.setType(Constant.FAV_TYPE.GOODS);
                    fjp.setStatus(Constant.FAV_TYPE.GOODS);
                    fjp.setPicUrl(goods.getPicUrl());
                    String date = DateUtil.format(fd.getCreateDatetime(), "yyyy年MM月dd日");
                    fjp.setCreateDatetime(date);
                    fjpList.add(fjp);
                }
            } else if (fd.getType() == Constant.FAV_TYPE.FREEPAPER) {
                FreePaper freePaper = freePaperService.getFreePaperById(fd.getShopId());
                if (freePaper != null) {
                    fjp.setUuid(freePaper.getUuid());
                    fjp.setName("Vol." + freePaper.getVolume());
                    fjp.setType(Constant.FAV_TYPE.FREEPAPER);
                    fjp.setStatus(Constant.FAV_TYPE.FREEPAPER);
                    fjp.setPicUrl(freePaper.getPicUrl());
                    String date = DateUtil.format(fd.getCreateDatetime(), "yyyy年MM月dd日");
                    fjp.setCreateDatetime(date);
                    fjpList.add(fjp);
                }
            } else if (fd.getType() == Constant.FAV_TYPE.RECRUIT) {
                Recruit recruit = recruitService.getRecruit(fd.getShopId());
                if (recruit != null) {
                    fjp.setUuid(recruit.getUuid());
                    fjp.setName(recruit.getRecruitName());
                    fjp.setType(Constant.FAV_TYPE.RECRUIT);
                    fjp.setStatus(Constant.FAV_TYPE.RECRUIT);
                    fjp.setPicUrl(recruit.getPicUrl1());
                    String date = DateUtil.format(fd.getCreateDatetime(), "yyyy年MM月dd日");
                    fjp.setCreateDatetime(date);
                    fjpList.add(fjp);
                }
            } else if (fd.getType() == Constant.FAV_TYPE.TV) {
                TvDetail tvDetail = tvDetailService.getTvDetail(fd.getShopId());
                if (tvDetail != null) {
                    Tv tv = tvService.getTvFavorite(tvDetail.getTvId());
                    fjp.setUuid(tv.getUuid());
                    fjp.setName(tv.getTitle());
                    fjp.setType(Constant.FAV_TYPE.TV);
                    fjp.setStatus(Constant.FAV_TYPE.TV);
                    fjp.setTvType(tv.getTvType().intValue());
                    fjp.setPicUrl(tv.getUrl());
                    String date = DateUtil.format(fd.getCreateDatetime(), "yyyy年MM月dd日");
                    fjp.setCreateDatetime(date);
                    fjpList.add(fjp);
                }
            } else if (fd.getType() == Constant.FAV_TYPE.PLACE) {
                Place place = placeService.getPlace(fd.getShopId());
                if (place != null) {
                    fjp.setUuid(place.getUuid());
                    fjp.setName(place.getName());
                    fjp.setType(Constant.FAV_TYPE.PLACE);
                    fjp.setStatus(Constant.FAV_TYPE.PLACE);
                    fjp.setPicUrl(place.getPicUrl());
                    String date = DateUtil.format(fd.getCreateDatetime(), "yyyy年MM月dd日");
                    fjp.setCreateDatetime(date);
                    fjpList.add(fjp);
                }
            } else if (fd.getType() == Constant.FAV_TYPE.SHOP) {
                Shop shop = shopService.getShopById(fd.getShopId());
                if (shop != null) {
                    logger.debug("----2----shopid" + shop.getId());
                    UserSetting us = notificationService.getUserSettingBySettingKeyAndSettingValueAndByItemId(userId, Constant.USER_SETTING.NOW4, Constant.USER_SETTING.OFF, shop.getId());
                    fjp.setUuid(shop.getUuid());
                    fjp.setType(Constant.FAV_TYPE.SHOP);
                    fjp.setStatus(Constant.FAV_TYPE.SHOP);
                    fjp.setShopType(shop.getShopType());
                    fjp.setName(shop.getName());
                    fjp.setPicUrl(shop.getPicUrl());
                    if (us == null) {
                        fjp.setUserSetting("0");
                    } else {
                        fjp.setUserSetting("1");
                    }
                    String date = DateUtil.format(fd.getCreateDatetime(), "yyyy年MM月dd日");
                    fjp.setCreateDatetime(date);
                    fjpList.add(fjp);
                }
            }
        }

        List<Notification> notifyList = notificationService.getNotifyForMypage(userId);
        List<NotificationJsonParse> njplist = new ArrayList<NotificationJsonParse>();
        if (notifyList.size() > 0) {
            for (Notification nt : notifyList) {
                NotificationJsonParse njp = new NotificationJsonParse();
                njp.setId(nt.getId());
                njp.setStatus(nt.getNotifyStatus());
                njp.setNotifiContentId(nt.getNotifiContentId());
                njp.setDate(DateUtil.dateToStringyyyymmdd(nt.getCreateDatetime()));
                if (nt.getItemId() != Constant.NOTIFY_ITEM.NOW4 && nt.getItemId() != Constant.NOTIFY_ITEM.NOW5 && nt.getItemId() != null) {
                    NotificationContent nc = notificationService.getNotificationContent(nt.getNotifiContentId());
                    if (nc != null) {
                        njp.setTitle(nc.getTitle());
                        njp.setContent(nc.getContent());
                    }
                } else if (nt.getItemId() == Constant.NOTIFY_ITEM.NOW5) {
                    Shop shop = new Shop();
                    ShopNowgo sn = shopNowgoService.getShopNowgoById(nt.getNotifiContentId());
                    if (sn != null) {
                        shop = shopService.getShopById(sn.getShopId());
                        logger.debug("----3----ShopNowgo id=" + sn.getId());
                        if (shop != null) {
                            njp.setTitle(shop.getName());
                            logger.debug("----4----shop id=" + shop.getId());
                        }
                        njp.setContent(sn.getDesc());
                        Date nowGoTime = sn.getUpdateDatetime();
                        String now5time = DateUtil.timeDifference(nowGoTime);
                        njp.setTime(now5time);
                    }
                } else if (nt.getItemId() == Constant.NOTIFY_ITEM.NOW4) {
                    Shop shop = new Shop();
                    ShopCoupon sc = shopCouponService.getCouponById(nt.getNotifiContentId());
                    if (sc != null) {
                        shop = shopService.getShopById(sc.getShopId());
                        logger.debug("----5----ShopCoupon id=" + sc.getId());
                        if (shop != null) {
                            njp.setTitle(shop.getName());
                            logger.debug("----6----shop id=" + shop.getId());
                        }
                        njp.setContent(sc.getDesc());
                        Date nowForTime = sc.getUpdateDatetime();
                        String now4time = DateUtil.timeDifference(nowForTime);
                        njp.setTime(now4time);
                        njp.setPicUrl(sc.getPicUrl1());
                    }
                }
                njplist.add(njp);
            }
        }

        int count = list2.size();
        long coin = coinService.getAmount(userId);
        Person person = personService.getPersonById(userId);
        String nickName = person.getNickname();
        String thumbnailUrl = person.getThumbnailUrl();

        Map<String, Object> m = new HashMap<>();
        m.put("nickName", nickName);
        m.put("coin", coin);
        m.put("count", count);
        m.put("list", list2);
        m.put("thumbnailUrl", thumbnailUrl);
        m.put("notifyList", njplist);
        m.put("shopList", fjpList);
        jsonObject.setResultList(m);
        return jsonObject.withStatus(JsonStatus.SUCCESS);
    }

    @RequestMapping(value = "/api/favorite/insert/", method = RequestMethod.POST)
    @ResponseBody
    public JsonObject personInsert(@ModelAttribute FavoriteForm form, HttpServletRequest hsr) throws APIException {

        long userId = authService.getUserId(hsr);
        long favId = form.getFavId();
        Integer type = form.getType();
        FavDetail favCheck = favDetailService.getFavDetailByUserId(userId, favId, type);
        if (favCheck != null) {
            logger.warn("Duplication FavDetail, UserId=" + userId + ", favId=" + favId + "type=" + type);
            return jsonObject.withStatus(JsonStatus.USER_ID_INVALID);
        } else {
            try {
                FavDetail favDetail = new FavDetail();
                favDetail.setShopId(favId);
                favDetail.setType(type.byteValue());
                favDetail.setUserId(userId);
                favDetail.setCreateDatetime(new Date(System.currentTimeMillis()));
                favDetailService.insertFavDetail(favDetail);
            } catch (Exception e) {
                logger.error("Insert favorite failed!, error=" + e.getMessage());
                logger.error("Insert favorite failed!, error=" + e.toString());
                e.printStackTrace();
                throw new APIException(JsonStatus.DATA_SAVE_FAILED);
            }
            return jsonObject.withStatus(JsonStatus.SUCCESS);
        }
    }

    @RequestMapping(value = "/api/favorite/remove/", method = RequestMethod.POST)
    @ResponseBody
    public JsonObject favRemove(@ModelAttribute FavoriteForm form, HttpServletRequest hsr) throws APIException {

        long userId = authService.getUserId(hsr);
        long favId = form.getFavId();
        Integer type = form.getType();
        FavDetail favCheck = favDetailService.getFavDetailByUserId(userId, favId, type);
        if (favCheck == null) {
            logger.warn("Duplication FavDetail, UserId=" + userId + ", favId=" + favId + "type=" + type);
            return jsonObject.withStatus(JsonStatus.USER_ID_INVALID);
        } else {
            // トランザクション管理の開始
            DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
            txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            TransactionStatus txStatus = txManager.getTransaction(txDefinition);

            try {
                FavDetail favDetail = new FavDetail();
                favDetail.setUserId(userId);
                favDetail.setShopId(favId);
                favDetail.setType((byte) type.intValue());
                favDetail.setDelFlg(Boolean.TRUE);
                favDetailService.deleteFavDetail(favDetail);
                txManager.commit(txStatus);
            } catch (Exception e) {
                txManager.rollback(txStatus);
                logger.error("delete favorite failed!, error=" + e.getMessage());
                logger.error("delete favorite failed!, error=" + e.toString());
                e.printStackTrace();
                throw new APIException(JsonStatus.DATA_SAVE_FAILED);
            }
            return jsonObject.withStatus(JsonStatus.SUCCESS);
        }
    }

    @RequestMapping(value = "/api/favorite/{favid}/{type}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getFavorite(@PathVariable long favid, @PathVariable int type, HttpServletRequest hsr) throws APIException {

        long userId = authService.getUserId(hsr);
        FavDetail favCheck = favDetailService.getFavDetailByUserId(userId, favid, type);
        Map<String, Object> m = new HashMap<>();
        if (favCheck != null) {
            logger.warn("FavDetail existed , UserId=" + userId + ", favId=" + favid + "type=" + type);
            m.put("status", 1);
        } else {
            m.put("status", 0);
        }
        jsonObject.setResultList(m);
        return jsonObject;
    }
}
