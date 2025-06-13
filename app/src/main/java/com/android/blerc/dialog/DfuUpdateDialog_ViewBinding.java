package com.android.blerc.dialog;

import android.view.View;
import android.widget.ProgressBar;

import com.android.blerc.R;

import butterknife.Unbinder;
import butterknife.internal.Utils;


/* loaded from: classes.dex */
public class DfuUpdateDialog_ViewBinding implements Unbinder {
    private DfuUpdateDialog target;

    public DfuUpdateDialog_ViewBinding(DfuUpdateDialog dfuUpdateDialog) {
        this(dfuUpdateDialog, dfuUpdateDialog.getWindow().getDecorView());
    }

    public DfuUpdateDialog_ViewBinding(DfuUpdateDialog dfuUpdateDialog, View view) {
        this.target = dfuUpdateDialog;
        dfuUpdateDialog.dfu_progress = (ProgressBar) Utils.findRequiredViewAsType(view, R.id.dfu_progress, "field 'dfu_progress'", ProgressBar.class);
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        DfuUpdateDialog dfuUpdateDialog = this.target;
        if (dfuUpdateDialog == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        dfuUpdateDialog.dfu_progress = null;
    }
}
