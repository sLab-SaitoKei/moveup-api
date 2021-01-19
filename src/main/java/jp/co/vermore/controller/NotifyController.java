package jp.co.vermore.controller;


import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;
import jp.co.vermore.common.Constant;
import jp.co.vermore.common.JsonObject;
import jp.co.vermore.common.JsonStatus;
import jp.co.vermore.common.mvc.APIException;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.entity.*;
import jp.co.vermore.form.NotificationForm;
import jp.co.vermore.jsonparse.NotificationContentJsonParse;
import jp.co.vermore.jsonparse.NotificationJsonParse;
import jp.co.vermore.jsonparse.UserSettingJsonParse;
import jp.co.vermore.service.*;
import jp.co.vermore.validation.NotificationRegisterParams;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

/**
 * NotifyController
 * Created by Richard Jia.
 * <p>
 * DateTime: 2018/04/19 13:08
 * Copyright: sLab, Corp
 */

@Controller
public class NotifyController extends BaseController {

    @Autowired
    private AuthService authService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopNowgoService shopNowgoService;

    @Autowired
    private ShopCouponService shopCouponService;

    @Autowired
    private NotificationService notificationService;


    //Add notification device info
    @RequestMapping(value = "/api/notification/register/", method = RequestMethod.POST)
    @ResponseBody
    public JsonObject registerNotificationDevice(@Valid NotificationRegisterParams params, HttpServletRequest hsr) throws APIException {
//        BindingResult result,
//        if (result.hasErrors()) {
//            logger.warn("notification register error.");
//            throw new APIException(JsonStatus.PARAMETER_ERROR);
//        }
        long userId = authService.getUserId(hsr);
        logger.debug("----1----userId=" + userId);
        NotificationDevice nfdForIos = notificationService.getNotificationDeviceByUserId(userId, Constant.NOTIFICATION.USER_TYPE_USER, Constant.NOTIFICATION.NOTIFY_DEVICE_IOS);
        NotificationDevice nfdForAnd = notificationService.getNotificationDeviceByUserId(userId, Constant.NOTIFICATION.USER_TYPE_USER, Constant.NOTIFICATION.NOTIFY_DEVICE_ANDROID);
        if (nfdForIos == null && nfdForAnd == null) {
            long newId = notificationService.insertNotificationDevice(userId, Constant.NOTIFICATION.USER_TYPE_USER, params.getDeviceId(), params.getOs(), params.getToken());
            logger.debug("----2----NotificationDevice id" + newId);
        } else if (nfdForIos != null && nfdForAnd == null) {
            logger.debug("----3----ios NotificationDevice id=" + nfdForIos.getId());
            long newId = notificationService.updateNotificationDeviceByUserId(userId, Constant.NOTIFICATION.USER_TYPE_USER, params.getDeviceId(), params.getOs(), params.getToken(),false);
            logger.debug("----4----userId=" + newId);
        } else if (nfdForIos == null && nfdForAnd != null) {
            logger.debug("----5----android NotificationDevice id=" + nfdForAnd.getId());
            long newId = notificationService.updateNotificationDeviceByUserId(userId, Constant.NOTIFICATION.USER_TYPE_USER, params.getDeviceId(), params.getOs(), params.getToken(),false);
            logger.debug("----6----userId=" + newId);
        }
        jsonObject.setResultList(null);
        logger.debug("----7----");
        return jsonObject.withStatus(JsonStatus.SUCCESS);
    }

    //Get notification info
    @RequestMapping(value = "/api/notification/{limit}/{offset}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getNotify(HttpServletRequest hsr, @PathVariable int limit, @PathVariable int offset) throws APIException {
        long userId = authService.getUserId(hsr);
        logger.debug("----1----userId=" + userId);
        List<Notification> notifyList = notificationService.getNotifyByUserId(userId, limit, offset);
        List<Notification> countList = notificationService.getNotifyCountByUserId(userId);
        logger.debug("----2----countList=" + countList.size());
        List<NotificationJsonParse> list = new ArrayList<NotificationJsonParse>();
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
                    }
                    if (sn != null && shop != null) {
                        njp.setTitle(shop.getName());
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
                        logger.debug("----4----ShopCoupon id=" + sc.getId());
                    }
                    if (sc != null && shop != null) {
                        njp.setTitle(shop.getName());
                        njp.setContent(sc.getDesc());
                        Date nowForTime = sc.getUpdateDatetime();
                        String now4time = DateUtil.timeDifference(nowForTime);
                        njp.setTime(now4time);
                        njp.setPicUrl(sc.getPicUrl1());
                    }
                }
                list.add(njp);
                logger.debug("----5----notify list=" + list.size());
            }
        }

//        String settingShopKey = Constant.USER_SETTING.SHOP;
//        String settingEventKey = Constant.USER_SETTING.EVENT;
//        String settingTvKey = Constant.USER_SETTING.TV;
//        String settingRecruitKey = Constant.USER_SETTING.RECRUIT;
//        String settingGoodsKey = Constant.USER_SETTING.GOODS;
//        String settingFreepaperKey = Constant.USER_SETTING.FREEPAPER;
//        String settingNow4Key = Constant.USER_SETTING.NOW4;
//        String settingNow5Key = Constant.USER_SETTING.NOW5;
//
//        UserSetting uSShop = notificationService.findKeyByUserId(userId, settingShopKey);
//        UserSetting uSEvent = notificationService.findKeyByUserId(userId, settingEventKey);
//        UserSetting uSTv = notificationService.findKeyByUserId(userId, settingTvKey);
//        UserSetting uSRecruit = notificationService.findKeyByUserId(userId, settingRecruitKey);
//        UserSetting uSGoods = notificationService.findKeyByUserId(userId, settingGoodsKey);
//        UserSetting uSFreepaper = notificationService.findKeyByUserId(userId, settingFreepaperKey);
//        UserSetting uSNow4 = notificationService.findKeyByUserId(userId, settingNow4Key);
//        UserSetting uSNow5 = notificationService.findKeyByUserId(userId, settingNow5Key);
//
//        UserSettingJsonParse ncjp = new UserSettingJsonParse();
//
//        if (uSShop != null) {
//            ncjp.setSettingShopKey(uSShop.getSettingKey());
//            ncjp.setSettingShopValue(uSShop.getSettingValue());
//        }
//        if (uSEvent != null) {
//            ncjp.setSettingEventKey(uSEvent.getSettingKey());
//            ncjp.setSettingEventValue(uSEvent.getSettingValue());
//        }
//        if (uSTv != null) {
//            ncjp.setSettingTvKey(uSTv.getSettingKey());
//            ncjp.setSettingTvValue(uSTv.getSettingValue());
//        }
//        if (uSRecruit != null) {
//            ncjp.setSettingRecruitKey(uSRecruit.getSettingKey());
//            ncjp.setSettingRecruitValue(uSRecruit.getSettingValue());
//        }
//        if (uSGoods != null) {
//            ncjp.setSettingGoodsKey(uSGoods.getSettingKey());
//            ncjp.setSettingGoodsValue(uSGoods.getSettingValue());
//        }
//        if (uSFreepaper != null) {
//            ncjp.setSettingFreepapaerKey(uSFreepaper.getSettingKey());
//            ncjp.setSettingFreepapaerValue(uSFreepaper.getSettingValue());
//        }
//        if (uSNow4 != null) {
//            ncjp.setSettingNow4Key(uSNow4.getSettingKey());
//            ncjp.setSettingNow4Value(uSNow4.getSettingValue());
//        }
//        if (uSNow5 != null) {
//            ncjp.setSettingNow5Key(uSNow5.getSettingKey());
//            ncjp.setSettingNow5Value(uSNow5.getSettingValue());
//        }

        Map<String, Object> map = new HashMap<>();
        map.put("notifyList", list);
        map.put("count", countList.size());
        jsonObject.setResultList(map);
        return jsonObject;
    }

    //Change the notification to read
    @RequestMapping(value = "/api/notification/read/", method = RequestMethod.POST)
    @ResponseBody
    public JsonObject readNotification(@ModelAttribute NotificationForm form, HttpServletRequest hsr) throws APIException {
        notificationService.updateNotificationReadStatus(form.getId(), Constant.NOTIFICATION.NOTIFY_STATUS_OPENED);
        jsonObject.setResultList(null);
        return jsonObject.withStatus(JsonStatus.SUCCESS);
    }

    //Get notification info for nav
    @RequestMapping(value = "/api/notification/nav/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getNotify(HttpServletRequest hsr) {
        long userId = 0;
        try {
            userId = authService.getUserId(hsr);
        } catch (APIException ex) {
        }
        List<Notification> notifyList = notificationService.getNotifyForNav(userId);
        Map<String, Object> map = new HashMap<>();
        map.put("count", notifyList.size());
        jsonObject.setResultList(map);
        return jsonObject;
    }

    //Get notification info for nav
    @RequestMapping(value = "/api/notification/content/", method = RequestMethod.POST)
    @ResponseBody
    public JsonObject getNotifyContent(@ModelAttribute NotificationForm form) throws APIException {

        NotificationContent nc = notificationService.getNotificationContent(form.getId());
        NotificationContentJsonParse ncjp = new NotificationContentJsonParse();
        ncjp.setTitle(nc.getTitle());
        ncjp.setDate(DateUtil.dateToStringyyyymmdd(nc.getCreateDatetime()));
        ncjp.setContent(nc.getContent());
        jsonObject.setResultList(ncjp);
        return jsonObject;
    }

    //Get notification info for nav
    @RequestMapping(value = "/api/user/settings/{type}/{uuid}/{settingKey}/{settingValue}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getUserSetting(HttpServletRequest hsr, @PathVariable Integer type, @PathVariable String uuid, @PathVariable String settingKey, @PathVariable String settingValue) throws APIException {
        long userId = authService.getUserId(hsr);

        UserSettingJsonParse ncjp = new UserSettingJsonParse();
//        String settingShopKey = Constant.USER_SETTING.SHOP;
//        String settingEventKey = Constant.USER_SETTING.EVENT;
//        String settingTvKey = Constant.USER_SETTING.TV;
//        String settingRecruitKey = Constant.USER_SETTING.RECRUIT;
//        String settingGoodsKey = Constant.USER_SETTING.GOODS;
//        String settingFreepaperKey = Constant.USER_SETTING.FREEPAPER;
//        String settingNow4Key = Constant.USER_SETTING.NOW4;
//        String settingNow5Key = Constant.USER_SETTING.NOW5;
//
//        UserSetting uSShop = notificationService.findKeyByUserId(userId, settingShopKey);
//        UserSetting uSEvent = notificationService.findKeyByUserId(userId, settingEventKey);
//        UserSetting uSTv = notificationService.findKeyByUserId(userId, settingTvKey);
//        UserSetting uSRecruit = notificationService.findKeyByUserId(userId, settingRecruitKey);
//        UserSetting uSGoods = notificationService.findKeyByUserId(userId, settingGoodsKey);
//        UserSetting uSFreepaper = notificationService.findKeyByUserId(userId, settingFreepaperKey);
//        UserSetting uSNow4 = notificationService.findKeyByUserId(userId, settingNow4Key);
//        UserSetting uSNow5 = notificationService.findKeyByUserId(userId, settingNow5Key);

        if (type == Constant.FAV_TYPE.SHOP) {
            Shop shop = shopService.getShopByUuid(uuid);
            UserSetting userSetting = notificationService.findKeyByUserIdAndTypeAndShopId(userId, type, shop.getId(), settingKey);
            if (userSetting == null) {
                UserSetting userSetting2 = new UserSetting();
                userSetting2.setUserId(userId);
                userSetting2.setItemType(type);
                if (type == Constant.FAV_TYPE.SHOP) {
                    if (shop != null) {
                        userSetting2.setItemId(shop.getId());
                    } else {
                        userSetting2.setItemId(0L);
                    }
                }
                userSetting2.setSettingKey(settingKey);
                userSetting2.setSettingValue(settingValue);
                notificationService.insertUserSetting(userSetting2);
            } else {
                if (type == Constant.FAV_TYPE.SHOP) {
                    if (shop != null) {
                        notificationService.updateUserSetting(userId, settingKey, settingValue, shop.getId(), type);
                    }
                } else {
                    notificationService.updateUserSetting(userId, settingKey, settingValue, 0L, 0);
                }
            }
        } else {
            UserSetting userSetting = notificationService.findKeyByUserId(userId, settingKey);
            if (userSetting == null) {
                UserSetting userSetting2 = new UserSetting();
                userSetting2.setUserId(userId);
                userSetting2.setItemType(type);
                if (type == Constant.FAV_TYPE.SHOP) {
                    Shop shop = shopService.getShopByUuid(uuid);
                    if (shop != null) {
                        userSetting2.setItemId(shop.getId());
                    } else {
                        userSetting2.setItemId(0L);
                    }
                }
                userSetting2.setSettingKey(settingKey);
                userSetting2.setSettingValue(settingValue);
                notificationService.insertUserSetting(userSetting2);
            } else {
                if (type == Constant.FAV_TYPE.SHOP) {
                    Shop shop = shopService.getShopByUuid(uuid);
                    if (shop != null) {
                        notificationService.updateUserSetting(userId, settingKey, settingValue, shop.getId(), type);
                    }
                } else {
                    notificationService.updateUserSetting(userId, settingKey, settingValue, 0L, 0);
                }
            }
        }

        ncjp.setSettingKey(settingKey);
        ncjp.setSettingValue(settingValue);
        ncjp.setSettingKey(settingKey);
        ncjp.setSettingValue(settingValue);
        jsonObject.setResultList(ncjp);
        return jsonObject;
    }


    //Get notification info for nav
    @RequestMapping(value = "/api/user/notify/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getUserSetting1(HttpServletRequest hsr) throws APIException {
        long userId = authService.getUserId(hsr);

        String settingShopKey = Constant.USER_SETTING.SHOP;
        String settingEventKey = Constant.USER_SETTING.EVENT;
        String settingTvKey = Constant.USER_SETTING.TV;
        String settingRecruitKey = Constant.USER_SETTING.RECRUIT;
        String settingGoodsKey = Constant.USER_SETTING.GOODS;
        String settingFreepaperKey = Constant.USER_SETTING.FREEPAPER;
        String settingNow4Key = Constant.USER_SETTING.NOW4;
        String settingNow5Key = Constant.USER_SETTING.NOW5;

        UserSetting uSShop = notificationService.findKeyByUserId(userId, settingShopKey);
        UserSetting uSEvent = notificationService.findKeyByUserId(userId, settingEventKey);
        UserSetting uSTv = notificationService.findKeyByUserId(userId, settingTvKey);
        UserSetting uSRecruit = notificationService.findKeyByUserId(userId, settingRecruitKey);
        UserSetting uSGoods = notificationService.findKeyByUserId(userId, settingGoodsKey);
        UserSetting uSFreepaper = notificationService.findKeyByUserId(userId, settingFreepaperKey);
        UserSetting uSNow4 = notificationService.findKeyByUserId(userId, settingNow4Key);
        UserSetting uSNow5 = notificationService.findKeyByUserId(userId, settingNow5Key);

        UserSettingJsonParse ncjp = new UserSettingJsonParse();
        ncjp.setSettingEventKey(settingEventKey);
        ncjp.setSettingTvKey(settingTvKey);
        ncjp.setSettingShopKey(settingShopKey);
        ncjp.setSettingGoodsKey(settingGoodsKey);
        ncjp.setSettingRecruitKey(settingRecruitKey);
        ncjp.setSettingFreepapaerKey(settingFreepaperKey);
        ncjp.setSettingNow4Key(settingNow4Key);
        ncjp.setSettingNow5Key(settingNow5Key);

        if (uSShop != null) {
            ncjp.setSettingShopValue(uSShop.getSettingValue());
        }
        if (uSEvent != null) {
            ncjp.setSettingEventValue(uSEvent.getSettingValue());
        }
        if (uSTv != null) {
            ncjp.setSettingTvValue(uSTv.getSettingValue());
        }
        if (uSRecruit != null) {
            ncjp.setSettingRecruitValue(uSRecruit.getSettingValue());
        }
        if (uSGoods != null) {
            ncjp.setSettingGoodsValue(uSGoods.getSettingValue());
        }
        if (uSFreepaper != null) {
            ncjp.setSettingFreepapaerValue(uSFreepaper.getSettingValue());
        }
        if (uSNow4 != null) {
            ncjp.setSettingNow4Value(uSNow4.getSettingValue());
        }
        if (uSNow5 != null) {
            ncjp.setSettingNow5Value(uSNow5.getSettingValue());
        }
        jsonObject.setResultList(ncjp);
        return jsonObject;
    }

    //test ios push
    @RequestMapping(value = "/api/notify/ios/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject sendNotificationToios() throws Exception {

        String deviceToken = "2d52517cc2f3d154f4fdc4f05604e83da4a6edd51203370d40efab05d7c1be14";
        String alert = "";//push content
        int badge = 3;
        String sound = "default";

        List<String> tokens = new ArrayList<String>();
        tokens.add(deviceToken);
        String certificatePath = "F:\\moveup\\admin\\p12\\MoveUp_Dev.p12";
        String certificatePassword = "123";//
        boolean sendCount = true;

        try {

            PushNotificationPayload payLoad = new PushNotificationPayload();
            payLoad.addAlert(alert); // push content
            payLoad.addBadge(badge); // iphone red icon number

            if (!StringUtils.isBlank(sound)) {
                payLoad.addSound(sound);
            }
            PushNotificationManager pushManager = new PushNotificationManager();
            //true：pro false：dev
            pushManager.initializeConnection(new AppleNotificationServerBasicImpl(certificatePath, certificatePassword, false));
            List<PushedNotification> notifications = new ArrayList<PushedNotification>();
            // push message
            if (sendCount) {

                Device device = new BasicDevice();
                device.setToken(tokens.get(0));
                PushedNotification notification = pushManager.sendNotification(device, payLoad, true);
                notifications.add(notification);
            } else {

                List<Device> device = new ArrayList<Device>();
                for (String token : tokens) {
                    device.add(new BasicDevice(token));
                }
                notifications = pushManager.sendNotifications(payLoad, device);
            }
            pushManager.stopConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        jsonObject.setResultList("success");
        return jsonObject;
    }

}
