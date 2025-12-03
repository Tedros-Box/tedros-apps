package org.tedros.it.tools.gitlab.ai.function;

import org.apache.commons.lang3.StringUtils;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.controller.TPropertieController;
import org.tedros.core.service.remote.TEjbServiceLocator;
import org.tedros.redminetools.domain.RedminePropertie;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;

public class GitLabApiPropertyUtil {
	
	private String gitlabKey; 
	private String gitlabUrl;
	
	
	private static GitLabApiPropertyUtil instance;
	
	static {
		String key;
		String url;
		
		TEjbServiceLocator loc = TEjbServiceLocator.getInstance();
		try {
			TPropertieController serv = loc.lookup(TPropertieController.JNDI_NAME);
			TResult<String> keyResult = serv.getValue(TedrosContext.getLoggedUser().getAccessToken(), RedminePropertie.GITLAB_KEY.getValue());
			TResult<String> urlResult = serv.getValue(TedrosContext.getLoggedUser().getAccessToken(), RedminePropertie.GITLAB_URL.getValue());			
			
			if(keyResult.getState().equals(TState.SUCCESS) && urlResult.getState().equals(TState.SUCCESS)) {
				key = keyResult.getValue();
				url = urlResult.getValue();
				
				instance = new GitLabApiPropertyUtil(key, url);
				
			}else{
				throw new RuntimeException("Não foi possivel recuperar os parametros do GitLab!");
			}
			
			if(StringUtils.isBlank(key) || StringUtils.isBlank(url)) {
				throw new RuntimeException("Alguns dos parametros do GitLab não foram devidamente configurados!");
			}
			
		}catch (Exception e) {
			throw new RuntimeException(e);
		}finally {
			loc.close();
		}
	}
	
	public static GitLabApiPropertyUtil getInstance() {
		return instance;
	}

	private GitLabApiPropertyUtil(String redmineKey, String redmineUrl) {
		this.gitlabKey = redmineKey;
		this.gitlabUrl = redmineUrl;	
	}

	public String getGitlabKey() {
		return gitlabKey;
	}
	
	public String getGitlabUrl() {
		return gitlabUrl;
	}

}
