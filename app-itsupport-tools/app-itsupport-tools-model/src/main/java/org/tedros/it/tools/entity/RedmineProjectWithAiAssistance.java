/**
 * 
 */
package org.tedros.it.tools.entity;

import java.util.List;

import org.tedros.server.entity.TEntity;

/**
 * 
 */
public class RedmineProjectWithAiAssistance extends TEntity {

	private static final long serialVersionUID = -8282133881936196760L;
	
	private String prompt;
	
	private List<RedmineProjectAssisted> projectsAssisted;

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public List<RedmineProjectAssisted> getProjectsAssisted() {
		return projectsAssisted;
	}

	public void setProjectsAssisted(List<RedmineProjectAssisted> projectsAssisted) {
		this.projectsAssisted = projectsAssisted;
	}
	
	

}
