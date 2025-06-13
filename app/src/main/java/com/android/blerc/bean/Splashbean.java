package com.android.blerc.bean;

import android.os.Environment;
import com.android.blerc.common.BleConfig;
import com.google.gson.annotations.SerializedName;
import java.io.File;
import java.util.List;

/* loaded from: classes.dex */
public class Splashbean {
    private static Splashbean instance;
    private String app_update;
    private String app_ver;
    private String firmware_update;
    private String firmware_ver;
    private StartBean start;

    private Splashbean() {
    }

    public static Splashbean getInstance() {
        if (instance == null) {
            synchronized (BleConfig.class) {
                if (instance == null) {
                    instance = new Splashbean();
                }
            }
        }
        return instance;
    }

    public String getApp_ver() {
        return this.app_ver;
    }

    public void setApp_ver(String str) {
        this.app_ver = str;
    }

    public String getApp_update() {
        return this.app_update;
    }

    public void setApp_update(String str) {
        this.app_update = str;
    }

    public String getFirmware_ver() {
        return this.firmware_ver;
    }

    public void setFirmware_ver(String str) {
        this.firmware_ver = str;
    }

    public String getFirmware_update() {
        return this.firmware_update;
    }

    public void setFirmware_update(String str) {
        this.firmware_update = str;
    }

    public StartBean getStart() {
        return this.start;
    }

    public void setStart(StartBean startBean) {
        this.start = startBean;
    }

    public static class StartBean {

        @SerializedName("1920x1080")
        private List<String> ratio;

        public List<String> getRatio() {
            return this.ratio;
        }

        public void setRatio(List<String> list) {
            this.ratio = list;
        }
    }

    public String getDownloadPath() {
        return Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_DOWNLOADS + File.separator;
    }
}
