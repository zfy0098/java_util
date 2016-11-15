package com.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.db.service.DBOperation;
import com.tool.LogTool;

public class TecrwinService {

	LogTool logtool = new LogTool();
	
	public static final String KEY_ALGORTHM = "RSA";//
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	public static final String PUBLIC_KEY = "PUBLIC_KEY";// 公钥
	public static final String PRIVATE_KEY = "PRIVATE_KEY";
	
	

	private String orgCode = "000005"; // 机构号

	public void send() {
		String url = "http://";

		
		SimpleDateFormat sf = new SimpleDateFormat ("yyyyMMddHHmmss");
		String sendTime = sf.format(new Date());
	    String orderid = sendTime + String.format("%06d", DBOperation.getSeqNo());
	    
		
		Map<String, String> map = new TreeMap<String, String>();
		map.put("orgId", orgCode);
		map.put("source", "0");
		map.put("settleAmt", "100");
		map.put("account", "13159949876");
		map.put("amount", "2000");
		map.put("tranTp", "0");
		map.put("orgOrderNo", orderid);
//		map.put("notifyUrl","211.103.172.38");
		
		String result = post(url,sendStr(map),null);

	}

	

	
	
	
	/**
	 *     参数根据key加密 调用post方法 发送请求 
	 * @param url
	 * @param resultMap
	 * @return   请求接口返回的数据
	 */
	public Map<String,String> sendStr(Map<String,String> resultMap){
		StringBuffer sbf = new StringBuffer();
		
		Map<String , String> map = resultMap;
		
		for (String key : map.keySet()) {
			sbf.append(key);
			sbf.append("=");
			sbf.append(map.get(key));
			sbf.append("&");
		}
		String string1 = "";
		try {
			string1 = sbf.substring(0,sbf.toString().length()-1);
			System.out.println(string1);
			String sign = sign(string1.getBytes(),PRIVATE_KEY);
			map.put("signature", sign.replaceAll("\r", "").replaceAll("\n", ""));
		} catch (IndexOutOfBoundsException e) {
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
		return  map;
	}
	
	
	/**
	 *     发送http post请求
	 * @param callURL   请求的目标地址
	 * @param resultMap 请求参数
	 * @param paramtype 参数格式：1位键值对 key-value形式 其他为json格式
	 * @return
	 */
	public String post(String callURL, Map<String,String> resultMap,String paramtype){ 

		RequestConfig config = RequestConfig.custom()
				.setConnectionRequestTimeout(40000).setConnectTimeout(40000)
				.setSocketTimeout(40000).build();

		HttpPost httppost = new HttpPost(callURL);

		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();// 设置进去

		if(paramtype!=null&&paramtype.equals("1")){
		//  key value 形式
			if(resultMap!=null){
				List<NameValuePair> formparams = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> entry : resultMap.entrySet()) {  
					  
				    formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue())); 
				}  
			    UrlEncodedFormEntity uefEntity;
				try {
					uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
					httppost.setEntity(uefEntity);
				} catch (UnsupportedEncodingException e) {
					System.out.println(e.getMessage());
					logtool.error(e.getMessage());
					return "";
				} 
			}
		}else{
			System.out.println(JSONObject.fromObject(resultMap).toString());
			
			StringEntity rsqentity = new StringEntity(JSONObject.fromObject(resultMap).toString(), "utf-8");
			rsqentity.setContentEncoding("UTF-8");
			rsqentity.setContentType("application/json");
			httppost.setEntity(rsqentity);
			
		}
		
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
	

	/**
	 *	用私钥对信息生成数字签名
	 * @param data	//加密数据
	 * @param privateKey	//私钥
	 * @return
	 * @throws Exception
	 */
	public static String sign(byte[] data,String privateKey)throws Exception{
		//解密私钥
		byte[] keyBytes = decryptBASE64(privateKey);
		//构造PKCS8EncodedKeySpec对象
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
		//指定加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
		//取私钥匙对象
		PrivateKey privateKey2 = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
		//用私钥对信息生成数字签名
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(privateKey2);
		signature.update(data);
		
		return encryptBASE64(signature.sign());
	}


	
	/**
	 * 校验数字签名
	 * @param data	加密数据
	 * @param publicKey	公钥
	 * @param sign	数字签名
	 * @return
	 * @throws Exception
	 */
	public static boolean verify(byte[] data,String publicKey,String sign)throws Exception{
		//解密公钥
		byte[] keyBytes = decryptBASE64(publicKey);
		//构造X509EncodedKeySpec对象
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
		//指定加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
		//取公钥匙对象
		PublicKey publicKey2 = keyFactory.generatePublic(x509EncodedKeySpec);
		
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(publicKey2);
		signature.update(data);
		//验证签名是否正常
		return signature.verify(decryptBASE64(sign));
		
	}
	
	/**
	 * BASE64解密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptBASE64(String key) throws Exception {
		return (new BASE64Decoder()).decodeBuffer(key);
	}

	/**
	 * BASE64加密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] key) throws Exception {
		return (new BASE64Encoder()).encodeBuffer(key);
	}

	public static void main(String[] args) throws Exception {
		TecrwinService tecrwin = new TecrwinService();

	}
}
