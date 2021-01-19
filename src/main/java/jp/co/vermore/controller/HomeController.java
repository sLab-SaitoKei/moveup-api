package jp.co.vermore.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.vermore.common.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.vermore.common.JsonObject;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.entity.SystemConf;
import jp.co.vermore.jsonparse.SystemConfJsonParse;
import jp.co.vermore.service.SystemConfService;

/**
 * Root path
 */

@Controller
public class HomeController extends BaseController {

    @Autowired
    private SystemConfService systemConfService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String home() {
        return "MoveUp API";
    }

    @RequestMapping(value = "/api/sys/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject systemConf() {

        List<SystemConf> systemConfList = systemConfService.getSystemConfAll();
        SystemConfJsonParse scjp = new SystemConfJsonParse();

        List<SystemConfJsonParse> scjpList = new ArrayList<>();

        for (SystemConf sc : systemConfList) {
            scjp.setSetting(sc.getSetting().toString());
            scjp.setValue(sc.getValue().toString());
            scjpList.add(scjp);
        }
        jsonObject.setResultList(scjpList);
        return jsonObject;
    }

    @RequestMapping(value = "/api/ios/version/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject versionCodeForIos() {

        SystemConf adminVersionForIos = systemConfService.getSystemConfByKey(Constant.SYS_CONF.SYSTEM_STATUS_IOS_ADMIN_VERSION);
        SystemConf mainVersionForIos = systemConfService.getSystemConfByKey(Constant.SYS_CONF.SYSTEM_STATUS_IOS_MAIN_VERSION);
        SystemConf nowVersionForIos = systemConfService.getSystemConfByKey(Constant.SYS_CONF.SYSTEM_STATUS_IOS_NOW_VERSION);
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<>();
        map.put("adminUrl", " https://itunes.apple.com/us/app/move-up管理/id1406005125?l=ja&ls=1&mt=8");
        map.put("adminVersion", adminVersionForIos.getValue());
        map.put("mainUrl", "https://itunes.apple.com/us/app/move-up/id1400816058?l=ja&ls=1&mt=8");
        map.put("mainVersion", mainVersionForIos.getValue());
        map.put("nowUrl", "https://itunes.apple.com/us/app/now5-4/id1436837195?l=ja&ls=1&mt=8");
        map.put("nowVersion", nowVersionForIos.getValue());

        mapList.add(map);
        jsonObject.setResultList(mapList);
        return jsonObject;
    }

    @RequestMapping(value = "/api/android/version/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject versionCodeForAnd() {

        SystemConf adminVersionForAnd = systemConfService.getSystemConfByKey(Constant.SYS_CONF.SYSTEM_STATUS_ANDROID_ADMIN_VERSION);
        SystemConf mainVersionForAnd = systemConfService.getSystemConfByKey(Constant.SYS_CONF.SYSTEM_STATUS_ANDROID_MAIN_VERSION);
        SystemConf nowVersionForAnd = systemConfService.getSystemConfByKey(Constant.SYS_CONF.SYSTEM_STATUS_ANDROID_NOW_VERSION);
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<>();
        map.put("adminUrl", "https://play.google.com/store/apps/details?id=vermore.co.jp.japanmoveupwest.admin");
        map.put("adminVersion", adminVersionForAnd.getValue());
        map.put("mainUrl", "https://play.google.com/store/apps/details?id=co.jp.japanmoveupwest");
        map.put("mainVersion", mainVersionForAnd.getValue());
        map.put("nowUrl", "https://play.google.com/store/apps/details?id=co.jp.japanmoveupwest.now");
        map.put("nowVersion", nowVersionForAnd.getValue());

        mapList.add(map);
        jsonObject.setResultList(mapList);
        return jsonObject;
    }
}
