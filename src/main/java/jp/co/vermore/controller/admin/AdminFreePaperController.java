package jp.co.vermore.controller.admin;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.DatatablesJsonObject;
import jp.co.vermore.common.mvc.APIException;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.entity.FreePaper;
import jp.co.vermore.entity.FreePaperDetail;
import jp.co.vermore.form.admin.FreePaperForm;
import jp.co.vermore.form.admin.FreePaperListForm;
import jp.co.vermore.service.AWSService;
import jp.co.vermore.service.FreePaperService;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;




/**
 * AdminFreePaperController
 * Created by wubin.
 *
 * DateTime: 2018/04/12 16:00
 * Copyright: sLab, Corp
 */
@Controller
public class AdminFreePaperController extends BaseController {

    @Autowired
    private FreePaperService freePaperService;

    @Autowired
    PlatformTransactionManager txManager;

    @Autowired
    private AWSService awsService;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "/admin/freepaper/list/", method = RequestMethod.GET)
    public String freePaperAll(Model model,HttpServletRequest request) {

        int errorCode = 0;
        if(!request.getSession().isNew()){
            if(request.getSession().getAttribute("error") != null && request.getSession().getAttribute("error") != ""){
                errorCode = (int)request.getSession().getAttribute("error");
                request.getSession().setAttribute("error",0);
            }
        }
        model.addAttribute("errorCode", errorCode);

        List<FreePaper> freePaper = freePaperService.getFreePaper();
        FreePaperForm form = new FreePaperForm();
        model.addAttribute("freePaper", freePaper);
        model.addAttribute("form", form);
        return "admin/freePaperList";
    }

    @RequestMapping(value = "/admin/freepaper/list/", method = RequestMethod.POST)
    @ResponseBody
    public DatatablesJsonObject freePaperList(@RequestBody FreePaperListForm form){
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
        List<FreePaper> dataList = freePaperService.getFreePaperAllByCondition(form);

        int totalCountFiltered = freePaperService.getFreePaperCountByCondition(form);
        int totalCount = freePaperService.getFreePaperCount();
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

    @RequestMapping(value = "/admin/freepaper/regist/", method = RequestMethod.GET)
    public String freePaperInsert(Model model) {
        FreePaperForm form = new FreePaperForm();
        model.addAttribute("form", form);
        return "admin/freePaperRegist";
    }

    @RequestMapping(value = "/admin/freepaper/regist/", method = RequestMethod.POST)
    public String freePaperRegist(@ModelAttribute FreePaperForm form, Model model,HttpServletRequest request)  {
        HttpSession session = request.getSession();
        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);
        try {
            MultipartFile[] arr = form.getPicFil();
            MultipartFile pdf = form.getPicFil1();

            CommonsMultipartFile cf = (CommonsMultipartFile) pdf;
            DiskFileItem fi = (DiskFileItem) cf.getFileItem();
            File f = fi.getStoreLocation();

            File png = pdfToPng(f);

            form.setPicUrl(awsService.postFile(png));
            Long id = freePaperService.insertFreePaper(form);
            if (arr.length>0) {
                for(int i = 0 ; i < arr.length; i++){
                    form.setPicUrlDetail(awsService.postFile(arr[i]));
                    freePaperService.insertFreePaperDetail(form, id,Constant.FREEPAPER_PIC_TYPE.PDF);
                }
                for(int i = 0 ; i < arr.length; i++){
                    CommonsMultipartFile cfi = (CommonsMultipartFile) arr[i];
                    DiskFileItem fii = (DiskFileItem) cfi.getFileItem();
                    File fimg = fii.getStoreLocation();
                    File img = pdfToPng(fimg);
                    form.setPicUrlDetail(awsService.postFile(img));
                    freePaperService.insertFreePaperDetail(form, id,Constant.FREEPAPER_PIC_TYPE.IMG);
                }
            }
            txManager.commit(txStatus);
            session.setAttribute("error",0);
        } catch (Exception e) {
            txManager.rollback(txStatus);
            session.setAttribute("error",1);
            logger.error("Insert FreePaper failed!, error=" + e.getMessage());
            logger.error("Insert FreePaper failed!, error=" + e.toString());
        }
        return "redirect:/admin/freepaper/list/";
    }

    @RequestMapping(value = "/admin/freepaper/edit/{id}/", method = RequestMethod.GET)
    public String freePaperDetail(@PathVariable Long id,Model model) {
        FreePaper freePaper = freePaperService.getFreePaperById(id);
        List<FreePaperDetail> list = freePaperService.getFreePaperDetailAll(id, Constant.FREEPAPER_PIC_TYPE.IMG);
        FreePaperForm form = new FreePaperForm();
        List<String> urlList = new ArrayList<String>();
        for(FreePaperDetail fd:list){
            urlList.add(fd.getPicUrl());
        }
        form.setId(id);
        form.setUrlList(urlList);
        form.setTitle(freePaper.getTitle());
        form.setDate(DateUtil.dateToStringyyyy_MM_dd(freePaper.getDate()));
        form.setSortScore(freePaper.getSortScore());
        form.setVolume(freePaper.getVolume());
        form.setPublishStart(DateUtil.dateToStringyyyy_MM_dd(freePaper.getPublishStart()));
        form.setPublishEnd(DateUtil.dateToStringyyyy_MM_dd(freePaper.getPublishEnd()));
        model.addAttribute("form", form);
        return "admin/freePaperEdit";
    }

    @RequestMapping(value = "/admin/freepaper/delete/", method = RequestMethod.POST)
    public String freePaperDelete(@ModelAttribute FreePaperForm form) {
        freePaperService.deleteFreePaper(form.getId());
        freePaperService.deleteFreePaperDetail(form.getId());
        return "redirect:/admin/freepaper/list/";
    }

    @RequestMapping(value = "/admin/freepaper/update/", method = RequestMethod.POST)
    public String freePaperUpdate(@ModelAttribute FreePaperForm form,HttpServletRequest request) throws APIException {
        HttpSession session = request.getSession();
        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try {
            freePaperService.updateFreePaper(form);
            txManager.commit(txStatus);
            session.setAttribute("error",0);
        } catch (Exception e) {
            txManager.rollback(txStatus);
            session.setAttribute("error",1);
            logger.error("Update freepaper failed!, error=" + e.getMessage());
            logger.error("Update freepaper failed!, error=" + e.toString());
        }
        return "redirect:/admin/freepaper/list/";
    }

    @RequestMapping(value = "/admin/freepaper/delete/{id}/", method = RequestMethod.GET)
    public String freePaperDelete(@PathVariable Long id,Model model) {
        freePaperService.deleteFreePaper(id);
        freePaperService.deleteFreePaperDetail(id);
        return "redirect:/admin/freepaper/list/";
    }

    private static File pdfToPng(File pdf){
        File tempFile =null;
        try {
            // load PDF document
            PDDocument document = PDDocument.load(pdf);

            // create renderer
            PDFRenderer renderer = new PDFRenderer(document);

            // write images to files to disk as PNG
            if(document.getPages().getCount() > 0) {
                tempFile = File.createTempFile(pdf.getName(), ".png");
                BufferedImage bim = renderer.renderImageWithDPI(0, 100, ImageType.RGB);
                Thumbnails.of(bim).scale(1).outputQuality(1).toFile(tempFile);
            }
            document.close();
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            logger.error("---------------e" + e.getStackTrace());
            StringWriter stack = new StringWriter();
            e.printStackTrace(new PrintWriter(stack));
            logger.error("---------------e" + stack.toString());
        }
        return tempFile;
    }

}
