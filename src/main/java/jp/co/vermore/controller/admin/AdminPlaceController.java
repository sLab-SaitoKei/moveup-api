package jp.co.vermore.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.entity.*;
import jp.co.vermore.form.admin.RoleFrom;
import jp.co.vermore.form.admin.ShopRestForm;
import jp.co.vermore.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.DatatablesJsonObject;
import jp.co.vermore.common.JsonStatus;
import jp.co.vermore.common.mvc.APIException;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.form.admin.PlaceForm;
import jp.co.vermore.form.admin.PlaceListForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * AdminPlaceController
 * Created by wubin.
 * <p>
 * DateTime: 2018/04/18 16:04
 * Copyright: sLab, Corp
 */
@Controller
public class AdminPlaceController extends BaseController {

    @Autowired
    private PlaceService placeService;

    @Autowired
    private TagDetailService tagDetailService;

    @Autowired
    private AWSService awsService;

    @Autowired
    private PicService picService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RandomShopService randomShopService;

    @Autowired
    private PlatformTransactionManager txManager;

    @Autowired
    private ShopTagService shopTagService;

    @RequestMapping(value = "/admin/place/list/", method = RequestMethod.GET)
    public String placeAll(Model model,HttpServletRequest request) {

        int errorCode = 0;
        if(!request.getSession().isNew()){
            if(request.getSession().getAttribute("error") != null && request.getSession().getAttribute("error") != ""){
                errorCode = (int)request.getSession().getAttribute("error");
                request.getSession().setAttribute("error",0);
            }
        }
        model.addAttribute("errorCode", errorCode);

        PlaceForm form = new PlaceForm();
        model.addAttribute("form", form);
        return "admin/placeList";
    }

    @RequestMapping(value = "/admin/place/list/", method = RequestMethod.POST)
    @ResponseBody
    public DatatablesJsonObject placeList(@RequestBody PlaceListForm form) {

        // set order statement
        if (form.getOrder().size() > 0
                && form.getColumns().get(form.getOrder().get(0).getColumn()).getName() != null
                && form.getColumns().get(form.getOrder().get(0).getColumn()).getName().length() > 0) {
            form.setOrderStatement(form.getColumns().get(form.getOrder().get(0).getColumn()).getName() + " " + form.getOrder().get(0).getDir());
            logger.debug("----2----order statement=" + form.getOrderStatement());
        } else {
            form.setOrderStatement("place_id");
            logger.debug("----2----order statement=" + form.getOrderStatement());
        }
        logger.debug("----3----");

        // query data
        List<PlaceDetail> dataList = placeService.getPlaceDetailAllByCondition(form);
        int totalCountFiltered = placeService.getPlaceDetailCountByCondition(form);
        int totalCount = placeService.getPlaceDetailCount();
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
        return jsonparse;
    }

    @RequestMapping(value = "/admin/place/regist/", method = RequestMethod.GET)
    public String placeRegist(Model model) {
        PlaceForm form = new PlaceForm();
        List<TagDetail> areaTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.AREA);
        model.addAttribute("form", form);
        model.addAttribute("areaList", areaTagList);
        return "admin/placeRegist";
    }

    @RequestMapping(value = "/admin/place/regist/", method = RequestMethod.POST)
    public String placeRegist(@ModelAttribute PlaceForm form, Model model,HttpServletRequest request) throws APIException {
        HttpSession session = request.getSession();
        try {
            if (!form.getPicFile().isEmpty()) {
                form.setPicUrl(awsService.postMiddleThumbnailFile(form.getPicFile()));
            }
            if (!form.getPicFile1().isEmpty()) {
                form.setPicUrl1(awsService.postFile(form.getPicFile1()));
            }
            if (!form.getPicFile2().isEmpty()) {
                form.setPicUrl2(awsService.postFile(form.getPicFile2()));
            }
            if (!form.getPicFile3().isEmpty()) {
                form.setPicUrl3(awsService.postFile(form.getPicFile3()));
            }
            if (!form.getPicFile4().isEmpty()) {
                form.setPicUrl4(awsService.postFile(form.getPicFile4()));
            }
            if (!form.getPicFile5().isEmpty()) {
                form.setPicUrl5(awsService.postFile(form.getPicFile5()));
            }
            if (!form.getPicFile7().isEmpty()) {
                form.setPicUrl7(awsService.postFile(form.getPicFile7()));
            }
            if (!form.getPicFile8().isEmpty()) {
                form.setPicUrl8(awsService.postFile(form.getPicFile8()));
            }
            if (!form.getPicFile9().isEmpty()) {
                form.setPicUrl9(awsService.postFile(form.getPicFile9()));
            }
            if (!form.getPicFile10().isEmpty()) {
                form.setPicUrl10(awsService.postFile(form.getPicFile10()));
            }
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
            logger.error("regist Place photos failed!, error=" + e.getMessage());
            logger.error("regist Place photos failed!, error=" + e.toString());
            e.printStackTrace();
            throw new APIException(JsonStatus.DATA_SAVE_FAILED);
        }

        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try {
            Place place = placeService.insertPlace(form);
            String location = tagDetailService.getDesc(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.AREA, form.getArea());
            Place place2 = placeService.getPlace(place.getId());
            form.setLocation(location);
            Long shopId = placeService.insertPlaceDetail(form, place.getId());

            if(place2 != null){
                RandomShopOne rso = new RandomShopOne();
                rso.setUuid(place2.getUuid());
                rso.setShopType((byte)Constant.SHOP_TAG.PLACE);
                rso.setArea((byte)place2.getArea().intValue());
                rso.setNow4((byte)0);
                rso.setNow5((byte)0);
                rso.setPublishStart(place2.getPublishStart());
                rso.setPublishEnd(place2.getPublishEnd());
                rso.setCreateDatetime(place2.getCreateDatetime());
                rso.setUpdateDatetime(place2.getUpdateDatetime());
                rso.setDelFlg(place2.getDelFlg());
                rso.setNote(place2.getNote());
                randomShopService.insertRandomShopOne(rso);

                RandomShopTwo rst = new RandomShopTwo();
                rst.setUuid(place2.getUuid());
                rst.setShopType((byte)Constant.SHOP_TAG.PLACE);
                rst.setArea((byte)place2.getArea().intValue());
                rst.setNow4((byte)0);
                rst.setNow5((byte)0);
                rst.setPublishStart(place2.getPublishStart());
                rst.setPublishEnd(place2.getPublishEnd());
                rst.setCreateDatetime(place2.getCreateDatetime());
                rst.setUpdateDatetime(place2.getUpdateDatetime());
                rst.setDelFlg(place2.getDelFlg());
                rst.setNote(place2.getNote());
                randomShopService.insertRandomShopTwo(rst);
            }

            if(form.getPicUrl7() != null ){
                picService.insertPlacePicUrl(form.getPicUrl7(),place.getId());
            }else{
                picService.insertPlacePicUrl("",place.getId());
            }
            if(form.getPicUrl8() != null ){
                picService.insertPlacePicUrl(form.getPicUrl8(),place.getId());
            }else{
                picService.insertPlacePicUrl("",place.getId());
            }
            if(form.getPicUrl9() != null ){
                picService.insertPlacePicUrl(form.getPicUrl9(),place.getId());
            }else{
                picService.insertPlacePicUrl("",place.getId());
            }
            if(form.getPicUrl10() != null ){
                picService.insertPlacePicUrl(form.getPicUrl10(),place.getId());
            }else{
                picService.insertPlacePicUrl("",place.getId());
            }
            if(form.getPicUrl11() != null ){
                picService.insertPlacePicUrl(form.getPicUrl11(),place.getId());
            }else{
                picService.insertPlacePicUrl("",place.getId());
            }
            if(form.getPicUrl12() != null ){
                picService.insertPlacePicUrl(form.getPicUrl12(),place.getId());
            }else{
                picService.insertPlacePicUrl("",place.getId());
            }

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

            int restRadio = 0;
            restRadio = form.getRestRadio();

            if (restRadio == 2 && stockList != null && stockList.size() > 0) {
                shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.PLACE, Constant.SHOP_TAG.REST_WEEK, rest);
            }

            if (restRadio == 1 && form.getRestDates() != null) {
                shopTagService.insertShopTags(shopId, Constant.SHOP_TAG.PLACE, Constant.SHOP_TAG.REST_DATE, form.getRestDates());
            }

            txManager.commit(txStatus);
            session.setAttribute("error",0);
        } catch (Exception e) {
            session.setAttribute("error",1);
            txManager.rollback(txStatus);
            logger.error("regist Place failed!, error=" + e.getMessage());
            logger.error("regist Place failed!, error=" + e.toString());
            e.printStackTrace();
        }
        return "redirect:/admin/place/list/";
    }

    @RequestMapping(value = "/admin/place/edit/{id}/", method = RequestMethod.GET)
    public String placeUpdate(Model model, @PathVariable Long id) {

        PlaceForm form = new PlaceForm();
        PlaceDetail detail = placeService.selectByPlaceListId(id);
        Place place = placeService.selectById(id);

        form.setPlaceListId(id);
        form.setArea(place.getArea());
        form.setUuid(place.getUuid());
        form.setIntroduce(place.getIntroduce());
        form.setCoordinate1(detail.getCoordinate1());
        form.setCoordinate2(detail.getCoordinate2());
        form.setPublishStart(DateUtil.format(place.getPublishStart(),DateUtil.yyyy_MM_dd));
        form.setPublishEnd(DateUtil.format(place.getPublishEnd(),DateUtil.yyyy_MM_dd));
        List<TagDetail> areaTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.AREA);
        List<Integer> restDateList = shopTagService.getContents(id, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_DATE);
        List<Integer> restWeekList = shopTagService.getContents(id, Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_WEEK);

        List<Pic> listPic = picService.getShopUrlList(place.getId(),Constant.EVENT_PIC_TYPE.PLACE);
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
                form.setPicUrl11(listPic.get(4).getPicUrl());
            }
            if(listPic.size()>5 && listPic.get(5).getPicUrl() != null && listPic.get(5).getPicUrl() !=""){
                form.setPicUrl12(listPic.get(5).getPicUrl());
            }
        }

        List<ShopRestForm> restFormList = new ArrayList<ShopRestForm>();
        List<Integer> restWeeksList = new ArrayList<Integer>();
        List<Integer> restWeekDaysList = new ArrayList<Integer>();

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

        form.setRestWeek(restWeeksList);
        form.setRestWeekDay(restWeekDaysList);
        form.setRestDates(restDateList);

        List<TagDetail> restDayTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_DATE);
        List<TagDetail> restWeekTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_WEEK);
        List<TagDetail> restWeekDayTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.REST_WEEKDAY);

        if (detail != null) {
            BeanUtils.copyProperties(detail, form);
            form.setPicUrl(place.getPicUrl());
            form.setPicUrl1(detail.getPicUrl());
            form.setPicUrl2(detail.getPicUrl1());
            form.setPicUrl3(detail.getPicUrl2());
            form.setPicUrl4(detail.getPicUrl3());
            form.setPicUrl5(detail.getPicUrl4());
            form.setFlicUrl(detail.getFlicUrl());
            form.setVideoUrl(detail.getVideoUrl());
            model.addAttribute("areaList", areaTagList);
            model.addAttribute("form", form);
            model.addAttribute("restDateList", restDayTagList);
            model.addAttribute("restWeekList", restWeekTagList);
            model.addAttribute("restWeekDayList", restWeekDayTagList);
            model.addAttribute("restFormList", restFormList);
            return "admin/placeEdit";
        } else {
            return "redirect:/admin/place/list/";
        }
    }

    @RequestMapping(value = "/admin/place/update/", method = RequestMethod.POST)
    public String placeUpdate(@ModelAttribute PlaceForm form,HttpServletRequest request) throws APIException {
        HttpSession session = request.getSession();
        // upload files
        try {
            if (!form.getPicFile().isEmpty()) {
                form.setPicUrl(awsService.postMiddleThumbnailFile(form.getPicFile()));
            }
            //main1
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
            logger.error("update Place photos failed!, error=" + e.getMessage());
            logger.error("update Place photos failed!, error=" + e.toString());
            e.printStackTrace();
            throw new APIException(JsonStatus.DATA_SAVE_FAILED);
        }

        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try {
            Place place = placeService.updatePlace(form);
            String location = tagDetailService.getDesc(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.AREA, form.getArea());
            Place place2 = placeService.getPlace(place.getId());
            form.setLocation(location);
            placeService.updatePlaceDetail(form);

            if(place2 != null){
                RandomShopOne rso = new RandomShopOne();
                rso.setUuid(place2.getUuid());
                rso.setShopType((byte)Constant.SHOP_TAG.PLACE);
                rso.setArea((byte)place2.getArea().intValue());
                rso.setNow4((byte)0);
                rso.setNow5((byte)0);
                rso.setPublishStart(place2.getPublishStart());
                rso.setPublishEnd(place2.getPublishEnd());
                rso.setCreateDatetime(place2.getCreateDatetime());
                rso.setUpdateDatetime(place2.getUpdateDatetime());
                rso.setDelFlg(place2.getDelFlg());
                rso.setNote(place2.getNote());
                randomShopService.updateRandomShopOne(rso);

                RandomShopTwo rst = new RandomShopTwo();
                rst.setUuid(place2.getUuid());
                rst.setShopType((byte)Constant.SHOP_TAG.PLACE);
                rst.setArea((byte)0);
                rst.setNow4((byte)0);
                rst.setNow5((byte)0);
                rst.setPublishStart(place2.getPublishStart());
                rst.setPublishEnd(place2.getPublishEnd());
                rst.setCreateDatetime(place2.getCreateDatetime());
                rst.setUpdateDatetime(place2.getUpdateDatetime());
                rst.setDelFlg(place2.getDelFlg());
                rst.setNote(place2.getNote());
                randomShopService.updateRandomShopTwo(rst);
            }

            Long idPicurl7 =0L ;
            Long idPpicurl8 =0L ;
            Long idPpicurl9 =0L;
            Long idPpicurl10 =0L;
            Long idPpicurl11 =0L;
            Long idPpicurl12 =0L;

            List<Pic> listPic = picService.getShopUrlList(place.getId(),Constant.EVENT_PIC_TYPE.PLACE);
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
                placeService.deleteDetailVideo(place.getId());
            }else{
                picService.updatePlacePicUrl("",place.getId(),idPpicurl11);
            }
            if(form.getPicUrl12()!=null && !form.getPicUrl12().equals("")){
                placeService.deleteDetailFlic(place.getId());
            }else{
                picService.updatePlacePicUrl("",place.getId(),idPpicurl12);
            }

//            picService.deleteShopPicUrl(form.getPlaceListId(),Constant.EVENT_PIC_TYPE.PLACE);
            if(form.getPicUrl7() != null ){
                picService.updatePlacePicUrl(form.getPicUrl7(),place.getId(),idPicurl7);
            }
            if(form.getPicUrl8() != null ){
                picService.updatePlacePicUrl(form.getPicUrl8(),place.getId(),idPpicurl8);
            }
            if(form.getPicUrl9() != null ){
                picService.updatePlacePicUrl(form.getPicUrl9(),place.getId(),idPpicurl9);
            }
            if(form.getPicUrl10() != null){
                picService.updatePlacePicUrl(form.getPicUrl10(),place.getId(),idPpicurl10);
            }
            if(form.getPicUrl11() != null ){
                picService.updatePlacePicUrl(form.getPicUrl11(),place.getId(),idPpicurl11);
            }
            if(form.getPicUrl12() != null ){
                picService.updatePlacePicUrl(form.getPicUrl12(),place.getId(),idPpicurl12);
            }else{
                picService.updatePlacePicUrl("",place.getId(),idPpicurl12);
            }

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

            if (restRadio == 2 && stockList != null && stockList.size() > 1) {
                shopTagService.deleteShopTags(form.getPlaceListId(), Constant.SHOP_TAG.PLACE, Constant.SHOP_TAG.REST_WEEK);
                shopTagService.deleteShopTags(form.getPlaceListId(), Constant.SHOP_TAG.PLACE, Constant.SHOP_TAG.REST_DATE);
                shopTagService.insertShopTags(form.getPlaceListId(), Constant.SHOP_TAG.PLACE, Constant.SHOP_TAG.REST_WEEK, rest);
            }

            if (restRadio == 1 && form.getRestDates() != null && form.getRestDates().size()>0) {
                shopTagService.deleteShopTags(form.getPlaceListId(), Constant.SHOP_TAG.PLACE, Constant.SHOP_TAG.REST_DATE);
                shopTagService.deleteShopTags(form.getPlaceListId(), Constant.SHOP_TAG.PLACE, Constant.SHOP_TAG.REST_WEEK);
                shopTagService.insertShopTags(form.getPlaceListId(), Constant.SHOP_TAG.PLACE, Constant.SHOP_TAG.REST_DATE, form.getRestDates());
            }
            txManager.commit(txStatus);
            session.setAttribute("error",0);
        } catch (Exception e) {
            txManager.rollback(txStatus);
            session.setAttribute("error",1);
            logger.error("Update Place failed!, error=" + e.getMessage());
            logger.error("Update Place failed!, error=" + e.toString());
            e.printStackTrace();
        }
        return "redirect:/admin/place/list/";
    }

    @RequestMapping(value = "/admin/place/delete/", method = RequestMethod.POST)
    public String placeDelete(@ModelAttribute PlaceForm form,HttpServletRequest request) {
        HttpSession session = request.getSession();
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try {
            int countPlace = placeService.deletePlace(form);
            int countDetail = placeService.deletePlaceDetail(form);
            adminService.deleteAdminCustomerByShopIdAndType(form.getId(),2);
            txManager.commit(txStatus);
            session.setAttribute("error",0);
        } catch (Exception ex) {
            session.setAttribute("error",1);
            logger.error("Delete Place failed!, error=" + ex.getMessage());
            logger.error("Delete Place failed!, error=" + ex.toString());
            ex.printStackTrace();
            txManager.rollback(txStatus);
        }
        return "redirect:/admin/place/list/";
    }
}
