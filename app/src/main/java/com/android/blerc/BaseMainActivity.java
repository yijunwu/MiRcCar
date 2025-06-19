package com.android.blerc;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;

import butterknife.ButterKnife;
import com.android.blerc.SwitchButton;
import com.android.blerc.VerticalSeekBar;
import com.android.blerc.bean.Splashbean;
import com.android.blerc.common.BleConfig;
import com.android.blerc.common.BleDeviceManager;
import com.android.blerc.common.ParamRequestInfo;
import com.android.blerc.common.RxEvent;
import com.android.blerc.common.SystemInfo;
import com.android.blerc.common.ViseBle;
import com.android.blerc.db.DBConstant;
import com.android.blerc.db.SharedPreferencesUtils;
import com.android.blerc.dialog.AgreementDialog;
import com.android.blerc.dialog.CommonDialog;
import com.android.blerc.dialog.ConnectDialog;
import com.android.blerc.dialog.FirmwareUpdateDialog;
import com.android.blerc.dialog.GuideDialog;
import com.android.blerc.dialog.ListViewDialog;
import com.android.blerc.dialog.NoDevicesTipDialog;
import com.android.blerc.dialog.VersionDialog;
import com.android.blerc.location.ApiLevelHelper;
import com.android.blerc.location.LocationUtils;
import com.android.blerc.model.BluetoothLeDevice;
import com.android.blerc.model.BluetoothLeDeviceStore;
import com.android.blerc.scan.IScanCallback;
import com.android.blerc.scan.ScanCallback;
import com.android.blerc.util.BleUtil;
import com.android.blerc.util.DataUtil;
import com.android.blerc.util.HexUtil;
import com.blankj.utilcode.util.ToastUtils;
import com.jakewharton.rxbinding2.view.RxView;
import io.reactivex.functions.Consumer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import me.jessyan.autosize.AutoSizeCompat;
import me.jessyan.autosize.utils.AutoSizeUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

// --- [新增] --- 引入手柄支持所需的相关包 ---
import android.content.Context;
import android.hardware.input.InputManager;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
// --- [新增结束] ---

/* loaded from: classes.dex */
public abstract class BaseMainActivity extends BarBaseActivity {
    public static final float EPSILON = 1.0E-9f;
    public static final float FILTER_COEFFICIENT = 0.98f;
    private static final float NS2S = 1.0E-9f;
    AgreementDialog agreementDialog;
    ImageView around_down;
    ImageView around_down1;
    ImageView around_down_img;
    ImageView around_down_img1;
    ImageView around_left;
    ImageView around_left1;
    ImageView around_left_img;
    ImageView around_left_img1;
    ImageView around_right;
    ImageView around_right1;
    ImageView around_right_img;
    ImageView around_right_img1;
    ImageView around_up;
    ImageView around_up1;
    ImageView around_up_img;
    ImageView around_up_img1;
    CommonDialog blerequestDialog;
    ImageView brake_imgbtn;
    Button centerbtn;
    ConnectDialog connectDialog;
    RelativeLayout drag_height;
    DragView drag_view;
    DragView drag_view1;
    FirmwareUpdateDialog firmwareUpdateDialog;
    public ImageView garage_imgbtn;
    GuideDialog guideDialog;
    LinearLayout hor_left;
    LinearLayout hor_right;
    LinearLayout hor_sensor_left;
    SeekBar horiSeekBar;
    SeekBar horiSeekBar1;
    LinearLayout horiSeekBar1_layout;
    LinearLayout horiSeekBar_layout;
    ImageView iv_battery;
    LinearLayout left_sensor_layout;
    ScrollView left_view;
    ImageView low_speed_imgbtn;
    ListViewDialog mListViewDialog;
    private sendThread mSendThread;
    private SensorManager mSensorManager;
    VersionDialog mVersionDialog;
    RelativeLayout main_layout;
    public ImageView menu_img;
    LinearLayout menu_set;
    CommonDialog quitDailog;
    LinearLayout right_sensor_layout;
    ScrollView right_view;
    ImageView sensor_left_down;
    ImageView sensor_left_up;
    ImageView sensor_right_down;
    ImageView sensor_right_up;
    ImageView set_close;
    public ImageView set_imgbtn;
    SwitchButton switch_accelerator;
    public ImageView switch_mode_imgbtn;
    SwitchButton switch_turn;
    LinearLayout toptitle;
    SeekBar turn_dr;
    LinearLayout turn_dr_add;
    LinearLayout turn_dr_subtract;
    SeekBar turn_middle;
    LinearLayout turn_middle_add;
    LinearLayout turn_middle_subtract;
    TextView tx_connect_tip;
    TextView tx_connect_title;
    TextView tx_switch_accelerator;
    TextView tx_switch_turn;
    TextView tx_turn_dr;
    TextView tx_turn_middle;
    LinearLayout ver_left;
    LinearLayout ver_right;
    LinearLayout ver_sensor_right;
    VerticalSeekBar verticalSeekBar;
    VerticalSeekBar verticalSeekBar1;
    LinearLayout verticalSeekBar1_layout;
    LinearLayout verticalSeekBar_layout;

    // --- [新增] --- 用于手柄支持的成员变量 ---
    private InputManager mInputManager;
    private InputDevice mGamepadDevice;
    private boolean isGamepadControlActive = false;
    // --- [新增结束] ---

    private final String TAG = "CarMi";
    private final int MY_PERMISSIONS_REQUEST_MULTIPLE = 2;
    int centerbtn_flag = 1;
    private Timer fuseTimer = new Timer();
    private boolean fuseFlag = false;
    private int battery = -1;
    private Integer savedBattery = -1;
    private Long savedTimeStamp = -1L;
    private boolean isSameDevice = false;
    public boolean isDfu = false;
    private AlphaAnimation mHideAnimation = null;

    // 1. 定义一个 ActivityResultLauncher
    public final ActivityResultLauncher<String[]> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), permissions -> {
                // 3. 在这里处理用户的响应
                boolean allPermissionsGranted = true;
                for (Boolean granted : permissions.values()) {
                    if (!granted) {
                        allPermissionsGranted = false;
                        break;
                    }
                }

                if (allPermissionsGranted) {
                    // 用户授予了所有权限，可以开始执行蓝牙扫描了！
                    Log.d("Permission", "所有权限已授予，开始扫描...");
                } else {
                    // 用户拒绝了部分或全部权限。
                    // 你应该优雅地处理这种情况，比如显示一个提示，说明为什么需要这些权限。
                    Log.d("Permission", "部分或全部权限被拒绝。");
                    Toast.makeText(this, "需要蓝牙和位置权限才能扫描设备", Toast.LENGTH_SHORT).show();
                }
            });
    private Handler mHandler = new Handler() { // from class: com.android.blerc.BaseMainActivity.10
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            try {
                switch (message.what) {
                    case 1:
                        BaseMainActivity baseMainActivity = BaseMainActivity.this;
                        baseMainActivity.isSameDevice = DBConstant.getInstance(baseMainActivity).getAddress().equals(BleConfig.getInstance().getMac());
                        BaseMainActivity.this.dimissProgressDialog();
                        DBConstant.getInstance(BaseMainActivity.this).setAddress(BleConfig.getInstance().getMac());
                        try {
                            BaseMainActivity.this.getIntent().removeExtra("splash");
                            BaseMainActivity.this.writeChar1(DataUtil.getInstance().byteMerger(HexUtil.hexStringToBytes("20"), HexUtil.intToByteArray(1)));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        BaseMainActivity baseMainActivity2 = BaseMainActivity.this;
                        baseMainActivity2.savedBattery = Integer.valueOf(Integer.parseInt((String) SharedPreferencesUtils.getParam(baseMainActivity2, "battery", "-1")));
                        BaseMainActivity baseMainActivity3 = BaseMainActivity.this;
                        baseMainActivity3.savedTimeStamp = Long.valueOf(Long.parseLong((String) SharedPreferencesUtils.getParam(baseMainActivity3, "battery-time", "-1")));
                        return;
                    case 2:
                        BaseMainActivity.this.stopSendThread();
                        if (!BaseMainActivity.this.isDfu) {
                            BaseMainActivity.this.showConnectDialog();
                        }
                        BaseMainActivity.this.tx_connect_tip.setVisibility(0);
                        BaseMainActivity.this.iv_battery.setVisibility(8);
                        if (BaseMainActivity.this.battery != -1) {
                            SharedPreferencesUtils.setParam(BaseMainActivity.this, "battery-time", System.currentTimeMillis() + "");
                            SharedPreferencesUtils.setParam(BaseMainActivity.this, "battery", BaseMainActivity.this.battery + "");
                            return;
                        }
                        return;
                    case 3:
                        BaseMainActivity.this.dialogDismiss();
                        BaseMainActivity baseMainActivity4 = BaseMainActivity.this;
                        baseMainActivity4.showProgressDialog(baseMainActivity4.getString(R.string.warmhints), BaseMainActivity.this.getString(R.string.exit), "连接设备中", 0);
                        return;
                    case 4:
                        ViseBle.getInstance().readCharacteristic(ViseBle.getInstance().getmBle_char5());
                        try {
                            Thread.sleep(200L);
                            BaseMainActivity.this.writeChar1(DataUtil.getInstance().byteMerger(HexUtil.hexStringToBytes("10"), HexUtil.intToByteArray(4)));
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                        if (ParamRequestInfo.getInstance().getDragBrake() == 0) {
                            BaseMainActivity.this.brake_imgbtn.setImageResource(R.mipmap.brake);
                            return;
                        } else {
                            BaseMainActivity.this.brake_imgbtn.setImageResource(R.mipmap.brake_press);
                            return;
                        }
                    case 5:
                        new Thread(new Runnable() { // from class: com.android.blerc.BaseMainActivity.10.1
                            @Override // java.lang.Runnable
                            public void run() {
                                ViseBle.getInstance().readCharacteristic(ViseBle.getInstance().getmBle_char4());
                                try {
                                    Thread.sleep(2000L);
                                } catch (InterruptedException e3) {
                                    e3.printStackTrace();
                                }
                                ViseBle.getInstance().readCharacteristic(ViseBle.getInstance().getmBle_char5());
                            }
                        }).start();
                        return;
                    case 6:
                        ViseBle.getInstance().readCharacteristic(ViseBle.getInstance().getmBle_char5());
                        return;
                    case 7:
                    default:
                        return;
                    case 8:
                        BaseMainActivity.this.mHandler.sendEmptyMessageDelayed(9, 3000L);
                        new Thread(new Runnable() { // from class: com.android.blerc.BaseMainActivity.10.2
                            @Override // java.lang.Runnable
                            public void run() {
                                ViseBle.getInstance().readCharacteristic(ViseBle.getInstance().getmBle_char5());
                            }
                        }).start();
                        return;
                    case 9:
                        BaseMainActivity.this.setBatteryProgress();
                        return;
                    case 10:
                        if (BleDeviceManager.getInstance().isBleConnectState()) {
                            ViseBle.getInstance().disConnect();
                        }
                        BaseMainActivity.this.enableBluetooth();
                        return;
                    case 11:
                        BaseMainActivity.this.setBatteryProgress();
                        if (ParamRequestInfo.getInstance().getDragBrake() == 0) {
                            BaseMainActivity.this.brake_imgbtn.setImageResource(R.mipmap.brake);
                        } else {
                            BaseMainActivity.this.brake_imgbtn.setImageResource(R.mipmap.brake_press);
                        }
                        if (Integer.parseInt(Splashbean.getInstance().getFirmware_ver().replaceAll("\\.", "")) > Integer.parseInt(SystemInfo.getInstance().getFirmwareVersion().substring(1).replaceAll("\\.", "")) && BaseMainActivity.this.battery > 60) {
                            BaseMainActivity.this.firmwareUpdateDialog.setAddress(BleConfig.getInstance().getMac());
                            BaseMainActivity.this.firmwareUpdateDialog.setDeviceName(BleConfig.getInstance().getName());
                            BaseMainActivity.this.firmwareUpdateDialog.show();
                            break;
                        }
                        break;
                    case 12:
                        if (BaseMainActivity.this.mHideAnimation != null) {
                            BaseMainActivity.this.mHideAnimation.cancel();
                        }
                        BaseMainActivity.this.mHideAnimation = new AlphaAnimation(1.0f, 0.0f);
                        BaseMainActivity.this.mHideAnimation.setDuration(2000L);
                        BaseMainActivity.this.mHideAnimation.setFillAfter(true);
                        BaseMainActivity.this.centerbtn.startAnimation(BaseMainActivity.this.mHideAnimation);
                        return;
                    case 13:
                        BaseMainActivity.this.centerbtn.setVisibility(0);
                        if (ParamRequestInfo.getInstance().getDragBrake() == 1) {
                            BaseMainActivity.this.centerbtn.setText(R.string.open_brake);
                            BaseMainActivity.this.brake_imgbtn.setImageResource(R.mipmap.brake_press);
                            BaseMainActivity.this.SetCenterbtnGone();
                            return;
                        } else {
                            BaseMainActivity.this.centerbtn.setText(R.string.close_brake);
                            BaseMainActivity.this.brake_imgbtn.setImageResource(R.mipmap.brake);
                            BaseMainActivity.this.SetCenterbtnGone();
                            return;
                        }
                    case 14:
                        if (ParamRequestInfo.getInstance().getDragBrake() == 0) {
                            BaseMainActivity.this.writeChar1(DataUtil.getInstance().byteMerger(HexUtil.hexStringToBytes("21"), HexUtil.intToByteArray(1), HexUtil.intToByteArray(1)));
                            break;
                        } else {
                            BaseMainActivity.this.writeChar1(DataUtil.getInstance().byteMerger(HexUtil.hexStringToBytes("21"), HexUtil.intToByteArray(1), HexUtil.intToByteArray(0)));
                            break;
                        }
                    case 15:
                        BaseMainActivity.this.onCreate(null);
                        return;
                }
            } catch (Exception unused) {
            }
        }
    };
    private SeekBar.OnSeekBarChangeListener horiSeekBarListener = new SeekBar.OnSeekBarChangeListener() { // from class: com.android.blerc.BaseMainActivity.23
        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            switch (seekBar.getId()) {
                case R.id.accelerator_dr /* 2131230746 */:
                    if (z) {
                        Log.e("CarMi", "accelerator_dr :  " + i);
                        SharedPreferencesUtils.setParam(BaseMainActivity.this, "accelerator_dr", Integer.valueOf(i));
                        break;
                    }
                    break;
                case R.id.horiSeekBar /* 2131230928 */:
                    if (i <= 1050) {
                        if (i < 950) {
                            BaseMainActivity.this.around_left_img.setVisibility(0);
                            BaseMainActivity.this.around_right_img.setVisibility(8);
                            BaseMainActivity.this.around_left.setVisibility(0);
                            BaseMainActivity.this.around_right.setVisibility(8);
                            break;
                        } else {
                            BaseMainActivity.this.around_left_img.setVisibility(8);
                            BaseMainActivity.this.around_right_img.setVisibility(8);
                            BaseMainActivity.this.around_left.setVisibility(8);
                            BaseMainActivity.this.around_right.setVisibility(8);
                            break;
                        }
                    } else {
                        BaseMainActivity.this.around_left_img.setVisibility(8);
                        BaseMainActivity.this.around_right_img.setVisibility(0);
                        BaseMainActivity.this.around_left.setVisibility(8);
                        BaseMainActivity.this.around_right.setVisibility(0);
                        break;
                    }
                case R.id.horiSeekBar1 /* 2131230929 */:
                    if (i <= 1050) {
                        if (i < 950) {
                            BaseMainActivity.this.around_left_img1.setVisibility(0);
                            BaseMainActivity.this.around_right_img1.setVisibility(8);
                            BaseMainActivity.this.around_left1.setVisibility(0);
                            BaseMainActivity.this.around_right1.setVisibility(8);
                            break;
                        } else {
                            BaseMainActivity.this.around_left_img1.setVisibility(8);
                            BaseMainActivity.this.around_right_img1.setVisibility(8);
                            BaseMainActivity.this.around_left1.setVisibility(8);
                            BaseMainActivity.this.around_right1.setVisibility(8);
                            break;
                        }
                    } else {
                        BaseMainActivity.this.around_left_img1.setVisibility(8);
                        BaseMainActivity.this.around_right_img1.setVisibility(0);
                        BaseMainActivity.this.around_left1.setVisibility(8);
                        BaseMainActivity.this.around_right1.setVisibility(0);
                        break;
                    }
                case R.id.turn_dr /* 2131231064 */:
                    SharedPreferencesUtils.setParam(BaseMainActivity.this, "turn_dr", Integer.valueOf(i));
                    break;
                case R.id.turn_middle /* 2131231067 */:
                    SharedPreferencesUtils.setParam(BaseMainActivity.this, "turn_middle", Integer.valueOf(i));
                    break;
            }
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStopTrackingTouch(SeekBar seekBar) {
            switch (seekBar.getId()) {
                case R.id.horiSeekBar /* 2131230928 */:
                    BaseMainActivity.this.around_left_img.setVisibility(8);
                    BaseMainActivity.this.around_right_img.setVisibility(8);
                    BaseMainActivity.this.around_left.setVisibility(8);
                    BaseMainActivity.this.around_right.setVisibility(8);
                    BaseMainActivity.this.horiSeekBar.setProgress(1000);
                    break;
                case R.id.horiSeekBar1 /* 2131230929 */:
                    BaseMainActivity.this.around_left_img1.setVisibility(8);
                    BaseMainActivity.this.around_right_img1.setVisibility(8);
                    BaseMainActivity.this.around_left1.setVisibility(8);
                    BaseMainActivity.this.around_right1.setVisibility(8);
                    BaseMainActivity.this.horiSeekBar1.setProgress(1000);
                    break;
            }
        }
    };
    private VerticalSeekBar.OnSeekBarChangeListener verticalSeekBarListener = new VerticalSeekBar.OnSeekBarChangeListener() { // from class: com.android.blerc.BaseMainActivity.24
        @Override // com.android.blerc.VerticalSeekBar.OnSeekBarChangeListener
        public void onStartTrackingTouch(VerticalSeekBar verticalSeekBar) {
        }

        @Override // com.android.blerc.VerticalSeekBar.OnSeekBarChangeListener
        public void onProgressChanged(VerticalSeekBar verticalSeekBar, int i, boolean z) {
            switch (verticalSeekBar.getId()) {
                case R.id.verticalSeekBar /* 2131231133 */:
                    if (i <= 1050) {
                        if (i < 950) {
                            BaseMainActivity.this.around_up_img.setVisibility(8);
                            BaseMainActivity.this.around_down_img.setVisibility(0);
                            BaseMainActivity.this.around_up.setVisibility(8);
                            BaseMainActivity.this.around_down.setVisibility(0);
                            if (DBConstant.getInstance(BaseMainActivity.this).isSensor()) {
                                BaseMainActivity.this.sensor_left_up.setImageResource(R.mipmap.sensor_up);
                                BaseMainActivity.this.sensor_left_down.setImageResource(R.mipmap.sensor_down_press);
                                break;
                            }
                        } else {
                            BaseMainActivity.this.around_up_img.setVisibility(8);
                            BaseMainActivity.this.around_down_img.setVisibility(8);
                            BaseMainActivity.this.around_up.setVisibility(8);
                            BaseMainActivity.this.around_down.setVisibility(8);
                            if (DBConstant.getInstance(BaseMainActivity.this).isSensor()) {
                                BaseMainActivity.this.sensor_left_up.setImageResource(R.mipmap.sensor_up);
                                BaseMainActivity.this.sensor_left_down.setImageResource(R.mipmap.sensor_down);
                                break;
                            }
                        }
                    } else {
                        BaseMainActivity.this.around_up_img.setVisibility(0);
                        BaseMainActivity.this.around_down_img.setVisibility(8);
                        BaseMainActivity.this.around_up.setVisibility(0);
                        BaseMainActivity.this.around_down.setVisibility(8);
                        if (DBConstant.getInstance(BaseMainActivity.this).isSensor()) {
                            BaseMainActivity.this.sensor_left_up.setImageResource(R.mipmap.sensor_up_press);
                            BaseMainActivity.this.sensor_left_down.setImageResource(R.mipmap.sensor_down);
                            break;
                        }
                    }
                    break;
                case R.id.verticalSeekBar1 /* 2131231134 */:
                    if (i <= 1050) {
                        if (i < 950) {
                            BaseMainActivity.this.around_up_img1.setVisibility(8);
                            BaseMainActivity.this.around_down_img1.setVisibility(0);
                            BaseMainActivity.this.around_up1.setVisibility(8);
                            BaseMainActivity.this.around_down1.setVisibility(0);
                            if (DBConstant.getInstance(BaseMainActivity.this).isSensor()) {
                                BaseMainActivity.this.sensor_right_up.setImageResource(R.mipmap.sensor_up);
                                BaseMainActivity.this.sensor_right_down.setImageResource(R.mipmap.sensor_down_press);
                                break;
                            }
                        } else {
                            BaseMainActivity.this.around_up_img1.setVisibility(8);
                            BaseMainActivity.this.around_down_img1.setVisibility(8);
                            BaseMainActivity.this.around_up1.setVisibility(8);
                            BaseMainActivity.this.around_down1.setVisibility(8);
                            if (DBConstant.getInstance(BaseMainActivity.this).isSensor()) {
                                BaseMainActivity.this.sensor_right_up.setImageResource(R.mipmap.sensor_up);
                                BaseMainActivity.this.sensor_right_down.setImageResource(R.mipmap.sensor_down);
                                break;
                            }
                        }
                    } else {
                        BaseMainActivity.this.around_up_img1.setVisibility(0);
                        BaseMainActivity.this.around_down_img1.setVisibility(8);
                        BaseMainActivity.this.around_up1.setVisibility(0);
                        BaseMainActivity.this.around_down1.setVisibility(8);
                        if (DBConstant.getInstance(BaseMainActivity.this).isSensor()) {
                            BaseMainActivity.this.sensor_right_up.setImageResource(R.mipmap.sensor_up_press);
                            BaseMainActivity.this.sensor_right_down.setImageResource(R.mipmap.sensor_down);
                            break;
                        }
                    }
                    break;
            }
        }

        @Override // com.android.blerc.VerticalSeekBar.OnSeekBarChangeListener
        public void onStopTrackingTouch(VerticalSeekBar verticalSeekBar) {
            switch (verticalSeekBar.getId()) {
                case R.id.verticalSeekBar /* 2131231133 */:
                    BaseMainActivity.this.verticalSeekBar.setProgress(1000);
                    BaseMainActivity.this.around_up_img.setVisibility(8);
                    BaseMainActivity.this.around_down_img.setVisibility(8);
                    BaseMainActivity.this.around_up.setVisibility(8);
                    BaseMainActivity.this.around_down.setVisibility(8);
                    break;
                case R.id.verticalSeekBar1 /* 2131231134 */:
                    BaseMainActivity.this.verticalSeekBar1.setProgress(1000);
                    BaseMainActivity.this.around_up_img1.setVisibility(8);
                    BaseMainActivity.this.around_down_img1.setVisibility(8);
                    BaseMainActivity.this.around_up1.setVisibility(8);
                    BaseMainActivity.this.around_down1.setVisibility(8);
                    break;
            }
        }
    };
    private int hor_value = 1000;
    private int ver_value = 1000;
    private int hor_turn_middle_value = 45;
    private int hor_turn_dr_value = 100;
    private int hor_accelerator_dr_value = 100;
    private ScanCallback periodScanCallback = new ScanCallback(new IScanCallback() { // from class: com.android.blerc.BaseMainActivity.27
        @Override // com.android.blerc.scan.IScanCallback
        public void onDeviceFound(BluetoothLeDevice bluetoothLeDevice) {
            if (bluetoothLeDevice.getName() == null || !TextUtils.equals(DBConstant.getInstance(BaseMainActivity.this).getAddress(), bluetoothLeDevice.getAddress()) || BleDeviceManager.getInstance().isBleConnectState()) {
                return;
            }
            ViseBle.getInstance().stopScan(BaseMainActivity.this.periodScanCallback);
            BleConfig.getInstance().setMac(bluetoothLeDevice.getAddress());
            ViseBle.getInstance().connectBleMac(DBConstant.getInstance(BaseMainActivity.this).getAddress());
        }

        @Override // com.android.blerc.scan.IScanCallback
        public void onScanFinish(BluetoothLeDeviceStore bluetoothLeDeviceStore) {
            Log.d("CarMi", "scan finish " + bluetoothLeDeviceStore);
        }

        @Override // com.android.blerc.scan.IScanCallback
        public void onScanTimeout() {
            Log.d("CarMi", "scan timeout");
        }
    });
    private float gx = 0.0f;
    private float gy = 0.0f;
    private float gz = 0.0f;
    private float[] deltaRotationVector = new float[4];
    private float timestamp = 0.0f;
    private float[] accMagOrientation = new float[3];
    private boolean initState = true;
    private float[] gyroMatrix = {1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f};
    private float[] gyroOrientation = {0.0f, 0.0f, 0.0f};
    private float[] gyro = new float[3];
    private float[] magnet = new float[3];
    private float[] accel = new float[3];
    private float[] rotationMatrix = new float[9];
    private SensorEventListener mSensorEventListener = new SensorEventListener() { // from class: com.android.blerc.BaseMainActivity.28
        @Override // android.hardware.SensorEventListener
        public void onAccuracyChanged(Sensor sensor, int i) {
        }

        @Override // android.hardware.SensorEventListener
        public void onSensorChanged(SensorEvent sensorEvent) {
            int type = sensorEvent.sensor.getType();
            if (type == 1) {
                System.arraycopy(sensorEvent.values, 0, BaseMainActivity.this.accel, 0, 3);
                BaseMainActivity.this.calculateAccMagOrientation();
                return;
            }
            if (type != 2) {
                if (type == 4 && BaseMainActivity.this.accMagOrientation != null) {
                    if (BaseMainActivity.this.initState) {
                        BaseMainActivity baseMainActivity = BaseMainActivity.this;
                        float[] rotationMatrixFromOrientation = baseMainActivity.getRotationMatrixFromOrientation(baseMainActivity.accMagOrientation);
                        SensorManager.getOrientation(rotationMatrixFromOrientation, new float[3]);
                        BaseMainActivity baseMainActivity2 = BaseMainActivity.this;
                        baseMainActivity2.gyroMatrix = baseMainActivity2.matrixMultiplication(baseMainActivity2.gyroMatrix, rotationMatrixFromOrientation);
                        BaseMainActivity.this.initState = false;
                    }
                    float[] fArr = new float[4];
                    if (BaseMainActivity.this.timestamp != 0.0f) {
                        float f = (sensorEvent.timestamp - BaseMainActivity.this.timestamp) * 1.0E-9f;
                        System.arraycopy(sensorEvent.values, 0, BaseMainActivity.this.gyro, 0, 3);
                        BaseMainActivity baseMainActivity3 = BaseMainActivity.this;
                        baseMainActivity3.getRotationVectorFromGyro(baseMainActivity3.gyro, fArr, f / 2.0f);
                    }
                    BaseMainActivity.this.timestamp = sensorEvent.timestamp;
                    float[] fArr2 = new float[9];
                    SensorManager.getRotationMatrixFromVector(fArr2, fArr);
                    BaseMainActivity baseMainActivity4 = BaseMainActivity.this;
                    baseMainActivity4.gyroMatrix = baseMainActivity4.matrixMultiplication(baseMainActivity4.gyroMatrix, fArr2);
                    SensorManager.getOrientation(BaseMainActivity.this.gyroMatrix, BaseMainActivity.this.gyroOrientation);
                    BaseMainActivity baseMainActivity5 = BaseMainActivity.this;
                    double d = baseMainActivity5.gyroOrientation[1] * 180.0f;
                    Double.isNaN(d);
                    baseMainActivity5.gx = (float) (d / 3.141592653589793d);
                    Log.d("CarMi", "anglex------------>" + BaseMainActivity.this.gx);
                    return;
                }
                return;
            }
            System.arraycopy(sensorEvent.values, 0, BaseMainActivity.this.magnet, 0, 3);
        }
    };
    private float[] fusedOrientation = new float[3];

    public abstract void jumpSetting();

    @Override // com.android.blerc.BarBaseActivity, com.android.blerc.BaseActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initSeekBarView();
        initView();

        // --- [新增] --- 初始化并设置手柄支持 ---
        setupGamepadSupport();
        // --- [新增结束] ---

        this.agreementDialog = new AgreementDialog(this, R.style.dialog, new AgreementDialog.OnCloseListener() { // from class: com.android.blerc.BaseMainActivity.1
            @Override // com.android.blerc.dialog.AgreementDialog.OnCloseListener
            public void onClick(Dialog dialog, boolean z) {
                if (z) {
                    SharedPreferencesUtils.setParam(BaseMainActivity.this, "agreement", true);
                    BaseMainActivity.this.showConnectDialog();
                } else {
                    BaseMainActivity.this.connectDialog.show();
                }
                dialog.dismiss();
            }
        });
        GuideDialog guideDialog = new GuideDialog(this);
        this.guideDialog = guideDialog;
        guideDialog.setCancelable(true);
        this.guideDialog.setCanceledOnTouchOutside(false);
        this.guideDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.blerc.BaseMainActivity.2
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                if (((Boolean) SharedPreferencesUtils.getParam(BaseMainActivity.this, "agreement", false)).booleanValue()) {
                    BaseMainActivity.this.showConnectDialog();
                } else {
                    BaseMainActivity.this.agreementDialog.show();
                }
            }
        });
        ConnectDialog connectDialog = new ConnectDialog(this);
        this.connectDialog = connectDialog;
        connectDialog.setCancelable(false);
        this.connectDialog.setCanceledOnTouchOutside(false);
        this.connectDialog.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: com.android.blerc.BaseMainActivity.3
            @Override // android.content.DialogInterface.OnKeyListener
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i != 4 || keyEvent.getAction() != 1) {
                    return false;
                }
                BaseMainActivity.this.showQuitDailog();
                BaseMainActivity.this.connectDialog.dismiss();
                return true;
            }
        });
        this.mListViewDialog = new ListViewDialog(this);
        this.mVersionDialog = new VersionDialog(this);
        FirmwareUpdateDialog firmwareUpdateDialog = new FirmwareUpdateDialog(this);
        this.firmwareUpdateDialog = firmwareUpdateDialog;
        firmwareUpdateDialog.setCanceledOnTouchOutside(false);
        this.firmwareUpdateDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.blerc.BaseMainActivity.4
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                BaseMainActivity.this.showConnectDialog();
            }
        });
        this.mListViewDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.blerc.BaseMainActivity.5
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                BaseMainActivity.this.startSendThread();
                BaseMainActivity.this.showConnectDialog();
            }
        });
        this.switch_turn.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.android.blerc.BaseMainActivity.6
            @Override // com.android.blerc.SwitchButton.OnCheckedChangeListener
            public void onCheckedChanged(SwitchButton switchButton, boolean z) {
            }
        });
        this.switch_accelerator.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.android.blerc.BaseMainActivity.7
            @Override // com.android.blerc.SwitchButton.OnCheckedChangeListener
            public void onCheckedChanged(SwitchButton switchButton, boolean z) {
            }
        });
        this.mSensorManager = (SensorManager) getSystemService("sensor");
        this.fuseTimer.scheduleAtFixedRate(new calculateFusedOrientationTask(), 1000L, 30L);
        this.savedBattery = Integer.valueOf(Integer.parseInt((String) SharedPreferencesUtils.getParam(this, "battery", "-1")));
        this.savedTimeStamp = Long.valueOf(Long.parseLong((String) SharedPreferencesUtils.getParam(this, "battery-time", "-1")));
    }

    @Override // com.android.blerc.BaseActivity, android.app.Activity
    protected void onResume() throws NumberFormatException {
        //disable the "recreate when locale changed" behavior for main activity since it has event handling for language switching
        this.currentActivityLocale = null;
        super.onResume();
        Log.e("CarMi", "onResume");
        if (DBConstant.getInstance(this).isSensor()) {
            SensorManager sensorManager = this.mSensorManager;
            sensorManager.registerListener(this.mSensorEventListener, sensorManager.getDefaultSensor(1), 1);
            SensorManager sensorManager2 = this.mSensorManager;
            sensorManager2.registerListener(this.mSensorEventListener, sensorManager2.getDefaultSensor(4), 1);
            SensorManager sensorManager3 = this.mSensorManager;
            sensorManager3.registerListener(this.mSensorEventListener, sensorManager3.getDefaultSensor(2), 1);
            initSensor();
        }
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        if (BleDeviceManager.getInstance().isBleConnectState()) {
            if (getIntent().getBooleanExtra("splash", false)) {
                this.battery = getIntent().getIntExtra("battery", -1);
                getIntent().removeExtra("splash");
                sendMsg(11, "", 0L);
            } else {
                this.tx_turn_middle.setText(R.string.turn_middle);
                this.tx_turn_dr.setText(R.string.turn_dr);
                this.tx_switch_turn.setText(R.string.turn_reverse);
                this.tx_switch_accelerator.setText(R.string.accelerator_reverse);
            }
            startSendThread();
        } else {
            if (((Boolean) SharedPreferencesUtils.getParam(this, Integer.parseInt(DBConstant.getInstance(this).getAppVersion().replaceAll("\\.", "")) + "GuideShow", false)).booleanValue()) {
                AgreementDialog agreementDialog = this.agreementDialog;
                if (agreementDialog == null || !agreementDialog.isShowing()) {
                    showConnectDialog();
                }
            } else if (!this.guideDialog.isShowing()) {
                this.guideDialog.show();
            }
        }
        if (DBConstant.getInstance(this).isLow_speed()) {
            this.low_speed_imgbtn.setImageResource(R.mipmap.low_speed_press);
        } else {
            this.low_speed_imgbtn.setImageResource(R.mipmap.low_speed);
        }
        if (DBConstant.getInstance(this).isRightHand()) {
            this.hor_left.setVisibility(8);
            this.ver_left.setVisibility(0);
            this.ver_right.setVisibility(8);
            this.hor_right.setVisibility(0);
            this.horiSeekBar1_layout.setVisibility(4);
            this.verticalSeekBar_layout.setVisibility(0);
            this.horiSeekBar_layout.setVisibility(0);
            this.verticalSeekBar1_layout.setVisibility(4);
            this.left_view.setTag("accelerate");
            this.right_view.setTag("direction");
            if (DBConstant.getInstance(this).isSensor()) {
                this.left_view.setVisibility(0);
                this.ver_left.setVisibility(8);
                this.hor_left.setVisibility(8);
                this.hor_sensor_left.setVisibility(0);
                this.horiSeekBar1_layout.setVisibility(4);
                this.verticalSeekBar_layout.setVisibility(0);
                this.left_sensor_layout.setVisibility(8);
                this.right_view.setVisibility(8);
                this.right_sensor_layout.setVisibility(0);
            }
        } else {
            this.ver_left.setVisibility(8);
            this.hor_left.setVisibility(0);
            this.hor_right.setVisibility(8);
            this.ver_right.setVisibility(0);
            this.horiSeekBar1_layout.setVisibility(0);
            this.verticalSeekBar_layout.setVisibility(4);
            this.horiSeekBar_layout.setVisibility(4);
            this.verticalSeekBar1_layout.setVisibility(0);
            this.left_view.setTag("direction");
            this.right_view.setTag("accelerate");
            if (DBConstant.getInstance(this).isSensor()) {
                this.left_view.setVisibility(8);
                this.left_sensor_layout.setVisibility(0);
                this.right_view.setVisibility(0);
                this.ver_right.setVisibility(8);
                this.hor_right.setVisibility(8);
                this.ver_sensor_right.setVisibility(0);
                this.horiSeekBar_layout.setVisibility(4);
                this.verticalSeekBar1_layout.setVisibility(0);
                this.right_sensor_layout.setVisibility(8);
            }
        }
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.menu_set_a);
        linearLayout.measure(0, 0);
        Integer numValueOf = Integer.valueOf(linearLayout.getMeasuredWidth());
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.menu_set);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) linearLayout2.getLayoutParams();
        layoutParams.width = numValueOf.intValue();
        linearLayout2.setLayoutParams(layoutParams);
        this.drag_view.refreshLayout();
        this.drag_view1.refreshLayout();
    }

    // --- [新增] --- 所有与手柄支持相关的新方法 ---

    /**
     * 设置手柄支持，包括初始化InputManager和注册监听器。
     */
    private void setupGamepadSupport() {
        mInputManager = (InputManager) getSystemService(Context.INPUT_SERVICE);
        checkForGamepad(); // 检查是否已有手柄连接

        // 注册一个监听器，以便在手柄连接或断开时收到通知
        mInputManager.registerInputDeviceListener(new InputManager.InputDeviceListener() {
            @Override
            public void onInputDeviceAdded(int deviceId) {
                InputDevice dev = InputDevice.getDevice(deviceId);
                if (isGamepad(dev)) {
                    Log.d("Gamepad", "手柄已连接: " + dev.getName());
                    mGamepadDevice = dev;
                    isGamepadControlActive = true;
                    // 提示用户手柄已连接。建议将 "手柄已连接" 等字符串定义在 strings.xml 中
                    Toast.makeText(BaseMainActivity.this, "手柄已连接: " + dev.getName(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onInputDeviceRemoved(int deviceId) {
                if (mGamepadDevice != null && mGamepadDevice.getId() == deviceId) {
                    Log.d("Gamepad", "手柄已断开: " + mGamepadDevice.getName());
                    Toast.makeText(BaseMainActivity.this, "手柄已断开: " + mGamepadDevice.getName(), Toast.LENGTH_SHORT).show();
                    mGamepadDevice = null;
                    isGamepadControlActive = false;
                    // 将控制值重置到中心位置
                    hor_value = 1000;
                    ver_value = 1000;
                }
            }

            @Override
            public void onInputDeviceChanged(int deviceId) {
                // 此处暂不使用
            }
        }, mHandler); // 使用现有的Handler来处理UI线程的更新
    }

    /**
     * 在App启动时检查是否已经有手柄连接。
     */
    private void checkForGamepad() {
        int[] deviceIds = mInputManager.getInputDeviceIds();
        for (int deviceId : deviceIds) {
            InputDevice dev = InputDevice.getDevice(deviceId);
            if (isGamepad(dev)) {
                mGamepadDevice = dev;
                isGamepadControlActive = true;
                Log.d("Gamepad", "发现已连接的手柄: " + dev.getName());
                Toast.makeText(this, "手柄已连接: " + dev.getName(), Toast.LENGTH_SHORT).show();
                break; // 找到一个即可
            }
        }
    }

    /**
     * 判断一个输入设备是否是游戏手柄。
     * @param device 要检查的输入设备
     * @return 如果是手柄则返回 true，否则返回 false
     */
    private boolean isGamepad(InputDevice device) {
        if (device == null) return false;
        int sources = device.getSources();
        // 检查设备源是否包含 SOURCE_JOYSTICK 或 SOURCE_GAMEPAD
        return ((sources & InputDevice.SOURCE_JOYSTICK) == InputDevice.SOURCE_JOYSTICK) ||
                ((sources & InputDevice.SOURCE_GAMEPAD) == InputDevice.SOURCE_GAMEPAD);
    }

    /**
     * 获取带有死区处理的摇杆轴值。
     * @param event 运动事件
     * @param device 输入设备
     * @param axis 要获取的轴
     * @return 处理死区后的轴值，范围通常是 -1.0 到 1.0
     */
    private float getCenteredAxis(MotionEvent event, InputDevice device, int axis) {
        final InputDevice.MotionRange range = device.getMotionRange(axis, event.getSource());
        if (range != null) {
            final float flat = range.getFlat(); // 获取死区范围
            final float value = event.getAxisValue(axis);
            // 如果轴值大于死区，则返回该值，否则返回0
            if (Math.abs(value) > flat) {
                return value;
            }
        }
        return 0;
    }

    /**
     * 重写此方法来处理模拟输入事件，如摇杆和扳机。
     */
    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        if (!isGamepadControlActive || mGamepadDevice == null) {
            return super.onGenericMotionEvent(event);
        }

        // 确认事件来自摇杆并且是移动事件
        if ((event.getSource() & InputDevice.SOURCE_JOYSTICK) == InputDevice.SOURCE_JOYSTICK &&
                event.getAction() == MotionEvent.ACTION_MOVE) {

            // --- 获取各摇杆和扳机的输入值 ---
            // 左摇杆 (Left Stick)
            float leftStickX = getCenteredAxis(event, mGamepadDevice, MotionEvent.AXIS_X);

            // 右摇杆 (Right Stick) - Y轴通常是 AXIS_RZ
            float rightStickY = -getCenteredAxis(event, mGamepadDevice, MotionEvent.AXIS_RZ); // Y轴反向，使向上推为正

            // 扳机 (Triggers)
            float lTrigger = getCenteredAxis(event, mGamepadDevice, MotionEvent.AXIS_LTRIGGER); // 左扳机, 0.0 到 1.0
            float rTrigger = getCenteredAxis(event, mGamepadDevice, MotionEvent.AXIS_RTRIGGER); // 右扳机, 0.0 到 1.0

            // 方向键 (D-Pad) 也可能作为轴报告
            float dpadX = getCenteredAxis(event, mGamepadDevice, MotionEvent.AXIS_HAT_X);

            // --- 将手柄输入映射到车辆控制 ---
            // 转向控制：优先使用D-Pad，其次是左摇杆
            float steerInput = (Math.abs(dpadX) > 0.1f) ? dpadX : leftStickX;

            // 油门控制：优先使用扳机，其次是右摇杆
            float throttleInput;
            if (rTrigger > 0.1f || lTrigger > 0.1f) {
                throttleInput = rTrigger - lTrigger; // 合成范围 -1.0 (反向) 到 1.0 (正向)
            } else {
                throttleInput = rightStickY;
            }

            // --- 将手柄值 (-1.0 to 1.0) 转换为车辆控制值 (0 to 2000) ---
            // hor_value 控制转向
            hor_value = (int) (1000 + (steerInput * 1000));
            // ver_value 控制油门
            ver_value = (int) (1000 + (throttleInput * 1000));

            // 限制值的范围，防止溢出
            hor_value = Math.max(0, Math.min(2000, hor_value));
            ver_value = Math.max(0, Math.min(2000, ver_value));

            // Log.d("Gamepad", "hor_value: " + hor_value + ", ver_value: " + ver_value);

            return true; // 事件已处理
        }

        return super.onGenericMotionEvent(event);
    }

    /**
     * 重写此方法来处理按键事件。
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!isGamepadControlActive) {
            return super.onKeyDown(keyCode, event);
        }

        boolean handled = false;
        // 确认事件来自手柄，并且是第一次按下（避免长按重复触发）
        if (((event.getSource() & InputDevice.SOURCE_GAMEPAD) == InputDevice.SOURCE_GAMEPAD) &&
                (event.getRepeatCount() == 0)) {

            Log.d("Gamepad", "按键按下: " + KeyEvent.keyCodeToString(keyCode));

            switch (keyCode) {
                case KeyEvent.KEYCODE_BUTTON_A: // 通常是手柄上的'A'键
                    // 示例：触发“低速模式”按钮
                    if (low_speed_imgbtn != null) low_speed_imgbtn.performClick();
                    handled = true;
                    break;
                case KeyEvent.KEYCODE_BUTTON_B: // 通常是手柄上的'B'键
                    // 示例：触发“刹车”按钮
                    if (brake_imgbtn != null) brake_imgbtn.performClick();
                    handled = true;
                    break;
                case KeyEvent.KEYCODE_BUTTON_Y: // 通常是手柄上的'Y'键
                    // 示例：触发“切换陀螺仪模式”按钮
                    if (switch_mode_imgbtn != null) switch_mode_imgbtn.performClick();
                    handled = true;
                    break;
                case KeyEvent.KEYCODE_BUTTON_X: // 通常是手柄上的'X'键
                    Toast.makeText(this, "Button X pressed", Toast.LENGTH_SHORT).show();
                    handled = true;
                    break;
                case KeyEvent.KEYCODE_BUTTON_L1: // 左肩键 LB
                    Toast.makeText(this, "Button LB pressed", Toast.LENGTH_SHORT).show();
                    handled = true;
                    break;
                case KeyEvent.KEYCODE_BUTTON_R1: // 右肩键 RB
                    Toast.makeText(this, "Button RB pressed", Toast.LENGTH_SHORT).show();
                    handled = true;
                    break;
                // 方向键如果被作为按键事件处理，放这里
                case KeyEvent.KEYCODE_DPAD_UP:
                case KeyEvent.KEYCODE_DPAD_DOWN:
                case KeyEvent.KEYCODE_DPAD_LEFT:
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    // 大部分手柄会通过 onGenericMotionEvent 处理方向键，但作为备用
                    handled = true;
                    break;
            }
        }
        return handled || super.onKeyDown(keyCode, event);
    }

    // --- [新增结束] ---

    public void showConnectDialog() {
        if (BleDeviceManager.getInstance().isBleConnectState() || this.mListViewDialog.isShowing()) {
            return;
        }
        quitdialogDismiss();
        requestBleDialogDismiss();
        if (this.connectDialog.isShowing()) {
            return;
        }
        this.connectDialog.show();
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
        this.mSensorManager.unregisterListener(this.mSensorEventListener);
        this.fuseFlag = false;
    }

    @Override // android.app.Activity
    protected void onStop() {
        super.onStop();
        Log.e("CarMi", "onStop");
        stopSendThread();
        this.drag_view.refreshLayout();
        this.drag_view1.refreshLayout();
        this.mSensorManager.unregisterListener(this.mSensorEventListener);
    }

    @Override // android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    public Resources getResources() {
        AutoSizeCompat.autoConvertDensityOfGlobal(super.getResources());
        return super.getResources();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        Log.e("CarMi", "onDestroy");
        EventBus.getDefault().unregister(this);
        BleDeviceManager.getInstance().setBleConnectState(false);
        ViseBle.getInstance().disConnect();
        stopSendThread();
        Log.d("CarMi", "turn_middle :  " + DBConstant.getInstance(this).getTurn_middle());
        Log.d("CarMi", "turn_dr :  " + DBConstant.getInstance(this).getTurn_dr());
        Log.d("CarMi", "accelerator_dr :  " + DBConstant.getInstance(this).getAccelerator_dr());
        this.mSensorManager.unregisterListener(this.mSensorEventListener);
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        Log.e("CarMi", "onBackPressed");
        showQuitDailog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showQuitDailog() {
        CommonDialog positiveButton = new CommonDialog(this, R.style.dialog, getString(R.string.confirm_exit), new CommonDialog.OnCloseListener() { // from class: com.android.blerc.BaseMainActivity.8
            @Override // com.android.blerc.dialog.CommonDialog.OnCloseListener
            public void onClick(Dialog dialog, boolean z) {
                if (z) {
                    BaseMainActivity.super.onBackPressed();
                    dialog.dismiss();
                } else {
                    BaseMainActivity.this.showConnectDialog();
                    dialog.dismiss();
                }
            }
        }).setNegativeButton(getString(R.string.no)).setPositiveButton(getString(R.string.yes));
        this.quitDailog = positiveButton;
        positiveButton.show();
        this.quitDailog.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: com.android.blerc.BaseMainActivity.9
            @Override // android.content.DialogInterface.OnKeyListener
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i != 4 || keyEvent.getAction() != 1) {
                    return false;
                }
                BaseMainActivity.super.onBackPressed();
                dialogInterface.dismiss();
                return true;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setBatteryProgress() {
        if (BleDeviceManager.getInstance().isBleConnectState()) {
            this.tx_connect_title.setText(getString(R.string.connected));
            this.tx_connect_tip.setVisibility(8);
            this.iv_battery.setVisibility(0);
            int iIntValue = this.battery;
            if (this.isSameDevice && this.savedTimeStamp.longValue() > 0 && System.currentTimeMillis() - this.savedTimeStamp.longValue() < 600000 && this.savedBattery.intValue() != -1) {
                iIntValue = this.savedBattery.intValue();
            }
            if (iIntValue >= 10) {
                this.main_layout.setBackgroundResource(R.mipmap.bg);
            } else if (iIntValue >= 0) {
                this.main_layout.setBackgroundResource(R.drawable.low_battery);
            }
            if (iIntValue >= 42) {
                this.iv_battery.setImageResource(R.mipmap.ic_power_high);
            } else if (iIntValue >= 25) {
                this.iv_battery.setImageResource(R.mipmap.ic_power_medium);
            } else {
                this.iv_battery.setImageResource(R.mipmap.ic_power_low);
            }
        }
    }

    public void setDfu(boolean z) {
        this.isDfu = z;
    }

    private void sendMsg(int i, String str, long j) {
        Message message = new Message();
        message.what = i;
        message.obj = str;
        if (j == 0) {
            this.mHandler.sendMessage(message);
        } else {
            this.mHandler.sendMessageDelayed(message, j);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RxEvent rxEvent) throws NumberFormatException {
        String msg = rxEvent.getMsg();
        Log.d("CarMi", "onEventMainThread收到了消息：" + msg);
        if (msg.equals("bleconnect")) {
            sendMsg(1, "", 1000L);
            return;
        }
        if (msg.equals("bledisconnect")) {
            sendMsg(2, "", 0L);
            return;
        }
        if (msg.equals("searchDev")) {
            sendMsg(10, "", 0L);
            return;
        }
        if (msg.equals("languageSwitch")) {
            sendMsg(15, "", 0L);
            return;
        }
        if (msg.equals("dismiss")) {
            sendMsg(3, "", 0L);
            return;
        }
        if (msg.startsWith("a0") || msg.startsWith("a1")) {
            String strSubstring = msg.substring(2, msg.length());
            if (strSubstring.startsWith("ff")) {
                return;
            }
            byte[] bArrHexStringToBytes = HexUtil.hexStringToBytes(strSubstring);
            if (HexUtil.byteToInt(DataUtil.getInstance().subBytes(bArrHexStringToBytes, 0, 1)) == 1) {
                byte[] bArrSubBytes = DataUtil.getInstance().subBytes(bArrHexStringToBytes, 1, bArrHexStringToBytes.length - 1);
                Log.d("CarMi", "dragbrake :  " + HexUtil.byteToInt(bArrSubBytes));
                ParamRequestInfo.getInstance().setDragBrake(HexUtil.byteToInt(bArrSubBytes));
            }
            if (msg.startsWith("a0")) {
                this.mHandler.sendEmptyMessage(4);
            }
            if (msg.startsWith("a1")) {
                this.mHandler.removeMessages(14);
                this.mHandler.sendEmptyMessage(13);
                return;
            }
            return;
        }
        if (msg.startsWith("90")) {
            String strSubstring2 = msg.substring(2, msg.length());
            if (strSubstring2.startsWith("ff")) {
                return;
            }
            byte[] bArrHexStringToBytes2 = HexUtil.hexStringToBytes(strSubstring2);
            if (HexUtil.byteToInt(DataUtil.getInstance().subBytes(bArrHexStringToBytes2, 0, 1)) == 4) {
                byte[] bArrSubBytes2 = DataUtil.getInstance().subBytes(bArrHexStringToBytes2, 1, bArrHexStringToBytes2.length - 1);
                Log.d("CarMi", "FirmwareVersion ::   " + HexUtil.encodeHexStr(bArrSubBytes2));
                Log.d("CarMi", "FirmwareVersion ::   " + HexUtil.encodeHexStr(DataUtil.getInstance().subBytes(bArrSubBytes2, 0, 1)));
                int iByteToInt = HexUtil.byteToInt(DataUtil.getInstance().subBytes(bArrSubBytes2, 0, 1));
                int iByteToInt2 = HexUtil.byteToInt(DataUtil.getInstance().subBytes(bArrSubBytes2, 1, 1));
                int iByteToInt3 = HexUtil.byteToInt(DataUtil.getInstance().subBytes(bArrSubBytes2, 2, 1));
                int iByteToInt4 = HexUtil.byteToInt(DataUtil.getInstance().subBytes(bArrSubBytes2, 3, 2));
                SystemInfo.getInstance().setFirmwareVersion("v" + iByteToInt + "." + iByteToInt2 + "." + iByteToInt3 + "." + iByteToInt4);
                if (Integer.parseInt(Splashbean.getInstance().getFirmware_ver().replaceAll("\\.", "")) <= Integer.parseInt(String.valueOf(iByteToInt) + String.valueOf(iByteToInt2) + String.valueOf(iByteToInt3) + String.valueOf(iByteToInt4)) || this.battery <= 60) {
                    return;
                }
                this.firmwareUpdateDialog.setAddress(BleConfig.getInstance().getMac());
                this.firmwareUpdateDialog.setDeviceName(BleConfig.getInstance().getName());
                this.firmwareUpdateDialog.show();
                return;
            }
            return;
        }
        if (msg.startsWith("battery")) {
            this.battery = Integer.parseInt(msg.split(":")[1]);
            this.mHandler.sendEmptyMessage(9);
            return;
        }
        if (msg.startsWith("ligth")) {
            sendMsg(7, msg.split(":")[1].toString(), 0L);
            return;
        }
        if (msg.equals("jumpToAgreement")) {
            Intent intent = new Intent(this, (Class<?>) ProtocolActivity.class);
            intent.putExtra("type", 1);
            startActivity(intent);
        } else if (msg.equals("jumpToPrivacy")) {
            Intent intent2 = new Intent(this, (Class<?>) ProtocolActivity.class);
            intent2.putExtra("type", 2);
            startActivity(intent2);
        } else if (msg.equals("showNoDeviceTip")) {
            new NoDevicesTipDialog(this, R.style.dialog, new NoDevicesTipDialog.OnCloseListener() { // from class: com.android.blerc.BaseMainActivity.11
                @Override // com.android.blerc.dialog.NoDevicesTipDialog.OnCloseListener
                public void onClick(Dialog dialog) {
                    dialog.dismiss();
                }
            }).show();
        }
    }

    public BitmapDrawable getNewDrawable(Activity activity, int i, int i2, int i3) {
        BitmapDrawable bitmapDrawable = new BitmapDrawable(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(activity.getResources(), i), i2, i3, true));
        bitmapDrawable.setTargetDensity(activity.getResources().getDisplayMetrics());
        return bitmapDrawable;
    }

    private void initSeekBarView() {
        this.verticalSeekBar.setThumb(getNewDrawable(this, R.mipmap.seekbar_press, AutoSizeUtils.dp2px(this, 57.0f), AutoSizeUtils.dp2px(this, 57.0f)));
        this.verticalSeekBar.setOnSeekBarChangeListener(this.verticalSeekBarListener);
        this.verticalSeekBar.setEnabled(true);
        this.verticalSeekBar.setMax(2000);
        this.verticalSeekBar.setProgress(1000);
        this.horiSeekBar.setThumb(getNewDrawable(this, R.mipmap.seekbar_press, AutoSizeUtils.dp2px(this, 57.0f), AutoSizeUtils.dp2px(this, 57.0f)));
        this.horiSeekBar.setOnSeekBarChangeListener(this.horiSeekBarListener);
        this.horiSeekBar.setEnabled(true);
        this.horiSeekBar.setMax(2000);
        this.horiSeekBar.setProgress(1000);
        this.verticalSeekBar1.setThumb(getNewDrawable(this, R.mipmap.seekbar_press, AutoSizeUtils.dp2px(this, 57.0f), AutoSizeUtils.dp2px(this, 57.0f)));
        this.verticalSeekBar1.setOnSeekBarChangeListener(this.verticalSeekBarListener);
        this.verticalSeekBar1.setEnabled(true);
        this.verticalSeekBar1.setMax(2000);
        this.verticalSeekBar1.setProgress(1000);
        this.horiSeekBar1.setThumb(getNewDrawable(this, R.mipmap.seekbar_press, AutoSizeUtils.dp2px(this, 57.0f), AutoSizeUtils.dp2px(this, 57.0f)));
        this.horiSeekBar1.setOnSeekBarChangeListener(this.horiSeekBarListener);
        this.horiSeekBar1.setEnabled(true);
        this.horiSeekBar1.setMax(2000);
        this.horiSeekBar1.setProgress(1000);
        this.turn_middle.setThumb(getNewDrawable(this, R.mipmap.seekbar_small, AutoSizeUtils.dp2px(this, 18.0f), AutoSizeUtils.dp2px(this, 18.0f)));
        this.turn_middle.setEnabled(true);
        this.turn_middle.setMax(90);
        this.turn_dr.setThumb(getNewDrawable(this, R.mipmap.seekbar_small, AutoSizeUtils.dp2px(this, 18.0f), AutoSizeUtils.dp2px(this, 18.0f)));
        this.turn_dr.setEnabled(true);
        this.turn_dr.setMax(100);
        this.turn_middle.setProgress(DBConstant.getInstance(this).getTurn_middle());
        this.turn_dr.setProgress(DBConstant.getInstance(this).getTurn_dr());
        this.turn_dr.setOnSeekBarChangeListener(this.horiSeekBarListener);
        this.turn_middle.setOnSeekBarChangeListener(this.horiSeekBarListener);
    }

    private void initView() {
        if (DBConstant.getInstance(this).getLanguage().equals("zh")) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.MarginLayoutParams(this.centerbtn.getLayoutParams()));
            layoutParams.width = AutoSizeUtils.dp2px(this, 150.0f);
            this.centerbtn.setLayoutParams(layoutParams);
            this.centerbtn.setBackgroundResource(R.mipmap.blue_button);
        } else {
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(new ViewGroup.MarginLayoutParams(this.centerbtn.getLayoutParams()));
            layoutParams2.width = AutoSizeUtils.dp2px(this, 236.0f);
            this.centerbtn.setLayoutParams(layoutParams2);
            this.centerbtn.setBackgroundResource(R.mipmap.blue_button_eng);
        }
        this.drag_view.setView(this.left_view, this.horiSeekBar1, this.verticalSeekBar, this.horiSeekBar1_layout);
        this.drag_view1.setView(this.right_view, this.horiSeekBar, this.verticalSeekBar1, this.horiSeekBar_layout);
        RxView.clicks(this.switch_mode_imgbtn).throttleFirst(200L, TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.BaseMainActivity.12
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                if (BaseMainActivity.this.getPackageManager().hasSystemFeature("android.hardware.sensor.gyroscope")) {
                    if (!DBConstant.getInstance(BaseMainActivity.this).isSensor()) {
                        BaseMainActivity.this.switch_mode_imgbtn.setImageResource(R.mipmap.switch_mode_select);
                        if (DBConstant.getInstance(BaseMainActivity.this).isRightHand()) {
                            BaseMainActivity.this.left_view.setVisibility(0);
                            BaseMainActivity.this.ver_left.setVisibility(8);
                            BaseMainActivity.this.hor_left.setVisibility(8);
                            BaseMainActivity.this.hor_sensor_left.setVisibility(0);
                            BaseMainActivity.this.horiSeekBar1_layout.setVisibility(4);
                            BaseMainActivity.this.verticalSeekBar_layout.setVisibility(0);
                            BaseMainActivity.this.left_sensor_layout.setVisibility(8);
                            BaseMainActivity.this.right_view.setVisibility(8);
                            BaseMainActivity.this.right_sensor_layout.setVisibility(0);
                        } else {
                            BaseMainActivity.this.left_view.setVisibility(8);
                            BaseMainActivity.this.left_sensor_layout.setVisibility(0);
                            BaseMainActivity.this.right_view.setVisibility(0);
                            BaseMainActivity.this.ver_right.setVisibility(8);
                            BaseMainActivity.this.hor_right.setVisibility(8);
                            BaseMainActivity.this.ver_sensor_right.setVisibility(0);
                            BaseMainActivity.this.horiSeekBar_layout.setVisibility(4);
                            BaseMainActivity.this.verticalSeekBar1_layout.setVisibility(0);
                            BaseMainActivity.this.right_sensor_layout.setVisibility(8);
                        }
                        BaseMainActivity.this.initSensor();
                        BaseMainActivity.this.mSensorManager.registerListener(BaseMainActivity.this.mSensorEventListener, BaseMainActivity.this.mSensorManager.getDefaultSensor(1), 1);
                        BaseMainActivity.this.mSensorManager.registerListener(BaseMainActivity.this.mSensorEventListener, BaseMainActivity.this.mSensorManager.getDefaultSensor(4), 1);
                        BaseMainActivity.this.mSensorManager.registerListener(BaseMainActivity.this.mSensorEventListener, BaseMainActivity.this.mSensorManager.getDefaultSensor(2), 1);
                        DBConstant.getInstance(BaseMainActivity.this).setSensor(true);
                        return;
                    }
                    BaseMainActivity.this.switch_mode_imgbtn.setImageResource(R.mipmap.switch_mode);
                    BaseMainActivity.this.left_view.setVisibility(0);
                    BaseMainActivity.this.hor_sensor_left.setVisibility(8);
                    BaseMainActivity.this.left_sensor_layout.setVisibility(8);
                    BaseMainActivity.this.right_view.setVisibility(0);
                    BaseMainActivity.this.ver_sensor_right.setVisibility(8);
                    BaseMainActivity.this.right_sensor_layout.setVisibility(8);
                    if (DBConstant.getInstance(BaseMainActivity.this).isRightHand()) {
                        BaseMainActivity.this.hor_left.setVisibility(8);
                        BaseMainActivity.this.ver_left.setVisibility(0);
                        BaseMainActivity.this.ver_right.setVisibility(8);
                        BaseMainActivity.this.hor_right.setVisibility(0);
                        BaseMainActivity.this.horiSeekBar1_layout.setVisibility(4);
                        BaseMainActivity.this.verticalSeekBar_layout.setVisibility(0);
                        BaseMainActivity.this.horiSeekBar_layout.setVisibility(0);
                        BaseMainActivity.this.verticalSeekBar1_layout.setVisibility(4);
                        BaseMainActivity.this.left_view.setTag("accelerate");
                        BaseMainActivity.this.right_view.setTag("direction");
                    } else {
                        BaseMainActivity.this.ver_left.setVisibility(8);
                        BaseMainActivity.this.hor_left.setVisibility(0);
                        BaseMainActivity.this.hor_right.setVisibility(8);
                        BaseMainActivity.this.ver_right.setVisibility(0);
                        BaseMainActivity.this.horiSeekBar1_layout.setVisibility(0);
                        BaseMainActivity.this.verticalSeekBar_layout.setVisibility(4);
                        BaseMainActivity.this.horiSeekBar_layout.setVisibility(4);
                        BaseMainActivity.this.verticalSeekBar1_layout.setVisibility(0);
                        BaseMainActivity.this.left_view.setTag("direction");
                        BaseMainActivity.this.right_view.setTag("accelerate");
                    }
                    BaseMainActivity.this.mSensorManager.unregisterListener(BaseMainActivity.this.mSensorEventListener);
                    BaseMainActivity.this.fuseFlag = false;
                    DBConstant.getInstance(BaseMainActivity.this).setSensor(false);
                    return;
                }
                ToastUtils.showShort("手机不支持陀螺仪功能");
            }
        });
        RxView.clicks(this.menu_img).throttleFirst(10L, TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.BaseMainActivity.13
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                if (BaseMainActivity.this.menu_set.getVisibility() == 0) {
                    BaseMainActivity.this.toptitle.setVisibility(0);
                    BaseMainActivity.this.menu_set.setVisibility(8);
                    BaseMainActivity.this.menu_img.setImageResource(R.mipmap.menu);
                    ViewGroup.LayoutParams layoutParams3 = BaseMainActivity.this.drag_height.getLayoutParams();
                    layoutParams3.height = AutoSizeUtils.dp2px(BaseMainActivity.this, 80.0f);
                    BaseMainActivity.this.drag_height.setLayoutParams(layoutParams3);
                    return;
                }
                BaseMainActivity.this.toptitle.setVisibility(8);
                BaseMainActivity.this.menu_set.setVisibility(0);
                BaseMainActivity.this.menu_img.setImageResource(R.mipmap.menu_press);
                ViewGroup.LayoutParams layoutParams4 = BaseMainActivity.this.drag_height.getLayoutParams();
                layoutParams4.height = AutoSizeUtils.dp2px(BaseMainActivity.this, 120.0f);
                BaseMainActivity.this.drag_height.setLayoutParams(layoutParams4);
            }
        });
        RxView.clicks(this.set_close).throttleFirst(100L, TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.BaseMainActivity.14
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                BaseMainActivity.this.toptitle.setVisibility(0);
                BaseMainActivity.this.menu_set.setVisibility(8);
                BaseMainActivity.this.menu_img.setImageResource(R.mipmap.menu);
                ViewGroup.LayoutParams layoutParams3 = BaseMainActivity.this.drag_height.getLayoutParams();
                layoutParams3.height = AutoSizeUtils.dp2px(BaseMainActivity.this, 80.0f);
                BaseMainActivity.this.drag_height.setLayoutParams(layoutParams3);
            }
        });
        RxView.clicks(this.turn_middle_subtract).throttleFirst(0L, TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.BaseMainActivity.15
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                BaseMainActivity.this.turn_middle.setProgress(BaseMainActivity.this.turn_middle.getProgress() - 1);
            }
        });
        RxView.clicks(this.turn_middle_add).throttleFirst(0L, TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.BaseMainActivity.16
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                BaseMainActivity.this.turn_middle.setProgress(BaseMainActivity.this.turn_middle.getProgress() + 1);
            }
        });
        RxView.clicks(this.turn_dr_subtract).throttleFirst(0L, TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.BaseMainActivity.17
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                BaseMainActivity.this.turn_dr.setProgress(BaseMainActivity.this.turn_dr.getProgress() - 1);
            }
        });
        RxView.clicks(this.turn_dr_add).throttleFirst(0L, TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.BaseMainActivity.18
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                BaseMainActivity.this.turn_dr.setProgress(BaseMainActivity.this.turn_dr.getProgress() + 1);
            }
        });
        RxView.clicks(this.set_imgbtn).throttleFirst(100L, TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.BaseMainActivity.19
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                BaseMainActivity.this.jumpSetting();
            }
        });
        RxView.clicks(this.low_speed_imgbtn).throttleFirst(200L, TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.BaseMainActivity.20
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                if (BleDeviceManager.getInstance().isBleConnectState()) {
                    BaseMainActivity.this.centerbtn.setVisibility(0);
                    BaseMainActivity.this.centerbtn_flag = 1;
                    if (!DBConstant.getInstance(BaseMainActivity.this).isLow_speed()) {
                        BaseMainActivity.this.centerbtn.setText(R.string.start_low_speed);
                        SharedPreferencesUtils.setParam(BaseMainActivity.this, "low_speed", true);
                        BaseMainActivity.this.low_speed_imgbtn.setImageResource(R.mipmap.low_speed_press);
                        BaseMainActivity.this.SetCenterbtnGone();
                        return;
                    }
                    BaseMainActivity.this.centerbtn.setText(R.string.quit_low_speed);
                    SharedPreferencesUtils.setParam(BaseMainActivity.this, "low_speed", false);
                    BaseMainActivity.this.low_speed_imgbtn.setImageResource(R.mipmap.low_speed);
                    BaseMainActivity.this.SetCenterbtnGone();
                    return;
                }
                Toast.makeText(BaseMainActivity.this, "请先连接设备", 0).show();
            }
        });
        RxView.clicks(this.brake_imgbtn).throttleFirst(100L, TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.BaseMainActivity.21
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                BaseMainActivity.this.mHandler.sendEmptyMessage(14);
                BaseMainActivity.this.mHandler.sendEmptyMessageDelayed(14, 300L);
            }
        });
        RxView.clicks(this.garage_imgbtn).throttleFirst(20L, TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.BaseMainActivity.22
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                BaseMainActivity.this.startActivity(new Intent(BaseMainActivity.this, (Class<?>) GarageActivity.class));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void SetCenterbtnGone() {
        this.mHandler.sendEmptyMessage(12);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startSendThread() {
        sendThread sendthread = this.mSendThread;
        if (sendthread == null || !sendthread.isAlive()) {
            sendThread sendthread2 = new sendThread();
            this.mSendThread = sendthread2;
            sendthread2.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopSendThread() {
        sendThread sendthread = this.mSendThread;
        if (sendthread != null) {
            try {
                sendthread.doExit();
                this.mSendThread.join(100L);
                this.mSendThread = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class sendThread extends Thread {
        boolean mExit = false;

        public sendThread() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void doExit() {
            this.mExit = true;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            super.run();
            while (!this.mExit) {
                if (BleDeviceManager.getInstance().isBleConnectState()) {
                    try {
                        Thread.sleep(20L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // --- [修改] --- 根据是否为手柄模式选择不同的输入源 ---
                    // 如果手柄未激活，则使用屏幕控件或陀螺仪的输入
                    if (!isGamepadControlActive) {
                        if (DBConstant.getInstance(BaseMainActivity.this).isRightHand()) {
                            BaseMainActivity baseMainActivity = BaseMainActivity.this;
                            baseMainActivity.hor_value = baseMainActivity.horiSeekBar.getProgress();
                            BaseMainActivity baseMainActivity2 = BaseMainActivity.this;
                            baseMainActivity2.ver_value = baseMainActivity2.verticalSeekBar.getProgress();
                        } else {
                            BaseMainActivity baseMainActivity3 = BaseMainActivity.this;
                            baseMainActivity3.hor_value = baseMainActivity3.horiSeekBar1.getProgress();
                            BaseMainActivity baseMainActivity4 = BaseMainActivity.this;
                            baseMainActivity4.ver_value = baseMainActivity4.verticalSeekBar1.getProgress();
                        }
                        if (DBConstant.getInstance(BaseMainActivity.this).isSensor()) {
                            if (BaseMainActivity.this.gx >= 30.0f) {
                                BaseMainActivity.this.gx = 30.0f;
                            }
                            if (BaseMainActivity.this.gx <= -30.0f) {
                                BaseMainActivity.this.gx = -30.0f;
                            }
                            BaseMainActivity baseMainActivity5 = BaseMainActivity.this;
                            baseMainActivity5.hor_value = (int) (1000.0f - (baseMainActivity5.gx * 33.0f));
                        }
                    }
                    // 如果手柄已激活, hor_value 和 ver_value 已经由 onGenericMotionEvent 更新，
                    // 所以此处无需做任何事。
                    // --- [修改结束] ---

                    if (BaseMainActivity.this.switch_turn.isChecked()) {
                        BaseMainActivity baseMainActivity6 = BaseMainActivity.this;
                        baseMainActivity6.hor_value = 2000 - baseMainActivity6.hor_value;
                    }
                    if (BaseMainActivity.this.switch_accelerator.isChecked()) {
                        BaseMainActivity baseMainActivity7 = BaseMainActivity.this;
                        baseMainActivity7.ver_value = 2000 - baseMainActivity7.ver_value;
                    }
                    BaseMainActivity.this.hor_value = (((BaseMainActivity.this.hor_value - 1000) * 6) / 10) + 1000; // TODO wuyijun modified
                    BaseMainActivity baseMainActivity8 = BaseMainActivity.this;
                    baseMainActivity8.hor_turn_middle_value = DBConstant.getInstance(baseMainActivity8).getTurn_middle();
                    if (BaseMainActivity.this.hor_turn_middle_value == 45) {
                        BaseMainActivity.this.hor_turn_middle_value = 0;
                    } else {
                        BaseMainActivity.this.hor_turn_middle_value -= 45;
                    }
                    BaseMainActivity baseMainActivity9 = BaseMainActivity.this;
                    baseMainActivity9.hor_turn_dr_value = DBConstant.getInstance(baseMainActivity9).getTurn_dr() + 20;
                    if (DBConstant.getInstance(BaseMainActivity.this).isLow_speed()) {
                        BaseMainActivity.this.hor_accelerator_dr_value = 70;
                    } else {
                        BaseMainActivity baseMainActivity10 = BaseMainActivity.this;
                        baseMainActivity10.hor_accelerator_dr_value = DBConstant.getInstance(baseMainActivity10).getAccelerator_dr() + 40;
                    }
                    BaseMainActivity baseMainActivity11 = BaseMainActivity.this;
                    baseMainActivity11.hor_value = (baseMainActivity11.hor_turn_middle_value * 10) + 1000 + (((BaseMainActivity.this.hor_value - 1000) * BaseMainActivity.this.hor_turn_dr_value) / 100);
                    if (BaseMainActivity.this.hor_value > 2000) {
                        BaseMainActivity.this.hor_value = 2000;
                    } else if (BaseMainActivity.this.hor_value < 0) {
                        BaseMainActivity.this.hor_value = 0;
                    }
                    // TODO wuyijun double check this
                    BaseMainActivity.this.ver_value = (((BaseMainActivity.this.ver_value - 1000) * BaseMainActivity.this.hor_accelerator_dr_value) / 100) + 1000;
                    if (BaseMainActivity.this.ver_value > 2000) {
                        BaseMainActivity.this.ver_value = 2000;
                    } else if (BaseMainActivity.this.ver_value < 0) {
                        BaseMainActivity.this.ver_value = 0;
                    }
                    BaseMainActivity.this.writeChar3(HexUtil.byteMerger(HexUtil.intbytes_direction(BaseMainActivity.this.hor_value), HexUtil.intbytes(BaseMainActivity.this.hor_value, BaseMainActivity.this.ver_value), HexUtil.intbytes_accelerator(BaseMainActivity.this.ver_value)));
                }
            }
        }
    }

    private void quitdialogDismiss() {
        CommonDialog commonDialog = this.quitDailog;
        if (commonDialog == null || !commonDialog.isShowing()) {
            return;
        }
        this.quitDailog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dialogDismiss() {
        ListViewDialog listViewDialog = this.mListViewDialog;
        if (listViewDialog == null || !listViewDialog.isShowing()) {
            return;
        }
        this.mListViewDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestBleDialogDismiss() {
        CommonDialog commonDialog = this.blerequestDialog;
        if (commonDialog == null || !commonDialog.isShowing()) {
            return;
        }
        this.blerequestDialog.dismiss();
    }

    private void dialogShowing() {
        ListViewDialog listViewDialog = this.mListViewDialog;
        if (listViewDialog == null) {
            ListViewDialog listViewDialog2 = new ListViewDialog(this);
            this.mListViewDialog = listViewDialog2;
            if (listViewDialog2.isShowing()) {
                return;
            }
            this.mListViewDialog.setCanceledOnTouchOutside(false);
            this.mListViewDialog.setOwnerActivity(this);
            this.mListViewDialog.show();
            return;
        }
        if (listViewDialog.isShowing()) {
            return;
        }
        this.mListViewDialog.setCanceledOnTouchOutside(false);
        this.mListViewDialog.setOwnerActivity(this);
        this.mListViewDialog.show();
    }

    private void checkPermission() {
        if (!LocationUtils.isOpenLocService(this) && ApiLevelHelper.isAtLeast(24)) {
            Toast.makeText(this, "您的Android版本在7.0以上，扫描需要打开位置信息。", 1).show();
            LocationUtils.gotoLocServiceSettings(this);
        } else if (Build.VERSION.SDK_INT >= 23) {
            int iCheckSelfPermission = ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE");
            int iCheckSelfPermission2 = ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE");
            if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0 && iCheckSelfPermission2 == 0 && iCheckSelfPermission == 0) {
                return;
            }
            requestPermissions(new String[]{"android.permission.ACCESS_COARSE_LOCATION", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}, 2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestBluetooth() {
        BleUtil.enableBluetooth(this, 11);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enableBluetooth() {
        if (!((Boolean) SharedPreferencesUtils.getParam(this, "agreement", false)).booleanValue()) {
            this.agreementDialog.show();
            return;
        }
        if (!BleUtil.isBleEnable(this)) {
            CommonDialog positiveButton = new CommonDialog(this, R.style.dialog, getString(R.string.request_ble), new CommonDialog.OnCloseListener() { // from class: com.android.blerc.BaseMainActivity.25
                @Override // com.android.blerc.dialog.CommonDialog.OnCloseListener
                public void onClick(Dialog dialog, boolean z) {
                    if (z) {
                        BaseMainActivity.this.requestBluetooth();
                    } else {
                        BaseMainActivity.this.requestBleDialogDismiss();
                        BaseMainActivity.this.showConnectDialog();
                    }
                }
            }).setNegativeButton(getString(R.string.no)).setPositiveButton(getString(R.string.yes));
            this.blerequestDialog = positiveButton;
            positiveButton.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.blerc.BaseMainActivity.26
                @Override // android.content.DialogInterface.OnDismissListener
                public void onDismiss(DialogInterface dialogInterface) {
                    if (BleUtil.isBleEnable(BaseMainActivity.this)) {
                        return;
                    }
                    BaseMainActivity.this.showConnectDialog();
                }
            });
            this.blerequestDialog.show();
            return;
        }
        boolean zIsSupportBle = BleUtil.isSupportBle(this);
        BleUtil.isBleEnable(this);
        if (zIsSupportBle) {
            dialogShowing();
        }
    }

    @Override // android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i != 2) {
            return;
        }
        for (String str : strArr) {
            if (!str.equals("android.permission.ACCESS_COARSE_LOCATION") && !str.equals("android.permission.READ_EXTERNAL_STORAGE")) {
                str.equals("android.permission.WRITE_EXTERNAL_STORAGE");
            }
        }
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 11) {
            requestBleDialogDismiss();
            if (i2 == -1) {
                dialogShowing();
            } else {
                onCreate(null);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initSensor() {
        this.timestamp = 0.0f;
        this.accMagOrientation = new float[3];
        this.initState = true;
        this.gyroMatrix = new float[]{1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f};
        this.gyroOrientation = new float[]{0.0f, 0.0f, 0.0f};
        this.gyro = new float[3];
        this.magnet = new float[3];
        this.accel = new float[3];
        this.rotationMatrix = new float[9];
        this.fuseFlag = true;
    }

    public void calculateAccMagOrientation() {
        if (SensorManager.getRotationMatrix(this.rotationMatrix, null, this.accel, this.magnet)) {
            SensorManager.getOrientation(this.rotationMatrix, this.accMagOrientation);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getRotationVectorFromGyro(float[] fArr, float[] fArr2, float f) {
        float[] fArr3 = new float[3];
        float fSqrt = (float) Math.sqrt((fArr[0] * fArr[0]) + (fArr[1] * fArr[1]) + (fArr[2] * fArr[2]));
        if (fSqrt > 1.0E-9f) {
            fArr3[0] = fArr[0] / fSqrt;
            fArr3[1] = fArr[1] / fSqrt;
            fArr3[2] = fArr[2] / fSqrt;
        }
        double d = fSqrt * f;
        float fSin = (float) Math.sin(d);
        float fCos = (float) Math.cos(d);
        fArr2[0] = fArr3[0] * fSin;
        fArr2[1] = fArr3[1] * fSin;
        fArr2[2] = fSin * fArr3[2];
        fArr2[3] = fCos;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float[] getRotationMatrixFromOrientation(float[] fArr) {
        float fSin = (float) Math.sin(fArr[1]);
        float fCos = (float) Math.cos(fArr[1]);
        float fSin2 = (float) Math.sin(fArr[2]);
        float fCos2 = (float) Math.cos(fArr[2]);
        float fSin3 = (float) Math.sin(fArr[0]);
        float fCos3 = (float) Math.cos(fArr[0]);
        return matrixMultiplication(new float[]{fCos3, fSin3, 0.0f, -fSin3, fCos3, 0.0f, 0.0f, 0.0f, 1.0f}, matrixMultiplication(new float[]{1.0f, 0.0f, 0.0f, 0.0f, fCos, fSin, 0.0f, -fSin, fCos}, new float[]{fCos2, 0.0f, fSin2, 0.0f, 1.0f, 0.0f, -fSin2, 0.0f, fCos2}));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float[] matrixMultiplication(float[] fArr, float[] fArr2) {
        return new float[]{(fArr[0] * fArr2[0]) + (fArr[1] * fArr2[3]) + (fArr[2] * fArr2[6]), (fArr[0] * fArr2[1]) + (fArr[1] * fArr2[4]) + (fArr[2] * fArr2[7]), (fArr[0] * fArr2[2]) + (fArr[1] * fArr2[5]) + (fArr[2] * fArr2[8]), (fArr[3] * fArr2[0]) + (fArr[4] * fArr2[3]) + (fArr[5] * fArr2[6]), (fArr[3] * fArr2[1]) + (fArr[4] * fArr2[4]) + (fArr[5] * fArr2[7]), (fArr[3] * fArr2[2]) + (fArr[4] * fArr2[5]) + (fArr[5] * fArr2[8]), (fArr[6] * fArr2[0]) + (fArr[7] * fArr2[3]) + (fArr[8] * fArr2[6]), (fArr[6] * fArr2[1]) + (fArr[7] * fArr2[4]) + (fArr[8] * fArr2[7]), (fArr[6] * fArr2[2]) + (fArr[7] * fArr2[5]) + (fArr[8] * fArr2[8])};
    }

    class calculateFusedOrientationTask extends TimerTask {
        calculateFusedOrientationTask() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            double d;
            if (BaseMainActivity.this.fuseFlag) {
                if (BaseMainActivity.this.gyroOrientation[0] < -1.5707963267948966d && BaseMainActivity.this.accMagOrientation[0] > 0.0d) {
                    float[] fArr = BaseMainActivity.this.fusedOrientation;
                    double d2 = BaseMainActivity.this.gyroOrientation[0];
                    Double.isNaN(d2);
                    double d3 = BaseMainActivity.this.accMagOrientation[0] * 0.01999998f;
                    Double.isNaN(d3);
                    fArr[0] = (float) (((d2 + 6.283185307179586d) * 0.9800000190734863d) + d3);
                    float[] fArr2 = BaseMainActivity.this.fusedOrientation;
                    double d4 = fArr2[0];
                    double d5 = ((double) BaseMainActivity.this.fusedOrientation[0]) > 3.141592653589793d ? 6.283185307179586d : 0.0d;
                    Double.isNaN(d4);
                    fArr2[0] = (float) (d4 - d5);
                } else if (BaseMainActivity.this.accMagOrientation[0] >= -1.5707963267948966d || BaseMainActivity.this.gyroOrientation[0] <= 0.0d) {
                    BaseMainActivity.this.fusedOrientation[0] = (BaseMainActivity.this.gyroOrientation[0] * 0.98f) + (BaseMainActivity.this.accMagOrientation[0] * 0.01999998f);
                } else {
                    float[] fArr3 = BaseMainActivity.this.fusedOrientation;
                    double d6 = BaseMainActivity.this.gyroOrientation[0] * 0.98f;
                    double d7 = 0.01999998f;
                    double d8 = BaseMainActivity.this.accMagOrientation[0];
                    Double.isNaN(d8);
                    Double.isNaN(d7);
                    Double.isNaN(d6);
                    fArr3[0] = (float) (d6 + (d7 * (d8 + 6.283185307179586d)));
                    float[] fArr4 = BaseMainActivity.this.fusedOrientation;
                    double d9 = fArr4[0];
                    double d10 = ((double) BaseMainActivity.this.fusedOrientation[0]) > 3.141592653589793d ? 6.283185307179586d : 0.0d;
                    Double.isNaN(d9);
                    fArr4[0] = (float) (d9 - d10);
                }
                if (BaseMainActivity.this.gyroOrientation[1] < -1.5707963267948966d && BaseMainActivity.this.accMagOrientation[1] > 0.0d) {
                    float[] fArr5 = BaseMainActivity.this.fusedOrientation;
                    double d11 = BaseMainActivity.this.gyroOrientation[1];
                    Double.isNaN(d11);
                    double d12 = BaseMainActivity.this.accMagOrientation[1] * 0.01999998f;
                    Double.isNaN(d12);
                    fArr5[1] = (float) (((d11 + 6.283185307179586d) * 0.9800000190734863d) + d12);
                    float[] fArr6 = BaseMainActivity.this.fusedOrientation;
                    double d13 = fArr6[1];
                    double d14 = ((double) BaseMainActivity.this.fusedOrientation[1]) > 3.141592653589793d ? 6.283185307179586d : 0.0d;
                    Double.isNaN(d13);
                    fArr6[1] = (float) (d13 - d14);
                } else if (BaseMainActivity.this.accMagOrientation[1] >= -1.5707963267948966d || BaseMainActivity.this.gyroOrientation[1] <= 0.0d) {
                    BaseMainActivity.this.fusedOrientation[1] = (BaseMainActivity.this.gyroOrientation[1] * 0.98f) + (BaseMainActivity.this.accMagOrientation[1] * 0.01999998f);
                } else {
                    float[] fArr7 = BaseMainActivity.this.fusedOrientation;
                    double d15 = BaseMainActivity.this.gyroOrientation[1] * 0.98f;
                    double d16 = 0.01999998f;
                    double d17 = BaseMainActivity.this.accMagOrientation[1];
                    Double.isNaN(d17);
                    Double.isNaN(d16);
                    Double.isNaN(d15);
                    fArr7[1] = (float) (d15 + (d16 * (d17 + 6.283185307179586d)));
                    float[] fArr8 = BaseMainActivity.this.fusedOrientation;
                    double d18 = fArr8[1];
                    double d19 = ((double) BaseMainActivity.this.fusedOrientation[1]) > 3.141592653589793d ? 6.283185307179586d : 0.0d;
                    Double.isNaN(d18);
                    fArr8[1] = (float) (d18 - d19);
                }
                if (BaseMainActivity.this.gyroOrientation[2] < -1.5707963267948966d && BaseMainActivity.this.accMagOrientation[2] > 0.0d) {
                    float[] fArr9 = BaseMainActivity.this.fusedOrientation;
                    double d20 = BaseMainActivity.this.gyroOrientation[2];
                    Double.isNaN(d20);
                    double d21 = 0.01999998f * BaseMainActivity.this.accMagOrientation[2];
                    Double.isNaN(d21);
                    fArr9[2] = (float) (((d20 + 6.283185307179586d) * 0.9800000190734863d) + d21);
                    float[] fArr10 = BaseMainActivity.this.fusedOrientation;
                    double d22 = fArr10[2];
                    d = ((double) BaseMainActivity.this.fusedOrientation[2]) <= 3.141592653589793d ? 0.0d : 6.283185307179586d;
                    Double.isNaN(d22);
                    fArr10[2] = (float) (d22 - d);
                } else if (BaseMainActivity.this.accMagOrientation[2] >= -1.5707963267948966d || BaseMainActivity.this.gyroOrientation[2] <= 0.0d) {
                    BaseMainActivity.this.fusedOrientation[2] = (BaseMainActivity.this.gyroOrientation[2] * 0.98f) + (0.01999998f * BaseMainActivity.this.accMagOrientation[2]);
                } else {
                    float[] fArr11 = BaseMainActivity.this.fusedOrientation;
                    double d23 = BaseMainActivity.this.gyroOrientation[2] * 0.98f;
                    double d24 = 0.01999998f;
                    double d25 = BaseMainActivity.this.accMagOrientation[2];
                    Double.isNaN(d25);
                    Double.isNaN(d24);
                    Double.isNaN(d23);
                    fArr11[2] = (float) (d23 + (d24 * (d25 + 6.283185307179586d)));
                    float[] fArr12 = BaseMainActivity.this.fusedOrientation;
                    double d26 = fArr12[2];
                    d = ((double) BaseMainActivity.this.fusedOrientation[2]) <= 3.141592653589793d ? 0.0d : 6.283185307179586d;
                    Double.isNaN(d26);
                    fArr12[2] = (float) (d26 - d);
                }
                BaseMainActivity baseMainActivity = BaseMainActivity.this;
                baseMainActivity.gyroMatrix = baseMainActivity.getRotationMatrixFromOrientation(baseMainActivity.fusedOrientation);
                System.arraycopy(BaseMainActivity.this.fusedOrientation, 0, BaseMainActivity.this.gyroOrientation, 0, 3);
            }
        }
    }
}
