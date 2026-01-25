/**
 * 
 */
package org.tedros.stock.ai.function;

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
public class EventsParam {

	@TRequiredProperty
	@JsonPropertyDescription("A list of types")
	private List<EventTypeParam> events;
}
