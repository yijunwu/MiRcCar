package com.android.blerc.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.blerc.R;
import com.android.blerc.common.RxEvent;
import com.android.blerc.db.DBConstant;

import java.util.Locale;
import org.greenrobot.eventbus.EventBus;

/* loaded from: classes.dex */
public class AgreementDialog extends BaseDialog implements View.OnClickListener {
    private ImageView agreeButton;
    private FrameLayout agreeLayout;
    private View btn_divider;
    private TextView cancelTxt;
    private String content;
    private LinearLayout contentLayout;
    private TextView contentText;
    private Boolean isChecked;
    private OnCloseListener listener;
    private Context mContext;
    private String negativeName;
    private boolean negativeShow;
    private String positiveName;
    private TextView submitTxt;
    private String title;

    public interface OnCloseListener {
        void onClick(Dialog dialog, boolean z);
    }

    public AgreementDialog setNegativeShow(boolean z) {
        this.negativeShow = z;
        return this;
    }

    public AgreementDialog(Context context) {
        super(context);
        this.isChecked = false;
        this.negativeShow = true;
        this.mContext = context;
    }

    public AgreementDialog(Context context, int i) {
        super(context, i);
        this.isChecked = false;
        this.negativeShow = true;
        this.mContext = context;
    }

    public AgreementDialog(Context context, int i, OnCloseListener onCloseListener) {
        super(context, i);
        this.isChecked = false;
        this.negativeShow = true;
        this.mContext = context;
        this.listener = onCloseListener;
    }

    public void setOnCloseListener(OnCloseListener onCloseListener) {
        this.listener = onCloseListener;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_agreement);
        setCanceledOnTouchOutside(false);
        initView();
    }

    @Override // com.android.blerc.dialog.BaseDialog, android.app.Dialog
    protected void onStart() {
        super.onStart();
        this.isChecked = false;
        this.agreeButton.setImageResource(R.mipmap.ic_agree_uncheck);
    }

    private void initView() {
        this.contentLayout = (LinearLayout) findViewById(R.id.content);
        this.contentText = (TextView) findViewById(R.id.tv_content);
        this.agreeButton = (ImageView) findViewById(R.id.iv_agree_button);
        this.submitTxt = (TextView) findViewById(R.id.submit);
        this.btn_divider = findViewById(R.id.btn_divider);
        this.submitTxt.setOnClickListener(this);
        this.cancelTxt = (TextView) findViewById(R.id.cancel);
        this.agreeLayout = (FrameLayout) findViewById(R.id.fly_agreement);
        this.cancelTxt.setOnClickListener(this);
        this.agreeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.android.blerc.dialog.AgreementDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (AgreementDialog.this.isChecked.booleanValue()) {
                    AgreementDialog.this.agreeButton.setImageResource(R.mipmap.ic_agree_uncheck);
                    AgreementDialog.this.submitTxt.setTextColor(AgreementDialog.this.getContext().getResources().getColor(R.color.disable_black));
                } else {
                    AgreementDialog.this.agreeButton.setImageResource(R.mipmap.ic_agree_check);
                    AgreementDialog.this.submitTxt.setTextColor(AgreementDialog.this.getContext().getResources().getColor(R.color.black));
                }
                AgreementDialog.this.isChecked = !AgreementDialog.this.isChecked; // TODO, original: Boolean.valueOf(!r3.isChecked.booleanValue());
            }
        });
        initTextSpan();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.cancel) {
            OnCloseListener onCloseListener = this.listener;
            if (onCloseListener != null) {
                onCloseListener.onClick(this, false);
                return;
            }
            return;
        }
        if (id == R.id.submit && this.listener != null && this.isChecked.booleanValue()) {
            this.listener.onClick(this, true);
        }
    }

    private void initTextSpan() {
        Locale.getDefault().getLanguage();
        if (DBConstant.getInstance(getContext()).getLanguage().equals("zh")) {
            initChineseTextSpan();
        } else {
            initEnglishTextSpan();
        }
    }

    private void initChineseTextSpan() {
        SpannableString spannableString = new SpannableString(getContext().getResources().getText(R.string.agreement_dialog_content));
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getContext().getResources().getColor(R.color.agreement_text));
        ForegroundColorSpan foregroundColorSpan2 = new ForegroundColorSpan(getContext().getResources().getColor(R.color.agreement_text));
        UnderlineSpan underlineSpan = new UnderlineSpan();
        UnderlineSpan underlineSpan2 = new UnderlineSpan();
        ClickableSpan clickableSpan = new ClickableSpan() { // from class: com.android.blerc.dialog.AgreementDialog.2
            @Override // android.text.style.ClickableSpan
            public void onClick(View view) {
                EventBus.getDefault().post(new RxEvent("jumpToAgreement"));
            }

            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setUnderlineText(false);
            }
        };
        ClickableSpan clickableSpan2 = new ClickableSpan() { // from class: com.android.blerc.dialog.AgreementDialog.3
            @Override // android.text.style.ClickableSpan
            public void onClick(View view) {
                EventBus.getDefault().post(new RxEvent("jumpToPrivacy"));
            }

            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setUnderlineText(false);
            }
        };
        spannableString.setSpan(foregroundColorSpan, 9, 18, 18);
        spannableString.setSpan(underlineSpan, 9, 18, 18);
        spannableString.setSpan(clickableSpan, 9, 18, 18);
        spannableString.setSpan(foregroundColorSpan2, 21, 25, 18);
        spannableString.setSpan(underlineSpan2, 21, 25, 18);
        spannableString.setSpan(clickableSpan2, 21, 25, 18);
        this.contentText.setText(spannableString);
        this.contentText.setMovementMethod(LinkMovementMethod.getInstance());
        this.contentText.setHighlightColor(getContext().getResources().getColor(android.R.color.transparent));
    }

    private void initEnglishTextSpan() {
        SpannableString spannableString = new SpannableString(getContext().getResources().getText(R.string.agreement_dialog_content));
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getContext().getResources().getColor(R.color.agreement_text));
        ForegroundColorSpan foregroundColorSpan2 = new ForegroundColorSpan(getContext().getResources().getColor(R.color.agreement_text));
        UnderlineSpan underlineSpan = new UnderlineSpan();
        UnderlineSpan underlineSpan2 = new UnderlineSpan();
        ClickableSpan clickableSpan = new ClickableSpan() { // from class: com.android.blerc.dialog.AgreementDialog.4
            @Override // android.text.style.ClickableSpan
            public void onClick(View view) {
                EventBus.getDefault().post(new RxEvent("jumpToAgreement"));
            }

            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setUnderlineText(false);
            }
        };
        ClickableSpan clickableSpan2 = new ClickableSpan() { // from class: com.android.blerc.dialog.AgreementDialog.5
            @Override // android.text.style.ClickableSpan
            public void onClick(View view) {
                EventBus.getDefault().post(new RxEvent("jumpToPrivacy"));
            }

            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setUnderlineText(false);
            }
        };
        spannableString.setSpan(foregroundColorSpan, 8, 23, 18);
        spannableString.setSpan(underlineSpan, 8, 23, 18);
        spannableString.setSpan(clickableSpan, 8, 23, 18);
        spannableString.setSpan(foregroundColorSpan2, 27, spannableString.length(), 18);
        spannableString.setSpan(underlineSpan2, 27, spannableString.length(), 18);
        spannableString.setSpan(clickableSpan2, 27, spannableString.length(), 18);
        this.contentText.setText(spannableString);
        this.contentText.setMovementMethod(LinkMovementMethod.getInstance());
        this.contentText.setHighlightColor(getContext().getResources().getColor(android.R.color.transparent));
    }
}
