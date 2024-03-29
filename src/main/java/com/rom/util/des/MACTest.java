package com.rom.util.des;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import net.sf.json.JSONObject;

public class MACTest {

	public static void main(String[] args) {
		
		// mackey tmkkey termtmkkey
		// 61 7BD33A2187144804049BE30F43B73732 30C4FC8E9F7C35CF9DE2D3075282ED2F
		// A9D10AF2CF1E8EDB225E184D78A124C9

		
		MACTest mac = new MACTest();
		
		JSONObject json = new JSONObject();
		
		json.put("data", "这是一个测试数据");
		
		String rmacStr = mac.mac("这是一个测试数据","7BD33A2187144804049BE30F43B73732");
		
		System.out.println(rmacStr);
	}
	


	public String mac(String macStr, String key) {
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
			System.arraycopy(Utils.string2Bytes(macAsc.substring(16, 32)), 0,macByteAll, 8, 8);

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
