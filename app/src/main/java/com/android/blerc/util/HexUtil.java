package com.android.blerc.util;

import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import kotlin.UByte;

/* loaded from: classes.dex */
public class HexUtil {
    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static byte[] int2bytes(int i) {
        return new byte[]{(byte) ((i >>> 0) & 255), (byte) ((i >>> 8) & 255)};
    }

    public static byte[] int4bytes(int i) {
        return new byte[]{(byte) ((i >>> 0) & 255), (byte) ((i >>> 8) & 255), (byte) ((i >>> 16) & 255), (byte) ((i >>> 24) & 255)};
    }

    public static byte[] intbytes(int i, int i2) {
        return new byte[]{(byte) (((i << 4) & 240) | ((i2 >> 8) & 15))};
    }

    public static byte[] intbytes_accelerator(int i) {
        return new byte[]{(byte) (i & 255)};
    }

    public static byte[] intbytes_direction(int i) {
        return new byte[]{(byte) ((i >> 4) & 255)};
    }

    public static char[] encodeHex(byte[] bArr) {
        return encodeHex(bArr, true);
    }

    public static char[] encodeHex(byte[] bArr, boolean z) {
        return encodeHex(bArr, z ? DIGITS_LOWER : DIGITS_UPPER);
    }

    protected static char[] encodeHex(byte[] bArr, char[] cArr) {
        int length = bArr.length;
        char[] cArr2 = new char[length << 1];
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i + 1;
            cArr2[i] = cArr[(bArr[i2] & 240) >>> 4];
            i = i3 + 1;
            cArr2[i3] = cArr[bArr[i2] & 15];
        }
        return cArr2;
    }

    public static String bytes2Hex(byte[] bArr) {
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        char[] cArr = new char[bArr.length * 2];
        char[] cArr2 = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        int i = 0;
        for (int i2 = 0; i2 < bArr.length; i2++) {
            int i3 = i + 1;
            cArr[i] = cArr2[(bArr[i2] >> 4) & 15];
            i = i3 + 1;
            cArr[i3] = cArr2[bArr[i2] & 15];
        }
        return new String(cArr);
    }

    public static String encodeHexStr(byte[] bArr) {
        return encodeHexStr(bArr, true);
    }

    public static String encodeHexStr(byte[] bArr, boolean z) {
        return encodeHexStr(bArr, z ? DIGITS_LOWER : DIGITS_UPPER);
    }

    public static byte[] byteMerger(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        byte[] bArr4 = new byte[bArr.length + bArr2.length + bArr3.length];
        System.arraycopy(bArr, 0, bArr4, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr4, bArr.length, bArr2.length);
        System.arraycopy(bArr3, 0, bArr4, bArr.length + bArr2.length, bArr3.length);
        return bArr4;
    }

    protected static String encodeHexStr(byte[] bArr, char[] cArr) {
        if (bArr == null) {
            Log.e(" HexUtil ", "this data is null.");
            return "";
        }
        return new String(encodeHex(bArr, cArr));
    }

    public static byte[] hexStringToBytes(String str) {
        if (str == null || str.equals("")) {
            return null;
        }
        String upperCase = str.toUpperCase();
        int length = upperCase.length() / 2;
        char[] charArray = upperCase.toCharArray();
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = (byte) (charToByte(charArray[i2 + 1]) | (charToByte(charArray[i2]) << 4));
        }
        return bArr;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static byte[] decodeHex(String str) {
        if (str == null) {
            Log.e(" HexUtil ", "this data is null.");
            return new byte[0];
        }
        return decodeHex(str.toCharArray());
    }

    public static byte[] decodeHex(char[] cArr) {
        int length = cArr.length;
        if ((length & 1) != 0) {
            throw new RuntimeException("Odd number of characters.");
        }
        byte[] bArr = new byte[length >> 1];
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int digit = toDigit(cArr[i], i) << 4;
            int i3 = i + 1;
            int digit2 = digit | toDigit(cArr[i3], i3);
            i = i3 + 1;
            bArr[i2] = (byte) (digit2 & 255);
            i2++;
        }
        return bArr;
    }

    protected static int toDigit(char c, int i) {
        int iDigit = Character.digit(c, 16);
        if (iDigit != -1) {
            return iDigit;
        }
        throw new RuntimeException("Illegal hexadecimal character " + c + " at index " + i);
    }

    public static byte[] subBytes(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, i2);
        return bArr2;
    }

    public static void intToByte(byte[] bArr, int i, int i2, boolean z) {
        if (z) {
            bArr[i2 + 0] = (byte) (i >> 24);
            bArr[i2 + 1] = (byte) (i >> 16);
            bArr[i2 + 2] = (byte) (i >> 8);
            bArr[i2 + 3] = (byte) (i >> 0);
            return;
        }
        bArr[i2 + 3] = (byte) (i >> 24);
        bArr[i2 + 2] = (byte) (i >> 16);
        bArr[i2 + 1] = (byte) (i >> 8);
        bArr[i2 + 0] = (byte) (i >> 0);
    }

    public static int byteToInt(byte[] bArr, int i, boolean z) {
        int i2;
        byte b;
        if (z) {
            i2 = ((bArr[i + 0] & UByte.MAX_VALUE) << 24) | ((bArr[i + 1] & UByte.MAX_VALUE) << 16) | ((bArr[i + 2] & UByte.MAX_VALUE) << 8);
            b = bArr[i + 3];
        } else {
            i2 = ((bArr[i + 3] & UByte.MAX_VALUE) << 24) | ((bArr[i + 2] & UByte.MAX_VALUE) << 16) | ((bArr[i + 1] & UByte.MAX_VALUE) << 8);
            b = bArr[i + 0];
        }
        return ((b & UByte.MAX_VALUE) << 0) | i2;
    }

    public static int byteToInt(byte[] bArr) {
        int i = 0;
        for (int i2 = 0; i2 < bArr.length; i2++) {
            i += (bArr[i2] & UByte.MAX_VALUE) << (i2 * 8);
        }
        return i;
    }

    public static byte[] intToByteArray(int i) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        dataOutputStream.writeByte(i);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        dataOutputStream.close();
        byteArrayOutputStream.close();
        return byteArray;
    }

    public static byte[] reverse(byte[] bArr) {
        byte[] bArr2 = new byte[bArr.length];
        for (int i = 0; i < bArr.length; i++) {
            bArr2[i] = bArr[(bArr.length - 1) - i];
        }
        return bArr2;
    }

    public static long byteToLong(byte[] bArr, int i, int i2, boolean z) {
        long j;
        byte b;
        long j2;
        byte b2;
        if (z) {
            if (i2 == 2) {
                j2 = (bArr[i + 0] & 255) << 8;
                b2 = bArr[i + 1];
            } else if (i2 == 4) {
                j2 = ((bArr[i + 0] & 255) << 24) | ((bArr[i + 1] & 255) << 16) | ((bArr[i + 2] & 255) << 8);
                b2 = bArr[i + 3];
            } else {
                if (i2 != 8) {
                    return 0L;
                }
                j2 = ((bArr[i + 3] & 255) << 32) | ((bArr[i + 1] & 255) << 48) | ((bArr[i + 0] & 255) << 56) | ((bArr[i + 2] & 255) << 40) | ((bArr[i + 4] & 255) << 24) | ((bArr[i + 5] & 255) << 16) | ((bArr[i + 6] & 255) << 8);
                b2 = bArr[i + 7];
            }
            return j2 | ((b2 & 255) << 0);
        }
        if (i2 == 2) {
            j = (bArr[i + 1] & 255) << 8;
            b = bArr[i + 0];
        } else if (i2 == 4) {
            j = ((bArr[i + 3] & 255) << 24) | ((bArr[i + 2] & 255) << 16) | ((bArr[i + 1] & 255) << 8);
            b = bArr[i + 0];
        } else {
            if (i2 != 8) {
                return 0L;
            }
            j = ((bArr[i + 4] & 255) << 32) | ((bArr[i + 6] & 255) << 48) | ((bArr[i + 7] & 255) << 56) | ((bArr[i + 5] & 255) << 40) | ((bArr[i + 3] & 255) << 24) | ((bArr[i + 2] & 255) << 16) | ((bArr[i + 1] & 255) << 8);
            b = bArr[i + 0];
        }
        return j | ((b & 255) << 0);
    }
}
