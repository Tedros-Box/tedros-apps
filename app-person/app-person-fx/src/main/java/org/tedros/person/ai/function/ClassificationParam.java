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
@JsonClassDescription("the person classification")
public class ClassificationParam {
	
	@JsonPropertyDescription("[NOT NULL] Acceptable values: "
			+ "LEGAL_PERSON, NATURAL_PERSON, EMPLOYEE, " + 
			"CUSTOMER, CLIENT_COMPANY, MEMBER, PHILANTHROPE, VOLUNTARY, ALL")
	private Classification classification;

	/**
	 * 
	 */
	public ClassificationParam() {
	
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
