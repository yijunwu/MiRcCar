package com.android.blerc.dfu;

import android.app.Activity;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class ActivityCollector {
    public static HashMap<Class<?>, Activity> activities = new LinkedHashMap();

    public static void addActivity(Activity activity, Class<?> cls) {
        activities.put(cls, activity);
    }

    public static <T extends Activity> boolean isActivityExist(Class<T> cls) {
        Activity activity = getActivity(cls);
        return (activity == null || activity.isFinishing() || activity.isDestroyed()) ? false : true;
    }

    public static <T extends Activity> T getActivity(Class<T> cls) {
        return (T) activities.get(cls);
    }

    public static void removeActivity(Activity activity) {
        if (activities.containsValue(activity)) {
            activities.remove(activity.getClass());
        }
    }

    public static void removeAllActivity() {
        HashMap<Class<?>, Activity> map = activities;
        if (map == null || map.size() <= 0) {
            return;
        }
        for (Map.Entry<Class<?>, Activity> entry : activities.entrySet()) {
            if (!entry.getValue().isFinishing()) {
                entry.getValue().finish();
            }
        }
        activities.clear();
    }
}
