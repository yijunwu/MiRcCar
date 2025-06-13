package com.android.blerc.scan;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanResult;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import com.android.blerc.common.BleConfig;
import com.android.blerc.common.ViseBle;
import com.android.blerc.model.BluetoothLeDevice;
import com.android.blerc.model.BluetoothLeDeviceStore;

/* loaded from: classes.dex */
public class ScanCallback implements BluetoothAdapter.LeScanCallback, IScanFilter {
    protected BluetoothLeDeviceStore bluetoothLeDeviceStore;
    protected Handler handler = new Handler(Looper.myLooper());
    protected boolean isScan = true;
    protected boolean isScanning = false;
    protected IScanCallback scanCallback;
    private android.bluetooth.le.ScanCallback v21ScanCallback;

    @Override // com.android.blerc.scan.IScanFilter
    public BluetoothLeDevice onFilter(BluetoothLeDevice bluetoothLeDevice) {
        return bluetoothLeDevice;
    }

    public ScanCallback(IScanCallback iScanCallback) {
        this.scanCallback = iScanCallback;
        if (iScanCallback == null) {
            throw new NullPointerException("this scanCallback is null!");
        }
        this.bluetoothLeDeviceStore = new BluetoothLeDeviceStore();
        if (Build.VERSION.SDK_INT >= 21) {
            this.v21ScanCallback = new android.bluetooth.le.ScanCallback() { // from class: com.android.blerc.scan.ScanCallback.1
                @Override // android.bluetooth.le.ScanCallback
                public void onScanResult(int i, ScanResult scanResult) {
                    super.onScanResult(i, scanResult);
                    BluetoothLeDevice bluetoothLeDeviceOnFilter = ScanCallback.this.onFilter(new BluetoothLeDevice(scanResult.getDevice(), scanResult.getRssi(), scanResult.getScanRecord().getBytes(), System.currentTimeMillis()));
                    if (bluetoothLeDeviceOnFilter != null) {
                        ScanCallback.this.bluetoothLeDeviceStore.addDevice(bluetoothLeDeviceOnFilter);
                        ScanCallback.this.scanCallback.onDeviceFound(bluetoothLeDeviceOnFilter);
                    }
                }
            };
        }
    }

    public ScanCallback setScan(boolean z) {
        this.isScan = z;
        return this;
    }

    public boolean isScanning() {
        return this.isScanning;
    }

    public void scan() {
        if (this.isScan) {
            if (this.isScanning) {
                return;
            }
            this.bluetoothLeDeviceStore.clear();
            if (BleConfig.getInstance().getScanTimeout() > 0) {
                this.handler.postDelayed(new Runnable() { // from class: com.android.blerc.scan.ScanCallback.2
                    @Override // java.lang.Runnable
                    public void run() {
                        ScanCallback.this.isScanning = false;
                        if (ViseBle.getInstance().getBluetoothAdapter() != null) {
                            ViseBle.getInstance().getBluetoothAdapter().stopLeScan(ScanCallback.this);
                        }
                        if (ScanCallback.this.bluetoothLeDeviceStore.getDeviceMap() != null && ScanCallback.this.bluetoothLeDeviceStore.getDeviceMap().size() > 0) {
                            ScanCallback.this.scanCallback.onScanFinish(ScanCallback.this.bluetoothLeDeviceStore);
                        } else {
                            ScanCallback.this.scanCallback.onScanTimeout();
                        }
                    }
                }, BleConfig.getInstance().getScanTimeout());
            } else if (BleConfig.getInstance().getScanRepeatInterval() > 0) {
                this.handler.postDelayed(new Runnable() { // from class: com.android.blerc.scan.ScanCallback.3
                    @Override // java.lang.Runnable
                    public void run() {
                        ScanCallback.this.isScanning = false;
                        if (ViseBle.getInstance().getBluetoothAdapter() != null) {
                            if (Build.VERSION.SDK_INT < 21) {
                                ScanCallback.this.stop18();
                            } else {
                                ScanCallback.this.stop21();
                            }
                        }
                        if (ScanCallback.this.bluetoothLeDeviceStore.getDeviceMap() != null && ScanCallback.this.bluetoothLeDeviceStore.getDeviceMap().size() > 0) {
                            ScanCallback.this.scanCallback.onScanFinish(ScanCallback.this.bluetoothLeDeviceStore);
                        } else {
                            ScanCallback.this.scanCallback.onScanTimeout();
                        }
                        ScanCallback.this.isScanning = true;
                        if (ViseBle.getInstance().getBluetoothAdapter() != null) {
                            if (Build.VERSION.SDK_INT < 21) {
                                ScanCallback.this.scan18();
                            } else {
                                ScanCallback.this.scan21();
                            }
                        }
                        ScanCallback.this.handler.postDelayed(this, BleConfig.getInstance().getScanRepeatInterval());
                    }
                }, BleConfig.getInstance().getScanRepeatInterval());
            }
            this.isScanning = true;
            if (ViseBle.getInstance().getBluetoothAdapter() != null) {
                if (Build.VERSION.SDK_INT < 21) {
                    scan18();
                    return;
                } else {
                    scan21();
                    return;
                }
            }
            return;
        }
        this.isScanning = false;
        if (ViseBle.getInstance().getBluetoothAdapter() != null) {
            if (Build.VERSION.SDK_INT < 21) {
                stop18();
            } else {
                stop21();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scan18() {
        ViseBle.getInstance().getBluetoothAdapter().startLeScan(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scan21() {
        ViseBle.getInstance().getBluetoothAdapter().getBluetoothLeScanner().startScan(this.v21ScanCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stop18() {
        ViseBle.getInstance().getBluetoothAdapter().stopLeScan(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stop21() {
        ViseBle.getInstance().getBluetoothAdapter().getBluetoothLeScanner().stopScan(this.v21ScanCallback);
    }

    public ScanCallback removeHandlerMsg() {
        this.handler.removeCallbacksAndMessages(null);
        this.bluetoothLeDeviceStore.clear();
        return this;
    }

    @Override // android.bluetooth.BluetoothAdapter.LeScanCallback
    public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
        BluetoothLeDevice bluetoothLeDeviceOnFilter = onFilter(new BluetoothLeDevice(bluetoothDevice, i, bArr, System.currentTimeMillis()));
        if (bluetoothLeDeviceOnFilter != null) {
            this.bluetoothLeDeviceStore.addDevice(bluetoothLeDeviceOnFilter);
            this.scanCallback.onDeviceFound(bluetoothLeDeviceOnFilter);
        }
    }
}
