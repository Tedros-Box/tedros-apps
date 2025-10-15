package org.tedros.redminetools.model;

import java.util.Date;
import java.util.List;

import org.tedros.server.entity.TEntity;

public class Version extends TEntity {
	
	private static final long serialVersionUID = 1L;
	
	private String projectId;
	
	private String projectName;

	private String name;
	
	private String description;
	
	private String status;
	
	private String sharing;
	
	private String dueDate;
	
	private Date createdOn;
	
	private Date updatedOn;
	
	private List<CustomField> customFields;

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

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
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

	public List<CustomField> getCustomFields() {
		return customFields;
	}

	public void setCustomFields(List<CustomField> customFields) {
		this.customFields = customFields;
	}
	
	
	@Override
	public String toString() {
		return name;
	}
	
}
