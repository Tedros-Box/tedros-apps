package org.tedros.it.tools.entity;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.tedros.it.tools.domain.DomainSchema;
import org.tedros.it.tools.domain.DomainTables;
import org.tedros.person.model.Employee;
import org.tedros.server.entity.TEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = DomainTables.GMUD, schema = DomainSchema.schema)
public class Gmud extends TEntity {

    private static final long serialVersionUID = 1L;

    @Column(length = 150, nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;
    
    @Column(length = 20, nullable = false)
    private String type; // NORMAL, STANDARD, EMERGENCY
    
    @Column(length = 20, nullable = false)
    private String status; // DRAFT, ANALYSIS, APPROVED, EXECUTING, FINISHED, REJECTED

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "requester_id", nullable = false)
    private Employee requester;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date scheduledDate;

    @Column(length = 1000)
    private String rollbackPlan;
    
    @Column
    private Long projectId;
    
    @Column(length = 120)
    private String projectName;
    
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_gmud", nullable = false, updatable = false)
    private List<GmudIssueReference> issueReferences;
    
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_gmud", nullable = false, updatable = false)
    private List<GmudItem> executionPlan;

    @OneToMany(mappedBy = "gmud", orphanRemoval = true, 
    		cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<GmudReview> reviews;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Employee getRequester() {
		return requester;
	}

	public void setRequester(Employee requester) {
		this.requester = requester;
	}

	public Date getScheduledDate() {
		return scheduledDate;
	}

	public void setScheduledDate(Date scheduledDate) {
		this.scheduledDate = scheduledDate;
	}

	public String getRollbackPlan() {
		return rollbackPlan;
	}

	public void setRollbackPlan(String rollbackPlan) {
		this.rollbackPlan = rollbackPlan;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public List<GmudIssueReference> getIssueReferences() {
		return issueReferences;
	}

	public void setIssueReferences(List<GmudIssueReference> issueReferences) {
		this.issueReferences = issueReferences;
	}

	public List<GmudItem> getExecutionPlan() {
		return executionPlan;
	}

	public void setExecutionPlan(List<GmudItem> executionPlan) {
		this.executionPlan = executionPlan;
	}

	public List<GmudReview> getReviews() {
		return reviews;
	}

	public void setReviews(List<GmudReview> reviews) {		
		this.reviews = reviews;
		if(this.reviews!=null) {
			for (GmudReview r : this.reviews) {
				r.setGmud(this);
			}
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Gmud))
			return false;
		Gmud other = (Gmud) obj;
		return Objects.equals(description, other.description) && Objects.equals(executionPlan, other.executionPlan)
				&& Objects.equals(issueReferences, other.issueReferences) && Objects.equals(projectId, other.projectId)
				&& Objects.equals(projectName, other.projectName) && Objects.equals(requester, other.requester)
				&& Objects.equals(reviews, other.reviews) && Objects.equals(rollbackPlan, other.rollbackPlan)
				&& Objects.equals(scheduledDate, other.scheduledDate) && Objects.equals(status, other.status)
				&& Objects.equals(title, other.title) && Objects.equals(type, other.type);
	}

	@Override
	public String toString() {
		return String.format("Gmud [getId()=%s, title=%s]", getId(), title);
	}
    
}
