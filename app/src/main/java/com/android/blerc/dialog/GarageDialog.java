package com.android.blerc.dialog;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import butterknife.ButterKnife;

import com.android.blerc.R;
import com.jakewharton.rxbinding2.view.RxView;

import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class GarageDialog extends BaseDialog {
    private final String TAG;
    ImageView back_img;
    private final Context mContext;

    public GarageDialog(Context context) {
        super(context, R.style.dialog);
        this.TAG = "GarageDialog";
        this.mContext = context;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_garage);
        ButterKnife.bind(this);
        initListView();
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
        WindowManager.LayoutParams attributes2 = window.getAttributes();
        double d = displayMetrics.heightPixels;
        Double.isNaN(d);
        attributes2.height = (int) (d * 0.8d);
        double d2 = displayMetrics.widthPixels;
        Double.isNaN(d2);
        attributes2.width = (int) (d2 * 0.8d);
        window.setAttributes(attributes);
    }

    @Override // android.app.Dialog
    protected void onStop() {
        super.onStop();
    }

    @Override // com.android.blerc.dialog.BaseDialog, android.app.Dialog
    protected void onStart() {
        super.onStart();
    }

    private void initListView() {
        RxView.clicks(this.back_img).throttleFirst(1L, TimeUnit.SECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.dialog.GarageDialog.1
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                GarageDialog.this.dismiss();
            }
        });
    }
}
