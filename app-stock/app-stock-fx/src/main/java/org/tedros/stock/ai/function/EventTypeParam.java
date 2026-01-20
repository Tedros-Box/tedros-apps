/**
 * 
 */
package org.tedros.stock.ai.function;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonClassDescription("The Inventory event fields")
public class EventTypeParam {
	
	@JsonPropertyDescription("the code")
	private String code;
	
	@JsonPropertyDescription("the name")
	private String name;
	
	@JsonPropertyDescription("the description")
	private String description;
}
