package org.tedros.redminetools.model;

import java.util.Date;
import java.util.List;

import org.tedros.server.entity.TEntity;

public class TIssue extends TEntity {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String subject;
    private Date startDate;
    private Date dueDate;
    private Date createdOn;
    private Date updatedOn;
    private Integer doneRatio;
    private Integer parentId;
    private Integer priorityId;
    private Float estimatedHours;
    private Float spentHours;
    private Integer assigneeId;
    private String assigneeName;
    private String notes;
    private Boolean privateNotes;
    private String priorityText;
    private Integer projectId;
    private String projectName;
    private Integer authorId;
    private String authorName;
    private TTracker tracker;
    private String description;
    private Date closedOn;
    private Integer statusId;
    private String statusName;
    private TRedmineVersion targetVersion;
    private TIssueCategory issueCategory;
    private Boolean privateIssue;
    private List<TCustomField> customFields;
    private List<TJournal> journals;
    private List<TIssueRelation> relations;
    private List<TAttachment> attachments;
    private List<TChangeset> changesets;
    private List<TWatcher> watchers;
    private List<TIssue> children;
   

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
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