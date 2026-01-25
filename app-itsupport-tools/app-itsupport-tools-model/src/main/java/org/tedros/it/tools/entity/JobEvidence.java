/**
 * 
 */
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

/**
 *  A job evidence carried out
 */
@Entity
@Table(name = DomainTables.JOB_EVIDENCE, schema = DomainSchema.schema)
public class JobEvidence extends TEntity {

	private static final long serialVersionUID = 7607736437723984845L;
	
	@Column(length=120, nullable = false)
	private String name;
	
	@Column
	private String description;
	
	@Column(length=50)
	private String tool;
	
	@Column(length=50, nullable = false)
	private String issueNumber;
	
	@Column(length=500)
	private String issueTitle;
	
	@Column(length=2083)
	private String issueLink;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="person_id", nullable=false)
	private Employee employee;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date executionDate;
	
	@OneToMany(orphanRemoval=true, 
			cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="id_evidence", nullable=false, updatable=false)
	private List<JobEvidenceItem> items;

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

	public String getTool() {
		return tool;
	}

	public void setTool(String tool) {
		this.tool = tool;
	}

	public String getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}

	public String getIssueTitle() {
		return issueTitle;
	}

	public void setIssueTitle(String issueTitle) {
		this.issueTitle = issueTitle;
	}

	public String getIssueLink() {
		return issueLink;
	}

	public void setIssueLink(String issueLink) {
		this.issueLink = issueLink;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Date getExecutionDate() {
		return executionDate;
	}

	public void setExecutionDate(Date executionDate) {
		this.executionDate = executionDate;
	}

	public List<JobEvidenceItem> getItems() {
		return items;
	}

	public void setItems(List<JobEvidenceItem> items) {
		this.items = items;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(description, employee, executionDate, issueLink, issueNumber, issueTitle,
				items, name, tool);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof JobEvidence)) {
			return false;
		}
		JobEvidence other = (JobEvidence) obj;
		return Objects.equals(description, other.description) && Objects.equals(employee, other.employee)
				&& Objects.equals(executionDate, other.executionDate) && Objects.equals(issueLink, other.issueLink)
				&& Objects.equals(issueNumber, other.issueNumber) && Objects.equals(issueTitle, other.issueTitle)
				&& Objects.equals(items, other.items) && Objects.equals(name, other.name)
				&& Objects.equals(tool, other.tool);
	}
	
	
	
	

}
