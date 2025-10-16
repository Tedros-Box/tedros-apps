package org.tedros.redminetools.model;

import org.tedros.server.entity.TEntity;

public class TIssueCategory extends TEntity {

	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private Integer projectId;
	
	private Integer assigneeId;
	
	private String assigneeName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(Integer assigneeId) {
		this.assigneeId = assigneeId;
	}

	public String getAssigneeName() {
		return assigneeName;
	}

	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
