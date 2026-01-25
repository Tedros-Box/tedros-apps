package org.tedros.extension.ai.model;

import java.util.List;

import org.tedros.extension.model.ContactType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactInfo {
	
	private String name;

	private ContactType type;
	
	private String value;
	
	private String observation;
	
	private List<ContactInfo> contacts;

}
