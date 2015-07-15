package com.digitalcuc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalcuc.mywidget.TitleBar;
import com.digitalcuc.service.SyncHttp;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class MainActivity extends Activity {
    private ListView mNewsList;
    private ArrayList<HashMap<String, Object>> mNewsData;
    private final int NEWSCOUNT = 5;
    private final int SUCCESS = 0;
    private final int NONEWS = 1;
    private final int NOMORENEWS = 2;
    private final int LOADERROR = 3;
    private SimpleAdapter mNewsListAdapter;
    private int mCid = 1;
    private Context mContext;
    private TitleBar titleBar;
    private ArrayList<Map<String, Object>> arrayList = new ArrayList<Map<String, Object>>();
    private ListView list_menu;
    private SimpleAdapter simpleAdapter;
    public SlidingMenu menu;
    private Single_Account single_account = Single_Account.getAccount();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.RIGHT);
        // 设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        // 设置滑动菜单视图的宽度
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // 设置渐入渐出效果的值
        menu.setFadeDegree(0.35f);
        /**
         * SLIDING_WINDOW will include the Title/ActionBar in the content
         * section of the SlidingMenu, while SLIDING_CONTENT does not.
         */
        //把滑动菜单添加进所有的Activity中，可选值SLIDING_CONTENT ， SLIDING_WINDOW
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //为侧滑菜单设置布局
        menu.setMenu(R.layout.leftmenu);
        for (int i = 0; i < getResources().getStringArray(R.array.menu).length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("menu_name", getResources().getStringArray(R.array.menu)[i]);
            arrayList.add(map);
        }
        list_menu = (ListView) menu.findViewById(R.id.menu_list);
        simpleAdapter = new SimpleAdapter(this, arrayList, R.layout.menu_item, new String[]{"menu_name"}, new int[]{R.id.menu_name});
        list_menu.setAdapter(simpleAdapter);
        list_menu.setOnItemClickListener(new OnItemClickListener() {
            Intent intent = new Intent();
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        intent.setClass(MainActivity.this,NewsActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent.setClass(MainActivity.this,BbsActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
//                        intent.setClass(MainActivity.this,LifeActivity.class);
//                        startActivity(intent);
                        Toast.makeText(getApplication(),"广院生活",Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        intent.setClass(MainActivity.this,MapActivity.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent.setClass(MainActivity.this,IActivity.class);
                        startActivity(intent);
                        break;
                    case 5:
                        MainActivity.this.finish();
                        break;


                }
            }
        });


        titleBar = (TitleBar) findViewById(R.id.titlebar);
        titleBar.showMenu(menu);
        titleBar.setTitle(single_account.getAccount_name());
        mNewsList = (ListView) findViewById(R.id.newslist);
        mNewsData = new ArrayList<HashMap<String, Object>>();
        mNewsListAdapter = new SimpleAdapter(this, mNewsData, R.layout.newslist_item,
                new String[]{"newslist_item_title", "newslist_item_digest", "newslist_item_source", "newslist_item_ptime"},
                new int[]{R.id.newslist_item_title, R.id.newslist_item_digest, R.id.newslist_item_source, R.id.newslist_item_ptime});
        mNewsList.setAdapter(mNewsListAdapter);
        LoadNewsAsyncTask task = new LoadNewsAsyncTask();
        task.execute(mCid, 0, false);

        mContext = this;

        mNewsList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, NewsDetailsActivity.class);
                intent.putExtra("categoryName", "目录名称");
                intent.putExtra("newsDate", mNewsData);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

    }

    private int getSpeCateNews(int cid, List<HashMap<String, Object>> newsList, int startid, Boolean firstTimes) {
        if (firstTimes) {
            newsList.clear();
        }
        String url = "/getSpecifyCategoryNews";
        String params = "startid=" + startid + "&count=" + NEWSCOUNT + "&cid=" + cid;
        SyncHttp syncHttp = new SyncHttp();
        try {
            String retStr = syncHttp.httpGet(url, params);
            JSONObject jsonObject = new JSONObject(retStr);
            int retCode = jsonObject.getInt("ret");
            if (0 == retCode) {
                JSONObject dataObject = jsonObject.getJSONObject("data");
                int totalnum = dataObject.getInt("totalnum");
                if (totalnum > 0) {
                    JSONArray newslist = dataObject.getJSONArray("newslist");
                    for (int i = 0; i < newslist.length(); i++) {
                        JSONObject newsObject = (JSONObject) newslist.opt(i);
                        HashMap<String, Object> hashMap = new HashMap<String, Object>();
                        hashMap.put("id", newsObject.getInt("id"));
                        hashMap.put("newslist_item_title", newsObject.getString("title"));
                        hashMap.put("newslist_item_digest", newsObject.getString("digest"));
                        hashMap.put("newslist_item_source", newsObject.getString("source"));
                        hashMap.put("newslist_item_ptime", newsObject.getString("ptime"));
                        hashMap.put("newslist_item_comments", newsObject.getString("commentcount"));
                        newsList.add(hashMap);
                    }
                    return SUCCESS;
                } else {
                    if (firstTimes) {
                        return NONEWS;
                    } else {
                        return NOMORENEWS;
                    }
                }
            } else {
                return LOADERROR;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return LOADERROR;
        }
    }

    private class LoadNewsAsyncTask extends AsyncTask<Object, Integer, Integer> {
        @Override
        protected Integer doInBackground(Object... params) {
            return getSpeCateNews((Integer) params[0], mNewsData, (Integer) params[1], (Boolean) params[2]);
        }

        @Override
        protected void onPostExecute(Integer result) {
            switch (result) {
                case SUCCESS:
                    String wel = "欢迎来到数字中传！";
                    Toast.makeText(MainActivity.this, wel, Toast.LENGTH_LONG).show();
                    break;
                case NONEWS:
                    Toast.makeText(MainActivity.this, R.string.no_news, Toast.LENGTH_LONG).show();
                    break;
                case NOMORENEWS:
                    Toast.makeText(MainActivity.this, R.string.no_more_news, Toast.LENGTH_LONG).show();
                    break;
                case LOADERROR:
                    Toast.makeText(MainActivity.this, R.string.load_news_failure, Toast.LENGTH_LONG).show();
                    break;
            }
            mNewsListAdapter.notifyDataSetChanged();

        }
    }
}


