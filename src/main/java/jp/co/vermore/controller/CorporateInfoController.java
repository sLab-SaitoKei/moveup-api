package jp.co.vermore.controller;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.JsonObject;
import jp.co.vermore.common.mvc.APIException;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.entity.*;
import jp.co.vermore.jsonparse.ShopSearchJsonParse;
import jp.co.vermore.jsonparse.shopdetailjsonparse.ShopCorporateInfoDetailJsonParse;
import jp.co.vermore.jsonparse.shopjsonparse.ShopCorporateInfoJsonParse;
import jp.co.vermore.service.*;

import jp.co.vermore.validation.CorporateInfoListParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * CorporateInfoController
 * Created by wubin.
 * <p>
 * DateTime: 2018/04/16 19:03
 * Copyright: sLab, Corp
 */
@Controller
public class CorporateInfoController extends BaseController {

    @Autowired
    private ShopService shopService;

    @Autowired
    CorporateInfoService corporateInfoService;

    @Autowired
    private ShopTagService shopTagService;

    @Autowired
    private TagDetailService tagDetailService;

    @Autowired
    private PicService picService;

    @Autowired
    private FavDetailService favDetailService;

    @Autowired
    private AuthService authService;

    @Autowired
    private RecruitService recruitService;

    @Autowired
    private ShopNowgoService shopNowgoService;

    @Autowired
    private ShopCouponService shopCouponService;

    @Autowired
    private RandomShopService randomShopService;

    @Autowired
    private NotificationService notificationService;


    @RequestMapping(value = "/api/corporateinfo/list/{shopType}/{limit}/{offset}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getCorporateInfoList(@PathVariable int shopType, @PathVariable int limit, @PathVariable int offset) {

        String picUrl1 = "";
        String picUrl2 = "";
        String station = "";
        String genreStr = "";
        List<String> randomOneUuidList = new ArrayList<String>();
        List<String> randomTwoUuidList = new ArrayList<String>();
        List<Shop> list = new ArrayList<Shop>();

//        List<Shop> list = shopService.getShopAll(shopType, limit, offset);
        List<Shop> count = shopService.getShopAllCount(shopType);
        int shopCount = count.size();
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



        if (shopType == Constant.SHOP_TAG.CORPORATE_INFO) {

            ShopCorporateInfoJsonParse ejp = new ShopCorporateInfoJsonParse();
            List<Map<String, Object>> contentList = new ArrayList<Map<String, Object>>();
            List<ShopCorporateInfoJsonParse> ejpList = new ArrayList<ShopCorporateInfoJsonParse>();
            List<Integer> stationTagList = new ArrayList<Integer>();
            List<TagDetail> getByStationList = new ArrayList<TagDetail>();
            List<TagDetail> getByGenreList = new ArrayList<TagDetail>();
            List<Integer> mainMenuNums = new ArrayList<Integer>();
            List<Long> idList = new ArrayList<Long>();
            List<Integer> tagList = new ArrayList<Integer>();

            tagList.add(Constant.SHOP_TAG.STATION);
            tagList.add(Constant.SHOP_TAG.FIRST_MENU);

            List<Map<String, Object>> areaList = tagDetailService.getTagDetailDesc(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.AREA);
            List<Map<String, Object>> mainMenuList = tagDetailService.getTagDetailDesc(Constant.SHOP_TAG.CORPORATE_INFO, Constant.SHOP_TAG.FIRST_MENU);

            Collections.shuffle(list);
            for (Shop ed : list) {
                idList.add(ed.getId());
            }

            if(idList.size()>0){
                List<CorporateInfoDetail> detailList = corporateInfoService.getCorporateInfoDetailList(idList);
                List<ShopTag> shopTagList = shopTagService.getShopTagList(idList, shopType, tagList);

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
                    getByGenreList = tagDetailService.getTagDetailListByContentForInfo(Constant.SHOP_TAG.FIRST_MENU, mainMenuNums);
                }

                if(list.size()>0 && shopTagList.size()>0){
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
                        }

                        if (mainMenuStr != null) {
                            genreStr = "";
                            System.out.println(mainMenuStr);
                            for (int i = 0; i < mainMenuStr.size(); i++) {
                                if (i == 0) {
                                    genreStr = genreStr + mainMenuStr.get(0);
                                } else {
                                    genreStr = genreStr + "・" + mainMenuStr.get(i);
                                }
                            }
                        }

                        if(detailList.size()>0) {
                            for (CorporateInfoDetail cd : detailList) {
                                long cid = ed.getId();
                                long detail = cd.getShopListId();
                                if (cid == detail) {
                                    picUrl1 = cd.getPicUrl3();
                                    picUrl2 = cd.getPicUrl4();
                                }
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
                        contentList.add(map);
                    }
                }
            }
            ejp.setShopCount(shopCount);
            ejp.setAreaList(areaList);
            ejp.setMainMenu(mainMenuList);
            ejp.setContentList(contentList);
            ejpList.add(ejp);
            jsonObject.setResultList(ejpList);
        }
        return jsonObject;
    }

    //コーポレートインフォ
    @RequestMapping(value = "/api/corporateinfo/detail/{uuid}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getcorporateDetail(@PathVariable String uuid, HttpServletRequest hsr) throws APIException, ParseException {

        Double coord1 = 0.0;
        Double coord2 = 0.0;

        String datenow = DateUtil.dateToStringyyyy_MM_dd(new Date());
        String datenow2 = DateUtil.dateToStringyyyy_MM_dd_hhmmss(new Date());
        Date now = DateUtil.stringToDateyyyy_MM_dd_HH_mm_ss(datenow2);
        Date date = DateUtil.stringToDateyyyy_MM_dd(datenow);

        Pic pic = new Pic();
        pic.setItemType(Constant.EVENT_PIC_TYPE.INFO);


        Long shopListId = shopService.getShopByUuid(uuid).getId();
        Shop shop = shopService.getShopById(shopListId);
        Long shopId = corporateInfoService.getCorporateId(shopListId);
        int shopType = corporateInfoService.getCorporateType(shopListId);
        String uuid2 = recruitService.findUuidByTypeAndId(shopType,shopListId);

        List<FavDetail> favDetail = new ArrayList<FavDetail>();
        Long userId = 0L;
        try {
            userId = authService.getUserId(hsr);
        }catch (APIException ex){}
        if(userId>0){
            favDetail = favDetailService.getFavDetailAll(userId, Constant.FAV_TYPE.CORPORATEINFO);
        }
        
        if (shopType == Constant.SHOP_TAG.CORPORATE_INFO) {
            List<ShopCorporateInfoDetailJsonParse> ejpList = new ArrayList<>();
            List<Shop> list = shopService.getCorporate();
            List<CorporateInfoDetail> detail = corporateInfoService.getCorporateInfoDetailAll(shopListId);
            String station = tagDetailService.getStation(shopListId, shopType);
            int walkTime = shopTagService.getContent(shopListId, Constant.SHOP_TAG.CORPORATE_INFO, Constant.SHOP_TAG.WALK_TIME);
            ShopCorporateInfoDetailJsonParse ejp2 = new ShopCorporateInfoDetailJsonParse();
            for (CorporateInfoDetail ed : detail) {
                ShopCorporateInfoDetailJsonParse ejp = new ShopCorporateInfoDetailJsonParse();
                List<Pic> picList = picService.selectPicById(ed.getShopListId(), pic.getItemType());
                List<Pic> picL2 = new ArrayList<>();

                if(picList.size()>=1){
                    ejp.setPicUrl7(picList.get(0).getPicUrl());
                }
                if(picList.size()>=2){
                    ejp.setPicUrl8(picList.get(1).getPicUrl());
                }
                if(picList.size()>=3){
                    ejp.setPicUrl9(picList.get(2).getPicUrl());
                }
                if(picList.size()>=4){
                    ejp.setPicUrl10(picList.get(3).getPicUrl());
                }
                if(picList.size()>=5){
                    ejp.setPicUrl11(picList.get(4).getPicUrl());
                }
                if(picList.size()>=6){
                    ejp.setPicUrl12(picList.get(5).getPicUrl());
                }

                ejp.setUuid2(uuid2);
                ejp.setShopType(shopType);
                ejp.setPicList(picL2);
                ejp.setId(ed.getId());
                ejp.setShopListID(ed.getShopListId());
                ejp.setName(ed.getName());
                ejp.setPicUrl1(ed.getPicUrl1());
                ejp.setPicUrl2(ed.getPicUrl2());
                ejp.setPicUrl3(ed.getPicUrl3());
                ejp.setPicUrl4(ed.getPicUrl4());
                ejp.setPicUrl5(ed.getPicUrl5());
                ejp.setPicUrl6(ed.getPicUrl6());
                ejp.setVideoUrl(ed.getVideoUrl());
                ejp.setTitle(ed.getTitle());
                ejp.setDesc1(ed.getDesc1());
                ejp.setDesc2(ed.getDesc2());//設立
                ejp.setDesc3(ed.getDesc3());//代表取締役
                ejp.setDesc4(ed.getDesc4());//資本金
                ejp.setDesc5(ed.getDesc5());//従業員数
                ejp.setDesc6(ed.getDesc6());//事業内容
                ejp.setDesc7(ed.getDesc7());//営業所、支店、支社
                ejp.setDesc8(ed.getDesc8());//HP
                ejp.setDesc9(ed.getDesc9());//公式アカウント
                ejp.setDesc10(ed.getDesc10());//関連、系列グループ店舗
                ejp.setDesc11(ed.getDesc11());//備考
                ejp.setTel(ed.getTel());
                ejp.setAddress(ed.getAddress());
                ejp.setStation(station);
                ejp.setWalkTime(walkTime);

                for (Shop sp : list) {
                    long spId = sp.getId();
                    long edId = ed.getShopListId();
                    if (spId == edId) {
                        coord1 = Double.parseDouble(sp.getCoordinate1());
                        coord2 = Double.parseDouble(sp.getCoordinate2());
                    }
                }
                ejp.setCoordinate1(coord1);
                ejp.setCoordinate2(coord2);

                ejp.setFavorite(false);
                if (favDetail.size() > 0) {
                    for (FavDetail fd : favDetail) {
                        if (ed.getShopListId().equals(fd.getShopId())) {
                            ejp.setFavorite(true);
                        }
                    }
                }

                //now5
                List<Map<String, Object>> nowGoList = new ArrayList<Map<String, Object>>();
                ShopNowgo shopNowgo = shopNowgoService.getShopNowgoByShopId(shopId);
                if(shopNowgo != null){
                    if(shop != null){
                        String dateup = DateUtil.dateToStringyyyy_MM_dd(shopNowgo.getUpdateDatetime());
                        if (datenow.equals(dateup)) {
                            Long nowgoId = shopNowgo.getShopId();
                            Long shopId2 = shop.getId();
                            if (nowgoId.equals(shopId2)) {
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
                            String dateEnd = DateUtil.dateToStringyyyy_MM_dd(sc.getPublishEnd());
                            Date dateEnd2 = DateUtil.stringToDateyyyy_MM_dd(dateEnd);
                            String dateF = DateUtil.dateToStringyyyy_MM_dd(sc.getPublishStart());
                            Date datef = DateUtil.stringToDateyyyy_MM_dd(dateF);
                            if (datef.getTime() <= date.getTime()) {
                                if (date.getTime() <= dateEnd2.getTime()) {
                                    if (sc.getShopId().equals(shop.getId())) {
                                        Map<String, Object> couponMap = new HashMap<>();
                                        Date couponTime = sc.getUpdateDatetime();
                                        String now4time = DateUtil.timeDifference(couponTime);
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
                CorporateInfoDetail infoDetail = corporateInfoService.getDetailByShopId(shopId);
                List<ShopCoupon> shopCouponList2 = shopCouponService.getShopCoupon(shopId);
                for (ShopCoupon sc : shopCouponList2) {
                    String dateEnd = DateUtil.dateToStringyyyy_MM_dd(sc.getPublishEnd());
                    Date dateEnd2 = DateUtil.stringToDateyyyy_MM_dd(dateEnd);
                    String dateF = DateUtil.dateToStringyyyy_MM_dd(sc.getPublishStart());
                    Date datef = DateUtil.stringToDateyyyy_MM_dd(dateF);
                    if (datef.getTime() <= date.getTime()) {
                        if (date.getTime() <= dateEnd2.getTime()) {
                            Map<String, Object> couponMap = new HashMap<>();
                            Date couponTime = sc.getUpdateDatetime();
                            String now4time = DateUtil.timeDifference(couponTime);
                            couponMap.put("name", infoDetail.getName());
                            couponMap.put("title", sc.getTitle());
                            couponMap.put("picUrl", sc.getPicUrl1());
                            couponMap.put("desc", sc.getDesc());
                            couponMap.put("time", now4time);
                            couponList2.add(couponMap);
                        }
                    }
                }

                //fav
                if(userId != 0 &&userId != null && shopListId != null){
                    FavDetail favDetail2 = favDetailService.getFavorite( userId, shopListId, Constant.FAV_TYPE.CORPORATEINFO);
                    if(favDetail2 != null){
                        UserSetting us = notificationService.getUserSettingBySettingKeyAndSettingValueAndByItemId(userId,Constant.USER_SETTING.NOW4,Constant.USER_SETTING.OFF,shopListId);
                        if(us == null){
                            ejp.setUserSetting("0");
                            ejp.setFavType(Constant.FAV_TYPE.CORPORATEINFO);
                        }else {
                            ejp.setUserSetting("1");
                            ejp.setFavType(Constant.FAV_TYPE.CORPORATEINFO);
                        }
                    }else{
                        ejp.setUserSetting("2");
                        ejp.setFavType(Constant.FAV_TYPE.CORPORATEINFO);
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

    @RequestMapping(value = "/api/corporateinfo/search/", method = RequestMethod.POST)
    @ResponseBody
    public JsonObject searchCorporateInfoList(@ModelAttribute CorporateInfoListParams params) {

        List<Long> shopIdList = new ArrayList<Long>();
        List<Long> idList = new ArrayList<Long>();
        ShopSearchJsonParse ejp = new ShopSearchJsonParse();
        List<Map<String, Object>> contentList = new ArrayList<Map<String, Object>>();

        String genreStr = "";
        String station = "";
        String areaString = "";

        List<Integer> areas = params.getArea();
        Integer mainMenu = params.getMainMenu();
        String Keyword = params.getKeyWord();
        Integer now4 = params.getNow4();
        Integer now5 = params.getNow5();
        Integer shopType = Constant.SHOP_TAG.CORPORATE_INFO;

        List<Long> getIdByArea = new ArrayList<Long>();
        List<Long> getIdByShopType = new ArrayList<Long>();
        List<Long> getIdByMainMenu = new ArrayList<Long>();
        List<Long> getIdByNow4 = new ArrayList<Long>();
        List<Long> getIdByNow5 = new ArrayList<Long>();
        List<Long> getIdByKeyword = new ArrayList<Long>();
        List<TagDetail> getByStationList = new ArrayList<TagDetail>();
        List<TagDetail> getByGenreList = new ArrayList<TagDetail>();
        List<ShopTag> shopTagList = new ArrayList<ShopTag>();
        List<Integer> stationTagList = new ArrayList<Integer>();
        List<Integer> mainMenuNums = new ArrayList<Integer>();
        List<Integer> tagList = new ArrayList<Integer>();

        tagList.add(Constant.SHOP_TAG.STATION);
        tagList.add(Constant.SHOP_TAG.FIRST_MENU);

        if (shopType != null) {
            getIdByShopType = shopService.getShopId(shopType);
        }
        if (areas != null && areas.size() > 0) {
            getIdByArea = shopTagService.getShopIdByContents(shopType, Constant.SHOP_TAG.AREA, areas);
        }
        if (mainMenu != null && mainMenu != 0) {
            getIdByMainMenu = shopTagService.getShopIdByContent(shopType, Constant.SHOP_TAG.FIRST_MENU, mainMenu);
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
        if (areas != null && areas.size() > 0 && getIdByArea != null) {
            getIdByShopType.retainAll(getIdByArea);
        }
        if (mainMenu != null && mainMenu != 0 && getIdByMainMenu != null) {
            getIdByShopType.retainAll(getIdByMainMenu);
        }
        if (now4 != null && getIdByNow4 != null && getIdByNow4.size() >= 0 && now4 != 0) {
            getIdByShopType.retainAll(getIdByNow4);
        }
        if (now5 != null && getIdByNow5 != null && getIdByNow5.size() >= 0 && now5 != 0) {
            getIdByShopType.retainAll(getIdByNow5);
        }
        if (Keyword != null && getIdByKeyword != null && getIdByKeyword.size() >= 0) {
            getIdByShopType.retainAll(getIdByKeyword);
        }

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

        if (idList == null || idList.size() == 0) {
            jsonObject.setResultList(0);
        } else {
            params.setIdList(idList);
            List<Shop> list = corporateInfoService.getCorporateInfoList(params);
            int shopCount = list.size();
            ejp.setShopCount(shopCount);

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

            if (idList != null && idList.size() > 0) {
                shopTagList = shopTagService.getShopTagList(idList, shopType, tagList);
            }

            if (stationTagList != null && stationTagList.size() > 0) {
                getByStationList = tagDetailService.getTagDetailListByContent(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.STATION, stationTagList);
            }
            if (mainMenuNums != null && mainMenuNums.size() > 0) {
                getByGenreList = tagDetailService.getTagDetailListByContent(shopType, Constant.SHOP_TAG.FIRST_MENU, mainMenuNums);
            }

            for (Shop ed : list) {
                Map<String, Object> map = new HashMap<>();
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
                }
                map.put("id", ed.getId());
                map.put("name", ed.getName());
                map.put("address", ed.getAddress());
                map.put("excerpt", ed.getExcerpt());
                map.put("picUrl", ed.getPicUrl());
                map.put("station", station);
                map.put("genre", mainMenuStr);
                contentList.add(map);
            }
            ejp.setArea(areaString);
            ejp.setContentList(contentList);
            jsonObject.setResultList(ejp);
        }
        return jsonObject;
    }
}

