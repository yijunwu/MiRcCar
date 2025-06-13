package com.android.blerc.dfu.service;

import android.content.Intent;
import android.os.Bundle;
import com.android.blerc.DfuUpdateActivity;
import com.android.blerc.dfu.BaseActivity;

/* loaded from: classes.dex */
public class NotificationActivity extends BaseActivity {
    @Override // com.android.blerc.dfu.BaseActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (isTaskRoot()) {
            Intent intent = new Intent(this, (Class<?>) DfuUpdateActivity.class);
            intent.addFlags(268435456);
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                intent.putExtras(extras);
                startActivity(intent);
            }
        }
        finish();
    }
}
