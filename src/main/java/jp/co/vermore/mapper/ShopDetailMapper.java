package jp.co.vermore.mapper;

import java.util.List;

import jp.co.vermore.entity.ShopDetail;

public interface ShopDetailMapper {

    ShopDetail getShopDetailById(Long id);

    List<ShopDetail> getShopDetailListById(List<Long> idList);

    ShopDetail getShopDetailByShopId(Long id);

    List<ShopDetail> getShopDetailByShopIdList(List<Long> idList);

    Long getShopListId(Long id);

    int getShopType(Long shopListId);

    Long getShopDetailId(Long shopListId);

    List<Long> getShopDetailIdList(List<Long> idList);

    int insertShopDetail(ShopDetail shopDetail);

    int updateShopDetail(ShopDetail shopDetail);

    int deleteShopDetail(Long id);

    int deleteShopDetailVideo(Long id);

    int deleteShopDetailFlic(Long id);

    List<ShopDetail> getShopListAdmin();

    List<ShopDetail> getShopDetailByNowGo();

    List<ShopDetail> getShopDetailJsonAll(Long shopListId);

    List<ShopDetail> getShopDetailAll();

}