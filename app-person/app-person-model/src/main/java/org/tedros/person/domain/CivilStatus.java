/**
 * 
 */
package org.tedros.person.domain;

/**
 * @author Davis Gordon
 *
 */
public enum CivilStatus {

	SINGLE ("#{label.single}"),
	MARRIED ("#{label.married}"),
	DIVORCED ("#{label.divorced}"),
	WIDOWED ("#{label.widowed}"),
	SEPARATED ("#{label.separated}");
	
	private String value;

	private CivilStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
