package org.tedros.extension.ai.model;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("Identifier parameters for file retrieval.")
public record FileParam(
		@JsonPropertyDescription("The unique numeric database ID (Long) of the file. This must be an existing ID found in previous context/search.")
		Long id) {

}
