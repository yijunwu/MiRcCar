package com.android.blerc.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import com.android.blerc.db.DBConstant;
import java.util.Locale;

/* loaded from: classes.dex */
public class BaseDialog extends Dialog {
    private final Context mContext;

    public BaseDialog(Context context, int i) {
        super(context, i);
        this.mContext = context;
    }

    public BaseDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public BaseDialog(Context context, boolean z, DialogInterface.OnCancelListener onCancelListener) {
        super(context, z, onCancelListener);
        this.mContext = context;
    }

    private void fullScreenImmersive(View view) {
        if (Build.VERSION.SDK_INT >= 19) {
            view.setSystemUiVisibility(5894);
        }
    }

    @Override // android.app.Dialog
    protected void onStart() {
        super.onStart();
        Locale locale = new Locale(DBConstant.getInstance(this.mContext).getLanguage());
        Resources resources = this.mContext.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, displayMetrics);
    }

    @Override // android.app.Dialog
    public void show() {
        getWindow().setFlags(8, 8);
        super.show();
        fullScreenImmersive(getWindow().getDecorView());
        getWindow().clearFlags(8);
    }
}
