package com.android.blerc;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;

/* loaded from: classes.dex */
public class VersionActivity_ViewBinding implements Unbinder {
    private VersionActivity target;

    public VersionActivity_ViewBinding(VersionActivity versionActivity) {
        this(versionActivity, versionActivity.getWindow().getDecorView());
    }

    public VersionActivity_ViewBinding(VersionActivity versionActivity, View view) {
        this.target = versionActivity;
        versionActivity.garage_back_img = (ImageView) Utils.findRequiredViewAsType(view, R.id.garage_back_img, "field 'garage_back_img'", ImageView.class);
        versionActivity.tx_garage_title = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_garage_title, "field 'tx_garage_title'", TextView.class);
        versionActivity.tx_garage_tip = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_garage_tip, "field 'tx_garage_tip'", TextView.class);
        versionActivity.tx_current_version = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_current_version, "field 'tx_current_version'", TextView.class);
        versionActivity.tx_bin_version = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_bin_version, "field 'tx_bin_version'", TextView.class);
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        VersionActivity versionActivity = this.target;
        if (versionActivity == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        versionActivity.garage_back_img = null;
        versionActivity.tx_garage_title = null;
        versionActivity.tx_garage_tip = null;
        versionActivity.tx_current_version = null;
        versionActivity.tx_bin_version = null;
    }
}
