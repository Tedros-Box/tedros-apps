package org.tedros.redminetools.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("Complete Redmine issue (ticket) with core fields, relations, history and custom fields")
public class TIssue {

    @JsonPropertyDescription("Unique issue ID in Redmine")
    private Integer id;

    @JsonPropertyDescription("Issue title/subject")
    private String subject;

    @JsonPropertyDescription("Planned start date")
    private Date startDate;

    @JsonPropertyDescription("Due date / deadline")
    private Date dueDate;

    @JsonPropertyDescription("Issue creation timestamp")
    private Date createdOn;

    @JsonPropertyDescription("Last update timestamp")
    private Date updatedOn;

    @JsonPropertyDescription("Completion percentage (0-100)")
    private Integer doneRatio;

    @JsonPropertyDescription("Parent issue ID (for sub-tasks)")
    private Integer parentId;

    @JsonPropertyDescription("Priority ID (references issue_priority)")
    private Integer priorityId;

    @JsonPropertyDescription("Estimated effort in hours")
    private Float estimatedHours;

    @JsonPropertyDescription("Total time spent in hours")
    private Float spentHours;

    @JsonPropertyDescription("Assigned user ID")
    private Integer assigneeId;

    @JsonPropertyDescription("Assigned user full name")
    private String assigneeName;

    @JsonPropertyDescription("Latest comment/notes added")
    private String notes;

    @JsonPropertyDescription("True if latest notes are private")
    private Boolean privateNotes;

    @JsonPropertyDescription("Priority name (e.g. High, Urgent)")
    private String priorityText;

    @JsonPropertyDescription("Project ID this issue belongs to")
    private Integer projectId;

    @JsonPropertyDescription("Project name")
    private String projectName;

    @JsonPropertyDescription("Author/creator user ID")
    private Integer authorId;

    @JsonPropertyDescription("Author full name")
    private String authorName;

    @JsonPropertyDescription("Issue tracker/type (Bug, Feature, etc.)")
    private TTracker tracker;

    @JsonPropertyDescription("Full issue description (supports Markdown)")
    private String description;

    @JsonPropertyDescription("Date when issue was closed")
    private Date closedOn;

    @JsonPropertyDescription("Current status ID")
    private Integer statusId;

    @JsonPropertyDescription("Current status name (Open, Closed, etc.)")
    private String statusName;

    @JsonPropertyDescription("Target/fix version for this issue")
    private TRedmineVersion targetVersion;

    @JsonPropertyDescription("Issue category within project")
    private TIssueCategory issueCategory;

    @JsonPropertyDescription("True if issue is private (visible only to certain roles)")
    private Boolean privateIssue;

    @JsonPropertyDescription("List of custom field values")
    private List<TCustomField> customFields;

    @JsonPropertyDescription("History of comments and field changes")
    private List<TJournal> journals;

    @JsonPropertyDescription("Relations to other issues (blocks, precedes, etc.)")
    private List<TIssueRelation> relations;

    @JsonPropertyDescription("File attachments linked to issue")
    private List<TAttachment> attachments;

    @JsonPropertyDescription("Linked repository changesets/commits")
    private List<TChangeset> changesets;

    @JsonPropertyDescription("Users watching this issue")
    private List<TWatcher> watchers;

    @JsonPropertyDescription("Child/sub-task issues")
    private List<TIssue> children;
   

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
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

	public Integer getDoneRatio() {
		return doneRatio;
	}

	public void setDoneRatio(Integer doneRatio) {
		this.doneRatio = doneRatio;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getPriorityId() {
		return priorityId;
	}

	public void setPriorityId(Integer priorityId) {
		this.priorityId = priorityId;
	}

	public Float getEstimatedHours() {
		return estimatedHours;
	}

	public void setEstimatedHours(Float estimatedHours) {
		this.estimatedHours = estimatedHours;
	}

	public Float getSpentHours() {
		return spentHours;
	}

	public void setSpentHours(Float spentHours) {
		this.spentHours = spentHours;
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

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Boolean getPrivateNotes() {
		return privateNotes;
	}

	public void setPrivateNotes(Boolean privateNotes) {
		this.privateNotes = privateNotes;
	}

	public String getPriorityText() {
		return priorityText;
	}

	public void setPriorityText(String priorityText) {
		this.priorityText = priorityText;
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

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public TTracker getTracker() {
		return tracker;
	}

	public void setTracker(TTracker tracker) {
		this.tracker = tracker;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getClosedOn() {
		return closedOn;
	}

	public void setClosedOn(Date closedOn) {
		this.closedOn = closedOn;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public TRedmineVersion getTargetVersion() {
		return targetVersion;
	}

	public void setTargetVersion(TRedmineVersion targetVersion) {
		this.targetVersion = targetVersion;
	}

	public TIssueCategory getCategory() {
		return issueCategory;
	}

	public void setCategory(TIssueCategory issueCategory) {
		this.issueCategory = issueCategory;
	}

	public Boolean getPrivateIssue() {
		return privateIssue;
	}

	public void setPrivateIssue(Boolean privateIssue) {
		this.privateIssue = privateIssue;
	}

	public List<TCustomField> getCustomFields() {
		return customFields;
	}

	public void setCustomFields(List<TCustomField> customFields) {
		this.customFields = customFields;
	}

	public List<TJournal> getJournals() {
		return journals;
	}

	public void setJournals(List<TJournal> journals) {
		this.journals = journals;
	}

	public List<TIssueRelation> getRelations() {
		return relations;
	}

	public void setRelations(List<TIssueRelation> relations) {
		this.relations = relations;
	}

	public List<TAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<TAttachment> attachments) {
		this.attachments = attachments;
	}

	public List<TChangeset> getChangesets() {
		return changesets;
	}

	public void setChangesets(List<TChangeset> changesets) {
		this.changesets = changesets;
	}

	public List<TWatcher> getWatchers() {
		return watchers;
	}

	public void setWatchers(List<TWatcher> watchers) {
		this.watchers = watchers;
	}

	public List<TIssue> getChildren() {
		return children;
	}

	public void setChildren(List<TIssue> children) {
		this.children = children;
	}

	@Override
    public String toString() {
        return subject;
    }
}