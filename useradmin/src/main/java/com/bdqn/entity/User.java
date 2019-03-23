package com.bdqn.entity;

import java.io.Serializable;

/**
 * 用户实体类
 * 
 * @author Administrator
 *
 */
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6025468042290505054L;
	private Integer userId;
	private String userName;
	private String userAccount;
	private String userPassword;
	private String userEmail;
	private Integer userStatus;

	public Integer getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
}
