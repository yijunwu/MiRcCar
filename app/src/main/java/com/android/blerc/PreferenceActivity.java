package com.android.blerc;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import com.android.blerc.db.DBConstant;
import com.jakewharton.rxbinding2.view.RxView;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class PreferenceActivity extends BaseActivity {
    private final String TAG = "Setting";
    ImageView garage_back_img;
    ImageView hand_bg_img;
    Button left_hand_btn;
    Button right_hand_btn;
    TextView tx_garage_tip;
    TextView tx_garage_title;

    @Override // com.android.blerc.BaseActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_preference);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        this.tx_garage_title.setText(R.string.setting_preference_title);
        this.tx_garage_tip.setText(R.string.setting_operator);
        RxView.clicks(this.garage_back_img).throttleFirst(1L, TimeUnit.SECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.PreferenceActivity.1
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                PreferenceActivity.this.finish();
            }
        });
        if (DBConstant.getInstance(this).isRightHand()) {
            this.hand_bg_img.setImageResource(R.mipmap.right_hand_bg);
            this.right_hand_btn.setBackgroundResource(R.mipmap.right_hand_blue);
            this.left_hand_btn.setBackgroundResource(R.mipmap.left_hand_gray);
        } else {
            this.hand_bg_img.setImageResource(R.mipmap.left_hand_bg);
            this.right_hand_btn.setBackgroundResource(R.mipmap.right_hand_gray);
            this.left_hand_btn.setBackgroundResource(R.mipmap.left_hand_blue);
        }
        RxView.clicks(this.right_hand_btn).throttleFirst(1L, TimeUnit.SECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.PreferenceActivity.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                if (DBConstant.getInstance(PreferenceActivity.this).isRightHand()) {
                    return;
                }
                DBConstant.getInstance(PreferenceActivity.this).setRightHand(true);
                PreferenceActivity.this.hand_bg_img.setImageResource(R.mipmap.right_hand_bg);
                PreferenceActivity.this.right_hand_btn.setBackgroundResource(R.mipmap.right_hand_blue);
                PreferenceActivity.this.left_hand_btn.setBackgroundResource(R.mipmap.left_hand_gray);
            }
        });
        RxView.clicks(this.left_hand_btn).throttleFirst(1L, TimeUnit.SECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.PreferenceActivity.3
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                if (DBConstant.getInstance(PreferenceActivity.this).isRightHand()) {
                    DBConstant.getInstance(PreferenceActivity.this).setRightHand(false);
                    PreferenceActivity.this.hand_bg_img.setImageResource(R.mipmap.left_hand_bg);
                    PreferenceActivity.this.right_hand_btn.setBackgroundResource(R.mipmap.right_hand_gray);
                    PreferenceActivity.this.left_hand_btn.setBackgroundResource(R.mipmap.left_hand_blue);
                }
            }
        });
    }
}
