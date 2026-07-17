package org.tedros.it.tools.gitlab.ai.function;

import org.apache.commons.lang3.StringUtils;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.controller.TPropertieController;
import org.tedros.core.controller.TUserPropertieController;
import org.tedros.core.security.model.TUser;
import org.tedros.core.service.remote.TEjbServiceLocator;
import org.tedros.it.tools.domain.ItSupportPropertie;
import org.tedros.server.result.TResult;

public class GitLabApiPropertyUtil {
	
	private String gitlabKey; 
	private String gitlabUrl;
	
	private static GitLabApiPropertyUtil instance;
	
	private GitLabApiPropertyUtil(String redmineKey, String redmineUrl) {
		this.gitlabKey = redmineKey;
		this.gitlabUrl = redmineUrl;	
	}
	
	public static GitLabApiPropertyUtil getInstance() {
		if(instance==null)
			create();
		
		return instance;
	}
	
	private static void create() {
		String key;
		String url;		
		
		try(TEjbServiceLocator loc = TEjbServiceLocator.getInstance()) {
			TPropertieController propertieController = loc.lookup(TPropertieController.JNDI_NAME);
			TResult<String> urlResult = propertieController.getValue(TedrosContext.getLoggedUser().getAccessToken(), ItSupportPropertie.GITLAB_URL.getValue());			
			
			if(urlResult.isSuccess()) {
				url = urlResult.getValue();
				
				if(StringUtils.isBlank(url)) {
					throw new RuntimeException("Nenhuma URL para o gitlab foi configurada no sistema!");
				}
				
			}else{
				throw new RuntimeException("Não foi possivel recuperar o parametro GITLAB_URL nas propriedades do sistema!");
			}
			
			TUser user = TedrosContext.getLoggedUser();
			TUserPropertieController userPropertieController = loc.lookup(TUserPropertieController.JNDI_NAME);
			TResult<String> keyResult = userPropertieController
					.getValue(user.getAccessToken(), user.getId(), ItSupportPropertie.GITLAB_KEY.getValue());
			
			if(keyResult.isSuccess()) {
				key = keyResult.getValue();
				
				if(StringUtils.isBlank(key)) {
					throw new RuntimeException("O usuario logado precisa configurar a api key do gitlab nas propriedades do usuario!");
				}
				
				instance = new GitLabApiPropertyUtil(key, url);
				
			}else{
				throw new RuntimeException("Não foi possivel recuperar o parametro GITLAB_KEY nas propriedades do usuario logado!");
			}
			
			
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getGitlabKey() {
		return gitlabKey;
	}
	
	public String getGitlabUrl() {
		return gitlabUrl;
	}

}
