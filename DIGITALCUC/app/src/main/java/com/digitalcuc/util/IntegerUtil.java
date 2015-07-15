package com.digitalcuc.util;


public class IntegerUtil
{
	
	public static String Int2String(Integer i)
	{
		try
		{
			String value = String.valueOf(i);
			return value;
		} catch (Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}
}