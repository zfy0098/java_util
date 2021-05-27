package com.rom.util.base64;

import org.apache.commons.codec.binary.Base64;



/**
 *    base64  加密 解密
 * @author zfy
 * @version 1.0
 *
 */

public class Base64Util {

	 /**    
     * BASE64解密   
     * @param key          
     * @return          
     * @throws Exception          
     */              
    public static byte[] decryptBASE64(String key) throws Exception {   
    	
        return Base64.decodeBase64(key.getBytes());     
    }               
                  
    /**         
     * BASE64加密   
     * @param key          
     * @return          
     * @throws Exception          
     */              
    public static String encryptBASE64(byte[] key) throws Exception {   
    	return new String(Base64.encodeBase64(key));
    }       
}
