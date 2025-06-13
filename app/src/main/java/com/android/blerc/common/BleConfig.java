package com.android.blerc.common;

/* loaded from: classes.dex */
public class BleConfig {
    private static BleConfig instance;
    private String mac;
    private String name;
    private int scanTimeout = BleConstant.DEFAULT_SCAN_TIME;
    private int connectTimeout = BleConstant.DEFAULT_CONN_TIME;
    private int operateTimeout = BleConstant.DEFAULT_OPERATE_TIME;
    private int connectRetryCount = 3;
    private int connectRetryInterval = 1000;
    private int operateRetryCount = 3;
    private int operateRetryInterval = 1000;
    private int maxConnectCount = 5;
    private int scanRepeatInterval = -1;

    private BleConfig() {
    }

    public static BleConfig getInstance() {
        if (instance == null) {
            synchronized (BleConfig.class) {
                if (instance == null) {
                    instance = new BleConfig();
                }
            }
        }
        return instance;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String str) {
        this.mac = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public int getOperateTimeout() {
        return this.operateTimeout;
    }

    public BleConfig setOperateTimeout(int i) {
        this.operateTimeout = i;
        return this;
    }

    public int getConnectTimeout() {
        return this.connectTimeout;
    }

    public BleConfig setConnectTimeout(int i) {
        this.connectTimeout = i;
        return this;
    }

    public int getScanTimeout() {
        return this.scanTimeout;
    }

    public BleConfig setScanTimeout(int i) {
        this.scanTimeout = i;
        return this;
    }

    public int getConnectRetryCount() {
        return this.connectRetryCount;
    }

    public BleConfig setConnectRetryCount(int i) {
        this.connectRetryCount = i;
        return this;
    }

    public int getConnectRetryInterval() {
        return this.connectRetryInterval;
    }

    public BleConfig setConnectRetryInterval(int i) {
        this.connectRetryInterval = i;
        return this;
    }

    public int getMaxConnectCount() {
        return this.maxConnectCount;
    }

    public BleConfig setMaxConnectCount(int i) {
        this.maxConnectCount = i;
        return this;
    }

    public int getOperateRetryCount() {
        return this.operateRetryCount;
    }

    public BleConfig setOperateRetryCount(int i) {
        this.operateRetryCount = i;
        return this;
    }

    public int getOperateRetryInterval() {
        return this.operateRetryInterval;
    }

    public BleConfig setOperateRetryInterval(int i) {
        this.operateRetryInterval = i;
        return this;
    }

    public int getScanRepeatInterval() {
        return this.scanRepeatInterval;
    }

    public BleConfig setScanRepeatInterval(int i) {
        this.scanRepeatInterval = i;
        return this;
    }
}
