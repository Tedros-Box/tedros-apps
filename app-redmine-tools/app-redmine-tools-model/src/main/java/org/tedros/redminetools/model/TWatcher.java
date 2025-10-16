package org.tedros.redminetools.model;

import org.tedros.server.entity.TEntity;

public class TWatcher extends TEntity{
	
	private static final long serialVersionUID = 1L;
	
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
	
}
