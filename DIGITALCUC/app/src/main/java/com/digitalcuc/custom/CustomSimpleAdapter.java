package com.digitalcuc.custom;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class CustomSimpleAdapter extends SimpleAdapter {
	public CustomSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to){
		super(context, data, resource, from, to);
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		View v = super.getView(position, convertView, parent);
		
		if(position == 0){
			TextView categoryTitle = (TextView) v;
//			categoryTitle.setBackgroundColor(R.color.white);
			categoryTitle.setTextColor(0xffffffff);
		}
		return v;
	}
	
	
}
