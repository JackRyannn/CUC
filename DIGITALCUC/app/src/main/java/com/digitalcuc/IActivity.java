package com.digitalcuc;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
public class IActivity extends Activity {
//	private final int SUCCESS = 1;
//	private final int LOADERROR = 0;
//	private Button btn1;
	Context mContext;
	int num = 2012;
	String icon;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_i);
		mContext=this;
		BottomBtn Btn=(BottomBtn)findViewById(R.id.bottombtn);
		Btn.sendContext(mContext);
//		btn1 = (Button)findViewById(R.id.button1);
//		btn1.setOnClickListener(new OnClickListener(){
//			@Override
//			public void onClick(View v){
//				 icon = ImageUtil.Image2String(getApplicationContext(),R.drawable.ic_launcher);
//				 UpdateAccountAsyncTask task = new UpdateAccountAsyncTask();
//				 task.execute(num,icon);
//			}
//		});
		
		 
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