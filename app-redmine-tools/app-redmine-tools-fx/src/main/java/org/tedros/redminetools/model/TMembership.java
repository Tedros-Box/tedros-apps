package org.tedros.redminetools.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("Project membership in Redmine - links a user or group to a project with roles")
public class TMembership {

    @JsonPropertyDescription("Unique membership ID")
    private Integer id;

    @JsonPropertyDescription("Project the membership belongs to")
    private TProject project;

    @JsonPropertyDescription("User ID (null if membership is for a group)")
    private Integer userId;

    @JsonPropertyDescription("User full name (when individual membership)")
    private String userName;

    @JsonPropertyDescription("Group ID (null if membership is for a user)")
    private Integer groupId;

    @JsonPropertyDescription("Group name (when group membership)")
    private String groupName;

    @JsonPropertyDescription("List of roles/permissions granted in this project")
    private List<TRedmineRole> roles;

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

	public List<TRedmineRole> getRoles() {
		return roles;
	}

	public void setRoles(List<TRedmineRole> roles) {
		this.roles = roles;
	}
	
	@Override
	public String toString() {
		return groupName!=null ? groupName : userName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
