package org.tedros.redminetools.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("Redmine project version / target version (milestone or release)")
public class TRedmineVersion {

    @JsonPropertyDescription("Unique version ID")
    private Integer id;

    @JsonPropertyDescription("Project ID this version belongs to")
    private String projectId;

    @JsonPropertyDescription("Project name")
    private String projectName;

    @JsonPropertyDescription("Version name (e.g. '2.5.0', 'Sprint 23')")
    private String name;

    @JsonPropertyDescription("Version description")
    private String description;

    @JsonPropertyDescription("Status: open, locked, closed")
    private String status;

    @JsonPropertyDescription("Sharing: none, descendants, hierarchy, tree, system")
    private String sharing;

    @JsonPropertyDescription("Target completion date")
    private Date dueDate;

    @JsonPropertyDescription("Creation timestamp")
    private Date createdOn;

    @JsonPropertyDescription("Last update timestamp")
    private Date updatedOn;

    @JsonPropertyDescription("Custom field values for this version")
    private List<TCustomField> customFields;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSharing() {
		return sharing;
	}

	public void setSharing(String sharing) {
		this.sharing = sharing;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public List<TCustomField> getCustomFields() {
		return customFields;
	}

	public void setCustomFields(List<TCustomField> customFields) {
		this.customFields = customFields;
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
