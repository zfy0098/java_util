package com.rom.util.httpclient;

import java.io.IOException;  
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;


import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


/**
 *    jar 文件：   commons-logging-1.2.jar
 *    			httpclient-4.5.jar
 *    			httpcore-4.4.1.jar
 * @author zfy
 *
 */
public class Test_api_threed {
	
	private String name;
	
	public Test_api_threed(String name){
		this.name = name;
	}
	
	public static void main(String[] args) {
		int count = 10;
		CyclicBarrier cyclicBarrier = new CyclicBarrier(count);
		ExecutorService executorService = Executors.newFixedThreadPool(count);
		for (int i = 0; i < count; i++){
			executorService.execute(new Test_api_threed(i + "").new Task(cyclicBarrier));
		}
		executorService.shutdown();
		while (!executorService.isTerminated()) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public class Task implements Runnable {
		private CyclicBarrier cyclicBarrier;

		public Task(CyclicBarrier cyclicBarrier) {
			this.cyclicBarrier = cyclicBarrier;
		}

		@Override
		public void run() {
			try {
				cyclicBarrier.await();
				CloseableHttpClient httpClient = HttpClients.createDefault();
				HttpPost post = new HttpPost(
						"http://localhost:9090/test/test.html");
				RequestConfig config = RequestConfig.custom()
						.setConnectionRequestTimeout(5000)
						.setConnectTimeout(5000).setSocketTimeout(5000)
						.build();
				CloseableHttpResponse response = null;
				try {
					
					ThreadLocalRandom random = ThreadLocalRandom.current();
					String id = random.nextInt(10000,99999) + "";
					
					
					 List<NameValuePair> formparams = new ArrayList<NameValuePair>(); 
					 formparams.add(new BasicNameValuePair("id", id)); 
					 formparams.add(new BasicNameValuePair("name", name));
					 post.setEntity(new UrlEncodedFormEntity(formparams, "UTF-8"));
					
//					post.setConfig(config);
					response = httpClient.execute(post);
					HttpEntity entity = response.getEntity();
					String content = EntityUtils.toString(entity);
					System.out.println("content:" + content);
					EntityUtils.consume(entity);

//					JSONObject jsonObj = JSONObject.fromObject(content);
//					Object appleid = jsonObj.get("info");
//					JSONObject jsonObj_1 = JSONObject.fromObject(appleid);
//					String appleid_1 = jsonObj_1.getString("appleid");
//					System.out.println(appleid_1);
//					Thread.sleep(1000);
//
//					HttpPost post_1 = new HttpPost(
//							"http://192.168.1.198:9090/iosapple/client/appcheck.html");
//					RequestConfig config_1 = RequestConfig.custom()
//							.setConnectionRequestTimeout(10000)
//							.setConnectTimeout(10000).setSocketTimeout(10000)
//							.build();
//					CloseableHttpResponse response_1 = null;
//					List<NameValuePair> formparams = new ArrayList<NameValuePair>();
//					formparams.add(new BasicNameValuePair("appleid", appleid_1));
//					formparams.add(new BasicNameValuePair("result", "0"));
//					post_1.setEntity(new UrlEncodedFormEntity(formparams, "UTF-8"));
////					post_1.setConfig(config_1);
//					response_1 = httpClient.execute(post_1);
//					HttpEntity entity_1 = response_1.getEntity();
//					String content_1 = EntityUtils.toString(entity_1);
//					System.out.println("content:" + content_1);
//					EntityUtils.consume(entity_1);
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						response.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
