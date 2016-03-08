package com.basicdroid.app.util;

/**
 * Created by Administrator on 2/16/2016.
 */
public class ParseUtil {

    public static Double parseDouble(String str) {
        try {
            if (str.isEmpty()) {
                return 0.0;
            } else {
                return Double.parseDouble(str);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0D;
    }

    public static Integer parseInt(String str) {
        try {
            if (str.isEmpty()) {
                return 0;
            } else {
                return Integer.parseInt(str);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Long parseLong(String str) {
        try {
            if (str.isEmpty()) {
                return (long) 0;
            } else {
                return Long.parseLong(str);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0L;
    }

}
