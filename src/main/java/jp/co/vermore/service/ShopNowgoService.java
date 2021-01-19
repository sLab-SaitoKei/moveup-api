package jp.co.vermore.service;

import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.entity.ShopCoupon;
import jp.co.vermore.entity.ShopNowgo;
import jp.co.vermore.mapper.ShopNowgoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * ShopNowgoService
 * Created by wubin.
 * * DateTime: 2018/06/25 13:38
 * Copyright: sLab, Corp
 */
@Service
public class ShopNowgoService {

    @Autowired
    private ShopNowgoMapper shopNowgoMapper;

    public List<ShopNowgo> getShopNowgoAll(){
        return shopNowgoMapper.getShopNowgoAll();
    }

    public List<ShopNowgo> getShopNowgoList(List<Long> shopIdList){
        return shopNowgoMapper.getShopNowgoList(shopIdList);
    }

    public List<ShopNowgo> getShopNowgo(int limit, int offset){
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        return shopNowgoMapper.getShopNowgo(tomorrow,today, limit,  offset);
    }

    public List<ShopNowgo> getShopNowgoForInvalid(){
        String tommorow = DateUtil.getTomorrow();
        String today = DateUtil.getYyyyMMdd();
        return shopNowgoMapper.getShopNowgoForInvalid(today,tommorow);
    }

    public List<ShopNowgo> getShopNowgoForValid(){
        String tommorow = DateUtil.getTomorrow();
        String today = DateUtil.getYyyyMMdd();
        return shopNowgoMapper.getShopNowgoForValid(tommorow,today);
    }

    public long updateShopNowgoByEntity(ShopNowgo entity) {
         shopNowgoMapper.updateShopNowgo(entity);
        return entity.getId();
    }

    public ShopNowgo getShopNowgoByShopId(long shopId) {
        return shopNowgoMapper.getShopNowgoByShopId(shopId);
    }

    public long insertShopNowgo(ShopNowgo entity){
        shopNowgoMapper.insertSelective(entity);
        return entity.getId();
    }

    public List<ShopNowgo> getShopNowgoListForNotify() {
        String today = DateUtil.getYyyyMMdd();
        String fiveMinuteAgo = DateUtil.getTimeByMinute(-5);
        return shopNowgoMapper.getShopNowgoListForNotify(fiveMinuteAgo,today);
    }

    public ShopNowgo getShopNowgoById(long id) {
        return shopNowgoMapper.getShopNowgoById(id);
    }
}
