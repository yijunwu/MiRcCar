package com.android.blerc.db;

import android.content.Context;
import android.content.pm.PackageManager;
import com.zhy.http.okhttp.BuildConfig;

/* loaded from: classes.dex */
public class DBConstant {
    private static DBConstant instance;
    private int accelerator_dr;
    private String address;
    private Context mContext;
    private int turn_dr;
    private int turn_middle;
    private boolean low_speed = false;
    private boolean rightHand = true;
    private boolean sensor = false;

    private DBConstant(Context context) {
        this.mContext = context;
    }

    public static DBConstant getInstance(Context context) {
        if (instance == null) {
            synchronized (DBConstant.class) {
                if (instance == null) {
                    instance = new DBConstant(context);
                }
            }
        }
        return instance;
    }

    public int getTurn_middle() {
        return ((Integer) SharedPreferencesUtils.getParam(this.mContext, "turn_middle", 0)).intValue();
    }

    public int getTurn_dr() {
        return ((Integer) SharedPreferencesUtils.getParam(this.mContext, "turn_dr", 0)).intValue();
    }

    public int getAccelerator_dr() {
        return ((Integer) SharedPreferencesUtils.getParam(this.mContext, "accelerator_dr", 0)).intValue();
    }

    public boolean isLow_speed() {
        return ((Boolean) SharedPreferencesUtils.getParam(this.mContext, "low_speed", false)).booleanValue();
    }

    public boolean isRightHand() {
        return ((Boolean) SharedPreferencesUtils.getParam(this.mContext, "right_hand", false)).booleanValue();
    }

    public void setRightHand(boolean z) {
        SharedPreferencesUtils.setParam(this.mContext, "right_hand", Boolean.valueOf(z));
    }

    public boolean isSensor() {
        return ((Boolean) SharedPreferencesUtils.getParam(this.mContext, "sensor", false)).booleanValue();
    }

    public void setSensor(boolean z) {
        SharedPreferencesUtils.setParam(this.mContext, "sensor", Boolean.valueOf(z));
    }

    public String getAddress() {
        return (String) SharedPreferencesUtils.getParam(this.mContext, "address", "");
    }

    public void setAddress(String str) {
        SharedPreferencesUtils.setParam(this.mContext, "address", str);
    }

    public String getLanguage() {
        return (String) SharedPreferencesUtils.getParam(this.mContext, "language", "zh");
    }

    public void setLanguage(String str) {
        SharedPreferencesUtils.setParam(this.mContext, "language", str);
    }

    public String getAppVersion() {
        try {
            return this.mContext.getPackageManager().getPackageInfo(this.mContext.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return BuildConfig.VERSION_NAME;
        }
    }
}
