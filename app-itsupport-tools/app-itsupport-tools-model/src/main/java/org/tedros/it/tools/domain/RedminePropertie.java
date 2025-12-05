/**
 * 
 */
package org.tedros.it.tools.domain;

/**
 * @author Davis Gordon
 *
 */
public enum RedminePropertie {
	
	REDMINE_KEY ("redmine.api.key", "The redmine api key with administrator permission"),
	REDMINE_URL ("redmine.url","The redmine url path"),
	GITLAB_KEY ("gitlab.api.key", "The Gitlab api key"),
	GITLAB_URL ("gitlab.url", "The Gitlab url path");
	
	private String value;
	private String description;
	
	private RedminePropertie(String v, String description) {
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
