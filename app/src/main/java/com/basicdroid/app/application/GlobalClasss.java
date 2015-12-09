package com.basicdroid.app.application;

import android.app.Application;

import com.basicdroid.app.util.CONST;
import io.paperdb.Paper;

/**
 * Created by techierishi on 8/11/15.
 */
public class GlobalClasss extends Application implements CONST{

    @Override
    public void onCreate() {
        super.onCreate();
        Paper.init(getApplicationContext());

    }

}
