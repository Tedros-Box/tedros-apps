package org.tedros.it.tools.gitlab.model;

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
@JsonClassDescription("The model to search git project by name")
public class TSearchGitProject {
	
	@JsonPropertyDescription("The git project name to be searched")
	private String name;

}
