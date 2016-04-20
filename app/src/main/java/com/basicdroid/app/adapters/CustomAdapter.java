package com.basicdroid.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.basicdroid.app.R;
import com.basicdroid.app.models.RowItem;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

	Context context;
	List<RowItem> rowItems;

	public CustomAdapter(Context context, List<RowItem> rowItems) {

		this.context = context;
		this.rowItems = rowItems;

	}

	@Override
	public int getCount() {

		return rowItems.size();
	}

	@Override
	public Object getItem(int position) {

		return rowItems.get(position);
	}

	@Override
	public long getItemId(int position) {

		return rowItems.indexOf(getItem(position));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item, null);
		}

		TextView temp_tv, minmax_tv, desc_tv, date_tv;

		ImageView niv;

		temp_tv = (TextView) convertView.findViewById(R.id.ftemp);
		minmax_tv = (TextView) convertView.findViewById(R.id.fmin_max);
		desc_tv = (TextView) convertView.findViewById(R.id.fdesc);
		date_tv = (TextView) convertView.findViewById(R.id.fdate);
		niv = (ImageView) convertView.findViewById(R.id.ficon);

		RowItem item = rowItems.get(position);

		temp_tv.setText(item.getTemp() + "\u2103");
		minmax_tv.setText(item.getTemp_min() + "\u2103" + "\\"
				+ item.getTemp_min() + "\u2103");
		desc_tv.setText(item.getDescription() + "");
		date_tv.setText(item.getMdate() + "");

		String icon_url = "http://openweathermap.org/img/w/" + item.getIcon()
				+ ".png";

		ImageLoader imageLoader = ImageLoader.getInstance(); // Get singleton instance
		DisplayImageOptions userimgoptions = new DisplayImageOptions.Builder()
				.showImageOnLoading(android.R.color.transparent)
				.cacheInMemory(true).cacheOnDisc(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		imageLoader.displayImage(icon_url, niv, userimgoptions);


		return convertView;
	}

}
