package jp.co.vermore.controller.admin;


import jp.co.vermore.common.DatatablesJsonObject;
import jp.co.vermore.common.mvc.APIException;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.entity.*;
import jp.co.vermore.form.admin.*;
import jp.co.vermore.service.AWSService;
import jp.co.vermore.service.PicService;
import jp.co.vermore.service.Team2020Service;
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
 * Team2020AdminController
 * Created by wangnannan.
 * <p>
 * DateTime: 2018/05/21 14:58
 * Copyright: sLab, Corp
 */
@Controller
public class AdminTeam2020Controller extends BaseController {

    @Autowired
    private Team2020Service team2020Service;

    @Autowired
    private PicService picService;

    @Autowired
    private AWSService awsService;

    @Autowired
    PlatformTransactionManager txManager;

    @RequestMapping(value = "/admin/team2020/list/", method = RequestMethod.GET)
    public String getTeam2020List(Model model,HttpServletRequest request) {
        int errorCode = 0;
        if(!request.getSession().isNew()){
            if(request.getSession().getAttribute("error") != null && request.getSession().getAttribute("error") != ""){
                errorCode = (int)request.getSession().getAttribute("error");
                request.getSession().setAttribute("error",0);
            }
        }
        model.addAttribute("errorCode", errorCode);
        return "admin/team2020";
    }

    @RequestMapping(value = "/admin/team2020/list/", method = RequestMethod.POST)
    @ResponseBody
    public DatatablesJsonObject getTeam2020List(@RequestBody Team2020ListForm form) {
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
        List<Team2020> dataList = team2020Service.getTeam2020AllByCondition(form);

        int totalCountFiltered = team2020Service.getTeam2020CountByCondition(form);
        int totalCount = team2020Service.getTeamCount();
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

    @RequestMapping(value = "/admin/team2020/regist/", method = RequestMethod.GET)
    public String team2020Insert(Model model) {
        Team2020Form form = new Team2020Form();
        model.addAttribute("form", form);
        return "admin/team2020Regist";
    }

    @RequestMapping(value = "/admin/team2020/regist/", method = RequestMethod.POST)
    public String team2020Regist(@ModelAttribute Team2020Form form, Model model,HttpServletRequest request) {
        HttpSession session = request.getSession();
        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);
        try {
            MultipartFile[] arr = form.getPicFil();

            Long id = team2020Service.insertTeam2020(form);
            if (arr.length > 0) {
                Pic pic = new Pic();
                for (int i = 0; i < arr.length; i++) {
                    pic.setPicUrl(awsService.postFile(arr[i]));
                    picService.insertPicUrl(pic, id);
                }
            }
            txManager.commit(txStatus);
            session.setAttribute("error",0);
        } catch (Exception e) {
            txManager.rollback(txStatus);
            session.setAttribute("error",1);
            logger.error("Insert Team2020 failed!, error=" + e.getMessage());
            logger.error("Insert Team2020 failed!, error=" + e.toString());
        }
        return "redirect:/admin/team2020/list/";
    }

    @RequestMapping(value = "/admin/team2020/delete/", method = RequestMethod.POST)
    public String team2020DetailDelete(@ModelAttribute Team2020Form form,HttpServletRequest request) {
        HttpSession session = request.getSession();
        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try {
            team2020Service.deleteTeam2020(form);
            team2020Service.deleteTeam2020Url(form);
            txManager.commit(txStatus);
            session.setAttribute("error",0);
        } catch (Exception e) {
            txManager.rollback(txStatus);
            session.setAttribute("error",1);
            logger.error("delete Team2020 failed!, error=" + e.getMessage());
            logger.error("delete Team2020 failed!, error=" + e.toString());
            e.printStackTrace();
        }
        return "redirect:/admin/team2020/list/";
    }

    @RequestMapping(value = "/admin/team2020/edit/{id}/", method = RequestMethod.GET)
    public String team2020(@PathVariable Long id, Model model) {
        Team2020 team2020 = team2020Service.getTeam2020ById(id);
        List<Pic> pic = picService.getTeam2020Url(id);
        Team2020Form form = new Team2020Form();
        List<String> urlList = new ArrayList<String>();
        for (Pic fd : pic) {
            urlList.add(fd.getPicUrl());
        }
        form.setId(id);
        form.setUrlList(urlList);
        form.setName(team2020.getName());
        form.setPseudonym(team2020.getPseudonym());
        form.setUuid(team2020.getUuid());
        form.setCategory(team2020.getCategory());
        model.addAttribute("form", form);
        return "admin/team2020Edit";
    }

    @RequestMapping(value = "/admin/team2020/update/", method = RequestMethod.POST)
    public String freePaperUpdate(@ModelAttribute Team2020Form form,HttpServletRequest request) throws APIException {
        HttpSession session = request.getSession();
        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try {
            team2020Service.updateTeam2020(form);
            txManager.commit(txStatus);
            session.setAttribute("error",0);
        } catch (Exception e) {
            txManager.rollback(txStatus);
            session.setAttribute("error",1);
            logger.error("Update Team2020 failed!, error=" + e.getMessage());
            logger.error("Update Team2020 failed!, error=" + e.toString());
        }
        return "redirect:/admin/team2020/list/";
    }
}
