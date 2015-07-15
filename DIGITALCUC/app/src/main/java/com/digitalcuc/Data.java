package com.digitalcuc;

import android.app.Application;

  
public class Data extends Application{  
    private String G_id;  
      
    public String getG_id(){  
        return this.G_id;  
    }  
    public void  setG_id(String c){
    	this.G_id = c;
    }
    @Override  
    public void onCreate(){  
        G_id = "Stranger";  
        super.onCreate();  
    }  
}  