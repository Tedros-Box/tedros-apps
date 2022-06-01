/**
 * 
 */
package com.tedros.extension.model;

/**
 * @author Davis Gordon
 *
 */
public enum ContactType {

	EMAIL("Email"), 
	FAX("Fax"), 
	PHONE("#{label.phone}"), 
	OTHER("#{label.other}");
	
	private String value;
	
	private ContactType(String v) {
		this.value = v;
	}

	public String getValue() {
		return value;
	}
}
