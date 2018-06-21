package com.wlhb.hongbao.constant;

import android.Manifest;

/**
 * Created by Administrator on 2018/4/25/025.
 */

public class Constant {
    public static final String[] PERMISSION_LIST = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_LOGS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.SET_DEBUG_APP,
            Manifest.permission.SYSTEM_ALERT_WINDOW,
            Manifest.permission.GET_ACCOUNTS};

    public static final String CHANNEL_KEY = "UMENG_CHANNEL";

    public static final class Prefs {
        public static final String CONFIG = "config";
    }


        public static final class Config {
            public static final String IS_FIRST_OPEN = "isFirstOpen";
            public static final String LOGIN_REMEMBER = "remember";
            public static final String LOGIN_USERNAME = "username";
            public static final String LOGIN_PASSWORD = "password";

    }

}
