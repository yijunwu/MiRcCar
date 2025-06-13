package com.android.blerc.util;

import android.content.Context;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class DataUtil {
    private static DataUtil instance;

    public static DataUtil getInstance() {
        if (instance == null) {
            synchronized (DataUtil.class) {
                if (instance == null) {
                    instance = new DataUtil();
                }
            }
        }
        return instance;
    }

    private DataUtil() {
    }

    public Object[] splitAry(byte[] bArr, int i) {
        int length = bArr.length % i == 0 ? bArr.length / i : (bArr.length / i) + 1;
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < length; i2++) {
            ArrayList arrayList2 = new ArrayList();
            int i3 = 0;
            for (int i4 = i2 * i; i3 < i && i4 < bArr.length; i4++) {
                arrayList2.add(Byte.valueOf(bArr[i4]));
                i3++;
            }
            arrayList.add(arrayList2);
        }
        Object[] objArr = new Object[arrayList.size()];
        for (int i5 = 0; i5 < arrayList.size(); i5++) {
            List list = (List) arrayList.get(i5);
            byte[] bArr2 = new byte[list.size()];
            for (int i6 = 0; i6 < list.size(); i6++) {
                bArr2[i6] = ((Byte) list.get(i6)).byteValue();
            }
            objArr[i5] = bArr2;
        }
        return objArr;
    }

    public void copyDataBase(Context context, String str, String str2) throws IOException {
        String str3 = str + str2;
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
        File file2 = new File(str3);
        if (!file2.exists()) {
            file2.createNewFile();
        }
        InputStream inputStreamOpen = context.getAssets().open("rcx/" + str2);
        FileOutputStream fileOutputStream = new FileOutputStream(str3);
        byte[] bArr = new byte[1024];
        while (true) {
            int i = inputStreamOpen.read(bArr);
            if (i > 0) {
                fileOutputStream.write(bArr, 0, i);
            } else {
                fileOutputStream.flush();
                fileOutputStream.close();
                inputStreamOpen.close();
                return;
            }
        }
    }

    public byte[] readFile(File file) throws IOException {
        if (file.isFile()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] bArr = new byte[1024];
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                while (true) {
                    int i = fileInputStream.read(bArr);
                    if (i != -1) {
                        byteArrayOutputStream.write(bArr, 0, i);
                    } else {
                        return byteArrayOutputStream.toByteArray();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            System.out.println("文件不存在！");
            return null;
        }
    }

    public byte[] subBytes(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[i2];
        for (int i3 = i; i3 < i + i2; i3++) {
            bArr2[i3 - i] = bArr[i3];
        }
        return bArr2;
    }

    public byte[] byteMerger(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[bArr.length + bArr2.length];
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr3, bArr.length, bArr2.length);
        return bArr3;
    }

    public byte[] byteMerger(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        byte[] bArr4 = new byte[bArr.length + bArr2.length + bArr3.length];
        System.arraycopy(bArr, 0, bArr4, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr4, bArr.length, bArr2.length);
        System.arraycopy(bArr3, 0, bArr4, bArr.length + bArr2.length, bArr3.length);
        return bArr4;
    }

    public byte[] byteMerger(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, byte[] bArr5) {
        byte[] bArr6 = new byte[bArr.length + bArr2.length + bArr3.length + bArr4.length + bArr5.length];
        System.arraycopy(bArr, 0, bArr6, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr6, bArr.length, bArr2.length);
        System.arraycopy(bArr3, 0, bArr6, bArr.length + bArr2.length, bArr3.length);
        System.arraycopy(bArr4, 0, bArr6, bArr.length + bArr2.length + bArr3.length, bArr4.length);
        System.arraycopy(bArr5, 0, bArr6, bArr.length + bArr2.length + bArr3.length + bArr4.length, bArr5.length);
        return bArr6;
    }

    public byte[] byteMerger(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) {
        byte[] bArr5 = new byte[bArr.length + bArr2.length + bArr3.length + bArr4.length];
        System.arraycopy(bArr, 0, bArr5, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr5, bArr.length, bArr2.length);
        System.arraycopy(bArr3, 0, bArr5, bArr.length + bArr2.length, bArr3.length);
        System.arraycopy(bArr4, 0, bArr5, bArr.length + bArr2.length + bArr3.length, bArr4.length);
        return bArr5;
    }

    public void delFolder(String str) {
        try {
            delAllFile(str);
            new File(str.toString()).delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean delAllFile(String str) {
        File file;
        File file2 = new File(str);
        if (!file2.exists() || !file2.isDirectory()) {
            return false;
        }
        String[] list = file2.list();
        boolean z = false;
        for (int i = 0; i < list.length; i++) {
            if (str.endsWith(File.separator)) {
                file = new File(str + list[i]);
            } else {
                file = new File(str + File.separator + list[i]);
            }
            if (file.isFile()) {
                file.delete();
            }
            if (file.isDirectory()) {
                delAllFile(str + "/" + list[i]);
                delFolder(str + "/" + list[i]);
                z = true;
            }
        }
        return z;
    }
}
