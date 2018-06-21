package com.wlhb.hongbao.http;


import com.wlhb.hongbao.app.App;
import com.wlhb.hongbao.event.BaseEvent;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by JS01 on 2016/6/8.
 */
public class BaseResponse<T> {

    public String code_key;
    public int resultCode;
    public String message;
    public T data;
    public Boolean success;


    public boolean isOK() {
        if (success) {
            return true;
        } else  {
            return false;
        }
    }
}
