package org.tedros.redminetools.model;

import org.tedros.server.entity.TEntity;

public class JournalDetail extends TEntity {

	private static final long serialVersionUID = 1L;

	private String newValue;
	
    private String name;
    
    private String property;
    
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
