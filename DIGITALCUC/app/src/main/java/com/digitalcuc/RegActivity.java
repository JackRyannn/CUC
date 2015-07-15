package com.digitalcuc;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.digitalcuc.service.SyncHttp;

public class RegActivity extends Activity {
	private Button btn_act=null;
	private Button btn_bac=null;
	private EditText edit_1;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reg);
		
			
			btn_act=(Button)findViewById(R.id.button1);
			btn_bac=(Button)findViewById(R.id.button2);
			edit_1=(EditText)findViewById(R.id.editText1);

			
			btn_act.setOnClickListener(new OnClickListener(){
				
	            @Override
	            public void onClick(View v) {
	            	String numStr=edit_1.getText().toString();	   
	            	RegActTask task=new RegActTask();
	            	task.execute(numStr);
//
	            }
	            
	        }
			
			);
			btn_bac.setOnClickListener(new OnClickListener(){
				
	            @Override
	            public void onClick(View v) {
	            	
	                Intent intent = new Intent();
	                intent.setClass(RegActivity.this, LogActivity.class);
	                startActivity(intent);
	            } 
	        }
	        );
			
	}
	private class RegActTask extends AsyncTask<String, Integer, Integer>{
		
		@Override
		protected Integer doInBackground(String... params){
			return RegAct(params[0]);
			 
		}

		@Override
		protected void onPostExecute(Integer result){
			switch(result){
			case 1:
			String INFO="111";
			Toast t1= Toast.makeText(getApplicationContext(), INFO, Toast.LENGTH_SHORT);
        	t1.show();
        	Intent intent = new Intent();
            intent.setClass(RegActivity.this, LogActivity.class);
            startActivity(intent);
            break;
			case 2:
				INFO="222";
				t1= Toast.makeText(getApplicationContext(), INFO, Toast.LENGTH_SHORT);
	        	t1.show();	        	
	        break;
			case 3:
				INFO="333";
				t1= Toast.makeText(getApplicationContext(), INFO, Toast.LENGTH_SHORT);
	        	t1.show();	        	
	        break;
			case 4:
				INFO="444";
				t1= Toast.makeText(getApplicationContext(), INFO, Toast.LENGTH_SHORT);
	        	t1.show();	        	
	        break;
			}
		}
	}
	
	public int RegAct(String num){
		String url="/getActInfo";
		String params="num="+num;
		SyncHttp syncHttp = new SyncHttp();
		int retCode=4;
		String retStr;
		try{
			retStr = syncHttp.httpGet(url, params);
			JSONObject jsonObject = new JSONObject(retStr);
			retCode = jsonObject.getInt("ret");
		} catch(Exception e){
			e.printStackTrace();
		}
		return retCode;
		
	}
}
	
