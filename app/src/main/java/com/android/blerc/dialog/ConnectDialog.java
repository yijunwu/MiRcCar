package com.android.blerc.dialog;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import butterknife.ButterKnife;

import com.android.blerc.R;
import com.android.blerc.common.RxEvent;
import com.jakewharton.rxbinding2.view.RxView;

import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.EventBus;

/* loaded from: classes.dex */
public class ConnectDialog extends BaseDialog {
    private final String TAG;
    Button centerbtn;
    private final Context mContext;

    public ConnectDialog(Context context) {
        super(context, R.style.dialog);
        this.TAG = "ConnectDialog";
        this.mContext = context;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_connect);
        ButterKnife.bind(this);
        initListView();
        getWindow().setLayout(-1, -1);
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
        RxView.clicks(this.centerbtn).throttleFirst(1L, TimeUnit.SECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.dialog.ConnectDialog.1
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                EventBus.getDefault().post(new RxEvent("searchDev"));
                ConnectDialog.this.dismiss();
            }
        });
    }
}
