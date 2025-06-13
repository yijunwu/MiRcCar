package com.android.blerc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

/* loaded from: classes.dex */
public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {
    protected Context mContext;
    protected LayoutInflater mInflater;
    protected int[] mLayoutIds;
    protected List<T> mList;
    protected BaseViewHolder mViewHolder = new HelperViewHolder();

    private int checkLayoutId(int i, T t) {
        return 0;
    }

    public int checkLayout(int i, T t) {
        return 0;
    }

    public abstract <H extends BaseViewHolder> void convert(H h, int i, T t);

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public BaseAdapter(Context context, List<T> list, int... iArr) {
        this.mContext = context;
        this.mList = list;
        this.mLayoutIds = iArr;
        this.mInflater = LayoutInflater.from(context);
    }

    public BaseAdapter(Context context, List<T> list) {
        this.mContext = context;
        this.mList = list;
        this.mInflater = LayoutInflater.from(context);
    }

    public BaseAdapter(Context context, int... iArr) {
        this.mContext = context;
        this.mLayoutIds = iArr;
        this.mInflater = LayoutInflater.from(context);
    }

    public BaseAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        int viewCheckLayoutId = getViewCheckLayoutId(i);
        BaseViewHolder baseViewHolder = this.mViewHolder.get(this.mContext, i, view, viewGroup, viewCheckLayoutId);
        this.mViewHolder = baseViewHolder;
        convert(baseViewHolder, i, this.mList.get(i));
        return this.mViewHolder.getConvertView(viewCheckLayoutId);
    }

    protected int getViewCheckLayoutId(int i) {
        int[] iArr = this.mLayoutIds;
        if (iArr == null) {
            return checkLayoutId(i, this.mList.get(i));
        }
        if (iArr.length == 0) {
            throw new ArrayIndexOutOfBoundsException("not layoutId");
        }
        return iArr[checkLayout(i, this.mList.get(i))];
    }

    @Override // android.widget.Adapter
    public int getCount() {
        List<T> list = this.mList;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        List<T> list = this.mList;
        if (list != null) {
            return list.get(i);
        }
        return null;
    }
}
