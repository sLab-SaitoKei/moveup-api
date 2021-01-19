package jp.co.vermore.mapper;

import jp.co.vermore.entity.Customer;
import jp.co.vermore.entity.AuthDetail;
import jp.co.vermore.entity.User;
import jp.co.vermore.form.admin.CustomerListForm;

import java.util.List;

/**
 * Created by xieyoujun on 2017/11/20.
 */
public interface UserMapper {

    Long insertUser(User user);

    Long count();

    int updateUserWithdraw(long id);

    User getUserById(long id);

    User getUserByUuid(String uuid);

    List<String> getUuidList();

    List<Long> getUserIdListByIdList(List<String> idList);

    List<Long> getUserIdList();

    List<Customer> getAdminAll();

    Customer getAdminByUserId(long id);

    List<AuthDetail> getCustomerAllByCondition(CustomerListForm form);

    int getCustomerCountByCondition(CustomerListForm form);

    int getCustomerCount();

}
