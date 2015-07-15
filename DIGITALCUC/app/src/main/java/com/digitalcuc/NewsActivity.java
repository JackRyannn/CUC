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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalcuc.custom.CustomSimpleAdapter;
import com.digitalcuc.model.Category;
import com.digitalcuc.mywidget.TitleBar;
import com.digitalcuc.service.SyncHttp;
import com.digitalcuc.util.StringUtil;

public class NewsActivity extends Activity {
    private final int NEWSCOUNT = 5; //返回新闻数目，从第几个开始一直到第几个，一共五个！
    private final int SUCCESS = 0;//加载成功
    private final int NONEWS = 1;//该栏目下没有新闻
    private final int NOMORENEWS = 2;//该栏目下没有更多新闻
    private final int LOADERROR = 3;//加载失败

    private int mColumnWidthDip;
    private int mFlingVelocityDip;
    private ArrayList<HashMap<String, Object>> mNewsData;
    private LayoutInflater mInflater;

    private ListView mNewsList;
    private int mCid;
    private String mCatName;
    private SimpleAdapter mNewsListAdapter;
    private TitleBar mTitleBar;
//    private Button mTitlebarRefresh;
//    private Button mTitlebarBack;
//    private ProgressBar mLoadnewsProgress;
    private Button mLoadMoreBtn;

    private LoadNewsAsyncTask loadNewsAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_news);
        mInflater = getLayoutInflater();
        mNewsData = new ArrayList<HashMap<String, Object>>();
        mNewsList = (ListView) findViewById(R.id.newslist);
        mTitleBar = (TitleBar)findViewById(R.id.titlebar);
        mTitleBar.setTitle("中传新闻");
        mTitleBar.setIv2Enable(View.INVISIBLE);
        mTitleBar.setBtn1Enable(View.VISIBLE);
//        mTitlebarRefresh = (Button) findViewById(R.id.titlebar_refresh);
//        mTitlebarBack = (Button) findViewById(R.id.titlebar_backbtn);
//        mLoadnewsProgress = (ProgressBar) findViewById(R.id.loadnews_progress);
//        mTitlebarRefresh.setOnClickListener(loadMoreListener);
//        mTitlebarBack.setOnClickListener(new OnClickListener() {
//                                             @Override
//                                             public void onClick(View v) {
//                                                 Intent intent = new Intent();
//                                                 intent.setClass(NewsActivity.this, MainActivity.class);
//                                                 startActivity(intent);
//                                             }
//                                         }
//        );
        //把px转换成dip
        mColumnWidthDip = 150;
        mFlingVelocityDip = 400;


        //获取新闻分类 getResource是获取系统资源函数
        String[] categoryArray = getResources().getStringArray(R.array.categories);
        //把新闻分类保存到List类型的categories中
        final List<HashMap<String, Category>> categories = new ArrayList<HashMap<String, Category>>();
        //分割新闻类型字符串
        for (int i = 0; i < categoryArray.length; i++) {
//			|必须用[]包起来，因为|是转义字符。 split将字符串按照|分割
            String[] temp = categoryArray[i].split("[|]");
            if (temp.length == 2) {
//				前面cid是类别序号，title是类别名称 把string类型to 成 int 类型
                int cid = StringUtil.String2Int(temp[0]);
                String title = temp[1];
                Category type = new Category(cid, title);
                HashMap<String, Category> hashMap = new HashMap<String, Category>();
                hashMap.put("category_title", type);
                categories.add(hashMap);
            }
        }
        //默认选中的新闻分类
        mCid = 1;
        mCatName = "广院天地";
        //创建Adapter，指明映射字段
        //SimpleAdapter categoryAdapter = new SimpleAdapter(this, categories, R.layout.category_title,new String[]{"category_title"}, new int[]{R.id.category_title});
        CustomSimpleAdapter categoryAdapter = new CustomSimpleAdapter(this, categories, R.layout.category_title, new String[]{"category_title"}, new int[]{R.id.category_title});
//		实例化网格视图
        GridView category = new GridView(this);
        category.setColumnWidth(mColumnWidthDip);//每个单元格宽度
        category.setNumColumns(GridView.AUTO_FIT);//单元格数目
        category.setGravity(Gravity.CENTER);
//		category.setSelector(new ColorDrawable(Color.TRANSPARENT));
        int width = mColumnWidthDip * categories.size();
//		LayoutParams设置了控件的宽和高！
        LayoutParams params = new LayoutParams(width, LayoutParams.MATCH_PARENT);
        //更新category宽度和高度，这样category在一行显示
        category.setLayoutParams(params);
        //设置适配器
        category.setAdapter(categoryAdapter);
        //把category加入到容器中
//		RelativeLayout categoryList=(RelativeLayout)findViewById(R.id.categorybar_layout);


        LinearLayout categoryList = (LinearLayout) findViewById(R.id.category_layout);
        categoryList.addView(category);
        //添加单元格点击事件
        category.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView categoryTitle;
                //恢复每个单元格背景色
                for (int i = 0; i < parent.getChildCount(); i++) {
                    categoryTitle = (TextView) (parent.getChildAt(i));
//                    categoryTitle.setBackground(null);
                    categoryTitle.setTextColor(0XFFADB2AD);
                }
                //设置选择单元格的背景色
                categoryTitle = (TextView) (parent.getChildAt(position));
                categoryTitle.setBackgroundResource(R.drawable.categorybar_item_background);
                categoryTitle.setTextColor(0XFFADB3AD);
                //获取选中的新闻分类id
                mCid = categories.get(position).get("category_title").getCid();
                mCatName = categories.get(position).get("category_title").getTitle();
                //获取该栏目下新闻
                getSpeCateNews(mCid, mNewsData, 0, true);
                //通知ListView进行更新
                //mNewsListAdapter.notifyDataSetChanged();
                loadNewsAsyncTask = new LoadNewsAsyncTask();
                loadNewsAsyncTask.execute(mCid, 0, true);
            }
        });

        // 水平滑动实例化
        final HorizontalScrollView categoryScrollView = (HorizontalScrollView) findViewById(R.id.category_scrollview);
//		右箭头貌似没什么用
        Button categoryArrowRight = (Button) findViewById(R.id.category_arrow_right);
        categoryArrowRight.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
//				点一下就会往右滑动一段距离
                categoryScrollView.fling(mFlingVelocityDip);
            }
        });
//		获取指定栏目的新闻列表
        getSpeCateNews(mCid, mNewsData, 0, true);
        mNewsListAdapter = new SimpleAdapter(this, mNewsData, R.layout.newslist_item,
                new String[]{"newslist_item_title", "newslist_item_digest", "newslist_item_source", "newslist_item_ptime"},
                new int[]{R.id.newslist_item_title, R.id.newslist_item_digest, R.id.newslist_item_source, R.id.newslist_item_ptime});
        View loadMoreLayout = mInflater.inflate(R.layout.loadmore, null);
//		在页脚加载一个view “加载更多layout”
        mNewsList.addFooterView(loadMoreLayout);
        mNewsList.setAdapter(mNewsListAdapter);
//		新闻列表添加监听器，点击后进入newsdetailactivity
        mNewsList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(NewsActivity.this, NewsDetailsActivity.class);
                //把需要的信息放到Intent中;
                intent.putExtra("categoryName", mCatName);
                intent.putExtra("newsDate", mNewsData);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

        mLoadMoreBtn = (Button) findViewById(R.id.loadmore_btn);
        mLoadMoreBtn.setOnClickListener(loadMoreListener);

        loadNewsAsyncTask = new LoadNewsAsyncTask();
        loadNewsAsyncTask.execute(mCid, 0, true);
    }

    /**
     * 获取指定类型的新闻列表
     *
     * @param cid        类型ID
     * @param newsList   保存新闻信息的集合
     * @param startid    分页
     * @param firstTimes 是否第一次加载
     */
//	获取特定种类的新闻 把获取的新闻填充到newslist中
    private int getSpeCateNews(int cid, List<HashMap<String, Object>> newsList, int startid, Boolean firstTimes) {
        if (firstTimes) {
            //如果是第一次，则清空集合里数据
            newsList.clear();
        }

        //请求URL和字符串
        String url = "/getSpecifyCategoryNews";
        String params = "startid=" + startid + "&count=" + NEWSCOUNT + "&cid=" + cid;
        SyncHttp syncHttp = new SyncHttp();
        try {
            //以Get方式请求，并获得返回结果
            String retStr = syncHttp.httpGet(url, params);
            JSONObject jsonObject = new JSONObject(retStr);
            //获取返回码，0表示成功
            int retCode = jsonObject.getInt("ret");
            if (0 == retCode) {
                JSONObject dataObject = jsonObject.getJSONObject("data");
                //获取返回数目
                int totalnum = dataObject.getInt("totalnum");
                if (totalnum > 0) {
                    //获取返回新闻集合
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

    private OnClickListener loadMoreListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            loadNewsAsyncTask = new LoadNewsAsyncTask();
            switch (v.getId()) {
                case R.id.loadmore_btn:
                    //获取该栏目下新闻
                    getSpeCateNews(mCid, mNewsData, mNewsData.size(), false);
                    //通知ListView进行更新
                    mNewsListAdapter.notifyDataSetChanged();
                    loadNewsAsyncTask.execute(mCid, mNewsData.size(), false);
                    break;
                case R.id.titlebar_refresh:
                    loadNewsAsyncTask.execute(mCid, 0, true);
                    break;
            }

        }
    };

    //	“加载更多” 异步任务
    private class LoadNewsAsyncTask extends AsyncTask<Object, Integer, Integer> {

        @Override

        protected void onPreExecute() {
            //隐藏刷新按钮
            //显示进度条
            //设置LoadMore Button 显示文本
            mLoadMoreBtn.setText(R.string.loadmore_txt);
        }

        @Override
        protected Integer doInBackground(Object... params) {
            return getSpeCateNews((Integer) params[0], mNewsData, (Integer) params[1], (Boolean) params[2]);
        }

        @Override
        protected void onPostExecute(Integer result) {
            //根据返回值显示相关的Toast
            switch (result) {
                case NONEWS:
                    Toast.makeText(NewsActivity.this, R.string.no_news, Toast.LENGTH_LONG).show();
                    break;
                case NOMORENEWS:
                    Toast.makeText(NewsActivity.this, R.string.no_more_news, Toast.LENGTH_LONG).show();
                    break;
                case LOADERROR:
                    Toast.makeText(NewsActivity.this, R.string.load_news_failure, Toast.LENGTH_LONG).show();
                    break;
            }
            mNewsListAdapter.notifyDataSetChanged();
            //显示刷新按钮
            //隐藏进度条
            //设置LoadMore Button 显示文本
            mLoadMoreBtn.setText(R.string.loadmore_btn);
        }
    }
}