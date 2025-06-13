package com.android.blerc;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.rcfans.R;

/* loaded from: classes.dex */
public class SettingActivity_ViewBinding implements Unbinder {
    private SettingActivity target;

    public SettingActivity_ViewBinding(SettingActivity settingActivity) {
        this(settingActivity, settingActivity.getWindow().getDecorView());
    }

    public SettingActivity_ViewBinding(SettingActivity settingActivity, View view) {
        this.target = settingActivity;
        settingActivity.garage_back_img = (ImageView) Utils.findRequiredViewAsType(view, R.id.garage_back_img, "field 'garage_back_img'", ImageView.class);
        settingActivity.tx_garage_title = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_garage_title, "field 'tx_garage_title'", TextView.class);
        settingActivity.tx_garage_tip = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_garage_tip, "field 'tx_garage_tip'", TextView.class);
        settingActivity.preference_layout = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.preference_layout, "field 'preference_layout'", LinearLayout.class);
        settingActivity.language_layout = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.language_layout, "field 'language_layout'", LinearLayout.class);
        settingActivity.guide_layout = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.guide_layout, "field 'guide_layout'", LinearLayout.class);
        settingActivity.version_layout = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.version_layout, "field 'version_layout'", LinearLayout.class);
        settingActivity.about_layout = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.about_layout, "field 'about_layout'", LinearLayout.class);
        settingActivity.tx_preference_tip = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_preference_tip, "field 'tx_preference_tip'", TextView.class);
        settingActivity.tx_language_tip = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_language_tip, "field 'tx_language_tip'", TextView.class);
        settingActivity.tx_version_tip = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_version_tip, "field 'tx_version_tip'", TextView.class);
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        SettingActivity settingActivity = this.target;
        if (settingActivity == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        settingActivity.garage_back_img = null;
        settingActivity.tx_garage_title = null;
        settingActivity.tx_garage_tip = null;
        settingActivity.preference_layout = null;
        settingActivity.language_layout = null;
        settingActivity.guide_layout = null;
        settingActivity.version_layout = null;
        settingActivity.about_layout = null;
        settingActivity.tx_preference_tip = null;
        settingActivity.tx_language_tip = null;
        settingActivity.tx_version_tip = null;
    }
}
