package jp.co.vermore.controller.admin;

import java.util.ArrayList;
import java.util.List;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.DatatablesJsonObject;
import jp.co.vermore.entity.EntryMail;
import jp.co.vermore.entity.NewsDetail;
import jp.co.vermore.entity.Pic;
import jp.co.vermore.form.admin.NewsListForm;
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

import jp.co.vermore.entity.News;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.form.admin.NewsForm;
import jp.co.vermore.service.NewsService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * NewsAdminController
 * Created by wubin.
 * <p>
 * DateTime: 2018/03/03 11:13
 * Copyright: sLab, Corp
 */
@Controller
public class AdminNewsController extends BaseController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private EntryService entryService;

    @Autowired
    private AWSService awsService;

    @Autowired
    private PicService picService;

    @Autowired
    PlatformTransactionManager txManager;

    @RequestMapping(value = "/admin/news/list/", method = RequestMethod.GET)
    public String newsAll(Model model,HttpServletRequest request) {

        int errorCode = 0;
        if(!request.getSession().isNew()){
            if(request.getSession().getAttribute("error") != null && request.getSession().getAttribute("error") != ""){
                errorCode = (int)request.getSession().getAttribute("error");
                request.getSession().setAttribute("error",0);
            }
        }
        model.addAttribute("errorCode", errorCode);

        List<News> news = newsService.getNewsAll();
        NewsForm form = new NewsForm();
        model.addAttribute("newsDeleteForm", form);
        model.addAttribute("news_all", news);
        return "admin/newsList";
    }

    @RequestMapping(value = "/admin/news/list/", method = RequestMethod.POST)
    @ResponseBody
    public DatatablesJsonObject newsList(@RequestBody NewsListForm form){
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
        List<News> dataList = newsService.getNewsAllByCondition(form);

        for(News news:dataList){
            int type =0;
            //it's my faults
            if(news.getType() == Constant.NEWS_TYPE.EVENT){
                type =  Constant.NEWS_TYPE.MOVEUP;
            }else if(news.getType() == Constant.NEWS_TYPE.MOVEUP){
                type = Constant.NEWS_TYPE.EVENT;
            }
            EntryMail entity = entryService.getEntryMailByEntryIdAndType( news.getId(),type);
            if(entity != null){
                news.setEntryType(1);
            }else{
                news.setEntryType(0);
            }
        }

        int totalCountFiltered = newsService.getNewsCountByCondition(form);
        int totalCount = newsService.getNewsCount();
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

    @RequestMapping(value = "/admin/news/regist/", method = RequestMethod.GET)
    public String newsInsert(Model model) {
        NewsForm form = new NewsForm();
        model.addAttribute("newsForm", form);
        return "admin/newsRegist";
    }

    @RequestMapping(value = "/admin/news/regist/", method = RequestMethod.POST)
    public String newsInsert(@ModelAttribute NewsForm form ,HttpServletRequest request) {
        HttpSession session = request.getSession();
        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try {
            long newsId = newsService.insertNews(form);
            newsService.insertDetailNews(form,newsId);
            MultipartFile[] top = form.getPicFile1();
            MultipartFile[] foot = form.getPicFile2();

            if(!form.getPicFile1()[0].isEmpty()) {
                Pic topPic = new Pic();
                if (top.length>0) {
                    for(int i = 0 ; i < top.length; i++){
                        topPic.setPicUrl(awsService.postFile(top[i]));
                        topPic.setItemId(newsId);
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
                        footPic.setItemId(newsId);
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
            logger.error("insert news failed!, error=" + e.getMessage());
            logger.error("insert news failed!, error=" + e.toString());
            e.printStackTrace();
        }
        return "redirect:/admin/news/list/";
    }

    @RequestMapping(value = "/admin/news/delete/", method = RequestMethod.POST)
    public String newsDetailDelete(@ModelAttribute NewsForm form,HttpServletRequest request) {
        HttpSession session = request.getSession();
        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try {
            newsService.deleteNews(form);
            newsService.deleteDetailNews(form);
            picService.deleteNewsPicUrl(form.getId(),Constant.EVENT_PIC_TYPE.NEWS_TOP);
            picService.deleteNewsPicUrl(form.getId(),Constant.EVENT_PIC_TYPE.NEWS_FOOT);
            txManager.commit(txStatus);
            session.setAttribute("error",0);
        } catch (Exception e) {
            txManager.rollback(txStatus);
            session.setAttribute("error",1);
            logger.error("delete news failed!, error=" + e.getMessage());
            logger.error("delete news failed!, error=" + e.toString());
            e.printStackTrace();
        }
        return "redirect:/admin/news/list/";
    }

    @RequestMapping(value = "/admin/news/edit/{id}/", method = RequestMethod.GET)
    public String newsUpdate(Model model , @PathVariable long id) {
        NewsForm newsForm = new NewsForm();
        List<News> list = newsService.getNewsList(id);
        String detail = newsService.getNewsDetail(id);

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

        newsForm.setPicUrl1(topList);
        newsForm.setPicUrl2(footList);

        if(list != null && list.size() > 0){
            newsForm.setId(list.get(0).getId());
            newsForm.setDetail(detail);
            newsForm.setTitle(list.get(0).getTitle());
            newsForm.setType(list.get(0).getType());
            newsForm.setExcerpt(list.get(0).getExcerpt());
            newsForm.setPublishStart(DateUtil.dateToStringyyyy_MM_dd_HH_mm(list.get(0).getPublishStart()));
            newsForm.setPublishEnd(DateUtil.dateToStringyyyy_MM_dd_HH_mm(list.get(0).getPublishEnd()));
            String date = DateUtil.dateToStringyyyy_MM_dd_HH_mm(list.get(0).getDate());
//            newsForm.setDate(date.replace(" ", "T"));
            newsForm.setDate(date);
            newsForm.setSortScore(list.get(0).getSortScore());

            model.addAttribute("newsForm", newsForm);
            return "admin/newsEdit";
        }else {
            return "redirect:/admin/news/list/";
        }
    }

    @RequestMapping(value = "/admin/news/update/", method = RequestMethod.POST)
    public String newsUpdate1(@ModelAttribute NewsForm form,HttpServletRequest request) {
        HttpSession session = request.getSession();
        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try {
            List<String> picUrl1 = form.getPicUrl1();
            newsService.updateNews(form);
            newsService.updateDetailNews(form);

            if(form.getPicUrl1().size()==0 && form.getPicFile1()[0].isEmpty()){
                picService.deleteNewsPicUrl(form.getId(),Constant.EVENT_PIC_TYPE.NEWS_TOP);
            }

            if(form.getPicUrl2().size()==0 && form.getPicFile2()[0].isEmpty()){
                picService.deleteNewsPicUrl(form.getId(),Constant.EVENT_PIC_TYPE.NEWS_FOOT);
            }

            if(!form.getPicFile1()[0].isEmpty()) {
                picService.deleteNewsPicUrl(form.getId(),Constant.EVENT_PIC_TYPE.NEWS_TOP);
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
                picService.deleteNewsPicUrl(form.getId(),Constant.EVENT_PIC_TYPE.NEWS_FOOT);
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
            logger.error("update news failed!, error=" + e.getMessage());
            logger.error("update news failed!, error=" + e.toString());
            e.printStackTrace();
        }
        return "redirect:/admin/news/list/";
    }
}
