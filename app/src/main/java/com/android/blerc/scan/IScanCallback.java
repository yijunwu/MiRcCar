package com.android.blerc.scan;

import com.android.blerc.model.BluetoothLeDevice;
import com.android.blerc.model.BluetoothLeDeviceStore;

/* loaded from: classes.dex */
public interface IScanCallback {
    void onDeviceFound(BluetoothLeDevice bluetoothLeDevice);

    void onScanFinish(BluetoothLeDeviceStore bluetoothLeDeviceStore);

    void onScanTimeout();
}
