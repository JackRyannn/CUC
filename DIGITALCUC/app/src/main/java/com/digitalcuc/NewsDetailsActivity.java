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
import android.widget.ViewFlipper;

import com.digitalcuc.mywidget.TitleBar;
import com.digitalcuc.service.SyncHttp;

public class NewsDetailsActivity extends Activity {
	
	private final int FINISH = 0;
	
	private ViewFlipper mNewsBodyFlipper;
	private LayoutInflater mNewsBodyInflater;
	private int mPosition = 0;
	private int mId;
	private ArrayList<HashMap<String, Object>> mNewsData;
	private static TextView mNewsDetails;
	private float mStartX;
	private TitleBar mTitleBar;
	private Button comments_num;
	private Button comments_pic;
	
	static private  Handler mHandler = new Handler()
	{
		@Override
		public  void handleMessage(Message msg)
		{
			switch (msg.arg1)
			{
			case 0:
				// 把获取到的新闻显示到界面上
				mNewsDetails.setText(Html.fromHtml(msg.obj.toString()));
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
		mTitleBar = (TitleBar)findViewById(R.id.titlebar);
		mTitleBar.setTitle("");
		mTitleBar.setIv2Enable(View.INVISIBLE);
		mTitleBar.setBtn2Enable(View.VISIBLE);
		mTitleBar.setBtn3Enable(View.VISIBLE);
//		Button newsDetailsTitlebarNext = (Button) findViewById(R.id.newsdetails_titlebar_next);
//		newsDetailsTitlebarNext.setOnClickListener(newsDetailsTitleBarOnClickListener);
//		//新闻回复Button
		comments_num = mTitleBar.getBtn3();
		NewsDetailsTitleBarOnClickListener newsDetailsTitleBarOnClickListener = new NewsDetailsTitleBarOnClickListener();
		comments_num.setOnClickListener(newsDetailsTitleBarOnClickListener);
//		btn_back = (Button) findViewById(R.id.btn_back);
//		btn_back.setOnClickListener(newsDetailsTitleBarOnClickListener);
		comments_pic = mTitleBar.getBtn2();
		comments_pic.setOnClickListener(newsDetailsTitleBarOnClickListener);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		// 设置标题栏名称
//		String categoryName = "NEWS DETAIL";
//		TextView titleBarTitle = (TextView) findViewById(R.id.newsdetails_titlebar_title);
//		titleBarTitle.setText(categoryName);
		// 获取点击位置
		mPosition = bundle.getInt("position");
		// 获取新闻集合
		Serializable s = bundle.getSerializable("newsDate");
		mNewsData = (ArrayList<HashMap<String, Object>>) s;
		mNewsBodyInflater = getLayoutInflater();
		inflateView(0);

	}
	
	
	private void showPrevious(){
		Intent intent = new Intent(NewsDetailsActivity.this, MainActivity.class);
		startActivity(intent);
		overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);    

	}
		
	private void showNext(){
		Intent intent = new Intent(NewsDetailsActivity.this, CommentsActivity.class);
		intent.putExtra("id", mId);
		startActivity(intent);
		overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);    
	}
	
	/**
	 * 处理NewsDetailsTitleBar点击事件
	 */
	class NewsDetailsTitleBarOnClickListener implements OnClickListener{
		@Override
		public void onClick(View v){
			switch (v.getId()){
			case R.id.comments_pic:
				Intent intent = new Intent(NewsDetailsActivity.this, CommentsActivity.class);
				intent.putExtra("id", mId);
				startActivity(intent);
				break;
			case R.id.comments_number:
				intent = new Intent(NewsDetailsActivity.this, CommentsActivity.class);
				intent.putExtra("id", mId);
				startActivity(intent);
				break;
			}

		}
	}
	

	private OnTouchListener  newsBodyOnTouchListener = new OnTouchListener (){

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()){
			// 手指按下
			case MotionEvent.ACTION_DOWN:
				// 记录起始坐标
				mStartX = event.getX();
				break;
			// 手指抬起
			case MotionEvent.ACTION_UP:
				// 往左滑动
				if (event.getX() < mStartX){
					showNext();
					}
				// 往右滑动
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
		// 动态创建新闻视图，并赋值
		View newsBodyLayout = mNewsBodyInflater.inflate(R.layout.news_body, null);
		// 获取点击新闻基本信息
		HashMap<String, Object> hashMap = mNewsData.get(mPosition);
		// 新闻标题
		TextView newsTitle = (TextView) newsBodyLayout.findViewById(R.id.news_body_title);
		newsTitle.setText(hashMap.get("newslist_item_title").toString());
		// 发布时间和出处
		TextView newsPtimeAndSource = (TextView) newsBodyLayout.findViewById(R.id.news_body_ptime_source);
		newsPtimeAndSource.setText(hashMap.get("newslist_item_ptime").toString() + "    " + hashMap.get("newslist_item_source").toString());
		// 新闻编号
		mId = (Integer) hashMap.get("id");
		// 新闻回复数
		comments_num.setText(hashMap.get("newslist_item_comments")+"");
		// 把新闻视图添加到Flipper中
		mNewsBodyFlipper = (ViewFlipper) findViewById(R.id.news_body_flipper);
		mNewsBodyFlipper.addView(newsBodyLayout,index);
	
		// 给新闻Body添加触摸事件
		mNewsDetails = (TextView) newsBodyLayout.findViewById(R.id.news_body_details);
		mNewsDetails.setOnTouchListener(newsBodyOnTouchListener);
	
		// 启动线程
		new UpdateNewsThread().start();

	}
//	新的线程
	private class UpdateNewsThread extends Thread
	{
		@Override
		public void run()
		{
			System.out.println("Thread:" + Thread.currentThread().getId());
			// 从网络上获取新闻
			String newsBody = getNewsBody();
			Message msg = mHandler.obtainMessage();
			msg.arg1 = FINISH;
			msg.obj = newsBody;
			mHandler.sendMessage(msg);
		}
	}
	
	
	/**
	 * 获取新闻详细信息
	 * 
	 * @return
	 */
	private String getNewsBody()
	{
		String retStr = "网络连接失败，请稍后再试";
		SyncHttp syncHttp = new SyncHttp();
		String url = "/getNews";
		String params = "id=" + mId;
		try
		{
			String retString = syncHttp.httpGet(url, params);
			JSONObject jsonObject = new JSONObject(retString);
			// 获取返回码，0表示成功
			int retCode = jsonObject.getInt("ret");
			if (0 == retCode)
			{
				JSONObject dataObject = jsonObject.getJSONObject("data");
				JSONObject newsObject = dataObject.getJSONObject("news");
				retStr = newsObject.getString("body");
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return retStr;
	}

}