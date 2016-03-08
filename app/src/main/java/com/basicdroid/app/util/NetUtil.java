package com.basicdroid.app.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 3/8/2016.
 */
public class NetUtil {

    public static boolean isNA(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    public static void checkNet(Context context, OnNetAvailableListener listener) {
        if (null != listener) {
            if (isNA(context)) {
                listener.onNetAvailable();

            } else {

                PopUps.showToast(context,"Internet Not Available !");
            }
        }
    }

    public interface OnNetAvailableListener {
        void onNetAvailable();
    }

}
