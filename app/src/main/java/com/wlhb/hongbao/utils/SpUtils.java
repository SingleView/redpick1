package com.wlhb.hongbao.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.wlhb.administrator.hongbao.BuildConfig;
import com.wlhb.hongbao.constant.SpConstant;

/**
 * Created by Administrator on 2018/3/24/024.
 */

public class SpUtils {
    public static void writeFirstRun(Context context){
        SharedPreferences sp = context.getSharedPreferences(SpConstant.SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putInt(SpConstant.IS_FIRST_RUN, BuildConfig.VERSION_CODE).commit();
    }

    public static boolean readFirstRun(Context context){
        return  context.getSharedPreferences(SpConstant.SP_NAME, Context.MODE_PRIVATE)
                .getInt(SpConstant.IS_FIRST_RUN, 0) < BuildConfig.VERSION_CODE;
    }
}
