package org.tedros.redminetools.model;

import java.util.Date;
import java.util.List;

import org.tedros.server.entity.TEntity;

public class TRedmineUser extends TEntity {

	private static final long serialVersionUID = 1L;

	private String login;
	
	private String password;
	
	private String firstName;
	
	private String lastName;
	
	private String mail;
	
	private String apiKey;
	
	private Date createdOn;
	
	private Date lastLoginOn;
	
	private Integer authSourceId;
	
	private Integer status;
	
	private String mailNotification;
	
	private Boolean mustChangePassword;
	
	private Boolean generatePassword;
	
	private List<TCustomField> customFields;
	
	private List<TMembership> memberships;
	
	private List<TGroup> groups;

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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstname) {
		this.firstName = firstname;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastname) {
		this.lastName = lastname;
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

	public Integer getAuthSourceId() {
		return authSourceId;
	}

	public void setAuthSourceId(Integer authSourceId) {
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

	public List<TCustomField> getCustomFields() {
		return customFields;
	}

	public void setCustomFields(List<TCustomField> customFields) {
		this.customFields = customFields;
	}

	public List<TMembership> getMemberships() {
		return memberships;
	}

	public void setMemberships(List<TMembership> memberships) {
		this.memberships = memberships;
	}

	public List<TGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<TGroup> groups) {
		this.groups = groups;
	}
	
	@Override
	public String toString() {
		return login;
	}
	
}
