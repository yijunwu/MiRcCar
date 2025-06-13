package com.android.blerc.scan;

import com.android.blerc.model.BluetoothLeDevice;

/* loaded from: classes.dex */
public interface IScanFilter {
    BluetoothLeDevice onFilter(BluetoothLeDevice bluetoothLeDevice);
}
