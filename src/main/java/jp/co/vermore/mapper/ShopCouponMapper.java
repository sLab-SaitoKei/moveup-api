package jp.co.vermore.mapper;

import java.util.Date;
import java.util.List;
import jp.co.vermore.entity.ShopCoupon;
import org.apache.ibatis.annotations.Param;

public interface ShopCouponMapper {

    long insert(ShopCoupon record);

    long updateByPrimaryKey(ShopCoupon record);

    ShopCoupon getCouponById(Long id);

    ShopCoupon selectCouponById(Long id,String now);

    ShopCoupon getCouponByShopId(Long shopId,String nowMin,String nextMin);

    List<ShopCoupon> getShopCoupon(Long shopId);

    List<ShopCoupon> getShopCoupon2(int type,String nowMin,String nextMin);

    List<ShopCoupon> getShopCouponList(List<Long> idList);

    List<ShopCoupon> getShopCouponListByCondition(long shopId, int limit, int offset);

    List<ShopCoupon> getShopCouponListForBatch(Long shopId,String tomorrow,String today);

    List<ShopCoupon> getShopCouponListForNotify(String fiveMinuteAgo, String nowMin,String nextMin);

    List<Long> getShopIdListForBatch(String tomorrow,String today);

    List<ShopCoupon> getShopCouponListNowFor(String uuid,String nowMin,String nextMin);

    List<ShopCoupon> getShopCouponAll();

    List<ShopCoupon> getShopCouponForInvalid(String nowMin,String nextMin);

    List<ShopCoupon> getShopCouponForValid(String nowMin,String nextMin);
}