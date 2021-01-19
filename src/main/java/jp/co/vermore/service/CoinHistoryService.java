package jp.co.vermore.service;

import jp.co.vermore.entity.CoinHistory;
import jp.co.vermore.form.admin.CoinListForm;
import jp.co.vermore.mapper.CoinHistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoinHistoryService {

    @Autowired
    private CoinHistoryMapper coinHistoryMapper;

    public List<CoinHistory> getCoinHistoryAllByCondition(CoinListForm form) {
        List<CoinHistory> detail = coinHistoryMapper.getCoinHistoryAllByCondition(form);
        return detail;
    }

    public int getCoinHistoryCountByCondition(CoinListForm form) {
        return coinHistoryMapper.getCoinHistoryCountByCondition(form);
    }

    public int getCoinHistoryCount() {
        return coinHistoryMapper.getCoinHistoryCount();
    }

    public List<CoinHistory> getCoinHistoryByUserId(Long userId) {
        return coinHistoryMapper.getCoinHistoryByUserId(userId);
    }

    public List<CoinHistory> getCoinList(long userId, int limit, int offset) {
        return coinHistoryMapper.getCoinList(userId,limit,offset);
    }


    public List<CoinHistory> getCoinListCount(long userId) {
        return coinHistoryMapper.getCoinListCount(userId);
    }

    public List<CoinHistory> getCoinHistoryList(CoinListForm form) {
        List<CoinHistory> dataList = coinHistoryMapper.getCoinHistoryList(form);
        return dataList;
    }

    public int getCoinHistoryListTotalCountFiltered(CoinListForm form) {
        return coinHistoryMapper.getCoinHistoryListTotalCountFiltered(form);
    }

    public int getCoinHistoryListCount() {
        return coinHistoryMapper.getCoinHistoryListCount();
    }

    public CoinHistory getCoinListDetail(Long id) {
        return coinHistoryMapper.getCoinListDetail(id);
    }
}
