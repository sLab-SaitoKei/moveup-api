package jp.co.vermore.jsonparse;

import java.util.List;
import java.util.Map;

public class ResultListJsonParse {
    private Object data;
    private List<Map<String,Object>> areaList;
    private List<Map<String,Object>> modeList;
    private List<Map<String,Object>> detailList;
    private List<Map<String,Object>> timeList;
    private List<Map<String,Object>> careerList;

    private List<Map<String,Object>> workPeriodList;
    private List<Map<String,Object>> workTimeList;
    private List<Map<String,Object>> capacityList;
    private List<Map<String,Object>> workWayList;
    private List<Map<String,Object>> specialList;
    private List<Map<String,Object>> environmentList;
    private List<Map<String,Object>> treatmentNewList;

    private int count;

    public List<Map<String, Object>> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Map<String, Object>> areaList) {
        this.areaList = areaList;
    }

    public List<Map<String, Object>> getModeList() {
        return modeList;
    }

    public void setModeList(List<Map<String, Object>> modeList) {
        this.modeList = modeList;
    }

    public List<Map<String, Object>> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<Map<String, Object>> detailList) {
        this.detailList = detailList;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Map<String, Object>> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<Map<String, Object>> timeList) {
        this.timeList = timeList;
    }

    public List<Map<String, Object>> getCareerList() {
        return careerList;
    }

    public void setCareerList(List<Map<String, Object>> careerList) {
        this.careerList = careerList;
    }

    public List<Map<String, Object>> getWorkPeriodList() {
        return workPeriodList;
    }

    public void setWorkPeriodList(List<Map<String, Object>> workPeriodList) {
        this.workPeriodList = workPeriodList;
    }

    public List<Map<String, Object>> getWorkTimeList() {
        return workTimeList;
    }

    public void setWorkTimeList(List<Map<String, Object>> workTimeList) {
        this.workTimeList = workTimeList;
    }

    public List<Map<String, Object>> getCapacityList() {
        return capacityList;
    }

    public void setCapacityList(List<Map<String, Object>> capacityList) {
        this.capacityList = capacityList;
    }

    public List<Map<String, Object>> getWorkWayList() {
        return workWayList;
    }

    public void setWorkWayList(List<Map<String, Object>> workWayList) {
        this.workWayList = workWayList;
    }

    public List<Map<String, Object>> getSpecialList() {
        return specialList;
    }

    public void setSpecialList(List<Map<String, Object>> specialList) {
        this.specialList = specialList;
    }

    public List<Map<String, Object>> getEnvironmentList() {
        return environmentList;
    }

    public void setEnvironmentList(List<Map<String, Object>> environmentList) {
        this.environmentList = environmentList;
    }

    public List<Map<String, Object>> getTreatmentNewList() {
        return treatmentNewList;
    }

    public void setTreatmentNewList(List<Map<String, Object>> treatmentNewList) {
        this.treatmentNewList = treatmentNewList;
    }

}
