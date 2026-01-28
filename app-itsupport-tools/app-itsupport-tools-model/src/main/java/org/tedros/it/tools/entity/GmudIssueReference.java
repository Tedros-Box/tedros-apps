package org.tedros.it.tools.entity;

import java.util.Objects;

import org.tedros.it.tools.domain.DomainSchema;
import org.tedros.it.tools.domain.DomainTables;
import org.tedros.server.entity.TEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = DomainTables.GMUD_ISSUE_REFERENCE, schema = DomainSchema.schema)
public class GmudIssueReference extends TEntity {

    private static final long serialVersionUID = 4246280208687191618L;

	@Column(nullable = false)
    private Long issueId;

	@Column(length = 200, nullable = false)
    private String issueTitle;
	
	@Column(length = 500)
    private String issueUrl;

	public Long getIssueId() {
		return issueId;
	}

	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}

	public String getIssueTitle() {
		return issueTitle;
	}

	public void setIssueTitle(String issueTitle) {
		this.issueTitle = issueTitle;
	}

	public String getIssueUrl() {
		return issueUrl;
	}

	public void setIssueUrl(String issueUrl) {
		this.issueUrl = issueUrl;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(issueId, issueTitle, issueUrl);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof GmudIssueReference))
			return false;
		GmudIssueReference other = (GmudIssueReference) obj;
		return Objects.equals(issueId, other.issueId) && Objects.equals(issueTitle, other.issueTitle)
				&& Objects.equals(issueUrl, other.issueUrl);
	}

	@Override
	public String toString() {
		return String.format("GmudIssueReference [issueId=%s, issueTitle=%s]", issueId, issueTitle);
	}

        
}
