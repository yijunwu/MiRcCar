package com.android.blerc;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import com.android.blerc.db.DBConstant;

/* loaded from: classes.dex */
public class DragView extends ScrollView {
    private final String TAG;
    SeekBar horiSeekBar;
    LinearLayout hori_linearLayout;
    float moveX;
    float moveY;
    VerticalSeekBar verticalSeekBar;
    ScrollView view;

    @Override // android.widget.ScrollView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return true;
    }

    public void setView(ScrollView scrollView, SeekBar seekBar, VerticalSeekBar verticalSeekBar, LinearLayout linearLayout) {
        this.view = scrollView;
        this.horiSeekBar = seekBar;
        this.verticalSeekBar = verticalSeekBar;
        this.hori_linearLayout = linearLayout;
    }

    public DragView(Context context) {
        super(context);
        this.TAG = "DragView";
    }

    public DragView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.TAG = "DragView";
    }

    public int dp2px(float f) {
        return (int) ((f * getResources().getDisplayMetrics().density) + 0.5f);
    }

    public int px2dp(float f) {
        return (int) ((f / getResources().getDisplayMetrics().density) + 0.5f);
    }

    @Override // android.widget.ScrollView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.moveX = motionEvent.getX();
            this.moveY = motionEvent.getY();
            if ((DBConstant.getInstance(getContext()).isSensor() && this.view.getTag().toString().equals("direction")) ? false : true) {
                float x = motionEvent.getX() - (this.view.getLeft() + (this.view.getWidth() / 2));
                float y = motionEvent.getY() - (this.view.getTop() + (this.view.getHeight() / 2));
                if (px2dp(Math.abs(x)) < 60 && px2dp(Math.abs(y)) < 60 && px2dp(y) > (-(this.view.getTop() - 15))) {
                    this.view.setTranslationX(x);
                    this.view.setTranslationY(y);
                }
            }
        } else if (action != 1) {
            if (action == 2 && (this.view.getTranslationX() != 0.0f || this.view.getTranslationY() != 0.0f)) {
                if (this.hori_linearLayout.getVisibility() == 0) {
                    double max = this.horiSeekBar.getMax() / this.horiSeekBar.getWidth();
                    Double.isNaN(max);
                    SeekBar seekBar = this.horiSeekBar;
                    double x2 = motionEvent.getX() - this.moveX;
                    Double.isNaN(x2);
                    seekBar.setProgress(((int) (x2 * max * 1.5d)) + 1000);
                } else {
                    double max2 = this.verticalSeekBar.getMax() / this.verticalSeekBar.getHeight();
                    Double.isNaN(max2);
                    VerticalSeekBar verticalSeekBar = this.verticalSeekBar;
                    double y2 = motionEvent.getY() - this.moveY;
                    Double.isNaN(y2);
                    verticalSeekBar.setProgress(1000 - ((int) (y2 * (max2 * 1.5d))));
                }
            }
        } else if (this.view.getTranslationX() != 0.0f || this.view.getTranslationY() != 0.0f) {
            this.view.setTranslationX(0.0f);
            this.view.setTranslationY(0.0f);
            if (this.hori_linearLayout.getVisibility() == 0) {
                this.horiSeekBar.onTouchEvent(motionEvent);
            } else {
                this.verticalSeekBar.onTouchEvent(motionEvent);
            }
        }
        return true;
    }

    public void refreshLayout() {
        if (this.view.getTranslationX() == 0.0f && this.view.getTranslationY() == 0.0f) {
            return;
        }
        this.view.setTranslationX(0.0f);
        this.view.setTranslationY(0.0f);
        MotionEvent motionEventObtain = MotionEvent.obtain(0L, 0L, 1, 0.0f, 0.0f, 0);
        if (this.hori_linearLayout.getVisibility() == 0) {
            this.horiSeekBar.onTouchEvent(motionEventObtain);
        } else {
            this.verticalSeekBar.onTouchEvent(motionEventObtain);
        }
    }
}
