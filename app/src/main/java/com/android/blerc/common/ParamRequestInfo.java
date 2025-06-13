package com.android.blerc.common;

/* loaded from: classes.dex */
public class ParamRequestInfo {
    private static ParamRequestInfo instance;
    public String DeviceSerialName;
    public int DragBrake;
    private int LightSwitch;

    private ParamRequestInfo() {
    }

    public static ParamRequestInfo getInstance() {
        if (instance == null) {
            synchronized (ParamRequestInfo.class) {
                if (instance == null) {
                    instance = new ParamRequestInfo();
                }
            }
        }
        return instance;
    }

    public int getLightSwitch() {
        return this.LightSwitch;
    }

    public void setLightSwitch(int i) {
        this.LightSwitch = i;
    }

    public int getDragBrake() {
        return this.DragBrake;
    }

    public void setDragBrake(int i) {
        this.DragBrake = i;
    }

    public String getDeviceSerialName() {
        return this.DeviceSerialName;
    }

    public void setDeviceSerialName(String str) {
        this.DeviceSerialName = str;
    }
}
