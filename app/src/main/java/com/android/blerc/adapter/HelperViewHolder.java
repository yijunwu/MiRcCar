package com.android.blerc.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/* loaded from: classes.dex */
public class HelperViewHolder extends BaseViewHolder implements ViewHelper<HelperViewHolder> {
    public HelperViewHolder(Context context, int i, ViewGroup viewGroup, int i2) {
        super(context, i, viewGroup, i2);
    }

    public HelperViewHolder() {
    }

    @Override // com.android.blerc.adapter.BaseViewHolder
    public HelperViewHolder get(Context context, int i, View view, ViewGroup viewGroup, int i2) {
        if (view == null) {
            return new HelperViewHolder(context, i, viewGroup, i2);
        }
        HelperViewHolder helperViewHolder = (HelperViewHolder) view.getTag();
        if (helperViewHolder.mLayoutId != i2) {
            helperViewHolder = new HelperViewHolder(context, i, viewGroup, i2);
        }
        helperViewHolder.setPosition(i);
        return helperViewHolder;
    }

    @Override // com.android.blerc.adapter.ViewHelper
    public HelperViewHolder setText(int i, String str) {
        ((TextView) getView(i)).setText(str);
        return this;
    }

    @Override // com.android.blerc.adapter.ViewHelper
    public HelperViewHolder setImageResource(int i, int i2) {
        ((ImageView) getView(i)).setImageResource(i2);
        return this;
    }

    @Override // com.android.blerc.adapter.ViewHelper
    public HelperViewHolder setBackgroundColor(int i, int i2) {
        getView(i).setBackgroundColor(i2);
        return this;
    }

    @Override // com.android.blerc.adapter.ViewHelper
    public HelperViewHolder setBackgroundColorRes(int i, int i2) {
        getView(i).setBackgroundResource(i2);
        return this;
    }

    @Override // com.android.blerc.adapter.ViewHelper
    public HelperViewHolder setTextColor(int i, int i2) {
        ((TextView) getView(i)).setTextColor(i2);
        return this;
    }

    @Override // com.android.blerc.adapter.ViewHelper
    public HelperViewHolder setTextColorRes(int i, int i2) {
        ((TextView) getView(i)).setTextColor(this.mContext.getResources().getColor(i2));
        return this;
    }

    @Override // com.android.blerc.adapter.ViewHelper
    public HelperViewHolder setImageDrawable(int i, Drawable drawable) {
        ((ImageView) getView(i)).setImageDrawable(drawable);
        return this;
    }

    @Override // com.android.blerc.adapter.ViewHelper
    public HelperViewHolder setImageDrawableRes(int i, int i2) {
        return setImageDrawable(i, this.mContext.getResources().getDrawable(i2));
    }

    @Override // com.android.blerc.adapter.ViewHelper
    public HelperViewHolder setImageUrl(int i, String str) {
        ImageView imageView = (ImageView) getView(i);
        if (!TextUtils.isEmpty(str)) {
            imageView.setImageURI(Uri.parse(str));
        }
        return this;
    }

    @Override // com.android.blerc.adapter.ViewHelper
    public HelperViewHolder setImageBitmap(int i, Bitmap bitmap) {
        ((ImageView) getView(i)).setImageBitmap(bitmap);
        return this;
    }

    @Override // com.android.blerc.adapter.ViewHelper
    public HelperViewHolder setVisible(int i, boolean z) {
        getView(i).setVisibility(z ? 0 : 8);
        return this;
    }

    @Override // com.android.blerc.adapter.ViewHelper
    public HelperViewHolder setTag(int i, Object obj) {
        getView(i).setTag(obj);
        return this;
    }

    @Override // com.android.blerc.adapter.ViewHelper
    public HelperViewHolder setTag(int i, int i2, Object obj) {
        getView(i).setTag(i2, obj);
        return this;
    }

    @Override // com.android.blerc.adapter.ViewHelper
    public HelperViewHolder setChecked(int i, boolean z) {
        ((Checkable) getView(i)).setChecked(z);
        return this;
    }

    @Override // com.android.blerc.adapter.ViewHelper
    public HelperViewHolder setAdapter(int i, Adapter adapter) {
        ((AdapterView) getView(i)).setAdapter(adapter);
        return this;
    }

    @Override // com.android.blerc.adapter.ViewHelper
    public HelperViewHolder setAlpha(int i, float f) {
        if (Build.VERSION.SDK_INT >= 11) {
            getView(i).setAlpha(f);
        } else {
            AlphaAnimation alphaAnimation = new AlphaAnimation(f, f);
            alphaAnimation.setDuration(0L);
            alphaAnimation.setFillAfter(true);
            getView(i).startAnimation(alphaAnimation);
        }
        return this;
    }

    @Override // com.android.blerc.adapter.ViewHelper
    public HelperViewHolder setTypeface(int i, Typeface typeface) {
        TextView textView = (TextView) getView(i);
        textView.setTypeface(typeface);
        textView.setPaintFlags(textView.getPaintFlags() | 128);
        return this;
    }

    @Override // com.android.blerc.adapter.ViewHelper
    public HelperViewHolder setTypeface(Typeface typeface, int... iArr) {
        for (int i : iArr) {
            TextView textView = (TextView) getView(i);
            textView.setTypeface(typeface);
            textView.setPaintFlags(textView.getPaintFlags() | 128);
        }
        return this;
    }

    @Override // com.android.blerc.adapter.ViewHelper
    public HelperViewHolder setProgress(int i, int i2) {
        ((ProgressBar) getView(i)).setProgress(i2);
        return this;
    }

    @Override // com.android.blerc.adapter.ViewHelper
    public HelperViewHolder setProgress(int i, int i2, int i3) {
        ProgressBar progressBar = (ProgressBar) getView(i);
        progressBar.setMax(i3);
        progressBar.setProgress(i2);
        return this;
    }

    @Override // com.android.blerc.adapter.ViewHelper
    public HelperViewHolder setMax(int i, int i2) {
        ((ProgressBar) getView(i)).setMax(i2);
        return this;
    }
}
