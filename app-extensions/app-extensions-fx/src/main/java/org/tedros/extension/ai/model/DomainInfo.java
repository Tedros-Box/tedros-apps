package org.tedros.extension.ai.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DomainInfo {
	
	@JsonPropertyDescription("the code")
	private String code;
	
	@JsonPropertyDescription("the name")
	private String name;
	
	@JsonPropertyDescription("the description")
	private String description;
	
}
