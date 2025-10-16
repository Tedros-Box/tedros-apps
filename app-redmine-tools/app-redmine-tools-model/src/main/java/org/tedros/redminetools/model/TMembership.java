package org.tedros.redminetools.model;

import java.util.List;
import org.tedros.server.entity.TEntity;

public class TMembership extends TEntity {
	
	private static final long serialVersionUID = 1L;

	private TProject project;
	
	private Integer userId;
	
	private String userName;
	
	private Integer groupId;
	
	private String groupName;
	
	private List<TRemineRole> roles;

	public TProject getProject() {
		return project;
	}

	public void setProject(TProject project) {
		this.project = project;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<TRemineRole> getRoles() {
		return roles;
	}

	public void setRoles(List<TRemineRole> roles) {
		this.roles = roles;
	}
	
	@Override
	public String toString() {
		return groupName!=null ? groupName : userName;
	}

}
