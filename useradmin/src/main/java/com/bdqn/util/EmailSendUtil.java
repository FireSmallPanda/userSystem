package com.bdqn.util;

import java.io.File;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import com.sun.mail.util.MailSSLSocketFactory;

/***
 * 步骤1：开启POP3/SMTP服务 步骤2：开启 SSL 加密
 */
public class EmailSendUtil {
	/**
	 * 
	 * @param serverHost
	 *            服务 smtp.qq.com
	 * @param email
	 *            发件方
	 * @param Password
	 *            第三方密码
	 * @param sentEmail
	 *            接收方
	 * @param emailTitle
	 *            邮件标题
	 * @param context
	 *            邮件内容
	 * @param emailType
	 *            邮件类型 html or text
	 * @throws Exception
	 */
	public void sendMail(String serverHost, String email, String password, String sentEmail, String emailTitle,
			String context, String emailType) throws Exception {
		Properties pro = new Properties();
		pro.put("mail.smtp.host", serverHost);// 存储发送邮件的服务器
		pro.put("mail.smtp.auth", "true"); // 通过服务器验证
		// 开启 SSL 加密
		MailSSLSocketFactory sf = new MailSSLSocketFactory();
		sf.setTrustAllHosts(true);
		pro.put("mail.smtp.ssl.enable", "true");
		pro.put("mail.smtp.ssl.socketFactory", sf);
		Session s = Session.getInstance(pro); // 建立邮件Session对象
		// s.setDebug(true);
		MimeMessage message = new MimeMessage(s); // 邮件会话新建一个消息对象
		try {
			message.setFrom(new InternetAddress(email)); // 设置发送地址
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(sentEmail)); // 设置接收地址
			message.setSubject(emailTitle); // 设置邮件标题

			// MimeMultipart类是一个容器类，包含MimeBodyPart类型的对象
			Multipart mainPart = new MimeMultipart();
			MimeBodyPart messageBodyPart = new MimeBodyPart();// 创建一个包含HTML内容的MimeBodyPart
			messageBodyPart.setContent(context, "text/html; charset=utf-8");
			// 设置HTML内容判断普通文件还是html
			if ("html".equals(emailType)) {
				message.setText(context); // 设置邮件正文
			} else {
				message.setText(context); // 设置邮件正文
			}
			mainPart.addBodyPart(messageBodyPart);
			// 存在附件时请消除下面注释 并添加filePaths 参数
			String[] filePaths = {"D:\\aa.jpg"};
			if (filePaths != null && filePaths.length > 0) {
				for (String filePath : filePaths) {
					messageBodyPart = new MimeBodyPart();
					File file = new File(filePath);
					if (file.exists()) {// 附件存在磁盘中
						FileDataSource fds = new FileDataSource(file);// 得到数据源
						messageBodyPart.setDataHandler(new DataHandler(fds));// 得到附件本身并至入BodyPart
						messageBodyPart.setFileName(file.getName());// 得到文件名同样至入BodyPart
						mainPart.addBodyPart(messageBodyPart);
					}
				}
			}

			// 将MimeMultipart对象设置为邮件内容
			message.setContent(mainPart);
			message.setSentDate(new Date()); // 设置邮件日期
			message.saveChanges(); // 保存邮件更改信息
			Transport transport = s.getTransport("smtp");
			/** 十六位授权码：shahtjckxtpnicea **/
			transport.connect(serverHost, email.substring(0, email.indexOf("@")), password); // 服务器地址，邮箱账号，邮箱密码
			transport.sendMessage(message, message.getAllRecipients()); // 发送邮件
			transport.close();// 关闭
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 建立邮件内容
	 * 
	 * @param infoMap
	 *            模板参数
	 * @return
	 */
	public String getEmailContent(Map<String, String> infoMap) {
		// 创建SAXReader对象
		SAXReader reader = new SAXReader();
		// 读取文件 转换成Document
		Document document = null;
		try {
			document = reader.read(new File(this.getClass().getResource("").getPath() + "//email.html"));
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// document转换为String字符串
		String documentStr = document.asXML();
		for (String key : infoMap.keySet()) {
			// map.keySet()返回的是所有key的值
			String str = infoMap.get(key);// 得到每个key多对用value的值
			documentStr = documentStr.replace("{" + key + "}", str);
		}
		return documentStr;

	};
}
