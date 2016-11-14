package com.rom.util.httpclient;

import java.io.File;  
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 *  httpClient 4.4 版本请求
 * @author DLHT
 * 2016年1月25日下午2:22:38
 * HttpClient4_4.java
 * DLHT
 */
public class HttpClient4_4 {
	public static void main(String[] args) {
		HttpClient4_4 http = new HttpClient4_4();
		http.test();
		
	}
	
	public void test() {
		RequestConfig config = RequestConfig.custom()
				.setConnectionRequestTimeout(100000).setConnectTimeout(100000)
				.setSocketTimeout(100000).build();
		
		
		HttpGet httpGet = new HttpGet("http://apis.baidu.com/showapi_open_bus/showapi_joke/joke_pic?page=1");
		
		
		httpGet.setHeader("apikey","305c5324ee9931804d4249815bcea961");
		
		//httpGet.setConfig(config);
		
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(null).build();//设置进去
		
		//HttpClients.createDefault();
		
		CloseableHttpResponse  response = null;
		StringBuffer out;
		try {
			response = httpClient.execute(httpGet); 
			
			System.out.println(response.getStatusLine().getStatusCode());
			
			HttpEntity entity = response.getEntity();
			InputStream in =  entity.getContent();
			
			out = new StringBuffer();  
			byte[] b = new byte[4096];  
			for (int n; (n = in.read(b)) != -1;) {  
			    out.append(new String(b, 0, n));  
			}
			System.out.println(out.toString());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	public String post(String callURL, Map<String,String> resultMap) throws UnsupportedEncodingException {
		RequestConfig config = RequestConfig.custom()
				.setConnectionRequestTimeout(40000).setConnectTimeout(40000)
				.setSocketTimeout(40000).build();

		HttpPost httppost = new HttpPost(callURL);

		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();// 设置进去

		//  key value 形式
		if(resultMap!=null){
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			for (Map.Entry<String, String> entry : resultMap.entrySet()) {  
				  
			    formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue())); 
			}  
		    UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8"); 
		    httppost.setEntity(uefEntity);
		}
		
		// json 格式
//		StringEntity rsqentity = new StringEntity(JSONObject.fromObject(resultMap).toString(), "utf-8");
//		rsqentity.setContentEncoding("UTF-8");
//		rsqentity.setContentType("application/json");
//		httppost.setEntity(rsqentity);

		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httppost);

			System.out.println(response.getStatusLine().getStatusCode());

			HttpEntity rspentity = response.getEntity();
			InputStream in = rspentity.getContent();

			String temp;
			BufferedReader data = new BufferedReader(new InputStreamReader(in, "utf-8"));
			StringBuffer result = new StringBuffer();
			while ((temp = data.readLine()) != null) {
				result.append(temp);
				temp = null;
			}
			System.out.println("content:"+ result.toString());
			return result.toString();
		} catch (ClientProtocolException e) {
			logtool.error(e.getMessage());
			System.out.println(e.getMessage());
		} catch (IllegalStateException e) {
			logtool.error(e.getMessage());
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
			logtool.error(e.getMessage());
		} finally {
			try {
				response.close();
				httpClient.close();
			} catch (IOException e) {
				logtool.error(e.getMessage());
			}
		}
		return null;
	}
	
	
	
	  public void upload() {  

	        CloseableHttpClient httpclient = HttpClients.createDefault();  

	        try {  

	            HttpPost httppost = new HttpPost("http://localhost:8080/myDemo/Ajax/serivceFile.action");  

	            FileBody bin = new FileBody(new File("F:\\image\\sendpix0.jpg"));  

	            StringBody comment = new StringBody("A binary file of some kind", ContentType.TEXT_PLAIN);  

	            HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("bin", bin).addPart("comment", comment).build();  

	            httppost.setEntity(reqEntity);  

	  

	            System.out.println("executing request " + httppost.getRequestLine());  

	            CloseableHttpResponse response = httpclient.execute(httppost);  

	            try {  

	                System.out.println("----------------------------------------");  

	                System.out.println(response.getStatusLine());  

	                HttpEntity resEntity = response.getEntity();  

	                if (resEntity != null) {  

	                    System.out.println("Response content length: " + resEntity.getContentLength());  

	                }  

	                EntityUtils.consume(resEntity);  

	            } finally {  

	                response.close();  

	            }  

	        } catch (ClientProtocolException e) {  

	            e.printStackTrace();  

	        } catch (IOException e) {  

	            e.printStackTrace();  

	        } finally {  

	            try {  

	                httpclient.close();  

	            } catch (IOException e) {  

	                e.printStackTrace();  

	            }  

	        }  

	    }  
}
