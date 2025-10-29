/**
 * 
 */
package org.tedros.extension.ai.function;

import java.util.List;

import org.tedros.ai.function.TRequiredProperty;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * 
 */

public class ListItemParam {
	
	@TRequiredProperty
	@JsonPropertyDescription("A list of items")
	private List<CodeNameDescParam> items;

	public List<CodeNameDescParam> getItems() {
		return items;
	}

	public void setItems(List<CodeNameDescParam> items) {
		this.items = items;
	}

	
	

}
