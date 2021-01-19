package jp.co.vermore.mapper;

import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.entity.ShopNowgo;
import java.util.List;

public interface ShopNowgoMapper {

    List<ShopNowgo> getShopNowgoAll();

    List<ShopNowgo> getShopNowgo(String tomorrow,String today,int limit, int offset);

    List<ShopNowgo> getShopNowgoList(List<Long> idList);

    long updateShopNowgo(ShopNowgo entity);

    ShopNowgo getShopNowgoByShopId(Long id);

    int insertSelective(ShopNowgo entity);

    List<ShopNowgo> getShopNowgoListForNotify(String fiveMinuteAgo,String today);

    ShopNowgo getShopNowgoById(Long id);

    List<ShopNowgo> getShopNowgoForInvalid(String today,String tommorow);

    List<ShopNowgo> getShopNowgoForValid(String tommorow,String today);
}