package com.bdqn.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bdqn.dao.IUserDao;
import com.bdqn.entity.User;

@Service(value = "uerServiceImpl")
public class UserServiceImpl implements IUserService {
	@Resource
	private IUserDao userDaoImpl;

	@Override
	public User getUserInfoByPassword(User user) {
		// 隐藏登入密码
		User retn = userDaoImpl.getUserInfoByPassword(user);
		if (retn != null) {
			retn.setUserPassword("");
		}

		return retn;
	}

	@Override
	public List<User> getAllUser() {
		return userDaoImpl.getAllUser();
	}

	@Override
	public boolean addUser(User user) {
		return userDaoImpl.addUser(user);
	}

	@Override
	public boolean deleteUser(int userId) {
		return userDaoImpl.deleteUser(userId);
	}

	@Override
	public List<User> serachUser(User user, int startRow, int endRow) {
		
		return userDaoImpl.serachUser(user, startRow, endRow);
	}

	@Override
	public boolean updateUser(User user) {
		
		return userDaoImpl.updateUser(user);
	}

	@Override
	public int searchUserCount(User user) {
		return userDaoImpl.searchUserCount(user);
	}

	@Override
	public String getUserEmail(User user) {
		return userDaoImpl.getUserEmail(user);
	}



}
