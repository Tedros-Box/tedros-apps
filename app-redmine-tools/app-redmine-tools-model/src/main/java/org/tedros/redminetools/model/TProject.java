package org.tedros.redminetools.model;

import java.util.Date;
import java.util.List;

import org.tedros.server.entity.TEntity;

public class TProject extends TEntity {
	
	private static final long serialVersionUID = -1747906306442723211L;
	
	private String homepage;
	
	private String identifier;
	
	private String name;
	
	private String description;
	
	private Date createdOn;
	
	private Date updatedOn;
	
	private Boolean publicProject;
	
	private Boolean inheritMembers;
	
	private Integer parentId;
	
	private List<TCustomField> customFields;
	
	private List<TTracker> trackers;	
		
	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
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

	public Boolean getPublicProject() {
		return publicProject;
	}

	public void setPublicProject(Boolean publicProject) {
		this.publicProject = publicProject;
	}

	public Boolean getInheritMembers() {
		return inheritMembers;
	}

	public void setInheritMembers(Boolean inheritMembers) {
		this.inheritMembers = inheritMembers;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public List<TCustomField> getCustomFields() {
		return customFields;
	}

	public void setCustomFields(List<TCustomField> customFields) {
		this.customFields = customFields;
	}

	public List<TTracker> getTrackers() {
		return trackers;
	}

	public void setTrackers(List<TTracker> trackers) {
		this.trackers = trackers;
	}

	@Override
	public String toString() {
		return name;
	}
}
