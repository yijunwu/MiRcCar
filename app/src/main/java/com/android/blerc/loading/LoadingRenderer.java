package com.android.blerc.loading;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.animation.LinearInterpolator;

/* loaded from: classes.dex */
public abstract class LoadingRenderer {
    private static final long ANIMATION_DURATION = 1333;
    private static final float DEFAULT_SIZE = 56.0f;
    private final ValueAnimator.AnimatorUpdateListener mAnimatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.blerc.loading.LoadingRenderer.1
        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            LoadingRenderer.this.computeRender(((Float) valueAnimator.getAnimatedValue()).floatValue());
            LoadingRenderer.this.invalidateSelf();
        }
    };
    protected final Rect mBounds = new Rect();
    private Drawable.Callback mCallback;
    protected long mDuration;
    protected float mHeight;
    private ValueAnimator mRenderAnimator;
    protected float mWidth;

    protected abstract void computeRender(float f);

    @Deprecated
    protected void draw(Canvas canvas, Rect rect) {
    }

    protected abstract void reset();

    protected abstract void setAlpha(int i);

    protected abstract void setColorFilter(ColorFilter colorFilter);

    public LoadingRenderer(Context context) {
        initParams(context);
        setupAnimators();
    }

    protected void draw(Canvas canvas) {
        draw(canvas, this.mBounds);
    }

    protected void addRenderListener(Animator.AnimatorListener animatorListener) {
        this.mRenderAnimator.addListener(animatorListener);
    }

    void start() {
        reset();
        this.mRenderAnimator.addUpdateListener(this.mAnimatorUpdateListener);
        this.mRenderAnimator.setRepeatCount(-1);
        this.mRenderAnimator.setDuration(this.mDuration);
        this.mRenderAnimator.start();
    }

    void stop() {
        this.mRenderAnimator.removeUpdateListener(this.mAnimatorUpdateListener);
        this.mRenderAnimator.setRepeatCount(0);
        this.mRenderAnimator.setDuration(0L);
        this.mRenderAnimator.end();
    }

    boolean isRunning() {
        return this.mRenderAnimator.isRunning();
    }

    void setCallback(Drawable.Callback callback) {
        this.mCallback = callback;
    }

    void setBounds(Rect rect) {
        this.mBounds.set(rect);
    }

    private void initParams(Context context) {
        this.mWidth = DensityUtil.dip2px(context, DEFAULT_SIZE);
        this.mHeight = DensityUtil.dip2px(context, DEFAULT_SIZE);
        this.mDuration = ANIMATION_DURATION;
    }

    private void setupAnimators() {
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mRenderAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setRepeatCount(-1);
        this.mRenderAnimator.setRepeatMode(1);
        this.mRenderAnimator.setDuration(this.mDuration);
        this.mRenderAnimator.setInterpolator(new LinearInterpolator());
        this.mRenderAnimator.addUpdateListener(this.mAnimatorUpdateListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invalidateSelf() {
        this.mCallback.invalidateDrawable(null);
    }
}
