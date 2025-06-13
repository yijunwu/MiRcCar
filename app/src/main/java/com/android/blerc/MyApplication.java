package com.android.blerc;

import android.app.Application;
import android.webkit.WebView;
import com.android.blerc.db.SharedPreferencesUtils;
import com.android.blerc.exception.CrashHandler;
import com.zhy.http.okhttp.OkHttpUtils;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.progressmanager.ProgressManager;
import okhttp3.OkHttpClient;

/* loaded from: classes.dex */
public class MyApplication extends Application {
    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(getApplicationContext());
        initSharedPreference();
        new WebView(this).destroy();
        OkHttpUtils.initClient(ProgressManager.getInstance().with(new OkHttpClient.Builder()).hostnameVerifier(new HostnameVerifier() { // from class: com.android.blerc.MyApplication.1
            @Override // javax.net.ssl.HostnameVerifier
            public boolean verify(String str, SSLSession sSLSession) {
                return true;
            }
        }).connectTimeout(5L, TimeUnit.SECONDS).readTimeout(5L, TimeUnit.SECONDS).build());
        AutoSizeConfig.getInstance().setBaseOnWidth(false);
        ViewPump.init(ViewPump.builder().addInterceptor(new CalligraphyInterceptor(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/MI_LanTing_GB.ttf").setFontAttrId(R.attr.fontPath).build())).build());
    }

    private void initSharedPreference() {
        if (!getApplicationContext().getSharedPreferences("share_data", 0).contains("turn_middle")) {
            SharedPreferencesUtils.setParam(getApplicationContext(), "turn_middle", 45);
            SharedPreferencesUtils.setParam(getApplicationContext(), "turn_dr", 80);
            SharedPreferencesUtils.setParam(getApplicationContext(), "accelerator_dr", 60);
            SharedPreferencesUtils.setParam(getApplicationContext(), "low_speed", false);
            SharedPreferencesUtils.setParam(getApplicationContext(), "right_hand", true);
        }
        SharedPreferencesUtils.setParam(getApplicationContext(), "sensor", false);
    }
}
