package com.android.blerc;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;

/* loaded from: classes.dex */
public class AboutActivity_ViewBinding implements Unbinder {
    private AboutActivity target;

    public AboutActivity_ViewBinding(AboutActivity aboutActivity) {
        this(aboutActivity, aboutActivity.getWindow().getDecorView());
    }

    public AboutActivity_ViewBinding(AboutActivity aboutActivity, View view) {
        this.target = aboutActivity;
        aboutActivity.garage_back_img = (ImageView) Utils.findRequiredViewAsType(view, R.id.garage_back_img, "field 'garage_back_img'", ImageView.class);
        aboutActivity.tx_garage_title = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_garage_title, "field 'tx_garage_title'", TextView.class);
        aboutActivity.tx_garage_tip = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_garage_tip, "field 'tx_garage_tip'", TextView.class);
        aboutActivity.version_layout = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.version_layout, "field 'version_layout'", LinearLayout.class);
        aboutActivity.protocol_layout = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.protocol_layout, "field 'protocol_layout'", LinearLayout.class);
        aboutActivity.privacy_layout = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.privacy_layout, "field 'privacy_layout'", LinearLayout.class);
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        AboutActivity aboutActivity = this.target;
        if (aboutActivity == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        aboutActivity.garage_back_img = null;
        aboutActivity.tx_garage_title = null;
        aboutActivity.tx_garage_tip = null;
        aboutActivity.version_layout = null;
        aboutActivity.protocol_layout = null;
        aboutActivity.privacy_layout = null;
    }
}
