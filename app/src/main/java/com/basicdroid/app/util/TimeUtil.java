package com.basicdroid.app.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2/16/2016.
 */
public class TimeUtil {

    public static String getFormattedTimeHM(long seconds) {

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis((long) seconds * 1000);

        String hrStr = "" + cal.get(Calendar.HOUR_OF_DAY);
        String mnStr = "" + cal.get(Calendar.MINUTE);

        return hrStr + " : " + mnStr;

    }

    public static String getHMSfromSeconds(long seconds, boolean showDeconds) {

        long hr = seconds / 3600;
        long rem = seconds % 3600;
        long mn = rem / 60;
        long sec = rem % 60;
        String hrStr = "";
        String mnStr = "";
        String secStr = "";
        if (hr > 0) {
            hrStr = (hr < 10 ? "0" : "") + hr + "h ";
        }
        if (mn > 0) {
            mnStr = (mn < 10 ? "0" : "") + mn + "m ";
        }
        secStr = (sec < 10 ? "0" : "") + sec + "s ";

        if (showDeconds) {
            return hrStr + mnStr + secStr;
        }

        return hrStr + mnStr;
    }

    public static long hhMmSsToSeconds(String hhmmss) {

        long seconds = 0;
        if (hhmmss != null && !hhmmss.isEmpty()) {
            String[] s_arr = hhmmss.split(":");
            int str_h = ParseUtil.parseInt(s_arr[0].trim());
            int str_m = ParseUtil.parseInt(s_arr[1].trim());
            int str_s = ParseUtil.parseInt(s_arr[2].trim());

            seconds = str_h * 3600 + str_m * 60 + str_s;

        }
        return seconds;
    }


    public static long getUTS(String str_date) {

        try {
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date date;
            date = (Date) formatter.parse(str_date);

            return (date.getTime() / 1000L);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getUTS(String str_date, String format) {

        try {
            DateFormat formatter = new SimpleDateFormat(format);
            Date date;
            date = (Date) formatter.parse(str_date);

            return (date.getTime() / 1000L);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getUTSWithHHmmSS(String str_date) {

        try {
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date;
            date = (Date) formatter.parse(str_date);

            return (date.getTime() / 1000L);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getUTS_from_YYYYMMddHHmmSS(String str_date) {

        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date;
            date = formatter.parse(str_date);

            return (date.getTime() / 1000L);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean isDateOk(String dstr) {
        return dstr.matches("([0-9]{2})-([0-9]{2})-([0-9]{4})");
    }

    public static String dateFromUTS(long timeStamp) {
        String UNIX_DATE_FORMAT = "dd-MM-yyyy";
        SimpleDateFormat formatter = new SimpleDateFormat(UNIX_DATE_FORMAT);
        Date time = new Date((long) timeStamp * 1000L);

        return formatter.format(time);
    }

    public static String month_dd_yyyyFromUTS(long timeStamp) {
        String UNIX_DATE_FORMAT = "MMMM dd, yyyy";
        SimpleDateFormat formatter = new SimpleDateFormat(UNIX_DATE_FORMAT);
        Date time = new Date((long) timeStamp * 1000L);

        return formatter.format(time);
    }

    public static String YYYY_mm_dd(long timeStamp) {
        String SERVER_DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(SERVER_DATE_FORMAT);
        Date time = new Date((long) timeStamp * 1000L);

        return formatter.format(time);
    }

    public static String fromStrToStr(String fromFormat, String toFormat, String dateTimeStr) {
        String strDt = "";
        SimpleDateFormat format = new SimpleDateFormat("" + fromFormat);
        try {
            Date date = format.parse(dateTimeStr);

            format = new SimpleDateFormat("" + toFormat);

            strDt = format.format(date);
            System.out.println("Date : " + date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return strDt;
    }

    public static String fromDateToStr(Date date, String toFormat) {
        String strDt = "";
        SimpleDateFormat format;
        try {

            format = new SimpleDateFormat("" + toFormat);

            strDt = format.format(date);
            System.out.println("Date : " + date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return strDt;
    }

    public static Date firstDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        Date date = new Date();
        date.setTime(cal.getTimeInMillis());

        return date;

    }

    public static Date  lastDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek() + 6);
        Date date = new Date();
        date.setTime(cal.getTimeInMillis());

        return date;

    }

    public static String currentYear() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        Date date = new Date();
        date.setTime(cal.getTimeInMillis());

        return fromDateToStr(date, "YYYY");

    }

    public static long getTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }
}
