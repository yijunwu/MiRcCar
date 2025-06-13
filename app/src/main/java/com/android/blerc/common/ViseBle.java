package com.android.blerc.common;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.android.blerc.scan.ScanCallback;
import java.util.List;
import java.util.UUID;
import org.greenrobot.eventbus.EventBus;

/* loaded from: classes.dex */
public class ViseBle {
    private static BleConfig bleConfig = BleConfig.getInstance();
    private static ViseBle instance;
    private Context context;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothGatt mBluetoothGatt;
    private BluetoothManager mBluetoothManager;
    private OnConnectListener mOnConnectListener;
    private OnDataAvailableListener mOnDataAvailableListener;
    private OnDisconnectListener mOnDisconnectListener;
    private OnServiceDiscoverListener mOnServiceDiscoverListener;
    private BluetoothGattCharacteristic mBle_char1 = null;
    private BluetoothGattCharacteristic mBle_char2 = null;
    private BluetoothGattCharacteristic mBle_char3 = null;
    private BluetoothGattCharacteristic mBle_char4 = null;
    private BluetoothGattCharacteristic mBle_char5 = null;
    private BluetoothGattCallback mGattCallback = new BluetoothGattCallback() { // from class: com.android.blerc.common.ViseBle.1
        @Override // android.bluetooth.BluetoothGattCallback
        public void onConnectionStateChange(BluetoothGatt bluetoothGatt, int i, int i2) {
            super.onConnectionStateChange(bluetoothGatt, i, i2);
            if (i == 0 && i2 == 2) {
                if (ViseBle.this.mOnConnectListener != null) {
                    ViseBle.this.mOnConnectListener.onConnect(bluetoothGatt);
                }
                ViseBle.this.mBluetoothGatt.discoverServices();
            } else if (i2 == 0) {
                if (ViseBle.this.mOnDisconnectListener != null) {
                    ViseBle.this.mOnDisconnectListener.onDisconnect(bluetoothGatt);
                }
                ViseBle.this.close();
            }
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onServicesDiscovered(BluetoothGatt bluetoothGatt, int i) {
            super.onServicesDiscovered(bluetoothGatt, i);
            if (i != 0 || ViseBle.this.mOnServiceDiscoverListener == null) {
                return;
            }
            ViseBle.this.mOnServiceDiscoverListener.onServiceDiscover(bluetoothGatt);
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onCharacteristicRead(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
            super.onCharacteristicRead(bluetoothGatt, bluetoothGattCharacteristic, i);
            if (ViseBle.this.mOnDataAvailableListener != null) {
                ViseBle.this.mOnDataAvailableListener.onCharacteristicRead(bluetoothGatt, bluetoothGattCharacteristic, i);
            } else {
                Log.d("CarMi", " mOnDataAvailableListener ");
            }
            if (bluetoothGattCharacteristic.getUuid().toString().equals(BleConstant.BATTERY_LEVEL)) {
                EventBus.getDefault().post(new RxEvent("battery:" + ((int) bluetoothGattCharacteristic.getValue()[0])));
                return;
            }
            if (bluetoothGattCharacteristic.getUuid().toString().equals(BleConstant.UUID_CHAR4)) {
                Log.d("CarMi", "lightswitch :  " + ((int) bluetoothGattCharacteristic.getValue()[0]));
                EventBus.getDefault().post(new RxEvent("light:" + ((int) bluetoothGattCharacteristic.getValue()[0])));
            }
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onCharacteristicChanged(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
            Log.d("CarMi", "onCharacteristicChanged UUID :  " + bluetoothGattCharacteristic.getUuid().toString());
            super.onCharacteristicChanged(bluetoothGatt, bluetoothGattCharacteristic);
            if (ViseBle.this.mOnDataAvailableListener != null) {
                ViseBle.this.mOnDataAvailableListener.onCharacteristicWrite(bluetoothGatt, bluetoothGattCharacteristic);
            }
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onDescriptorWrite(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i) {
            super.onDescriptorWrite(bluetoothGatt, bluetoothGattDescriptor, i);
            Log.d("CarMi", "onDescriptorWrite gatt :  " + bluetoothGatt);
            Log.d("CarMi", "onDescriptorWrite descriptor :  " + bluetoothGattDescriptor.getCharacteristic().getUuid());
            Log.d("CarMi", "onDescriptorWrite status :  " + i);
            if (i == 0 && bluetoothGattDescriptor.getCharacteristic().getUuid().toString().equals(BleConstant.UUID_CHAR2)) {
                ViseBle.getInstance().setCharacteristicNotification(ViseBle.this.getmBle_char5(), true);
            }
        }
    };

    public interface OnConnectListener {
        void onConnect(BluetoothGatt bluetoothGatt);
    }

    public interface OnDataAvailableListener {
        void onCharacteristicRead(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i);

        void onCharacteristicWrite(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic);
    }

    public interface OnDisconnectListener {
        void onDisconnect(BluetoothGatt bluetoothGatt);
    }

    public interface OnServiceDiscoverListener {
        void onServiceDiscover(BluetoothGatt bluetoothGatt);
    }

    public static ViseBle getInstance() {
        if (instance == null) {
            synchronized (ViseBle.class) {
                if (instance == null) {
                    instance = new ViseBle();
                }
            }
        }
        return instance;
    }

    private ViseBle() {
    }

    public BluetoothGattCharacteristic getmBle_char1() {
        return this.mBle_char1;
    }

    public void setmBle_char1(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        this.mBle_char1 = bluetoothGattCharacteristic;
    }

    public BluetoothGattCharacteristic getmBle_char2() {
        return this.mBle_char2;
    }

    public void setmBle_char2(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        this.mBle_char2 = bluetoothGattCharacteristic;
    }

    public BluetoothGattCharacteristic getmBle_char3() {
        return this.mBle_char3;
    }

    public void setmBle_char3(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        this.mBle_char3 = bluetoothGattCharacteristic;
    }

    public BluetoothGattCharacteristic getmBle_char4() {
        return this.mBle_char4;
    }

    public void setmBle_char4(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        this.mBle_char4 = bluetoothGattCharacteristic;
    }

    public BluetoothGattCharacteristic getmBle_char5() {
        return this.mBle_char5;
    }

    public void setmBle_char5(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        this.mBle_char5 = bluetoothGattCharacteristic;
    }

    public static BleConfig config() {
        return bleConfig;
    }

    public void init(Context context) {
        if (this.context != null || context == null) {
            return;
        }
        Context applicationContext = context.getApplicationContext();
        this.context = applicationContext;
        BluetoothManager bluetoothManager = (BluetoothManager) applicationContext.getSystemService("bluetooth");
        this.mBluetoothManager = bluetoothManager;
        this.mBluetoothAdapter = bluetoothManager.getAdapter();
    }

    public void startScan(ScanCallback scanCallback) {
        if (scanCallback == null) {
            throw new IllegalArgumentException("this ScanCallback is Null!");
        }
        scanCallback.setScan(true).scan();
    }

    public void stopScan(ScanCallback scanCallback) {
        if (scanCallback == null) {
            throw new IllegalArgumentException("this ScanCallback is Null!");
        }
        scanCallback.setScan(false).removeHandlerMsg().scan();
    }

    public BluetoothAdapter getBluetoothAdapter() {
        return this.mBluetoothAdapter;
    }

    public void setOnConnectListener(OnConnectListener onConnectListener) {
        this.mOnConnectListener = onConnectListener;
    }

    public void setOnDisconnectListener(OnDisconnectListener onDisconnectListener) {
        this.mOnDisconnectListener = onDisconnectListener;
    }

    public void setOnServiceDiscoverListener(OnServiceDiscoverListener onServiceDiscoverListener) {
        this.mOnServiceDiscoverListener = onServiceDiscoverListener;
    }

    public void setOnDataAvailableListener(OnDataAvailableListener onDataAvailableListener) {
        this.mOnDataAvailableListener = onDataAvailableListener;
    }

    public boolean connectBleMac(String str) {
        if (this.mBluetoothAdapter == null || TextUtils.isEmpty(str)) {
            return false;
        }
        BluetoothGatt bluetoothGatt = this.mBluetoothGatt;
        if (bluetoothGatt != null) {
            return bluetoothGatt.connect();
        }
        this.mBluetoothGatt = this.mBluetoothAdapter.getRemoteDevice(str).connectGatt(this.context, false, this.mGattCallback);
        return true;
    }

    public void disConnect() {
        BluetoothGatt bluetoothGatt;
        if (this.mBluetoothAdapter == null || (bluetoothGatt = this.mBluetoothGatt) == null) {
            return;
        }
        bluetoothGatt.disconnect();
    }

    public void close() {
        BluetoothGatt bluetoothGatt = this.mBluetoothGatt;
        if (bluetoothGatt == null) {
            return;
        }
        bluetoothGatt.close();
        this.mBluetoothGatt = null;
    }

    public void readCharacteristic(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        if (this.mBluetoothAdapter == null || this.mBluetoothGatt == null) {
            return;
        }
        Log.d("CarMi", "uuid :  " + bluetoothGattCharacteristic.getUuid().toString());
        this.mBluetoothGatt.readCharacteristic(bluetoothGattCharacteristic);
    }

    public void setCharacteristicNotification(BluetoothGattCharacteristic bluetoothGattCharacteristic, boolean z) {
        BluetoothGatt bluetoothGatt;
        BluetoothGattDescriptor descriptor;
        if (this.mBluetoothAdapter == null || (bluetoothGatt = this.mBluetoothGatt) == null || !bluetoothGatt.setCharacteristicNotification(bluetoothGattCharacteristic, z) || (descriptor = bluetoothGattCharacteristic.getDescriptor(UUID.fromString(BleConstant.CLIENT_CHARACTERISTIC_CONFIG))) == null) {
            return;
        }
        if (z) {
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        } else {
            descriptor.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
        }
        this.mBluetoothGatt.writeDescriptor(descriptor);
    }

    public void writeCharacteristic(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        this.mBluetoothGatt.writeCharacteristic(bluetoothGattCharacteristic);
    }

    public void writeCharacteristicNoResponse(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        BluetoothGatt bluetoothGatt;
        if (this.mBluetoothAdapter == null || (bluetoothGatt = this.mBluetoothGatt) == null) {
            return;
        }
        bluetoothGatt.writeCharacteristic(bluetoothGattCharacteristic);
    }

    public List<BluetoothGattService> getSupportedGattServices() {
        BluetoothGatt bluetoothGatt = this.mBluetoothGatt;
        if (bluetoothGatt == null) {
            return null;
        }
        return bluetoothGatt.getServices();
    }
}
