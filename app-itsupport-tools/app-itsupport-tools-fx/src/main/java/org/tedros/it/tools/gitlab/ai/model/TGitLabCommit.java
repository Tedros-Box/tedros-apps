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
@JsonClassDescription("The model to get gitlab commit by project id and commit sha")
public class TGitLabCommit {
	
	@TRequiredProperty
	@JsonPropertyDescription("The project id")
	private Long projectId;
	
	@TRequiredProperty
	@JsonPropertyDescription("The commit hash or name of a branch or tag")
	private String commitSha;
}
