package com.digitalcuc;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalcuc.mywidget.TitleBar;

public class IActivity extends Activity {
	private TitleBar mTitleBar;
	private Button commit;
	private Single_Account single_account = new Single_Account();
	private TextView tv1;


	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_i);
		tv1 = (TextView)findViewById(R.id.setting_name);
		tv1.setText(single_account.getAccount_name());
		mTitleBar = (TitleBar)findViewById(R.id.titlebar);
		mTitleBar.setIv2Enable(View.INVISIBLE);
		mTitleBar.setBtn4Enable(View.VISIBLE);
		commit = mTitleBar.getBtn4();
		commit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(getApplicationContext(),"确认",Toast.LENGTH_SHORT).show();
				IActivity.this.finish();
			}
		});
	}
	

//private final int UpdateAccount(int num,String icon){
//	int retCode = 0;
//	String url = "/updateAccount";
//	List<Parameter> params = new ArrayList<Parameter>();
//	String numStr = IntegerUtil.Int2String(num);
//	params.add(new Parameter("num",numStr));
//	params.add(new Parameter("icon",icon));
//	SyncHttp syncHttp = new SyncHttp();
//	try{
//	String retStr = syncHttp.httpPost(url, params); 
//	JSONObject jObject = new JSONObject(retStr);
//	retCode = jObject.getInt("ret");
//	}
//	catch(Exception e )
//	{
//	e.printStackTrace();
//	}
//	return retCode;
//}
//private class UpdateAccountAsyncTask extends AsyncTask<Object, Integer, Integer>{		
//	@Override		
//	protected void onPreExecute(){			
//	}
//	@Override
//	protected Integer doInBackground(Object... params){
//		return UpdateAccount((Integer)params[0],(String)params[1]);
//	}
//	@Override
//	protected void onPostExecute(Integer result){
//		//��ݷ���ֵ��ʾ��ص�Toast
//		switch (result){
//		case SUCCESS:
//			Toast.makeText(IActivity.this, R.string.hello_world, Toast.LENGTH_LONG).show();
//		break;
//		case LOADERROR:
//			Toast.makeText(IActivity.this, R.string.load_notes_failure, Toast.LENGTH_LONG).show();
//			break;
//		}
//		
//	}
//}
}