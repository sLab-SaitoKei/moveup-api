package jp.co.vermore.mapper;

import jp.co.vermore.entity.NotificationContent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NotificationContentMapper {

    int insertSelective(NotificationContent record);

    NotificationContent selectByPrimaryKey(Long id);

}

//    int countByExample(NotificationContent example);
//
//    int deleteByExample(NotificationContent example);
//
//    int deleteByPrimaryKey(Integer id);
//
//    int insert(NotificationContent record);

//    int updateByExampleSelective(@Param("record") NotificationContent record, @Param("example") NotificationContent example);
//
//    int updateByExample(@Param("record") NotificationContent record, @Param("example") NotificationContent example);
//
//    int updateByPrimaryKeySelective(NotificationContent record);
//
//    int updateByPrimaryKey(NotificationContent record);