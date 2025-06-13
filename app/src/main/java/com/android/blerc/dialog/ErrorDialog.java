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
public class ErrorDialog extends BaseDialog implements View.OnClickListener {
    private String content;
    private TextView contentTxt;
    private TextView exitTxt;
    private OnCloseListener listener;
    private Context mContext;
    private String negativeName;
    private String positiveName;
    private String title;
    private TextView titleTxt;

    public interface OnCloseListener {
        void onClick(Dialog dialog);
    }

    public ErrorDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public ErrorDialog(Context context, int i, String str) {
        super(context, i);
        this.mContext = context;
        this.content = str;
    }

    public ErrorDialog(Context context, int i, String str, OnCloseListener onCloseListener) {
        super(context, i);
        this.mContext = context;
        this.content = str;
        this.listener = onCloseListener;
    }

    protected ErrorDialog(Context context, boolean z, DialogInterface.OnCancelListener onCancelListener) {
        super(context, z, onCancelListener);
        this.mContext = context;
    }

    public ErrorDialog setTitle(String str) {
        this.title = str;
        return this;
    }

    public ErrorDialog setPositiveButton(String str) {
        this.positiveName = str;
        return this;
    }

    public ErrorDialog setNegativeButton(String str) {
        this.negativeName = str;
        return this;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_error);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        this.contentTxt = (TextView) findViewById(R.id.content);
        this.titleTxt = (TextView) findViewById(R.id.title);
        TextView textView = (TextView) findViewById(R.id.exit);
        this.exitTxt = textView;
        textView.setOnClickListener(this);
        this.contentTxt.setText(this.content);
        if (!TextUtils.isEmpty(this.negativeName)) {
            this.exitTxt.setText(this.negativeName);
        }
        if (TextUtils.isEmpty(this.title)) {
            return;
        }
        this.titleTxt.setText(this.title);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() != R.id.exit) {
            return;
        }
        OnCloseListener onCloseListener = this.listener;
        if (onCloseListener != null) {
            onCloseListener.onClick(this);
        }
        dismiss();
    }
}
