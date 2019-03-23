package com.bdqn.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目实体类
 * @author Administrator
 *
 */
public class Projects implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer projectsId;
	private String projectsName;
	private Date projectsStart;
	private Date projectsEnd;
	private Integer projectsStatus;

	public Integer getProjectsId() {
		return projectsId;
	}

	public void setProjectsId(Integer projectsId) {
		this.projectsId = projectsId;
	}

	public String getProjectsName() {
		return projectsName;
	}

	public void setProjectsName(String projectsName) {
		this.projectsName = projectsName;
	}

	public Date getProjectsStart() {
		return projectsStart;
	}

	public void setProjectsStart(Date projectsStart) {
		this.projectsStart = projectsStart;
	}

	public Date getProjectsEnd() {
		return projectsEnd;
	}

	public void setProjectsEnd(Date projectsEnd) {
		this.projectsEnd = projectsEnd;
	}

	public Integer getProjectsStatus() {
		return projectsStatus;
	}

	public void setProjectsStatus(Integer projectsStatus) {
		this.projectsStatus = projectsStatus;
	}

}
