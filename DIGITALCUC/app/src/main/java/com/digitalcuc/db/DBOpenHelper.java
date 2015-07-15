package com.digitalcuc.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {     
	private static String name="digitalcuc.db";
	private static String TBL_NAME="tbl_stu";
	private static  int version=1;
	private SQLiteDatabase db;
	     public DBOpenHelper(Context context) 
	  {     
	    super(context,name,null,version);     
	     }     
	     
	     @Override    
	     public void onCreate(SQLiteDatabase db) {  
	    	 this.db=db;
	    	 this.db.execSQL("CREATE TABLE "+"tbl_stu(_id INTEGER PRIMARY KEY AUTOINCREMENT,num INTEGER, password INTEGER,phone INTEGER);");  
	     }     
	     
	     @Override    
	 public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {     
	    	 
	     }     
	     
	 @Override    
	 public void onOpen(SQLiteDatabase db) {     
	         super.onOpen(db);       
	     }
	 public void insert(ContentValues values){
		 SQLiteDatabase db=getWritableDatabase();
		 db.insert(TBL_NAME, null, values);
		 db.close();
	 }
	 public Cursor query(){
		 SQLiteDatabase db=getWritableDatabase();
		 Cursor c=db.query(TBL_NAME,null,null,null,null,null,null);
		 return c;
	 }
	 
	 }     