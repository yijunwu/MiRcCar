package com.android.blerc.common;

/* loaded from: classes.dex */
public class SystemInfo {
    private static SystemInfo instance;
    private String FirmwareVersion;

    private SystemInfo() {
    }

    public static SystemInfo getInstance() {
        if (instance == null) {
            synchronized (SystemInfo.class) {
                if (instance == null) {
                    instance = new SystemInfo();
                }
            }
        }
        return instance;
    }

    public String getFirmwareVersion() {
        return this.FirmwareVersion;
    }

    public void setFirmwareVersion(String str) {
        this.FirmwareVersion = str;
    }
}
