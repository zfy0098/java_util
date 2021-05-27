package com.rom.util.image;


import java.awt.image.BufferedImage; 
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream; 
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;




/**
 *     base64编码和图片互转
 * @author zfy
 *
 */
public class Image64Bit {
	
	public static void main(String[] args) {
		// 测试从Base64编码转换为图片文件

		String strImg = "这里放64位编码";
		GenerateImage(strImg, "D:/wangyc.jpg");

		// 测试从图片文件转换为Base64编码
		System.out.println(GetImageStr("F:/lufei.jpg"));

	}

	public static String GetImageStr(String imgFilePath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		byte[] data = null;

		// 读取图片字节数组
		try {
			InputStream in = new FileInputStream(imgFilePath);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 对字节数组Base64编码
//		BASE64Encoder encoder = new BASE64Encoder();
//		encoder.encode(data);// 返回Base64编码过的字节数组字符串
//		return new String(Base64.encode(data));
		return new String(Base64.encodeBase64(data));
	}

	public static boolean GenerateImage(String imgStr, String imgFilePath) {// 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) // 图像数据为空
			return false;
		try {
			// Base64解码
			byte[] bytes = Base64.decodeBase64(imgStr.getBytes());
 			for (int i = 0; i < bytes.length; ++i) {
				if (bytes[i] < 0) {// 调整异常数据
					bytes[i] += 256;
				}
			}
			// 生成jpeg图片
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(bytes);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	/**
	 * // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	 * @param imageUrl
	 * @return
	 */
	public static String encodeImgageToBase64(URL imageUrl) {
		ByteArrayOutputStream outputStream = null;
		try {
			BufferedImage bufferedImage = ImageIO.read(imageUrl);
			outputStream = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "jpg", outputStream);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		// BASE64Encoder encoder = new BASE64Encoder();
		// encoder.encode(outputStream.toByteArray());// 返回Base64编码过的字节数组字符串
		return new String(Base64.encodeBase64(outputStream.toByteArray())); 
	}
	
}

