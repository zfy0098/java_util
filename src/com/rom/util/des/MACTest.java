package com.mac;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class MACTest {

	public static void main(String[] args) {
		MACTest mac = new MACTest();
		
		JSONObject json = new JSONObject();
		
		json.put("data", "这是一个测试数据");
		
		String rmacStr = mac.makeMac(json.toString());
		
		System.out.println(rmacStr);
	}
	
	
	public String makeMac(String json) {
		Map<String, Object> contentData = parseJSON2Map(json);
		String macStr = "";
		Object[] key_arr = contentData.keySet().toArray();
		Arrays.sort(key_arr);
		for (Object key : key_arr) {
			Object value = contentData.get(key);
			if (value != null) {
				if (!key.equals("mac")) {
					macStr += value.toString();
				}
			}
		}
		
		System.out.println(macStr);
		
		// mackey tmkkey termtmkkey
		// 61 7BD33A2187144804049BE30F43B73732 30C4FC8E9F7C35CF9DE2D3075282ED2F
		// A9D10AF2CF1E8EDB225E184D78A124C9

		String rMac = mac(macStr, "7BD33A2187144804049BE30F43B73732");
		return rMac;
	}

	public static Map<String, Object> parseJSON2Map(String jsonStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 最外层解析
		JSONObject json = JSONObject.fromObject(jsonStr);
		for (Object k : json.keySet()) {
			Object v = json.get(k);
			// 如果内层还是数组的话，继续解析
			if (v instanceof JSONArray) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				Iterator<JSONObject> it = ((JSONArray) v).iterator();
				while (it.hasNext()) {
					JSONObject json2 = it.next();
					list.add(parseJSON2Map(json2.toString()));
				}
				map.put(k.toString(), list);
			} else {
				map.put(k.toString(), v);
			}
		}
		return map;
	}

	public String mac(String macStr, String key) {

		System.out.println("macStr" + macStr);
		String initKey = "2";
		return mac(macStr, key, initKey);
	}

	public String mac(String macStr, String key, String keyIndex) {
		String macByte = "";
		try {
			macByte = Utils.GenXorData(macStr.getBytes("GBK"), 0);
		} catch (UnsupportedEncodingException e1) {

			e1.printStackTrace();
		}
		String macAsc = Utils.bcd2Str(macByte.getBytes());
		// 加密
		try {

			String initKey = "22222222222222222222222222222222";
			// 解析密钥明文
			String keyde = Utils.bcd2Str(decrypt3(key, initKey));
			byte[] leftByte = encrypt3(macAsc.substring(0, 16), keyde);
			byte[] macByteAll = new byte[16];
			System.arraycopy(leftByte, 0, macByteAll, 0, 8);
			System.arraycopy(Utils.string2Bytes(macAsc.substring(16, 32)), 0,
					macByteAll, 8, 8);

			String temp = Utils.GenXorData(macByteAll, 0);
			byte[] mac = encrypt3(temp, keyde);
			return Utils.bcd2Str(Utils.bcd2Str(mac).getBytes()).substring(0, 8);
		} catch (Exception e) {

			e.printStackTrace();
			return "";
		}
	}

	public static byte[] encrypt3(String data, String keyStr) throws Exception {
		byte[] keyByte = Utils.ASCII_To_BCD(keyStr.getBytes());
		byte input[] = Utils.ASCII_To_BCD(data.getBytes());
		byte keyBytes[] = new byte[24];
		System.arraycopy(keyByte, 0, keyBytes, 0, 16);
		System.arraycopy(keyByte, 0, keyBytes, 16, 8);
		SecretKeySpec key = new SecretKeySpec(keyBytes, "DESede");
		Cipher cipher = Cipher.getInstance("DESede/ECB/NOPADDING");
		cipher.init(1, key);
		byte cipherText[] = new byte[cipher.getOutputSize(input.length)];
		int ctLength = cipher.update(input, 0, input.length, cipherText, 0);
		ctLength += cipher.doFinal(cipherText, ctLength);
		return cipherText;
	}

	public static byte[] decrypt3(String data, String keyStr) throws Exception {
		byte[] keyByte = Utils.ASCII_To_BCD(keyStr.getBytes());
		byte input[] = Utils.ASCII_To_BCD(data.getBytes());
		byte keyBytes[] = new byte[24];
		System.arraycopy(keyByte, 0, keyBytes, 0, 16);
		System.arraycopy(keyByte, 0, keyBytes, 16, 8);
		SecretKeySpec key = new SecretKeySpec(keyBytes, "DESede");
		Cipher cipher = Cipher.getInstance("DESede/ECB/NOPADDING");
		cipher.init(2, key);
		byte cipherText[] = new byte[cipher.getOutputSize(input.length)];
		int ctLength = cipher.update(input, 0, input.length, cipherText, 0);
		ctLength += cipher.doFinal(cipherText, ctLength);
		return cipherText;
	}
}
