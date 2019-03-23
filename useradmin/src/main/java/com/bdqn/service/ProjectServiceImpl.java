package com.bdqn.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bdqn.dao.IProjectsDao;
import com.bdqn.entity.Projects;

@Service(value = "projectServiceImpl")
public class ProjectServiceImpl implements IProjectService {
	@Resource
	private IProjectsDao projectsDaoImpl;

	@Override
	public boolean addProject(Projects projects) {
		return projectsDaoImpl.addProject(projects);
	}

	@Override
	public boolean deleteProject(int projectsId) {
		return projectsDaoImpl.deleteProject(projectsId);
	}

	@Override
	public boolean updateProject(Projects projects) {
		return projectsDaoImpl.updateProject(projects);
	}

	@Override
	public List<Projects> serachProject(Projects projects, int startRow,
			int endRow) {
		return projectsDaoImpl.serachProject(projects, startRow, endRow);
	}

	@Override
	public int searchProjectCount(Projects projects) {

		return projectsDaoImpl.searchProjectCount(projects);
	}

}
