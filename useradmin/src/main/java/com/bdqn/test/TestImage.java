package com.bdqn.test;

import com.bdqn.dao.UsersImageDaoImpl;
import com.bdqn.entity.UsersImage;

public class TestImage {

	public static void main(String[] args) {
		// addImage();
		//getImage();
		
		delImage();
		
		// updateImage();

	}

	private static void updateImage() {
		UsersImageDaoImpl imageDaoImpl = new UsersImageDaoImpl();
		UsersImage image = new UsersImage();
		image.setUserImageName("修改过的哦");
		image.setUserImagePath("F:/sdsdsdsd.jpg");
		image.setUserId(2);
		
		System.out.println(imageDaoImpl.updateUsersImage(image));
	}

	private static void delImage() {
		UsersImageDaoImpl imageDaoImpl = new UsersImageDaoImpl();
		UsersImage image = new UsersImage();
		image.setUserImageId(5);
		System.out.println(imageDaoImpl.delUsersImage(image));
		
	}

	private static void getImage() {
		UsersImageDaoImpl imageDaoImpl = new UsersImageDaoImpl();
		UsersImage image = new UsersImage();
		image.setUserId(1);
		image.setUserImageType("1");
		System.out.println(imageDaoImpl.getUsersImage(image).getUserImagePath());
		
	}

	private static void addImage() {
		UsersImageDaoImpl imageDaoImpl = new UsersImageDaoImpl();
		UsersImage image = new UsersImage();
		image.setUserId(2);
		image.setUserImageName("头像二");
		image.setUserImagePath("xxx");
		image.setUserImageType("1");
		// imageDaoImpl.addUsersImage(image);
		System.out.println(imageDaoImpl.addUsersImage(image));
	}

}
