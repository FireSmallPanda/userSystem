package com.bdqn.dao;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Repository;

import com.bdqn.entity.UsersImage;

@Repository(value = "usersImageDaoImpl")
public class UsersImageDaoImpl implements IUsersImageDao {

	@Override
	public boolean addUsersImage(UsersImage image) {
		int result = -1;
		SqlSession session = null;
		// 初始图片状态
		image.setUserImageStatus(1);
		try {
			Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			SqlSessionFactory factory = builder.build(reader);
			session = factory.openSession();
			result = session.update("com.bdqn.dao.IUsersImageDao.addUsersImage", image);
			session.commit();
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean delUsersImage(UsersImage image) {
		SqlSession session = null;
		try {
			Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			SqlSessionFactory factory = builder.build(reader);
			session = factory.openSession();
			int changeNum = session.update("com.bdqn.dao.IUsersImageDao.delUsersImage", image);
			session.commit();
			if (changeNum > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
	}

	@Override
	public boolean updateUsersImage(UsersImage image) {
		SqlSession session = null;
		try {
			Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			SqlSessionFactory factory = builder.build(reader);
			session = factory.openSession();
			session.update("com.bdqn.dao.IUsersImageDao.updateUsersImage", image);
			session.commit();
			return true;
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
	}

	@Override
	public UsersImage getUsersImage(UsersImage image) {
		SqlSession session = null;
		image.setUserImageStatus(1);
		try {
			Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			SqlSessionFactory factory = builder.build(reader);
			session = factory.openSession();
			List<UsersImage> list = new ArrayList<UsersImage>();
			list = session.selectList("com.bdqn.dao.IUsersImageDao.getUsersImage", image);
			if (list.size() >= 1) { // 若大于一条数据则返回
				return list.get(list.size()-1);// 返回最后一条
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

}
