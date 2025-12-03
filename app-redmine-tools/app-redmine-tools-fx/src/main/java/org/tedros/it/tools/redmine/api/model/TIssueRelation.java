package org.tedros.it.tools.redmine.api.model;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("Relation between two Redmine issues (blocks, precedes, relates, etc.)")
public class TIssueRelation {

    @JsonPropertyDescription("Unique relation ID")
    private Integer id;

    @JsonPropertyDescription("Source issue ID (this issue)")
    private Integer issueId;

    @JsonPropertyDescription("Target issue ID (linked issue)")
    private Integer issueToId;

    @JsonPropertyDescription("Relation type: relates, blocks, blocked, precedes, follows, duplicates, duplicated, copied_to, copied_from")
    private String relationType;

    @JsonPropertyDescription("Delay in days (used only for precedes/follows relations)")
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

	public String getType() {
		return relationType;
	}

	public void setType(String relationType) {
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}


