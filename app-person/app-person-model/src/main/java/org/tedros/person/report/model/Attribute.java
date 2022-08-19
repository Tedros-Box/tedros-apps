/**
 * 
 */
package org.tedros.person.report.model;

import java.io.Serializable;

import org.tedros.person.model.PersonAttributes;

/**
 * @author Davis Gordon
 *
 */
public class Attribute implements Serializable {

	private static final long serialVersionUID = -4996916547868627677L;

	private String name;
	
	private String value;
	
	private String description;
	
	public Attribute(PersonAttributes e) {
		this.name = e.getName();
		this.value = e.getValue();
		this.description = e.getDescription();
	}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
