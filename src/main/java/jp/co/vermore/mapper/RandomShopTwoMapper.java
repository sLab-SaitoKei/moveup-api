package jp.co.vermore.mapper;

import jp.co.vermore.entity.RandomShopOne;
import jp.co.vermore.entity.RandomShopTwo;
import java.util.List;

public interface RandomShopTwoMapper {

    List<RandomShopTwo> getRandomShopTwoAll(String tomorrow,String today);

    int deleteRandomShopTwo(List<Long> idList);

    int insertRandomShopTwo(RandomShopTwo shop);

    int updateRandomShopTwo(RandomShopTwo shop);

    List<RandomShopTwo> getShopRandomTwo(int shopType,String tomorrow,String today,int limit, int offset);

    List<RandomShopTwo> getShopRandomTwoForPlace(int shopType, int area, String tomorrow,String today, int limit, int offset);

    List<RandomShopTwo> getShopRandomTwoForNow4(int shopType, String tomorrow,String today, int limit, int offset);

    List<RandomShopTwo> getShopRandomTwoForNow5(int shopType, String tomorrow,String today, int limit, int offset);

    List<String> getRandomTwoUuidListByUuidFor(String uuid,int shopType,String tomorrow,String today);
}