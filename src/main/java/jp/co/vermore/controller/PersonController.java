package jp.co.vermore.controller;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.JsonObject;
import jp.co.vermore.common.JsonStatus;
import jp.co.vermore.common.mvc.APIException;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.common.util.StringUtil;
import jp.co.vermore.entity.*;
import jp.co.vermore.form.PersonEditForm;
import jp.co.vermore.jsonparse.UserSettingJsonParse;
import jp.co.vermore.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static jp.co.vermore.common.util.DateUtil.YYYY年MM月DD日;

/**
 * PersonController
 * Created by wubin.
 * <p>
 * DateTime: 2018/04/10 9:09
 * Copyright: sLab, Corp
 */
@Controller
public class PersonController extends BaseController {

    @Autowired
    private PersonService personService;

    @Autowired
    private AuthService authService;

    @Autowired
    private CoinService coinService;

    @Autowired
    private AWSService awsService;

    @Autowired
    private TagDetailService tagDetailService;

    @Autowired
    private WithdrawService withdrawService;

    @Autowired
    private EntryService entryService;

    @Autowired
    PlatformTransactionManager txManager;

    @RequestMapping(value = "/api/person/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getPersonList(HttpServletRequest hsr) throws APIException {

        long userId = authService.getUserId(hsr);
        Integer loginType = authService.getUserLoginType(hsr);
        UserForeign userForeign = authService.getUserForeignByUserId(userId);
        long coin = coinService.getAmount(userId);
        String id = userForeign.getForeignId();
        Person person = personService.getPersonById(userId);
        List<UserSetting> list = personService.getUserSettingById(userId);
        List<UserSettingJsonParse> ujpList = new ArrayList<>();
        List<TagDetail> areaTagList = tagDetailService.getTagDetailByModuleAndType(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.AREA);

        for (UserSetting us : list) {
            UserSettingJsonParse ujp = new UserSettingJsonParse();
            ujp.setSettingKey(us.getSettingKey());
            ujp.setSettingValue(us.getSettingValue());
            ujpList.add(ujp);
        }
        Map<String, Object> m = new HashMap<>();
        if(person ==null){

        }else if(person!=null){
            String date = DateUtil.dateToStringyyyy_MM_dd(person.getBirthday());
            String[] ss = new String[3];
            ss = date.split("-");
            String birthyear = ss[0];
            String birthmonth = ss[1];
            String birthday = ss[2];
            m.put("loginType", loginType);
            m.put("firstName", person.getFirstName());
            m.put("secondName", person.getSecondName());
            m.put("firstNameKana", person.getFirstNameKana());
            m.put("secondNameKana", person.getSecondNameKana());
            m.put("nickname", person.getNickname());
            m.put("gender", person.getGender());
            m.put("birthyear", birthyear );
            m.put("birthmonth", birthmonth );
            m.put("birthday", birthday );
            m.put("coin", coin);
            m.put("thumbnailUrl", person.getThumbnailUrl());
            m.put("career",person.getCareer());
            m.put("zipcode", person.getZipcode());
            m.put("address", person.getAddress());
            m.put("address2", person.getAddress2());
            m.put("address3", person.getAddress3());
            m.put("mail",person.getMail());
            m.put("areaList", areaTagList);
            m.put("settingList", ujpList);
        }
        jsonObject.setResultList(m);
        return jsonObject.withStatus(JsonStatus.SUCCESS);
    }

    @RequestMapping(value = "/api/person/confirm/", method = RequestMethod.POST)
    @ResponseBody
    public JsonObject personConfirm(@ModelAttribute PersonEditForm form) throws APIException {

        String firstName = form.getFirstName();
        String secondName = form.getSecondName();
        String fistNameKana = form.getFirstNameKana();
        String secondNameKana = form.getSecondNameKana();
        Integer birthyear = Integer.valueOf(form.getBirthyear());
        Integer birthmonth = Integer.valueOf(form.getBirthmonth());
        Integer birthday = Integer.valueOf(form.getBirthday());
        String nickName = form.getNickname();
        String adddress = form.getAddress();
        String thumbnailUrl = form.getThumbnailUrl();
        Integer career = form.getCareer();
        Integer gender = form.getGender();
        Integer area = form.getArea();
        String zipcode = form.getZipcode();
        String mailAddress = form.getMail();
        String password = form.getPassword();
        String areaStr = tagDetailService.getDesc(Constant.SHOP_TAG.FOOD,Constant.SHOP_TAG.AREA,area);
        List<Person> settingList =form.getSettingList();

        List<UserSettingJsonParse> ujpList = new ArrayList<>();
        Map<String, Object> m = new HashMap<>();
        m.put("first_name",firstName);
        m.put("second_name", secondName);
        m.put("first_name_kana", fistNameKana);
        m.put("second_name_kana", secondNameKana);
        m.put("nickname",nickName);
        m.put("gender", gender);
        m.put("thumbnail_url", thumbnailUrl);
        m.put("career", career);
        m.put("area",areaStr);
        m.put("zipcode",zipcode);
        m.put("address", adddress);
        m.put("mail",mailAddress);
        m.put("password",password);
        m.put("settingList", ujpList);

        jsonObject.setResultList(m);
        return jsonObject;
    }

    @RequestMapping(value = "/api/person/insert/", method = RequestMethod.POST)
    @ResponseBody
    public JsonObject personInsert(@ModelAttribute PersonEditForm form, HttpServletRequest hsr) throws APIException {

        String firstName = form.getFirstName();
        String secondName = form.getSecondName();
        String fistNameKana = form.getFirstNameKana();
        String secondNameKana = form.getSecondNameKana();
        Integer birthyear = form.getBirthyear() != null?Integer.valueOf(form.getBirthyear()):0;
        Integer birthmonth = form.getBirthyear() != null?Integer.valueOf(form.getBirthmonth()):0;
        Integer birthday = form.getBirthyear() != null?Integer.valueOf(form.getBirthday()):0;
        String nickName = form.getNickname();
        String adddress = form.getAddress();
        String address2 = form.getAddress2();
        String address3 = form.getAddress3();
        String thumbnailUrl = form.getThumbnailUrl();
        Integer career = form.getCareer();
        Integer gender = form.getGender();
        Integer area = form.getArea();
        String zipcode = form.getZipcode();
        String mailAddress = form.getMail();
        String password = form.getPassword();
        List<Person> settingList =form.getSettingList();

        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try {
            long userId = authService.getUserId(hsr);

                Person person = new Person();
                person.setUserId(userId);
                if (nickName != null) {
                    person.setNickname(nickName);
                }
                if (adddress != null) {
                    person.setAddress(adddress);
                }
                if (address2 !=null){
                    person.setAddress2(address2);
                }
                if(address3 != null){
                    person.setAddress3(address3);
                }
                if (thumbnailUrl != null) {
                    person.setThumbnailUrl(thumbnailUrl);
                }
                if (career != null) {
                    person.setCareer(career);
                }
                if (area != null) {
                    person.setCareer(area);
                }
                if (gender != null) {
                    person.setGender(gender);
                }
                if (zipcode != null) {
                    person.setZipcode(zipcode);
                }
                if(firstName != null){
                    person.setFirstName(firstName);
                }
                if(secondName != null){
                    person.setSecondName(secondName);
                }
                if(fistNameKana != null){
                    person.setFirstNameKana(fistNameKana);
                }
                if(secondNameKana !=null){
                    person.setSecondNameKana(secondNameKana);
                }
                if(birthday != null){
                    person.setBirthday(DateUtil.getBrithdayDate(birthyear, birthmonth, birthday));
                }
            person.setCreateDatetime(new Date(System.currentTimeMillis()));
            person.setUpdateDatetime(new Date(System.currentTimeMillis()));
            personService.insertPerson(person);
            if(password!=null){
                UserForeign userForeign = new UserForeign();

                userForeign.setUserId(userId);
                userForeign.setForeignType(Constant.USER_FOREIGN_TYPE.MAIL);
                userForeign.setForeignId(mailAddress);
                userForeign.setIsLogin(0);
                userForeign.setAccessToken(Constant.EMPTY_STRING);
                userForeign.setRefreshToken(Constant.EMPTY_STRING);
                userForeign.setTokenSecret(Constant.EMPTY_STRING);
                userForeign.setPassword(StringUtil.sha256(password));
                userForeign.setExpireDatetime(new Date(System.currentTimeMillis()));
                userForeign.setCreateDatetime(new Date(System.currentTimeMillis()));
                userForeign.setUpdateDatetime(new Date(System.currentTimeMillis()));
                userForeign.setDelFlg(Boolean.FALSE);
                userForeign.setNote(Constant.EMPTY_STRING);
            }

            String areaStr ="";
            List<UserSetting> list = personService.getUserSettingById(userId);
            List<UserSettingJsonParse> ujpList = new ArrayList<>();
            Person p = personService.getPersonById(userId);
            if(area!=null){
                 areaStr = tagDetailService.getDesc(Constant.SHOP_TAG.FOOD,Constant.SHOP_TAG.AREA,area);
            }

            for (UserSetting us : list) {
                UserSettingJsonParse ujp = new UserSettingJsonParse();
                ujp.setSettingKey(us.getSettingKey());
                ujp.setSettingValue(us.getSettingValue());
                ujpList.add(ujp);
            }
            Map<String, Object> m = new HashMap<>();
            if(p ==null){

            }else if(p!=null){
                String date = DateUtil.dateToStringyyyy_MM_dd(p.getBirthday());
                String[] ss = new String[3];
                ss = date.split("-");
                String birthyear2 = ss[0];
                String birthmonth2 = ss[1];
                String birthday2 = ss[2];
                m.put("firstName", p.getFirstName());
                m.put("secondName", p.getSecondName());
                m.put("firstNameKana", p.getFirstNameKana());
                m.put("secondNameKana", p.getSecondNameKana());
                m.put("nickname", p.getNickname());
                m.put("gender", p.getGender());
                m.put("birthyear", birthyear2 );
                m.put("birthmonth", birthmonth2 );
                m.put("birthday", birthday2 );
                m.put("thumbnailUrl", p.getThumbnailUrl());
                m.put("career", p.getCareer());
                m.put("area",areaStr);
                m.put("zipcode", p.getZipcode());
                m.put("address", p.getAddress());
                m.put("address2", p.getAddress2());
                m.put("address3", p.getAddress3());
                m.put("mail",mailAddress);
                m.put("password",password);
                m.put("settingList", ujpList);
            }
           jsonObject.setResultList(m);
            txManager.commit(txStatus);
        } catch (Exception e) {
            txManager.rollback(txStatus);
            logger.error("Insert person failed!, error=" + e.getMessage());
            logger.error("Insert person failed!, error=" + e.toString());
            e.printStackTrace();
            throw new APIException(JsonStatus.DATA_SAVE_FAILED);
        }
        return jsonObject.withStatus(JsonStatus.SUCCESS);
    }

    @RequestMapping(value = "/api/person/update/", method = RequestMethod.POST)
    @ResponseBody
    public JsonObject personUpdate(@ModelAttribute PersonEditForm form, HttpServletRequest hsr) throws APIException {

        String firstName = form.getFirstName();
        String secondName = form.getSecondName();
        String fistNameKana = form.getFirstNameKana();
        String secondNameKana = form.getSecondNameKana();
        Integer birthyear = form.getBirthyear() != null?Integer.valueOf(form.getBirthyear()):0;
        Integer birthmonth = form.getBirthyear() != null?Integer.valueOf(form.getBirthmonth()):0;
        Integer birthday = form.getBirthyear() != null?Integer.valueOf(form.getBirthday()):0;
        String nickName = form.getNickname();
        String adddress = form.getAddress();
        String address2 =form.getAddress2();
        String address3 = form.getAddress3();
        MultipartFile thumbnail = form.getThumbnail();
        Integer area = form.getArea()!= null?Integer.valueOf(form.getArea()):0;
        Integer career = form.getCareer()!= null?Integer.valueOf(form.getCareer()):0;
        Integer gender = form.getGender()!= null?Integer.valueOf(form.getGender()):0;
        String zipcode = form.getZipcode();
        String mailAddress = form.getMail();
        String password = "";
        if(form.getPassword()!=null){
             password = StringUtil.sha256(form.getPassword());
        }
        List<Person> settingList =form.getSettingList();

        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try {
            long userId = authService.getUserId(hsr);
            UserSetting userSetting = new UserSetting();
            String thumbnailUrl = "";
            if(thumbnail!=null) {
                 thumbnailUrl = awsService.postFile(thumbnail);
            }

            if(settingList != null && settingList.size()>0){
                userSetting.setUserId(userId);
                for(Person li:settingList){
                    userSetting.setSettingKey(li.getKey());
                    userSetting.setSettingValue(li.getValue());
                    personService.insertUserSetting(userSetting);
                 }
            }

            Person person = new Person();
            person.setUserId(userId);
            if (nickName != null) {
                person.setNickname(nickName);
            }
            if (adddress != null) {
                person.setAddress(adddress);
            }
            if (address2 != null){
                person.setAddress2(address2);
            }
            if (address3 !=null ){
                person.setAddress3(address3);
            }
            if (thumbnailUrl != null&& thumbnailUrl!= "" ) {
                person.setThumbnailUrl(thumbnailUrl);
            }
            if (area != null && career != 0) {
                person.setArea(area);
            }
            if (career != null&& career != 0) {
                person.setCareer(career);
            }
            if (gender != null && gender != 0) {
                person.setGender(gender);
            }
            if (zipcode != null) {
                person.setZipcode(zipcode);
            }
            if(firstName != null){
                person.setFirstName(firstName);
            }
            if(secondName != null){
                person.setSecondName(secondName);
            }
            if(fistNameKana != null){
                person.setFirstNameKana(fistNameKana);
            }
            if(secondNameKana !=null){
                person.setSecondNameKana(secondNameKana);
            }
            if(mailAddress !=null && mailAddress!= ""){
                person.setMail(mailAddress);
            }
            if(birthday != 0 && birthday != 0 && birthday != 0){
                person.setBirthday(DateUtil.getBrithdayDate(birthyear, birthmonth, birthday));
            }
            person.setCreateDatetime(new Date(System.currentTimeMillis()));
            person.setUpdateDatetime(new Date(System.currentTimeMillis()));
            personService.updatePerson(person);

            if(password!=null&&password!=""){
                UserForeign userForeign = authService.getUserForeignByUserId(userId);
                userForeign.setPassword(password);
                authService.updateUserForeign(userForeign);
            }

            String areaStr = "";
            List<UserSetting> list = personService.getUserSettingById(userId);
            List<UserSettingJsonParse> ujpList = new ArrayList<>();
            UserForeign userForeign = authService.getUserForeignByUserId(userId);
            String id = userForeign.getForeignId();
            Person p = personService.getPersonById(userId);
            if(area != null){
                 areaStr = tagDetailService.getDesc(Constant.SHOP_TAG.FOOD,Constant.SHOP_TAG.AREA,area);
            }

            for (UserSetting us : list) {
                UserSettingJsonParse ujp = new UserSettingJsonParse();
                ujp.setSettingKey(us.getSettingKey());
                ujp.setSettingValue(us.getSettingValue());
                ujpList.add(ujp);
            }

            Map<String, Object> m = new HashMap<>();
            if(p ==null){

            }else if(p!=null){
                String date = DateUtil.dateToStringyyyy_MM_dd(p.getBirthday());
                String[] ss = new String[3];
                ss = date.split("-");
                String birthyear3 = ss[0];
                String birthmonth3 = ss[1];
                String birthday3 = ss[2];
                m.put("firstName", p.getFirstName());
                m.put("secondName", p.getSecondName());
                m.put("firstNameKana", p.getFirstNameKana());
                m.put("secondNameKana", p.getSecondNameKana());
                m.put("nickname", p.getNickname());
                m.put("gender",p.getGender());
                m.put("birthyear", birthyear3 );
                m.put("birthmonth", birthmonth3 );
                m.put("birthday", birthday3 );
                m.put("thumbnailUrl", p.getThumbnailUrl());
                m.put("career", p.getCareer());
                m.put("zipcode", p.getZipcode());
                m.put("area",areaStr);
                m.put("address", p.getAddress());
                m.put("address2", p.getAddress2());
                m.put("address3", p.getAddress3());
                m.put("mail",p.getMail());
                m.put("settingList", ujpList);
            }
            jsonObject.setResultList(m);
            txManager.commit(txStatus);
        } catch (Exception e) {
            txManager.rollback(txStatus);
            logger.error("Update person failed!, error=" + e.getMessage());
            logger.error("Update person failed!, error=" + e.toString());
            e.printStackTrace();
            throw new APIException(JsonStatus.DATA_SAVE_FAILED);
        }
        return jsonObject.withStatus(JsonStatus.SUCCESS);
    }

    @RequestMapping(value = "/api/person/header/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getPersonHeader(HttpServletRequest hsr) {
        long userId = 0L;
        try {
            userId = authService.getUserId(hsr);
        }catch (APIException ex){}
        Map<String,Object> result = new HashMap<>();
        Person person = null;
        if(userId > 0) {
            person = personService.getPersonById(userId);
            if(person != null){
                if(person.getFirstNameKana().equals("") || person.getSecondNameKana().equals("") || person.getAddress().equals("") || person.getMail().equals("")
                        || person.getFirstName().equals("")  || person.getSecondName().equals("")  || person.getGender()==Constant.PERSON_GENDER.UNKNOW || person.getZipcode().equals("")){
                    result.put("hasPerson", false);
                }else{
                    result.put("hasPerson", true);
                }
                result.put("isLogin", true);
                result.put("header", person.getThumbnailUrl());
                result.put("nickname", person.getNickname());
            }else{
                result.put("isLogin", false);
                result.put("hasPerson", false);
            }
        }
        jsonObject.setResultList(result);
        return jsonObject;
    }

    @RequestMapping(value = "/api/person/withdraw/", method = RequestMethod.POST)
    @ResponseBody
    public JsonObject deletePerson(@RequestBody MultiValueMap<String, String> formData, HttpServletRequest hsr,HttpServletResponse response) throws APIException,IOException {

        long  userId = authService.getUserId(hsr);
        String mail = personService.getPersonMailByUid(userId);
        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try{

            Withdraw withdraw = new Withdraw();
            byte reason = 0;
            String detail = "";
            if(formData.getFirst("reason") !=null || formData.getFirst("reason") !=""){
                reason = Byte.parseByte(formData.getFirst("reason"));
            }
            if(formData.getFirst("detail")!= null){
                detail = formData.getFirst("detail");
            }

            withdraw.setUserId(userId);
            withdraw.setReason(reason);
            withdraw.setDetail(detail);
            withdrawService.insertWithdraw(withdraw);
            authService.updateUserWithdraw(userId);
            entryService.deleteEntryByID(userId);
            personService.deletePerson(userId);

           txManager.commit(txStatus);
        } catch (Exception e) {
            txManager.rollback(txStatus);
            logger.error("withdraw  failed!, error=" + e.getMessage());
            logger.error("withdraw  failed!, error=" + e.toString());
            e.printStackTrace();
            throw new APIException(JsonStatus.DATA_SAVE_FAILED);
        }

        String uuid = hsr.getHeader(Constant.SESSION.UUID);
        String sid = hsr.getHeader(Constant.SESSION.SESSIONID);
        if (sid == null || sid.isEmpty()) {
            Cookie cookies[] = hsr.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (c.getName().equals(Constant.SESSION.SESSIONID)) sid = c.getValue();
                    if (c.getName().equals(Constant.SESSION.UUID)) uuid = c.getValue();
                }
            }
        }
        if(sid != null && uuid != null) {
            setLogoutCookie(response, uuid, sid);
        }

        if(mail != null && !mail.equals("")){
            awsService.sendRetreattMail(mail);
        }
        //todo:おトク情報、ストア
        jsonObject.setResultList(null);
        return jsonObject.withStatus(JsonStatus.SUCCESS);
    }

    private void setLogoutCookie(HttpServletResponse response, String uid, String sid) {

        Cookie uidCookie = new Cookie(Constant.SESSION.UUID, uid);
        Cookie sidCookie = new Cookie(Constant.SESSION.SESSIONID, sid);
        uidCookie.setPath("/");
        sidCookie.setPath("/");
        uidCookie.setHttpOnly(true);
        sidCookie.setHttpOnly(true);
        uidCookie.setMaxAge(0);
        sidCookie.setMaxAge(0);

        response.addCookie(uidCookie);
        response.addCookie(sidCookie);
    }



}
