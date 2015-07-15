package com.digitalcuc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.digitalcuc.model.Parameter;
import com.digitalcuc.mywidget.TitleBar;
import com.digitalcuc.service.SyncHttp;
import com.digitalcuc.util.IntegerUtil;

/**
 *@author coolszy
 *@date 2012-3-22
 *@blog http://blog.92coding.com
 */
public class CommentsActivity extends Activity
{
	private final int COMMENTSCOUNT = 10; //返回新闻
	private final int SUCCESS = 0;//加载成功
	private final int NOCOMMENTS = 1;//该栏目下没有新闻
	private final int NOMORECOMMENTS = 2;//该栏目下没有更多新闻
	private final int LOADERROR = 3;//加载失败	
	private List<HashMap<String, Object>> commentslist;
	private EditText reply;
	private ImageButton post;
	private int id=1;
    private SimpleAdapter commentsAdapter;
	private TitleBar mTitleBar;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.comments);
		mTitleBar = (TitleBar)findViewById(R.id.titlebar);
		mTitleBar.setTitle("评论");
		mTitleBar.setIv2Enable(View.INVISIBLE);

//		mNewsList=(ListView)findViewById(R.id.newslist);
//		mNewsData = new ArrayList<HashMap<String,Object>>();
////		simpleadapter(this,arraylist格式的数据，布局文件， 每个item的名字，对应的id）
//		mNewsListAdapter = new SimpleAdapter(this, mNewsData, R.layout.newslist_item, 
//                new String[]{"newslist_item_title", "newslist_item_digest", "newslist_item_source", "newslist_item_ptime"},
//                new int[]{R.id.newslist_item_title, R.id.newslist_item_digest, R.id.newslist_item_source, R.id.newslist_item_ptime});
//		mNewsList.setAdapter(mNewsListAdapter);
//		LoadNewsAsyncTask task = new LoadNewsAsyncTask();
//		task.execute(mCid,0,false);
//		
		
		
		reply = (EditText)findViewById(R.id.news_reply_edittext);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		id = bundle.getInt("id");
		commentslist = new ArrayList<HashMap<String,Object>>();
//		for (int i = 0; i < 10; i++)
//		{
//			HashMap<String, Object> hashMap = new HashMap<String, Object>();
//			hashMap.put("commentator_from","北京网友");
//			hashMap.put("comment_ptime", "2014-09-02 20:21:22");
//			hashMap.put("comment_content", "内容");
//			commentslist.add(hashMap);
//		}
		ListView commentsList = (ListView) findViewById(R.id.comments_list);
		commentsAdapter = new SimpleAdapter(this, commentslist, R.layout.comments_list_item, new String[]
		{ "comment_nickname", "comment_ptime", "comment_content" }, new int[]
		{ R.id.comment_nickname, R.id.comment_ptime, R.id.comment_content });
		commentsList.setAdapter(commentsAdapter);
		
		LoadCommentsAsyncTask task = new LoadCommentsAsyncTask();
		task.execute(id,0,false);
		post = (ImageButton)findViewById(R.id.news_collect_btn);
		post.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				
				String content = reply.getText().toString();
				CommentTask commentTask = new CommentTask();
				commentTask.execute(id,content);
			}
		});
	
}
//	String url = "http://10.0.2.2:8080/web/postComment";
//	List<Parameter> params = new ArrayList<Parameter>();
//	params.add(new Parameter("nid", mCategoryNids.get(mCurrentPosition)+""));
//	params.add(new Parameter("region", "安徽"));
//	params.add(new Parameter("content", mNewsReplyEditText.getText().toString()));
//	
	private final int PostComment(int id,String content){
		int retCode = 0;
		String url = "/postComment";
		List<Parameter> params = new ArrayList<Parameter>();
		String idStr = IntegerUtil.Int2String(id);
		params.add(new Parameter("id",idStr));
		params.add(new Parameter("num",idStr));
		params.add(new Parameter("content",content));
		SyncHttp syncHttp = new SyncHttp();
		try{
		String retStr = syncHttp.httpPost(url, params); 
		JSONObject jObject = new JSONObject(retStr);
		retCode = jObject.getInt("ret");
		}
		catch(Exception e )
		{
		e.printStackTrace();
		}
		return retCode;
	}
	private class  CommentTask extends AsyncTask<Object, Integer, Integer>
	{
		protected Integer doInBackground(Object...params){
//			(Integer)是一个类名
			return PostComment((Integer)params[0],(String)params[1]);
		}
		protected void onPostExecute(Integer result){
			//根据返回值显示相关的Toast 
			switch(result){
			case 1:
			String INFO="评论成功！";
			Toast t1= Toast.makeText(getApplicationContext(), INFO, Toast.LENGTH_SHORT);
        	t1.show();
            break;		
			case 0:
				INFO="评论失败，无法连接服务器！";
				t1= Toast.makeText(getApplicationContext(), INFO, Toast.LENGTH_SHORT);
	        	t1.show();	        	
	        break;
			}
		}
	}
//		刷新评论
	private int getComments(Integer id,List<HashMap<String, Object>> commentsData,Integer startid,Boolean firstTimes){
		if (firstTimes){
			//如果是第一次，则清空集合里数据
			commentsData.clear();
		}
		
		//请求URL和字符串
		String url = "/getComments";
		String params = "startid="+startid+"&count="+COMMENTSCOUNT+"&id="+id;
		SyncHttp syncHttp = new SyncHttp();
		try{
			//以Get方式请求，并获得返回结果
			String retStr = syncHttp.httpGet(url, params);
			JSONObject jsonObject = new JSONObject(retStr);
			//获取返回码，0表示成功
			int retCode = jsonObject.getInt("ret");
			if (1==retCode){
				JSONObject dataObject = jsonObject.getJSONObject("data");
				//获取返回数目
				int totalnum = dataObject.getInt("totalnum");
				if (totalnum>0){
					//获取返回新闻集合
					JSONArray jcommentslist = dataObject.getJSONArray("commentslist");
					for(int i=0;i<jcommentslist.length();i++){
						JSONObject commentsObject = (JSONObject)jcommentslist.opt(i); 
						HashMap<String, Object> hashMap = new HashMap<String, Object>();
						hashMap.put("comment_content", commentsObject.getString("content"));
						hashMap.put("comment_ptime", commentsObject.getString("ptime"));
						hashMap.put("comment_nickname", commentsObject.getString("nickname"));
						hashMap.put("comment_opposeCount", commentsObject.getString("opposeCount"));
						commentsData.add(hashMap);
					}
					return SUCCESS;
				}else{
					if (firstTimes){
						return NOCOMMENTS;
					}else{
						return NOMORECOMMENTS;
					}
				}
			}else{
				return LOADERROR;
			}
		} catch (Exception e){
			e.printStackTrace();
			return LOADERROR;
		}
	}
	
	
//	“加载更多” 异步任务
	private class LoadCommentsAsyncTask extends AsyncTask<Object, Integer, Integer>{
		@Override
		protected Integer doInBackground(Object... params){
			return getComments((Integer)params[0],commentslist,(Integer)params[1],(Boolean)params[2]);
		}

		@Override
		protected void onPostExecute(Integer result){
			//根据返回值显示相关的Toast
			switch (result){			
			case LOADERROR:
				Toast.makeText(CommentsActivity.this, R.string.load_news_failure, Toast.LENGTH_LONG).show();
				break;
			}
			commentsAdapter.notifyDataSetChanged();
			
		}
	}
	
}
