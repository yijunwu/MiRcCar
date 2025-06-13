package com.android.blerc.loading;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;

/* loaded from: classes.dex */
public class LoadingDrawable extends Drawable implements Animatable {
    private final Drawable.Callback mCallback;
    private final LoadingRenderer mLoadingRender;

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    public LoadingDrawable(LoadingRenderer loadingRenderer) {
        Drawable.Callback callback = new Drawable.Callback() { // from class: com.android.blerc.loading.LoadingDrawable.1
            @Override // android.graphics.drawable.Drawable.Callback
            public void invalidateDrawable(Drawable drawable) {
                LoadingDrawable.this.invalidateSelf();
            }

            @Override // android.graphics.drawable.Drawable.Callback
            public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
                LoadingDrawable.this.scheduleSelf(runnable, j);
            }

            @Override // android.graphics.drawable.Drawable.Callback
            public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
                LoadingDrawable.this.unscheduleSelf(runnable);
            }
        };
        this.mCallback = callback;
        this.mLoadingRender = loadingRenderer;
        loadingRenderer.setCallback(callback);
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.mLoadingRender.setBounds(rect);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        if (getBounds().isEmpty()) {
            return;
        }
        this.mLoadingRender.draw(canvas);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mLoadingRender.setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mLoadingRender.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Animatable
    public void start() {
        this.mLoadingRender.start();
    }

    @Override // android.graphics.drawable.Animatable
    public void stop() {
        this.mLoadingRender.stop();
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        return this.mLoadingRender.isRunning();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return (int) this.mLoadingRender.mHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return (int) this.mLoadingRender.mWidth;
    }
}
