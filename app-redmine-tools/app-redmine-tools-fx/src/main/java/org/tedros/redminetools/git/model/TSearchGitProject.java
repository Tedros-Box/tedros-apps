package org.tedros.redminetools.git.model;

import com.fasterxml.jackson.annotation.JsonClassDescription;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonClassDescription("Complete Redmine issue (ticket) with core fields, relations, history and custom fields")
public class TSearchGitProject {
	
	private String name;

}
