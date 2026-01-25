/**
 * 
 */
package org.tedros.person.ai.function;

import org.tedros.ai.function.TRequiredProperty;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonClassDescription("The person attributes fields.")
public class PersonAttributeParam  {
	
	@JsonPropertyDescription("the code")
	private String code;
	
	@JsonPropertyDescription("the name")
	private String name;
	
	@JsonPropertyDescription("the description")
	private String description;
	
	@TRequiredProperty
	@JsonPropertyDescription("[NOT NULL] the person classification. Acceptable values: "
			+ "LEGAL_PERSON, NATURAL_PERSON, EMPLOYEE, " + 
			"CUSTOMER, CLIENT_COMPANY, MEMBER, PHILANTHROPE, VOLUNTARY")
	private Classification classification;	

}
