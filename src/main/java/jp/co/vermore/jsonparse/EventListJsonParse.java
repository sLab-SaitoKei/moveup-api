package jp.co.vermore.jsonparse;

import jp.co.vermore.common.mvc.BaseJsonParse;

import java.util.List;

/**
 * EventJsonParse
 * Created by xieyoujun.
 * <p>
 * DateTime: 2018/03/01 12:35
 * Copyright: sLab, Corp
 */

public class EventListJsonParse extends BaseJsonParse {

    private int count;

    private List<EventJsonParse> eventList;


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<EventJsonParse> getEventList() {
        return eventList;
    }

    public void setEventList(List<EventJsonParse> eventList) {
        this.eventList = eventList;
    }
}
