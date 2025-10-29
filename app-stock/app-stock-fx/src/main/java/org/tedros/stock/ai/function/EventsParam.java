/**
 * 
 */
package org.tedros.stock.ai.function;

import java.util.List;

import org.tedros.ai.function.TRequiredProperty;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * 
 */

public class EventsParam {

	@TRequiredProperty
	@JsonPropertyDescription("A list of types")
	private List<EventTypeParam> events;

	public List<EventTypeParam> getEvents() {
		return events;
	}

	public void setEvents(List<EventTypeParam> events) {
		this.events = events;
	}
	

}
