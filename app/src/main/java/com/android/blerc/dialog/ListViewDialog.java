package com.android.blerc.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;

import com.android.blerc.R;
import com.android.blerc.adapter.DeviceAdapter;
import com.android.blerc.common.BleConfig;
import com.android.blerc.common.BleDeviceManager;
import com.android.blerc.common.DeviceMsg;
import com.android.blerc.common.RxEvent;
import com.android.blerc.common.ViseBle;
import com.android.blerc.model.BluetoothLeDevice;
import com.android.blerc.model.BluetoothLeDeviceStore;
import com.android.blerc.scan.IScanCallback;
import com.android.blerc.scan.ScanCallback;
import com.jakewharton.rxbinding2.view.RxView;

import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.EventBus;

/* loaded from: classes.dex */
public class ListViewDialog extends BaseDialog {
    private final String TAG;
    private DeviceAdapter adapter;
    LinearLayout ble_list_layout;
    TextView ble_list_title;
    private BluetoothLeDeviceStore bluetoothLeDeviceStore;
    View btn_divider;
    LinearLayout btn_layout;
    ArrayList<DeviceMsg> deviceMsgs;
    ImageView device_connected;
    ProgressBar device_loading;
    LinearLayout fail_layout;
    View layout_divider;
    private final Context mContext;
    private Handler mHandler;
    ListView mListView;
    private ScanCallback periodScanCallback;
    ImageView previous_device_connected;
    ProgressBar previous_device_loading;
    TextView start_scan;
    RelativeLayout stop_scan;
    TextView submit;
    int timeout;

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onWindowFocusChanged(boolean z) {
        if (!z) {
        }
    }

    public ListViewDialog(Context context) {
        super(context, R.style.dialog);
        this.TAG = "ListViewDialog";
        this.bluetoothLeDeviceStore = new BluetoothLeDeviceStore();
        this.deviceMsgs = new ArrayList<>();
        this.timeout = 0;
        this.mHandler = new Handler() { // from class: com.android.blerc.dialog.ListViewDialog.4
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                super.handleMessage(message);
                int i = message.what;
                if (i == 0) {
                    if (ListViewDialog.this.adapter == null || ListViewDialog.this.bluetoothLeDeviceStore == null) {
                        return;
                    }
                    ListViewDialog.this.adapter.setListAll(ListViewDialog.this.bluetoothLeDeviceStore.getDeviceList());
                    return;
                }
                if (i != 1) {
                    if (i == 2) {
                        ListViewDialog.this.ble_list_layout.setVisibility(8);
                        ListViewDialog.this.fail_layout.setVisibility(0);
                        ListViewDialog.this.ble_list_title.setText(R.string.connect_fail);
                        ListViewDialog.this.device_loading.setVisibility(8);
                        ListViewDialog.this.device_connected.setVisibility(8);
                        ListViewDialog.this.timeout = 0;
                        ListViewDialog.this.stopScan();
                        return;
                    }
                    if (i != 3) {
                        return;
                    }
                    if (ViseBle.getInstance().connectBleMac(ListViewDialog.this.bluetoothLeDeviceStore.getDeviceList().get(((Integer) message.obj).intValue()).getAddress())) {
                        Message message2 = new Message();
                        message2.what = 1;
                        message2.obj = message.obj;
                        ListViewDialog.this.timeout = 0;
                        ListViewDialog.this.mHandler.sendMessageDelayed(message2, 500L);
                        return;
                    }
                    ListViewDialog.this.ble_list_layout.setVisibility(8);
                    ListViewDialog.this.fail_layout.setVisibility(0);
                    return;
                }
                if (BleDeviceManager.getInstance().isBleConnectState()) {
                    BleConfig.getInstance().setMac(ListViewDialog.this.bluetoothLeDeviceStore.getDeviceList().get(((Integer) message.obj).intValue()).getAddress());
                    BleConfig.getInstance().setName(ListViewDialog.this.bluetoothLeDeviceStore.getDeviceList().get(((Integer) message.obj).intValue()).getName());
                    ListViewDialog.this.ble_list_layout.setVisibility(0);
                    ListViewDialog.this.device_loading.setVisibility(8);
                    ListViewDialog.this.device_connected.setVisibility(0);
                    ListViewDialog.this.btn_divider.setVisibility(0);
                    ListViewDialog.this.submit.setVisibility(0);
                    ListViewDialog listViewDialog = ListViewDialog.this;
                    listViewDialog.previous_device_connected = listViewDialog.device_connected;
                    return;
                }
                if (ListViewDialog.this.timeout > 3) {
                    ListViewDialog.this.mHandler.sendEmptyMessage(2);
                    return;
                }
                Message message3 = new Message();
                message3.what = 1;
                message3.obj = message.obj;
                ListViewDialog.this.timeout++;
                ListViewDialog.this.mHandler.sendMessageDelayed(message3, 1000L);
            }
        };
        this.periodScanCallback = new ScanCallback(new IScanCallback() { // from class: com.android.blerc.dialog.ListViewDialog.5
            @Override // com.android.blerc.scan.IScanCallback
            public void onDeviceFound(BluetoothLeDevice bluetoothLeDevice) {
                if (bluetoothLeDevice.getName() != null) {
                    Log.d("ListViewDialog", "Founded Scan Device:" + bluetoothLeDevice.getName());
                    if (bluetoothLeDevice.getName().startsWith("AJ") || bluetoothLeDevice.getName().startsWith("AJ") || bluetoothLeDevice.getName().startsWith("AR") || bluetoothLeDevice.getName().startsWith("Dfu")) {
                        ListViewDialog.this.bluetoothLeDeviceStore.addDevice(bluetoothLeDevice);
                        ListViewDialog.this.mHandler.sendEmptyMessage(0);
                    }
                }
            }

            @Override // com.android.blerc.scan.IScanCallback
            public void onScanFinish(BluetoothLeDeviceStore bluetoothLeDeviceStore) {
                Log.d("ListViewDialog", "scan finish " + bluetoothLeDeviceStore);
                ListViewDialog.this.stop_scan.setVisibility(8);
                ListViewDialog.this.layout_divider.setVisibility(0);
                ListViewDialog.this.ble_list_layout.setVisibility(0);
                ListViewDialog.this.btn_layout.setVisibility(0);
                ListViewDialog.this.stopScan();
                if (ListViewDialog.this.adapter.isEmpty()) {
                    EventBus.getDefault().post(new RxEvent("showNoDeviceTip"));
                }
            }

            @Override // com.android.blerc.scan.IScanCallback
            public void onScanTimeout() {
                Log.d("ListViewDialog", "scan timeout");
                ListViewDialog.this.stop_scan.setVisibility(8);
                ListViewDialog.this.layout_divider.setVisibility(0);
                ListViewDialog.this.btn_layout.setVisibility(0);
                ListViewDialog.this.stopScan();
                if (ListViewDialog.this.adapter.isEmpty()) {
                    EventBus.getDefault().post(new RxEvent("showNoDeviceTip"));
                }
            }
        });
        this.mContext = context;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.content_dialog);
        ButterKnife.bind(this);
        initListView();
    }

    @Override // android.app.Dialog
    protected void onStop() {
        super.onStop();
        stopScan();
    }

    @Override // com.android.blerc.dialog.BaseDialog, android.app.Dialog
    protected void onStart() {
        super.onStart();
        BluetoothLeDeviceStore bluetoothLeDeviceStore = this.bluetoothLeDeviceStore;
        if (bluetoothLeDeviceStore != null) {
            bluetoothLeDeviceStore.clear();
        }
        this.btn_divider.setVisibility(8);
        this.submit.setVisibility(8);
        startScan();
    }

    private void initListView() {
        DeviceAdapter deviceAdapter = new DeviceAdapter(this.mContext);
        this.adapter = deviceAdapter;
        this.mListView.setAdapter((ListAdapter) deviceAdapter);
        this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.android.blerc.dialog.ListViewDialog.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                Log.d("ListViewDialog", "ADDRESS : " + ListViewDialog.this.bluetoothLeDeviceStore.getDeviceList().get(i).getAddress());
                Log.d("ListViewDialog", "NAME : " + ListViewDialog.this.bluetoothLeDeviceStore.getDeviceList().get(i).getName());
                Log.d("ListViewDialog", "RSSI : " + ListViewDialog.this.bluetoothLeDeviceStore.getDeviceList().get(i).getRssi());
                ListViewDialog.this.bluetoothLeDeviceStore.getDeviceList().get(i).getAddress();
                ListViewDialog.this.device_loading = (ProgressBar) view.findViewById(R.id.device_loading);
                ListViewDialog.this.device_connected = (ImageView) view.findViewById(R.id.device_connected);
                if (ListViewDialog.this.previous_device_loading != null) {
                    ListViewDialog.this.previous_device_loading.setVisibility(8);
                }
                ListViewDialog listViewDialog = ListViewDialog.this;
                listViewDialog.previous_device_loading = listViewDialog.device_loading;
                if (BleDeviceManager.getInstance().isBleConnectState()) {
                    ViseBle.getInstance().disConnect();
                    ListViewDialog.this.device_loading.setVisibility(0);
                    if (ListViewDialog.this.previous_device_connected != null) {
                        ListViewDialog.this.previous_device_connected.setVisibility(8);
                    }
                    Message message = new Message();
                    message.what = 3;
                    message.obj = Integer.valueOf(i);
                    ListViewDialog.this.mHandler.sendMessageDelayed(message, 1000L);
                    return;
                }
                ListViewDialog.this.device_loading.setVisibility(0);
                if (ListViewDialog.this.bluetoothLeDeviceStore.getDeviceList().get(i).getName().contains("SN")) {
                    Message message2 = new Message();
                    message2.what = 3;
                    message2.obj = Integer.valueOf(i);
                    ListViewDialog.this.mHandler.sendMessage(message2);
                    return;
                }
                FirmwareUpdateDialog firmwareUpdateDialog = new FirmwareUpdateDialog(ListViewDialog.this.mContext, ListViewDialog.this.bluetoothLeDeviceStore.getDeviceList().get(i).getAddress(), ListViewDialog.this.bluetoothLeDeviceStore.getDeviceList().get(i).getName());
                firmwareUpdateDialog.setCanceledOnTouchOutside(false);
                firmwareUpdateDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.blerc.dialog.ListViewDialog.1.1
                    @Override // android.content.DialogInterface.OnDismissListener
                    public void onDismiss(DialogInterface dialogInterface) {
                        ListViewDialog.this.dismiss();
                    }
                });
                firmwareUpdateDialog.show();
            }
        });
        RxView.clicks(this.start_scan).throttleFirst(1L, TimeUnit.SECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.dialog.ListViewDialog.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                Log.d("ListViewDialog", "START SCAN ");
                ListViewDialog.this.startScan();
                ListViewDialog.this.stop_scan.setVisibility(0);
                ListViewDialog.this.ble_list_layout.setVisibility(8);
                ListViewDialog.this.layout_divider.setVisibility(8);
                ListViewDialog.this.btn_layout.setVisibility(8);
                ListViewDialog.this.fail_layout.setVisibility(8);
                ListViewDialog.this.ble_list_title.setText(R.string.ble_list);
            }
        });
        RxView.clicks(this.submit).throttleFirst(1L, TimeUnit.SECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.dialog.ListViewDialog.3
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                ListViewDialog.this.dismiss();
            }
        });
    }

    public void startScan() {
        DeviceAdapter deviceAdapter = this.adapter;
        if (deviceAdapter != null) {
            deviceAdapter.setListAll(new ArrayList());
        }
        ViseBle.getInstance().startScan(this.periodScanCallback);
        invalidateOptionsMenu();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopScan() {
        ViseBle.getInstance().stopScan(this.periodScanCallback);
        invalidateOptionsMenu();
    }
}
