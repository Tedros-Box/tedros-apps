/**
 * 
 */
package org.tedros.stock.ai.function;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * 
 */
@JsonClassDescription("The Inventory event fields")
public class EventTypeParam {
	
	@JsonPropertyDescription("the code")
	private String code;
	
	@JsonPropertyDescription("the name")
	private String name;
	
	@JsonPropertyDescription("the description")
	private String description;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
