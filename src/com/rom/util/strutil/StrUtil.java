package com.rom.util.strutil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * 字符串工具类
 * 
 * @author zfy
 * 
 */
public class StrUtil {

	/**
	 * 判断字符换是否为空， 如果为空 将返回true
	 * 
	 * @param str
	 *            要判断的字符串
	 * @return
	 */
	public boolean isEmpty(String str) {
		if (str == null || str.trim().isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * 将字符穿的首字母转换成打写
	 * 
	 * @param str
	 *            要转换的字符串
	 * @return
	 */
	public String capitalize(String str) {
		if (isEmpty(str)) {
			return "";
		}
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	/**
	 * 将指定字符串的首字母转换成小写
	 * 
	 * @param str
	 * @return
	 */
	public String Camel(String str) {
		if (isEmpty(str)) {
			return "";
		}
		return str.substring(0, 1).toLowerCase() + str.substring(1);
	}

	public String getMac(String ipAddress) throws SocketException,
			UnknownHostException {
		String str = "";
		String macAddress = "";
		final String LOOPBACK_ADDRESS = "127.0.0.1";
		// 如果为127.0.0.1,则获取本地MAC地址。
		if (LOOPBACK_ADDRESS.equals(ipAddress)) {
			InetAddress inetAddress = InetAddress.getLocalHost();
			// 貌似此方法需要JDK1.6。
			byte[] mac = NetworkInterface.getByInetAddress(inetAddress)
					.getHardwareAddress();
			// 下面代码是把mac地址拼装成String
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				if (i != 0) {
					sb.append("-");
				}
				// mac[i] & 0xFF 是为了把byte转化为正整数
				String s = Integer.toHexString(mac[i] & 0xFF);
				sb.append(s.length() == 1 ? 0 + s : s);
			}
			// 把字符串所有小写字母改为大写成为正规的mac地址并返回
			macAddress = sb.toString().trim().toUpperCase();
			return macAddress;
		} else {
			// 获取非本地IP的MAC地址
			try {
				System.out.println(ipAddress);
				Process p = Runtime.getRuntime()
						.exec("nbtstat -A " + ipAddress);
				System.out.println("===process==" + p);

				InputStreamReader ir = new InputStreamReader(p.getInputStream());

				BufferedReader br = new BufferedReader(ir);

				while ((str = br.readLine()) != null) {
					System.out.println(str);
					str = new String(str.getBytes("gbk"), "utf-8");
					if (str.indexOf("MAC") > 1) {
						macAddress = str.substring(str.indexOf("MAC") + 9,
								str.length());
						macAddress = macAddress.trim();
						System.out.println("macAddress:" + macAddress);
						break;
					}
				}
				p.destroy();
				br.close();
				ir.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			return macAddress;
		}
	}

	public String getMACAddressByIp(String ip) {
		String str = "";
		String macAddress = "";
		try {
			Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);
			InputStreamReader ir = new InputStreamReader(p.getInputStream(),
					"utf-8");
			LineNumberReader input = new LineNumberReader(ir);
			while ((str = input.readLine()) != null) {
				str = input.readLine();
				str = new String(str.getBytes("iso-8859-1"), "utf-8");
				System.out.println(str);
				if (str != null) {
					if (str.indexOf("MAC Address") > 1) {
						macAddress = str.substring(
								str.indexOf("MAC Address") + 14, str.length());
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
		return macAddress;
	}

	public String randomCode() {
		String[] beforeShuffle = new String[] { "0", "1", "2", "3", "4", "5",
				"6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
				"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
				"U", "V", "W", "X", "Y", "Z" };
		List<String> list = Arrays.asList(beforeShuffle);

		// 随机打乱原来的顺序，和洗牌一样。
		Collections.shuffle(list);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i));
		}
		String afterShuffle = sb.toString();
		String result = afterShuffle.substring(5, 9);
		System.out.print(result);
		return result;
	}

	public static String generateValue() {
		return generateValue(UUID.randomUUID().toString());
	}

	public static String generateValue(String param) {
		return UUID.fromString(UUID.nameUUIDFromBytes(param.getBytes()).toString()).toString();
	}

	public static void main(String[] args) throws SocketException,
			UnknownHostException {
		System.out.println(StrUtil.generateValue());
		System.out.println(StrUtil.generateValue("zfy"));
		System.out.println(StrUtil.generateValue("zfy"));
		System.out.println(StrUtil.generateValue("zfy"));
		System.out.println(StrUtil.generateValue("zfy"));

	}

}
