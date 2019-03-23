package com.bdqn.dao;

import com.bdqn.entity.ConsumerView;

public interface IConsumerViewDao {

	/**
	 * 添加用户意见
	 * @param consumerView
	 * @return
	 */
	boolean addConsumerView(ConsumerView consumerView);
}
