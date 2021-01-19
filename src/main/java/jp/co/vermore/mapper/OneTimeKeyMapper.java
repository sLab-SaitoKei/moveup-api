package jp.co.vermore.mapper;

import jp.co.vermore.entity.OneTimeKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OneTimeKeyMapper {

    Long deleteByPrimaryKey(Long id);

    int insert(OneTimeKey record);

    OneTimeKey selectByTypeAndToken(@Param("type") int type, @Param("token") String token);

}