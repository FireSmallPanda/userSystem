package com.bdqn.test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.bdqn.util.EmailSendUtil;

public class testEmail {

	public static void main(String[] args) {
		 sentEmail();
		 File file1 = new File( "email.html" );
		 System.out.println();
		//getURL();
	}

	private static void getURL() {
		System.out.println(ClassLoader.class.getResource("email.html"));
	}

	private static void sentEmail() {
		EmailSendUtil sendUtil = new EmailSendUtil();
		String context = "";
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		Map<String, String> map = new HashMap<>();
		map.put("userAccount", "aaa***");
		map.put("code", "25654561231");
		map.put("date", dateString);
		context = sendUtil.getEmailContent(map);
		System.out.println(context);
		try {
			 sendUtil.sendMail("smtp.qq.com", "1020529941@qq.com", "nxlzpshzqiydbeih", "792434170@qq.com", "密码找回邮件", context, "html");
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

}
