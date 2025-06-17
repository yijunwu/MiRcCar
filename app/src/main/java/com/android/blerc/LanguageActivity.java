package com.android.blerc;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import com.android.blerc.common.RxEvent;
import com.android.blerc.db.DBConstant;
import com.jakewharton.rxbinding2.view.RxView;
import io.reactivex.functions.Consumer;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.EventBus;

/* loaded from: classes.dex */
public class LanguageActivity extends BaseActivity {
    private final String TAG = "Setting";
    ImageView chinese_img;
    LinearLayout chinese_layout;
    ImageView eng_img;
    LinearLayout eng_layout;
    ImageView garage_back_img;
    TextView tx_chinese;
    TextView tx_eng;
    TextView tx_garage_tip;
    TextView tx_garage_title;

    @Override // com.android.blerc.BaseActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_language);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        this.tx_garage_title.setText(R.string.setting_language_title);
        this.tx_garage_tip.setText(R.string.setting_language_tip);
        RxView.clicks(this.garage_back_img).throttleFirst(1L, TimeUnit.SECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.LanguageActivity.1
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                LanguageActivity.this.finish();
            }
        });
        if (DBConstant.getInstance(this).getLanguage().equals("zh")) {
            setChineseView();
        } else {
            setEngView();
        }
        RxView.clicks(this.chinese_layout).throttleFirst(1L, TimeUnit.SECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.LanguageActivity.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                LanguageActivity.this.updateActivity("zh");
            }
        });
        RxView.clicks(this.eng_layout).throttleFirst(1L, TimeUnit.SECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.LanguageActivity.3
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                LanguageActivity.this.updateActivity("en");
            }
        });
    }

    private void setChineseView() {
        this.chinese_img.setVisibility(0);
        this.chinese_img.setBackgroundResource(R.mipmap.setting_arrow_blue);
        this.tx_chinese.setTextColor(Color.parseColor("#246fff"));
        this.eng_img.setVisibility(4);
        this.tx_eng.setTextColor(Color.parseColor("#ffffff"));
    }

    private void setEngView() {
        this.chinese_img.setVisibility(4);
        this.tx_chinese.setTextColor(Color.parseColor("#ffffff"));
        this.eng_img.setVisibility(0);
        this.eng_img.setBackgroundResource(R.mipmap.setting_arrow_blue);
        this.tx_eng.setTextColor(Color.parseColor("#246fff"));
    }

    public void updateActivity(String str) {
        DBConstant.getInstance(this).setLanguage(str);
        Locale locale = new Locale(str);
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, displayMetrics);
        recreateWithCompatibility();
        EventBus.getDefault().post(new RxEvent("languageSwitch"));
    }
}
