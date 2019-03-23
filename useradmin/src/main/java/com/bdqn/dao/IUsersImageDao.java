package com.bdqn.dao;

import com.bdqn.entity.UsersImage;

public interface IUsersImageDao {

	/**
	 * 添加图片
	 * 
	 * @param image
	 * @return
	 */
	boolean addUsersImage(UsersImage image);

	/**
	 * 删除图片
	 * 
	 * @param image
	 * @return
	 */
	boolean delUsersImage(UsersImage image);

	/**
	 * 修改图片
	 * 
	 * @param image
	 * @return
	 */
	boolean updateUsersImage(UsersImage image);

	/**
	 * 得到图片
	 * 
	 * @param image
	 * @return
	 */
	UsersImage getUsersImage(UsersImage image);
}
