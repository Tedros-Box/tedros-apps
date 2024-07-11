/**
 * 
 */
package org.tedros.person.ai.function;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * @author Davis Gordon
 *
 */
@JsonClassDescription("The person attributes to search")
public class Search {
	
	@JsonPropertyDescription("the person name")
	private String name;
	
	@JsonPropertyDescription("the person contact (email address, phone number and others)")
	private String contact;
	
	@JsonPropertyDescription("the person document (ID nukber, Fiscal number and others")
	private String document;
	
	@JsonPropertyDescription("the person address location")
	private Location location;
	
	@JsonPropertyDescription("the person classification. Acceptable values: "
			+ "LEGAL_PERSON, NATURAL_PERSON, EMPLOYEE, " + 
			"CUSTOMER, CLIENT_COMPANY, MEMBER, PHILANTHROPE, VOLUNTARY, ALL")
	private Classification classification;

	/**
	 * 
	 */
	public Search() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the contact
	 */
	public String getContact() {
		return contact;
	}

	/**
	 * @param contact the contact to set
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}

	/**
	 * @return the document
	 */
	public String getDocument() {
		return document;
	}

	/**
	 * @param document the document to set
	 */
	public void setDocument(String document) {
		this.document = document;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * @return the classification
	 */
	public Classification getClassification() {
		return classification;
	}

	/**
	 * @param classification the classification to set
	 */
	public void setClassification(Classification classification) {
		this.classification = classification;
	}

}
