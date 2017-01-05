package com.rom.util.email;

import java.io.UnsupportedEncodingException;  
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;



/**
 *     邮件工具类  
 * @author zfy
 * 		需要jar 包
 * 			activation.jar
 * 			mail.jar
 *
 */
public class SendMail {

    public boolean sendMail(MailBean mb) {
        String host = mb.getHost();
        final String username = mb.getUsername();
        final String password = mb.getPassword();
        String from = mb.getFrom();
        String to = mb.getTo();
        String subject = mb.getSubject();
        String content = mb.getContent();
        String fileName = mb.getFilename();
        Vector<String> file = mb.getFile();
      
        
        Properties props = System.getProperties();
        props.put("mail.smtp.host", host);                // 设置SMTP的主机
        props.put("mail.smtp.auth", "true");            // 需要经过验证
        
        Session session = Session.getInstance(props, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject);

            
            //   设置抄送人
            msg.setRecipients(Message.RecipientType.CC,
					(Address[]) InternetAddress.parse("zfy0098@163.com"));
            
            Multipart mp = new MimeMultipart();
            MimeBodyPart mbpContent = new MimeBodyPart();
            mbpContent.setText(content);
            mp.addBodyPart(mbpContent);

            /*    往邮件中添加附件    */
           if(file!=null){
        	   Enumeration<String> efile = file.elements();
               while (efile.hasMoreElements()) {
                   MimeBodyPart mbpFile = new MimeBodyPart();
                   fileName = efile.nextElement().toString();
                   FileDataSource fds = new FileDataSource(fileName);
                   mbpFile.setDataHandler(new DataHandler(fds));
                try {
					mbpFile.setFileName("=?GBK?B?" + Base64.getEncoder().encode(fds.getName().getBytes("GBK")) + "?=");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} 
                   mp.addBodyPart(mbpFile);
               }
               System.out.println("添加成功");
           }

            msg.setContent(mp);
            msg.setSentDate(new Date());
            Transport.send(msg);
            
        } catch (MessagingException me) {
            me.printStackTrace();
            return false;
        }
        return true;
    }

}