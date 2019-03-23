package com.bdqn.test;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class TestXMl {
	public static void main(String[] args) {
		try {
			test5();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void test5() throws Exception {
		// 创建SAXReader对象
		SAXReader reader = new SAXReader();
	String filePath=	TestXMl.class.getResource("aaa.html").toString();

		// 读取文件 转换成Document
		Document document = reader.read(new File("src/main/java/com/bdqn/test/aaa.html"));
		// document转换为String字符串
		String documentStr = document.asXML();
		System.out.println( documentStr);
		
	}

}
