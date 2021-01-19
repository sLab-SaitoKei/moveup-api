package jp.co.vermore.controller;

import java.util.ArrayList;
import java.util.List;

import jp.co.vermore.common.JsonStatus;
import jp.co.vermore.entity.Demo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.BeanUtils;

import jp.co.vermore.service.DemoService;
import jp.co.vermore.form.DemoForm;

/**
 * Created by xieyoujun on 2017/11/20.
 */
@Controller
public class DemoController {

    @Autowired
    private DemoService demoService;

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    public class BaseJson {

        String statusCode;
        String message;
        List<?> resultList;

        public String getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<?> getResultList() {
            return resultList;
        }

        public void setResultList(List<?> resultList) {
            this.resultList = resultList;
        }
    }

    @RequestMapping(value = "/test/", method = RequestMethod.GET)
    @ResponseBody
    public BaseJson test() {
        logger.info("Json start----");

        List<Demo> list = new ArrayList<Demo>();
        list.add(new Demo(1,"32"));
        list.add(new Demo(2, JsonStatus.SUCCESS.getMessage()));
        list.add(new Demo(3, JsonStatus.SUCCESS.statusCode()));
        list.add(new Demo(4, JsonStatus.UNKNOWN_ERROR.getMessage()));
        list.add(new Demo(5, JsonStatus.UNKNOWN_ERROR.statusCode()));

        BaseJson bj = new BaseJson();

        bj.setStatusCode(JsonStatus.SUCCESS.statusCode());
        bj.setMessage(JsonStatus.SUCCESS.getMessage());
        bj.setResultList(list);

        return bj;
    }

    @RequestMapping(value = "/test/", method = RequestMethod.POST)
    @ResponseBody
    public BaseJson testPost() {
        logger.info("Json start----");

        List<Demo> list = new ArrayList<Demo>();
        list.add(new Demo(1,"32"));
        list.add(new Demo(2, JsonStatus.SUCCESS.getMessage()));
        list.add(new Demo(3, JsonStatus.SUCCESS.statusCode()));
        list.add(new Demo(4, JsonStatus.UNKNOWN_ERROR.getMessage()));
        list.add(new Demo(5, JsonStatus.UNKNOWN_ERROR.statusCode()));

        BaseJson bj = new BaseJson();

        bj.setStatusCode(JsonStatus.SUCCESS.statusCode());
        bj.setMessage(JsonStatus.SUCCESS.getMessage());
        bj.setResultList(list);

        return bj;
    }

    @RequestMapping(value = "/demo/{id}", method = RequestMethod.GET)
    public String demo(Model model, @PathVariable int id) {
        logger.info("Start demo controller");

        Demo demo = demoService.getDemo(id);
        model.addAttribute("message1", "一件を取得");
        model.addAttribute("demo", demo);
        return "entry/demo";
    }

    @RequestMapping(value = "/demo/", method = RequestMethod.GET)
    public String demoAll(Model model) {
        logger.info("----Start demo all");
        List<Demo> demos = demoService.getDemoAll();
        model.addAttribute("message2", "全件取得");
        model.addAttribute("demos_all", demos);
        return "entry/demo";
    }

    @RequestMapping(value = "/demo/dto/{id}", method = RequestMethod.GET)
    public String demoDto(Model model, @PathVariable int id) {
        Demo parmDto = new Demo(id, null);
        Demo resultDto = demoService.getDemoByEntity(parmDto);
        System.out.println("--------------------" + resultDto.getName());
        model.addAttribute("message3", "DTO取得");
        model.addAttribute("demos_dto", resultDto);
        return "entry/demo";
    }


    @RequestMapping(value = "/demo/insert/", method = RequestMethod.GET)
    public String demoInsert(Model model) {
        DemoForm form = new DemoForm();
        model.addAttribute("demoForm", form);
        model.addAttribute("message4", "insertサンプル");
        return "entry/demoInsert";
    }

    @RequestMapping(value = "/demo/insert/", method = RequestMethod.POST)
    public String demoInsert(@ModelAttribute DemoForm form, Model model) {
        int count = demoService.insertDemo(form.getName());
        logger.info("挿入件数は" + count + "件です。");
        return "redirect:/demo/";
    }

    @RequestMapping(value = "/demo/delete/", method = RequestMethod.GET)
    public String demoDelete(Model model) {
        DemoForm form = new DemoForm();
        model.addAttribute("demoForm", form);
        model.addAttribute("message5", "deleteサンプル");
        return "entry/demoDelete";
    }

    @RequestMapping(value = "/demo/delete/", method = RequestMethod.POST)
    public String demoDelete(@ModelAttribute DemoForm form, Model model) {
        int count = demoService.deleteDemo(form.getId());
        logger.info("削除件数は" + count + "件です。");
        return "redirect:/demo/";
    }

    @RequestMapping(value = "/demo/update/{id}/", method = RequestMethod.GET)
    public String usrUpdate(Model model, @PathVariable int id) {
        Demo demo = demoService.getDemo(id);
        model.addAttribute("message", "Updateサンプルです");
        model.addAttribute("demo", demo);
        DemoForm form = new DemoForm();
        form.setId(demo.getId());
        form.setName(demo.getName());
        model.addAttribute("demoForm", form);
        return "entry/demoUpdate";
    }

    @RequestMapping(value = "/demo/update/{id}/", method = RequestMethod.POST)
    public String testUpdate(Model model, @ModelAttribute DemoForm form) {
        Demo dto = new Demo(0,"");
        BeanUtils.copyProperties(form, dto);
        int count = demoService.updateDemo(dto);
        logger.info("更新件数は" + count + "件です。");
        return "redirect:/demo/";
    }

}
