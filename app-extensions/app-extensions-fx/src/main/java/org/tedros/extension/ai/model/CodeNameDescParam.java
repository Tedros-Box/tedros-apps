/**
 * 
 */
package org.tedros.extension.ai.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeNameDescParam  {
	
	@JsonPropertyDescription("the item code")
	private String code;
	
	@JsonPropertyDescription("the item name")
	private String name;
	
	@JsonPropertyDescription("the item description")
	private String description;
}
