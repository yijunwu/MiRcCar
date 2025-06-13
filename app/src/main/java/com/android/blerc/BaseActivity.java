package com.android.blerc;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import com.android.blerc.db.DBConstant;
import com.android.blerc.dialog.CommonDialog;
import com.android.blerc.dialog.ErrorDialog;
import java.util.Locale;

/* loaded from: classes.dex */
public class BaseActivity extends Activity {
    private ProgressDialog mProgressDialog;

    public void clickCommonDialog(Dialog dialog, boolean z, int i, String str) {
    }

    public void clickErrorDialog(Dialog dialog, int i) {
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Locale locale = new Locale(DBConstant.getInstance(this).getLanguage());
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, displayMetrics);
    }

    public void setFullscreen(boolean z, boolean z2) {
        int i = !z ? 5892 : 5888;
        if (!z2) {
            i |= 2;
        }
        getWindow().getDecorView().setSystemUiVisibility(i);
        setNavigationStatusColor(0);
    }

    public void setNavigationStatusColor(int i) {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().addFlags(Integer.MIN_VALUE);
            getWindow().setNavigationBarColor(i);
            getWindow().setStatusBarColor(i);
        }
    }

    public void setProgressMessage(String str) {
        ProgressDialog progressDialog = this.mProgressDialog;
        if (progressDialog == null || !progressDialog.isShowing()) {
            return;
        }
        this.mProgressDialog.setMessage(str);
    }

    public void setProgressMessage(int i) {
        ProgressDialog progressDialog = this.mProgressDialog;
        if (progressDialog == null || !progressDialog.isShowing()) {
            return;
        }
        this.mProgressDialog.setMessage(getString(i));
    }

    public void setProgressValue(int i) {
        ProgressDialog progressDialog = this.mProgressDialog;
        if (progressDialog == null || !progressDialog.isShowing()) {
            return;
        }
        this.mProgressDialog.setProgress(i);
    }

    public void showProgressDialog(String str, String str2, String str3, int i) {
        dimissProgressDialog();
        ProgressDialog progressDialog = new ProgressDialog(this);
        this.mProgressDialog = progressDialog;
        progressDialog.setProgressStyle(i);
        this.mProgressDialog.setMessage(str3);
        this.mProgressDialog.setTitle(str);
        this.mProgressDialog.setCancelable(false);
        this.mProgressDialog.setButton(-1, str2, new DialogInterface.OnClickListener() { // from class: com.android.blerc.BaseActivity.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i2) {
                dialogInterface.dismiss();
            }
        });
        this.mProgressDialog.show();
    }

    public void dimissProgressDialog() {
        ProgressDialog progressDialog = this.mProgressDialog;
        if (progressDialog == null || !progressDialog.isShowing()) {
            return;
        }
        this.mProgressDialog.dismiss();
    }

    public boolean isShowingProgressDialog() {
        ProgressDialog progressDialog = this.mProgressDialog;
        return progressDialog != null && progressDialog.isShowing();
    }

    protected void showConfirmationDialog(String str, String str2, final int i, final String str3) {
        new CommonDialog(this, R.style.dialog, str2, new CommonDialog.OnCloseListener() { // from class: com.android.blerc.BaseActivity.2
            @Override // com.android.blerc.dialog.CommonDialog.OnCloseListener
            public void onClick(Dialog dialog, boolean z) {
                BaseActivity.this.clickCommonDialog(dialog, z, i, str3);
            }
        }).setTitle(str).show();
    }

    protected void showErrorDialog(String str, final int i) {
        new ErrorDialog(this, R.style.dialog, str, new ErrorDialog.OnCloseListener() { // from class: com.android.blerc.BaseActivity.3
            @Override // com.android.blerc.dialog.ErrorDialog.OnCloseListener
            public void onClick(Dialog dialog) {
                BaseActivity.this.clickErrorDialog(dialog, i);
            }
        }).setTitle(getString(R.string.warmhints)).show();
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        setFullscreen(false, false);
    }
}
