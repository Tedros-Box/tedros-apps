package org.tedros.it.tools.gitlab.ai.model;

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
@JsonClassDescription("The model to search git merge requests")
public class TGitLabMergeRequest {
	
	@TRequiredProperty
	@JsonPropertyDescription("The git project id")
	private Long projectId;
	
	@TRequiredProperty
	@JsonPropertyDescription("The git merge request iid")
	private Long mergeRequestIid;

}
