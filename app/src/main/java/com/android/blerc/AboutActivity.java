package com.android.blerc;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import com.jakewharton.rxbinding2.view.RxView;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class AboutActivity extends BaseActivity {
    private final String TAG = "Setting";
    ImageView garage_back_img;
    LinearLayout privacy_layout;
    LinearLayout protocol_layout;
    TextView tx_garage_tip;
    TextView tx_garage_title;
    LinearLayout version_layout;

    @Override // com.android.blerc.BaseActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        this.tx_garage_title.setText(R.string.setting_about_title);
        this.tx_garage_tip.setText(R.string.setting_about_tip);
        RxView.clicks(this.garage_back_img).throttleFirst(1L, TimeUnit.SECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.AboutActivity.1
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                AboutActivity.this.finish();
            }
        });
        RxView.clicks(this.version_layout).throttleFirst(1L, TimeUnit.SECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.AboutActivity.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                AboutActivity.this.startActivity(new Intent(AboutActivity.this, (Class<?>) VersionActivity.class));
            }
        });
        RxView.clicks(this.protocol_layout).throttleFirst(1L, TimeUnit.SECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.AboutActivity.3
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                Intent intent = new Intent(AboutActivity.this, (Class<?>) ProtocolActivity.class);
                intent.putExtra("type", 1);
                AboutActivity.this.startActivity(intent);
            }
        });
        RxView.clicks(this.privacy_layout).throttleFirst(1L, TimeUnit.SECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.AboutActivity.4
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                Intent intent = new Intent(AboutActivity.this, (Class<?>) ProtocolActivity.class);
                intent.putExtra("type", 2);
                AboutActivity.this.startActivity(intent);
            }
        });
    }
}
