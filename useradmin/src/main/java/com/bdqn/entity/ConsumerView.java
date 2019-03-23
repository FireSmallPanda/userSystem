package com.bdqn.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * 用户介意实体类
 * @author Administrator
 *
 */
public class ConsumerView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer consumerViewId;
	private String consumerViewContactWay;
	private String consumerViewContext;
	private Date consumerViewDate;
	private Integer consumerViewStatus;

	public Integer getConsumerViewId() {
		return consumerViewId;
	}

	public void setConsumerViewId(Integer consumerViewId) {
		this.consumerViewId = consumerViewId;
	}

	public String getConsumerViewContactWay() {
		return consumerViewContactWay;
	}

	public void setConsumerViewContactWay(String consumerViewContactWay) {
		this.consumerViewContactWay = consumerViewContactWay;
	}

	public String getConsumerViewContext() {
		return consumerViewContext;
	}

	public void setConsumerViewContext(String consumerViewContext) {
		this.consumerViewContext = consumerViewContext;
	}

	public Date getConsumerViewDate() {
		return consumerViewDate;
	}

	public void setConsumerViewDate(Date consumerViewDate) {
		this.consumerViewDate = consumerViewDate;
	}

	public Integer getConsumerViewStatus() {
		return consumerViewStatus;
	}

	public void setConsumerViewStatus(Integer consumerViewStatus) {
		this.consumerViewStatus = consumerViewStatus;
	}

}
