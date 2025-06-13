package com.android.blerc.location;

import android.os.Build;

/* loaded from: classes.dex */
public class ApiLevelHelper {
    private ApiLevelHelper() {
    }

    public static boolean isAtLeast(int i) {
        return Build.VERSION.SDK_INT >= i;
    }

    public static boolean isLowerThan(int i) {
        return Build.VERSION.SDK_INT < i;
    }
}
