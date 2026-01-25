package org.tedros.extension.ai.model;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonClassDescription("Document search parameters")
public class DocumentSearchParam {

	@JsonPropertyDescription("the document id")
	private Long id;
	
	@JsonPropertyDescription("the document code")
	private String code;
	
	@JsonPropertyDescription("part of the document name to search")
	private String name;
}
