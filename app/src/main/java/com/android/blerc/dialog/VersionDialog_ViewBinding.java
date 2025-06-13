package com.android.blerc.dialog;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.blerc.R;

import butterknife.Unbinder;
import butterknife.internal.Utils;


/* loaded from: classes.dex */
public class VersionDialog_ViewBinding implements Unbinder {
    private VersionDialog target;

    public VersionDialog_ViewBinding(VersionDialog versionDialog) {
        this(versionDialog, versionDialog.getWindow().getDecorView());
    }

    public VersionDialog_ViewBinding(VersionDialog versionDialog, View view) {
        this.target = versionDialog;
        versionDialog.ok = (TextView) Utils.findRequiredViewAsType(view, R.id.ok, "field 'ok'", TextView.class);
        versionDialog.tv_app_version = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_app_version, "field 'tv_app_version'", TextView.class);
        versionDialog.tv_firmware_version = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_firmware_version, "field 'tv_firmware_version'", TextView.class);
        versionDialog.app_updata = (ImageView) Utils.findRequiredViewAsType(view, R.id.app_updata, "field 'app_updata'", ImageView.class);
        versionDialog.firmware_updata = (ImageView) Utils.findRequiredViewAsType(view, R.id.firmware_updata, "field 'firmware_updata'", ImageView.class);
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        VersionDialog versionDialog = this.target;
        if (versionDialog == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        versionDialog.ok = null;
        versionDialog.tv_app_version = null;
        versionDialog.tv_firmware_version = null;
        versionDialog.app_updata = null;
        versionDialog.firmware_updata = null;
    }
}
