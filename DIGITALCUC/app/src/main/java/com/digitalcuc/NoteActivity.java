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
	private Button mNotesdetailsTitlebarComm;
	private int mCursor;
	private Button btn_back;
	private Button comment_pic;
	
	
	static private  Handler mHandler = new Handler()
	{
		@Override
		public  void handleMessage(Message msg)
		{
			System.out.println("Handler:" + Thread.currentThread().getId());
			switch (msg.arg1)
			{
			case 0:
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
		setContentView(R.layout.activity_newsdetail);
		
//		NotesDetailsTitleBarOnClickListener notesDetailsTitleBarOnClickListener = new NotesDetailsTitleBarOnClickListener();
//		btn_back = (Button) findViewById(R.id.btn_back);
//		btn_back.setOnClickListener(notesDetailsTitleBarOnClickListener);
//		comment_pic = (Button) findViewById(R.id.comment_pic);
//		comment_pic.setOnClickListener(notesDetailsTitleBarOnClickListener);
//		Button notesDetailsTitlebarPref = (Button) findViewById(R.id.comment_account);
//		notesDetailsTitlebarPref.setOnClickListener(notesDetailsTitleBarOnClickListener);
//		mNotesdetailsTitlebarComm = (Button) findViewById(R.id.comment_account);
//		mNotesdetailsTitlebarComm.setOnClickListener(notesDetailsTitleBarOnClickListener);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		String categoryName = bundle.getString("categoryName");
		TextView titleBarTitle = (TextView) findViewById(R.id.newsdetails_titlebar_title);
		titleBarTitle.setText(categoryName);
		mCursor = mPosition = bundle.getInt("position");
		Serializable s = bundle.getSerializable("notesDate");
		mNotesData = (ArrayList<HashMap<String, Object>>) s;
		mNotesBodyInflater = getLayoutInflater();
		inflateView(0);
	}
	
	private void showPrevious(){
		if (mPosition>0){
			mPosition--;
			HashMap<String, Object> hashMap = mNotesData.get(mPosition);
			mid = (Integer) hashMap.get("id");
			if (mCursor > mPosition){
				mCursor = mPosition;
				inflateView(0);
				System.out.println(mNotesBodyFlipper.getChildCount());
				mNotesBodyFlipper.showNext();
			}
			mNotesBodyFlipper.setInAnimation(this, R.anim.push_right_in);
			mNotesBodyFlipper.setOutAnimation(this, R.anim.push_right_out);
			mNotesBodyFlipper.showPrevious();
		}
		else{
			Toast.makeText(this, R.string.app_name, Toast.LENGTH_SHORT).show();
		}
		System.out.println(mCursor +";"+mPosition);
	}
		
	private void showNext(){
		if (mPosition<mNotesData.size()-1){
			mNotesBodyFlipper.setInAnimation(this, R.anim.push_left_in);
			mNotesBodyFlipper.setOutAnimation(this, R.anim.push_left_out);
			mPosition++;
			if (mPosition >= mNotesBodyFlipper.getChildCount()){
				inflateView(mNotesBodyFlipper.getChildCount());
			}
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
//			case R.id.btn_back:
//				Intent intent = new Intent(NoteActivity.this, BbsActivity.class);
//				startActivity(intent);
//				break;
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
			case MotionEvent.ACTION_DOWN:
				mStartX = event.getX();
				break;
			case MotionEvent.ACTION_UP:
				if (event.getX() < mStartX){
					showNext();
					}
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
		View notesBodyLayout = mNotesBodyInflater.inflate(R.layout.news_body, null);
		HashMap<String, Object> hashMap = mNotesData.get(mPosition);
		TextView notesTitle = (TextView) notesBodyLayout.findViewById(R.id.news_body_title);
		notesTitle.setText(hashMap.get("noteslist_item_title").toString());
		TextView notesPtimeAndSource = (TextView) notesBodyLayout.findViewById(R.id.news_body_ptime_source);
		notesPtimeAndSource.setText(hashMap.get("noteslist_item_ptime").toString() + "    " + hashMap.get("noteslist_item_source").toString());
		mid = (Integer) hashMap.get("id");
		mNotesdetailsTitlebarComm.setText(hashMap.get("noteslist_item_comments")+"");
		mNotesBodyFlipper = (ViewFlipper) findViewById(R.id.news_body_flipper);
		mNotesBodyFlipper.addView(notesBodyLayout,index);
		mNotesDetails = (TextView) notesBodyLayout.findViewById(R.id.news_body_details);
		mNotesDetails.setOnTouchListener(notesBodyOnTouchListener);
		new UpdateNotesThread().start();
	}
	
	private class UpdateNotesThread extends Thread
	{
		@Override
		public void run()
		{
			System.out.println("Thread:" + Thread.currentThread().getId());
			String notesBody = getNotesBody();
			Message msg = mHandler.obtainMessage();
			msg.arg1 = FINISH;
			msg.obj = notesBody;
			mHandler.sendMessage(msg);
		}
	}
	
	private String getNotesBody()
	{
		String retStr = "------";
		SyncHttp syncHttp = new SyncHttp();
		String url = "/getNotes";
		String params = "id=" + mid;
		try
		{
			String retString = syncHttp.httpGet(url, params);
			JSONObject jsonObject = new JSONObject(retString);
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
