package jp.co.vermore.controller;

import java.util.ArrayList;
import java.util.List;

import jp.co.vermore.entity.*;
import jp.co.vermore.jsonparse.*;
import jp.co.vermore.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.vermore.common.JsonObject;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.common.util.DateUtil;

/**
 * TopController
 * Created by Wangyan.
 * <p>
 * DateTime: 2018/03/16 8:50
 * Copyright: sLab, Corp
 */

@Controller
public class TopController extends BaseController {

    @Autowired
    private TopService topService;

    @Autowired
    private EventService eventService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private RiseService riseService;

    @Autowired
    private TvService tvService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private FreePaperService freePaperService;

    @Autowired
    private WidgetService widgetService;

    //TOP
    @RequestMapping(value = "/api/top/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getTopPicForPc() {
        //EVENT
        List<Event> eventList = eventService.getEventForTop();
        List<TopEventJsonParse> tejpList = new ArrayList<>();
        if (eventList != null) {
            for (Event ed : eventList) {
                TopEventJsonParse tejp = new TopEventJsonParse();
                tejp.setUuid(ed.getUuid());
                tejp.setSortScore(ed.getSortScore());
                tejp.setTitle(ed.getTitle());
                tejp.setExcerpt(ed.getExcerpt());
                tejp.setPicUrl(ed.getPicUrl());

                String YYYYMMDD = DateUtil.dateToStringyyyy_MM_dd(ed.getHoldDate());
                String[] yyyymmdd = YYYYMMDD.split("-");
                String mmdd =yyyymmdd[0] + "/" + yyyymmdd[1] + "/" + yyyymmdd[2];
                tejp.setHoldDate(mmdd + " " + DateUtil.getWeekOfDate(ed.getHoldDate()));
                tejpList.add(tejp);
            }
        }

        //NEWS
        List<News> newsList = newsService.getNewsAllForTop();
        List<TopNewsJsonParse> tnjpList = new ArrayList<>();
        if (newsList != null) {
            for (News nd : newsList) {
                TopNewsJsonParse tnjp = new TopNewsJsonParse();
                tnjp.setUuid(nd.getUuid());
                tnjp.setDate(DateUtil.dateToStringyyyy_MM_dd(nd.getDate()));
                if (nd.getType() == 2) {
                    tnjp.setType("MOVEUP");
                    tnjp.setTypeColor("background-color:red");
                } else if (nd.getType() == 1 || nd.getType() == 4) {
                    tnjp.setType("EVENT");
                    tnjp.setTypeColor("background-color:#004984");
                } else {
                    tnjp.setType("NEWS");
                    tnjp.setTypeColor("background-color:#009a4c");
                }
                tnjp.setTitle(nd.getTitle());
                tnjpList.add(tnjp);
            }
        }

        //RISE
        List<Rise> riseList = riseService.getRiseForTop();
        List<TopRiseJsonParse> trjpList = new ArrayList<>();
        if (riseList != null) {
            for (Rise rd : riseList) {
                TopRiseJsonParse trjp = new TopRiseJsonParse();
                trjp.setId(rd.getId());
                trjp.setSortScore(rd.getSortScore());
                trjp.setName1(rd.getName1());
                trjp.setPicUrl(rd.getPicUrl());
                trjpList.add(trjp);
            }
        }

        //TV
        List<Tv> tvList = tvService.getTvForTop();
        List<TopTvJsonParse> tvjpList = new ArrayList<>();
        if (tvList != null && tvList.size()>0) {
            for (Tv tv : tvList) {
                TopTvJsonParse tvp = new TopTvJsonParse();
                tvp.setTvPic(tv.getUrl());
                tvp.setUuid(tv.getUuid());
                tvp.setTitle(tv.getTitle());
                tvp.setOffice(tv.getOffice());

                tvp.setDate(DateUtil.dateToStringyyyy_MM_dd(tv.getDate()));
                tvjpList.add(tvp);
            }
        }

        //Rise Info.
        List<Comment> commentList = commentService.getCommentRiseForTop();
        List<TopCommentJsonParse> tcjpList = new ArrayList<>();
        if (commentList != null) {
            for (Comment cd : commentList) {
                TopCommentJsonParse cjp = new TopCommentJsonParse();
                cjp.setItemId(cd.getItemId());
                cjp.setCreateDatetime(DateUtil.dateToStringyyyy_MM_dd(cd.getCreateDatetime()));
                cjp.setDesc(cd.getDesc());
                tcjpList.add(cjp);
            }
        }

        //STORE
        List<Goods> goodsList = goodsService.getGoodsForTop();
        List<TopGoodsJsonParse> tgjpList = new ArrayList<>();
        if (goodsList != null) {
            for (Goods gd : goodsList) {
                TopGoodsJsonParse tgjp = new TopGoodsJsonParse();
                tgjp.setUuid(gd.getUuid());
                tgjp.setPicUrl(gd.getPicUrl());
                tgjp.setTitle(gd.getTitle());
                tgjpList.add(tgjp);
            }
        }


        //FREE PAPER
        List<FreePaper> list = freePaperService.getFreePaperForTop();
        List<FreePaperJsonParse> freePaperList = new ArrayList<>();
        if (list != null) {
            for (FreePaper fpd : list) {
                FreePaperJsonParse fpjp = new FreePaperJsonParse();
                fpjp.setUuid(fpd.getUuid());
                fpjp.setRanking(fpd.getVolume());
                fpjp.setPicUrl(fpd.getPicUrl());
                fpjp.setTitle(fpd.getTitle());

                String YYYYMMDD = DateUtil.dateToStringyyyy_MM_dd(fpd.getDate());
                String[] yyyymmdd = YYYYMMDD.split("-");
                String mmdd = yyyymmdd[1] + "/" + yyyymmdd[2];

                fpjp.setYear(yyyymmdd[0]);
                fpjp.setDate(mmdd);
                fpjp.setWeekDay(DateUtil.getWeekOfDate(fpd.getDate()));

                freePaperList.add(fpjp);
            }
        }


        //TOP
        List<Top> topList = topService.getTopPic();
        List<TopJsonParse> tjpList = new ArrayList<>();
        if (topList != null && topList.size() > 0) {
            TopJsonParse tjp = new TopJsonParse();
            tjp.setType(topList.get(2).getType());

            tjp.setPicUrlLeft(topList.get(0).getUrl());
            tjp.setVideoUrl(topList.get(1).getUrl());
            tjp.setPicUrlRight1(topList.get(2).getUrl());
            tjp.setPicUrlRight2(topList.get(3).getUrl());
            tjp.setPicUrlRight3(topList.get(4).getUrl());

            tjp.setLinkUrlLeft(topList.get(0).getLinkUrl());
            tjp.setLinkUrlRight1(topList.get(2).getLinkUrl());
            tjp.setLinkUrlRight2(topList.get(3).getLinkUrl());
            tjp.setLinkUrlRight3(topList.get(4).getLinkUrl());

            tjp.setItemTypeLeft(topList.get(0).getItemType());
            tjp.setItemTypeRight0(topList.get(1).getItemType());
            tjp.setItemTypeRight1(topList.get(2).getItemType());
            tjp.setItemTypeRight2(topList.get(3).getItemType());
            tjp.setItemTypeRight3(topList.get(4).getItemType());

            tjp.setNews(tnjpList);
            tjp.setRise(trjpList);
            tjp.setGoods(tgjpList);
            tjp.setTv(tvjpList);
            tjp.setEvent(tejpList);
            tjp.setComment(tcjpList);
            tjp.setFreePaper(freePaperList);

            tjpList.add(tjp);
        }

        jsonObject.setResultList(tjpList);
        return jsonObject;
    }

    //TOP
    @RequestMapping(value = "/api/top/app/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getTopPicForApp() {
        List<Top> topList = topService.getTopPic();
        List<TopAppJsonParse> tjpList = new ArrayList<>();
        if (topList != null && topList.size() > 0) {
            TopAppJsonParse tjp = new TopAppJsonParse();

            tjp.setItemTypeLeft(topList.get(0).getItemType());
            tjp.setItemTypeRight0(topList.get(1).getItemType());
            tjp.setItemTypeRight1(topList.get(2).getItemType());
            tjp.setItemTypeRight2(topList.get(3).getItemType());
            tjp.setItemTypeRight3(topList.get(4).getItemType());

            tjp.setLinkUrlLeft(topList.get(0).getLinkUrl());
            tjp.setLinkUrlRight1(topList.get(1).getLinkUrl());
            tjp.setLinkUrlRight1(topList.get(2).getLinkUrl());
            tjp.setLinkUrlRight2(topList.get(3).getLinkUrl());
            tjp.setLinkUrlRight3(topList.get(4).getLinkUrl());

            tjp.setPicUrlLeft(topList.get(0).getUrl());
            tjp.setVideoUrl(topList.get(1).getUrl());
            tjp.setPicUrlRight1(topList.get(2).getUrl());
            tjp.setPicUrlRight2(topList.get(3).getUrl());
            tjp.setPicUrlRight3(topList.get(4).getUrl());
            tjpList.add(tjp);
        }
        jsonObject.setResultList(tjpList);
        return jsonObject;
    }

}
