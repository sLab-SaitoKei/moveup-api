package jp.co.vermore.kit.component.chart.model;

import lombok.Data;

/**
 * Created by fgm on 2017/4/7.
 */
@Data
public class XYLine {
    private double yValue;
    private String  xValue;
    private String groupName;
    public XYLine(){

    }
    public XYLine(double yValue, String xValue, String groupName){
        this.yValue=yValue;
        this.xValue=xValue;
        this.groupName=groupName;
    }

    public double getyValue() {
        return yValue;
    }

    public void setyValue(double yValue) {
        this.yValue = yValue;
    }

    public String getxValue() {
        return xValue;
    }

    public void setxValue(String xValue) {
        this.xValue = xValue;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
