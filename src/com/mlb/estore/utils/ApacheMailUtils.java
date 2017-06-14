package com.mlb.estore.utils;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

// javaMail 
public class ApacheMailUtils {
	public static boolean sendMail(String sendto, String subject, String msg) {
		try {
			// Email email = new SimpleEmail();
			Email email = new HtmlEmail();// 获取邮件对象
			// 发送邮件服务器主机名
			// email.setHostName("smtp.qq.com");
			email.setHostName("localhost");
			// email.setSmtpPort(465);// 发件邮件服务器端口号
			// 发件人的邮箱账号和授权码(代替密码使用)
			// email.setAuthenticator(new DefaultAuthenticator("369811823",
			// "tjsvkqsqiwzmqkkbhaa"));
			email.setAuthenticator(new DefaultAuthenticator("a@eryeer.com",
					"123"));
			// email.setSSLOnConnect(true);// 安全校验连接
			email.setFrom("a@eryeer.com");// 邮件发件人信息
			email.setSubject(subject);
			// email.setMsg(msg); 发送超文本信息 ---> 设置信息内容类型 是一个超文本
			email.setContent(msg, "text/html;charset=utf-8");
			email.addTo(sendto);// 将发送给指定的用户 收件人信息
			email.send();// 发送邮件
			return true;
		} catch (EmailException e) {
			e.printStackTrace();
			return false;
		}
	}
}
