package com.android.blerc.util;

import android.app.Activity;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;

/* loaded from: classes.dex */
public class BleUtil {
    public static void enableBluetooth(Activity activity, int i) {
        activity.startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), i);
    }

    public static boolean isSupportBle(Context context) {
        return (context == null || !context.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le") || ((BluetoothManager) context.getSystemService("bluetooth")).getAdapter() == null) ? false : true;
    }

    public static boolean isBleEnable(Context context) {
        if (isSupportBle(context)) {
            return ((BluetoothManager) context.getSystemService("bluetooth")).getAdapter().isEnabled();
        }
        return false;
    }
}
