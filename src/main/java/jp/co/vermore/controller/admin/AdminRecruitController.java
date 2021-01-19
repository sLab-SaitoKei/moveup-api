package jp.co.vermore.controller.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.JsonObject;
import jp.co.vermore.common.JsonStatus;
import jp.co.vermore.common.mvc.APIException;
import jp.co.vermore.entity.*;
import jp.co.vermore.form.admin.RecruitListForm;
import jp.co.vermore.common.DatatablesJsonObject;
import jp.co.vermore.form.admin.RoleFrom;
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

import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.form.admin.RecruitForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * NewsController
 * Created by wubin.
 * <p>
 * DateTime: 2018/03/03 11:13
 * Copyright: sLab, Corp
 */
@Controller
public class AdminRecruitController extends BaseController {

    @Autowired
    private RecruitService recruitService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private TagDetailService tagDetailService;

    @Autowired
    private PicService picService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RandomShopService randomShopService;

    @Autowired
    private PlatformTransactionManager txManager;

    @Autowired
    private AWSService awsService;

    @RequestMapping(value = "/admin/recruit/list/", method = RequestMethod.GET)
    public String recruitAll(Model model,HttpServletRequest request) {
        logger.debug("----1----");

        int errorCode = 0;
        if(!request.getSession().isNew()){
            if(request.getSession().getAttribute("error") != null && request.getSession().getAttribute("error") != ""){
                errorCode = (int)request.getSession().getAttribute("error");
                request.getSession().setAttribute("error",0);
            }
        }
        model.addAttribute("errorCode", errorCode);

        List<Recruit> news = recruitService.getRecruitAll();
        logger.debug("----2----news count="+news.size());
        RecruitForm form = new RecruitForm();
        model.addAttribute("recruitDeleteForm", form);
        model.addAttribute("recruit_all", news);
        logger.debug("----3----");
        return "admin/recruitList";
    }

    @RequestMapping(value = "/admin/recruit/list/", method = RequestMethod.POST)
    @ResponseBody
    public DatatablesJsonObject recruitList(@RequestBody RecruitListForm form){
        logger.debug("----1----");
        // set order statement
        if(form.getOrder().size() > 0
                && form.getColumns().get(form.getOrder().get(0).getColumn()).getName() != null
                && form.getColumns().get(form.getOrder().get(0).getColumn()).getName().length() > 0){
            form.setOrderStatement(form.getColumns().get(form.getOrder().get(0).getColumn()).getName() + " " + form.getOrder().get(0).getDir());
            logger.debug("----2----order statement="+form.getOrderStatement());
        }else{
            form.setOrderStatement("recruit_id");
            logger.debug("----2----order statement="+form.getOrderStatement());
        }
        logger.debug("----3----");

        // query data
        List<RecruitDetail> dataList = recruitService.getRecruitDetailAllByCondition(form);
        int totalCountFiltered = recruitService.getRecruitDetailCountByCondition(form);
        int totalCount = recruitService.getRecruitDetailCount();
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

    @RequestMapping(value = "/admin/recruit/regist/", method = RequestMethod.GET)
    public String recruitInsert(Model model) {
        logger.debug("----1----");
        List<Shop> shopList = shopService.getShopAll();
        List<TagDetail> modeTagList = tagDetailService.getTagDetailByModuleAndType(Constant.TAG_MODULE_TYPE.RECRUIT, Constant.RECRUIT_TAG_TYPE.MODE);
//        List<TagDetail> detailTagList = tagDetailService.getTagDetailByModuleAndType(Constant.TAG_MODULE_TYPE.RECRUIT, Constant.RECRUIT_TAG_TYPE.DETAIL);
//        List<TagDetail> featureTagList = tagDetailService.getTagDetailByModuleAndType(Constant.TAG_MODULE_TYPE.RECRUIT, Constant.RECRUIT_TAG_TYPE.FEATURE);
        List<TagDetail> treatmentTagList = tagDetailService.getTagDetailByModuleAndType(Constant.TAG_MODULE_TYPE.RECRUIT, Constant.RECRUIT_TAG_TYPE.TREATMENT);
        List<TagDetail> carerrTagList = tagDetailService.getTagDetailByModuleAndType(Constant.TAG_MODULE_TYPE.RECRUIT, Constant.RECRUIT_TAG_TYPE.CAREER);
        List<TagDetail> workPeriodTagList = tagDetailService.getTagDetailByModuleAndType(Constant.TAG_MODULE_TYPE.RECRUIT, Constant.RECRUIT_TAG_TYPE.WORK_PERIOD);
        List<TagDetail> workTimeTagList = tagDetailService.getTagDetailByModuleAndType(Constant.TAG_MODULE_TYPE.RECRUIT, Constant.RECRUIT_TAG_TYPE.WORK_TIME);
        List<TagDetail> capacityTagList = tagDetailService.getTagDetailByModuleAndType(Constant.TAG_MODULE_TYPE.RECRUIT, Constant.RECRUIT_TAG_TYPE.CAPACITY);
        List<TagDetail> workWayTagList = tagDetailService.getTagDetailByModuleAndType(Constant.TAG_MODULE_TYPE.RECRUIT, Constant.RECRUIT_TAG_TYPE.WORK_WAY);
        List<TagDetail> allowanceSpecialTagList = tagDetailService.getTagDetailByModuleAndType(Constant.TAG_MODULE_TYPE.RECRUIT, Constant.RECRUIT_TAG_TYPE.ALLOWANCE_SPECIAL);
        List<TagDetail> workEnvironmentTagList = tagDetailService.getTagDetailByModuleAndType(Constant.TAG_MODULE_TYPE.RECRUIT, Constant.RECRUIT_TAG_TYPE.WORK_ENVIRONMENT);
        List<TagDetail> treatmentNewTagList = tagDetailService.getTagDetailByModuleAndType(Constant.TAG_MODULE_TYPE.RECRUIT, Constant.RECRUIT_TAG_TYPE.TREATMENT_NEW);
        List<TagDetail> areaTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.AREA);
        logger.debug("----2----shop count="+shopList.size());

        RecruitForm form = new RecruitForm();
        model.addAttribute("shopList", shopList);
        model.addAttribute("modeMap", getMap(modeTagList));
//        model.addAttribute("detailMap", getMap(detailTagList));
//        model.addAttribute("featureMap", getMap(featureTagList));
        model.addAttribute("treatmentMap", getMap(treatmentTagList));
        model.addAttribute("areaList", areaTagList);
        model.addAttribute("carerrList", carerrTagList);
        model.addAttribute("workPeriodMap", getMap(workPeriodTagList));
        model.addAttribute("workTimeMap", getMap(workTimeTagList));
        model.addAttribute("capacityMap", getMap(capacityTagList));
        model.addAttribute("workWayMap", getMap(workWayTagList));
        model.addAttribute("allowanceSpecialMap", getMap(allowanceSpecialTagList));
        model.addAttribute("workEnvironmentMap", getMap(workEnvironmentTagList));
        model.addAttribute("treatmentNewMap", getMap(treatmentNewTagList));
        model.addAttribute("recruitForm", form);
        logger.debug("----3----");
        return "admin/recruitRegist";
    }

    @RequestMapping(value = "/admin/recruit/regist/", method = RequestMethod.POST)
    public String recruitInsert(@ModelAttribute RecruitForm recruitForm, Model model) throws APIException {
        logger.debug("----1----");
        try {
            if (!recruitForm.getPicFile().isEmpty()) {
                recruitForm.setPicUrl(awsService.postMiddleThumbnailFile(recruitForm.getPicFile()));
            }
            logger.debug("----2----pic url="+recruitForm.getPicUrl());
            if (!recruitForm.getPicFile1().isEmpty()) {
                recruitForm.setPicUrl1(awsService.postFile(recruitForm.getPicFile1()));
            }
            logger.debug("----3----pic url1="+recruitForm.getPicUrl1());
            if (!recruitForm.getPicFile2().isEmpty()) {
                recruitForm.setPicUrl2(awsService.postFile(recruitForm.getPicFile2()));
            }
            logger.debug("----4----pic url2="+recruitForm.getPicUrl2());
            if (!recruitForm.getPicFile3().isEmpty()) {
                recruitForm.setPicUrl3(awsService.postFile(recruitForm.getPicFile3()));
            }
            logger.debug("----5----pic url3="+recruitForm.getPicUrl3());
            if (!recruitForm.getPicFile4().isEmpty()) {
                recruitForm.setPicUrl4(awsService.postFile(recruitForm.getPicFile4()));
            }
            logger.debug("----6----pic url4="+recruitForm.getPicUrl4());
            if (!recruitForm.getPicFile5().isEmpty()) {
                recruitForm.setPicUrl5(awsService.postFile(recruitForm.getPicFile5()));
            }
            logger.debug("----7----pic url5="+recruitForm.getPicUrl5());
            if (!recruitForm.getPicFile6().isEmpty()) {
                recruitForm.setPicUrl6(awsService.postFile(recruitForm.getPicFile6()));
            }
            logger.debug("----8----pic url6="+recruitForm.getPicUrl6());
            if (!recruitForm.getPicFile7().isEmpty()) {
                recruitForm.setPicUrl7(awsService.postFile(recruitForm.getPicFile7()));
            }
            logger.debug("----9----pic url7="+recruitForm.getPicUrl7());
            if (!recruitForm.getPicFile8().isEmpty()) {
                recruitForm.setPicUrl8(awsService.postFile(recruitForm.getPicFile8()));
            }
            logger.debug("----10----pic url8="+recruitForm.getPicUrl8());
            if (!recruitForm.getPicFile9().isEmpty()) {
                recruitForm.setPicUrl9(awsService.postFile(recruitForm.getPicFile9()));
            }
            logger.debug("----11----pic url9="+recruitForm.getPicUrl9());
            if (!recruitForm.getPicFile10().isEmpty()) {
                recruitForm.setPicUrl10(awsService.postFile(recruitForm.getPicFile10()));
            }
            logger.debug("----12----pic url10="+recruitForm.getPicUrl10());
        }catch (IOException e) {
            throw new APIException(JsonStatus.DATA_SAVE_FAILED);
        }
        logger.debug("----13----");

        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try {
            // upload files

            if( recruitForm.getSearchId() != null && !recruitForm.getSearchId().equals("") ){
                Long shopId1 = shopService.getShopByUuid(recruitForm.getSearchId()).getId();
                recruitForm.setShopId(shopId1);
                Integer shoptype1 = shopService.getShopByUuid(recruitForm.getSearchId()).getShopType();
                recruitForm.setShopType(shoptype1);
            }

            Recruit recruit = recruitService.insertRecruit(recruitForm);
            Long countDetail = recruitService.insertRecruitDetail(recruitForm, recruit.getId());
            Recruit recruit2 = recruitService.getRecruit(recruit.getId());

            if(recruit2 != null){
                RandomShopOne rso = new RandomShopOne();
                rso.setUuid(recruit2.getUuid());
                rso.setShopType((byte)Constant.SHOP_TAG.RECRUIT);
                rso.setArea((byte)0);
                rso.setNow4((byte)0);
                rso.setNow5((byte)0);
                rso.setPublishStart(recruit2.getPublishStart());
                rso.setPublishEnd(recruit2.getPublishEnd());
                rso.setCreateDatetime(recruit2.getCreateDatetime());
                rso.setUpdateDatetime(recruit2.getUpdateDatetime());
                rso.setDelFlg(recruit2.getDelFlg());
                rso.setNote(recruit2.getNote());
                randomShopService.insertRandomShopOne(rso);

                RandomShopTwo rst = new RandomShopTwo();
                rst.setUuid(recruit2.getUuid());
                rst.setShopType((byte)Constant.SHOP_TAG.RECRUIT);
                rst.setArea((byte)0);
                rst.setNow4((byte)0);
                rst.setNow5((byte)0);
                rst.setPublishStart(recruit2.getPublishStart());
                rst.setPublishEnd(recruit2.getPublishEnd());
                rst.setCreateDatetime(recruit2.getCreateDatetime());
                rst.setUpdateDatetime(recruit2.getUpdateDatetime());
                rst.setDelFlg(recruit2.getDelFlg());
                rst.setNote(recruit2.getNote());
                randomShopService.insertRandomShopTwo(rst);
            }

            if(recruitForm.getPicUrl6() != null ){
                picService.insertRecruitPicUrl(recruitForm.getPicUrl6(),recruit.getId());
            }else{
                picService.insertRecruitPicUrl("",recruit.getId());
            }
            if(recruitForm.getPicUrl7() != null ){
                picService.insertRecruitPicUrl(recruitForm.getPicUrl7(),recruit.getId());
            }else{
                picService.insertRecruitPicUrl("",recruit.getId());
            }
            if(recruitForm.getPicUrl8() != null ){
                picService.insertRecruitPicUrl(recruitForm.getPicUrl8(),recruit.getId());
            }else{
                picService.insertRecruitPicUrl("",recruit.getId());
            }
            if(recruitForm.getPicUrl9() != null ){
                picService.insertRecruitPicUrl(recruitForm.getPicUrl9(),recruit.getId());
            }else{
                picService.insertRecruitPicUrl("",recruit.getId());
            }
            if(recruitForm.getPicUrl10() != null){
                picService.insertRecruitPicUrl(recruitForm.getPicUrl10(),recruit.getId());
            }else{
                picService.insertRecruitPicUrl("",recruit.getId());
            }

            recruitForm.setRecruitId(recruit.getId());
            recruitForm.setUuid(recruit.getUuid());
            Long role = adminService.insertAdminRole(recruitForm);
            recruitForm.setRole(role);
            AdminCustomer customer = adminService.insertAdminCustomer(recruitForm);
            recruitForm.setLoginName(customer.getLoginName());
            recruitForm.setCustomer(customer);

            // Update Tags
            if(recruitForm.getModeList().size()>0){
                recruitService.insertTags(recruit.getId(), Constant.RECRUIT_TAG_TYPE.MODE, recruitForm.getModeList());
            }
//            if(recruitForm.getDetailList().size()>0){
//                recruitService.insertTags(recruit.getId(), Constant.RECRUIT_TAG_TYPE.DETAIL, recruitForm.getDetailList());
//            }
//            if(recruitForm.getFeatureList().size()>0){
//                recruitService.insertTags(recruit.getId(), Constant.RECRUIT_TAG_TYPE.FEATURE, recruitForm.getFeatureList());
//            }
            if(recruitForm.getTreatmentList().size()>0){
                recruitService.insertTags(recruit.getId(), Constant.RECRUIT_TAG_TYPE.TREATMENT, recruitForm.getTreatmentList());
            }


            if(recruitForm.getWorkingTimeStart() != null && recruitForm.getWorkingTimeStart().length() > 0){
                recruitService.insertTag(recruit.getId(), Constant.RECRUIT_TAG_TYPE.WORKING_TIME_START, DateUtil.stringToUnixTime(recruitForm.getWorkingTimeStart()));
            }
            if(recruitForm.getWorkingTimeEnd() != null && recruitForm.getWorkingTimeEnd().length() > 0){
                recruitService.insertTag(recruit.getId(), Constant.RECRUIT_TAG_TYPE.WORKING_TIME_END, DateUtil.stringToUnixTime(recruitForm.getWorkingTimeEnd()));
            }
            if(recruitForm.getCarerr() > 0){
                recruitService.insertTag(recruit.getId(), Constant.RECRUIT_TAG_TYPE.CAREER, recruitForm.getCarerr());
            }
            if(recruitForm.getWorkPeriod().size() > 0){
                recruitService.insertTags(recruitForm.getRecruitId(), Constant.RECRUIT_TAG_TYPE.WORK_PERIOD, recruitForm.getWorkPeriod());
            }
            if(recruitForm.getWorkTimeList().size() > 0){
                recruitService.insertTags(recruitForm.getRecruitId(), Constant.RECRUIT_TAG_TYPE.WORK_TIME, recruitForm.getWorkTimeList());
            }
            if(recruitForm.getCapacityList().size() > 0){
                recruitService.insertTags(recruitForm.getRecruitId(), Constant.RECRUIT_TAG_TYPE.CAPACITY, recruitForm.getCapacityList());
            }
            if(recruitForm.getWorkWayList().size() > 0){
                recruitService.insertTags(recruitForm.getRecruitId(), Constant.RECRUIT_TAG_TYPE.WORK_WAY, recruitForm.getWorkWayList());
            }
            if(recruitForm.getAllowanceSpecialList().size() > 0){
                recruitService.insertTags(recruitForm.getRecruitId(), Constant.RECRUIT_TAG_TYPE.ALLOWANCE_SPECIAL, recruitForm.getAllowanceSpecialList());
            }
            if(recruitForm.getWorkEnvironmentList().size() > 0){
                recruitService.insertTags(recruitForm.getRecruitId(), Constant.RECRUIT_TAG_TYPE.WORK_ENVIRONMENT, recruitForm.getWorkEnvironmentList());
            }
            if(recruitForm.getTreatmentList().size() > 0){
                recruitService.insertTags(recruitForm.getRecruitId(), Constant.RECRUIT_TAG_TYPE.TREATMENT_NEW, recruitForm.getTreatmentList());
            }
            if(recruitForm.getArea() > 0){
                recruitService.insertTag(recruit.getId(), Constant.RECRUIT_TAG_TYPE.AREA, recruitForm.getArea());
            }

            txManager.commit(txStatus);
        }catch (Exception ex){
            logger.debug("----9----exception message="+ex.getMessage());
            logger.error("Recruit regist failed, error=" + ex.getMessage());
            logger.error("Recruit regist feed, error=" + ex.toString());
            ex.printStackTrace();
            txManager.rollback(txStatus);
        }
        logger.debug("----10----");
        RoleFrom roleFrom = new RoleFrom();
        roleFrom.setPassword(recruitForm.getCustomer().getPwd());
        AdminCustomer customer = adminService.selectAdminCustomerByRole(recruitForm.getRole());
        AdminRole role = adminService.selectById(recruitForm.getRole());
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

    @RequestMapping(value = "/admin/recruit/delete/", method = RequestMethod.POST)
    public String recruitDetailDelete(@ModelAttribute RecruitForm form) {
        logger.debug("----1----");
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try {
            int countRecruit = recruitService.deleteRecruit(form);
            int countDetail = recruitService.deleteRecruitDetail(form);
            adminService.deleteAdminCustomerByShopIdAndType(form.getId(),3);
            txManager.commit(txStatus);
        }catch (Exception ex){
            logger.debug("----2----exception message="+ex.getMessage());
            logger.error("Recruit delete failed, error=" + ex.getMessage());
            logger.error("Recruit delete feed, error=" + ex.toString());
            ex.printStackTrace();
            txManager.rollback(txStatus);
        }
        logger.debug("----3----");
        return "redirect:/admin/recruit/list/";
    }

    @RequestMapping(value = "/admin/recruit/update/{id}/", method = RequestMethod.GET)
    public String recruitUpdate(Model model , @PathVariable Long id) {
        logger.debug("----1----");
        RecruitForm recruitForm = new RecruitForm();
        Recruit recruit = recruitService.getRecruit(id);

        if(recruit != null){
            logger.debug("----2----");
            RecruitDetail recruitDetail = recruitService.getRecruitDetailByRecruit(id);
            List<Shop> shopList = shopService.getShopAll();
//            Long si =  recruitDetail.getShopId();
//            String uuid = shopService.getShopById(si).getUuid();

            List<Integer> area = recruitService.getContent(id, Constant.RECRUIT_TAG_TYPE.AREA);
            List<Integer> modeList = recruitService.getContent(id, Constant.RECRUIT_TAG_TYPE.MODE);
//            List<Integer> detailList = recruitService.getContent(id, Constant.RECRUIT_TAG_TYPE.DETAIL);
//            List<Integer> featureList = recruitService.getContent(id, Constant.RECRUIT_TAG_TYPE.FEATURE);
            List<Integer> treatmentList = recruitService.getContent(id, Constant.RECRUIT_TAG_TYPE.TREATMENT);
            List<Integer> workingTimeStartList = recruitService.getContent(id, Constant.RECRUIT_TAG_TYPE.WORKING_TIME_START);
            List<Integer> workingTimeEndList = recruitService.getContent(id, Constant.RECRUIT_TAG_TYPE.WORKING_TIME_END);
            List<Integer> carerrList = recruitService.getContent(id, Constant.RECRUIT_TAG_TYPE.CAREER);
            List<Integer> workPeriodList = recruitService.getContent(id, Constant.RECRUIT_TAG_TYPE.WORK_PERIOD);
            List<Integer> workTimeList = recruitService.getContent(id, Constant.RECRUIT_TAG_TYPE.WORK_TIME);
            List<Integer> capacityList = recruitService.getContent(id, Constant.RECRUIT_TAG_TYPE.CAPACITY);
            List<Integer> workWayList = recruitService.getContent(id, Constant.RECRUIT_TAG_TYPE.WORK_WAY);
            List<Integer> allowanceSpecialList = recruitService.getContent(id, Constant.RECRUIT_TAG_TYPE.ALLOWANCE_SPECIAL);
            List<Integer> workEnvironmentList = recruitService.getContent(id, Constant.RECRUIT_TAG_TYPE.WORK_ENVIRONMENT);
            List<Integer> treatmentNewList = recruitService.getContent(id, Constant.RECRUIT_TAG_TYPE.TREATMENT_NEW);
            logger.debug("----3----shop count="+shopList.size());

            if(area.size()>0){
                recruitForm.setArea(area.get(0));
            }

            recruitForm.setModeList(modeList);
//            recruitForm.setDetailList(detailList);
//            recruitForm.setFeatureList(featureList);
            recruitForm.setTreatmentList(treatmentList);
            recruitForm.setWorkPeriod(workPeriodList);
            recruitForm.setWorkTimeList(workTimeList);
            recruitForm.setCapacityList(capacityList);
            recruitForm.setWorkWayList(workWayList);
            recruitForm.setAllowanceSpecialList(allowanceSpecialList);
            recruitForm.setWorkEnvironmentList(workEnvironmentList);
            recruitForm.setTreatmentList(treatmentNewList);

            if(workingTimeStartList != null && workingTimeStartList.size() > 0){
                recruitForm.setWorkingTimeStart(DateUtil.unixTimeStringTime(workingTimeStartList.get(0)));
            }
            if(workingTimeEndList != null && workingTimeEndList.size() > 0){
                recruitForm.setWorkingTimeEnd(DateUtil.unixTimeStringTime(workingTimeEndList.get(0)));
            }
            logger.debug("----4----");

            List<TagDetail> modeTagList = tagDetailService.getTagDetailByModuleAndType(Constant.TAG_MODULE_TYPE.RECRUIT, Constant.RECRUIT_TAG_TYPE.MODE);
//            List<TagDetail> detailTagList = tagDetailService.getTagDetailByModuleAndType(Constant.TAG_MODULE_TYPE.RECRUIT, Constant.RECRUIT_TAG_TYPE.DETAIL);
//            List<TagDetail> featureTagList = tagDetailService.getTagDetailByModuleAndType(Constant.TAG_MODULE_TYPE.RECRUIT, Constant.RECRUIT_TAG_TYPE.FEATURE);
            List<TagDetail> treatmentTagList = tagDetailService.getTagDetailByModuleAndType(Constant.TAG_MODULE_TYPE.RECRUIT, Constant.RECRUIT_TAG_TYPE.TREATMENT);
            List<TagDetail> carerrTagList = tagDetailService.getTagDetailByModuleAndType(Constant.TAG_MODULE_TYPE.RECRUIT, Constant.RECRUIT_TAG_TYPE.CAREER);
            List<TagDetail> workPeriodTagList = tagDetailService.getTagDetailByModuleAndType(Constant.TAG_MODULE_TYPE.RECRUIT, Constant.RECRUIT_TAG_TYPE.WORK_PERIOD);
            List<TagDetail> workTimeTagList = tagDetailService.getTagDetailByModuleAndType(Constant.TAG_MODULE_TYPE.RECRUIT, Constant.RECRUIT_TAG_TYPE.WORK_TIME);
            List<TagDetail> capacityTagList = tagDetailService.getTagDetailByModuleAndType(Constant.TAG_MODULE_TYPE.RECRUIT, Constant.RECRUIT_TAG_TYPE.CAPACITY);
            List<TagDetail> workWayTagList = tagDetailService.getTagDetailByModuleAndType(Constant.TAG_MODULE_TYPE.RECRUIT, Constant.RECRUIT_TAG_TYPE.WORK_WAY);
            List<TagDetail> allowanceSpecialTagList = tagDetailService.getTagDetailByModuleAndType(Constant.TAG_MODULE_TYPE.RECRUIT, Constant.RECRUIT_TAG_TYPE.ALLOWANCE_SPECIAL);
            List<TagDetail> workEnvironmentTagList = tagDetailService.getTagDetailByModuleAndType(Constant.TAG_MODULE_TYPE.RECRUIT, Constant.RECRUIT_TAG_TYPE.WORK_ENVIRONMENT);
            List<TagDetail> treatmentNewTagList = tagDetailService.getTagDetailByModuleAndType(Constant.TAG_MODULE_TYPE.RECRUIT, Constant.RECRUIT_TAG_TYPE.TREATMENT_NEW);
            List<TagDetail> areaTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.AREA);

            List<Pic> listPic = picService.getShopUrlList(recruit.getId(),Constant.EVENT_PIC_TYPE.RECRUIT);
            if(listPic != null && listPic.size()>0){
                if(listPic.get(0).getPicUrl() != null && listPic.get(0).getPicUrl() !=""){
                    recruitForm.setPicUrl6(listPic.get(0).getPicUrl());
                }
                if(listPic.size()>1 && listPic.get(1).getPicUrl() != null && listPic.get(1).getPicUrl() !=""){
                    recruitForm.setPicUrl7(listPic.get(1).getPicUrl());
                }
                if(listPic.size()>2 && listPic.get(2).getPicUrl() != null && listPic.get(2).getPicUrl() !=""){
                    recruitForm.setPicUrl8(listPic.get(2).getPicUrl());
                }
                if(listPic.size()>3 && listPic.get(3).getPicUrl() != null && listPic.get(3).getPicUrl() !=""){
                    recruitForm.setPicUrl9(listPic.get(3).getPicUrl());
                }
                if(listPic.size()>4 && listPic.get(4).getPicUrl() != null && listPic.get(4).getPicUrl() !=""){
                    recruitForm.setPicUrl10(listPic.get(4).getPicUrl());
                }
            }

            logger.debug("----5----");
            BeanUtils.copyProperties(recruitDetail, recruitForm);
            // set recruit list page image
            recruitForm.setPicUrl(recruit.getPicUrl1());
            if(carerrList.size() > 0) {
                recruitForm.setCarerr(carerrList.get(0));
            }else{
                recruitForm.setCarerr(0);
            }
            recruitForm.setUuid(recruit.getUuid());
            recruitForm.setPublishStart(DateUtil.dateToStringyyyy_MM_dd(recruit.getPublishStart()));
            recruitForm.setPublishEnd(DateUtil.dateToStringyyyy_MM_dd(recruit.getPublishEnd()));

            if(recruit.getShopId() != 0 &&recruit.getShopId() != null){
               Shop shop =  shopService.getShopById(recruit.getShopId());
               if(shop != null){
                   recruitForm.setShopUuid(shop.getUuid());
               }else {
                   recruitForm.setShopUuid("");
               }
            }

            model.addAttribute("shopList", shopList);
            model.addAttribute("areaList", areaTagList);
            model.addAttribute("modeMap", getMap(modeTagList));
//            model.addAttribute("detailMap", getMap(detailTagList));
//            model.addAttribute("featureMap", getMap(featureTagList));
            model.addAttribute("treatmentMap", getMap(treatmentTagList));
            model.addAttribute("workPeriodMap", getMap(workPeriodTagList));
            model.addAttribute("workTimeMap", getMap(workTimeTagList));
            model.addAttribute("capacityMap", getMap(capacityTagList));
            model.addAttribute("workWayMap", getMap(workWayTagList));
            model.addAttribute("allowanceSpecialMap", getMap(allowanceSpecialTagList));
            model.addAttribute("workEnvironmentMap", getMap(workEnvironmentTagList));
            model.addAttribute("treatmentNewMap", getMap(treatmentNewTagList));
            model.addAttribute("carerrList", carerrTagList);
            model.addAttribute("recruitForm", recruitForm);
            logger.debug("----6----");
            return "admin/recruitEdit";
        }else {
            logger.debug("----7----");
            return "redirect:/admin/recruit/list/";
        }
    }

    @RequestMapping(value = "/admin/recruit/update/", method = RequestMethod.POST)
    public String recruitUpdate1(@ModelAttribute RecruitForm recruitForm,HttpServletRequest request) throws APIException {
        logger.debug("----1----");

        try {
            if (!recruitForm.getPicFile().isEmpty()) {
                recruitForm.setPicUrl(awsService.postMiddleThumbnailFile(recruitForm.getPicFile()));
            }
            logger.debug("----2----pic url="+recruitForm.getPicUrl());
            if (!recruitForm.getPicFile1().isEmpty()) {
                recruitForm.setPicUrl1(awsService.postFile(recruitForm.getPicFile1()));
            }
            logger.debug("----3----pic url1="+recruitForm.getPicUrl1());
            if (!recruitForm.getPicFile2().isEmpty()) {
                recruitForm.setPicUrl2(awsService.postFile(recruitForm.getPicFile2()));
            }
            logger.debug("----4----pic url2="+recruitForm.getPicUrl2());
            if (!recruitForm.getPicFile3().isEmpty()) {
                recruitForm.setPicUrl3(awsService.postFile(recruitForm.getPicFile3()));
            }
            logger.debug("----5----pic url3="+recruitForm.getPicUrl3());
            if (!recruitForm.getPicFile4().isEmpty()) {
                recruitForm.setPicUrl4(awsService.postFile(recruitForm.getPicFile4()));
            }
            logger.debug("----6----pic url4="+recruitForm.getPicUrl4());
            if (!recruitForm.getPicFile5().isEmpty()) {
                recruitForm.setPicUrl5(awsService.postFile(recruitForm.getPicFile5()));
            }
            logger.debug("----7----pic url5="+recruitForm.getPicUrl5());
            if (!recruitForm.getPicFile6().isEmpty()) {
                recruitForm.setPicUrl6(awsService.postFile(recruitForm.getPicFile6()));
            }
            logger.debug("----8----pic url6="+recruitForm.getPicUrl6());
            if (!recruitForm.getPicFile7().isEmpty()) {
                recruitForm.setPicUrl7(awsService.postFile(recruitForm.getPicFile7()));
            }
            logger.debug("----9----pic url7="+recruitForm.getPicUrl7());
            if (!recruitForm.getPicFile8().isEmpty()) {
                recruitForm.setPicUrl8(awsService.postFile(recruitForm.getPicFile8()));
            }
            logger.debug("----10----pic url8="+recruitForm.getPicUrl8());
            if (!recruitForm.getPicFile9().isEmpty()) {
                recruitForm.setPicUrl9(awsService.postFile(recruitForm.getPicFile9()));
            }
            logger.debug("----11----pic url9="+recruitForm.getPicUrl9());
            if (!recruitForm.getPicFile10().isEmpty()) {
                recruitForm.setPicUrl10(awsService.postFile(recruitForm.getPicFile10()));
            }
            logger.debug("----12----pic url10="+recruitForm.getPicUrl10());
        }catch (IOException e) {
            throw new APIException(JsonStatus.DATA_SAVE_FAILED);
        }
        logger.debug("----12----");

        // update database
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        HttpSession session = request.getSession();
        try {
            if(recruitForm.getShopUuid()!=null && recruitForm.getShopUuid()!="" ){
                Shop s = shopService.getShopByUuid(recruitForm.getShopUuid());
                if(s != null){
                    recruitForm.setShopId(s.getId());
                }else {
                    recruitForm.setShopId(0L);
                }
                AdminCustomer adminCustomer = adminService.getAdminCustomerByShopIdAndType(recruitForm.getRecruitId(),Constant.CUSTOMER.CUSTOMER_RECRUIT);
                if(adminCustomer != null && !adminCustomer.getFcAdminId().equals(s.getId())){
                    adminCustomer.setFcAdminId(s.getId());
                    adminCustomer.setFcType((byte)Constant.CUSTOMER.SHOP);
                    adminService.updateAdminCustomerByAdminId(adminCustomer);
                }
            }

            Recruit recruit = recruitService.updateRecruit(recruitForm);
            recruitService.updateRecruitDetail(recruitForm);
            Recruit recruit2 = recruitService.getRecruit(recruit.getId());

            if(recruit2 != null){
                RandomShopOne rso = new RandomShopOne();
                rso.setUuid(recruit2.getUuid());
                rso.setShopType((byte)Constant.SHOP_TAG.RECRUIT);
                rso.setArea((byte)0);
                rso.setNow4((byte)0);
                rso.setNow5((byte)0);
                rso.setPublishStart(recruit2.getPublishStart());
                rso.setPublishEnd(recruit2.getPublishEnd());
                rso.setCreateDatetime(recruit2.getCreateDatetime());
                rso.setUpdateDatetime(recruit2.getUpdateDatetime());
                rso.setDelFlg(recruit2.getDelFlg());
                rso.setNote(recruit2.getNote());
                randomShopService.updateRandomShopOne(rso);

                RandomShopTwo rst = new RandomShopTwo();
                rst.setUuid(recruit2.getUuid());
                rst.setShopType((byte)Constant.SHOP_TAG.RECRUIT);
                rst.setArea((byte)0);
                rst.setNow4((byte)0);
                rst.setNow5((byte)0);
                rst.setPublishStart(recruit2.getPublishStart());
                rst.setPublishEnd(recruit2.getPublishEnd());
                rst.setCreateDatetime(recruit2.getCreateDatetime());
                rst.setUpdateDatetime(recruit2.getUpdateDatetime());
                rst.setDelFlg(recruit2.getDelFlg());
                rst.setNote(recruit2.getNote());
                randomShopService.updateRandomShopTwo(rst);
            }

            Long idPicurl6 =0L;
            Long idPicurl7 =0L ;
            Long idPpicurl8 =0L ;
            Long idPpicurl9 =0L;
            Long idPpicurl10 =0L;

            List<Pic> listPic = picService.getShopUrlList(recruitForm.getRecruitId(),Constant.EVENT_PIC_TYPE.RECRUIT);
            if(listPic != null && listPic.size()>0){
                if(listPic.get(0).getPicUrl() != null && listPic.get(0).getPicUrl() !=""){
                    idPicurl6=listPic.get(0).getId();
                }
                if(listPic.size()>1 && listPic.get(1).getPicUrl() != null && listPic.get(1).getPicUrl() !=""){
                    idPicurl7=listPic.get(1).getId();
                }
                if(listPic.size()>2 && listPic.get(2).getPicUrl() != null && listPic.get(2).getPicUrl() !=""){
                    idPpicurl8=listPic.get(2).getId();
                }
                if(listPic.size()>3 && listPic.get(3).getPicUrl() != null && listPic.get(3).getPicUrl() !=""){
                    idPpicurl9=listPic.get(3).getId();
                }
                if(listPic.size()>4 && listPic.get(4).getPicUrl() != null && listPic.get(4).getPicUrl() !=""){
                    idPpicurl10=listPic.get(4).getId();
                }
            }

            if(recruitForm.getPicUrl10()!=null && !recruitForm.getPicUrl10().equals("")){
                recruitService.deleteDetailVideo(recruitForm.getRecruitId());
            }else{
                picService.updateRecruitPicUrl("",recruitForm.getRecruitId(),idPpicurl10);
            }

//            picService.deleteShopPicUrl(recruitForm.getRecruitId(),Constant.EVENT_PIC_TYPE.RECRUIT);
            if(recruitForm.getPicUrl6() != null ){
                picService.updateRecruitPicUrl(recruitForm.getPicUrl6(),recruitForm.getRecruitId(),idPicurl6);
            }
            if(recruitForm.getPicUrl7() != null ){
                picService.updateRecruitPicUrl(recruitForm.getPicUrl7(),recruitForm.getRecruitId(),idPicurl7);
            }
            if(recruitForm.getPicUrl8() != null ){
                picService.updateRecruitPicUrl(recruitForm.getPicUrl8(),recruitForm.getRecruitId(),idPpicurl8);
            }
            if(recruitForm.getPicUrl9() != null ){
                picService.updateRecruitPicUrl(recruitForm.getPicUrl9(),recruitForm.getRecruitId(),idPpicurl9);
            }
            if(recruitForm.getPicUrl10() != null ){
                picService.updateRecruitPicUrl(recruitForm.getPicUrl10(),recruitForm.getRecruitId(),idPpicurl10);
            }

            // Update Tags
            recruitService.deleteTags(recruitForm.getRecruitId(), Constant.RECRUIT_TAG_TYPE.MODE);
            recruitService.insertTags(recruitForm.getRecruitId(), Constant.RECRUIT_TAG_TYPE.MODE, recruitForm.getModeList());
//            recruitService.deleteTags(recruitForm.getRecruitId(), Constant.RECRUIT_TAG_TYPE.DETAIL);
//            recruitService.insertTags(recruitForm.getRecruitId(), Constant.RECRUIT_TAG_TYPE.DETAIL, recruitForm.getDetailList());
//            recruitService.deleteTags(recruitForm.getRecruitId(), Constant.RECRUIT_TAG_TYPE.FEATURE);
//            recruitService.insertTags(recruitForm.getRecruitId(), Constant.RECRUIT_TAG_TYPE.FEATURE, recruitForm.getFeatureList());
            recruitService.deleteTags(recruitForm.getRecruitId(), Constant.RECRUIT_TAG_TYPE.TREATMENT);
            recruitService.insertTags(recruitForm.getRecruitId(), Constant.RECRUIT_TAG_TYPE.TREATMENT, recruitForm.getTreatmentList());
            if(recruitForm.getWorkingTimeStart() != null && recruitForm.getWorkingTimeStart().length() > 0){
                recruitService.deleteTags(recruitForm.getRecruitId(), Constant.RECRUIT_TAG_TYPE.WORKING_TIME_START);
                recruitService.insertTag(recruitForm.getRecruitId(), Constant.RECRUIT_TAG_TYPE.WORKING_TIME_START, DateUtil.stringToUnixTime(recruitForm.getWorkingTimeStart()));
            }
            if(recruitForm.getWorkingTimeEnd() != null && recruitForm.getWorkingTimeEnd().length() > 0){
                recruitService.deleteTags(recruitForm.getRecruitId(), Constant.RECRUIT_TAG_TYPE.WORKING_TIME_END);
                recruitService.insertTag(recruitForm.getRecruitId(), Constant.RECRUIT_TAG_TYPE.WORKING_TIME_END, DateUtil.stringToUnixTime(recruitForm.getWorkingTimeEnd()));
            }
            if(recruitForm.getCarerr() > 0){
                recruitService.deleteTags(recruitForm.getRecruitId(), Constant.RECRUIT_TAG_TYPE.CAREER);
                recruitService.insertTag(recruitForm.getRecruitId(), Constant.RECRUIT_TAG_TYPE.CAREER, recruitForm.getCarerr());
            }
            if(recruitForm.getWorkPeriod().size() > 0){
                recruitService.deleteTags(recruitForm.getRecruitId(), Constant.RECRUIT_TAG_TYPE.WORK_PERIOD);
                recruitService.insertTags(recruitForm.getRecruitId(), Constant.RECRUIT_TAG_TYPE.WORK_PERIOD, recruitForm.getWorkPeriod());
            }
            if(recruitForm.getWorkTimeList().size() > 0){
                recruitService.deleteTags(recruitForm.getRecruitId(), Constant.RECRUIT_TAG_TYPE.WORK_TIME);
                recruitService.insertTags(recruitForm.getRecruitId(), Constant.RECRUIT_TAG_TYPE.WORK_TIME, recruitForm.getWorkTimeList());
            }
            if(recruitForm.getCapacityList().size() > 0){
                recruitService.deleteTags(recruitForm.getRecruitId(), Constant.RECRUIT_TAG_TYPE.CAPACITY);
                recruitService.insertTags(recruitForm.getRecruitId(), Constant.RECRUIT_TAG_TYPE.CAPACITY, recruitForm.getCapacityList());
            }
            if(recruitForm.getWorkWayList().size() > 0){
                recruitService.deleteTags(recruitForm.getRecruitId(), Constant.RECRUIT_TAG_TYPE.WORK_WAY);
                recruitService.insertTags(recruitForm.getRecruitId(), Constant.RECRUIT_TAG_TYPE.WORK_WAY, recruitForm.getWorkWayList());
            }
            if(recruitForm.getAllowanceSpecialList().size() > 0){
                recruitService.deleteTags(recruitForm.getRecruitId(), Constant.RECRUIT_TAG_TYPE.ALLOWANCE_SPECIAL);
                recruitService.insertTags(recruitForm.getRecruitId(), Constant.RECRUIT_TAG_TYPE.ALLOWANCE_SPECIAL, recruitForm.getAllowanceSpecialList());
            }
            if(recruitForm.getWorkEnvironmentList().size() > 0){
                recruitService.deleteTags(recruitForm.getRecruitId(), Constant.RECRUIT_TAG_TYPE.WORK_ENVIRONMENT);
                recruitService.insertTags(recruitForm.getRecruitId(), Constant.RECRUIT_TAG_TYPE.WORK_ENVIRONMENT, recruitForm.getWorkEnvironmentList());
            }
            if(recruitForm.getTreatmentList().size() > 0){
                recruitService.deleteTags(recruitForm.getRecruitId(), Constant.RECRUIT_TAG_TYPE.TREATMENT_NEW);
                recruitService.insertTags(recruitForm.getRecruitId(), Constant.RECRUIT_TAG_TYPE.TREATMENT_NEW, recruitForm.getTreatmentList());
            }
            if (recruitForm.getArea() > 0) {
                recruitService.deleteTags(recruitForm.getRecruitId(), Constant.RECRUIT_TAG_TYPE.AREA);
                recruitService.insertTag(recruitForm.getRecruitId(), Constant.RECRUIT_TAG_TYPE.AREA, recruitForm.getArea());
            }
            txManager.commit(txStatus);
            session.setAttribute("error",0);
        }catch (Exception ex){
            session.setAttribute("error",1);
            logger.debug("----9----exception message="+ex.getMessage());
            logger.error("Recruit update failed, error=" + ex.getMessage());
            logger.error("Recruit update feed, error=" + ex.toString());
            ex.printStackTrace();
            txManager.rollback(txStatus);
        }
        logger.debug("----10----");
        return "redirect:/admin/recruit/list/";
    }

    private Map<Integer, String> getMap(List<TagDetail> tagDetailList){
        Map<Integer,String> result = new HashMap<>();
        if(tagDetailList != null && tagDetailList.size() > 0) {
            for (TagDetail tagDetail : tagDetailList) {
                result.put(tagDetail.getContent(), tagDetail.getDesc());
            }
        }
        return result;
    }

}