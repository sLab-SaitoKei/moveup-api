package jp.co.vermore.controller.admin;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.DatatablesJsonObject;
import jp.co.vermore.common.JsonStatus;
import jp.co.vermore.common.mvc.APIException;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.entity.*;
import jp.co.vermore.form.admin.CorporateInfoForm;
import jp.co.vermore.form.admin.CorporateInfoListForm;
import jp.co.vermore.form.admin.RoleFrom;
import jp.co.vermore.form.admin.ShopForm;
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
import java.util.ArrayList;
import java.util.List;

/**
 * AdminCorporateInfoController
 * Created by wubin.
 * <p>
 * DateTime: 2018/04/17 15:14
 * Copyright: sLab, Corp
 */
@Controller
public class AdminCorporateInfoController extends BaseController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopTagService shopTagService;

    @Autowired
    private TagDetailService tagDetailService;

    @Autowired
    private CorporateInfoService corporateInfoService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private PicService picService;

    @Autowired
    private AWSService awsService;

    @Autowired
    private RandomShopService randomShopService;

    @Autowired
    PlatformTransactionManager txManager;

    @RequestMapping(value = "/admin/corporateinfo/list/", method = RequestMethod.GET)
    public String corporateInfo(Model model,HttpServletRequest request) {

        int errorCode = 0;
        if(!request.getSession().isNew()){
            if(request.getSession().getAttribute("error") != null && request.getSession().getAttribute("error") != ""){
                errorCode = (int)request.getSession().getAttribute("error");
                request.getSession().setAttribute("error",0);
            }
        }
        model.addAttribute("errorCode", errorCode);


        return "admin/corporateInfoList";
    }

    @RequestMapping(value = "/admin/corporateinfo/list/", method = RequestMethod.POST)
    @ResponseBody
    public DatatablesJsonObject corporateInfoList(@RequestBody CorporateInfoListForm form){
        logger.debug("----1----");
        // set order statement
        if(form.getOrder().size() > 0
                && form.getColumns().get(form.getOrder().get(0).getColumn()).getName() != null
                && form.getColumns().get(form.getOrder().get(0).getColumn()).getName().length() > 0){
            form.setOrderStatement(form.getColumns().get(form.getOrder().get(0).getColumn()).getName() + " " + form.getOrder().get(0).getDir());
            logger.debug("----2----order statement="+form.getOrderStatement());
        }else{
            form.setOrderStatement("id");
            logger.debug("----2----order statement="+form.getOrderStatement());
        }
        logger.debug("----3----");

        // query data

        int totalCountFiltered = corporateInfoService.getCorporateInfoCountByCondition(form);
        int totalCount = corporateInfoService.getCorporateInfoCount();
        List<ShopForm> dataList = new ArrayList<>();
        List<CorporateInfoDetail> rtnList = corporateInfoService.getCorporateInfoAllByCondition(form);
        ShopForm dataForm;
        int content;
        String genre;

        for (CorporateInfoDetail cid : rtnList) {
            dataForm = new ShopForm();
            dataForm.setId(cid.getId());
            dataForm.setShopListId(cid.getShopListId());
            dataForm.setName(cid.getName());
            dataForm.setTitle(cid.getTitle());
            dataForm.setTel(cid.getTel());
            dataForm.setAddress(cid.getAddress());
            dataForm.setPublishStart(cid.getPublishStart());
            dataForm.setPublishEnd(cid.getPublishEnd());
            content = shopTagService.getContent(cid.getShopListId(), Constant.SHOP_TAG.CORPORATE_INFO, Constant.SHOP_TAG.FIRST_MENU);
            genre = tagDetailService.getDesc(Constant.SHOP_TAG.CORPORATE_INFO, Constant.SHOP_TAG.FIRST_MENU, content);
            dataForm.setGenre(genre);
            if(form.getGenre() == null || form.getGenre() == ""){
                dataList.add(dataForm);
            }else{
                if(genre == null){
                    totalCountFiltered--;
                }else{
                    if(genre.indexOf(form.getGenre()) < 0){
                        totalCountFiltered--;
                    }else{
                        dataList.add(dataForm);
                    }
                }
            }
        }

        logger.debug("----4----data count="+dataList.size());
        logger.debug("----5----total filtered="+totalCountFiltered);
        logger.debug("----6----total count="+totalCount);
        logger.debug("----7----page="+form.getDraw());

        // return json data
        DatatablesJsonObject jsonparse = new DatatablesJsonObject();
        jsonparse.setDraw(form.getDraw());
        jsonparse.setRecordsFiltered(totalCountFiltered);
        jsonparse.setRecordsTotal(totalCount);
        jsonparse.setData(dataList);
        logger.debug("----8----");
        return jsonparse;
    }

    @RequestMapping(value = "/admin/corporateinfo/regist/", method = RequestMethod.GET)
    public String shopRegistInput(Model model) {
        CorporateInfoForm form = new CorporateInfoForm();
        List<TagDetail> areaTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.AREA);
        List<TagDetail> stationTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.STATION);
        List<TagDetail> firstMenuList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.CORPORATE_INFO, Constant.SHOP_TAG.FIRST_MENU);

        model.addAttribute("form", form);
        model.addAttribute("areaList", areaTagList);
        model.addAttribute("stationList", stationTagList);
        model.addAttribute("firstMenuList", firstMenuList);

        return "admin/corporateInfoRegist";
    }

    @RequestMapping(value = "/admin/corporateinfo/regist/", method = RequestMethod.POST)
    public String shopRegist(@ModelAttribute CorporateInfoForm form, Model model
            ,HttpServletRequest request) throws APIException {
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
        } catch (Exception e) {
            session.setAttribute("error",1);
            throw new APIException(JsonStatus.DATA_SAVE_FAILED);
        }

        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try {
            Shop shop = shopService.insertCorporateList(form);
            Long id = corporateInfoService.insertCorporateInfoDetail(form, shop.getId());
            Shop shop2 = shopService.getShopById(shop.getId());

            if(shop2 != null){
                RandomShopOne rso = new RandomShopOne();
                rso.setUuid(shop2.getUuid());
                rso.setShopType((byte)Constant.SHOP_TAG.CORPORATE_INFO);
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
                rst.setShopType((byte)Constant.SHOP_TAG.CORPORATE_INFO);
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

            form.setShopId(shop.getId());
            form.setUuid(shop.getUuid());
            Long role = adminService.insertAdminRole(form);
            form.setRole(role);
            AdminCustomer customer = adminService.insertAdminCustomer(form);
            form.setLoginName(customer.getLoginName());
            form.setCustomer(customer);

            if(form.getPicUrl7() != null){
                picService.insertInfoPicUrl(form.getPicUrl7(),shop.getId());
            }else{
                picService.insertInfoPicUrl("",shop.getId());
            }
            if(form.getPicUrl8() != null){
                picService.insertInfoPicUrl(form.getPicUrl8(),shop.getId());
            }else{
                picService.insertInfoPicUrl("",shop.getId());
            }
            if(form.getPicUrl9() != null ){
                picService.insertInfoPicUrl(form.getPicUrl9(),shop.getId());
            }else{
                picService.insertInfoPicUrl("",shop.getId());
            }
            if(form.getPicUrl10() != null ){
                picService.insertInfoPicUrl(form.getPicUrl10(),shop.getId());
            }else{
                picService.insertInfoPicUrl("",shop.getId());
            }
            if(form.getPicUrl11() != null){
                picService.insertInfoPicUrl(form.getPicUrl11(),shop.getId());
            }else{
                picService.insertInfoPicUrl("",shop.getId());
            }
            if(form.getPicUrl12() != null){
                picService.insertInfoPicUrl(form.getPicUrl12(),shop.getId());
            }else{
                picService.insertInfoPicUrl("",shop.getId());
            }

            shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.CORPORATE_INFO, Constant.SHOP_TAG.AREA, form.getArea());
            shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.CORPORATE_INFO, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
            shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.CORPORATE_INFO, Constant.SHOP_TAG.STATION, form.getStation());
            shopTagService.insertShopTag(shop.getId(), Constant.SHOP_TAG.CORPORATE_INFO, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());

            txManager.commit(txStatus);
            session.setAttribute("error",0);
        } catch (Exception e) {
            txManager.rollback(txStatus);
            session.setAttribute("error",1);
            logger.error("regist CorporateInfo failed!, error=" + e.getMessage());
            logger.error("regist CorporateInfo failed!, error=" + e.toString());
        }
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

    @RequestMapping(value = "/admin/corporateinfo/edit/{id}/", method = RequestMethod.GET)
    public String shopUpdate(Model model, @PathVariable Long id) {

        CorporateInfoForm form = new CorporateInfoForm();
        Shop shop = shopService.getShopById(id);
        CorporateInfoDetail detail = corporateInfoService.getDetailByShopId(shop.getId());

        int area = shopTagService.getContent(id, Constant.SHOP_TAG.CORPORATE_INFO, Constant.SHOP_TAG.AREA);
        int firstMenu = shopTagService.getContent(id, Constant.SHOP_TAG.CORPORATE_INFO, Constant.SHOP_TAG.FIRST_MENU);
        int station = shopTagService.getContent(id, Constant.SHOP_TAG.CORPORATE_INFO, Constant.SHOP_TAG.STATION);
        int walkTime = shopTagService.getContent(id, Constant.SHOP_TAG.CORPORATE_INFO, Constant.SHOP_TAG.WALK_TIME);

        List<Pic> listPic = picService.getShopUrlList(shop.getId(),Constant.EVENT_PIC_TYPE.INFO);
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

        form.setId(detail.getId());
        form.setShopId(id);
        form.setUuid(shop.getUuid());
        form.setShopListId(shop.getId());
        form.setExcerpt(shop.getExcerpt());
        form.setPicUrl(shop.getPicUrl());
        form.setArea(area);
        form.setCoordinate1(shop.getCoordinate1());
        form.setCoordinate2(shop.getCoordinate2());
        form.setFirstMenu(firstMenu);
        form.setStation(station);
        form.setWalkTime(walkTime);
        form.setPublishStart(DateUtil.dateToStringyyyy_MM_dd(shop.getPublishStart()));
        form.setPublishEnd(DateUtil.dateToStringyyyy_MM_dd(shop.getPublishEnd()));

        List<TagDetail> firstMenuList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.CORPORATE_INFO, Constant.SHOP_TAG.FIRST_MENU);
        List<TagDetail> areaTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.AREA);
        List<TagDetail> stationTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.STATION);

        if (shop != null) {
            BeanUtils.copyProperties(detail, form);
            model.addAttribute("firstMenuList", firstMenuList);
            model.addAttribute("areaList", areaTagList);
            model.addAttribute("stationList", stationTagList);
            model.addAttribute("form", form);
            return "admin/corporateInfoEdit";
        } else {
            return "redirect:/admin/corporateinfo/list/";
        }
    }

    @RequestMapping(value = "/admin/corporateinfo/update/", method = RequestMethod.POST)
    public String shopUpdate(@ModelAttribute CorporateInfoForm form, HttpServletRequest request) throws APIException {
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
        } catch (Exception e) {
            session.setAttribute("error",1);
            e.printStackTrace();
            throw new APIException(JsonStatus.DATA_SAVE_FAILED);
        }

        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try {
            Shop shop = shopService.updateCorporateList(form);
            Long shopId = corporateInfoService.updateCorporateInfoDetail(form);
            Shop shop2 = shopService.getShopById(shop.getId());

            if(shop2 != null){
                RandomShopOne rso = new RandomShopOne();
                rso.setUuid(shop2.getUuid());
                rso.setShopType((byte)Constant.SHOP_TAG.CORPORATE_INFO);
                rso.setArea((byte)0);
                rso.setNow4((byte)0);
                rso.setNow5((byte)0);
                rso.setPublishStart(shop2.getPublishStart());
                rso.setPublishEnd(shop2.getPublishEnd());
                rso.setCreateDatetime(shop2.getCreateDatetime());
                rso.setUpdateDatetime(shop2.getUpdateDatetime());
                rso.setDelFlg(shop2.getDelFlg());
                rso.setNote(shop2.getNote());
                randomShopService.updateRandomShopOne(rso);

                RandomShopTwo rst = new RandomShopTwo();
                rst.setUuid(shop2.getUuid());
                rst.setShopType((byte)Constant.SHOP_TAG.CORPORATE_INFO);
                rst.setArea((byte)0);
                rst.setNow4((byte)0);
                rst.setNow5((byte)0);
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

            List<Pic> listPic = picService.getShopUrlList(shopId,Constant.EVENT_PIC_TYPE.INFO);
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
                corporateInfoService.deleteDetailVideo(shopId);
            }else{
                picService.updateInfoPicUrl("",shopId,idPpicurl11);
            }
            if(form.getPicUrl12()!=null && !form.getPicUrl12().equals("")){
                corporateInfoService.deleteDetailFlic(shopId);
            }else{
                picService.updateShopPicUrl("",shopId,idPpicurl12);
            }

            if(form.getPicUrl7() != null  ){
                picService.updateInfoPicUrl(form.getPicUrl7(),shopId,idPicurl7);
            }
            if(form.getPicUrl8() != null ){
                picService.updateInfoPicUrl(form.getPicUrl8(),shopId,idPpicurl8);
            }
            if(form.getPicUrl9() != null ){
                picService.updateInfoPicUrl(form.getPicUrl9(),shopId,idPpicurl9);
            }
            if(form.getPicUrl10() != null ){
                picService.updateInfoPicUrl(form.getPicUrl10(),shopId,idPpicurl10);
            }
            if(form.getPicUrl11() != null ){
                picService.updateInfoPicUrl(form.getPicUrl11(),shopId,idPpicurl11);
            }
            if(form.getPicUrl12() != null){
                picService.updateInfoPicUrl(form.getPicUrl12(),shopId,idPpicurl12);
            }else{
                picService.updateInfoPicUrl("",shopId,idPpicurl12);
            }

            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.CORPORATE_INFO, Constant.SHOP_TAG.AREA) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.CORPORATE_INFO, Constant.SHOP_TAG.AREA, form.getArea());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.CORPORATE_INFO, Constant.SHOP_TAG.AREA, form.getArea());
            }
            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.CORPORATE_INFO, Constant.SHOP_TAG.FIRST_MENU) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.CORPORATE_INFO, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.CORPORATE_INFO, Constant.SHOP_TAG.FIRST_MENU, form.getFirstMenu());
            }
            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.CORPORATE_INFO, Constant.SHOP_TAG.WALK_TIME) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.CORPORATE_INFO, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.CORPORATE_INFO, Constant.SHOP_TAG.WALK_TIME, form.getWalkTime());
            }
            if (shopTagService.getContent(shopId, Constant.SHOP_TAG.CORPORATE_INFO, Constant.SHOP_TAG.STATION) == 0) {
                shopTagService.insertShopTag(shopId, Constant.SHOP_TAG.CORPORATE_INFO, Constant.SHOP_TAG.STATION, form.getStation());
            } else {
                shopTagService.updateShopTag(shopId, Constant.SHOP_TAG.CORPORATE_INFO, Constant.SHOP_TAG.STATION, form.getStation());
            }
            txManager.commit(txStatus);
            session.setAttribute("error",0);
        } catch (Exception e) {
            txManager.rollback(txStatus);
            session.setAttribute("error",1);
            logger.error("Update CorporateInfo failed!, error=" + e.getMessage());
            logger.error("Update CorporateInfo failed!, error=" + e.toString());
        }
        return "redirect:/admin/corporateinfo/list/";
    }

    // Shop admin delete
    @RequestMapping(value = "/admin/corporateinfo/delete/", method = RequestMethod.POST)
    public String corporateInfoDelete(HttpServletRequest hsr
            ,HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long id = Long.valueOf(hsr.getParameterValues("shopListId")[0]);
        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);
        try {
            Long shopId = corporateInfoService.getCorporateId(id);
            shopService.deleteShop(id);
            corporateInfoService.deleteCorporateInfoDetail(id);
            shopTagService.deleteShopTag(shopId);

            txManager.commit(txStatus);
            session.setAttribute("error",0);
        } catch (Exception e) {
            txManager.rollback(txStatus);
            session.setAttribute("error",1);
            logger.error("Delete CorporateInfo failed!, error=" + e.getMessage());
            logger.error("Delete CorporateInfo failed!, error=" + e.toString());
        }
        return "redirect:/admin/corporateinfo/list/";
    }
}
