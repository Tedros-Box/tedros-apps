package org.tedros.redminetools.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("Redmine custom field value attached to an issue or project")
public class TCustomField {

    @JsonPropertyDescription("Custom field definition ID")
    private Integer id;

    @JsonPropertyDescription("Custom field name (e.g. 'Resolution', 'Environment')")
    private String name;

    @JsonPropertyDescription("Single value (for non-multiple fields)")
    private String value;

    @JsonPropertyDescription("True if field supports multiple values")
    private Boolean multiple;

    @JsonPropertyDescription("List of selected values (for multi-select/list fields)")
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
