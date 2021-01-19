package jp.co.vermore.form;

import jp.co.vermore.common.mvc.BaseForm;

/**
 * Created by xieyoujun on 2017/11/28.
 */
public class CoinRegistForm extends BaseForm {

    private int coin;

    private Long itemId;


    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
}
