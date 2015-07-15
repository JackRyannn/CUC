package com.digitalcuc;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Seat extends LinearLayout {
 
    private ImageView imageView;
     
    public Seat(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }
    public Seat(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.seat, this);
        imageView=(ImageView)findViewById(R.drawable.nonseat);
    }

    public void setImageResource(int resId) { 
        imageView.setImageResource(resId); 
    } 

 
}