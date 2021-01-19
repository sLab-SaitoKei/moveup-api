package jp.co.vermore.mapper;

import java.util.List;
import jp.co.vermore.entity.Demo;

/**
 * Created by xieyoujun on 2017/11/20.
 */
public interface DemoMapper {
    Demo getDemo(long id);
    Demo getDemoByEntity(Demo dto);
    List<Demo> getDemoAll();
    int insertDemo(String name);
    int deleteDemo(long id);
    int updateDemo(Demo dto);
}
