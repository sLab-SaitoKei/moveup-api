package jp.co.vermore.mapper;

import java.util.List;

import jp.co.vermore.entity.Shop;
import jp.co.vermore.entity.ShopForList;
import jp.co.vermore.form.admin.ShopListForm;
import jp.co.vermore.validation.CorporateInfoListParams;
import jp.co.vermore.validation.ShopListParams;
import org.apache.ibatis.annotations.Param;

public interface ShopMapper {

    int insertShop(Shop shop);

    int updateShop(Shop shop);

    int updateShopForNow4ByIdList(List<Long> idList,boolean now4);

    int updateShopForNow5ByIdList(List<Long> idList,boolean now5);

    Long deleteShop(Shop shop);

    List<Shop> getShopJsonAll(int shopType,String tomorrow,String today,int limit, int offset);

    List<Shop> getShopByCoupon(int shopType,String tomorrow,String today,int limit, int offset);

    List<Shop> getShopByCoupon2(List<Long> idList,int shopType,String tomorrow,String today,int limit, int offset);

    List<Shop> getShopByCoupon3(List<Long> idList,int shopType,String tomorrow,String today);

    List<Shop> getShopAllByCoupon(int shopType,String tomorrow,String today);

    List<Shop> getShopByNow5(int shopType,String tomorrow,String today,int limit, int offset);

    List<Shop> getShopByNow5All(int shopType,String tomorrow,String today);

    List<Shop> getCorporate();

    List<Long> getShopIdByKeyword(Integer shopType,String Keyword);

    List<Long> getShopIdByNow4(Integer shopType,Integer now4);

    List<Long> getShopIdByNow5(Integer shopType,Integer now5);

    List<Long> getShopId(Integer shopType);

    List<Shop> getShopList(ShopListParams params);

    List<Shop> getCorporateInfoList(CorporateInfoListParams params);

    Shop getShopById(Long shopListId);

    List<Shop> getShopByIdList(List<Long> shopIdList,String tomorrow,String today);

    Shop getShopByUuid(String uuid);

    List<Shop> getShopByUuidList(List<String> list);

    Shop getShopByIdAndType(Long shopListId,int shopType);

    List<Shop> getShopAll();

    List<Shop> getShopForSettlementBatch(String monthOfFirst,String LastmonthFirst);

    List<Shop> getShopAllForRandom();

    Long count();

    List<Shop> getShopAllByIdListAndCondition(List<Long> idList, String name, int shopType);

    List<ShopForList> getShopAllByCondition(ShopListForm form);

    int getShopCountByCondition(ShopListForm form);

    int getShopCount();

//    By wangyu ↓
    List<Long> getIdBybusiness(Integer shopType, Integer tagType, Integer content);

    List<Shop> getCoordinate();

    String getShopUuidByShopIdShop(Long id);

    String getShopNameByShopIdShop(Long id);

//    By wangyu ↑

    Long getShopIdByUUID(String shopUUID);

    List<Shop> getShopAllCount(int shopType,String tomorrow,String today);

    List<Shop> getShopListCount(ShopListParams params);
}