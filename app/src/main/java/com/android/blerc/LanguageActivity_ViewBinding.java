package com.android.blerc;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;

/* loaded from: classes.dex */
public class LanguageActivity_ViewBinding implements Unbinder {
    private LanguageActivity target;

    public LanguageActivity_ViewBinding(LanguageActivity languageActivity) {
        this(languageActivity, languageActivity.getWindow().getDecorView());
    }

    public LanguageActivity_ViewBinding(LanguageActivity languageActivity, View view) {
        this.target = languageActivity;
        languageActivity.garage_back_img = (ImageView) Utils.findRequiredViewAsType(view, R.id.garage_back_img, "field 'garage_back_img'", ImageView.class);
        languageActivity.tx_garage_title = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_garage_title, "field 'tx_garage_title'", TextView.class);
        languageActivity.tx_garage_tip = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_garage_tip, "field 'tx_garage_tip'", TextView.class);
        languageActivity.chinese_layout = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.chinese_layout, "field 'chinese_layout'", LinearLayout.class);
        languageActivity.eng_layout = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.eng_layout, "field 'eng_layout'", LinearLayout.class);
        languageActivity.chinese_img = (ImageView) Utils.findRequiredViewAsType(view, R.id.chinese_img, "field 'chinese_img'", ImageView.class);
        languageActivity.tx_chinese = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_chinese, "field 'tx_chinese'", TextView.class);
        languageActivity.eng_img = (ImageView) Utils.findRequiredViewAsType(view, R.id.eng_img, "field 'eng_img'", ImageView.class);
        languageActivity.tx_eng = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_eng, "field 'tx_eng'", TextView.class);
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        LanguageActivity languageActivity = this.target;
        if (languageActivity == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        languageActivity.garage_back_img = null;
        languageActivity.tx_garage_title = null;
        languageActivity.tx_garage_tip = null;
        languageActivity.chinese_layout = null;
        languageActivity.eng_layout = null;
        languageActivity.chinese_img = null;
        languageActivity.tx_chinese = null;
        languageActivity.eng_img = null;
        languageActivity.tx_eng = null;
    }
}
