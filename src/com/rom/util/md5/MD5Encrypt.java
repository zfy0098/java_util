package com.rom.util.md5;


/**
 *    16位MD5加密 
 *    16位MD5加密
 */

import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException;

public class MD5Encrypt {

	private final static char[] hexDigits = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	private static String bytesToHex(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		int t;
		for (int i = 0; i < 16; i++) {
			t = bytes[i];
			if (t < 0){
				t += 256;
			}
			sb.append(hexDigits[(t >>> 4)]);
			sb.append(hexDigits[(t % 16)]);
		}
		return sb.toString();
	}

	private static String code(String input, int bit) throws Exception {
		try {
			MessageDigest md = MessageDigest.getInstance(System.getProperty(
					"MD5.algorithm", "MD5"));
			if (bit == 16){
				return bytesToHex(md.digest(input.getBytes("utf-8")))
						.substring(8, 24);
			}
			return bytesToHex(md.digest(input.getBytes("utf-8")));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new Exception("Could not found MD5 algorithm.", e);
		}
	}

	public static String md516(String input) throws Exception {
		return code(input, 16);
	}
	
	public static String md532(String input) throws Exception{
		return code(input, 32);
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(MD5Encrypt.md532("ZFY"));
	}
}

