package com.android.blerc;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;

/* loaded from: classes.dex */
public class PreferenceActivity_ViewBinding implements Unbinder {
    private PreferenceActivity target;

    public PreferenceActivity_ViewBinding(PreferenceActivity preferenceActivity) {
        this(preferenceActivity, preferenceActivity.getWindow().getDecorView());
    }

    public PreferenceActivity_ViewBinding(PreferenceActivity preferenceActivity, View view) {
        this.target = preferenceActivity;
        preferenceActivity.garage_back_img = (ImageView) Utils.findRequiredViewAsType(view, R.id.garage_back_img, "field 'garage_back_img'", ImageView.class);
        preferenceActivity.tx_garage_title = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_garage_title, "field 'tx_garage_title'", TextView.class);
        preferenceActivity.tx_garage_tip = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_garage_tip, "field 'tx_garage_tip'", TextView.class);
        preferenceActivity.hand_bg_img = (ImageView) Utils.findRequiredViewAsType(view, R.id.hand_bg_img, "field 'hand_bg_img'", ImageView.class);
        preferenceActivity.left_hand_btn = (Button) Utils.findRequiredViewAsType(view, R.id.left_hand_btn, "field 'left_hand_btn'", Button.class);
        preferenceActivity.right_hand_btn = (Button) Utils.findRequiredViewAsType(view, R.id.right_hand_btn, "field 'right_hand_btn'", Button.class);
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        PreferenceActivity preferenceActivity = this.target;
        if (preferenceActivity == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        preferenceActivity.garage_back_img = null;
        preferenceActivity.tx_garage_title = null;
        preferenceActivity.tx_garage_tip = null;
        preferenceActivity.hand_bg_img = null;
        preferenceActivity.left_hand_btn = null;
        preferenceActivity.right_hand_btn = null;
    }
}
