package org.tedros.redminetools.model;

import org.tedros.server.entity.TEntity;
import java.util.Date;
import java.util.Set;

public class RedmineIssue extends TEntity {

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
    private Tracker tracker;
    private String description;
    private Date closedOn;
    private Integer statusId;
    private String statusName;
    private Version targetVersion;
    private IssueCategory issueCategory;
    private Boolean privateIssue;
    private Set<CustomField> customFields;
    private Set<Journal> journals;
    private Set<IssueRelation> relations;
    private Set<Attachment> attachments;
    private Set<Changeset> changesets;
    private Set<Watcher> watchers;
    private Set<RedmineIssue> children;
   

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

	public Tracker getTracker() {
		return tracker;
	}

	public void setTracker(Tracker tracker) {
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

	public Version getTargetVersion() {
		return targetVersion;
	}

	public void setTargetVersion(Version targetVersion) {
		this.targetVersion = targetVersion;
	}

	public IssueCategory getIssueCategory() {
		return issueCategory;
	}

	public void setIssueCategory(IssueCategory issueCategory) {
		this.issueCategory = issueCategory;
	}

	public Boolean getPrivateIssue() {
		return privateIssue;
	}

	public void setPrivateIssue(Boolean privateIssue) {
		this.privateIssue = privateIssue;
	}

	public Set<CustomField> getCustomFields() {
		return customFields;
	}

	public void setCustomFields(Set<CustomField> customFields) {
		this.customFields = customFields;
	}

	public Set<Journal> getJournals() {
		return journals;
	}

	public void setJournals(Set<Journal> journals) {
		this.journals = journals;
	}

	public Set<IssueRelation> getRelations() {
		return relations;
	}

	public void setRelations(Set<IssueRelation> relations) {
		this.relations = relations;
	}

	public Set<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(Set<Attachment> attachments) {
		this.attachments = attachments;
	}

	public Set<Changeset> getChangesets() {
		return changesets;
	}

	public void setChangesets(Set<Changeset> changesets) {
		this.changesets = changesets;
	}

	public Set<Watcher> getWatchers() {
		return watchers;
	}

	public void setWatchers(Set<Watcher> watchers) {
		this.watchers = watchers;
	}

	public Set<RedmineIssue> getChildren() {
		return children;
	}

	public void setChildren(Set<RedmineIssue> children) {
		this.children = children;
	}

	@Override
    public String toString() {
        return subject;
    }
}