package jp.co.vermore.controller.admin;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.DatatablesJsonObject;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.entity.EntryMail;
import jp.co.vermore.entity.Report;
import jp.co.vermore.entity.Pic;
import jp.co.vermore.form.admin.ReportForm;
import jp.co.vermore.form.admin.ReportListForm;
import jp.co.vermore.service.AWSService;
import jp.co.vermore.service.EntryService;
import jp.co.vermore.service.ReportService;
import jp.co.vermore.service.PicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * ReportAdminController
 * Created by wubin.
 * <p>
 * DateTime: 2018/03/03 11:13
 * Copyright: sLab, Corp
 */
@Controller
public class AdminReportController extends BaseController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private EntryService entryService;

    @Autowired
    private AWSService awsService;

    @Autowired
    private PicService picService;

    @Autowired
    PlatformTransactionManager txManager;

    @RequestMapping(value = "/admin/report/list/", method = RequestMethod.GET)
    public String reportAll(Model model,HttpServletRequest request) {

        int errorCode = 0;
        //TODO
        if(!request.getSession().isNew()){
            if(request.getSession().getAttribute("error") != null && request.getSession().getAttribute("error") != ""){
                errorCode = (int)request.getSession().getAttribute("error");
                request.getSession().setAttribute("error",0);
            }
        }
        model.addAttribute("errorCode", errorCode);

        List<Report> report = reportService.getReportAll();
        ReportForm form = new ReportForm();
        model.addAttribute("reportDeleteForm", form);
        model.addAttribute("report_all", report);
        return "admin/reportList";
    }

    @RequestMapping(value = "/admin/report/list/", method = RequestMethod.POST)
    @ResponseBody
    public DatatablesJsonObject reportList(@RequestBody ReportListForm form){
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
        List<Report> dataList = reportService.getReportAllByCondition(form);

        for(Report report:dataList){
            int type =0;
            //it's my faults
            if(report.getType() == Constant.REPORT_TYPE.EVENT){
                type =  Constant.REPORT_TYPE.MOVEUP;
            }else if(report.getType() == Constant.REPORT_TYPE.MOVEUP){
                type = Constant.REPORT_TYPE.EVENT;
            }
            EntryMail entity = entryService.getEntryMailByEntryIdAndType( report.getId(),type);
            if(entity != null){
                report.setEntryType(1);
            }else{
                report.setEntryType(0);
            }
        }

        int totalCountFiltered = reportService.getReportCountByCondition(form);
        int totalCount = reportService.getReportCount();
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

    @RequestMapping(value = "/admin/report/regist/", method = RequestMethod.GET)
    public String reportInsert(Model model) {
        ReportForm form = new ReportForm();
        model.addAttribute("reportForm", form);
        return "admin/reportRegist";
    }

    @RequestMapping(value = "/admin/report/regist/", method = RequestMethod.POST)
    public String reportInsert(@ModelAttribute ReportForm form ,HttpServletRequest request) {
        HttpSession session = request.getSession();
        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try {
            long reportId = reportService.insertReport(form);
            reportService.insertDetailReport(form,reportId);
            MultipartFile[] top = form.getPicFile1();
            MultipartFile[] foot = form.getPicFile2();

            if(!form.getPicFile1()[0].isEmpty()) {
                Pic topPic = new Pic();
                if (top.length>0) {
                    for(int i = 0 ; i < top.length; i++){
                        topPic.setPicUrl(awsService.postFile(top[i]));
                        topPic.setItemId(reportId);
                        topPic.setItemType(Constant.EVENT_PIC_TYPE.REPORT_TOP);
                        picService.insertPic(topPic);
                    }
                }
            }

            if(!form.getPicFile2()[0].isEmpty()) {
                Pic footPic = new Pic();
                if (foot.length>0) {
                    for(int i = 0 ; i < foot.length; i++){
                        footPic.setPicUrl(awsService.postFile(foot[i]));
                        footPic.setItemId(reportId);
                        footPic.setItemType(Constant.EVENT_PIC_TYPE.REPORT_FOOT);
                        picService.insertPic(footPic);
                    }
                }
            }

            txManager.commit(txStatus);
            session.setAttribute("error",0);
        } catch (Exception e) {
            txManager.rollback(txStatus);
            session.setAttribute("error",1);
            logger.error("insert report failed!, error=" + e.getMessage());
            logger.error("insert report failed!, error=" + e.toString());
            e.printStackTrace();
        }
        return "redirect:/admin/report/list/";
    }

    @RequestMapping(value = "/admin/report/delete/", method = RequestMethod.POST)
    public String reportDetailDelete(@ModelAttribute ReportForm form,HttpServletRequest request) {
        HttpSession session = request.getSession();
        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try {
            reportService.deleteReport(form);
            reportService.deleteDetailReport(form);
            picService.deleteReportPicUrl(form.getId(),Constant.EVENT_PIC_TYPE.REPORT_TOP);
            picService.deleteReportPicUrl(form.getId(),Constant.EVENT_PIC_TYPE.REPORT_FOOT);
            txManager.commit(txStatus);
            session.setAttribute("error",0);
        } catch (Exception e) {
            txManager.rollback(txStatus);
            session.setAttribute("error",1);
            logger.error("delete report failed!, error=" + e.getMessage());
            logger.error("delete report failed!, error=" + e.toString());
            e.printStackTrace();
        }
        return "redirect:/admin/report/list/";
    }

    @RequestMapping(value = "/admin/report/edit/{id}/", method = RequestMethod.GET)
    public String reportUpdate(Model model , @PathVariable long id) {
        ReportForm reportForm = new ReportForm();
        List<Report> list = reportService.getReportList(id);
        String detail = reportService.getReportDetail(id);

        List<Pic> topPicList = picService.getPic(id,Constant.EVENT_PIC_TYPE.REPORT_TOP);
        List<String> topList = new ArrayList<String>();
        for(Pic pic:topPicList){
            topList.add(pic.getPicUrl());
        }

        List<Pic> footPicList = picService.getPic(id,Constant.EVENT_PIC_TYPE.REPORT_FOOT);
        List<String> footList = new ArrayList<String>();
        for(Pic pic:footPicList){
            footList.add(pic.getPicUrl());
        }

        reportForm.setPicUrl1(topList);
        reportForm.setPicUrl2(footList);

        if(list != null && list.size() > 0){
            reportForm.setId(list.get(0).getId());
            reportForm.setDetail(detail);
            reportForm.setTitle(list.get(0).getTitle());
            reportForm.setType(list.get(0).getType());
            reportForm.setExcerpt(list.get(0).getExcerpt());
            reportForm.setPublishStart(DateUtil.dateToStringyyyy_MM_dd_HH_mm(list.get(0).getPublishStart()));
            reportForm.setPublishEnd(DateUtil.dateToStringyyyy_MM_dd_HH_mm(list.get(0).getPublishEnd()));
            String date = DateUtil.dateToStringyyyy_MM_dd_HH_mm(list.get(0).getDate());
//            reportForm.setDate(date.replace(" ", "T"));
            reportForm.setDate(date);
            reportForm.setSortScore(list.get(0).getSortScore());

            model.addAttribute("reportForm", reportForm);
            return "admin/reportEdit";
        }else {
            return "redirect:/admin/report/list/";
        }
    }

    @RequestMapping(value = "/admin/report/update/", method = RequestMethod.POST)
    public String reportUpdate1(@ModelAttribute ReportForm form,HttpServletRequest request) {
        HttpSession session = request.getSession();
        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try {
            List<String> picUrl1 = form.getPicUrl1();
            reportService.updateReport(form);
            reportService.updateDetailReport(form);

            if(form.getPicUrl1().size()==0 && form.getPicFile1()[0].isEmpty()){
                picService.deleteReportPicUrl(form.getId(),Constant.EVENT_PIC_TYPE.REPORT_TOP);
            }

            if(form.getPicUrl2().size()==0 && form.getPicFile2()[0].isEmpty()){
                picService.deleteReportPicUrl(form.getId(),Constant.EVENT_PIC_TYPE.REPORT_FOOT);
            }

            if(!form.getPicFile1()[0].isEmpty()) {
                picService.deleteReportPicUrl(form.getId(),Constant.EVENT_PIC_TYPE.REPORT_TOP);
                MultipartFile[] top = form.getPicFile1();
                Pic topPic = new Pic();
                if (top.length>0) {
                    for(int i = 0 ; i < top.length; i++){
                        topPic.setPicUrl(awsService.postFile(top[i]));
                        topPic.setItemId(form.getId());
                        topPic.setItemType(Constant.EVENT_PIC_TYPE.REPORT_TOP);
                        picService.insertPic(topPic);
                    }
                }
            }

            if(!form.getPicFile2()[0].isEmpty()){
                picService.deleteReportPicUrl(form.getId(),Constant.EVENT_PIC_TYPE.REPORT_FOOT);
                MultipartFile[] foot = form.getPicFile2();
                Pic footPic = new Pic();
                if (foot.length>0) {
                    for(int i = 0 ; i < foot.length; i++){
                        footPic.setPicUrl(awsService.postFile(foot[i]));
                        footPic.setItemId(form.getId());
                        footPic.setItemType(Constant.EVENT_PIC_TYPE.REPORT_FOOT);
                        picService.insertPic(footPic);
                    }
                }
            }

            txManager.commit(txStatus);
            session.setAttribute("error",0);
        } catch (Exception e) {
            txManager.rollback(txStatus);
            session.setAttribute("error",1);
            logger.error("update report failed!, error=" + e.getMessage());
            logger.error("update report failed!, error=" + e.toString());
            e.printStackTrace();
        }
        return "redirect:/admin/report/list/";
    }
}
