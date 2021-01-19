package jp.co.vermore.controller;

import jp.co.vermore.common.JsonObject;
import jp.co.vermore.common.JsonStatus;
import jp.co.vermore.common.mvc.APIException;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.service.AWSService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * CarryController
 * Created by wubin.
 * <p>
 * DateTime: 2018/05/16 18:57
 * Copyright: sLab, Corp
 */
@Controller
public class CarryController extends BaseController {

    @Autowired
    private AWSService awsService;

    @Autowired
    PlatformTransactionManager txManager;

    @RequestMapping(value = "/api/insert/carry/", method = RequestMethod.POST)
    @ResponseBody
    public JsonObject insertCarry(@RequestBody MultiValueMap<String, String> formData) throws IOException {
        awsService.sendCarryMail(formData);
        return jsonObject.withStatus(JsonStatus.SUCCESS);
    }
}
