package com.android.blerc.location;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;

/* loaded from: classes.dex */
public class LocationUtils {
    private LocationUtils() {
    }

    public static boolean isOpenLocService(Context context) {
        boolean zIsProviderEnabled;
        boolean zIsProviderEnabled2;
        if (context != null) {
            LocationManager locationManager = (LocationManager) context.getSystemService("location");
            if (locationManager != null) {
                zIsProviderEnabled2 = locationManager.isProviderEnabled("gps");
                zIsProviderEnabled = locationManager.isProviderEnabled("network");
            } else {
                zIsProviderEnabled = false;
                zIsProviderEnabled2 = false;
            }
            if (zIsProviderEnabled2 || zIsProviderEnabled) {
                return true;
            }
        }
        return false;
    }

    public static void gotoLocServiceSettings(Activity activity) {
        Intent intent = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
        intent.setFlags(268435456);
        activity.startActivity(intent);
    }

    public static void gotoLocServiceSettings(Activity activity, int i) {
        Intent intent = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
        intent.setFlags(268435456);
        activity.startActivityForResult(intent, i);
    }
}
