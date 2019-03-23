package com.bdqn.entity;

import java.io.Serializable;

/**
 * 用户图片实体类
 * 
 * @author Administrator
 *
 */
public class UsersImage implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	private Integer userImageId;
	private Integer userId;
	private String userImageName;
	private String userImagePath;
	private String userImageType;
	private Integer userImageStatus;

	public Integer getUserImageId() {
		return userImageId;
	}

	public void setUserImageId(Integer userImageId) {
		this.userImageId = userImageId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserImageName() {
		return userImageName;
	}

	public void setUserImageName(String userImageName) {
		this.userImageName = userImageName;
	}

	public String getUserImagePath() {
		return userImagePath;
	}

	public void setUserImagePath(String userImagePath) {
		this.userImagePath = userImagePath;
	}

	public String getUserImageType() {
		return userImageType;
	}

	public void setUserImageType(String userImageType) {
		this.userImageType = userImageType;
	}

	public Integer getUserImageStatus() {
		return userImageStatus;
	}

	public void setUserImageStatus(Integer userImageStatus) {
		this.userImageStatus = userImageStatus;
	}

}
