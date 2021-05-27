package com.rom.util.md5;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * 
 *     需要jar包
 *     commons-codec-1.3.jar
 *     commons-io-1.4.jar
 * @author DLHT
 *
 */

public class FileMD5 {

	public static String getMd5ByFile(File file) throws FileNotFoundException {
		String value = null;
		FileInputStream in = new FileInputStream(file);
		try {
			MappedByteBuffer byteBuffer = in.getChannel().map(
					FileChannel.MapMode.READ_ONLY, 0, file.length());
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(byteBuffer);
			BigInteger bi = new BigInteger(1, md5.digest());
			value = bi.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}

	private static char[] hexChar = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static String getHash(String fileName, String hashType)
			throws IOException, NoSuchAlgorithmException {

		File f = new File(fileName);
		System.out
				.println(" -------------------------------------------------------------------------------");
		System.out.println("|当前文件名称:" + f.getName());
		System.out.println("|当前文件大小:" + (f.length() / 1024 / 1024) + "MB");
		System.out.println("|当前文件路径[绝对]:" + f.getAbsolutePath());
		System.out.println("|当前文件路径[---]:" + f.getCanonicalPath());
		System.out
				.println(" -------------------------------------------------------------------------------");

		InputStream ins = new FileInputStream(f);

		byte[] buffer = new byte[8192];
		MessageDigest md5 = MessageDigest.getInstance(hashType);

		int len;
		while ((len = ins.read(buffer)) != -1) {
			md5.update(buffer, 0, len);
		}

		ins.close();
		// 也可以用apache自带的计算MD5方法
		return DigestUtils.md5Hex(md5.digest());
		// 自己写的转计算MD5方法
		// return toHexString(md5.digest());
	}

	public static String getHash2(String fileName) {
		File f = new File(fileName);
		return String.valueOf(f.lastModified());
	}

	protected static String toHexString(byte[] b) {
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(hexChar[(b[i] & 0xf0) >>> 4]);
			sb.append(hexChar[b[i] & 0x0f]);
		}
		return sb.toString();
	}

	/*
	 * 获取MessageDigest支持几种加密算法
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static String[] getCryptolmpls(String serviceType) {

		Set result = new HashSet();
		// all providers
		Provider[] providers = Security.getProviders();
		for (int i = 0; i < providers.length; i++) {
			// get services provided by each provider
			Set keys = providers[i].keySet();
			for (Iterator it = keys.iterator(); it.hasNext();) {
				String key = it.next().toString();
				key = key.split(" ")[0];

				if (key.startsWith(serviceType + ".")) {
					result.add(key.substring(serviceType.length() + 1));
				} else if (key.startsWith("Alg.Alias." + serviceType + ".")) {
					result.add(key.substring(serviceType.length() + 11));
				}
			}
		}
		return (String[]) result.toArray(new String[result.size()]);
	}

	public static void main(String[] args) throws IOException {

		String path = "F:\\lockscreen1212.rar";

		String v = getMd5ByFile(new File(path));
		System.out.println("MD5:" + v.toUpperCase());

		FileInputStream fis = new FileInputStream(path);
		String md5 = DigestUtils.md5Hex(IOUtils.toByteArray(fis));
		IOUtils.closeQuietly(fis);
		System.out.println("MD5:" + md5);

		System.out.println("MD5:" + DigestUtils.md5Hex("WANGQIUYUN"));

		// 调用方法
		String[] names = getCryptolmpls("MessageDigest");
		for (String name : names) {
			System.out.println(name);
		}
		long start = System.currentTimeMillis();
		System.out.println("开始计算文件MD5值,请稍后...");
		String fileName = "E:\\Office_2010_Toolkit_2.2.3_XiaZaiBa.zip";
		// // String fileName = "E:\\SoTowerStudio-3.1.0.exe";
		String hashType = "MD5";
		String hash;
		try {
			hash = getHash(fileName, hashType);
			System.out.println("MD5:" + hash);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println("一共耗时:" + (end - start) + "毫秒");
	}

}