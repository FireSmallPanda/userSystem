package com.bdqn.test;

import com.bdqn.entity.DictBean;
import com.bdqn.util.DictUtil;

public class TestDict {

	public static void main(String[] args) {
		searchDict();

	}

	private static void searchDict() {
		DictUtil dictUtil = new DictUtil();
		DictBean bean = dictUtil.getDictByKey("100003", "101", "2");
		if(bean!=null){
			System.out.println(bean.getDictContentCN());
			
		}else{
			
			System.out.println("无效字典项");
		}

	}

}
