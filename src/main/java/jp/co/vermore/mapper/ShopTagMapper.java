package jp.co.vermore.mapper;

import java.util.List;

import jp.co.vermore.entity.ShopTag;

public interface ShopTagMapper {

    List<Integer> getContent(Long shopId,Integer moduleType, Integer tagType);

    int insertShopTag(ShopTag shopTag);

    int updateShopTag(ShopTag shopTag);

    int deleteShopTag(Long shopId);

    int deleteShopTags(Long shopId,Integer moduleType,Integer type);

    List<Long> getShopId(Integer shopType);

    List<ShopTag> getShopTagList(List<Long> idList,Integer moduleType, List<Integer> tagList);

    List<Long> getShopIdByContent(Integer shopType,Integer tagType, Integer content);

    List<Long> getShopIdByContents(Integer shopType,Integer tagType,List<Integer> list);

    List<Integer> getContents(Long shopId, Integer shopType, Integer tagType);

    List<ShopTag> getShopTagAllByShopIdListAndCondition(List<Long> shopIdList, int moduleType, int tagType, List<Integer> contentList);
}