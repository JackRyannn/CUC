package com.digitalcuc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.digitalcuc.service.SyncHttp;

public class BbsActivity extends Activity {
    private Context mContext;
    private final int NOTECOUNT = 10;
    private final int SUCCESS = 0;
    private final int NONOTES = 1;
    private final int NOMORENOTES = 2;
    private final int LOADERROR = 3;

    private ArrayList<HashMap<String, Object>> mNotesData;
    private LayoutInflater mInflater;

    private ListView mNotesList;
    private int mCid = 1;
    private String mCatName;
    private SimpleAdapter mNotesListAdapter;
    private Button mTitlebarRefresh;
    private Button mTitlebarBack;
    private ProgressBar mLoadnotesProgress;
    private Button mLoadMoreBtn;

    private LoadNotesAsyncTask LoadNotesAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bbs);
        mInflater = getLayoutInflater();
        mNotesData = new ArrayList<HashMap<String, Object>>();
        mNotesList = (ListView) findViewById(R.id.noteslist);
        mTitlebarRefresh = (Button) findViewById(R.id.titlebar_refresh);
        mTitlebarBack = (Button) findViewById(R.id.titlebar_backbtn);
        mLoadnotesProgress = (ProgressBar) findViewById(R.id.loadnotes_progress);
        mTitlebarRefresh.setOnClickListener(loadMoreListener);
        mTitlebarBack.setOnClickListener(new OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 Intent intent = new Intent();
                                                 intent.setClass(BbsActivity.this, MainActivity.class);
                                                 startActivity(intent);
                                             }
                                         }
        );
        getSpeCateNotes(mCid, mNotesData, 0, true);
        mNotesListAdapter = new SimpleAdapter(this, mNotesData, R.layout.newslist_item,
                new String[]{"noteslist_item_title", "noteslist_item_source", "noteslist_item_ptime"},
                new int[]{R.id.newslist_item_title, R.id.newslist_item_source, R.id.newslist_item_ptime});
        View loadMoreLayout = mInflater.inflate(R.layout.loadmore, null);
//				��ҳ�ż���һ��view �����ظ��layout��
        mNotesList.addFooterView(loadMoreLayout);
        mNotesList.setAdapter(mNotesListAdapter);
//				�����б���Ӽ���������������newsdetailactivity
        mNotesList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BbsActivity.this, NoteActivity.class);
                //����Ҫ����Ϣ�ŵ�Intent��;
                intent.putExtra("categoryName", mCatName);
                intent.putExtra("notesDate", mNotesData);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

        mLoadMoreBtn = (Button) findViewById(R.id.loadmore_btn);
        mLoadMoreBtn.setOnClickListener(loadMoreListener);
        LoadNotesAsyncTask = new LoadNotesAsyncTask();
        LoadNotesAsyncTask.execute(mCid, 0, true);

        mContext = this;
        BbsBottomBtn Btn = (BbsBottomBtn) findViewById(R.id.bbsbottombtn);
        Btn.sendContext(mContext);
    }

    /**
     * ��ȡָ�����͵������б�
     *
     * @param cid        ����ID
     * @param newsList   ����������Ϣ�ļ���
     * @param startid    ��ҳ
     * @param firstTimes �Ƿ��һ�μ���
     */
//			��ȡ�ض���������� �ѻ�ȡ��������䵽newslist��
    private int getSpeCateNotes(int cid, List<HashMap<String, Object>> notesList, int startid, Boolean firstTimes) {
        if (firstTimes) {
            //����ǵ�һ�Σ�����ռ��������
            notesList.clear();
        }

        //����URL���ַ�
        String url = "/getSpecifyCategoryNotes";
        String params = "startid=" + startid + "&count=" + NOTECOUNT + "&cid=" + cid;
        SyncHttp syncHttp = new SyncHttp();
        try {
            //��Get��ʽ���󣬲���÷��ؽ��
            String retStr = syncHttp.httpGet(url, params);
            JSONObject jsonObject = new JSONObject(retStr);
            //��ȡ�����룬0��ʾ�ɹ�
            int retCode = jsonObject.getInt("ret");
            if (0 == retCode) {
                JSONObject dataObject = jsonObject.getJSONObject("data");
                //��ȡ������Ŀ
                int totalnum = dataObject.getInt("totalnum");
                if (totalnum > 0) {
                    //��ȡ�������ż���
                    JSONArray noteslist = dataObject.getJSONArray("noteslist");
                    for (int i = 0; i < noteslist.length(); i++) {
                        JSONObject notesObject = (JSONObject) noteslist.opt(i);
                        HashMap<String, Object> hashMap = new HashMap<String, Object>();
                        hashMap.put("id", notesObject.getInt("id"));
                        hashMap.put("noteslist_item_title", notesObject.getString("title"));
                        hashMap.put("noteslist_item_source", notesObject.getString("source"));
                        hashMap.put("noteslist_item_ptime", notesObject.getString("ptime"));
                        hashMap.put("noteslist_item_comments", notesObject.getString("commentcount"));
                        notesList.add(hashMap);
                    }
                    return SUCCESS;
                } else {
                    if (firstTimes) {
                        return NONOTES;
                    } else {
                        return NOMORENOTES;
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
            LoadNotesAsyncTask = new LoadNotesAsyncTask();
            switch (v.getId()) {
                case R.id.loadmore_btn:
                    getSpeCateNotes(mCid, mNotesData, mNotesData.size(), false);
                    mNotesListAdapter.notifyDataSetChanged();
                    LoadNotesAsyncTask.execute(mCid, mNotesData.size(), false);
                    break;
                case R.id.titlebar_refresh:
                    LoadNotesAsyncTask.execute(mCid, 0, true);
                    break;
            }

        }
    };

    private class LoadNotesAsyncTask extends AsyncTask<Object, Integer, Integer> {

        @Override

        protected void onPreExecute() {
            mTitlebarRefresh.setVisibility(View.GONE);
            mLoadnotesProgress.setVisibility(View.VISIBLE);
            mLoadMoreBtn.setText(R.string.loadmore_txt);
        }

        @Override
        protected Integer doInBackground(Object... params) {
            return getSpeCateNotes((Integer) params[0], mNotesData, (Integer) params[1], (Boolean) params[2]);
        }

        @Override
        protected void onPostExecute(Integer result) {
            switch (result) {
                case NONOTES:
                    Toast.makeText(BbsActivity.this, R.string.no_notes, Toast.LENGTH_LONG).show();
                    break;
                case NOMORENOTES:
                    Toast.makeText(BbsActivity.this, R.string.no_more_notes, Toast.LENGTH_LONG).show();
                    break;
                case LOADERROR:
                    Toast.makeText(BbsActivity.this, R.string.load_notes_failure, Toast.LENGTH_LONG).show();
                    break;
            }
            mNotesListAdapter.notifyDataSetChanged();
            mTitlebarRefresh.setVisibility(View.VISIBLE);
            mLoadnotesProgress.setVisibility(View.GONE);
            mLoadMoreBtn.setText(R.string.loadmore_btn);
        }


    }

}
