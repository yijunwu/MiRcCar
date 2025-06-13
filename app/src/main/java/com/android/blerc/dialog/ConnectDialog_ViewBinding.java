package com.android.blerc.dialog;

import android.view.View;
import android.widget.Button;

import com.android.blerc.R;

import butterknife.Unbinder;
import butterknife.internal.Utils;


/* loaded from: classes.dex */
public class ConnectDialog_ViewBinding implements Unbinder {
    private ConnectDialog target;

    public ConnectDialog_ViewBinding(ConnectDialog connectDialog) {
        this(connectDialog, connectDialog.getWindow().getDecorView());
    }

    public ConnectDialog_ViewBinding(ConnectDialog connectDialog, View view) {
        this.target = connectDialog;
        connectDialog.centerbtn = (Button) Utils.findRequiredViewAsType(view, R.id.centerbtn, "field 'centerbtn'", Button.class);
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        ConnectDialog connectDialog = this.target;
        if (connectDialog == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        connectDialog.centerbtn = null;
    }
}
