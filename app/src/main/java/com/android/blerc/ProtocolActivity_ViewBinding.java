package com.android.blerc;

import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;

/* loaded from: classes.dex */
public class ProtocolActivity_ViewBinding implements Unbinder {
    private ProtocolActivity target;

    public ProtocolActivity_ViewBinding(ProtocolActivity protocolActivity) {
        this(protocolActivity, protocolActivity.getWindow().getDecorView());
    }

    public ProtocolActivity_ViewBinding(ProtocolActivity protocolActivity, View view) {
        this.target = protocolActivity;
        protocolActivity.garage_back_img = (ImageView) Utils.findRequiredViewAsType(view, R.id.garage_back_img, "field 'garage_back_img'", ImageView.class);
        protocolActivity.tx_garage_title = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_garage_title, "field 'tx_garage_title'", TextView.class);
        protocolActivity.tx_garage_tip = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_garage_tip, "field 'tx_garage_tip'", TextView.class);
        protocolActivity.wv_webview = (WebView) Utils.findRequiredViewAsType(view, R.id.wv_webview, "field 'wv_webview'", WebView.class);
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        ProtocolActivity protocolActivity = this.target;
        if (protocolActivity == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        protocolActivity.garage_back_img = null;
        protocolActivity.tx_garage_title = null;
        protocolActivity.tx_garage_tip = null;
        protocolActivity.wv_webview = null;
    }
}
