package jp.co.vermore.form.admin;

/**
 * StockJsonParse
 * Created by Wangyan.
 * <p>
 * DateTime: 2018/03/14 12:08
 * Copyright: sLab, Corp
 */

public class ShopRestForm {

    private int amountId;

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(int weekDay) {
        this.weekDay = weekDay;
    }

    private int week = 0;

    private int weekDay = 0;

    public int getAmountId() {
        return amountId;
    }

    public void setAmountId(int amountId) {
        this.amountId = amountId;
    }

}


