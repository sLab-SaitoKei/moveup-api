package jp.co.vermore.mapper;

import java.util.List;
import jp.co.vermore.entity.UserSetting;
import org.apache.ibatis.annotations.Param;

public interface UserSettingMapper {

   int insertUserSetting(UserSetting userSetting);

   List<UserSetting> getUserSettingById(long userId);

   List<UserSetting> getUserSettingBySettingKeyAndSettingValue(String settingKey,String settingValue);

   UserSetting getUserSettingBySettingKeyAndSettingValueAndByItemId(Long userId,String settingKey,String settingValue,long itemId);

   UserSetting findKeyByUserIdAndTypeAndShopId(Long userId , int type, Long shopId ,String settingKey);

   UserSetting findKeyByUserId(@Param("userId") long userId ,@Param("settingKey") String settingKey);

   List<UserSetting> findUserIdByKey(@Param("settingKey") String settingKey);

   void updateUserSetting(@Param("userId") long userId ,@Param("settingKey") String settingKey,@Param("settingValue") String settingValue,@Param("itemId") long itemId ,@Param("itemType") Integer itemType );

//   void insertUserSetting2(long userId , String settingKey,String settingValue);

   List<UserSetting> findUserIdBySettingKey(@Param("settingKey") String settingKey);

}