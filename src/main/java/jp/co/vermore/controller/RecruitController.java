package jp.co.vermore.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.JsonStatus;
import jp.co.vermore.common.mvc.APIException;
import jp.co.vermore.entity.*;
import jp.co.vermore.jsonparse.ResultListJsonParse;
import jp.co.vermore.service.*;
import jp.co.vermore.validation.RecruitSearchParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jp.co.vermore.common.JsonObject;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.jsonparse.RecruitDetailJsonParse;
import jp.co.vermore.jsonparse.RecruitJsonParse;
import jp.co.vermore.validation.RecruitListParams;

/**
 * NewsController
 * Created by wubin.
 * <p>
 * DateTime: 2018/03/03 11:13
 * Copyright: sLab, Corp
 */

@Controller
@Validated
@Component
public class RecruitController extends BaseController {

    @Autowired
    private RecruitService recruitService;

    @Autowired
    private TagDetailService tagDetailService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopTagService shopTagService;

    @Autowired
    private AuthService authService;

    @Autowired
    private FavDetailService favDetailService;

    @Autowired
    private PicService picService;

    @Autowired
    private PlaceService placeService;

    @Autowired
    private RandomShopService randomShopService;

    @Autowired
    private NotificationService notificationService;


    @RequestMapping(value = "/api/recruit/list/{limit}/{offset}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getRecruitList(@Valid RecruitListParams params, BindingResult result, HttpServletRequest hsr) throws APIException {
//        List<Recruit> list = recruitService.getRecruitAll(params.getLimit(), params.getOffset());

        List<String> randomOneUuidList = new ArrayList<String>();
        List<String> randomTwoUuidList = new ArrayList<String>();
        List<Recruit> list = new ArrayList<Recruit>();
        int minute = DateUtil.getMinute(new Date(System.currentTimeMillis()));

        if(minute>5 && minute <55){
            List<RandomShopOne> randomOnelist = randomShopService.getShopRandomOne(2, params.getLimit(), params.getOffset());
            if(randomOnelist.size()>0){
                for(RandomShopOne rso:randomOnelist){
                    randomOneUuidList.add(rso.getUuid());
                }
            }

            if(randomOneUuidList.size()>0){
                list = recruitService.getRecruitByUuidList(randomOneUuidList);
            }
        }else{
            List<RandomShopTwo> randomTwolist = randomShopService.getShopRandomTwo(2, params.getLimit(), params.getOffset());
            if(randomTwolist.size()>0){
                for(RandomShopTwo rso:randomTwolist){
                    randomTwoUuidList.add(rso.getUuid());
                }
            }

            if(randomTwoUuidList.size()>0){
                list = recruitService.getRecruitByUuidList(randomTwoUuidList);
            }
        }

        List<FavDetail> favDetail = new ArrayList<FavDetail>();
        long userId = 0L;
        try {
            userId = authService.getUserId(hsr);
        }catch (APIException ex){}
        if(userId>0){
            favDetail = favDetailService.getFavDetailAll(userId, Constant.FAV_TYPE.RECRUIT);
        }

        List<RecruitJsonParse> ejpList = new ArrayList<>();
        List<Map<String, Object>> contentList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> areaList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> modeList = new ArrayList<Map<String, Object>>();
//        List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> timeList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> careerList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> workPeriodList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> workTimeList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> capacityList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> workWayList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> specialList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> environmentList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> treatmentNewList = new ArrayList<Map<String, Object>>();
        List<RecruitTag> recruitTagList = new ArrayList<RecruitTag>();
        List<TagDetail> tagDetail = new ArrayList<TagDetail>();

        List<Integer> modeTagList = new ArrayList<Integer>();
//        List<Integer> detailTagList = new ArrayList<Integer>();
        List<Integer> careerTagList = new ArrayList<Integer>();
        List<Integer> workPeriodTagList = new ArrayList<Integer>();
        List<Integer> workTimeTagList = new ArrayList<Integer>();
        List<Integer> capacityTagList = new ArrayList<Integer>();
        List<Integer> workWayTagList = new ArrayList<Integer>();
        List<Integer> specialTagList = new ArrayList<Integer>();
        List<Integer> environmentTagList = new ArrayList<Integer>();
        List<Integer> treatmentNewTagList = new ArrayList<Integer>();
        List<TagDetail> getByModeList = new ArrayList<TagDetail>();
        List<TagDetail> getByCareerList = new ArrayList<TagDetail>();
//        List<TagDetail> getByDetailList = new ArrayList<TagDetail>();
        List<TagDetail> getByWorkPeriodList = new ArrayList<TagDetail>();
        List<TagDetail> getByWorkTimeList = new ArrayList<TagDetail>();
        List<TagDetail> getByCapacityList = new ArrayList<TagDetail>();
        List<TagDetail> getByWorkWayList = new ArrayList<TagDetail>();
        List<TagDetail> getBySpecialList = new ArrayList<TagDetail>();
        List<TagDetail> getByEnvironmentList = new ArrayList<TagDetail>();
        List<TagDetail> getByTreatmentNewList = new ArrayList<TagDetail>();
        List<Integer> rtagDetalList = new ArrayList<Integer>();
        List<Integer> ftagDetalList = new ArrayList<Integer>();
        List<Integer> tagList = new ArrayList<Integer>();
        List<Long> idList = new ArrayList<Long>();
        String workingTimeStart = "";
        String workingTimeEnd = "";
        String modeStr = "";
        String detailStr = "";
        String careerStr = "";
        String workPeriodStr = "";
        String workTimeStr = "";
        String capacityStr = "";
        String workWayStr = "";
        String specialStr = "";
        String environmentStr = "";
        String treatmentNewStr = "";

        ftagDetalList.add(Constant.SHOP_TAG.AREA);
        ftagDetalList.add(Constant.SHOP_TAG.NIGHT_OPEN_TIME);
        rtagDetalList.add(Constant.RECRUIT_TAG_TYPE.MODE);
//        rtagDetalList.add(Constant.RECRUIT_TAG_TYPE.DETAIL);
        rtagDetalList.add(Constant.RECRUIT_TAG_TYPE.CAREER);
        rtagDetalList.add(Constant.RECRUIT_TAG_TYPE.WORK_PERIOD);
        rtagDetalList.add(Constant.RECRUIT_TAG_TYPE.WORK_TIME);
        rtagDetalList.add(Constant.RECRUIT_TAG_TYPE.CAPACITY);
        rtagDetalList.add(Constant.RECRUIT_TAG_TYPE.WORK_WAY);
        rtagDetalList.add(Constant.RECRUIT_TAG_TYPE.ALLOWANCE_SPECIAL);
        rtagDetalList.add(Constant.RECRUIT_TAG_TYPE.WORK_ENVIRONMENT);
        rtagDetalList.add(Constant.RECRUIT_TAG_TYPE.TREATMENT_NEW);

        tagList.add(Constant.RECRUIT_TAG_TYPE.MODE);
//        tagList.add(Constant.RECRUIT_TAG_TYPE.DETAIL);
        tagList.add(Constant.RECRUIT_TAG_TYPE.CAREER);
        tagList.add(Constant.RECRUIT_TAG_TYPE.WORK_PERIOD);
        tagList.add(Constant.RECRUIT_TAG_TYPE.WORK_TIME);
        tagList.add(Constant.RECRUIT_TAG_TYPE.CAPACITY);
        tagList.add(Constant.RECRUIT_TAG_TYPE.WORK_WAY);
        tagList.add(Constant.RECRUIT_TAG_TYPE.ALLOWANCE_SPECIAL);
        tagList.add(Constant.RECRUIT_TAG_TYPE.WORK_ENVIRONMENT);
        tagList.add(Constant.RECRUIT_TAG_TYPE.TREATMENT_NEW);
        tagList.add(Constant.RECRUIT_TAG_TYPE.WORKING_TIME_START);
        tagList.add(Constant.RECRUIT_TAG_TYPE.WORKING_TIME_END);

        List<TagDetail> recruitTagDetailList = tagDetailService.getTagDetailList(Constant.SHOP_TAG.RECRUIT, rtagDetalList);
        List<TagDetail> foodTagDetailList = tagDetailService.getTagDetailList(Constant.SHOP_TAG.FOOD, ftagDetalList);

        if(recruitTagDetailList != null){
            for(TagDetail td:recruitTagDetailList){
                tagDetail.add(td);
            }
        }
        if(foodTagDetailList != null){
            for(TagDetail td:foodTagDetailList){
                tagDetail.add(td);
            }
        }

        for(TagDetail t:tagDetail){
            if (t.getModuleType() == Constant.SHOP_TAG.FOOD && t.getTagType() == Constant.SHOP_TAG.AREA) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", t.getContent());
                map.put("desc", t.getDesc());
                areaList.add(map);
            }
            if (t.getModuleType() == Constant.SHOP_TAG.FOOD && t.getTagType() == Constant.SHOP_TAG.NIGHT_OPEN_TIME) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", t.getContent());
                map.put("desc", t.getDesc());
                timeList.add(map);
            }
            if (t.getModuleType() == Constant.SHOP_TAG.RECRUIT && t.getTagType() == Constant.RECRUIT_TAG_TYPE.MODE) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", t.getContent());
                map.put("desc", t.getDesc());
                modeList.add(map);
            }
//            if (t.getModuleType() == Constant.SHOP_TAG.RECRUIT && t.getTagType() == Constant.RECRUIT_TAG_TYPE.DETAIL) {
//                Map<String, Object> map = new HashMap<>();
//                map.put("content", t.getContent());
//                map.put("desc", t.getDesc());
//                detailList.add(map);
//            }
            if (t.getModuleType() == Constant.SHOP_TAG.RECRUIT && t.getTagType() == Constant.RECRUIT_TAG_TYPE.CAREER) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", t.getContent());
                map.put("desc", t.getDesc());
                careerList.add(map);
            }
            if (t.getModuleType() == Constant.SHOP_TAG.RECRUIT && t.getTagType() == Constant.RECRUIT_TAG_TYPE.WORK_PERIOD) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", t.getContent());
                map.put("desc", t.getDesc());
                workPeriodList.add(map);
            }
            if (t.getModuleType() == Constant.SHOP_TAG.RECRUIT && t.getTagType() == Constant.RECRUIT_TAG_TYPE.WORK_TIME) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", t.getContent());
                map.put("desc", t.getDesc());
                workTimeList.add(map);
            }
            if (t.getModuleType() == Constant.SHOP_TAG.RECRUIT && t.getTagType() == Constant.RECRUIT_TAG_TYPE.CAPACITY) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", t.getContent());
                map.put("desc", t.getDesc());
                capacityList.add(map);
            }
            if (t.getModuleType() == Constant.SHOP_TAG.RECRUIT && t.getTagType() == Constant.RECRUIT_TAG_TYPE.WORK_WAY) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", t.getContent());
                map.put("desc", t.getDesc());
                workWayList.add(map);
            }
            if (t.getModuleType() == Constant.SHOP_TAG.RECRUIT && t.getTagType() == Constant.RECRUIT_TAG_TYPE.ALLOWANCE_SPECIAL) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", t.getContent());
                map.put("desc", t.getDesc());
                specialList.add(map);
            }
            if (t.getModuleType() == Constant.SHOP_TAG.RECRUIT && t.getTagType() == Constant.RECRUIT_TAG_TYPE.WORK_ENVIRONMENT) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", t.getContent());
                map.put("desc", t.getDesc());
                environmentList.add(map);
            }
            if (t.getModuleType() == Constant.SHOP_TAG.RECRUIT && t.getTagType() == Constant.RECRUIT_TAG_TYPE.TREATMENT_NEW) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", t.getContent());
                map.put("desc", t.getDesc());
                treatmentNewList.add(map);
            }
        }

        Collections.shuffle(list);
        for(Recruit r:list){
            idList.add(r.getId());
        }

        if(idList != null && idList.size()> 0){
//            detailList= shopDetailService.getShopDetailByShopIdList(idList);
             recruitTagList = recruitService.getRecruitTagList(idList, tagList);
        }

        for(RecruitTag st : recruitTagList){
            if ( st.getTagType() == Constant.RECRUIT_TAG_TYPE.MODE) {
                if (st.getContent() != null) {
                    modeTagList.add(st.getContent());
                }
            }
//            if ( st.getTagType() == Constant.RECRUIT_TAG_TYPE.DETAIL) {
//                if (st.getContent() != null) {
//                    detailTagList.add(st.getContent());
//                }
//            }
            if ( st.getTagType() == Constant.RECRUIT_TAG_TYPE.CAREER) {
                if (st.getContent() != null) {
                    careerTagList.add(st.getContent());
                }
            }
            if ( st.getTagType() == Constant.RECRUIT_TAG_TYPE.WORK_PERIOD) {
                if (st.getContent() != null) {
                    workPeriodTagList.add(st.getContent());
                }
            }
            if ( st.getTagType() == Constant.RECRUIT_TAG_TYPE.WORK_TIME) {
                if (st.getContent() != null) {
                    workTimeTagList.add(st.getContent());
                }
            }
            if ( st.getTagType() == Constant.RECRUIT_TAG_TYPE.CAPACITY) {
                if (st.getContent() != null) {
                    capacityTagList.add(st.getContent());
                }
            }
            if ( st.getTagType() == Constant.RECRUIT_TAG_TYPE.WORK_WAY) {
                if (st.getContent() != null) {
                    workWayTagList.add(st.getContent());
                }
            }
            if ( st.getTagType() == Constant.RECRUIT_TAG_TYPE.ALLOWANCE_SPECIAL) {
                if (st.getContent() != null) {
                    specialTagList.add(st.getContent());
                }
            }
            if ( st.getTagType() == Constant.RECRUIT_TAG_TYPE.WORK_ENVIRONMENT) {
                if (st.getContent() != null) {
                    environmentTagList.add(st.getContent());
                }
            }
            if ( st.getTagType() == Constant.RECRUIT_TAG_TYPE.TREATMENT_NEW) {
                if (st.getContent() != null) {
                    treatmentNewTagList.add(st.getContent());
                }
            }
        }

        if(modeTagList != null && modeTagList.size()> 0){
            getByModeList = tagDetailService.getTagDetailListByContentForRecruit( Constant.RECRUIT_TAG_TYPE.MODE,modeTagList);
        }
//        if(detailTagList != null && detailTagList.size()> 0){
//            getByDetailList = tagDetailService.getTagDetailListByContent(Constant.SHOP_TAG.RECRUIT, Constant.RECRUIT_TAG_TYPE.DETAIL,detailTagList);
//        }
        if(careerTagList != null && careerTagList.size()> 0){
            getByCareerList = tagDetailService.getTagDetailListByContentForRecruit( Constant.RECRUIT_TAG_TYPE.CAREER,careerTagList);
        }
        if(workPeriodTagList != null && workPeriodTagList.size()> 0){
            getByWorkPeriodList = tagDetailService.getTagDetailListByContentForRecruit( Constant.RECRUIT_TAG_TYPE.WORK_PERIOD,workPeriodTagList);
        }
        if(workTimeTagList != null && workTimeTagList.size()> 0){
            getByWorkTimeList = tagDetailService.getTagDetailListByContentForRecruit(Constant.RECRUIT_TAG_TYPE.WORK_TIME,workTimeTagList);
        }
        if(capacityTagList != null && capacityTagList.size()> 0){
            getByCapacityList = tagDetailService.getTagDetailListByContentForRecruit( Constant.RECRUIT_TAG_TYPE.CAPACITY,capacityTagList);
        }
        if(workWayTagList != null && workWayTagList.size()> 0){
            getByWorkWayList = tagDetailService.getTagDetailListByContentForRecruit( Constant.RECRUIT_TAG_TYPE.WORK_WAY,workWayTagList);
        }
        if(specialTagList != null && specialTagList.size()> 0){
            getBySpecialList = tagDetailService.getTagDetailListByContentForRecruit( Constant.RECRUIT_TAG_TYPE.ALLOWANCE_SPECIAL,specialTagList);
        }
        if(environmentTagList != null && environmentTagList.size()> 0){
            getByEnvironmentList = tagDetailService.getTagDetailListByContentForRecruit( Constant.RECRUIT_TAG_TYPE.WORK_ENVIRONMENT,environmentTagList);
        }
        if(treatmentNewTagList != null && treatmentNewTagList.size()> 0){
            getByTreatmentNewList = tagDetailService.getTagDetailListByContentForRecruit( Constant.RECRUIT_TAG_TYPE.TREATMENT_NEW,treatmentNewTagList);
        }


        for (Recruit rt : list) {
            List<String> modes = new ArrayList<String>();
            List<String> details = new ArrayList<String>();
            List<String> career = new ArrayList<String>();
            List<String> workPeriod = new ArrayList<String>();
            List<String> workTime = new ArrayList<String>();
            List<String> capacity = new ArrayList<String>();
            List<String> workWay = new ArrayList<String>();
            List<String> special = new ArrayList<String>();
            List<String> environment = new ArrayList<String>();
            List<String> treatmentNew = new ArrayList<String>();
            for (RecruitTag st : recruitTagList) {

                if(rt.getId().equals(st.getRecruitId()) && st.getTagType() == Constant.RECRUIT_TAG_TYPE.WORKING_TIME_START) {
                    if (st.getContent() != null && st.getContent() != 0) {
                        workingTimeStart = DateUtil.unixTimeStringTime(st.getContent());
                    }else {
                        workingTimeStart ="";
                    }
                }
                if(rt.getId().equals(st.getRecruitId()) && st.getTagType() == Constant.RECRUIT_TAG_TYPE.WORKING_TIME_END){
                    if(st.getContent() != null && st.getContent() != 0){
                        workingTimeEnd =DateUtil.unixTimeStringTime(st.getContent());
                    }else {
                        workingTimeEnd ="";
                    }
                }

                if(rt.getId().equals(st.getRecruitId()) && st.getTagType() == Constant.RECRUIT_TAG_TYPE.MODE){
                    for(TagDetail td:getByModeList){
                        if(td.getContent() == st.getContent()){
                            modeStr = td.getDesc();
                            modes.add(modeStr);
                        }
                    }
                }

//                if(rt.getId() == st.getRecruitId() && st.getTagType() == Constant.RECRUIT_TAG_TYPE.DETAIL){
//                    for(TagDetail td:getByDetailList){
//                        if(td.getContent() == st.getContent()){
//                            detailStr = td.getDesc();
//                            details.add(detailStr);
//                        }
//                    }
//                }

                if(rt.getId().equals(st.getRecruitId()) && st.getTagType() == Constant.RECRUIT_TAG_TYPE.CAREER){
                    for(TagDetail td:getByCareerList){
                        if(td.getContent() == st.getContent()){
                            careerStr = td.getDesc();
                            career.add(careerStr);
                        }
                    }
                }

                if(rt.getId().equals(st.getRecruitId()) && st.getTagType() == Constant.RECRUIT_TAG_TYPE.WORK_PERIOD){
                    for(TagDetail td:getByWorkPeriodList){
                        if(td.getContent() == st.getContent()){
                            workPeriodStr = td.getDesc();
                            workPeriod.add(workPeriodStr);
                        }
                    }
                }

                if(rt.getId().equals(st.getRecruitId()) && st.getTagType() == Constant.RECRUIT_TAG_TYPE.WORK_TIME){
                    for(TagDetail td:getByWorkTimeList){
                        if(td.getContent() == st.getContent()){
                            workTimeStr = td.getDesc();
                            workTime.add(workTimeStr);
                        }
                    }
                }

                if(rt.getId().equals(st.getRecruitId()) && st.getTagType() == Constant.RECRUIT_TAG_TYPE.CAPACITY){
                    for(TagDetail td:getByCapacityList){
                        if(td.getContent() == st.getContent()){
                            capacityStr = td.getDesc();
                            capacity.add(capacityStr);
                        }
                    }
                }

                if(rt.getId().equals(st.getRecruitId()) && st.getTagType() == Constant.RECRUIT_TAG_TYPE.WORK_WAY){
                    for(TagDetail td:getByWorkWayList){
                        if(td.getContent() == st.getContent()){
                            workWayStr = td.getDesc();
                            workWay.add(workWayStr);
                        }
                    }
                }

                if(rt.getId().equals(st.getRecruitId()) && st.getTagType() == Constant.RECRUIT_TAG_TYPE.ALLOWANCE_SPECIAL){
                    for(TagDetail td:getBySpecialList){
                        if(td.getContent() == st.getContent()){
                            specialStr = td.getDesc();
                            special.add(specialStr);
                        }
                    }
                }

                if(rt.getId().equals(st.getRecruitId()) && st.getTagType() == Constant.RECRUIT_TAG_TYPE.WORK_ENVIRONMENT){
                    for(TagDetail td:getByEnvironmentList){
                        if(td.getContent() == st.getContent()){
                            environmentStr = td.getDesc();
                            environment.add(environmentStr);
                        }
                    }
                }

                if(rt.getId().equals(st.getRecruitId()) && st.getTagType() == Constant.RECRUIT_TAG_TYPE.TREATMENT_NEW){
                    for(TagDetail td:getByTreatmentNewList){
                        if(td.getContent() == st.getContent()){
                            treatmentNewStr = td.getDesc();
                            treatmentNew.add(treatmentNewStr);
                        }
                    }
                }
            }

            Map<String, Object> map = new HashMap<>();
            map.put("id", rt.getId());
            map.put("uuid", rt.getUuid());
            map.put("shopId", rt.getShopId());
            map.put("shopName", rt.getCompanyName());
            map.put("recruitName", rt.getRecruitName());
            map.put("title", rt.getTitle1());
            map.put("picUrl1",rt.getPicUrl1());
            map.put("address", rt.getAddress());
            map.put("salary", rt.getSalary());
            map.put("station", rt.getStation());
            map.put("modes", modes);
            map.put("details", details);
            map.put("career", career);
            map.put("workPeriod", workPeriod);
            map.put("workTime", workTime);
            map.put("capacity", capacity);
            map.put("workWay", workWay);
            map.put("special", special);
            map.put("environment", environment);
            map.put("treatmentNew", treatmentNew);
            map.put("workingTime", rt.getWorkingTime());
            map.put("workingTimeStart", workingTimeStart);
            map.put("workingTimeEnd", workingTimeEnd);
            map.put("favorite",false);
            if(favDetail.size() >0 ) {
                for (FavDetail  fd: favDetail){
                    if(rt.getId().equals(fd.getShopId())){
                        map.put("favorite",true);
                    }
                }
            }
            contentList.add(map);
        }

        int count = recruitService.getRecruitDetailCount();
        ResultListJsonParse jsonParse = new ResultListJsonParse();
        jsonParse.setAreaList(areaList);
        jsonParse.setModeList(modeList);
        jsonParse.setTimeList(timeList);
        jsonParse.setCareerList(careerList);
        jsonParse.setWorkPeriodList(workPeriodList);
        jsonParse.setWorkTimeList(workTimeList);
        jsonParse.setCapacityList(capacityList);
        jsonParse.setWorkWayList(workWayList);
        jsonParse.setSpecialList(specialList);
        jsonParse.setEnvironmentList(environmentList);
        jsonParse.setTreatmentNewList(treatmentNewList);
        jsonParse.setData(contentList);
        jsonParse.setCount(count);
        jsonObject.setResultList(jsonParse);
        return jsonObject;
    }

    @RequestMapping(value = "/api/recruit/{uuid}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getRecruitDetail(@PathVariable String uuid, HttpServletRequest hsr) throws APIException, APIException {
        Recruit recruit = recruitService.getRecruitByUuid(uuid);
        String headerUuid = hsr.getHeader(Constant.SESSION.UUID);
        List<FavDetail> favDetail = new ArrayList<FavDetail>();
        Long userId = 0L;
        try {
            userId = authService.getUserId(hsr);
        }catch (APIException ex){}
        if(userId>0){
            favDetail = favDetailService.getFavDetailAll(userId, Constant.FAV_TYPE.RECRUIT);
        }

        if (recruit == null) {
            logger.warn("Recruit detail not find recruit_uuid=" + uuid);
            throw new APIException(JsonStatus.DATA_NOT_FOUND);
        }
        Pic pic = new Pic();
        pic.setItemType(Constant.EVENT_PIC_TYPE.RECRUIT);
        Long id = recruit.getId();
        int shopType = recruit.getShopType();
        List<RecruitDetailJsonParse> ejpList = new ArrayList<>();
        RecruitDetail recruitDetail = recruitService.getRecruitDetailByRecruit(id);
        List<String> modeList = recruitService.getDescByRecruit(id, Constant.RECRUIT_TAG_TYPE.MODE);
//        List<String> detailList = recruitService.getDescByRecruit(id, Constant.RECRUIT_TAG_TYPE.DETAIL);
//        List<String> featureList = recruitService.getDescByRecruit(id, Constant.RECRUIT_TAG_TYPE.FEATURE);
        List<String> treatmentList = recruitService.getDescByRecruit(id, Constant.RECRUIT_TAG_TYPE.TREATMENT);
        List<Integer> workingTimeStart = recruitService.getContent(id, Constant.RECRUIT_TAG_TYPE.WORKING_TIME_START);
        List<Integer> workingTimeEnd = recruitService.getContent(id, Constant.RECRUIT_TAG_TYPE.WORKING_TIME_END);
        List<String> workPeriodList = recruitService.getDescByRecruit(id, Constant.RECRUIT_TAG_TYPE.WORK_PERIOD);
        List<String> workTimeList = recruitService.getDescByRecruit(id, Constant.RECRUIT_TAG_TYPE.WORK_TIME);
        List<String> capacityList = recruitService.getDescByRecruit(id, Constant.RECRUIT_TAG_TYPE.CAPACITY);
        List<String> workWayList = recruitService.getDescByRecruit(id, Constant.RECRUIT_TAG_TYPE.WORK_WAY);
        List<String> specialList = recruitService.getDescByRecruit(id, Constant.RECRUIT_TAG_TYPE.ALLOWANCE_SPECIAL);
        List<String> environmentList = recruitService.getDescByRecruit(id, Constant.RECRUIT_TAG_TYPE.WORK_ENVIRONMENT);
        List<String> treatmentNewList = recruitService.getDescByRecruit(id, Constant.RECRUIT_TAG_TYPE.TREATMENT_NEW);

        List<Pic> picList = picService.selectPicById(id,pic.getItemType());
        List<Pic> picL2 = new ArrayList<>();
        if (picList.size()>0){
            for (Pic picL : picList){
                Pic pic2 = new Pic();
                pic2.setPicUrl(picL.getPicUrl());
                picL2.add(pic2);
            }
        }else {
            picL2.add(null);
        }

        List<Recruit> recruitList = new ArrayList<>();
        List<String> uuidList = new ArrayList<String>();
        int minute = DateUtil.getMinute(new Date(System.currentTimeMillis()));

        if(minute>5 && minute <55){
            uuidList = randomShopService.getRandomOneUuidListByUuidFor(uuid,Constant.SHOP_TAG.RECRUIT);
        }else{
            uuidList = randomShopService.getRandomTwoUuidListByUuidFor(uuid,Constant.SHOP_TAG.RECRUIT);
        }

        if(uuidList.size()>0){
            Collections.shuffle(uuidList);
            recruitList = recruitService.findRecruitByUuidList(uuidList);
        }


//        recruitList = recruitService.findAllByTime();
        String uuid2;
        if( shopType == Constant.SHOP_TAG.PLACE){
            uuid2 = placeService.getShopUuidByShopIdPlace(recruit.getShopId());
        }else{
            uuid2 = shopService.getShopUuidByShopIdShop(recruit.getShopId());
        }

        RecruitDetailJsonParse ejp = new RecruitDetailJsonParse();

        if (picList.size() >= 1) {
            ejp.setPicUrl7(picList.get(0).getPicUrl());
        }
        if (picList.size() >= 2) {
            ejp.setPicUrl8(picList.get(1).getPicUrl());
        }
        if (picList.size() >= 3) {
            ejp.setPicUrl9(picList.get(2).getPicUrl());
        }
        if (picList.size() >= 4) {
            ejp.setPicUrl10(picList.get(3).getPicUrl());
        }
        if (picList.size() >= 5) {
            ejp.setPicUrl11(picList.get(4).getPicUrl());
        }
        if (picList.size() >= 6) {
            ejp.setPicUrl12(picList.get(5).getPicUrl());
        }

        ejp.setUuid2(uuid2);
        ejp.setShopType(recruitDetail.getShopType());
        ejp.setRecruitList(recruitList);
        ejp.setPicList(picL2);
        ejp.setId(recruitDetail.getId());
        ejp.setRecruitId(recruitDetail.getRecruitId());
        ejp.setRecruitName(recruitDetail.getRecruitName());
        ejp.setShopName(recruitDetail.getCompanyName());
        ejp.setTitle1(recruitDetail.getTitle1());
        ejp.setTitle2(recruitDetail.getTitle2());
        ejp.setPicUrl1(recruitDetail.getPicUrl1());
        ejp.setPicUrl2(recruitDetail.getPicUrl2());
        ejp.setPicUrl3(recruitDetail.getPicUrl3());
        ejp.setPicUrl4(recruitDetail.getPicUrl4());
        ejp.setPicUrl5(recruitDetail.getPicUrl5());
        ejp.setVideoUrl1(recruitDetail.getVideoUrl1());
        ejp.setContent(recruitDetail.getContent());
        ejp.setCapacity(recruitDetail.getCapacity());
        ejp.setAddress(recruitDetail.getAddress());
        ejp.setSalary(recruitDetail.getSalary());
        ejp.setSalaryFull(recruitDetail.getSalaryFull());
        ejp.setShopId(recruitDetail.getShopId());
        ejp.setStation(recruitDetail.getStation());
        ejp.setTel(recruitDetail.getTel());
        ejp.setTreatment(recruitDetail.getTreatment());
        ejp.setVacation(recruitDetail.getVacation());
        ejp.setDesc(recruitDetail.getDesc());
        ejp.setSpot(recruitDetail.getSpot());
        ejp.setWorkDay(recruitDetail.getWorkDay());
        ejp.setExecutive(recruitDetail.getExecutive());
        ejp.setAcceptance(recruitDetail.getAcceptance());
        ejp.setWorkPeriod(recruitDetail.getWorkPeriod());
        ejp.setWorkingDate(recruitDetail.getWorkingDate());
        ejp.setWorkingTime(recruitDetail.getWorkingTime());
        ejp.setCreateDatetime(recruitDetail.getCreateDatetime());
        ejp.setUpdateDatetime(recruitDetail.getUpdateDatetime());
        ejp.setDelFlg(recruitDetail.getDelFlg());
        ejp.setNote(recruitDetail.getNote());
        ejp.setModeList(modeList);
//        ejp.setDetailList(detailList);
//        ejp.setFeatureList(featureList);
        ejp.setWorkPeriodList(workPeriodList);
        ejp.setWorkTimeList(workTimeList);
        ejp.setCapacityList(capacityList);
        ejp.setWorkWayList(workWayList);
        ejp.setSpecialList(specialList);
        ejp.setEnvironmentList(environmentList);
        ejp.setTreatmentNewList(treatmentNewList);
        ejp.setFavorite(false);
        if(favDetail.size() >0 ) {
            for (FavDetail  fd: favDetail){
                if(recruit.getId().equals(fd.getShopId())){
                    ejp.setFavorite(true);
                }
            }
        }

        if (workingTimeStart != null && workingTimeStart.size() > 0) {
            if(workingTimeStart.get(0) !=0 && workingTimeStart.get(0) != null ){
                ejp.setWorkingTimeStart(DateUtil.unixTimeStringTime(workingTimeStart.get(0)));
            }else{
                ejp.setWorkingTimeStart("");
            }

        }
        if (workingTimeEnd != null && workingTimeEnd.size() > 0) {
            if(workingTimeEnd.get(0) != null && workingTimeEnd.get(0) !=0){
                ejp.setWorkingTimeEnd(DateUtil.unixTimeStringTime(workingTimeEnd.get(0)));
            }else{
                ejp.setWorkingTimeEnd("");
            }
        }

        //fav
        if(userId != 0 &&userId != null && recruit.getId() != null){
            FavDetail favDetail2 = favDetailService.getFavorite( userId, recruit.getId(), Constant.FAV_TYPE.RECRUIT);
            if(favDetail2 != null){
                UserSetting us = notificationService.getUserSettingBySettingKeyAndSettingValueAndByItemId(userId,Constant.USER_SETTING.NOW4,Constant.USER_SETTING.OFF,recruit.getId());
                if(us == null){
                    ejp.setUserSetting("0");
                    ejp.setFavType(Constant.FAV_TYPE.RECRUIT);
                }else {
                    ejp.setUserSetting("1");
                    ejp.setFavType(Constant.FAV_TYPE.RECRUIT);
                }
            }else{
                ejp.setUserSetting("2");
                ejp.setFavType(Constant.FAV_TYPE.RECRUIT);
            }
        }

        ejpList.add(ejp);

        jsonObject.setResultList(ejpList);

        return jsonObject;
    }

    @RequestMapping(value = "/api/recruit/search/", method = RequestMethod.POST)
    @ResponseBody
    public JsonObject searchShopList(@Valid RecruitSearchParams params, BindingResult result,HttpServletRequest hsr) throws APIException {
        if (result.hasErrors()) {
            logger.warn("recruit search error.");
            throw new APIException(JsonStatus.PARAMETER_ERROR);
        }

        List<Recruit> recruitList = recruitService.getRecruitAll();

        List<Long> recruitIdList = new ArrayList<>();
        List<Long> shopIdList = new ArrayList<>();
        for (Recruit recruit : recruitList) {
            recruitIdList.add(recruit.getId());
            shopIdList.add(recruit.getShopId());
        }
        List<List<Long>> totalIdList = new ArrayList<>();
        List<Long> recruitIdList1 = new ArrayList<>();

        List<Recruit> getRecruitListByKeyword = recruitService.getRecruitAllByKeyword( params.getKeyWord());
        for (Recruit recruit : getRecruitListByKeyword) {
            recruitIdList1.add(recruit.getId());
        }
        totalIdList.add(recruitIdList1);

        if (params.getAreaList() != null && params.getAreaList().size() > 0) {
            List<RecruitTag> areaRecruitResultList = recruitService.getRecruitTagAllByRecruitIdListAndCondition(recruitIdList, Constant.RECRUIT_TAG_TYPE.AREA, params.getAreaList());
            List<Long> areaRecruitResultIdList = new ArrayList<>();
            for (RecruitTag recruitTag : areaRecruitResultList) {
                areaRecruitResultIdList.add(recruitTag.getRecruitId());
            }
            totalIdList.add(areaRecruitResultIdList);                                                                   // Recruit Tag Mode condition filtering results
        }

        if (params.getModeList() != null && params.getModeList().size() > 0) {
            List<RecruitTag> modeRecruitResultList = recruitService.getRecruitTagAllByRecruitIdListAndCondition(recruitIdList, Constant.RECRUIT_TAG_TYPE.MODE, params.getModeList());
            List<Long> modeRecruitResultIdList = new ArrayList<>();
            for (RecruitTag recruitTag : modeRecruitResultList) {
                modeRecruitResultIdList.add(recruitTag.getRecruitId());
            }
            totalIdList.add(modeRecruitResultIdList);                                                                   // Recruit Tag Mode condition filtering results
        }

        if (params.getCarerr() > 0) {
            List<Integer> carerrContentList = new ArrayList<>();
            carerrContentList.add(params.getCarerr());
            List<RecruitTag> carerrRecruitResultList = recruitService.getRecruitTagAllByRecruitIdListAndCondition(recruitIdList, Constant.RECRUIT_TAG_TYPE.CAREER, carerrContentList);
            List<Long> carerrRecruitResultIdList = new ArrayList<>();
            for (RecruitTag recruitTag : carerrRecruitResultList) {
                carerrRecruitResultIdList.add(recruitTag.getRecruitId());
            }
            totalIdList.add(carerrRecruitResultIdList);                                                                 // Recruit Tag Carerr condition filtering results
        }

        if (params.getWorkPeriodList() != null && params.getWorkPeriodList().size() > 0) {
            List<RecruitTag> workPeriodRecruitResultList = recruitService.getRecruitTagAllByRecruitIdListAndCondition(recruitIdList, Constant.RECRUIT_TAG_TYPE.WORK_PERIOD, params.getWorkPeriodList());
            List<Long> workPeriodRecruitResultIdList = new ArrayList<>();
            for (RecruitTag recruitTag : workPeriodRecruitResultList) {
                workPeriodRecruitResultIdList.add(recruitTag.getRecruitId());
            }
            totalIdList.add(workPeriodRecruitResultIdList);                                                              // Recruit Tag workPeriod condition filtering results
        }

        if (params.getWorkTimeList() != null && params.getWorkTimeList().size() > 0) {
            List<RecruitTag> workTimeRecruitResultList = recruitService.getRecruitTagAllByRecruitIdListAndCondition(recruitIdList, Constant.RECRUIT_TAG_TYPE.WORK_TIME, params.getWorkTimeList());
            List<Long> workTimeRecruitResultIdList = new ArrayList<>();
            for (RecruitTag recruitTag : workTimeRecruitResultList) {
                workTimeRecruitResultIdList.add(recruitTag.getRecruitId());
            }
            totalIdList.add(workTimeRecruitResultIdList);                                                              // Recruit Tag workTime condition filtering results
        }

        if (params.getCapacityList() != null && params.getCapacityList().size() > 0) {
            List<RecruitTag> capacityRecruitResultList = recruitService.getRecruitTagAllByRecruitIdListAndCondition(recruitIdList, Constant.RECRUIT_TAG_TYPE.CAPACITY, params.getCapacityList());
            List<Long> capacityRecruitResultIdList = new ArrayList<>();
            for (RecruitTag recruitTag : capacityRecruitResultList) {
                capacityRecruitResultIdList.add(recruitTag.getRecruitId());
            }
            totalIdList.add(capacityRecruitResultIdList);                                                              // Recruit Tag capacity condition filtering results
        }

        if (params.getWorkWayList() != null && params.getWorkWayList().size() > 0) {
            List<RecruitTag> workWayRecruitResultList = recruitService.getRecruitTagAllByRecruitIdListAndCondition(recruitIdList, Constant.RECRUIT_TAG_TYPE.WORK_WAY, params.getWorkWayList());
            List<Long> workWayRecruitResultIdList = new ArrayList<>();
            for (RecruitTag recruitTag : workWayRecruitResultList) {
                workWayRecruitResultIdList.add(recruitTag.getRecruitId());
            }
            totalIdList.add(workWayRecruitResultIdList);                                                              // Recruit Tag workWay condition filtering results
        }

        if (params.getSpecialList() != null && params.getSpecialList().size() > 0) {
            List<RecruitTag> specialRecruitResultList = recruitService.getRecruitTagAllByRecruitIdListAndCondition(recruitIdList, Constant.RECRUIT_TAG_TYPE.ALLOWANCE_SPECIAL, params.getSpecialList());
            List<Long> specialRecruitResultIdList = new ArrayList<>();
            for (RecruitTag recruitTag : specialRecruitResultList) {
                specialRecruitResultIdList.add(recruitTag.getRecruitId());
            }
            totalIdList.add(specialRecruitResultIdList);                                                              // Recruit Tag special condition filtering results
        }

        if (params.getEnvironmentList() != null && params.getEnvironmentList().size() > 0) {
            List<RecruitTag> environmentRecruitResultList = recruitService.getRecruitTagAllByRecruitIdListAndCondition(recruitIdList, Constant.RECRUIT_TAG_TYPE.WORK_ENVIRONMENT, params.getEnvironmentList());
            List<Long> environmentRecruitResultIdList = new ArrayList<>();
            for (RecruitTag recruitTag : environmentRecruitResultList) {
                environmentRecruitResultIdList.add(recruitTag.getRecruitId());
            }
            totalIdList.add(environmentRecruitResultIdList);                                                              // Recruit Tag environment condition filtering results
        }

        if (params.getTreatmentNewList() != null && params.getTreatmentNewList().size() > 0) {
            List<RecruitTag> treatmentRecruitResultList = recruitService.getRecruitTagAllByRecruitIdListAndCondition(recruitIdList, Constant.RECRUIT_TAG_TYPE.TREATMENT_NEW, params.getTreatmentNewList());
            List<Long> treatmentRecruitResultIdList = new ArrayList<>();
            for (RecruitTag recruitTag : treatmentRecruitResultList) {
                treatmentRecruitResultIdList.add(recruitTag.getRecruitId());
            }
            totalIdList.add(treatmentRecruitResultIdList);                                                              // Recruit Tag treatment condition filtering results
        }

        if (params.getWorkingTimeStart() > 0) {
            List<RecruitTag> workingStartRecruitResultList = recruitService.getRecruitTagAllByRecruitIdListAndConditionTimeStart(recruitIdList, Constant.RECRUIT_TAG_TYPE.WORKING_TIME_START, params.getWorkingTimeStart());
            List<Long> workingStartRecruitResultIdList = new ArrayList<>();
            for (RecruitTag recruitTag : workingStartRecruitResultList) {
                workingStartRecruitResultIdList.add(recruitTag.getRecruitId());
            }
            totalIdList.add(workingStartRecruitResultIdList);                                                                 // Recruit Tag WorkingStart condition filtering results
        }

        if (params.getWorkingTimeEnd() > 0) {
            List<RecruitTag> workingEndRecruitResultList = recruitService.getRecruitTagAllByRecruitIdListAndConditionTimeEnd(recruitIdList, Constant.RECRUIT_TAG_TYPE.WORKING_TIME_END, params.getWorkingTimeEnd());
            List<Long> workingEndRecruitResultIdList = new ArrayList<>();
            for (RecruitTag recruitTag : workingEndRecruitResultList) {
                workingEndRecruitResultIdList.add(recruitTag.getRecruitId());
            }
            totalIdList.add(workingEndRecruitResultIdList);                                                                 // Recruit Tag WorkingEnd condition filtering results
        }

        // retain every id list
        if (totalIdList.size() > 1) {
            for (int i = 1; i < totalIdList.size(); i++) {
                totalIdList.get(0).retainAll(totalIdList.get(i));
            }
        }

        List<Long> resultIdList;
        if (totalIdList.size() > 0) {
            resultIdList = totalIdList.get(0);
        } else {
            resultIdList = new ArrayList<>();
        }

        long userId = authService.getUserId(hsr);
        List<FavDetail> favDetail = favDetailService.getFavDetailAll(userId, Constant.FAV_TYPE.RECRUIT);

        List<Recruit> list = recruitService.getRecruitAllByIdListAndCondition(resultIdList, params.getLimit(), params.getOffset());
        List<RecruitJsonParse> ejpList = new ArrayList<>();
        List<String> modeList = new ArrayList<>();
        List<String> areaList = new ArrayList<>();
        List<String> careerList = new ArrayList<>();
        List<String> workPeriodList = new ArrayList<>();
        List<String> workTimeList = new ArrayList<>();
        List<String> workWayList = new ArrayList<>();
        List<String> specialList = new ArrayList<>();
        for (Recruit ed : list) {
            RecruitJsonParse ejp = new RecruitJsonParse();
            modeList = recruitService.getDescByRecruit(ed.getId(), Constant.RECRUIT_TAG_TYPE.MODE);
//            List<String> detailList = recruitService.getDescByRecruit(ed.getId(), Constant.RECRUIT_TAG_TYPE.DETAIL);
            careerList = recruitService.getDescByRecruit(ed.getId(), Constant.RECRUIT_TAG_TYPE.CAREER);
            workPeriodList = recruitService.getDescByRecruit(ed.getId(), Constant.RECRUIT_TAG_TYPE.WORK_PERIOD);
            workTimeList = recruitService.getDescByRecruit(ed.getId(), Constant.RECRUIT_TAG_TYPE.WORK_TIME);
            List<String> capacityList = recruitService.getDescByRecruit(ed.getId(), Constant.RECRUIT_TAG_TYPE.CAPACITY);
            workWayList = recruitService.getDescByRecruit(ed.getId(), Constant.RECRUIT_TAG_TYPE.WORK_WAY);
            specialList = recruitService.getDescByRecruit(ed.getId(), Constant.RECRUIT_TAG_TYPE.ALLOWANCE_SPECIAL);
            List<String> environmentTagList = recruitService.getDescByRecruit(ed.getId(), Constant.RECRUIT_TAG_TYPE.WORK_ENVIRONMENT);
            List<String> treatmentNewTagList = recruitService.getDescByRecruit(ed.getId(), Constant.RECRUIT_TAG_TYPE.TREATMENT_NEW);
            List<Integer> workingTimeStart = recruitService.getContent(ed.getId(), Constant.RECRUIT_TAG_TYPE.WORKING_TIME_START);
            List<Integer> workingTimeEnd = recruitService.getContent(ed.getId(), Constant.RECRUIT_TAG_TYPE.WORKING_TIME_END);

            ejp.setUuid(ed.getUuid());
            ejp.setShopName(ed.getCompanyName());
            ejp.setRecruitName(ed.getRecruitName());
            ejp.setTitle(ed.getTitle1());
            ejp.setPicUrl1(ed.getPicUrl1());
            ejp.setAddress(ed.getAddress());
            ejp.setSalary(ed.getSalary());
            ejp.setShopId(ed.getShopId());
            ejp.setStation(ed.getStation());
            ejp.setWorkingTime(ed.getWorkingTime());
            ejp.setCreateDatetime(ed.getCreateDatetime());
            ejp.setUpdateDatetime(ed.getUpdateDatetime());
            ejp.setDelFlg(ed.getDelFlg());
            ejp.setNote(ed.getNote());
            ejp.setModes(modeList);
//            ejp.setDetails(detailList);
            ejp.setCareer(careerList);
            ejp.setWorkPeriod(workPeriodList);
            ejp.setWorkTime(workTimeList);
            ejp.setWorkWay(workWayList);
            ejp.setCapacity(capacityList);
            ejp.setEnvironment(environmentTagList);
            ejp.setTreatmentNew(treatmentNewTagList);
            ejp.setSpecial(specialList);
            ejp.setFavorite(false);
            if(favDetail.size() >0 ) {
                for (FavDetail  fd: favDetail){
                    if(ed.getId()==fd.getShopId()){
                        ejp.setFavorite(true);
                    }
                }
            }
            if (workingTimeStart != null && workingTimeStart.size() > 0) {
                if(workingTimeStart.get(0) != null && workingTimeStart.get(0) !=0){
                    ejp.setWorkingTimeStart(DateUtil.unixTimeStringTime(workingTimeStart.get(0)));
                }else {
                    ejp.setWorkingTimeStart("");
                }
            }
            if (workingTimeEnd != null && workingTimeEnd.size() > 0) {
                if(workingTimeEnd.get(0) != null && workingTimeEnd.get(0) != 0){
                    ejp.setWorkingTimeEnd(DateUtil.unixTimeStringTime(workingTimeEnd.get(0)));
                }else{
                    ejp.setWorkingTimeEnd("");
                }
            }
            ejpList.add(ejp);
        }

        String areaString ="";
        if (params.getAreaList() != null && params.getAreaList().size()>0) {
            for (int i = 0; i < params.getAreaList().size(); i++) {
                String area = tagDetailService.getDesc(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.AREA, Integer.parseInt(String.valueOf(params.getAreaList().get(i))));
                if (i == 0) {
                    areaString = area;
                } else {
                    areaString += "," + area;
                }
            }
        }
        int count = list.size();
        Map<String, Object> jsonResult = new HashMap<>();
        jsonResult.put("data", ejpList);
        jsonResult.put("count", count);
        jsonResult.put("areaString", areaString);
        jsonResult.put("areaList", areaList);
        jsonResult.put("modeList", modeList);
        jsonResult.put("careerList", careerList);
        jsonResult.put("workPeriodList", workPeriodList);
        jsonResult.put("workTimeList", workTimeList);
        jsonResult.put("workWayList", workWayList);
        jsonResult.put("specialList", specialList);
        jsonObject.setResultList(jsonResult);
        return jsonObject;
    }
}