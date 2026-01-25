/**
 * 
 */
package org.tedros.person.ai.function;

import java.util.List;

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
@JsonClassDescription("The person attributes list")
public class PersonAttributeParamList  {
	
	@JsonPropertyDescription("the person attributes")
	private List<PersonAttributeParam> attributes;
	
		

}
