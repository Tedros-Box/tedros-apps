package org.tedros.redminetools.model;

import org.tedros.server.entity.TEntity;

public class Changeset extends TEntity{
	
	private static final long serialVersionUID = 1L;

	private String revision;
	
	private User user;
	
	private String comments;
	
	private String committedOn;

	public String getRevision() {
		return revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getCommittedOn() {
		return committedOn;
	}

	public void setCommittedOn(String committedOn) {
		this.committedOn = committedOn;
	}
	
	@Override
	public String toString() {
		return revision;
	}
	
}
