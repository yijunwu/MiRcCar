package com.android.blerc;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import com.android.blerc.common.BleConstant;
import com.android.blerc.common.BleDeviceManager;
import com.android.blerc.common.RxEvent;
import com.android.blerc.common.ViseBle;
import com.android.blerc.util.HexUtil;
import io.reactivex.disposables.Disposable;
import java.util.Iterator;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

/* loaded from: classes.dex */
public class BarBaseActivity extends BaseActivity {
    private Disposable mDevStateDisposable;
    FrameLayout mViewContent;
    private String TAG = " CarMi ";
    private ViseBle.OnConnectListener mConnectListener = new ViseBle.OnConnectListener() { // from class: com.android.blerc.BarBaseActivity.1
        @Override // com.android.blerc.common.ViseBle.OnConnectListener
        public void onConnect(BluetoothGatt bluetoothGatt) {
            Log.e(BarBaseActivity.this.TAG, "      mConnectListener        ");
        }
    };
    private ViseBle.OnDisconnectListener mDisconnectListener = new ViseBle.OnDisconnectListener() { // from class: com.android.blerc.BarBaseActivity.2
        @Override // com.android.blerc.common.ViseBle.OnDisconnectListener
        public void onDisconnect(BluetoothGatt bluetoothGatt) {
            Log.e(BarBaseActivity.this.TAG, "      mDisconnectListener        " + bluetoothGatt.getDevice().getAddress());
            BleDeviceManager.getInstance().setBleConnectState(false);
            EventBus.getDefault().post(new RxEvent("bledisconnect"));
        }
    };
    private ViseBle.OnServiceDiscoverListener mServiceDiscoverListener = new ViseBle.OnServiceDiscoverListener() { // from class: com.android.blerc.BarBaseActivity.3
        @Override // com.android.blerc.common.ViseBle.OnServiceDiscoverListener
        public void onServiceDiscover(BluetoothGatt bluetoothGatt) {
            BarBaseActivity.this.displayGatServices(ViseBle.getInstance().getSupportedGattServices());
        }
    };
    private ViseBle.OnDataAvailableListener mDataAvailableListener = new ViseBle.OnDataAvailableListener() { // from class: com.android.blerc.BarBaseActivity.4
        @Override // com.android.blerc.common.ViseBle.OnDataAvailableListener
        public void onCharacteristicRead(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
        }

        @Override // com.android.blerc.common.ViseBle.OnDataAvailableListener
        public void onCharacteristicWrite(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
            if (bluetoothGattCharacteristic.getUuid().toString().equals(BleConstant.BATTERY_LEVEL)) {
                Log.d("CarMi", "battery :  " + ((int) bluetoothGattCharacteristic.getValue()[0]));
                EventBus.getDefault().post(new RxEvent("battery:" + ((int) bluetoothGattCharacteristic.getValue()[0])));
                return;
            }
            EventBus.getDefault().post(new RxEvent(HexUtil.encodeHexStr(bluetoothGattCharacteristic.getValue())));
        }
    };

    @Override // com.android.blerc.BaseActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        super.setContentView(R.layout.activity_barbase);
        this.mViewContent = (FrameLayout) findViewById(R.id.viewContent);
        BleDeviceManager.getInstance().init(this);
        ViseBle.getInstance().setOnConnectListener(this.mConnectListener);
        ViseBle.getInstance().setOnDisconnectListener(this.mDisconnectListener);
        ViseBle.getInstance().setOnServiceDiscoverListener(this.mServiceDiscoverListener);
        ViseBle.getInstance().setOnDataAvailableListener(this.mDataAvailableListener);
    }

    @Override // android.app.Activity
    public void setContentView(int i) {
        getLayoutInflater().inflate(i, this.mViewContent);
    }

    public void writeChar1(byte[] bArr) {
        if (ViseBle.getInstance().getmBle_char1() != null) {
            ViseBle.getInstance().getmBle_char1().setValue(bArr);
            Log.e(this.TAG, "writeChar1  :  " + HexUtil.encodeHexStr(bArr));
            ViseBle.getInstance().writeCharacteristic(ViseBle.getInstance().getmBle_char1());
        }
    }

    public void writeChar2(byte[] bArr) {
        if (ViseBle.getInstance().getmBle_char2() != null) {
            ViseBle.getInstance().getmBle_char2().setValue(bArr);
            ViseBle.getInstance().writeCharacteristic(ViseBle.getInstance().getmBle_char2());
        }
    }

    public void writeChar4(byte[] bArr) {
        if (ViseBle.getInstance().getmBle_char4() != null) {
            ViseBle.getInstance().getmBle_char4().setValue(bArr);
            ViseBle.getInstance().writeCharacteristic(ViseBle.getInstance().getmBle_char4());
        }
    }

    public void writeChar3(byte[] bArr) {
        if (BleDeviceManager.getInstance().isBleConnectState()) {
            if (ViseBle.getInstance().getmBle_char3() != null) {
                ViseBle.getInstance().getmBle_char3().setValue(bArr);
                ViseBle.getInstance().writeCharacteristic(ViseBle.getInstance().getmBle_char3());
                return;
            }
            return;
        }
        Log.e(this.TAG, "      33333333333333333333333333333        ");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void displayGatServices(List<BluetoothGattService> list) {
        if (list == null) {
            return;
        }
        Iterator<BluetoothGattService> it = list.iterator();
        while (it.hasNext()) {
            for (BluetoothGattCharacteristic bluetoothGattCharacteristic : it.next().getCharacteristics()) {
                Log.e(this.TAG, "UUID : " + bluetoothGattCharacteristic.getUuid().toString());
                if (bluetoothGattCharacteristic.getUuid().toString().equals(BleConstant.UUID_CHAR1)) {
                    ViseBle.getInstance().setmBle_char1(bluetoothGattCharacteristic);
                }
                if (bluetoothGattCharacteristic.getUuid().toString().equals(BleConstant.UUID_CHAR2)) {
                    ViseBle.getInstance().setmBle_char2(bluetoothGattCharacteristic);
                    ViseBle.getInstance().setCharacteristicNotification(bluetoothGattCharacteristic, true);
                }
                if (bluetoothGattCharacteristic.getUuid().toString().equals(BleConstant.UUID_CHAR3)) {
                    ViseBle.getInstance().setmBle_char3(bluetoothGattCharacteristic);
                    bluetoothGattCharacteristic.setWriteType(1);
                }
                if (bluetoothGattCharacteristic.getUuid().toString().equals(BleConstant.UUID_CHAR4)) {
                    ViseBle.getInstance().setmBle_char4(bluetoothGattCharacteristic);
                }
                if (bluetoothGattCharacteristic.getUuid().toString().equals(BleConstant.BATTERY_LEVEL)) {
                    ViseBle.getInstance().setmBle_char5(bluetoothGattCharacteristic);
                }
            }
        }
        BleDeviceManager.getInstance().setBleConnectState(true);
        EventBus.getDefault().post(new RxEvent("bleconnect"));
    }
}
