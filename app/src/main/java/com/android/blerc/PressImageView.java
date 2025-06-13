package com.android.blerc;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;

/* loaded from: classes.dex */
public class PressImageView extends AppCompatImageView {
    public PressImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public PressImageView(Context context) {
        super(context);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        Drawable drawable;
        int action = motionEvent.getAction();
        if (action == 0) {
            Drawable drawable2 = getDrawable();
            if (drawable2 != null) {
                drawable2.mutate().setColorFilter(-7829368, PorterDuff.Mode.LIGHTEN);
            }
        } else if ((action == 1 || action == 3) && (drawable = getDrawable()) != null) {
            drawable.mutate().clearColorFilter();
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setImageDrawable(null);
    }

    @Override // android.widget.ImageView
    public void setScaleType(ImageView.ScaleType scaleType) {
        super.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }
}
