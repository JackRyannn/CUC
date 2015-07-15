package com.digitalcuc;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

public class BottomBtn extends LinearLayout {
		
	private ImageView iv_toggle;
	private ImageView iv_plus;
	private PopupWindow popWindow; 
	private Context  mContext;
	 
		    public BottomBtn(Context context) {
		
		        super(context);
		        
		    }
		
		    public BottomBtn(Context context, AttributeSet attrs) {
		
		        super(context, attrs);
		
		
		        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		        inflater.inflate(R.layout.btn_bottom, this);
		        setupView();

		
		    }
		    public void sendContext(Context context)
		    {		     
		    	mContext=context;	    	
		    }
		    private void setupView() {		
				iv_toggle = (ImageView) findViewById(R.id.toggle_btn);
				iv_plus = (ImageView) findViewById(R.id.plus_btn);
			
		    iv_toggle.setOnClickListener(new OnClickListener() {  
		    	  
		    	@Override
				public void onClick(View v) {
					switch (v.getId()) {
					case R.id.toggle_btn:
						clickPlusBtn();
						break;
					default:
						break;
					}
				}
	        });  
		    }
			private void clickPlusBtn() {
				showPopupWindow(iv_toggle);
				iv_toggle.setImageResource(R.drawable.toolbar_btn_pressed);
				iv_plus.setImageResource(R.drawable.toolbar_plusback);	
			}
			private void restorePlusBtn() {
				iv_toggle.setImageResource(R.drawable.toolbar_btn_normal);
				iv_plus.setImageResource(R.drawable.toolbar_plus);
			}
			@SuppressWarnings("deprecation")
			private void showPopupWindow(View parent) {
				
				if (popWindow == null) {
					LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					View view = layoutInflater.inflate(R.layout.write_tab, null);
					TextView t1=(TextView)view.findViewById(R.id.textView1);
					TextView t2=(TextView)view.findViewById(R.id.textView2);
					TextView t3=(TextView)view.findViewById(R.id.textView3);
					TextView t4=(TextView)view.findViewById(R.id.textView4);
					TextView t5=(TextView)view.findViewById(R.id.textView5);
					TextView t6=(TextView)view.findViewById(R.id.textView6);
				    t1.setOnClickListener(new OnClickListener()
				    {
				    	@Override
				    	public void onClick(View v){
				    		Intent intent = new Intent();
				    		intent.setClass(v.getContext(), NewsActivity.class);
				    		v.getContext().startActivity(intent);
				    	}
				    });
				    t2.setOnClickListener(new OnClickListener()
			    {
			    	@Override
			    	public void onClick(View v){
			    		Intent intent = new Intent();
			    		intent.setClass(v.getContext(), ActivityActivity.class);
			    		v.getContext().startActivity(intent);
			    	}
			    });
			    t3.setOnClickListener(new OnClickListener()
			    {
			    	@Override
			    	public void onClick(View v){
			    		Intent intent = new Intent();
			    		intent.putExtra("cid", 1);
			    		intent.setClass(v.getContext(), BbsActivity.class);
			    		v.getContext().startActivity(intent);
			    	}
			    });
			    t4.setOnClickListener(new OnClickListener()
			    {
			    	@Override
			    	public void onClick(View v){
			    		Intent intent = new Intent();
			    		intent.setClass(v.getContext(), MapActivity.class);
			    		v.getContext().startActivity(intent);
			    	}
			    });
			    t5.setOnClickListener(new OnClickListener()
			    {
			    	@Override
			    	public void onClick(View v){
			    		Intent intent = new Intent();
			    		intent.setClass(v.getContext(), AllmapActivity.class);
			    		v.getContext().startActivity(intent);
			    	}
			    });
			    t6.setOnClickListener(new OnClickListener()
			    {
			    	@Override
			    	public void onClick(View v){
			    		Intent intent = new Intent();
			    		intent.setClass(v.getContext(), IActivity.class);
			    		v.getContext().startActivity(intent);
			    	}
			    });
			    popWindow = new PopupWindow(view,LinearLayout.LayoutParams.MATCH_PARENT,640);
				}
				
				popWindow.setFocusable(true);
				popWindow.setTouchable(true); 
				popWindow.setOutsideTouchable(true);
				popWindow.setBackgroundDrawable(new BitmapDrawable());
				popWindow.showAsDropDown(parent, Gravity.CENTER_HORIZONTAL,0,100);
				popWindow.setOnDismissListener(new OnDismissListener() {
		            @Override  
		            public void onDismiss() {  
		                restorePlusBtn();               
		            }  
		        }); 
			}

}
