package com.bdqn.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bdqn.entity.User;

@Service
public interface IUserService {
	/**
	 * 根据帐户密码获取user信息
	 * 
	 * @param user
	 * @return
	 */
	public User getUserInfoByPassword(User user);

	/**
	 * 得到所有用户列表
	 * 
	 * @return
	 */
	public List<User> getAllUser();
	/**
	 * 添加用户
	 * 
	 * @param user
	 *            用户信息
	 * @return 添加结果
	 */
	boolean addUser(User user);
	
	/**
	 * 删除用户
	 * @param userId
	 * @return
	 */
	boolean deleteUser(int userId);
	
	/**
	 * 更改用户  
	 * @param user 用户信息
	 * @return
	 */
	boolean updateUser(User user);
	
	/**
	 * 搜索User
	 * @param user
	 * @return
	 */
	List<User> serachUser(User user,int startRow,int endRow);
	/**
	 * 条件查询总记录数
	 * @param user
	 * @return
	 */
	int searchUserCount(User user);
	
	/**
	 * 得到用户邮箱
	 * @param user
	 * @return
	 */
	String getUserEmail(User user);
}
