package jp.co.vermore.controller.admin;

import java.util.List;

import jp.co.vermore.entity.Rise;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import jp.co.vermore.form.RiseRegistForm;
import jp.co.vermore.service.RiseService;

/**
 * RiseController
 * Created by Wangyan.
 * <p>
 * DateTime: 2018/02/28 17:44
 * Copyright: sLab, Corp
 */
@Controller
class AdminRiseController {
    @Autowired
    private RiseService riseService;

    @Autowired
    private RiseService RiseService;

    //Rise list
    @RequestMapping(value = "/admin/rise/list/", method = RequestMethod.GET)
    public String riseAllForAdmin(Model model){
        List<Rise> rises = riseService.getRiseAllForAdmin();
        model.addAttribute("message2", "全件取得");
        model.addAttribute("riseList", rises);
        return "admin/riseList";
    }

    //Rise regitst
    @RequestMapping(value = "/admin/rise/insert/", method = RequestMethod.GET)
    public String riseInsert(Model model) {
        RiseRegistForm riseRegistForm = new RiseRegistForm();
        model.addAttribute("riseRegistForm", riseRegistForm);
        return "admin/riseRegitst";
    }

    //Rise regitst
    @RequestMapping(value = "/admin/rise/insert/", method = RequestMethod.POST)
    public String riseInsert(@ModelAttribute RiseRegistForm riseRegistForm, Model model) {
        long riseId = riseService.insertRise(riseRegistForm);
        try {
            RiseService.insertRiseDetail(riseRegistForm,riseId);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "redirect:/admin/rise/list/";
    }

    /*//Rise regitst
    @RequestMapping(value = "/admin/rise/list/update/", method = RequestMethod.POST)
    public String riseUpdate(@PathVariable int id, Model model) {
        RiseEditForm riseEditForm = new RiseEditForm();

        model.addAttribute("riseEditForm", riseEditForm);
        return "admin/riseEdit";
    }*/


}

