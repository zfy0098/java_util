package com.rom.util.des;

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

import org.apache.commons.codec.binary.Base64;
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



public class TecrwinService {

	
	public static final String KEY_ALGORTHM = "RSA";//
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	public static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApZVmx6x8Q3rdj6WP0T9xB+1OHPjdGKM7F/0+N4Xax+C7jOE/CvmlJQhqan0QMWXZR42l22cnDwP2UJevWBqFgws3NHW/2XJiDwkdlRoQ78TkJsnEKAkcevWfZQSM2387oGDoB8eQ8gpfaTt7HiqRk3F5fWh9c3pteosyr5+L4x23gYQ9Db867PEavGVHsf64fhKSbeK7gGJtoIgtR/uYOwT/Pp3WfEv2bWmyMuKqaXuGJNLawYEJYJFrceWf8J2uAHqinI04PStQwiaZprUmvbck/fmYLEw2sJh7exLgBU+1V2I6eQHS6k1sWaGBBOa96+0SCPNG9cz3bdkCT1CaPwIDAQAB";// 公钥
	public static final String PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCllWbHrHxDet2PpY/RP3EH7U4c+N0YozsX/T43hdrH4LuM4T8K+aUlCGpqfRAxZdlHjaXbZycPA/ZQl69YGoWDCzc0db/ZcmIPCR2VGhDvxOQmycQoCRx69Z9lBIzbfzugYOgHx5DyCl9pO3seKpGTcXl9aH1zem16izKvn4vjHbeBhD0Nvzrs8Rq8ZUex/rh+EpJt4ruAYm2giC1H+5g7BP8+ndZ8S/ZtabIy4qppe4Yk0trBgQlgkWtx5Z/wna4AeqKcjTg9K1DCJpmmtSa9tyT9+ZgsTDawmHt7EuAFT7VXYjp5AdLqTWxZoYEE5r3r7RII80b1zPdt2QJPUJo/AgMBAAECggEBAJoE/jV9G5kpEC4tsjjQ6jKVabNJSV9BdhY6WUUMHRUO1WWVny52SEbKLTIgYo+QTSthdoU+zfYJmXQdo9wvz0C1jgQeAvYgj1WtBPdL57UfT5lE1FDpKLS3BCzSrrL9BlmHykxyNUDupREdYsgMkJy1UGx2ZTqlBnCWSe9SWKxH2OuUOg6qn4by8gkcKO4kQP3iNfXwlF9HeBO+PrcWLxtgd3HQ2IscMdhGluKW3yYjyou6hHlAyPxExVJEdgq2g70XPsFdITQgN245GGq823zV80ZE8Ess/PP8Uaur72C18IEJAVT1Xxozu+eWBykNWX8ctoEWdT7F373PMrEe9wECgYEA0oWlSJSwrSHOA0B2DEcq+BhHTmX6vggT4MpNKWg3WRrw5xjZJfFFjdwOJiWt/YjoevGCedNQcHe63AtzWZeGNI2SQsMxbYV4otG8e1oj0TOhSwS/ZjayMvH/pYEKG4fGbEpu5XMSKh/7K8vwukkwr0uXiBur3xFNgNXuaWo+GrsCgYEAyVqOTSdVC6MDaCFvW6BBnQu+an9YbPEAG02ae+xPk3G31BKmrbztsdrloRx3uaf8mVAM/A9cQy93RpokkQBYlu5K5Q3JCN1RvZnvzT/7EUyZWE9D6Q5W7FuVuKXJOZ6SfEnadhcKuHKf1TNxsEuMqAoihErBVP1hGVDmOkmmsE0CgYBB9ETX/F1toE2ejy9soU9A8rpEQYbQyk8P+dE6y7+rVtlqTUqarIAR9YMpSFy+NYpAGjG6YQ2ubpRII/47b1FIXaIY8HYnzc0BlZvrOU5HWFmL2yzrpO8nLtHc1BSKk3sCqj8b+3URZXuXOQluE0gBYzlSFvk9pXoWhu9uby6NyQKBgDrzEyRNdp2gjRfIaiTGJ0+GJ6pgPBAxApn7v1W2mpmNOoeRKlFFNcXTU1U202p8Xvy3rgWBrb5RwDbgXAJDuqv9ednTjl7VBOBgmA9cQvIOnfp3wmcR9qreKVhU2TPQVAylRps6Jb5YeKjfldJKXBS6Wt6mUDHEuM/DmpPIClAlAoGBAJkbLj9G/Au4JbWZogbF9DIHKGCYHVDsHQI6ppx7tGxb3RU9RynchtOQiveYJdBuRgeNy3lqmkCSCJu9L1nV1uZXaFm7GJtPKRuJFhsZ+r9wqU6LxzwfWN0U/yzHUohpCKWKo/tXI9aKw6HiNdcpzhYf3H5PvODggO286da9c/Bs";
	
	

	private String orgCode = "机构号"; // 机构号

	public void send() {
		String url = "";

		SimpleDateFormat sf = new SimpleDateFormat ("yyyyMMddHHmmss");
		String sendTime = sf.format(new Date());
	    String orderid = sendTime + String.format("%06d", "订单号");
		
		Map<String, String> map = new TreeMap<String, String>();
		map.put("orgId", orgCode);
		map.put("source", "0");
		map.put("settleAmt", "100");
		map.put("account", "");
		map.put("amount", "2000");
		map.put("tranTp", "0");
		map.put("orgOrderNo", orderid);
//		map.put("notifyUrl","http://www.baidu.com");
		
		String result = post(url,sendStr(map),null);

	}
	
	
	/**
	 *   注册接口
	 */
	public void regist(){
		String url = "";
		Map<String,String> map = new TreeMap<String,String>();
		map.put("pmsBankNo", ""); //必填，12位联行号
		map.put("certNo", "");//必填，证件号
		map.put("mobile", ""); // 必填，结算卡绑定的11位手机号码
		map.put("password", "admin"); //必填，商户密码
		map.put("cardNo", ""); //必填，银行卡号
		map.put("orgId", "000005"); //必填，6为平台机构号
		map.put("realName", ""); //必填，结算卡对应的真实姓名
		map.put("account", ""); // 必填，11位手机号
		map.put("mchntName", "测试商户"); //必填，商户名称
		
		String result = post(url,sendStr(map),null);
		
		
	}
	
	
	/**
	 *  固定码结果
	 */
	public void oneqrpay(){
		String url = "";
		
		SimpleDateFormat sf = new SimpleDateFormat ("yyyyMMddHHmmss");
		String sendTime = sf.format(new Date());
	    String orderid = sendTime + String.format("%06d", "订单号");
		
		Map<String,String> map = new TreeMap<String,String>();
		map.put("account", "");
		map.put("source", "0");
		map.put("orgOrderNo", orderid);
		map.put("tranTp", "0");
		map.put("amount", "2000");
		map.put("orgId", orgCode);
		map.put("settleAmt", "100");
		
		String result = post(url,sendStr(map),"1");
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
				try {
					UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
					httppost.setEntity(uefEntity);
				} catch (UnsupportedEncodingException e) {
					System.out.println(e.getMessage());
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
			System.out.println(e.getMessage());
		} catch (IllegalStateException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				response.close();
				httpClient.close();
			} catch (IOException e) {
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
		return Base64.decodeBase64(key.getBytes());
	}

	/**
	 * BASE64加密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] key) throws Exception {
		return new String(Base64.encodeBase64(key));
	}

	public static void main(String[] args) throws Exception {
		TecrwinService tecrwin = new TecrwinService();
//		tecrwin.regist();
		tecrwin.send();
//		tecrwin.oneqrpay();
		
		
//		String data = "amount=2&orgId=000005&source=0&tranTp=0";
//		String sign = tecrwin.sign(data.getBytes(), PRIVATE_KEY);
//		boolean flag = tecrwin.verify(data.getBytes(), PUBLIC_KEY, sign);
//		System.out.println(flag);
		
	}
}
