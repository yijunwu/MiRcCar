package com.android.blerc.common;

/* loaded from: classes.dex */
public class DeviceMsg {
    String devicemac;
    String devicename;
    int devicerssi;

    public DeviceMsg(String str, String str2, int i) {
        this.devicename = str;
        this.devicemac = str2;
        this.devicerssi = i;
    }

    public String getDevicename() {
        return this.devicename;
    }

    public void setDevicename(String str) {
        this.devicename = str;
    }

    public String getDevicemac() {
        return this.devicemac;
    }

    public void setDevicemac(String str) {
        this.devicemac = str;
    }

    public int getDevicerssi() {
        return this.devicerssi;
    }

    public void setDevicerssi(int i) {
        this.devicerssi = i;
    }

    public String toString() {
        return "DeviceMsg{devicename='" + this.devicename + "', devicemac='" + this.devicemac + "', devicerssi=" + this.devicerssi + '}';
    }
}
