package com.bdqn.service;

import com.bdqn.entity.ConsumerView;

public interface IConsumerViewService {
	/**
	 * 添加用户意见
	 * @param consumerView
	 * @return
	 */
	boolean addConsumerView(ConsumerView consumerView);
}
