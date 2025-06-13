package com.android.blerc.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.android.blerc.R;


/* loaded from: classes.dex */
public class CommonDialog extends BaseDialog implements View.OnClickListener {
    private View btn_divider;
    private TextView cancelTxt;
    private String content;
    private TextView contentTxt;
    private OnCloseListener listener;
    private Context mContext;
    private String negativeName;
    private boolean negativeShow;
    private String positiveName;
    private TextView submitTxt;
    private String title;
    private TextView titleTxt;

    public interface OnCloseListener {
        void onClick(Dialog dialog, boolean z);
    }

    public CommonDialog setNegativeShow(boolean z) {
        this.negativeShow = z;
        return this;
    }

    public CommonDialog(Context context) {
        super(context);
        this.negativeShow = true;
        this.mContext = context;
    }

    public CommonDialog(Context context, int i, String str) {
        super(context, i);
        this.negativeShow = true;
        this.mContext = context;
        this.content = str;
    }

    public CommonDialog(Context context, int i, String str, OnCloseListener onCloseListener) {
        super(context, i);
        this.negativeShow = true;
        this.mContext = context;
        this.content = str;
        this.listener = onCloseListener;
    }

    protected CommonDialog(Context context, boolean z, DialogInterface.OnCancelListener onCancelListener) {
        super(context, z, onCancelListener);
        this.negativeShow = true;
        this.mContext = context;
    }

    public void setOnCloseListener(OnCloseListener onCloseListener) {
        this.listener = onCloseListener;
    }

    public CommonDialog setTitle(String str) {
        this.title = str;
        return this;
    }

    public CommonDialog setPositiveButton(String str) {
        this.positiveName = str;
        return this;
    }

    public CommonDialog setNegativeButton(String str) {
        this.negativeName = str;
        return this;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_common);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        this.contentTxt = (TextView) findViewById(R.id.content);
        this.titleTxt = (TextView) findViewById(R.id.title);
        this.submitTxt = (TextView) findViewById(R.id.submit);
        this.btn_divider = findViewById(R.id.btn_divider);
        this.submitTxt.setOnClickListener(this);
        TextView textView = (TextView) findViewById(R.id.cancel);
        this.cancelTxt = textView;
        textView.setOnClickListener(this);
        this.contentTxt.setText(this.content);
        if (!this.negativeShow) {
            this.cancelTxt.setVisibility(8);
            this.btn_divider.setVisibility(8);
        }
        if (!TextUtils.isEmpty(this.positiveName)) {
            this.submitTxt.setText(this.positiveName);
        }
        if (!TextUtils.isEmpty(this.negativeName)) {
            this.cancelTxt.setText(this.negativeName);
        }
        if (TextUtils.isEmpty(this.title)) {
            return;
        }
        this.titleTxt.setText(this.title);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        OnCloseListener onCloseListener;
        int id = view.getId();
        if (id != R.id.cancel) {
            if (id == R.id.submit && (onCloseListener = this.listener) != null) {
                onCloseListener.onClick(this, true);
                return;
            }
            return;
        }
        OnCloseListener onCloseListener2 = this.listener;
        if (onCloseListener2 != null) {
            onCloseListener2.onClick(this, false);
        }
    }
}
