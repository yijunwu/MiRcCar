package com.android.blerc.adapter;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public abstract class HelperAdapter<T> extends BaseAdapter<T> implements DataHelper<T> {
    public abstract void HelpConvert(HelperViewHolder helperViewHolder, int i, T t);

    public HelperAdapter(Context context, List list, int... iArr) {
        super(context, list, iArr);
    }

    public HelperAdapter(Context context, List list) {
        super(context, list);
    }

    public HelperAdapter(Context context, int... iArr) {
        super(context, iArr);
    }

    public HelperAdapter(Context context) {
        super(context);
    }

    @Override // com.android.blerc.adapter.BaseAdapter
    public <H extends BaseViewHolder> void convert(H h, int i, T t) {
        HelpConvert((HelperViewHolder) h, i, t);
    }

    @Override // android.widget.BaseAdapter, android.widget.ListAdapter, com.android.blerc.adapter.DataHelper
    public boolean isEnabled(int i) {
        return this.mList != null && i < this.mList.size();
    }

    @Override // com.android.blerc.adapter.DataHelper
    public void addItemToHead(T t) {
        add(0, t);
    }

    @Override // com.android.blerc.adapter.DataHelper
    public void addItemToLast(T t) {
        add(this.mList.size(), t);
    }

    @Override // com.android.blerc.adapter.DataHelper
    public void addItemsToHead(List<T> list) {
        addAll(0, list);
    }

    @Override // com.android.blerc.adapter.DataHelper
    public void addItemsToLast(List<T> list) {
        addAll(this.mList != null ? this.mList.size() : 0, list);
    }

    @Override // com.android.blerc.adapter.DataHelper
    public void addAll(int i, List<T> list) {
        if (this.mList != null) {
            this.mList.addAll(i, list);
        }
        notifyDataSetChanged();
    }

    @Override // com.android.blerc.adapter.DataHelper
    public void add(int i, T t) {
        if (this.mList != null) {
            this.mList.add(i, t);
        }
        notifyDataSetChanged();
    }

    @Override // com.android.blerc.adapter.DataHelper
    public T getData(int i) {
        if (getCount() == 0 || this.mList == null) {
            return null;
        }
        return this.mList.get(i);
    }

    @Override // com.android.blerc.adapter.DataHelper
    public void alterObj(T t, T t2) {
        alterObj(this.mList != null ? this.mList.indexOf(t) : 0, t2);
    }

    @Override // com.android.blerc.adapter.DataHelper
    public void alterObj(int i, T t) {
        if (this.mList != null) {
            this.mList.set(i, t);
        }
        notifyDataSetChanged();
    }

    @Override // com.android.blerc.adapter.DataHelper
    public void remove(T t) {
        if (this.mList != null) {
            this.mList.remove(t);
        }
        notifyDataSetChanged();
    }

    @Override // com.android.blerc.adapter.DataHelper
    public void removeToIndex(int i) {
        if (this.mList != null) {
            this.mList.remove(i);
        }
        notifyDataSetChanged();
    }

    @Override // com.android.blerc.adapter.DataHelper
    public void replaceAll(List<T> list) {
        if (this.mList != null) {
            this.mList.clear();
        }
        addAll(0, list);
    }

    @Override // com.android.blerc.adapter.DataHelper
    public void clear() {
        if (this.mList != null) {
            this.mList.clear();
        }
        notifyDataSetChanged();
    }

    @Override // com.android.blerc.adapter.DataHelper
    public boolean contains(T t) {
        return this.mList != null && this.mList.contains(t);
    }

    @Override // com.android.blerc.adapter.DataHelper
    public void setListAll(List<T> list) {
        if (this.mList == null) {
            this.mList = new ArrayList();
        }
        this.mList.clear();
        this.mList.addAll(list);
        notifyDataSetChanged();
    }
}
