package com.android.blerc;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatSeekBar;

/* loaded from: classes.dex */
public class VerticalSeekBar extends AppCompatSeekBar {
    private OnSeekBarChangeListener mOnSeekBarChangeListener;
    private Drawable mThumb;

    public interface OnSeekBarChangeListener {
        void onProgressChanged(VerticalSeekBar verticalSeekBar, int i, boolean z);

        void onStartTrackingTouch(VerticalSeekBar verticalSeekBar);

        void onStopTrackingTouch(VerticalSeekBar verticalSeekBar);
    }

    public VerticalSeekBar(Context context) {
        this(context, null);
    }

    public VerticalSeekBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, android.R.attr.seekBarStyle);
    }

    public VerticalSeekBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setOnSeekBarChangeListener(OnSeekBarChangeListener onSeekBarChangeListener) {
        this.mOnSeekBarChangeListener = onSeekBarChangeListener;
    }

    void onStartTrackingTouch() {
        OnSeekBarChangeListener onSeekBarChangeListener = this.mOnSeekBarChangeListener;
        if (onSeekBarChangeListener != null) {
            onSeekBarChangeListener.onStartTrackingTouch(this);
        }
    }

    void onStopTrackingTouch() {
        OnSeekBarChangeListener onSeekBarChangeListener = this.mOnSeekBarChangeListener;
        if (onSeekBarChangeListener != null) {
            onSeekBarChangeListener.onStopTrackingTouch(this);
        }
    }

    void onProgressRefresh(float f, boolean z) {
        Drawable drawable = this.mThumb;
        if (drawable != null) {
            setThumbPos(getHeight(), drawable, f, Integer.MIN_VALUE);
            invalidate();
        }
        OnSeekBarChangeListener onSeekBarChangeListener = this.mOnSeekBarChangeListener;
        if (onSeekBarChangeListener != null) {
            onSeekBarChangeListener.onProgressChanged(this, getProgress(), z);
        }
    }

    private void setThumbPos(int i, Drawable drawable, float f, int i2) {
        int i3;
        int paddingLeft = (i + getPaddingLeft()) - getPaddingRight();
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        int thumbOffset = (int) (f * ((paddingLeft - intrinsicWidth) + (getThumbOffset() * 2)));
        if (i2 == Integer.MIN_VALUE) {
            Rect bounds = drawable.getBounds();
            i2 = bounds.top;
            i3 = bounds.bottom;
        } else {
            i3 = i2 + intrinsicHeight;
        }
        drawable.setBounds(thumbOffset, i2, intrinsicWidth + thumbOffset, i3);
    }

    @Override // android.view.View
    public void invalidate() {
        super.invalidate();
        onSizeChanged(getWidth(), getHeight(), 0, 0);
    }

    @Override // android.support.v7.widget.AppCompatSeekBar, android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    protected void onDraw(Canvas canvas) {
        canvas.rotate(-90.0f);
        canvas.translate(-getHeight(), 0.0f);
        super.onDraw(canvas);
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    protected synchronized void onMeasure(int i, int i2) {
        super.onMeasure(i2, i);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    @Override // android.widget.AbsSeekBar
    public void setThumb(Drawable drawable) {
        this.mThumb = drawable;
        super.setThumb(drawable);
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i2, i, i3, i4);
    }

    @Override // android.widget.AbsSeekBar, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isEnabled()) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action == 0) {
            setPressed(true);
            onStartTrackingTouch();
            trackTouchEvent(motionEvent);
            onSizeChanged(getWidth(), getHeight(), 0, 0);
        } else if (action == 1) {
            trackTouchEvent(motionEvent);
            onStopTrackingTouch();
            setPressed(false);
            onSizeChanged(getWidth(), getHeight(), 0, 0);
        } else if (action == 2) {
            trackTouchEvent(motionEvent);
            attemptClaimDrag();
            onSizeChanged(getWidth(), getHeight(), 0, 0);
        } else if (action == 3) {
            onStopTrackingTouch();
            setPressed(false);
            onSizeChanged(getWidth(), getHeight(), 0, 0);
        }
        return true;
    }

    private void trackTouchEvent(MotionEvent motionEvent) {
        int y = (int) motionEvent.getY();
        int height = getHeight();
        int paddingRight = getPaddingRight();
        int i = height - paddingRight;
        float paddingLeft = y < paddingRight ? 1.0f : y > i ? 0.0f : (((float)height - y) + paddingRight) / (i - getPaddingLeft()); //TODO wuyijun modified
        float max = getMax() * paddingLeft;
        if (motionEvent.getAction() == 1) {
            setProgress(1000);
        } else {
            setProgress((int) max);
        }
        onProgressRefresh(paddingLeft, true);
    }

    private void attemptClaimDrag() {
        if (getParent() != null) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
    }

    @Override // android.widget.ProgressBar
    public synchronized void setProgress(int i) {
        super.setProgress(i);
        onSizeChanged(getWidth(), getHeight(), 0, 0);
        if (this.mOnSeekBarChangeListener != null) {
            this.mOnSeekBarChangeListener.onProgressChanged(this, getProgress(), true);
        }
    }
}
