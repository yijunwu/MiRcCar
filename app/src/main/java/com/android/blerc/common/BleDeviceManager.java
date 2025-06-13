package com.android.blerc.common;

import android.content.Context;

import androidx.vectordrawable.graphics.drawable.PathInterpolatorCompat;

/* loaded from: classes.dex */
public class BleDeviceManager {
    private static BleDeviceManager instance;
    private boolean bleConnectState;

    private BleDeviceManager() {
    }

    public static BleDeviceManager getInstance() {
        if (instance == null) {
            synchronized (BleDeviceManager.class) {
                if (instance == null) {
                    instance = new BleDeviceManager();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        if (context == null) {
            return;
        }
        ViseBle.config().setScanTimeout(2000).setScanRepeatInterval(BleConstant.DEFAULT_CONN_TIME).setConnectTimeout(PathInterpolatorCompat.MAX_NUM_POINTS).setOperateTimeout(BleConstant.DEFAULT_OPERATE_TIME).setConnectRetryCount(1).setConnectRetryInterval(1000).setOperateRetryCount(2).setOperateRetryInterval(1000).setMaxConnectCount(3);
        ViseBle.getInstance().init(context.getApplicationContext());
    }

    public boolean isBleConnectState() {
        return this.bleConnectState;
    }

    public void setBleConnectState(boolean z) {
        this.bleConnectState = z;
    }
}
