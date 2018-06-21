package com.wlhb.hongbao.app;

import android.app.Activity;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2018/3/25/025.
 */

public class AppManager {

    private static final String TAG = "AppManager";

    public static Map<String, WeakReference<Activity>> map;

    static {
        map = new ConcurrentHashMap<>();
    }


    public static void add(Activity activity) {
        if (activity != null) {
            String name = activity.getClass().getSimpleName();
            if (!map.containsKey(name)) {
                Log.d(TAG, "add activity:" + name);
                WeakReference<Activity> weakRef = new WeakReference<>(activity);
                map.put(name, weakRef);
            }
        }
    }


    public static void finish(Class<? extends Activity> cls) {
        if (cls == null) {
            return;
        }
        Set<Map.Entry<String, WeakReference<Activity>>> entries = map.entrySet();
        Iterator<Map.Entry<String, WeakReference<Activity>>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, WeakReference<Activity>> next = iterator.next();
            WeakReference<Activity> value = next.getValue();
            Activity activity = value.get();
            if (activity != null && activity.getClass() == cls) {
                Log.d(TAG, "finishing " + activity.getClass().getSimpleName());
                activity.finish();
                remove(activity);
                break;
            }
        }
    }


    @SafeVarargs
    public static void finish(Class<? extends Activity>... clses) {
        if (clses == null || clses.length == 0) {
            return;
        }
        Set<Map.Entry<String, WeakReference<Activity>>> entries = map.entrySet();
        Iterator<Map.Entry<String, WeakReference<Activity>>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, WeakReference<Activity>> next = iterator.next();
            WeakReference<Activity> value = next.getValue();
            Activity activity = value.get();
            if (activity != null) {
                Class<? extends Activity> cls = activity.getClass();
                for (Class clazz : clses) {
                    if (cls == clazz) {
                        Log.d(TAG, "finishing " + activity.getClass().getSimpleName());
                        activity.finish();
                        remove(activity);
                        break;
                    }
                }
            }
        }
    }


    public static void remove(Activity activity) {
        if (activity != null) {
            String name = activity.getClass().getSimpleName();
            if (map.containsKey(name)) {
                Log.d(TAG, "remove activity:" + name);
                map.remove(name);
            }
        }
    }

    public static void finishAll() {
        Set<Map.Entry<String, WeakReference<Activity>>> entries = map.entrySet();
        Iterator<Map.Entry<String, WeakReference<Activity>>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, WeakReference<Activity>> next = iterator.next();
            WeakReference<Activity> value = next.getValue();
            Activity activity = value.get();
            if (activity != null) {
                Log.d(TAG, "finishing " + activity.getClass().getSimpleName());
                activity.finish();
                remove(activity);
            }
        }
    }
}
