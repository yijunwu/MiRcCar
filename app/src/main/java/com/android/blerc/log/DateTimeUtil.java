package com.android.blerc.log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/* loaded from: classes.dex */
public class DateTimeUtil {
    public static final SimpleDateFormat format_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    public static final SimpleDateFormat format_yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
    public static final SimpleDateFormat format_yyyy_MM_dd_HH_mm_ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat format_yyyy_MM_dd_HH_mm_ss_SSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    public static final SimpleDateFormat format_MMddHHmmssSSS = new SimpleDateFormat("MMddHHmmssSSS");
    public static final SimpleDateFormat format_yyyMMddHHmmssSSS = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    public static int getQuarterByMonth(int i) {
        int i2 = (i < 1 || i > 3) ? 0 : 1;
        if (i >= 4 && i <= 6) {
            i2 = 2;
        }
        int i3 = (i < 7 || i > 9) ? i2 : 3;
        if (i < 10 || i > 12) {
            return i3;
        }
        return 4;
    }

    public static String format(String str) {
        return format(new Date(), str);
    }

    public static String format(String str, int i, int i2) {
        return format(new Date(), str, i, i2);
    }

    public static String format(Date date, String str) {
        return format(date, str, -1, 0);
    }

    public static String format(long j, String str) {
        return format(new Date(j), str, -1, 0);
    }

    public static String format(Date date, String str, int i, int i2) {
        if (date == null) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (i != -1) {
            calendar.add(i, i2);
        }
        return new SimpleDateFormat(str).format(calendar.getTime());
    }

    public static Date parse(String str, String str2) throws ParseException {
        return new SimpleDateFormat(str2).parse(str);
    }

    public static Date offsetDay(int i) {
        return offsetTime(new Date(), 0, 0, i, 0, 0, 0);
    }

    public static Date offsetWeek(int i) {
        return offsetTime(new Date(), 0, 0, i * 7, 0, 0, 0);
    }

    public static Date offsetMonth(int i) {
        return offsetTime(new Date(), 0, i, 0, 0, 0, 0);
    }

    public static Date offsetQuarter(int i) {
        return offsetTime(new Date(), 0, i * 3, 0, 0, 0, 0);
    }

    public static Date offsetYear(int i) {
        return offsetTime(new Date(), i, 0, 0, 0, 0, 0);
    }

    public static Date offsetDay(Date date, int i) {
        return offsetTime(date, 0, 0, i, 0, 0, 0);
    }

    public static Date offsetWeek(Date date, int i) {
        return offsetTime(date, 0, 0, i * 7, 0, 0, 0);
    }

    public static Date offsetMonth(Date date, int i) {
        return offsetTime(date, 0, i, 0, 0, 0, 0);
    }

    public static Date offsetQuarter(Date date, int i) {
        return offsetTime(date, 0, i * 3, 0, 0, 0, 0);
    }

    public static Date offsetYear(Date date, int i) {
        return offsetTime(date, i, 0, 0, 0, 0, 0);
    }

    public static Date offsetTime(int i) {
        return offsetTime(new Date(), 0, 0, 0, 0, 0, i);
    }

    public static Date offsetTime(int i, int i2) {
        return offsetTime(new Date(), 0, 0, 0, 0, i, i2);
    }

    public static Date offsetTime(int i, int i2, int i3) {
        return offsetTime(new Date(), 0, 0, 0, i, i2, i3);
    }

    public static Date offsetTime(int i, int i2, int i3, int i4) {
        return offsetTime(new Date(), 0, 0, i, i2, i3, i4);
    }

    public static Date offsetTime(int i, int i2, int i3, int i4, int i5) {
        return offsetTime(new Date(), 0, i, i2, i3, i4, i5);
    }

    public static Date offsetTime(int i, int i2, int i3, int i4, int i5, int i6) {
        return offsetTime(new Date(), i, i2, i3, i4, i5, i6);
    }

    public static Date offsetTime(Date date, int i) {
        return offsetTime(date, 0, 0, 0, 0, 0, i);
    }

    public static Date offsetTime(Date date, int i, int i2) {
        return offsetTime(date, 0, 0, 0, 0, i, i2);
    }

    public static Date offsetTime(Date date, int i, int i2, int i3) {
        return offsetTime(date, 0, 0, 0, i, i2, i3);
    }

    public static Date offsetTime(Date date, int i, int i2, int i3, int i4) {
        return offsetTime(date, 0, 0, i, i2, i3, i4);
    }

    public static Date offsetTime(Date date, int i, int i2, int i3, int i4, int i5) {
        return offsetTime(date, 0, i, i2, i3, i4, i5);
    }

    public static Date offsetTime(Date date, int i, int i2, int i3, int i4, int i5, int i6) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(1, i);
        calendar.add(2, i2);
        calendar.add(5, i3);
        calendar.add(11, i4);
        calendar.add(12, i5);
        calendar.add(13, i6);
        return calendar.getTime();
    }

    public static Date specifyDay(Date date, int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(5, i);
        return calendar.getTime();
    }

    public static Date specifyTime(int i) {
        return specifyTime(new Date(), -1, -1, -1, -1, -1, i);
    }

    public static Date specifyTime(int i, int i2) {
        return specifyTime(new Date(), -1, -1, -1, -1, i, i2);
    }

    public static Date specifyTime(int i, int i2, int i3) {
        return specifyTime(new Date(), -1, -1, -1, i, i2, i3);
    }

    public static Date specifyTime(int i, int i2, int i3, int i4) {
        return specifyTime(new Date(), -1, -1, i, i2, i3, i4);
    }

    public static Date specifyTime(int i, int i2, int i3, int i4, int i5) {
        return specifyTime(new Date(), -1, i, i2, i3, i4, i5);
    }

    public static Date specifyTime(int i, int i2, int i3, int i4, int i5, int i6) {
        return specifyTime(new Date(), i, i2, i3, i4, i5, i6);
    }

    public static Date specifyTime(Date date, int i) {
        return specifyTime(date, -1, -1, -1, -1, -1, i);
    }

    public static Date specifyTime(Date date, int i, int i2) {
        return specifyTime(date, -1, -1, -1, -1, i, i2);
    }

    public static Date specifyTime(Date date, int i, int i2, int i3) {
        return specifyTime(date, -1, -1, -1, i, i2, i3);
    }

    public static Date specifyTime(Date date, int i, int i2, int i3, int i4) {
        return specifyTime(date, -1, -1, i, i2, i3, i4);
    }

    public static Date specifyTime(Date date, int i, int i2, int i3, int i4, int i5) {
        return specifyTime(date, -1, i, i2, i3, i4, i5);
    }

    public static Date specifyTime(Date date, int i, int i2, int i3, int i4, int i5, int i6) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (i != -1) {
            calendar.set(1, i);
        }
        if (i2 != -1) {
            calendar.set(2, i2);
        }
        if (i3 != -1) {
            calendar.set(5, i3);
        }
        if (i4 != -1) {
            calendar.set(11, i4);
        }
        if (i5 != -1) {
            calendar.set(12, i5);
        }
        if (i6 != -1) {
            calendar.set(13, i6);
        }
        return calendar.getTime();
    }

    public static Date getStartTimeOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        return calendar.getTime();
    }

    public static Date getEndTimeOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(11, 23);
        calendar.set(12, 59);
        calendar.set(13, 59);
        return calendar.getTime();
    }

    public static Date getStartTimeOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(7, 2);
        calendar.setFirstDayOfWeek(2);
        return calendar.getTime();
    }

    public static Date getEndTimeOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(11, 23);
        calendar.set(12, 59);
        calendar.set(13, 59);
        calendar.set(7, 1);
        calendar.setFirstDayOfWeek(2);
        return calendar.getTime();
    }

    public static Date getStartTimeOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(5, 1);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        return calendar.getTime();
    }

    public static Date getEndTimeOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(5, calendar.getActualMaximum(5));
        calendar.set(11, 23);
        calendar.set(12, 59);
        calendar.set(13, 59);
        return calendar.getTime();
    }

    public static Date getStartTimeOfMonth(String str, String str2) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new SimpleDateFormat(str2).parse(str));
        calendar.set(5, 1);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        return calendar.getTime();
    }

    public static Date getEndTimeOfMonth(String str, String str2) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new SimpleDateFormat(str2).parse(str));
        calendar.set(5, calendar.getActualMaximum(5));
        calendar.set(11, 23);
        calendar.set(12, 59);
        calendar.set(13, 59);
        return calendar.getTime();
    }

    public static Date getStartTimeOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(2, 0);
        calendar.set(5, 1);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        return calendar.getTime();
    }

    public static Date getEndTimeOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int actualMaximum = calendar.getActualMaximum(5);
        calendar.set(2, 11);
        calendar.set(5, actualMaximum);
        calendar.set(11, 23);
        calendar.set(12, 59);
        calendar.set(13, 59);
        return calendar.getTime();
    }

    public long getDistDates(Date date, Date date2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        long timeInMillis = calendar.getTimeInMillis();
        calendar.setTime(date2);
        return Math.abs(calendar.getTimeInMillis() - timeInMillis) / 86400000;
    }

    public static int getWeekOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(2);
        calendar.setTime(date);
        return calendar.get(3);
    }

    public static int getMonthByWeek(int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(3, i);
        calendar.set(7, 2);
        calendar.setFirstDayOfWeek(2);
        return calendar.get(2) + 1;
    }

    public static int getQuarterOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int i = calendar.get(2) + 1;
        int i2 = (i < 7 || i > 9) ? (i < 4 || i > 6) ? (i < 1 || i > 3) ? 0 : 1 : 2 : 3;
        if (i < 10 || i > 12) {
            return i2;
        }
        return 4;
    }

    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(1);
    }

    public static int getQuarterByWeek(int i) {
        return getQuarterByMonth(getMonthByWeek(i));
    }

    public static void main(String[] strArr) throws ParseException {
        System.out.println(getWeekOfYear(new Date()));
        System.out.println(getMonthByWeek(38));
        System.out.println(getStartTimeOfWeek(new Date()));
        System.out.println(getEndTimeOfWeek(new Date()));
        System.out.println(getQuarterOfYear(new Date()));
        System.out.println(getYear(new Date()));
        System.out.println(format(parse("2014-01-01", "yyyy-MM-dd"), "yyyy-w"));
        System.out.println(parse("2014-10-01", "yyyy-MM-dd").getTime());
        System.out.println(format("yyyy"));
        System.out.println(format("yyyy-M"));
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(1438591770000L)));
    }
}
