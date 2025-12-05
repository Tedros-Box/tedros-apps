package org.tedros.it.tools.redmine.api.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("Redmine time entry - logged hours on an issue or project")
public class TTimeEntry {

    @JsonPropertyDescription("Unique time entry ID")
    private Integer id;

    @JsonPropertyDescription("Issue ID this time was spent on (null if project-only)")
    private Integer issueId;

    @JsonPropertyDescription("Project ID this time belongs to")
    private Integer projectId;

    @JsonPropertyDescription("Project name")
    private String projectName;

    @JsonPropertyDescription("Full name of user who logged the time")
    private String userName;

    @JsonPropertyDescription("User ID who logged the time")
    private String userId;

    @JsonPropertyDescription("Time entry activity name (Design, Development, etc.)")
    private String activityName;

    @JsonPropertyDescription("Activity enumeration ID")
    private String activityId;

    @JsonPropertyDescription("Hours spent (e.g. '2.5', '1.75')")
    private String hours;

    @JsonPropertyDescription("Comment/description of the work done")
    private String comment;

    @JsonPropertyDescription("Date when the time was spent")
    private Date spentOn;

    @JsonPropertyDescription("Creation timestamp")
    private Date createdOn;

    @JsonPropertyDescription("Last update timestamp")
    private Date updatedOn;

    @JsonPropertyDescription("Custom field values for this time entry")
    private List<TCustomField> customFields;

	public Integer getIssueId() {
		return issueId;
	}

	public void setIssueId(Integer issueId) {
		this.issueId = issueId;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getSpentOn() {
		return spentOn;
	}

	public void setSpentOn(Date spentOn) {
		this.spentOn = spentOn;
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
		return activityName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
