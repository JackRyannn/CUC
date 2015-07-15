package com.digitalcuc.mywidget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalcuc.MainActivity;
import com.digitalcuc.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.w3c.dom.Text;

public class TitleBar extends RelativeLayout {

    TextView tv1;
    ImageView iv1;
    ImageView iv2;
    Button btn1;
    Button btn2;
    Button btn3;

    public TitleBar(Context context) {
        super(context);


    }

    public TitleBar(final Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.widget_titlebar, this);
        btn1 = (Button) findViewById(R.id.refresh);
        btn2 = (Button) findViewById(R.id.comments_pic);
        btn3 = (Button) findViewById(R.id.comments_number);
        iv1 = (ImageView) findViewById(R.id.imageButton);
        iv2 = (ImageView) findViewById(R.id.imageButton2);
        tv1 = (TextView) findViewById(R.id.textView);
        tv1.setText("Visitor");
        iv1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = (Activity) context;
                activity.finish();
            }
        });

    }

    //ªÒ»°menu
    public void showMenu(final SlidingMenu menu) {
        iv2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                menu.showMenu();

            }
        });
    }

    public void setTitle(String str) {
        tv1.setText(str);
    }

    public void setIv2Enable(int i) {
        iv2.setVisibility(i);
    }

    public void setBtn1Enable(int i) {
        btn1.setVisibility(i);
    }

    public void setBtn2Enable(int i) {
        btn2.setVisibility(i);
    }

    public void setBtn3Enable(int i) {
        btn3.setVisibility(i);
    }

    public Button getBtn2() {
        return btn2;
    }

    public Button getBtn3() {
        return btn3;
    }
}
