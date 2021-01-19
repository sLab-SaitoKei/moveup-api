package jp.co.vermore.form.admin;

public class SettlementListForm extends DatatablesBaseForm {
    private Long customerId;
    private String customerName;
    private String shopUUID;
    private Integer month;
    private Integer amountTotal;
    private Integer status;
    private String mon;
    private String shopName;
    private int selStatus;

    public int getSelStatus() {
        return selStatus;
    }

    public void setSelStatus(int selStatus) {
        this.selStatus = selStatus;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getMon() {
        return mon;
    }

    public void setMon(String mon) {
        this.mon = mon;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getShopUUID() {
        return shopUUID;
    }

    public void setShopUUID(String shopUUID) {
        this.shopUUID = shopUUID;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(Integer amountTotal) {
        this.amountTotal = amountTotal;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

}
