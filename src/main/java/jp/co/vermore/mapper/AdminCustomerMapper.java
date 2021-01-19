package jp.co.vermore.mapper;

import jp.co.vermore.entity.AdminCustomer;
import jp.co.vermore.entity.AdminUser;
import jp.co.vermore.entity.Customer;
import jp.co.vermore.form.admin.RoleListForm;

import java.util.List;

public interface AdminCustomerMapper {

    List<AdminCustomer> selectAll();

    AdminCustomer selectByRole(Long role);

    int deleteAdminCustomerByShopIdAndType(long shopId, int type);

    int insertAdminCustomer(AdminCustomer adminCustomer);

    Customer selectByPrimaryKey(Integer id);

    int updateAdminCustomer(AdminCustomer customer);

    List<AdminCustomer> getAdminCustomerAllByCondition(RoleListForm form);

    int getAdminCustomerCountByCondition(RoleListForm form);

    int getAdminCustomerCount();

    AdminCustomer selectByLoginNameAndPassword(String loginName, String password);

    AdminCustomer getAdminCustomerByShopIdAndType(Long shopId, int customerType);

    AdminCustomer selectByLoginName(String loginName);

    List<AdminCustomer> selectByShopIdAndType(Long shopId,int type);
}