/**
 * 
 */
package com.tedros.person.domain;

/**
 * @author Davis Gordon
 *
 */
public enum Sex {

	MALE ("#{label.masculine}"),
	FEMALE ("#{label.feminine}");
	
	private String value;

	private Sex(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
