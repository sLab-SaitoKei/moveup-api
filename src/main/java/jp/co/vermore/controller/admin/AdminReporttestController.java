package jp.co.vermore.controller.admin;

import java.util.ArrayList;
import java.util.List;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.DatatablesJsonObject;
import jp.co.vermore.entity.EntryMail;
import jp.co.vermore.entity.ReporttestDetail;
import jp.co.vermore.entity.Pic;
import jp.co.vermore.form.admin.ReporttestListForm;
import jp.co.vermore.service.AWSService;
import jp.co.vermore.service.EntryService;
import jp.co.vermore.service.PicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jp.co.vermore.entity.Reporttest;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.form.admin.ReporttestForm;
import jp.co.vermore.service.ReporttestService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * ReporttestAdminController
 * Created by wubin.
 * <p>
 * DateTime: 2018/03/03 11:13
 * Copyright: sLab, Corp
 */
@Controller
public class AdminReporttestController extends BaseController {

    @Autowired
    private ReporttestService reporttestService;

    @Autowired
    private EntryService entryService;

    @Autowired
    private AWSService awsService;

    @Autowired
    private PicService picService;

    @Autowired
    PlatformTransactionManager txManager;

    @RequestMapping(value = "/admin/reporttest/list/", method = RequestMethod.GET)
    public String reporttestAll(Model model,HttpServletRequest request) {

        int errorCode = 0;
        if(!request.getSession().isNew()){
            if(request.getSession().getAttribute("error") != null && request.getSession().getAttribute("error") != ""){
                errorCode = (int)request.getSession().getAttribute("error");
                request.getSession().setAttribute("error",0);
            }
        }
        model.addAttribute("errorCode", errorCode);

        List<Reporttest> reporttest = reporttestService.getReporttestAll();
        ReporttestForm form = new ReporttestForm();
        model.addAttribute("reporttestDeleteForm", form);
        model.addAttribute("reporttest_all", reporttest);
        return "admin/reporttestList";
    }

    @RequestMapping(value = "/admin/reporttest/list/", method = RequestMethod.POST)
    @ResponseBody
    public DatatablesJsonObject reporttestList(@RequestBody ReporttestListForm form){
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
        List<Reporttest> dataList = reporttestService.getReporttestAllByCondition(form);

        for(Reporttest reporttest:dataList){
            int type =0;
            //it's my faults
            if(reporttest.getType() == Constant.NEWS_TYPE.EVENT){
                type =  Constant.NEWS_TYPE.MOVEUP;
            }else if(reporttest.getType() == Constant.NEWS_TYPE.MOVEUP){
                type = Constant.NEWS_TYPE.EVENT;
            }
            EntryMail entity = entryService.getEntryMailByEntryIdAndType( reporttest.getId(),type);
            if(entity != null){
                reporttest.setEntryType(1);
            }else{
                reporttest.setEntryType(0);
            }
        }

        int totalCountFiltered = reporttestService.getReporttestCountByCondition(form);
        int totalCount = reporttestService.getReporttestCount();
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

    @RequestMapping(value = "/admin/reporttest/regist/", method = RequestMethod.GET)
    public String reporttestInsert(Model model) {
        ReporttestForm form = new ReporttestForm();
        model.addAttribute("reporttestForm", form);
        return "admin/reporttestRegist";
    }

    @RequestMapping(value = "/admin/reporttest/regist/", method = RequestMethod.POST)
    public String reporttestInsert(@ModelAttribute ReporttestForm form ,HttpServletRequest request) {
        HttpSession session = request.getSession();
        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try {
            long reporttestId = reporttestService.insertReporttest(form);
            reporttestService.insertDetailReporttest(form,reporttestId);
            MultipartFile[] top = form.getPicFile1();
            MultipartFile[] foot = form.getPicFile2();

            if(!form.getPicFile1()[0].isEmpty()) {
                Pic topPic = new Pic();
                if (top.length>0) {
                    for(int i = 0 ; i < top.length; i++){
                        topPic.setPicUrl(awsService.postFile(top[i]));
                        topPic.setItemId(reporttestId);
                        topPic.setItemType(Constant.EVENT_PIC_TYPE.NEWS_TOP);
                        picService.insertPic(topPic);
                    }
                }
            }

            if(!form.getPicFile2()[0].isEmpty()) {
                Pic footPic = new Pic();
                if (foot.length>0) {
                    for(int i = 0 ; i < foot.length; i++){
                        footPic.setPicUrl(awsService.postFile(foot[i]));
                        footPic.setItemId(reporttestId);
                        footPic.setItemType(Constant.EVENT_PIC_TYPE.NEWS_FOOT);
                        picService.insertPic(footPic);
                    }
                }
            }

            txManager.commit(txStatus);
            session.setAttribute("error",0);
        } catch (Exception e) {
            txManager.rollback(txStatus);
            session.setAttribute("error",1);
            logger.error("insert reporttest failed!, error=" + e.getMessage());
            logger.error("insert reporttest failed!, error=" + e.toString());
            e.printStackTrace();
        }
        return "redirect:/admin/reporttest/list/";
    }

    @RequestMapping(value = "/admin/reporttest/delete/", method = RequestMethod.POST)
    public String reporttestDetailDelete(@ModelAttribute ReporttestForm form,HttpServletRequest request) {
        HttpSession session = request.getSession();
        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try {
            reporttestService.deleteReporttest(form);
            reporttestService.deleteDetailReporttest(form);
            picService.deleteReporttestPicUrl(form.getId(),Constant.EVENT_PIC_TYPE.NEWS_TOP);
            picService.deleteReporttestPicUrl(form.getId(),Constant.EVENT_PIC_TYPE.NEWS_FOOT);
            txManager.commit(txStatus);
            session.setAttribute("error",0);
        } catch (Exception e) {
            txManager.rollback(txStatus);
            session.setAttribute("error",1);
            logger.error("delete reporttest failed!, error=" + e.getMessage());
            logger.error("delete reporttest failed!, error=" + e.toString());
            e.printStackTrace();
        }
        return "redirect:/admin/reporttest/list/";
    }

    @RequestMapping(value = "/admin/reporttest/edit/{id}/", method = RequestMethod.GET)
    public String reporttestUpdate(Model model , @PathVariable long id) {
        ReporttestForm reporttestForm = new ReporttestForm();
        List<Reporttest> list = reporttestService.getReporttestList(id);
        String detail = reporttestService.getReporttestDetail(id);

        List<Pic> topPicList = picService.getPic(id,Constant.EVENT_PIC_TYPE.NEWS_TOP);
        List<String> topList = new ArrayList<String>();
        for(Pic pic:topPicList){
            topList.add(pic.getPicUrl());
        }

        List<Pic> footPicList = picService.getPic(id,Constant.EVENT_PIC_TYPE.NEWS_FOOT);
        List<String> footList = new ArrayList<String>();
        for(Pic pic:footPicList){
            footList.add(pic.getPicUrl());
        }

        reporttestForm.setPicUrl1(topList);
        reporttestForm.setPicUrl2(footList);

        if(list != null && list.size() > 0){
            reporttestForm.setId(list.get(0).getId());
            reporttestForm.setDetail(detail);
            reporttestForm.setTitle(list.get(0).getTitle());
            reporttestForm.setType(list.get(0).getType());
            reporttestForm.setExcerpt(list.get(0).getExcerpt());
            reporttestForm.setPublishStart(DateUtil.dateToStringyyyy_MM_dd_HH_mm(list.get(0).getPublishStart()));
            reporttestForm.setPublishEnd(DateUtil.dateToStringyyyy_MM_dd_HH_mm(list.get(0).getPublishEnd()));
            String date = DateUtil.dateToStringyyyy_MM_dd_HH_mm(list.get(0).getDate());
//            reporttestForm.setDate(date.replace(" ", "T"));
            reporttestForm.setDate(date);
            reporttestForm.setSortScore(list.get(0).getSortScore());

            model.addAttribute("reporttestForm", reporttestForm);
            return "admin/reporttestEdit";
        }else {
            return "redirect:/admin/reporttest/list/";
        }
    }

    @RequestMapping(value = "/admin/reporttest/update/", method = RequestMethod.POST)
    public String reporttestUpdate1(@ModelAttribute ReporttestForm form,HttpServletRequest request) {
        HttpSession session = request.getSession();
        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try {
            List<String> picUrl1 = form.getPicUrl1();
            reporttestService.updateReporttest(form);
            reporttestService.updateDetailReporttest(form);

            if(form.getPicUrl1().size()==0 && form.getPicFile1()[0].isEmpty()){
                picService.deleteReporttestPicUrl(form.getId(),Constant.EVENT_PIC_TYPE.NEWS_TOP);
            }

            if(form.getPicUrl2().size()==0 && form.getPicFile2()[0].isEmpty()){
                picService.deleteReporttestPicUrl(form.getId(),Constant.EVENT_PIC_TYPE.NEWS_FOOT);
            }

            if(!form.getPicFile1()[0].isEmpty()) {
                picService.deleteReporttestPicUrl(form.getId(),Constant.EVENT_PIC_TYPE.NEWS_TOP);
                MultipartFile[] top = form.getPicFile1();
                Pic topPic = new Pic();
                if (top.length>0) {
                    for(int i = 0 ; i < top.length; i++){
                        topPic.setPicUrl(awsService.postFile(top[i]));
                        topPic.setItemId(form.getId());
                        topPic.setItemType(Constant.EVENT_PIC_TYPE.NEWS_TOP);
                        picService.insertPic(topPic);
                    }
                }
            }

            if(!form.getPicFile2()[0].isEmpty()){
                picService.deleteReporttestPicUrl(form.getId(),Constant.EVENT_PIC_TYPE.NEWS_FOOT);
                MultipartFile[] foot = form.getPicFile2();
                Pic footPic = new Pic();
                if (foot.length>0) {
                    for(int i = 0 ; i < foot.length; i++){
                        footPic.setPicUrl(awsService.postFile(foot[i]));
                        footPic.setItemId(form.getId());
                        footPic.setItemType(Constant.EVENT_PIC_TYPE.NEWS_FOOT);
                        picService.insertPic(footPic);
                    }
                }
            }

            txManager.commit(txStatus);
            session.setAttribute("error",0);
        } catch (Exception e) {
            txManager.rollback(txStatus);
            session.setAttribute("error",1);
            logger.error("update reporttest failed!, error=" + e.getMessage());
            logger.error("update reporttest failed!, error=" + e.toString());
            e.printStackTrace();
        }
        return "redirect:/admin/reporttest/list/";
    }
}
