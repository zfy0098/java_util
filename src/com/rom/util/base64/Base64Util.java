package com.rom.util.base64;

import sun.misc.BASE64Decoder; 
import sun.misc.BASE64Encoder;


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
        return (new BASE64Decoder()).decodeBuffer(key);               
    }               
                  
    /**         
     * BASE64加密   
     * @param key          
     * @return          
     * @throws Exception          
     */              
    public static String encryptBASE64(byte[] key) throws Exception {               
        return (new BASE64Encoder()).encodeBuffer(key);               
    }       
}
