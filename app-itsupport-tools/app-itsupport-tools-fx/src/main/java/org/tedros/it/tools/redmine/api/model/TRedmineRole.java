package org.tedros.it.tools.redmine.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("Redmine role defining permissions in a project or membership")
public class TRedmineRole {

    @JsonPropertyDescription("Unique role ID")
    private Integer id;

    @JsonPropertyDescription("Role name (e.g. Manager, Developer, Reporter)")
    private String name;

    @JsonPropertyDescription("True if role is inherited from parent project or group")
    private Boolean inherited;

    @JsonPropertyDescription("List of granted permissions (e.g. :view_issues, :add_issues, :edit_wiki_pages)")
    private List<String> permissions;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getInherited() {
		return inherited;
	}

	public void setInherited(Boolean inherited) {
		this.inherited = inherited;
	}

	public List<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
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
