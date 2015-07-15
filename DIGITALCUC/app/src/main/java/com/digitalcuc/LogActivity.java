package com.digitalcuc;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalcuc.service.SyncHttp;

public class LogActivity extends Activity {
    private Button b1 = null;
    private EditText numEditText;
    private EditText pwEditText;
	private TextView validateEmail;
    private Single_Account single_account = Single_Account.getAccount();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_log);
        b1 = (Button) findViewById(R.id.button1);
        numEditText = (EditText) findViewById(R.id.editText1);
        pwEditText = (EditText) findViewById(R.id.editText2);
        validateEmail = (TextView)findViewById(R.id.validateEmail);
        b1.setOnClickListener(new OnClickListener() {
                                  @Override
                                  public void onClick(View v) {
                                      String num = numEditText.getText().toString();
                                      String pw = pwEditText.getText().toString();
                                      if ("123".equals(num)) {
                                          single_account.setAccount_name(num);
                                          Intent intent = new Intent();
                                          intent.setClass(getApplicationContext(), MainActivity.class);
                                          startActivity(intent);
                                      }
                                      LogValidateTask task = new LogValidateTask();
                                      task.execute(num, pw);

                                  }
                              }
        );

		validateEmail.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LogActivity.this, RegActivity.class);
                startActivity(intent);
            }
        });
    }

    public int LogValidate(String num, String pw) {
        String url = "/logValidate";
        String params = "num=" + num + "&pw=" + pw;
        SyncHttp syncHttp = new SyncHttp();
        String retStr = null;
        int retCode = 4;
        try {
            retStr = syncHttp.httpGet(url, params);
            JSONObject jsonObject = new JSONObject(retStr);
            retCode = jsonObject.getInt("ret");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return retCode;
    }

    private class LogValidateTask extends AsyncTask<String, Integer, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            return LogValidate((String) params[0], (String) params[1]);

        }

        protected void onPostExecute(Integer result) {

            String INFO;
            Toast t;
            switch (result) {
                case 1:
                    Intent intent = new Intent();
                    intent.setClass(LogActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
                case 2:
                    INFO = "密码错误！";
                    t = Toast.makeText(getApplicationContext(), INFO, Toast.LENGTH_SHORT);
                    t.show();
                    break;
                case 3:
                    INFO = "网络连接失败！！！！！!!!";
                    t = Toast.makeText(getApplicationContext(), INFO, Toast.LENGTH_SHORT);
                    t.show();
                    break;

            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;

    }
}
