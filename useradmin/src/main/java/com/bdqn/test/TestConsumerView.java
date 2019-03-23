package com.bdqn.test;


import com.bdqn.dao.ConsumerViewDaoImpl;
import com.bdqn.entity.ConsumerView;

public class TestConsumerView {

	public static void main(String[] args) {
		addConsumerView();

	}

	private static void addConsumerView() {
		ConsumerView consumerView = new ConsumerView();
		ConsumerViewDaoImpl consumerViewDaoImpl = new ConsumerViewDaoImpl();
		consumerView.setConsumerViewContactWay("aavvvcc");
		consumerView.setConsumerViewContext("很好很好！");
		System.out.println(consumerViewDaoImpl.addConsumerView(consumerView));
		
	}

}
