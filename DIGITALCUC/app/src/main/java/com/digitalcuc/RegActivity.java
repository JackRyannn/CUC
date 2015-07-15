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
	private EditText edit_1;//学号
//	private EditText edit_2;//昵称
//	private EditText edit_3;//密码
//	private EditText edit_4;//手机号
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reg);
		
			
			btn_act=(Button)findViewById(R.id.button1);
			btn_bac=(Button)findViewById(R.id.button2);
			edit_1=(EditText)findViewById(R.id.editText1);
//			edit_2=(EditText)findViewById(R.id.editText2);
//			edit_3=(EditText)findViewById(R.id.editText3);
//			edit_4=(EditText)findViewById(R.id.editText4);
			
			
			btn_act.setOnClickListener(new OnClickListener(){
				
	            @Override
	            public void onClick(View v) {
	            	String numStr=edit_1.getText().toString();	   
//	            	String nickname=edit_2.getText().toString();
//	            	String password=edit_3.getText().toString();
//	            	String phone=edit_4.getText().toString();
	            	RegActTask task=new RegActTask();
	            	task.execute(numStr);
//本地数据库实例     	ContentValues values = new ContentValues();
//	            	values.put("num", num);
//	            	values.put("password", password);
//	            	values.put("phone", phone);
//	            	DBOpenHelper helper= new DBOpenHelper(getApplicationContext());
//	            	helper.insert(values);
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
//	参数1：向后台任务的执行方法传递参数的类型 
//    参数2：在后台任务执行过程中，要求主UI线程处理中间状态，通常是一些UI处理中传递的参数类型 
//    参数3：后台任务执行完返回时的参数类型 
	private class RegActTask extends AsyncTask<String, Integer, Integer>{
		
//		@Override
////		在后台程序运行前在ui线程上操作
//		protected void onPreExecute(){
//			
//		}

		@Override
//		运行在后台并处理后台的操作
		protected Integer doInBackground(String... params){
			return RegAct((String)params[0]);
			 
		}

		@Override
//		操作完毕后在主线程中运行
		protected void onPostExecute(Integer result){
			//根据返回值显示相关的Toast 
			switch(result){
			case 1:
			String INFO="激活成功！";
			Toast t1= Toast.makeText(getApplicationContext(), INFO, Toast.LENGTH_SHORT);
        	t1.show();
        	Intent intent = new Intent();
            intent.setClass(RegActivity.this, LogActivity.class);
            startActivity(intent);
            break;
			case 2:
				INFO="已经激活，可直接登录！";
				t1= Toast.makeText(getApplicationContext(), INFO, Toast.LENGTH_SHORT);
	        	t1.show();	        	
	        break;
			case 3:
				INFO="该学号不存在！";
				t1= Toast.makeText(getApplicationContext(), INFO, Toast.LENGTH_SHORT);
	        	t1.show();	        	
	        break;
			case 4:
				INFO="无法连接服务器！";
				t1= Toast.makeText(getApplicationContext(), INFO, Toast.LENGTH_SHORT);
	        	t1.show();	        	
	        break;
			}
		}
	}
	
	// 现在是直接把学号和数据库的学号比较如果存在就激活成功，但是是在服务器进行处理的 ,String nickname,String password,String phone
	public int RegAct(String num){
		String url="/getActInfo";
		String params="num="+num;
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
}
	
