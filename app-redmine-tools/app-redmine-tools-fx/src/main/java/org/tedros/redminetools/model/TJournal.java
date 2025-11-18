package org.tedros.redminetools.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("Single history entry (journal) for a Redmine issue - comment + field changes")
public class TJournal {

    @JsonPropertyDescription("Unique journal entry ID")
    private Integer id;

    @JsonPropertyDescription("Comment/notes added in this update")
    private String notes;

    @JsonPropertyDescription("User who made the update/comment")
    private TRedmineUser user;

    @JsonPropertyDescription("Timestamp of the update")
    private Date createdOn;

    @JsonPropertyDescription("List of field changes (status, assignee, custom fields, etc.)")
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
