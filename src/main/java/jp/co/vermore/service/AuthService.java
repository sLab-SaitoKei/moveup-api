package jp.co.vermore.service;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.JsonStatus;
import jp.co.vermore.common.mvc.APIException;
import jp.co.vermore.common.mvc.BaseService;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.common.util.StringUtil;
import jp.co.vermore.entity.*;
import jp.co.vermore.form.admin.CustomerListForm;
import jp.co.vermore.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * AuthService
 * Created by xieyoujun.
 * <p>
 * DateTime: 2018/03/21 16:41
 * Copyright: sLab, Corp
 */

@Service
public class AuthService extends BaseService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserForeignMapper userForeignMapper;

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private SessionIdMapper sessionIdMapper;

    @Autowired
    private OneTimeKeyMapper oneTimeKeyMapper;

    public long addUser() {
        User user = new User();
        user.setStatus(Constant.USER_STATUS.EFFECT);
        String uuid = StringUtil.getUuid();
        while (true) {
            boolean check = checkUuid(uuid);
            if (check == false) {
                break;
            } else {
                uuid = StringUtil.getUuid();
            }
        }
        user.setUuid(uuid);
        userMapper.insertUser(user);
        return user.getId();
    }


    public User getUserById(long id) {
        User entity = userMapper.getUserById(id);
        return entity;
    }

    public long updateUserWithdraw(Long id) {
        long userId = userMapper.updateUserWithdraw(id);
        return userId;
    }

    public long addUserForeign(UserForeign userForeign) {
        return userForeignMapper.insertUserForeign(userForeign);
    }

    public UserForeign getUserForeignByUserId(long userId) {
        UserForeign entity = userForeignMapper.getByUserId(userId);
        return entity;
    }

    public  List<UserForeign> getUserForeignByUserIdList(List<Long> userId) {
        List<UserForeign> entity = userForeignMapper.getUserForeignByUserIdList(userId);
        return entity;
    }

    public UserForeign getUserForeignByUserIdAndForeignType(long userId,int type) {
        UserForeign entity = userForeignMapper.getUserForeignByUserIdAndForeignType(userId,type);
        return entity;
    }


    public UserForeign getUserForeignByForeignTypeAndForeignId(int foreignType, String foreignId) {
        UserForeign entity = userForeignMapper.getByForeignTypeAndForeignId(foreignType, foreignId);
        return entity;
    }

    public UserForeign getUserForeignByForeignId(String foreignId) {
        UserForeign entity = userForeignMapper.getByForeignId(foreignId);
        return entity;
    }

    public UserForeign getUserForeignByForeignTypeAndForeignIdAndPassword(int foreignType, String foreignId, String password) {
        UserForeign entity = userForeignMapper.getByForeignTypeAndForeignIdAndPassword(foreignType, foreignId, password);
        return entity;
    }

    public int updateUserForeign(UserForeign uf) {
        return userForeignMapper.updateByPrimaryKey(uf);
    }

    public int deleteUserForeignByUserId(long userId) {
        return userForeignMapper.deleteByUserId(userId);
    }

    public int insertSessionIdByUuidAndSessionId(String uuid, String sessionId) {
        SessionId entity = new SessionId();
        entity.setUuid(uuid);
        entity.setSessionId(sessionId);
        entity.setExpireDatetime(DateUtil.getNowDate());
        int cnt = sessionIdMapper.insert(entity);
        return cnt;
    }


    public long getUserId(HttpServletRequest hsr) throws APIException {

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

        User user = null;
        if (uuid != null && sid != null) {
            SessionId sessionId = sessionIdMapper.selectByUuidAndSessionId(uuid, sid);
            if (sessionId == null) {
                logger.warn("User try to login with empty sessionId");
                throw new APIException(JsonStatus.ACCESS_DENY);
            }
            user = userMapper.getUserByUuid(uuid);
            if (user.getStatus() != Constant.USER_STATUS.EFFECT) throw new APIException(JsonStatus.ACCESS_DENY);
        }

        if (user == null) {
            logger.warn("User id  not find, uuid=" + uuid + ", sid=" + sid);
            throw new APIException(JsonStatus.ACCESS_DENY);
        }

        return user.getId();
    }

    public int getUserLoginType(HttpServletRequest hsr) throws APIException {
        long userId = this.getUserId(hsr);
        UserForeign userForeign = userForeignMapper.getByUserId(userId);
        if (userForeign == null) {
            logger.warn("userforeign id  not find, userId=" + userId);
        }
        return userForeign.getForeignType();
    }

    public int addMailForgetOneTimeKey(String otk, String mail) {
        OneTimeKey oneTimeKey = new OneTimeKey();
        oneTimeKey.setType((byte) Constant.ONE_TIME_KEY.MAIL_FORGET);
        oneTimeKey.setToken(otk);
        oneTimeKey.setMail(mail);
        long currentTime = System.currentTimeMillis() ;
        currentTime +=60*60*1000;
        Date date=new Date(currentTime);
        oneTimeKey.setExpireDatetime(date);
        return oneTimeKeyMapper.insert(oneTimeKey);
    }

    public int addMailRegistOneTimeKey(String otk, String mail) {
        OneTimeKey oneTimeKey = new OneTimeKey();
        oneTimeKey.setType((byte) Constant.ONE_TIME_KEY.MAIL_REGIST);
        oneTimeKey.setToken(otk);
        oneTimeKey.setMail(mail);
        long currentTime = System.currentTimeMillis() ;
        currentTime +=60*60*1000;
        Date date=new Date(currentTime);
        oneTimeKey.setExpireDatetime(date);
        return oneTimeKeyMapper.insert(oneTimeKey);
    }

    public OneTimeKey getOneTimeKeyByTypeAndToken(int type, String token) {
        return oneTimeKeyMapper.selectByTypeAndToken(type, token);
    }

    public Long delOneTimeKeyByTypeAndToken(Long id) {
        return oneTimeKeyMapper.deleteByPrimaryKey(id);
    }

    public long countUser() {
        return userMapper.count();
    }

    public List<Long> getUserIdListByIdList(List<String> idList) {
        return userMapper.getUserIdListByIdList(idList);
    }

    public List<Long> getUserIdList() {
        return userMapper.getUserIdList();
    }


    public List<Customer> getAdminAll() {
        return userMapper.getAdminAll();
    }

    public Customer getAdminByUserId(long id) {
        return userMapper.getAdminByUserId(id);
    }


    public List<AuthDetail> getCustomerAllByCondition(CustomerListForm form) {
        List<AuthDetail> authDetail = userMapper.getCustomerAllByCondition(form);
        return authDetail;
    }

    public int getCustomerCountByCondition(CustomerListForm form) {
        return userMapper.getCustomerCountByCondition(form);
    }

    public int getCustomerCount() {
        return userMapper.getCustomerCount();
    }


    public int addPerson(Person person) {
        return personMapper.insertPerson(person);
    }

    private boolean checkUuid(String uuid) {
        boolean exist;
        List<String> uuidList = userMapper.getUuidList();
        if (uuidList.contains(uuid)) {
            exist = true;
        } else {
            exist = false;
        }
        return exist;
    }

}
