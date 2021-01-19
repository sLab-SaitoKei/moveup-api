package jp.co.vermore.service;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.JsonObject;
import jp.co.vermore.common.JsonStatus;
import jp.co.vermore.common.mvc.APIException;
import jp.co.vermore.common.mvc.BaseService;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.common.util.StringUtil;
import jp.co.vermore.entity.CoinHistory;
import jp.co.vermore.entity.CoinMaster;
import jp.co.vermore.mapper.CoinHistoryMapper;
import jp.co.vermore.mapper.CoinMasterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;


import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


/**
 * CoinService
 * Created by Wangyan.
 * <p>
 * DateTime: 2018/04/03 16:39
 * Copyright: sLab, Corp
 */

@Service
public class CoinService extends BaseService{
    @Autowired
    private CoinHistoryMapper coinHistoryMapper;

    @Autowired
    private CoinMasterMapper coinMasterMapper;

    //increaseTestCoin
//    public int increaseTestCoin(Long userId, Long itemId, int coin) throws APIException{
//        String serialNumber = StringUtil.getSerialNumber();
//        return changeCoin(userId, coin, itemId, Constant.COIN_ID.TEST, Constant.COIN_TYPE.INCREASE, serialNumber);
//    }

    //reduceTestCoin
//    public int reduceTestCoin(Long userId, Long itemId, int coin) throws APIException{
//        String serialNumber = StringUtil.getSerialNumber();
//        return changeCoin(userId, coin, itemId, Constant.COIN_ID.TEST, Constant.COIN_TYPE.REDUCE, serialNumber);
//    }

    //Consume Coin
    /**
     *
 　　* @param userId  int  User ID
     * @param itemId  int  Item ID (shopId, goodsId...)
     * @param coin    int  Need to be reduced coin
     * @param money   int  Consumption amount
     * @param ratio   int  Convertibility rate
     *
 　　*/
    public String consumeCoin(Long userId, Long itemId, int coin, int money, int ratio) throws APIException{
        String serialNumber = StringUtil.getSerialNumber();
        changeCoin(userId, itemId, coin, Constant.COIN_ID.SHOP, Constant.COIN_TYPE.REDUCE, serialNumber);
        if(money < coin){
            logger.warn("Try to consume money < coin, user=" + userId + ", itemId=" + itemId + ", coin=" + coin + ", money=" + money);
            throw new APIException(JsonStatus.COIN_EXCESS);
        }
        int increaseCoin = (int) ((money - coin) * (ratio * 0.01));
        changeCoin(userId, itemId, increaseCoin, Constant.COIN_ID.SHOP, Constant.COIN_TYPE.INCREASE , serialNumber);
        return serialNumber;
    }

    //Cancel Consume Coin
    public String cancelConsumeCoin(String serialNumber) throws APIException{
        List<CoinHistory> coinHistoryList = coinHistoryMapper.getCoinHistoryBySerialNumber(serialNumber);
        String serialNumberNew = StringUtil.getSerialNumber();
        Long userId;
        Long itemId;
        for(CoinHistory ch : coinHistoryList){
            userId = ch.getUserId();
            itemId = ch.getItemId();
            if(ch.getCoinType() == Constant.COIN_TYPE.REDUCE){
                changeCoin(userId, itemId, Math.toIntExact(ch.getCoinChanged()), Constant.COIN_ID.SHOP, Constant.COIN_TYPE.INCREASE, serialNumberNew);
            }else{
                changeCoin(userId, itemId, Math.toIntExact(ch.getCoinChanged()), Constant.COIN_ID.SHOP, Constant.COIN_TYPE.REDUCE , serialNumberNew);
            }
        }
        return serialNumberNew;
    }

    public String adCoin(Long userId, Long itemId, int coin) throws APIException{
        String serialNumber = StringUtil.getSerialNumber();
        changeCoin(userId, itemId, coin, Constant.COIN_ID.FREEPAPER_AD, Constant.COIN_TYPE.INCREASE, serialNumber);
        return serialNumber;
    }

    public String tvCoin(Long userId, Long itemId, int coin) throws APIException{
        String serialNumber = StringUtil.getSerialNumber();
        changeCoin(userId, itemId, coin, Constant.COIN_ID.TV_AD, Constant.COIN_TYPE.INCREASE, serialNumber);
        return serialNumber;
    }

    //Change Coin
    private int changeCoin(Long userId, Long itemId, int coin, int coinId, Byte coinType ,String serialNumber) throws APIException {
        int count = 0;
        CoinHistory coinHistory = new CoinHistory();
        coinHistory.setUserId(userId);
        coinHistory.setCoinId(coinId);
        coinHistory.setItemId(itemId);
        coinHistory.setCoinChanged(coin);
        coinHistory.setCoinType(coinType);
        coinHistory.setSerialNumber(serialNumber);
        coinHistory.setCreateDatetime(new Date(System.currentTimeMillis()));
        coinHistory.setDelFlg(Boolean.FALSE);
        coinHistory.setNote(Constant.EMPTY_STRING);
        long amount ;
        if(coinHistoryMapper.getNewestCoin(userId) != null){
            amount = coinHistoryMapper.getNewestCoin(userId).getAmount();
        }else {
            amount = 0;
        }
        if(coinType == Constant.COIN_TYPE.INCREASE){
            coinHistory.setAmount(amount + coin);
            count = coinHistoryMapper.insertSelective(coinHistory);
        }else if(coinType == Constant.COIN_TYPE.REDUCE){
            if(amount < coin){
                logger.warn("Try to change money < coin, user=" + userId + ", itemId=" + itemId + ", coin=" + coin + ", amount=" + amount);
                throw new APIException(JsonStatus.COIN_EXCESS);
            }
            coinHistory.setAmount(amount - coin);
            count = coinHistoryMapper.insertSelective(coinHistory);
        }else {
            logger.warn("coin type not found, coinType=" + coinType);
            throw new APIException(JsonStatus.COIN_OPERATION_FAILED);
        }
        return count;
    }

    //Get Coin History List By UserId
    public List<CoinHistory> getCoinHistoryListByUserId(Long userId){
        return coinHistoryMapper.getCoinHistoryByUserId(userId);
    }

    //Get Today Coin History By UserId
    public List<CoinHistory> getTodayCoinListByUserId(Long userId,Long itemId){
        String today = DateUtil.getYyyyMMdd();
        String tomorrow = DateUtil.getTomorrow();
        return coinHistoryMapper.getTodayCoinListByUserId(userId,itemId,today,tomorrow);
    }

    //Get Coin History List By Item
    public List<CoinHistory> getCoinHistoryListByItem(Long itemId, int coinId){
        return coinHistoryMapper.getCoinHistoryByItem(itemId, coinId);
    }

    //Get Amount
    public long getAmount(long userId){
        if(coinHistoryMapper.getNewestCoin(userId) == null){
            return 0;
        }
        return coinHistoryMapper.getNewestCoin(userId).getAmount();
    }

    //Get Coin Master
    public List<CoinMaster> getCoinMaster (){
        return coinMasterMapper.getCoinMaster();
    }

    //check three
    public int getPointCount(long userId){
        int count = coinHistoryMapper.getPointCount(userId);
        return count;
    }
}
