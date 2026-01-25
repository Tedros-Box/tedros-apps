package org.tedros.it.tools.gitlab.ai.function;

import org.slf4j.Logger;
import org.tedros.it.tools.gitlab.gateway.GitLabGateway;
import org.tedros.util.TLoggerUtil;

public final class GitLabGatewayFactory {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(GitLabGatewayFactory.class); 
	
	private static GitLabGateway gateway;
	
	private GitLabGatewayFactory() {
		
	}
	
	private static void createGateway() {
		if(gateway==null) {
			try {
				GitLabApiPropertyUtil instance = GitLabApiPropertyUtil.getInstance();
				gateway = GitLabGateway.getInstance(instance.getGitlabUrl(), instance.getGitlabKey());
			}catch (Exception e) {
				LOGGER.error("Error initializing GitLab gateway: {}", e.getMessage(), e);
			}
		}
	}
	
	public static GitLabGateway getGateway() {
		if(gateway==null)
			createGateway();
		return gateway;
	}

}
