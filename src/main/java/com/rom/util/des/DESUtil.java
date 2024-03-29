package com.rom.util.des;

import java.io.UnsupportedEncodingException;  
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;



public class DESUtil {
	
	private static final String Algorithm = "DESede";

	/**
	 * 5.1 MAC加密算法
			加密方式
				采用双倍长密钥对数据进行加密
			加密算法
			将报文中所有请求数据按照字段名的 ascii 码从小到大排序构成MAC ELEMEMENT BLOCK （MAB）。
			b)  对MAB，按每8个字节做异或（不管信息中的字符格式），如果最后不满8个字节，则添加“0X00”。
			示例	：
			MAB = M1 M2 M3 M4
			其中：	
			M1 = MS11 MS12 MS13 MS14 MS15 MS16 MS17 MS18
			M2 = MS21 MS22 MS23 MS24 MS25 MS26 MS27 MS28
			M3 = MS31 MS32 MS33 MS34 MS35 MS36 MS37 MS38
			M4 = MS41 MS42 MS43 MS44 MS45 MS46 MS47 MS48
			
			按如下规则进行异或运算：
						MS11 MS12 MS13 MS14 MS15 MS16 MS17 MS18
			XOR）			MS21 MS22 MS23 MS24 MS25 MS26 MS27 MS28
			---------------------------------------------------
			TEMP BLOCK1 =	TM11 TM12 TM13 TM14 TM15 TM16 TM17 TM18
			
			然后，进行下一步的运算：
			TM11 TM12 TM13 TM14 TM15 TM16 TM17 TM18
			XOR）			MS31 MS32 MS33 MS34 MS35 MS36 MS37 MS38
			---------------------------------------------------
			TEMP BLOCK2 =	TM21 TM22 TM23 TM24 TM25 TM26 TM27 TM28
			
			再进行下一步的运算：
			TM21 TM22 TM23 TM24 TM25 TM26 TM27 TM28
			XOR）			MS41 MS42 MS43 MS44 MS45 MS46 MS47 MS48
			---------------------------------------------------
			RESULT BLOCK =	TM31 TM32 TM33 TM34 TM35 TM36 TM37 TM38
					
			c)  将异或运算后的最后8个字节（RESULT BLOCK）转换成16 个HEXDECIMAL：
			RESULT BLOCK = TM31 TM32 TM33 TM34 TM35 TM36 TM37 TM38
				         = TM311 TM312 TM321 TM322 TM331 TM332 TM341 TM342 ||
					       TM351 TM352 TM361 TM362 TM371 TM372 TM381 TM382
			
			d)  取前8 个字节用MAK加密：
			ENC BLOCK1 = eMAK（TM311 TM312 TM321 TM322 TM331 TM332 TM341 TM342）
						= EN(1)1 EN(1)2 EN(1)3 EN(14) EN(1)5 EN(1)6 EN(1)7 EN(1)8
			
			e)  将加密后的结果与后8 个字节异或：
			EN(1)1  EN(1)2  EN(1)3  EN(14)  EN(1)5  EN(1)6  EN(1)7  EN(1)8
			XOR）	TM351 TM352 TM361 TM362 TM371 TM372 TM381 TM382
			------------------------------------------------------------
			TEMP BLOCK=	TE11  TE12  TE13  TE14  TE15  TE16  TE17  TE18
			
			f)  用异或的结果TEMP BLOCK 再进行一次单倍长密钥算法运算。
			ENC BLOCK2 = eMAK（TE11 TE12 TE13 TE14 TE15 TE16 TE17 TE18）
					= EN(2)1 EN(2)2 EN(2)3 EN(2)4 EN(2)5 EN(2)6 EN(2)7 EN(2)8
			
			g)  将运算后的结果（ENC BLOCK2）转换成16 个HEXDECIMAL：
			ENC BLOCK2 = EN(2)1 EN(2)2 EN(2)3 EN(2)4 EN(2)5 EN(2)6 EN(2)7 EN(2)8
			= EM211 EM212 EM221 EM222 EM231 EM232 EM241 EM242 ||
						 EM251 EM252 EM261 EM262 EM271 EM272 EM281 EM282
			示例	：
			ENC RESULT= %H84, %H56, %HB1, %HCD, %H5A, %H3F, %H84, %H84
			转换成16 个HEXDECIMAL:
			“8456B1CD5A3F8484”
			h)  取前8个字节作为MAC值。
				取”8456B1CD”为MAC值。

	 * @param data
	 * @param keyStr
	 * @return
	 * @throws Exception
	 */

    public static byte[] encrypt3(String data, String keyStr) throws Exception {
    	byte[] keyByte = ASCII_To_BCD(keyStr.getBytes());
        byte input[] = ASCII_To_BCD(data.getBytes());
        byte keyBytes[] = new byte[24];
        System.arraycopy(keyByte, 0, keyBytes, 0, 16);
        System.arraycopy(keyByte, 0, keyBytes, 16, 8);
        SecretKeySpec key = new SecretKeySpec(keyBytes, "DESede");
        Cipher cipher = Cipher.getInstance("DESede/ECB/NOPADDING");
//      Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(1, key);
        byte cipherText[] = new byte[cipher.getOutputSize(input.length)];
        int ctLength = cipher.update(input, 0, input.length, cipherText, 0);
        ctLength += cipher.doFinal(cipherText, ctLength);
        return cipherText;
    }

	public static byte[] decrypt3(String data, String keyStr) throws Exception {
		byte[] keyByte = ASCII_To_BCD(keyStr.getBytes());
		byte input[] = ASCII_To_BCD(data.getBytes());
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

	public static byte[] encrypt(String data, String keyStr) throws Exception {
		byte input[] = ASCII_To_BCD(data.getBytes());
		byte keyBytes[] = new byte[8];
		byte[] key8 = ASCII_To_BCD(keyStr.getBytes());
		System.arraycopy(key8, 0, keyBytes, 0, 8);
		SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
		Cipher cipher = Cipher.getInstance("DES/ECB/NOPADDING");
		cipher.init(1, key);
		byte cipherText[] = new byte[cipher.getOutputSize(input.length)];
		int ctLength = cipher.update(input, 0, input.length, cipherText, 0);
		ctLength += cipher.doFinal(cipherText, ctLength);
		return cipherText;
	}

	public static byte[] decrypt(String data, String keyStr) throws Exception {
		byte input[] = ASCII_To_BCD(data.getBytes());
		byte keyBytes[] = new byte[8];
		byte[] key8 = ASCII_To_BCD(keyStr.getBytes());
		System.arraycopy(key8, 0, keyBytes, 0, 8);
		SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
		Cipher cipher = Cipher.getInstance("DES/ECB/NOPADDING");
		cipher.init(2, key);
		byte cipherText[] = new byte[cipher.getOutputSize(input.length)];
		int ctLength = cipher.update(input, 0, input.length, cipherText, 0);
		ctLength += cipher.doFinal(cipherText, ctLength);
		return cipherText;
	}
	/**
	 * 加密
	 * 
	 * @param src
	 * @return
	 */
	public static byte[] encryptMode(byte[] src, String key, String encoding) {
		try {
			SecretKey deskey = new SecretKeySpec(build3DesKey(key, encoding), Algorithm);
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密
	 * 
	 * @param src
	 * @return
	 */
	public static byte[] decryptMode(byte[] src, String key, String encoding) {
		try {
			SecretKey deskey = new SecretKeySpec(build3DesKey(key, encoding), Algorithm);
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	public static String mac(String macStr, String key, String keyIndex) {
		try {
			String macByte = GenXorData(macStr.getBytes("GBK"), 0);
			System.out.println("macByte" + macByte); 
			String macAsc = bcd2Str(macByte.getBytes());
			
			// 加密
			String keyde = deskey(key, keyIndex);
			
			byte[] leftByte = DESUtil.encrypt3(macAsc.substring(0, 16), keyde);
			byte[] macByteAll = new byte[16];
			System.arraycopy(leftByte, 0, macByteAll, 0, 8);
			System.arraycopy(string2Bytes(macAsc.substring(16, 32)), 0, macByteAll, 8, 8);

			String temp = GenXorData(macByteAll, 0);
			byte[] mac = DESUtil.encrypt3(temp, keyde);
			return bcd2Str(bcd2Str(mac).getBytes()).substring(0, 8);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 *   解密你要
	 * @param key
	 * @param keyIndex
	 * @return
	 * @throws Exception
	 */
	public static String deskey(String key , String keyIndex) throws Exception{ 
//		String initKey = LoadPro.loadProperties("jmj", keyIndex);
		String initKey = "22222222222222222222222222222222";
		// 解析密钥明文
		String keyde = bcd2Str(DESUtil.decrypt3(key, initKey));
		System.out.println("加密秘钥：" + keyde);
		return keyde;
	}
	
	

	public static byte[] ASCII_To_BCD(byte[] ascii) {
		int asc_len = ascii.length;
		byte[] bcd = new byte[asc_len / 2];
		int j = 0;
		for (int i = 0; i < (asc_len + 1) / 2; i++) {
			bcd[i] = asc_to_bcd(ascii[j++]);
			bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc_to_bcd(ascii[j++])) + (bcd[i] << 4));
		}
		return bcd;
	}

	public static byte asc_to_bcd(byte asc) {
		byte bcd;

		if ((asc >= '0') && (asc <= '9'))
			bcd = (byte) (asc - '0');
		else if ((asc >= 'A') && (asc <= 'F'))
			bcd = (byte) (asc - 'A' + 10);
		else if ((asc >= 'a') && (asc <= 'f'))
			bcd = (byte) (asc - 'a' + 10);
		else
			bcd = (byte) (asc - 48);
		return bcd;
	}
	
	
	public static String bcd2Str(byte[] bytes) {
		char temp[] = new char[bytes.length * 2], val;

		for (int i = 0; i < bytes.length; i++) {
			val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
			temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');

			val = (char) (bytes[i] & 0x0f);
			temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
		}
		return new String(temp);
	}

	public static String GenXorData(byte[] bBuf, int iStart) {

		int i = 0;
		int nLen = 0;
		int nDataLen = 0;
		int nXorDataLen = 0;
		byte[] s1 = new byte[8];
		byte[] s2 = new byte[8];
		byte[] buf = bBuf;
		nDataLen = buf.length;
		nLen = 8 - (nDataLen % 8);
		nLen = (nLen == 8) ? 0 : nLen;
		nXorDataLen = (nDataLen + nLen); // 不足8的倍数，用0x00补齐。
		byte[] pBuf = new byte[nXorDataLen];

		System.arraycopy(buf, 0, pBuf, 0, nDataLen);
		System.arraycopy(pBuf, 0, s1, 0, 8);
		for (i = 8; i < nXorDataLen; i += 8) {
			System.arraycopy(pBuf, i, s2, 0, 8);
			s1 = setxor(s1, s2);
		}
		return bcd2Str(s1);
	}

	public static byte[] setxor(byte[] b1, byte[] b2) {

		byte[] snbyte = new byte[b1.length];
		for (int i = 0, j = 0; i < b1.length; i++, j++) {
			snbyte[i] = (byte) (b1[i] ^ b2[j]);
		}
		return snbyte;
	}

	public static byte[] string2Bytes(String str) {
		byte[] b = new byte[str.length() / 2];
		for (int i = 0; i < b.length; i++) {
			b[i] = (byte) Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
		}
		return b;
	}
	
	public static String bytes2HexStr(byte[] ba,boolean isInsSpace) {
		StringBuffer md = new StringBuffer(32);
		for (int i = 0; i < ba.length; i++) {
			int j = ba[i];
			if (j < 0)
				j += 256;
			md.append(hex(j / 16));
			md.append(hex(j % 16));
			if(isInsSpace)
				md.append(" ");
		}
		return md.toString();
	}

	public static String rightPad(String value,int len,String padStr){
		String temp="";
		for(int i=value.length();i<len;i++){
			temp+=padStr;
		}
		return value+temp;
	}
	
	public static char hex(int i) {
		switch (i) {
		case 10:
			return 'A';
		case 11:
			return 'B';
		case 12:
			return 'C';
		case 13:
			return 'D';
		case 14:
			return 'E';
		case 15:
			return 'F';
		default:
			return (char) (i + 48);
		}
	}
	
	
	/**
	 * 构建3DES秘钥
	 * 
	 * @param keyStr
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] build3DesKey(String keyStr, String encoding) throws UnsupportedEncodingException {
		byte[] key = new byte[24];
		byte[] temp = keyStr.getBytes(encoding);

		if (key.length > temp.length) {
			System.arraycopy(temp, 0, key, 0, temp.length);
		} else {
			System.arraycopy(temp, 0, key, 0, key.length);
		}
		return key;
	}
	
	
	
	
	
	
	/**
	 * Base64编码
	 * @param key
	 * @param data
	 * @return
	 */
	public static String encode(String key, String data) throws Exception {
		byte[] keyByte = key.getBytes("utf-8");
		byte[] dataByte = data.getBytes("utf-8");
		byte[] valueByte = des3Encryption(keyByte, dataByte);
		String value = new String(Base64.getEncoder().encode(valueByte), "utf-8");
		return value;
	}
	
	
	public static String decode(String key, String value) throws Exception{
		byte[] keyByte = key.getBytes("utf-8");
		byte[] valueByte = Base64.getDecoder().decode(value.getBytes("utf-8"));
		byte[] dataByte = des3Decryption(keyByte,valueByte); 
		String data = new String(dataByte, "utf-8");
		return data;
	}
	
	
	/**
	 * des3Encryption加密
	 * @param key
	 * @param data
	 * @return
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws IllegalStateException
	 */
	public static byte[] des3Encryption(byte[] key, byte[] data)
			throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
			IllegalBlockSizeException, IllegalStateException {
		final String Algorithm = "DESede";

		SecretKey deskey = new SecretKeySpec(key, Algorithm);

		Cipher c1 = Cipher.getInstance(Algorithm);
		c1.init(Cipher.ENCRYPT_MODE, deskey);
		return c1.doFinal(data);
	}

	public static byte[] des3Decryption(byte[] key, byte[] data)
			throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
			IllegalBlockSizeException, IllegalStateException {
		final String Algorithm = "DESede";

		SecretKey deskey = new SecretKeySpec(key, Algorithm);

		Cipher c1 = Cipher.getInstance(Algorithm);
		c1.init(Cipher.DECRYPT_MODE, deskey);
		return c1.doFinal(data);
	}
	
	
	
	public static void main(String[] args) throws Exception {
		String keyde = DESUtil.encode("740D4D0A0C6C5FE72DB9AB4E8A882E86" , "6226890118244838");
		System.out.println(keyde); 
	}
}
