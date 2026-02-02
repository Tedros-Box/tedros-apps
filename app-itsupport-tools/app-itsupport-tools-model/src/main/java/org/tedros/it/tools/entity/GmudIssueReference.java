package org.tedros.it.tools.entity;

import java.util.Objects;

import org.tedros.it.tools.domain.DomainSchema;
import org.tedros.it.tools.domain.DomainTables;
import org.tedros.server.entity.TEntity;
import org.tedros.server.entity.TVersionEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = DomainTables.GMUD_ISSUE_REFERENCE, schema = DomainSchema.schema)
public class GmudIssueReference extends TVersionEntity {

    private static final long serialVersionUID = 4246280208687191618L;

	@Column(nullable = false)
    private Integer issueId;

	@Column(length = 200, nullable = false)
    private String issueTitle;
	
	public Integer getIssueId() {
		return issueId;
	}

	public void setIssueId(Integer issueId) {
		this.issueId = issueId;
	}

	public String getIssueTitle() {
		return issueTitle;
	}

	public void setIssueTitle(String issueTitle) {
		this.issueTitle = issueTitle;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(issueId, issueTitle);
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
		return Objects.equals(issueId, other.issueId) && Objects.equals(issueTitle, other.issueTitle);
	}

	@Override
	public String toString() {
		return String.format("GmudIssueReference [issueId=%s, issueTitle=%s]", issueId, issueTitle);
	}

        
}
