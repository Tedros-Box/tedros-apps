package org.tedros.redminetools.model;

import java.util.Date;

import org.tedros.server.entity.TEntity;

public class TChangeset extends TEntity{
	
	private static final long serialVersionUID = 1L;

	private String revision;
	
	private TRedmineUser user;
	
	private String comments;
	
	private Date committedOn;

	public String getRevision() {
		return revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}

	public TRedmineUser getUser() {
		return user;
	}

	public void setUser(TRedmineUser user) {
		this.user = user;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getCommittedOn() {
		return committedOn;
	}

	public void setCommittedOn(Date committedOn) {
		this.committedOn = committedOn;
	}
	
	@Override
	public String toString() {
		return revision;
	}
	
}
