package com.android.blerc;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import butterknife.ButterKnife;
import com.android.blerc.db.DBConstant;
import com.jakewharton.rxbinding2.view.RxView;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;
import me.jessyan.autosize.utils.AutoSizeUtils;

/* loaded from: classes.dex */
public class GarageActivity extends BaseActivity {
    LottieAnimationView animationView;
    public RelativeLayout arrow_down_layout;
    public LinearLayout arrow_up_layout;
    public ImageView car_arrow_down;
    public ImageView car_arrow_up;
    public ImageView car_parameter_img;
    ImageView garage_back_img;
    ImageView garagestate1;
    ImageView garagestate2;
    ImageView garagestate3;
    ImageView garagestate4;
    ImageView garagestate5;
    private final String TAG = "Garage";
    int current = 1;
    float middle_float = 0.7456f;
    public long animationViewDuration = 3832;
    long frontDuration = (long) (3832 * 0.7456f);
    long backDuration = 3832 - ((long) (3832 * 0.7456f));
    public ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(3800L);

    @Override // com.android.blerc.BaseActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_garage);
        ButterKnife.bind(this);
        initView();
        this.animationView.useHardwareAcceleration(true);
        this.animationView.enableMergePathsForKitKatAndAbove(true);
    }

    @Override // com.android.blerc.BaseActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        if (DBConstant.getInstance(this).getLanguage().equals("zh")) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.car_parameter_img.getLayoutParams();
            layoutParams.width = AutoSizeUtils.dp2px(this, 102.0f);
            this.car_parameter_img.setLayoutParams(layoutParams);
            this.car_parameter_img.setImageResource(R.mipmap.car_parameter);
        } else {
            RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.car_parameter_img.getLayoutParams();
            layoutParams2.width = AutoSizeUtils.dp2px(this, 120.0f);
            this.car_parameter_img.setLayoutParams(layoutParams2);
            this.car_parameter_img.setImageResource(R.mipmap.car_parameter_eng);
        }
        RxView.clicks(this.garage_back_img).throttleFirst(1L, TimeUnit.SECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.GarageActivity.1
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                GarageActivity.this.finish();
            }
        });
    }

    public void doAnimation() {
        this.animator.removeAllUpdateListeners();
        this.animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.blerc.GarageActivity.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                GarageActivity.this.animationView.setProgress(((Float) valueAnimator.getAnimatedValue()).floatValue());
                double dMaxMemory = (Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory()) - Runtime.getRuntime().freeMemory();
                Double.isNaN(dMaxMemory);
                if (((float) ((dMaxMemory * 1.0d) / 1048576.0d)) < 100.0f) {
                    Log.d("Garage", "recycleBitmaps");
                    GarageActivity.this.animationView.recycleBitmaps();
                }
            }
        });
        this.animator.start();
    }
}
