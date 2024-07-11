/**
 * 
 */
package org.tedros.extension.ai.function;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * 
 */
public class CodeNameDescParam  {
	
	@JsonPropertyDescription("the item code")
	private String code;
	
	@JsonPropertyDescription("the item name")
	private String name;
	
	@JsonPropertyDescription("the item description")
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
