package jp.co.vermore.jsonparse;

import jp.co.vermore.common.mvc.BaseJsonParse;

import java.util.List;
import java.util.Map;

/**
 * StockJsonParse
 * Created by Wangyan.
 * <p>
 * DateTime: 2018/03/14 12:08
 * Copyright: sLab, Corp
 */

public class PaymentHistoryJsonParse extends BaseJsonParse {

    private List<Map<String, Object>> historyList;

    private int counts;



    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    public List<Map<String, Object>> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(List<Map<String, Object>> historyList) {
        this.historyList = historyList;
    }
}
