package com.android.blerc.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;
import butterknife.ButterKnife;

import com.android.blerc.R;
import com.android.blerc.common.BleConfig;
import com.android.blerc.dfu.service.DfuService;

import java.io.File;
import no.nordicsemi.android.dfu.DfuProgressListener;
import no.nordicsemi.android.dfu.DfuServiceInitiator;
import no.nordicsemi.android.dfu.DfuServiceListenerHelper;

/* loaded from: classes.dex */
public class DfuUpdateDialog extends BaseDialog {
    private final String TAG;
    private final DfuProgressListener dfuProgressListener;
    ProgressBar dfu_progress;
    private final Context mContext;

    private void initListView() {
    }

    public DfuUpdateDialog(Context context) {
        super(context, R.style.dialog);
        this.TAG = "DfuUpdateDialog";
        this.dfuProgressListener = new DfuProgressListener() { // from class: com.android.blerc.dialog.DfuUpdateDialog.1
            @Override // no.nordicsemi.android.dfu.DfuProgressListener
            public void onDeviceConnecting(String str) {
                Log.i("DfuUpdateDialog", "onDeviceConnecting: " + str);
            }

            @Override // no.nordicsemi.android.dfu.DfuProgressListener
            public void onDeviceConnected(String str) {
                Log.i("DfuUpdateDialog", "onDeviceConnected: " + str);
            }

            @Override // no.nordicsemi.android.dfu.DfuProgressListener
            public void onDfuProcessStarting(String str) {
                Log.i("DfuUpdateDialog", "onDfuProcessStarting: " + str);
            }

            @Override // no.nordicsemi.android.dfu.DfuProgressListener
            public void onDfuProcessStarted(String str) {
                Log.i("DfuUpdateDialog", "onDfuProcessStarted: " + str);
            }

            @Override // no.nordicsemi.android.dfu.DfuProgressListener
            public void onEnablingDfuMode(String str) {
                Log.i("DfuUpdateDialog", "onEnablingDfuMode: " + str);
            }

            @Override // no.nordicsemi.android.dfu.DfuProgressListener
            public void onProgressChanged(String str, int i, float f, float f2, int i2, int i3) {
                Log.i("DfuUpdateDialog", "onProgressChanged: " + str + "百分比" + i + ",speed " + f + ",avgSpeed " + f2 + ",currentPart " + i2 + ",partTotal " + i3);
                DfuUpdateDialog.this.dfu_progress.setProgress(i);
            }

            @Override // no.nordicsemi.android.dfu.DfuProgressListener
            public void onFirmwareValidating(String str) {
                Log.i("DfuUpdateDialog", "onFirmwareValidating: " + str);
            }

            @Override // no.nordicsemi.android.dfu.DfuProgressListener
            public void onDeviceDisconnecting(String str) {
                Log.i("DfuUpdateDialog", "onDeviceDisconnecting: " + str);
            }

            @Override // no.nordicsemi.android.dfu.DfuProgressListener
            public void onDeviceDisconnected(String str) {
                Log.i("DfuUpdateDialog", "onDeviceDisconnected: " + str);
            }

            @Override // no.nordicsemi.android.dfu.DfuProgressListener
            public void onDfuCompleted(String str) {
                Log.i("DfuUpdateDialog", "onDfuCompleted: " + str);
                DfuUpdateDialog.this.dismiss();
                DfuUpdateDialog.this.showNormalDialog("升级成功");
            }

            @Override // no.nordicsemi.android.dfu.DfuProgressListener
            public void onDfuAborted(String str) {
                Log.i("DfuUpdateDialog", "onDfuAborted: " + str);
            }

            @Override // no.nordicsemi.android.dfu.DfuProgressListener
            public void onError(String str, int i, int i2, String str2) {
                Log.i("DfuUpdateDialog", "onError: " + str + "  ,error: " + i + " ,message:" + str2);
                DfuUpdateDialog.this.dismiss();
                DfuUpdateDialog.this.showNormalDialog("升级失败");
            }
        };
        this.mContext = context;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_dfu);
        ButterKnife.bind(this);
        initListView();
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
        WindowManager.LayoutParams attributes2 = window.getAttributes();
        double d = displayMetrics.heightPixels;
        Double.isNaN(d);
        attributes2.height = (int) (d * 0.5d);
        double d2 = displayMetrics.widthPixels;
        Double.isNaN(d2);
        attributes2.width = (int) (d2 * 0.6d);
        window.setAttributes(attributes);
        if (new File("/mnt/sdcard/DfuUpdate.zip").exists()) {
            Log.d("DfuUpdateDialog", " MAC :  " + BleConfig.getInstance().getMac());
            Log.d("DfuUpdateDialog", " NAME :  " + BleConfig.getInstance().getName());
            DfuServiceInitiator unsafeExperimentalButtonlessServiceInSecureDfuEnabled = new DfuServiceInitiator(BleConfig.getInstance().getMac()).setDeviceName(BleConfig.getInstance().getName()).setKeepBond(true).setForceDfu(false).setDisableNotification(true).setPacketsReceiptNotificationsEnabled(true).setUnsafeExperimentalButtonlessServiceInSecureDfuEnabled(true);
            Log.d("DfuUpdateDialog", " path :  /mnt/sdcard/DfuUpdate.zip");
            unsafeExperimentalButtonlessServiceInSecureDfuEnabled.setZip("/mnt/sdcard/DfuUpdate.zip");
            unsafeExperimentalButtonlessServiceInSecureDfuEnabled.start(this.mContext, DfuService.class);
            return;
        }
        Toast.makeText(this.mContext, "升级文件不存在。。。", 0).show();
    }

    @Override // android.app.Dialog
    protected void onStop() {
        super.onStop();
        DfuServiceListenerHelper.unregisterProgressListener(this.mContext, this.dfuProgressListener);
    }

    @Override // com.android.blerc.dialog.BaseDialog, android.app.Dialog
    protected void onStart() {
        super.onStart();
        DfuServiceListenerHelper.registerProgressListener(this.mContext, this.dfuProgressListener);
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onWindowFocusChanged(boolean z) {
        if (z) {
            setHeight();
        }
    }

    private void setHeight() {
        Window window = getWindow();
        DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
        WindowManager.LayoutParams attributes = window.getAttributes();
        int height = window.getDecorView().getHeight();
        double d = displayMetrics.heightPixels;
        Double.isNaN(d);
        if (height >= ((int) (d * 0.8d))) {
            double d2 = displayMetrics.heightPixels;
            Double.isNaN(d2);
            attributes.height = (int) (d2 * 0.8d);
        }
        window.setAttributes(attributes);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showNormalDialog(String str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("提示");
        builder.setMessage(str);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { // from class: com.android.blerc.dialog.DfuUpdateDialog.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}
