package com.basicdroid.app.application;

import android.util.Log;

import com.basicdroid.app.libs.debug.FakeCrashLibrary;
import com.basicdroid.app.libs.debug.Timber;
import com.basicdroid.app.util.CONST;

import io.paperdb.BuildConfig;
import io.paperdb.Paper;

/**
 * Created by techierishi on 8/11/15.
 */
public class GlobalClasss extends MyApplication implements CONST{

    @Override
    public void onCreate() {
        super.onCreate();
        Paper.init(getApplicationContext());

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }


    }
    /** A tree which logs important information for crash reporting. */
    private static class CrashReportingTree extends Timber.Tree {
        @Override protected void log(int priority, String tag, String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }

            FakeCrashLibrary.log(priority, tag, message);

            if (t != null) {
                if (priority == Log.ERROR) {
                    FakeCrashLibrary.logError(t);
                } else if (priority == Log.WARN) {
                    FakeCrashLibrary.logWarning(t);
                }
            }
        }
    }
}
