package com.android.blerc.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes.dex */
public class DisplayUtils {
    static final String TAG = DisplayUtils.class.getSimpleName();

    public static Point getDisplaySize(Activity activity) {
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= 13) {
            activity.getWindowManager().getDefaultDisplay().getSize(point);
        } else {
            Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
            point.x = defaultDisplay.getWidth();
            point.y = defaultDisplay.getHeight();
        }
        return point;
    }

    public static int dip2px(Activity activity, float f) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return (int) ((f * displayMetrics.density) + 0.5f);
    }

    public static int dp2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int dip2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int px2dip(Activity activity, float f) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return (int) ((f / displayMetrics.density) + 0.5f);
    }

    public static int px2dip(Context context, float f) {
        return (int) ((f / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static void overridingPendingAnim(Activity activity, int i, int i2) {
        if (activity == null) {
            return;
        }
        Log.d(TAG, "OverridePending:Activity=" + activity);
        activity.overridePendingTransition(i, i2);
    }

    public static Bitmap getBlurBmp(Context context, Bitmap bitmap, float f) throws ClassNotFoundException {
        try {
            Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * f), (int) (bitmap.getHeight() * f), false);
            Class<?> cls = Class.forName("miui.util.ScreenshotUtils");
            Bitmap bitmap2 = (Bitmap) cls.getMethod("getBlurBackground", Bitmap.class, Bitmap.class).invoke(cls, bitmapCreateScaledBitmap, null);
            bitmapCreateScaledBitmap.recycle();
            return bitmap2;
        } catch (Exception unused) {
            return null;
        }
    }

    public static Bitmap getLockscreenBmp(Context context) throws ClassNotFoundException {
        try {
            Class<?> cls = Class.forName("miui.content.res.ThemeResources");
            return ((BitmapDrawable) cls.getDeclaredMethod("getLockWallpaperCache", Context.class).invoke(cls, context)).getBitmap();
        } catch (Exception unused) {
            return null;
        }
    }

    public static void setTransparentStatusBar(Window window) throws IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        if (Build.VERSION.SDK_INT < 19) {
            return;
        }
        try {
            Class<?> cls = window.getClass();
            Class<?> cls2 = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            int i = cls2.getField("EXTRA_FLAG_STATUS_BAR_TRANSPARENT").getInt(cls2);
            cls.getMethod("setExtraFlags", Integer.TYPE, Integer.TYPE).invoke(window, Integer.valueOf(i), Integer.valueOf(cls2.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE").getInt(cls2) | i));
            window.addFlags(67108864);
        } catch (Exception unused) {
        }
    }

    public static int getWidth(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        return view.getMeasuredWidth();
    }

    public static int getHeight(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        return view.getMeasuredHeight();
    }

    public static void setLayoutX(View view, int i) {
        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
        marginLayoutParams.setMargins(i, marginLayoutParams.topMargin, marginLayoutParams.width + i, marginLayoutParams.bottomMargin);
        view.setLayoutParams(new RelativeLayout.LayoutParams(marginLayoutParams));
    }

    public static void setLayoutY(View view, int i) {
        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
        marginLayoutParams.setMargins(marginLayoutParams.leftMargin, i, marginLayoutParams.rightMargin, marginLayoutParams.height + i);
        view.setLayoutParams(new RelativeLayout.LayoutParams(marginLayoutParams));
    }

    public static void setLayout(View view, int i, int i2) {
        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
        marginLayoutParams.setMargins(i, i2, marginLayoutParams.width + i, marginLayoutParams.height + i2);
        view.setLayoutParams(new RelativeLayout.LayoutParams(marginLayoutParams));
    }
}
