package com.bdqn.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.bdqn.dao.ProjectsDaoImpl;
import com.bdqn.entity.Projects;

public class TestProject {

	public static void main(String[] args) {
		/* try {
			addProject();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		// 添加项目
		// deleteProject();

		// updateProject();

		 searchProject();
		//searchProjectPage();
	}

	/**
	 * 根据页面查询项目
	 */
	private static void searchProjectPage() {
		ProjectsDaoImpl pdi = new ProjectsDaoImpl();
		List <Projects> list = pdi.serachProject(null, 1, 5);
		
		
		System.out.println(list.size());

	}

	/** 搜索项目 */
	private static void searchProject() {
		ProjectsDaoImpl pdi = new ProjectsDaoImpl();
		Projects projects = new Projects();
		// projects.setProjectsId(2);
		// projects.setProjectsName("小");
		
		projects.setProjectsStart(null);
		projects.setProjectsEnd(null);
		System.out.println(pdi.searchProjectCount(projects));
	}

	/** 更新项目 */
	private static void updateProject() {
		ProjectsDaoImpl pdi = new ProjectsDaoImpl();
		Projects projects = new Projects();
		projects.setProjectsId(2);
		projects.setProjectsName("新能源开发x");
		Date date = new Date();
		projects.setProjectsStatus(1);
		System.out.println(pdi.updateProject(projects));

	}

	/** 删除项目 */
	private static void deleteProject() {
		ProjectsDaoImpl pdi = new ProjectsDaoImpl();
		System.out.println(pdi.deleteProject(6));
	}

	/** 添加项目 
	 * @throws ParseException */
	private static void addProject() throws ParseException {

		ProjectsDaoImpl pdi = new ProjectsDaoImpl();
		Projects projects = new Projects();
		projects.setProjectsName("新能源开发");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse("2016-10-05");
		projects.setProjectsStart(date);
		projects.setProjectsEnd(date);
		System.out.println(pdi.addProject(projects));

	}

}
