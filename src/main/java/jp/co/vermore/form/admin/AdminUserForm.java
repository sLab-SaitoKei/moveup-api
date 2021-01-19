package jp.co.vermore.form.admin;

import jp.co.vermore.entity.AdminRole;
import jp.co.vermore.entity.AdminUser;

import java.util.List;

public class AdminUserForm {
    private AdminUser user;
    private List<AdminRole> roleList;

    public AdminUser getUser() {
        return user;
    }

    public void setUser(AdminUser user) {
        this.user = user;
    }

    public List<AdminRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<AdminRole> roleList) {
        this.roleList = roleList;
    }
}
