package jp.co.vermore.form;

/**
 * StockJsonParse
 * Created by Wangyan.
 * <p>
 * DateTime: 2018/03/14 12:08
 * Copyright: sLab, Corp
 */

public class StockRegistForm {

    private int amountId;

    private Byte color;

    private String colorName;

    private int xs = 0;

    private int s = 0;

    private int m = 0;

    private int l = 0;

    private int xl = 0;

    private int freesize = 0;

    public Byte getColor() {
        return color;
    }

    public void setColor(Byte color) {
        this.color = color;
    }

    public int getXs() {
        return xs;
    }

    public void setXs(int xs) {
        this.xs = xs;
    }

    public int getS() {
        return s;
    }

    public void setS(int s) {
        this.s = s;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getL() {
        return l;
    }

    public void setL(int l) {
        this.l = l;
    }

    public int getXl() {
        return xl;
    }

    public void setXl(int xl) {
        this.xl = xl;
    }

    public int getAmountId() {
        return amountId;
    }

    public void setAmountId(int amountId) {
        this.amountId = amountId;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public int getFreesize() {
        return freesize;
    }

    public void setFreesize(int freesize) {
        this.freesize = freesize;
    }
}


