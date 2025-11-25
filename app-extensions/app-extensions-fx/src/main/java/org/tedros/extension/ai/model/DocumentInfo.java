package org.tedros.extension.ai.model;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonClassDescription("Document information")
public class DocumentInfo {
	
	@JsonPropertyDescription("the document id")
	private Long id;
	
	@JsonPropertyDescription("the document code")
	private String code;
	
	@JsonPropertyDescription("the document name")
	private String name;
	
	@JsonPropertyDescription("additional information about the document")
	private String additionalInfo;
	
	@JsonPropertyDescription("the document type information")
	private DocumentTypeInfo type;
	
	@JsonPropertyDescription("the document status information")
	private DomainInfo status;
	
	@JsonPropertyDescription("the document observation")
	private String observation;
	
	@JsonPropertyDescription("the document content")
	private String content; 
	
	@JsonPropertyDescription("the digital file information")
	private FileInfo file;
	
	@JsonPropertyDescription("the system view name who created the document")
	private String createdByViewName;
	
	@JsonPropertyDescription("the document creation date")
	private String createdDate;
	
	@JsonPropertyDescription("the document last update date")
	private String lastUpdateDate;

}
