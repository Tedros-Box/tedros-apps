package org.tedros.it.tools.redmine.api.model;

import java.util.Date;

import org.tedros.ai.function.TRequiredProperty;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonClassDescription("File attachment linked to a Redmine issue, wiki page or other entity")
public class TAttachment {
	
	@TRequiredProperty
    @JsonPropertyDescription("Unique attachment ID in Redmine")
    private Integer id;
	
    @JsonPropertyDescription("Original filename with extension")
    private String fileName;

    @JsonPropertyDescription("File size in bytes")
    private Long fileSize;

    @JsonPropertyDescription("MIME type (e.g. application/pdf, image/png)")
    private String contentType;

    @TRequiredProperty
    @JsonPropertyDescription("Direct download URL of the file")
    private String contentURL;

    @JsonPropertyDescription("Optional description or caption")
    private String description;

    @JsonPropertyDescription("Upload timestamp")
    private Date createdOn;

    @JsonPropertyDescription("User who uploaded the file")
    private TRedmineUser author;

    @JsonPropertyDescription("Download token for non-logged-in access")
    private String token;
	
	@Override
	public String toString() {
		return fileName;
	}
	
}
