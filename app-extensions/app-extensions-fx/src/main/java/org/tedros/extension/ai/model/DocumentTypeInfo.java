package org.tedros.extension.ai.model;

import org.tedros.extension.model.DocType;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@JsonClassDescription("Document type information")
public class DocumentTypeInfo extends DomainInfo {

	@JsonPropertyDescription("the document type")
	private DocType documentType;
	
}
