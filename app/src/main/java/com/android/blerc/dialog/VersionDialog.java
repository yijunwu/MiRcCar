package com.android.blerc.dialog;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;

import com.android.blerc.R;
import com.android.blerc.common.AppMsg;
import com.android.blerc.common.SystemInfo;
import com.jakewharton.rxbinding2.view.RxView;

import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class VersionDialog extends BaseDialog {
    private final String TAG;
    ImageView app_updata;
    ImageView firmware_updata;
    private final Context mContext;
    TextView ok;
    TextView tv_app_version;
    TextView tv_firmware_version;

    public VersionDialog(Context context) {
        super(context, R.style.dialog);
        this.TAG = "VersionDialog";
        this.mContext = context;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_version);
        ButterKnife.bind(this);
        initListView();
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
        WindowManager.LayoutParams attributes2 = window.getAttributes();
        double d = displayMetrics.heightPixels;
        Double.isNaN(d);
        attributes2.height = (int) (d * 0.8d);
        double d2 = displayMetrics.widthPixels;
        Double.isNaN(d2);
        attributes2.width = (int) (d2 * 0.5d);
        window.setAttributes(attributes);
    }

    @Override // android.app.Dialog
    protected void onStop() {
        super.onStop();
    }

    @Override // com.android.blerc.dialog.BaseDialog, android.app.Dialog
    protected void onStart() {
        super.onStart();
        this.tv_app_version.setText(AppMsg.getVersion(this.mContext));
        this.tv_firmware_version.setText(SystemInfo.getInstance().getFirmwareVersion());
    }

    private void initListView() {
        RxView.clicks(this.ok).throttleFirst(1L, TimeUnit.SECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.dialog.VersionDialog.1
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                VersionDialog.this.dismiss();
            }
        });
        RxView.clicks(this.app_updata).throttleFirst(1L, TimeUnit.SECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.dialog.VersionDialog.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                Toast.makeText(VersionDialog.this.mContext, "当前已是最新版本", 0).show();
            }
        });
        RxView.clicks(this.firmware_updata).throttleFirst(1L, TimeUnit.SECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.dialog.VersionDialog.3
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                Log.d("VersionDialog", "START DFU ");
                VersionDialog.this.dismiss();
                new DfuUpdateDialog(VersionDialog.this.mContext).show();
            }
        });
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
}
