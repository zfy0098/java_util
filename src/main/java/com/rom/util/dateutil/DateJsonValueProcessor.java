package com.rom.util.dateutil;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;


/**
 * 
 * 	数据库中的java.sql.Timestamp转换成Date
 * 
 * 查询数据库中的时间类型为 java.sql.Timestamp
 * 
 * 保存在json中需要格式化
 * 
 * 自定义工具类 DateJsonValueProcessor
 * 
 * 
 * @author a
 *
 */

public class DateJsonValueProcessor implements JsonValueProcessor {
	
	/**
	 * java中的instanceof 运算符是用来在运行时指出对象是否是特定类的一个实例。instanceof通过返回一个布尔值来指出， 这个对象是否是这个特定类或者是它的子类的一个实例。
	 * 用法： 
	 * 		result = object instanceof class 
	 * 参数：
	 * 		Result：布尔类型。 
	 * 		Object：必选项。任意对象表达式。
	 * 		Class：必选项。任意已定义的对象类。 
	 * 说明： 
	 * 		如果 object 是class 的一个实例，则 instanceof 运算符返回 true。如果 object 不是指定类的一个实例，或者 object 是null，则返回 false。
	 * 
	 */
	
	
	/**
	 * 
	 * 在保存json数据的同事调用 JsonConfig 
	 * 1 JsonConfig jsonConfig = new JsonConfig(); 
	 * 2 jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new  DateJsonValueProcessor("yyyy-MM-dd")); 
	 * 3 JSONArray jo = JSONArray.fromObject(list,jsonConfig);
	 * 
	 */
	
	private String datePattern = "yyyy-MM-dd";

	public DateJsonValueProcessor() {
		super();
	}

	public DateJsonValueProcessor(String format) {
		super();
		this.datePattern = format;
	}

	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		return process(value);
	}

	public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
		return process(value);
	}

	private Object process(Object value) {
		try {
			if (value instanceof Date) {
				SimpleDateFormat sdf = new SimpleDateFormat(datePattern, Locale.UK);
				return sdf.format((Date) value);
			}
			return "";
		} catch (Exception e) {
			return "";
		}

	}

	public String getDatePattern() {
		return datePattern;
	}

	public void setDatePattern(String pDatePattern) {
		datePattern = pDatePattern;
	}
	

}
