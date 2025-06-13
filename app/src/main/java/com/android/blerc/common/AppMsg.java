package com.android.blerc.common;

import android.content.Context;
import android.content.pm.PackageManager;

/* loaded from: classes.dex */
public class AppMsg {
    public static String getVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }
}
