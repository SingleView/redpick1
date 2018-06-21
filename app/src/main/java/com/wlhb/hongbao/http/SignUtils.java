package com.wlhb.hongbao.http;

import android.util.Log;

import com.litesuits.common.utils.MD5Util;
import com.wlhb.hongbao.utils.HexDump;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**5
 * Created by hetwen on 2016/12/16.
 */

public class SignUtils {

    private static final String TAG = "SignUtils";

    private static final String SECURE_STR = "$!#HeQi1o0";


    public static String getSignature(List<String> keyValues) {
        String signature = null;
        if (keyValues != null) {
            Collections.sort(keyValues, new Comparator<String>() {
                @Override
                public int compare(String s1, String s2) {
                    return s1.compareTo(s2);
                }
            });
            String preSignature = "";
            for (int i = 0; i < keyValues.size(); i++) {
                if (i < keyValues.size() - 1) {
                    preSignature += keyValues.get(i) + "&";
                } else {
                    preSignature += keyValues.get(i);
                }
            }
            preSignature += SECURE_STR;
            Log.d(TAG, "preSignature: " + preSignature);
            signature = HexDump.toHexString(MD5Util.md5(preSignature)).toUpperCase();
        }
        return signature;
    }

}
