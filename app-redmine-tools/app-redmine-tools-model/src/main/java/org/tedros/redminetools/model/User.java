package org.tedros.redminetools.model;

import java.util.Date;
import java.util.List;

import org.tedros.server.entity.TEntity;

public class User extends TEntity {

	private static final long serialVersionUID = 1L;

	private String login;
	
	private String password;
	
	private String firstname;
	
	private String lastname;
	
	private String mail;
	
	private String apiKey;
	
	private Date createdOn;
	
	private Date lastLoginOn;
	
	private String authSourceId;
	
	private Integer status;
	
	private String mailNotification;
	
	private Boolean mustChangePassword;
	
	private Boolean generatePassword;
	
	private List<CustomField> customFields;
	
	private List<Membership> memberships;
	
	private List<Group> groups;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getLastLoginOn() {
		return lastLoginOn;
	}

	public void setLastLoginOn(Date lastLoginOn) {
		this.lastLoginOn = lastLoginOn;
	}

	public String getAuthSourceId() {
		return authSourceId;
	}

	public void setAuthSourceId(String authSourceId) {
		this.authSourceId = authSourceId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMailNotification() {
		return mailNotification;
	}

	public void setMailNotification(String mailNotification) {
		this.mailNotification = mailNotification;
	}

	public Boolean getMustChangePassword() {
		return mustChangePassword;
	}

	public void setMustChangePassword(Boolean mustChangePassword) {
		this.mustChangePassword = mustChangePassword;
	}

	public Boolean getGeneratePassword() {
		return generatePassword;
	}

	public void setGeneratePassword(Boolean generatePassword) {
		this.generatePassword = generatePassword;
	}

	public List<CustomField> getCustomFields() {
		return customFields;
	}

	public void setCustomFields(List<CustomField> customFields) {
		this.customFields = customFields;
	}

	public List<Membership> getMemberships() {
		return memberships;
	}

	public void setMemberships(List<Membership> memberships) {
		this.memberships = memberships;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	
	@Override
	public String toString() {
		return login;
	}
	
}
