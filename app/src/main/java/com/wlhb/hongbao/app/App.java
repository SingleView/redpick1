package com.wlhb.hongbao.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDexApplication;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wlhb.hongbao.bean.UserData;
import com.wlhb.hongbao.bean.Wxregist;
import com.wlhb.hongbao.cache.ACache;
import com.wlhb.hongbao.constant.Constant;
import com.wlhb.hongbao.utils.SystemUtils;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2018/3/26/026.
 */

public class App extends MultiDexApplication implements IConfiger {

    public static Wxregist userData;

    public static String token = "";
    public static int id = -3;
    public static int packetId = 0;
    private static ACache mCache;

    public static boolean isDebug = true;
    public static int DEBUG_LEVEL = 0;
    private static App instance;

    public static String versionName;

    public static int versionCode = 1;
    private SharedPreferences prefs;
    public static IWXAPI api;


    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);  // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);          // 初始化 JPush

        api = WXAPIFactory.createWXAPI(this, "wx59462dc0749720d9",true);
        //将应用的appid注册到微信
        api.registerApp("wx59462dc0749720d9");

        prefs = getSharedPreferences(Constant.Prefs.CONFIG, MODE_PRIVATE);
        String processName = SystemUtils.getProcessName(this);
        String packageName = getPackageName();


        instance = this;
        mCache = ACache.get(this);
        getVersions();
        getUser();

    }

    private void getVersions() {
        versionName = SystemUtils.getVersionName(this);
        versionCode = SystemUtils.getVersionCode(this);
    }


    public static Context getContext() {
        if (instance != null) {
            return instance.getApplicationContext();
        }
        return null;
    }

    public static void exit() {
        AppManager.finishAll();
    }

    private void getUser() {
        userData = (Wxregist) mCache.getAsObject("user_data");
        if (userData != null) {
            token = userData.token;
            id = userData.id;
        }
    }

    /**
     * 退出登录
     */
    public static void logout() {
        userData = null;
        token = null;
        //        CrashReport.setUserId("unknown");
        mCache.remove("user_data");
        if (getInstance() != null) {
            IConfiger configer = getInstance();
            configer.writeBoolean(Constant.Config.LOGIN_REMEMBER, false);
            configer.writeString(Constant.Config.LOGIN_USERNAME, null);
            configer.writeString(Constant.Config.LOGIN_PASSWORD, null);
        }


        EMClient.getInstance().logout(true, new EMCallBack() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onProgress(int progress, String status) {
            }

            @Override
            public void onError(int code, String message) {
            }
        });
    }

    @Override
    public String readString(String key, String defaultValue) {
        if (prefs != null) {
            return prefs.getString(key, defaultValue);
        }
        return defaultValue;
    }

    @Override
    public void writeString(String key, String value) {
        if (prefs != null) {
            prefs.edit().putString(key, value).apply();
        }
    }

    @Override
    public int readInt(String key, int defaultValue) {
        if (prefs != null) {
            return prefs.getInt(key, defaultValue);
        }
        return defaultValue;
    }

    @Override
    public void writeInt(String key, int value) {
        if (prefs != null) {
            prefs.edit().putInt(key, value).apply();
        }
    }

    @Override
    public float readFloat(String key, float defaultValue) {
        if (prefs != null) {
            return prefs.getFloat(key, defaultValue);
        }
        return defaultValue;
    }


    @Override
    public void writeFloat(String key, float value) {
        if (prefs != null) {
            prefs.edit().putFloat(key, value).apply();
        }
    }

    @Override
    public boolean readBoolean(String key, boolean defaultValue) {
        if (prefs != null) {
            return prefs.getBoolean(key, defaultValue);
        }
        return defaultValue;
    }

    @Override
    public void writeBoolean(String key, boolean value) {
        if (prefs != null) {
            prefs.edit().putBoolean(key, value).apply();
        }
    }


    public static App getInstance() {
        return instance;
    }


    /**
     * 登录
     *
     * @param data
     */
    public static void login(Wxregist data) {
        login(data, true);
    }

    /**
     * 登录
     *
     * @param data
     * @param isRemember
     */
    public static void login(Wxregist data, boolean isRemember) {
        if (data != null) {
            userData = data;
            token = data.token;
            id = data.id;
            //            CrashReport.setUserId(uid);
            mCache.put("user_data", userData);
            //保存配置
            if (getInstance() != null) {
                IConfiger configer = getInstance();
                if (configer != null) {
                    configer.writeBoolean(Constant.Config.LOGIN_REMEMBER, isRemember);
                }
            }
        }

        }


}
