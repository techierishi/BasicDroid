package com.basicdroid.app.application;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class MyApplication extends Application {

	private static MyApplication mInstance;
	private static Context mAppContext;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		this.setAppContext(this);
	}

	public static synchronized MyApplication getInstance() {
		Log.d("App Controller", "" + mInstance);
		if (mInstance != null)
			return mInstance;
		else
			return mInstance = new MyApplication();
	}


	public static Context getAppContext() {
		return mAppContext;
	}
	public void setAppContext(Context mAppContext) {
		this.mAppContext = mAppContext;
	}
}
