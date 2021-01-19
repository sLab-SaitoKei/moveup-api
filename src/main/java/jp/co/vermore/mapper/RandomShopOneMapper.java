package jp.co.vermore.mapper;

import jp.co.vermore.entity.RandomShopOne;
import jp.co.vermore.entity.ShopNowgo;

import java.util.List;

public interface RandomShopOneMapper {

    int insertRandomShopOne(RandomShopOne shop);

    int updateRandomShopOne(RandomShopOne shop);

    List<RandomShopOne> getRandomShopOneAll(String tomorrow,String today);

    int deleteRandomShopOne(List<Long> idList);

    List<RandomShopOne> getShopRandomOne(int shopType,String tomorrow,String today,int limit, int offset);

    List<RandomShopOne> getShopRandomOneForPlace(int shopType,int area,String tomorrow,String today,int limit, int offset);

    List<RandomShopOne> getShopRandomOneForNow4(int shopType,String tomorrow,String today,int limit, int offset);

    List<RandomShopOne> getShopRandomOneForNow5(int shopType,String tomorrow,String today,int limit, int offset);

    List<String> getRandomOneUuidListByUuidFor(String uuid,int shopType,String tomorrow,String today);
}