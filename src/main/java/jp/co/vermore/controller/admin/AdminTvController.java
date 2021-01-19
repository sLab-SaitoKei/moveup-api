package jp.co.vermore.controller.admin;


import jp.co.vermore.common.Constant;
import jp.co.vermore.common.DatatablesJsonObject;
import jp.co.vermore.common.JsonStatus;
import jp.co.vermore.common.mvc.APIException;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.entity.*;
import jp.co.vermore.form.EventRegistForm;
import jp.co.vermore.form.admin.TVListForm;
import jp.co.vermore.form.admin.Team2020Form;
import jp.co.vermore.form.admin.Team2020ListForm;
import jp.co.vermore.form.admin.TvRegistForm;
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
 * TvAdminController
 * Created by wangnannan.
 * <p>
 * DateTime: 2018/06/08 16:31
 * Copyright: sLab, Corp
 */
@Controller
public class AdminTvController extends BaseController {

    @Autowired
    private TvService tvService;

    @Autowired
    private TvDetailService tvDetailService;

    @Autowired
    private TvVideoService tvVideoService;

    @Autowired
    private FavDetailService favDetailService;

    @Autowired
    private AWSService awsService;

    @Autowired
    PlatformTransactionManager txManager;

    @RequestMapping(value = "/admin/tv/list/", method = RequestMethod.GET)
    public String getTvList(Model model,HttpServletRequest request) {
        int errorCode = 0;
        if(!request.getSession().isNew()){
            if(request.getSession().getAttribute("error") != null && request.getSession().getAttribute("error") != ""){
                errorCode = (int)request.getSession().getAttribute("error");
                request.getSession().setAttribute("error",0);
            }
        }
        model.addAttribute("errorCode", errorCode);
        return "admin/tvList";
    }

    @RequestMapping(value = "/admin/tv/list/", method = RequestMethod.POST)
    @ResponseBody
    public DatatablesJsonObject getTVList(@RequestBody TVListForm form) {
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
        List<Tv> dataList = tvService.getTvList(form);

        int totalCountFiltered = tvService.getTotalCountFiltered(form);
        int totalCount = tvService.getTotalCount();

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

    @RequestMapping(value = "/admin/tv/regist/", method = RequestMethod.GET)
    public String tvInsert(Model model) {
        TvRegistForm form = new TvRegistForm();
        model.addAttribute("tvform", form);
        return "admin/tvRegist";
    }

    // TV admin add execution
    @RequestMapping(value = "/admin/tv/regist/", method = RequestMethod.POST)
    public String tvRegist(@ModelAttribute TvRegistForm form, Model model, HttpServletRequest request) throws APIException {
        HttpSession session = request.getSession();
        // upload files
        try {
            if (!form.getPicFile().isEmpty()) {
                //form.setPicUrl(awsService.postMiddleThumbnailFile(form.getPicFile()));
                form.setPicUrl(awsService.postFile(form.getPicFile()));
            }
            if (!form.getVideoFileFirst().isEmpty()) {
                form.setVideoUrlFirst(awsService.postFile(form.getVideoFileFirst()));
            }
            session.setAttribute("error",0);
        } catch (IOException e) {
            session.setAttribute("error",1);
            throw new APIException(JsonStatus.DATA_SAVE_FAILED);
        }

        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try {
            Long tvId = tvService.insertTv(form);
            Long tvDetailId = tvDetailService.insertTvDetail(form, tvId);
            tvVideoService.insertTvVideo(form, tvDetailId);

            txManager.commit(txStatus);
            session.setAttribute("error",0);
        } catch (Exception e) {
            txManager.rollback(txStatus);
            session.setAttribute("error",1);
            logger.error("insert Tv failed, error=" + e.getMessage());
            logger.error("insert Tv failed, error=" + e.toString());
            e.printStackTrace();
        }
        return "redirect:/admin/tv/list/";
    }

    //   Tv admin edit show
    @RequestMapping(value = "/admin/tv/edit/{id}/", method = RequestMethod.GET)
    public String tvEdit(Model model, @PathVariable Long id) {
        TvRegistForm tvRegistForm = new TvRegistForm();

        Tv tv = tvService.getById(id);
        tvRegistForm.setTvId(tv.getId());
        tvRegistForm.setTitle(tv.getTitle());
        tvRegistForm.setOffice(tv.getOffice());
        tvRegistForm.setSortScore(tv.getSortScore().toString());
        tvRegistForm.setPicUrl(tv.getUrl());
        tvRegistForm.setTvType(tv.getTvType());
        tvRegistForm.setDate(DateUtil.dateToStringyyyy_MM_dd(tv.getDate()));
        tvRegistForm.setPublishStart(DateUtil.dateToStringyyyy_MM_dd_HH_mm(tv.getPublishStart()));
        tvRegistForm.setPublishEnd(DateUtil.dateToStringyyyy_MM_dd_HH_mm(tv.getPublishEnd()));


        TvDetail tvDetail = tvDetailService.getTvDetailById(tv.getId());
        tvRegistForm.setTvDetailId(tvDetail.getId());
        tvRegistForm.setDesc1(tvDetail.getDetail());
        tvRegistForm.setType(tvDetail.getType());

        List<TvVideo> tvVideo = tvVideoService.getTvVideoById(tvDetail.getId());
        if (tvVideo.size() > 0) {
            for (TvVideo list : tvVideo) {
                if (list.getLastUrl().equals("")) {
                    tvRegistForm.setVideoUrlLast("");
                } else {
                    tvRegistForm.setVideoUrlLast(list.getLastUrl());
                }

                if(list.getCategory() == Constant.TV_VIDEO_TYPE.YOUTUBE_TYPE){
                    tvRegistForm.setYouTubeVideoUrl(list.getFirstUrl());
                }else if(list.getCategory() == Constant.TV_VIDEO_TYPE.AWS_TYPE){
                    tvRegistForm.setVideoUrlFirst(list.getFirstUrl());
                }
                tvRegistForm.setStatus(list.getStatus());
                tvRegistForm.setCategory(list.getCategory());
            }
        }

        model.addAttribute("tvRegistForm", tvRegistForm);
        return "admin/tvEdit";
    }

    @RequestMapping(value = "/admin/tv/update/", method = RequestMethod.POST)
    public String tvUpdate(@ModelAttribute TvRegistForm form, HttpServletRequest request) throws APIException {
        HttpSession session = request.getSession();
        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);
        // upload files
        try {
            if (!form.getPicFile().isEmpty()) {
                //form.setPicUrl(awsService.postMiddleThumbnailFile(form.getPicFile()));
                form.setPicUrl(awsService.postFile(form.getPicFile()));
            }
            if (!form.getVideoFileLast().isEmpty()) {
                form.setVideoUrlLast(awsService.postFile(form.getVideoFileLast()));
            }
            session.setAttribute("error",0  );
        } catch (IOException e) {
            session.setAttribute("error",1);
            throw new APIException(JsonStatus.DATA_SAVE_FAILED);
        }

        try {
            Long tvId = tvService.updateTv(form);
            TvDetail tvDetail = tvDetailService.updateTvDetail(form, tvId);
            tvVideoService.updateTvVideo(form, tvDetail.getId());

            txManager.commit(txStatus);
            session.setAttribute("error",0  );
        } catch (Exception e) {
            txManager.rollback(txStatus);
            session.setAttribute("error",1);
            logger.error("Update TV failed!, error=" + e.getMessage());
            logger.error("Update TV failed!, error=" + e.toString());
        }
        return "redirect:/admin/tv/list/";
    }

    @RequestMapping(value = "/admin/tv/delete/", method = RequestMethod.POST)
    public String tvDelete(@ModelAttribute TvRegistForm form,HttpServletRequest request) {
        HttpSession session = request.getSession();
        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try {
            Long tvId = tvService.deleteTv(form);
            TvDetail TvDetail = tvDetailService.getTvDetailById(form.getId());
            tvDetailService.deleteTvDetail(tvId);
            tvVideoService.deleteTvVideo(TvDetail.getId());
            favDetailService.deletefavDetail(TvDetail.getId(), Constant.FAV_TYPE.TV);
            txManager.commit(txStatus);
            session.setAttribute("error",0);
        } catch (Exception e) {
            txManager.rollback(txStatus);
            session.setAttribute("error",1);
            logger.error("delete TV failed!, error=" + e.getMessage());
            logger.error("delete TV failed!, error=" + e.toString());
            e.printStackTrace();
        }
        return "redirect:/admin/tv/list/";
    }
}
