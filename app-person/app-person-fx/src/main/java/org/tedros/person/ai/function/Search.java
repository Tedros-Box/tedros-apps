/**
 * 
 */
package org.tedros.person.ai.function;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Davis Gordon
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
}
