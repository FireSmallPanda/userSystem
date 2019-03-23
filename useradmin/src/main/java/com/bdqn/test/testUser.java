package com.bdqn.test;

import com.bdqn.dao.UserDaoImpl;
import com.bdqn.entity.User;

public class testUser {

	public static void main(String[] args) {
		searchAcount();

	}

	private static void searchAcount() {
		UserDaoImpl daoImpl = new UserDaoImpl();
		User user = new User();
		user.setUserAccount("aaasdasdasa");
		System.out.println(daoImpl.searchUserCount(user));
		
	}

}
