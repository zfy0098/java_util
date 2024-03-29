package com.rom.util.ehcache;
import java.net.URL;   

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;


/**
 *   需要jar包
 *   ehcache-2.10.0.jar
 *   slf4j-api-1.7.7.jar
 *   slf4j-jdk14-1.7.7.jar
 *   
 *   ehcache.xml
 * @author DLHT
 * 2015年10月26日下午3:29:56
 * EhcacheUtil.java
 * DLHT
 */

public class EhcacheUtil {
	private static final String path = "/ehcache.xml";  
    private URL url;  
  
    private CacheManager manager;  
  
    private static EhcacheUtil ehCache;  
  
    private EhcacheUtil(String path) {  
        url = getClass().getResource(path);  
        manager = CacheManager.create(url);  
    }  
  
    public static EhcacheUtil getInstance() {  
        if (ehCache== null) {  
            ehCache= new EhcacheUtil(path);  
        }  
        return ehCache;  
    }  
  
    public void put(String cacheName, String key, Object value) {  
        Cache cache = manager.getCache(cacheName);  
        Element element = new Element(key, value);  
        cache.put(element);  
    }  
  
    public Object get(String cacheName, String key) {  
        Cache cache = manager.getCache(cacheName);  
        Element element = cache.get(key);  
        return element == null ? null : element.getObjectValue();  
    }  
  
    public Cache get(String cacheName) {  
        return manager.getCache(cacheName);  
    }  
  
    public void remove(String cacheName, String key) {  
        Cache cache = manager.getCache(cacheName);  
        cache.remove(key);  
    }  
    public void shutdown(){
    	manager.shutdown();
    }
    public int cachesize(String cacheName){
    	return manager.getCache(cacheName).getSize();
    	
    }
}
