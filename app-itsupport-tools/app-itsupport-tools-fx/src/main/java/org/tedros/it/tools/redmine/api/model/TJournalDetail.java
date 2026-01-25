package org.tedros.it.tools.redmine.api.model;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("Single field change recorded in a Redmine issue journal/history")
public class TJournalDetail {

    @JsonPropertyDescription("New value after the change (as string)")
    private String newValue;

    @JsonPropertyDescription("Field name (e.g. status, priority, custom field name)")
    private String name;

    @JsonPropertyDescription("Property type: 'attr' (standard field), 'cf_<id>' (custom field), 'attachment', 'relation'")
    private String property;

    @JsonPropertyDescription("Previous value before the change (as string)")
    private String oldValue;

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
    
	@Override
	public String toString() {
		return name;
	}    
}
