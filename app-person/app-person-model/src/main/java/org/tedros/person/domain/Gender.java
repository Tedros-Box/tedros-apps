/**
 * 
 */
package org.tedros.person.domain;

/**
 * @author Davis Gordon
 *
 */
public enum Gender {

	MASCULINE ("#{label.masculine}"),
	FEMININE ("#{label.feminine}"),
	NEUTER ("#{label.neuter}"),
	COMMON ("#{label.common}"),
	OTHER ("#{label.other}");
	
	private String value;

	private Gender(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
