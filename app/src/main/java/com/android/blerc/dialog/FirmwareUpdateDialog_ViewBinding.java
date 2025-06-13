package com.android.blerc.dialog;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.android.blerc.CircleProgressBar;
import com.android.blerc.R;


/* loaded from: classes.dex */
public class FirmwareUpdateDialog_ViewBinding implements Unbinder {
    private FirmwareUpdateDialog target;

    public FirmwareUpdateDialog_ViewBinding(FirmwareUpdateDialog firmwareUpdateDialog) {
        this(firmwareUpdateDialog, firmwareUpdateDialog.getWindow().getDecorView());
    }

    public FirmwareUpdateDialog_ViewBinding(FirmwareUpdateDialog firmwareUpdateDialog, View view) {
        this.target = firmwareUpdateDialog;
        firmwareUpdateDialog.version_title = (TextView) Utils.findRequiredViewAsType(view, R.id.version_title, "field 'version_title'", TextView.class);
        firmwareUpdateDialog.version_tip = (TextView) Utils.findRequiredViewAsType(view, R.id.version_tip, "field 'version_tip'", TextView.class);
        firmwareUpdateDialog.found_layout = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.found_layout, "field 'found_layout'", RelativeLayout.class);
        firmwareUpdateDialog.tx_found = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_found, "field 'tx_found'", TextView.class);
        firmwareUpdateDialog.tx_found_version = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_found_version, "field 'tx_found_version'", TextView.class);
        firmwareUpdateDialog.check_layout = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.check_layout, "field 'check_layout'", RelativeLayout.class);
        firmwareUpdateDialog.download_layout = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.download_layout, "field 'download_layout'", RelativeLayout.class);
        firmwareUpdateDialog.tx_check = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_check, "field 'tx_check'", TextView.class);
        firmwareUpdateDialog.tx_check_version = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_check_version, "field 'tx_check_version'", TextView.class);
        firmwareUpdateDialog.fail_layout = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.fail_layout, "field 'fail_layout'", LinearLayout.class);
        firmwareUpdateDialog.layout_divider = Utils.findRequiredView(view, R.id.layout_divider, "field 'layout_divider'");
        firmwareUpdateDialog.btn_layout = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.btn_layout, "field 'btn_layout'", LinearLayout.class);
        firmwareUpdateDialog.tx_cancel = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_cancel, "field 'tx_cancel'", TextView.class);
        firmwareUpdateDialog.btn_divider = Utils.findRequiredView(view, R.id.btn_divider, "field 'btn_divider'");
        firmwareUpdateDialog.submit = (TextView) Utils.findRequiredViewAsType(view, R.id.submit, "field 'submit'", TextView.class);
        firmwareUpdateDialog.download_progressbar = (CircleProgressBar) Utils.findRequiredViewAsType(view, R.id.download_progressbar, "field 'download_progressbar'", CircleProgressBar.class);
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        FirmwareUpdateDialog firmwareUpdateDialog = this.target;
        if (firmwareUpdateDialog == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        firmwareUpdateDialog.version_title = null;
        firmwareUpdateDialog.version_tip = null;
        firmwareUpdateDialog.found_layout = null;
        firmwareUpdateDialog.tx_found = null;
        firmwareUpdateDialog.tx_found_version = null;
        firmwareUpdateDialog.check_layout = null;
        firmwareUpdateDialog.download_layout = null;
        firmwareUpdateDialog.tx_check = null;
        firmwareUpdateDialog.tx_check_version = null;
        firmwareUpdateDialog.fail_layout = null;
        firmwareUpdateDialog.layout_divider = null;
        firmwareUpdateDialog.btn_layout = null;
        firmwareUpdateDialog.tx_cancel = null;
        firmwareUpdateDialog.btn_divider = null;
        firmwareUpdateDialog.submit = null;
        firmwareUpdateDialog.download_progressbar = null;
    }
}
