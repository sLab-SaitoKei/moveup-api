package jp.co.vermore.common.mvc;

import jp.co.vermore.common.JsonObject;
import jp.co.vermore.common.JsonStatus;
import jp.co.vermore.controller.HomeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * BaseController
 * Created by xieyoujun.
 * <p>
 * DateTime: 2018/03/07 2:05
 * Copyright: sLab, Corp
 */

public class BaseController {

    protected JsonObject jsonObject = new JsonObject(JsonStatus.SUCCESS);

    protected static final Logger logger = LoggerFactory.getLogger(HomeController.class);

}
