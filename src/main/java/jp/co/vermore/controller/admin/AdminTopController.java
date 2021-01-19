package jp.co.vermore.controller.admin;

import jp.co.vermore.common.JsonStatus;
import jp.co.vermore.common.mvc.APIException;
import jp.co.vermore.entity.Top;
import jp.co.vermore.form.TopForm;
import jp.co.vermore.form.admin.RecruitForm;
import jp.co.vermore.service.AWSService;
import jp.co.vermore.service.TopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * TopAdminController
 * Created by Wangyan.
 * <p>
 * DateTime: 2018/04/06 11:39
 * Copyright: sLab, Corp
 */
@Controller
public class AdminTopController {

    @Autowired
    private TopService topService;

    @Autowired
    PlatformTransactionManager txManager;

    @Autowired
    private AWSService awsService;

    @RequestMapping(value = "/admin/top/", method = RequestMethod.GET)
    public String top(Model model,HttpServletRequest request) {

        int errorCode = 0;
        if(!request.getSession().isNew()){
            if(request.getSession().getAttribute("error") != null && request.getSession().getAttribute("error") != ""){
                errorCode = (int)request.getSession().getAttribute("error");
                request.getSession().setAttribute("error",0);
            }
        }
        model.addAttribute("errorCode", errorCode);


        List<Top> topList = topService.getTopForAdmin();
        TopForm tf = new TopForm();
        for(Top t : topList){
            if(t.getId() == 1){
//                String url = t.getUrl();
//                String[] urls = url.split("----------");
//                tf.setPicUrl1(urls[0]);
//                tf.setVideoUrl1(urls[1]);
//                tf.setType1(t.getType());
                tf.setPicUrl1(t.getUrl());
                tf.setLinkUrl1(t.getLinkUrl());
                tf.setItemType1(t.getItemType());
            }else if(t.getId() == 2){
//                String url = t.getUrl();
//                String[] urls = url.split("----------");
//                tf.setPicUrl2(urls[0]);
//                tf.setVideoUrl2(urls[1]);
                if(t.getType() == 1){
                    tf.setPicUrl2(t.getUrl());
                    tf.setLinkUrl2(t.getLinkUrl());
                    tf.setItemType2(t.getItemType());
                }else{
                    tf.setVideoUrl2(t.getUrl());
                }
                tf.setType2(t.getType());
            }else if(t.getId() == 3){
                tf.setItemType3(t.getItemType());
                tf.setUrl3(t.getUrl());
                tf.setLinkUrl3(t.getLinkUrl());
            }else if(t.getId() == 4){
                tf.setItemType4(t.getItemType());
                tf.setUrl4(t.getUrl());
                tf.setLinkUrl4(t.getLinkUrl());
            }else if(t.getId() == 5){
                tf.setItemType5(t.getItemType());
                tf.setUrl5(t.getUrl());
                tf.setLinkUrl5(t.getLinkUrl());
            }
        }
        model.addAttribute("topForm", tf);
        return "admin/top";
    }

    @RequestMapping(value = "/admin/top/edit/", method = RequestMethod.POST)
    public String topUpdate(@ModelAttribute TopForm topForm,HttpServletRequest request) throws APIException {
        HttpSession session = request.getSession();
        // upload files
        try {
            if (!topForm.getPicFile1().isEmpty()) {
                topForm.setPicUrl1(awsService.postFile(topForm.getPicFile1()));
            }
            if (!topForm.getPicFile2().isEmpty()) {
                topForm.setPicUrl2(awsService.postFile(topForm.getPicFile2()));
            }
            if (!topForm.getPicFile3().isEmpty()) {
                topForm.setUrl3(awsService.postFile(topForm.getPicFile3()));
            }
            if (!topForm.getPicFile4().isEmpty()) {
                topForm.setUrl4(awsService.postFile(topForm.getPicFile4()));
            }
            if (!topForm.getPicFile5().isEmpty()) {
                topForm.setUrl5(awsService.postFile(topForm.getPicFile5()));
            }

            topService.updateTop(topForm);
            session.setAttribute("error",0);
        } catch (IOException e) {
            session.setAttribute("error",1);
            throw new APIException(JsonStatus.DATA_SAVE_FAILED);
        }
        return "redirect:/admin/top/";
    }

}
