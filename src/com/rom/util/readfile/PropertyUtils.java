package com.rom.util.readfile;



import java.io.IOException;
import java.util.Properties;

/**
 *   读取配置文件 信息
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-4-23
 * Time: 18:02:11
 * To change this template use File | Settings | File Templates.
 */
public class PropertyUtils {
    private Properties pro = new Properties();
    private static PropertyUtils propertyUtils = new PropertyUtils();

    private PropertyUtils() {
        try {
            pro.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("system.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static PropertyUtils getInstance(){
           return propertyUtils;
    }

    public static  String getValue(String key) {
            return (String)getInstance().pro.get(key);
    }
}
