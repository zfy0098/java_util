package com.rom.util.des;

import java.security.InvalidKeyException;  
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Map;



import com.lowagie.text.pdf.codec.Base64;
import com.rom.util.rsa.MyRSA;

/**
 * RSA数字签名，借用MyRSA中的算法，不再重复 数字签名遵循“私钥签名，公钥验签”原则，因为私钥是个人身份认证
 * 
 * @author Kinsley
 * 
 */
public class MySignature {

	/** 数字签名算法。JDK只提供了MD2withRSA, MD5withRSA, SHA1withRSA，其他的算法需要第三方包才能支持 */
	public static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

//	public static final String PLAIN_TEXT = "MANUTD is the greatest club in the world";
	public static final String PLAIN_TEXT = "这个是我要加密的数据";

	public static void main(String[] args) {
		// 建立两套公私钥对
		Map<String, byte[]> keyMap1 = MyRSA.generateKeyBytes();
		PublicKey publicKey1 = MyRSA.restorePublicKey(keyMap1.get(MyRSA.PUBLIC_KEY));
		PrivateKey privateKey1 = MyRSA.restorePrivateKey(keyMap1.get(MyRSA.PRIVATE_KEY));

		Map<String, byte[]> keyMap2 = MyRSA.generateKeyBytes();
		PublicKey publicKey2 = MyRSA.restorePublicKey(keyMap2.get(MyRSA.PUBLIC_KEY));
		PrivateKey privateKey2 = MyRSA.restorePrivateKey(keyMap2.get(MyRSA.PRIVATE_KEY));

		/**
		 * 假设现在A签名后向B发送消息 A用B的公钥进行加密 用自己A的私钥进行签名
		 */
		byte[] encodedText = MyRSA.RSAEncode(publicKey2, PLAIN_TEXT.getBytes());
		
		System.out.println( Base64.encodeBytes(encodedText));
		
		byte[] signature = sign(privateKey1, PLAIN_TEXT.getBytes());

		/**
		 * 现在B收到了A的消息，进行两步操作 用B的私钥解密得到明文 将明文和A的公钥进行验签操作
		 */

		byte[] decodedText = MyRSA.RSADecode(privateKey2, encodedText).getBytes();
		System.out.println("Decoded Text: " + new String(decodedText));

		System.out.println("Signature is " + verify(publicKey1, signature, decodedText));
	}

	/**
	 * 签名，三步走 1. 实例化，传入算法 2. 初始化，传入私钥 3. 签名
	 * 
	 * @param key
	 * @param plainText
	 * @return
	 */
	public static byte[] sign(PrivateKey privateKey, byte[] plainText) {
		try {
			// 实例化
			Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);

			// 初始化，传入私钥
			signature.initSign(privateKey);

			// 更新
			signature.update(plainText);

			// 签名
			return signature.sign();

		} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 验签，三步走 1. 实例化，传入算法 2. 初始化，传入公钥 3. 验签
	 * 
	 * @param publicKey
	 * @param signatureVerify
	 * @param plainText
	 * @return
	 */
	public static boolean verify(PublicKey publicKey, byte[] signatureVerify,
			byte[] plainText) {
		try {
			// 实例化
			Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);

			// 初始化
			signature.initVerify(publicKey);

			// 更新
			signature.update(plainText);

			// 验签
			return signature.verify(signatureVerify);
		} catch (NoSuchAlgorithmException | InvalidKeyException
				| SignatureException e) {
			e.printStackTrace();
		}

		return false;
	}
}