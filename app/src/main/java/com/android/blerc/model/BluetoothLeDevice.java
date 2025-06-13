package com.android.blerc.model;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.blerc.util.HexUtil;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class BluetoothLeDevice implements Parcelable {
    public static final Parcelable.Creator<BluetoothLeDevice> CREATOR = new Parcelable.Creator<BluetoothLeDevice>() { // from class: com.android.blerc.model.BluetoothLeDevice.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BluetoothLeDevice createFromParcel(Parcel parcel) {
            return new BluetoothLeDevice(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BluetoothLeDevice[] newArray(int i) {
            return new BluetoothLeDevice[i];
        }
    };
    private static final long LOG_INVALIDATION_THRESHOLD = 10000;
    protected static final int MAX_RSSI_LOG_SIZE = 10;
    private static final String PARCEL_EXTRA_BLUETOOTH_DEVICE = "bluetooth_device";
    private static final String PARCEL_EXTRA_CURRENT_RSSI = "current_rssi";
    private static final String PARCEL_EXTRA_CURRENT_TIMESTAMP = "current_timestamp";
    private static final String PARCEL_EXTRA_DEVICE_RSSI_LOG = "device_rssi_log";
    private static final String PARCEL_EXTRA_DEVICE_SCANRECORD = "device_scanrecord";
    private static final String PARCEL_EXTRA_DEVICE_SCANRECORD_STORE = "device_scanrecord_store";
    private static final String PARCEL_EXTRA_FIRST_RSSI = "device_first_rssi";
    private static final String PARCEL_EXTRA_FIRST_TIMESTAMP = "first_timestamp";
    private int mCurrentRssi;
    private long mCurrentTimestamp;
    private final BluetoothDevice mDevice;
    private final int mFirstRssi;
    private final long mFirstTimestamp;
    private final Map<Long, Integer> mRssiLog;
    private final byte[] mScanRecord;

    private static String resolveBondingState(int i) {
        switch (i) {
            case 10:
                return "UnBonded";
            case 11:
                return "Pairing";
            case 12:
                return "Paired";
            default:
                return "Unknown";
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BluetoothLeDevice(BluetoothDevice bluetoothDevice, int i, byte[] bArr, long j) {
        this.mDevice = bluetoothDevice;
        this.mFirstRssi = i;
        this.mFirstTimestamp = j;
        this.mScanRecord = bArr;
        this.mRssiLog = new LinkedHashMap(10);
        updateRssiReading(j, i);
    }

    public BluetoothLeDevice(BluetoothLeDevice bluetoothLeDevice) {
        this.mCurrentRssi = bluetoothLeDevice.getRssi();
        this.mCurrentTimestamp = bluetoothLeDevice.getTimestamp();
        this.mDevice = bluetoothLeDevice.getDevice();
        this.mFirstRssi = bluetoothLeDevice.getFirstRssi();
        this.mFirstTimestamp = bluetoothLeDevice.getFirstTimestamp();
        this.mRssiLog = bluetoothLeDevice.getRssiLog();
        this.mScanRecord = bluetoothLeDevice.getScanRecord();
    }

    protected BluetoothLeDevice(Parcel parcel) {
        Bundle bundle = parcel.readBundle(getClass().getClassLoader());
        this.mCurrentRssi = bundle.getInt(PARCEL_EXTRA_CURRENT_RSSI, 0);
        this.mCurrentTimestamp = bundle.getLong(PARCEL_EXTRA_CURRENT_TIMESTAMP, 0L);
        this.mDevice = (BluetoothDevice) bundle.getParcelable(PARCEL_EXTRA_BLUETOOTH_DEVICE);
        this.mFirstRssi = bundle.getInt(PARCEL_EXTRA_FIRST_RSSI, 0);
        this.mFirstTimestamp = bundle.getLong(PARCEL_EXTRA_FIRST_TIMESTAMP, 0L);
        this.mRssiLog = (Map) bundle.getSerializable(PARCEL_EXTRA_DEVICE_RSSI_LOG);
        this.mScanRecord = bundle.getByteArray(PARCEL_EXTRA_DEVICE_SCANRECORD);
    }

    private void addToRssiLog(long j, int i) {
        synchronized (this.mRssiLog) {
            if (j - this.mCurrentTimestamp > 10000) {
                this.mRssiLog.clear();
            }
            this.mCurrentRssi = i;
            this.mCurrentTimestamp = j;
            this.mRssiLog.put(Long.valueOf(j), Integer.valueOf(i));
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BluetoothLeDevice bluetoothLeDevice = (BluetoothLeDevice) obj;
        if (this.mCurrentRssi != bluetoothLeDevice.mCurrentRssi || this.mCurrentTimestamp != bluetoothLeDevice.mCurrentTimestamp) {
            return false;
        }
        BluetoothDevice bluetoothDevice = this.mDevice;
        if (bluetoothDevice == null) {
            if (bluetoothLeDevice.mDevice != null) {
                return false;
            }
        } else if (!bluetoothDevice.equals(bluetoothLeDevice.mDevice)) {
            return false;
        }
        if (this.mFirstRssi != bluetoothLeDevice.mFirstRssi || this.mFirstTimestamp != bluetoothLeDevice.mFirstTimestamp) {
            return false;
        }
        Map<Long, Integer> map = this.mRssiLog;
        if (map == null) {
            if (bluetoothLeDevice.mRssiLog != null) {
                return false;
            }
        } else if (!map.equals(bluetoothLeDevice.mRssiLog)) {
            return false;
        }
        return Arrays.equals(this.mScanRecord, bluetoothLeDevice.mScanRecord);
    }

    public String getAddress() {
        return this.mDevice.getAddress();
    }

    public String getBluetoothDeviceBondState() {
        return resolveBondingState(this.mDevice.getBondState());
    }

    public BluetoothDevice getDevice() {
        return this.mDevice;
    }

    public int getFirstRssi() {
        return this.mFirstRssi;
    }

    public long getFirstTimestamp() {
        return this.mFirstTimestamp;
    }

    public String getName() {
        return this.mDevice.getName();
    }

    public int getRssi() {
        return this.mCurrentRssi;
    }

    protected Map<Long, Integer> getRssiLog() {
        Map<Long, Integer> map;
        synchronized (this.mRssiLog) {
            map = this.mRssiLog;
        }
        return map;
    }

    public double getRunningAverageRssi() {
        int i;
        int iIntValue;
        synchronized (this.mRssiLog) {
            Iterator<Long> it = this.mRssiLog.keySet().iterator();
            i = 0;
            iIntValue = 0;
            while (it.hasNext()) {
                i++;
                iIntValue += this.mRssiLog.get(it.next()).intValue();
            }
        }
        if (i > 0) {
            return iIntValue / i;
        }
        return 0.0d;
    }

    public byte[] getScanRecord() {
        return this.mScanRecord;
    }

    public long getTimestamp() {
        return this.mCurrentTimestamp;
    }

    public int hashCode() {
        int i = (this.mCurrentRssi + 31) * 31;
        long j = this.mCurrentTimestamp;
        int i2 = (i + ((int) (j ^ (j >>> 32)))) * 31;
        BluetoothDevice bluetoothDevice = this.mDevice;
        int iHashCode = (((i2 + (bluetoothDevice == null ? 0 : bluetoothDevice.hashCode())) * 31) + this.mFirstRssi) * 31;
        long j2 = this.mFirstTimestamp;
        int i3 = (iHashCode + ((int) (j2 ^ (j2 >>> 32)))) * 31;
        Map<Long, Integer> map = this.mRssiLog;
        return ((i3 + (map != null ? map.hashCode() : 0)) * 31) + Arrays.hashCode(this.mScanRecord);
    }

    public String toString() {
        return "BluetoothLeDevice [mDevice=" + this.mDevice + ", mRssi=" + this.mFirstRssi + ", mScanRecord=" + HexUtil.encodeHexStr(this.mScanRecord) + ", mRecordStore=" + getBluetoothDeviceBondState() + ", getBluetoothDeviceClassName()=]";
    }

    public void updateRssiReading(long j, int i) {
        addToRssiLog(j, i);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        Bundle bundle = new Bundle(getClass().getClassLoader());
        bundle.putByteArray(PARCEL_EXTRA_DEVICE_SCANRECORD, this.mScanRecord);
        bundle.putInt(PARCEL_EXTRA_FIRST_RSSI, this.mFirstRssi);
        bundle.putInt(PARCEL_EXTRA_CURRENT_RSSI, this.mCurrentRssi);
        bundle.putLong(PARCEL_EXTRA_FIRST_TIMESTAMP, this.mFirstTimestamp);
        bundle.putLong(PARCEL_EXTRA_CURRENT_TIMESTAMP, this.mCurrentTimestamp);
        bundle.putParcelable(PARCEL_EXTRA_BLUETOOTH_DEVICE, this.mDevice);
        bundle.putSerializable(PARCEL_EXTRA_DEVICE_RSSI_LOG, (Serializable) this.mRssiLog);
        parcel.writeBundle(bundle);
    }
}
