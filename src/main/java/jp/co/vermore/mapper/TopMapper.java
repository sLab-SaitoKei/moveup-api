package jp.co.vermore.mapper;

import java.util.List;
import jp.co.vermore.entity.Top;
import org.apache.ibatis.annotations.Param;


public interface TopMapper {

    List<Top> getTopPic();

    List<Top> getTopForAdmin();

    int updateTop(Top top);

}