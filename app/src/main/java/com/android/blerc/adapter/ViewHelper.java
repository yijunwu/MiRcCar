package com.android.blerc.adapter;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.widget.Adapter;
import com.android.blerc.adapter.BaseViewHolder;

/* loaded from: classes.dex */
public interface ViewHelper<T extends BaseViewHolder> {
    T setAdapter(int i, Adapter adapter);

    T setAlpha(int i, float f);

    T setBackgroundColor(int i, int i2);

    T setBackgroundColorRes(int i, int i2);

    T setChecked(int i, boolean z);

    T setImageBitmap(int i, Bitmap bitmap);

    T setImageDrawable(int i, Drawable drawable);

    T setImageDrawableRes(int i, int i2);

    T setImageResource(int i, int i2);

    T setImageUrl(int i, String str);

    T setMax(int i, int i2);

    T setProgress(int i, int i2);

    T setProgress(int i, int i2, int i3);

    T setTag(int i, int i2, Object obj);

    T setTag(int i, Object obj);

    T setText(int i, String str);

    T setTextColor(int i, int i2);

    T setTextColorRes(int i, int i2);

    T setTypeface(int i, Typeface typeface);

    T setTypeface(Typeface typeface, int... iArr);

    T setVisible(int i, boolean z);
}
