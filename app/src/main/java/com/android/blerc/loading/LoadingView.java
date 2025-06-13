package com.android.blerc.loading;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;

import com.android.blerc.R;


/* loaded from: classes.dex */
public class LoadingView extends AppCompatImageView {
    private LoadingDrawable mLoadingDrawable;

    public LoadingView(Context context) {
        super(context);
    }

    public LoadingView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initAttrs(context, attributeSet);
    }

    private void initAttrs(Context context, AttributeSet attributeSet) {
        try {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.LoadingView);
            setLoadingRenderer(LoadingRendererFactory.createLoadingRenderer(context, typedArrayObtainStyledAttributes.getInt(0, 0)));
            typedArrayObtainStyledAttributes.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLoadingRenderer(LoadingRenderer loadingRenderer) {
        LoadingDrawable loadingDrawable = new LoadingDrawable(loadingRenderer);
        this.mLoadingDrawable = loadingDrawable;
        setImageDrawable(loadingDrawable);
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimation();
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnimation();
    }

    @Override // android.view.View
    protected void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (i == 0 && getVisibility() == 0) {
            startAnimation();
        } else {
            stopAnimation();
        }
    }

    private void startAnimation() {
        LoadingDrawable loadingDrawable = this.mLoadingDrawable;
        if (loadingDrawable != null) {
            loadingDrawable.start();
        }
    }

    private void stopAnimation() {
        LoadingDrawable loadingDrawable = this.mLoadingDrawable;
        if (loadingDrawable != null) {
            loadingDrawable.stop();
        }
    }
}
