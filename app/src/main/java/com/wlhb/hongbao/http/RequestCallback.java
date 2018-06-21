package com.wlhb.hongbao.http;

import android.util.Log;

/**
 * Created by JS01 on 2016/6/15.
 */
public class RequestCallback<T> {

    private static final String TAG = "RequestCallback";

    public void onSuccess(T t) {
        Log.d(TAG, "onSuccess");
    }

    public void onFailure(int code, String error) {
        Log.d(TAG, "onFailure:" + code + "," + error);
    }
}
