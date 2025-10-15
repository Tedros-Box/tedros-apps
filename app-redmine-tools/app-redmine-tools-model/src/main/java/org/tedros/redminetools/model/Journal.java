package org.tedros.redminetools.model;

import java.util.Date;
import java.util.List;

import org.tedros.server.entity.TEntity;

public class Journal extends TEntity {
	
	private static final long serialVersionUID = 1L;
	
	private String notes;
	
	private User user;
	
	private Date createdOn;
	
	private List<JournalDetail> details;

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public List<JournalDetail> getDetails() {
		return details;
	}

	public void setDetails(List<JournalDetail> details) {
		this.details = details;
	}
	
	@Override
	public String toString() {
		return notes;
	}
	
}
