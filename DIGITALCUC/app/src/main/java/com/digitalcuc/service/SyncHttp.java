package com.digitalcuc.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.digitalcuc.model.Parameter;




public class SyncHttp
{
	public String httpGet(String url, String params) throws Exception
	{
		String response = null; 
//		params不是空值 则 url后面把params值加上
		String Url = "";
		if (null!=params&&!params.equals(""))
		{
//			192.168.1.108:8080/web/getActInfo?num=123 这样服务器端就可以接收了~
			 Url = "http://www.kan-shu.cn"+url+"?" + params;
		}
		
		int timeoutConnection = 3000;  
		int timeoutSocket = 5000;  
		HttpParams httpParameters = new BasicHttpParams();// Set the timeout in milliseconds until a connection is established.  
//	    建立连接如果超时则断开
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);// Set the default socket timeout (SO_TIMEOUT) // in milliseconds which is the timeout for waiting for data.  
//	 	建立连接收不到包超时则断开
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);  
	    
		HttpClient httpClient = new DefaultHttpClient(httpParameters);  
		HttpGet httpGet = new HttpGet(Url);
		
		try
		{
			HttpResponse httpResponse = httpClient.execute(httpGet);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) //SC_OK = 200
			{
//				将响应httpResponse实例转换成string串
				response = EntityUtils.toString(httpResponse.getEntity());
			}
			else
			{
				response = "状态码"+statusCode;
			}
		} catch (Exception e)
		{
			throw new Exception(e);			
		} 
		
		return response;
	}
	

	public String httpPost(String url, List<Parameter> params) throws Exception
	{
		String response = null;
		int timeoutConnection = 3000;  
		int timeoutSocket = 5000;  
		String Url = "http://www.kan-shu.cn"+url;
		HttpParams httpParameters = new BasicHttpParams();// Set the timeout in milliseconds until a connection is established.  
	    HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);// Set the default socket timeout (SO_TIMEOUT) // in milliseconds which is the timeout for waiting for data.  
	    HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);  
		HttpClient httpClient = new DefaultHttpClient(httpParameters);  
		HttpPost httpPost = new HttpPost(Url);
		if (params.size()>=0)
		{
			httpPost.setEntity(new UrlEncodedFormEntity(buildNameValuePair(params),HTTP.UTF_8));
		}
		HttpResponse httpResponse = httpClient.execute(httpPost);
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		if(statusCode==HttpStatus.SC_OK)
		{
			response = EntityUtils.toString(httpResponse.getEntity());
		}
		else
		{
			response = "状态码"+statusCode;
		}
		return response;
	}
	private List<BasicNameValuePair> buildNameValuePair(List<Parameter> params)
	{
		List<BasicNameValuePair> result = new ArrayList<BasicNameValuePair>();
		for (Parameter param : params)
		{
			BasicNameValuePair pair = new BasicNameValuePair(param.getName(), param.getValue());
			result.add(pair);
		}
		return result;
	}
}
