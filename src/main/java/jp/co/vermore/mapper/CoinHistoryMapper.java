package jp.co.vermore.mapper;

import jp.co.vermore.entity.CoinHistory;
import jp.co.vermore.form.admin.CoinListForm;

import java.util.List;

public interface CoinHistoryMapper {

    int insertSelective(CoinHistory record);

    CoinHistory getNewestCoin(long userId);

    List<CoinHistory> getCoinHistoryByUserId(long userId);

    List<CoinHistory> getTodayCoinListByUserId(long userId,long itemId, String today,String tomorrow);

    List<CoinHistory> getCoinHistoryByItem(long itemId ,int coinId);

    List<CoinHistory> getCoinHistoryBySerialNumber(String serialNumber);

    List<CoinHistory> getCoinHistoryAllByCondition(CoinListForm form);

    int getCoinHistoryCountByCondition(CoinListForm form);

    int getCoinHistoryCount();

    List<CoinHistory> getCoinList(long userId, int limit, int offset);

    List<CoinHistory> getCoinListCount(long userId);

    List<CoinHistory> getCoinHistoryList(CoinListForm form);

    int getCoinHistoryListTotalCountFiltered(CoinListForm form);

    int getCoinHistoryListCount();

    CoinHistory getCoinListDetail(Long id);

    int getPointCount(long userId);
}