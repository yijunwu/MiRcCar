package com.android.blerc.dfu.service;

import android.app.Activity;
import android.content.Intent;
import android.os.IBinder;
import no.nordicsemi.android.dfu.DfuBaseService;

/* loaded from: classes.dex */
public class DfuService extends DfuBaseService {
    @Override // no.nordicsemi.android.dfu.DfuBaseService
    protected boolean isDebug() {
        return false;
    }

    @Override // android.app.IntentService, android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // no.nordicsemi.android.dfu.DfuBaseService
    protected Class<? extends Activity> getNotificationTarget() {
        return NotificationActivity.class;
    }
}
