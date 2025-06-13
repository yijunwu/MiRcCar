package com.android.blerc.log;

import android.content.Context;
import android.os.Environment;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/* loaded from: classes.dex */
public class Loger {
    public static void LogTask(Context context, String str) throws IOException {
        if (Environment.getExternalStorageState().equals("mounted")) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(Constants.LOG_TASK, true);
                fileOutputStream.write((DateTimeUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss:SSS") + " --- " + str + "\r\n").getBytes());
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (IOException unused) {
            }
        }
    }
}
