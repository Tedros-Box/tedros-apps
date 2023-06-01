/**
 * 
 */
package org.tedros.extension.model;

/**
 * @author Davis Gordon
 *
 */
public enum DocType {
	
	PERSON_FISCAL_ID_NUMBER ("#{label.person.fiscal.id.number}"),
	COMPANY_FISCAL_ID_NUMBER ("#{label.company.fiscal.id.number}"),
	PERSON_ID_NUMBER ("#{label.person.id.number}"),
	COMPANY_ID_NUMBER ("#{label.company.id.number}"),
	OTHER ("#{label.other}");

	private String value;

	private DocType(String value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
}
