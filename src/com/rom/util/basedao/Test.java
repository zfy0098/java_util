package com.rom.recommend.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

public class Test {

	
	//  读取外部配置文件 获取文件内容  取代java内常量  方便修改
	public static String geturl(HttpServletRequest request){
		StringBuffer newurl = new StringBuffer();
		// 项目中路径为 src下 
		InputStream in = request.getSession().getServletContext()
				.getResourceAsStream("/WEB-INF/classes/system.properties");
		Properties prop = new Properties();
		try {
			prop.load(in);
			//  download   为文件中的参数名称  newurl  为文件中的download 的值
			newurl.append(prop.getProperty("download").toString());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return newurl.toString();
	}
	
}
