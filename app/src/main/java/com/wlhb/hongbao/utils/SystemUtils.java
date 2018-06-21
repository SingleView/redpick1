package com.wlhb.hongbao.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;

import com.litesuits.common.utils.MD5Util;
import com.wlhb.hongbao.app.App;
import com.wlhb.hongbao.constant.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by JS01 on 2016/6/8.
 */
public class SystemUtils {

    /**
     * 判断是不是模拟器
     *
     * @return
     */
    public static boolean isEmulator() {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT);
    }

    /**
     * 系统检查
     */
    public static String checkSystem(Activity activity) {
        Context appContext = activity.getApplicationContext();
        String channel = SystemUtils.getMetaData(appContext, Constant.CHANNEL_KEY);
        String versionName = getVersionName(appContext);
        int versionCode = getVersionCode(appContext);
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        float density = metrics.density;
        int dpi = metrics.densityDpi;
        JSONObject json = new JSONObject();
        try {
            json.putOpt("model", Build.MODEL);
            json.putOpt("width", width);
            json.putOpt("height", height);
            json.putOpt("density", density);
            json.putOpt("dpi", dpi);
            json.putOpt("mode", (App.isDebug ? "debug" : "release"));
            json.putOpt("debug_level", App.DEBUG_LEVEL);
            json.putOpt("channel", channel);
            json.putOpt("api", Build.VERSION.SDK_INT);
            json.putOpt("android", Build.VERSION.RELEASE);
            json.putOpt("version_name", versionName);
            json.putOpt("version_code", versionCode);
            json.putOpt("isEmulator", isEmulator());
            json.put("SHA1", SHA1(activity));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String info = json.toString();
        System.out.println("System Info:" + info);
        return info;
    }

    /**
     * 是否有NavigationBar
     *
     * @param context
     * @return
     */
    public static boolean hasNavBar(Context context) {
        boolean hasNavBar = false;
        Resources res = context.getResources();
        int id = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavBar = res.getBoolean(id);
        }

        try {
            Class<?> sysprop = Class.forName("android.os.SystemProperties");
            Method m = sysprop.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(sysprop, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavBar = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasNavBar;
    }


    /**
     * 获取NavigationBar的高度
     *
     * @param context
     * @return
     */
    public static int getNavBarHeight(Context context) {
        int navBarHeight = 0;
        Resources res = context.getResources();
        int id = res.getIdentifier("navigation_bar_height", "dimen", "android");
        if (id > 0 && hasNavBar(context)) {
            navBarHeight = res.getDimensionPixelOffset(id);
        }
        return navBarHeight;
    }

    /**
     * 获取屏幕宽度
     *
     * @param activity
     * @return
     */
    public static final int getScreenWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @param activity
     * @return
     */
    public static final int getScreenHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 读取清单文件中的配置
     *
     * @return
     */
    public static String getMetaData(Context context, String key) {
        String value = null;
        try {
            ApplicationInfo appinfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            value = appinfo.metaData.getString(key);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 获取版本名称
     *
     * @return
     */
    public static String getVersionName(Context context) {
        String versionCode = "1.0.0";
        try {
            PackageInfo pkginfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionCode = pkginfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取版本号
     *
     * @return
     */
    public static int getVersionCode(Context context) {
        int versionCode = 1;
        try {
            PackageInfo pkginfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionCode = pkginfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取进程名
     *
     * @param appContext
     * @return
     */
    public static String getProcessName(Context appContext) {
        String pname = null;
        int pid = android.os.Process.myPid();
        ActivityManager am = (ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processes = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo p : processes) {
            if (p.pid == pid) {
                pname = p.processName;
            }
        }
        return pname;
    }

    public static String md5Password(@NonNull String password) {
        String md51 = HexDump.toHexString(MD5Util.md5(password)).toLowerCase();
        String md52 = HexDump.toHexString(MD5Util.md5(md51 + "MixDJ*&fTA8!fKKs")).toLowerCase();
        return md52;
    }

    public static String SHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取视频缩略图（这里获取第一帧）
     *
     * @param filePath
     * @return
     */
    public static Bitmap getVideoThumbnail(String filePath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            bitmap = retriever.getFrameAtTime(TimeUnit.MILLISECONDS.toMicros(1));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }


}
