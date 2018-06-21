package com.wlhb.hongbao.http;


import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by Administrator on 2018/3/26/026.
 */

public class CallManager {
    private static final String TAG = "CallManager";

    private static List<Call> list;

    static {
        list = new ArrayList<>();
    }

    public static void add(Call call) {
        if (call != null) {
            Log.d(TAG, "add call");
            if (!list.contains(call)) {
                list.add(call);
            }
        }
    }

    public static void remove(Call call) {
        if (call != null) {
            Log.d(TAG, "remove call");
            if (list.contains(call)) {
                list.remove(call);
            }
        }
    }

    public static void cancelAll() {
        if (!list.isEmpty()) {
            Log.d(TAG, "cancel all call");
            for (Call call : list) {
                call.cancel();
            }
        }
    }
}
