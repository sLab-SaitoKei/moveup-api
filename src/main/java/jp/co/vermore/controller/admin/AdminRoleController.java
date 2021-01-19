package jp.co.vermore.controller.admin;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.DatatablesJsonObject;
import jp.co.vermore.common.JsonStatus;
import jp.co.vermore.common.mvc.APIException;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.entity.AdminCustomer;
import jp.co.vermore.entity.AdminRole;
import jp.co.vermore.entity.AdminUser;
import jp.co.vermore.entity.Shop;
import jp.co.vermore.form.admin.RoleFrom;
import jp.co.vermore.form.admin.RoleListForm;
import jp.co.vermore.service.AdminService;

import jp.co.vermore.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * AdminShopCustomerController
 * Created by wubin.
 * <p>
 * DateTime: 2018/05/03 9:34
 * Copyright: sLab, Corp
 */
@Controller
public class AdminRoleController extends BaseController {

    @Autowired
    AdminService adminService;

    @Autowired
    ShopService shopService;

    @Autowired
    PlatformTransactionManager txManager;

    @RequestMapping(value = "/admin/role/list/", method = RequestMethod.GET)
    public String adminCustomerList(Model model,HttpServletRequest request) {
        int errorCode = 0;
        if(!request.getSession().isNew()){
            if(request.getSession().getAttribute("error") != null && request.getSession().getAttribute("error") != ""){
                errorCode = (int)request.getSession().getAttribute("error");
                request.getSession().setAttribute("error",0);
            }
        }
        model.addAttribute("errorCode", errorCode);
        return "admin/roleList";
    }

    @RequestMapping(value = "/admin/role/list/", method = RequestMethod.POST)
    @ResponseBody
    public DatatablesJsonObject recruitList(@RequestBody RoleListForm form) {
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

        if(form.getShopUuid() != null && form.getShopUuid() != ""){
            Shop shop = shopService.getShopByUuid(form.getShopUuid());
            if(shop!= null){
                form.setFcAdminId(shop.getId());
            }else {
                form.setFcAdminId(-1L);
            }
        }

        // query data
        List<AdminCustomer> dataList = adminService.getAdminCustomerAllByCondition(form);
        int totalCountFiltered = adminService.getAdminCustomerCountByCondition(form);
        int totalCount = adminService.getAdminCustomerCount();
        logger.debug("----4----data count=" + dataList.size());
        logger.debug("----5----total filtered=" + totalCountFiltered);
        logger.debug("----6----total count=" + totalCount);
        logger.debug("----7----page=" + form.getDraw());

        if(dataList.size()>0 ){
            for(AdminCustomer ac:dataList){
                if(ac.getFcAdminId() != 0){
                    Shop s = shopService.getShopById(ac.getFcAdminId());
                    if(s != null){
                        ac.setShopUuid(s.getUuid());
                    }else {
                        ac.setShopUuid("");
                    }
                }
            }
        }

        // return json data
        DatatablesJsonObject jsonparse = new DatatablesJsonObject();
        jsonparse.setDraw(form.getDraw());
        jsonparse.setRecordsFiltered(totalCountFiltered);
        jsonparse.setRecordsTotal(totalCount);
        jsonparse.setData(dataList);
        logger.debug("----8----");
        return jsonparse;
    }

    // role admin
    @RequestMapping(value = "/admin/role/edit/{id}/", method = RequestMethod.GET)
    public String customerList(Model model, @PathVariable Long id) {

        RoleFrom roleFrom = new RoleFrom();
        AdminRole adminRole = adminService.selectById(id);
        AdminCustomer adminCustomer = adminService.selectAdminCustomerByRole(id);
        AdminUser adminUser = adminService.selectAdminUserByRole(id);

        if(adminCustomer == null){

        }else{
            roleFrom.setType(adminRole.getType());
            roleFrom.setLoginName(adminCustomer.getLoginName());
            roleFrom.setShowName(adminCustomer.getShowName());
            roleFrom.setPassword("");
            roleFrom.setFcType(adminCustomer.getFcType());
            roleFrom.setFcAdminId(adminCustomer.getFcAdminId());
            roleFrom.setCustomerType(adminCustomer.getCustomerType());
            roleFrom.setMail(adminCustomer.getMail());
            roleFrom.setStatus(adminCustomer.getStatus());
            roleFrom.setRole(adminCustomer.getRole());
            if(adminCustomer.getFcAdminId() != null && adminCustomer.getFcAdminId() != 0){
                Shop shop = shopService.getShopById(adminCustomer.getFcAdminId());
                if(shop != null){
                    roleFrom.setShopUuid(shop.getUuid());
                }
            }
        }
        if(adminUser == null){

        }else{
            roleFrom.setLoginName(adminUser.getLoginName());
            roleFrom.setShowName(adminUser.getShowName());
            roleFrom.setPassword(adminUser.getPassword());
            roleFrom.setMail(adminUser.getMail());
            roleFrom.setStatus(adminUser.getStatus());
            roleFrom.setRole(adminUser.getRole());
        }
        roleFrom.setName(adminRole.getName());
        roleFrom.setAction(adminRole.getAction());
        roleFrom.setPrivilege(adminRole.getPrivilege());

        model.addAttribute("roleFrom", roleFrom);
        return "admin/roleEdit";
    }

    @RequestMapping(value = "/admin/role/update/", method = RequestMethod.POST)
    public String placeUpdate(@ModelAttribute RoleFrom form,HttpServletRequest request) throws APIException {
        HttpSession session = request.getSession();
        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try {
                adminService.updateAdminRole(form);
            if(form.getType()==Constant.USER_TYPE.ADMIN_USER){
                adminService.updateAdminUser(form);
            }else if(form.getType()==Constant.USER_TYPE.CUSTOMER){
                adminService.updateAdminCustomer(form);
            }
            txManager.commit(txStatus);
            session.setAttribute("error",0);
        } catch (Exception e) {
            txManager.rollback(txStatus);
            session.setAttribute("error",1);
            logger.error("Update role failed!, error=" + e.getMessage());
            logger.error("Update role failed!, error=" + e.toString());
            e.printStackTrace();
        }
        return "redirect:/admin/role/list/";
    }
}
