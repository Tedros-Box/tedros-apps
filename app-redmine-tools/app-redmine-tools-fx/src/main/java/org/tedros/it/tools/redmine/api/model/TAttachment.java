package org.tedros.it.tools.redmine.api.model;

import java.util.Date;

import org.tedros.ai.function.TRequiredProperty;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

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
		
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String filename) {
		this.fileName = filename;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long filesize) {
		this.fileSize = filesize;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContentURL() {
		return contentURL;
	}

	public void setContentURL(String contentUrl) {
		this.contentURL = contentUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public TRedmineUser getAuthor() {
		return author;
	}

	public void setAuthor(TRedmineUser author) {
		this.author = author;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return fileName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
