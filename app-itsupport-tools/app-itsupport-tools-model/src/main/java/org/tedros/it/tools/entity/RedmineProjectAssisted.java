package org.tedros.it.tools.entity;

import java.util.Objects;

import org.tedros.server.entity.TEntity;

public class RedmineProjectAssisted extends TEntity {
	
	private static final long serialVersionUID = -5944390693198614163L;
	
	private String projectJson;
	
	private String aiAnalysis;

	public String getProjectJson() {
		return projectJson;
	}

	public void setProjectJson(String projectJson) {
		this.projectJson = projectJson;
	}

	public String getAiAnalysis() {
		return aiAnalysis;
	}

	public void setAiAnalysis(String aiAnalysis) {
		this.aiAnalysis = aiAnalysis;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(aiAnalysis, projectJson);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof RedmineProjectAssisted))
			return false;
		RedmineProjectAssisted other = (RedmineProjectAssisted) obj;
		return Objects.equals(aiAnalysis, other.aiAnalysis) && Objects.equals(projectJson, other.projectJson);
	}

}
