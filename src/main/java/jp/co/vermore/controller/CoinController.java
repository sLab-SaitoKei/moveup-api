package jp.co.vermore.controller;

import jp.co.vermore.common.JsonObject;
import jp.co.vermore.common.JsonStatus;
import jp.co.vermore.common.mvc.APIException;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.entity.CoinHistory;
import jp.co.vermore.entity.CoinMaster;
import jp.co.vermore.entity.News;
import jp.co.vermore.entity.Team2020;
import jp.co.vermore.form.CoinRegistForm;
import jp.co.vermore.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CoinController
 * Created by wangnannan.
 * <p>
 * DateTime: 2018/06/01 11:52
 * Copyright: sLab, Corp
 */
@Controller
public class CoinController extends BaseController {

    @Autowired
    private CoinHistoryService coinHistoryService;

    @Autowired
    private CoinService coinService;

    @Autowired
    private CoinMasterService coinMasterService;

    @Autowired
    private AuthService authService;


    @RequestMapping(value = "/api/coin/list/{limit}/{offset}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getCionList(HttpServletRequest hsr, @PathVariable int limit, @PathVariable int offset) throws APIException {
        long userId = authService.getUserId(hsr);
        List<CoinHistory> coinList = coinHistoryService.getCoinList(userId,limit,offset);
        List<CoinHistory> count = coinHistoryService.getCoinListCount(userId);
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        if(coinList!=null){
            for(CoinHistory list : coinList){
                Map<String, Object> map = new HashMap<>();
                map.put("coinChanged",list.getCoinChanged());
                map.put("coinType",list.getCoinType());
                map.put("create_datetime", DateUtil.dateToStringyyyymmdd(list.getCreateDatetime()));
                CoinMaster CoinMaster = coinMasterService.getCoinMasterList(list.getCoinId(), list.getCoinType());
                map.put("desc",CoinMaster.getDesc());
                mapList.add(map);
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("cionList", mapList);
        map.put("cionListCount", count.size());
        jsonObject.setResultList(map);
        jsonObject.setStatus(JsonStatus.SUCCESS);
        return jsonObject;
    }

    @RequestMapping(value = "/api/coin/ad/", method = RequestMethod.POST)
    @ResponseBody
    public JsonObject insertUserCoin(@ModelAttribute CoinRegistForm form, HttpServletRequest hsr) throws APIException {
        long userId = authService.getUserId(hsr);
        List<CoinHistory> todayCoin = coinService.getTodayCoinListByUserId(userId,form.getItemId());
        if(todayCoin.size()<1){
            String serialNumber = coinService.adCoin(userId,form.getItemId(),form.getCoin());
            long coin = coinService.getAmount(userId);
            logger.debug("add 1 coin from Freepaper AD serialNumber="+ serialNumber + "userId="+ userId);
            jsonObject.setStatus(JsonStatus.SUCCESS);
        }else{
            long coin = coinService.getAmount(userId);
            List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
            if(todayCoin!=null){
                for(CoinHistory list : todayCoin){
                    Map<String, Object> map = new HashMap<>();
                    map.put("coinChanged",list.getCoinChanged());
                    map.put("coinType",list.getCoinType());
                    map.put("create_datetime", DateUtil.dateToStringyyyymmdd(list.getCreateDatetime()));
                    CoinMaster CoinMaster = coinMasterService.getCoinMasterList(list.getCoinId(), list.getCoinType());
                    map.put("desc",CoinMaster.getDesc());
                    mapList.add(map);
                }
            }
            Map<String, Object> map = new HashMap<>();
            map.put("cionList", mapList);
            jsonObject.setResultList(map);
            jsonObject.setStatus(JsonStatus.COIN_EXCESS);
            logger.debug("today coin added from Freepaper AD userId=" + userId);
        }
        return jsonObject;
    }

    @RequestMapping(value = "/api/coin/ad/tv/", method = RequestMethod.POST)
    @ResponseBody
    public JsonObject tvCoin(@ModelAttribute CoinRegistForm form, HttpServletRequest hsr) throws APIException {
        long userId = authService.getUserId(hsr);
        List<CoinHistory> todayCoin = coinService.getTodayCoinListByUserId(userId,form.getItemId());
        if(todayCoin.size()<1){
            String serialNumber = coinService.tvCoin(userId,form.getItemId(),form.getCoin());
            logger.debug("add 1 coin from TV AD serialNumber="+ serialNumber + "userId="+ userId);
            jsonObject.setStatus(JsonStatus.SUCCESS);
        }else{
            List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
            if(todayCoin!=null){
                for(CoinHistory list : todayCoin){
                    Map<String, Object> map = new HashMap<>();
                    map.put("coinChanged",list.getCoinChanged());
                    map.put("coinType",list.getCoinType());
                    map.put("create_datetime", DateUtil.dateToStringyyyymmdd(list.getCreateDatetime()));
                    CoinMaster CoinMaster = coinMasterService.getCoinMasterList(list.getCoinId(), list.getCoinType());
                    map.put("desc",CoinMaster.getDesc());
                    mapList.add(map);
                }
            }
            Map<String, Object> map = new HashMap<>();
            map.put("cionList", mapList);
            jsonObject.setResultList(map);
            logger.debug("today coin added from TV AD userId=" + userId);
            jsonObject.setStatus(JsonStatus.COIN_EXCESS);
        }
        return jsonObject;
    }

}
