package com.digitalcuc;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalcuc.service.SyncHttp;

public class LibActivity extends Activity {
	private ImageButton btn_11=null;
	private ImageButton btn_12=null;
	private ImageButton btn_13=null;
	private ImageButton btn_14=null;
	private ImageButton btn_21=null;
	private ImageButton btn_22=null;
	private ImageButton btn_23=null;
	private ImageButton btn_24=null;
	private Button btn_flush=null;
	private TextView tv1=null;
	private isLoadDataListener loadLisneter;
	private int s=0;
	private int s2=0;
	private int style=0;
	private String numStr=null;
	private int count = 0;
	Context mContext;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lib);
		mContext=this;
		BottomBtn Btn=(BottomBtn)findViewById(R.id.bottombtn);
		Btn.sendContext(mContext);
			btn_11=(ImageButton)findViewById(R.id.imageButton11);
			btn_12=(ImageButton)findViewById(R.id.ImageButton12);
			btn_13=(ImageButton)findViewById(R.id.ImageButton13);
			btn_14=(ImageButton)findViewById(R.id.ImageButton14);
			btn_21=(ImageButton)findViewById(R.id.ImageButton21);
			btn_22=(ImageButton)findViewById(R.id.ImageButton22);
			btn_23=(ImageButton)findViewById(R.id.ImageButton23);
			btn_24=(ImageButton)findViewById(R.id.ImageButton24);
			btn_flush=(Button)findViewById(R.id.imageButton);
			tv1=(TextView)findViewById(R.id.total);
			setLoadDataComplete(new isLoadDataListener() {
                @Override
                public void loadComplete() {
                	s+=1;
                    // TODO Auto-generated method stub
                	if(s2==1){
                		switch(s){           	
    				case 1:
    					btn_11.setImageResource(R.drawable.seat);
    					break;
    				case 2:
    					btn_12.setImageResource(R.drawable.seat);
    					break;
    				case 3:
    					btn_13.setImageResource(R.drawable.seat);
    					break;
    				case 4:
    					btn_14.setImageResource(R.drawable.seat);
    					break;
    				case 5:
    					btn_21.setImageResource(R.drawable.seat);
    					break;
    				case 6:
    					btn_22.setImageResource(R.drawable.seat);
    					break;
    				case 7:
    					btn_23.setImageResource(R.drawable.seat);
    					break;
    				case 8:
    					btn_24.setImageResource(R.drawable.seat);
    					break;
    				}
                	}else{
                		switch(s){
        				case 1:
        					btn_11.setImageResource(R.drawable.nonseat);
        					break;
        				case 2:
        					btn_12.setImageResource(R.drawable.nonseat);
        					break;
        				case 3:
        					btn_13.setImageResource(R.drawable.nonseat);
        					break;
        				case 4:
        					btn_14.setImageResource(R.drawable.nonseat);
        					break;
        				case 5:
        					btn_21.setImageResource(R.drawable.nonseat);
        					break;
        				case 6:
        					btn_22.setImageResource(R.drawable.nonseat);
        					break;
        				case 7:
        					btn_23.setImageResource(R.drawable.nonseat);
        					break;
        				case 8:
        					btn_24.setImageResource(R.drawable.nonseat);
        					break;
        				}
                	}
                }
            });
			
			numStr="1";	
        	RegActTask task=new RegActTask();
        	task.execute(numStr);
        	numStr="2";	   
        	task=new RegActTask();
        	task.execute(numStr);
        	numStr="3";	   
        	task=new RegActTask();
        	task.execute(numStr);
        	numStr="4";	   
        	task=new RegActTask();
        	task.execute(numStr);
        	numStr="5";	   
        	task=new RegActTask();
        	task.execute(numStr);
        	numStr="6";	   
        	task=new RegActTask();
        	task.execute(numStr);
        	numStr="7";	   
        	task=new RegActTask();
        	task.execute(numStr);
        	numStr="8";	   
        	task=new RegActTask();
        	task.execute(numStr);

        	btn_flush.setOnClickListener(new OnClickListener(){				
	            @Override
	            public void onClick(View v) {
	            	Intent intent = new Intent();
					intent.setClass(LibActivity.this,LibActivity.class);
					startActivity(intent);
	            }	            
	        });
        	
			btn_11.setOnClickListener(new OnClickListener(){				
	            @Override
	            public void onClick(View v) {
	            	s = 0;
	            	style = 1;
	            	String numStr="1";	   
	            	RegActTask task=new RegActTask();
	            	task.execute(numStr); 
	            }	            
	        });
			btn_12.setOnClickListener(new OnClickListener(){				
	            @Override
	            public void onClick(View v) {
	            	s = 1;
	            	style = 1;
	            	String numStr="2";	   
	            	RegActTask task=new RegActTask();
	            	task.execute(numStr); 
	            	}	            
	        });
			btn_13.setOnClickListener(new OnClickListener(){				
	            @Override
	            public void onClick(View v) {
	            	s = 2;
	            	style = 1;
	            	String numStr="3";	   
	            	RegActTask task=new RegActTask();
	            	task.execute(numStr); 
	            	}	            
	        });
			btn_14.setOnClickListener(new OnClickListener(){				
	            @Override
	            public void onClick(View v) {
	            	s = 3;
	            	style = 1;
	            	String numStr="4";	   
	            	RegActTask task=new RegActTask();
	            	task.execute(numStr); 
	            	}	            
	        });
			btn_21.setOnClickListener(new OnClickListener(){				
	            @Override
	            public void onClick(View v) {
	            	s = 4;
	            	style = 1;
	            	String numStr="5";	   
	            	RegActTask task=new RegActTask();
	            	task.execute(numStr); 
	            	}	            
	        });
			btn_22.setOnClickListener(new OnClickListener(){				
	            @Override
	            public void onClick(View v) {
	            	s = 5;
	            	style = 1;
	            	String numStr="6";	   
	            	RegActTask task=new RegActTask();
	            	task.execute(numStr); 
	            	}	            
	        });
			btn_23.setOnClickListener(new OnClickListener(){				
	            @Override
	            public void onClick(View v) {
	            	s = 6;
	            	style = 1;
	            	String numStr="7";	   
	            	RegActTask task=new RegActTask();
	            	task.execute(numStr);
	            	}	            
	        });
			btn_24.setOnClickListener(new OnClickListener(){				
	            @Override
	            public void onClick(View v) {
	            	s = 7;
	            	style = 1;
	            	String numStr="8";	   
	            	RegActTask task=new RegActTask();
	            	task.execute(numStr); }        
	        });
			
			
	}
	private class RegActTask extends AsyncTask<String, Integer, Integer>{
		
		@Override
		protected Integer doInBackground(String... params){
			return RegAct((String)params[0]);		 
		}

		@Override
		protected void onPostExecute(Integer result){
			switch(result){
			case 1:		
			s2=1;
			count += 1;
			if(numStr.equals("8")){
	        	tv1.setText(""+count);
			}
			String INFO="WrongCode";
			Toast t1= Toast.makeText(getApplicationContext(), INFO, Toast.LENGTH_SHORT);
			if(style==1){
        	t1.show();
			}
            break;
			case 3:
				s2=2;	
				if(style==1){
					count -= 1;
			        tv1.setText(""+count);	
	        	  }      	
	        break;
			case 2:
				if(style==1){
									
				INFO="WrongCode2";
				t1= Toast.makeText(getApplicationContext(), INFO, Toast.LENGTH_SHORT);
	        	t1.show();	}        	
	        break;
			case 4:
				INFO="WrongCode3";
				t1= Toast.makeText(getApplicationContext(), INFO, Toast.LENGTH_SHORT);
	        	t1.show();	        	
	        break;
			}
			if (loadLisneter != null) {
                loadLisneter.loadComplete();
            }
		}
	}

	public int RegAct(String num){
		String url="/getLibInfo";
		String params="num="+num+"&style="+style;
		SyncHttp syncHttp = new SyncHttp();
		int retCode=4;

		String retStr=null;
		try{
			retStr = syncHttp.httpGet(url, params);
			JSONObject jsonObject = new JSONObject(retStr);
			retCode = jsonObject.getInt("ret");
		} catch(Exception e){
			e.printStackTrace();
		}
		return retCode;

	}
	private interface isLoadDataListener {
         void loadComplete();
    }
	
	public void setLoadDataComplete(isLoadDataListener dataComplete) {
        this.loadLisneter = dataComplete;
    }
	
}
	
