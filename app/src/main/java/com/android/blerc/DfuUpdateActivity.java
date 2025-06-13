package com.android.blerc;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import com.android.blerc.common.BleConfig;
import com.android.blerc.dfu.service.DfuService;
import com.jakewharton.rxbinding2.view.RxView;
import io.reactivex.functions.Consumer;
import java.io.File;
import java.util.concurrent.TimeUnit;
import no.nordicsemi.android.dfu.DfuProgressListener;
import no.nordicsemi.android.dfu.DfuServiceInitiator;
import no.nordicsemi.android.dfu.DfuServiceListenerHelper;

/* loaded from: classes.dex */
public class DfuUpdateActivity extends BaseActivity {
    private final String TAG = "CarMi";
    private final DfuProgressListener dfuProgressListener = new DfuProgressListener() { // from class: com.android.blerc.DfuUpdateActivity.2
        @Override // no.nordicsemi.android.dfu.DfuProgressListener
        public void onDeviceConnecting(String str) {
            Log.i("CarMi", "onDeviceConnecting: " + str);
        }

        @Override // no.nordicsemi.android.dfu.DfuProgressListener
        public void onDeviceConnected(String str) {
            Log.i("CarMi", "onDeviceConnected: " + str);
        }

        @Override // no.nordicsemi.android.dfu.DfuProgressListener
        public void onDfuProcessStarting(String str) {
            Log.i("CarMi", "onDfuProcessStarting: " + str);
        }

        @Override // no.nordicsemi.android.dfu.DfuProgressListener
        public void onDfuProcessStarted(String str) {
            Log.i("CarMi", "onDfuProcessStarted: " + str);
        }

        @Override // no.nordicsemi.android.dfu.DfuProgressListener
        public void onEnablingDfuMode(String str) {
            Log.i("CarMi", "onEnablingDfuMode: " + str);
        }

        @Override // no.nordicsemi.android.dfu.DfuProgressListener
        public void onProgressChanged(String str, int i, float f, float f2, int i2, int i3) {
            Log.i("CarMi", "onProgressChanged: " + str + "百分比" + i + ",speed " + f + ",avgSpeed " + f2 + ",currentPart " + i2 + ",partTotal " + i3);
            DfuUpdateActivity.this.progressBar.setProgress(i);
            TextView textView = DfuUpdateActivity.this.tv_show;
            StringBuilder sb = new StringBuilder();
            sb.append("升级进度：");
            sb.append(i);
            sb.append("%");
            textView.setText(sb.toString());
        }

        @Override // no.nordicsemi.android.dfu.DfuProgressListener
        public void onFirmwareValidating(String str) {
            Log.i("CarMi", "onFirmwareValidating: " + str);
        }

        @Override // no.nordicsemi.android.dfu.DfuProgressListener
        public void onDeviceDisconnecting(String str) {
            Log.i("CarMi", "onDeviceDisconnecting: " + str);
        }

        @Override // no.nordicsemi.android.dfu.DfuProgressListener
        public void onDeviceDisconnected(String str) {
            Log.i("CarMi", "onDeviceDisconnected: " + str);
        }

        @Override // no.nordicsemi.android.dfu.DfuProgressListener
        public void onDfuCompleted(String str) {
            Log.i("CarMi", "onDfuCompleted: " + str);
            DfuUpdateActivity.this.progressBar.setVisibility(8);
        }

        @Override // no.nordicsemi.android.dfu.DfuProgressListener
        public void onDfuAborted(String str) {
            Log.i("CarMi", "onDfuAborted: " + str);
            DfuUpdateActivity.this.progressBar.setVisibility(8);
        }

        @Override // no.nordicsemi.android.dfu.DfuProgressListener
        public void onError(String str, int i, int i2, String str2) {
            Log.i("CarMi", "onError: " + str + "  ,error: " + i + " ,message:" + str2);
            DfuUpdateActivity.this.tv_show.setText("升级失败");
            DfuUpdateActivity.this.progressBar.setVisibility(8);
        }
    };
    private String path;
    ProgressBar progressBar;
    Button startBtn;
    TextView tv_show;

    @Override // com.android.blerc.BaseActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_dfu);
        ButterKnife.bind(this);
        RxView.clicks(this.startBtn).throttleFirst(1L, TimeUnit.SECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.DfuUpdateActivity.1
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                if (new File("/mnt/sdcard/DfuUpdate.zip").exists()) {
                    DfuUpdateActivity.this.tv_show.setText("/mnt/sdcard/DfuUpdate.zip");
                    DfuUpdateActivity.this.progressBar.setVisibility(0);
                    Log.d("CarMi", " MAC :  " + BleConfig.getInstance().getMac());
                    Log.d("CarMi", " NAME :  " + BleConfig.getInstance().getName());
                    DfuServiceInitiator unsafeExperimentalButtonlessServiceInSecureDfuEnabled = new DfuServiceInitiator(BleConfig.getInstance().getMac()).setDeviceName(BleConfig.getInstance().getName()).setKeepBond(true).setForceDfu(false).setDisableNotification(true).setPacketsReceiptNotificationsEnabled(true).setUnsafeExperimentalButtonlessServiceInSecureDfuEnabled(true);
                    Log.d("CarMi", " path :  /mnt/sdcard/DfuUpdate.zip");
                    unsafeExperimentalButtonlessServiceInSecureDfuEnabled.setZip("/mnt/sdcard/DfuUpdate.zip");
                    unsafeExperimentalButtonlessServiceInSecureDfuEnabled.start(DfuUpdateActivity.this, DfuService.class);
                    return;
                }
                Toast.makeText(DfuUpdateActivity.this, "升级文件不存在。。。", 0).show();
            }
        });
    }

    @Override // com.android.blerc.BaseActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        DfuServiceListenerHelper.registerProgressListener(this, this.dfuProgressListener);
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
        DfuServiceListenerHelper.unregisterProgressListener(this, this.dfuProgressListener);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
    }
}
