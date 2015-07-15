package com.digitalcuc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ActivityActivity extends Activity{
	Context mContext;
	private ImageButton btn_left=null;
	private ImageButton btn_right=null;
	private ImageView iv = null;
	@Override
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity);
		iv = (ImageView)findViewById(R.id.imageView1);
		btn_left=(ImageButton)findViewById(R.id.imgbtn_l);
		btn_right=(ImageButton)findViewById(R.id.imgbtn_r);
		btn_left.setOnClickListener(new OnClickListener(){
			
            @Override
            public void onClick(View v) {
            	iv.setImageResource(R.drawable.post);
            } 
        }
        );		
		btn_right.setOnClickListener(new OnClickListener(){
			
            @Override
            public void onClick(View v) {
            	iv.setImageResource(R.drawable.post2);
            } 
        }
        );
		mContext=this;
		BottomBtn Btn=(BottomBtn)findViewById(R.id.bottombtn);
		Btn.sendContext(mContext);
	}

}

