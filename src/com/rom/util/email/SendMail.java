package com.rom.util.email;

import java.io.UnsupportedEncodingException; 
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
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

import org.apache.commons.codec.binary.Base64;



public class SendMail {
	public boolean sendMail(MailBean mb) {
		String host = mb.getHost();
		final String username = mb.getUsername();
		final String password = mb.getPassword();
		String from = mb.getFrom();
//		String to = mb.getTo();
		String subject = mb.getSubject();
		String content = mb.getContent();
		String fileName = mb.getFilename();
		Vector<String> file = mb.getFile();

		Properties props = System.getProperties();
		props.put("mail.smtp.host", host); // 设置SMTP的主机
		props.put("mail.smtp.auth", "true"); // 需要经过验证

		Session session = Session.getInstance(props, new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			MimeMessage msg = new MimeMessage(session);
			// 设置多个收件人地址  
            List<String> list = mb.getTo();  
            
            String toAddress = getAddress(list);  
			
			msg.setFrom(new InternetAddress(from));
			
			InternetAddress[] address = InternetAddress.parse(toAddress);  
			msg.setRecipients(Message.RecipientType.TO, address);
			
			msg.setSubject(subject);

			// 设置抄送人
			if (mb.getCopyColumn() != null) {
				msg.setRecipients(Message.RecipientType.CC,
						(Address[]) InternetAddress.parse(getAddress(mb.getCopyColumn())));
			}

			Multipart mp = new MimeMultipart();
			MimeBodyPart mbpContent = new MimeBodyPart();
			mbpContent.setText(content);
			mp.addBodyPart(mbpContent);

			/* 往邮件中添加附件 */
			if (file != null) {
				Enumeration<String> efile = file.elements();
				while (efile.hasMoreElements()) {
					MimeBodyPart mbpFile = new MimeBodyPart();
					fileName = efile.nextElement().toString();
					FileDataSource fds = new FileDataSource(fileName);
					mbpFile.setDataHandler(new DataHandler(fds));
					try {
						mbpFile.setFileName("=?GBK?B?"
								+ Base64.encodeBase64(fds.getName().getBytes("GBK"))
								+ "?=");
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
	
	
	
	public String getAddress(List<String> list){
		String address = "";
		for (String string : list) {
			address += string + ","; 
		}
		return address;
	}
}
