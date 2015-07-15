package com.digitalcuc.util;

import java.io.ByteArrayOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;


public class ImageUtil
{
	
	public static String Image2String(Context mContext,int id)
	{
		
//		建立一个byte类型的缓冲区，将将要传输出去的数据汇集起来一次性传输出去。
			 ByteArrayOutputStream baos = new ByteArrayOutputStream();
		     Bitmap bitmap = ((BitmapDrawable)mContext.getResources().getDrawable(id)).getBitmap();
	     
//		     将bitmap图片压缩成png格式，压缩率100%，压缩进baos中
		     bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//		     System.out.println("图片的大小："+baos.toByteArray().length);  
		     byte[] buffer = baos.toByteArray();
		     String photo = Base64.encodeToString(buffer, 0, buffer.length,Base64.DEFAULT); 
		     return photo;
	}
 
}
