package jp.co.vermore.service;

import java.util.Date;
import java.util.List;

import jp.co.vermore.common.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.vermore.entity.ShopCoupon;
import jp.co.vermore.mapper.ShopCouponMapper;

/**
 * ShopCouponService
 * Created by xieyoujun.
 * <p>
 * DateTime: 2018/04/04 15:50
 * Copyright: sLab, Corp
 */

@Service
public class ShopCouponService {

    @Autowired
    private ShopCouponMapper shopCouponMapper;

    public List<ShopCoupon> getShopCoupon(Long shopId){
        List<ShopCoupon> shopCoupon = shopCouponMapper.getShopCoupon(shopId);
        return shopCoupon;
    }
    public List<ShopCoupon> getShopCouponList(List<Long> shopIdList){
        return shopCouponMapper.getShopCouponList(shopIdList);
    }

    public List<ShopCoupon> getShopCouponAll(){
        return shopCouponMapper.getShopCouponAll();
    }

    public List<ShopCoupon> getShopCoupon2(int type){
//        String tommorowMin = DateUtil.getTomorrowMin();
//        String todayMin = DateUtil.getYyyyMMddMin();
//        String now = DateUtil.getNow();
        String nowMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(0);
        String nextMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(1);
        return shopCouponMapper.getShopCoupon2(type,nowMin,nextMin);
    }

    public List<ShopCoupon> getShopCouponForInvalid(){
        String nowMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(0);
        String nextMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(1);
        return shopCouponMapper.getShopCouponForInvalid(nowMin,nextMin);
    }

    public List<ShopCoupon> getShopCouponForValid(){
        String nowMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(0);
        String nextMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(1);
        return shopCouponMapper.getShopCouponForValid(nowMin,nextMin);
    }

    public ShopCoupon getCouponById(Long id){
        ShopCoupon entity = shopCouponMapper.getCouponById(id);
        return entity;
    }

    public ShopCoupon selectCouponById(Long id){
        Date pushDate = new Date(System.currentTimeMillis());
        String nowMin = DateUtil.getNow();
        ShopCoupon entity = shopCouponMapper.selectCouponById(id,nowMin);
        return entity;
    }

    public ShopCoupon getCouponByShopId(Long shopId){
        String nowMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(0);
        String nextMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(1);
        ShopCoupon entity = shopCouponMapper.getCouponByShopId(shopId,nowMin,nextMin);
        return entity;
    }

    public List<ShopCoupon> getListByShopId(long shopId, int limit, int offset) {
        return shopCouponMapper.getShopCouponListByCondition(shopId, limit, offset);
    }

    public List<ShopCoupon> getShopCouponListForBatch(Long shopId,String lastMonthOfdate) {
//        String yesterday = DateUtil.getYesterdayYyyy_MM_dd_HH_mm_ss();
//        String today = DateUtil.getTodayYyyy_MM_dd_HH_mm_ss();
        String tomorrow = DateUtil.getTomorrowYyyy_MM_dd_HH_mm_ss(lastMonthOfdate);
        String today = lastMonthOfdate +" "+"00:00:00";
        return shopCouponMapper.getShopCouponListForBatch(shopId,tomorrow,today);
    }

    public List<ShopCoupon> getShopCouponListForNotify() {
        String nowMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(0);
        String nextMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(1);
        String fiveMinuteAgo = DateUtil.getTimeByMinute(-5);
        return shopCouponMapper.getShopCouponListForNotify(fiveMinuteAgo,nowMin,nextMin);
    }

    public List<Long> getShopIdListForBatch(String lastMonthOfdate) {
        String tomorrow = DateUtil.getTomorrowYyyy_MM_dd_HH_mm_ss(lastMonthOfdate);
        String today = lastMonthOfdate +" "+"00:00:00";
//        String yesterday = DateUtil.getYesterdayYyyy_MM_dd_HH_mm_ss();
//        String today = DateUtil.getTodayYyyy_MM_dd_HH_mm_ss();
        return shopCouponMapper.getShopIdListForBatch(tomorrow,today);
    }

    public long add(ShopCoupon shopCoupon) {
         shopCouponMapper.insert(shopCoupon);
        return shopCoupon.getId();
    }

    public long update(ShopCoupon shopCoupon) {
         shopCouponMapper.updateByPrimaryKey(shopCoupon);
        return shopCoupon.getId();
    }

    public List<ShopCoupon> getShopCouponListNowFor(String uuid){
        String nowMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(0);
        String nextMin= DateUtil.getTimeByMinuteYyyy_MM_ddHHmm(1);
         List<ShopCoupon> shopCp  =  shopCouponMapper.getShopCouponListNowFor(uuid,nowMin,nextMin);
         return shopCp;
    }

}
