package org.tedros.redminetools.model;

import java.util.Date;

import org.tedros.server.entity.TEntity;

public class Attachment extends TEntity {
	
	
	private static final long serialVersionUID = -1747906306442723211L;
	
	private String filename;
	
	private Long filesize;
	
	private String contentType;
	
	private String contentUrl;
	
	private String description;
	
	private Date createdOn;
	
	private User author;
	
	private String token;
		
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Long getFilesize() {
		return filesize;
	}

	public void setFilesize(Long filesize) {
		this.filesize = filesize;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContentUrl() {
		return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
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

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
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
		return filename;
	}
}
