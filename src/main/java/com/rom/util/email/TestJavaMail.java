package com.rom.util.email;

public class TestJavaMail {
	public static void main(String[] args) {

		MailBean mb = new MailBean();
		mb.setHost("smtp.qiye.163.com"); // 设置SMTP主机(163)，若用126，则设为：smtp.126.com
		mb.setUsername("zhoufangyu@ronghuijinfubj.com"); // 设置发件人邮箱的用户名
		mb.setPassword("siyanlv3@"); // 设置发件人邮箱的密码，需将*号改成正确的密码
		mb.setFrom("zhoufangyu@ronghuijinfubj.com"); // 设置发件人的邮箱
		
		mb.setTo("11@qq.com");  // 设置收件人的邮箱
//		mb.setTo("9921297@qq.com");
		
//		mb.setCopyColumn("ronghui@ronghuijinfubj.com");	//  设置抄送人
//		mb.setCopyColumn("jishu@ronghuijinfubj.com");
		
		mb.setSubject("测试邮件"); // 设置邮件的主题
		mb.setContent("本邮件中包含三个附件，请检查！ 其实没有邮件"); // 设置邮件的正文

		// mb.attachFile("F:/commons-collections-3.1.jar"); // 往邮件中添加附件
		// mb.attachFile("F:/c3p0-0.9.1.2.jar");
		// mb.attachFile("F:/副本.jar");

		SendMail sm = new SendMail();
		System.out.println("正在发送邮件...");

		if (sm.sendMail(mb)) // 发送邮件
			System.out.println("发送成功!");
		else
			System.out.println("发送失败!");
	}
}
