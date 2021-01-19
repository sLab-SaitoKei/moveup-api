package jp.co.vermore.validation;

import javax.validation.constraints.Min;

public class RecruitListParams {

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
}
