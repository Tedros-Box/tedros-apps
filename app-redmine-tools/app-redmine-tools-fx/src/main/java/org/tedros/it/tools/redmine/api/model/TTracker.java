package org.tedros.it.tools.redmine.api.model;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("Redmine issue tracker/type (Bug, Feature, Task, Support, etc.)")
public class TTracker {

    @JsonPropertyDescription("Unique tracker ID")
    private Integer id;

    @JsonPropertyDescription("Tracker name (e.g. Bug, Feature Request, Task)")
    private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
