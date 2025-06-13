package com.android.blerc.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/* loaded from: classes.dex */
public class BaseViewHolder {
    protected Context mContext;
    private View mConvertView;
    private SparseArray<View> mConvertViews;
    protected int mLayoutId;
    private int mPosition;
    private SparseArray<View> mViews;

    public BaseViewHolder(Context context, int i, ViewGroup viewGroup, int i2) {
        this.mViews = new SparseArray<>();
        SparseArray<View> sparseArray = new SparseArray<>();
        this.mConvertViews = sparseArray;
        View view = sparseArray.get(i2);
        this.mConvertView = view;
        this.mPosition = i;
        this.mContext = context;
        this.mLayoutId = i2;
        if (view == null) {
            View viewInflate = LayoutInflater.from(context).inflate(i2, viewGroup, false);
            this.mConvertView = viewInflate;
            this.mConvertViews.put(i2, viewInflate);
            this.mConvertView.setTag(this);
        }
    }

    public BaseViewHolder() {
        this.mViews = new SparseArray<>();
        this.mConvertViews = new SparseArray<>();
    }

    public <T extends BaseViewHolder> T get(Context context, int i, View view, ViewGroup viewGroup, int i2) {
        if (view == null) {
            return (T) new BaseViewHolder(context, i, viewGroup, i2);
        }
        T t = (T) view.getTag();
        if (t.mLayoutId != i2) {
            return (T) new BaseViewHolder(context, i, viewGroup, i2);
        }
        t.setPosition(i);
        return t;
    }

    public <T extends View> T getView(int i) {
        T t = (T) this.mViews.get(i);
        if (t != null) {
            return t;
        }
        T t2 = (T) this.mConvertView.findViewById(i);
        this.mViews.put(i, t2);
        return t2;
    }

    public View getConvertView() {
        return this.mConvertViews.valueAt(0);
    }

    public View getConvertView(int i) {
        return this.mConvertViews.get(i);
    }

    public void setPosition(int i) {
        this.mPosition = i;
    }

    public int getLayoutId() {
        return this.mLayoutId;
    }
}
