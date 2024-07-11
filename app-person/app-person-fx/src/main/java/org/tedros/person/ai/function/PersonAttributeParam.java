/**
 * 
 */
package org.tedros.person.ai.function;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * 
 */
@JsonClassDescription("The person attributes fields.")
public class PersonAttributeParam  {
	
	@JsonPropertyDescription("the code")
	private String code;
	
	@JsonPropertyDescription("the name")
	private String name;
	
	@JsonPropertyDescription("the description")
	private String description;
	
	@JsonPropertyDescription("[NOT NULL] the person classification. Acceptable values: "
			+ "LEGAL_PERSON, NATURAL_PERSON, EMPLOYEE, " + 
			"CUSTOMER, CLIENT_COMPANY, MEMBER, PHILANTHROPE, VOLUNTARY")
	private Classification classification;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Classification getClassification() {
		return classification;
	}

	public void setClassification(Classification classification) {
		this.classification = classification;
	}

}
