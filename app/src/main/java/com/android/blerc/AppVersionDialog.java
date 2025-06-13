package com.android.blerc;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import com.android.blerc.CircleProgressBar;
import com.android.blerc.bean.Splashbean;
import com.android.blerc.db.DBConstant;
import com.android.blerc.dialog.BaseDialog;
import com.android.blerc.util.NetworkUtils;
import com.android.blerc.util.Utils;
import com.jakewharton.rxbinding2.view.RxView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class AppVersionDialog extends BaseDialog {
    private final String TAG;
    View btn_divider;
    LinearLayout btn_layout;
    RelativeLayout check_layout;
    RelativeLayout download_layout;
    CircleProgressBar download_progressbar;
    LinearLayout fail_layout;
    RelativeLayout found_layout;
    boolean isListen;
    View layout_divider;
    private final Context mContext;
    private boolean splash;
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

    public AppVersionDialog(Context context) {
        super(context, R.style.dialog);
        this.TAG = "AppVersionDialog";
        this.splash = false;
        this.stepFlag = 0;
        this.isListen = false;
        this.mContext = context;
    }

    public AppVersionDialog(Context context, boolean z) {
        super(context, R.style.dialog);
        this.TAG = "AppVersionDialog";
        this.splash = false;
        this.stepFlag = 0;
        this.isListen = false;
        this.mContext = context;
        this.splash = z;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_appversion);
        ButterKnife.bind(this);
        initView();
    }

    @Override // android.app.Dialog
    protected void onStop() {
        super.onStop();
        this.isListen = false;
    }

    @Override // com.android.blerc.dialog.BaseDialog, android.app.Dialog
    protected void onStart() {
        super.onStart();
        if (this.splash) {
            found_confirm();
        } else {
            checking();
        }
    }

    private void initView() {
        RxView.clicks(this.tx_cancel).throttleFirst(1L, TimeUnit.SECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.AppVersionDialog.1
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                AppVersionDialog.this.dismiss();
            }
        });
        RxView.clicks(this.submit).throttleFirst(1L, TimeUnit.SECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.AppVersionDialog.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                if (AppVersionDialog.this.stepFlag == 2) {
                    if (AppVersionDialog.this.splash) {
                        ((SplashActivity) AppVersionDialog.this.mContext).installApkO();
                    } else {
                        ((SettingActivity) AppVersionDialog.this.mContext).installApkO();
                    }
                }
                AppVersionDialog.this.dismiss();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void found_confirm() {
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
        this.version_title.setText(R.string.app_version_title);
        this.tx_found_version.setText("v" + Splashbean.getInstance().getApp_ver());
        this.tx_cancel.setText(R.string.cancel);
        this.submit.setText(R.string.app_version_now);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void latest() {
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

    private void checking() {
        if (NetworkUtils.isConnected()) {
            OkHttpUtils.get().url(Utils.version_url).build().execute(new StringCallback() { // from class: com.android.blerc.AppVersionDialog.3
                @Override // com.zhy.http.okhttp.callback.Callback
                public void onError(Call call, Exception exc, int i) {
                    Log.d("AppVersionDialog", "Exception : " + exc.toString());
                    AppVersionDialog.this.latest();
                }

                @Override // com.zhy.http.okhttp.callback.Callback
                public void onResponse(String str, int i) throws NumberFormatException {
                    Log.d("AppVersionDialog", "response : " + str);
                    try {
                        JSONObject jSONObject = new JSONObject(str);
                        Log.d("AppVersionDialog", "app_ver :  " + jSONObject.getString("app_ver"));
                        Log.d("AppVersionDialog", "app_update :  " + jSONObject.getString("app_update"));
                        Log.d("AppVersionDialog", "firmware_ver :  " + jSONObject.getString("firmware_ver"));
                        Log.d("AppVersionDialog", "firmware_update :  " + jSONObject.getString("firmware_update"));
                        Log.d("AppVersionDialog", "start :  " + jSONObject.getString("start"));
                        Splashbean.getInstance().setApp_ver(jSONObject.getString("app_ver"));
                        Splashbean.getInstance().setApp_update(jSONObject.getString("app_update"));
                        Splashbean.getInstance().setFirmware_ver(jSONObject.getString("firmware_ver"));
                        Splashbean.getInstance().setFirmware_update(jSONObject.getString("firmware_update"));
                        if (Integer.parseInt(jSONObject.getString("app_ver").replaceAll("\\.", "")) > Integer.parseInt(DBConstant.getInstance(AppVersionDialog.this.mContext).getAppVersion().replaceAll("\\.", ""))) {
                            AppVersionDialog.this.found_confirm();
                        } else {
                            AppVersionDialog.this.latest();
                        }
                    } catch (JSONException unused) {
                    }
                }
            });
        }
    }
}
