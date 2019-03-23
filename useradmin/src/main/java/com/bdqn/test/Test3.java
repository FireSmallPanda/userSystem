package com.bdqn.test;

import java.util.Scanner;

public class Test3 {
	public static void main(String[] args){
		System.out.println("请输入班级的个数:");
		Scanner sc = new Scanner(System.in);
		String[] p = new String[sc.nextInt()];
		System.out.println("输入"+p.length+"班级名称:");
		for(int i=0; i<p.length;i++){
			p[i]=sc.next();
		}
		System.out.println("班级名称如下所示:");
		for(int j=0; j<p.length;j++){
			System.out.println(p[j]);
		}
		for(int j=0;j<p.length;j++){
			System.out.println("输入"+p[j]+"班级的人数：");
			p[j]=sc.next();	
		}
		
	}
	

}