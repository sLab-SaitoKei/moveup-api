package jp.co.vermore.controller.admin;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.DatatablesJsonObject;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.entity.Inquire;
import jp.co.vermore.form.admin.InquireForm;
import jp.co.vermore.form.admin.InquireListForm;
import jp.co.vermore.service.InquireService;
import jp.co.vermore.service.WidgetService;

import org.springframework.beans.BeanUtils;
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
import java.util.List;

/**
 * AdminContactController
 * Created by wubin.
 *
 * DateTime: 2018/04/23 15:50
 * Copyright: sLab, Corp
 */
@Controller
public class AdminInquireController extends BaseController {

    @Autowired
    InquireService inquireService;

    @Autowired
    WidgetService widgetService;

    @Autowired
    PlatformTransactionManager txManager;

    @RequestMapping(value = "/admin/inquire/list/", method = RequestMethod.GET)
    public String inquire(Model model,HttpServletRequest request) {
        int errorCode = 0;
        if(!request.getSession().isNew()){
            if(request.getSession().getAttribute("error") != null && request.getSession().getAttribute("error") != ""){
                errorCode = (int)request.getSession().getAttribute("error");
                request.getSession().setAttribute("error",0);
            }
        }
        model.addAttribute("errorCode", errorCode);

        InquireForm form = new InquireForm();
        model.addAttribute("form", form);
        return "admin/inquireList";
    }

    @RequestMapping(value = "/admin/inquire/list/", method = RequestMethod.POST)
    @ResponseBody
    public DatatablesJsonObject inquireList(@RequestBody InquireListForm form) {

        // set order statement
        if (form.getOrder().size() > 0
                && form.getColumns().get(form.getOrder().get(0).getColumn()).getName() != null
                && form.getColumns().get(form.getOrder().get(0).getColumn()).getName().length() > 0) {
            form.setOrderStatement(form.getColumns().get(form.getOrder().get(0).getColumn()).getName() + " " + form.getOrder().get(0).getDir());
            logger.debug("----2----order statement=" + form.getOrderStatement());
        } else {
            form.setOrderStatement("inquire_id");
            logger.debug("----2----order statement=" + form.getOrderStatement());
        }
        logger.debug("----3----");

        // query data
        List<Inquire> dataList = inquireService.getInquireAllByCondition(form);
        int totalCountFiltered = inquireService.getInquireCountByCondition(form);
        int totalCount = inquireService.getInquireCount();
        logger.debug("----4----data count=" + dataList.size());
        logger.debug("----5----total filtered=" + totalCountFiltered);
        logger.debug("----6----total count=" + totalCount);
        logger.debug("----7----page=" + form.getDraw());

        for(Inquire in:dataList){
            String date = DateUtil.dateToStringyyyy_MM_dd(in.getCreateDatetime());
            String type = widgetService.getInquireType(in.getType());
            if(in.getContents().length()>30){
                String content = (in.getContents()).substring(0,30)+"...";
                in.setContent(content);
            }else {
                in.setContent(in.getContents());
            }
            in.setDate(date);
            in.setTypeStr(type);
        }
        // return json data
        DatatablesJsonObject jsonparse = new DatatablesJsonObject();
        jsonparse.setDraw(form.getDraw());
        jsonparse.setRecordsFiltered(totalCountFiltered);
        jsonparse.setRecordsTotal(totalCount);
        jsonparse.setData(dataList);
        return jsonparse;
    }

    @RequestMapping(value = "/admin/inquire/detail/{id}/", method = RequestMethod.GET)
    public String inquireDetail(Model model, @PathVariable Long id) {

        InquireForm form = new InquireForm();
        Inquire inquire = inquireService.selectById(id);

        if (inquire != null) {
            String contengNew = inquire.getContents().replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
            BeanUtils.copyProperties(inquire, form);
            form.setContents(contengNew);
            form.setDate(DateUtil.dateToStringyyyy_MM_dd(inquire.getCreateDatetime()));
            form.setTypeStr(widgetService.getInquireType(inquire.getType()));
            model.addAttribute("form", form);
            return "admin/inquireDetail";
        } else {
            return "redirect:/admin/inquire/list/";
        }
    }

    @RequestMapping(value = "/admin/inquire/update/", method = RequestMethod.POST)
    public String newsUpdate1(@ModelAttribute InquireForm form,HttpServletRequest request) {
        HttpSession session = request.getSession();
        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try {
            inquireService.updateInquire(form);

            txManager.commit(txStatus);
            session.setAttribute("error",0);
        } catch (Exception e) {
            txManager.rollback(txStatus);
            session.setAttribute("error",1);
            logger.error("update inquire failed!, error=" + e.getMessage());
            logger.error("update inquire failed!, error=" + e.toString());
            e.printStackTrace();
        }
        return "redirect:/admin/inquire/list/";
    }

    @RequestMapping(value = "/admin/inquire/delete/", method = RequestMethod.POST)
    public String placeDelete(@ModelAttribute InquireForm form) {

        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try {
            int countPlace = inquireService.deleteInquire(form);
            txManager.commit(txStatus);
        } catch (Exception ex) {
            logger.error("Delete inquire failed!, error=" + ex.getMessage());
            logger.error("Delete inquire failed!, error=" + ex.toString());
            ex.printStackTrace();
            txManager.rollback(txStatus);
        }
        return "redirect:/admin/inquire/list/";
    }
}
