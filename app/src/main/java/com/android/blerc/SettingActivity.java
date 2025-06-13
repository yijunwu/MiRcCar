package com.android.blerc;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import com.andorid.blerc.AboutActivity;
import com.andorid.blerc.BaseActivity;
import com.andorid.blerc.GuideActivity;
import com.andorid.blerc.LanguageActivity;
import com.andorid.blerc.PreferenceActivity;
import com.andorid.blerc.db.DBConstant;
import com.jakewharton.rxbinding2.view.RxView;
import com.rcfans.R;
import io.reactivex.functions.Consumer;
import java.util.List;
import java.util.concurrent.TimeUnit;
import pub.devrel.easypermissions.EasyPermissions;

/* loaded from: classes.dex */
public class SettingActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    private final String TAG = "Setting";
    LinearLayout about_layout;
    AppVersionDialog appVersionDialog;
    ImageView garage_back_img;
    LinearLayout guide_layout;
    LinearLayout language_layout;
    LinearLayout preference_layout;
    TextView tx_garage_tip;
    TextView tx_garage_title;
    TextView tx_language_tip;
    TextView tx_preference_tip;
    TextView tx_version_tip;
    LinearLayout version_layout;

    @Override // pub.devrel.easypermissions.EasyPermissions.PermissionCallbacks
    public void onPermissionsGranted(int i, List<String> list) {
    }

    @Override // com.andorid.blerc.BaseActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        this.appVersionDialog = new AppVersionDialog(this);
        initView();
    }

    @Override // com.andorid.blerc.BaseActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        onCreate(null);
        if (DBConstant.getInstance(this).isRightHand()) {
            this.tx_preference_tip.setText(R.string.right_hand);
        } else {
            this.tx_preference_tip.setText(R.string.left_hand);
        }
        if (DBConstant.getInstance(this).getLanguage().equals("zh")) {
            this.tx_language_tip.setText(R.string.chinese);
        } else {
            this.tx_language_tip.setText(R.string.english);
        }
        this.tx_version_tip.setText(DBConstant.getInstance(this).getAppVersion());
    }

    @Override // android.app.Activity
    protected void onStop() {
        super.onStop();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        this.tx_garage_title.setText(R.string.setting);
        this.tx_garage_tip.setText(R.string.setting_tip);
        RxView.clicks(this.garage_back_img).throttleFirst(1L, TimeUnit.SECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.SettingActivity.1
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                SettingActivity.this.finish();
            }
        });
        RxView.clicks(this.preference_layout).throttleFirst(1L, TimeUnit.SECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.SettingActivity.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                SettingActivity.this.startActivity(new Intent(SettingActivity.this, (Class<?>) PreferenceActivity.class));
            }
        });
        RxView.clicks(this.language_layout).throttleFirst(1L, TimeUnit.SECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.SettingActivity.3
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                SettingActivity.this.startActivity(new Intent(SettingActivity.this, (Class<?>) LanguageActivity.class));
            }
        });
        RxView.clicks(this.guide_layout).throttleFirst(1L, TimeUnit.SECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.SettingActivity.4
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                SettingActivity.this.startActivity(new Intent(SettingActivity.this, (Class<?>) GuideActivity.class));
            }
        });
        RxView.clicks(this.version_layout).throttleFirst(1L, TimeUnit.SECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.SettingActivity.5
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                SettingActivity.this.dialogShowing();
            }
        });
        RxView.clicks(this.about_layout).throttleFirst(1L, TimeUnit.SECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.SettingActivity.6
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                SettingActivity.this.startActivity(new Intent(SettingActivity.this, (Class<?>) AboutActivity.class));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dialogShowing() {
        AppVersionDialog appVersionDialog = this.appVersionDialog;
        if (appVersionDialog == null) {
            if (appVersionDialog.isShowing()) {
                return;
            }
            this.appVersionDialog.setCanceledOnTouchOutside(false);
            this.appVersionDialog.show();
            return;
        }
        if (appVersionDialog.isShowing()) {
            return;
        }
        this.appVersionDialog.setCanceledOnTouchOutside(false);
        this.appVersionDialog.show();
    }

    public void installApkO() {
        InstallUtils.installApk(this);
    }

    @Override // android.app.Activity, android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        EasyPermissions.onRequestPermissionsResult(i, strArr, iArr, this);
    }

    @Override // pub.devrel.easypermissions.EasyPermissions.PermissionCallbacks
    public void onPermissionsDenied(int i, List<String> list) {
        EasyPermissions.somePermissionPermanentlyDenied(this, list);
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
    }
}
