package org.tedros.redminetools.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("Complete Redmine user account with memberships, groups and settings")
public class TRedmineUser {

    @JsonPropertyDescription("Unique user ID in Redmine")
    private Integer id;

    @JsonPropertyDescription("Login username")
    private String login;

    @JsonPropertyDescription("Password (usually null or hashed - not sent in API)")
    private String password;

    @JsonPropertyDescription("First name")
    private String firstName;

    @JsonPropertyDescription("Last name")
    private String lastName;

    @JsonPropertyDescription("Email address")
    private String mail;

    @JsonPropertyDescription("User's personal API access key")
    private String apiKey;

    @JsonPropertyDescription("Account creation timestamp")
    private Date createdOn;

    @JsonPropertyDescription("Last successful login timestamp")
    private Date lastLoginOn;

    @JsonPropertyDescription("Authentication source ID (null = internal, >0 = LDAP/etc.)")
    private Integer authSourceId;

    @JsonPropertyDescription("Account status: 1=active, 2=registered, 3=locked")
    private Integer status;

    @JsonPropertyDescription("Mail notification setting: 'all','selected','only_my_events', etc.")
    private String mailNotification;

    @JsonPropertyDescription("True if user must change password on next login")
    private Boolean mustChangePassword;

    @JsonPropertyDescription("True if password should be auto-generated")
    private Boolean generatePassword;

    @JsonPropertyDescription("Custom field values for this user")
    private List<TCustomField> customFields;

    @JsonPropertyDescription("Project memberships with roles")
    private List<TMembership> memberships;

    @JsonPropertyDescription("Groups the user belongs to")
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
