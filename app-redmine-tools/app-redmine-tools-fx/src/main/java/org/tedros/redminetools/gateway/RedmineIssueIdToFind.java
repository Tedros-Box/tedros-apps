package org.tedros.redminetools.gateway;

import org.tedros.ai.function.TRequiredProperty;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("Represents a filter for Redmine issues by issue id")
public class RedmineIssueIdToFind {
		
	
	@TRequiredProperty
	@JsonPropertyDescription("the issue id")
	private Integer issueId;
	
	public Integer getIssueId() {
		return issueId;
	}
	
	public void setIssueId(Integer issueId) {
		this.issueId = issueId;
	}	
		        
}

