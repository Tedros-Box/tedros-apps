package org.tedros.it.tools.redmine.api.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("Repository commit/changeset linked to a Redmine issue")
public class TChangeset {

    @JsonPropertyDescription("Internal Redmine changeset ID")
    private Integer id;

    @JsonPropertyDescription("VCS revision (e.g. Git SHA, SVN revision number)")
    private String revision;

    @JsonPropertyDescription("User who committed the change")
    private TRedmineUser user;

    @JsonPropertyDescription("Commit message / comments")
    private String comments;

    @JsonPropertyDescription("Commit timestamp")
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
