package org.tedros.it.tools.redmine.ai.function;

import org.apache.commons.lang3.StringUtils;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.controller.TPropertieController;
import org.tedros.core.controller.TUserPropertieController;
import org.tedros.core.security.model.TUser;
import org.tedros.core.service.remote.TEjbServiceLocator;
import org.tedros.it.tools.domain.ItSupportPropertie;
import org.tedros.server.result.TResult;

public class RedmineApiPropertyUtil {
	
	private String redmineKey; 
	private String redmineUrl;
	
	private static RedmineApiPropertyUtil instance;
	
	static {
		String redmineKey;
		String redmineUrl;
		
		
		try(TEjbServiceLocator loc = TEjbServiceLocator.getInstance()) {
			TPropertieController propertieController = loc.lookup(TPropertieController.JNDI_NAME);
			TResult<String> urlResult = propertieController.getValue(TedrosContext.getLoggedUser().getAccessToken(), ItSupportPropertie.REDMINE_URL.getValue());
			
			if(urlResult.isSuccess()) {
				redmineUrl = urlResult.getValue();
				
				if(StringUtils.isBlank(redmineUrl)) {
					throw new RuntimeException("Nenhuma URL para o redmine foi configurada no sistema!");
				}
				
			}else{
				throw new RuntimeException("Não foi possivel recuperar o parametro REDMINE_URL nas propriedades do sistema!");
			}
			
			TUser user = TedrosContext.getLoggedUser();
			TUserPropertieController userPropertieController = loc.lookup(TUserPropertieController.JNDI_NAME);
			TResult<String> keyResult = userPropertieController
					.getValue(user.getAccessToken(), user.getId(), ItSupportPropertie.REDMINE_KEY.getValue());
			
			if(keyResult.isSuccess()) {
				redmineKey = keyResult.getValue();
				
				if(StringUtils.isBlank(redmineKey)) {
					throw new RuntimeException("O usuario logado precisa configurar a api key do redmine nas propriedades do usuario!");
				}
				
				instance = new RedmineApiPropertyUtil(redmineKey, redmineUrl);
				
			}else{
				throw new RuntimeException("Não foi possivel recuperar o parametro REDMINE_KEY nas propriedas do usuario logado!");
			}
			
			
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static RedmineApiPropertyUtil getInstance() {
		return instance;
	}

	private RedmineApiPropertyUtil(String redmineKey, String redmineUrl) {
		this.redmineKey = redmineKey;
		this.redmineUrl = redmineUrl;
	}
	
	public String getRedmineKey() {
		return redmineKey;
	}
	
	public String getRedmineUrl() {
		return redmineUrl;
	}

}
