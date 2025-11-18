package org.tedros.redminetools.model;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("Redmine issue category defined within a project")
public class TIssueCategory {

    @JsonPropertyDescription("Unique category ID")
    private Integer id;

    @JsonPropertyDescription("Category name (e.g. 'Backend', 'UI')")
    private String name;

    @JsonPropertyDescription("Project ID this category belongs to")
    private Integer projectId;

    @JsonPropertyDescription("User ID auto-assigned when category selected")
    private Integer assigneeId;

    @JsonPropertyDescription("Auto-assigned user full name")
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
