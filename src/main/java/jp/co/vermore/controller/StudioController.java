package jp.co.vermore.controller;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.JsonObject;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.entity.News;
import jp.co.vermore.entity.NewsDetail;
import jp.co.vermore.entity.Pic;
import jp.co.vermore.entity.StudioGallery;
import jp.co.vermore.service.NewsService;
import jp.co.vermore.service.PicService;
import jp.co.vermore.service.StudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Team2020Controller
 * Created by wangnannan.
 * <p>
 * DateTime: 2018/05/18 16:50
 * Copyright: sLab, Corp
 */
@Controller
public class StudioController extends BaseController {

    @Autowired
    private StudioService studioService;

    @Autowired
    private PicService picService;

    @Autowired
    private NewsService newsService;

    @RequestMapping(value = "/api/studioNews/list/{sortScore}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getStudioTopList(@PathVariable int sortScore) {
        //   sortScore = 1  TOPに表示(0:不明, 1:あり, 2:なし)  1
        List<News> studioNews = studioService.getStudioNewsList(Constant.NEWS_TYPE.STUDIO_NEWS, sortScore);
        List<StudioGallery> studioGallery = studioService.getStudioGalleryList();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> galleryList = new ArrayList<Map<String, Object>>();
        if(studioNews!=null){
            for(News studioNewsList : studioNews){
                Map<String, Object> map = new HashMap<>();
                map.put("id", studioNewsList.getId());
                map.put("uuid",studioNewsList.getUuid());
                map.put("date", DateUtil.dateToStringyyyymmdd(studioNewsList.getDate()));
                map.put("sortScore",studioNewsList.getSortScore());
                map.put("type", studioNewsList.getType());
                map.put("title",studioNewsList.getTitle());
                map.put("excerpt",studioNewsList.getExcerpt());
                map.put("publishStart", DateUtil.dateToStringyyyymmdd(studioNewsList.getPublishStart()));
                map.put("publishEnd", DateUtil.dateToStringyyyymmdd(studioNewsList.getPublishEnd()));
                Pic picUrl = picService.getStudioPicUrl(studioNewsList.getId(), Constant.EVENT_PIC_TYPE.STUDIO_NEWS);
                map.put("picUrl", picUrl);
                list.add(map);
            }
        }
        if(studioGallery!=null){
            for(StudioGallery  photoGallery : studioGallery){
                Map<String, Object> map = new HashMap<>();
                map.put("id",photoGallery.getId());
                map.put("url",photoGallery.getUrl());
                map.put("type",photoGallery.getType());
                map.put("score",photoGallery.getScore());
//                Pic picUrl = picService.getStudioPicUrl(photoGallery.getId(), Constant.EVENT_PIC_TYPE.STUDIO_GALLERY);
//                map.put("picUrl", picUrl);
                galleryList.add(map);
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("studioNewsList", list);
        map.put("galleryList", galleryList);
        jsonObject.setResultList(map);
        return jsonObject;
    }

    @RequestMapping(value = "/api/studioNewsAll/list/{limit}/{offset}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getStudioListAll(@PathVariable int limit, @PathVariable int offset) {
        List<News>  studioNews = studioService.getStudioNewsListAll(Constant.NEWS_TYPE.STUDIO_NEWS, limit, offset);
        int studioNewsCount = studioService.getStudioCount();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if(studioNews!=null){
            for(News studioNewsList : studioNews){
                Map<String, Object> map = new HashMap<>();
                map.put("id", studioNewsList.getId());
                map.put("uuid",studioNewsList.getUuid());
                map.put("date", DateUtil.dateToStringyyyymmdd(studioNewsList.getDate()));
                map.put("sortScore",studioNewsList.getSortScore());
                map.put("type", studioNewsList.getType());
                map.put("title",studioNewsList.getTitle());
                map.put("excerpt",studioNewsList.getExcerpt());
                map.put("publishStart", DateUtil.dateToStringyyyymmdd(studioNewsList.getPublishStart()));
                map.put("publishEnd", DateUtil.dateToStringyyyymmdd(studioNewsList.getPublishEnd()));
                Pic picUrl = picService.getStudioPicUrl(studioNewsList.getId(), Constant.EVENT_PIC_TYPE.STUDIO_NEWS);
                map.put("picUrl", picUrl);
                list.add(map);
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("studioNewsListAll", list);
        map.put("count",studioNewsCount);
        jsonObject.setResultList(map);
        return jsonObject;
    }

    @RequestMapping(value = "/api/studioNewsDetail/{uuid}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getStudioListDetail(@PathVariable String uuid) {
        News news = newsService.getNewsByUuid(uuid);
        NewsDetail newsDetail = studioService.getNewsDetail(news.getId());
        Map<String, Object> mapDetail = new HashMap<>();
        if(newsDetail!=null){
            mapDetail.put("id", newsDetail.getId());
            mapDetail.put("newsId",newsDetail.getNewsId());
            mapDetail.put("date", DateUtil.dateToStringyyyymmdd(newsDetail.getDate()));
            mapDetail.put("detail",newsDetail.getDetail());
            mapDetail.put("type", newsDetail.getType());
            mapDetail.put("title",newsDetail.getTitle());
            mapDetail.put("publishStart", DateUtil.dateToStringyyyymmdd(newsDetail.getPublishStart()));
            mapDetail.put("publishEnd", DateUtil.dateToStringyyyymmdd(newsDetail.getPublishEnd()));
        }
        List<Pic> picUrl = picService.getstudioNewsDetailPicUrl(news.getId(), Constant.EVENT_PIC_TYPE.STUDIO_NEWS_DETAIL);
        mapDetail.put("picUrl", picUrl);

        Map<String, Object> map = new HashMap<>();
        map.put("studioNewsDetail", mapDetail);
        jsonObject.setResultList(map);
        return jsonObject;
    }

     @RequestMapping(value = "/api/studioGallery/", method = RequestMethod.GET)
     @ResponseBody
     public JsonObject getStudioGallery() {
         List<StudioGallery> galleryList = studioService.getStudioGalleryForAdmin();
         List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
         if(galleryList!=null){
             for(StudioGallery studioNewsList : galleryList) {
                 Map<String, Object> map = new HashMap<>();
                 map.put("id", studioNewsList.getId());
                 map.put("url", studioNewsList.getUrl());
                 map.put("score", studioNewsList.getScore());
                 map.put("type", studioNewsList.getType());
                 map.put("update_datetime", DateUtil.dateToStringyyyymmdd(studioNewsList.getUpdateDatetime()));
                 list.add(map);
             }
         }
         Map<String, Object> map = new HashMap<>();
         map.put("studioGallery", list);
         jsonObject.setResultList(map);
         return jsonObject;
     }
}
