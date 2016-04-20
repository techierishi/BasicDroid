package com.basicdroid.app.application;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;


public class MyApplication extends Application {

	private static MyApplication mInstance;
	private static Context mAppContext;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		initImageLoader();
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

	public void initImageLoader() {
		ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
				this).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.tasksProcessingOrder(QueueProcessingType.FIFO)
				// .imageDownloader(DownloadModule.getCustomImageDownaloder(this))
				// .discCacheFileNameGenerator(new FileNameGenerator() {
				// @Override
				// public String generate(String imageUri) {
				// return Utils.getNostraImageFileName(imageUri);
				// }
				// })
				.memoryCache(new WeakMemoryCache());

		com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(builder.build());
	}
}
