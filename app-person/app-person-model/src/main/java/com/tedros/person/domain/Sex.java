/**
 * 
 */
package com.tedros.person.domain;

/**
 * @author Davis Gordon
 *
 */
public enum Sex {

	MALE ("#{label.male}"),
	FEMALE ("#{label.female}");
	
	private String value;

	private Sex(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
