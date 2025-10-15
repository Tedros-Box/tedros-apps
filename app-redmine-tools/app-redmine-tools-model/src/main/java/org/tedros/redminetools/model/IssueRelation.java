package org.tedros.redminetools.model;

import org.tedros.server.entity.TEntity;

public class IssueRelation extends TEntity {
	
	private static final long serialVersionUID = 1L;

	private Integer issueId;
	
	private Integer issueToId;
	
	private String relationType;
	
	private Integer delay;

	public Integer getIssueId() {
		return issueId;
	}

	public void setIssueId(Integer issueId) {
		this.issueId = issueId;
	}

	public Integer getIssueToId() {
		return issueToId;
	}

	public void setIssueToId(Integer issueToId) {
		this.issueToId = issueToId;
	}

	public String getRelationType() {
		return relationType;
	}

	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}

	public Integer getDelay() {
		return delay;
	}

	public void setDelay(Integer delay) {
		this.delay = delay;
	}
	
	@Override
	public String toString() {
		return String.format("%s - %s", issueId, issueToId);
	}
	
}


