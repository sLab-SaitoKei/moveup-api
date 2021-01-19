package jp.co.vermore.jsonparse;

import jp.co.vermore.common.mvc.BaseJsonParse;

/**
 * StockJsonParse
 * Created by Wangyan.
 * <p>
 * DateTime: 2018/03/14 12:08
 * Copyright: sLab, Corp
 */

public class StockJsonParse extends BaseJsonParse {

    private int amount;

    private  Byte colors;

    private  Byte size;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Byte getColors() {
        return colors;
    }

    public void setColors(Byte colors) {
        this.colors = colors;
    }

    public Byte getSize() {
        return size;
    }

    public void setSize(Byte size) { this.size = size; }
}
