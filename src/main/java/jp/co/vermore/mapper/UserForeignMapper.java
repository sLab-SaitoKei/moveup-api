package jp.co.vermore.mapper;

import jp.co.vermore.entity.UserForeign;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by xieyoujun on 2017/11/20.
 */
public interface UserForeignMapper {

    int insertUserForeign(UserForeign userForeign);

    UserForeign getByMail(String mail);

    UserForeign getByUserId(long userId);

    List<UserForeign> getUserForeignByUserIdList(List<Long> userIdList);

    UserForeign getUserForeignByUserIdAndForeignType(long userId,int type);

    UserForeign getByForeignId( @Param("foreignId") String foreignId);

    UserForeign getByForeignTypeAndForeignId(@Param("foreignType") int foreignType, @Param("foreignId") String foreignId);

    UserForeign getByForeignTypeAndForeignIdAndPassword(@Param("foreignType") int foreignType, @Param("foreignId") String foreignId, @Param("password") String password);

    int updateByPrimaryKey(UserForeign record);

    int deleteByUserId(long userId);
}
