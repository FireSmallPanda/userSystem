package com.bdqn.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bdqn.dao.IUsersImageDao;
import com.bdqn.entity.UsersImage;
@Service(value = "sersImageService")
public class UsersImageService implements IUsersImageService {
	@Resource
	private IUsersImageDao usersImageDaoImpl;

	@Override
	public boolean addUsersImage(UsersImage image) {
		return usersImageDaoImpl.addUsersImage(image);
	}

	@Override
	public boolean delUsersImage(UsersImage image) {
		return usersImageDaoImpl.delUsersImage(image);
	}

	@Override
	public boolean updateUsersImage(UsersImage image) {
		return usersImageDaoImpl.updateUsersImage(image);
	}

	@Override
	public UsersImage getUsersImage(UsersImage image) {
		return usersImageDaoImpl.getUsersImage(image);
	}

}
