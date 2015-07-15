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
		
//		����һ��byte���͵Ļ�����������Ҫ�����ȥ�����ݻ㼯����һ���Դ����ȥ��
			 ByteArrayOutputStream baos = new ByteArrayOutputStream();
		     Bitmap bitmap = ((BitmapDrawable)mContext.getResources().getDrawable(id)).getBitmap();
	     
//		     ��bitmapͼƬѹ����png��ʽ��ѹ����100%��ѹ����baos��
		     bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//		     System.out.println("ͼƬ�Ĵ�С��"+baos.toByteArray().length);  
		     byte[] buffer = baos.toByteArray();
		     String photo = Base64.encodeToString(buffer, 0, buffer.length,Base64.DEFAULT); 
		     return photo;
	}
 
}
