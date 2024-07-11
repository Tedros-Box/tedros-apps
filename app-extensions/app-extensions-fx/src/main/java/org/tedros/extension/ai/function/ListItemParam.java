/**
 * 
 */
package org.tedros.extension.ai.function;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * 
 */

public class ListItemParam {
	
	@JsonPropertyDescription("A list of items")
	@JsonProperty(required = true)
	private List<CodeNameDescParam> items;

	public List<CodeNameDescParam> getItems() {
		return items;
	}

	public void setItems(List<CodeNameDescParam> items) {
		this.items = items;
	}

	
	

}
