package jp.co.vermore.form.admin;

import jp.co.vermore.common.mvc.BaseForm;

public class CoinDetailForm extends BaseForm {

    private long coinId;

    private long userId;

    private long itemID;

    private long coinChanged;

    private String serialNumber;

    private Byte coinType;

    private Long amount;

    private String desc;

    private String createDatetime;

    public long getCoinId() {
        return coinId;
    }

    public void setCoinId(long coinId) {
        this.coinId = coinId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getItemID() {
        return itemID;
    }

    public void setItemID(long itemID) {
        this.itemID = itemID;
    }

    public long getCoinChanged() {
        return coinChanged;
    }

    public void setCoinChanged(long coinChanged) {
        this.coinChanged = coinChanged;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Byte getCoinType() {
        return coinType;
    }

    public void setCoinType(Byte coinType) {
        this.coinType = coinType;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(String createDatetime) {
        this.createDatetime = createDatetime;
    }
}
