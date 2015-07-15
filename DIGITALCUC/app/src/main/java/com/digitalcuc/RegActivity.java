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
	private EditText edit_1;//ѧ��
//	private EditText edit_2;//�ǳ�
//	private EditText edit_3;//����
//	private EditText edit_4;//�ֻ���
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
//�������ݿ�ʵ��     	ContentValues values = new ContentValues();
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
//	����1�����̨�����ִ�з������ݲ��������� 
//    ����2���ں�̨����ִ�й����У�Ҫ����UI�̴߳����м�״̬��ͨ����һЩUI�����д��ݵĲ������� 
//    ����3����̨����ִ���귵��ʱ�Ĳ������� 
	private class RegActTask extends AsyncTask<String, Integer, Integer>{
		
//		@Override
////		�ں�̨��������ǰ��ui�߳��ϲ���
//		protected void onPreExecute(){
//			
//		}

		@Override
//		�����ں�̨�������̨�Ĳ���
		protected Integer doInBackground(String... params){
			return RegAct((String)params[0]);
			 
		}

		@Override
//		������Ϻ������߳�������
		protected void onPostExecute(Integer result){
			//���ݷ���ֵ��ʾ��ص�Toast 
			switch(result){
			case 1:
			String INFO="����ɹ���";
			Toast t1= Toast.makeText(getApplicationContext(), INFO, Toast.LENGTH_SHORT);
        	t1.show();
        	Intent intent = new Intent();
            intent.setClass(RegActivity.this, LogActivity.class);
            startActivity(intent);
            break;
			case 2:
				INFO="�Ѿ������ֱ�ӵ�¼��";
				t1= Toast.makeText(getApplicationContext(), INFO, Toast.LENGTH_SHORT);
	        	t1.show();	        	
	        break;
			case 3:
				INFO="��ѧ�Ų����ڣ�";
				t1= Toast.makeText(getApplicationContext(), INFO, Toast.LENGTH_SHORT);
	        	t1.show();	        	
	        break;
			case 4:
				INFO="�޷����ӷ�������";
				t1= Toast.makeText(getApplicationContext(), INFO, Toast.LENGTH_SHORT);
	        	t1.show();	        	
	        break;
			}
		}
	}
	
	// ������ֱ�Ӱ�ѧ�ź����ݿ��ѧ�űȽ�������ھͼ���ɹ����������ڷ��������д���� ,String nickname,String password,String phone
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
	
