package com.android.blerc.adapter;

import java.util.List;

/* loaded from: classes.dex */
interface DataHelper<T> {
    void add(int i, T t);

    void addAll(int i, List<T> list);

    void addItemToHead(T t);

    void addItemToLast(T t);

    void addItemsToHead(List<T> list);

    void addItemsToLast(List<T> list);

    void alterObj(int i, T t);

    void alterObj(T t, T t2);

    void clear();

    boolean contains(T t);

    T getData(int i);

    boolean isEnabled(int i);

    void remove(T t);

    void removeToIndex(int i);

    void replaceAll(List<T> list);

    void setListAll(List<T> list);
}
