package org.tedros.redminetools.model;

import org.tedros.server.entity.TEntity;

public class RedmineProject extends TEntity {
	
	private static final long serialVersionUID = -1747906306442723211L;
	
	private String homepage;
	
	private String identifier;
	
	private String name;
	
	private String description;

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
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
	
	@Override
	public String toString() {
		return name;
	}
}
