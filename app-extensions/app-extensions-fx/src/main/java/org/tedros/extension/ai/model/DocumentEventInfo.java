package org.tedros.extension.ai.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentEventInfo {
	
	private String title;
	
	private String description;
	
	private String content;
	
	private String dateEvent;
	
	public List<ContactInfo> contacts;

}
