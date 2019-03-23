package com.bdqn.dao;

import java.io.Reader;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Repository;

import com.bdqn.entity.User;

@Repository(value = "userDaoImpl")
public class UserDaoImpl implements IUserDao {

	@Override
	public User getUserInfoByPassword(User user) {
		SqlSession session = null;
		try {
			Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			SqlSessionFactory factory = builder.build(reader);
			session = factory.openSession();
			return session.selectOne("com.bdqn.dao.IUserDao.getUserInfoByPassword", user);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

	@Override
	public List<User> getAllUser() {
		SqlSession session = null;
		try {
			Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			SqlSessionFactory factory = builder.build(reader);
			session = factory.openSession();
			return session.selectList("com.bdqn.dao.IUserDao.getAllUser");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			session.close();
		}
		return null;
	}

	@Override
	public boolean addUser(User user) {
		int result = -1;
		SqlSession session = null;
		// 初始用户可用
		user.setUserStatus(1);
		try {
			Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			SqlSessionFactory factory = builder.build(reader);
			session = factory.openSession();
			result = session.update("com.bdqn.dao.IUserDao.addUser", user);
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
	public boolean deleteUser(int userId) {
		SqlSession session = null;
		try {
			Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			SqlSessionFactory factory = builder.build(reader);
			session = factory.openSession();
			int changeNum = session.update("com.bdqn.dao.IUserDao.deleteUser", userId);
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

	public boolean updateUser(User user) {
		SqlSession session = null;
		try {
			Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			SqlSessionFactory factory = builder.build(reader);
			session = factory.openSession();
			session.update("com.bdqn.dao.IUserDao.updateUser", user);
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

	@SuppressWarnings("unchecked")
	@Override
	public List<User> serachUser(User user, int startRow, int endRow) {
		SqlSession session = null;
		@SuppressWarnings("rawtypes")
		HashMap map = new HashMap();
		map.put("userId", user.getUserId());
		map.put("userName", user.getUserName());
		map.put("userEmail", user.getUserEmail());
		map.put("userStatus", user.getUserStatus());
		map.put("startRow", startRow);
		map.put("endRow", endRow);
		try {
			Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			SqlSessionFactory factory = builder.build(reader);
			session = factory.openSession();
			return session.selectList("com.bdqn.dao.IUserDao.serachUser", map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

	@Override
	public int searchUserCount(User user) {
		SqlSession session = null;
		try {
			Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			SqlSessionFactory factory = builder.build(reader);
			session = factory.openSession();
			int num = session.selectOne("com.bdqn.dao.IUserDao.serachUserCount", user);
			session.commit();
			return num;
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
			return -1;
		} finally {
			session.close();
		}
	}

	@Override
	public String getUserEmail(User user) {
		SqlSession session = null;
		try {
			Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			SqlSessionFactory factory = builder.build(reader);
			session = factory.openSession();
			User retn = (User) session.selectOne("com.bdqn.dao.IUserDao.getUserEmail", user);
			if(retn!=null){
				
				return retn.getUserEmail();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

}
