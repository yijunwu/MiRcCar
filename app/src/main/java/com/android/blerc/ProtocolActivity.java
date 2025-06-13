package com.android.blerc;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import com.android.blerc.db.DBConstant;
import com.jakewharton.rxbinding2.view.RxView;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class ProtocolActivity extends BaseActivity {
    public static final int TYPE_AGREEMENT = 1;
    public static final int TYPE_PRIVACY = 2;
    private final String TAG = "Setting";
    ImageView garage_back_img;
    TextView tx_garage_tip;
    TextView tx_garage_title;
    WebView wv_webview;

    @Override // com.android.blerc.BaseActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_about_protocol);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        String str;
        if (getIntent().getIntExtra("type", 1) == 1) {
            this.tx_garage_title.setText(R.string.setting_about_protocol);
            this.tx_garage_tip.setText(R.string.setting_protocol_tip);
            str = DBConstant.getInstance(this).getLanguage().equals("zh") ? "https://www.megahobby.cn/Jimny/userAgument.html" : "https://www.megahobby.cn/Jimny/userAgument-EN.html";
        } else {
            this.tx_garage_title.setText(R.string.setting_about_privacy);
            this.tx_garage_tip.setText(R.string.setting_privacy_tip);
            str = DBConstant.getInstance(this).getLanguage().equals("zh") ? "https://www.megahobby.cn/Jimny/PrivacyPolicy.html" : "https://www.megahobby.cn/Jimny/PrivacyPolicy-EN.html";
        }
        WebSettings settings = this.wv_webview.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        this.wv_webview.loadUrl(str);
        this.wv_webview.setWebViewClient(new WebViewClient() { // from class: com.android.blerc.ProtocolActivity.1
            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView webView, String str2) {
                webView.loadUrl(str2);
                return true;
            }
        });
        RxView.clicks(this.garage_back_img).throttleFirst(1L, TimeUnit.SECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.ProtocolActivity.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                ProtocolActivity.this.finish();
            }
        });
    }
}
