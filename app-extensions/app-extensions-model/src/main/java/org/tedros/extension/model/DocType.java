/**
 * 
 */
package org.tedros.extension.model;

/**
 * @author Davis Gordon
 *
 */
public enum DocType {
	
	TAX ("#{label.tax.number}"),
	ID ("#{label.id.number}"),
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
