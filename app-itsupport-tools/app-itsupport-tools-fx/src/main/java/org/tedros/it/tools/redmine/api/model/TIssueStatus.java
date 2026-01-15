package org.tedros.it.tools.redmine.api.model;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("Represents the status of an issue in Redmine.")
public class TIssueStatus {
	
	@JsonPropertyDescription("Unique issue status ID in Redmine")
	private Integer id;
	
	@JsonPropertyDescription("Issue status name")
    private String name;
	
	@JsonPropertyDescription("Indicates if this is the default status for new issues")
    private boolean defaultStatus;
	
	@JsonPropertyDescription("Indicates if this status represents a closed issue")
    private boolean closed;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDefaultStatus() {
		return defaultStatus;
	}

	public void setDefaultStatus(boolean defaultStatus) {
		this.defaultStatus = defaultStatus;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
