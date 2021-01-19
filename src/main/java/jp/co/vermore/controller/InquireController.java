package jp.co.vermore.controller;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.JsonObject;
import jp.co.vermore.common.JsonStatus;
import jp.co.vermore.common.mvc.APIException;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.entity.Inquire;
import jp.co.vermore.service.AWSService;
import jp.co.vermore.service.AuthService;
import jp.co.vermore.service.InquireService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * InquireController
 * Created by wubin.
 * <p>
 * DateTime: 2018/04/24 13:45
 * Copyright: sLab, Corp
 */
@Controller
public class InquireController extends BaseController {

    @Autowired
    InquireService inquireService;

    @Autowired
    private AWSService awsService;

    @Autowired
    PlatformTransactionManager txManager;

    @RequestMapping(value = "/api/user/inquire/", method = RequestMethod.POST)
    @ResponseBody
    public JsonObject userInquire(@RequestBody MultiValueMap<String, String> formData) throws APIException, IOException {

//        Long id = authService.getUserId(hsr);
        String mail = formData.getFirst("mail");
        String contents = formData.getFirst("contents");

        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try {
            Inquire inquire = new Inquire();
            inquire.setContents(contents);
            inquire.setMail(mail);
            inquire.setType((byte) Constant.INQUIRE_TYPE.USER);
            inquire.setStatus((byte)Constant.INQUIRE_STATUS.INQUIRE_STATUS_UNFINISH);
            inquireService.addInquire(inquire);
            txManager.commit(txStatus);
        } catch (Exception e) {
            txManager.rollback(txStatus);
            logger.error("Insert inquire failed!, error=" + e.getMessage());
            logger.error("Insert inquire failed!, error=" + e.toString());
            e.printStackTrace();
            throw new APIException(JsonStatus.DATA_SAVE_FAILED);
        }

        awsService.sendInquireMail(mail,contents);
        logger.info("Send confirm mail=" + mail);
        logger.info("Regist mail send to mail=" + mail);
        return jsonObject.withStatus(JsonStatus.SUCCESS);
    }
}
