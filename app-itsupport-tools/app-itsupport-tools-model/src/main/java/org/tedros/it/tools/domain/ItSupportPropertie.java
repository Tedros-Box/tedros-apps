/**
 * 
 */
package org.tedros.it.tools.domain;

/**
 * @author Davis Gordon
 *
 */
public enum ItSupportPropertie {
	
	ISSUE_TOOL 	("issue.tracker.tool", "The issue tool to be used, JIRA or REDMINE"),
	JIRA_KEY 	("jira.api.key", "The Jira api key with administrator permission"),
	JIRA_URL 	("jira.url","The Jira url path"),
	REDMINE_KEY ("redmine.api.key", "The redmine api key with administrator permission"),
	REDMINE_URL ("redmine.url","The redmine url path"),
	GITLAB_KEY 	("gitlab.api.key", "The Gitlab api key"),
	GITLAB_URL 	("gitlab.url", "The Gitlab url path");
	
	private String value;
	private String description;
	
	private ItSupportPropertie(String v, String description) {
		this.value = v;
		this.description = description;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
}
