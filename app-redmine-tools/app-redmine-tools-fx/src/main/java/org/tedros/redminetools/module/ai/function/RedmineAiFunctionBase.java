package org.tedros.redminetools.module.ai.function;

import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.tedros.ai.function.TFunction;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.controller.TPropertieController;
import org.tedros.core.service.remote.TEjbServiceLocator;
import org.tedros.redminetools.domain.RedminePropertie;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;

public abstract class RedmineAiFunctionBase<T> extends TFunction<T> {
	
	
	protected static String REDMINE_KEY; 
	protected static String REDMINE_URL;

	protected RedmineAiFunctionBase(String name, String description, Class<T> model, Function<T, Object> callback) {
		super(name, description, model, callback);
		
		TEjbServiceLocator loc = TEjbServiceLocator.getInstance();
		try {
			TPropertieController serv = loc.lookup(TPropertieController.JNDI_NAME);
			TResult<String> keyResult = serv.getValue(TedrosContext.getLoggedUser().getAccessToken(), RedminePropertie.REDMINE_KEY.getValue());
			TResult<String> urlResult = serv.getValue(TedrosContext.getLoggedUser().getAccessToken(), RedminePropertie.REDMINE_URL.getValue());
			
			if(keyResult.getState().equals(TState.SUCCESS) && urlResult.getState().equals(TState.SUCCESS)) {
				RedmineAiFunctionBase.REDMINE_KEY = keyResult.getValue();
				RedmineAiFunctionBase.REDMINE_URL = urlResult.getValue();
			}else{
				throw new RuntimeException("Não foi possivel recuperar os parametros do redmine!");
			}
			
			if(StringUtils.isBlank(REDMINE_KEY) || StringUtils.isBlank(REDMINE_URL)) {
				throw new RuntimeException("Alguns dos parametros do redmine não foram devidamente configurados!");
			}
			
		}catch (Exception e) {
			throw new RuntimeException(e);
		}finally {
			loc.close();
		}
	}

}
