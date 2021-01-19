package jp.co.vermore.service;

import com.google.firebase.messaging.*;
import com.relayrides.pushy.apns.ApnsClient;
import com.relayrides.pushy.apns.ApnsClientBuilder;
import com.relayrides.pushy.apns.util.ApnsPayloadBuilder;
import com.relayrides.pushy.apns.util.SimpleApnsPushNotification;
import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.*;
import javapns.notification.management.APNPayload;
import jp.co.vermore.common.Constant;
import jp.co.vermore.common.JsonObject;
import jp.co.vermore.common.mvc.BaseService;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.entity.*;
import jp.co.vermore.entity.Notification;
import jp.co.vermore.form.PushForm;
import jp.co.vermore.form.admin.NotifyListForm;
import jp.co.vermore.mapper.*;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLException;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * NotificationService
 * Created by xieyoujun.
 * <p>
 * DateTime: 2018/03/26 12:50
 * Copyright: sLab, Corp
 */

@Service
public class NotificationService extends BaseService{

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private NotificationContentMapper notificationContentMapper;

    @Autowired
    private NotificationDeviceMapper notificationDeviceMapper;

    @Autowired
    private UserSettingMapper UserSettingMapper;

    @Autowired
    private NotifyNow45Mapper notifyNow45Mapper;

    @Value(value = "${hosturl}")
    private String hosturl;

    public void sendAPNS() {
        try {
            // 通知クライアントを作成
            final ApnsClient apnsClient = new ApnsClientBuilder().build();
            apnsClient.registerSigningKey(new File("[APNs Auth Keyが置かれた場所までのファイルパス]"),
                    "[TeamId]",
                    "[APNs Auth Keyの認証コード]",
                    "[通知先アプリのAppID]");
            // ペイロードを作成
            ApnsPayloadBuilder payloadBuilder = new ApnsPayloadBuilder();
            payloadBuilder.setAlertBody("hoge");
            String payload = payloadBuilder.buildWithDefaultMaximumLength();
            // 開発用APNsサーバに接続
            Future<Void> connectFuture = apnsClient.connect(ApnsClient.DEVELOPMENT_APNS_HOST);
            // 接続完了まで同期で待つ
            connectFuture.get();
            // 通知オブジェクトを作成
            SimpleApnsPushNotification pn = new SimpleApnsPushNotification("[デバイスToken]", "[通知先アプリのAppID]", payload);
            // 通知実行
            apnsClient.sendNotification(pn);
            // APNsサーバから切断
            apnsClient.disconnect();
        } catch (SSLException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public List<Notification> getNotifyByUserId(Long userId, int limit, int offset) {
        return notificationMapper.getNotifyByUserId(userId, Constant.NOTIFICATION.USER_TYPE_USER, limit, offset);
    }

    public List<Notification> getNotifyCountByUserId(Long userId) {
        return notificationMapper.getNotifyCountByUserId(userId, Constant.NOTIFICATION.USER_TYPE_USER);
    }

    public List<Notification> getNotifyForMypage(Long userId) {
        return notificationMapper.getNotifyForMypage(userId, Constant.NOTIFICATION.USER_TYPE_USER);
    }

    public List<Notification> getNotifyForNav(Long userId) {
        return notificationMapper.getNotifyForNav(userId, Constant.NOTIFICATION.USER_TYPE_USER, Constant.NOTIFICATION.NOTIFY_STATUS_UNOPENED);
    }

    public NotificationContent getNotificationContent(Long id) {
        return notificationContentMapper.selectByPrimaryKey(id);
    }


    public List<NotificationEntire> getNotifyEntireAllByCondition(NotifyListForm form) {
        List<NotificationEntire> recruitDetail = notificationMapper.getNotifyEntireAllByCondition(form);
        return recruitDetail;
    }

    public int getNotifyCountByCondition(NotifyListForm form) {
        return notificationMapper.getNotifyCountByCondition(form);
    }

    public int getNotifyCount() {
        return notificationMapper.getNotifyCount();
    }

    public long insertNotificationContent(String title, String content) {
        NotificationContent notificationContent = new NotificationContent();
        notificationContent.setTitle(title);
        notificationContent.setContent(content);
        notificationContent.setCreateDatetime(new Date(System.currentTimeMillis()));
        notificationContent.setUpdateDatetime(new Date(System.currentTimeMillis()));
        notificationContent.setDelFlg(Boolean.FALSE);
        notificationContent.setNote(Constant.EMPTY_STRING);
        notificationContentMapper.insertSelective(notificationContent);
        return notificationContent.getId();
    }

    public long insertNotification(long userId, int userType, int itemId, long notifiContentId, Byte notifyType, Byte NotifiedDevice, Date publishStart) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setUserType(userType);
        notification.setItemId(itemId);
        notification.setNotifiContentId(notifiContentId);
        notification.setNotifyType(notifyType);
        notification.setNotifiedDevice(NotifiedDevice);
        notification.setNotifyStatus(Constant.NOTIFICATION.NOTIFY_STATUS_UNOPENED);
        notification.setPushStatus(Constant.NOTIFICATION.PUSH_STATUS_UNSENT);
        notification.setPublishStart(publishStart);
        notification.setPublishEnd(DateUtil.getNextDay(new Date()));
        notification.setCreateDatetime(new Date(System.currentTimeMillis()));
        notification.setUpdateDatetime(new Date(System.currentTimeMillis()));
        notification.setDelFlg(Boolean.FALSE);
        notification.setNote(Constant.EMPTY_STRING);
        notificationMapper.insertSelective(notification);
        return notification.getId();
    }

    public List<NotificationEntire> getNotifyEntireAllByCondition(Long user_id, int user_type, int limit, int offset) {
        return notificationMapper.getNotifyEntireAllByUserIdAndUserType(user_id, user_type, limit, offset);
    }

    public Notification getById(long notificationId) {
        return notificationMapper.selectByPrimaryKey(notificationId);
    }

    public Notification selectById(long notificationId) {
        return notificationMapper.selectById(notificationId);
    }

    public int updateNotificationReadStatus(Long id, Byte notifyStatus) {
        Notification notification = new Notification();
        notification.setId(id);
        notification.setNotifyStatus(notifyStatus);
        notification.setUpdateDatetime(new Date(System.currentTimeMillis()));
        return notificationMapper.updateByPrimaryKeySelective(notification);
    }

    public int updateNotificationPushStatus(Long id, Byte pushStatus) {
        Notification notification = new Notification();
        notification.setId(id);
        notification.setPushStatus(pushStatus);
        notification.setUpdateDatetime(new Date(System.currentTimeMillis()));
        return notificationMapper.updateByPrimaryKeySelective(notification);
    }



    public long insertNotificationDevice(long userId, int userType, String deviceId, byte os, String token) {
        NotificationDevice notificationDevice = new NotificationDevice();
        notificationDevice.setUserId(userId);
        notificationDevice.setUserType(userType);
        notificationDevice.setDeviceId(deviceId);
        notificationDevice.setOs(os);
        notificationDevice.setToken(token);
        notificationDevice.setCreateDatetime(new Date(System.currentTimeMillis()));
        notificationDevice.setUpdateDatetime(new Date(System.currentTimeMillis()));
        notificationDevice.setDelFlg(Boolean.FALSE);
        notificationDevice.setNote(Constant.EMPTY_STRING);
        notificationDeviceMapper.insertSelective(notificationDevice);
        return notificationDevice.getId();
    }

    public NotificationDevice getNotificationDeviceByUserId(long userId,int userType,byte os) {
        return notificationDeviceMapper.getNotificationDeviceByUserId(userId,userType,os);
    }

    public long updateNotificationDeviceByUserId(long userId, int userType, String deviceId, byte os, String token,Boolean delFlg) {
        NotificationDevice notificationDevice = new NotificationDevice();
        notificationDevice.setUserId(userId);
        notificationDevice.setUserType(userType);
        notificationDevice.setDeviceId(deviceId);
        notificationDevice.setOs(os);
        notificationDevice.setToken(token);
        notificationDevice.setUpdateDatetime(new Date(System.currentTimeMillis()));
        notificationDevice.setDelFlg(delFlg);
        notificationDevice.setNote(Constant.EMPTY_STRING);
        notificationDeviceMapper.updateByUserId(notificationDevice);
        return userId;
    }

    public int deleteNotification(Long id) {
        Notification notification = new Notification();
        notification.setId(id);
        notification.setDelFlg(Boolean.TRUE);
        return notificationMapper.deleteById(notification);
    }

    public int getNotifyUnreadCountByCondition(Long id, int userType) {
        return notificationMapper.getNotifyUnreadCountByCondition(id, userType);
    }

    public UserSetting findKeyByUserIdAndTypeAndShopId(Long userId, int type, Long shopId, String settingKey) {
        return UserSettingMapper.findKeyByUserIdAndTypeAndShopId(userId, type, shopId, settingKey);
    }

    public int updateUserSetting(Long userId, String settingKey, String settingValue, long itemId, Integer itemType) {
        UserSettingMapper.updateUserSetting(userId, settingKey, settingValue, itemId, itemType);
        return 0;
    }

    public int updateNotifyItem() {
        return notificationMapper.updateNotifyItem();
    }

    public int insertUserSetting(UserSetting userSetting) {
        return UserSettingMapper.insertUserSetting(userSetting);
    }

    public UserSetting findKeyByUserId(long userId, String settingKey) {
        UserSetting findKeyByUserId = UserSettingMapper.findKeyByUserId(userId, settingKey);
        return findKeyByUserId;
    }


    public List<UserSetting> getUserSettingBySettingKeyAndSettingValue(String settingKey, String settingValue) {
        List<UserSetting> list = UserSettingMapper.getUserSettingBySettingKeyAndSettingValue(settingKey, settingValue);
        return list;
    }

    public UserSetting getUserSettingBySettingKeyAndSettingValueAndByItemId(Long userId, String settingKey, String settingValue, long itemId) {
        UserSetting entity = UserSettingMapper.getUserSettingBySettingKeyAndSettingValueAndByItemId(userId, settingKey, settingValue, itemId);
        return entity;
    }

    public List<UserSetting> findUserIdByKey(String settingKey) {
        List<UserSetting> list = UserSettingMapper.findUserIdBySettingKey(settingKey);
        return list;
    }

    public List<Notification> getNotifyByUserType(Notification notify) {
        List<Notification> list = notificationMapper.getNotifyByUserType(notify);
        return list;
    }

    public List<Notification> getNotifyForIosByPushStatus() {
        List<Notification> list = notificationMapper.getNotifyForIosByPushStatus();
        return list;
    }

    public List<Notification> getNotifyForAndByPushStatus() {
        List<Notification> list = notificationMapper.getNotifyForAndByPushStatus();
        return list;
    }

    public List<NotificationDevice> getNotificationDeviceByUserType(NotificationDevice notifyDevice) {
        List<NotificationDevice> list = notificationDeviceMapper.getNotificationDeviceByUserType(notifyDevice);
        return list;
    }

    public List<Long> getUserIdForNotifyDevice() {
        List<Long> list = notificationDeviceMapper.selectAllUserId();
        return list;
    }


    public int insertNotifyNow45(NotifyNow45 notifyNow45) {
        return notifyNow45Mapper.insertNotifyNow45(notifyNow45);
    }

    public int updateNotifyNow45(NotifyNow45 notifyNow45) {
        return notifyNow45Mapper.updateNotifyNow45(notifyNow45);
    }

    public int deleteNotifyNow45(NotifyNow45 notifyNow45) {
        return notifyNow45Mapper.deleteNotifyNow45(notifyNow45);
    }

    public NotifyNow45 getNotifyNow45ByUserIdAndItemId(long userId, long itemId ,int notifyType) {
        return notifyNow45Mapper.getNotifyNow45ByUserIdAndItemId(userId ,itemId ,notifyType);
    }

    public int notifyPush(HttpServletRequest request, PushForm pushForm) {
        int result = 0 ;
        int badge = 0;
        String sound = "default";

        if (pushForm.getUserType() == Constant.NOTIFICATION.USER_TYPE_ADMIN) {
            List<Notification> adminNotifyForBadge = notificationMapper.getBadgeNumForAdmin(pushForm.getUserId());
            if (adminNotifyForBadge.size() > 0) {
                badge = adminNotifyForBadge.size();
            }
        } else if (pushForm.getUserType() == Constant.NOTIFICATION.USER_TYPE_USER) {
            int badge1 = 0;
            int badge2 = 0;
            List<Notification> userNotifyForBadge = notificationMapper.getBadgeNumForUser(pushForm.getUserId());
            List<NotifyNow45> userNotify54ForBadge = notifyNow45Mapper.getBadgeNumForUser(pushForm.getUserId());
            if (userNotifyForBadge.size() > 0) {
                badge1 = userNotifyForBadge.size();
            }
            if(userNotify54ForBadge.size()>0){
                badge2 = userNotify54ForBadge.size();
            }
            badge = badge1 + badge2;
        }
        String path = request.getSession().getServletContext().getRealPath("/");
        String certificatePath = path+"static/file/MoveUp_Pro.p12";
        String certificatePassword = "123";//
        boolean sendCount = true;

        try {
            PushNotificationBigPayload  payLoad = new PushNotificationBigPayload();
            payLoad.addAlert(pushForm.getTitle()); // push content
            payLoad.addBadge(badge); // iphone red icon number
            payLoad.addCustomDictionary("pushType",pushForm.getType());
            payLoad.addCustomDictionary("contentId",pushForm.getContentId());
            payLoad.addCustomDictionary("itemId",pushForm.getItemId());
            payLoad.addCustomDictionary("date",pushForm.getDate());
            payLoad.addCustomDictionary("title",pushForm.getTitle());
            payLoad.addCustomDictionary("shopUuid",pushForm.getUuid());
            payLoad.addCustomDictionary("shopType",pushForm.getShopType());
            if(pushForm.getItemId() !=0){
                payLoad.addCustomDictionary("notifyContent",pushForm.getContent());
            }

            if (!StringUtils.isBlank(sound)) {
                payLoad.addSound(sound);
            }
            PushNotificationManager pushManager = new PushNotificationManager();
            //true：pro false：dev
            pushManager.initializeConnection(new AppleNotificationServerBasicImpl(certificatePath, certificatePassword, true));
            List<PushedNotification> notifications = new ArrayList<PushedNotification>();
            // push message
            if (sendCount) {

                Device device = new BasicDevice();
                device.setToken(pushForm.getDeviceToken());
                PushedNotification notification = pushManager.sendNotification(device, payLoad, true);
                notifications.add(notification);
            }
//            else {
//
//                List<Device> device = new ArrayList<Device>();
//                for (String token : tokens) {
//                    device.add(new BasicDevice(token));
//                }
//                notifications = pushManager.sendNotifications(payLoad, device);
//            }
            pushManager.stopConnection();
        } catch (Exception e) {
            logger.error("----1----ios push result=" + result);
            result = 1;
            e.printStackTrace();
        }
        return result;
    }

    public int pushFCMNotification(PushForm pushForm) {

        int result = 0;
        // This registration token comes from the client FCM SDKs.
        String registrationToken = "fKerKSNXsHA:APA91bF8Lw-NKNkuc372WitV48LDq8mPjND8rbyKI7jQoM9J8EUK4hUjU1cGQ3nddF2X1-IqUw3MKQWD1i-0dKTb7ojEJqZB31Hc0lMb1qg-RcgFaQ4T_eMAjfmOQ2opeXY7B4GgFpf_";
        String content = "";
        if(pushForm.getItemId() !=0){
            content =   pushForm.getContent();
        }
        // See documentation on defining a message payload.
        Message message = Message.builder()
                .setAndroidConfig(AndroidConfig.builder()
                        .setTtl(7*24*3600 * 1000) // 7 day in milliseconds
                        .setPriority(AndroidConfig.Priority.NORMAL)
                        .setNotification(AndroidNotification.builder()
                                .setTitle(pushForm.getTitle())
                                .setBody("")
                                .setIcon("")
                                .setSound("default")
                                .setColor("#f45342")
                                .build())
                        .build())
                .setToken(pushForm.getDeviceToken())
                .putData("pushType", String.valueOf(pushForm.getType()))
                .putData("shopType", String.valueOf(pushForm.getShopType()))
                .putData("shopUuid",pushForm.getUuid())
                .putData("contentId", String.valueOf(pushForm.getContentId()))
                .putData("itemId", String.valueOf(pushForm.getItemId()))
                .putData("notifyContent", String.valueOf(content))
                .build();

        // Send a message to the device corresponding to the provided
        String response = null;
        try {
            response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            logger.error("----1----android push result=" + result);
            e.printStackTrace();
            result= 1;
        }
        // Response is a message ID string.
        return result;
    }
}
