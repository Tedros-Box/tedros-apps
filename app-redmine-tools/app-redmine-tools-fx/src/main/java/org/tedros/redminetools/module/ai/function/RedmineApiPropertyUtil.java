package org.tedros.redminetools.module.ai.function;

import org.apache.commons.lang3.StringUtils;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.controller.TPropertieController;
import org.tedros.core.service.remote.TEjbServiceLocator;
import org.tedros.redminetools.domain.RedminePropertie;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;

public class RedmineApiPropertyUtil {
	
	private String redmineKey; 
	private String redmineUrl;
	
	private static RedmineApiPropertyUtil instance;
	
	static {
		String redmineKey;
		String redmineUrl;
		
		TEjbServiceLocator loc = TEjbServiceLocator.getInstance();
		try {
			TPropertieController serv = loc.lookup(TPropertieController.JNDI_NAME);
			TResult<String> keyResult = serv.getValue(TedrosContext.getLoggedUser().getAccessToken(), RedminePropertie.REDMINE_KEY.getValue());
			TResult<String> urlResult = serv.getValue(TedrosContext.getLoggedUser().getAccessToken(), RedminePropertie.REDMINE_URL.getValue());
			
			if(keyResult.getState().equals(TState.SUCCESS) && urlResult.getState().equals(TState.SUCCESS)) {
				redmineKey = keyResult.getValue();
				redmineUrl = urlResult.getValue();
				
				instance = new RedmineApiPropertyUtil(redmineKey, redmineUrl);
				
			}else{
				throw new RuntimeException("Não foi possivel recuperar os parametros do redmine!");
			}
			
			if(StringUtils.isBlank(redmineKey) || StringUtils.isBlank(redmineUrl)) {
				throw new RuntimeException("Alguns dos parametros do redmine não foram devidamente configurados!");
			}
			
		}catch (Exception e) {
			throw new RuntimeException(e);
		}finally {
			loc.close();
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
