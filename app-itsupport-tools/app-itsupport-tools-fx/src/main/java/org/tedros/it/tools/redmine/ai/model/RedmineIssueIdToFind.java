package org.tedros.it.tools.redmine.ai.model;

import org.tedros.ai.function.TRequiredProperty;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonClassDescription("Represents a filter for Redmine issues by issue id")
public class RedmineIssueIdToFind {
	
	@TRequiredProperty
	@JsonPropertyDescription("the issue id")
	private Integer issueId;
		        
}

