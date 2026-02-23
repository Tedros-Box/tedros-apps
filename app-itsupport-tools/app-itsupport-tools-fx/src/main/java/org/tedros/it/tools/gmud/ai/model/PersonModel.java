package org.tedros.it.tools.gmud.ai.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonModel {
	
	private Long id;
	private String name;
	private String email;

}
