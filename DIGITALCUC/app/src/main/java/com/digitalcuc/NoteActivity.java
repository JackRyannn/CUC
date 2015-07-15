package com.digitalcuc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.digitalcuc.service.SyncHttp;

public class NoteActivity extends Activity {
	
	private final int FINISH = 0;
	
	private ViewFlipper mNotesBodyFlipper;
	private LayoutInflater mNotesBodyInflater;
	int count;
	private int mPosition = 0;
	private int mid;
	private ArrayList<HashMap<String, Object>> mNotesData;
	private static TextView mNotesDetails;
	private float mStartX;
	private Button mNotesdetailsTitlebarComm;// ���Żظ���
	private int mCursor;
	private Button btn_back;// ���Żظ���
	private Button comment_pic;// ���Żظ���
	
	
	static private  Handler mHandler = new Handler()
	{
		@Override
		public  void handleMessage(Message msg)
		{
			System.out.println("Handler:" + Thread.currentThread().getId());
			switch (msg.arg1)
			{
			case 0:
				// �ѻ�ȡ����������ʾ��������
				mNotesDetails.setText(Html.fromHtml(msg.obj.toString()));
				break;
			}
		}
	};
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.newsdetails);
		
//		NotesDetailsTitleBarOnClickListener notesDetailsTitleBarOnClickListener = new NotesDetailsTitleBarOnClickListener();
		//��һƪ����
//		btn_back = (Button) findViewById(R.id.btn_back);
//		btn_back.setOnClickListener(notesDetailsTitleBarOnClickListener);
//		comment_pic = (Button) findViewById(R.id.comment_pic);
//		comment_pic.setOnClickListener(notesDetailsTitleBarOnClickListener);
//		Button notesDetailsTitlebarPref = (Button) findViewById(R.id.comment_account);
//		notesDetailsTitlebarPref.setOnClickListener(notesDetailsTitleBarOnClickListener);
		// ��һƪ����
		//���Żظ�Button
//		mNotesdetailsTitlebarComm = (Button) findViewById(R.id.comment_account);
//		mNotesdetailsTitlebarComm.setOnClickListener(notesDetailsTitleBarOnClickListener);
		//��ȡ���ݵ����
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		// ���ñ��������
		String categoryName = bundle.getString("categoryName");
		TextView titleBarTitle = (TextView) findViewById(R.id.newsdetails_titlebar_title);
		titleBarTitle.setText(categoryName);
		// ��ȡ���λ��
		mCursor = mPosition = bundle.getInt("position");
		// ��ȡ���ż���
		Serializable s = bundle.getSerializable("notesDate");
//		����ǿ������ת�������⣬����д�����˴�
		
		mNotesData = (ArrayList<HashMap<String, Object>>) s;
		// ��̬����������ͼ������ֵ
		mNotesBodyInflater = getLayoutInflater();
		inflateView(0);
	}
	
	
	private void showPrevious(){
		if (mPosition>0){
			mPosition--;
			//��¼��ǰ���ű��
			HashMap<String, Object> hashMap = mNotesData.get(mPosition);
			mid = (Integer) hashMap.get("id");
			if (mCursor > mPosition){
				mCursor = mPosition;
				inflateView(0);
				System.out.println(mNotesBodyFlipper.getChildCount());
				mNotesBodyFlipper.showNext();// ��ʾ��һҳ
			}
			mNotesBodyFlipper.setInAnimation(this, R.anim.push_right_in);// ������һҳ����ʱ�Ķ���
			mNotesBodyFlipper.setOutAnimation(this, R.anim.push_right_out);// ���嵱ǰҳ��ȥ�Ķ���
			mNotesBodyFlipper.showPrevious();// ��ʾ��һҳ
		}
		else{
			Toast.makeText(this, R.string.app_name, Toast.LENGTH_SHORT).show();
		}
		System.out.println(mCursor +";"+mPosition);
	}
		
	private void showNext(){
		if (mPosition<mNotesData.size()-1){
			//������һ������
			mNotesBodyFlipper.setInAnimation(this, R.anim.push_left_in);
			mNotesBodyFlipper.setOutAnimation(this, R.anim.push_left_out);
			mPosition++;
			if (mPosition >= mNotesBodyFlipper.getChildCount()){
				inflateView(mNotesBodyFlipper.getChildCount());
			}
            // ��ʾ��һ��
			mNotesBodyFlipper.showNext();
		}
		else{
			Toast.makeText(this, R.string.action_settings, Toast.LENGTH_SHORT).show();
		}
	}
//	class NotesDetailsTitleBarOnClickListener implements OnClickListener{
//		@Override
//		public void onClick(View v){
//			switch (v.getId()){
//			// ��һ������
//			case R.id.btn_back:
//				Intent intent = new Intent(NoteActivity.this, BbsActivity.class);
//				startActivity(intent);
//				break;
//			// ��һ������
//
//			case R.id.comment_pic:
//				intent = new Intent(NoteActivity.this, NoteCommentsActivity.class);
//				intent.putExtra("id", mid);
//				startActivity(intent);
//				break;
//			case R.id.comment_account:
//				intent = new Intent(NoteActivity.this, NoteCommentsActivity.class);
//				intent.putExtra("id", mid);
//				startActivity(intent);
//				break;
//			}
//		}
//	}
	
	private OnTouchListener  notesBodyOnTouchListener = new OnTouchListener (){

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()){
			// ��ָ����
			case MotionEvent.ACTION_DOWN:
				// ��¼��ʼ���
				mStartX = event.getX();
				break;
			// ��ָ̧��
			case MotionEvent.ACTION_UP:
				// ���󻬶�
				if (event.getX() < mStartX){
					showNext();
					}
				// ���һ���
				else if (event.getX() > mStartX){
					showPrevious();
					}
				break;
			}
			return true;
		}
	};
	
	private void inflateView(int index)
	{
		// ��̬����������ͼ������ֵ
		View notesBodyLayout = mNotesBodyInflater.inflate(R.layout.news_body, null);
		// ��ȡ������Ż���Ϣ
		HashMap<String, Object> hashMap = mNotesData.get(mPosition);
		// ���ű���
		TextView notesTitle = (TextView) notesBodyLayout.findViewById(R.id.news_body_title);
		notesTitle.setText(hashMap.get("noteslist_item_title").toString());
		// ����ʱ��ͳ���
		TextView notesPtimeAndSource = (TextView) notesBodyLayout.findViewById(R.id.news_body_ptime_source);
		notesPtimeAndSource.setText(hashMap.get("noteslist_item_ptime").toString() + "    " + hashMap.get("noteslist_item_source").toString());
		// ���ű��
		mid = (Integer) hashMap.get("id");
		// ���Żظ���
		mNotesdetailsTitlebarComm.setText(hashMap.get("noteslist_item_comments")+"");
	
		// ��������ͼ��ӵ�Flipper��
		mNotesBodyFlipper = (ViewFlipper) findViewById(R.id.news_body_flipper);
		mNotesBodyFlipper.addView(notesBodyLayout,index);
	
		// ������Body��Ӵ����¼�
		mNotesDetails = (TextView) notesBodyLayout.findViewById(R.id.news_body_details);
		mNotesDetails.setOnTouchListener(notesBodyOnTouchListener);
	
		// �����߳�
		new UpdateNotesThread().start();
	}
	
	private class UpdateNotesThread extends Thread
	{
		@Override
		public void run()
		{
			System.out.println("Thread:" + Thread.currentThread().getId());
			// �������ϻ�ȡ����
			String notesBody = getNotesBody();
			Message msg = mHandler.obtainMessage();
			msg.arg1 = FINISH;
			msg.obj = notesBody;
			mHandler.sendMessage(msg);
		}
	}
	
	
	/**
	 * ��ȡ������ϸ��Ϣ
	 * 
	 * @return
	 */
	private String getNotesBody()
	{
		String retStr = "��������ʧ�ܣ����Ժ�����";
		SyncHttp syncHttp = new SyncHttp();
		String url = "/getNotes";
		String params = "id=" + mid;
		try
		{
			String retString = syncHttp.httpGet(url, params);
			JSONObject jsonObject = new JSONObject(retString);
			// ��ȡ�����룬0��ʾ�ɹ�
			int retCode = jsonObject.getInt("ret");
			if (0 == retCode)
			{
				JSONObject dataObject = jsonObject.getJSONObject("data");
				JSONObject notesObject = dataObject.getJSONObject("notes");
				retStr = notesObject.getString("body");
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return retStr;
	}
}
