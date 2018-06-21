package com.wlhb.hongbao.event;

/**
 * Created by hetwen on 2017/1/5.
 */

public class BaseEvent {

    private int eventCode = -1;

    private Object data;

    public BaseEvent(int eventCode) {
        this(eventCode, null);
    }

    public BaseEvent(int eventCode, Object data) {
        this.eventCode = eventCode;
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getEventCode() {

        return eventCode;
    }

    public void setEventCode(int eventCode) {
        this.eventCode = eventCode;
    }
}
