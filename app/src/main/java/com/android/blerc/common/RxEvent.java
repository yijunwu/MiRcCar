package com.android.blerc.common;

/* loaded from: classes.dex */
public class RxEvent {
    private static volatile RxEvent mInstance;
    private String mMsg;

    private RxEvent() {
    }

    public static RxEvent getInstance() {
        if (mInstance == null) {
            synchronized (RxEvent.class) {
                if (mInstance == null) {
                    mInstance = new RxEvent();
                }
            }
        }
        return mInstance;
    }

    public RxEvent(String str) {
        this.mMsg = str;
    }

    public String getMsg() {
        return this.mMsg;
    }
}
