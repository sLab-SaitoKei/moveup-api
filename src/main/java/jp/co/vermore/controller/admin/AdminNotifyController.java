package jp.co.vermore.controller.admin;


import jp.co.vermore.common.Constant;
import jp.co.vermore.common.DatatablesJsonObject;
import jp.co.vermore.common.mvc.APIException;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.entity.*;
import jp.co.vermore.form.admin.NotifyForm;
import jp.co.vermore.form.admin.NotifyListForm;
import jp.co.vermore.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * AdminNotifyController
 * Created by Richard Jia.
 * <p>
 * DateTime: 2018/03/03 11:13
 * Copyright: sLab, Corp
 */
@Controller
public class AdminNotifyController extends BaseController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AuthService authService;

    @Autowired
    private AdminService adminService;

    @Autowired
    PlatformTransactionManager txManager;


    @RequestMapping(value = "/admin/notify/list/", method = RequestMethod.GET)
    public String notifyAll(Model model,HttpServletRequest request) {
        int errorCode = 0;
        if(!request.getSession().isNew()){
            if(request.getSession().getAttribute("error") != null && request.getSession().getAttribute("error") != ""){
                errorCode = (int)request.getSession().getAttribute("error");
                request.getSession().setAttribute("error",0);
            }
        }
        model.addAttribute("errorCode", errorCode);
        logger.debug("----1----");
        return "admin/notifyList";
    }

    @RequestMapping(value = "/admin/notify/list/", method = RequestMethod.POST)
    @ResponseBody
    public DatatablesJsonObject notifyList(@RequestBody NotifyListForm form) {
        logger.debug("----1----");
        // set order statement
        if (form.getOrder().size() > 0
                && form.getColumns().get(form.getOrder().get(0).getColumn()).getName() != null
                && form.getColumns().get(form.getOrder().get(0).getColumn()).getName().length() > 0) {
            form.setOrderStatement(form.getColumns().get(form.getOrder().get(0).getColumn()).getName() + " " + form.getOrder().get(0).getDir());
            logger.debug("----2----order statement=" + form.getOrderStatement());
        } else {
            form.setOrderStatement("create_datetime");
            logger.debug("----2----order statement=" + form.getOrderStatement());
        }
        logger.debug("----3----");

        // query data
        List<NotificationEntire> dataList = notificationService.getNotifyEntireAllByCondition(form);
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).getUserType() == Constant.NOTIFICATION.USER_TYPE_ADMIN) {
                dataList.get(i).setStrUserType("管理者");
                dataList.get(i).setStrName(dataList.get(i).getShowName());
                dataList.get(i).setUuid(dataList.get(i).getLoginName());
            } else if (dataList.get(i).getUserType() == Constant.NOTIFICATION.USER_TYPE_USER) {
                dataList.get(i).setStrUserType("ユーザー");
                dataList.get(i).setStrName(dataList.get(i).getFullName());
            }

            if (dataList.get(i).getNotifyType() == Constant.NOTIFICATION.NOTIFY_TYPE_INAPP) {
                dataList.get(i).setStrNotifyType("お知らせ");
            } else if (dataList.get(i).getNotifyType() == Constant.NOTIFICATION.NOTIFY_TYPE_PUSH) {
                dataList.get(i).setStrNotifyType("プッシュ通知");
            }

            if (dataList.get(i).getNotifiedDevice() == Constant.NOTIFICATION.NOTIFY_DEVICE_ANDROID) {
                dataList.get(i).setStrNotifiedDevice("Android");
            } else if (dataList.get(i).getNotifiedDevice() == Constant.NOTIFICATION.NOTIFY_DEVICE_IOS) {
                dataList.get(i).setStrNotifiedDevice("iOS");
            } else if (dataList.get(i).getNotifiedDevice() == Constant.NOTIFICATION.NOTIFY_DEVICE_ALL) {
                dataList.get(i).setStrNotifiedDevice("All");
            }

            if (dataList.get(i).getNotifyStatus() == Constant.NOTIFICATION.NOTIFY_STATUS_UNOPENED) {
                dataList.get(i).setStrNotifyStatus("未開封");
            } else if (dataList.get(i).getNotifyStatus() == Constant.NOTIFICATION.NOTIFY_STATUS_OPENED) {
                dataList.get(i).setStrNotifyStatus("開封ずみ");
            }

            if (dataList.get(i).getPushStatus() == Constant.NOTIFICATION.PUSH_STATUS_UNSENT) {
                dataList.get(i).setStrPushStatus("プッシュ未送信");
            } else if (dataList.get(i).getPushStatus() == Constant.NOTIFICATION.PUSH_STATUS_SENT) {
                dataList.get(i).setStrPushStatus("プッシュ送信ずみ");
            }
        }
        int totalCountFiltered = notificationService.getNotifyCountByCondition(form);
        int totalCount = notificationService.getNotifyCount();
        logger.debug("----4----data count=" + dataList.size());
        logger.debug("----5----total filtered=" + totalCountFiltered);
        logger.debug("----6----total count=" + totalCount);
        logger.debug("----7----page=" + form.getDraw());

        // return json data
        DatatablesJsonObject jsonparse = new DatatablesJsonObject();
        jsonparse.setDraw(form.getDraw());
        jsonparse.setRecordsFiltered(totalCountFiltered);
        jsonparse.setRecordsTotal(totalCount);
        jsonparse.setData(dataList);
        logger.debug("----8----");
        return jsonparse;
    }

    @RequestMapping(value = "/admin/notify/regist/", method = RequestMethod.GET)
    public String notifyInsert(Model model) {
        logger.debug("----1----");

        NotifyForm form = new NotifyForm();
        form.setSendType(Constant.NOTIFICATION.SEND_TYPE_ALL);
        form.setUserType(Constant.NOTIFICATION.USER_TYPE_USER);
        form.setPlatform(Constant.NOTIFICATION.NOTIFY_DEVICE_ALL);
        model.addAttribute("notifyForm", form);
        logger.debug("----2----");
        return "admin/notifyRegist";
    }

    @RequestMapping(value = "/admin/notify/regist/", method = RequestMethod.POST)
    public String notifyInsert(@ModelAttribute NotifyForm notifyForm,HttpServletRequest request) throws APIException {
        HttpSession session = request.getSession();
        logger.debug("----1----send type=" + notifyForm.getSendType());
        List<Long> newDataIdList = new ArrayList<>();
        String settingKey = String.valueOf(notifyForm.getItemId());
        if (notifyForm.getSendType() == Constant.NOTIFICATION.SEND_TYPE_ALL) {
            logger.debug("----2----");
            List<Long> idList = new ArrayList<>();
            List<UserSetting> userIdList = new ArrayList<>();
            List<Long> userIdList2 = new ArrayList<>();
            List<Long> idList2 = new ArrayList<>();
            if (notifyForm.getUserType() == Constant.NOTIFICATION.USER_TYPE_ADMIN) {
                idList = adminService.getAdminUserIdList();
            } else {
                idList2 = authService.getUserIdList();
                userIdList = notificationService.findUserIdByKey(settingKey);
                if (userIdList != null) {
                    for (UserSetting us :
                            userIdList) {
                        userIdList2.add(us.getUserId());
                    }
                }
                idList2.removeAll(userIdList2);
                if (idList2 != null) {
                    for (int a = 0; a <= idList2.size() - 1; a++) {
                        idList.add(idList2.get(a));
                    }
                }
            }
            logger.debug("----3----");
            newDataIdList = idList;
            logger.debug("----4----id count=" + newDataIdList.size());
        } else if(notifyForm.getSendType() == Constant.NOTIFICATION.SEND_TYPE_SPECIFY) {
            if (notifyForm.getUserList().length() > 0) {
                String[] idStringList = null;
                if (notifyForm.getUserList().contains(",")) {
                    idStringList = notifyForm.getUserList().split(",");
                } else {
                    idStringList = notifyForm.getUserList().split("，");
                }
                logger.debug("----2----input id count=" + idStringList.length);
                List idList = new ArrayList();
                // filter invalid input id
                if (notifyForm.getUserType() == Constant.NOTIFICATION.USER_TYPE_ADMIN) {
                    for (int i = 0; i < idStringList.length; i++) {
                        if (idStringList[i] != "" && idStringList[i] != null) {

                            idList.add(idStringList[i]);
                        }
                    }
                } else {
                    for (int i = 0; i < idStringList.length; i++) {
                        if (idStringList[i] != "" && idStringList[i] != null) {

                            idList.add(idStringList[i]);

                        }
                    }
                }
                logger.debug("----3----filtered input id count=" + idList.size());
                if (idList.size() > 0) {
                    List<Long> filteredIdList = new ArrayList<>();
                    List<Long> filteredIdList2 = new ArrayList<>();
                    List<UserSetting> userIdList = new ArrayList<>();
                    List<Long> userIdList2 = new ArrayList<>();
                    // filter invalid input id by database
                    if (notifyForm.getUserType() == Constant.NOTIFICATION.USER_TYPE_ADMIN) {
                        filteredIdList = adminService.getAdminUserIdListByIdList(idList);
                    } else {
                        filteredIdList2 = authService.getUserIdListByIdList(idList);
                        userIdList = notificationService.findUserIdByKey(settingKey);
                        if (userIdList != null) {
                            for (UserSetting us :
                                    userIdList) {
                                userIdList2.add(us.getUserId());
                            }
                        }
                        filteredIdList2.removeAll(userIdList2);
                        if (filteredIdList2 != null) {
                            for (int a = 0; a <= filteredIdList2.size() - 1; a++) {
                                filteredIdList.add(filteredIdList2.get(a));
                            }
                        }
                    }
                    newDataIdList = filteredIdList;
                    logger.debug("----4----id count=" + newDataIdList.size());
                }
            }
        }else if(notifyForm.getSendType() == Constant.NOTIFICATION.SEND_TYPE_APP){
            newDataIdList = notificationService.getUserIdForNotifyDevice();
            logger.debug("----5----user id count=" + newDataIdList.size());
        }
        if (newDataIdList != null && newDataIdList.size() > 0) {
            logger.debug("----6----");
            DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
            txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            TransactionStatus txStatus = txManager.getTransaction(txDefinition);

            try {
                // insert notification content data
                long notificationContentId = 0l;
                if(notifyForm.getItemId() != 0){
                     notificationContentId = notificationService.insertNotificationContent(notifyForm.getTitle(), notifyForm.getItemUrl());
                }else {
                     notificationContentId = notificationService.insertNotificationContent(notifyForm.getTitle(), notifyForm.getContent());
                }
                logger.debug("----7----notification content id=" + notificationContentId);

                Date pushDate = null;
                if (notifyForm.getPushTime() != null && notifyForm.getPushTime().length() > 0) {
                    pushDate = DateUtil.stringToDateyyyy_MM_dd_HH_mm_ss(notifyForm.getPushTime());
                }
                logger.debug("----8----user type=" + notifyForm.getUserType());
                logger.debug("----9----notification type=" + notifyForm.getPush());
                logger.debug("----10----platform type=" + notifyForm.getPlatform());
                logger.debug("----11----push time=" + notifyForm.getPushTime());
                // foreach insert notification data
                List<String> userTokens = new ArrayList<String>();
                List<String> adminTokens = new ArrayList<String>();
                for (Long userId : newDataIdList) {
                    notificationService.insertNotification(userId, notifyForm.getUserType(), notifyForm.getItemId(), notificationContentId, notifyForm.getPush(), notifyForm.getPlatform(), pushDate);
                    logger.info("Admin manage notification regist userId=" + userId);
                }
                txManager.commit(txStatus);
                session.setAttribute("error",0);
            } catch (Exception ex) {
                txManager.rollback(txStatus);
                session.setAttribute("error",1);
                logger.error("Admin manage notification regist failed, error=" + ex.getMessage());
                logger.error("Admin manage notification regist, error=" + ex.toString());
                ex.printStackTrace();
            }
        }
        logger.debug("----12----");
        return "redirect:/admin/notify/list/";
    }

    @RequestMapping(value = "/admin/notify/delete/", method = RequestMethod.POST)
    public String notifyDelete(@RequestParam Long id,HttpServletRequest request) {
        HttpSession session = request.getSession();
        logger.debug("----1----");
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try {
            int deleteCount = notificationService.deleteNotification(id);
            txManager.commit(txStatus);
            session.setAttribute("error",0);
        } catch (Exception ex) {
            txManager.rollback(txStatus);
            session.setAttribute("error",1);
            logger.error("Admin manage notification delete failed, error=" + ex.getMessage());
            logger.error("Admin manage notification delete, error=" + ex.toString());
            ex.printStackTrace();
        }
        logger.debug("----2----");
        return "redirect:/admin/notify/list/";
    }

}