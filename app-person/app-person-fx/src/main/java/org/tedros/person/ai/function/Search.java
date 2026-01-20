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
@JsonClassDescription("Criteria for searching a person or organization. Fill at least one field to perform a search.")
public class Search {
	
	@JsonPropertyDescription("The full or partial name of the person or company.")
	private String name;
	
	@JsonPropertyDescription("Contact information, such as email address, phone number, or social media handle.")
	private String contact;
	
	@JsonPropertyDescription("Official identification document (e.g., Tax ID, SSN, Passport number, Driver's License).")
	private String document;
	
	@JsonPropertyDescription("Filter by geographic location address.")
	private Location location;
	
	// Aprimorei a descrição para ajudar a IA a escolher o tipo certo baseada na intenção do usuário
	@JsonPropertyDescription("The entity type. Use 'NATURAL_PERSON' for humans, 'LEGAL_PERSON' or 'CLIENT_COMPANY' for organizations. "
			+ "Other values: EMPLOYEE, CUSTOMER, MEMBER, PHILANTHROPE, VOLUNTARY. Default is 'ALL'.")
	private Classification classification;

	/**
	 * 
	 */
	public Search() {
		
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
