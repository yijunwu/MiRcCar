package com.android.blerc.dialog;

import android.view.View;
import android.widget.ImageView;

import com.android.blerc.R;

import butterknife.Unbinder;
import butterknife.internal.Utils;


/* loaded from: classes.dex */
public class GarageDialog_ViewBinding implements Unbinder {
    private GarageDialog target;

    public GarageDialog_ViewBinding(GarageDialog garageDialog) {
        this(garageDialog, garageDialog.getWindow().getDecorView());
    }

    public GarageDialog_ViewBinding(GarageDialog garageDialog, View view) {
        this.target = garageDialog;
        garageDialog.back_img = (ImageView) Utils.findRequiredViewAsType(view, R.id.garage_back_img, "field 'back_img'", ImageView.class);
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        GarageDialog garageDialog = this.target;
        if (garageDialog == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        garageDialog.back_img = null;
    }
}
