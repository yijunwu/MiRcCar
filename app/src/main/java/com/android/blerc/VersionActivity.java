package com.android.blerc;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import com.android.blerc.common.SystemInfo;
import com.android.blerc.db.DBConstant;
import com.jakewharton.rxbinding2.view.RxView;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class VersionActivity extends BaseActivity {
    private final String TAG = "Setting";
    ImageView garage_back_img;
    TextView tx_bin_version;
    TextView tx_current_version;
    TextView tx_garage_tip;
    TextView tx_garage_title;

    @Override // com.android.blerc.BaseActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_about_version);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        this.tx_garage_title.setText(R.string.setting_version_title);
        this.tx_garage_tip.setText(R.string.setting_version_tip);
        RxView.clicks(this.garage_back_img).throttleFirst(1L, TimeUnit.SECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.VersionActivity.1
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                VersionActivity.this.finish();
            }
        });
        this.tx_current_version.setText(getString(R.string.setting_version_current) + "  v" + DBConstant.getInstance(this).getAppVersion());
        this.tx_bin_version.setText(getString(R.string.setting_version_bin) + " " + SystemInfo.getInstance().getFirmwareVersion());
    }
}
