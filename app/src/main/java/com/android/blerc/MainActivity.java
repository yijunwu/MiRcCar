package com.android.blerc;

import android.content.Intent;
import com.andorid.blerc.BaseMainActivity;

/* loaded from: classes.dex */
public class MainActivity extends BaseMainActivity {
    @Override // com.andorid.blerc.BaseMainActivity
    public void jumpSetting() {
        startActivity(new Intent(this, (Class<?>) SettingActivity.class));
    }
}
