package org.tedros.redminetools.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("Redmine project with settings, hierarchy and enabled trackers")
public class TProject {

    @JsonPropertyDescription("Unique project ID")
    private Integer id;

    @JsonPropertyDescription("Project homepage URL (optional)")
    private String homepage;

    @JsonPropertyDescription("Short unique identifier (used in URLs)")
    private String identifier;

    @JsonPropertyDescription("Full project name/title")
    private String name;

    @JsonPropertyDescription("Project description (supports Markdown)")
    private String description;

    @JsonPropertyDescription("Project creation timestamp")
    private Date createdOn;

    @JsonPropertyDescription("Last update timestamp")
    private Date updatedOn;

    @JsonPropertyDescription("True if project is public (visible to non-members)")
    private Boolean publicProject;

    @JsonPropertyDescription("True if subprojects inherit members from parent")
    private Boolean inheritMembers;

    @JsonPropertyDescription("Parent project ID (for subprojects)")
    private Integer parentId;

    @JsonPropertyDescription("Custom field values defined for this project")
    private List<TCustomField> customFields;

    @JsonPropertyDescription("Trackers enabled in this project (Bug, Feature, etc.)")
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
