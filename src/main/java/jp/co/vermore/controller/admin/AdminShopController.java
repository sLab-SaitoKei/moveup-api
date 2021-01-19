package jp.co.vermore.controller.admin;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.DatatablesJsonObject;
import jp.co.vermore.common.JsonStatus;
import jp.co.vermore.common.mvc.APIException;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.entity.*;
import jp.co.vermore.form.admin.*;
import jp.co.vermore.service.*;
import org.springframework.beans.BeanUtils;
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
import java.io.IOException;
import java.util.*;


/**
 * ShopAdminController
 * Created by wubin.
 * <p>
 * DateTime: 2018/03/19 14:40
 * Copyright: sLab, Corp
 */

@Controller
public class AdminShopController extends BaseController {

    @Autowired
    PlatformTransactionManager txManager;

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopDetailService shopDetailService;

    @Autowired
    private TagDetailService tagDetailService;

    @Autowired
    private ShopTagService shopTagService;

    @Autowired
    private PicService picService;

    @Autowired
    private AWSService awsService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RandomShopService randomShopService;


    @RequestMapping(value = "/admin/shop/list/", method = RequestMethod.GET)
    public String shop(Model model,HttpServletRequest request) {
        int errorCode = 0;
        if(!request.getSession().isNew()){
            if(request.getSession().getAttribute("error") != null && request.getSession().getAttribute("error") != ""){
                errorCode = (int)request.getSession().getAttribute("error");
                request.getSession().setAttribute("error",0);
            }
        }
        model.addAttribute("errorCode", errorCode);
        return "admin/shopList";
    }


    @RequestMapping(value = "/admin/shop/list/", method = RequestMethod.POST)
    @ResponseBody
    public DatatablesJsonObject shopList(@RequestBody ShopListForm form) {
        logger.debug("----1----");
        // set order statement
        if (form.getOrder().size() > 0
                && form.getColumns().get(form.getOrder().get(0).getColumn()).getName() != null
                && form.getColumns().get(form.getOrder().get(0).getColumn()).getName().length() > 0) {
            form.setOrderStatement(form.getColumns().get(form.getOrder().get(0).getColumn()).getName() + " " + form.getOrder().get(0).getDir());
            logger.debug("----2----order statement=" + form.getOrderStatement());
        } else {
            form.setOrderStatement("id");
            logger.debug("----2----order statement=" + form.getOrderStatement());
        }
        logger.debug("----3----");

        // query data
        List<ShopForList> dataList = shopService.getShopAllByCondition(form);

        int totalCountFiltered = shopService.getShopCountByCondition(form);
        int totalCount = shopService.getShopCount();
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

    // Shop admin add
    @RequestMapping(value = "/admin/shop/regist/{category}/", method = RequestMethod.GET)
    public String shopRegistInput(Model model, @PathVariable int category) {
        ShopRegistForm shopRegistForm = new ShopRegistForm();
        model.addAttribute("shopRegistForm", shopRegistForm);

        List<Integer> weekDayList = new ArrayList<Integer>();
        List<Integer> openTimeList = new ArrayList<Integer>();
        List<Integer> characterList = new ArrayList<Integer>();

        shopRegistForm.setWeekDayList(weekDayList);
        shopRegistForm.setOpenTimeList(openTimeList);
        shopRegistForm.setCharacterList(characterList);
        shopRegistForm.setShopType(category);

        List<TagDetail> areaTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.AREA);
        List<TagDetail> stationTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.STATION);
        List<TagDetail> dayPriceTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_PRICE_LOW);
        List<TagDetail> shopOpenTimeTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_OPEN_TIME);
        List<TagDetail> nightPriceTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_PRICE_LOW);
        List<TagDetail> shopCloseTimeTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_OPEN_TIME);

        List<TagDetail> brandTagList = new ArrayList<TagDetail>();
        List<TagDetail> firstMenuList = new ArrayList<TagDetail>();
        List<TagDetail> sceneTagList = new ArrayList<TagDetail>();
        List<TagDetail> characterTagList = new ArrayList<TagDetail>();
        model.addAttribute("brandMap", getMap(brandTagList));
        model.addAttribute("characterMap", getMap(characterTagList));
        model.addAttribute("firstMenuMap", getMap(firstMenuList));
        model.addAttribute("sceneMap", getMap(sceneTagList));

        //食べる
        if (category == Constant.SHOP_TAG.FOOD) {
            firstMenuList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.FIRST_MENU);
            characterTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.CHARACTER);
            sceneTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.SCENE);
            List<TagDetail> seatTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.SEAT);
            List<TagDetail> restDayTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_DATE);
            List<TagDetail> restWeekTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_WEEK);
            List<TagDetail> restWeekDayTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_WEEKDAY);

            model.addAttribute("firstMenuMap", getMap(firstMenuList));
            model.addAttribute("characterMap", getMap(characterTagList));
            model.addAttribute("sceneMap", getMap(sceneTagList));
            model.addAttribute("seatTagList", seatTagList);
            model.addAttribute("restDayList", restDayTagList);
            model.addAttribute("restWeekList", restWeekTagList);
            model.addAttribute("restWeekDayList", restWeekDayTagList);
        }

        //ファッション
        if (category == Constant.SHOP_TAG.FASTION) {
            firstMenuList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.FIRST_MENU);
            characterTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.CHARACTER);
            sceneTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.SCENE);
            brandTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.BRAND);

            model.addAttribute("firstMenuMap", getMap(firstMenuList));
            model.addAttribute("characterMap", getMap(characterTagList));
            model.addAttribute("brandMap", getMap(brandTagList));
            model.addAttribute("sceneMap", getMap(sceneTagList));
        }

        //健康ビューティー
        if (category == Constant.SHOP_TAG.HEALTH) {
            firstMenuList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.FIRST_MENU);
            List<TagDetail> secondMenuList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.SECOND_MENU);
            characterTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.CHARACTER);
            sceneTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.SCENE);
            brandTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.BRAND);
            List<TagDetail> seatTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.SEAT);
            List<TagDetail> massageTimeTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.MASSAGE_TIME);

            model.addAttribute("firstMenuList", firstMenuList);
            model.addAttribute("secondMenuList", secondMenuList);
            model.addAttribute("characterMap", getMap(characterTagList));
            model.addAttribute("brandMap", getMap(brandTagList));
            model.addAttribute("sceneMap", getMap(sceneTagList));
            model.addAttribute("seatTagList", seatTagList);
            model.addAttribute("massageTimeTagList", massageTimeTagList);
        }

        //遊ぶ
        if (category == Constant.SHOP_TAG.PLAY) {
            firstMenuList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.FIRST_MENU);
            characterTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.CHARACTER);
            sceneTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.SCENE);

            model.addAttribute("firstMenuMap", getMap(firstMenuList));
            model.addAttribute("characterMap", getMap(characterTagList));
            model.addAttribute("sceneMap", getMap(sceneTagList));
        }

        //ブライダル
        if (category == Constant.SHOP_TAG.BRIDAL) {
            firstMenuList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.FIRST_MENU);
            characterTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.CHARACTER);
            sceneTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.SCENE);
            List<TagDetail> headCountTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.HEADCOUNT);

            model.addAttribute("firstMenuList", firstMenuList);
            model.addAttribute("sceneList", sceneTagList);
            model.addAttribute("characterMap", getMap(characterTagList));
            model.addAttribute("headCountTagList", headCountTagList);
        }

        //乗る
        if (category == Constant.SHOP_TAG.DRIVE) {
            firstMenuList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.FIRST_MENU);
            characterTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.CHARACTER);
            sceneTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.SCENE);

            model.addAttribute("firstMenuMap", getMap(firstMenuList));
            model.addAttribute("sceneMap", getMap(sceneTagList));
            model.addAttribute("characterMap", getMap(characterTagList));
        }

        //学ぶ
        if (category == Constant.SHOP_TAG.LEARNING) {
            firstMenuList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.FIRST_MENU);
            List<TagDetail> secondMenuList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.SECOND_MENU);
            characterTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.CHARACTER);
            sceneTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.SCENE);

            model.addAttribute("firstMenuList", firstMenuList);
            model.addAttribute("secondMenuList", secondMenuList);
            model.addAttribute("sceneMap", getMap(sceneTagList));
            model.addAttribute("characterMap", getMap(characterTagList));
        }

        //施設
        if (category == Constant.SHOP_TAG.FACILITY) {
            firstMenuList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.FIRST_MENU);
            List<TagDetail> secondMenuList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.SECOND_MENU);
            characterTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.CHARACTER);
            sceneTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.SCENE);
            List<TagDetail> roomTypeTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.ROOM_TYPE);
            List<TagDetail> roomNumTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.ROOM_NUM);
            List<TagDetail> headCountTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.HEADCOUNT);

            model.addAttribute("firstMenuList", firstMenuList);
            model.addAttribute("secondMenuList", secondMenuList);
            model.addAttribute("sceneMap", getMap(sceneTagList));
            model.addAttribute("roomTypeTagList", roomTypeTagList);
            model.addAttribute("roomNumTagList", roomNumTagList);
            model.addAttribute("headCountTagList", headCountTagList);
            model.addAttribute("characterMap", getMap(characterTagList));
        }

        //ライフスタイル
        if (category == Constant.SHOP_TAG.LIFE) {
            firstMenuList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.FIRST_MENU);
            List<TagDetail> secondMenuList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.SECOND_MENU);
            characterTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.CHARACTER);

            model.addAttribute("firstMenuList", firstMenuList);
            model.addAttribute("secondMenuList", secondMenuList);
            model.addAttribute("characterMap", getMap(characterTagList));
        }

        model.addAttribute("areaList", areaTagList);
        model.addAttribute("stationList", stationTagList);
        model.addAttribute("dayPriceList", dayPriceTagList);
        model.addAttribute("nightPriceList", nightPriceTagList);
        model.addAttribute("shopOpenTimeList", shopOpenTimeTagList);
        model.addAttribute("shopCloseTimeList", shopCloseTimeTagList);
        return "admin/shopRegist";
    }

    // shop admin add execution
    @RequestMapping(value = "/admin/shop/regist/{category}/", method = RequestMethod.POST)
    public String shopRegist(@ModelAttribute ShopRegistForm form, @PathVariable int category, Model model,HttpServletRequest request) throws APIException {
        HttpSession session = request.getSession();
        try {
            if (!form.getPicFile().isEmpty()) {
                form.setPicUrl(awsService.postMiddleThumbnailFile(form.getPicFile()));
            }
            //main
            if (!form.getPicFile1().isEmpty()) {
                form.setPicUrl1(awsService.postFile(form.getPicFile1()));
            }
            //sub1
            if (!form.getPicFile2().isEmpty()) {
                form.setPicUrl2(awsService.postFile(form.getPicFile2()));
            }
            //sub2
            if (!form.getPicFile3().isEmpty()) {
                form.setPicUrl3(awsService.postFile(form.getPicFile3()));
            }
            //sub3
            if (!form.getPicFile4().isEmpty()) {
                form.setPicUrl4(awsService.postFile(form.getPicFile4()));
            }
            //sub4
            if (!form.getPicFile5().isEmpty()) {
                form.setPicUrl5(awsService.postFile(form.getPicFile5()));
            }
            //sub5
            if (!form.getPicFile7().isEmpty()) {
                form.setPicUrl7(awsService.postFile(form.getPicFile7()));
            }
            //sub6
            if (!form.getPicFile8().isEmpty()) {
                form.setPicUrl8(awsService.postFile(form.getPicFile8()));
            }
            //sub7
            if (!form.getPicFile9().isEmpty()) {
                form.setPicUrl9(awsService.postFile(form.getPicFile9()));
            }
            //sub8
            if (!form.getPicFile10().isEmpty()) {
                form.setPicUrl10(awsService.postFile(form.getPicFile10()));
            }
            //sub9
            if (!form.getPicFile11().isEmpty()) {
                form.setPicUrl11(awsService.postFile(form.getPicFile11()));
            }
            //sub10
            if (!form.getPicFile12().isEmpty()) {
                form.setPicUrl12(awsService.postFile(form.getPicFile12()));
            }
            session.setAttribute("error",0);
        } catch (IOException e) {
            session.setAttribute("error",1);
            throw new APIException(JsonStatus.DATA_SAVE_FAILED);
        }
        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try {
            Shop shop = shopService.insertShop(form);
            Long shopId = shopDetailService.insertShopDetail(form, shop.getId());
            Shop shop2 = shopService.getShopById(shop.getId());

            if(shop2 != null){
                RandomShopOne rso = new RandomShopOne();
                rso.setUuid(shop2.getUuid());
                rso.setShopType((byte)shop2.getShopType().intValue());
                rso.setArea((byte)0);
                rso.setNow4((byte)0);
                rso.setNow5((byte)0);
                rso.setPublishStart(shop2.getPublishStart());
                rso.setPublishEnd(shop2.getPublishEnd());
                rso.setCreateDatetime(shop2.getCreateDatetime());
                rso.setUpdateDatetime(shop2.getUpdateDatetime());
                rso.setDelFlg(shop2.getDelFlg());
                rso.setNote(shop2.getNote());
                randomShopService.insertRandomShopOne(rso);

                RandomShopTwo rst = new RandomShopTwo();
                rst.setUuid(shop2.getUuid());
                rst.setShopType((byte)shop2.getShopType().intValue());
                rst.setArea((byte)0);
                rst.setNow4((byte)0);
                rst.setNow5((byte)0);
                rst.setPublishStart(shop2.getPublishStart());
                rst.setPublishEnd(shop2.getPublishEnd());
                rst.setCreateDatetime(shop2.getCreateDatetime());
                rst.setUpdateDatetime(shop2.getUpdateDatetime());
                rst.setDelFlg(shop2.getDelFlg());
                rst.setNote(shop2.getNote());
                randomShopService.insertRandomShopTwo(rst);
            }


            if(form.getPicUrl7() != null && !form.getPicUrl7().equals("")){
                picService.insertShopPicUrl(form.getPicUrl7(),shop.getId());
            }else{
                picService.insertShopPicUrl("",shop.getId());
            }
            if(form.getPicUrl8() != null && !form.getPicUrl8().equals("")){
                picService.insertShopPicUrl(form.getPicUrl8(),shop.getId());
            }else{
                picService.insertShopPicUrl("",shop.getId());
            }
            if(form.getPicUrl9() != null && !form.getPicUrl9().equals("")){
                picService.insertShopPicUrl(form.getPicUrl9(),shop.getId());
            }else{
                picService.insertShopPicUrl("",shop.getId());
            }
            if(form.getPicUrl10() != null && !form.getPicUrl10().equals("")){
                picService.insertShopPicUrl(form.getPicUrl10(),shop.getId());
            }else{
                picService.insertShopPicUrl("",shop.getId());
            }
            if(form.getPicUrl11() != null && !form.getPicUrl11().equals("")){
                picService.insertShopPicUrl(form.getPicUrl11(),shop.getId());
            }else{
                picService.insertShopPicUrl("",shop.getId());
            }
            if(form.getPicUrl12() != null && !form.getPicUrl12().equals("")){
                picService.insertShopPicUrl(form.getPicUrl12(),shop.getId());
            }else{
                picService.insertShopPicUrl("",shop.getId());
            }

            form.setShopId(shop.getId());
            form.setUuid(shop.getUuid());
            Long role = adminService.insertAdminRole(form);
            form.setRole(role);
            AdminCustomer customer = adminService.insertAdminCustomer(form);
            form.setLoginName(customer.getLoginName());
            form.setCustomer(customer);

//            form.setUuid(shop.getUuid());
//            AdminCustomer customer = adminService.insertAdminCustomer(form);
//            form.setLoginName(customer.getLoginName());
//            adminService.insertAdminRole(form);

            int restRadio = 0;
            restRadio = form.getRestRadio();
            List<Integer> rest = new ArrayList<Integer>();

            List<ShopRestForm> stockList = form.getStock();
            if (stockList != null && stockList.size() > 0) {
                for (ShopRestForm ssf : stockList) {
                    if (ssf.getWeek() > 0 && ssf.getWeekDay() > 0) {
                        int week = ssf.getWeek();
                        int weekDay = ssf.getWeekDay();
                        String restStr = String.valueOf(week) + String.valueOf(weekDay);
                        if (restStr != null && restStr != "00") {
                            rest.add(Integer.valueOf(restStr));
                        }
                    }
                }
            }


            //食べる
            if (category == Constant.SHOP_TAG.FOOD) {
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.AREA, form.getArea());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_PRICE_LOW, form.getDayPriceLow());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_PRICE_LOW, form.getNightPriceLow());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getDayOpenTime());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_CLOSE_TIME, form.getDayCloseTime());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getDayLastOrder());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_OPEN_TIME, form.getNightOpenTime());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getNightCloseTime());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_LAST_ORDER, form.getNightLastOrder());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_WEEKDAY, form.getArea());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.STATION, form.getStation());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_PRICE_HIGH, form.getDayPriceHigh());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_PRICE_HIGH, form.getNightPriceHigh());
                shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.FIRST_MENU, form.getGenreList());
                if (restRadio == 2 && stockList != null && stockList.size() > 0) {
                    shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_WEEK, rest);
                }
                if (restRadio == 1 && form.getRestDates() != null ) {
                    shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_DATE, form.getRestDates());
                }
                shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.SCENE, form.getSceneList());
                shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.CHARACTER, form.getCharacterList());
            }

            //ファッション
            if (category == Constant.SHOP_TAG.FASTION) {
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.AREA, form.getArea());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.DAY_PRICE_LOW, form.getPriceLow());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.DAY_PRICE_HIGH, form.getPriceHigh());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.STATION, form.getStation());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
                shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.FIRST_MENU, form.getGenreList());
                shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.SCENE, form.getObjectList());
                shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.CHARACTER, form.getCharacterList());
                shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.BRAND, form.getBrandList());
                if (restRadio == 2 && stockList != null && stockList.size() > 0) {
                    shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.REST_WEEK, rest);
                }

                if (restRadio == 1 && form.getRestDates() != null) {
                    shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.REST_DATE, form.getRestDates());
                }
            }

            //健康ビューティー
            if (category == Constant.SHOP_TAG.HEALTH) {
                if (form.getFirstMenu() == Constant.SHOP_HEALTH_GENRE.SALON) {
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_PRICE_LOW, form.getPriceLow());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_PRICE_LOW, form.getNightPriceLow());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_PRICE_HIGH, form.getPriceHigh());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_PRICE_HIGH, form.getNightPriceHigh());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.SEAT, form.getSeat());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.MASSAGE_TIME, form.getMassageTime());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.AREA, form.getArea());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
                    shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.SECOND_MENU, form.getSecondMenu());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.STATION, form.getStation());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
                    shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.CHARACTER, form.getCharacterList());
                    if (restRadio == 2 && stockList != null && stockList.size() > 0) {
                        shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.REST_WEEK, rest);
                    }

                    if (restRadio == 1 && form.getRestDates() != null) {
                        shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.REST_DATE, form.getRestDates());
                    }
                } else if (form.getFirstMenu() == Constant.SHOP_HEALTH_GENRE.MASSAGE ||
                        form.getFirstMenu() == Constant.SHOP_HEALTH_GENRE.CHIROPRATIC || form.getFirstMenu() == Constant.SHOP_HEALTH_GENRE.COSMETIC_SURGERY) {
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_PRICE_LOW, form.getPriceLow());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_PRICE_LOW, form.getNightPriceLow());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_PRICE_HIGH, form.getPriceHigh());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_PRICE_HIGH, form.getNightPriceHigh());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.SEAT, form.getSeat());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.MASSAGE_TIME, form.getMassageTime());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.AREA, form.getArea());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.STATION, form.getStation());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
                    shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.CHARACTER, form.getCharacterList());
                    if (restRadio == 2 && stockList != null && stockList.size() > 0) {
                        shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.REST_WEEK, rest);
                    }

                    if (restRadio == 1 && form.getRestDates() != null) {
                        shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.REST_DATE, form.getRestDates());
                    }
                } else if (form.getFirstMenu() == Constant.SHOP_HEALTH_GENRE.COSMETICS || form.getFirstMenu() == Constant.SHOP_HEALTH_GENRE.DRUGSTORE ||
                        form.getFirstMenu() == Constant.SHOP_HEALTH_GENRE.SUPPLEMENT) {
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_PRICE_LOW, form.getPriceLow());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_PRICE_LOW, form.getNightPriceLow());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_PRICE_HIGH, form.getPriceHigh());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_PRICE_HIGH, form.getNightPriceHigh());
                    shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.BRAND, form.getBrandList());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.AREA, form.getArea());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.STATION, form.getStation());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
                    shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.CHARACTER, form.getCharacterList());
                    if (restRadio == 2 && stockList != null && stockList.size() > 0) {
                        shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.REST_WEEK, rest);
                    }

                    if (restRadio == 1 && form.getRestDates() != null) {
                        shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.REST_DATE, form.getRestDates());
                    }
                } else if (form.getFirstMenu() == Constant.SHOP_HEALTH_GENRE.HOT_SPRING || form.getFirstMenu() == Constant.SHOP_HEALTH_GENRE.TRAINING) {
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_PRICE_LOW, form.getPriceLow());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_PRICE_LOW, form.getNightPriceLow());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_PRICE_HIGH, form.getPriceHigh());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_PRICE_HIGH, form.getNightPriceHigh());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.AREA, form.getArea());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.STATION, form.getStation());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
                    shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.CHARACTER, form.getCharacterList());
                    if (restRadio == 2 && stockList != null && stockList.size() > 0) {
                        shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.REST_WEEK, rest);
                    }

                    if (restRadio == 1 && form.getRestDates() != null) {
                        shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.REST_DATE, form.getRestDates());
                    }
                }

            }

            //遊ぶ
            if (category == Constant.SHOP_TAG.PLAY) {
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.AREA, form.getArea());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.DAY_PRICE_LOW, form.getDayPriceLow());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.DAY_PRICE_HIGH, form.getDayPriceHigh());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.NIGHT_PRICE_LOW, form.getNightPriceLow());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.NIGHT_PRICE_HIGH, form.getNightPriceHigh());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.STATION, form.getStation());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
                shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.FIRST_MENU, form.getGenreList());
                shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.SCENE, form.getSceneList());
                shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.CHARACTER, form.getCharacterList());
                if (restRadio == 2 && stockList != null && stockList.size() > 0) {
                    shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.REST_WEEK, rest);
                }

                if (restRadio == 1 && form.getRestDates() != null) {
                    shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.REST_DATE, form.getRestDates());
                }
            }

            //ブライダル
            if (category == Constant.SHOP_TAG.BRIDAL) {
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.AREA, form.getArea());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.FIRST_MENU, form.getCeremony());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.SCENE, form.getPlace());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.STATION, form.getStation());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.HEADCOUNT, form.getHeadCount());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.FOOD_PRICE, form.getFoodPrice());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.DRINK_PRICE, form.getDrinkPrice());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.CORKAGE_PRICE, form.getCorkagePrice());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.CONTACT_TIME, form.getContactTime());
                shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.CHARACTER, form.getCharacterList());
                if (restRadio == 2 && stockList != null && stockList.size() > 0) {
                    shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.REST_WEEK, rest);
                }

                if (restRadio == 1 && form.getRestDates() != null) {
                    shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.REST_DATE, form.getRestDates());
                }
            }

            //乗る
            if (category == Constant.SHOP_TAG.DRIVE) {
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.AREA, form.getArea());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.DAY_PRICE_LOW, form.getAveragePriceLow());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.DAY_PRICE_HIGH, form.getAveragePriceHigh());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.STATION, form.getStation());
                shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
                shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.FIRST_MENU, form.getGenreList());
                shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.SCENE, form.getObjectList());
                shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.CHARACTER, form.getCharacterList());
                if (restRadio == 2 && stockList != null && stockList.size() > 0) {
                    shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.REST_WEEK, rest);
                }

                if (restRadio == 1 && form.getRestDates() != null) {
                    shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.REST_DATE, form.getRestDates());
                }
            }

            //学ぶ
            if (category == Constant.SHOP_TAG.LEARNING) {

                if (form.getFirstMenu() == Constant.SHOP_LEARN_GENRE.SCHOOL) {

                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.AREA, form.getArea());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
                    shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.SECOND_MENU, form.getSecondMenu());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.SCENE, form.getScene());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.STATION, form.getStation());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
                    if (restRadio == 2 && stockList != null && stockList.size() > 0) {
                        shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.REST_WEEK, rest);
                    }

                    if (restRadio == 1 && form.getRestDates() != null) {
                        shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.REST_DATE, form.getRestDates());
                    }

                } else if (form.getFirstMenu() == Constant.SHOP_LEARN_GENRE.PRE_SCHOOL || form.getFirstMenu() == Constant.SHOP_LEARN_GENRE.CRAM_SCHOOL ||
                        form.getFirstMenu() == Constant.SHOP_LEARN_GENRE.CONVERSATION || form.getFirstMenu() == Constant.SHOP_LEARN_GENRE.COOKING_CLASS ||
                        form.getFirstMenu() == Constant.SHOP_LEARN_GENRE.DANCE_CLASS || form.getFirstMenu() == Constant.SHOP_LEARN_GENRE.MUSIC_CLASS ||
                        form.getFirstMenu() == Constant.SHOP_LEARN_GENRE.SPORTS_SCHOOL || form.getFirstMenu() == Constant.SHOP_LEARN_GENRE.CHILD_EDUCATION ||
                        form.getFirstMenu() == Constant.SHOP_LEARN_GENRE.QULIFICATION || form.getFirstMenu() == Constant.SHOP_LEARN_GENRE.IT_CLASS) {

                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.AREA, form.getArea());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.SCENE, form.getScene());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.DAY_PRICE_LOW, form.getPriceLow());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.DAY_PRICE_HIGH, form.getPriceHigh());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.NIGHT_PRICE_LOW, form.getNightPriceLow());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.NIGHT_PRICE_HIGH, form.getNightPriceHigh());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.STATION, form.getStation());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
                    shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.CHARACTER, form.getCharacterList());
                    if (restRadio == 2 && stockList != null && stockList.size() > 0) {
                        shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.REST_WEEK, rest);
                    }

                    if (restRadio == 1 && form.getRestDates() != null) {
                        shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.REST_DATE, form.getRestDates());
                    }
                }
            }

            //施設
            if (category == Constant.SHOP_TAG.FACILITY) {

                if (form.getFirstMenu() == Constant.SHOP_FACILITY_GENRE.ACCOMMODATION) {
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.AREA, form.getArea());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
                    shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.SECOND_MENU, form.getSecondMenu());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_PRICE_LOW, form.getDayPriceLow());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_PRICE_HIGH, form.getDayPriceHigh());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.NIGHT_PRICE_LOW, form.getNightPriceLow());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.NIGHT_PRICE_HIGH, form.getNightPriceHigh());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.STATION, form.getStation());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.ROOM_TYPE, form.getRoomType());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.ROOM_NUM, form.getRoomNum());
                    shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.CHARACTER, form.getCharacterList());
                    if (restRadio == 2 && stockList != null && stockList.size() > 0) {
                        shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.REST_WEEK, rest);
                    }

                    if (restRadio == 1 && form.getRestDates() != null) {
                        shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.REST_DATE, form.getRestDates());
                    }

                } else if (form.getFirstMenu() == Constant.SHOP_FACILITY_GENRE.TEMPLE) {
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.AREA, form.getArea());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.STATION, form.getStation());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.HEADCOUNT, form.getHeadCount());
                    shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.CHARACTER, form.getCharacterList());
                    if (restRadio == 2 && stockList != null && stockList.size() > 0) {
                        shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.REST_WEEK, rest);
                    }

                    if (restRadio == 1 && form.getRestDates() != null) {
                        shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.REST_DATE, form.getRestDates());
                    }

                } else if (form.getFirstMenu() == Constant.SHOP_FACILITY_GENRE.PARADE) {
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.AREA, form.getArea());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.STATION, form.getStation());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
                    shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.CHARACTER, form.getCharacterList());
                    if (restRadio == 2 && stockList != null && stockList.size() > 0) {
                        shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.REST_WEEK, rest);
                    }

                    if (restRadio == 1 && form.getRestDates() != null) {
                        shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.REST_DATE, form.getRestDates());
                    }

                } else if ((form.getFirstMenu() == Constant.SHOP_FACILITY_GENRE.COMMUNAL || form.getFirstMenu() == Constant.SHOP_FACILITY_GENRE.COMMERCIAL)) {
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.AREA, form.getArea());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
                    shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.SECOND_MENU, form.getSecondMenu());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.STATION, form.getStation());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
                    shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.CHARACTER, form.getCharacterList());
                    if (restRadio == 2 && stockList != null && stockList.size() > 0) {
                        shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.REST_WEEK, rest);
                    }

                    if (restRadio == 1 && form.getRestDates() != null) {
                        shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.REST_DATE, form.getRestDates());
                    }
                }
            }

            //ライフスタイル
            if (category == Constant.SHOP_TAG.LIFE) {

                if (form.getFirstMenu() == Constant.SHOP_LIFE_GENRE.LIFE) {
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.AREA, form.getArea());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
                    shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.SECOND_MENU, form.getSecondMenu());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.DAY_PRICE_LOW, form.getPriceLow());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.DAY_PRICE_HIGH, form.getPriceHigh());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.STATION, form.getStation());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
                    shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.CHARACTER, form.getCharacterList());
                    if (restRadio == 2 && stockList != null && stockList.size() > 0) {
                        shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.REST_WEEK, rest);
                    }

                    if (restRadio == 1 && form.getRestDates() != null) {
                        shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.REST_DATE, form.getRestDates());
                    }

                } else if (form.getFirstMenu() == Constant.SHOP_LIFE_GENRE.PET || form.getFirstMenu() == Constant.SHOP_LIFE_GENRE.PURCHASE) {
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.AREA, form.getArea());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.STATION, form.getStation());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
                    shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.CHARACTER, form.getCharacterList());
                    if (restRadio == 2 && stockList != null && stockList.size() > 0) {
                        shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.REST_WEEK, rest);
                    }

                    if (restRadio == 1 && form.getRestDates() != null) {
                        shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.REST_DATE, form.getRestDates());
                    }

                } else if (form.getFirstMenu() == Constant.SHOP_LIFE_GENRE.HOUSE || form.getFirstMenu() == Constant.SHOP_LIFE_GENRE.HOBBY ||
                        form.getFirstMenu() == Constant.SHOP_LIFE_GENRE.FINANCIAL || form.getFirstMenu() == Constant.SHOP_LIFE_GENRE.HOSPITAL) {
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.AREA, form.getArea());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
                    shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.SECOND_MENU, form.getSecondMenu());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.STATION, form.getStation());
                    shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
                    shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.CHARACTER, form.getCharacterList());
                    if (restRadio == 2 && stockList != null && stockList.size() > 0) {
                        shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.REST_WEEK, rest);
                    }
                    if (restRadio == 1 && form.getRestDates() != null) {
                        shopTagService.insertShopTags(shop.getId(), Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.REST_DATE, form.getRestDates());
                    }
                }
            }
            txManager.commit(txStatus);
            session.setAttribute("error",0);
        } catch (Exception e) {
            txManager.rollback(txStatus);
            session.setAttribute("error",1);
            logger.error("regist shop  failed!, error=" + e.getMessage());
            logger.error("regist shop  failed!, error=" + e.toString());
        }

//        return "redirect:/admin/role/confirm/"+form.getRole()+"/";
        RoleFrom roleFrom = new RoleFrom();
        roleFrom.setPassword(form.getCustomer().getPwd());
        AdminCustomer customer = adminService.selectAdminCustomerByRole(form.getRole());
        AdminRole role = adminService.selectById(form.getRole());
        roleFrom.setType(role.getType());
        roleFrom.setPrivilege(role.getPrivilege());
        roleFrom.setShowName(customer.getShowName());
        roleFrom.setLoginName(customer.getLoginName());
        roleFrom.setStatus(customer.getStatus());
        roleFrom.setMail(customer.getMail());
        roleFrom.setFcType(customer.getFcType());
        roleFrom.setFcAdminId(customer.getFcAdminId());
        roleFrom.setCustomerType(customer.getCustomerType());
        model.addAttribute("roleFrom", roleFrom);

        return "admin/roleConfirm";
    }

    @RequestMapping(value = "/admin/shop/edit/{id}/", method = RequestMethod.GET)
    public String shopUpdate(Model model, @PathVariable Long id) {

        ShopRegistForm form = new ShopRegistForm();

        ShopDetail shopDetail = shopDetailService.getShopDetailByShopId(id);
        Shop shop = shopService.getShopById(shopDetail.getShopListId());
        int shopType = shopDetailService.getShopType(shopDetail.getShopListId());
        String coordinate1 = shop.getCoordinate1();
        String coordinate2 = shop.getCoordinate2();

        List<Pic> listPic = picService.getShopUrlList(shop.getId(),Constant.EVENT_PIC_TYPE.SHOP);
        if(listPic != null && listPic.size()>0){
            if(listPic.get(0).getPicUrl() != null && listPic.get(0).getPicUrl() !=""){
                form.setPicUrl7(listPic.get(0).getPicUrl());
            }
            if(listPic.size()>1 && listPic.get(1).getPicUrl() != null && listPic.get(1).getPicUrl() !=""){
                form.setPicUrl8(listPic.get(1).getPicUrl());
            }
            if(listPic.size()>2 && listPic.get(2).getPicUrl() != null && listPic.get(2).getPicUrl() !=""){
                form.setPicUrl9(listPic.get(2).getPicUrl());
            }
            if(listPic.size()>3 && listPic.get(3).getPicUrl() != null && listPic.get(3).getPicUrl() !=""){
                form.setPicUrl10(listPic.get(3).getPicUrl());
            }
            if(listPic.size()>4 && listPic.get(4).getPicUrl() != null && listPic.get(4).getPicUrl() !=""){
                form.setVideoStatus("pic");
                form.setPicUrl11(listPic.get(4).getPicUrl());
            }
            if(listPic.size()>5 && listPic.get(5).getPicUrl() != null && listPic.get(5).getPicUrl() !=""){
                form.setFlicStatus("pic");
                form.setPicUrl12(listPic.get(5).getPicUrl());
            }
        }

        form.setNow5(shop.getNow5());
        form.setUuid(shop.getUuid());
        form.setCoordinate1(coordinate1);
        form.setCoordinate2(coordinate2);

        List<Integer> restWeeksList = new ArrayList<Integer>();
        List<Integer> restWeekDaysList = new ArrayList<Integer>();
        List<TagDetail> brandTagList = new ArrayList<TagDetail>();
        List<TagDetail> characterTagList = new ArrayList<TagDetail>();
        List<TagDetail> sceneTagList = new ArrayList<TagDetail>();
        List<TagDetail> firstMenuList = new ArrayList<TagDetail>();
        model.addAttribute("brandMap", getMap(brandTagList));
        model.addAttribute("characterMap", getMap(characterTagList));
        model.addAttribute("firstMenuMap", getMap(firstMenuList));
        model.addAttribute("sceneMap", getMap(sceneTagList));

        if (shopType == Constant.SHOP_TAG.FOOD) {

            int area = shopTagService.getContent(id, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.AREA);
            int dayPriceLow = shopTagService.getContent(id, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_PRICE_LOW);
            int nightPriceLow = shopTagService.getContent(id, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_PRICE_LOW);
            int lunchOpen = shopTagService.getContent(id, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_OPEN_TIME);
            int lunchClose = shopTagService.getContent(id, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_CLOSE_TIME);
            int lunchLastOrder = shopTagService.getContent(id, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_LAST_ORDER);
            int dinnerOpen = shopTagService.getContent(id, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_OPEN_TIME);
            int dinnerClose = shopTagService.getContent(id, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_CLOSE_TIME);
            int dinnerLastOrder = shopTagService.getContent(id, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_LAST_ORDER);
            int station = shopTagService.getContent(id, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.STATION);
            int walkTime = shopTagService.getContent(id, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.WALK_TIME);
            int seat = shopTagService.getContent(id, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.SEAT);
            int dayPriceHigh = shopTagService.getContent(id, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_PRICE_HIGH);
            int nightPriceHigh = shopTagService.getContent(id, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_PRICE_HIGH);
            List<Integer> genreList = shopTagService.getContents(id, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.FIRST_MENU);
            List<Integer> sceneList = shopTagService.getContents(id, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.SCENE);
            List<Integer> characterList = shopTagService.getContents(id, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.CHARACTER);
            List<Integer> restDateList = shopTagService.getContents(id, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_DATE);
            List<Integer> restWeekList = shopTagService.getContents(id, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_WEEK);

            List<ShopRestForm> restFormList = new ArrayList<ShopRestForm>();
            int amountId = 0;
            if (restWeekList != null) {
                for (Integer rw : restWeekList) {
                    String w = String.valueOf(rw);
                    Integer wNum = Integer.valueOf(w.substring(0, 1));
                    Integer wdNum = Integer.valueOf(w.substring(1, 2));
                    restWeeksList.add(wNum);
                    restWeekDaysList.add(wdNum);
                    ShopRestForm ssf = new ShopRestForm();

                    amountId = amountId + 1;
                    ssf.setWeek(wNum);
                    ssf.setWeekDay(wdNum);
                    ssf.setAmountId(amountId);
                    restFormList.add(ssf);
                }
            }

            form.setShopId(id);
            form.setShopListId(shop.getId());
            form.setExcerpt(shop.getExcerpt());
            form.setPicUrl(shop.getPicUrl());
            form.setArea(area);
            form.setDayPriceLow(dayPriceLow);
            form.setNightPriceLow(nightPriceLow);
            form.setDayOpenTime(lunchOpen);
            form.setDayCloseTime(lunchClose);
            form.setDayLastOrder(lunchLastOrder);
            form.setNightOpenTime(dinnerOpen);
            form.setNightCloseTime(dinnerClose);
            form.setNightLastOrder(dinnerLastOrder);
            form.setGenreList(genreList);
            form.setSceneList(sceneList);
            form.setSeat(seat);
            form.setStation(station);
            form.setWalkTime(walkTime);
            form.setDayPriceHigh(dayPriceHigh);
            form.setNightPriceHigh(nightPriceHigh);
            form.setCharacterList(characterList);
            form.setRestWeek(restWeeksList);
            form.setRestWeekDay(restWeekDaysList);
            form.setRestDates(restDateList);

            firstMenuList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.FIRST_MENU);
            List<TagDetail> secondMenuList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.SECOND_MENU);
            List<TagDetail> areaTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.AREA);
            List<TagDetail> stationTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.STATION);
            List<TagDetail> dayPriceTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_PRICE_LOW);
            List<TagDetail> nightPriceTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_PRICE_LOW);
            List<TagDetail> shopOpenTimeTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_OPEN_TIME);
            List<TagDetail> shopCloseTimeTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_OPEN_TIME);
            characterTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.CHARACTER);
            sceneTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.SCENE);
            List<TagDetail> seatTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.SEAT);
            List<TagDetail> restDayTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_DATE);
            List<TagDetail> restWeekTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_WEEK);
            List<TagDetail> restWeekDayTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_WEEKDAY);

            if (shop != null) {
                BeanUtils.copyProperties(shopDetail, form);
                form.setPublishStart(DateUtil.dateToStringyyyy_MM_dd(shop.getPublishStart()));
                form.setPublishEnd(DateUtil.dateToStringyyyy_MM_dd(shop.getPublishEnd()));
                model.addAttribute("firstMenuMap", getMap(firstMenuList));
                model.addAttribute("secondMenuList", secondMenuList);
                model.addAttribute("areaList", areaTagList);
                model.addAttribute("stationList", stationTagList);
                model.addAttribute("dayPriceList", dayPriceTagList);
                model.addAttribute("nightPriceList", nightPriceTagList);
                model.addAttribute("shopOpenTimeList", shopOpenTimeTagList);
                model.addAttribute("shopCloseTimeList", shopCloseTimeTagList);
                model.addAttribute("sceneMap", getMap(sceneTagList));
                model.addAttribute("seatTagList", seatTagList);
                model.addAttribute("restDateList", restDayTagList);
                model.addAttribute("restWeekList", restWeekTagList);
                model.addAttribute("restWeekDayList", restWeekDayTagList);
                model.addAttribute("characterMap", getMap(characterTagList));
                model.addAttribute("shopForm", form);
                model.addAttribute("restFormList", restFormList);
                return "admin/shopEdit";
            } else {
                return "redirect:/admin/shop/list/";
            }
        }

        if (shopType == Constant.SHOP_TAG.FASTION) {

            int area = shopTagService.getContent(id, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.AREA);
            int shopOpen = shopTagService.getContent(id, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.DAY_OPEN_TIME);
            int shopClose = shopTagService.getContent(id, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.NIGHT_CLOSE_TIME);
            int lastOrder = shopTagService.getContent(id, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.DAY_LAST_ORDER);
            int firstMenu = shopTagService.getContent(id, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.FIRST_MENU);
            int scene = shopTagService.getContent(id, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.SCENE);
            int station = shopTagService.getContent(id, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.STATION);
            int walkTime = shopTagService.getContent(id, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.WALK_TIME);
            int lowPrice = shopTagService.getContent(id, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.DAY_PRICE_LOW);
            int highPrice = shopTagService.getContent(id, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.DAY_PRICE_HIGH);
            List<Integer> genreList = shopTagService.getContents(id, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.FIRST_MENU);
            List<Integer> sceneList = shopTagService.getContents(id, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.SCENE);
            List<Integer> characterList = shopTagService.getContents(id, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.CHARACTER);
            List<Integer> brandList = shopTagService.getContents(id, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.CHARACTER);
            List<Integer> restDateList = shopTagService.getContents(id, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.REST_DATE);
            List<Integer> restWeekList = shopTagService.getContents(id, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.REST_WEEK);

            List<ShopRestForm> restFormList = new ArrayList<ShopRestForm>();
            int amountId = 0;
            if (restWeekList != null) {
                for (Integer rw : restWeekList) {
                    if (rw != null) {
                        String w = String.valueOf(rw);
                        Integer wNum = Integer.valueOf(w.substring(0, 1));
                        Integer wdNum = Integer.valueOf(w.substring(1, 2));
                        restWeeksList.add(wNum);
                        restWeekDaysList.add(wdNum);
                        ShopRestForm ssf = new ShopRestForm();

                        amountId = amountId + 1;
                        ssf.setWeek(wNum);
                        ssf.setWeekDay(wdNum);
                        ssf.setAmountId(amountId);
                        restFormList.add(ssf);
                    }
                }
            }

            form.setShopId(id);
            form.setShopListId(shop.getId());
            form.setExcerpt(shop.getExcerpt());
            form.setPicUrl(shop.getPicUrl());
            form.setArea(area);
            form.setPriceLow(lowPrice);
            form.setPriceHigh(highPrice);
            form.setOpenTime(shopOpen);
            form.setCloseTime(shopClose);
            form.setLastOrder(lastOrder);
            form.setGenreList(genreList);
            form.setObjectList(sceneList);
            form.setStation(station);
            form.setWalkTime(walkTime);
            form.setCharacterList(characterList);
            form.setBrandList(brandList);
            form.setRestWeek(restWeeksList);
            form.setRestWeekDay(restWeekDaysList);
            form.setRestDates(restDateList);

            List<TagDetail> areaTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.AREA);
            List<TagDetail> stationTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.STATION);
            List<TagDetail> dayPriceTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_PRICE_LOW);
            List<TagDetail> shopOpenTimeTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_OPEN_TIME);
            List<TagDetail> shopCloseTimeTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_OPEN_TIME);
            firstMenuList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.FIRST_MENU);
            characterTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.CHARACTER);
            sceneTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.SCENE);
            brandTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.BRAND);
            List<TagDetail> restDayTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_DATE);
            List<TagDetail> restWeekTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_WEEK);
            List<TagDetail> restWeekDayTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_WEEKDAY);

            if (shop != null) {
                BeanUtils.copyProperties(shopDetail, form);
                form.setPublishStart(DateUtil.dateToStringyyyy_MM_dd(shop.getPublishStart()));
                form.setPublishEnd(DateUtil.dateToStringyyyy_MM_dd(shop.getPublishEnd()));
                model.addAttribute("firstMenuMap", getMap(firstMenuList));
                model.addAttribute("areaList", areaTagList);
                model.addAttribute("stationList", stationTagList);
                model.addAttribute("dayPriceList", dayPriceTagList);
                model.addAttribute("shopOpenTimeList", shopOpenTimeTagList);
                model.addAttribute("shopCloseTimeList", shopCloseTimeTagList);
                model.addAttribute("sceneMap", getMap(sceneTagList));
                model.addAttribute("restDateList", restDayTagList);
                model.addAttribute("restWeekList", restWeekTagList);
                model.addAttribute("restWeekDayList", restWeekDayTagList);
                model.addAttribute("characterMap", getMap(characterTagList));
                model.addAttribute("brandMap", getMap(brandTagList));
                model.addAttribute("shopForm", form);
                model.addAttribute("restFormList", restFormList);
                return "admin/shopEdit";
            } else {
                return "redirect:/admin/shop/list/";
            }
        }

        if (shopType == Constant.SHOP_TAG.HEALTH) {

            int area = shopTagService.getContent(id, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.AREA);
            int shopOpen = shopTagService.getContent(id, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_OPEN_TIME);
            int shopClose = shopTagService.getContent(id, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_CLOSE_TIME);
            int lastOrder = shopTagService.getContent(id, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_LAST_ORDER);
            int firstMenu = shopTagService.getContent(id, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.FIRST_MENU);
            List<Integer> secondMenu = shopTagService.getContents(id, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.SECOND_MENU);
            int station = shopTagService.getContent(id, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.STATION);
            int walkTime = shopTagService.getContent(id, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.WALK_TIME);
            int dayPriceLow = shopTagService.getContent(id, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_PRICE_LOW);
            int nightPriceLow = shopTagService.getContent(id, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_PRICE_LOW);
            int dayPriceHigh = shopTagService.getContent(id, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_PRICE_HIGH);
            int nightPriceHigh = shopTagService.getContent(id, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_PRICE_HIGH);
            int seat = shopTagService.getContent(id, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.SEAT);
            int massageTime = shopTagService.getContent(id, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.MASSAGE_TIME);
            List<Integer> characterList = shopTagService.getContents(id, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.CHARACTER);
            List<Integer> brandList = shopTagService.getContents(id, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.CHARACTER);
            List<Integer> restDateList = shopTagService.getContents(id, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.REST_DATE);
            List<Integer> restWeekList = shopTagService.getContents(id, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.REST_WEEK);

            List<ShopRestForm> restFormList = new ArrayList<ShopRestForm>();
            int amountId = 0;
            if (restWeekList != null) {
                for (Integer rw : restWeekList) {
                    String w = String.valueOf(rw);
                    Integer wNum = Integer.valueOf(w.substring(0, 1));
                    Integer wdNum = Integer.valueOf(w.substring(1, 2));
                    restWeeksList.add(wNum);
                    restWeekDaysList.add(wdNum);
                    ShopRestForm ssf = new ShopRestForm();

                    amountId = amountId + 1;
                    ssf.setWeek(wNum);
                    ssf.setWeekDay(wdNum);
                    ssf.setAmountId(amountId);
                    restFormList.add(ssf);
                }
            }

            form.setShopId(id);
            form.setShopListId(shop.getId());
            form.setExcerpt(shop.getExcerpt());
            form.setPicUrl(shop.getPicUrl());
            form.setArea(area);
//            form.setDayPriceLow(dayPriceLow);
//            form.setNightPriceLow(nightPriceLow);
//            form.setDayPriceHigh(dayPriceHigh);
//            form.setNightPriceHigh(nightPriceHigh);
            form.setPriceLow(dayPriceLow);
            form.setPriceHigh(dayPriceHigh);
            form.setOpenTime(shopOpen);
            form.setCloseTime(shopClose);
            form.setLastOrder(lastOrder);
            form.setFirstMenu(firstMenu);
            form.setSecondMenu(secondMenu);
            form.setStation(station);
            form.setWalkTime(walkTime);
            form.setSeat(seat);
            form.setMassageTime(massageTime);
            form.setCharacterList(characterList);
            form.setBrandList(brandList);
            form.setRestWeek(restWeeksList);
            form.setRestWeekDay(restWeekDaysList);
            form.setRestDates(restDateList);

            List<TagDetail> areaTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.AREA);
            List<TagDetail> stationTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.STATION);
            List<TagDetail> dayPriceTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_PRICE_LOW);
            List<TagDetail> nightPriceTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_PRICE_LOW);
            List<TagDetail> shopOpenTimeTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_OPEN_TIME);
            List<TagDetail> shopCloseTimeTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_OPEN_TIME);
            firstMenuList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.FIRST_MENU);
            List<TagDetail> secondMenuList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.SECOND_MENU);
            List<TagDetail> massageTimeTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.MASSAGE_TIME);
            List<TagDetail> seatTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.SEAT);
            characterTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.CHARACTER);
            sceneTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.SCENE);
            brandTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.BRAND);
            List<TagDetail> restDayTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_DATE);
            List<TagDetail> restWeekTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_WEEK);
            List<TagDetail> restWeekDayTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_WEEKDAY);


            if (shop != null) {
                BeanUtils.copyProperties(shopDetail, form);
                form.setPublishStart(DateUtil.dateToStringyyyy_MM_dd(shop.getPublishStart()));
                form.setPublishEnd(DateUtil.dateToStringyyyy_MM_dd(shop.getPublishEnd()));
                model.addAttribute("firstMenuList", firstMenuList);
                model.addAttribute("secondMenuList", secondMenuList);
                model.addAttribute("areaList", areaTagList);
                model.addAttribute("stationList", stationTagList);
                model.addAttribute("dayPriceList", dayPriceTagList);
                model.addAttribute("nightPriceList", nightPriceTagList);
                model.addAttribute("shopOpenTimeList", shopOpenTimeTagList);
                model.addAttribute("shopCloseTimeList", shopCloseTimeTagList);
                model.addAttribute("restDateList", restDayTagList);
                model.addAttribute("restWeekList", restWeekTagList);
                model.addAttribute("restWeekDayList", restWeekDayTagList);
                model.addAttribute("sceneList", sceneTagList);
                model.addAttribute("seatTagList", seatTagList);
                model.addAttribute("massageTimeTagList", massageTimeTagList);
                model.addAttribute("characterMap", getMap(characterTagList));
                model.addAttribute("brandMap", getMap(brandTagList));
                model.addAttribute("shopForm", form);
                model.addAttribute("restFormList", restFormList);
                return "admin/shopEdit";
            } else {
                return "redirect:/admin/shop/list/";
            }
        }

        if (shopType == Constant.SHOP_TAG.PLAY) {

            int area = shopTagService.getContent(id, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.AREA);
            int shopOpen = shopTagService.getContent(id, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.DAY_OPEN_TIME);
            int shopClose = shopTagService.getContent(id, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.NIGHT_CLOSE_TIME);
            int lastOrder = shopTagService.getContent(id, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.DAY_LAST_ORDER);
            int station = shopTagService.getContent(id, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.STATION);
            int walkTime = shopTagService.getContent(id, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.WALK_TIME);
            int dayPricelow = shopTagService.getContent(id, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.DAY_PRICE_LOW);
            int nightPriceLow = shopTagService.getContent(id, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.NIGHT_PRICE_LOW);
            int dayPriceHigh = shopTagService.getContent(id, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.DAY_PRICE_HIGH);
            int nightPriceHigh = shopTagService.getContent(id, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.NIGHT_PRICE_HIGH);
            List<Integer> genreList = shopTagService.getContents(id, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.FIRST_MENU);
            List<Integer> sceneList = shopTagService.getContents(id, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.SCENE);
            List<Integer> characterList = shopTagService.getContents(id, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.CHARACTER);
            List<Integer> restDateList = shopTagService.getContents(id, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.REST_DATE);
            List<Integer> restWeekList = shopTagService.getContents(id, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.REST_WEEK);

            List<ShopRestForm> restFormList = new ArrayList<ShopRestForm>();
            int amountId = 0;
            if (restWeekList != null) {
                for (Integer rw : restWeekList) {
                    String w = String.valueOf(rw);
                    Integer wNum = Integer.valueOf(w.substring(0, 1));
                    Integer wdNum = Integer.valueOf(w.substring(1, 2));
                    restWeeksList.add(wNum);
                    restWeekDaysList.add(wdNum);
                    ShopRestForm ssf = new ShopRestForm();

                    amountId = amountId + 1;
                    ssf.setWeek(wNum);
                    ssf.setWeekDay(wdNum);
                    ssf.setAmountId(amountId);
                    restFormList.add(ssf);
                }
            }

            form.setShopId(id);
            form.setShopListId(shop.getId());
            form.setExcerpt(shop.getExcerpt());
            form.setPicUrl(shop.getPicUrl());
            form.setArea(area);
            form.setDayPriceLow(dayPricelow);
            form.setDayPriceHigh(dayPriceHigh);
            form.setNightPriceLow(nightPriceLow);
            form.setNightPriceHigh(nightPriceHigh);
            form.setOpenTime(shopOpen);
            form.setCloseTime(shopClose);
            form.setLastOrder(lastOrder);
            form.setGenreList(genreList);
            form.setStation(station);
            form.setWalkTime(walkTime);
            form.setSceneList(sceneList);
            form.setCharacterList(characterList);
            form.setRestWeek(restWeeksList);
            form.setRestWeekDay(restWeekDaysList);
            form.setRestDates(restDateList);

            List<TagDetail> areaTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.AREA);
            List<TagDetail> stationTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.STATION);
            List<TagDetail> dayPriceTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_PRICE_LOW);
            List<TagDetail> nightPriceTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_PRICE_LOW);
            List<TagDetail> shopOpenTimeTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_OPEN_TIME);
            List<TagDetail> shopCloseTimeTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_OPEN_TIME);
            firstMenuList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.FIRST_MENU);
            characterTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.CHARACTER);
            sceneTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.SCENE);
            List<TagDetail> restDayTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_DATE);
            List<TagDetail> restWeekTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_WEEK);
            List<TagDetail> restWeekDayTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_WEEKDAY);


            if (shop != null) {
                BeanUtils.copyProperties(shopDetail, form);
                form.setPublishStart(DateUtil.dateToStringyyyy_MM_dd(shop.getPublishStart()));
                form.setPublishEnd(DateUtil.dateToStringyyyy_MM_dd(shop.getPublishEnd()));
                model.addAttribute("firstMenuMap", getMap(firstMenuList));
                model.addAttribute("areaList", areaTagList);
                model.addAttribute("stationList", stationTagList);
                model.addAttribute("dayPriceList", dayPriceTagList);
                model.addAttribute("nightPriceList", nightPriceTagList);
                model.addAttribute("shopOpenTimeList", shopOpenTimeTagList);
                model.addAttribute("shopCloseTimeList", shopCloseTimeTagList);
                model.addAttribute("restDateList", restDayTagList);
                model.addAttribute("restWeekList", restWeekTagList);
                model.addAttribute("restWeekDayList", restWeekDayTagList);
                model.addAttribute("sceneMap", getMap(sceneTagList));
                model.addAttribute("characterMap", getMap(characterTagList));
                model.addAttribute("shopForm", form);
                model.addAttribute("restFormList", restFormList);
                return "admin/shopEdit";
            } else {
                return "redirect:/admin/shop/list/";
            }
        }

        if (shopType == Constant.SHOP_TAG.BRIDAL) {

            int area = shopTagService.getContent(id, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.AREA);
            int shopOpen = shopTagService.getContent(id, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.DAY_OPEN_TIME);
            int shopClose = shopTagService.getContent(id, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.NIGHT_CLOSE_TIME);
            int lastOrder = shopTagService.getContent(id, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.DAY_LAST_ORDER);
            int firstMenu = shopTagService.getContent(id, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.FIRST_MENU);
            int station = shopTagService.getContent(id, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.STATION);
            int walkTime = shopTagService.getContent(id, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.WALK_TIME);
            int headCount = shopTagService.getContent(id, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.HEADCOUNT);
            int contactTime = shopTagService.getContent(id, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.CONTACT_TIME);
            int foodPrice = shopTagService.getContent(id, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.FOOD_PRICE);
            int drinkPrice = shopTagService.getContent(id, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.DRINK_PRICE);
            int corkagePrice = shopTagService.getContent(id, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.CORKAGE_PRICE);
            int scene = shopTagService.getContent(id, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.SCENE);
            List<Integer> characterList = shopTagService.getContents(id, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.CHARACTER);
            List<Integer> restDateList = shopTagService.getContents(id, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.REST_DATE);
            List<Integer> restWeekList = shopTagService.getContents(id, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.REST_WEEK);

            List<ShopRestForm> restFormList = new ArrayList<ShopRestForm>();
            int amountId = 0;
            if (restWeekList != null) {
                for (Integer rw : restWeekList) {
                    String w = String.valueOf(rw);
                    Integer wNum = Integer.valueOf(w.substring(0, 1));
                    Integer wdNum = Integer.valueOf(w.substring(1, 2));
                    restWeeksList.add(wNum);
                    restWeekDaysList.add(wdNum);
                    ShopRestForm ssf = new ShopRestForm();

                    amountId = amountId + 1;
                    ssf.setWeek(wNum);
                    ssf.setWeekDay(wdNum);
                    ssf.setAmountId(amountId);
                    restFormList.add(ssf);
                }
            }

            form.setShopId(id);
            form.setShopListId(shop.getId());
            form.setExcerpt(shop.getExcerpt());
            form.setPicUrl(shop.getPicUrl());
            form.setArea(area);
            form.setHeadCount(headCount);
            form.setContactTime(contactTime);
            form.setFoodPrice(foodPrice);
            form.setDrinkPrice(drinkPrice);
            form.setCorkagePrice(corkagePrice);
            form.setOpenTime(shopOpen);
            form.setCloseTime(shopClose);
            form.setLastOrder(lastOrder);
            form.setCeremony(firstMenu);
            form.setStation(station);
            form.setWalkTime(walkTime);
            form.setPlace(scene);
            form.setCharacterList(characterList);
            form.setRestWeek(restWeeksList);
            form.setRestWeekDay(restWeekDaysList);
            form.setRestDates(restDateList);

            List<TagDetail> areaTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.AREA);
            List<TagDetail> stationTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.STATION);
            List<TagDetail> dayPriceTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_PRICE_LOW);
            List<TagDetail> shopOpenTimeTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_OPEN_TIME);
            List<TagDetail> shopCloseTimeTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_OPEN_TIME);
            firstMenuList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.FIRST_MENU);
            characterTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.CHARACTER);
            sceneTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.SCENE);
            List<TagDetail> headCountTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.HEADCOUNT);
            List<TagDetail> restDayTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_DATE);
            List<TagDetail> restWeekTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_WEEK);
            List<TagDetail> restWeekDayTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_WEEKDAY);


            if (shop != null) {
                BeanUtils.copyProperties(shopDetail, form);
                form.setPublishStart(DateUtil.dateToStringyyyy_MM_dd(shop.getPublishStart()));
                form.setPublishEnd(DateUtil.dateToStringyyyy_MM_dd(shop.getPublishEnd()));
                model.addAttribute("firstMenuList", firstMenuList);
                model.addAttribute("areaList", areaTagList);
                model.addAttribute("stationList", stationTagList);
                model.addAttribute("dayPriceList", dayPriceTagList);
                model.addAttribute("shopOpenTimeList", shopOpenTimeTagList);
                model.addAttribute("shopCloseTimeList", shopCloseTimeTagList);
                model.addAttribute("sceneList", sceneTagList);
                model.addAttribute("restDateList", restDayTagList);
                model.addAttribute("restWeekList", restWeekTagList);
                model.addAttribute("restWeekDayList", restWeekDayTagList);
                model.addAttribute("headCountList", headCountTagList);
                model.addAttribute("characterMap", getMap(characterTagList));
                model.addAttribute("shopForm", form);
                model.addAttribute("restFormList", restFormList);
                return "admin/shopEdit";
            } else {
                return "redirect:/admin/shop/list/";
            }
        }

        if (shopType == Constant.SHOP_TAG.DRIVE) {

            int area = shopTagService.getContent(id, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.AREA);
            int shopOpen = shopTagService.getContent(id, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.DAY_OPEN_TIME);
            int shopClose = shopTagService.getContent(id, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.NIGHT_CLOSE_TIME);
            int lastOrder = shopTagService.getContent(id, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.DAY_LAST_ORDER);
            int station = shopTagService.getContent(id, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.STATION);
            int walkTime = shopTagService.getContent(id, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.WALK_TIME);
            int priceLow = shopTagService.getContent(id, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.DAY_PRICE_LOW);
            int priceHigh = shopTagService.getContent(id, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.DAY_PRICE_HIGH);
            List<Integer> genreList = shopTagService.getContents(id, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.FIRST_MENU);
            List<Integer> sceneList = shopTagService.getContents(id, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.SCENE);
            List<Integer> characterList = shopTagService.getContents(id, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.CHARACTER);
            List<Integer> restDateList = shopTagService.getContents(id, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.REST_DATE);
            List<Integer> restWeekList = shopTagService.getContents(id, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.REST_WEEK);

            List<ShopRestForm> restFormList = new ArrayList<ShopRestForm>();
            int amountId = 0;
            if (restWeekList != null) {
                for (Integer rw : restWeekList) {
                    String w = String.valueOf(rw);
                    Integer wNum = Integer.valueOf(w.substring(0, 1));
                    Integer wdNum = Integer.valueOf(w.substring(1, 2));
                    restWeeksList.add(wNum);
                    restWeekDaysList.add(wdNum);
                    ShopRestForm ssf = new ShopRestForm();

                    amountId = amountId + 1;
                    ssf.setWeek(wNum);
                    ssf.setWeekDay(wdNum);
                    ssf.setAmountId(amountId);
                    restFormList.add(ssf);
                }
            }

            form.setShopId(id);
            form.setShopListId(shop.getId());
            form.setExcerpt(shop.getExcerpt());
            form.setPicUrl(shop.getPicUrl());
            form.setArea(area);
            form.setAveragePriceLow(priceLow);
            form.setAveragePriceHigh(priceHigh);
            form.setOpenTime(shopOpen);
            form.setCloseTime(shopClose);
            form.setLastOrder(lastOrder);
            form.setGenreList(genreList);
            form.setStation(station);
            form.setWalkTime(walkTime);
            form.setObjectList(sceneList);
            form.setCharacterList(characterList);
            form.setRestWeek(restWeeksList);
            form.setRestWeekDay(restWeekDaysList);
            form.setRestDates(restDateList);

            List<TagDetail> areaTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.AREA);
            List<TagDetail> stationTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.STATION);
            List<TagDetail> dayPriceTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_PRICE_LOW);
            List<TagDetail> nightPriceTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_PRICE_LOW);
            List<TagDetail> shopOpenTimeTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_OPEN_TIME);
            List<TagDetail> shopCloseTimeTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_OPEN_TIME);
            firstMenuList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.FIRST_MENU);
            characterTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.CHARACTER);
            sceneTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.SCENE);
            List<TagDetail> restDayTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_DATE);
            List<TagDetail> restWeekTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_WEEK);
            List<TagDetail> restWeekDayTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_WEEKDAY);


            if (shop != null) {
                BeanUtils.copyProperties(shopDetail, form);
                form.setPublishStart(DateUtil.dateToStringyyyy_MM_dd(shop.getPublishStart()));
                form.setPublishEnd(DateUtil.dateToStringyyyy_MM_dd(shop.getPublishEnd()));
                model.addAttribute("firstMenuMap", getMap(firstMenuList));
                model.addAttribute("areaList", areaTagList);
                model.addAttribute("stationList", stationTagList);
                model.addAttribute("dayPriceList", dayPriceTagList);
                model.addAttribute("nightPriceList", nightPriceTagList);
                model.addAttribute("shopOpenTimeList", shopOpenTimeTagList);
                model.addAttribute("shopCloseTimeList", shopCloseTimeTagList);
                model.addAttribute("restDateList", restDayTagList);
                model.addAttribute("restWeekList", restWeekTagList);
                model.addAttribute("restWeekDayList", restWeekDayTagList);
                model.addAttribute("sceneMap", getMap(sceneTagList));
                model.addAttribute("characterMap", getMap(characterTagList));
                model.addAttribute("shopForm", form);
                model.addAttribute("restFormList", restFormList);
                return "admin/shopEdit";
            } else {
                return "redirect:/admin/shop/list/";
            }
        }

        if (shopType == Constant.SHOP_TAG.LEARNING) {

            int area = shopTagService.getContent(id, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.AREA);
            int shopOpen = shopTagService.getContent(id, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.DAY_OPEN_TIME);
            int shopClose = shopTagService.getContent(id, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.NIGHT_CLOSE_TIME);
            int lastOrder = shopTagService.getContent(id, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.DAY_LAST_ORDER);
            int firstMenu = shopTagService.getContent(id, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.FIRST_MENU);
            List<Integer> secondMenu = shopTagService.getContents(id, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.SECOND_MENU);
            int station = shopTagService.getContent(id, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.STATION);
            int walkTime = shopTagService.getContent(id, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.WALK_TIME);
            int dayPricelow = shopTagService.getContent(id, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.DAY_PRICE_LOW);
            int nightPriceLow = shopTagService.getContent(id, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.NIGHT_PRICE_LOW);
            int dayPriceHigh = shopTagService.getContent(id, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.DAY_PRICE_HIGH);
            int nightPriceHigh = shopTagService.getContent(id, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.NIGHT_PRICE_HIGH);
            List<Integer> sceneList = shopTagService.getContents(id, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.SCENE);
            List<Integer> characterList = shopTagService.getContents(id, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.CHARACTER);
            List<Integer> restDateList = shopTagService.getContents(id, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.REST_DATE);
            List<Integer> restWeekList = shopTagService.getContents(id, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.REST_WEEK);

            List<ShopRestForm> restFormList = new ArrayList<ShopRestForm>();
            int amountId = 0;
            if (restWeekList != null) {
                for (Integer rw : restWeekList) {
                    String w = String.valueOf(rw);
                    Integer wNum = Integer.valueOf(w.substring(0, 1));
                    Integer wdNum = Integer.valueOf(w.substring(1, 2));
                    restWeeksList.add(wNum);
                    restWeekDaysList.add(wdNum);
                    ShopRestForm ssf = new ShopRestForm();

                    amountId = amountId + 1;
                    ssf.setWeek(wNum);
                    ssf.setWeekDay(wdNum);
                    ssf.setAmountId(amountId);
                    restFormList.add(ssf);
                }
            }

            form.setShopId(id);
            form.setShopListId(shop.getId());
            form.setExcerpt(shop.getExcerpt());
            form.setPicUrl(shop.getPicUrl());
            form.setArea(area);
            form.setDayPriceLow(dayPricelow);
            form.setDayPriceHigh(dayPriceHigh);
            form.setNightPriceLow(nightPriceLow);
            form.setNightPriceHigh(nightPriceHigh);
            form.setOpenTime(shopOpen);
            form.setCloseTime(shopClose);
            form.setLastOrder(lastOrder);
            form.setFirstMenu(firstMenu);
            form.setSecondMenu(secondMenu);
            form.setStation(station);
            form.setWalkTime(walkTime);
            form.setSceneList(sceneList);
            form.setCharacterList(characterList);
            form.setRestWeek(restWeeksList);
            form.setRestWeekDay(restWeekDaysList);
            form.setRestDates(restDateList);

            List<TagDetail> areaTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.AREA);
            List<TagDetail> stationTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.STATION);
            List<TagDetail> dayPriceTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_PRICE_LOW);
            List<TagDetail> nightPriceTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_PRICE_LOW);
            List<TagDetail> shopOpenTimeTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_OPEN_TIME);
            List<TagDetail> shopCloseTimeTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_OPEN_TIME);
            firstMenuList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.FIRST_MENU);
            List<TagDetail> secondMenuList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.SECOND_MENU);
            characterTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.CHARACTER);
            sceneTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.SCENE);
            List<TagDetail> restDayTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_DATE);
            List<TagDetail> restWeekTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_WEEK);
            List<TagDetail> restWeekDayTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_WEEKDAY);

            if (shop != null) {
                BeanUtils.copyProperties(shopDetail, form);
                form.setPublishStart(DateUtil.dateToStringyyyy_MM_dd(shop.getPublishStart()));
                form.setPublishEnd(DateUtil.dateToStringyyyy_MM_dd(shop.getPublishEnd()));
                model.addAttribute("firstMenuList", firstMenuList);
                model.addAttribute("secondMenuList", secondMenuList);
                model.addAttribute("areaList", areaTagList);
                model.addAttribute("stationList", stationTagList);
                model.addAttribute("dayPriceList", dayPriceTagList);
                model.addAttribute("nightPriceList", nightPriceTagList);
                model.addAttribute("shopOpenTimeList", shopOpenTimeTagList);
                model.addAttribute("shopCloseTimeList", shopCloseTimeTagList);
                model.addAttribute("restDateList", restDayTagList);
                model.addAttribute("restWeekList", restWeekTagList);
                model.addAttribute("restWeekDayList", restWeekDayTagList);
                model.addAttribute("sceneMap", getMap(sceneTagList));
                model.addAttribute("characterMap", getMap(characterTagList));
                model.addAttribute("shopForm", form);
                model.addAttribute("restFormList", restFormList);
                return "admin/shopEdit";
            } else {
                return "redirect:/admin/shop/list/";
            }
        }

        if (shopType == Constant.SHOP_TAG.FACILITY) {

            int area = shopTagService.getContent(id, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.AREA);
            int shopOpen = shopTagService.getContent(id, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_OPEN_TIME);
            int shopClose = shopTagService.getContent(id, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.NIGHT_CLOSE_TIME);
            int lastOrder = shopTagService.getContent(id, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_LAST_ORDER);
            int firstMenu = shopTagService.getContent(id, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.FIRST_MENU);
            List<Integer> secondMenu = shopTagService.getContents(id, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.SECOND_MENU);
            int station = shopTagService.getContent(id, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.STATION);
            int walkTime = shopTagService.getContent(id, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.WALK_TIME);
            int dayPricelow = shopTagService.getContent(id, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_PRICE_LOW);
            int nightPriceLow = shopTagService.getContent(id, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.NIGHT_PRICE_LOW);
            int dayPriceHigh = shopTagService.getContent(id, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_PRICE_HIGH);
            int nightPriceHigh = shopTagService.getContent(id, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.NIGHT_PRICE_HIGH);
            int roomType = shopTagService.getContent(id, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.ROOM_TYPE);
            int roomNum = shopTagService.getContent(id, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.ROOM_NUM);
            int headCount = shopTagService.getContent(id, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.HEADCOUNT);
            List<Integer> characterList = shopTagService.getContents(id, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.CHARACTER);
            List<Integer> restDateList = shopTagService.getContents(id, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.REST_DATE);
            List<Integer> restWeekList = shopTagService.getContents(id, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.REST_WEEK);

            List<ShopRestForm> restFormList = new ArrayList<ShopRestForm>();
            int amountId = 0;
            if (restWeekList != null) {
                for (Integer rw : restWeekList) {
                    String w = String.valueOf(rw);
                    Integer wNum = Integer.valueOf(w.substring(0, 1));
                    Integer wdNum = Integer.valueOf(w.substring(1, 2));
                    restWeeksList.add(wNum);
                    restWeekDaysList.add(wdNum);
                    ShopRestForm ssf = new ShopRestForm();

                    amountId = amountId + 1;
                    ssf.setWeek(wNum);
                    ssf.setWeekDay(wdNum);
                    ssf.setAmountId(amountId);
                    restFormList.add(ssf);
                }
            }

            form.setShopId(id);
            form.setShopListId(shop.getId());
            form.setExcerpt(shop.getExcerpt());
            form.setPicUrl(shop.getPicUrl());
            form.setArea(area);
            form.setDayPriceLow(dayPricelow);
            form.setDayPriceHigh(dayPriceHigh);
            form.setNightPriceLow(nightPriceLow);
            form.setNightPriceHigh(nightPriceHigh);
            form.setOpenTime(shopOpen);
            form.setCloseTime(shopClose);
            form.setLastOrder(lastOrder);
            form.setFirstMenu(firstMenu);
            form.setSecondMenu(secondMenu);
            form.setStation(station);
            form.setWalkTime(walkTime);
            form.setRoomType(roomType);
            form.setRoomNum(roomNum);
            form.setHeadCount(headCount);
            form.setCharacterList(characterList);
            form.setRestWeek(restWeeksList);
            form.setRestWeekDay(restWeekDaysList);
            form.setRestDates(restDateList);

            List<TagDetail> areaTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.AREA);
            List<TagDetail> stationTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.STATION);
            List<TagDetail> dayPriceTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_PRICE_LOW);
            List<TagDetail> nightPriceTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_PRICE_LOW);
            List<TagDetail> shopOpenTimeTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_OPEN_TIME);
            List<TagDetail> shopCloseTimeTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_OPEN_TIME);
            firstMenuList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.FIRST_MENU);
            List<TagDetail> secondMenuList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.SECOND_MENU);
            List<TagDetail> headCountTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.HEADCOUNT);
            List<TagDetail> roomNumTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.ROOM_NUM);
            List<TagDetail> roomTypeTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.ROOM_TYPE);
            characterTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.CHARACTER);
            List<TagDetail> restDayTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_DATE);
            List<TagDetail> restWeekTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_WEEK);
            List<TagDetail> restWeekDayTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_WEEKDAY);

            if (shop != null) {
                BeanUtils.copyProperties(shopDetail, form);
                form.setPublishStart(DateUtil.dateToStringyyyy_MM_dd(shop.getPublishStart()));
                form.setPublishEnd(DateUtil.dateToStringyyyy_MM_dd(shop.getPublishEnd()));
                model.addAttribute("firstMenuList", firstMenuList);
                model.addAttribute("secondMenuList", secondMenuList);
                model.addAttribute("areaList", areaTagList);
                model.addAttribute("stationList", stationTagList);
                model.addAttribute("dayPriceList", dayPriceTagList);
                model.addAttribute("nightPriceList", nightPriceTagList);
                model.addAttribute("shopOpenTimeList", shopOpenTimeTagList);
                model.addAttribute("shopCloseTimeList", shopCloseTimeTagList);
                model.addAttribute("headCountList", headCountTagList);
                model.addAttribute("roomTypeTagList", roomTypeTagList);
                model.addAttribute("roomNumTagList", roomNumTagList);
                model.addAttribute("restDateList", restDayTagList);
                model.addAttribute("restWeekList", restWeekTagList);
                model.addAttribute("restWeekDayList", restWeekDayTagList);
                model.addAttribute("characterMap", getMap(characterTagList));
                model.addAttribute("shopForm", form);
                model.addAttribute("restFormList", restFormList);
                return "admin/shopEdit";
            } else {
                return "redirect:/admin/shop/list/";
            }
        }

        if (shopType == Constant.SHOP_TAG.LIFE) {

            int area = shopTagService.getContent(id, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.AREA);
            int shopOpen = shopTagService.getContent(id, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.DAY_OPEN_TIME);
            int shopClose = shopTagService.getContent(id, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.NIGHT_CLOSE_TIME);
            int lastOrder = shopTagService.getContent(id, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.DAY_LAST_ORDER);
            int firstMenu = shopTagService.getContent(id, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.FIRST_MENU);
            List<Integer> secondMenu = shopTagService.getContents(id, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.SECOND_MENU);
            int station = shopTagService.getContent(id, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.STATION);
            int walkTime = shopTagService.getContent(id, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.WALK_TIME);
            int lowPrice = shopTagService.getContent(id, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.DAY_PRICE_LOW);
            int highPrice = shopTagService.getContent(id, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.DAY_PRICE_HIGH);
            List<Integer> characterList = shopTagService.getContents(id, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.CHARACTER);
            List<Integer> restDateList = shopTagService.getContents(id, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.REST_DATE);
            List<Integer> restWeekList = shopTagService.getContents(id, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.REST_WEEK);

            List<ShopRestForm> restFormList = new ArrayList<ShopRestForm>();
            int amountId = 0;
            if (restWeekList != null) {
                for (Integer rw : restWeekList) {
                    String w = String.valueOf(rw);
                    Integer wNum = Integer.valueOf(w.substring(0, 1));
                    Integer wdNum = Integer.valueOf(w.substring(1, 2));
                    restWeeksList.add(wNum);
                    restWeekDaysList.add(wdNum);
                    ShopRestForm ssf = new ShopRestForm();

                    amountId = amountId + 1;
                    ssf.setWeek(wNum);
                    ssf.setWeekDay(wdNum);
                    ssf.setAmountId(amountId);
                    restFormList.add(ssf);
                }
            }

            form.setShopId(id);
            form.setShopListId(shop.getId());
            form.setExcerpt(shop.getExcerpt());
            form.setPicUrl(shop.getPicUrl());
            form.setArea(area);
            form.setPriceLow(lowPrice);
            form.setPriceHigh(highPrice);
            form.setOpenTime(shopOpen);
            form.setCloseTime(shopClose);
            form.setLastOrder(lastOrder);
            form.setFirstMenu(firstMenu);
            form.setSecondMenu(secondMenu);
            form.setStation(station);
            form.setWalkTime(walkTime);
            form.setCharacterList(characterList);
            form.setRestWeek(restWeeksList);
            form.setRestWeekDay(restWeekDaysList);
            form.setRestDates(restDateList);

            List<TagDetail> areaTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.AREA);
            List<TagDetail> stationTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.STATION);
            List<TagDetail> dayPriceTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_PRICE_LOW);
            List<TagDetail> nightPriceTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_PRICE_LOW);
            List<TagDetail> shopOpenTimeTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_OPEN_TIME);
            List<TagDetail> shopCloseTimeTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_OPEN_TIME);
            firstMenuList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.FIRST_MENU);
            List<TagDetail> secondMenuList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.SECOND_MENU);
            characterTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.CHARACTER);
            List<TagDetail> restDayTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_DATE);
            List<TagDetail> restWeekTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_WEEK);
            List<TagDetail> restWeekDayTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_WEEKDAY);

            if (shop != null) {
                BeanUtils.copyProperties(shopDetail, form);
                form.setPublishStart(DateUtil.dateToStringyyyy_MM_dd(shop.getPublishStart()));
                form.setPublishEnd(DateUtil.dateToStringyyyy_MM_dd(shop.getPublishEnd()));
                model.addAttribute("firstMenuList", firstMenuList);
                model.addAttribute("secondMenuList", secondMenuList);
                model.addAttribute("areaList", areaTagList);
                model.addAttribute("stationList", stationTagList);
                model.addAttribute("dayPriceList", dayPriceTagList);
                model.addAttribute("nightPriceList", nightPriceTagList);
                model.addAttribute("shopOpenTimeList", shopOpenTimeTagList);
                model.addAttribute("shopCloseTimeList", shopCloseTimeTagList);
                model.addAttribute("restDateList", restDayTagList);
                model.addAttribute("restWeekList", restWeekTagList);
                model.addAttribute("restWeekDayList", restWeekDayTagList);
                model.addAttribute("characterMap", getMap(characterTagList));
                model.addAttribute("shopForm", form);
                model.addAttribute("restFormList", restFormList);
                return "admin/shopEdit";
            } else {
                return "redirect:/admin/shop/list/";
            }
        }
        return "";
    }

    // shop admin add execution
    @RequestMapping(value = "/admin/shop/update/", method = RequestMethod.POST)
    public String shopUpdate(@ModelAttribute ShopRegistForm form,HttpServletRequest request) throws APIException {
        HttpSession session = request.getSession();
        // upload files
        try {
            if (!form.getPicFile().isEmpty()) {
                form.setPicUrl(awsService.postMiddleThumbnailFile(form.getPicFile()));
            }
            //main
            if (!form.getPicFile1().isEmpty()) {
                form.setPicUrl1(awsService.postFile(form.getPicFile1()));
            }
            //sub1
            if (!form.getPicFile2().isEmpty()) {
                form.setPicUrl2(awsService.postFile(form.getPicFile2()));
            }
            //sub2
            if (!form.getPicFile3().isEmpty()) {
                form.setPicUrl3(awsService.postFile(form.getPicFile3()));
            }
            //sub3
            if (!form.getPicFile4().isEmpty()) {
                form.setPicUrl4(awsService.postFile(form.getPicFile4()));
            }
            //sub4
            if (!form.getPicFile5().isEmpty()) {
                form.setPicUrl5(awsService.postFile(form.getPicFile5()));
            }
            //sub5
            if (!form.getPicFile7().isEmpty()) {
                form.setPicUrl7(awsService.postFile(form.getPicFile7()));
            }
            //sub6
            if (!form.getPicFile8().isEmpty()) {
                form.setPicUrl8(awsService.postFile(form.getPicFile8()));
            }
            //sub7
            if (!form.getPicFile9().isEmpty()) {
                form.setPicUrl9(awsService.postFile(form.getPicFile9()));
            }
            //sub8
            if (!form.getPicFile10().isEmpty()) {
                form.setPicUrl10(awsService.postFile(form.getPicFile10()));
            }
            //sub9
            if (!form.getPicFile11().isEmpty()) {
                form.setPicUrl11(awsService.postFile(form.getPicFile11()));
            }
            //sub10
            if (!form.getPicFile12().isEmpty()) {
                form.setPicUrl12(awsService.postFile(form.getPicFile12()));
            }
            session.setAttribute("error",0);
        } catch (IOException e) {
            session.setAttribute("error",1);
            throw new APIException(JsonStatus.DATA_SAVE_FAILED);
        }

        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try {
            Shop shop = shopService.updateShop(form);
            Long shopDetailId = shopDetailService.updateShopDetail(form);
            Shop shop2 = shopService.getShopById(shop.getId());

            if(shop2 != null){
                RandomShopOne rso = new RandomShopOne();
                rso.setUuid(shop2.getUuid());
                rso.setShopType((byte)shop2.getShopType().intValue());
                rso.setPublishStart(shop2.getPublishStart());
                rso.setPublishEnd(shop2.getPublishEnd());
                rso.setCreateDatetime(shop2.getCreateDatetime());
                rso.setUpdateDatetime(shop2.getUpdateDatetime());
                rso.setDelFlg(shop2.getDelFlg());
                rso.setNote(shop2.getNote());
                randomShopService.updateRandomShopOne(rso);

                RandomShopTwo rst = new RandomShopTwo();
                rst.setUuid(shop2.getUuid());
                rst.setShopType((byte)shop2.getShopType().intValue());
                rst.setPublishStart(shop2.getPublishStart());
                rst.setPublishEnd(shop2.getPublishEnd());
                rst.setCreateDatetime(shop2.getCreateDatetime());
                rst.setUpdateDatetime(shop2.getUpdateDatetime());
                rst.setDelFlg(shop2.getDelFlg());
                rst.setNote(shop2.getNote());
                randomShopService.updateRandomShopTwo(rst);
            }

            Long idPicurl7 =0L ;
            Long idPpicurl8 =0L ;
            Long idPpicurl9 =0L;
            Long idPpicurl10 =0L;
            Long idPpicurl11 =0L;
            Long idPpicurl12 =0L;

            List<Pic> listPic = picService.getShopUrlList(shop.getId(),Constant.EVENT_PIC_TYPE.SHOP);
            if(listPic != null && listPic.size()>0){
                if(listPic.get(0).getPicUrl() != null && listPic.get(0).getPicUrl() !=""){
                    idPicurl7=listPic.get(0).getId();
                }
                if(listPic.size()>1 && listPic.get(1).getPicUrl() != null && listPic.get(1).getPicUrl() !=""){
                    idPpicurl8=listPic.get(1).getId();
                }
                if(listPic.size()>2 && listPic.get(2).getPicUrl() != null && listPic.get(2).getPicUrl() !=""){
                    idPpicurl9=listPic.get(2).getId();
                }
                if(listPic.size()>3 && listPic.get(3).getPicUrl() != null && listPic.get(3).getPicUrl() !=""){
                    idPpicurl10=listPic.get(3).getId();
                }
                if(listPic.size()>4 && listPic.get(4).getPicUrl() != null && listPic.get(4).getPicUrl() !=""){
                    idPpicurl11=listPic.get(4).getId();
                }
                if(listPic.size()>5 && listPic.get(5).getPicUrl() != null && listPic.get(5).getPicUrl() !=""){
                    idPpicurl12=listPic.get(5).getId();
                }
            }

            if(form.getPicUrl11()!=null && !form.getPicUrl11().equals("")){
                shopDetailService.deleteShopDetailVideo(shop.getId());
            }else{
                picService.updateShopPicUrl("",shop.getId(),idPpicurl11);
            }
            if(form.getPicUrl12()!=null && !form.getPicUrl12().equals("")){
                shopDetailService.deleteShopDetailFlic(shop.getId());
            }else{
                picService.updateShopPicUrl("",shop.getId(),idPpicurl12);
            }

//            picService.deleteShopPicUrl(form.getShopListId(),Constant.EVENT_PIC_TYPE.SHOP);
            if(form.getPicUrl7() != null){
                picService.updateShopPicUrl(form.getPicUrl7(),shop.getId(),idPicurl7);
            }
            if(form.getPicUrl8() != null ){
                picService.updateShopPicUrl(form.getPicUrl8(),shop.getId(),idPpicurl8);
            }
            if(form.getPicUrl9() != null ){
                picService.updateShopPicUrl(form.getPicUrl9(),shop.getId(),idPpicurl9);
            }
            if(form.getPicUrl10() != null  ){
                picService.updateShopPicUrl(form.getPicUrl10(),shop.getId(),idPpicurl10);
            }
            if(form.getPicUrl11() != null ){
                picService.updateShopPicUrl(form.getPicUrl11(),shop.getId(),idPpicurl11);
            }
            if(form.getPicUrl12() != null ){
                picService.updateShopPicUrl(form.getPicUrl12(),shop.getId(),idPpicurl12);
            }else{
                picService.updateShopPicUrl("",shop.getId(),idPpicurl12);
            }

            if (form.getShopType() == Constant.SHOP_TAG.FOOD) {
                foodUpdate(shop.getId(), form);

            } else if (form.getShopType() == Constant.SHOP_TAG.FASTION) {
                fastionUpdate(shop.getId(), form);

            } else if (form.getShopType() == Constant.SHOP_TAG.HEALTH) {
                healthUpdate(shop.getId(), form);

            } else if (form.getShopType() == Constant.SHOP_TAG.PLAY) {
                playUpdate(shop.getId(), form);

            } else if (form.getShopType() == Constant.SHOP_TAG.BRIDAL) {
                bridalUpdate(shop.getId(), form);

            } else if (form.getShopType() == Constant.SHOP_TAG.DRIVE) {
                driveUpdate(shop.getId(), form);

            } else if (form.getShopType() == Constant.SHOP_TAG.LEARNING) {
                learnUpdate(shop.getId(), form);

            } else if (form.getShopType() == Constant.SHOP_TAG.FACILITY) {
                facilityUpdate(shop.getId(), form);

            } else if (form.getShopType() == Constant.SHOP_TAG.LIFE) {
                lifeUpdate(shop.getId(), form);
            }
            txManager.commit(txStatus);
            session.setAttribute("error",0);
        } catch (Exception e) {
            txManager.rollback(txStatus);
            session.setAttribute("error",1);
            logger.error("update shop  failed!, error=" + e.getMessage());
            logger.error("update shop  failed!, error=" + e.toString());
        }
        return "redirect:/admin/shop/list/";
    }

    // Shop admin delete
    @RequestMapping(value = "/admin/shop/delete/", method = RequestMethod.POST)
    public String newsDetailDelete(@ModelAttribute ShopForm form,HttpServletRequest request) {
        HttpSession session = request.getSession();
        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);
        try {
            Long shopDetailId = shopDetailService.getShopDetailId(form.getId());
            shopService.deleteShop(form.getId());
            shopDetailService.deleteShopDetail(form.getId());
            shopTagService.deleteShopTag(form.getId());
            adminService.deleteAdminCustomerByShopIdAndType(form.getId(),1);

            txManager.commit(txStatus);
            session.setAttribute("error",0);
        } catch (Exception e) {
            txManager.rollback(txStatus);
            session.setAttribute("error",1);
            logger.error("delete shop  failed!, error=" + e.getMessage());
            logger.error("delete shop  failed!, error=" + e.toString());
        }
        return "redirect:/admin/shop/list/";
    }

    private Map<Integer, String> getMap(List<TagDetail> tagDetailList) {
        Map<Integer, String> result = new HashMap<>();
        if (tagDetailList != null && tagDetailList.size() > 0) {
            for (TagDetail tagDetail : tagDetailList) {
                result.put(tagDetail.getContent(), tagDetail.getDesc());
            }
        }
        return result;
    }

    //食べる
    private void foodUpdate(Long shopId, @ModelAttribute ShopRegistForm form) {

        int restRadio =0;
        restRadio = form.getRestRadio();
        List<Integer> rest = new ArrayList<Integer>();

        List<ShopRestForm> stockList = form.getStock();
        if (stockList != null && stockList.size() > 0) {
            for (ShopRestForm ssf : stockList) {
                if (ssf.getWeek() > 0 && ssf.getWeekDay() > 0) {
                    int week = ssf.getWeek();
                    int weekDay = ssf.getWeekDay();
                    String restStr = String.valueOf(week) + String.valueOf(weekDay);
                    if (restStr != null && restStr != "00") {
                        rest.add(Integer.valueOf(restStr));
                    }
                }
            }
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.AREA) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.AREA, form.getArea());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.AREA, form.getArea());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_PRICE_LOW) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_PRICE_LOW, form.getDayPriceLow());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_PRICE_LOW, form.getDayPriceLow());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_PRICE_LOW) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_PRICE_LOW, form.getNightPriceLow());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_PRICE_LOW, form.getNightPriceLow());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_OPEN_TIME) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getDayOpenTime());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getDayOpenTime());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_CLOSE_TIME) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_CLOSE_TIME, form.getDayCloseTime());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_CLOSE_TIME, form.getDayCloseTime());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_LAST_ORDER) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getDayLastOrder());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getDayLastOrder());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_OPEN_TIME) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_OPEN_TIME, form.getNightOpenTime());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_OPEN_TIME, form.getNightOpenTime());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_CLOSE_TIME) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getNightCloseTime());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getNightCloseTime());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_LAST_ORDER) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_LAST_ORDER, form.getNightLastOrder());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_LAST_ORDER, form.getNightLastOrder());
        }

//        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.FIRST_MENU) == 0) {
//            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
//        } else {
//            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
//        }
//
//        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.SCENE) == 0) {
//            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.SCENE, form.getScene());
//        } else {
//            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.SCENE, form.getScene());
//        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.STATION) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.STATION, form.getStation());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.STATION, form.getStation());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.SEAT) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.SEAT, form.getSeat());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.SEAT, form.getSeat());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.WALK_TIME) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_PRICE_HIGH) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_PRICE_HIGH, form.getDayPriceHigh());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.DAY_PRICE_HIGH, form.getDayPriceHigh());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_PRICE_HIGH) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_PRICE_HIGH, form.getNightPriceHigh());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.NIGHT_PRICE_HIGH, form.getNightPriceHigh());
        }
        shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.FIRST_MENU);
        shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.FIRST_MENU, form.getGenreList());

        if (restRadio == 2 && stockList != null && stockList.size() > 1) {
            shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_WEEK);
            shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_DATE);
            shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_WEEK, rest);
        }
        if (restRadio == 1 && form.getRestDates() != null && form.getRestDates().size()>0) {
            shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_WEEK);
            shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_DATE);
            shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_DATE, form.getRestDates());
        }

        shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.SCENE);
        shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.SCENE, form.getSceneList());

        shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.CHARACTER);
        shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.CHARACTER, form.getCharacterList());
    }

    //ファッション
    private void fastionUpdate(Long shopId, @ModelAttribute ShopRegistForm form) {

        int restRadio =0;
        restRadio = form.getRestRadio();
        List<Integer> rest = new ArrayList<Integer>();

        List<ShopRestForm> stockList = form.getStock();
        if (stockList != null && stockList.size() > 0) {
            for (ShopRestForm ssf : stockList) {
                if (ssf.getWeek() > 0 && ssf.getWeekDay() > 0) {
                    int week = ssf.getWeek();
                    int weekDay = ssf.getWeekDay();
                    String restStr = String.valueOf(week) + String.valueOf(weekDay);
                    if (restStr != null && restStr != "00") {
                        rest.add(Integer.valueOf(restStr));
                    }
                }
            }
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.AREA) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.AREA, form.getArea());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.AREA, form.getArea());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.DAY_PRICE_LOW) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.DAY_PRICE_LOW, form.getPriceLow());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.DAY_PRICE_LOW, form.getPriceLow());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.DAY_PRICE_HIGH) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.DAY_PRICE_HIGH, form.getPriceHigh());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.DAY_PRICE_HIGH, form.getPriceHigh());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.DAY_OPEN_TIME) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.NIGHT_CLOSE_TIME) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.DAY_LAST_ORDER) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
        }

//        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.FIRST_MENU) == 0) {
//            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
//        } else {
//            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
//        }
//
//        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.SCENE) == 0) {
//            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.SCENE, form.getObject());
//        } else {
//            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.SCENE, form.getObject());
//        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.STATION) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.STATION, form.getStation());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.STATION, form.getStation());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.WALK_TIME) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
        }

        shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.FIRST_MENU);
        shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.FIRST_MENU, form.getGenreList());

        shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.SCENE);
        shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.SCENE, form.getObjectList());

        shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.CHARACTER);
        shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.CHARACTER, form.getCharacterList());

        shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.BRAND);
        shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.BRAND, form.getBrandList());

        if (restRadio == 2 && stockList != null && stockList.size() > 1) {
            shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.REST_WEEK);
            shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.REST_DATE);
            shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.REST_WEEK, rest);
        }
        if (restRadio == 1 && form.getRestDates() != null && form.getRestDates().size()>0) {
            shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.REST_WEEK);
            shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.REST_DATE);
            shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.FASTION, Constant.SHOP_TAG.REST_DATE, form.getRestDates());
        }
    }

    //健康ビューティー
    private void healthUpdate(Long shopId, @ModelAttribute ShopRegistForm form) {

        int restRadio =0;
        restRadio = form.getRestRadio();
        List<Integer> rest = new ArrayList<Integer>();

        List<ShopRestForm> stockList = form.getStock();
        if (stockList != null && stockList.size() > 0) {
            for (ShopRestForm ssf : stockList) {
                if (ssf.getWeek() > 0 && ssf.getWeekDay() > 0) {
                    int week = ssf.getWeek();
                    int weekDay = ssf.getWeekDay();
                    String restStr = String.valueOf(week) + String.valueOf(weekDay);
                    if (restStr != null && restStr != "00") {
                        rest.add(Integer.valueOf(restStr));
                    }
                }
            }
        }

        if (form.getFirstMenu() == Constant.SHOP_HEALTH_GENRE.SALON) {
            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.AREA) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.AREA, form.getArea());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.AREA, form.getArea());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_PRICE_LOW) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_PRICE_LOW, form.getPriceLow());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_PRICE_LOW, form.getPriceLow());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_PRICE_LOW) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_PRICE_LOW, form.getNightPriceLow());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_PRICE_LOW, form.getNightPriceLow());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_PRICE_HIGH) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_PRICE_HIGH, form.getPriceHigh());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_PRICE_HIGH, form.getPriceHigh());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_PRICE_HIGH) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_PRICE_HIGH, form.getNightPriceHigh());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_PRICE_HIGH, form.getNightPriceHigh());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_OPEN_TIME) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_CLOSE_TIME) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_LAST_ORDER) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.FIRST_MENU) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
            }

            if (shopTagService.getContents(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.SECOND_MENU).size()> 0) {
                shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.SECOND_MENU, form.getSecondMenu());
            } else {
                shopTagService.updateShopTags(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.SECOND_MENU, form.getSecondMenu());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.SCENE) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.SCENE, form.getScene());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.SCENE, form.getScene());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.STATION) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.STATION, form.getStation());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.STATION, form.getStation());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.WALK_TIME) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.SEAT) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.SEAT, form.getSeat());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.SEAT, form.getSeat());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.MASSAGE_TIME) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.MASSAGE_TIME, form.getMassageTime());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.MASSAGE_TIME, form.getMassageTime());
            }

            shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.CHARACTER);
            shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.CHARACTER, form.getCharacterList());

            shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.BRAND);
            shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.BRAND, form.getBrandList());

            if (restRadio == 2 && stockList != null && stockList.size() > 1) {
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.REST_WEEK);
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.REST_DATE);
                shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.REST_WEEK, rest);
            }
            if (restRadio == 1 && form.getRestDates() != null && form.getRestDates().size()>0) {
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.REST_WEEK);
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.REST_DATE);
                shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.REST_DATE, form.getRestDates());
            }
        } else if (form.getFirstMenu() == Constant.SHOP_HEALTH_GENRE.MASSAGE ||
                form.getFirstMenu() == Constant.SHOP_HEALTH_GENRE.CHIROPRATIC || form.getFirstMenu() == Constant.SHOP_HEALTH_GENRE.COSMETIC_SURGERY) {

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.AREA) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.AREA, form.getArea());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.AREA, form.getArea());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_PRICE_LOW) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_PRICE_LOW, form.getPriceLow());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_PRICE_LOW, form.getPriceLow());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_PRICE_LOW) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_PRICE_LOW, form.getNightPriceLow());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_PRICE_LOW, form.getNightPriceLow());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_PRICE_HIGH) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_PRICE_HIGH, form.getPriceHigh());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_PRICE_HIGH, form.getPriceHigh());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_PRICE_HIGH) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_PRICE_HIGH, form.getNightPriceHigh());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_PRICE_HIGH, form.getNightPriceHigh());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_OPEN_TIME) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_CLOSE_TIME) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_LAST_ORDER) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.FIRST_MENU) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.SCENE) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.SCENE, form.getScene());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.SCENE, form.getScene());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.STATION) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.STATION, form.getStation());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.STATION, form.getStation());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.WALK_TIME) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.SEAT) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.SEAT, form.getSeat());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.SEAT, form.getSeat());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.MASSAGE_TIME) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.MASSAGE_TIME, form.getMassageTime());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.MASSAGE_TIME, form.getMassageTime());
            }
            shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.CHARACTER);
            shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.CHARACTER, form.getCharacterList());

            shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.BRAND);
            shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.BRAND, form.getBrandList());

            if (restRadio == 2 && stockList != null && stockList.size() > 1) {
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.REST_WEEK);
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.REST_DATE);
                shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.REST_WEEK, rest);
            }
            if (restRadio == 1 && form.getRestDates() != null && form.getRestDates().size()>0) {
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.REST_WEEK);
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.REST_DATE);
                shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.REST_DATE, form.getRestDates());
            }
        } else if (form.getFirstMenu() == Constant.SHOP_HEALTH_GENRE.COSMETICS || form.getFirstMenu() == Constant.SHOP_HEALTH_GENRE.DRUGSTORE ||
                form.getFirstMenu() == Constant.SHOP_HEALTH_GENRE.SUPPLEMENT) {

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.AREA) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.AREA, form.getArea());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.AREA, form.getArea());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_PRICE_LOW) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_PRICE_LOW, form.getPriceLow());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_PRICE_LOW, form.getPriceLow());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_PRICE_LOW) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_PRICE_LOW, form.getNightPriceLow());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_PRICE_LOW, form.getNightPriceLow());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_PRICE_HIGH) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_PRICE_HIGH, form.getPriceHigh());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_PRICE_HIGH, form.getPriceHigh());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_PRICE_HIGH) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_PRICE_HIGH, form.getNightPriceHigh());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_PRICE_HIGH, form.getNightPriceHigh());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_OPEN_TIME) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_CLOSE_TIME) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_LAST_ORDER) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.FIRST_MENU) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.SCENE) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.SCENE, form.getScene());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.SCENE, form.getScene());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.STATION) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.STATION, form.getStation());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.STATION, form.getStation());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.WALK_TIME) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
            }

            shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.CHARACTER);
            shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.CHARACTER, form.getCharacterList());

            shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.BRAND);
            shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.BRAND, form.getBrandList());

            if (restRadio == 2 && stockList != null && stockList.size() > 1) {
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.REST_WEEK);
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.REST_DATE);
                shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.REST_WEEK, rest);
            }
            if (restRadio == 1 && form.getRestDates() != null && form.getRestDates().size()>0) {
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.REST_WEEK);
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.REST_DATE);
                shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.REST_DATE, form.getRestDates());
            }
        } else {
            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.AREA) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.AREA, form.getArea());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.AREA, form.getArea());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_PRICE_LOW) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_PRICE_LOW, form.getPriceLow());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_PRICE_LOW, form.getPriceLow());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_PRICE_LOW) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_PRICE_LOW, form.getNightPriceLow());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_PRICE_LOW, form.getNightPriceLow());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_PRICE_HIGH) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_PRICE_HIGH, form.getPriceHigh());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_PRICE_HIGH, form.getPriceHigh());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_PRICE_HIGH) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_PRICE_HIGH, form.getNightPriceHigh());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_PRICE_HIGH, form.getNightPriceHigh());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_OPEN_TIME) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_CLOSE_TIME) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_LAST_ORDER) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.FIRST_MENU) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.SCENE) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.SCENE, form.getScene());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.SCENE, form.getScene());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.STATION) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.STATION, form.getStation());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.STATION, form.getStation());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.WALK_TIME) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
            }

            shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.CHARACTER);
            shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.CHARACTER, form.getCharacterList());

            if (restRadio == 2 && stockList != null && stockList.size() > 1) {
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.REST_WEEK);
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.REST_DATE);
                shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.REST_WEEK, rest);
            }
            if (restRadio == 1 && form.getRestDates() != null && form.getRestDates().size()>0) {
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.REST_WEEK);
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.REST_DATE);
                shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.HEALTH, Constant.SHOP_TAG.REST_DATE, form.getRestDates());
            }
        }
    }

    //遊ぶ
    private void playUpdate(Long shopId, @ModelAttribute ShopRegistForm form) {

        int restRadio =0;
        restRadio = form.getRestRadio();
        List<Integer> rest = new ArrayList<Integer>();

        List<ShopRestForm> stockList = form.getStock();
        if (stockList != null && stockList.size() > 0) {
            for (ShopRestForm ssf : stockList) {
                if (ssf.getWeek() > 0 && ssf.getWeekDay() > 0) {
                    int week = ssf.getWeek();
                    int weekDay = ssf.getWeekDay();
                    String restStr = String.valueOf(week) + String.valueOf(weekDay);
                    if (restStr != null && restStr != "00") {
                        rest.add(Integer.valueOf(restStr));
                    }
                }
            }
        }
        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.AREA) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.AREA, form.getArea());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.AREA, form.getArea());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.DAY_PRICE_LOW) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.DAY_PRICE_LOW, form.getDayPriceLow());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.DAY_PRICE_LOW, form.getDayPriceLow());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.NIGHT_PRICE_LOW) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.NIGHT_PRICE_LOW, form.getNightPriceLow());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.NIGHT_PRICE_LOW, form.getNightPriceLow());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.DAY_PRICE_HIGH) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.DAY_PRICE_HIGH, form.getDayPriceHigh());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.DAY_PRICE_HIGH, form.getDayPriceHigh());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.NIGHT_PRICE_HIGH) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.NIGHT_PRICE_HIGH, form.getNightPriceHigh());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.NIGHT_PRICE_HIGH, form.getNightPriceHigh());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.DAY_OPEN_TIME) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.NIGHT_CLOSE_TIME) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.DAY_LAST_ORDER) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
        }

//        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.FIRST_MENU) == 0) {
//            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
//        } else {
//            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
//        }

//        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.SCENE) == 0) {
//            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.SCENE, form.getScene());
//        } else {
//            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.SCENE, form.getScene());
//        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.STATION) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.STATION, form.getStation());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.STATION, form.getStation());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.WALK_TIME) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
        }

        shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.FIRST_MENU);
        shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.FIRST_MENU, form.getGenreList());

        shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.SCENE);
        shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.SCENE, form.getSceneList());

        shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.CHARACTER);
        shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.CHARACTER, form.getCharacterList());

        if (restRadio == 2 && stockList != null && stockList.size() > 1) {
            shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.REST_WEEK);
            shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.REST_DATE);
            shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.REST_WEEK, rest);
        }
        if (restRadio == 1 && form.getRestDates() != null && form.getRestDates().size()>0) {
            shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.REST_WEEK);
            shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.REST_DATE);
            shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.PLAY, Constant.SHOP_TAG.REST_DATE, form.getRestDates());
        }
    }

    //ブライダル
    private void bridalUpdate(Long shopId, @ModelAttribute ShopRegistForm form) {

        int restRadio =0;
        restRadio = form.getRestRadio();
        List<Integer> rest = new ArrayList<Integer>();

        List<ShopRestForm> stockList = form.getStock();
        if (stockList != null && stockList.size() > 0) {
            for (ShopRestForm ssf : stockList) {
                if (ssf.getWeek() > 0 && ssf.getWeekDay() > 0) {
                    int week = ssf.getWeek();
                    int weekDay = ssf.getWeekDay();
                    String restStr = String.valueOf(week) + String.valueOf(weekDay);
                    if (restStr != null && restStr != "00") {
                        rest.add(Integer.valueOf(restStr));
                    }
                }
            }
        }


        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.AREA) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.AREA, form.getArea());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.AREA, form.getArea());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.DAY_OPEN_TIME) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.NIGHT_CLOSE_TIME) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.DAY_LAST_ORDER) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.FIRST_MENU) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.FIRST_MENU, form.getCeremony());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.FIRST_MENU, form.getCeremony());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.SCENE) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.SCENE, form.getPlace());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.SCENE, form.getPlace());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.STATION) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.STATION, form.getStation());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.STATION, form.getStation());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.WALK_TIME) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.HEADCOUNT) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.HEADCOUNT, form.getHeadCount());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.HEADCOUNT, form.getHeadCount());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.CONTACT_TIME) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.CONTACT_TIME, form.getContactTime());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.CONTACT_TIME, form.getContactTime());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.FOOD_PRICE) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.FOOD_PRICE, form.getFoodPrice());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.FOOD_PRICE, form.getFoodPrice());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.DRINK_PRICE) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.DRINK_PRICE, form.getDrinkPrice());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.DRINK_PRICE, form.getDrinkPrice());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.CORKAGE_PRICE) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.CORKAGE_PRICE, form.getCorkagePrice());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.CORKAGE_PRICE, form.getCorkagePrice());
        }

        shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.CHARACTER);
        shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.CHARACTER, form.getCharacterList());

        if (restRadio == 2 && stockList != null && stockList.size() > 1) {
            shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.REST_WEEK);
            shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.REST_DATE);
            shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.REST_WEEK, rest);
        }
        if (restRadio == 1 && form.getRestDates() != null && form.getRestDates().size()>0) {
            shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.REST_WEEK);
            shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.REST_DATE);
            shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.BRIDAL, Constant.SHOP_TAG.REST_DATE, form.getRestDates());
        }
    }

    //乗る
    private void driveUpdate(Long shopId, @ModelAttribute ShopRegistForm form) {

        int restRadio =0;
        restRadio = form.getRestRadio();
        List<Integer> rest = new ArrayList<Integer>();

        List<ShopRestForm> stockList = form.getStock();
        if (stockList != null && stockList.size() > 0) {
            for (ShopRestForm ssf : stockList) {
                if (ssf.getWeek() > 0 && ssf.getWeekDay() > 0) {
                    int week = ssf.getWeek();
                    int weekDay = ssf.getWeekDay();
                    String restStr = String.valueOf(week) + String.valueOf(weekDay);
                    if (restStr != null && restStr != "00") {
                        rest.add(Integer.valueOf(restStr));
                    }
                }
            }
        }


        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.AREA) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.AREA, form.getArea());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.AREA, form.getArea());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.DAY_PRICE_LOW) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.DAY_PRICE_LOW, form.getAveragePriceLow());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.DAY_PRICE_LOW, form.getAveragePriceLow());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.DAY_PRICE_HIGH) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.DAY_PRICE_HIGH, form.getAveragePriceHigh());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.DAY_PRICE_HIGH, form.getAveragePriceHigh());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.DAY_OPEN_TIME) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.NIGHT_CLOSE_TIME) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.DAY_LAST_ORDER) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
        }
//        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.FIRST_MENU) == 0) {
//            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
//        } else {
//            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
//        }

//        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.SCENE) == 0) {
//            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.SCENE, form.getObject());
//        } else {
//            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.SCENE, form.getObject());
//        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.STATION) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.STATION, form.getStation());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.STATION, form.getStation());
        }

        if (shopTagService.getContent(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.WALK_TIME) == 0) {
            shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
        } else {
            shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
        }

        shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.FIRST_MENU);
        shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.FIRST_MENU, form.getGenreList());

        shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.SCENE);
        shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.SCENE, form.getObjectList());


        shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.CHARACTER);
        shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.CHARACTER, form.getCharacterList());

        if (restRadio == 2 && stockList != null && stockList.size() > 1) {
            shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.REST_WEEK);
            shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.REST_DATE);
            shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.REST_WEEK, rest);
        }
        if (restRadio == 1 && form.getRestDates() != null && form.getRestDates().size()>0) {
            shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.REST_WEEK);
            shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.REST_DATE);
            shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.DRIVE, Constant.SHOP_TAG.REST_DATE, form.getRestDates());
        }
    }

    //学ぶ
    private void learnUpdate(Long shopId, @ModelAttribute ShopRegistForm form) {

        int restRadio =0;
        restRadio = form.getRestRadio();
        List<Integer> rest = new ArrayList<Integer>();

        List<ShopRestForm> stockList = form.getStock();
        if (stockList != null && stockList.size() > 0) {
            for (ShopRestForm ssf : stockList) {
                if (ssf.getWeek() > 0 && ssf.getWeekDay() > 0) {
                    int week = ssf.getWeek();
                    int weekDay = ssf.getWeekDay();
                    String restStr = String.valueOf(week) + String.valueOf(weekDay);
                    if (restStr != null && restStr != "00") {
                        rest.add(Integer.valueOf(restStr));
                    }
                }
            }
        }

        if (form.getFirstMenu() == Constant.SHOP_LEARN_GENRE.SCHOOL) {
            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.AREA) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.AREA, form.getArea());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.AREA, form.getArea());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.DAY_OPEN_TIME) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.NIGHT_CLOSE_TIME) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.DAY_LAST_ORDER) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.FIRST_MENU) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.SCENE) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.SCENE, form.getObject());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.SCENE, form.getObject());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.STATION) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.STATION, form.getStation());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.STATION, form.getStation());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.WALK_TIME) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
            }

            shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.SECOND_MENU);
            shopTagService.updateShopTags(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.SECOND_MENU, form.getSecondMenu());

            shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.SCENE);
            shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.SCENE, form.getSceneList());

            if (restRadio == 2 && stockList != null && stockList.size() > 1) {
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.REST_WEEK);
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.REST_DATE);
                shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.REST_WEEK, rest);
            }
            if (restRadio == 1 && form.getRestDates() != null && form.getRestDates().size()>0) {
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.REST_WEEK);
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.REST_DATE);
                shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.REST_DATE, form.getRestDates());
            }

        } else {

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.AREA) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.AREA, form.getArea());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.AREA, form.getArea());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.DAY_PRICE_LOW) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.DAY_PRICE_LOW, form.getPriceLow());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.DAY_PRICE_LOW, form.getPriceLow());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.NIGHT_PRICE_LOW) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.NIGHT_PRICE_LOW, form.getNightPriceLow());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.NIGHT_PRICE_LOW, form.getNightPriceLow());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.DAY_PRICE_HIGH) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.DAY_PRICE_HIGH, form.getPriceHigh());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.DAY_PRICE_HIGH, form.getPriceHigh());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.NIGHT_PRICE_HIGH) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.NIGHT_PRICE_HIGH, form.getNightPriceHigh());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.NIGHT_PRICE_HIGH, form.getNightPriceHigh());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.DAY_OPEN_TIME) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.NIGHT_CLOSE_TIME) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.DAY_LAST_ORDER) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.FIRST_MENU) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.STATION) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.STATION, form.getStation());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.STATION, form.getStation());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.WALK_TIME) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
            }

            shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.SCENE);
            shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.SCENE, form.getSceneList());

            shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.CHARACTER);
            shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.CHARACTER, form.getCharacterList());

            if (restRadio == 2 && stockList != null && stockList.size() > 1) {
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.REST_WEEK);
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.REST_DATE);
                shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.REST_WEEK, rest);
            }
            if (restRadio == 1 && form.getRestDates() != null && form.getRestDates().size()>0) {
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.REST_WEEK);
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.REST_DATE);
                shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.LEARNING, Constant.SHOP_TAG.REST_DATE, form.getRestDates());
            }
        }
    }

    //施設
    private void facilityUpdate(Long shopId, @ModelAttribute ShopRegistForm form) {

        int restRadio =0;
        restRadio = form.getRestRadio();
        List<Integer> rest = new ArrayList<Integer>();

        List<ShopRestForm> stockList = form.getStock();
        if (stockList != null && stockList.size() > 0) {
            for (ShopRestForm ssf : stockList) {
                if (ssf.getWeek() > 0 && ssf.getWeekDay() > 0) {
                    int week = ssf.getWeek();
                    int weekDay = ssf.getWeekDay();
                    String restStr = String.valueOf(week) + String.valueOf(weekDay);
                    if (restStr != null && restStr != "00") {
                        rest.add(Integer.valueOf(restStr));
                    }
                }
            }
        }

        if (form.getFirstMenu() == Constant.SHOP_FACILITY_GENRE.ACCOMMODATION) {

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.AREA) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.AREA, form.getArea());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.AREA, form.getArea());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_PRICE_LOW) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_PRICE_LOW, form.getDayPriceLow());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_PRICE_LOW, form.getDayPriceLow());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.NIGHT_PRICE_LOW) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.NIGHT_PRICE_LOW, form.getNightPriceLow());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.NIGHT_PRICE_LOW, form.getNightPriceLow());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_PRICE_HIGH) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_PRICE_HIGH, form.getDayPriceHigh());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_PRICE_HIGH, form.getDayPriceHigh());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.NIGHT_PRICE_HIGH) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.NIGHT_PRICE_HIGH, form.getNightPriceHigh());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.NIGHT_PRICE_HIGH, form.getNightPriceHigh());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_OPEN_TIME) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.NIGHT_CLOSE_TIME) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_LAST_ORDER) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.FIRST_MENU) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.STATION) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.STATION, form.getStation());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.STATION, form.getStation());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.WALK_TIME) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.ROOM_TYPE) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.ROOM_TYPE, form.getRoomType());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.ROOM_TYPE, form.getRoomType());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.ROOM_NUM) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.ROOM_NUM, form.getRoomNum());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.ROOM_NUM, form.getRoomNum());
            }
            shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.SECOND_MENU);
            shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.SECOND_MENU, form.getCharacterList());

            shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.CHARACTER);
            shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.CHARACTER, form.getCharacterList());

            if (restRadio == 2 && stockList != null && stockList.size() > 1) {
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.REST_WEEK);
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.REST_DATE);
                shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.REST_WEEK, rest);
            }
            if (restRadio == 1 && form.getRestDates() != null && form.getRestDates().size()>0) {
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.REST_WEEK);
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.REST_DATE);
                shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.REST_DATE, form.getRestDates());
            }
        } else if (form.getFirstMenu() == Constant.SHOP_FACILITY_GENRE.TEMPLE) {

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.AREA) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.AREA, form.getArea());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.AREA, form.getArea());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_OPEN_TIME) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.NIGHT_CLOSE_TIME) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_LAST_ORDER) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.FIRST_MENU) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.STATION) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.STATION, form.getStation());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.STATION, form.getStation());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.WALK_TIME) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.HEADCOUNT) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.HEADCOUNT, form.getHeadCount());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.HEADCOUNT, form.getHeadCount());
            }

            shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.CHARACTER);
            shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.CHARACTER, form.getCharacterList());

            if (restRadio == 2 && stockList != null && stockList.size() > 1) {
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.REST_WEEK);
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.REST_DATE);
                shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.REST_WEEK, rest);
            }
            if (restRadio == 1 && form.getRestDates() != null && form.getRestDates().size()>0) {
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.REST_WEEK);
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.REST_DATE);
                shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.REST_DATE, form.getRestDates());
            }
        } else {

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.AREA) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.AREA, form.getArea());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.AREA, form.getArea());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_OPEN_TIME) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.NIGHT_CLOSE_TIME) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.FIRST_MENU) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_LAST_ORDER) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.STATION) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.STATION, form.getStation());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.STATION, form.getStation());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.WALK_TIME) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
            }

            shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.SECOND_MENU);
            shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.SECOND_MENU, form.getCharacterList());

            shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.CHARACTER);
            shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.CHARACTER, form.getCharacterList());

            if (restRadio == 2 && stockList != null && stockList.size() > 1) {
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.REST_WEEK);
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.REST_DATE);
                shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.REST_WEEK, rest);
            }
            if (restRadio == 1 && form.getRestDates() != null && form.getRestDates().size()>0) {
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.REST_WEEK);
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.REST_DATE);
                shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.FACILITY, Constant.SHOP_TAG.REST_DATE, form.getRestDates());
            }
        }
    }

    //ライフスタイル
    private void lifeUpdate(Long shopId, @ModelAttribute ShopRegistForm form) {

        int restRadio =0;
        restRadio = form.getRestRadio();
        List<Integer> rest = new ArrayList<Integer>();

        List<ShopRestForm> stockList = form.getStock();
        if (stockList != null && stockList.size() > 0) {
            for (ShopRestForm ssf : stockList) {
                if (ssf.getWeek() > 0 && ssf.getWeekDay() > 0) {
                    int week = ssf.getWeek();
                    int weekDay = ssf.getWeekDay();
                    String restStr = String.valueOf(week) + String.valueOf(weekDay);
                    if (restStr != null && restStr != "00") {
                        rest.add(Integer.valueOf(restStr));
                    }
                }
            }
        }

        if (form.getFirstMenu() == Constant.SHOP_LIFE_GENRE.LIFE) {

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.AREA) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.AREA, form.getArea());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.AREA, form.getArea());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.DAY_PRICE_LOW) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.DAY_PRICE_LOW, form.getPriceLow());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.DAY_PRICE_LOW, form.getPriceLow());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.DAY_PRICE_HIGH) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.DAY_PRICE_HIGH, form.getPriceHigh());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.DAY_PRICE_HIGH, form.getPriceHigh());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.DAY_OPEN_TIME) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.NIGHT_CLOSE_TIME) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.DAY_LAST_ORDER) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.FIRST_MENU) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.STATION) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.STATION, form.getStation());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.STATION, form.getStation());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.WALK_TIME) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
            }

            shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.SECOND_MENU);
            shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.SECOND_MENU, form.getCharacterList());

            shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.CHARACTER);
            shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.CHARACTER, form.getCharacterList());

            if (restRadio == 2 && stockList != null && stockList.size() > 1) {
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.REST_WEEK);
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.REST_DATE);
                shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.REST_WEEK, rest);
            }
            if (restRadio == 1 && form.getRestDates() != null && form.getRestDates().size()>0) {
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.REST_WEEK);
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.REST_DATE);
                shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.REST_DATE, form.getRestDates());
            }

        } else {

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.AREA) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.AREA, form.getArea());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.AREA, form.getArea());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.DAY_OPEN_TIME) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.DAY_OPEN_TIME, form.getOpenTime());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.NIGHT_CLOSE_TIME) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.NIGHT_CLOSE_TIME, form.getCloseTime());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.DAY_LAST_ORDER) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.DAY_LAST_ORDER, form.getLastOrder());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.FIRST_MENU) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.STATION) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.STATION, form.getStation());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.STATION, form.getStation());
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.WALK_TIME) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
            }

            shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.SECOND_MENU);
            shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.SECOND_MENU, form.getCharacterList());

            shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.CHARACTER);
            shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.CHARACTER, form.getCharacterList());

            if (restRadio == 2 && stockList != null && stockList.size() > 1) {
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.REST_WEEK);
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.REST_DATE);
                shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.REST_WEEK, rest);
            }
            if (restRadio == 1 && form.getRestDates() != null && form.getRestDates().size()>0) {
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.REST_WEEK);
                shopTagService.deleteShopTags(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.REST_DATE);
                shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.LIFE, Constant.SHOP_TAG.REST_DATE, form.getRestDates());
            }
        }
    }
}
