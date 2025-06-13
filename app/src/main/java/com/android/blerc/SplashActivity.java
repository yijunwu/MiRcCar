package com.android.blerc;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import butterknife.ButterKnife;
import com.andorid.blerc.BarBaseActivity;
import com.andorid.blerc.LottieAnimationView;
import com.andorid.blerc.bean.Splashbean;
import com.andorid.blerc.common.ParamRequestInfo;
import com.andorid.blerc.common.RxEvent;
import com.andorid.blerc.common.SystemInfo;
import com.andorid.blerc.common.ViseBle;
import com.andorid.blerc.db.DBConstant;
import com.andorid.blerc.location.ApiLevelHelper;
import com.andorid.blerc.location.LocationUtils;
import com.andorid.blerc.util.DataUtil;
import com.andorid.blerc.util.HexUtil;
import com.andorid.blerc.util.NetworkUtils;
import com.andorid.blerc.util.Utils;
import com.rcfans.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import okhttp3.Call;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pub.devrel.easypermissions.EasyPermissions;

/* loaded from: classes.dex */
public class SplashActivity extends BarBaseActivity implements EasyPermissions.PermissionCallbacks {
    LottieAnimationView animation;
    AppVersionDialog appVersionDialog;
    ImageView image;
    private List<String> img_List;
    List<ImageView> list;
    List<ImageView> listDoc;
    LinearLayout ll;
    RelativeLayout one_layout;
    Button start_btn;
    RelativeLayout two_layout;
    ViewPager vp;
    private final String TAG = "CarMi";
    int[] imgArray = {R.mipmap.p1, R.mipmap.p4};
    int battery = -1;
    int appVersion = 1;
    int newVersion = 1;
    private Handler mHandler = new Handler() { // from class: com.android.blerc.SplashActivity.6
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            int i = message.what;
            if (i == 0) {
                Intent intent = new Intent(SplashActivity.this, (Class<?>) MainActivity.class);
                intent.putExtra("splash", false);
                intent.putExtra("battery", SplashActivity.this.battery);
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                SplashActivity.this.finish();
                return;
            }
            if (i == 1) {
                try {
                    SplashActivity.this.writeChar1(DataUtil.getInstance().byteMerger(HexUtil.hexStringToBytes("20"), HexUtil.intToByteArray(1)));
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
            if (i != 4) {
                return;
            }
            ViseBle.getInstance().readCharacteristic(ViseBle.getInstance().getmBle_char5());
            try {
                SplashActivity.this.writeChar1(DataUtil.getInstance().byteMerger(HexUtil.hexStringToBytes("10"), HexUtil.intToByteArray(4)));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    };

    @Override // com.andorid.blerc.BarBaseActivity, com.andorid.blerc.BaseActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if ((getIntent().getFlags() & 4194304) != 0) {
            finish();
        }
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        changeLanguage(DBConstant.getInstance(this).getLanguage());
        AppVersionDialog appVersionDialog = new AppVersionDialog(this, true);
        this.appVersionDialog = appVersionDialog;
        appVersionDialog.setCanceledOnTouchOutside(false);
        this.appVersionDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.blerc.SplashActivity.1
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                SplashActivity.this.mHandler.sendEmptyMessageDelayed(0, 100L);
            }
        });
        checkPermission();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        this.animation.useHardwareAcceleration(true);
        this.animation.enableMergePathsForKitKatAndAbove(true);
        checking();
        this.animation.addAnimatorListener(new Animator.AnimatorListener() { // from class: com.android.blerc.SplashActivity.2
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                Log.d("CarMi", "end");
                SplashActivity.this.animation.recycleBitmaps();
                if (SplashActivity.this.newVersion <= SplashActivity.this.appVersion) {
                    SplashActivity.this.mHandler.sendEmptyMessage(0);
                } else {
                    SplashActivity.this.appVersionDialog.show();
                }
            }
        });
        this.animation.addAnimatorUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.blerc.SplashActivity.3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                double dMaxMemory = (Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory()) - Runtime.getRuntime().freeMemory();
                Double.isNaN(dMaxMemory);
                if (((float) ((dMaxMemory * 1.0d) / 1048576.0d)) < 100.0f) {
                    Log.d("CarMi", "recycleBitmaps");
                    SplashActivity.this.animation.recycleBitmaps();
                }
            }
        });
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
    public void onEventMainThread(RxEvent rxEvent) {
        String msg = rxEvent.getMsg();
        Log.d("CarMi", "onEventMainThread收到了消息：" + msg);
        if (msg.equals("bleconnect")) {
            sendMsg(1, "", 500L);
            return;
        }
        if (msg.equals("bledisconnect")) {
            sendMsg(2, "", 0L);
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
            this.mHandler.sendEmptyMessage(4);
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
                return;
            }
            return;
        }
        if (msg.startsWith("battery")) {
            Log.d("CarMi", msg);
            this.battery = Integer.parseInt(msg.split(":")[1]);
        }
    }

    private void changeLanguage(String str) {
        Locale locale = new Locale(str);
        Resources resources = getBaseContext().getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, displayMetrics);
    }

    private void checking() {
        if (NetworkUtils.isConnected()) {
            OkHttpUtils.get().url(Utils.version_url).build().execute(new StringCallback() { // from class: com.android.blerc.SplashActivity.4
                @Override // com.zhy.http.okhttp.callback.Callback
                public void onError(Call call, Exception exc, int i) {
                    Log.d("CarMi", "Exception : " + exc.toString());
                    SplashActivity.this.mHandler.sendEmptyMessageDelayed(0, 100L);
                }

                @Override // com.zhy.http.okhttp.callback.Callback
                public void onResponse(String str, int i) {
                    Log.d("CarMi", "response : " + str);
                    try {
                        JSONObject jSONObject = new JSONObject(str);
                        Log.d("CarMi", "app_ver :  " + jSONObject.getString("app_ver"));
                        Log.d("CarMi", "app_update :  " + jSONObject.getString("app_update"));
                        Log.d("CarMi", "firmware_ver :  " + jSONObject.getString("firmware_ver"));
                        Log.d("CarMi", "firmware_update :  " + jSONObject.getString("firmware_update"));
                        Log.d("CarMi", "start :  " + jSONObject.getString("start"));
                        Splashbean.getInstance().setApp_ver(jSONObject.getString("app_ver"));
                        Splashbean.getInstance().setApp_update(jSONObject.getString("app_update"));
                        Splashbean.getInstance().setFirmware_ver(jSONObject.getString("firmware_ver"));
                        Splashbean.getInstance().setFirmware_update(jSONObject.getString("firmware_update"));
                        SplashActivity.this.appVersion = Integer.parseInt(DBConstant.getInstance(SplashActivity.this).getAppVersion().replaceAll("\\.", ""));
                        SplashActivity.this.newVersion = Integer.parseInt(jSONObject.getString("app_ver").replaceAll("\\.", ""));
                    } catch (JSONException unused) {
                    }
                }
            });
        } else {
            this.mHandler.sendEmptyMessageDelayed(0, 100L);
        }
    }

    public void installApkO() {
        InstallUtils.installApk(this);
    }

    @Override // android.app.Activity, android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        EasyPermissions.onRequestPermissionsResult(i, strArr, iArr, this);
    }

    @Override // pub.devrel.easypermissions.EasyPermissions.PermissionCallbacks
    public void onPermissionsGranted(int i, List<String> list) {
        Log.d("CarMi", i + "已经获得此权限！");
    }

    @Override // pub.devrel.easypermissions.EasyPermissions.PermissionCallbacks
    public void onPermissionsDenied(int i, List<String> list) {
        EasyPermissions.somePermissionPermanentlyDenied(this, list);
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
    }

    private void checkPermission() {
        if (!LocationUtils.isOpenLocService(this) && ApiLevelHelper.isAtLeast(24)) {
            Toast.makeText(this, "您的Android版本在7.0以上，扫描需要打开位置信息。", 1).show();
            LocationUtils.gotoLocServiceSettings(this);
        } else if (Build.VERSION.SDK_INT >= 23) {
            int iCheckSelfPermission = ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE");
            int iCheckSelfPermission2 = ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE");
            if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0 && iCheckSelfPermission2 == 0 && iCheckSelfPermission == 0) {
                return;
            }
            requestPermissions(new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}, 2);
        }
    }

    private int[] getRes() throws Resources.NotFoundException {
        TypedArray typedArrayObtainTypedArray = getResources().obtainTypedArray(R.array.c);
        int length = typedArrayObtainTypedArray.length();
        int[] iArr = new int[length];
        int length2 = typedArrayObtainTypedArray.length() - 1;
        for (int i = 0; i < length; i++) {
            iArr[i] = typedArrayObtainTypedArray.getResourceId(length2, -1);
            length2--;
        }
        typedArrayObtainTypedArray.recycle();
        return iArr;
    }

    private void showWelcomeImage() {
        if (NetworkUtils.isConnected()) {
            this.mHandler.sendEmptyMessageDelayed(1, 20L);
        } else {
            this.mHandler.sendEmptyMessageDelayed(0, 20L);
        }
    }

    private void getWelcomeImage() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        final int i = displayMetrics.widthPixels;
        final int i2 = displayMetrics.heightPixels;
        Log.d("CarMi", "Heigh :  " + i);
        Log.d("CarMi", "Width :  " + i2);
        if (NetworkUtils.isConnected()) {
            OkHttpUtils.get().url("http://www.rcfans.com/jimny/start.json").build().execute(new StringCallback() { // from class: com.android.blerc.SplashActivity.5
                @Override // com.zhy.http.okhttp.callback.Callback
                public void onError(Call call, Exception exc, int i3) {
                    Log.d("CarMi", "Exception : " + exc.toString());
                }

                @Override // com.zhy.http.okhttp.callback.Callback
                public void onResponse(String str, int i3) throws JSONException {
                    Log.d("CarMi", "response : " + str);
                    try {
                        JSONObject jSONObject = new JSONObject(str);
                        Log.d("CarMi", "app_ver :  " + jSONObject.getString("app_ver"));
                        Log.d("CarMi", "app_update :  " + jSONObject.getString("app_update"));
                        Log.d("CarMi", "firmware_ver :  " + jSONObject.getString("firmware_ver"));
                        Log.d("CarMi", "firmware_update :  " + jSONObject.getString("firmware_update"));
                        Log.d("CarMi", "start :  " + jSONObject.getString("start"));
                        Splashbean.getInstance().setApp_ver(jSONObject.getString("app_ver"));
                        Splashbean.getInstance().setApp_update(jSONObject.getString("app_update"));
                        Splashbean.getInstance().setFirmware_ver(jSONObject.getString("firmware_ver"));
                        Splashbean.getInstance().setFirmware_update(jSONObject.getString("firmware_update"));
                        JSONArray jSONArray = new JSONObject(jSONObject.getString("start")).getJSONArray(i + "x" + i2);
                        SplashActivity.this.img_List = new ArrayList();
                        for (int i4 = 0; i4 < jSONArray.length(); i4++) {
                            Log.d("CarMi", "array :  " + jSONArray.getString(i4));
                            SplashActivity.this.img_List.add(jSONArray.getString(i4));
                        }
                    } catch (JSONException e) {
                        try {
                            JSONArray jSONArray2 = new JSONObject(new JSONObject(str).getString("start")).getJSONArray("1920x1080");
                            SplashActivity.this.img_List = new ArrayList();
                            for (int i5 = 0; i5 < jSONArray2.length(); i5++) {
                                Log.d("CarMi", "array :  " + jSONArray2.getString(i5));
                                SplashActivity.this.img_List.add(jSONArray2.getString(i5));
                            }
                            e.printStackTrace();
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    private void addDoc() {
        this.listDoc = new ArrayList();
        for (int i = 0; i < this.img_List.size(); i++) {
            ImageView imageView = new ImageView(this);
            if (i == 0) {
                imageView.setImageResource(R.mipmap.point_selected);
            } else {
                imageView.setImageResource(R.mipmap.point_mormal);
            }
            this.listDoc.add(imageView);
            this.ll.addView(imageView);
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override // android.app.Activity
    protected void onStop() {
        super.onStop();
    }

    private void initData() {
        this.list = new ArrayList();
        for (int i = 0; i < this.img_List.size(); i++) {
            this.list.add(new ImageView(this));
        }
    }

    class MyPagerAdapter extends PagerAdapter {
        @Override // android.support.v4.view.PagerAdapter
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        MyPagerAdapter() {
        }

        @Override // android.support.v4.view.PagerAdapter
        public int getCount() {
            return SplashActivity.this.list.size();
        }

        @Override // android.support.v4.view.PagerAdapter
        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView(SplashActivity.this.list.get(i));
        }

        @Override // android.support.v4.view.PagerAdapter
        public Object instantiateItem(ViewGroup viewGroup, int i) {
            viewGroup.addView(SplashActivity.this.list.get(i));
            return SplashActivity.this.list.get(i);
        }
    }
}
