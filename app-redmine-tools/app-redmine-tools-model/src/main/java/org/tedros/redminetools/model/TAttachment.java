package org.tedros.redminetools.model;

import java.util.Date;

import org.tedros.server.entity.TEntity;

public class TAttachment extends TEntity {
	
	
	private static final long serialVersionUID = -1747906306442723211L;
	
	private String fileName;
	
	private Long fileSize;
	
	private String contentType;
	
	private String contentURL;
	
	private String description;
	
	private Date createdOn;
	
	private TRedmineUser author;
	
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
}
