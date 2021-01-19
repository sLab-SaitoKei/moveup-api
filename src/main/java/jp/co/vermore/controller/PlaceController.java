package jp.co.vermore.controller;

import java.util.*;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.mvc.APIException;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.entity.*;
import jp.co.vermore.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jp.co.vermore.common.JsonObject;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.jsonparse.PlaceJsonParse;
import jp.co.vermore.jsonparse.PlaceDetailJsonParse;

import javax.servlet.http.HttpServletRequest;


/**
 * PlaceController
 * Created by Wangyan.
 * <p>
 * DateTime: 2018/03/20 16:43
 * Copyright: sLab, Corp
 */

@Controller
public class PlaceController extends BaseController {

    @Autowired
    private PlaceService placeService;

    @Autowired
    private TagDetailService tagDetailService;

    @Autowired
    private PicService picService;

    @Autowired
    private FavDetailService favDetailService;

    @Autowired
    private AuthService authService;

    @Autowired
    private RandomShopService randomShopService;

    @Autowired
    private RecruitService recruitService;

    @Autowired
    private NotificationService notificationService;

    //Place list
    @RequestMapping(value = "/api/place/list/{limit}/{offset}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getPlaceList(@PathVariable int limit, @PathVariable int offset) {

        List<String> randomOneUuidList = new ArrayList<String>();
        List<String> randomTwoUuidList = new ArrayList<String>();
        List<Place> list = new ArrayList<Place>();
        int minute = DateUtil.getMinute(new Date(System.currentTimeMillis()));

        if(minute>5 && minute <55){
            List<RandomShopOne> randomOnelist = randomShopService.getShopRandomOne(7, limit, offset);
            if(randomOnelist.size()>0){
                for(RandomShopOne rso:randomOnelist){
                    randomOneUuidList.add(rso.getUuid());
                }
            }

            if(randomOneUuidList.size()>0){
                list = placeService.getPlaceByUuidList(randomOneUuidList);
            }
        }else{
            List<RandomShopTwo> randomTwolist = randomShopService.getShopRandomTwo(7, limit, offset);
            if(randomTwolist.size()>0){
                for(RandomShopTwo rso:randomTwolist){
                    randomTwoUuidList.add(rso.getUuid());
                }
            }

            if(randomTwoUuidList.size()>0){
                list = placeService.getPlaceByUuidList(randomTwoUuidList);
            }
        }


        List<Place> placeList = placeService.getPlaceAll(limit, offset, 0);
        List<Place> count = placeService.getPlaceAllCount(0);
        List<PlaceJsonParse> pjpList = new ArrayList<>();
        for (Place pd : placeList) {
            PlaceJsonParse pjp = new PlaceJsonParse();
            pjp.setUuid(pd.getUuid());
            pjp.setName(pd.getName());
            pjp.setIntroduce(pd.getIntroduce());
            pjp.setArea(pd.getArea());
            pjp.setAddress(pd.getAddress());
            pjp.setFlicUrl(pd.getFlicUrl());
            pjp.setPicUrl(pd.getPicUrl());
            pjp.setPlaceCount(count.size());
            pjp.setCoordinate1(Double.parseDouble(pd.getCoordinate1()));
            pjp.setCoordinate2(Double.parseDouble(pd.getCoordinate2()));
            pjpList.add(pjp);
        }
        jsonObject.setResultList(pjpList);
        return jsonObject;
    }

    //Place list by area
    @RequestMapping(value = "/api/place/list/area/{area}/{limit}/{offset}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getPlaceListByArea(@PathVariable int area, @PathVariable int limit, @PathVariable int offset) {

        List<String> randomOneUuidList = new ArrayList<String>();
        List<String> randomTwoUuidList = new ArrayList<String>();
        List<Place> list = new ArrayList<Place>();
        int minute = DateUtil.getMinute(new Date(System.currentTimeMillis()));

        if(minute>5 && minute <55){
            List<RandomShopOne> randomOnelist = randomShopService.getShopRandomOneForPlace(7,area, limit, offset);
            if(randomOnelist.size()>0){
                for(RandomShopOne rso:randomOnelist){
                    randomOneUuidList.add(rso.getUuid());
                }
            }

            if(randomOneUuidList.size()>0){
                list = placeService.getPlaceByUuidList(randomOneUuidList);
            }
        }else{
            List<RandomShopTwo> randomTwolist = randomShopService.getShopRandomTwoForPlace(7, area, limit, offset);
            if(randomTwolist.size()>0){
                for(RandomShopTwo rso:randomTwolist){
                    randomTwoUuidList.add(rso.getUuid());
                }
            }

            if(randomTwoUuidList.size()>0){
                list = placeService.getPlaceByUuidList(randomTwoUuidList);
            }
        }

//        List<Place> placeList = placeService.getPlaceAllByArea(area, limit, offset);
        Collections.shuffle(list);
        List<Place> placeList2 = placeService.getPlaceAllCount(area);
        List<String> location = placeService.getPlaceDetail();
        List<TagDetail> tagDetail = tagDetailService.getTagDetailByModuleAndType(Constant.TAG_MODULE_TYPE.FOOD, Constant.SHOP_TAG.AREA);
        List<Map<String, Object>> areaList = new ArrayList<>();
        if (tagDetail.size() > 0) {
            if (location.size() > 0) {
                for (int i = 0; i < location.size(); i++) {
                    for (TagDetail td : tagDetail) {
                        Map<String, Object> map = new HashMap<>();
                        if (location.get(i).equals(td.getDesc())) {
                            map.put("area",td.getContent());
                            map.put("location",td.getDesc());
                            areaList.add(map);
                        }
                    }
                }
            }
        }

        List<PlaceJsonParse> pjpList = new ArrayList<>();

        for (Place pd : list) {
            PlaceJsonParse pjp = new PlaceJsonParse();
            pjp.setUuid(pd.getUuid());
            pjp.setName(pd.getName());
            pjp.setIntroduce(pd.getIntroduce());
            pjp.setArea(pd.getArea());
            pjp.setAddress(pd.getAddress());
            pjp.setFlicUrl(pd.getFlicUrl());
            pjp.setPicUrl(pd.getPicUrl());
            pjpList.add(pjp);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("placeList", pjpList);
        map.put("count", placeList2.size());
        map.put("areaList", areaList);
        jsonObject.setResultList(map);
        return jsonObject;
    }


    //Place detail
    @RequestMapping(value = "/api/place/{uuid}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getPlaceListByArea(@PathVariable String uuid, HttpServletRequest hsr) throws APIException {
        Pic pic = new Pic();
        List<Pic> picL2 = new ArrayList<>();
        List<FavDetail> favDetail = new ArrayList<FavDetail>();
        List<PlaceDetailJsonParse> pdjpList = new ArrayList<>();
        PlaceDetailJsonParse pdjp = new PlaceDetailJsonParse();

        Long userId = 0L;
        try {
            userId = authService.getUserId(hsr);
        }catch (APIException ex){}
        if(userId>0){
            favDetail = favDetailService.getFavDetailAll(userId, Constant.FAV_TYPE.PLACE);
        }

        Place place = placeService.selectByUuid(uuid);
        PlaceDetail placeDetail = placeService.selectByPlaceListId(place.getId());
        String uuid2 = recruitService.findUuidByTypeAndId(Constant.SHOP_TAG.PLACE,place.getId());

        pic.setItemType(Constant.EVENT_PIC_TYPE.PLACE);
        Long id = place.getId();

        List<Pic> picList = picService.selectPicById(id, pic.getItemType());

        if(picList.size()>=1){
            pdjp.setPicUrl7(picList.get(0).getPicUrl());
        }
        if(picList.size()>=2){
            pdjp.setPicUrl8(picList.get(1).getPicUrl());
        }
        if(picList.size()>=3){
            pdjp.setPicUrl9(picList.get(2).getPicUrl());
        }
        if(picList.size()>=4){
            pdjp.setPicUrl10(picList.get(3).getPicUrl());
        }
        if(picList.size()>=5){
            pdjp.setPicUrl11(picList.get(4).getPicUrl());
        }
        if(picList.size()>=6){
            pdjp.setPicUrl12(picList.get(5).getPicUrl());
        }


        List<Place> findByUuidArea = new ArrayList<Place>();
        List<String> uuidList = new ArrayList<String>();
        List<Place> placeList2 = new ArrayList<>();
        int minute = DateUtil.getMinute(new Date(System.currentTimeMillis()));

        if(minute>5 && minute <55){
            uuidList = randomShopService.getRandomOneUuidListByUuidFor(uuid,Constant.SHOP_TAG.PLACE);
        }else{
            uuidList = randomShopService.getRandomTwoUuidListByUuidFor(uuid,Constant.SHOP_TAG.PLACE);
        }

          Collections.shuffle(uuidList);
          findByUuidArea = placeService.findPlaceByUuidList(uuidList);
//        findByUuidArea = placeService.findByUuidArea( uuid);

        for (Place placee : findByUuidArea) {
            Place place2 = new Place();
            place2.setId(placee.getId());
            place2.setUuid(placee.getUuid());
            place2.setName(placee.getName());
            place2.setIntroduce(placee.getIntroduce());
            place2.setAddress(placee.getAddress());
            place2.setArea(placee.getArea());
            place2.setPicUrl(placee.getPicUrl());
            place2.setFlicUrl(placee.getFlicUrl());
            place2.setCreateDatetime(placee.getCreateDatetime());
            place2.setUpdateDatetime(placee.getUpdateDatetime());
            place2.setNote(placee.getNote());
            place2.setCoordinate1(placee.getCoordinate1());
            place2.setCoordinate2(placee.getCoordinate2());
            place2.setPublishEnd(placee.getPublishEnd());
            place2.setPublishStart(placee.getPublishStart());
            placeList2.add(place2);
        }

        pdjp.setPlaceListId(place.getId());
        pdjp.setPlaceList2(placeList2);
        pdjp.setPicList(picL2);
        pdjp.setUuid2(uuid2);
        pdjp.setName(placeDetail.getName());
        pdjp.setTel(placeDetail.getTel());
        pdjp.setAddress(placeDetail.getAddress());
        pdjp.setLocation(placeDetail.getLocation());
        pdjp.setStation(placeDetail.getStation());
        pdjp.setWalk(placeDetail.getWalk());
        pdjp.setTime(placeDetail.getTime());
        pdjp.setHoliday(placeDetail.getHoliday());
        pdjp.setPicUrl(placeDetail.getPicUrl());
        pdjp.setPicUrl1(placeDetail.getPicUrl1());
        pdjp.setPicUrl2(placeDetail.getPicUrl2());
        pdjp.setPicUrl3(placeDetail.getPicUrl3());
        pdjp.setPicUrl4(placeDetail.getPicUrl4());
        pdjp.setFlicUrl(placeDetail.getFlicUrl());
        pdjp.setVideoUrl(placeDetail.getVideoUrl());
        pdjp.setTitle1(placeDetail.getTitle());
        pdjp.setDesc1(placeDetail.getDesc());
        pdjp.setCoordinate1(Double.parseDouble(placeDetail.getCoordinate1()));
        pdjp.setCoordinate2(Double.parseDouble(placeDetail.getCoordinate2()));


        List<Place> placeList = placeService.getPlaceAllByArea(place.getArea(), 10, 0);
        List<PlaceJsonParse> pjpList = new ArrayList<>();

        for (Place pd : placeList) {
            if (place.getId().equals(pd.getId())) {
                continue;
            } else {
                PlaceJsonParse pjp = new PlaceJsonParse();
                pjp.setUuid(pd.getUuid());
                pjp.setName(pd.getName());
                pjp.setIntroduce(pd.getIntroduce());
                pjp.setArea(pd.getArea());
                pjp.setAddress(pd.getAddress());
                pjp.setFlicUrl(pd.getFlicUrl());
                pjp.setPicUrl(pd.getPicUrl());
                pjp.setCoordinate1(Double.parseDouble(pd.getCoordinate1()));
                pjp.setCoordinate2(Double.parseDouble(pd.getCoordinate2()));
                pjpList.add(pjp);
            }
        }

        //fav
        if(userId != 0 &&userId != null && place.getId() != null){
            FavDetail favDetail2 = favDetailService.getFavorite( userId, place.getId(), Constant.FAV_TYPE.PLACE);
            if(favDetail2 != null){
                UserSetting us = notificationService.getUserSettingBySettingKeyAndSettingValueAndByItemId(userId,Constant.USER_SETTING.NOW4,Constant.USER_SETTING.OFF,place.getId());
                if(us == null){
                    pdjp.setUserSetting("0");
                    pdjp.setFavType(Constant.FAV_TYPE.PLACE);
                }else {
                    pdjp.setUserSetting("1");
                    pdjp.setFavType(Constant.FAV_TYPE.PLACE);
                }
            }else{
                pdjp.setUserSetting("2");
                pdjp.setFavType(Constant.FAV_TYPE.PLACE);
            }
        }

        pdjp.setFavorite(false);
        if (favDetail.size() > 0) {
            for (FavDetail fd : favDetail) {
                if (place.getId().equals(fd.getShopId()) ) {
                    pdjp.setFavorite(true);
                }
            }
        }

        pdjp.setPlaceList(pjpList);
        pdjpList.add(pdjp);

        jsonObject.setResultList(pdjpList);
        return jsonObject;
    }
}
