package com.bdqn.dao;

import java.io.Reader;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Repository;

import com.bdqn.entity.Projects;

@Repository(value = "projectsDaoImpl")
public class ProjectsDaoImpl implements IProjectsDao {

	@Override
	public boolean addProject(Projects projects) {
		int result = -1;
		SqlSession session = null;
		// 初始项目可用
		projects.setProjectsStatus(1);
		try {
			Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			SqlSessionFactory factory = builder.build(reader);
			session = factory.openSession();
			result = session.update("com.bdqn.dao.IProjectsDao.addProject", projects);
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
	public boolean deleteProject(int projectsId) {
		SqlSession session = null;
		try {
			Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			SqlSessionFactory factory = builder.build(reader);
			session = factory.openSession();
			int changeNum = session.update("com.bdqn.dao.IProjectsDao.deleteProject", projectsId);
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
	public boolean updateProject(Projects projects) {
		SqlSession session = null;
		try {
			Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			SqlSessionFactory factory = builder.build(reader);
			session = factory.openSession();
			session.update("com.bdqn.dao.IProjectsDao.updateProject", projects);
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
	public List<Projects> serachProject(Projects projects, int startRow, int endRow) {
		SqlSession session = null;
		Projects project = new Projects();
		if (projects != null) {
			project = projects;
		}
		@SuppressWarnings("rawtypes")
		HashMap map = new HashMap();
		map.put("projectsId", project.getProjectsId());
		map.put("projectsName", project.getProjectsName());
		map.put("projectsStart", project.getProjectsStart());
		map.put("projectsEnd", project.getProjectsEnd());
		map.put("projectsStatus", project.getProjectsStatus());
		map.put("startRow", startRow);
		map.put("endRow", endRow);
		try {
			Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			SqlSessionFactory factory = builder.build(reader);
			session = factory.openSession();
			return session.selectList("com.bdqn.dao.IProjectsDao.serachProject", map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

	@Override
	public int searchProjectCount(Projects projects) {
		SqlSession session = null;
		try {
			Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			SqlSessionFactory factory = builder.build(reader);
			session = factory.openSession();
			int num = session.selectOne("com.bdqn.dao.IProjectsDao.serachProjectCount", projects);
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

}
