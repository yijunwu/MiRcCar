package com.android.blerc.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.blerc.R;


/* loaded from: classes.dex */
public class NoDevicesTipDialog extends BaseDialog implements View.OnClickListener {
    private Boolean isChecked;
    private OnCloseListener listener;
    private Context mContext;
    private boolean negativeShow;
    private TextView submitTxt;

    public interface OnCloseListener {
        void onClick(Dialog dialog);
    }

    public NoDevicesTipDialog setNegativeShow(boolean z) {
        this.negativeShow = z;
        return this;
    }

    public NoDevicesTipDialog(Context context) {
        super(context);
        this.isChecked = false;
        this.negativeShow = true;
        this.mContext = context;
    }

    public NoDevicesTipDialog(Context context, int i, OnCloseListener onCloseListener) {
        super(context, i);
        this.isChecked = false;
        this.negativeShow = true;
        this.mContext = context;
        this.listener = onCloseListener;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_no_devices_tip);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        TextView textView = (TextView) findViewById(R.id.submit);
        this.submitTxt = textView;
        textView.setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        OnCloseListener onCloseListener;
        if (view.getId() == R.id.submit && (onCloseListener = this.listener) != null) {
            onCloseListener.onClick(this);
        }
    }
}
