package jp.co.vermore.controller.admin;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.DatatablesJsonObject;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.common.util.StringUtil;
import jp.co.vermore.entity.AdminRole;
import jp.co.vermore.entity.AdminUser;
import jp.co.vermore.entity.AuthDetail;
import jp.co.vermore.entity.Customer;
import jp.co.vermore.form.LoginForm;
import jp.co.vermore.form.admin.AdminUserForm;
import jp.co.vermore.form.admin.CustomerEditForm;
import jp.co.vermore.form.admin.CustomerListForm;
import jp.co.vermore.service.AdminService;
import jp.co.vermore.service.AuthService;
import jp.co.vermore.service.TagDetailService;
import jp.co.vermore.service.WidgetService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * AdminController
 * Created by Wangyan.
 * <p>
 * DateTime: 2018/03/23 12:10
 * Copyright: sLab, Corp
 */

@Controller
public class AdminUserController extends BaseController {

    @Autowired
    private AdminService adminUserService;

    @Autowired
    private WidgetService widgetService;

    @Autowired
    private AuthService authService;

    @Autowired
    TagDetailService tagDetailService;

    //Login
    @RequestMapping(value = "/admin/login/", method = RequestMethod.GET)
    public String getLogin(Model model) {
        LoginForm loginForm = new LoginForm();
//        loginForm.setUsername("admin");
//        loginForm.setPassword("123456");
        model.addAttribute("error", "");
        model.addAttribute("loginForm", loginForm);
        return "admin/login";
    }

    @RequestMapping(value = "/admin/login/", method = RequestMethod.POST)
    public String postLogin(Model model, HttpServletRequest request, @ModelAttribute LoginForm form) {
        // get admin user
        AdminUser adminUser = adminUserService.getAdminUserByLoginNameAndPassword(form.getUsername(), StringUtil.sha256(form.getPassword()));


        if (adminUser == null) {
            model.addAttribute("error", "user or password error!");
            model.addAttribute("loginForm", new LoginForm());
            return "admin/login";
        } else {
            List<AdminRole> roleList = adminUserService.getAdminRoleByType(adminUser.getRole().byteValue());
            AdminUserForm adminUserForm = new AdminUserForm();
            adminUserForm.setUser(adminUser);
            adminUserForm.setRoleList(roleList);
            request.getSession().setAttribute(Constant.ADMIN_SESSION.USER, adminUserForm);
            return "redirect:/admin/";
        }
    }


    // Customer admin list
    @RequestMapping(value = "/admin/user/list/", method = RequestMethod.GET)
    public String eventList(Model model) {

        List<Customer> List = authService.getAdminAll();
        model.addAttribute("user_list", List);

        return "admin/userList";
    }

    @RequestMapping(value = "/admin/user/list/", method = RequestMethod.POST)
    @ResponseBody
    public DatatablesJsonObject recruitList(@RequestBody CustomerListForm form) {
        logger.debug("----1----");
        // set order statement
        if (form.getOrder().size() > 0
                && form.getColumns().get(form.getOrder().get(0).getColumn()).getName() != null
                && form.getColumns().get(form.getOrder().get(0).getColumn()).getName().length() > 0) {
            form.setOrderStatement(form.getColumns().get(form.getOrder().get(0).getColumn()).getName() + " " + form.getOrder().get(0).getDir());
            logger.debug("----2----order statement=" + form.getOrderStatement());
        } else {
            form.setOrderStatement("user.id");
            logger.debug("----2----order statement=" + form.getOrderStatement());
        }
        logger.debug("----3----");

        // query data
        List<AuthDetail> dataList = authService.getCustomerAllByCondition(form);
        int totalCountFiltered = authService.getCustomerCountByCondition(form);
        int totalCount = authService.getCustomerCount();
        logger.debug("----4----data count=" + dataList.size());
        logger.debug("----5----total filtered=" + totalCountFiltered);
        logger.debug("----6----total count=" + totalCount);
        logger.debug("----7----page=" + form.getDraw());

        // return json data
        DatatablesJsonObject jsonparse = new DatatablesJsonObject();
        jsonparse.setDraw(form.getDraw());
        jsonparse.setRecordsFiltered(totalCountFiltered);
        jsonparse.setRecordsTotal(totalCount);
        jsonparse.setData(dataList);
        logger.debug("----8----");
        return jsonparse;
    }


    // Customer admin list
    @RequestMapping(value = "/admin/user/edit/{id}/", method = RequestMethod.GET)
    public String eventList(Model model, @PathVariable Long id) {

        Customer ac = authService.getAdminByUserId(id);
        CustomerEditForm jd = new CustomerEditForm();

        jd.setId(ac.getId());
        jd.setUserId(ac.getUserId());
        jd.setUuid(ac.getUuid());
        jd.setStatus(ac.getStatus());
        jd.setForeignId(ac.getForeignId());
        jd.setForeignType(ac.getForeignType());
        jd.setNickname(ac.getNickname());
        if (ac.getBirthday() != null && DateUtils.isSameDay(ac.getBirthday(), DateUtil.getDefaultDate())) {
        } else {
            jd.setBirthday(ac.getBirthday());
        }
        jd.setGender(ac.getGender());
        jd.setFirstName(ac.getFirstName());
        jd.setSecondName(ac.getSecondName());
        jd.setFirstNameKana(ac.getFirstNameKana());
        jd.setSecondNameKana(ac.getSecondNameKana());
        if (ac.getArea() != null) {
            jd.setArea(tagDetailService.getDesc(Constant.SHOP_TAG.FOOD, Constant.SHOP_TAG.AREA, ac.getArea()));
        }
        jd.setThumbnailUrl(ac.getThumbnailUrl());
        if (ac.getCareer() != null) {
            jd.setCareer(widgetService.getCareer(ac.getCareer()));
        }
        jd.setZipcode(ac.getZipcode());
        jd.setAddress(ac.getAddress());
        jd.setMail(ac.getMail());
        model.addAttribute("user_detail", jd);

        return "admin/userEdit";
    }
}
