/**
 * 
 */
package org.tedros.extension.ai.model;

import java.util.List;

import org.tedros.ai.function.TRequiredProperty;

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
public class ListItemParam {
	
	@TRequiredProperty
	@JsonPropertyDescription("A list of items")
	private List<CodeNameDescParam> items;

}
