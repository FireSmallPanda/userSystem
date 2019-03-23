package com.bdqn.action;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bdqn.entity.ConsumerView;

import com.bdqn.service.IConsumerViewService;

@Controller
public class ConsumerViewController {
	/**
	 * 添加用户意见
	 * @param consumerView
	 * @return
	 */
	@RequestMapping("userDo/doAddConsumerView.htm")
	@ResponseBody
	public String doAddConsumerView(ConsumerView consumerView) {
		boolean flag = consumerViewServiceImpl.addConsumerView(consumerView);
		
		if(flag){
			
			return "01";// 添加成功
			
		}else {
			
			return "02"; // 添加失败
			
		}

	}

	@Resource
	private IConsumerViewService consumerViewServiceImpl;
}
