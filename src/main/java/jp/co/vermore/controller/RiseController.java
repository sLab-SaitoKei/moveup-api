package jp.co.vermore.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.vermore.entity.Rise;
import jp.co.vermore.entity.RiseDetail;
import jp.co.vermore.service.CommentService;
import jp.co.vermore.common.JsonObject;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.jsonparse.RiseDetailJsonParse;
import jp.co.vermore.jsonparse.RiseJsonParse;
import jp.co.vermore.service.RiseService;

/**
 * RiseController
 * Created by Wangyan.
 * <p>
 * DateTime: 2018/02/28 17:44
 * Copyright: sLab, Corp
 */
@Controller
public class RiseController extends BaseController {

    @Autowired
    private RiseService riseService;

    @Autowired
    private CommentService commentService;

    //Rise list
    @RequestMapping(value = "/api/rise/list/{limit}/{offset}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getRiseList(@PathVariable int limit, @PathVariable int offset) {

        List<Rise> list = riseService.getRiseAll(limit,offset);

        List<RiseJsonParse> rjpList = new ArrayList<>();
        RiseJsonParse rjp = new RiseJsonParse();
        for (Rise rd: list) {
            rjp.setId(rd.getId());
            rjp.setName1(rd.getName1());
            rjp.setName2(rd.getName2());
            rjp.setCategory(rd.getCategory());
            rjp.setPicUrl(rd.getPicUrl());
            rjpList.add(rjp);
        }
        jsonObject.setResultList(rjpList);
        return jsonObject;
    }

    //Rise list by category
    @RequestMapping(value = "/api/rise/list/category/{category}/{limit}/{offset}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getRiseListByCategory(@PathVariable String category,@PathVariable int limit, @PathVariable int offset) {
        List<Rise> list = riseService.getRiseAllByCategory(category,limit,offset);
        List<RiseJsonParse> rjpList = new ArrayList<>();
        RiseJsonParse rjp = new RiseJsonParse();
        for (Rise rd: list) {
            rjp.setId(rd.getId());
            rjp.setName1(rd.getName1());
            rjp.setName2(rd.getName2());
            rjp.setCategory(rd.getCategory());
            rjp.setPicUrl(rd.getPicUrl());
            rjpList.add(rjp);
        }
        jsonObject.setResultList(rjpList);
        return jsonObject;
    }

    //Rise Detail
    @RequestMapping(value = "/api/rise/{id}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getRise(@PathVariable int id) {

        RiseDetail riseDetail = riseService.getRiseDetail(id);
        List<RiseDetailJsonParse> rjpList = new ArrayList<>();
            RiseDetailJsonParse rjp = new RiseDetailJsonParse();

            rjp.setName1(riseDetail.getName1());
            rjp.setName2(riseDetail.getName2());
            rjp.setOffice(riseDetail.getOffice());
            rjp.setCategory(riseDetail.getCategory());
            rjp.setHonor(riseDetail.getHonor());
            rjp.setTopTitle(riseDetail.getTopTitle());
            rjp.setTitle1(riseDetail.getTitle1());
            rjp.setTitle2(riseDetail.getTitle2());
            rjp.setTitle3(riseDetail.getTitle3());
            rjp.setTopPicUrl(riseDetail.getTopPicUrl());
            rjp.setPicUrl1(riseDetail.getPicUrl1());
            rjp.setPicUrl2(riseDetail.getPicUrl2());
            rjp.setPicUrl3(riseDetail.getPicUrl3());
            rjp.setPicUrl4(riseDetail.getPicUrl4());
            rjp.setVideoUrl1(riseDetail.getVideoUrl1());
            rjp.setDesc1(riseDetail.getDesc1());
            rjp.setDesc2(riseDetail.getDesc2());
            rjp.setDesc3(riseDetail.getDesc3());

            rjpList.add(rjp);

        jsonObject.setResultList(rjpList);
        return jsonObject;
    }




}

