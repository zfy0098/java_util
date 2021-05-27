package com.rom.util.httpclient;

import java.io.File;  
import java.io.FileInputStream; 
import java.io.FileOutputStream;
import java.io.IOException; 
import java.io.InputStream;
import java.io.UnsupportedEncodingException; 
import java.security.KeyManagementException; 
import java.security.KeyStore; 
import java.security.KeyStoreException; 
import java.security.NoSuchAlgorithmException; 
import java.security.UnrecoverableKeyException; 
import java.security.cert.CertificateException; 
import java.text.SimpleDateFormat;
import java.util.ArrayList; 
import java.util.Date;
import java.util.List; 
import java.util.Scanner;
import org.apache.http.Header;
import org.apache.http.HttpEntity; 
import org.apache.http.HttpResponse; 
import org.apache.http.NameValuePair; 
import org.apache.http.ParseException; 
import org.apache.http.client.ClientProtocolException; 
import org.apache.http.client.HttpClient; 
import org.apache.http.client.entity.UrlEncodedFormEntity; 
import org.apache.http.client.methods.HttpGet; 
import org.apache.http.client.methods.HttpPost; 
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.conn.scheme.Scheme; 
import org.apache.http.conn.ssl.SSLSocketFactory; 
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient; 
import org.apache.http.message.BasicNameValuePair; 
import org.apache.http.util.EntityUtils; 
   

/**
 *      httpclient 工具类
 * @author zfy
 *
 *		需要jar包
 *			commons-logging-1.1.1.jar
 *			httpclient-4.2.3.jar
 *			httpcore-4.2.5.jar
 */

public class HttpClientTest { 
   
	public static void main(String[] args) {
		HttpClientTest test = new HttpClientTest();
		test.postForm();
	}
    /**
     * HttpClient连接SSL
     */ 
    public void ssl() { 
        DefaultHttpClient httpclient = new DefaultHttpClient(); 
        try { 
            KeyStore trustStore = KeyStore.getInstance(KeyStore 
                    .getDefaultType()); 
            FileInputStream instream = new FileInputStream(new File("d:\\tomcat.keystore")); 
            try { 
                // 加载keyStore d:\\tomcat.keystore 
                trustStore.load(instream, "123456".toCharArray()); 
            } catch (CertificateException e) { 
                e.printStackTrace(); 
            } finally { 
                try { 
                    instream.close(); 
                } catch (Exception ignore) { 
                } 
            } 
            // 穿件Socket工厂,将trustStore注入 
            SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore); 
            // 创建Scheme 
            Scheme sch = new Scheme("https", 8443, socketFactory); 
            // 注册Scheme 
            httpclient.getConnectionManager().getSchemeRegistry().register(sch); 
            // 创建http请求(get方式) 
            HttpGet httpget = new HttpGet( 
                    "https://localhost:8443/myDemo/Ajax/serivceJ.action"); 
            System.out.println("executing request" + httpget.getRequestLine()); 
            HttpResponse response = httpclient.execute(httpget); 
            HttpEntity entity = response.getEntity(); 
            System.out.println("----------------------------------------"); 
            System.out.println(response.getStatusLine()); 
            if (entity != null) { 
                System.out.println("Response content length: " + entity.getContentLength()); 
                String ss = EntityUtils.toString(entity); 
                System.out.println(ss); 
                EntityUtils.consume(entity); 
            } 
        } catch (ParseException e) { 
            e.printStackTrace(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } catch (KeyManagementException e) { 
            e.printStackTrace(); 
        } catch (UnrecoverableKeyException e) { 
            e.printStackTrace(); 
        } catch (NoSuchAlgorithmException e) { 
            e.printStackTrace(); 
        } catch (KeyStoreException e) { 
            e.printStackTrace(); 
        } finally { 
            httpclient.getConnectionManager().shutdown(); 
        } 
    } 
   
    /**
     * post方式提交表单（模拟用户登录请求）
     */ 
    public void postForm() { 
        // 创建默认的httpClient实例. 
        HttpClient httpclient = new DefaultHttpClient(); 
        // 创建httppost 
        HttpPost httppost = new HttpPost("http://localhost:8080/test/test.html");
        // 创建参数队列 
        List<NameValuePair> formparams = new ArrayList<NameValuePair>(); 
        formparams.add(new BasicNameValuePair("username", "admin")); 
        formparams.add(new BasicNameValuePair("password", "123456")); 
        UrlEncodedFormEntity uefEntity; 
        try { 
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8"); 
            httppost.setEntity(uefEntity); 
            System.out.println("executing request " + httppost.getURI()); 
            HttpResponse response; 
            response = httpclient.execute(httppost); 
            HttpEntity entity = response.getEntity(); 
            if (entity != null) { 
                System.out.println("--------------------------------------"); 
                System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8")); 
                System.out.println("--------------------------------------"); 
            } 
        } catch (ClientProtocolException e) { 
            e.printStackTrace(); 
        } catch (UnsupportedEncodingException e1) { 
            e1.printStackTrace(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } finally { 
            // 关闭连接,释放资源 
            httpclient.getConnectionManager().shutdown(); 
        } 
    } 
   
    /**
     * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
     */ 
    public void post() { 
        // 创建默认的httpClient实例. 
        HttpClient httpclient = new DefaultHttpClient(); 
        // 创建httppost 
        HttpPost httppost = new HttpPost("http://localhost:9090/Test/IosTest.do"); 
        // 创建参数队列 
        List<NameValuePair> formparams = new ArrayList<NameValuePair>(); 
        formparams.add(new BasicNameValuePair("params", "这事一个测试数据")); 
        UrlEncodedFormEntity uefEntity; 
        try { 
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8"); 
            httppost.setEntity(uefEntity); 
//            System.out.println("executing request " + httppost.getURI()); 
            HttpResponse response; 
            response = httpclient.execute(httppost); 
            HttpEntity entity = response.getEntity(); 
            if (entity != null) { 
                System.out.println("--------------------------------------"); 
                System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8")); 
                System.out.println("--------------------------------------"); 
            } 
        } catch (ClientProtocolException e) { 
            e.printStackTrace(); 
        } catch (UnsupportedEncodingException e1) { 
            e1.printStackTrace(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } finally { 
            // 关闭连接,释放资源 
            httpclient.getConnectionManager().shutdown(); 
        } 
    } 
   
    /**
     * 发送 get请求
     */ 
    public void get() { 
    	DefaultHttpClient httpclient = new DefaultHttpClient(); 
   
        try { 
            // 创建httpget. 
            HttpGet method = new HttpGet("https://appleid.apple.com/account"); 
            method.setHeader("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
    		method.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:36.0) Gecko/20100101 Firefox/36.0");
    		method.setHeader("Host", "appleid.apple.com");
    		method.setHeader("Connection", "Keep-Alive");
    		method.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
    		method.getParams().setParameter("http.protocol.cookie-policy",CookiePolicy.BROWSER_COMPATIBILITY);
    		// 执行get请求. 
            HttpResponse response = httpclient.execute(method); 
            //snct
            Header scnt=response.getLastHeader("scnt");
            List<Cookie> cookies = ((AbstractHttpClient) httpclient).getCookieStore().getCookies();    
            if (cookies.isEmpty()) {    
                System.out.println("None");    
            } else {    
                for (int i = 0; i < cookies.size(); i++) {  
                    System.out.println("- " + cookies.get(i).toString());  
                }    
            }  
            // 获取响应实体 
            HttpEntity entity = response.getEntity(); 
            System.out.println("--------------------------------------"); 
            // 打印响应状态 
            System.out.println(response.getStatusLine()); 
            if (entity != null) { 
                // 打印响应内容长度 
            //    System.out.println("Response content length: "   + entity.getContentLength()); 
                // 打印响应内容 
                System.out.println("Response content: "  + EntityUtils.toString(entity)); 
            } 
            System.out.println("------------------------------------"); 
           HttpGet get = new HttpGet("https://appleid.apple.com/captcha?type=IMAGE"); 
            // 执行get请求. 
            HttpResponse responseimg = httpclient.execute(get); 
            String name = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            File storeFile = new File("E:/"+name + ".jpg");
            FileOutputStream fileOutputStream = new FileOutputStream(storeFile);
            FileOutputStream output = fileOutputStream;
            HttpEntity entity2 = responseimg.getEntity();
            if (entity != null) {
            InputStream instream = entity2.getContent();
          
            byte b[] = new byte[1024];
            int j = 0;
            while( (j = instream.read(b))!=-1){
            output.write(b,0,j);
            }
            }
            output.flush();
            output.close();
            Scanner sc=new Scanner(System.in);
            System.out.println("输入验证码：");
            String cathap=sc.next();
            int a=2474+cathap.length();
            String cl=String.valueOf(a);
            
           // 创建httppost 
            HttpPost httppost = new HttpPost( "https://appleid.apple.com/account"); 
            method.setHeader("Host", "appleid.apple.com");
            method.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:36.0) Gecko/20100101 Firefox/36.0");
            method.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            method.setHeader("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
            method.setHeader("Referer","https://appleid.apple.com/account");
            method.setHeader("Connection", "Keep-Alive");
            method.setHeader("Content-Type", "application/x-www-form-urlencoded");
            method.setHeader("Content-Length", cl);
            method.getParams().setParameter("http.protocol.cookie-policy",CookiePolicy.BROWSER_COMPATIBILITY);
            // 创建参数队列 
            List<NameValuePair> formparams = new ArrayList<NameValuePair>(); 
            formparams.add(new BasicNameValuePair(scnt.getName(), scnt.getValue())); 
            formparams.add(new BasicNameValuePair("account.person.name.lastName","崔")); 
            formparams.add(new BasicNameValuePair("account.person.name.firstName", "英楠")); 
            formparams.add(new BasicNameValuePair("account.name","admin@delongapp.cn")); 
            formparams.add(new BasicNameValuePair("account.password.password", "c45y5ngNan")); 
            formparams.add(new BasicNameValuePair("account.password.confirmedPassword", "c45y5ngNan")); 
            formparams.add(new BasicNameValuePair("account.security.securityQuestions.questions[0].id", "137")); 
            formparams.add(new BasicNameValuePair("account.security.securityQuestions.questions[0].answer", "零点起飞学")); 
            formparams.add(new BasicNameValuePair("account.security.securityQuestions.questions[1].id", "143")); 
            formparams.add(new BasicNameValuePair("account.security.securityQuestions.questions[1].answer", "周人你")); 
            formparams.add(new BasicNameValuePair("account.security.securityQuestions.questions[2].id", "135")); 
            formparams.add(new BasicNameValuePair("account.security.securityQuestions.questions[2].answer", "蒋鸣慧")); 
            formparams.add(new BasicNameValuePair("account.person.birthday.year", "1987")); 
            formparams.add(new BasicNameValuePair("account.person.birthday.monthOfYear", "11")); 
            formparams.add(new BasicNameValuePair("account.person.birthday.dayOfMonth", "11")); 
            formparams.add(new BasicNameValuePair("account.person.birthday.pending","false")); 
            formparams.add(new BasicNameValuePair("account.security.rescueEmailAddress.emailAddress", "")); 
            formparams.add(new BasicNameValuePair("account.person.primaryAddress.countryCode","CHN")); 
            formparams.add(new BasicNameValuePair("account.person.primaryAddress.company", "")); 
            formparams.add(new BasicNameValuePair("account.person.primaryAddress.line1", "北京市朝阳区建外大街97号院")); 
            formparams.add(new BasicNameValuePair("account.person.primaryAddress.line2", "")); 
            formparams.add(new BasicNameValuePair("account.person.primaryAddress.line3", "")); 
            formparams.add(new BasicNameValuePair("account.person.primaryAddress.city", "北京")); 
            formparams.add(new BasicNameValuePair("account.person.primaryAddress.stateProvinceCode", "10")); 
            formparams.add(new BasicNameValuePair("account.person.primaryAddress.postalCode", "1000024")); 
            formparams.add(new BasicNameValuePair("account.preferences.preferredLanguage","zh_CN")); 
            formparams.add(new BasicNameValuePair("account.preferences.marketingPreferences.appleUpdates","true")); 
            formparams.add(new BasicNameValuePair("_account.preferences.marketingPreferences.appleUpdates","on")); 
            formparams.add(new BasicNameValuePair("account.preferences.marketingPreferences.iTunesUpdates","true")); 
            formparams.add(new BasicNameValuePair("_account.preferences.marketingPreferences.iTunesUpdates","on")); 
            formparams.add(new BasicNameValuePair("captcha.answer", cathap)); 
            formparams.add(new BasicNameValuePair("appid", "")); 
            formparams.add(new BasicNameValuePair("retuurnUrl", "")); 
            formparams.add(new BasicNameValuePair("errorUrl", "")); 
            formparams.add(new BasicNameValuePair("clientInfo", "%7B%22U%22%3A%22Mozilla%2F5.0+%28Windows+NT+6.1%3B+WOW64%29+AppleWebKit%2F537.36+%28KHTML%2C+like+Gecko%29+Chrome%2F31.0.1650.63+Safari%2F537.36%22%2C%22L%22%3A%22zh-CN%22%2C%22Z%22%3A%22GMT%2B08%3A00%22%2C%22V%22%3A%221.1%22%2C%22F%22%3A%22VGa44j1e3NlY5BSo9z4ofjb75PaK4Vpjt.gEngMQEjZrVglE4YcA.0Yz3ccbbJYMLgiPFU77qZoOSix5ezdstlYysrhsui68AQgk5Wn.bqgXK_Pmtd0SHp815LyjaY2.rINj.rIN4WzcjftckcKyAd65hz74WySXvOxwaw4a8sgS0N.BUs_43wuZPup_nH2t05oaYAhrcpMxE6DBUr5xj6Kkt8QSa.oDN3ypZHgfLMC7Afyz.sUAx9.Gj_GGEQIgwe98vDdYejftckuyPBDjaY2ftckZZLQ084akJ9V4Ipv.156fUfSHopNOt934N1L6fAL.BwCxN4t1VKWZWuxbuJjkWiK7.M_0pjRdRk2icVkZhw.Tf5.EKWG.pNOt94CN1L6eKKN.nkrpVMZ90L5H6eKy4Iw7Qx4md.1weihq57Zv5ftwHcrYKtFA8M3HSYKwxvEWMjJPSEV4yzlLAUXhayIz40zAjbTyRVOeOJRwdQSb1DxqhyA_r_LwwKdBvpZfWfUXtStKjE4PIDzm_n0vN7K25DwQPcrXnYEs4ZGtsgEngMQEliIhgjTuIvljlLnU8zXVyxGdotxO9svuB5d_rec5MeBNlYCa1nkBMfs.EUs%22%7D")); 
            
            UrlEncodedFormEntity uefEntity; 
                uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8"); 
                httppost.setEntity(uefEntity); 
                System.out.println("executing request " + httppost.getURI()); 
                HttpResponse response1; 
                response1 = httpclient.execute(httppost); 
                HttpEntity entity1 = response1.getEntity(); 
                if (entity != null) { 
                    System.out.println("--------------------------------------"); 
                    System.out.print("status=========="+response1.getStatusLine());
                    System.out.println("Response content: " 
                            + EntityUtils.toString(entity1, "UTF-8")); 
                    System.out.println("--------------------------------------"); 
                }   
        } catch (ClientProtocolException e) { 
            e.printStackTrace(); 
        } catch (ParseException e) { 
            e.printStackTrace(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } finally { 
            // 关闭连接,释放资源 
            httpclient.getConnectionManager().shutdown(); 
        } 
    }
}