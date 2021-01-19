package jp.co.vermore.validation;

import javax.validation.constraints.Min;
import java.util.List;

public class RecruitSearchParams {

    private String keyWord;

    private int shopType;

    private int carerr;

    private List<Integer> areaList;

    private List<Integer> modeList;

    private List<Integer> workPeriodList;

    private List<Integer> workTimeList;

    private List<Integer> capacityList;

    private List<Integer> workWayList;

    private List<Integer> specialList;

    private List<Integer> environmentList;

    private List<Integer> treatmentNewList;

    private int workingTimeStart;

    private int workingTimeEnd;

    private List<Integer> detailList;

    @Min(value = 1, message = "Limit can't be less than 1")
    private int limit;

    @Min(value = 0, message = "Offset can't be less than 0")
    private int offset;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public int getShopType() {
        return shopType;
    }

    public void setShopType(int shopType) {
        this.shopType = shopType;
    }

    public List<Integer> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Integer> areaList) {
        this.areaList = areaList;
    }

    public List<Integer> getModeList() {
        return modeList;
    }

    public void setModeList(List<Integer> modeList) {
        this.modeList = modeList;
    }

    public int getWorkingTimeStart() {
        return workingTimeStart;
    }

    public void setWorkingTimeStart(int workingTimeStart) {
        this.workingTimeStart = workingTimeStart;
    }

    public int getWorkingTimeEnd() {
        return workingTimeEnd;
    }

    public void setWorkingTimeEnd(int workingTimeEnd) {
        this.workingTimeEnd = workingTimeEnd;
    }

    public List<Integer> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<Integer> detailList) {
        this.detailList = detailList;
    }

    public int getCarerr() {
        return carerr;
    }

    public void setCarerr(int carerr) {
        this.carerr = carerr;
    }

    public List<Integer> getWorkPeriodList() {
        return workPeriodList;
    }

    public void setWorkPeriodList(List<Integer> workPeriodList) {
        this.workPeriodList = workPeriodList;
    }

    public List<Integer> getWorkTimeList() {
        return workTimeList;
    }

    public void setWorkTimeList(List<Integer> workTimeList) {
        this.workTimeList = workTimeList;
    }

    public List<Integer> getCapacityList() {
        return capacityList;
    }

    public void setCapacityList(List<Integer> capacityList) {
        this.capacityList = capacityList;
    }

    public List<Integer> getWorkWayList() {
        return workWayList;
    }

    public void setWorkWayList(List<Integer> workWayList) {
        this.workWayList = workWayList;
    }

    public List<Integer> getTreatmentNewList() {
        return treatmentNewList;
    }

    public void setTreatmentNewList(List<Integer> treatmentNewList) {
        this.treatmentNewList = treatmentNewList;
    }

    public List<Integer> getSpecialList() {
        return specialList;
    }

    public void setSpecialList(List<Integer> specialList) {
        this.specialList = specialList;
    }

    public List<Integer> getEnvironmentList() {
        return environmentList;
    }

    public void setEnvironmentList(List<Integer> environmentList) {
        this.environmentList = environmentList;
    }


}
