package org.tedros.it.tools.redmine.api.model;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("User watching a Redmine issue for notifications")
public class TWatcher {

    @JsonPropertyDescription("User ID that is watching the issue")
    private Integer id;

    @JsonPropertyDescription("Full name of the watching user")
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
