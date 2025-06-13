package com.android.blerc.loading;

import android.content.Context;

/* loaded from: classes.dex */
public class DensityUtil {
    public static float dip2px(Context context, float f) {
        return f * context.getResources().getDisplayMetrics().density;
    }
}
