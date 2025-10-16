package org.tedros.redminetools.model;

import java.util.List;

import org.tedros.server.entity.TEntity;

public class TRemineRole extends TEntity {
	
	private static final long serialVersionUID = 1L;

	private String name;
	
	private Boolean inherited;
	
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

}
