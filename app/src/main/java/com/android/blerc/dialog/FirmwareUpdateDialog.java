package com.android.blerc.dialog;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import com.android.blerc.BaseMainActivity;
import com.android.blerc.CircleProgressBar;
import com.android.blerc.R;
import com.android.blerc.bean.Splashbean;
import com.android.blerc.dfu.service.DfuService;
import com.jakewharton.rxbinding2.view.RxView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import io.reactivex.functions.Consumer;
import java.io.File;
import java.util.concurrent.TimeUnit;
import me.jessyan.progressmanager.ProgressListener;
import me.jessyan.progressmanager.ProgressManager;
import me.jessyan.progressmanager.body.ProgressInfo;
import no.nordicsemi.android.dfu.DfuProgressListener;
import no.nordicsemi.android.dfu.DfuServiceInitiator;
import no.nordicsemi.android.dfu.DfuServiceListenerHelper;
import okhttp3.Call;

/* loaded from: classes.dex */
public class FirmwareUpdateDialog extends BaseDialog {
    private final String TAG;
    public String address;
    View btn_divider;
    LinearLayout btn_layout;
    RelativeLayout check_layout;
    public String deviceName;
    private final DfuProgressListener dfuProgressListener;
    RelativeLayout download_layout;
    CircleProgressBar download_progressbar;
    LinearLayout fail_layout;
    RelativeLayout found_layout;
    View layout_divider;
    private final Context mContext;
    private Handler mHandler;
    private DfuServiceInitiator starter;
    int stepFlag;
    TextView submit;
    TextView tx_cancel;
    TextView tx_check;
    TextView tx_check_version;
    TextView tx_found;
    TextView tx_found_version;
    TextView version_tip;
    TextView version_title;

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onWindowFocusChanged(boolean z) {
        if (!z) {
        }
    }

    public FirmwareUpdateDialog(Context context) {
        super(context, R.style.dialog);
        this.TAG = "FirmwareUpdateDialog";
        this.stepFlag = 0;
        this.dfuProgressListener = new DfuProgressListener() { // from class: com.android.blerc.dialog.FirmwareUpdateDialog.1
            @Override // no.nordicsemi.android.dfu.DfuProgressListener
            public void onDeviceConnecting(String str) {
                Log.i("FirmwareUpdateDialog", "onDeviceConnecting: " + str);
            }

            @Override // no.nordicsemi.android.dfu.DfuProgressListener
            public void onDeviceConnected(String str) {
                Log.i("FirmwareUpdateDialog", "onDeviceConnected: " + str);
            }

            @Override // no.nordicsemi.android.dfu.DfuProgressListener
            public void onDfuProcessStarting(String str) {
                Log.i("FirmwareUpdateDialog", "onDfuProcessStarting: " + str);
                FirmwareUpdateDialog.this.download_progressbar.setProgress(0.0f);
            }

            @Override // no.nordicsemi.android.dfu.DfuProgressListener
            public void onDfuProcessStarted(String str) {
                Log.i("FirmwareUpdateDialog", "onDfuProcessStarted: " + str);
            }

            @Override // no.nordicsemi.android.dfu.DfuProgressListener
            public void onEnablingDfuMode(String str) {
                Log.i("FirmwareUpdateDialog", "onEnablingDfuMode: " + str);
            }

            @Override // no.nordicsemi.android.dfu.DfuProgressListener
            public void onProgressChanged(String str, int i, float f, float f2, int i2, int i3) {
                FirmwareUpdateDialog.this.download_progressbar.setProgress(i);
                FirmwareUpdateDialog.this.tx_check.setText(FirmwareUpdateDialog.this.mContext.getString(R.string.app_firmware_installing) + i + "%");
            }

            @Override // no.nordicsemi.android.dfu.DfuProgressListener
            public void onFirmwareValidating(String str) {
                Log.i("FirmwareUpdateDialog", "onFirmwareValidating: " + str);
            }

            @Override // no.nordicsemi.android.dfu.DfuProgressListener
            public void onDeviceDisconnecting(String str) {
                Log.i("FirmwareUpdateDialog", "onDeviceDisconnecting: " + str);
            }

            @Override // no.nordicsemi.android.dfu.DfuProgressListener
            public void onDeviceDisconnected(String str) {
                Log.i("FirmwareUpdateDialog", "onDeviceDisconnected: " + str);
            }

            @Override // no.nordicsemi.android.dfu.DfuProgressListener
            public void onDfuCompleted(String str) {
                FirmwareUpdateDialog.this.install_success();
            }

            @Override // no.nordicsemi.android.dfu.DfuProgressListener
            public void onDfuAborted(String str) {
                Log.i("FirmwareUpdateDialog", "onDfuAborted: " + str);
            }

            @Override // no.nordicsemi.android.dfu.DfuProgressListener
            public void onError(String str, int i, int i2, String str2) {
                FirmwareUpdateDialog.this.install_fail();
            }
        };
        this.mHandler = new Handler() { // from class: com.android.blerc.dialog.FirmwareUpdateDialog.4
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                super.handleMessage(message);
                if (message.what != 1) {
                    return;
                }
                FirmwareUpdateDialog.this.downloading();
            }
        };
        this.mContext = context;
    }

    public FirmwareUpdateDialog(Context context, String str, String str2) {
        super(context, R.style.dialog);
        this.TAG = "FirmwareUpdateDialog";
        this.stepFlag = 0;
        this.dfuProgressListener = new DfuProgressListener() { // from class: com.android.blerc.dialog.FirmwareUpdateDialog.1
            @Override // no.nordicsemi.android.dfu.DfuProgressListener
            public void onDeviceConnecting(String str3) {
                Log.i("FirmwareUpdateDialog", "onDeviceConnecting: " + str3);
            }

            @Override // no.nordicsemi.android.dfu.DfuProgressListener
            public void onDeviceConnected(String str3) {
                Log.i("FirmwareUpdateDialog", "onDeviceConnected: " + str3);
            }

            @Override // no.nordicsemi.android.dfu.DfuProgressListener
            public void onDfuProcessStarting(String str3) {
                Log.i("FirmwareUpdateDialog", "onDfuProcessStarting: " + str3);
                FirmwareUpdateDialog.this.download_progressbar.setProgress(0.0f);
            }

            @Override // no.nordicsemi.android.dfu.DfuProgressListener
            public void onDfuProcessStarted(String str3) {
                Log.i("FirmwareUpdateDialog", "onDfuProcessStarted: " + str3);
            }

            @Override // no.nordicsemi.android.dfu.DfuProgressListener
            public void onEnablingDfuMode(String str3) {
                Log.i("FirmwareUpdateDialog", "onEnablingDfuMode: " + str3);
            }

            @Override // no.nordicsemi.android.dfu.DfuProgressListener
            public void onProgressChanged(String str3, int i, float f, float f2, int i2, int i3) {
                FirmwareUpdateDialog.this.download_progressbar.setProgress(i);
                FirmwareUpdateDialog.this.tx_check.setText(FirmwareUpdateDialog.this.mContext.getString(R.string.app_firmware_installing) + i + "%");
            }

            @Override // no.nordicsemi.android.dfu.DfuProgressListener
            public void onFirmwareValidating(String str3) {
                Log.i("FirmwareUpdateDialog", "onFirmwareValidating: " + str3);
            }

            @Override // no.nordicsemi.android.dfu.DfuProgressListener
            public void onDeviceDisconnecting(String str3) {
                Log.i("FirmwareUpdateDialog", "onDeviceDisconnecting: " + str3);
            }

            @Override // no.nordicsemi.android.dfu.DfuProgressListener
            public void onDeviceDisconnected(String str3) {
                Log.i("FirmwareUpdateDialog", "onDeviceDisconnected: " + str3);
            }

            @Override // no.nordicsemi.android.dfu.DfuProgressListener
            public void onDfuCompleted(String str3) {
                FirmwareUpdateDialog.this.install_success();
            }

            @Override // no.nordicsemi.android.dfu.DfuProgressListener
            public void onDfuAborted(String str3) {
                Log.i("FirmwareUpdateDialog", "onDfuAborted: " + str3);
            }

            @Override // no.nordicsemi.android.dfu.DfuProgressListener
            public void onError(String str3, int i, int i2, String str22) {
                FirmwareUpdateDialog.this.install_fail();
            }
        };
        this.mHandler = new Handler() { // from class: com.android.blerc.dialog.FirmwareUpdateDialog.4
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                super.handleMessage(message);
                if (message.what != 1) {
                    return;
                }
                FirmwareUpdateDialog.this.downloading();
            }
        };
        this.mContext = context;
        this.address = str;
        this.deviceName = str2;
    }

    public void setAddress(String str) {
        this.address = str;
    }

    public void setDeviceName(String str) {
        this.deviceName = str;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_firmware_update);
        ButterKnife.bind(this);
        initView();
        this.starter = new DfuServiceInitiator(this.address).setDeviceName(this.deviceName).setKeepBond(true).setForceDfu(false).setDisableNotification(true).setPacketsReceiptNotificationsEnabled(true).setUnsafeExperimentalButtonlessServiceInSecureDfuEnabled(true);
    }

    @Override // android.app.Dialog
    protected void onStop() {
        super.onStop();
        DfuServiceListenerHelper.unregisterProgressListener(this.mContext, this.dfuProgressListener);
    }

    @Override // com.android.blerc.dialog.BaseDialog, android.app.Dialog
    protected void onStart() {
        super.onStart();
        found_confirm();
        DfuServiceListenerHelper.registerProgressListener(this.mContext, this.dfuProgressListener);
    }

    private void initView() {
        RxView.clicks(this.tx_cancel).throttleFirst(1L, TimeUnit.SECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.dialog.FirmwareUpdateDialog.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                int i = FirmwareUpdateDialog.this.stepFlag;
                if (i == 2) {
                    FirmwareUpdateDialog.this.dismiss();
                    return;
                }
                if (i == 3) {
                    FirmwareUpdateDialog.this.download_cancel_confirm();
                    return;
                }
                if (i == 4) {
                    FirmwareUpdateDialog.this.downloading();
                } else if (i == 6) {
                    FirmwareUpdateDialog.this.dismiss();
                } else {
                    if (i != 7) {
                        return;
                    }
                    FirmwareUpdateDialog.this.dismiss();
                }
            }
        });
        RxView.clicks(this.submit).throttleFirst(1L, TimeUnit.SECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.dialog.FirmwareUpdateDialog.3
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                switch (FirmwareUpdateDialog.this.stepFlag) {
                    case 1:
                        FirmwareUpdateDialog.this.dismiss();
                        break;
                    case 2:
                        FirmwareUpdateDialog.this.downloading();
                        break;
                    case 4:
                        FirmwareUpdateDialog.this.dismiss();
                        break;
                    case 5:
                        FirmwareUpdateDialog.this.downloading();
                        break;
                    case 6:
                        FirmwareUpdateDialog.this.installing();
                        break;
                    case 7:
                        FirmwareUpdateDialog.this.dismiss();
                        break;
                    case 8:
                        FirmwareUpdateDialog.this.dismiss();
                        break;
                    case 9:
                        FirmwareUpdateDialog.this.dismiss();
                        break;
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void download_cancel_confirm() {
        this.stepFlag = 4;
        this.version_title.setVisibility(8);
        this.version_tip.setVisibility(0);
        this.found_layout.setVisibility(8);
        this.check_layout.setVisibility(8);
        this.download_layout.setVisibility(8);
        this.fail_layout.setVisibility(8);
        this.layout_divider.setVisibility(0);
        this.btn_layout.setVisibility(0);
        this.tx_cancel.setVisibility(0);
        this.btn_divider.setVisibility(0);
        this.submit.setVisibility(0);
        this.version_tip.setText(R.string.app_version_dl_confirm);
        this.tx_cancel.setText(R.string.no); // TODO wuyijun original: R.string.f4no
        this.submit.setText(R.string.yes);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void download_fail() {
        this.stepFlag = 5;
        this.version_title.setVisibility(0);
        this.version_tip.setVisibility(8);
        this.found_layout.setVisibility(8);
        this.check_layout.setVisibility(8);
        this.download_layout.setVisibility(8);
        this.fail_layout.setVisibility(0);
        this.layout_divider.setVisibility(0);
        this.btn_layout.setVisibility(0);
        this.tx_cancel.setVisibility(8);
        this.btn_divider.setVisibility(8);
        this.submit.setVisibility(0);
        this.version_title.setText(R.string.app_version_dl_fail);
        this.submit.setText(R.string.app_version_dl_redo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void download_done() {
        this.stepFlag = 6;
        this.version_title.setVisibility(8);
        this.version_tip.setVisibility(0);
        this.found_layout.setVisibility(8);
        this.check_layout.setVisibility(8);
        this.download_layout.setVisibility(8);
        this.fail_layout.setVisibility(8);
        this.layout_divider.setVisibility(0);
        this.btn_layout.setVisibility(0);
        this.tx_cancel.setVisibility(0);
        this.btn_divider.setVisibility(0);
        this.submit.setVisibility(0);
        this.version_tip.setText(R.string.app_firmware_dl_done);
        this.tx_cancel.setText(R.string.cancel);
        this.submit.setText(R.string.app_firmware_install);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void downloading() {
        this.stepFlag = 3;
        this.version_title.setVisibility(0);
        this.version_tip.setVisibility(8);
        this.found_layout.setVisibility(8);
        this.check_layout.setVisibility(8);
        this.download_layout.setVisibility(0);
        this.fail_layout.setVisibility(8);
        this.layout_divider.setVisibility(0);
        this.btn_layout.setVisibility(0);
        this.tx_cancel.setVisibility(0);
        this.btn_divider.setVisibility(8);
        this.submit.setVisibility(8);
        this.version_title.setText(R.string.app_firmware_title);
        OkHttpUtils.get().url(Splashbean.getInstance().getFirmware_update()).build().execute(new FileCallBack(Splashbean.getInstance().getDownloadPath(), "jimny.zip") { // from class: com.android.blerc.dialog.FirmwareUpdateDialog.5
            @Override // com.zhy.http.okhttp.callback.Callback
            public void onError(Call call, Exception exc, int i) {
                FirmwareUpdateDialog.this.download_fail();
            }

            @Override // com.zhy.http.okhttp.callback.Callback
            public void onResponse(File file, int i) {
                FirmwareUpdateDialog.this.download_done();
            }
        });
        ProgressManager.getInstance().addResponseListener(Splashbean.getInstance().getFirmware_update(), new ProgressListener() { // from class: com.android.blerc.dialog.FirmwareUpdateDialog.6
            @Override // me.jessyan.progressmanager.ProgressListener
            public void onProgress(ProgressInfo progressInfo) {
                FirmwareUpdateDialog.this.download_progressbar.setProgress(progressInfo.getPercent());
                FirmwareUpdateDialog.this.tx_check.setText(FirmwareUpdateDialog.this.mContext.getString(R.string.app_version_downloading) + progressInfo.getPercent() + "%");
            }

            @Override // me.jessyan.progressmanager.ProgressListener
            public void onError(long j, Exception exc) {
                FirmwareUpdateDialog.this.download_fail();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void installing() {
        this.stepFlag = 7;
        this.version_title.setVisibility(0);
        this.version_tip.setVisibility(8);
        this.found_layout.setVisibility(8);
        this.check_layout.setVisibility(8);
        this.download_layout.setVisibility(0);
        this.fail_layout.setVisibility(8);
        this.layout_divider.setVisibility(0);
        this.btn_layout.setVisibility(0);
        this.tx_cancel.setVisibility(0);
        this.btn_divider.setVisibility(8);
        this.submit.setVisibility(8);
        this.tx_check.setText(R.string.app_firmware_installing);
        this.tx_check_version.setText(R.string.app_firmware_installing_tip);
        this.starter.setZip(Splashbean.getInstance().getDownloadPath() + "jimny.zip");
        if (Build.VERSION.SDK_INT >= 26) {
            DfuServiceInitiator.createDfuNotificationChannel(this.mContext);
        }
        this.starter.start(this.mContext, DfuService.class);
        ((BaseMainActivity) this.mContext).setDfu(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void install_success() {
        this.stepFlag = 8;
        this.version_title.setVisibility(8);
        this.version_tip.setVisibility(0);
        this.found_layout.setVisibility(8);
        this.check_layout.setVisibility(8);
        this.download_layout.setVisibility(8);
        this.fail_layout.setVisibility(8);
        this.layout_divider.setVisibility(0);
        this.btn_layout.setVisibility(0);
        this.tx_cancel.setVisibility(8);
        this.btn_divider.setVisibility(8);
        this.submit.setVisibility(0);
        this.version_tip.setText(R.string.app_firmware_install_success);
        this.submit.setText(R.string.app_reconnect);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void install_fail() {
        this.stepFlag = 9;
        this.version_title.setVisibility(0);
        this.version_tip.setVisibility(8);
        this.found_layout.setVisibility(8);
        this.check_layout.setVisibility(8);
        this.download_layout.setVisibility(8);
        this.fail_layout.setVisibility(0);
        this.layout_divider.setVisibility(0);
        this.btn_layout.setVisibility(0);
        this.tx_cancel.setVisibility(8);
        this.btn_divider.setVisibility(8);
        this.submit.setVisibility(0);
        this.version_title.setText(R.string.app_firmware_install_fail);
        this.submit.setText(R.string.ok);
    }

    private void found_confirm() {
        this.stepFlag = 2;
        this.version_title.setVisibility(0);
        this.version_tip.setVisibility(8);
        this.found_layout.setVisibility(0);
        this.check_layout.setVisibility(8);
        this.download_layout.setVisibility(8);
        this.fail_layout.setVisibility(8);
        this.layout_divider.setVisibility(0);
        this.btn_layout.setVisibility(0);
        this.tx_cancel.setVisibility(0);
        this.btn_divider.setVisibility(0);
        this.submit.setVisibility(0);
        this.version_title.setText(R.string.app_firmware_title);
        this.tx_found_version.setText("v" + Splashbean.getInstance().getFirmware_ver());
        this.tx_cancel.setText(R.string.cancel);
        this.submit.setText(R.string.app_version_now);
    }

    private void latest() {
        this.stepFlag = 1;
        this.version_title.setVisibility(8);
        this.version_tip.setVisibility(0);
        this.found_layout.setVisibility(8);
        this.check_layout.setVisibility(8);
        this.download_layout.setVisibility(8);
        this.fail_layout.setVisibility(8);
        this.layout_divider.setVisibility(0);
        this.btn_layout.setVisibility(0);
        this.tx_cancel.setVisibility(8);
        this.btn_divider.setVisibility(8);
        this.submit.setVisibility(0);
    }
}
