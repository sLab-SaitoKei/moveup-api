package jp.co.vermore.controller;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.JsonObject;
import jp.co.vermore.common.mvc.APIException;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.common.util.StringUtil;
import jp.co.vermore.entity.*;
import jp.co.vermore.jsonparse.FavoriteJsonParse;
import jp.co.vermore.jsonparse.ShopSearchJsonParse;
import jp.co.vermore.jsonparse.shopdetailjsonparse.*;
import jp.co.vermore.jsonparse.shopjsonparse.*;
import jp.co.vermore.service.*;
import jp.co.vermore.validation.ShopListParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ShopController
 * Created by wubin.
 * <p>
 * DateTime: 2018/03/12 12:09
 * Copyright: sLab, Corp
 */
@Controller
public class ShopController extends BaseController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopNowgoService shopNowgoService;

    @Autowired
    private ShopDetailService shopDetailService;

    @Autowired
    private TagDetailService tagDetailService;

    @Autowired
    private ShopTagService shopTagService;

    @Autowired
    private ShopCouponService shopCouponService;

    @Autowired
    private PicService picService;

    @Autowired
    private RecruitService recruitService;

    @Autowired
    private RandomShopService randomShopService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AuthService authService;

    @Autowired
    private FavDetailService favDetailService;

    @Autowired
    PlatformTransactionManager txManager;

    //shopList
    @RequestMapping(value = "/api/shop/list/{shopType}/{limit}/{offset}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getShopList(@PathVariable int shopType, @PathVariable int limit, @PathVariable int offset) {

        int stationNum = 0;
        int mainMenuNum = 0;
        int priceLowNum = 0;
        int priceHighNum = 0;
        int dayPriceLowNum = 0;
        int dayPriceHighNum = 0;
        int nightPriceLowNum = 0;
        int nightPriceHighNum = 0;
        String mainMenu = "";
        String genreStr = "";
        String station = "";

        List<Shop> count = shopService.getShopAllCount(shopType);
        List<String> randomOneUuidList = new ArrayList<String>();
        List<String> randomTwoUuidList = new ArrayList<String>();
        List<Shop> list = new ArrayList<Shop>();

        int minute = DateUtil.getMinute(new Date(System.currentTimeMillis()));

        if(minute>5 && minute <55){
            List<RandomShopOne> randomOnelist = randomShopService.getShopRandomOne(shopType, limit, offset);
            if(randomOnelist.size()>0){
                for(RandomShopOne rso:randomOnelist){
                    randomOneUuidList.add(rso.getUuid());
                }
            }

            if(randomOneUuidList.size()>0){
                list = shopService.getShopByUuidList(randomOneUuidList);
            }
        }else{
            List<RandomShopTwo> randomTwolist = randomShopService.getShopRandomTwo(shopType, limit, offset);
            if(randomTwolist.size()>0){
                for(RandomShopTwo rso:randomTwolist){
                    randomTwoUuidList.add(rso.getUuid());
                }
            }

            if(randomTwoUuidList.size()>0){
                list = shopService.getShopByUuidList(randomTwoUuidList);
            }
        }

        //ALL
        if (shopType == Constant.SHOP_TAG.ALL) {
            Collections.shuffle(list);
            jsonObject = getShop(Constant.SHOP_TAG.ALL, list, count.size());
        }

        //食べる
        if (shopType == Constant.SHOP_TAG.FOOD) {
            Collections.shuffle(list);
            jsonObject = getShop(Constant.SHOP_TAG.FOOD, list, count.size());
        }

        //ファッション
        if (shopType == Constant.SHOP_TAG.FASTION) {
            Collections.shuffle(list);
            jsonObject = getShop(Constant.SHOP_TAG.FASTION, list, count.size());
        }

        //健康ビューティー
        if (shopType == Constant.SHOP_TAG.HEALTH) {
            Collections.shuffle(list);
            jsonObject = getShop(Constant.SHOP_TAG.HEALTH, list, count.size());

        }

        //遊ぶ
        if (shopType == Constant.SHOP_TAG.PLAY) {
            Collections.shuffle(list);
            jsonObject = getShop(Constant.SHOP_TAG.PLAY, list, count.size());
        }

        //ブライダル
        if (shopType == Constant.SHOP_TAG.BRIDAL) {
            Collections.shuffle(list);
            jsonObject = getShop(Constant.SHOP_TAG.BRIDAL, list, count.size());

        }

        //乗る
        if (shopType == Constant.SHOP_TAG.DRIVE) {
            Collections.shuffle(list);
            jsonObject = getShop(Constant.SHOP_TAG.DRIVE, list, count.size());
        }

        //学ぶ
        if (shopType == Constant.SHOP_TAG.LEARNING) {
            Collections.shuffle(list);
            jsonObject = getShop(Constant.SHOP_TAG.LEARNING, list, count.size());
        }

        //施設
        if (shopType == Constant.SHOP_TAG.FACILITY) {
            Collections.shuffle(list);
            jsonObject = getShop(Constant.SHOP_TAG.FACILITY, list, count.size());
        }

        //ライフスタイル
        if (shopType == Constant.SHOP_TAG.LIFE) {
            Collections.shuffle(list);
            jsonObject = getShop(Constant.SHOP_TAG.LIFE, list, count.size());
        }
        return jsonObject;
    }

    //shopDetail
    @RequestMapping(value = "/api/shop/detail/{uuid}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getShopDetail(@PathVariable String uuid, HttpServletRequest hsr) throws APIException {
        Long userId = 0L;
        try {
            userId = authService.getUserId(hsr);
        }catch (APIException ex){}
        Long shopId = shopService.getShopByUuid(uuid).getId();
        int shopType = shopDetailService.getShopType(shopId);
        String uuid2 = recruitService.findUuidByTypeAndId(shopType, shopId);

        String datenow = DateUtil.dateToStringyyyy_MM_dd(new Date());
        Date date = DateUtil.stringToDateyyyy_MM_dd(datenow);
        String datenow2 = DateUtil.dateToStringyyyy_MM_dd_hhmmss(new Date());
        Date now = DateUtil.stringToDateyyyy_MM_dd_HH_mm_ss(datenow2);


        //食べる
        if (shopType == Constant.SHOP_TAG.FOOD) {

            String genreStr = "";
            Pic pic = new Pic();
            List<Pic> picL2 = new ArrayList<>();
            pic.setItemType(Constant.EVENT_PIC_TYPE.SHOP);
            List<ShopFoodDetailJsonParse> ejpList = new ArrayList<ShopFoodDetailJsonParse>();

            List<ShopDetail> list = shopDetailService.getShopDetailAll(shopId);
            Shop shop = shopService.getShopById(shopId);
            Double coord1 = Double.parseDouble(shop.getCoordinate1());
            Double coord2 = Double.parseDouble(shop.getCoordinate2());
            String station = tagDetailService.getStation(shopId, shopType);

            int dayPriceLowNum = shopTagService.getContent(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_PRICE_LOW);
            int nightPriceLowNum = shopTagService.getContent(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_PRICE_LOW);
            int dayPriceHighNum = shopTagService.getContent(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_PRICE_HIGH);
            int nightPriceHighNum = shopTagService.getContent(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_PRICE_HIGH);
            int walkTime = shopTagService.getContent(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.WALK_TIME);
            int lunchOpen = shopTagService.getContent(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_OPEN_TIME);
            int lunchClose = shopTagService.getContent(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_CLOSE_TIME);
            int lunchLastOrder = shopTagService.getContent(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_LAST_ORDER);
            int dinnerOpen = shopTagService.getContent(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_OPEN_TIME);
            int dinnerClose = shopTagService.getContent(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_CLOSE_TIME);
            int dinnerLastOrder = shopTagService.getContent(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_LAST_ORDER);

            List<Integer> mainMenuNums = shopTagService.getContents(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.FIRST_MENU);
            for (int i = 0; i < mainMenuNums.size(); i++) {
                String mainMenu = tagDetailService.getDesc(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.FIRST_MENU, mainMenuNums.get(i));
                if (i == 0) {
                    genreStr = genreStr + mainMenu;
                } else {
                    genreStr = genreStr + "・" + mainMenu;
                }
            }

            String lunch = "";
            String dinner = "";
            String shopOpenTime = "";
            String lunchOpenTime = intToTime(lunchOpen);
            String lunchCloseTime = intToTime(lunchClose);
            String lunchLastOrderTime = intToTime(lunchLastOrder);
            String dinnerOpenTime = intToTime(dinnerOpen);
            String dinnerCloseTime = intToTime(dinnerClose);
            String dinnerLastOrderTime = intToTime(dinnerLastOrder);

            if (lunchOpen != 0 && lunchClose != 0 && lunchLastOrder != 0) {
                lunch = lunchOpenTime + "~" + lunchCloseTime + "(" + "L.O." + lunchLastOrderTime + ")";
            } else if (lunchOpen != 0 && lunchClose == 0 && lunchLastOrder != 0) {
                lunch = lunchOpenTime + "~" + " " + "(" + "L.O." + lunchLastOrderTime + ")";
            } else if (lunchOpen == 0 && lunchClose != 0 && lunchLastOrder != 0) {
                lunch =  "~" + lunchCloseTime + "(" + "L.O." + lunchLastOrderTime + ")";
            } else if (lunchOpen != 0 && lunchClose != 0 && lunchLastOrder == 0) {
                lunch = lunchOpenTime + "~" + lunchCloseTime;
            } else if (lunchOpen == 0 && lunchClose != 0 && lunchLastOrder == 0) {
                lunch =  "~" + lunchCloseTime;
            } else if (lunchOpen != 0 && lunchClose == 0 && lunchLastOrder == 0) {
                lunch = lunchOpenTime + "~" ;
            } else {
                lunch = "";
            }

            if (dinnerOpen != 0 && dinnerClose != 0 &&dinnerLastOrder != 0) {
                dinner = dinnerOpenTime + "~" + dinnerCloseTime + "(" + "L.O." + dinnerLastOrderTime + ")";
            } else if (dinnerOpen == 0 && dinnerClose != 0 &&dinnerLastOrder != 0) {
                dinner =  "~" + dinnerCloseTime + "(" + "L.O." + dinnerLastOrderTime + ")";
            } else if (dinnerOpen != 0 && dinnerClose == 0 &&dinnerLastOrder != 0) {
                dinner = dinnerOpenTime + "~" + " " + "(" + "L.O." + dinnerLastOrderTime + ")";
            } else if (dinnerOpen != 0 && dinnerClose == 0 && dinnerLastOrder == 0) {
                dinner = dinnerOpenTime + "~" ;
            } else if (dinnerOpen == 0 && dinnerClose != 0&& dinnerLastOrder == 0) {
                dinner =   "~" + dinnerCloseTime;
            } else {
                dinner = "";
            }

            if (lunchOpen != 0 && lunchClose ==0 && lunchLastOrder ==0 && dinnerOpen ==0 && dinnerClose != 0 && dinnerLastOrder !=0) {
                shopOpenTime = lunchOpenTime + "~"+ dinnerClose + "(" + "L.O." + dinnerLastOrder + ")";
            } else if(lunchOpen != 0 && lunchClose ==0 && lunchLastOrder ==0 && dinnerOpen ==0 && dinnerClose != 0 && dinnerLastOrder ==0){
                shopOpenTime = lunchOpenTime + "~"+ dinnerCloseTime ;
            }

            for (ShopDetail ed : list) {
                ShopFoodDetailJsonParse ejp = new ShopFoodDetailJsonParse();
                List<Pic> picList = picService.selectPicById(ed.getShopListId(), pic.getItemType());

                if (picList.size() >= 1) {
                    ejp.setPicUrl7(picList.get(0).getPicUrl());
                }
                if (picList.size() >= 2) {
                    ejp.setPicUrl8(picList.get(1).getPicUrl());
                }
                if (picList.size() >= 3) {
                    ejp.setPicUrl9(picList.get(2).getPicUrl());
                }
                if (picList.size() >= 4) {
                    ejp.setPicUrl10(picList.get(3).getPicUrl());
                }
                if (picList.size() >= 5) {
                    ejp.setPicUrl11(picList.get(4).getPicUrl());
                }
                if (picList.size() >= 6) {
                    ejp.setPicUrl12(picList.get(5).getPicUrl());
                }

                List<ShopCoupon> scList = new ArrayList<>();
                scList = shopCouponService.getShopCouponListNowFor(uuid);

                ejp.setUuid2(uuid2);
                ejp.setShopType(shopType);
                ejp.setScList(scList);
                ejp.setPicList(picL2);
                ejp.setId(ed.getShopListId());
                ejp.setName(ed.getName());
                ejp.setPicUrl1(ed.getPicUrl1());
                ejp.setPicUrl2(ed.getPicUrl2());
                ejp.setPicUrl3(ed.getPicUrl3());
                ejp.setPicUrl4(ed.getPicUrl4());
                ejp.setPicUrl5(ed.getPicUrl5());
                ejp.setPicUrl6(ed.getPicUrl6());
                ejp.setVideoUrl(ed.getVideoUrl());
                ejp.setTel(ed.getTel());
                ejp.setAddress(ed.getAddress());
                ejp.setStation(station);
                ejp.setWalkTime(walkTime);
                ejp.setDayPriceLow(dayPriceLowNum);
                ejp.setDayPriceHigh(dayPriceHighNum);
                ejp.setNightPriceLow(nightPriceLowNum);
                ejp.setNightPriceHigh(nightPriceHighNum);
                ejp.setGenre(genreStr);
                ejp.setCoord1(coord1);
                ejp.setCoord2(coord2);
                ejp.setLunch(lunch);
                ejp.setDinner(dinner);
                ejp.setShopOpenTime(shopOpenTime);
                ejp.setTitle(ed.getTitle());
                ejp.setDesc1(ed.getDesc1());
                ejp.setDesc2(ed.getDesc2());//座数
                ejp.setDesc3(ed.getDesc3());//個室
                ejp.setDesc4(ed.getDesc4());//貸切
                ejp.setDesc5(ed.getDesc5());//事前予約
                ejp.setDesc6(ed.getDesc6());//喫煙・禁煙
                ejp.setDesc7(ed.getDesc7());//駐車場
                ejp.setDesc8(ed.getDesc8());//テイクアウト
                ejp.setDesc9(ed.getDesc9());//出前・宅配
                ejp.setDesc10(ed.getDesc10());//飲み放題
                ejp.setDesc11(ed.getDesc11());//コース料理
                ejp.setDesc32(ed.getDesc32());//その他特徴設備
                ejp.setDesc15(ed.getDesc15());//支払い方法
                ejp.setDesc35(ed.getDesc35());//HP
                ejp.setDesc36(ed.getDesc36());//公式アカウント
                ejp.setDesc37(ed.getDesc37());//関連、系列グループ店舗
                ejp.setDesc38(ed.getDesc38());//備考
                ejp.setDesc39(ed.getDesc39());//定休日
                ejp.setDesc40(ed.getDesc40());//アクセス

                //now5
                List<Map<String, Object>> nowGoList = new ArrayList<Map<String, Object>>();
                ShopNowgo shopNowgo = shopNowgoService.getShopNowgoByShopId(shopId);
                if(shopNowgo != null){
                    if(shop != null){
                        String dateup = DateUtil.dateToStringyyyy_MM_dd(shopNowgo.getUpdateDatetime());
                        if (datenow.equals(dateup)) {
                            Long nowgoId = shopNowgo.getShopId();
                            Long shopListId = shop.getId();
                            if (nowgoId.equals(shopListId)) {
                                Map<String, Object> nowGoMap = new HashMap<String, Object>();
                                Date nowGoTime = shopNowgo.getUpdateDatetime();
                                String now5time = DateUtil.timeDifference(nowGoTime);
                                nowGoMap.put("uuid", shop.getUuid());
                                nowGoMap.put("type", shop.getShopType());
                                nowGoMap.put("address", shop.getAddress());
                                nowGoMap.put("name", shop.getName());
                                nowGoMap.put("now5", shopNowgo.getDesc());
                                nowGoMap.put("time", now5time);
                                nowGoList.add(nowGoMap);

                            }
                        }
                    }
                }

                //now4
                List<Map<String, Object>> couponList = new ArrayList<Map<String, Object>>();
                List<ShopCoupon> shopCouponList = shopCouponService.getShopCoupon(shopId);
                if(shopCouponList.size()>0){
                    for (ShopCoupon sc : shopCouponList) {
                        if(shop != null){
                            String datenow3 = DateUtil.dateToStringYyyy_MM_dd_hhmm(new Date());
                            Date date2 = DateUtil.stringToDateyyyy_MM_dd_HH_mm(datenow3);
                            String dateEnd = DateUtil.dateToStringYyyy_MM_dd_hhmm(sc.getPublishEnd());
                            Date dateEnd2 = DateUtil.stringToDateyyyy_MM_dd_HH_mm(dateEnd);
                            String dateF = DateUtil.dateToStringYyyy_MM_dd_hhmm(sc.getPublishStart());
                            Date datef = DateUtil.stringToDateyyyy_MM_dd_HH_mm(dateF);
                            if (datef.getTime() <= date2.getTime()) {
                                if (date2.getTime() <= dateEnd2.getTime()) {
                                    if (sc.getShopId().equals(shop.getId())) {
                                        Map<String, Object> couponMap = new HashMap<>();
                                        String now4time ="";
                                        Date couponUpdateTime = sc.getUpdateDatetime();
                                        Date couponPublistStartTime = sc.getPublishStart();
                                        if(couponUpdateTime.getTime()>=couponPublistStartTime.getTime()){
                                            now4time = DateUtil.timeDifference(couponUpdateTime);
                                        }else {
                                            now4time = DateUtil.timeDifference(couponPublistStartTime);
                                        }
                                        couponMap.put("uuid", shop.getUuid());
                                        couponMap.put("type", shop.getShopType());
                                        couponMap.put("address", shop.getAddress());
                                        couponMap.put("name", shop.getName());
                                        couponMap.put("title", sc.getTitle());
                                        couponMap.put("picUrl", sc.getPicUrl1());
                                        couponMap.put("desc", sc.getDesc());
                                        couponMap.put("time", now4time);
                                        couponList.add(couponMap);

                                    }
                                }
                            }
                        }
                    }
                }

                //couppon
                List<Map<String, Object>> couponList2 = new ArrayList<Map<String, Object>>();
                ShopDetail shopDetail = shopDetailService.getShopDetailByShopId(shopId);
                List<ShopCoupon> shopCouponList2 = shopCouponService.getShopCoupon(shopId);
                for (ShopCoupon sc : shopCouponList2) {
                    String dateEnd = DateUtil.dateToStringyyyy_MM_dd(sc.getPublishEnd());
                    Date dateEnd2 = DateUtil.stringToDateyyyy_MM_dd(dateEnd);
                    String dateF = DateUtil.dateToStringyyyy_MM_dd(sc.getPublishStart());
                    Date datef = DateUtil.stringToDateyyyy_MM_dd(dateF);
                    if (datef.getTime() <= date.getTime()) {
                        if (date.getTime() <= dateEnd2.getTime()) {
                            Map<String, Object> couponMap = new HashMap<>();
                            Date couponTime = sc.getPublishStart();
                            String now4time = DateUtil.timeDifference(couponTime);
                            couponMap.put("name", shopDetail.getName());
                            couponMap.put("title", sc.getTitle());
                            couponMap.put("picUrl", sc.getPicUrl1());
                            couponMap.put("desc", sc.getDesc());
                            couponMap.put("time", now4time);
                            couponList2.add(couponMap);
                        }
                    }
                }

                //fav
                if(userId != 0 &&userId != null && shopId != null){
                    FavDetail favDetail = favDetailService.getFavorite( userId, shopId, Constant.FAV_TYPE.SHOP);
                    if(favDetail != null){
                        UserSetting us = notificationService.getUserSettingBySettingKeyAndSettingValueAndByItemId(userId,Constant.USER_SETTING.NOW4,Constant.USER_SETTING.OFF,shopId);
                        if(us == null){
                            ejp.setUserSetting("0");
                            ejp.setFavType(Constant.FAV_TYPE.SHOP);
                        }else {
                            ejp.setUserSetting("1");
                            ejp.setFavType(Constant.FAV_TYPE.SHOP);
                        }
                    }else{
                        ejp.setUserSetting("2");
                        ejp.setFavType(Constant.FAV_TYPE.SHOP);
                    }
                }



                List<Recruit> recruitList = recruitService.getRecruitByShopId(ed.getShopListId());
                if (recruitList.size() > 0) {
                    ejp.setRecruitList(recruitList);
                }
                ejp.setCouponList2(couponList2);
                ejp.setCouponList(couponList);
                ejp.setNowGoList(nowGoList);
                ejpList.add(ejp);
            }
            jsonObject.setResultList(ejpList);
        }

        //ファッション
        if (shopType == Constant.SHOP_TAG.FASTION) {

            String genreStr = "";
            List<ShopDetail> list = shopDetailService.getShopDetailAll(shopId);
            Shop shop = shopService.getShopById(shopId);
            Double coord1 = Double.parseDouble(shop.getCoordinate1());
            Double coord2 = Double.parseDouble(shop.getCoordinate2());
            String station = tagDetailService.getStation(shopId, shopType);
            Pic pic = new Pic();

            pic.setItemType(Constant.EVENT_PIC_TYPE.SHOP);
            List<Pic> picL2 = new ArrayList<>();

            List<ShopFastionDetailJsonParse> ejpList = new ArrayList<ShopFastionDetailJsonParse>();
            List<Map<Integer, String>> brandStringList = new ArrayList<Map<Integer, String>>();
            Map<Integer, String> map = new HashMap<Integer, String>();

            int dayPriceLow = shopTagService.getContent(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.DAY_PRICE_LOW);
            int dayPriceHigh = shopTagService.getContent(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.DAY_PRICE_HIGH);
            int walkTime = shopTagService.getContent(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.WALK_TIME);
            int shopOpen = shopTagService.getContent(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.DAY_OPEN_TIME);
            int shopClose = shopTagService.getContent(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.NIGHT_CLOSE_TIME);
            int lastOrder = shopTagService.getContent(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.DAY_LAST_ORDER);
//            List<ShopCoupon> shopCouponList = shopCouponMapper.getShopCoupon(shopId);
            List<Integer> brandList = shopTagService.getContents(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.BRAND);

            List<Integer> mainMenuNums = shopTagService.getContents(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.FIRST_MENU);
            for (int i = 0; i < mainMenuNums.size(); i++) {
                String mainMenu = tagDetailService.getDesc(Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.FIRST_MENU, mainMenuNums.get(i));
                if (i == 0) {
                    genreStr = genreStr + mainMenu;
                } else {
                    genreStr = genreStr + "・" + mainMenu;
                }
            }

            for (int i = 0; i < brandList.size(); i++) {
                String brandString = tagDetailService.getDesc(Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.BRAND, brandList.get(i));
                map.put(brandList.get(i), brandString);
                brandStringList.add(map);
            }

            String shopOpenTime = "";
            String shopCloseTime = "";
            String shopLastOrder = "";
            shopOpenTime = intToTime(shopOpen);
            shopCloseTime = intToTime(shopClose);
            shopLastOrder = intToTime(lastOrder);

            if (shopOpen != 0 && shopClose != 0 && shopLastOrder != null && !shopLastOrder.equals(":") && lastOrder != 0) {
                shopOpenTime = shopOpenTime + "~" + shopCloseTime + "(" + "最終受付" + " " + shopLastOrder + ")";
            }else if (shopOpen == 0 && shopClose != 0 && shopLastOrder != null && !shopLastOrder.equals(":") && lastOrder != 0) {
                shopOpenTime =  " " +"~" + shopCloseTime + "(" + "最終受付" + " " + shopLastOrder + ")";
            }else if (shopOpen != 0 && shopClose == 0 && shopLastOrder != null && !shopLastOrder.equals(":") && lastOrder != 0) {
                shopOpenTime = shopOpenTime + "~" + " " + "(" + "最終受付" + " " + shopLastOrder + ")";
            }else if ((shopOpenTime != null && shopOpen != 0) && (shopCloseTime != null && shopClose != 0)) {
                shopOpenTime = shopOpenTime + "~" + shopCloseTime;
            }else if ((shopOpenTime != null && shopOpen != 0) && (shopCloseTime != null && shopClose == 0)) {
                shopOpenTime = shopOpenTime + "~" ;
            }else if ((shopOpenTime != null && shopOpen == 0) && (shopCloseTime != null && shopClose != 0)) {
                shopOpenTime =  "~" + shopCloseTime;
            } else {
                shopOpenTime = "";
            }

            for (ShopDetail ed : list) {
                ShopFastionDetailJsonParse ejp = new ShopFastionDetailJsonParse();
                List<Pic> picList = picService.selectPicById(ed.getShopListId(), pic.getItemType());

                if (picList.size() >= 1) {
                    ejp.setPicUrl7(picList.get(0).getPicUrl());
                }
                if (picList.size() >= 2) {
                    ejp.setPicUrl8(picList.get(1).getPicUrl());
                }
                if (picList.size() >= 3) {
                    ejp.setPicUrl9(picList.get(2).getPicUrl());
                }
                if (picList.size() >= 4) {
                    ejp.setPicUrl10(picList.get(3).getPicUrl());
                }
                if (picList.size() >= 5) {
                    ejp.setPicUrl11(picList.get(4).getPicUrl());
                }
                if (picList.size() >= 6) {
                    ejp.setPicUrl12(picList.get(5).getPicUrl());
                }

                List<ShopCoupon> scList = new ArrayList<>();
                scList = shopCouponService.getShopCouponListNowFor(uuid);

                ejp.setUuid2(uuid2);
                ejp.setShopType(shopType);
                ejp.setScList(scList);
                ejp.setId(ed.getShopListId());
                ejp.setName(ed.getName());
                ejp.setPicUrl1(ed.getPicUrl1());
                ejp.setPicUrl2(ed.getPicUrl2());
                ejp.setPicUrl3(ed.getPicUrl3());
                ejp.setPicUrl4(ed.getPicUrl4());
                ejp.setPicUrl5(ed.getPicUrl5());
                ejp.setPicUrl6(ed.getPicUrl6());
                ejp.setVideoUrl(ed.getVideoUrl());
                ejp.setTel(ed.getTel());
                ejp.setAddress(ed.getAddress());
                ejp.setStation(station);
                ejp.setGenre(genreStr);
                ejp.setCoord1(coord1);
                ejp.setCoord2(coord2);
                ejp.setWalkTime(walkTime);
                ejp.setDayPriceLow(dayPriceLow);
                ejp.setDayPriceHigh(dayPriceHigh);
                ejp.setShopOpenTime(shopOpenTime);
                ejp.setShopCloseTime(shopCloseTime);
                ejp.setTitle(ed.getTitle());
                ejp.setDesc1(ed.getDesc1());
                ejp.setDesc7(ed.getDesc7());
                ejp.setDesc9(ed.getDesc9());
                ejp.setDesc13(ed.getDesc13());
                ejp.setDesc15(ed.getDesc15());
                ejp.setDesc35(ed.getDesc35());
                ejp.setDesc36(ed.getDesc36());
                ejp.setDesc37(ed.getDesc37());
                ejp.setDesc38(ed.getDesc38());
                ejp.setDesc39(ed.getDesc39());//定休日
                ejp.setDesc40(ed.getDesc40());//アクセス
                ejp.setBrandStringList(brandStringList);

                //now5
                List<Map<String, Object>> nowGoList = new ArrayList<Map<String, Object>>();
                ShopNowgo shopNowgo = shopNowgoService.getShopNowgoByShopId(shopId);
                if(shopNowgo != null){
                    if(shop != null){
                        String dateup = DateUtil.dateToStringyyyy_MM_dd(shopNowgo.getUpdateDatetime());
                        if (datenow.equals(dateup)) {
                            Long nowgoId = shopNowgo.getShopId();
                            Long shopListId = shop.getId();
                            if (nowgoId.equals(shopListId)) {
                                Map<String, Object> nowGoMap = new HashMap<String, Object>();
                                Date nowGoTime = shopNowgo.getUpdateDatetime();
                                String now5time = DateUtil.timeDifference(nowGoTime);
                                nowGoMap.put("uuid", shop.getUuid());
                                nowGoMap.put("type", shop.getShopType());
                                nowGoMap.put("address", shop.getAddress());
                                nowGoMap.put("name", shop.getName());
                                nowGoMap.put("now5", shopNowgo.getDesc());
                                nowGoMap.put("time", now5time);
                                nowGoList.add(nowGoMap);
                            }
                        }
                    }
                }

                //now4
                List<Map<String, Object>> couponList = new ArrayList<Map<String, Object>>();
                List<ShopCoupon> shopCouponList = shopCouponService.getShopCoupon(shopId);
                if(shopCouponList.size()>0){
                    for (ShopCoupon sc : shopCouponList) {
                        if(shop != null){
                            String datenow3 = DateUtil.dateToStringYyyy_MM_dd_hhmm(new Date());
                            Date date2 = DateUtil.stringToDateyyyy_MM_dd_HH_mm(datenow3);
                            String dateEnd = DateUtil.dateToStringYyyy_MM_dd_hhmm(sc.getPublishEnd());
                            Date dateEnd2 = DateUtil.stringToDateyyyy_MM_dd_HH_mm(dateEnd);
                            String dateF = DateUtil.dateToStringYyyy_MM_dd_hhmm(sc.getPublishStart());
                            Date datef = DateUtil.stringToDateyyyy_MM_dd_HH_mm(dateF);
                            if (datef.getTime() <= date2.getTime()) {
                                if (date2.getTime() <= dateEnd2.getTime()) {
                                    if (sc.getShopId().equals(shop.getId())) {
                                        Map<String, Object> couponMap = new HashMap<>();
                                        String now4time ="";
                                        Date couponUpdateTime = sc.getUpdateDatetime();
                                        Date couponPublistStartTime = sc.getPublishStart();
                                        if(couponUpdateTime.getTime()>=couponPublistStartTime.getTime()){
                                            now4time = DateUtil.timeDifference(couponUpdateTime);
                                        }else {
                                            now4time = DateUtil.timeDifference(couponPublistStartTime);
                                        }
                                        couponMap.put("uuid", shop.getUuid());
                                        couponMap.put("type", shop.getShopType());
                                        couponMap.put("address", shop.getAddress());
                                        couponMap.put("name", shop.getName());
                                        couponMap.put("title", sc.getTitle());
                                        couponMap.put("picUrl", sc.getPicUrl1());
                                        couponMap.put("desc", sc.getDesc());
                                        couponMap.put("time", now4time);
                                        couponList.add(couponMap);
                                    }
                                }
                            }
                        }
                    }
                }
                //お店のcoupon
                List<Map<String, Object>> couponList2 = new ArrayList<Map<String, Object>>();
                ShopDetail shopDetail = shopDetailService.getShopDetailByShopId(shopId);
                List<ShopCoupon> shopCouponList2 = shopCouponService.getShopCoupon(shopId);
                for (ShopCoupon sc : shopCouponList2) {
                    String dateEnd = DateUtil.dateToStringyyyy_MM_dd(sc.getPublishEnd());
                    Date dateEnd2 = DateUtil.stringToDateyyyy_MM_dd(dateEnd);
                    String dateF = DateUtil.dateToStringyyyy_MM_dd(sc.getPublishStart());
                    Date datef = DateUtil.stringToDateyyyy_MM_dd(dateF);
                    if (datef.getTime() <= date.getTime()) {
                        if (date.getTime() <= dateEnd2.getTime()) {
                            Map<String, Object> couponMap = new HashMap<>();
                            Date couponTime = sc.getPublishStart();
                            String now4time = DateUtil.timeDifference(couponTime);
                            couponMap.put("name", shopDetail.getName());
                            couponMap.put("title", sc.getTitle());
                            couponMap.put("picUrl", sc.getPicUrl1());
                            couponMap.put("desc", sc.getDesc());
                            couponMap.put("time", now4time);
                            couponList2.add(couponMap);
                        }
                    }
                }

                //fav
                if(userId != 0 && userId != null &&shopId != null){
                    FavDetail favDetail = favDetailService.getFavorite( userId, shopId, Constant.FAV_TYPE.SHOP);
                    if(favDetail != null){
                        UserSetting us = notificationService.getUserSettingBySettingKeyAndSettingValueAndByItemId(userId,Constant.USER_SETTING.NOW4,Constant.USER_SETTING.OFF,shopId);
                        if(us == null){
                            ejp.setUserSetting("0");
                            ejp.setFavType(Constant.FAV_TYPE.SHOP);
                        }else {
                            ejp.setUserSetting("1");
                            ejp.setFavType(Constant.FAV_TYPE.SHOP);
                        }
                    }else{
                        ejp.setUserSetting("2");
                        ejp.setFavType(Constant.FAV_TYPE.SHOP);
                    }
                }

                List<Recruit> recruitList = recruitService.getRecruitByShopId(ed.getShopListId());
                if (recruitList.size() > 0) {
                    ejp.setRecruitList(recruitList);
                }
                ejp.setCouponList2(couponList2);
                ejp.setCouponList(couponList);
                ejp.setNowGoList(nowGoList);
                ejpList.add(ejp);
            }
            jsonObject.setResultList(ejpList);
        }

        //健康ビューティー
        if (shopType == Constant.SHOP_TAG.HEALTH) {

            List<ShopHealthDetailJsonParse> ejpList = new ArrayList<ShopHealthDetailJsonParse>();
            List<Map<Integer, String>> brandStringList = new ArrayList<Map<Integer, String>>();
            Map<Integer, String> map = new HashMap<Integer, String>();

            List<ShopDetail> list = shopDetailService.getShopDetailAll(shopId);
            String station = tagDetailService.getStation(shopId, shopType);
            Shop shop = shopService.getShopById(shopId);
            Double coord1 = Double.parseDouble(shop.getCoordinate1());
            Double coord2 = Double.parseDouble(shop.getCoordinate2());

            List<Pic> picL2 = new ArrayList<>();
            int dayPriceLowNum = shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_PRICE_LOW);
            int dayPriceHighNum = shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_PRICE_HIGH);
            int nightPriceLowNum = shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_PRICE_LOW);
            int nightPriceHighNum = shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_PRICE_HIGH);
            int walkTime = shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.WALK_TIME);
            int shopOpen = shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_OPEN_TIME);
            int shopClose = shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_CLOSE_TIME);
            int lastOrder = shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_LAST_ORDER);
            int seat = shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.SEAT);
            int massageTime = shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.MASSAGE_TIME);
            int mainMenu = shopTagService.getContent(shopId, shopType, Constant.SHOP_TAG.FIRST_MENU);
            String genreStr = tagDetailService.getDesc(Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.FIRST_MENU, mainMenu);
//            List<ShopCoupon> shopCouponList = shopCouponMapper.getShopCoupon(shopId);
            List<Integer> brandList = shopTagService.getContents(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.BRAND);
            Pic pic = new Pic();
            pic.setItemType(Constant.EVENT_PIC_TYPE.SHOP);
            for (int i = 0; i < brandList.size(); i++) {
                String brandString = tagDetailService.getDesc(Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.BRAND, brandList.get(i));
                map.put(brandList.get(i), brandString);
                brandStringList.add(map);
            }

            String shopOpenTime = "";
            String shopCloseTime = "";
            String shopLastOrder = "";
            shopOpenTime = intToTime(shopOpen);
            shopCloseTime = intToTime(shopClose);
            shopLastOrder = intToTime(lastOrder);

            if (shopOpen != 0 && shopClose != 0 && shopLastOrder != null && !shopLastOrder.equals(":") && lastOrder != 0) {
                shopOpenTime = shopOpenTime + "~" + shopCloseTime + "(" + "最終受付" + " " + shopLastOrder + ")";
            }else if (shopOpen == 0 && shopClose != 0 && shopLastOrder != null && !shopLastOrder.equals(":") && lastOrder != 0) {
                shopOpenTime =  " " +"~" + shopCloseTime + "(" + "最終受付" + " " + shopLastOrder + ")";
            }else if (shopOpen != 0 && shopClose == 0 && shopLastOrder != null && !shopLastOrder.equals(":") && lastOrder != 0) {
                shopOpenTime = shopOpenTime + "~" + " " + "(" + "最終受付" + " " + shopLastOrder + ")";
            }else if ((shopOpenTime != null && shopOpen != 0) && (shopCloseTime != null && shopClose != 0)) {
                shopOpenTime = shopOpenTime + "~" + shopCloseTime;
            }else if ((shopOpenTime != null && shopOpen != 0) && (shopCloseTime != null && shopClose == 0)) {
                shopOpenTime = shopOpenTime + "~" ;
            }else if ((shopOpenTime != null && shopOpen == 0) && (shopCloseTime != null && shopClose != 0)) {
                shopOpenTime =  "~" + shopCloseTime;
            } else {
                shopOpenTime = "";
            }


            for (ShopDetail ed : list) {
                ShopHealthDetailJsonParse ejp = new ShopHealthDetailJsonParse();
                List<Pic> picList = picService.selectPicById(ed.getShopListId(), pic.getItemType());

                if (picList.size() >= 1) {
                    ejp.setPicUrl7(picList.get(0).getPicUrl());
                }
                if (picList.size() >= 2) {
                    ejp.setPicUrl8(picList.get(1).getPicUrl());
                }
                if (picList.size() >= 3) {
                    ejp.setPicUrl9(picList.get(2).getPicUrl());
                }
                if (picList.size() >= 4) {
                    ejp.setPicUrl10(picList.get(3).getPicUrl());
                }
                if (picList.size() >= 5) {
                    ejp.setPicUrl11(picList.get(4).getPicUrl());
                }
                if (picList.size() >= 6) {
                    ejp.setPicUrl12(picList.get(5).getPicUrl());
                }

                List<ShopCoupon> scList = new ArrayList<>();
                scList = shopCouponService.getShopCouponListNowFor(uuid);
                ejp.setUuid2(uuid2);
                ejp.setShopType(shopType);
                ejp.setScList(scList);
                ejp.setPicList(picL2);
                ejp.setId(ed.getShopListId());
                ejp.setName(ed.getName());
                ejp.setPicUrl1(ed.getPicUrl1());
                ejp.setPicUrl2(ed.getPicUrl2());
                ejp.setPicUrl3(ed.getPicUrl3());
                ejp.setPicUrl4(ed.getPicUrl4());
                ejp.setPicUrl5(ed.getPicUrl5());
                ejp.setPicUrl6(ed.getPicUrl6());
                ejp.setVideoUrl(ed.getVideoUrl());
                ejp.setTel(ed.getTel());
                ejp.setAddress(ed.getAddress());
                ejp.setStation(station);
                ejp.setWalkTime(walkTime);
                ejp.setShopOpenTime(shopOpenTime);
                ejp.setShopCloseTime(shopCloseTime);
                ejp.setTitle(ed.getTitle());
                ejp.setDesc1(ed.getDesc1());
                ejp.setDesc2(ed.getDesc2());//座数
                ejp.setDesc7(ed.getDesc7());//駐車場
                ejp.setDesc12(ed.getDesc12());//取扱いブランド
                ejp.setDesc14(ed.getDesc14());//平均施術時間
                ejp.setDesc15(ed.getDesc15());//支払い方法
                ejp.setDesc16(ed.getDesc16());//スタッフプロフィール
                ejp.setDesc17(ed.getDesc17());//基本メニュー
                ejp.setDesc32(ed.getDesc32());//その他特徴設備
                ejp.setDesc33(ed.getDesc33());//その他特徴サービス
                ejp.setDesc35(ed.getDesc35());//HP
                ejp.setDesc36(ed.getDesc36());//公式アカウント
                ejp.setDesc37(ed.getDesc37()); //関連、系列グループ店舗
                ejp.setDesc38(ed.getDesc38());//備考
                ejp.setDesc39(ed.getDesc39());//定休日
                ejp.setDesc40(ed.getDesc40());//アクセス
                ejp.setSeat(seat);
                ejp.setMassageTime(massageTime);
                ejp.setDayPriceLow(dayPriceLowNum);
                ejp.setDayPriceHigh(dayPriceHighNum);
                ejp.setNightPriceLow(nightPriceLowNum);
                ejp.setNightPriceHigh(nightPriceHighNum);
                ejp.setGenre(genreStr);
                ejp.setCoord1(coord1);
                ejp.setCoord2(coord2);
                ejp.setBrandStringList(brandStringList);

                //now5
                List<Map<String, Object>> nowGoList = new ArrayList<Map<String, Object>>();
                ShopNowgo shopNowgo = shopNowgoService.getShopNowgoByShopId(shopId);
                if(shopNowgo != null){
                    if(shop != null){
                        String dateup = DateUtil.dateToStringyyyy_MM_dd(shopNowgo.getUpdateDatetime());
                        if (datenow.equals(dateup)) {
                            Long nowgoId = shopNowgo.getShopId();
                            Long shopListId = shop.getId();
                            if (nowgoId.equals(shopListId)) {
                                Map<String, Object> nowGoMap = new HashMap<String, Object>();
                                Date nowGoTime = shopNowgo.getUpdateDatetime();
                                String now5time = DateUtil.timeDifference(nowGoTime);
                                nowGoMap.put("uuid", shop.getUuid());
                                nowGoMap.put("type", shop.getShopType());
                                nowGoMap.put("address", shop.getAddress());
                                nowGoMap.put("name", shop.getName());
                                nowGoMap.put("now5", shopNowgo.getDesc());
                                nowGoMap.put("time", now5time);
                                nowGoList.add(nowGoMap);
                            }
                        }
                    }
                }

                //now4
                List<Map<String, Object>> couponList = new ArrayList<Map<String, Object>>();
                List<ShopCoupon> shopCouponList = shopCouponService.getShopCoupon(shopId);
                if(shopCouponList.size()>0){
                    for (ShopCoupon sc : shopCouponList) {
                        if(shop != null){
                            String datenow3 = DateUtil.dateToStringYyyy_MM_dd_hhmm(new Date());
                            Date date2 = DateUtil.stringToDateyyyy_MM_dd_HH_mm(datenow3);
                            String dateEnd = DateUtil.dateToStringYyyy_MM_dd_hhmm(sc.getPublishEnd());
                            Date dateEnd2 = DateUtil.stringToDateyyyy_MM_dd_HH_mm(dateEnd);
                            String dateF = DateUtil.dateToStringYyyy_MM_dd_hhmm(sc.getPublishStart());
                            Date datef = DateUtil.stringToDateyyyy_MM_dd_HH_mm(dateF);
                            if (datef.getTime() <= date2.getTime()) {
                                if (date2.getTime() <= dateEnd2.getTime()) {
                                    if (sc.getShopId().equals(shop.getId())) {
                                        Map<String, Object> couponMap = new HashMap<>();
                                        String now4time ="";
                                        Date couponUpdateTime = sc.getUpdateDatetime();
                                        Date couponPublistStartTime = sc.getPublishStart();
                                        if(couponUpdateTime.getTime()>=couponPublistStartTime.getTime()){
                                            now4time = DateUtil.timeDifference(couponUpdateTime);
                                        }else {
                                            now4time = DateUtil.timeDifference(couponPublistStartTime);
                                        }
                                        couponMap.put("uuid", shop.getUuid());
                                        couponMap.put("type", shop.getShopType());
                                        couponMap.put("address", shop.getAddress());
                                        couponMap.put("name", shop.getName());
                                        couponMap.put("title", sc.getTitle());
                                        couponMap.put("picUrl", sc.getPicUrl1());
                                        couponMap.put("desc", sc.getDesc());
                                        couponMap.put("time", now4time);
                                        couponList.add(couponMap);
                                    }
                                }
                            }
                        }
                    }
                }

                //お店のcoupon
                List<Map<String, Object>> couponList2 = new ArrayList<Map<String, Object>>();
                ShopDetail shopDetail = shopDetailService.getShopDetailByShopId(shopId);
                List<ShopCoupon> shopCouponList2 = shopCouponService.getShopCoupon(shopId);
                for (ShopCoupon sc : shopCouponList2) {
                    String dateEnd = DateUtil.dateToStringyyyy_MM_dd(sc.getPublishEnd());
                    Date dateEnd2 = DateUtil.stringToDateyyyy_MM_dd(dateEnd);
                    String dateF = DateUtil.dateToStringyyyy_MM_dd(sc.getPublishStart());
                    Date datef = DateUtil.stringToDateyyyy_MM_dd(dateF);
                    if (datef.getTime() <= date.getTime()) {
                        if (date.getTime() <= dateEnd2.getTime()) {
                            Map<String, Object> couponMap = new HashMap<>();
                            Date couponTime = sc.getPublishStart();
                            String now4time = DateUtil.timeDifference(couponTime);
                            couponMap.put("name", shopDetail.getName());
                            couponMap.put("title", sc.getTitle());
                            couponMap.put("picUrl", sc.getPicUrl1());
                            couponMap.put("desc", sc.getDesc());
                            couponMap.put("time", now4time);
                            couponList2.add(couponMap);
                        }
                    }
                }

                //fav
                if(userId != 0 && userId != null && shopId != null){
                    FavDetail favDetail = favDetailService.getFavorite( userId, shopId, Constant.FAV_TYPE.SHOP);
                    if(favDetail != null){
                        UserSetting us = notificationService.getUserSettingBySettingKeyAndSettingValueAndByItemId(userId,Constant.USER_SETTING.NOW4,Constant.USER_SETTING.OFF,shopId);
                        if(us == null){
                            ejp.setUserSetting("0");
                            ejp.setFavType(Constant.FAV_TYPE.SHOP);
                        }else {
                            ejp.setUserSetting("1");
                            ejp.setFavType(Constant.FAV_TYPE.SHOP);
                        }
                    }else{
                        ejp.setUserSetting("2");
                        ejp.setFavType(Constant.FAV_TYPE.SHOP);
                    }
                }

                List<Recruit> recruitList = recruitService.getRecruitByShopId(ed.getShopListId());
                if (recruitList.size() > 0) {
                    ejp.setRecruitList(recruitList);
                }
                ejp.setCouponList2(couponList2);
                ejp.setCouponList(couponList);
                ejp.setNowGoList(nowGoList);
                ejpList.add(ejp);
            }

            jsonObject.setResultList(ejpList);
        }

        //遊ぶ
        if (shopType == Constant.SHOP_TAG.PLAY) {

            String genreStr = "";
            List<ShopDetail> list = shopDetailService.getShopDetailAll(shopId);
            Shop shop = shopService.getShopById(shopId);
            Double coord1 = Double.parseDouble(shop.getCoordinate1());
            Double coord2 = Double.parseDouble(shop.getCoordinate2());
            String station = tagDetailService.getStation(shopId, shopType);
            Pic pic = new Pic();

            pic.setItemType(Constant.EVENT_PIC_TYPE.SHOP);
            List<Pic> picL2 = new ArrayList<>();

            List<ShopPlayDetailJsonParse> ejpList = new ArrayList<ShopPlayDetailJsonParse>();

            int dayPriceLowNum = shopTagService.getContent(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.DAY_PRICE_LOW);
            int dayPriceHighNum = shopTagService.getContent(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.DAY_PRICE_HIGH);
            int nightPriceLowNum = shopTagService.getContent(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.NIGHT_PRICE_LOW);
            int nightPriceHighNum = shopTagService.getContent(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.NIGHT_PRICE_HIGH);
            int walkTime = shopTagService.getContent(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.WALK_TIME);
            int shopOpen = shopTagService.getContent(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.DAY_OPEN_TIME);
            int shopClose = shopTagService.getContent(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.NIGHT_CLOSE_TIME);
            int lastOrder = shopTagService.getContent(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.DAY_LAST_ORDER);
//            List<ShopCoupon> shopCouponList = shopCouponMapper.getShopCoupon(shopId);

            String shopOpenTime = "";
            String shopCloseTime = "";
            String shopLastOrder = "";
            shopOpenTime = intToTime(shopOpen);
            shopCloseTime = intToTime(shopClose);
            shopLastOrder = intToTime(lastOrder);

            if (shopOpen != 0 && shopClose != 0 && shopLastOrder != null && !shopLastOrder.equals(":") && lastOrder != 0) {
                shopOpenTime = shopOpenTime + "~" + shopCloseTime + "(" + "最終受付" + " " + shopLastOrder + ")";
            }else if (shopOpen == 0 && shopClose != 0 && shopLastOrder != null && !shopLastOrder.equals(":") && lastOrder != 0) {
                shopOpenTime =  " " +"~" + shopCloseTime + "(" + "最終受付" + " " + shopLastOrder + ")";
            }else if (shopOpen != 0 && shopClose == 0 && shopLastOrder != null && !shopLastOrder.equals(":") && lastOrder != 0) {
                shopOpenTime = shopOpenTime + "~" + " " + "(" + "最終受付" + " " + shopLastOrder + ")";
            }else if ((shopOpenTime != null && shopOpen != 0) && (shopCloseTime != null && shopClose != 0)) {
                shopOpenTime = shopOpenTime + "~" + shopCloseTime;
            }else if ((shopOpenTime != null && shopOpen != 0) && (shopCloseTime != null && shopClose == 0)) {
                shopOpenTime = shopOpenTime + "~" ;
            }else if ((shopOpenTime != null && shopOpen == 0) && (shopCloseTime != null && shopClose != 0)) {
                shopOpenTime =  "~" + shopCloseTime;
            } else {
                shopOpenTime = "";
            }

            List<Integer> mainMenuNums = shopTagService.getContents(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.FIRST_MENU);
            for (int i = 0; i < mainMenuNums.size(); i++) {
                String mainMenu = tagDetailService.getDesc(Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.FIRST_MENU, mainMenuNums.get(i));
                if (i == 0) {
                    genreStr = genreStr + mainMenu;
                } else {
                    genreStr = genreStr + "・" + mainMenu;
                }
            }
            for (ShopDetail ed : list) {
                ShopPlayDetailJsonParse ejp = new ShopPlayDetailJsonParse();
                List<Pic> picList = picService.selectPicById(ed.getShopListId(), pic.getItemType());

                if (picList.size() >= 1) {
                    ejp.setPicUrl7(picList.get(0).getPicUrl());
                }
                if (picList.size() >= 2) {
                    ejp.setPicUrl8(picList.get(1).getPicUrl());
                }
                if (picList.size() >= 3) {
                    ejp.setPicUrl9(picList.get(2).getPicUrl());
                }
                if (picList.size() >= 4) {
                    ejp.setPicUrl10(picList.get(3).getPicUrl());
                }
                if (picList.size() >= 5) {
                    ejp.setPicUrl11(picList.get(4).getPicUrl());
                }
                if (picList.size() >= 6) {
                    ejp.setPicUrl12(picList.get(5).getPicUrl());
                }

                List<ShopCoupon> scList = new ArrayList<>();
                scList = shopCouponService.getShopCouponListNowFor(uuid);
                ejp.setUuid2(uuid2);
                ejp.setShopType(shopType);
                ejp.setScList(scList);
                ejp.setId(ed.getShopListId());
                ejp.setName(ed.getName());
                ejp.setPicUrl1(ed.getPicUrl1());
                ejp.setPicUrl2(ed.getPicUrl2());
                ejp.setPicUrl3(ed.getPicUrl3());
                ejp.setPicUrl4(ed.getPicUrl4());
                ejp.setPicUrl5(ed.getPicUrl5());
                ejp.setPicUrl6(ed.getPicUrl6());
                ejp.setVideoUrl(ed.getVideoUrl());
                ejp.setStation(station);
                ejp.setWalkTime(walkTime);
                ejp.setShopOpenTime(shopOpenTime);
                ejp.setShopCloseTime(shopCloseTime);
                ejp.setTel(ed.getTel());
                ejp.setGenre(genreStr);
                ejp.setCoord1(coord1);
                ejp.setCoord2(coord2);
                ejp.setAddress(ed.getAddress());
                ejp.setTitle(ed.getTitle());
                ejp.setDesc1(ed.getDesc1());
                ejp.setDesc18(ed.getDesc18());//団体予約
                ejp.setDesc34(ed.getDesc34());//ペット
                ejp.setDesc7(ed.getDesc7());//駐車場
                ejp.setDesc15(ed.getDesc15());//支払い方法
                ejp.setDesc32(ed.getDesc32());//その他特徴設備
                ejp.setDesc33(ed.getDesc33());//その他特徴サービス
                ejp.setDesc35(ed.getDesc35());//HP
                ejp.setDesc36(ed.getDesc36());//公式アカウント
                ejp.setDesc37(ed.getDesc37());//関連、系列グループ店舗
                ejp.setDesc38(ed.getDesc38());//備考
                ejp.setDesc39(ed.getDesc39());//定休日
                ejp.setDesc40(ed.getDesc40());//アクセス
                ejp.setDayPriceLow(dayPriceLowNum);
                ejp.setDayPriceHigh(dayPriceHighNum);
                ejp.setNightPriceLow(nightPriceLowNum);
                ejp.setNightPriceHigh(nightPriceHighNum);

                //now5
                List<Map<String, Object>> nowGoList = new ArrayList<Map<String, Object>>();
                ShopNowgo shopNowgo = shopNowgoService.getShopNowgoByShopId(shopId);
                if(shopNowgo != null){
                    if(shop != null){
                        String dateup = DateUtil.dateToStringyyyy_MM_dd(shopNowgo.getUpdateDatetime());
                        if (datenow.equals(dateup)) {
                            Long nowgoId = shopNowgo.getShopId();
                            Long shopListId = shop.getId();
                            if (nowgoId.equals(shopListId)) {
                                Map<String, Object> nowGoMap = new HashMap<String, Object>();
                                Date nowGoTime = shopNowgo.getUpdateDatetime();
                                String now5time = DateUtil.timeDifference(nowGoTime);
                                nowGoMap.put("uuid", shop.getUuid());
                                nowGoMap.put("type", shop.getShopType());
                                nowGoMap.put("address", shop.getAddress());
                                nowGoMap.put("name", shop.getName());
                                nowGoMap.put("now5", shopNowgo.getDesc());
                                nowGoMap.put("time", now5time);
                                nowGoList.add(nowGoMap);
                            }
                        }
                    }
                }

                //now4
                List<Map<String, Object>> couponList = new ArrayList<Map<String, Object>>();
                List<ShopCoupon> shopCouponList = shopCouponService.getShopCoupon(shopId);
                if(shopCouponList.size()>0){
                    for (ShopCoupon sc : shopCouponList) {
                        if(shop != null){
                            String datenow3 = DateUtil.dateToStringYyyy_MM_dd_hhmm(new Date());
                            Date date2 = DateUtil.stringToDateyyyy_MM_dd_HH_mm(datenow3);
                            String dateEnd = DateUtil.dateToStringYyyy_MM_dd_hhmm(sc.getPublishEnd());
                            Date dateEnd2 = DateUtil.stringToDateyyyy_MM_dd_HH_mm(dateEnd);
                            String dateF = DateUtil.dateToStringYyyy_MM_dd_hhmm(sc.getPublishStart());
                            Date datef = DateUtil.stringToDateyyyy_MM_dd_HH_mm(dateF);
                            if (datef.getTime() <= date2.getTime()) {
                                if (date2.getTime() <= dateEnd2.getTime()) {
                                    if (sc.getShopId().equals(shop.getId())) {
                                        Map<String, Object> couponMap = new HashMap<>();
                                        String now4time ="";
                                        Date couponUpdateTime = sc.getUpdateDatetime();
                                        Date couponPublistStartTime = sc.getPublishStart();
                                        if(couponUpdateTime.getTime()>=couponPublistStartTime.getTime()){
                                            now4time = DateUtil.timeDifference(couponUpdateTime);
                                        }else {
                                            now4time = DateUtil.timeDifference(couponPublistStartTime);
                                        }
                                        couponMap.put("uuid", shop.getUuid());
                                        couponMap.put("type", shop.getShopType());
                                        couponMap.put("address", shop.getAddress());
                                        couponMap.put("name", shop.getName());
                                        couponMap.put("title", sc.getTitle());
                                        couponMap.put("picUrl", sc.getPicUrl1());
                                        couponMap.put("desc", sc.getDesc());
                                        couponMap.put("time", now4time);
                                        couponList.add(couponMap);
                                    }
                                }
                            }
                        }
                    }
                }

                //お店のcoupon
                List<Map<String, Object>> couponList2 = new ArrayList<Map<String, Object>>();
                ShopDetail shopDetail = shopDetailService.getShopDetailByShopId(shopId);
                List<ShopCoupon> shopCouponList2 = shopCouponService.getShopCoupon(shopId);
                for (ShopCoupon sc : shopCouponList2) {
                    String dateEnd = DateUtil.dateToStringyyyy_MM_dd(sc.getPublishEnd());
                    Date dateEnd2 = DateUtil.stringToDateyyyy_MM_dd(dateEnd);
                    String dateF = DateUtil.dateToStringyyyy_MM_dd(sc.getPublishStart());
                    Date datef = DateUtil.stringToDateyyyy_MM_dd(dateF);
                    if (datef.getTime() <= date.getTime()) {
                        if (date.getTime() <= dateEnd2.getTime()) {
                            Map<String, Object> couponMap = new HashMap<>();
                            Date couponTime = sc.getPublishStart();
                            String now4time = DateUtil.timeDifference(couponTime);
                            couponMap.put("name", shopDetail.getName());
                            couponMap.put("title", sc.getTitle());
                            couponMap.put("picUrl", sc.getPicUrl1());
                            couponMap.put("desc", sc.getDesc());
                            couponMap.put("time", now4time);
                            couponList2.add(couponMap);
                        }
                    }
                }

                //fav
                if(userId != 0 &&userId != null && shopId != null){
                    FavDetail favDetail = favDetailService.getFavorite( userId, shopId, Constant.FAV_TYPE.SHOP);
                    if(favDetail != null){
                        UserSetting us = notificationService.getUserSettingBySettingKeyAndSettingValueAndByItemId(userId,Constant.USER_SETTING.NOW4,Constant.USER_SETTING.OFF,shopId);
                        if(us == null){
                            ejp.setUserSetting("0");
                            ejp.setFavType(Constant.FAV_TYPE.SHOP);
                        }else {
                            ejp.setUserSetting("1");
                            ejp.setFavType(Constant.FAV_TYPE.SHOP);
                        }
                    }else{
                        ejp.setUserSetting("2");
                        ejp.setFavType(Constant.FAV_TYPE.SHOP);
                    }
                }


                List<Recruit> recruitList = recruitService.getRecruitByShopId(ed.getShopListId());
                if (recruitList.size() > 0) {
                    ejp.setRecruitList(recruitList);
                }

                ejp.setCouponList2(couponList2);
                ejp.setCouponList(couponList);
                ejp.setNowGoList(nowGoList);
                ejpList.add(ejp);
            }
            jsonObject.setResultList(ejpList);
        }

        //ブライダル
        if (shopType == Constant.SHOP_TAG.BRIDAL) {

            List<ShopDetail> list = shopDetailService.getShopDetailAll(shopId);
            Shop shop = shopService.getShopById(shopId);
            Double coord1 = Double.parseDouble(shop.getCoordinate1());
            Double coord2 = Double.parseDouble(shop.getCoordinate2());
            String station = tagDetailService.getStation(shopId, shopType);
            Pic pic = new Pic();

            pic.setItemType(Constant.EVENT_PIC_TYPE.SHOP);
            List<Pic> picL2 = new ArrayList<>();

            List<ShopBridalDetailJsonParse> ejpList = new ArrayList<ShopBridalDetailJsonParse>();

            int contact = shopTagService.getContent(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.CONTACT_TIME);
            int foodPrice = shopTagService.getContent(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.FOOD_PRICE);
            int drinkPrice = shopTagService.getContent(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.DRINK_PRICE);
            int corkagePrice = shopTagService.getContent(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.CORKAGE_PRICE);
            int headCount = shopTagService.getContent(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.HEADCOUNT);
            int walkTime = shopTagService.getContent(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.WALK_TIME);
            int shopOpen = shopTagService.getContent(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.DAY_OPEN_TIME);
            int shopClose = shopTagService.getContent(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.NIGHT_CLOSE_TIME);
            int lastOrder = shopTagService.getContent(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.DAY_LAST_ORDER);
//            List<ShopCoupon> shopCouponList = shopCouponMapper.getShopCoupon(shopId);
            String contactTime = intToTime(contact);
            String shopOpenTime = "";
            String shopCloseTime = "";
            String shopLastOrder = "";
            shopOpenTime = intToTime(shopOpen);
            shopCloseTime = intToTime(shopClose);
            shopLastOrder = intToTime(lastOrder);

            if (shopOpen != 0 && shopClose != 0 && shopLastOrder != null && !shopLastOrder.equals(":") && lastOrder != 0) {
                shopOpenTime = shopOpenTime + "~" + shopCloseTime + "(" + "最終受付" + " " + shopLastOrder + ")";
            }else if (shopOpen == 0 && shopClose != 0 && shopLastOrder != null && !shopLastOrder.equals(":") && lastOrder != 0) {
                shopOpenTime =  " " +"~" + shopCloseTime + "(" + "最終受付" + " " + shopLastOrder + ")";
            }else if (shopOpen != 0 && shopClose == 0 && shopLastOrder != null && !shopLastOrder.equals(":") && lastOrder != 0) {
                shopOpenTime = shopOpenTime + "~" + " " + "(" + "最終受付" + " " + shopLastOrder + ")";
            }else if ((shopOpenTime != null && shopOpen != 0) && (shopCloseTime != null && shopClose != 0)) {
                shopOpenTime = shopOpenTime + "~" + shopCloseTime;
            }else if ((shopOpenTime != null && shopOpen != 0) && (shopCloseTime != null && shopClose == 0)) {
                shopOpenTime = shopOpenTime + "~" ;
            }else if ((shopOpenTime != null && shopOpen == 0) && (shopCloseTime != null && shopClose != 0)) {
                shopOpenTime =  "~" + shopCloseTime;
            } else {
                shopOpenTime = "";
            }

            for (ShopDetail ed : list) {
                ShopBridalDetailJsonParse ejp = new ShopBridalDetailJsonParse();
                List<Pic> picList = picService.selectPicById(ed.getShopListId(), pic.getItemType());

                if (picList.size() >= 1) {
                    ejp.setPicUrl7(picList.get(0).getPicUrl());
                }
                if (picList.size() >= 2) {
                    ejp.setPicUrl8(picList.get(1).getPicUrl());
                }
                if (picList.size() >= 3) {
                    ejp.setPicUrl9(picList.get(2).getPicUrl());
                }
                if (picList.size() >= 4) {
                    ejp.setPicUrl10(picList.get(3).getPicUrl());
                }
                if (picList.size() >= 5) {
                    ejp.setPicUrl11(picList.get(4).getPicUrl());
                }
                if (picList.size() >= 6) {
                    ejp.setPicUrl12(picList.get(5).getPicUrl());
                }

                List<ShopCoupon> scList = new ArrayList<>();
                scList = shopCouponService.getShopCouponListNowFor(uuid);

                ejp.setUuid2(uuid2);
                ejp.setShopType(shopType);
                ejp.setScList(scList);
                ejp.setId(ed.getShopListId());
                ejp.setName(ed.getName());
                ejp.setPicUrl1(ed.getPicUrl1());
                ejp.setPicUrl2(ed.getPicUrl2());
                ejp.setPicUrl3(ed.getPicUrl3());
                ejp.setPicUrl4(ed.getPicUrl4());
                ejp.setPicUrl5(ed.getPicUrl5());
                ejp.setPicUrl6(ed.getPicUrl6());
                ejp.setVideoUrl(ed.getVideoUrl());
                ejp.setTel(ed.getTel());
                ejp.setAddress(ed.getAddress());
                ejp.setStation(station);
                ejp.setCoord1(coord1);
                ejp.setCoord2(coord2);
                ejp.setWalkTime(walkTime);
                ejp.setShopOpenTime(shopOpenTime);
                ejp.setShopCloseTime(shopCloseTime);
                ejp.setTitle(ed.getTitle());
                ejp.setDesc1(ed.getDesc1());
                ejp.setDesc19(ed.getDesc19());//収容人数
                ejp.setDesc20(ed.getDesc20());//宿泊施設
                ejp.setDesc21(ed.getDesc21());//二次会
                ejp.setDesc22(ed.getDesc22());//送迎
                ejp.setDesc7(ed.getDesc7());//駐車場
                ejp.setDesc15(ed.getDesc15());//支払い方法
                ejp.setDesc32(ed.getDesc32());//その他特徴設備
                ejp.setDesc33(ed.getDesc33());//その他特徴サービス
                ejp.setDesc35(ed.getDesc35());//HP
                ejp.setDesc36(ed.getDesc36());//公式アカウント
                ejp.setDesc37(ed.getDesc37());//関連、系列グループ店舗
                ejp.setDesc38(ed.getDesc38());//備考
                ejp.setDesc40(ed.getDesc40());//アクセス
                ejp.setHeadCount(headCount);
                ejp.setContactTime(contactTime);
                ejp.setFoodPrice(foodPrice);
                ejp.setDrinkPrice(drinkPrice);
                ejp.setCorkagePrice(corkagePrice);

                //now5
                List<Map<String, Object>> nowGoList = new ArrayList<Map<String, Object>>();
                ShopNowgo shopNowgo = shopNowgoService.getShopNowgoByShopId(shopId);
                if(shopNowgo != null){
                    if(shop != null){
                        String dateup = DateUtil.dateToStringyyyy_MM_dd(shopNowgo.getUpdateDatetime());
                        if (datenow.equals(dateup)) {
                            Long nowgoId = shopNowgo.getShopId();
                            Long shopListId = shop.getId();
                            if (nowgoId.equals(shopListId)) {
                                Map<String, Object> nowGoMap = new HashMap<String, Object>();
                                Date nowGoTime = shopNowgo.getUpdateDatetime();
                                String now5time = DateUtil.timeDifference(nowGoTime);
                                nowGoMap.put("uuid", shop.getUuid());
                                nowGoMap.put("type", shop.getShopType());
                                nowGoMap.put("address", shop.getAddress());
                                nowGoMap.put("name", shop.getName());
                                nowGoMap.put("now5", shopNowgo.getDesc());
                                nowGoMap.put("time", now5time);
                                nowGoList.add(nowGoMap);
                            }
                        }
                    }
                }

                //now4
                List<Map<String, Object>> couponList = new ArrayList<Map<String, Object>>();
                List<ShopCoupon> shopCouponList = shopCouponService.getShopCoupon(shopId);
                if(shopCouponList.size()>0){
                    for (ShopCoupon sc : shopCouponList) {
                        if(shop != null){
                            String datenow3 = DateUtil.dateToStringYyyy_MM_dd_hhmm(new Date());
                            Date date2 = DateUtil.stringToDateyyyy_MM_dd_HH_mm(datenow3);
                            String dateEnd = DateUtil.dateToStringYyyy_MM_dd_hhmm(sc.getPublishEnd());
                            Date dateEnd2 = DateUtil.stringToDateyyyy_MM_dd_HH_mm(dateEnd);
                            String dateF = DateUtil.dateToStringYyyy_MM_dd_hhmm(sc.getPublishStart());
                            Date datef = DateUtil.stringToDateyyyy_MM_dd_HH_mm(dateF);
                            if (datef.getTime() <= date2.getTime()) {
                                if (date2.getTime() <= dateEnd2.getTime()) {
                                    if (sc.getShopId().equals(shop.getId())) {
                                        Map<String, Object> couponMap = new HashMap<>();
                                        String now4time ="";
                                        Date couponUpdateTime = sc.getUpdateDatetime();
                                        Date couponPublistStartTime = sc.getPublishStart();
                                        if(couponUpdateTime.getTime()>=couponPublistStartTime.getTime()){
                                            now4time = DateUtil.timeDifference(couponUpdateTime);
                                        }else {
                                            now4time = DateUtil.timeDifference(couponPublistStartTime);
                                        }
                                        couponMap.put("uuid", shop.getUuid());
                                        couponMap.put("type", shop.getShopType());
                                        couponMap.put("address", shop.getAddress());
                                        couponMap.put("name", shop.getName());
                                        couponMap.put("title", sc.getTitle());
                                        couponMap.put("picUrl", sc.getPicUrl1());
                                        couponMap.put("desc", sc.getDesc());
                                        couponMap.put("time", now4time);
                                        couponList.add(couponMap);
                                    }
                                }
                            }
                        }
                    }
                }

                //お店のcoupon
                List<Map<String, Object>> couponList2 = new ArrayList<Map<String, Object>>();
                ShopDetail shopDetail = shopDetailService.getShopDetailByShopId(shopId);
                List<ShopCoupon> shopCouponList2 = shopCouponService.getShopCoupon(shopId);
                for (ShopCoupon sc : shopCouponList2) {
                    String dateEnd = DateUtil.dateToStringyyyy_MM_dd(sc.getPublishEnd());
                    Date dateEnd2 = DateUtil.stringToDateyyyy_MM_dd(dateEnd);
                    String dateF = DateUtil.dateToStringyyyy_MM_dd(sc.getPublishStart());
                    Date datef = DateUtil.stringToDateyyyy_MM_dd(dateF);
                    if (datef.getTime() <= date.getTime()) {
                        if (date.getTime() <= dateEnd2.getTime()) {
                            Map<String, Object> couponMap = new HashMap<>();
                            Date couponTime = sc.getPublishStart();
                            String now4time = DateUtil.timeDifference(couponTime);
                            couponMap.put("name", shopDetail.getName());
                            couponMap.put("title", sc.getTitle());
                            couponMap.put("picUrl", sc.getPicUrl1());
                            couponMap.put("desc", sc.getDesc());
                            couponMap.put("time", now4time);
                            couponList2.add(couponMap);
                        }
                    }
                }

                //fav
                if(userId != 0 &&userId != null && shopId != null){
                    FavDetail favDetail = favDetailService.getFavorite( userId, shopId, Constant.FAV_TYPE.SHOP);
                    if(favDetail != null){
                        UserSetting us = notificationService.getUserSettingBySettingKeyAndSettingValueAndByItemId(userId,Constant.USER_SETTING.NOW4,Constant.USER_SETTING.OFF,shopId);
                        if(us == null){
                            ejp.setUserSetting("0");
                            ejp.setFavType(Constant.FAV_TYPE.SHOP);
                        }else {
                            ejp.setUserSetting("1");
                            ejp.setFavType(Constant.FAV_TYPE.SHOP);
                        }
                    }else{
                        ejp.setUserSetting("2");
                        ejp.setFavType(Constant.FAV_TYPE.SHOP);
                    }
                }

                List<Recruit> recruitList = recruitService.getRecruitByShopId(ed.getShopListId());
                if (recruitList.size() > 0) {
                    ejp.setRecruitList(recruitList);
                }

                ejp.setCouponList2(couponList2);
                ejp.setCouponList(couponList);
                ejp.setNowGoList(nowGoList);
                ejpList.add(ejp);
            }
            jsonObject.setResultList(ejpList);
        }

        //乗る
        if (shopType == Constant.SHOP_TAG.DRIVE) {

            String genreStr = "";
            List<ShopDetail> list = shopDetailService.getShopDetailAll(shopId);
            Shop shop = shopService.getShopById(shopId);
            Double coord1 = Double.parseDouble(shop.getCoordinate1());
            Double coord2 = Double.parseDouble(shop.getCoordinate2());
            String station = tagDetailService.getStation(shopId, shopType);
            Pic pic = new Pic();

            pic.setItemType(Constant.EVENT_PIC_TYPE.SHOP);
            List<Pic> picL2 = new ArrayList<>();

            List<ShopDriveDetailJsonParse> ejpList = new ArrayList<ShopDriveDetailJsonParse>();

            int priceLow = shopTagService.getContent(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.DAY_PRICE_LOW);
            int priceHigh = shopTagService.getContent(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.DAY_PRICE_HIGH);
            int walkTime = shopTagService.getContent(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.WALK_TIME);
            int shopOpen = shopTagService.getContent(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.DAY_OPEN_TIME);
            int shopClose = shopTagService.getContent(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.NIGHT_CLOSE_TIME);
            int lastOrder = shopTagService.getContent(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.DAY_LAST_ORDER);
//            List<ShopCoupon> shopCouponList = shopCouponMapper.getShopCoupon(shopId);

            String shopOpenTime = "";
            String shopCloseTime = "";
            String shopLastOrder = "";
            shopOpenTime = intToTime(shopOpen);
            shopCloseTime = intToTime(shopClose);
            shopLastOrder = intToTime(lastOrder);

            if (shopOpen != 0 && shopClose != 0 && shopLastOrder != null && !shopLastOrder.equals(":") && lastOrder != 0) {
                shopOpenTime = shopOpenTime + "~" + shopCloseTime + "(" + "最終受付" + " " + shopLastOrder + ")";
            }else if (shopOpen == 0 && shopClose != 0 && shopLastOrder != null && !shopLastOrder.equals(":") && lastOrder != 0) {
                shopOpenTime =  " " +"~" + shopCloseTime + "(" + "最終受付" + " " + shopLastOrder + ")";
            }else if (shopOpen != 0 && shopClose == 0 && shopLastOrder != null && !shopLastOrder.equals(":") && lastOrder != 0) {
                shopOpenTime = shopOpenTime + "~" + " " + "(" + "最終受付" + " " + shopLastOrder + ")";
            }else if ((shopOpenTime != null && shopOpen != 0) && (shopCloseTime != null && shopClose != 0)) {
                shopOpenTime = shopOpenTime + "~" + shopCloseTime;
            }else if ((shopOpenTime != null && shopOpen != 0) && (shopCloseTime != null && shopClose == 0)) {
                shopOpenTime = shopOpenTime + "~" ;
            }else if ((shopOpenTime != null && shopOpen == 0) && (shopCloseTime != null && shopClose != 0)) {
                shopOpenTime =  "~" + shopCloseTime;
            } else {
                shopOpenTime = "";
            }

            List<Integer> mainMenuNums = shopTagService.getContents(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.FIRST_MENU);
            for (int i = 0; i < mainMenuNums.size(); i++) {
                String mainMenu = tagDetailService.getDesc(Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.FIRST_MENU, mainMenuNums.get(i));
                if (i == 0) {
                    genreStr = genreStr + mainMenu;
                } else {
                    genreStr = genreStr + "・" + mainMenu;
                }
            }

            for (ShopDetail ed : list) {
                ShopDriveDetailJsonParse ejp = new ShopDriveDetailJsonParse();
                List<Pic> picList = picService.selectPicById(ed.getShopListId(), pic.getItemType());
                if (picList.size() >= 1) {
                    ejp.setPicUrl7(picList.get(0).getPicUrl());
                }
                if (picList.size() >= 2) {
                    ejp.setPicUrl8(picList.get(1).getPicUrl());
                }
                if (picList.size() >= 3) {
                    ejp.setPicUrl9(picList.get(2).getPicUrl());
                }
                if (picList.size() >= 4) {
                    ejp.setPicUrl10(picList.get(3).getPicUrl());
                }
                if (picList.size() >= 5) {
                    ejp.setPicUrl11(picList.get(4).getPicUrl());
                }
                if (picList.size() >= 6) {
                    ejp.setPicUrl12(picList.get(5).getPicUrl());
                }

                List<ShopCoupon> scList = new ArrayList<>();
                scList = shopCouponService.getShopCouponListNowFor(uuid);
                ejp.setUuid2(uuid2);
                ejp.setShopType(shopType);
                ejp.setScList(scList);
                ejp.setId(ed.getShopListId());
                ejp.setName(ed.getName());
                ejp.setPicUrl1(ed.getPicUrl1());
                ejp.setPicUrl2(ed.getPicUrl2());
                ejp.setPicUrl3(ed.getPicUrl3());
                ejp.setPicUrl4(ed.getPicUrl4());
                ejp.setPicUrl5(ed.getPicUrl5());
                ejp.setPicUrl6(ed.getPicUrl6());
                ejp.setVideoUrl(ed.getVideoUrl());
                ejp.setTel(ed.getTel());
                ejp.setAddress(ed.getAddress());
                ejp.setStation(station);
                ejp.setWalkTime(walkTime);
                ejp.setCoord1(coord1);
                ejp.setCoord2(coord2);
                ejp.setGenre(genreStr);
                ejp.setShopOpenTime(shopOpenTime);
                ejp.setShopCloseTime(shopCloseTime);
                ejp.setTitle(ed.getTitle());
                ejp.setDesc1(ed.getDesc1());
                ejp.setDesc7(ed.getDesc7());//駐車場
                ejp.setDesc15(ed.getDesc15());//支払い方法
                ejp.setDesc33(ed.getDesc33());//その他特徴サービス
                ejp.setDesc35(ed.getDesc35());//HP
                ejp.setDesc36(ed.getDesc36());//公式アカウント
                ejp.setDesc37(ed.getDesc37());//関連、系列グループ店舗
                ejp.setDesc38(ed.getDesc38());//備考
                ejp.setDesc39(ed.getDesc39());//定休日
                ejp.setDesc40(ed.getDesc40());//アクセス
                ejp.setDayPriceLow(priceLow);
                ejp.setDayPriceHigh(priceHigh);

                //now5
                List<Map<String, Object>> nowGoList = new ArrayList<Map<String, Object>>();
                ShopNowgo shopNowgo = shopNowgoService.getShopNowgoByShopId(shopId);
                if(shopNowgo != null){
                    if(shop != null){
                        String dateup = DateUtil.dateToStringyyyy_MM_dd(shopNowgo.getUpdateDatetime());
                        if (datenow.equals(dateup)) {
                            Long nowgoId = shopNowgo.getShopId();
                            Long shopListId = shop.getId();
                            if (nowgoId.equals(shopListId)) {
                                Map<String, Object> nowGoMap = new HashMap<String, Object>();
                                Date nowGoTime = shopNowgo.getUpdateDatetime();
                                String now5time = DateUtil.timeDifference(nowGoTime);
                                nowGoMap.put("uuid", shop.getUuid());
                                nowGoMap.put("type", shop.getShopType());
                                nowGoMap.put("address", shop.getAddress());
                                nowGoMap.put("name", shop.getName());
                                nowGoMap.put("now5", shopNowgo.getDesc());
                                nowGoMap.put("time", now5time);
                                nowGoList.add(nowGoMap);
                            }
                        }
                    }
                }

                //now4
                List<Map<String, Object>> couponList = new ArrayList<Map<String, Object>>();
                List<ShopCoupon> shopCouponList = shopCouponService.getShopCoupon(shopId);
                if(shopCouponList.size()>0){
                    for (ShopCoupon sc : shopCouponList) {
                        if(shop != null){
                            String datenow3 = DateUtil.dateToStringYyyy_MM_dd_hhmm(new Date());
                            Date date2 = DateUtil.stringToDateyyyy_MM_dd_HH_mm(datenow3);
                            String dateEnd = DateUtil.dateToStringYyyy_MM_dd_hhmm(sc.getPublishEnd());
                            Date dateEnd2 = DateUtil.stringToDateyyyy_MM_dd_HH_mm(dateEnd);
                            String dateF = DateUtil.dateToStringYyyy_MM_dd_hhmm(sc.getPublishStart());
                            Date datef = DateUtil.stringToDateyyyy_MM_dd_HH_mm(dateF);
                            if (datef.getTime() <= date2.getTime()) {
                                if (date2.getTime() <= dateEnd2.getTime()) {
                                    if (sc.getShopId().equals(shop.getId())) {
                                        Map<String, Object> couponMap = new HashMap<>();
                                        String now4time ="";
                                        Date couponUpdateTime = sc.getUpdateDatetime();
                                        Date couponPublistStartTime = sc.getPublishStart();
                                        if(couponUpdateTime.getTime()>=couponPublistStartTime.getTime()){
                                            now4time = DateUtil.timeDifference(couponUpdateTime);
                                        }else {
                                            now4time = DateUtil.timeDifference(couponPublistStartTime);
                                        }
                                        couponMap.put("uuid", shop.getUuid());
                                        couponMap.put("type", shop.getShopType());
                                        couponMap.put("address", shop.getAddress());
                                        couponMap.put("name", shop.getName());
                                        couponMap.put("title", sc.getTitle());
                                        couponMap.put("picUrl", sc.getPicUrl1());
                                        couponMap.put("desc", sc.getDesc());
                                        couponMap.put("time", now4time);
                                        couponList.add(couponMap);
                                    }
                                }
                            }
                        }
                    }
                }

                //お店のcoupon
                List<Map<String, Object>> couponList2 = new ArrayList<Map<String, Object>>();
                ShopDetail shopDetail = shopDetailService.getShopDetailByShopId(shopId);
                List<ShopCoupon> shopCouponList2 = shopCouponService.getShopCoupon(shopId);
                for (ShopCoupon sc : shopCouponList2) {
                    String dateEnd = DateUtil.dateToStringyyyy_MM_dd(sc.getPublishEnd());
                    Date dateEnd2 = DateUtil.stringToDateyyyy_MM_dd(dateEnd);
                    String dateF = DateUtil.dateToStringyyyy_MM_dd(sc.getPublishStart());
                    Date datef = DateUtil.stringToDateyyyy_MM_dd(dateF);
                    if (datef.getTime() <= date.getTime()) {
                        if (date.getTime() <= dateEnd2.getTime()) {
                            Map<String, Object> couponMap = new HashMap<>();
                            Date couponTime = sc.getPublishStart();
                            String now4time = DateUtil.timeDifference(couponTime);
                            couponMap.put("name", shopDetail.getName());
                            couponMap.put("title", sc.getTitle());
                            couponMap.put("picUrl", sc.getPicUrl1());
                            couponMap.put("desc", sc.getDesc());
                            couponMap.put("time", now4time);
                            couponList2.add(couponMap);
                        }
                    }
                }

                //fav
                if(userId != 0 &&userId != null && shopId != null){
                    FavDetail favDetail = favDetailService.getFavorite( userId, shopId, Constant.FAV_TYPE.SHOP);
                    if(favDetail != null){
                        UserSetting us = notificationService.getUserSettingBySettingKeyAndSettingValueAndByItemId(userId,Constant.USER_SETTING.NOW4,Constant.USER_SETTING.OFF,shopId);
                        if(us == null){
                            ejp.setUserSetting("0");
                            ejp.setFavType(Constant.FAV_TYPE.SHOP);
                        }else {
                            ejp.setUserSetting("1");
                            ejp.setFavType(Constant.FAV_TYPE.SHOP);
                        }
                    }else{
                        ejp.setUserSetting("2");
                        ejp.setFavType(Constant.FAV_TYPE.SHOP);
                    }
                }

                List<Recruit> recruitList = recruitService.getRecruitByShopId(ed.getShopListId());
                if (recruitList.size() > 0) {
                    ejp.setRecruitList(recruitList);
                }

                ejp.setCouponList2(couponList2);
                ejp.setCouponList(couponList);
                ejp.setNowGoList(nowGoList);
                ejpList.add(ejp);
            }
            jsonObject.setResultList(ejpList);
        }

        //学ぶ
        if (shopType == Constant.SHOP_TAG.LEARNING) {

            List<ShopDetail> list = shopDetailService.getShopDetailAll(shopId);
            Shop shop = shopService.getShopById(shopId);
            Double coord1 = Double.parseDouble(shop.getCoordinate1());
            Double coord2 = Double.parseDouble(shop.getCoordinate2());
            String station = tagDetailService.getStation(shopId, shopType);
            Pic pic = new Pic();

            pic.setItemType(Constant.EVENT_PIC_TYPE.SHOP);
            List<Pic> picL2 = new ArrayList<>();

            List<ShopLearningDetailJsonParse> ejpList = new ArrayList<ShopLearningDetailJsonParse>();

            int dayPriceLowNum = shopTagService.getContent(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.DAY_PRICE_LOW);
            int dayPriceHighNum = shopTagService.getContent(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.DAY_PRICE_HIGH);
            int nightPriceLowNum = shopTagService.getContent(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.NIGHT_PRICE_LOW);
            int nightPriceHighNum = shopTagService.getContent(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.NIGHT_PRICE_HIGH);
            int walkTime = shopTagService.getContent(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.WALK_TIME);
            int shopOpen = shopTagService.getContent(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.DAY_OPEN_TIME);
            int shopClose = shopTagService.getContent(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.NIGHT_CLOSE_TIME);
            int lastOrder = shopTagService.getContent(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.DAY_LAST_ORDER);
            int mainMenu = shopTagService.getContent(shopId, shopType, Constant.SHOP_TAG.FIRST_MENU);
            String genreStr = tagDetailService.getDesc(Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.FIRST_MENU, mainMenu);
//            List<ShopCoupon> shopCouponList = shopCouponMapper.getShopCoupon(shopId);

            String shopOpenTime = "";
            String shopCloseTime = "";
            String shopLastOrder = "";
            shopOpenTime = intToTime(shopOpen);
            shopCloseTime = intToTime(shopClose);
            shopLastOrder = intToTime(lastOrder);

            if (shopOpen != 0 && shopClose != 0 && shopLastOrder != null && !shopLastOrder.equals(":") && lastOrder != 0) {
                shopOpenTime = shopOpenTime + "~" + shopCloseTime + "(" + "最終受付" + " " + shopLastOrder + ")";
            }else if (shopOpen == 0 && shopClose != 0 && shopLastOrder != null && !shopLastOrder.equals(":") && lastOrder != 0) {
                shopOpenTime =  " " +"~" + shopCloseTime + "(" + "最終受付" + " " + shopLastOrder + ")";
            }else if (shopOpen != 0 && shopClose == 0 && shopLastOrder != null && !shopLastOrder.equals(":") && lastOrder != 0) {
                shopOpenTime = shopOpenTime + "~" + " " + "(" + "最終受付" + " " + shopLastOrder + ")";
            }else if ((shopOpenTime != null && shopOpen != 0) && (shopCloseTime != null && shopClose != 0)) {
                shopOpenTime = shopOpenTime + "~" + shopCloseTime;
            }else if ((shopOpenTime != null && shopOpen != 0) && (shopCloseTime != null && shopClose == 0)) {
                shopOpenTime = shopOpenTime + "~" ;
            }else if ((shopOpenTime != null && shopOpen == 0) && (shopCloseTime != null && shopClose != 0)) {
                shopOpenTime =  "~" + shopCloseTime;
            } else {
                shopOpenTime = "";
            }

            for (ShopDetail ed : list) {
                ShopLearningDetailJsonParse ejp = new ShopLearningDetailJsonParse();
                List<Pic> picList = picService.selectPicById(ed.getShopListId(), pic.getItemType());

                if (picList.size() >= 1) {
                    ejp.setPicUrl7(picList.get(0).getPicUrl());
                }
                if (picList.size() >= 2) {
                    ejp.setPicUrl8(picList.get(1).getPicUrl());
                }
                if (picList.size() >= 3) {
                    ejp.setPicUrl9(picList.get(2).getPicUrl());
                }
                if (picList.size() >= 4) {
                    ejp.setPicUrl10(picList.get(3).getPicUrl());
                }
                if (picList.size() >= 5) {
                    ejp.setPicUrl11(picList.get(4).getPicUrl());
                }
                if (picList.size() >= 6) {
                    ejp.setPicUrl12(picList.get(5).getPicUrl());
                }

                List<ShopCoupon> scList = new ArrayList<>();
                scList = shopCouponService.getShopCouponListNowFor(uuid);

                ejp.setUuid2(uuid2);
                ejp.setShopType(shopType);
                ejp.setScList(scList);
                ejp.setId(ed.getShopListId());
                ejp.setName(ed.getName());
                ejp.setPicUrl1(ed.getPicUrl1());
                ejp.setPicUrl2(ed.getPicUrl2());
                ejp.setPicUrl3(ed.getPicUrl3());
                ejp.setPicUrl4(ed.getPicUrl4());
                ejp.setPicUrl5(ed.getPicUrl5());
                ejp.setPicUrl6(ed.getPicUrl6());
                ejp.setVideoUrl(ed.getVideoUrl());
                ejp.setTel(ed.getTel());
                ejp.setAddress(ed.getAddress());
                ejp.setStation(station);
                ejp.setWalkTime(walkTime);
                ejp.setCoord1(coord1);
                ejp.setCoord2(coord2);
                ejp.setShopOpenTime(shopOpenTime);
                ejp.setShopCloseTime(shopCloseTime);
                ejp.setTitle(ed.getTitle());
                ejp.setDesc1(ed.getDesc1());
                ejp.setDesc23(ed.getDesc23());//校訓
                ejp.setDesc24(ed.getDesc24());//指導方針
                ejp.setDesc30(ed.getDesc30());//特徴設備
                ejp.setDesc7(ed.getDesc7());//駐車場
                ejp.setDesc15(ed.getDesc15());//支払い方法
                ejp.setDesc32(ed.getDesc32());//その他特徴設備
                ejp.setDesc33(ed.getDesc33());//その他特徴サービス
                ejp.setDesc35(ed.getDesc35());//HP
                ejp.setDesc36(ed.getDesc36());//公式アカウント
                ejp.setDesc37(ed.getDesc37());//関連、系列グループ店舗
                ejp.setDesc38(ed.getDesc38());//備考
                ejp.setDesc39(ed.getDesc39());//定休日
                ejp.setDesc40(ed.getDesc40());//アクセス
                ejp.setDayPriceLow(dayPriceLowNum);
                ejp.setDayPriceHigh(dayPriceHighNum);
                ejp.setNightPriceLow(nightPriceLowNum);
                ejp.setNightPriceHigh(nightPriceHighNum);
                ejp.setGenre(genreStr);

                //now5
                List<Map<String, Object>> nowGoList = new ArrayList<Map<String, Object>>();
                ShopNowgo shopNowgo = shopNowgoService.getShopNowgoByShopId(shopId);
                if(shopNowgo != null){
                    if(shop != null){
                        String dateup = DateUtil.dateToStringyyyy_MM_dd(shopNowgo.getUpdateDatetime());
                        if (datenow.equals(dateup)) {
                            Long nowgoId = shopNowgo.getShopId();
                            Long shopListId = shop.getId();
                            if (nowgoId.equals(shopListId)) {
                                Map<String, Object> nowGoMap = new HashMap<String, Object>();
                                Date nowGoTime = shopNowgo.getUpdateDatetime();
                                String now5time = DateUtil.timeDifference(nowGoTime);
                                nowGoMap.put("uuid", shop.getUuid());
                                nowGoMap.put("type", shop.getShopType());
                                nowGoMap.put("address", shop.getAddress());
                                nowGoMap.put("name", shop.getName());
                                nowGoMap.put("now5", shopNowgo.getDesc());
                                nowGoMap.put("time", now5time);
                                nowGoList.add(nowGoMap);
                            }
                        }
                    }
                }

                //now4
                List<Map<String, Object>> couponList = new ArrayList<Map<String, Object>>();
                List<ShopCoupon> shopCouponList = shopCouponService.getShopCoupon(shopId);
                if(shopCouponList.size()>0){
                    for (ShopCoupon sc : shopCouponList) {
                        if(shop != null){
                            String datenow3 = DateUtil.dateToStringYyyy_MM_dd_hhmm(new Date());
                            Date date2 = DateUtil.stringToDateyyyy_MM_dd_HH_mm(datenow3);
                            String dateEnd = DateUtil.dateToStringYyyy_MM_dd_hhmm(sc.getPublishEnd());
                            Date dateEnd2 = DateUtil.stringToDateyyyy_MM_dd_HH_mm(dateEnd);
                            String dateF = DateUtil.dateToStringYyyy_MM_dd_hhmm(sc.getPublishStart());
                            Date datef = DateUtil.stringToDateyyyy_MM_dd_HH_mm(dateF);
                            if (datef.getTime() <= date2.getTime()) {
                                if (date2.getTime() <= dateEnd2.getTime()) {
                                    if (sc.getShopId().equals(shop.getId())) {
                                        Map<String, Object> couponMap = new HashMap<>();
                                        String now4time ="";
                                        Date couponUpdateTime = sc.getUpdateDatetime();
                                        Date couponPublistStartTime = sc.getPublishStart();
                                        if(couponUpdateTime.getTime()>=couponPublistStartTime.getTime()){
                                            now4time = DateUtil.timeDifference(couponUpdateTime);
                                        }else {
                                            now4time = DateUtil.timeDifference(couponPublistStartTime);
                                        }
                                        couponMap.put("uuid", shop.getUuid());
                                        couponMap.put("type", shop.getShopType());
                                        couponMap.put("address", shop.getAddress());
                                        couponMap.put("name", shop.getName());
                                        couponMap.put("title", sc.getTitle());
                                        couponMap.put("picUrl", sc.getPicUrl1());
                                        couponMap.put("desc", sc.getDesc());
                                        couponMap.put("time", now4time);
                                        couponList.add(couponMap);
                                    }
                                }
                            }
                        }
                    }
                }

                //お店のcoupon
                List<Map<String, Object>> couponList2 = new ArrayList<Map<String, Object>>();
                ShopDetail shopDetail = shopDetailService.getShopDetailByShopId(shopId);
                List<ShopCoupon> shopCouponList2 = shopCouponService.getShopCoupon(shopId);
                for (ShopCoupon sc : shopCouponList2) {
                    String dateEnd = DateUtil.dateToStringyyyy_MM_dd(sc.getPublishEnd());
                    Date dateEnd2 = DateUtil.stringToDateyyyy_MM_dd(dateEnd);
                    String dateF = DateUtil.dateToStringyyyy_MM_dd(sc.getPublishStart());
                    Date datef = DateUtil.stringToDateyyyy_MM_dd(dateF);
                    if (datef.getTime() <= date.getTime()) {
                        if (date.getTime() <= dateEnd2.getTime()) {
                            Map<String, Object> couponMap = new HashMap<>();
                            Date couponTime = sc.getPublishStart();
                            String now4time = DateUtil.timeDifference(couponTime);
                            couponMap.put("name", shopDetail.getName());
                            couponMap.put("title", sc.getTitle());
                            couponMap.put("picUrl", sc.getPicUrl1());
                            couponMap.put("desc", sc.getDesc());
                            couponMap.put("time", now4time);
                            couponList2.add(couponMap);
                        }
                    }
                }

                //fav
                if(userId != 0 &&userId != null && shopId != null){
                    FavDetail favDetail = favDetailService.getFavorite( userId, shopId, Constant.FAV_TYPE.SHOP);
                    if(favDetail != null){
                        UserSetting us = notificationService.getUserSettingBySettingKeyAndSettingValueAndByItemId(userId,Constant.USER_SETTING.NOW4,Constant.USER_SETTING.OFF,shopId);
                        if(us == null){
                            ejp.setUserSetting("0");
                            ejp.setFavType(Constant.FAV_TYPE.SHOP);
                        }else {
                            ejp.setUserSetting("1");
                            ejp.setFavType(Constant.FAV_TYPE.SHOP);
                        }
                    }else{
                        ejp.setUserSetting("2");
                        ejp.setFavType(Constant.FAV_TYPE.SHOP);
                    }
                }


                List<Recruit> recruitList = recruitService.getRecruitByShopId(ed.getShopListId());
                if (recruitList.size() > 0) {
                    ejp.setRecruitList(recruitList);
                }

                ejp.setCouponList2(couponList2);
                ejp.setCouponList(couponList);
                ejp.setNowGoList(nowGoList);
                ejpList.add(ejp);
            }
            jsonObject.setResultList(ejpList);
        }

        //施設
        if (shopType == Constant.SHOP_TAG.FACILITY) {

            List<ShopDetail> list = shopDetailService.getShopDetailAll(shopId);
            Shop shop = shopService.getShopById(shopId);
            Double coord1 = Double.parseDouble(shop.getCoordinate1());
            Double coord2 = Double.parseDouble(shop.getCoordinate2());
            String station = tagDetailService.getStation(shopId, shopType);
            Pic pic = new Pic();

            pic.setItemType(Constant.EVENT_PIC_TYPE.SHOP);
            List<Pic> picL2 = new ArrayList<>();

            List<ShopFacilityDetailJsonParse> ejpList = new ArrayList<ShopFacilityDetailJsonParse>();

            int dayPriceLowNum = shopTagService.getContent(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_PRICE_LOW);
            int dayPriceHighNum = shopTagService.getContent(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_PRICE_HIGH);
            int nightPriceLowNum = shopTagService.getContent(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.NIGHT_PRICE_LOW);
            int nightPriceHighNum = shopTagService.getContent(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.NIGHT_PRICE_HIGH);
            int roomType = shopTagService.getContent(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.ROOM_TYPE);
            int roomNum = shopTagService.getContent(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.ROOM_NUM);
            int headCount = shopTagService.getContent(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.HEADCOUNT);
            int walkTime = shopTagService.getContent(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.WALK_TIME);
            int shopOpen = shopTagService.getContent(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_OPEN_TIME);
            int shopClose = shopTagService.getContent(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.NIGHT_CLOSE_TIME);
            int lastOrder = shopTagService.getContent(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_LAST_ORDER);
            int mainMenu = shopTagService.getContent(shopId, shopType, Constant.SHOP_TAG.FIRST_MENU);
            String genreStr = tagDetailService.getDesc(Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.FIRST_MENU, mainMenu);
//            List<ShopCoupon> shopCouponList = shopCouponMapper.getShopCoupon(shopId);

            String roomTypeString = tagDetailService.getDesc(Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.ROOM_TYPE, roomType);

            String shopOpenTime = "";
            String shopCloseTime = "";
            String shopLastOrder = "";
            shopOpenTime = intToTime(shopOpen);
            shopCloseTime = intToTime(shopClose);
            shopLastOrder = intToTime(lastOrder);

            if (shopOpen != 0 && shopClose != 0 && shopLastOrder != null && !shopLastOrder.equals(":") && lastOrder != 0) {
                shopOpenTime = shopOpenTime + "~" + shopCloseTime + "(" + "最終受付" + " " + shopLastOrder + ")";
            }else if (shopOpen == 0 && shopClose != 0 && shopLastOrder != null && !shopLastOrder.equals(":") && lastOrder != 0) {
                shopOpenTime =  " " +"~" + shopCloseTime + "(" + "最終受付" + " " + shopLastOrder + ")";
            }else if (shopOpen != 0 && shopClose == 0 && shopLastOrder != null && !shopLastOrder.equals(":") && lastOrder != 0) {
                shopOpenTime = shopOpenTime + "~" + " " + "(" + "最終受付" + " " + shopLastOrder + ")";
            }else if ((shopOpenTime != null && shopOpen != 0) && (shopCloseTime != null && shopClose != 0)) {
                shopOpenTime = shopOpenTime + "~" + shopCloseTime;
            }else if ((shopOpenTime != null && shopOpen != 0) && (shopCloseTime != null && shopClose == 0)) {
                shopOpenTime = shopOpenTime + "~" ;
            }else if ((shopOpenTime != null && shopOpen == 0) && (shopCloseTime != null && shopClose != 0)) {
                shopOpenTime =  "~" + shopCloseTime;
            } else {
                shopOpenTime = "";
            }

            String dayPriceLow = StringUtil.getPrice(dayPriceLowNum);
            String dayPriceHigh = StringUtil.getPrice(dayPriceHighNum);
            String nightPriceLow = StringUtil.getPrice(nightPriceLowNum);
            String nightPriceHigh = StringUtil.getPrice(nightPriceHighNum);

            for (ShopDetail ed : list) {
                ShopFacilityDetailJsonParse ejp = new ShopFacilityDetailJsonParse();
                List<Pic> picList = picService.selectPicById(ed.getShopListId(), pic.getItemType());

                if (picList.size() >= 1) {
                    ejp.setPicUrl7(picList.get(0).getPicUrl());
                }
                if (picList.size() >= 2) {
                    ejp.setPicUrl8(picList.get(1).getPicUrl());
                }
                if (picList.size() >= 3) {
                    ejp.setPicUrl9(picList.get(2).getPicUrl());
                }
                if (picList.size() >= 4) {
                    ejp.setPicUrl10(picList.get(3).getPicUrl());
                }
                if (picList.size() >= 5) {
                    ejp.setPicUrl11(picList.get(4).getPicUrl());
                }
                if (picList.size() >= 6) {
                    ejp.setPicUrl12(picList.get(5).getPicUrl());
                }

                List<ShopCoupon> scList = new ArrayList<>();
                scList = shopCouponService.getShopCouponListNowFor(uuid);

                ejp.setUuid2(uuid2);
                ejp.setShopType(shopType);
                ejp.setScList(scList);
                ejp.setId(ed.getShopListId());
                ejp.setName(ed.getName());
                ejp.setPicUrl1(ed.getPicUrl1());
                ejp.setPicUrl2(ed.getPicUrl2());
                ejp.setPicUrl3(ed.getPicUrl3());
                ejp.setPicUrl4(ed.getPicUrl4());
                ejp.setPicUrl5(ed.getPicUrl5());
                ejp.setPicUrl6(ed.getPicUrl6());
                ejp.setVideoUrl(ed.getVideoUrl());
                ejp.setTel(ed.getTel());
                ejp.setAddress(ed.getAddress());
                ejp.setStation(station);
                ejp.setWalkTime(walkTime);
                ejp.setCoord1(coord1);
                ejp.setCoord2(coord2);
                ejp.setShopOpenTime(shopOpenTime);
                ejp.setShopCloseTime(shopCloseTime);
                ejp.setTitle(ed.getTitle());
                ejp.setDesc1(ed.getDesc1());
                ejp.setDesc7(ed.getDesc7());//駐車場
                ejp.setDesc15(ed.getDesc15());//支払い方法
                ejp.setDesc19(ed.getDesc19());//収容人数
                ejp.setDesc20(ed.getDesc20());//宿泊施設
                ejp.setDesc21(ed.getDesc21());//二次会
                ejp.setDesc22(ed.getDesc22());//送迎
                ejp.setDesc25(ed.getDesc25());//食事
                ejp.setDesc26(ed.getDesc26());//利用用途
                ejp.setDesc27(ed.getDesc27());//プラン／コース
                ejp.setDesc31(ed.getDesc31());//サービス一覧
                ejp.setDesc32(ed.getDesc32());//その他特徴設備
                ejp.setDesc33(ed.getDesc33());//その他特徴サービス
                ejp.setDesc35(ed.getDesc35());//HP
                ejp.setDesc36(ed.getDesc36());//公式アカウント
                ejp.setDesc37(ed.getDesc37());//関連、系列グループ店舗
                ejp.setDesc38(ed.getDesc38());//備考
                ejp.setDesc39(ed.getDesc39());//定休日
                ejp.setDesc40(ed.getDesc40());//アクセス
                ejp.setDayPriceLow(dayPriceLow);
                ejp.setDayPriceHigh(dayPriceHigh);
                ejp.setNightPriceLow(nightPriceLow);
                ejp.setNightPriceHigh(nightPriceHigh);
                ejp.setGenre(genreStr);
                ejp.setRoomType(roomTypeString);
                ejp.setRoomNum(roomNum);
                ejp.setHeadCount(headCount);

                //now5
                List<Map<String, Object>> nowGoList = new ArrayList<Map<String, Object>>();
                ShopNowgo shopNowgo = shopNowgoService.getShopNowgoByShopId(shopId);
                if(shopNowgo != null){
                    if(shop != null){
                        String dateup = DateUtil.dateToStringyyyy_MM_dd(shopNowgo.getUpdateDatetime());
                        if (datenow.equals(dateup)) {
                            Long nowgoId = shopNowgo.getShopId();
                            Long shopListId = shop.getId();
                            if (nowgoId.equals(shopListId)) {
                                Map<String, Object> nowGoMap = new HashMap<String, Object>();
                                Date nowGoTime = shopNowgo.getUpdateDatetime();
                                String now5time = DateUtil.timeDifference(nowGoTime);
                                nowGoMap.put("uuid", shop.getUuid());
                                nowGoMap.put("type", shop.getShopType());
                                nowGoMap.put("address", shop.getAddress());
                                nowGoMap.put("name", shop.getName());
                                nowGoMap.put("now5", shopNowgo.getDesc());
                                nowGoMap.put("time", now5time);
                                nowGoList.add(nowGoMap);
                            }
                        }
                    }
                }

                //now4
                List<Map<String, Object>> couponList = new ArrayList<Map<String, Object>>();
                List<ShopCoupon> shopCouponList = shopCouponService.getShopCoupon(shopId);
                if(shopCouponList.size()>0){
                    for (ShopCoupon sc : shopCouponList) {
                        if(shop != null){
                            String datenow3 = DateUtil.dateToStringYyyy_MM_dd_hhmm(new Date());
                            Date date2 = DateUtil.stringToDateyyyy_MM_dd_HH_mm(datenow3);
                            String dateEnd = DateUtil.dateToStringYyyy_MM_dd_hhmm(sc.getPublishEnd());
                            Date dateEnd2 = DateUtil.stringToDateyyyy_MM_dd_HH_mm(dateEnd);
                            String dateF = DateUtil.dateToStringYyyy_MM_dd_hhmm(sc.getPublishStart());
                            Date datef = DateUtil.stringToDateyyyy_MM_dd_HH_mm(dateF);
                            if (datef.getTime() <= date2.getTime()) {
                                if (date2.getTime() <= dateEnd2.getTime()) {
                                    if (sc.getShopId().equals(shop.getId())) {
                                        Map<String, Object> couponMap = new HashMap<>();
                                        String now4time ="";
                                        Date couponUpdateTime = sc.getUpdateDatetime();
                                        Date couponPublistStartTime = sc.getPublishStart();
                                        if(couponUpdateTime.getTime()>=couponPublistStartTime.getTime()){
                                            now4time = DateUtil.timeDifference(couponUpdateTime);
                                        }else {
                                            now4time = DateUtil.timeDifference(couponPublistStartTime);
                                        }
                                        couponMap.put("uuid", shop.getUuid());
                                        couponMap.put("type", shop.getShopType());
                                        couponMap.put("address", shop.getAddress());
                                        couponMap.put("name", shop.getName());
                                        couponMap.put("title", sc.getTitle());
                                        couponMap.put("picUrl", sc.getPicUrl1());
                                        couponMap.put("desc", sc.getDesc());
                                        couponMap.put("time", now4time);
                                        couponList.add(couponMap);
                                    }
                                }
                            }
                        }
                    }
                }

                //お店のcoupon
                List<Map<String, Object>> couponList2 = new ArrayList<Map<String, Object>>();
                ShopDetail shopDetail = shopDetailService.getShopDetailByShopId(shopId);
                List<ShopCoupon> shopCouponList2 = shopCouponService.getShopCoupon(shopId);
                for (ShopCoupon sc : shopCouponList2) {
                    String dateEnd = DateUtil.dateToStringyyyy_MM_dd(sc.getPublishEnd());
                    Date dateEnd2 = DateUtil.stringToDateyyyy_MM_dd(dateEnd);
                    String dateF = DateUtil.dateToStringyyyy_MM_dd(sc.getPublishStart());
                    Date datef = DateUtil.stringToDateyyyy_MM_dd(dateF);
                    if (datef.getTime() <= date.getTime()) {
                        if (date.getTime() <= dateEnd2.getTime()) {
                            Map<String, Object> couponMap = new HashMap<>();
                            Date couponTime = sc.getPublishStart();
                            String now4time = DateUtil.timeDifference(couponTime);
                            couponMap.put("name", shopDetail.getName());
                            couponMap.put("title", sc.getTitle());
                            couponMap.put("picUrl", sc.getPicUrl1());
                            couponMap.put("desc", sc.getDesc());
                            couponMap.put("time", now4time);
                            couponList2.add(couponMap);
                        }
                    }
                }

                //fav
                if(userId != 0 &&userId != null && shopId != null){
                    FavDetail favDetail = favDetailService.getFavorite( userId, shopId, Constant.FAV_TYPE.SHOP);
                    if(favDetail != null){
                        UserSetting us = notificationService.getUserSettingBySettingKeyAndSettingValueAndByItemId(userId,Constant.USER_SETTING.NOW4,Constant.USER_SETTING.OFF,shopId);
                        if(us == null){
                            ejp.setUserSetting("0");
                            ejp.setFavType(Constant.FAV_TYPE.SHOP);
                        }else {
                            ejp.setUserSetting("1");
                            ejp.setFavType(Constant.FAV_TYPE.SHOP);
                        }
                    }else{
                        ejp.setUserSetting("2");
                        ejp.setFavType(Constant.FAV_TYPE.SHOP);
                    }
                }

                List<Recruit> recruitList = recruitService.getRecruitByShopId(ed.getShopListId());
                if (recruitList.size() > 0) {
                    ejp.setRecruitList(recruitList);
                }

                ejp.setCouponList2(couponList2);
                ejp.setCouponList(couponList);
                ejp.setNowGoList(nowGoList);
                ejpList.add(ejp);
            }
            jsonObject.setResultList(ejpList);
        }

        //ライフスタイル
        if (shopType == Constant.SHOP_TAG.LIFE) {

            List<ShopDetail> list = shopDetailService.getShopDetailAll(shopId);
            Shop shop = shopService.getShopById(shopId);
            Double coord1 = Double.parseDouble(shop.getCoordinate1());
            Double coord2 = Double.parseDouble(shop.getCoordinate2());
            String station = tagDetailService.getStation(shopId, shopType);
            Pic pic = new Pic();

            pic.setItemType(Constant.EVENT_PIC_TYPE.SHOP);
            List<Pic> picL2 = new ArrayList<>();
            List<ShopLifeDetailJsonParse> ejpList = new ArrayList<ShopLifeDetailJsonParse>();

            int priceLow = shopTagService.getContent(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.DAY_PRICE_LOW);
            int priceHigh = shopTagService.getContent(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.DAY_PRICE_HIGH);
            int walkTime = shopTagService.getContent(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.WALK_TIME);
            int shopOpen = shopTagService.getContent(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.DAY_OPEN_TIME);
            int shopClose = shopTagService.getContent(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.NIGHT_CLOSE_TIME);
            int lastOrder = shopTagService.getContent(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.DAY_LAST_ORDER);
            int mainMenu = shopTagService.getContent(shopId, shopType, Constant.SHOP_TAG.FIRST_MENU);
            String genreStr = tagDetailService.getDesc(Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.FIRST_MENU, mainMenu);
//            List<ShopCoupon> shopCouponList = shopCouponMapper.getShopCoupon(shopId);

            String shopOpenTime = "";
            String shopCloseTime = "";
            String shopLastOrder = "";
            shopOpenTime = intToTime(shopOpen);
            shopCloseTime = intToTime(shopClose);
            shopLastOrder = intToTime(lastOrder);

            if (shopOpen != 0 && shopClose != 0 && shopLastOrder != null && !shopLastOrder.equals(":") && lastOrder != 0) {
                shopOpenTime = shopOpenTime + "~" + shopCloseTime + "(" + "最終受付" + " " + shopLastOrder + ")";
            }else if (shopOpen == 0 && shopClose != 0 && shopLastOrder != null && !shopLastOrder.equals(":") && lastOrder != 0) {
                shopOpenTime =  " " +"~" + shopCloseTime + "(" + "最終受付" + " " + shopLastOrder + ")";
            }else if (shopOpen != 0 && shopClose == 0 && shopLastOrder != null && !shopLastOrder.equals(":") && lastOrder != 0) {
                shopOpenTime = shopOpenTime + "~" + " " + "(" + "最終受付" + " " + shopLastOrder + ")";
            }else if ((shopOpenTime != null && shopOpen != 0) && (shopCloseTime != null && shopClose != 0)) {
                shopOpenTime = shopOpenTime + "~" + shopCloseTime;
            }else if ((shopOpenTime != null && shopOpen != 0) && (shopCloseTime != null && shopClose == 0)) {
                shopOpenTime = shopOpenTime + "~" ;
            }else if ((shopOpenTime != null && shopOpen == 0) && (shopCloseTime != null && shopClose != 0)) {
                shopOpenTime =  "~" + shopCloseTime;
            } else {
                shopOpenTime = "";
            }

            for (ShopDetail ed : list) {
                ShopLifeDetailJsonParse ejp = new ShopLifeDetailJsonParse();
                List<Pic> picList = picService.selectPicById(ed.getShopListId(), pic.getItemType());

                if (picList.size() >= 1) {
                    ejp.setPicUrl7(picList.get(0).getPicUrl());
                }
                if (picList.size() >= 2) {
                    ejp.setPicUrl8(picList.get(1).getPicUrl());
                }
                if (picList.size() >= 3) {
                    ejp.setPicUrl9(picList.get(2).getPicUrl());
                }
                if (picList.size() >= 4) {
                    ejp.setPicUrl10(picList.get(3).getPicUrl());
                }
                if (picList.size() >= 5) {
                    ejp.setPicUrl11(picList.get(4).getPicUrl());
                }
                if (picList.size() >= 6) {
                    ejp.setPicUrl12(picList.get(5).getPicUrl());
                }

                List<ShopCoupon> scList = new ArrayList<>();
                scList = shopCouponService.getShopCouponListNowFor(uuid);

                ejp.setUuid2(uuid2);
                ejp.setShopType(shopType);
                ejp.setScList(scList);
                ejp.setId(ed.getShopListId());
                ejp.setName(ed.getName());
                ejp.setPicUrl1(ed.getPicUrl1());
                ejp.setPicUrl2(ed.getPicUrl2());
                ejp.setPicUrl3(ed.getPicUrl3());
                ejp.setPicUrl4(ed.getPicUrl4());
                ejp.setPicUrl5(ed.getPicUrl5());
                ejp.setPicUrl6(ed.getPicUrl6());
                ejp.setVideoUrl(ed.getVideoUrl());
                ejp.setTel(ed.getTel());
                ejp.setAddress(ed.getAddress());
                ejp.setStation(station);
                ejp.setWalkTime(walkTime);
                ejp.setCoord1(coord1);
                ejp.setCoord2(coord2);
                ejp.setShopOpenTime(shopOpenTime);
                ejp.setShopCloseTime(shopCloseTime);
                ejp.setTitle(ed.getTitle());
                ejp.setDesc1(ed.getDesc1());
                ejp.setDesc7(ed.getDesc7());//駐車場
                ejp.setDesc28(ed.getDesc28());//所属団体
                ejp.setDesc15(ed.getDesc15());//支払い方法
                ejp.setDesc29(ed.getDesc29());//店舗一覧
                ejp.setDesc31(ed.getDesc31());//サービス一覧,主な取り扱いサービス
                ejp.setDesc32(ed.getDesc32());//その他特徴設備
                ejp.setDesc33(ed.getDesc33());//その他特徴サービス
                ejp.setDesc35(ed.getDesc35());//HP
                ejp.setDesc36(ed.getDesc36());//公式アカウント
                ejp.setDesc37(ed.getDesc37());//関連、系列グループ店舗
                ejp.setDesc38(ed.getDesc38());//備考
                ejp.setDesc39(ed.getDesc39());//定休日
                ejp.setDesc40(ed.getDesc40());//アクセス
                ejp.setDayPriceLow(priceLow);
                ejp.setDayPriceHigh(priceHigh);
                ejp.setGenre(genreStr);

                //now5
                List<Map<String, Object>> nowGoList = new ArrayList<Map<String, Object>>();
                ShopNowgo shopNowgo = shopNowgoService.getShopNowgoByShopId(shopId);
                if(shopNowgo != null){
                    if(shop != null){
                        String dateup = DateUtil.dateToStringyyyy_MM_dd(shopNowgo.getUpdateDatetime());
                        if (datenow.equals(dateup)) {
                            Long nowgoId = shopNowgo.getShopId();
                            Long shopListId = shop.getId();
                            if (nowgoId.equals(shopListId)) {
                                Map<String, Object> nowGoMap = new HashMap<String, Object>();
                                Date nowGoTime = shopNowgo.getUpdateDatetime();
                                String now5time = DateUtil.timeDifference(nowGoTime);
                                nowGoMap.put("uuid", shop.getUuid());
                                nowGoMap.put("type", shop.getShopType());
                                nowGoMap.put("address", shop.getAddress());
                                nowGoMap.put("name", shop.getName());
                                nowGoMap.put("now5", shopNowgo.getDesc());
                                nowGoMap.put("time", now5time);
                                nowGoList.add(nowGoMap);
                            }
                        }
                    }
                }

                //now4
                List<Map<String, Object>> couponList = new ArrayList<Map<String, Object>>();
                List<ShopCoupon> shopCouponList = shopCouponService.getShopCoupon(shopId);
                if(shopCouponList.size()>0){
                    for (ShopCoupon sc : shopCouponList) {
                        if(shop != null){
                            String datenow3 = DateUtil.dateToStringYyyy_MM_dd_hhmm(new Date());
                            Date date2 = DateUtil.stringToDateyyyy_MM_dd_HH_mm(datenow3);
                            String dateEnd = DateUtil.dateToStringYyyy_MM_dd_hhmm(sc.getPublishEnd());
                            Date dateEnd2 = DateUtil.stringToDateyyyy_MM_dd_HH_mm(dateEnd);
                            String dateF = DateUtil.dateToStringYyyy_MM_dd_hhmm(sc.getPublishStart());
                            Date datef = DateUtil.stringToDateyyyy_MM_dd_HH_mm(dateF);
                            if (datef.getTime() <= date2.getTime()) {
                                if (date2.getTime() <= dateEnd2.getTime()) {
                                    if (sc.getShopId().equals(shop.getId())) {
                                        Map<String, Object> couponMap = new HashMap<>();
                                        String now4time ="";
                                        Date couponUpdateTime = sc.getUpdateDatetime();
                                        Date couponPublistStartTime = sc.getPublishStart();
                                        if(couponUpdateTime.getTime()>=couponPublistStartTime.getTime()){
                                            now4time = DateUtil.timeDifference(couponUpdateTime);
                                        }else {
                                            now4time = DateUtil.timeDifference(couponPublistStartTime);
                                        }
                                        couponMap.put("uuid", shop.getUuid());
                                        couponMap.put("type", shop.getShopType());
                                        couponMap.put("address", shop.getAddress());
                                        couponMap.put("name", shop.getName());
                                        couponMap.put("title", sc.getTitle());
                                        couponMap.put("picUrl", sc.getPicUrl1());
                                        couponMap.put("desc", sc.getDesc());
                                        couponMap.put("time", now4time);
                                        couponList.add(couponMap);
                                    }
                                }
                            }
                        }
                    }
                }

                //お店のcoupon
                List<Map<String, Object>> couponList2 = new ArrayList<Map<String, Object>>();
                ShopDetail shopDetail = shopDetailService.getShopDetailByShopId(shopId);
                List<ShopCoupon> shopCouponList2 = shopCouponService.getShopCoupon(shopId);
                for (ShopCoupon sc : shopCouponList2) {
                    String dateEnd = DateUtil.dateToStringyyyy_MM_dd(sc.getPublishEnd());
                    Date dateEnd2 = DateUtil.stringToDateyyyy_MM_dd(dateEnd);
                    String dateF = DateUtil.dateToStringyyyy_MM_dd(sc.getPublishStart());
                    Date datef = DateUtil.stringToDateyyyy_MM_dd(dateF);
                    if (datef.getTime() <= date.getTime()) {
                        if (date.getTime() <= dateEnd2.getTime()) {
                            Map<String, Object> couponMap = new HashMap<>();
                            Date couponTime = sc.getPublishStart();
                            String now4time = DateUtil.timeDifference(couponTime);
                            couponMap.put("name", shopDetail.getName());
                            couponMap.put("title", sc.getTitle());
                            couponMap.put("picUrl", sc.getPicUrl1());
                            couponMap.put("desc", sc.getDesc());
                            couponMap.put("time", now4time);
                            couponList2.add(couponMap);
                        }
                    }
                }

                //fav
                if(userId != 0 &&userId != null && shopId != null){
                    FavDetail favDetail = favDetailService.getFavorite( userId, shopId, Constant.FAV_TYPE.SHOP);
                    if(favDetail != null){
                        UserSetting us = notificationService.getUserSettingBySettingKeyAndSettingValueAndByItemId(userId,Constant.USER_SETTING.NOW4,Constant.USER_SETTING.OFF,shopId);
                        if(us == null){
                            ejp.setUserSetting("0");
                            ejp.setFavType(Constant.FAV_TYPE.SHOP);
                        }else {
                            ejp.setUserSetting("1");
                            ejp.setFavType(Constant.FAV_TYPE.SHOP);
                        }
                    }else{
                        ejp.setUserSetting("2");
                        ejp.setFavType(Constant.FAV_TYPE.SHOP);
                    }
                }

                List<Recruit> recruitList = recruitService.getRecruitByShopId(ed.getShopListId());
                if (recruitList.size() > 0) {
                    ejp.setRecruitList(recruitList);
                }
                ejp.setCouponList2(couponList2);
                ejp.setCouponList(couponList);
                ejp.setNowGoList(nowGoList);
                ejpList.add(ejp);
            }
            jsonObject.setResultList(ejpList);
        }
        return jsonObject;
    }

    //shopSearch
    @RequestMapping(value = "/api/shop/search/", method = RequestMethod.POST)
    @ResponseBody
    public JsonObject searchShopList(@ModelAttribute ShopListParams params) {

        List<Long> shopIdList = new ArrayList<Long>();
        List<Long> idList = new ArrayList<Long>();
        List<Map<String, Object>> contentList = new ArrayList<Map<String, Object>>();
        ShopSearchJsonParse ejp = new ShopSearchJsonParse();

        int dayPriceLowNum = 0;
        int dayPriceHighNum = 0;
        int nightPriceLowNum = 0;
        int nightPriceHighNum = 0;
        String genreStr = "";
        String station = "";
        String areaString = "";
        String picUrl1 = "";
        String picUrl2 = "";

        List<Integer> areas = params.getArea();
        List<Integer> brands = params.getBrand();
        List<Integer> characters = params.getCharacter();
        Integer dayPriceLow = params.getDayPriceLow();
        Integer nightPriceLow = params.getNightPriceLow();
        Integer dayPriceHigh = params.getDayPriceHigh();
        Integer nightPricehigh = params.getNightPriceHigh();
        Integer shopTime = params.getShopTime();
        Integer shopType = params.getShopType();
        Integer mainMenu = params.getMainMenu();
        Integer headCount = params.getHeadCount();
        Integer massageTime = params.getMassageTime();
        Integer roomNum = params.getRoomNum();
        Integer roomType = params.getRoomType();
        Integer scene = params.getScene();
        Integer now4 = params.getNow4();
        Integer now5 = params.getNow5();
        Integer limit = params.getLimit();
        Integer offset = params.getOffset();
        String Keyword = params.getKeyWord();
        Integer business = params.getBusiness();

        List<Long> getIdByShopType = new ArrayList<Long>();
        List<Long> getIdByArea = new ArrayList<Long>();
        List<Long> getIdByBrand = new ArrayList<Long>();
        List<Long> getIdByCharacter = new ArrayList<Long>();
        List<Long> getIdByDayPriceLow = new ArrayList<Long>();
        List<Long> getIdBynNightPriceLow = new ArrayList<Long>();
        List<Long> getIdByDayPriceHigh = new ArrayList<Long>();
        List<Long> getIdByNightPricehigh = new ArrayList<Long>();
        List<Long> getIdByShopTime = new ArrayList<Long>();
        List<Long> getIdByShopTime2 = new ArrayList<Long>();//night
        List<Long> getIdByMainMenu = new ArrayList<Long>();
        List<Long> getIdByHeadCount = new ArrayList<Long>();
        List<Long> getIdByMassageTime = new ArrayList<Long>();
        List<Long> getIdByRoomNum = new ArrayList<Long>();
        List<Long> getIdByRoomType = new ArrayList<Long>();
        List<Long> getIdByScene = new ArrayList<Long>();
        List<Long> getIdByNow4 = new ArrayList<Long>();
        List<Long> getIdByNow5 = new ArrayList<Long>();
        List<Long> getIdByKeyword = new ArrayList<Long>();
//      By wangyu ↓
        List<Long> getIdBybusinessDate = new ArrayList<Long>();
        List<Long> getIdBybusinessWeek = new ArrayList<Long>();
//      By wangyu ↑

        List<ShopDetail> detailList = new ArrayList<ShopDetail>();
        List<ShopTag> shopTagList = new ArrayList<ShopTag>();
        List<TagDetail> getByStationList = new ArrayList<TagDetail>();
        List<TagDetail> getByGenreList = new ArrayList<TagDetail>();
        List<Integer> tagDetalList = new ArrayList<Integer>();
        List<Integer> tagList = new ArrayList<Integer>();
        List<Integer> stationTagList = new ArrayList<Integer>();
        List<Integer> mainMenuNums = new ArrayList<Integer>();

        tagList.add(Constant.SHOP_TAG.STATION);
        tagList.add(Constant.SHOP_TAG.FIRST_MENU);
        tagList.add(Constant.SHOP_TAG.DAY_PRICE_LOW);
        tagList.add(Constant.SHOP_TAG.DAY_PRICE_HIGH);
        tagList.add(Constant.SHOP_TAG.NIGHT_PRICE_LOW);
        tagList.add(Constant.SHOP_TAG.NIGHT_PRICE_HIGH);


        if (shopType != null) {
            getIdByShopType = shopService.getShopId(shopType);
        }
        if (areas != null && areas.size() > 0) {
            getIdByArea = shopTagService.getShopIdByContents(shopType, Constant.SHOP_TAG.AREA, areas);
        }
        if (brands != null && brands.size() > 0) {
            getIdByBrand = shopTagService.getShopIdByContents(shopType, Constant.SHOP_TAG.BRAND, brands);
        }
        if (characters != null && characters.size() > 0) {
            getIdByCharacter = shopTagService.getShopIdByContents(shopType, Constant.SHOP_TAG.CHARACTER, characters);
        }
        if (dayPriceLow != null && dayPriceLow != 0) {
            getIdByDayPriceLow = shopTagService.getShopIdByContent(shopType, Constant.SHOP_TAG.DAY_PRICE_LOW, dayPriceLow);
        }
        if (nightPriceLow != null && nightPriceLow != 0) {
            getIdBynNightPriceLow = shopTagService.getShopIdByContent(shopType, Constant.SHOP_TAG.NIGHT_PRICE_LOW, nightPriceLow);
        }
        if (dayPriceHigh != null && dayPriceHigh != 0) {
            getIdByDayPriceHigh = shopTagService.getShopIdByContent(shopType, Constant.SHOP_TAG.DAY_PRICE_HIGH, dayPriceHigh);
        }
        if (nightPricehigh != null && nightPricehigh != 0) {
            getIdByNightPricehigh = shopTagService.getShopIdByContent(shopType, Constant.SHOP_TAG.NIGHT_PRICE_HIGH, nightPricehigh);
        }
        if (shopTime != null && shopTime != 0) {
            getIdByShopTime = shopTagService.getShopIdByContent(shopType, Constant.SHOP_TAG.DAY_OPEN_TIME, shopTime);
        }
        if (shopTime != null && shopTime != 0) {
            getIdByShopTime2 = shopTagService.getShopIdByContent(shopType, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, shopTime);
        }
        if (mainMenu != null && mainMenu != 0) {
            getIdByMainMenu = shopTagService.getShopIdByContent(shopType, Constant.SHOP_TAG.FIRST_MENU, mainMenu);
        }
        if (headCount != null && headCount != 0) {
            getIdByHeadCount = shopTagService.getShopIdByContent(shopType, Constant.SHOP_TAG.HEADCOUNT, headCount);
        }
        if (massageTime != null && massageTime != 0) {
            getIdByMassageTime = shopTagService.getShopIdByContent(shopType, Constant.SHOP_TAG.MASSAGE_TIME, massageTime);
        }
        if (roomNum != null && roomNum != 0) {
            getIdByRoomNum = shopTagService.getShopIdByContent(shopType, Constant.SHOP_TAG.ROOM_NUM, roomNum);
        }
        if (roomType != null && roomType != 0) {
            getIdByRoomType = shopTagService.getShopIdByContent(shopType, Constant.SHOP_TAG.ROOM_TYPE, roomType);
        }
        if (scene != null && scene != 0) {
            getIdByScene = shopTagService.getShopIdByContent(shopType, Constant.SHOP_TAG.SCENE, scene);
        }
        if (now4 != null && now4 != 0) {
            getIdByNow4 = shopService.getShopIdByNow4(shopType, now4);
        }
        if (now5 != null && now5 != 0) {
            getIdByNow5 = shopService.getShopIdByNow5(shopType, now5);
        }
        if (Keyword != null) {
            getIdByKeyword = shopService.getShopIdByKeyword(shopType, Keyword);
        }
        if (business != null) {
            if (business == 0 && shopType != 7 && shopType != 2) {
                SimpleDateFormat df = new SimpleDateFormat("dd");
                int content = Integer.parseInt(df.format(new Date()));
                int tagType = 32;
                getIdBybusinessDate = shopService.getIdBybusiness(shopType, tagType, content);
                Integer[] weeks = {7, 1, 2, 3, 4, 5, 6};
                Calendar c = Calendar.getInstance();
                int week = c.get(Calendar.WEEK_OF_MONTH);
                int day = c.get(Calendar.DAY_OF_WEEK);
                String week2 = Integer.toString(week);
                String day2 = Integer.toString(weeks[day - 1]);
                String content2 = String.valueOf(week2 + day2);
                int content3 = Integer.parseInt(content2);
                tagType = 33;
                getIdBybusinessWeek = shopService.getIdBybusiness(shopType, tagType, content3);
            }
        }

        if (areas != null && areas.size() > 0 && getIdByArea != null) {
            getIdByShopType.retainAll(getIdByArea);
        }
        if (brands != null && brands.size() > 0 && getIdByBrand != null) {
            getIdByShopType.retainAll(getIdByBrand);
        }
        if (characters != null && characters.size() > 0 && getIdByCharacter != null) {
            getIdByShopType.retainAll(getIdByCharacter);
        }
        if (dayPriceLow != null && dayPriceLow > 0 && getIdByDayPriceLow != null) {
            getIdByShopType.retainAll(getIdByDayPriceLow);
        }
        if (nightPriceLow != null && nightPriceLow > 0 && getIdBynNightPriceLow != null) {
            getIdByShopType.retainAll(getIdBynNightPriceLow);
        }
        if (dayPriceHigh != null && dayPriceHigh > 0 && getIdByDayPriceHigh != null) {
            getIdByShopType.retainAll(getIdByDayPriceHigh);
        }
        if (nightPricehigh != null && nightPricehigh > 0 && getIdByNightPricehigh != null) {
            getIdByShopType.retainAll(getIdByNightPricehigh);
        }
        if (shopTime != null && shopTime > 0 && getIdByShopTime != null && shopTime != 0) {
            getIdByShopType.retainAll(getIdByShopTime);
        }
        if (shopTime != null && shopTime > 0 && getIdByShopTime2 != null && shopTime != 0) {
            getIdByShopType.retainAll(getIdByShopTime2);
        }
        if (mainMenu != null && mainMenu > 0 && getIdByMainMenu != null && mainMenu != 0) {
            getIdByShopType.retainAll(getIdByMainMenu);
        }
        if (headCount != null && headCount > 0 && getIdByHeadCount != null) {
            getIdByShopType.retainAll(getIdByHeadCount);
        }
        if (massageTime != null && massageTime > 0 && getIdByMassageTime != null) {
            getIdByShopType.retainAll(getIdByMassageTime);
        }
        if (roomNum != null && roomNum > 0 && getIdByRoomNum != null) {
            getIdByShopType.retainAll(getIdByRoomNum);
        }
        if (roomType != null && roomType > 0 && getIdByRoomType != null) {
            getIdByShopType.retainAll(getIdByRoomType);
        }
        if (scene != null && scene > 0 && getIdByScene != null && scene != 0) {
            getIdByShopType.retainAll(getIdByScene);
        }
        if (now4 != null && now4 > 0 && getIdByNow4 != null && now4 != 0) {
            getIdByShopType.retainAll(getIdByNow4);
        }
        if (now5 != null && now5 > 0 && getIdByNow5 != null && now5 != 0) {
            getIdByShopType.retainAll(getIdByNow5);
        }
        if (Keyword != null && getIdByKeyword != null) {
            getIdByShopType.retainAll(getIdByKeyword);
        }
        getIdBybusinessDate.addAll(getIdBybusinessWeek);
        if (business != null && getIdBybusinessDate != null) {
            getIdByShopType.removeAll(getIdBybusinessDate);
        }
//        if (business != null && getIdBybusinessWeek != null && getIdBybusinessWeek.size() >= 0) {
//            getIdByShopType.retainAll(getIdBybusinessWeek);
//        }

        for (Long cd : getIdByShopType) {
            if (!shopIdList.contains(cd)) {
                shopIdList.add(cd);
            }
        }
        for (Long shopId : shopIdList) {
            idList.add(shopId);
        }

        if (areas == null) {

        } else if (areas.size() > 0) {
            for (int i = 0; i < areas.size(); i++) {
                String area = tagDetailService.getDesc(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.AREA, Integer.parseInt(String.valueOf(areas.get(i))));
                if (i == 0) {
                    areaString = area;
                } else {
                    areaString += "," + area;
                }
            }
        }

        if (idList == null) {

        } else if (idList.size() > 0) {
            Collections.shuffle(idList);
            params.setIdList(idList);
            shopTagList = shopTagService.getShopTagList(idList, shopType, tagList);
            detailList = shopDetailService.getShopDetailByShopIdList(idList);

            List<Shop> list = shopService.getShopList(params);
            List<Shop> count = shopService.getShopListCount(params);
            Collections.shuffle(list);

            for (ShopTag st : shopTagList) {
                if (st.getTagType() == Constant.SHOP_TAG.STATION) {
                    if (st.getContent() != null) {
                        stationTagList.add(st.getContent());
                    }
                }
                if (st.getTagType() == Constant.SHOP_TAG.FIRST_MENU) {
                    if (st.getContent() != null) {
                        mainMenuNums.add(st.getContent());
                    }
                }
            }

            if (stationTagList != null && stationTagList.size() > 0) {
                getByStationList = tagDetailService.getTagDetailListByContent(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.STATION, stationTagList);
            }
            if (mainMenuNums != null && mainMenuNums.size() > 0) {
                getByGenreList = tagDetailService.getTagDetailListByContent(shopType, Constant.SHOP_TAG.FIRST_MENU, mainMenuNums);
            }

            int shopCount = count.size();
            for (Shop ed : list) {
                Map<String, Object> map = new HashMap<String, Object>();
                List<String> mainMenuStr = new ArrayList<String>();

                for (ShopTag st : shopTagList) {
                    long id = ed.getId();
                    long shopId = st.getShopId();
                    if (id == shopId && (st.getTagType() == Constant.SHOP_TAG.STATION)) {
                        if (st.getContent() != null) {
                            for (TagDetail td : getByStationList) {
                                if (td.getContent() == st.getContent()) {
                                    station = td.getDesc();
                                }
                            }
                        }
                    }

                    if (id == shopId && (st.getTagType() == Constant.SHOP_TAG.FIRST_MENU)) {
                        if (st.getContent() != null) {
                            for (TagDetail ta : getByGenreList) {
                                int tagDetailContent = ta.getContent();
                                int tagContent = st.getContent();
                                int tagDetailType = ta.getModuleType();
                                int tagType = st.getModuleType();
                                if (tagDetailContent == tagContent && tagDetailType == tagType) {
                                    mainMenuStr.add(ta.getDesc());
                                }
                            }
                        }
                    }
                    if (id == shopId && st.getTagType() == Constant.SHOP_TAG.DAY_PRICE_LOW) {
                        if (st.getContent() != null) {
                            dayPriceLowNum = st.getContent();
                        }
                    }

                    if (id == shopId && st.getTagType() == Constant.SHOP_TAG.DAY_PRICE_HIGH) {
                        if (st.getContent() != null) {
                            dayPriceHighNum = st.getContent();
                        }
                    }

                    if (id == shopId && st.getTagType() == Constant.SHOP_TAG.NIGHT_PRICE_LOW) {
                        if (st.getContent() != null) {
                            nightPriceLowNum = st.getContent();
                        }
                    }

                    if (id == shopId && st.getTagType() == Constant.SHOP_TAG.NIGHT_PRICE_HIGH) {
                        if (st.getContent() != null) {
                            nightPriceHighNum = st.getContent();
                        }
                    }

                    if (mainMenuStr != null) {
                        genreStr = "";
                        for (int i = 0; i < mainMenuStr.size(); i++) {
                            if (i == 0) {
                                genreStr = genreStr + mainMenuStr.get(0);
                            } else {
                                genreStr = genreStr + "・" + mainMenuStr.get(i);
                            }
                        }
                    }
                }

                for (ShopDetail sd : detailList) {
                    long sdId = sd.getShopListId();
                    long edId = ed.getId();
                    if (edId == sdId) {
                        picUrl1 = sd.getPicUrl3();
                        picUrl2 = sd.getPicUrl4();
                    }
                }

                map.put("uuid", ed.getUuid());
                map.put("type", ed.getShopType());
                map.put("name", ed.getName());
                map.put("address", ed.getAddress());
                map.put("excerpt", ed.getExcerpt());
                map.put("picUrl", ed.getPicUrl());
                map.put("picUrl1", picUrl1);
                map.put("picUrl2", picUrl2);
                map.put("station", station);
                map.put("genre", genreStr);
                map.put("dayPriceLow", dayPriceLowNum);
                map.put("dayPriceHigh", dayPriceHighNum);
                map.put("nightPriceLow", nightPriceLowNum);
                map.put("nightPriceHigh", nightPriceHighNum);
                contentList.add(map);
            }

            //検索条件内容
//            List<Map<String, Object>> areaList = new ArrayList<Map<String, Object>>();
//            List<Map<String, Object>> budgetList = new ArrayList<Map<String, Object>>();
//            List<Map<String, Object>> closeTimeList = new ArrayList<Map<String, Object>>();
//            List<Map<String, Object>> sceneList = new ArrayList<Map<String, Object>>();
//            List<Map<String, Object>> detailSearchList = new ArrayList<Map<String, Object>>();
//            List<Map<String, Object>> mainMenuList = new ArrayList<Map<String, Object>>();
//            List<Map<String, Object>> subMenuList = new ArrayList<Map<String, Object>>();
//            List<Map<String, Object>> massageTimeList = new ArrayList<Map<String, Object>>();
//            List<Map<String, Object>> seatList = new ArrayList<Map<String, Object>>();
//            List<Map<String, Object>> brandList = new ArrayList<Map<String, Object>>();
//            List<Map<String, Object>> headCountList = new ArrayList<Map<String, Object>>();
//            List<Map<String, Object>> roomTypetList = new ArrayList<Map<String, Object>>();
//            List<Map<String, Object>> roomNumList = new ArrayList<Map<String, Object>>();
//
////            List<ShopDetail> detailList = new ArrayList<ShopDetail>();
////            List<ShopTag> shopTagList = new ArrayList<ShopTag>();
////            List<TagDetail> getByStationList = new ArrayList<TagDetail>();
////            List<TagDetail> getByGenreList = new ArrayList<TagDetail>();
//            List<Integer> tagDetalList2 = new ArrayList<Integer>();
//            List<Integer> tagList2 = new ArrayList<Integer>();
////            List<Integer> stationTagList = new ArrayList<Integer>();
////            List<Integer> mainMenuNums = new ArrayList<Integer>();
////            List<Long> idList = new ArrayList<Long>();
//
//            tagDetalList2.add(Constant.SHOP_TAG.AREA);
//            tagDetalList2.add(Constant.SHOP_TAG.DAY_PRICE_LOW);
//            tagDetalList2.add(Constant.SHOP_TAG.NIGHT_OPEN_TIME);
//            tagDetalList2.add(Constant.SHOP_TAG.SCENE);
//            tagDetalList2.add(Constant.SHOP_TAG.CHARACTER);
//            tagDetalList2.add(Constant.SHOP_TAG.FIRST_MENU);
//            tagDetalList2.add(Constant.SHOP_TAG.SECOND_MENU);
//            tagDetalList2.add(Constant.SHOP_TAG.MASSAGE_TIME);
//            tagDetalList2.add(Constant.SHOP_TAG.BRAND);
//            tagDetalList2.add(Constant.SHOP_TAG.SEAT);
//            tagDetalList2.add(Constant.SHOP_TAG.ROOM_TYPE);
//            tagDetalList2.add(Constant.SHOP_TAG.ROOM_NUM);
//            tagDetalList2.add(Constant.SHOP_TAG.HEADCOUNT);
//
//            tagList2.add(Constant.SHOP_TAG.STATION);
//            tagList2.add(Constant.SHOP_TAG.FIRST_MENU);
//            tagList2.add(Constant.SHOP_TAG.DAY_PRICE_LOW);
//            tagList2.add(Constant.SHOP_TAG.DAY_PRICE_HIGH);
//            tagList2.add(Constant.SHOP_TAG.NIGHT_PRICE_LOW);
//            tagList2.add(Constant.SHOP_TAG.NIGHT_PRICE_HIGH);
//
//            List<TagDetail> tagDetailList = tagDetailService.getTagDetailList(shopType, tagDetalList2);
//            List<TagDetail> foodTagDetailList = tagDetailService.getTagDetailList(Constant.SHOP_TAG.FOOD, tagDetalList2);
//
//            for (TagDetail td : foodTagDetailList) {
//                if (td.getModuleType() == Constant.SHOP_TAG.FOOD && td.getTagType() == Constant.SHOP_TAG.AREA) {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("content", td.getContent());
//                    map.put("desc", td.getDesc());
//                    areaList.add(map);
//                }
//
//                if (td.getModuleType() == Constant.SHOP_TAG.FOOD && td.getTagType() == Constant.SHOP_TAG.DAY_PRICE_LOW) {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("content", td.getContent());
//                    map.put("desc", td.getDesc());
//                    budgetList.add(map);
//                }
//
//                if (td.getModuleType() == Constant.SHOP_TAG.FOOD && td.getTagType() == Constant.SHOP_TAG.NIGHT_OPEN_TIME) {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("content", td.getContent());
//                    map.put("desc", td.getDesc());
//                    closeTimeList.add(map);
//                }
//
//                if (td.getModuleType() == Constant.SHOP_TAG.FOOD && td.getTagType() == Constant.SHOP_TAG.SEAT) {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("content", td.getContent());
//                    map.put("desc", td.getDesc());
//                    seatList.add(map);
//                }
//            }
//
//            for (TagDetail td : tagDetailList) {
//                if (td.getModuleType() == shopType.byteValue() && td.getTagType() == Constant.SHOP_TAG.SCENE) {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("content", td.getContent());
//                    map.put("desc", td.getDesc());
//                    sceneList.add(map);
//                }
//
//                if (td.getModuleType() == shopType.byteValue() && td.getTagType() == Constant.SHOP_TAG.CHARACTER) {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("content", td.getContent());
//                    map.put("desc", td.getDesc());
//                    detailSearchList.add(map);
//                }
//
//                if (td.getModuleType() == shopType.byteValue() && td.getTagType() == Constant.SHOP_TAG.FIRST_MENU) {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("content", td.getContent());
//                    map.put("desc", td.getDesc());
//                    mainMenuList.add(map);
//                }
//
//                if (td.getModuleType() == shopType.byteValue() && td.getTagType() == Constant.SHOP_TAG.SECOND_MENU) {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("content", td.getContent());
//                    map.put("desc", td.getDesc());
//                    subMenuList.add(map);
//                }
//
//                if (td.getModuleType() == shopType.byteValue() && td.getTagType() == Constant.SHOP_TAG.BRAND) {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("content", td.getContent());
//                    map.put("desc", td.getDesc());
//                    brandList.add(map);
//                }
//
//                if (td.getModuleType() == shopType.byteValue() && td.getTagType() == Constant.SHOP_TAG.ROOM_NUM) {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("content", td.getContent());
//                    map.put("desc", td.getDesc());
//                    roomNumList.add(map);
//                }
//
//                if (td.getModuleType() == shopType.byteValue() && td.getTagType() == Constant.SHOP_TAG.ROOM_TYPE) {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("content", td.getContent());
//                    map.put("desc", td.getDesc());
//                    roomTypetList.add(map);
//                }
//
//                if (td.getModuleType() == shopType.byteValue() && td.getTagType() == Constant.SHOP_TAG.MASSAGE_TIME) {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("content", td.getContent());
//                    map.put("desc", td.getDesc());
//                    massageTimeList.add(map);
//                }
//
//                if (td.getModuleType() == shopType.byteValue() && td.getTagType() == Constant.SHOP_TAG.HEADCOUNT) {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("content", td.getContent());
//                    map.put("desc", td.getDesc());
//                    headCountList.add(map);
//                }
//            }
//
//            if (shopType == Constant.SHOP_TAG.ALL) {
//                List<ShopAllJsonParse> ejpList = new ArrayList<ShopAllJsonParse>();
//                ShopAllJsonParse aejp = new ShopAllJsonParse();
//
//                aejp.setContentList(contentList);
//                aejp.setShopCount(shopCount);
//                aejp.setAreaList(areaList);
//                aejp.setBudgetList(budgetList);
//                aejp.setshopTimeList(closeTimeList);
//                ejpList.add(aejp);
//                jsonObject.setResultList(ejpList);
//
//            }
//            if (shopType == Constant.SHOP_TAG.FOOD) {
//                List<ShopFoodJsonParse> ejpList = new ArrayList<ShopFoodJsonParse>();
//                ShopFoodJsonParse fejp = new ShopFoodJsonParse();
//
//                fejp.setContentList(contentList);
//                fejp.setShopCount(shopCount);
//                fejp.setAreaList(areaList);
//                fejp.setBudgetList(budgetList);
//                fejp.setshopTimeList(closeTimeList);
//                fejp.setSceneList(sceneList);
//                fejp.setDetailSearchList(detailSearchList);
//                fejp.setMainMenu(mainMenuList);
//                fejp.setArea(areaString);
//                ejpList.add(fejp);
//                jsonObject.setResultList(ejpList);
//
//            } else if (shopType == Constant.SHOP_TAG.FASTION) {
//                List<ShopFastionJsonParse> ejpList = new ArrayList<ShopFastionJsonParse>();
//                ShopFastionJsonParse sejp = new ShopFastionJsonParse();
//
//                sejp.setContentList(contentList);
//                sejp.setShopCount(shopCount);
//                sejp.setAreaList(areaList);
//                sejp.setBudgetList(budgetList);
//                sejp.setshopTimeList(closeTimeList);
//                sejp.setSceneList(sceneList);
//                sejp.setDetailSearchList(detailSearchList);
//                sejp.setMainMenu(mainMenuList);
//                sejp.setArea(areaString);
//                ejpList.add(sejp);
//                jsonObject.setResultList(ejpList);
//
//            } else if (shopType == Constant.SHOP_TAG.HEALTH) {
//                List<ShopHealthJsonParse> ejpList = new ArrayList<ShopHealthJsonParse>();
//                ShopHealthJsonParse hejp = new ShopHealthJsonParse();
//
//                hejp.setContentList(contentList);
//                hejp.setShopCount(shopCount);
//                hejp.setAreaList(areaList);
//                hejp.setBudgetList(budgetList);
//                hejp.setShopTimeList(closeTimeList);
//                hejp.setBrandList(brandList);
//                hejp.setSeatList(seatList);
//                hejp.setMassageTimeList(massageTimeList);
//                hejp.setDetailSearchList(detailSearchList);
//                hejp.setMainMenu(mainMenuList);
//                hejp.setSubMenu(subMenuList);
//                ejp.setArea(areaString);
//                ejpList.add(hejp);
//                jsonObject.setResultList(ejpList);
//
//
//            } else if (shopType == Constant.SHOP_TAG.PLAY) {
//                List<ShopPlayJsonParse> ejpList = new ArrayList<ShopPlayJsonParse>();
//                ShopPlayJsonParse pejp = new ShopPlayJsonParse();
//
//                pejp.setContentList(contentList);
//                pejp.setShopCount(shopCount);
//                pejp.setAreaList(areaList);
//                pejp.setBudgetList(budgetList);
//                pejp.setShopTimeList(closeTimeList);
//                pejp.setSceneList(sceneList);
//                pejp.setDetailSearchList(detailSearchList);
//                pejp.setMainMenu(mainMenuList);
//                ejp.setArea(areaString);
//                ejpList.add(pejp);
//                jsonObject.setResultList(ejpList);
//
//            } else if (shopType == Constant.SHOP_TAG.BRIDAL) {
//                List<ShopBridalJsonParse> ejpList = new ArrayList<ShopBridalJsonParse>();
//                ShopBridalJsonParse bejp = new ShopBridalJsonParse();
//
//                bejp.setContentList(contentList);
//                bejp.setShopCount(shopCount);
//                bejp.setAreaList(areaList);
//                bejp.setBudgetList(budgetList);
//                bejp.setShopTimeList(closeTimeList);
//                bejp.setDetailSearchList(detailSearchList);
//                bejp.setHeadCountList(headCountList);
//                bejp.setMainMenu(mainMenuList);
//                bejp.setArea(areaString);
//                ejpList.add(bejp);
//                jsonObject.setResultList(ejpList);
//
//            } else if (shopType == Constant.SHOP_TAG.DRIVE) {
//                List<ShopDriveJsonParse> ejpList = new ArrayList<ShopDriveJsonParse>();
//                ShopDriveJsonParse dejp = new ShopDriveJsonParse();
//
//                dejp.setContentList(contentList);
//                dejp.setShopCount(shopCount);
//                dejp.setAreaList(areaList);
//                dejp.setBudgetList(budgetList);
//                dejp.setShopTimeList(closeTimeList);
//                dejp.setSceneList(sceneList);
//                dejp.setDetailSearchList(detailSearchList);
//                dejp.setMainMenu(mainMenuList);
//                dejp.setArea(areaString);
//                ejpList.add(dejp);
//                jsonObject.setResultList(ejpList);
//
//
//            } else if (shopType == Constant.SHOP_TAG.LEARNING) {
//                List<ShopLearnJsonParse> ejpList = new ArrayList<ShopLearnJsonParse>();
//                ShopLearnJsonParse lejp = new ShopLearnJsonParse();
//
//                lejp.setContentList(contentList);
//                lejp.setShopCount(shopCount);
//                lejp.setAreaList(areaList);
//                lejp.setBudgetList(budgetList);
//                lejp.setShopTimeList(closeTimeList);
//                lejp.setDetailSearchList(detailSearchList);
//                lejp.setSceneList(sceneList);
//                lejp.setMainMenu(mainMenuList);
//                lejp.setSubMenu(subMenuList);
//                lejp.setArea(areaString);
//                ejpList.add(lejp);
//                jsonObject.setResultList(ejpList);
//
//            } else if (shopType == Constant.SHOP_TAG.FACILITY) {
//                List<ShopFacilityJsonParse> ejpList = new ArrayList<ShopFacilityJsonParse>();
//                ShopFacilityJsonParse cejp = new ShopFacilityJsonParse();
//
//                cejp.setContentList(contentList);
//                cejp.setShopCount(shopCount);
//                cejp.setAreaList(areaList);
//                cejp.setBudgetList(budgetList);
//                cejp.setShopTimeList(closeTimeList);
//                cejp.setDetailSearchList(detailSearchList);
//                cejp.setHeadCountList(headCountList);
//                cejp.setRoomTypeList(roomTypetList);
//                cejp.setRoomNumList(roomNumList);
//                cejp.setMainMenu(mainMenuList);
//                cejp.setSubMenu(subMenuList);
//                cejp.setArea(areaString);
//                ejpList.add(cejp);
//                jsonObject.setResultList(ejpList);
//
//            } else if (shopType == Constant.SHOP_TAG.LIFE) {
//                List<ShopLifeJsonParse> ejpList = new ArrayList<ShopLifeJsonParse>();
//                ShopLifeJsonParse iejp = new ShopLifeJsonParse();
//
//                iejp.setContentList(contentList);
//                iejp.setShopCount(shopCount);
//                iejp.setAreaList(areaList);
//                iejp.setBudgetList(budgetList);
//                iejp.setShopTimeList(closeTimeList);
//                iejp.setDetailSearchList(detailSearchList);
//                iejp.setMainMenu(mainMenuList);
//                iejp.setSubMenu(subMenuList);
//                iejp.setArea(areaString);
//                ejpList.add(iejp);
//                jsonObject.setResultList(ejpList);
//            }

            ejp.setShopCount(shopCount);
            ejp.setArea(areaString);
            ejp.setContentList(contentList);
            jsonObject.setResultList(ejp);
        }else {
//            List<Map<String, Object>> areaList = new ArrayList<Map<String, Object>>();
//            List<Map<String, Object>> budgetList = new ArrayList<Map<String, Object>>();
//            List<Map<String, Object>> closeTimeList = new ArrayList<Map<String, Object>>();
//            List<Map<String, Object>> sceneList = new ArrayList<Map<String, Object>>();
//            List<Map<String, Object>> detailSearchList = new ArrayList<Map<String, Object>>();
//            List<Map<String, Object>> mainMenuList = new ArrayList<Map<String, Object>>();
//            List<Map<String, Object>> subMenuList = new ArrayList<Map<String, Object>>();
//            List<Map<String, Object>> massageTimeList = new ArrayList<Map<String, Object>>();
//            List<Map<String, Object>> seatList = new ArrayList<Map<String, Object>>();
//            List<Map<String, Object>> brandList = new ArrayList<Map<String, Object>>();
//            List<Map<String, Object>> headCountList = new ArrayList<Map<String, Object>>();
//            List<Map<String, Object>> roomTypetList = new ArrayList<Map<String, Object>>();
//            List<Map<String, Object>> roomNumList = new ArrayList<Map<String, Object>>();
//
////            List<ShopDetail> detailList = new ArrayList<ShopDetail>();
////            List<ShopTag> shopTagList = new ArrayList<ShopTag>();
////            List<TagDetail> getByStationList = new ArrayList<TagDetail>();
////            List<TagDetail> getByGenreList = new ArrayList<TagDetail>();
//            List<Integer> tagDetalList2 = new ArrayList<Integer>();
//            List<Integer> tagList2 = new ArrayList<Integer>();
////            List<Integer> stationTagList = new ArrayList<Integer>();
////            List<Integer> mainMenuNums = new ArrayList<Integer>();
////            List<Long> idList = new ArrayList<Long>();
//
//            tagDetalList2.add(Constant.SHOP_TAG.AREA);
//            tagDetalList2.add(Constant.SHOP_TAG.DAY_PRICE_LOW);
//            tagDetalList2.add(Constant.SHOP_TAG.NIGHT_OPEN_TIME);
//            tagDetalList2.add(Constant.SHOP_TAG.SCENE);
//            tagDetalList2.add(Constant.SHOP_TAG.CHARACTER);
//            tagDetalList2.add(Constant.SHOP_TAG.FIRST_MENU);
//            tagDetalList2.add(Constant.SHOP_TAG.SECOND_MENU);
//            tagDetalList2.add(Constant.SHOP_TAG.MASSAGE_TIME);
//            tagDetalList2.add(Constant.SHOP_TAG.BRAND);
//            tagDetalList2.add(Constant.SHOP_TAG.SEAT);
//            tagDetalList2.add(Constant.SHOP_TAG.ROOM_TYPE);
//            tagDetalList2.add(Constant.SHOP_TAG.ROOM_NUM);
//            tagDetalList2.add(Constant.SHOP_TAG.HEADCOUNT);
//
//            tagList2.add(Constant.SHOP_TAG.STATION);
//            tagList2.add(Constant.SHOP_TAG.FIRST_MENU);
//            tagList2.add(Constant.SHOP_TAG.DAY_PRICE_LOW);
//            tagList2.add(Constant.SHOP_TAG.DAY_PRICE_HIGH);
//            tagList2.add(Constant.SHOP_TAG.NIGHT_PRICE_LOW);
//            tagList2.add(Constant.SHOP_TAG.NIGHT_PRICE_HIGH);
//
//            List<TagDetail> tagDetailList = tagDetailService.getTagDetailList(shopType, tagDetalList2);
//            List<TagDetail> foodTagDetailList = tagDetailService.getTagDetailList(Constant.SHOP_TAG.FOOD, tagDetalList2);
//
//            for (TagDetail td : foodTagDetailList) {
//                if (td.getModuleType() == Constant.SHOP_TAG.FOOD && td.getTagType() == Constant.SHOP_TAG.AREA) {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("content", td.getContent());
//                    map.put("desc", td.getDesc());
//                    areaList.add(map);
//                }
//
//                if (td.getModuleType() == Constant.SHOP_TAG.FOOD && td.getTagType() == Constant.SHOP_TAG.DAY_PRICE_LOW) {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("content", td.getContent());
//                    map.put("desc", td.getDesc());
//                    budgetList.add(map);
//                }
//
//                if (td.getModuleType() == Constant.SHOP_TAG.FOOD && td.getTagType() == Constant.SHOP_TAG.NIGHT_OPEN_TIME) {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("content", td.getContent());
//                    map.put("desc", td.getDesc());
//                    closeTimeList.add(map);
//                }
//
//                if (td.getModuleType() == Constant.SHOP_TAG.FOOD && td.getTagType() == Constant.SHOP_TAG.SEAT) {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("content", td.getContent());
//                    map.put("desc", td.getDesc());
//                    seatList.add(map);
//                }
//            }
//
//            for (TagDetail td : tagDetailList) {
//                if (td.getModuleType() == shopType.byteValue()  && td.getTagType() == Constant.SHOP_TAG.SCENE) {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("content", td.getContent());
//                    map.put("desc", td.getDesc());
//                    sceneList.add(map);
//                }
//
//                if (td.getModuleType() == shopType.byteValue() && td.getTagType() == Constant.SHOP_TAG.CHARACTER) {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("content", td.getContent());
//                    map.put("desc", td.getDesc());
//                    detailSearchList.add(map);
//                }
//
//                if (td.getModuleType() == shopType.byteValue()&& td.getTagType() == Constant.SHOP_TAG.FIRST_MENU) {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("content", td.getContent());
//                    map.put("desc", td.getDesc());
//                    mainMenuList.add(map);
//                }
//
//                if (td.getModuleType() == shopType.byteValue() && td.getTagType() == Constant.SHOP_TAG.SECOND_MENU) {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("content", td.getContent());
//                    map.put("desc", td.getDesc());
//                    subMenuList.add(map);
//                }
//
//                if (td.getModuleType() == shopType.byteValue() && td.getTagType() == Constant.SHOP_TAG.BRAND) {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("content", td.getContent());
//                    map.put("desc", td.getDesc());
//                    brandList.add(map);
//                }
//
//                if (td.getModuleType() == shopType.byteValue() && td.getTagType() == Constant.SHOP_TAG.ROOM_NUM) {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("content", td.getContent());
//                    map.put("desc", td.getDesc());
//                    roomNumList.add(map);
//                }
//
//                if (td.getModuleType() == shopType.byteValue() && td.getTagType() == Constant.SHOP_TAG.ROOM_TYPE) {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("content", td.getContent());
//                    map.put("desc", td.getDesc());
//                    roomTypetList.add(map);
//                }
//
//                if (td.getModuleType() == shopType.byteValue() && td.getTagType() == Constant.SHOP_TAG.MASSAGE_TIME) {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("content", td.getContent());
//                    map.put("desc", td.getDesc());
//                    massageTimeList.add(map);
//                }
//
//                if (td.getModuleType() == shopType.byteValue() && td.getTagType() == Constant.SHOP_TAG.HEADCOUNT) {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("content", td.getContent());
//                    map.put("desc", td.getDesc());
//                    headCountList.add(map);
//                }
//            }
//
//            if (shopType == Constant.SHOP_TAG.ALL) {
//                List<ShopAllJsonParse> ejpList = new ArrayList<ShopAllJsonParse>();
//                ShopAllJsonParse aejp = new ShopAllJsonParse();
//
//                aejp.setContentList(contentList);
//                aejp.setShopCount(0);
//                aejp.setAreaList(areaList);
//                aejp.setBudgetList(budgetList);
//                aejp.setshopTimeList(closeTimeList);
//                ejpList.add(aejp);
//                jsonObject.setResultList(ejpList);
//
//            }
//            if (shopType == Constant.SHOP_TAG.FOOD) {
//                List<ShopFoodJsonParse> ejpList = new ArrayList<ShopFoodJsonParse>();
//                ShopFoodJsonParse fejp = new ShopFoodJsonParse();
//
//                fejp.setContentList(contentList);
//                fejp.setShopCount(0);
//                fejp.setAreaList(areaList);
//                fejp.setBudgetList(budgetList);
//                fejp.setshopTimeList(closeTimeList);
//                fejp.setSceneList(sceneList);
//                fejp.setDetailSearchList(detailSearchList);
//                fejp.setMainMenu(mainMenuList);
//                fejp.setArea(areaString);
//                ejpList.add(fejp);
//                jsonObject.setResultList(ejpList);
//
//            } else if (shopType == Constant.SHOP_TAG.FASTION) {
//                List<ShopFastionJsonParse> ejpList = new ArrayList<ShopFastionJsonParse>();
//                ShopFastionJsonParse sejp = new ShopFastionJsonParse();
//
//                sejp.setContentList(contentList);
//                sejp.setShopCount(0);
//                sejp.setAreaList(areaList);
//                sejp.setBudgetList(budgetList);
//                sejp.setshopTimeList(closeTimeList);
//                sejp.setSceneList(sceneList);
//                sejp.setDetailSearchList(detailSearchList);
//                sejp.setMainMenu(mainMenuList);
//                sejp.setArea(areaString);
//                ejpList.add(sejp);
//                jsonObject.setResultList(ejpList);
//
//            } else if (shopType == Constant.SHOP_TAG.HEALTH) {
//                List<ShopHealthJsonParse> ejpList = new ArrayList<ShopHealthJsonParse>();
//                ShopHealthJsonParse hejp = new ShopHealthJsonParse();
//
//                hejp.setContentList(contentList);
//                hejp.setShopCount(0);
//                hejp.setAreaList(areaList);
//                hejp.setBudgetList(budgetList);
//                hejp.setShopTimeList(closeTimeList);
//                hejp.setBrandList(brandList);
//                hejp.setSeatList(seatList);
//                hejp.setMassageTimeList(massageTimeList);
//                hejp.setDetailSearchList(detailSearchList);
//                hejp.setMainMenu(mainMenuList);
//                hejp.setSubMenu(subMenuList);
//                ejp.setArea(areaString);
//                ejpList.add(hejp);
//                jsonObject.setResultList(ejpList);
//
//
//            } else if (shopType == Constant.SHOP_TAG.PLAY) {
//                List<ShopPlayJsonParse> ejpList = new ArrayList<ShopPlayJsonParse>();
//                ShopPlayJsonParse pejp = new ShopPlayJsonParse();
//
//                pejp.setContentList(contentList);
//                pejp.setShopCount(0);
//                pejp.setAreaList(areaList);
//                pejp.setBudgetList(budgetList);
//                pejp.setShopTimeList(closeTimeList);
//                pejp.setSceneList(sceneList);
//                pejp.setDetailSearchList(detailSearchList);
//                pejp.setMainMenu(mainMenuList);
//                ejp.setArea(areaString);
//                ejpList.add(pejp);
//                jsonObject.setResultList(ejpList);
//
//            } else if (shopType == Constant.SHOP_TAG.BRIDAL) {
//                List<ShopBridalJsonParse> ejpList = new ArrayList<ShopBridalJsonParse>();
//                ShopBridalJsonParse bejp = new ShopBridalJsonParse();
//
//                bejp.setContentList(contentList);
//                bejp.setShopCount(0);
//                bejp.setAreaList(areaList);
//                bejp.setBudgetList(budgetList);
//                bejp.setShopTimeList(closeTimeList);
//                bejp.setDetailSearchList(detailSearchList);
//                bejp.setHeadCountList(headCountList);
//                bejp.setMainMenu(mainMenuList);
//                bejp.setArea(areaString);
//                ejpList.add(bejp);
//                jsonObject.setResultList(ejpList);
//
//            } else if (shopType == Constant.SHOP_TAG.DRIVE) {
//                List<ShopDriveJsonParse> ejpList = new ArrayList<ShopDriveJsonParse>();
//                ShopDriveJsonParse dejp = new ShopDriveJsonParse();
//
//                dejp.setContentList(contentList);
//                dejp.setShopCount(0);
//                dejp.setAreaList(areaList);
//                dejp.setBudgetList(budgetList);
//                dejp.setShopTimeList(closeTimeList);
//                dejp.setSceneList(sceneList);
//                dejp.setDetailSearchList(detailSearchList);
//                dejp.setMainMenu(mainMenuList);
//                dejp.setArea(areaString);
//                ejpList.add(dejp);
//                jsonObject.setResultList(ejpList);
//
//
//            } else if (shopType == Constant.SHOP_TAG.LEARNING) {
//                List<ShopLearnJsonParse> ejpList = new ArrayList<ShopLearnJsonParse>();
//                ShopLearnJsonParse lejp = new ShopLearnJsonParse();
//
//                lejp.setContentList(contentList);
//                lejp.setShopCount(0);
//                lejp.setAreaList(areaList);
//                lejp.setBudgetList(budgetList);
//                lejp.setShopTimeList(closeTimeList);
//                lejp.setDetailSearchList(detailSearchList);
//                lejp.setSceneList(sceneList);
//                lejp.setMainMenu(mainMenuList);
//                lejp.setSubMenu(subMenuList);
//                lejp.setArea(areaString);
//                ejpList.add(lejp);
//                jsonObject.setResultList(ejpList);
//
//            } else if (shopType == Constant.SHOP_TAG.FACILITY) {
//                List<ShopFacilityJsonParse> ejpList = new ArrayList<ShopFacilityJsonParse>();
//                ShopFacilityJsonParse cejp = new ShopFacilityJsonParse();
//
//                cejp.setContentList(contentList);
//                cejp.setShopCount(0);
//                cejp.setAreaList(areaList);
//                cejp.setBudgetList(budgetList);
//                cejp.setShopTimeList(closeTimeList);
//                cejp.setDetailSearchList(detailSearchList);
//                cejp.setHeadCountList(headCountList);
//                cejp.setRoomTypeList(roomTypetList);
//                cejp.setRoomNumList(roomNumList);
//                cejp.setMainMenu(mainMenuList);
//                cejp.setSubMenu(subMenuList);
//                cejp.setArea(areaString);
//                ejpList.add(cejp);
//                jsonObject.setResultList(ejpList);
//
//            } else if (shopType == Constant.SHOP_TAG.LIFE) {
//                List<ShopLifeJsonParse> ejpList = new ArrayList<ShopLifeJsonParse>();
//                ShopLifeJsonParse iejp = new ShopLifeJsonParse();
//
//                iejp.setContentList(contentList);
//                iejp.setShopCount(0);
//                iejp.setAreaList(areaList);
//                iejp.setBudgetList(budgetList);
//                iejp.setShopTimeList(closeTimeList);
//                iejp.setDetailSearchList(detailSearchList);
//                iejp.setMainMenu(mainMenuList);
//                iejp.setSubMenu(subMenuList);
//                iejp.setArea(areaString);
//                ejpList.add(iejp);
//                jsonObject.setResultList(ejpList);
//            }
            ejp.setArea(areaString);
            jsonObject.setResultList(ejp);
        }
        return jsonObject;
    }

    @RequestMapping(value = "/api/shopcoupon/list/{type}/{limit}/{offset}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getCouponList(@PathVariable int type, @PathVariable int limit, @PathVariable int offset,HttpServletRequest hsr) throws APIException {

        long userId = 0L;
        try {
            userId = authService.getUserId(hsr);
        }catch (APIException ex){}
        String datenow = DateUtil.dateToStringyyyy_MM_dd(new Date());
        String datenow2 = DateUtil.dateToStringyyyy_MM_dd_hhmmss(new Date());
        Date now = DateUtil.stringToDateyyyy_MM_dd_HH_mm_ss(datenow2);
        Date date = DateUtil.stringToDateyyyy_MM_dd(datenow);

        List<Long> idList2 = new ArrayList<Long>();
        List<Long> idList3 = new ArrayList<Long>();
        List<Shop> list = new ArrayList<Shop>();
        List<Shop> list2 = new ArrayList<Shop>();
        List<ShopCoupon> shopCoupons = shopCouponService.getShopCoupon2(type);
        if(shopCoupons.size()>0){
            for(ShopCoupon sc:shopCoupons){
                idList3.add(sc.getShopId());
            }
            for (Long cd : idList3) {
                if (!idList2.contains(cd)) {
                    idList2.add(cd);
                }
            }
        }
        if(idList2.size()>0){
            list = shopService.getShopByCoupon2(idList2,type, limit, offset);
            list2 = shopService.getShopByCoupon3(idList2,type);
        }
//        List<Shop> list = shopService.getShopByCoupon(type, limit, offset);
//        List<String> randomOneUuidList = new ArrayList<String>();
//        List<String> randomTwoUuidList = new ArrayList<String>();
//        List<Shop> list = new ArrayList<Shop>();
//        int minute = DateUtil.getMinute(new Date(System.currentTimeMillis()));
//
//        if(minute>5 && minute <55){
//            List<RandomShopOne> randomOnelist = randomShopService.getShopRandomOneForNow4(type, limit, offset);
//            if(randomOnelist.size()>0){
//                for(RandomShopOne rso:randomOnelist){
//                    randomOneUuidList.add(rso.getUuid());
//                }
//            }
//
//            if(randomOneUuidList.size()>0){
//                list = shopService.getShopByUuidList(randomOneUuidList);
//            }
//        }else{
//            List<RandomShopTwo> randomTwolist = randomShopService.getShopRandomTwoForNow4(type, limit, offset);
//            if(randomTwolist.size()>0){
//                for(RandomShopTwo rso:randomTwolist){
//                    randomTwoUuidList.add(rso.getUuid());
//                }
//            }
//
//            if(randomTwoUuidList.size()>0){
//                list = shopService.getShopByUuidList(randomTwoUuidList);
//            }
//        }

//        List<Shop> list2 = shopService.getShopAllByCoupon(type);
        List<Long> idList = new ArrayList<Long>();
        List<ShopCouponJsonParse> ejpList = new ArrayList<ShopCouponJsonParse>();
        List<Map<String, Object>> contentList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> couponList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> nowGoList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> foodMainMenuList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> fastionMainMenuList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> healthMainMenuList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> playMainMenuList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> bridalMainMenuList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> driveMainMenuList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> infoMainMenuList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> learnMainMenuList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> facilityMainMenuList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> lifeMainMenuList = new ArrayList<Map<String, Object>>();

        ShopCouponJsonParse scjp = new ShopCouponJsonParse();
        int shopCount = list2.size();

        List<TagDetail> TagDetailListByTagType = tagDetailService.getTagDetailBytagType(Constant.SHOP_TAG.FIRST_MENU);
        for (TagDetail td : TagDetailListByTagType) {
            if (td.getModuleType() == Constant.SHOP_TAG.FOOD && td.getTagType() == Constant.SHOP_TAG.FIRST_MENU) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", td.getContent());
                map.put("desc", td.getDesc());
                foodMainMenuList.add(map);
            }
        }

        for (TagDetail td : TagDetailListByTagType) {
            if (td.getModuleType() == Constant.SHOP_TAG.FASTION && td.getTagType() == Constant.SHOP_TAG.FIRST_MENU) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", td.getContent());
                map.put("desc", td.getDesc());
                fastionMainMenuList.add(map);
            }
        }


        for (TagDetail td : TagDetailListByTagType) {
            if (td.getModuleType() == Constant.SHOP_TAG.HEALTH && td.getTagType() == Constant.SHOP_TAG.FIRST_MENU) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", td.getContent());
                map.put("desc", td.getDesc());
                healthMainMenuList.add(map);
            }
        }


        for (TagDetail td : TagDetailListByTagType) {
            if (td.getModuleType() == Constant.SHOP_TAG.PLAY && td.getTagType() == Constant.SHOP_TAG.FIRST_MENU) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", td.getContent());
                map.put("desc", td.getDesc());
                playMainMenuList.add(map);
            }
        }


        for (TagDetail td : TagDetailListByTagType) {
            if (td.getModuleType() == Constant.SHOP_TAG.BRIDAL && td.getTagType() == Constant.SHOP_TAG.FIRST_MENU) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", td.getContent());
                map.put("desc", td.getDesc());
                bridalMainMenuList.add(map);
            }
        }


        for (TagDetail td : TagDetailListByTagType) {
            if (td.getModuleType() == Constant.SHOP_TAG.DRIVE && td.getTagType() == Constant.SHOP_TAG.FIRST_MENU) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", td.getContent());
                map.put("desc", td.getDesc());
                driveMainMenuList.add(map);
            }
        }


        for (TagDetail td : TagDetailListByTagType) {
            if (td.getModuleType() == Constant.SHOP_TAG.CORPORATE_INFO && td.getTagType() == Constant.SHOP_TAG.FIRST_MENU) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", td.getContent());
                map.put("desc", td.getDesc());
                infoMainMenuList.add(map);
            }
        }


        for (TagDetail td : TagDetailListByTagType) {
            if (td.getModuleType() == Constant.SHOP_TAG.LEARNING && td.getTagType() == Constant.SHOP_TAG.FIRST_MENU) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", td.getContent());
                map.put("desc", td.getDesc());
                learnMainMenuList.add(map);
            }
        }

        for (TagDetail td : TagDetailListByTagType) {
            if (td.getModuleType() == Constant.SHOP_TAG.FACILITY && td.getTagType() == Constant.SHOP_TAG.FIRST_MENU) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", td.getContent());
                map.put("desc", td.getDesc());
                facilityMainMenuList.add(map);
            }
        }

        for (TagDetail td : TagDetailListByTagType) {
            if (td.getModuleType() == Constant.SHOP_TAG.LIFE && td.getTagType() == Constant.SHOP_TAG.FIRST_MENU) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", td.getContent());
                map.put("desc", td.getDesc());
                lifeMainMenuList.add(map);
            }
        }


//        By wangyu ↓

        if(list.size()>0){
            for (Shop ed : list) {
                idList.add(ed.getId());
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("uuid", ed.getUuid());
                map.put("type", ed.getShopType());
                map.put("name", ed.getName());
                map.put("address", ed.getAddress());
//                map.put("excerpt", ed.getExcerpt());
                map.put("picUrl", ed.getPicUrl());
                map.put("coordinate1", Double.parseDouble(ed.getCoordinate1()));
                map.put("coordinate2", Double.parseDouble(ed.getCoordinate2()));

                ShopCoupon shopCoupon = shopCouponService.getCouponByShopId(ed.getId());
                if(shopCoupon!= null){
                    String now4time ="";
                    Date couponUpdateTime = shopCoupon.getUpdateDatetime();
                    Date couponPublistStartTime = shopCoupon.getPublishStart();
                    if(couponUpdateTime.getTime()>=couponPublistStartTime.getTime()){
                        now4time = DateUtil.timeDifference(couponUpdateTime);
                    }else {
                        now4time = DateUtil.timeDifference(couponPublistStartTime);
                    }
                    map.put("time", now4time);
                    map.put("excerpt", shopCoupon.getDesc());
                }
                contentList.add(map);
            }
        }

        List<Long> favIdList = new ArrayList<Long>();
        List<Shop> getFavShopList = new ArrayList<Shop>();
        List<FavDetail> getFavShop = new ArrayList<FavDetail>();
        List<ShopCoupon> cpList = new ArrayList<ShopCoupon>();
        List<ShopNowgo> snList = new ArrayList<ShopNowgo>();
        if(userId>0){
            getFavShop = favDetailService.getFavByUserIdAndType(userId,Constant.FAV_TYPE.SHOP);
        }
        if(getFavShop.size()>0){
            for(FavDetail s: getFavShop){
                favIdList.add(s.getShopId());
            }
        }
        if(favIdList.size()>0){
            getFavShopList = shopService.getShopByIdList(favIdList);
            cpList = shopCouponService.getShopCouponList(favIdList);
            snList = shopNowgoService.getShopNowgoList(favIdList);
        }

        if (cpList.size() > 0 && getFavShopList.size() > 0) {
            for (ShopCoupon sc : cpList) {
                for (Shop sd : getFavShopList) {
                    String datenow3 = DateUtil.dateToStringYyyy_MM_dd_hhmm(new Date());
                    Date date2 = DateUtil.stringToDateyyyy_MM_dd_HH_mm(datenow3);

                    String dateEnd = DateUtil.dateToStringYyyy_MM_dd_hhmm(sc.getPublishEnd());
                    Date dateEnd2 = DateUtil.stringToDateyyyy_MM_dd_HH_mm(dateEnd);
                    String dateF = DateUtil.dateToStringYyyy_MM_dd_hhmm(sc.getPublishStart());
                    Date datef = DateUtil.stringToDateyyyy_MM_dd_HH_mm(dateF);
                    if (datef.getTime() <= date2.getTime()) {
                        if (date2.getTime() <= dateEnd2.getTime()) {
                            if (sc.getShopId().equals(sd.getId())) {
                                Map<String, Object> couponMap = new HashMap<String, Object>();
                                String now4time ="";
                                Date couponUpdateTime = sc.getUpdateDatetime();
                                Date couponPublistStartTime = sc.getPublishStart();
                                if(couponUpdateTime.getTime()>=couponPublistStartTime.getTime()){
                                    now4time = DateUtil.timeDifference(couponUpdateTime);
                                }else {
                                    now4time = DateUtil.timeDifference(couponPublistStartTime);
                                }
                                NotifyNow45 notifyNow45 = notificationService.getNotifyNow45ByUserIdAndItemId(userId , sc.getId(), Constant.NOTIFY_NOW45.now4);
                                if(notifyNow45 != null){
                                    couponMap.put("status", notifyNow45.getNotifyStatus());
                                }else {
                                    couponMap.put("status", 0);
                                }
                                couponMap.put("uuid", sd.getUuid());
                                couponMap.put("name", sd.getName());
                                couponMap.put("title", sc.getTitle());
                                couponMap.put("picUrl", sc.getPicUrl1());
                                couponMap.put("desc", sc.getDesc());
                                couponMap.put("time", now4time);
                                couponMap.put("itemId", sc.getId());
                                couponMap.put("shopType",sd.getShopType());
                                couponList.add(couponMap);
                            }
                        }
                    }
                }
            }
        }

       if(snList.size()>0 && getFavShopList.size()>0){
           for (ShopNowgo sn : snList) {
               for (Shop sd : getFavShopList) {
                   String dateEnd = DateUtil.dateToStringyyyy_MM_dd(new Date());
                   Date dateEnd2 = sn.getPublishEnd();
                   String dateup = DateUtil.dateToStringyyyy_MM_dd(sn.getUpdateDatetime());
                   if (datenow.equals(dateup)) {
                       if (now.before(dateEnd2)) {
                           long nowgoId = sn.getShopId();
                           long shopDetailId = sd.getId();
                           if (nowgoId == shopDetailId) {
                               Map<String, Object> nowGoMap = new HashMap<String, Object>();
                               Date nowGoTime = sn.getUpdateDatetime();
                               String now5time = DateUtil.timeDifference(nowGoTime);
                               NotifyNow45 notifyNow45 = notificationService.getNotifyNow45ByUserIdAndItemId(userId ,sn.getId() , Constant.NOTIFY_NOW45.now4);
                               if(notifyNow45 != null){
                                   nowGoMap.put("status", notifyNow45.getNotifyStatus());
                               }else {
                                   nowGoMap.put("status", 0);
                               }
                               nowGoMap.put("uuid", sd.getUuid());
                               nowGoMap.put("address", sd.getAddress());
                               nowGoMap.put("name", sd.getName());
                               nowGoMap.put("now5", sn.getDesc());
                               nowGoMap.put("time", now5time);
                               nowGoMap.put("itemId", sn.getId());
                               nowGoMap.put("shopType",sd.getShopType());
                               nowGoList.add(nowGoMap);
                           }
                       }
                   }
               }
           }
       }

        List<Map<String, Object>> areaList = new ArrayList<Map<String, Object>>();
        List<TagDetail> tagDetailList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.AREA);
        for (TagDetail td : tagDetailList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("content", td.getContent());
            map.put("desc", td.getDesc());
            areaList.add(map);
        }
//      By wangyu ↑

        scjp.setAreaList(areaList);
        scjp.setContentList(contentList);
        scjp.setFoodMainMenuList(foodMainMenuList);
        scjp.setFastionMainMenuList(fastionMainMenuList);
        scjp.setHealthMainMenuList(healthMainMenuList);
        scjp.setPlayMainMenuList(playMainMenuList);
        scjp.setBridalMainMenuList(bridalMainMenuList);
        scjp.setDriveMainMenuList(driveMainMenuList);
        scjp.setInfoMainMenuList(infoMainMenuList);
        scjp.setLearnMainMenuList(learnMainMenuList);
        scjp.setFacilityMainMenuList(facilityMainMenuList);
        scjp.setLifeMainMenuList(lifeMainMenuList);
        scjp.setCouponList(couponList);
        scjp.setNowGoList(nowGoList);
        scjp.setShopCount(shopCount);
        ejpList.add(scjp);
        jsonObject.setResultList(ejpList);
        return jsonObject;
    }

    //shopSearch
        @RequestMapping(value = "/api/shopcoupon/search/", method = RequestMethod.POST)
    @ResponseBody
    public JsonObject searchCoupon(@ModelAttribute ShopListParams params) {

        String areaString = "";
        List<Long> shopIdList = new ArrayList<Long>();
        List<Map<String, Object>> contentList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> couponList = new ArrayList<Map<String, Object>>();
        ShopSearchJsonParse ejp = new ShopSearchJsonParse();

        List<Integer> areas = params.getArea();
        String Keyword = params.getKeyWord();
        Integer shopType = params.getShopType();
        Integer mainMenu = params.getMainMenu();
        Integer dayPriceLow = params.getDayPriceLow();
        Integer nightPricehigh = params.getNightPriceHigh();

        List<Long> getIdByShopType = new ArrayList<Long>();
        List<Long> getIdByArea = new ArrayList<Long>();
        List<Long> getIdByMainMenu = new ArrayList<Long>();
        List<Long> getIdByDayPriceLow = new ArrayList<Long>();
        List<Long> getIdByNightPricehigh = new ArrayList<Long>();
        List<Long> getIdByKeyword = new ArrayList<Long>();

        List<Long> idList2 = new ArrayList<Long>();
        List<Long> idList3 = new ArrayList<Long>();
        List<ShopCoupon> shopCoupons = shopCouponService.getShopCoupon2(params.getShopType());
        if(shopCoupons.size()>0){
            for(ShopCoupon sc:shopCoupons){
                idList3.add(sc.getShopId());
            }
            for (Long cd : idList3) {
                if (!idList2.contains(cd)) {
                    idList2.add(cd);
                }
            }
        }

        if (shopType != null) {
            getIdByShopType = shopService.getShopIdByNow4(shopType, 1);
        }
        if (areas != null && areas.size() > 0 && areas.get(0) != 0) {
            getIdByArea = shopTagService.getShopIdByContents(shopType, Constant.SHOP_TAG.AREA, areas);
        }
        if (mainMenu != null && mainMenu != 0) {
            getIdByMainMenu = shopTagService.getShopIdByContent(shopType, Constant.SHOP_TAG.FIRST_MENU, mainMenu);
        }
        if (dayPriceLow != null && dayPriceLow != 0) {
            getIdByDayPriceLow = shopTagService.getShopIdByContent(shopType, Constant.SHOP_TAG.DAY_PRICE_LOW, dayPriceLow);
        }
        if (nightPricehigh != null && nightPricehigh != 0) {
            getIdByNightPricehigh = shopTagService.getShopIdByContent(shopType, Constant.SHOP_TAG.NIGHT_PRICE_HIGH, nightPricehigh);
        }
        if (Keyword != null) {
            getIdByKeyword = shopService.getShopIdByKeyword(shopType, Keyword);
        }

        if (areas != null && areas.size() > 0 && areas.get(0) != 0 && getIdByArea != null) {
            idList2.retainAll(getIdByArea);
        }
        if (mainMenu != null && mainMenu != 0 && getIdByMainMenu != null && getIdByMainMenu.size() >= 0) {
            idList2.retainAll(getIdByMainMenu);
        }
        if (dayPriceLow != null && dayPriceLow != 0 && getIdByDayPriceLow != null && getIdByDayPriceLow.size() >= 0) {
            idList2.retainAll(getIdByDayPriceLow);
        }
        if (nightPricehigh != null && nightPricehigh != 0 && getIdByNightPricehigh != null && getIdByNightPricehigh.size() >= 0) {
            idList2.retainAll(getIdByNightPricehigh);
        }
        if (Keyword != null && getIdByKeyword != null && getIdByKeyword.size() >= 0) {
            idList2.retainAll(getIdByKeyword);
        }

        for (Long cd : idList2) {
            if (!shopIdList.contains(cd)) {
                shopIdList.add(cd);
            }
        }

        if (shopIdList == null) {

        } else if (shopIdList.size() > 0) {

            params.setIdList(shopIdList);

            List<Shop> list = shopService.getShopList(params);
            List<Shop> count = shopService.getShopListCount(params);
            if (areas == null) {

            } else if (areas.size() > 0) {
                for (int i = 0; i < areas.size(); i++) {
                    String area = tagDetailService.getDesc(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.AREA, Integer.parseInt(String.valueOf(areas.get(i))));
                    if (i == 0) {
                        areaString = area;
                    } else {
                        areaString += "," + area;
                    }
                }
            }
            int shopCount = count.size();
            for (Shop ed : list) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("uuid", ed.getUuid());
                map.put("type", ed.getShopType());
                map.put("name", ed.getName());
                map.put("address", ed.getAddress());
                map.put("picUrl", ed.getPicUrl());
                map.put("coordinate1", Double.parseDouble(ed.getCoordinate1()));
                map.put("coordinate2", Double.parseDouble(ed.getCoordinate2()));

                ShopCoupon shopCoupon = shopCouponService.getCouponByShopId(ed.getId());
                if(shopCoupon!= null){
                    String now4time ="";
                    Date couponUpdateTime = shopCoupon.getUpdateDatetime();
                    Date couponPublistStartTime = shopCoupon.getPublishStart();
                    if(couponUpdateTime.getTime()>=couponPublistStartTime.getTime()){
                        now4time = DateUtil.timeDifference(couponUpdateTime);
                    }else {
                        now4time = DateUtil.timeDifference(couponPublistStartTime);
                    }
                    map.put("time", now4time);
                    map.put("excerpt", shopCoupon.getDesc());
                }
                contentList.add(map);
            }

            List<ShopCoupon> cpList = new ArrayList<ShopCoupon>();
            List<ShopDetail> detailList = new ArrayList<ShopDetail>();

            if (shopIdList != null && shopIdList.size() > 0) {
                cpList = shopCouponService.getShopCouponList(shopIdList);
            }

            if (cpList.size() > 0) {
                for (ShopCoupon sc : cpList) {
                    for (Shop sd : list) {
                        long id1 = sc.getShopId();
                        long id2 = sd.getId();
                        if (id1 == id2) {
                            Map<String, Object> couponMap = new HashMap<String, Object>();
                            Date couponTime = sc.getPublishStart();
                            String now4time = DateUtil.timeDifference(couponTime);
                            couponMap.put("uuid", sd.getUuid());
                            couponMap.put("address", sd.getAddress());
                            couponMap.put("name", sd.getName());
                            couponMap.put("title", sc.getTitle());
                            couponMap.put("picUrl", sc.getPicUrl1());
                            couponMap.put("desc", sc.getDesc());
                            couponMap.put("time", now4time);
                            couponMap.put("itemId", sc.getId());
                            couponMap.put("shopType",sd.getShopType());
                            couponList.add(couponMap);
                        }
                    }
                }
            }
            ejp.setShopCount(shopCount);
            ejp.setArea(areaString);
            ejp.setContentList(contentList);
            ejp.setCouponList(couponList);
            jsonObject.setResultList(ejp);
        } else {
            jsonObject.setResultList(0);
        }
        return jsonObject;
    }

    @RequestMapping(value = "/api/shopnowgo/list/{type}/{limit}/{offset}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getNowGoList(@PathVariable int type, @PathVariable int limit, @PathVariable int offset,HttpServletRequest hsr) throws APIException {
        long userId = 0L;
        try {
            userId = authService.getUserId(hsr);
        }catch (APIException ex){}
        String datenow = DateUtil.dateToStringyyyy_MM_dd(new Date());
        String datenow2 = DateUtil.dateToStringyyyy_MM_dd_hhmmss(new Date());
        Date now = DateUtil.stringToDateyyyy_MM_dd_HH_mm_ss(datenow2);
        Date date = DateUtil.stringToDateyyyy_MM_dd(datenow);
        List<String> randomOneUuidList = new ArrayList<String>();
        List<String> randomTwoUuidList = new ArrayList<String>();
        List<Shop> list = new ArrayList<Shop>();
        int minute = DateUtil.getMinute(new Date(System.currentTimeMillis()));

        if(minute>5 && minute <55){
            List<RandomShopOne> randomOnelist = randomShopService.getShopRandomOneForNow5(type, limit, offset);
            if(randomOnelist.size()>0){
                for(RandomShopOne rso:randomOnelist){
                    randomOneUuidList.add(rso.getUuid());
                }
            }

            if(randomOneUuidList.size()>0){
                list = shopService.getShopByUuidList(randomOneUuidList);
            }
        }else{
            List<RandomShopTwo> randomTwolist = randomShopService.getShopRandomTwoForNow5(type, limit, offset);
            if(randomTwolist.size()>0){
                for(RandomShopTwo rso:randomTwolist){
                    randomTwoUuidList.add(rso.getUuid());
                }
            }

            if(randomTwoUuidList.size()>0){
                list = shopService.getShopByUuidList(randomTwoUuidList);
            }
        }


        List<Shop> list2 = shopService.getShopByNow5All(type);
        ShopCouponJsonParse scjp = new ShopCouponJsonParse();
        List<ShopCouponJsonParse> ejpList = new ArrayList<ShopCouponJsonParse>();
        List<Integer> tagDetalList = new ArrayList<Integer>();
        List<Map<String, Object>> contentList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> couponList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> nowGoList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> mainMenuList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> foodMainMenuList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> fastionMainMenuList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> healthMainMenuList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> playMainMenuList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> bridalMainMenuList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> driveMainMenuList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> infoMainMenuList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> learnMainMenuList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> facilityMainMenuList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> lifeMainMenuList = new ArrayList<Map<String, Object>>();

        int shopCount = list2.size();

        List<TagDetail> TagDetailListByTagType = tagDetailService.getTagDetailBytagType(Constant.SHOP_TAG.FIRST_MENU);
        for (TagDetail td : TagDetailListByTagType) {
            if (td.getModuleType() == Constant.SHOP_TAG.FOOD && td.getTagType() == Constant.SHOP_TAG.FIRST_MENU) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", td.getContent());
                map.put("desc", td.getDesc());
                foodMainMenuList.add(map);
            }
        }

        for (TagDetail td : TagDetailListByTagType) {
            if (td.getModuleType() == Constant.SHOP_TAG.FASTION && td.getTagType() == Constant.SHOP_TAG.FIRST_MENU) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", td.getContent());
                map.put("desc", td.getDesc());
                fastionMainMenuList.add(map);
            }
        }


        for (TagDetail td : TagDetailListByTagType) {
            if (td.getModuleType() == Constant.SHOP_TAG.HEALTH && td.getTagType() == Constant.SHOP_TAG.FIRST_MENU) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", td.getContent());
                map.put("desc", td.getDesc());
                healthMainMenuList.add(map);
            }
        }


        for (TagDetail td : TagDetailListByTagType) {
            if (td.getModuleType() == Constant.SHOP_TAG.PLAY && td.getTagType() == Constant.SHOP_TAG.FIRST_MENU) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", td.getContent());
                map.put("desc", td.getDesc());
                playMainMenuList.add(map);
            }
        }


        for (TagDetail td : TagDetailListByTagType) {
            if (td.getModuleType() == Constant.SHOP_TAG.BRIDAL && td.getTagType() == Constant.SHOP_TAG.FIRST_MENU) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", td.getContent());
                map.put("desc", td.getDesc());
                bridalMainMenuList.add(map);
            }
        }


        for (TagDetail td : TagDetailListByTagType) {
            if (td.getModuleType() == Constant.SHOP_TAG.DRIVE && td.getTagType() == Constant.SHOP_TAG.FIRST_MENU) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", td.getContent());
                map.put("desc", td.getDesc());
                driveMainMenuList.add(map);
            }
        }


        for (TagDetail td : TagDetailListByTagType) {
            if (td.getModuleType() == Constant.SHOP_TAG.CORPORATE_INFO && td.getTagType() == Constant.SHOP_TAG.FIRST_MENU) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", td.getContent());
                map.put("desc", td.getDesc());
                infoMainMenuList.add(map);
            }
        }


        for (TagDetail td : TagDetailListByTagType) {
            if (td.getModuleType() == Constant.SHOP_TAG.LEARNING && td.getTagType() == Constant.SHOP_TAG.FIRST_MENU) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", td.getContent());
                map.put("desc", td.getDesc());
                learnMainMenuList.add(map);
            }
        }

        for (TagDetail td : TagDetailListByTagType) {
            if (td.getModuleType() == Constant.SHOP_TAG.FACILITY && td.getTagType() == Constant.SHOP_TAG.FIRST_MENU) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", td.getContent());
                map.put("desc", td.getDesc());
                facilityMainMenuList.add(map);
            }
        }

        for (TagDetail td : TagDetailListByTagType) {
            if (td.getModuleType() == Constant.SHOP_TAG.LIFE && td.getTagType() == Constant.SHOP_TAG.FIRST_MENU) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", td.getContent());
                map.put("desc", td.getDesc());
                lifeMainMenuList.add(map);
            }
        }

//        By wangyu ↓
        List<Long> idList = new ArrayList<Long>();
        List<ShopDetail> detailList = new ArrayList<ShopDetail>();
        List<ShopCoupon> cpList = new ArrayList<ShopCoupon>();

        if(list.size()>0){
            Collections.shuffle(list);
            for (Shop ed : list) {
                idList.add(ed.getId());
            }
            if (idList != null && idList.size() > 0) {
                detailList = shopDetailService.getShopDetailByShopIdList(idList);
            }
            for (Shop ed : list) {
                for (ShopDetail sde : detailList) {
                    long id1 = ed.getId();
                    long id2 = sde.getShopListId();
                    if (id1 == id2) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        String content = sde.getNow5();
                        map.put("uuid", ed.getUuid());
                        map.put("type", ed.getShopType());
                        map.put("name", ed.getName());
                        map.put("address", ed.getAddress());
//                        map.put("excerpt", ed.getExcerpt());
                        map.put("picUrl", ed.getPicUrl());
                        map.put("now5", content);
                        map.put("coordinate1", Double.parseDouble(ed.getCoordinate1()));
                        map.put("coordinate2", Double.parseDouble(ed.getCoordinate2()));

                        ShopNowgo shopNowgo = shopNowgoService.getShopNowgoByShopId(ed.getId());
                        if(shopNowgo!= null){
                            Date nowGoTime = shopNowgo.getUpdateDatetime();
                            String now5time = DateUtil.timeDifference(nowGoTime);
                            map.put("time", now5time);
                            map.put("excerpt", shopNowgo.getDesc());
                        }
                        contentList.add(map);
                    }
                }
            }
        }

        List<Long> favIdList = new ArrayList<Long>();
        List<Shop> getFavShopList = new ArrayList<Shop>();
        List<FavDetail> getFavShop = new ArrayList<FavDetail>();
        List<ShopCoupon> coList = new ArrayList<ShopCoupon>();
        List<ShopNowgo> snList = new ArrayList<ShopNowgo>();
        if(userId >0){
             getFavShop = favDetailService.getFavByUserIdAndType(userId,Constant.FAV_TYPE.SHOP);
        }

        if(getFavShop.size()>0){
            for(FavDetail s: getFavShop){
                favIdList.add(s.getShopId());
            }
        }
        if(favIdList.size()>0){
            getFavShopList = shopService.getShopByIdList(favIdList);
            coList = shopCouponService.getShopCouponList(favIdList);
            snList = shopNowgoService.getShopNowgoList(favIdList);
        }
        if (coList.size() > 0 && getFavShopList.size() > 0) {
            for (ShopCoupon sc : coList) {
                for (Shop sd : getFavShopList) {
                    String datenow3 = DateUtil.dateToStringYyyy_MM_dd_hhmm(new Date());
                    Date date2 = DateUtil.stringToDateyyyy_MM_dd_HH_mm(datenow3);

                    String dateEnd = DateUtil.dateToStringYyyy_MM_dd_hhmm(sc.getPublishEnd());
                    Date dateEnd2 = DateUtil.stringToDateyyyy_MM_dd_HH_mm(dateEnd);
                    String dateF = DateUtil.dateToStringYyyy_MM_dd_hhmm(sc.getPublishStart());
                    Date datef = DateUtil.stringToDateyyyy_MM_dd_HH_mm(dateF);
                    if (datef.getTime() <= date2.getTime()) {
                        if (date2.getTime() <= dateEnd2.getTime()) {
                            if (sc.getShopId().equals(sd.getId())) {
                                Map<String, Object> couponMap = new HashMap<String, Object>();
                                String now4time ="";
                                Date couponUpdateTime = sc.getUpdateDatetime();
                                Date couponPublistStartTime = sc.getPublishStart();
                                if(couponUpdateTime.getTime()>=couponPublistStartTime.getTime()){
                                    now4time = DateUtil.timeDifference(couponUpdateTime);
                                }else {
                                    now4time = DateUtil.timeDifference(couponPublistStartTime);
                                }
                                NotifyNow45 notifyNow45 = notificationService.getNotifyNow45ByUserIdAndItemId(userId ,sc.getId(), Constant.NOTIFY_NOW45.now4);
                                if(notifyNow45 != null){
                                    couponMap.put("status", notifyNow45.getNotifyStatus());
                                }else {
                                    couponMap.put("status", 0);
                                }
                                couponMap.put("uuid", sd.getUuid());
                                couponMap.put("name", sd.getName());
                                couponMap.put("title", sc.getTitle());
                                couponMap.put("picUrl", sc.getPicUrl1());
                                couponMap.put("desc", sc.getDesc());
                                couponMap.put("time", now4time);
                                couponMap.put("itemId", sc.getId());
                                couponMap.put("shopType",sd.getShopType());
                                couponList.add(couponMap);
                            }
                        }
                    }
                }
            }
        }

        if(snList.size()>0 && getFavShopList.size()>0){
            for (ShopNowgo sn : snList) {
                for (Shop sd : getFavShopList) {
                    String dateEnd = DateUtil.dateToStringyyyy_MM_dd(new Date());
                    Date dateEnd2 = sn.getPublishEnd();
                    String dateup = DateUtil.dateToStringyyyy_MM_dd(sn.getUpdateDatetime());
                    if (datenow.equals(dateup)) {
                        if (now.before(dateEnd2)) {
                            long nowgoId = sn.getShopId();
                            long shopDetailId = sd.getId();
                            if (nowgoId == shopDetailId) {
                                Map<String, Object> nowGoMap = new HashMap<String, Object>();
                                Date nowGoTime = sn.getUpdateDatetime();
                                String now5time = DateUtil.timeDifference(nowGoTime);
                                NotifyNow45 notifyNow45 = notificationService.getNotifyNow45ByUserIdAndItemId(userId ,sn.getId() , Constant.NOTIFY_NOW45.now5);
                                if(notifyNow45 != null){
                                    nowGoMap.put("status", notifyNow45.getNotifyStatus());
                                }else {
                                    nowGoMap.put("status", 0);
                                }
                                nowGoMap.put("uuid", sd.getUuid());
                                nowGoMap.put("address", sd.getAddress());
                                nowGoMap.put("name", sd.getName());
                                nowGoMap.put("now5", sn.getDesc());
                                nowGoMap.put("time", now5time);
                                nowGoMap.put("itemId", sn.getId());
                                nowGoMap.put("shopType",sd.getShopType());
                                nowGoList.add(nowGoMap);
                            }
                        }
                    }
                }
            }
        }

        List<Map<String, Object>> areaList = new ArrayList<Map<String, Object>>();
        List<TagDetail> tagDetailList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.AREA);
        for (TagDetail td : tagDetailList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("content", td.getContent());
            map.put("desc", td.getDesc());
            areaList.add(map);
        }

//        By wangyu ↑

        scjp.setAreaList(areaList);
        scjp.setContentList(contentList);
        scjp.setCouponList(couponList);
        scjp.setFoodMainMenuList(foodMainMenuList);
        scjp.setFastionMainMenuList(fastionMainMenuList);
        scjp.setHealthMainMenuList(healthMainMenuList);
        scjp.setPlayMainMenuList(playMainMenuList);
        scjp.setBridalMainMenuList(bridalMainMenuList);
        scjp.setDriveMainMenuList(driveMainMenuList);
        scjp.setInfoMainMenuList(infoMainMenuList);
        scjp.setLearnMainMenuList(learnMainMenuList);
        scjp.setFacilityMainMenuList(facilityMainMenuList);
        scjp.setLifeMainMenuList(lifeMainMenuList);
        scjp.setShopCount(shopCount);
        scjp.setNowGoList(nowGoList);
        ejpList.add(scjp);
        jsonObject.setResultList(ejpList);
        return jsonObject;
    }

    //shopSearch
    @RequestMapping(value = "/api/shopnowgo/search/", method = RequestMethod.POST)
    @ResponseBody
    public JsonObject searchNowGoList(@ModelAttribute ShopListParams params) {

        String areaString = "";
        List<Long> shopIdList = new ArrayList<Long>();
        List<Map<String, Object>> contentList = new ArrayList<Map<String, Object>>();
        ShopSearchJsonParse ejp = new ShopSearchJsonParse();
        List<Shop> countList = shopService.getShopByNow5All(params.getShopType());
        List<Integer> areas = params.getArea();
        String Keyword = params.getKeyWord();
        Integer shopType = params.getShopType();
        Integer mainMenu = params.getMainMenu();
        Integer dayPriceLow = params.getDayPriceLow();
        Integer nightPricehigh = params.getNightPriceHigh();

        List<Long> getIdByShopType = new ArrayList<Long>();
        List<Long> getIdByMainMenu = new ArrayList<Long>();
        List<Long> getIdByArea = new ArrayList<Long>();
        List<Long> getIdByDayPriceLow = new ArrayList<Long>();
        List<Long> getIdByNightPricehigh = new ArrayList<Long>();
        List<Long> getIdByKeyword = new ArrayList<Long>();
        List<Map<String, Object>> nowGoList = new ArrayList<Map<String, Object>>();

        String datenow = DateUtil.dateToStringyyyy_MM_dd(new Date());
        String datenow2 = DateUtil.dateToStringyyyy_MM_dd_hhmmss(new Date());
        Date now = DateUtil.stringToDateyyyy_MM_dd_HH_mm_ss(datenow2);

        if (shopType != null) {
            getIdByShopType = shopService.getShopIdByNow5(shopType, 1);
        }
        if (areas != null && areas.size() > 0 && areas.get(0) != 0) {
            getIdByArea = shopTagService.getShopIdByContents(shopType, Constant.SHOP_TAG.AREA, areas);
        }
        if (mainMenu != null && mainMenu != 0) {
            getIdByMainMenu = shopTagService.getShopIdByContent(shopType, Constant.SHOP_TAG.FIRST_MENU, mainMenu);
        }
        if (dayPriceLow != null && dayPriceLow != 0) {
            getIdByDayPriceLow = shopTagService.getShopIdByContent(shopType, Constant.SHOP_TAG.DAY_PRICE_LOW, dayPriceLow);
        }
        if (nightPricehigh != null && nightPricehigh != 0) {
            getIdByNightPricehigh = shopTagService.getShopIdByContent(shopType, Constant.SHOP_TAG.NIGHT_PRICE_HIGH, nightPricehigh);
        }
        if (Keyword != null) {
            getIdByKeyword = shopService.getShopIdByKeyword(shopType, Keyword);
        }

        if (areas != null && areas.size() > 0 && areas.get(0) != 0 && getIdByArea != null) {
            getIdByShopType.retainAll(getIdByArea);
        }
        if (mainMenu != null && mainMenu != 0 && getIdByMainMenu != null && getIdByMainMenu.size() >= 0) {
            getIdByShopType.retainAll(getIdByMainMenu);
        }
        if (dayPriceLow != null && dayPriceLow != 0 && getIdByDayPriceLow != null && getIdByDayPriceLow.size() >= 0) {
            getIdByShopType.retainAll(getIdByDayPriceLow);
        }
        if (nightPricehigh != null && nightPricehigh != 0 && getIdByNightPricehigh != null && getIdByNightPricehigh.size() >= 0) {
            getIdByShopType.retainAll(getIdByNightPricehigh);
        }
        if (Keyword != null && getIdByKeyword != null && getIdByKeyword.size() >= 0) {
            getIdByShopType.retainAll(getIdByKeyword);
        }

        for (Long cd : getIdByShopType) {
            if (!shopIdList.contains(cd)) {
                shopIdList.add(cd);
            }
        }

        if (shopIdList == null) {

        } else if (shopIdList.size() > 0) {
            Collections.shuffle(shopIdList);
            params.setIdList(shopIdList);
            List<Shop> list = shopService.getShopList(params);
            List<Shop> count = shopService.getShopListCount(params);
            if (areas == null) {

            } else if (areas.size() > 0) {
                for (int i = 0; i < areas.size(); i++) {
                    String area = tagDetailService.getDesc(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.AREA, Integer.parseInt(String.valueOf(areas.get(i))));
                    if (i == 0) {
                        areaString = area;
                    } else {
                        areaString += "," + area;
                    }
                }
            }

            int shopCount = count.size();
            for (Shop ed : list) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("uuid", ed.getUuid());
                map.put("type", ed.getShopType());
                map.put("name", ed.getName());
                map.put("address", ed.getAddress());
//                map.put("excerpt", ed.getExcerpt());
                map.put("picUrl", ed.getPicUrl());
                map.put("coordinate1", Double.parseDouble(ed.getCoordinate2()));
                map.put("coordinate2", Double.parseDouble(ed.getCoordinate2()));

                ShopNowgo shopNowgo = shopNowgoService.getShopNowgoByShopId(ed.getId());
                if(shopNowgo!= null){
                    Date nowGoTime = shopNowgo.getUpdateDatetime();
                    String now5time = DateUtil.timeDifference(nowGoTime);
                    map.put("time", now5time);
                    map.put("excerpt", shopNowgo.getDesc());
                }
                contentList.add(map);
            }

            if(shopIdList.size()>0){
                List<ShopNowgo> getNowgoAll = shopNowgoService.getShopNowgoAll();
                for (ShopNowgo sn : getNowgoAll) {
                    for (Shop sd : list) {
                        String dateEnd = DateUtil.dateToStringyyyy_MM_dd_hhmmss(new Date());
                        Date dateEnd2 = DateUtil.stringToDateyyyy_MM_dd_HH_mm_ss(dateEnd);
                        String dateup = DateUtil.dateToStringyyyy_MM_dd(sn.getUpdateDatetime());
                        if (datenow.equals(dateup)) {
                            if (now.before(dateEnd2)) {
                                if (sn.getShopId() == sd.getId()) {
                                    Map<String, Object> nowGoMap = new HashMap<String, Object>();
                                    Date nowGoTime = sn.getUpdateDatetime();
                                    String now5time = DateUtil.timeDifference(nowGoTime);
                                    nowGoMap.put("uuid", sd.getUuid());
                                    nowGoMap.put("address", sd.getAddress());
                                    nowGoMap.put("name", sd.getName());
                                    nowGoMap.put("now5", sn.getDesc());
                                    nowGoMap.put("time", now5time);
                                    nowGoMap.put("itemId", sn.getId());
                                    nowGoMap.put("shopType",sd.getShopType());
                                    nowGoList.add(nowGoMap);
                                }
                            }
                        }
                    }
                }
            }
            ejp.setShopCount(shopCount);
            ejp.setArea(areaString);
            ejp.setContentList(contentList);
            ejp.setNow5List(nowGoList);
            jsonObject.setResultList(ejp);
        } else {
            jsonObject.setResultList(0);
        }
        return jsonObject;
    }

    private static String intToTime(int time) {
        String hourTime = "";
        String minuTime = "";
        String stringTime = String.valueOf(time);
        if (stringTime.length() == 4) {
            hourTime = stringTime.substring(0, 2);
            minuTime = stringTime.substring(2, 4);
        } else if (stringTime.length() == 3) {
            hourTime = stringTime.substring(0, 1);
            minuTime = stringTime.substring(1, 3);
        }
        String timeString = hourTime + ":" + minuTime;
        return timeString;
    }


    private JsonObject getShop(int shopType, List<Shop> list, int shopCount) {

        int stationNum = 0;
        int mainMenuNum = 0;
        int priceLowNum = 0;
        int priceHighNum = 0;
        int dayPriceLowNum = 0;
        int dayPriceHighNum = 0;
        int nightPriceLowNum = 0;
        int nightPriceHighNum = 0;
        String mainMenu = "";
        String genreStr = "";
        String station = "";
        String picUrl1 = "";
        String picUrl2 = "";

        List<Map<String, Object>> contentList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> areaList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> budgetList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> closeTimeList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> sceneList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> detailSearchList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> mainMenuList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> subMenuList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> massageTimeList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> seatList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> brandList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> headCountList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> roomTypetList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> roomNumList = new ArrayList<Map<String, Object>>();

        List<ShopDetail> detailList = new ArrayList<ShopDetail>();
        List<ShopTag> shopTagList = new ArrayList<ShopTag>();
        List<TagDetail> getByStationList = new ArrayList<TagDetail>();
        List<TagDetail> getByGenreList = new ArrayList<TagDetail>();
        List<Integer> tagDetalList = new ArrayList<Integer>();
        List<Integer> tagList = new ArrayList<Integer>();
        List<Integer> stationTagList = new ArrayList<Integer>();
        List<Integer> mainMenuNums = new ArrayList<Integer>();
        List<Long> idList = new ArrayList<Long>();
//        int shopCount = list.size();

        tagDetalList.add(Constant.SHOP_TAG.AREA);
        tagDetalList.add(Constant.SHOP_TAG.DAY_PRICE_LOW);
        tagDetalList.add(Constant.SHOP_TAG.NIGHT_OPEN_TIME);
        tagDetalList.add(Constant.SHOP_TAG.SCENE);
        tagDetalList.add(Constant.SHOP_TAG.CHARACTER);
        tagDetalList.add(Constant.SHOP_TAG.FIRST_MENU);
        tagDetalList.add(Constant.SHOP_TAG.SECOND_MENU);
        tagDetalList.add(Constant.SHOP_TAG.MASSAGE_TIME);
        tagDetalList.add(Constant.SHOP_TAG.BRAND);
        tagDetalList.add(Constant.SHOP_TAG.SEAT);
        tagDetalList.add(Constant.SHOP_TAG.ROOM_TYPE);
        tagDetalList.add(Constant.SHOP_TAG.ROOM_NUM);
        tagDetalList.add(Constant.SHOP_TAG.HEADCOUNT);

        tagList.add(Constant.SHOP_TAG.STATION);
        tagList.add(Constant.SHOP_TAG.FIRST_MENU);
        tagList.add(Constant.SHOP_TAG.DAY_PRICE_LOW);
        tagList.add(Constant.SHOP_TAG.DAY_PRICE_HIGH);
        tagList.add(Constant.SHOP_TAG.NIGHT_PRICE_LOW);
        tagList.add(Constant.SHOP_TAG.NIGHT_PRICE_HIGH);

        List<TagDetail> tagDetailList = tagDetailService.getTagDetailList(shopType, tagDetalList);
        List<TagDetail> foodTagDetailList = tagDetailService.getTagDetailList(Constant.SHOP_TAG.FOOD, tagDetalList);

        for (TagDetail td : foodTagDetailList) {
            if (td.getModuleType() == Constant.SHOP_TAG.FOOD && td.getTagType() == Constant.SHOP_TAG.AREA) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", td.getContent());
                map.put("desc", td.getDesc());
                areaList.add(map);
            }

            if (td.getModuleType() == Constant.SHOP_TAG.FOOD && td.getTagType() == Constant.SHOP_TAG.DAY_PRICE_LOW) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", td.getContent());
                map.put("desc", td.getDesc());
                budgetList.add(map);
            }

            if (td.getModuleType() == Constant.SHOP_TAG.FOOD && td.getTagType() == Constant.SHOP_TAG.NIGHT_OPEN_TIME) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", td.getContent());
                map.put("desc", td.getDesc());
                closeTimeList.add(map);
            }

            if (td.getModuleType() == Constant.SHOP_TAG.FOOD && td.getTagType() == Constant.SHOP_TAG.SEAT) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", td.getContent());
                map.put("desc", td.getDesc());
                seatList.add(map);
            }
        }

        for (TagDetail td : tagDetailList) {
            if (td.getModuleType() == shopType && td.getTagType() == Constant.SHOP_TAG.SCENE) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", td.getContent());
                map.put("desc", td.getDesc());
                sceneList.add(map);
            }

            if (td.getModuleType() == shopType && td.getTagType() == Constant.SHOP_TAG.CHARACTER) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", td.getContent());
                map.put("desc", td.getDesc());
                detailSearchList.add(map);
            }

            if (td.getModuleType() == shopType && td.getTagType() == Constant.SHOP_TAG.FIRST_MENU) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", td.getContent());
                map.put("desc", td.getDesc());
                mainMenuList.add(map);
            }

            if (td.getModuleType() == shopType && td.getTagType() == Constant.SHOP_TAG.SECOND_MENU) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", td.getContent());
                map.put("desc", td.getDesc());
                subMenuList.add(map);
            }

            if (td.getModuleType() == shopType && td.getTagType() == Constant.SHOP_TAG.BRAND) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", td.getContent());
                map.put("desc", td.getDesc());
                brandList.add(map);
            }

            if (td.getModuleType() == shopType && td.getTagType() == Constant.SHOP_TAG.ROOM_NUM) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", td.getContent());
                map.put("desc", td.getDesc());
                roomNumList.add(map);
            }

            if (td.getModuleType() == shopType && td.getTagType() == Constant.SHOP_TAG.ROOM_TYPE) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", td.getContent());
                map.put("desc", td.getDesc());
                roomTypetList.add(map);
            }

            if (td.getModuleType() == shopType && td.getTagType() == Constant.SHOP_TAG.MASSAGE_TIME) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", td.getContent());
                map.put("desc", td.getDesc());
                massageTimeList.add(map);
            }

            if (td.getModuleType() == shopType && td.getTagType() == Constant.SHOP_TAG.HEADCOUNT) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", td.getContent());
                map.put("desc", td.getDesc());
                headCountList.add(map);
            }
        }

        for (Shop ed : list) {
            idList.add(ed.getId());
        }

        if (idList != null && idList.size() > 0) {
            detailList = shopDetailService.getShopDetailByShopIdList(idList);
            shopTagList = shopTagService.getShopTagList(idList, shopType, tagList);
        }


        for (ShopTag st : shopTagList) {
            if (st.getTagType() == Constant.SHOP_TAG.STATION) {
                if (st.getContent() != null) {
                    stationTagList.add(st.getContent());
                }
            }
            if (st.getTagType() == Constant.SHOP_TAG.FIRST_MENU) {
                if (st.getContent() != null) {
                    mainMenuNums.add(st.getContent());
                }
            }
        }

        if (stationTagList != null && stationTagList.size() > 0) {
            getByStationList = tagDetailService.getTagDetailListByContent(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.STATION, stationTagList);
        }
        if (mainMenuNums != null && mainMenuNums.size() > 0) {
            getByGenreList = tagDetailService.getTagDetailListByContent(shopType, Constant.SHOP_TAG.FIRST_MENU, mainMenuNums);
        }

        for (Shop ed : list) {
            List<String> mainMenuStr = new ArrayList<String>();
            for (ShopTag st : shopTagList) {
                long id = ed.getId();
                long shopId = st.getShopId();
                if (id == shopId && (st.getTagType() == Constant.SHOP_TAG.STATION)) {
                    if (st.getContent() != null) {
                        for (TagDetail td : getByStationList) {
                            if (td.getContent() == st.getContent()) {
                                station = td.getDesc();
                            }
                        }
                    }
                }

                if (id == shopId && (st.getTagType() == Constant.SHOP_TAG.FIRST_MENU)) {
                    if (st.getContent() != null) {
                        for (TagDetail ta : getByGenreList) {
                            int tagDetailContent = ta.getContent();
                            int tagContent = st.getContent();
                            int tagDetailType = ta.getModuleType();
                            int tagType = st.getModuleType();
                            if (tagDetailContent == tagContent && tagDetailType == tagType) {
                                mainMenuStr.add(ta.getDesc());
                            }
                        }
                    }
                }
                if (id == shopId && st.getTagType() == Constant.SHOP_TAG.DAY_PRICE_LOW) {
                    if (st.getContent() != null) {
                        dayPriceLowNum = st.getContent();
                    }
                }

                if (id == shopId && st.getTagType() == Constant.SHOP_TAG.DAY_PRICE_HIGH) {
                    if (st.getContent() != null) {
                        dayPriceHighNum = st.getContent();
                    }
                }

                if (id == shopId && st.getTagType() == Constant.SHOP_TAG.NIGHT_PRICE_LOW) {
                    if (st.getContent() != null) {
                        nightPriceLowNum = st.getContent();
                    }
                }

                if (id == shopId && st.getTagType() == Constant.SHOP_TAG.NIGHT_PRICE_HIGH) {
                    if (st.getContent() != null) {
                        nightPriceHighNum = st.getContent();
                    }
                }
            }

            if (mainMenuStr != null) {
                genreStr = "";
                for (int i = 0; i < mainMenuStr.size(); i++) {
                    if (i == 0) {
                        genreStr = genreStr + mainMenuStr.get(0);
                    } else {
                        genreStr = genreStr + "・" + mainMenuStr.get(i);
                    }
                }
            }

            for (ShopDetail sd : detailList) {
                long sdId = sd.getShopListId();
                long edId = ed.getId();
                if (edId == sdId) {
                    picUrl1 = sd.getPicUrl3();
                    picUrl2 = sd.getPicUrl4();
                }
            }

            Map<String, Object> map = new HashMap<>();
            map.put("uuid", ed.getUuid());
            map.put("name", ed.getName());
            map.put("address", ed.getAddress());
            map.put("excerpt", ed.getExcerpt());
            map.put("picUrl", ed.getPicUrl());
            map.put("picUrl1", picUrl1);
            map.put("picUrl2", picUrl2);
            map.put("station", station);
            map.put("genre", genreStr);
            map.put("dayPriceLow", dayPriceLowNum);
            map.put("dayPriceHigh", dayPriceHighNum);
            map.put("nightPriceLow", nightPriceLowNum);
            map.put("nightPriceHigh", nightPriceHighNum);
            contentList.add(map);
        }

        if (shopType == Constant.SHOP_TAG.ALL) {
            List<ShopAllJsonParse> ejpList = new ArrayList<ShopAllJsonParse>();
            ShopAllJsonParse ejp = new ShopAllJsonParse();

            ejp.setContentList(contentList);
            ejp.setShopCount(shopCount);
            ejp.setAreaList(areaList);
            ejp.setBudgetList(budgetList);
            ejp.setshopTimeList(closeTimeList);
            ejpList.add(ejp);
            jsonObject.setResultList(ejpList);

        }
        if (shopType == Constant.SHOP_TAG.FOOD) {
            List<ShopFoodJsonParse> ejpList = new ArrayList<ShopFoodJsonParse>();
            ShopFoodJsonParse ejp = new ShopFoodJsonParse();

            ejp.setContentList(contentList);
            ejp.setShopCount(shopCount);
            ejp.setAreaList(areaList);
            ejp.setBudgetList(budgetList);
            ejp.setshopTimeList(closeTimeList);
            ejp.setSceneList(sceneList);
            ejp.setDetailSearchList(detailSearchList);
            ejp.setMainMenu(mainMenuList);
            ejpList.add(ejp);
            jsonObject.setResultList(ejpList);

        } else if (shopType == Constant.SHOP_TAG.FASTION) {
            List<ShopFastionJsonParse> ejpList = new ArrayList<ShopFastionJsonParse>();
            ShopFastionJsonParse ejp = new ShopFastionJsonParse();

            ejp.setContentList(contentList);
            ejp.setShopCount(shopCount);
            ejp.setAreaList(areaList);
            ejp.setBudgetList(budgetList);
            ejp.setshopTimeList(closeTimeList);
            ejp.setSceneList(sceneList);
            ejp.setDetailSearchList(detailSearchList);
            ejp.setMainMenu(mainMenuList);
            ejpList.add(ejp);
            jsonObject.setResultList(ejpList);

        } else if (shopType == Constant.SHOP_TAG.HEALTH) {
            List<ShopHealthJsonParse> ejpList = new ArrayList<ShopHealthJsonParse>();
            ShopHealthJsonParse ejp = new ShopHealthJsonParse();

            ejp.setContentList(contentList);
            ejp.setShopCount(shopCount);
            ejp.setAreaList(areaList);
            ejp.setBudgetList(budgetList);
            ejp.setShopTimeList(closeTimeList);
            ejp.setBrandList(brandList);
            ejp.setSeatList(seatList);
            ejp.setMassageTimeList(massageTimeList);
            ejp.setDetailSearchList(detailSearchList);
            ejp.setMainMenu(mainMenuList);
            ejp.setSubMenu(subMenuList);
            ejpList.add(ejp);
            jsonObject.setResultList(ejpList);


        } else if (shopType == Constant.SHOP_TAG.PLAY) {
            List<ShopPlayJsonParse> ejpList = new ArrayList<ShopPlayJsonParse>();
            ShopPlayJsonParse ejp = new ShopPlayJsonParse();

            ejp.setContentList(contentList);
            ejp.setShopCount(shopCount);
            ejp.setAreaList(areaList);
            ejp.setBudgetList(budgetList);
            ejp.setShopTimeList(closeTimeList);
            ejp.setSceneList(sceneList);
            ejp.setDetailSearchList(detailSearchList);
            ejp.setMainMenu(mainMenuList);
            ejpList.add(ejp);
            jsonObject.setResultList(ejpList);

        } else if (shopType == Constant.SHOP_TAG.BRIDAL) {
            List<ShopBridalJsonParse> ejpList = new ArrayList<ShopBridalJsonParse>();
            ShopBridalJsonParse ejp = new ShopBridalJsonParse();

            ejp.setContentList(contentList);
            ejp.setShopCount(shopCount);
            ejp.setAreaList(areaList);
            ejp.setBudgetList(budgetList);
            ejp.setShopTimeList(closeTimeList);
            ejp.setDetailSearchList(detailSearchList);
            ejp.setHeadCountList(headCountList);
            ejp.setMainMenu(mainMenuList);
            ejpList.add(ejp);
            jsonObject.setResultList(ejpList);

        } else if (shopType == Constant.SHOP_TAG.DRIVE) {
            List<ShopDriveJsonParse> ejpList = new ArrayList<ShopDriveJsonParse>();
            ShopDriveJsonParse ejp = new ShopDriveJsonParse();

            ejp.setContentList(contentList);
            ejp.setShopCount(shopCount);
            ejp.setAreaList(areaList);
            ejp.setBudgetList(budgetList);
            ejp.setShopTimeList(closeTimeList);
            ejp.setSceneList(sceneList);
            ejp.setDetailSearchList(detailSearchList);
            ejp.setMainMenu(mainMenuList);
            ejpList.add(ejp);
            jsonObject.setResultList(ejpList);


        } else if (shopType == Constant.SHOP_TAG.LEARNING) {
            List<ShopLearnJsonParse> ejpList = new ArrayList<ShopLearnJsonParse>();
            ShopLearnJsonParse ejp = new ShopLearnJsonParse();

            ejp.setContentList(contentList);
            ejp.setShopCount(shopCount);
            ejp.setAreaList(areaList);
            ejp.setBudgetList(budgetList);
            ejp.setShopTimeList(closeTimeList);
            ejp.setDetailSearchList(detailSearchList);
            ejp.setSceneList(sceneList);
            ejp.setMainMenu(mainMenuList);
            ejp.setSubMenu(subMenuList);
            ejpList.add(ejp);
            jsonObject.setResultList(ejpList);

        } else if (shopType == Constant.SHOP_TAG.FACILITY) {
            List<ShopFacilityJsonParse> ejpList = new ArrayList<ShopFacilityJsonParse>();
            ShopFacilityJsonParse ejp = new ShopFacilityJsonParse();

            ejp.setContentList(contentList);
            ejp.setShopCount(shopCount);
            ejp.setAreaList(areaList);
            ejp.setBudgetList(budgetList);
            ejp.setShopTimeList(closeTimeList);
            ejp.setDetailSearchList(detailSearchList);
            ejp.setHeadCountList(headCountList);
            ejp.setRoomTypeList(roomTypetList);
            ejp.setRoomNumList(roomNumList);
            ejp.setMainMenu(mainMenuList);
            ejp.setSubMenu(subMenuList);
            ejpList.add(ejp);
            jsonObject.setResultList(ejpList);

        } else if (shopType == Constant.SHOP_TAG.LIFE) {
            List<ShopLifeJsonParse> ejpList = new ArrayList<ShopLifeJsonParse>();
            ShopLifeJsonParse ejp = new ShopLifeJsonParse();

            ejp.setContentList(contentList);
            ejp.setShopCount(shopCount);
            ejp.setAreaList(areaList);
            ejp.setBudgetList(budgetList);
            ejp.setShopTimeList(closeTimeList);
            ejp.setDetailSearchList(detailSearchList);
            ejp.setMainMenu(mainMenuList);
            ejp.setSubMenu(subMenuList);
            ejpList.add(ejp);
            jsonObject.setResultList(ejpList);
        }
        return jsonObject;
    }

    @RequestMapping(value = "/api/update/notify/now54/", method = RequestMethod.POST)
    @ResponseBody
    public JsonObject updateNotify54(@RequestBody MultiValueMap<String, String> formData , HttpServletRequest hsr) throws APIException {

        String itemId = formData.getFirst("itemId");
        logger.debug("----1----itemId=" + itemId);
        String Type = formData.getFirst("notifyType");
        logger.debug("----2----Type=" + Type);
        int notifyType = Integer.valueOf(Type);
        long userId = authService.getUserId(hsr);

        NotifyNow45 notifyNow45 = new NotifyNow45();
        notifyNow45.setUserId(userId);
        notifyNow45.setItemId(Long.valueOf(itemId));
        notifyNow45.setPushType(Constant.NOTIFY_NOW45.NOTIFY_TYPE_PUSH);
        notifyNow45.setNotifyStatus(Constant.NOTIFY_NOW45.NOTIFY_STATUS_OPENED);
        notifyNow45.setNotifyType((byte)notifyType);
        notifyNow45.setUpdateDatetime(new Date(System.currentTimeMillis()));
        notificationService.updateNotifyNow45(notifyNow45);

        jsonObject.setResultList(null);
        return jsonObject;
    }

    @RequestMapping(value = "/api/update/shop/notify/now54/", method = RequestMethod.POST)
    @ResponseBody
    public JsonObject updateNotify(@RequestBody MultiValueMap<String, String> formData , HttpServletRequest hsr) throws APIException {

        String uuid = formData.getFirst("uuid");
        logger.debug("----1----shop uuid=" + uuid);
        long userId = authService.getUserId(hsr);
        logger.debug("----2----userId=" + userId);
        FavDetail favDetail = null;
        Shop shop = shopService.getShopByUuid(uuid);
        if(shop != null){
            logger.debug("----3----shop id=" + shop.getId());
             favDetail = favDetailService.getFavDetailByUserId(userId,shop.getId(),Constant.FAV_TYPE.SHOP);
        }

        if(favDetail != null){
            logger.debug("----4----favDetail id=" + favDetail.getId());
            ShopNowgo shopNowgo = shopNowgoService.getShopNowgoByShopId(shop.getId());
            List<ShopCoupon> couponList = shopCouponService.getShopCoupon(shop.getId());

            DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
            txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            TransactionStatus txStatus = txManager.getTransaction(txDefinition);
            try {
                if(shopNowgo != null){
                    logger.debug("----5----shopNowgo id=" + shopNowgo.getId());
                    NotifyNow45 notifyNow45 = new NotifyNow45();
                    notifyNow45.setUserId(userId);
                    notifyNow45.setItemId(Long.valueOf(shopNowgo.getId()));
                    notifyNow45.setPushType(Constant.NOTIFY_NOW45.NOTIFY_TYPE_PUSH);
                    notifyNow45.setNotifyStatus(Constant.NOTIFY_NOW45.NOTIFY_STATUS_OPENED);
                    notifyNow45.setNotifyType(Constant.NOTIFY_NOW45.now4);
                    notifyNow45.setUpdateDatetime(new Date(System.currentTimeMillis()));
                    notificationService.updateNotifyNow45(notifyNow45);
                }

                if(couponList.size()>0){
                    for(ShopCoupon sc:couponList){
                        logger.debug("----6----ShopCoupon id=" + sc.getId());
                        NotifyNow45 notifyNow45 = new NotifyNow45();
                        notifyNow45.setUserId(userId);
                        notifyNow45.setItemId(Long.valueOf(sc.getId()));
                        notifyNow45.setPushType(Constant.NOTIFY_NOW45.NOTIFY_TYPE_PUSH);
                        notifyNow45.setNotifyStatus(Constant.NOTIFY_NOW45.NOTIFY_STATUS_OPENED);
                        notifyNow45.setNotifyType(Constant.NOTIFY_NOW45.now4);
                        notifyNow45.setUpdateDatetime(new Date(System.currentTimeMillis()));
                        notificationService.updateNotifyNow45(notifyNow45);
                    }
                }
                txManager.commit(txStatus);
            }catch (Exception ex) {
                txManager.rollback(txStatus);
                logger.error(" now4 notification push failed, error=" + ex.getMessage());
                logger.error(" now4 notification push, error=" + ex.toString());
                ex.printStackTrace();
            }
        }
        jsonObject.setResultList(null);
        return jsonObject;
    }
}

