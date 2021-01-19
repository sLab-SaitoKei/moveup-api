package jp.co.vermore.controller;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.JsonObject;
import jp.co.vermore.common.mvc.APIException;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.entity.*;
import jp.co.vermore.jsonparse.NewsDetailJsonParse;
import jp.co.vermore.jsonparse.NewsJsonParse;
import jp.co.vermore.jsonparse.Team2020JsonParse;
import jp.co.vermore.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * TVController
 * Created by wangnannan.
 * <p>
 * DateTime: 2018/06/08 14:34
 * Copyright: sLab, Corp
 */
@Controller
public class TvController extends BaseController {

    @Autowired
    private TvService tvService;

    @Autowired
    private TvDetailService tvDetailService;

    @Autowired
    private TvVideoService tvVideoService;

    @Autowired
    private AuthService authService;

    @Autowired
    private FavDetailService favDetailService;

    @RequestMapping(value = "/api/tvList/{type}/{limit}/{offset}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getTvList(@PathVariable int type, @PathVariable int limit, @PathVariable int offset, HttpServletRequest hsr) throws APIException {
        Long userId = 0L;
        try {
             userId = authService.getUserId(hsr);
        }catch (APIException ex){}
        List<Tv> tv = tvService.getTvListAll(type,limit, offset);
        List<Tv> countlist = tvService.getTvListAllByType(type);

        List<Map<String, Object>> tvList = new ArrayList<Map<String, Object>>();
        if(tv.size() != 0){
            for (Tv tvMap : tv){
                Map<String, Object> map = new HashMap<>();
                map.put("id", tvMap.getId());
                map.put("uuid", tvMap.getUuid());
                map.put("title", tvMap.getTitle());
                map.put("office", tvMap.getOffice());
                map.put("url", tvMap.getUrl());
                map.put("tvType", tvMap.getTvType());
                if (tvMap.getTvType() == 1) {
                    map.put("status", "SPECIAL");
                    map.put("color", "background-color:#F40104");
                }else if (tvMap.getTvType() == 2) {
                    map.put("status", "KIDS");
                    map.put("color", "background-color: #F68028");
                }else if (tvMap.getTvType() == 3) {
                    map.put("status", "GIRLS");
                    map.put("color", "background-color: #F41992");
                }else if (tvMap.getTvType() == 4) {
                    map.put("status", "LIFE");
                    map.put("color", "background-color: #2F9A51");
                }else if (tvMap.getTvType() == 5) {
                    map.put("status", "OKAYAMA");
                    map.put("color", "background-color: #174981");
                }
                map.put("favorite", false);
                map.put("date", DateUtil.dateToStringyyyy_MM_dd(tvMap.getDate()));
                TvDetail tvDetail = tvDetailService.getTvDetailById(tvMap.getId());
                if(userId>0 && tvDetail != null){
                    List<FavDetail> favDetail = favDetailService.getFavDetailAll(userId, Constant.FAV_TYPE.TV);
                    if(favDetail.size() >0 ) {
                        for (FavDetail  fd: favDetail){
                            if(tvDetail.getId().equals(fd.getShopId())){
                                map.put("favorite", true);
                            }
                        }
                    }
                }
                tvList.add(map);
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("tvList", tvList);
        map.put("tvListCount", countlist.size());
        jsonObject.setResultList(map);
        return jsonObject;
    }

    @RequestMapping(value = "/api/tv/detail/{uuid}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getTvDetailList(@PathVariable String uuid, HttpServletRequest hsr) throws APIException {
        long userId =0L;
        Tv tv = tvService.getTvByuuid(uuid);
        TvDetail tvDetail = tvDetailService.getTvDetailById(tv.getId());
        List<TvVideo> tvVideo = tvVideoService.getTvVideoById(tvDetail.getId());

        Map<String, Object> map = new HashMap<>();
        map.put("id",tvDetail.getId());
        map.put("type",tvDetail.getType());
        map.put("date",DateUtil.dateToStringyyyy_MM_dd(tvDetail.getDate()));
        map.put("title",tvDetail.getTitle());
        map.put("detail",tvDetail.getDetail());

        for(TvVideo video : tvVideo){
            if(video.getCategory()==Constant.TV_VIDEO_TYPE.YOUTUBE){
                if(video.getLastUrl().equals("")){
                    map.put("YouTubeVideoUrl",video.getFirstUrl());
                    map.put("AWSVideoUrl",null);
                }else{
                    map.put("YouTubeVideoUrl",video.getLastUrl());
                }
                map.put("picUrl",video.getPicUrl());
            }else if(video.getCategory()==Constant.TV_VIDEO_TYPE.AWS){
                if(video.getLastUrl().equals("")){
                    map.put("AWSVideoUrl",video.getFirstUrl());
                    map.put("YouTubeVideoUrl",null);
                }else{
                    map.put("AWSVideoUrl",video.getLastUrl());
                }
                map.put("picUrl",video.getPicUrl());
            }
        }
        map.put("favorite", false);
        if(tvDetail.getType() == 1){
            userId = authService.getUserId(hsr);
            List<FavDetail> favDetail = favDetailService.getFavDetailAll(userId, Constant.FAV_TYPE.TV);
            if(favDetail.size() >0 ) {
                for (FavDetail  fd: favDetail){
                    if(tvDetail.getId().equals(fd.getShopId())){
                        map.put("favorite", true);
                    }
                }
            }
        }

        jsonObject.setResultList(map);
        return jsonObject;
    }
}
