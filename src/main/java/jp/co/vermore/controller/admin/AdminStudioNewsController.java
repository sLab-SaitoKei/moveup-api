package jp.co.vermore.controller.admin;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.DatatablesJsonObject;
import jp.co.vermore.common.JsonStatus;
import jp.co.vermore.common.mvc.APIException;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.entity.*;
import jp.co.vermore.form.EventRegistForm;
import jp.co.vermore.form.StudioGalleryForm;
import jp.co.vermore.form.TopForm;
import jp.co.vermore.form.admin.NewsForm;
import jp.co.vermore.form.admin.NewsListForm;
import jp.co.vermore.service.*;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * StudioNewsAdminController
 * Created by wubin.
 * <p>
 * DateTime: 2018/05/25 20:27
 * Copyright: sLab, Corp
 */
@Controller
public class AdminStudioNewsController extends BaseController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private StudioService studioService;

    @Autowired
    private EntryService entryService;

    @Autowired
    PlatformTransactionManager txManager;

    @Autowired
    private AWSService awsService;

    @Autowired
    private PicService picService;

    @RequestMapping(value = "/admin/studioNews/list/", method = RequestMethod.GET)
    public String newsAll(Model model,HttpServletRequest request) {

        int errorCode = 0;
        if(!request.getSession().isNew()){
            if(request.getSession().getAttribute("error") != null && request.getSession().getAttribute("error") != ""){
                errorCode = (int)request.getSession().getAttribute("error");
                request.getSession().setAttribute("error",0);
            }
        }
        model.addAttribute("errorCode", errorCode);

        int type = Constant.NEWS_TYPE.STUDIO_NEWS;
        List<News> news = newsService.getStudioNewsALL(type);
        NewsForm form = new NewsForm();
        model.addAttribute("newsDeleteForm", form);
        model.addAttribute("studioNews_all", news);
        return "admin/studioNewsList";
    }

    @RequestMapping(value = "/admin/studio/list/", method = RequestMethod.POST)
    @ResponseBody
    public DatatablesJsonObject studioList(@RequestBody NewsListForm form) {
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
        List<News> dataList = studioService.getStudioAllByCondition(form);

        int totalCountFiltered = studioService.getStudioCountByCondition(form);
        int totalCount = studioService.getStudioCount();
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

    @RequestMapping(value = "/admin/studio/regist/", method = RequestMethod.GET)
    public String newsInsert(Model model) {
        NewsForm form = new NewsForm();
        model.addAttribute("studioNewsForm", form);
        return "admin/studioRegist";
    }

    @RequestMapping(value = "/admin/studio/regist/", method = RequestMethod.POST)
    public String studioInsert(@ModelAttribute NewsForm form, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try {
            long newsId = newsService.insertStudioNews(form);
            newsService.insertDetailStudioNews(form, newsId);

            Pic starPic = new Pic();
            if (!form.getPicFile().isEmpty()) {
                starPic.setPicUrl(awsService.postFile(form.getPicFile()));
                starPic.setItemId(newsId);
                starPic.setItemType(Constant.EVENT_PIC_TYPE.STUDIO_NEWS);
                picService.insertPic(starPic);
            }

            MultipartFile[] star = form.getPicFile1();

            if (star.length > 0) {
                for (int i = 0; i < star.length; i++) {
                    starPic.setPicUrl(awsService.postWatermarkFile(request, star[i]));
                    starPic.setItemId(newsId);
                    starPic.setItemType(Constant.EVENT_PIC_TYPE.STUDIO_NEWS_DETAIL);
                    picService.insertPic(starPic);
                }
            }
            txManager.commit(txStatus);
            session.setAttribute("error",0);
        } catch (Exception e) {
            txManager.rollback(txStatus);
            session.setAttribute("error",1);
            logger.error("insert studioNews failed!, error=" + e.getMessage());
            logger.error("insert studioNews failed!, error=" + e.toString());
            e.printStackTrace();
        }
        return "redirect:/admin/studioNews/list/";
    }

    @RequestMapping(value = "/admin/studio/delete/", method = RequestMethod.POST)
    public String newsDetailDelete(@ModelAttribute NewsForm form,HttpServletRequest request) {
        HttpSession session = request.getSession();
        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try {
            newsService.deleteNews(form);
            newsService.deleteDetailNews(form);
            picService.deleteStudioNewsPicUrl(form);

            txManager.commit(txStatus);
            session.setAttribute("error",0);
        } catch (Exception e) {
            txManager.rollback(txStatus);
            session.setAttribute("error",1);
            logger.error("delete studioNews failed!, error=" + e.getMessage());
            logger.error("delete studioNews failed!, error=" + e.toString());
            e.printStackTrace();
        }
        return "redirect:/admin/studioNews/list/";
    }

    @RequestMapping(value = "/admin/studio/edit/{id}/", method = RequestMethod.GET)
    public String newsUpdate(Model model, @PathVariable int id) {
        NewsForm newsForm = new NewsForm();
        List<News> list = newsService.getNewsList(id);
        String detail = newsService.getNewsDetail(id);
        if (list != null && list.size() > 0) {
            newsForm.setId(list.get(0).getId());
            newsForm.setDetail(detail);
            newsForm.setTitle(list.get(0).getTitle());
            newsForm.setType(list.get(0).getType());
            newsForm.setExcerpt(list.get(0).getExcerpt());
            newsForm.setPublishStart(DateUtil.dateToStringyyyy_MM_dd(list.get(0).getPublishStart()));
            newsForm.setPublishEnd(DateUtil.dateToStringyyyy_MM_dd(list.get(0).getPublishEnd()));
            newsForm.setDate(DateUtil.dateToStringyyyy_MM_dd(list.get(0).getDate()));
            newsForm.setSortScore(list.get(0).getSortScore());
            Pic pic = picService.getStudioUrl(id);
            newsForm.setPicUrl(pic.getPicUrl());
            List<Pic> picList = picService.getStudioUrlList(id);
            List<String> starList = new ArrayList<String>();
            for (Pic picUrl : picList) {
                starList.add(picUrl.getPicUrl());
            }
            newsForm.setPicUrl1(starList);
            model.addAttribute("newsForm", newsForm);
            return "admin/studioEdit";
        } else {
            return "redirect:/admin/studioNews/list/";
        }
    }

    @RequestMapping(value = "/admin/studio/update/", method = RequestMethod.POST)
    public String newsUpdate1(@ModelAttribute NewsForm form,HttpServletRequest request) {
        HttpSession session = request.getSession();
        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try {
            newsService.updateStudioNews(form);
            newsService.updateDetailStudioNews(form);
            txManager.commit(txStatus);
            session.setAttribute("error",0);
        } catch (Exception e) {
            txManager.rollback(txStatus);
            session.setAttribute("error",1);
            logger.error("update studioNews failed!, error=" + e.getMessage());
            logger.error("update studioNews failed!, error=" + e.toString());
            e.printStackTrace();
        }
        return "redirect:/admin/studioNews/list/";
    }

    @RequestMapping(value = "/admin/studioGallery/", method = RequestMethod.GET)
    public String StudioGallery(Model model) {
        List<StudioGallery> galleryList = studioService.getStudioGalleryForAdmin();
        StudioGalleryForm tf = new StudioGalleryForm();
        for (StudioGallery t : galleryList) {
            if (t.getId() == 1) {
                tf.setUrl1(t.getUrl());

            } else if (t.getId() == 2) {
                tf.setUrl2(t.getUrl());

            } else if (t.getId() == 3) {
                tf.setUrl3(t.getUrl());

            } else if (t.getId() == 4) {
                tf.setUrl4(t.getUrl());

            } else if (t.getId() == 5) {
                tf.setUrl5(t.getUrl());

            } else if (t.getId() == 6) {
                tf.setUrl6(t.getUrl());

            } else if (t.getId() == 7) {
                tf.setUrl7(t.getUrl());

            } else if (t.getId() == 8) {
                tf.setUrl8(t.getUrl());
            }
        }
        model.addAttribute("studioGalleryForm", tf);
        return "admin/studioGallery";
    }

    @RequestMapping(value = "/admin/studioGallery/edit/", method = RequestMethod.POST)
    public String galleryUpdate(@ModelAttribute StudioGalleryForm studioGalleryForm,HttpServletRequest request) throws APIException {
        HttpSession session = request.getSession();
        // upload files
        try {
            if (!studioGalleryForm.getPicFile1().isEmpty()) {
                studioGalleryForm.setUrl1(awsService.postFile(studioGalleryForm.getPicFile1()));
            }
            if (!studioGalleryForm.getPicFile2().isEmpty()) {
                studioGalleryForm.setUrl2(awsService.postFile(studioGalleryForm.getPicFile2()));
            }
            if (!studioGalleryForm.getPicFile3().isEmpty()) {
                studioGalleryForm.setUrl3(awsService.postFile(studioGalleryForm.getPicFile3()));
            }
            if (!studioGalleryForm.getPicFile4().isEmpty()) {
                studioGalleryForm.setUrl4(awsService.postFile(studioGalleryForm.getPicFile4()));
            }
            if (!studioGalleryForm.getPicFile5().isEmpty()) {
                studioGalleryForm.setUrl5(awsService.postFile(studioGalleryForm.getPicFile5()));
            }
            if (!studioGalleryForm.getPicFile6().isEmpty()) {
                studioGalleryForm.setUrl6(awsService.postFile(studioGalleryForm.getPicFile6()));
            }
            if (!studioGalleryForm.getPicFile7().isEmpty()) {
                studioGalleryForm.setUrl7(awsService.postFile(studioGalleryForm.getPicFile7()));
            }
            if (!studioGalleryForm.getPicFile8().isEmpty()) {
                studioGalleryForm.setUrl8(awsService.postFile(studioGalleryForm.getPicFile8()));
            }
            session.setAttribute("error",0);
        } catch (IOException e) {
            session.setAttribute("error",1);
            throw new APIException(JsonStatus.DATA_SAVE_FAILED);
        }
        studioService.updateGallery(studioGalleryForm);
        return "redirect:/admin/studioGallery/";
    }
}