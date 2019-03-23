package com.bdqn.dao;

import java.util.List;

import com.bdqn.entity.Projects;

public interface IProjectsDao {
	/**
	 * 添加项目
	 * @param projects
	 * @return
	 */
	boolean addProject(Projects projects);
	
	/**
	 * 删除项目
	 * @param projectsId
	 * @return
	 */
	boolean deleteProject(int projectsId);
	/**
	 * 更改项目
	 * @param projects
	 * @return
	 */
	boolean updateProject(Projects projects);
	/**
	 * 收索项目
	 * @param projects
	 * @param startRow
	 * @param endRow
	 * @return
	 */
	List<Projects> serachProject(Projects projects,int startRow,int endRow);
	/**
	 * 查询项目总记录数
	 * @param projects
	 * @return
	 */
	int searchProjectCount(Projects projects);
}
