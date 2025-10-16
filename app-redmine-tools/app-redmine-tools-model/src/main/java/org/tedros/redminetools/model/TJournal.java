package org.tedros.redminetools.model;

import java.util.Date;
import java.util.List;

import org.tedros.server.entity.TEntity;

public class TJournal extends TEntity {
	
	private static final long serialVersionUID = 1L;
	
	private String notes;
	
	private TRedmineUser user;
	
	private Date createdOn;
	
	private List<TJournalDetail> details;

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public TRedmineUser getUser() {
		return user;
	}

	public void setUser(TRedmineUser user) {
		this.user = user;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public List<TJournalDetail> getDetails() {
		return details;
	}

	public void setDetails(List<TJournalDetail> details) {
		this.details = details;
	}
	
	@Override
	public String toString() {
		return notes;
	}
	
}
