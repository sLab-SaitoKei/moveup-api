package jp.co.vermore.mapper;

import jp.co.vermore.entity.Notification;
import jp.co.vermore.entity.NotificationEntire;
import jp.co.vermore.form.admin.NotifyListForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NotificationMapper {

    int insertSelective(Notification record);

    Notification selectByPrimaryKey(Long id);

    Notification selectById(Long id);

    int updateByPrimaryKeySelective(Notification record);

    List<Notification> getNotifyByUserType(Notification record);

    List<Notification> getBadgeNumForAdmin(long userId);

    List<Notification> getBadgeNumForUser(long userId);

    List<Notification> getNotifyForIosByPushStatus();

    List<Notification> getNotifyForAndByPushStatus();

    List<Notification> getNotifyByUserId(Long userId, int userType,int limit, int offset);

    List<Notification> getNotifyCountByUserId(Long userId, int userType);

    List<Notification> getNotifyForMypage(Long userId, int userType);

    List<Notification> getNotifyForNav(Long userId, int userType ,Byte notifyStatus);

    List<NotificationEntire> getNotifyEntireAllByCondition(NotifyListForm form);

    int getNotifyCountByCondition(NotifyListForm form);

    int getNotifyCount();

    List<NotificationEntire> getNotifyEntireAllByUserIdAndUserType(Long user_id, int userType, int limit, int offset);

    int deleteById(Notification record);

    int getNotifyUnreadCountByCondition(Long id, int userType);

    int updateNotifyItem();
}