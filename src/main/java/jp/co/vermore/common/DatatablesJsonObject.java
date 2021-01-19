package jp.co.vermore.common;

/**
 * Created by jia.
 * <p>
 * DateTime: 2018/04/10 11:13
 * Copyright: sLab, Corp
 */

public class DatatablesJsonObject {

    // page
    private int draw;

    // total records count
    private int recordsTotal;

    // total records after filtered
    private int recordsFiltered;

    // error message
    private String error;

    // data list
    private Object data;

    private String orderStatement;

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getOrderStatement() {
        return orderStatement;
    }

    public void setOrderStatement(String orderStatement) {
        this.orderStatement = orderStatement;
    }

}
