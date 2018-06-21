package com.wlhb.hongbao.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/4/10/010.
 */

public class MyToastUtils {
    public static void showShortToast(Context context, String s) {
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
    }
}
