package com.digitalcuc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
public class AllmapActivity extends Activity {
	private Button btn1;
	Context mContext;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_allmap);
		btn1 = (Button)findViewById(R.id.science_area);
		mContext=this;
		BottomBtn Btn=(BottomBtn)findViewById(R.id.bottombtn);
		Btn.sendContext(mContext);

		 btn1.setOnClickListener(new OnClickListener() {  
	    	  
		    	@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setClass(getApplicationContext(), LibActivity.class);
					startActivity(intent);
				}
	        });  
		 
	}
}