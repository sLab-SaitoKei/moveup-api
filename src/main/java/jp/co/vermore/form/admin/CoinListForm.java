package jp.co.vermore.form.admin;

import java.util.Date;

public class CoinListForm extends DatatablesBaseForm {

    private Long coinId;

    private Long userId;

    private Long ItemID;

    private String serialNumber;

    private Long amountFrom;

    private Long amountTo;

    private Date createDatetimeFrom;

    private Date createDatetimeTo;

    private String uuId;

    public String getUuId() {
        return uuId;
    }

    public void setUuId(String uuId) {
        this.uuId = uuId;
    }

    public Long getCoinId() {
        return coinId;
    }

    public void setCoinId(Long coinId) {
        this.coinId = coinId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getItemID() {
        return ItemID;
    }

    public void setItemID(Long itemID) {
        ItemID = itemID;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Long getAmountFrom() {
        return amountFrom;
    }

    public void setAmountFrom(Long amountFrom) {
        this.amountFrom = amountFrom;
    }

    public Long getAmountTo() {
        return amountTo;
    }

    public void setAmountTo(Long amountTo) {
        this.amountTo = amountTo;
    }

    public Date getCreateDatetimeFrom() {
        return createDatetimeFrom;
    }

    public void setCreateDatetimeFrom(Date createDatetimeFrom) {
        this.createDatetimeFrom = createDatetimeFrom;
    }

    public Date getCreateDatetimeTo() {
        return createDatetimeTo;
    }

    public void setCreateDatetimeTo(Date createDatetimeTo) {
        this.createDatetimeTo = createDatetimeTo;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
