package org.tedros.redminetools.model;

import java.util.List;

import org.tedros.server.entity.TEntity;

public class CustomField extends TEntity {
	
	private static final long serialVersionUID = 1L;

	private String name;
	
	private String value;
	
	private Boolean multiple;
	
	private List<String> values;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Boolean getMultiple() {
		return multiple;
	}

	public void setMultiple(Boolean multiple) {
		this.multiple = multiple;
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
