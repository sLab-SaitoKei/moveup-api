package jp.co.vermore.controller.admin;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.entity.News;
import jp.co.vermore.entity.Person;
import jp.co.vermore.entity.User;
import jp.co.vermore.form.DashboardForm;
import jp.co.vermore.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AdminController
 * Created by Xie Youjun.
 * <p>
 * DateTime: 2018/04/17 23:13
 * Copyright: sLab, Corp
 */

@Controller
public class AdminDashboardController {

    @Autowired
    private AuthService authService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private PersonService personService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private EventService eventService;

    @Autowired
    private FreePaperService freePaperService;

    @RequestMapping(value = "/admin/", method = RequestMethod.GET)
    public String login(Model model){

        DashboardForm dashboardForm = new DashboardForm();
        dashboardForm.setMau(String.valueOf(authService.countUser()));
        dashboardForm.setShopAmount(String.valueOf(shopService.countShop()));
        List<Person> personList=personService.getPersonAll();
        int student=0;int employee=0;int housewife=0;int job=0;int other=0;
        int age10=0;int age20=0; int age30=0; int age40=0; int ageOther=0;
        int male=0; int female=0;
        for(Person p : personList){
            int career=p.getCareer();
            if(career== Constant.CAREER.JUNIOR_STUDENT||career== Constant.CAREER.SENIOR_STUDENT||career== Constant.CAREER.COLLEGE_STUDENT||career== Constant.CAREER.UNIVERSITY_STUDENT){
                student++;
            }else if(career== Constant.CAREER.CIVIL_SERVANT||career== Constant.CAREER.EMPLOYEE||career== Constant.CAREER.TEMPORARY||career== Constant.CAREER.DISPATCHED||career== Constant.CAREER.CONTRACT){
                employee++;
            }else if(career== Constant.CAREER.HOUSE_WIFE||career== Constant.CAREER.HOUSE_HUSBAND){
                housewife++;
            }else if(career== Constant.CAREER.ARBEIT){
                job++;
            }else {
                other++;
            }

            Date date=p.getBirthday();
            Integer age=DateUtil.calAge(date);
            if(age>=10&&age<20){
                age10++;
            }else if(age >=20&&age<30){
                age20++;
            }else if(age >=30&&age<40){
                age30++;
            }else if(age >=40&&age<50){
                age40++;
            }else {
                ageOther++;
            }

            Integer gender=p.getGender();
            if(gender==Constant.PERSON_GENDER.MALE){
                male++;
            }else if(gender==Constant.PERSON_GENDER.FEMALE){
                female++;
            }

        }
        int listSize=personList.size();

        Map genderMap=new HashMap();
        genderMap.put("male",male);
        genderMap.put("personQuantity",(male+female));
        genderMap.put("malePercent",Math.round(male*100/(male+female)));
        genderMap.put("femalePercent",Math.round(female*100/(male+female)));
        dashboardForm.setGender(genderMap);

        Map ageMap=new HashMap();
        ageMap.put("age10",Math.round(age10*100/listSize));
        ageMap.put("age20",Math.round(age20*100/listSize));
        ageMap.put("age30",Math.round(age30*100/listSize));
        ageMap.put("age40",Math.round(age40*100/listSize));
        ageMap.put("ageOther",Math.round(ageOther*100/listSize));
        dashboardForm.setAge(ageMap);


        Map map=new HashMap();
        map.put("student",student);
        map.put("employee",employee);
        map.put("housewife",housewife);
        map.put("job",job);
        map.put("other",other);
        dashboardForm.setCareerAmount(map);


        int studentRatio=Math.round(student*100/listSize);
        int employeeRatio=Math.round(employee*100/listSize);
        int housewifeRatio=Math.round(housewife*100/listSize);
        int jobRatio=Math.round(job*100/listSize);
        int otherRatio=Math.round(other*100/listSize);

        Map mapRatio=new HashMap();
        mapRatio.put("student",studentRatio);
        mapRatio.put("employee",employeeRatio);
        mapRatio.put("housewife",housewifeRatio);
        mapRatio.put("job",jobRatio);
        mapRatio.put("other",otherRatio);
        dashboardForm.setCareerRatio(mapRatio);
        // todo: implement rise PV
        dashboardForm.setRisePV("100");
        dashboardForm.setGoodsAmount(String.valueOf(goodsService.countGoods()));
        dashboardForm.setNews(String.valueOf(newsService.getNewsCount()));
        dashboardForm.setEvent(String.valueOf(eventService.getEventCount()));
        dashboardForm.setFreepaper(String.valueOf(freePaperService.getFreePaperCount()));

        model.addAttribute("dashboardForm", dashboardForm);
        return "admin/index";
    }

}
