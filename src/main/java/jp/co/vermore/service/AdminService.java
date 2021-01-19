package jp.co.vermore.service;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.JsonStatus;
import jp.co.vermore.common.mvc.APIException;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.common.util.StringUtil;
import jp.co.vermore.entity.*;
import jp.co.vermore.form.admin.*;
import jp.co.vermore.mapper.AdminCustomerMapper;
import jp.co.vermore.mapper.AdminRoleMapper;
import jp.co.vermore.mapper.AdminUserMapper;
import jp.co.vermore.mapper.SessionIdMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * AdminService
 * Created by Wangyan.
 * <p>
 * DateTime: 2018/03/23 12:46
 * Copyright: sLab, Corp
 */
@Service
public class AdminService extends BaseController {

    @Autowired
    private AdminUserMapper adminUserMapper;

    @Autowired
    private AdminCustomerMapper adminCustomerMapper;

    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Autowired
    private SessionIdMapper sessionIdMapper;

    @Autowired
    private ShopService shopService;

    @Autowired
    private CorporateInfoService corporateInfoService;

    @Autowired
    private RecruitService recruitService;


    public List<AdminRole> getAdminRoleByType(Byte type) {
        AdminRoleExample example = new AdminRoleExample();
        AdminRoleExample.Criteria criteria = example.createCriteria();
        criteria.andTypeEqualTo(type);
        return adminRoleMapper.selectByExample(example);
    }



    @Deprecated // todo: this method is no longer in use
    public AdminUser login(String loginName, String password) {
        AdminUser entity = adminUserMapper.login(loginName, password);
        return entity;
    }

    public AdminCustomer getAdminCustomerByLoginNameAndPassword(String loginName, String password) {
        return adminCustomerMapper.selectByLoginNameAndPassword(loginName, password);
    }

    public AdminUser getAdminUserByLoginNameAndPassword(String loginName, String password) {
        AdminUserExample adminUserExample = new AdminUserExample();
        AdminUserExample.Criteria criteria = adminUserExample.createCriteria();
        criteria.andLoginNameEqualTo(loginName);
        criteria.andPasswordEqualTo(password);
        List<AdminUser> resultList = adminUserMapper.selectByExample(adminUserExample);
        return resultList.size() > 0 ? resultList.get(0) : null;
    }


    public AdminCustomer getAdminCustomerByShopIdAndType(Long shopId, int customerType) {
        return adminCustomerMapper.getAdminCustomerByShopIdAndType(shopId, customerType);
    }

    public long getAdminUserId(HttpServletRequest hsr) throws APIException {

        String sid = "";
        sid = hsr.getHeader(Constant.SESSION.ADMIN_SESSIONID);
        logger.debug("--1--sessionId=" + sid);

        if (sid == null || sid.isEmpty()) {
            Cookie cookies[] = hsr.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (c.getName().equals(Constant.SESSION.ADMIN_SESSIONID)) sid = c.getValue();
                }
            }
            logger.debug("--2--sessionId=" + sid);
        }

        AdminCustomer adminCustomer = null;
        SessionId sessionId = null;
        if (sid != null) {
            sessionId = sessionIdMapper.selectBySessionId(sid);
            if (sessionId == null) throw new APIException(JsonStatus.ACCESS_DENY);
        } else throw new APIException(JsonStatus.ACCESS_DENY);

        adminCustomer = adminCustomerMapper.selectByLoginName(sessionId.getUuid());
        if (adminCustomer == null) throw new APIException(JsonStatus.ACCESS_DENY);

        return adminCustomer.getId();
    }

    public AdminCustomer getAdminCustomer(HttpServletRequest hsr) throws APIException {

        String sid = "";
        sid = hsr.getHeader(Constant.SESSION.ADMIN_SESSIONID);
        logger.debug("--1--sessionId=" + sid);

        if (sid == null || sid.isEmpty()) {
            Cookie cookies[] = hsr.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (c.getName().equals(Constant.SESSION.ADMIN_SESSIONID)) sid = c.getValue();
                }
            }
            logger.debug("--2--sessionId=" + sid);
        }

        AdminCustomer adminCustomer = null;
        SessionId sessionId = null;
        if (sid != null) {
            sessionId = sessionIdMapper.selectBySessionId(sid);
            if (sessionId == null) throw new APIException(JsonStatus.ACCESS_DENY);
        } else throw new APIException(JsonStatus.ACCESS_DENY);

        adminCustomer = adminCustomerMapper.selectByLoginName(sessionId.getUuid());
        if (adminCustomer == null) throw new APIException(JsonStatus.ACCESS_DENY);

        return adminCustomer;
    }

    public List<Long> getAdminUserIdListByIdList(List<String> idList) {
        return adminUserMapper.getAdminUserIdListByIdList(idList);
    }

    public List<Long> getAdminUserIdList() {
        return adminUserMapper.getAdminUserIdList();
    }

    public AdminCustomer insertAdminCustomer(ShopRegistForm form) {
        AdminCustomer adminCustomer = new AdminCustomer();
        Shop shop = shopService.getShopByUuid(form.getSearchId());
        if (form.getAdminShop() == Constant.CUSTOMER.SHOP) {
            adminCustomer.setLoginName("SP" + form.getUuid());
            if(shop != null){
                adminCustomer.setFcAdminId(shop.getId());
            }else{
                adminCustomer.setFcAdminId(0L);
            }
            adminCustomer.setShowName(form.getName());
        } else  {
            adminCustomer.setLoginName("SPA" + form.getUuid());
            adminCustomer.setFcAdminId((long) 0);
            adminCustomer.setShowName(form.getName() + "管理店");
        }

        adminCustomer.setShopId(form.getShopId());
        adminCustomer.setStatus((byte) Constant.CUSTOMER.EFFECTIVE);
        adminCustomer.setFcType((byte) form.getAdminShop());
        adminCustomer.setCustomerType((byte) Constant.CUSTOMER.CUSTOMER_SHOP);
        adminCustomer.setMail(form.getMail());
        String pass = StringUtil.getPswd();
        adminCustomer.setPassword(StringUtil.sha256(pass));
        adminCustomer.setPwd(pass);
        adminCustomer.setRole(form.getRole());
        adminCustomer.setCreateDatetime(new Date(System.currentTimeMillis()));
        adminCustomerMapper.insertAdminCustomer(adminCustomer);
        return adminCustomer;
    }

    public AdminCustomer insertAdminCustomer(CorporateInfoForm form) {
        AdminCustomer adminCustomer = new AdminCustomer();
        Shop shop = shopService.getShopByUuid(form.getSearchId());
        if (form.getAdminShop() == Constant.CUSTOMER.SHOP) {
            adminCustomer.setLoginName("SP" + form.getUuid());
            if(shop != null){
                adminCustomer.setFcAdminId(shop.getId());
            }else{
                adminCustomer.setFcAdminId(0L);
            }
            adminCustomer.setShowName(form.getName());
        } else  {
            adminCustomer.setLoginName("SPA" + form.getUuid());
            adminCustomer.setFcAdminId((long) 0);
            adminCustomer.setShowName(form.getName() + "管理店");
        }
        adminCustomer.setShopId(form.getShopId());
        adminCustomer.setStatus((byte) Constant.CUSTOMER.EFFECTIVE);
        adminCustomer.setFcType((byte) form.getAdminShop());
        adminCustomer.setCustomerType((byte) Constant.CUSTOMER.CUSTOMER_SHOP);
        adminCustomer.setMail(form.getMail());
        String pass = StringUtil.getPswd();
        adminCustomer.setPassword(StringUtil.sha256(pass));
        adminCustomer.setPwd(pass);
        adminCustomer.setRole(form.getRole());
        adminCustomer.setCreateDatetime(new Date(System.currentTimeMillis()));
        adminCustomerMapper.insertAdminCustomer(adminCustomer);
        return adminCustomer;
    }

    public AdminCustomer insertAdminCustomer(RecruitForm form) {
        AdminCustomer adminCustomer = new AdminCustomer();
        Shop shop = shopService.getShopByUuid(form.getSearchId());
        if (form.getAdminShop() == Constant.CUSTOMER.SHOP) {
            adminCustomer.setLoginName("SP" + form.getUuid());
            if(shop != null){
                adminCustomer.setFcAdminId(shop.getId());
            }else{
                adminCustomer.setFcAdminId(0L);
            }
            adminCustomer.setShowName(form.getRecruitName());
        } else  {
            adminCustomer.setLoginName("SPA" + form.getUuid());
            adminCustomer.setFcAdminId((long) 0);
            adminCustomer.setShowName(form.getRecruitName() + "管理店");
        }

        adminCustomer.setShopId(form.getRecruitId());
        adminCustomer.setStatus((byte) Constant.CUSTOMER.EFFECTIVE);
        adminCustomer.setFcType((byte) form.getAdminShop());
        adminCustomer.setCustomerType((byte) Constant.CUSTOMER.CUSTOMER_RECRUIT);
        adminCustomer.setMail(form.getMail());
        String pass = StringUtil.getPswd();
        adminCustomer.setPassword(StringUtil.sha256(pass));
        adminCustomer.setPwd(pass);
        adminCustomer.setRole(form.getRole());
        adminCustomer.setCreateDatetime(new Date(System.currentTimeMillis()));
        adminCustomerMapper.insertAdminCustomer(adminCustomer);
        return adminCustomer;
    }

    public Long insertAdminRole(ShopRegistForm form) {
        AdminRole role = new AdminRole();
        if (form.getAdminShop() == Constant.CUSTOMER.SHOP) {
            form.setLoginName("SP" + form.getUuid());

        } else  {
            form.setLoginName("SPA" + form.getUuid());
        }
        if(form.getLoginName() == null){
            role.setName("");
        }else{
            role.setName(form.getLoginName());
        }
        role.setType((byte) Constant.USER_TYPE.CUSTOMER);
        role.setAction(0);//TODO
        role.setPrivilege(Constant.USER_PRIVILEGE.WRITE);
        role.setCreateDatetime(new Date(System.currentTimeMillis()));
        adminRoleMapper.insertAdminRole(role);
        return role.getId();
    }

    public Long insertAdminRole(CorporateInfoForm form) {
        AdminRole role = new AdminRole();
        if (form.getAdminShop() == Constant.CUSTOMER.SHOP) {
            form.setLoginName("SP" + form.getUuid());

        } else  {
            form.setLoginName("SPA" + form.getUuid());

        }
        if(form.getLoginName() == null){
            role.setName("");
        }else{
            role.setName(form.getLoginName());
        }
        role.setType((byte) Constant.USER_TYPE.CUSTOMER);
        role.setAction(0);//TODO
        role.setPrivilege(Constant.USER_PRIVILEGE.WRITE);
        role.setCreateDatetime(new Date(System.currentTimeMillis()));
        adminRoleMapper.insertAdminRole(role);
        return role.getId();
    }

    public Long insertAdminRole(RecruitForm form) {
        AdminRole role = new AdminRole();
        if (form.getAdminShop() == Constant.CUSTOMER.SHOP) {
            form.setLoginName("SP" + form.getUuid());
        } else {
            form.setLoginName("SPA" + form.getUuid());
        }
        if(form.getLoginName() == null){
            role.setName("");
        }else{
            role.setName(form.getLoginName());
        }
        role.setType((byte) Constant.USER_TYPE.CUSTOMER);
        role.setAction(0);//TODO
        role.setPrivilege(Constant.USER_PRIVILEGE.WRITE);
        role.setCreateDatetime(new Date(System.currentTimeMillis()));
        adminRoleMapper.insertAdminRole(role);
        return role.getId();
    }

    public List<AdminCustomer> selectAll() {
        List<AdminCustomer> list = adminCustomerMapper.selectAll();
        return list;
    }

    public AdminCustomer selectAdminCustomerByRole(Long role) {
        AdminCustomer entity = adminCustomerMapper.selectByRole(role);
        return entity;
    }

    public List<AdminCustomer> selectByShopIdAndType(Long shopId,int type) {
        return adminCustomerMapper.selectByShopIdAndType(shopId,type);
    }

    public List<AdminCustomer> getAdminCustomerAllByCondition(RoleListForm form) {
        List<AdminCustomer> list = adminCustomerMapper.getAdminCustomerAllByCondition(form);
        return list;
    }

    public int getAdminCustomerCountByCondition(RoleListForm form) {
        return adminCustomerMapper.getAdminCustomerCountByCondition(form);
    }

    public int getAdminCustomerCount() {
        return adminCustomerMapper.getAdminCustomerCount();
    }

    public AdminRole selectById(Long id) {
        AdminRole entity = adminRoleMapper.selectById(id);
        return entity;
    }

    public AdminUser selectAdminUserByRole(Long role) {
        AdminUser entity = adminUserMapper.selectByRole(role);
        return entity;
    }

    public Long updateAdminRole(RoleFrom form) {
        AdminRole role = new AdminRole();
        role.setId(form.getRole());
        role.setType(form.getType());
//        role.setAction(form.getAction());
        role.setPrivilege(form.getPrivilege());
        role.setUpdateDatetime(new Date(System.currentTimeMillis()));
        role.setDelFlg(Boolean.FALSE);
        role.setNote(Constant.EMPTY_STRING);
        adminRoleMapper.updateAdminRole(role);
        return role.getId();
    }

    public Long updateAdminCustomer(RoleFrom form) {
        AdminCustomer customer = new AdminCustomer();
        customer.setLoginName(form.getLoginName());
        customer.setShowName(form.getShowName());
        if(form.getPassword()!=null&&form.getPassword()!=""){
            customer.setPassword(StringUtil.sha256(form.getPassword()));
        }
        if(form.getShopUuid() != null && form.getShopUuid() != ""){
            Shop shop = shopService.getShopByUuid(form.getShopUuid());
            if(shop != null){
                customer.setFcAdminId(shop.getId());
            }
        }else {
            customer.setFcAdminId(0L);
        }
        customer.setFcType(form.getFcType());
        customer.setCustomerType(form.getCustomerType());
        customer.setStatus(form.getStatus());
        customer.setMail(form.getMail());
        customer.setRole(form.getRole());
        customer.setUpdateDatetime(new Date(System.currentTimeMillis()));
        customer.setDelFlg(Boolean.FALSE);
        customer.setNote(Constant.EMPTY_STRING);
        adminCustomerMapper.updateAdminCustomer(customer);
        return customer.getId();
    }

    public Long updateAdminUser(RoleFrom form) {
        AdminUser user = new AdminUser();
        user.setLoginName(form.getLoginName());
        user.setShowName(form.getShowName());
        user.setPassword(StringUtil.sha256(form.getPassword()));
        user.setStatus(form.getStatus());
        user.setMail(form.getMail());
        user.setRole(form.getRole());
        user.setUpdateDatetime(new Date(System.currentTimeMillis()));
        user.setDelFlg(Boolean.FALSE);
        user.setNote(Constant.EMPTY_STRING);
        adminUserMapper.updateAdminUser(user);
        return user.getId();
    }

    public int updateAdminCustomerByAdminId(AdminCustomer customer) {
        return adminCustomerMapper.updateAdminCustomer(customer);
    }

    public int deleteAdminCustomerByShopIdAndType(long shopId, int type) {
        return adminCustomerMapper.deleteAdminCustomerByShopIdAndType(shopId,type);
    }
}
