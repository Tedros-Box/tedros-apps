package org.tedros.it.tools.gitlab.ai.function;

import org.slf4j.Logger;
import org.tedros.it.tools.gitlab.gateway.GitLabGateway;
import org.tedros.util.TLoggerUtil;

public final class GitLabGatewayUtil {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(GitLabGatewayUtil.class); 
	
	private static GitLabGateway gateway;
	
	private GitLabGatewayUtil() {
		
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
	
	public static GitLabGateway gateway() {
		if(gateway==null)
			createGateway();
		return gateway;
	}

}
