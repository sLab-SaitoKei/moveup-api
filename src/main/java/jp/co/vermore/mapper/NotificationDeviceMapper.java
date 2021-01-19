package jp.co.vermore.mapper;

import jp.co.vermore.entity.NotificationDevice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NotificationDeviceMapper {

    int insertSelective(NotificationDevice record);

    int updateByUserId(NotificationDevice record);

    List<Long> selectAllUserId();

    List<NotificationDevice> getNotificationDeviceByUserType(NotificationDevice notifyDevice);

    NotificationDevice getNotificationDeviceByUserId(long userId,int userType,byte os);

}