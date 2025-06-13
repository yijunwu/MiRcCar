package com.android.blerc.dialog;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;

import com.android.blerc.R;
import com.android.blerc.common.BleConstant;
import com.android.blerc.db.DBConstant;
import com.android.blerc.db.SharedPreferencesUtils;
import com.jakewharton.rxbinding2.view.RxView;

import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class GuideDialog extends BaseDialog {
    private final String TAG;
    AlphaAnimation alphaAnimation;
    AlphaAnimation alphaAnimationTip;
    AlphaAnimation alphaAnimationTitle;
    ImageView banner1;
    ImageView banner2;
    ImageView banner3;
    ImageView banner4;
    ImageView banner5;
    ImageView banner6;
    ImageView banner7;
    ImageView banner8;
    private ClipDrawable clipDrawable;
    ImageView guide_icon1;
    ImageView guide_icon2;
    ImageView guide_icon3;
    ImageView guide_icon4;
    ImageView guide_icon5;
    ImageView guide_icon6;
    ImageView guide_icon7;
    ImageView guide_icon8;
    RelativeLayout guide_layout1;
    RelativeLayout guide_layout2;
    RelativeLayout guide_layout3;
    RelativeLayout guide_layout4;
    RelativeLayout guide_layout5;
    RelativeLayout guide_layout6;
    RelativeLayout guide_layout7;
    RelativeLayout guide_layout8;
    ImageView guide_line1;
    ImageView guide_line2;
    ImageView guide_line3;
    ImageView guide_line4;
    ImageView guide_line5;
    ImageView guide_line6;
    ImageView guide_line7;
    ImageView guide_line8;
    RelativeLayout guide_view;
    long iconDuration;
    private int index;
    long lineDuration;
    private final Context mContext;
    ImageView skip_imgbtn;
    TextView tx_tip1;
    TextView tx_tip2;
    TextView tx_tip3;
    TextView tx_tip4;
    TextView tx_tip5;
    TextView tx_tip6;
    TextView tx_tip7;
    TextView tx_tip8;
    TextView tx_title1;
    TextView tx_title2;
    TextView tx_title3;
    TextView tx_title4;
    TextView tx_title5;
    TextView tx_title6;
    TextView tx_title7;
    TextView tx_title8;
    ValueAnimator valueAnimator;

    static /* synthetic */ int access$108(GuideDialog guideDialog) {
        int i = guideDialog.index;
        guideDialog.index = i + 1;
        return i;
    }

    public GuideDialog(Context context) {
        super(context, R.style.guidedialog);
        this.TAG = "GuideDialog";
        this.index = 1;
        this.alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        this.alphaAnimationTitle = new AlphaAnimation(0.0f, 1.0f);
        this.alphaAnimationTip = new AlphaAnimation(0.0f, 1.0f);
        this.iconDuration = 300L;
        this.lineDuration = 400L;
        this.mContext = context;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_guide);
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
        playAnimation(this.index);
    }

    private void initListView() {
        if (DBConstant.getInstance(this.mContext).getLanguage().equals("zh")) {
            this.skip_imgbtn.setImageResource(R.mipmap.skip);
        } else {
            this.skip_imgbtn.setImageResource(R.mipmap.skip_eng);
        }
        RxView.clicks(this.skip_imgbtn).throttleFirst(1L, TimeUnit.SECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.dialog.GuideDialog.1
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                int i = Integer.parseInt(DBConstant.getInstance(GuideDialog.this.mContext).getAppVersion().replaceAll("\\.", ""));
                SharedPreferencesUtils.setParam(GuideDialog.this.mContext, i + "GuideShow", true);
                GuideDialog.this.dismiss();
            }
        });
        RxView.clicks(this.guide_view).throttleFirst(1L, TimeUnit.SECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.dialog.GuideDialog.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                GuideDialog.access$108(GuideDialog.this);
                if (GuideDialog.this.index > 8) {
                    int i = Integer.parseInt(DBConstant.getInstance(GuideDialog.this.mContext).getAppVersion().replaceAll("\\.", ""));
                    SharedPreferencesUtils.setParam(GuideDialog.this.mContext, i + "GuideShow", true);
                    GuideDialog.this.dismiss();
                    return;
                }
                GuideDialog guideDialog = GuideDialog.this;
                guideDialog.playAnimation(guideDialog.index);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playAnimation(int i) {
        switch (i) {
            case 1:
                setBannerImage(this.banner1);
                playLayout(this.guide_layout1, this.guide_icon1, this.guide_line1, this.tx_title1, this.tx_tip1);
                break;
            case 2:
                setBannerImage(this.banner2);
                playLayout(this.guide_layout2, this.guide_icon2, this.guide_line2, this.tx_title2, this.tx_tip2);
                break;
            case 3:
                setBannerImage(this.banner3);
                playLayout(this.guide_layout3, this.guide_icon3, this.guide_line3, this.tx_title3, this.tx_tip3);
                break;
            case 4:
                setBannerImage(this.banner4);
                playLayout(this.guide_layout4, this.guide_icon4, this.guide_line4, this.tx_title4, this.tx_tip4);
                break;
            case 5:
                setBannerImage(this.banner5);
                playLayout(this.guide_layout5, this.guide_icon5, this.guide_line5, this.tx_title5, this.tx_tip5);
                break;
            case 6:
                setBannerImage(this.banner6);
                playLayout(this.guide_layout6, this.guide_icon6, this.guide_line6, this.tx_title6, this.tx_tip6);
                break;
            case 7:
                setBannerImage(this.banner7);
                playLayout(this.guide_layout7, this.guide_icon7, this.guide_line7, this.tx_title7, this.tx_tip7);
                break;
            case 8:
                setBannerImage(this.banner8);
                playLayout(this.guide_layout8, this.guide_icon8, this.guide_line8, this.tx_title8, this.tx_tip8);
                break;
        }
    }

    private void playLayout(RelativeLayout relativeLayout, ImageView imageView, final ImageView imageView2, final TextView textView, final TextView textView2) {
        this.guide_layout1.setVisibility(8);
        this.guide_layout2.setVisibility(8);
        this.guide_layout3.setVisibility(8);
        this.guide_layout4.setVisibility(8);
        this.guide_layout5.setVisibility(8);
        this.guide_layout6.setVisibility(8);
        this.guide_layout7.setVisibility(8);
        this.guide_layout8.setVisibility(8);
        imageView.setVisibility(4);
        imageView2.setVisibility(4);
        textView.setVisibility(4);
        textView2.setVisibility(4);
        relativeLayout.setVisibility(0);
        this.alphaAnimation.setDuration(this.iconDuration);
        this.alphaAnimation.setFillAfter(true);
        imageView.setVisibility(0);
        imageView.startAnimation(this.alphaAnimation);
        ClipDrawable clipDrawable = (ClipDrawable) imageView2.getDrawable();
        this.clipDrawable = clipDrawable;
        clipDrawable.setLevel(0);
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(0, BleConstant.DEFAULT_CONN_TIME);
        this.valueAnimator = valueAnimatorOfInt;
        valueAnimatorOfInt.setDuration(this.lineDuration);
        this.valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.blerc.dialog.GuideDialog.3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                GuideDialog.this.clipDrawable.setLevel(((Integer) valueAnimator.getAnimatedValue()).intValue());
            }
        });
        this.alphaAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.android.blerc.dialog.GuideDialog.4
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                imageView2.setVisibility(0);
                GuideDialog.this.valueAnimator.start();
            }
        });
        this.alphaAnimationTitle.setDuration(this.lineDuration);
        this.alphaAnimationTitle.setFillAfter(true);
        this.valueAnimator.addListener(new Animator.AnimatorListener() { // from class: com.android.blerc.dialog.GuideDialog.5
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                textView.setVisibility(0);
                textView.startAnimation(GuideDialog.this.alphaAnimationTitle);
            }
        });
        this.alphaAnimationTip.setDuration(this.lineDuration);
        this.alphaAnimationTip.setFillAfter(true);
        this.alphaAnimationTitle.setAnimationListener(new Animation.AnimationListener() { // from class: com.android.blerc.dialog.GuideDialog.6
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                textView2.setVisibility(0);
                textView2.startAnimation(GuideDialog.this.alphaAnimationTip);
            }
        });
    }

    private void setBannerImage(ImageView imageView) {
        this.banner1.setImageResource(R.mipmap.guide_normal);
        this.banner2.setImageResource(R.mipmap.guide_normal);
        this.banner3.setImageResource(R.mipmap.guide_normal);
        this.banner4.setImageResource(R.mipmap.guide_normal);
        this.banner5.setImageResource(R.mipmap.guide_normal);
        this.banner6.setImageResource(R.mipmap.guide_normal);
        this.banner7.setImageResource(R.mipmap.guide_normal);
        this.banner8.setImageResource(R.mipmap.guide_normal);
        imageView.setImageResource(R.mipmap.guide_select);
    }
}
