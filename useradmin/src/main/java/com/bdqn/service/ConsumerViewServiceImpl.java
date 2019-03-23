package com.bdqn.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bdqn.dao.IConsumerViewDao;
import com.bdqn.entity.ConsumerView;
@Service(value = "consumerViewServiceImpl")
public class ConsumerViewServiceImpl implements IConsumerViewService {

	@Resource
	private IConsumerViewDao consumerViewDaoImpl;
	
	@Override
	public boolean addConsumerView(ConsumerView consumerView) {
		return consumerViewDaoImpl.addConsumerView(consumerView);
	}

}
