package com.android.blerc;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;

/* loaded from: classes.dex */
public class DfuUpdateActivity_ViewBinding implements Unbinder {
    private DfuUpdateActivity target;

    public DfuUpdateActivity_ViewBinding(DfuUpdateActivity dfuUpdateActivity) {
        this(dfuUpdateActivity, dfuUpdateActivity.getWindow().getDecorView());
    }

    public DfuUpdateActivity_ViewBinding(DfuUpdateActivity dfuUpdateActivity, View view) {
        this.target = dfuUpdateActivity;
        dfuUpdateActivity.tv_show = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_result, "field 'tv_show'", TextView.class);
        dfuUpdateActivity.progressBar = (ProgressBar) Utils.findRequiredViewAsType(view, R.id.dfu_progress, "field 'progressBar'", ProgressBar.class);
        dfuUpdateActivity.startBtn = (Button) Utils.findRequiredViewAsType(view, R.id.startDfu, "field 'startBtn'", Button.class);
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        DfuUpdateActivity dfuUpdateActivity = this.target;
        if (dfuUpdateActivity == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        dfuUpdateActivity.tv_show = null;
        dfuUpdateActivity.progressBar = null;
        dfuUpdateActivity.startBtn = null;
    }
}
