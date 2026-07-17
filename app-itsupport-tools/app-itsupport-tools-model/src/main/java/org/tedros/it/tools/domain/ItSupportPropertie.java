/**
 * 
 */
package org.tedros.it.tools.domain;

import org.tedros.common.domain.TType;

/**
 * @author Davis Gordon
 *
 */
public enum ItSupportPropertie {
	
	ISSUE_TOOL 	("issue.tracker.tool", "The issue tool to be used, JIRA or REDMINE", TType.SYSTEM),
	JIRA_KEY 	("jira.api.key", "The Jira user api key", TType.USER),
	JIRA_URL 	("jira.url","The Jira url path", TType.SYSTEM),
	REDMINE_KEY ("redmine.api.key", "The redmine user api key", TType.USER),
	REDMINE_URL ("redmine.url","The redmine url path", TType.SYSTEM),
	GITLAB_KEY 	("gitlab.api.key", "The Gitlab user api key", TType.USER),
	GITLAB_URL 	("gitlab.url", "The Gitlab url path", TType.SYSTEM);
	
	private String value;
	private String description;
	private TType type;
	
	private ItSupportPropertie(String v, String description, TType type) {
		this.value = v;
		this.description = description;
		this.type = type;
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

	public TType getType() {
		return type;
	}
}
