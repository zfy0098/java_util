package com.rom.util.httpclient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class Test extends BaseDao{

	public void post(){
		 
		String sql = "select * from network where code='20000' and type=3 and keyss='969c7f7c'";
		List<Map<String, Object>> list = queryForList(sql, null);
		
		System.out.println(list.size());
		
		for (Map<String, Object> map : list) {
			
			System.out.println(map.get("transNo").toString());
			
			 // 创建默认的httpClient实例. 
	        HttpClient httpclient = new DefaultHttpClient(); 
	        // 创建httppost 
	        HttpPost httppost = new HttpPost("http://123.57.138.106:8082/ispNotify/services/InfiniteCallBack"); 
	        // 创建参数队列 
	        List<NameValuePair> formparams = new ArrayList<NameValuePair>(); 
	        formparams.add(new BasicNameValuePair("code", "20000")); 
	        formparams.add(new BasicNameValuePair("message", "请求自服平台超时"));
	        formparams.add(new BasicNameValuePair("request_no", map.get("transNo").toString()));
	        UrlEncodedFormEntity uefEntity; 
	        try { 
	            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8"); 
	            httppost.setEntity(uefEntity); 
//	            System.out.println("executing request " + httppost.getURI()); 
	            HttpResponse response; 
	            response = httpclient.execute(httppost); 
	            HttpEntity entity = response.getEntity(); 
	            if (entity != null) { 
	                System.out.println("--------------------------------------"); 
	                System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8")); 
	                System.out.println("--------------------------------------"); 
	            } 
	        } catch (ClientProtocolException e) { 
	            e.printStackTrace(); 
	        } catch (UnsupportedEncodingException e1) { 
	            e1.printStackTrace(); 
	        } catch (IOException e) { 
	            e.printStackTrace(); 
	        } finally { 
	            // 关闭连接,释放资源 
	            httpclient.getConnectionManager().shutdown(); 
	        } 
		}
	}
	
	
	public static void main(String[] args) {
		Test test = new Test();
		test.post();
	}
}
