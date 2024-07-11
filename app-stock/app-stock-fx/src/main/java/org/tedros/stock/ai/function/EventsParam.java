/**
 * 
 */
package org.tedros.stock.ai.function;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * 
 */

public class EventsParam {
	

	@JsonPropertyDescription("A list of types")
	@JsonProperty(required = true)
	private List<EventTypeParam> events;

	public List<EventTypeParam> getEvents() {
		return events;
	}

	public void setEvents(List<EventTypeParam> events) {
		this.events = events;
	}
	

}
