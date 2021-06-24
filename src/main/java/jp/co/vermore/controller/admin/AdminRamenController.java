package jp.co.vermore.controller.admin;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.DatatablesJsonObject;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.entity.EntryMail;
import jp.co.vermore.entity.Pic;
import jp.co.vermore.entity.Ramen;
import jp.co.vermore.form.admin.RamenForm;
import jp.co.vermore.form.admin.RamenListForm;
import jp.co.vermore.service.AWSService;
import jp.co.vermore.service.EntryService;
import jp.co.vermore.service.PicService;
import jp.co.vermore.service.RamenService;
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
 * RamenAdminController
 * Created by wubin.
 * <p>
 * DateTime: 2018/03/03 11:13
 * Copyright: sLab, Corp
 */
@Controller
public class AdminRamenController extends BaseController {

    @Autowired
    private RamenService ramenService;

    @Autowired
    private EntryService entryService;

    @Autowired
    private AWSService awsService;

    @Autowired
    private PicService picService;

    @Autowired
    PlatformTransactionManager txManager;

    @RequestMapping(value = "/admin/ramen/list/", method = RequestMethod.GET)
    public String ramenAll(Model model,HttpServletRequest request) {

        int errorCode = 0;
        //TODO
        if(!request.getSession().isNew()){
            if(request.getSession().getAttribute("error") != null && request.getSession().getAttribute("error") != ""){
                errorCode = (int)request.getSession().getAttribute("error");
                request.getSession().setAttribute("error",0);
            }
        }
        model.addAttribute("errorCode", errorCode);

        List<Ramen> ramen = ramenService.getRamenAll();
        RamenForm form = new RamenForm();
        model.addAttribute("ramenDeleteForm", form);
        model.addAttribute("ramen_all", ramen);
        return "admin/ramenList";
    }

    @RequestMapping(value = "/admin/ramen/list/", method = RequestMethod.POST)
    @ResponseBody
    public DatatablesJsonObject ramenList(@RequestBody RamenListForm form){
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
        List<Ramen> dataList = ramenService.getRamenAllByCondition(form);

        for(Ramen ramen:dataList){
            int type =0;
            //it's my faults
            if(ramen.getType() == Constant.RAMEN_TYPE.EVENT){
                type =  Constant.RAMEN_TYPE.MOVEUP;
            }else if(ramen.getType() == Constant.RAMEN_TYPE.MOVEUP){
                type = Constant.RAMEN_TYPE.EVENT;
            }
            EntryMail entity = entryService.getEntryMailByEntryIdAndType( ramen.getId(),type);
            if(entity != null){
                ramen.setEntryType(1);
            }else{
                ramen.setEntryType(0);
            }
        }

        int totalCountFiltered = ramenService.getRamenCountByCondition(form);
        int totalCount = ramenService.getRamenCount();
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

    @RequestMapping(value = "/admin/ramen/regist/", method = RequestMethod.GET)
    public String ramenInsert(Model model) {
        RamenForm form = new RamenForm();
        model.addAttribute("ramenForm", form);
        return "admin/ramenRegist";
    }

    @RequestMapping(value = "/admin/ramen/regist/", method = RequestMethod.POST)
    public String ramenInsert(@ModelAttribute RamenForm form ,HttpServletRequest request) {
        HttpSession session = request.getSession();
        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try {
            long ramenId = ramenService.insertRamen(form);
            ramenService.insertDetailRamen(form,ramenId);
            MultipartFile[] top = form.getPicFile1();
            MultipartFile[] foot = form.getPicFile2();

            if(!form.getPicFile1()[0].isEmpty()) {
                Pic topPic = new Pic();
                if (top.length>0) {
                    for(int i = 0 ; i < top.length; i++){
                        topPic.setPicUrl(awsService.postFile(top[i]));
                        topPic.setItemId(ramenId);
                        topPic.setItemType(Constant.EVENT_PIC_TYPE.RAMEN_TOP);
                        picService.insertPic(topPic);
                    }
                }
            }

            if(!form.getPicFile2()[0].isEmpty()) {
                Pic footPic = new Pic();
                if (foot.length>0) {
                    for(int i = 0 ; i < foot.length; i++){
                        footPic.setPicUrl(awsService.postFile(foot[i]));
                        footPic.setItemId(ramenId);
                        footPic.setItemType(Constant.EVENT_PIC_TYPE.RAMEN_FOOT);
                        picService.insertPic(footPic);
                    }
                }
            }

            txManager.commit(txStatus);
            session.setAttribute("error",0);
        } catch (Exception e) {
            txManager.rollback(txStatus);
            session.setAttribute("error",1);
            logger.error("insert ramen failed!, error=" + e.getMessage());
            logger.error("insert ramen failed!, error=" + e.toString());
            e.printStackTrace();
        }
        return "redirect:/admin/ramen/list/";
    }

    @RequestMapping(value = "/admin/ramen/delete/", method = RequestMethod.POST)
    public String ramenDetailDelete(@ModelAttribute RamenForm form,HttpServletRequest request) {
        HttpSession session = request.getSession();
        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try {
            ramenService.deleteRamen(form);
            ramenService.deleteDetailRamen(form);
            picService.deleteRamenPicUrl(form.getId(),Constant.EVENT_PIC_TYPE.RAMEN_TOP);
            picService.deleteRamenPicUrl(form.getId(),Constant.EVENT_PIC_TYPE.RAMEN_FOOT);
            txManager.commit(txStatus);
            session.setAttribute("error",0);
        } catch (Exception e) {
            txManager.rollback(txStatus);
            session.setAttribute("error",1);
            logger.error("delete ramen failed!, error=" + e.getMessage());
            logger.error("delete ramen failed!, error=" + e.toString());
            e.printStackTrace();
        }
        return "redirect:/admin/ramen/list/";
    }

    @RequestMapping(value = "/admin/ramen/edit/{id}/", method = RequestMethod.GET)
    public String ramenUpdate(Model model , @PathVariable long id) {
        RamenForm ramenForm = new RamenForm();
        List<Ramen> list = ramenService.getRamenList(id);
        String detail = ramenService.getRamenDetail(id);

        List<Pic> topPicList = picService.getPic(id,Constant.EVENT_PIC_TYPE.RAMEN_TOP);
        List<String> topList = new ArrayList<String>();
        for(Pic pic:topPicList){
            topList.add(pic.getPicUrl());
        }

        List<Pic> footPicList = picService.getPic(id,Constant.EVENT_PIC_TYPE.RAMEN_FOOT);
        List<String> footList = new ArrayList<String>();
        for(Pic pic:footPicList){
            footList.add(pic.getPicUrl());
        }

        ramenForm.setPicUrl1(topList);
        ramenForm.setPicUrl2(footList);

        if(list != null && list.size() > 0){
            ramenForm.setId(list.get(0).getId());
            ramenForm.setDetail(detail);
            ramenForm.setTitle(list.get(0).getTitle());
            ramenForm.setType(list.get(0).getType());
            ramenForm.setExcerpt(list.get(0).getExcerpt());
            ramenForm.setPublishStart(DateUtil.dateToStringyyyy_MM_dd_HH_mm(list.get(0).getPublishStart()));
            ramenForm.setPublishEnd(DateUtil.dateToStringyyyy_MM_dd_HH_mm(list.get(0).getPublishEnd()));
            String date = DateUtil.dateToStringyyyy_MM_dd_HH_mm(list.get(0).getDate());
//            ramenForm.setDate(date.replace(" ", "T"));
            ramenForm.setDate(date);
            ramenForm.setSortScore(list.get(0).getSortScore());

            model.addAttribute("ramenForm", ramenForm);
            return "admin/ramenEdit";
        }else {
            return "redirect:/admin/ramen/list/";
        }
    }

    @RequestMapping(value = "/admin/ramen/update/", method = RequestMethod.POST)
    public String ramenUpdate1(@ModelAttribute RamenForm form,HttpServletRequest request) {
        HttpSession session = request.getSession();
        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try {
            List<String> picUrl1 = form.getPicUrl1();
            ramenService.updateRamen(form);
            ramenService.updateDetailRamen(form);

            if(form.getPicUrl1().size()==0 && form.getPicFile1()[0].isEmpty()){
                picService.deleteRamenPicUrl(form.getId(),Constant.EVENT_PIC_TYPE.RAMEN_TOP);
            }

            if(form.getPicUrl2().size()==0 && form.getPicFile2()[0].isEmpty()){
                picService.deleteRamenPicUrl(form.getId(),Constant.EVENT_PIC_TYPE.RAMEN_FOOT);
            }

            if(!form.getPicFile1()[0].isEmpty()) {
                picService.deleteRamenPicUrl(form.getId(),Constant.EVENT_PIC_TYPE.RAMEN_TOP);
                MultipartFile[] top = form.getPicFile1();
                Pic topPic = new Pic();
                if (top.length>0) {
                    for(int i = 0 ; i < top.length; i++){
                        topPic.setPicUrl(awsService.postFile(top[i]));
                        topPic.setItemId(form.getId());
                        topPic.setItemType(Constant.EVENT_PIC_TYPE.RAMEN_TOP);
                        picService.insertPic(topPic);
                    }
                }
            }

            if(!form.getPicFile2()[0].isEmpty()){
                picService.deleteRamenPicUrl(form.getId(),Constant.EVENT_PIC_TYPE.RAMEN_FOOT);
                MultipartFile[] foot = form.getPicFile2();
                Pic footPic = new Pic();
                if (foot.length>0) {
                    for(int i = 0 ; i < foot.length; i++){
                        footPic.setPicUrl(awsService.postFile(foot[i]));
                        footPic.setItemId(form.getId());
                        footPic.setItemType(Constant.EVENT_PIC_TYPE.RAMEN_FOOT);
                        picService.insertPic(footPic);
                    }
                }
            }

            txManager.commit(txStatus);
            session.setAttribute("error",0);
        } catch (Exception e) {
            txManager.rollback(txStatus);
            session.setAttribute("error",1);
            logger.error("update ramen failed!, error=" + e.getMessage());
            logger.error("update ramen failed!, error=" + e.toString());
            e.printStackTrace();
        }
        return "redirect:/admin/ramen/list/";
    }
}
