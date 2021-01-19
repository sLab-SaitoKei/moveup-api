package jp.co.vermore.service;

import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.entity.Demo;
import jp.co.vermore.entity.RandomShopOne;
import jp.co.vermore.entity.RandomShopTwo;
import jp.co.vermore.entity.ShopNowgo;
import jp.co.vermore.mapper.DemoMapper;
import jp.co.vermore.mapper.RandomShopOneMapper;
import jp.co.vermore.mapper.RandomShopTwoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wubin on 2018/08/08.
 */

@Service
public class RandomShopService {
    @Autowired
    private RandomShopOneMapper randomShopOneMapper;

    @Autowired
    private RandomShopTwoMapper randomShopTwoMapper;

    public int insertRandomShopOne(RandomShopOne randomShop){
        return  randomShopOneMapper.insertRandomShopOne(randomShop);
    }

    public int updateRandomShopOne(RandomShopOne randomShop){
        return  randomShopOneMapper.updateRandomShopOne(randomShop);
    }

    public List<RandomShopOne> getRandomShopOneAll(){
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        return  randomShopOneMapper.getRandomShopOneAll(tomorrow,today);
    }

    public int deleteRandomShopOne(List<Long> idList){
        return  randomShopOneMapper.deleteRandomShopOne(idList);
    }

    public List<RandomShopTwo> getRandomShopTwoAll(){
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        return  randomShopTwoMapper.getRandomShopTwoAll(tomorrow,today);
    }

    public int deleteRandomShopTwo(List<Long> idList){
        return  randomShopTwoMapper.deleteRandomShopTwo(idList);
    }

    public int insertRandomShopTwo(RandomShopTwo randomShop){
        return  randomShopTwoMapper.insertRandomShopTwo(randomShop);
    }

    public int updateRandomShopTwo(RandomShopTwo randomShop){
        return  randomShopTwoMapper.updateRandomShopTwo(randomShop);
    }

    public List<RandomShopOne> getShopRandomOne(int shopType,int limit, int offset) {
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        List<RandomShopOne> list = randomShopOneMapper.getShopRandomOne(shopType,tomorrow,today,limit,offset);
        return list;
    }

    public List<RandomShopOne> getShopRandomOneForPlace(int shopType,int area ,int limit, int offset) {
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        List<RandomShopOne> list = randomShopOneMapper.getShopRandomOneForPlace(shopType,area,tomorrow,today,limit,offset);
        return list;
    }

    public List<RandomShopTwo> getShopRandomTwoForPlace(int shopType,int area ,int limit, int offset) {
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        List<RandomShopTwo> list = randomShopTwoMapper.getShopRandomTwoForPlace(shopType,area,tomorrow,today,limit,offset);
        return list;
    }

    public List<RandomShopOne> getShopRandomOneForNow4(int shopType,int limit, int offset) {
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        List<RandomShopOne> list = randomShopOneMapper.getShopRandomOneForNow4(shopType,tomorrow,today,limit,offset);
        return list;
    }

    public List<RandomShopOne> getShopRandomOneForNow5(int shopType,int limit, int offset) {
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        List<RandomShopOne> list = randomShopOneMapper.getShopRandomOneForNow5(shopType,tomorrow,today,limit,offset);
        return list;
    }

    public List<RandomShopTwo> getShopRandomTwo(int shopType,int limit, int offset) {
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        List<RandomShopTwo> list = randomShopTwoMapper.getShopRandomTwo(shopType,tomorrow,today,limit,offset);
        return list;
    }

    public List<RandomShopTwo> getShopRandomTwoForNow4(int shopType,int limit, int offset) {
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        List<RandomShopTwo> list = randomShopTwoMapper.getShopRandomTwoForNow4(shopType,tomorrow,today,limit,offset);
        return list;
    }

    public List<RandomShopTwo> getShopRandomTwoForNow5(int shopType,int limit, int offset) {
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        List<RandomShopTwo> list = randomShopTwoMapper.getShopRandomTwoForNow5(shopType,tomorrow,today,limit,offset);
        return list;
    }

    public List<String> getRandomOneUuidListByUuidFor(String uuid,int shopType){
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        List<String> list = randomShopOneMapper.getRandomOneUuidListByUuidFor(uuid,shopType,tomorrow,today);
        return  list;
    }

    public List<String> getRandomTwoUuidListByUuidFor(String uuid,int shopType){
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        List<String> list = randomShopTwoMapper.getRandomTwoUuidListByUuidFor(uuid,shopType,tomorrow,today);
        return  list;
    }

}
