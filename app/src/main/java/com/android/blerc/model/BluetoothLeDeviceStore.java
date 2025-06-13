package com.android.blerc.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class BluetoothLeDeviceStore {
    private final Map<String, BluetoothLeDevice> mDeviceMap = new HashMap();

    public void addDevice(BluetoothLeDevice bluetoothLeDevice) {
        if (bluetoothLeDevice == null) {
            return;
        }
        if (this.mDeviceMap.containsKey(bluetoothLeDevice.getAddress())) {
            this.mDeviceMap.get(bluetoothLeDevice.getAddress()).updateRssiReading(bluetoothLeDevice.getTimestamp(), bluetoothLeDevice.getRssi());
        } else {
            this.mDeviceMap.put(bluetoothLeDevice.getAddress(), bluetoothLeDevice);
        }
    }

    public void removeDevice(BluetoothLeDevice bluetoothLeDevice) {
        if (bluetoothLeDevice != null && this.mDeviceMap.containsKey(bluetoothLeDevice.getAddress())) {
            this.mDeviceMap.remove(bluetoothLeDevice.getAddress());
        }
    }

    public void clear() {
        this.mDeviceMap.clear();
    }

    public Map<String, BluetoothLeDevice> getDeviceMap() {
        return this.mDeviceMap;
    }

    public List<BluetoothLeDevice> getDeviceList() {
        ArrayList arrayList = new ArrayList(this.mDeviceMap.values());
        Collections.sort(arrayList, new Comparator<BluetoothLeDevice>() { // from class: com.android.blerc.model.BluetoothLeDeviceStore.1
            @Override // java.util.Comparator
            public int compare(BluetoothLeDevice bluetoothLeDevice, BluetoothLeDevice bluetoothLeDevice2) {
                return bluetoothLeDevice.getAddress().compareToIgnoreCase(bluetoothLeDevice2.getAddress());
            }
        });
        return arrayList;
    }

    public String toString() {
        return "BluetoothLeDeviceStore{DeviceList=" + getDeviceList() + '}';
    }
}
