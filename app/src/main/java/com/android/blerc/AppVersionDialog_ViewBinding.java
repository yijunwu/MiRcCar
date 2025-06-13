package com.android.blerc;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.android.blerc.CircleProgressBar;


/* loaded from: classes.dex */
public class AppVersionDialog_ViewBinding implements Unbinder {
    private AppVersionDialog target;

    public AppVersionDialog_ViewBinding(AppVersionDialog appVersionDialog) {
        this(appVersionDialog, appVersionDialog.getWindow().getDecorView());
    }

    public AppVersionDialog_ViewBinding(AppVersionDialog appVersionDialog, View view) {
        this.target = appVersionDialog;
        appVersionDialog.version_title = (TextView) Utils.findRequiredViewAsType(view, R.id.version_title, "field 'version_title'", TextView.class);
        appVersionDialog.version_tip = (TextView) Utils.findRequiredViewAsType(view, R.id.version_tip, "field 'version_tip'", TextView.class);
        appVersionDialog.found_layout = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.found_layout, "field 'found_layout'", RelativeLayout.class);
        appVersionDialog.tx_found = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_found, "field 'tx_found'", TextView.class);
        appVersionDialog.tx_found_version = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_found_version, "field 'tx_found_version'", TextView.class);
        appVersionDialog.check_layout = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.check_layout, "field 'check_layout'", RelativeLayout.class);
        appVersionDialog.download_layout = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.download_layout, "field 'download_layout'", RelativeLayout.class);
        appVersionDialog.tx_check = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_check, "field 'tx_check'", TextView.class);
        appVersionDialog.tx_check_version = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_check_version, "field 'tx_check_version'", TextView.class);
        appVersionDialog.fail_layout = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.fail_layout, "field 'fail_layout'", LinearLayout.class);
        appVersionDialog.layout_divider = Utils.findRequiredView(view, R.id.layout_divider, "field 'layout_divider'");
        appVersionDialog.btn_layout = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.btn_layout, "field 'btn_layout'", LinearLayout.class);
        appVersionDialog.tx_cancel = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_cancel, "field 'tx_cancel'", TextView.class);
        appVersionDialog.btn_divider = Utils.findRequiredView(view, R.id.btn_divider, "field 'btn_divider'");
        appVersionDialog.submit = (TextView) Utils.findRequiredViewAsType(view, R.id.submit, "field 'submit'", TextView.class);
        appVersionDialog.download_progressbar = (CircleProgressBar) Utils.findRequiredViewAsType(view, R.id.download_progressbar, "field 'download_progressbar'", CircleProgressBar.class);
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        AppVersionDialog appVersionDialog = this.target;
        if (appVersionDialog == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        appVersionDialog.version_title = null;
        appVersionDialog.version_tip = null;
        appVersionDialog.found_layout = null;
        appVersionDialog.tx_found = null;
        appVersionDialog.tx_found_version = null;
        appVersionDialog.check_layout = null;
        appVersionDialog.download_layout = null;
        appVersionDialog.tx_check = null;
        appVersionDialog.tx_check_version = null;
        appVersionDialog.fail_layout = null;
        appVersionDialog.layout_divider = null;
        appVersionDialog.btn_layout = null;
        appVersionDialog.tx_cancel = null;
        appVersionDialog.btn_divider = null;
        appVersionDialog.submit = null;
        appVersionDialog.download_progressbar = null;
    }
}
