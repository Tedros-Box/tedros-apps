package org.tedros.redminetools.module.ai.function;

import java.util.List;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Response;
import org.tedros.redminetools.gateway.RedmineApiGateway;
import org.tedros.redminetools.gateway.RedmineUserFilter;
import org.tedros.redminetools.model.TRedmineUser;
import org.tedros.util.TLoggerUtil;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RedmineSearchUserAiFunction extends TFunction<RedmineUserFilter> {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(RedmineSearchUserAiFunction.class);

	public RedmineSearchUserAiFunction() {
		super("filter_redmine_user_by_name", "Filter redmine users by user name", RedmineUserFilter.class, 
				v -> {
					try {
						
						ObjectMapper mapper = new ObjectMapper();
						
						LOGGER.info("Filtros recebidos: {}", mapper.writeValueAsString(v));
						
						RedmineApiPropertyUtil propertyUtil = RedmineApiPropertyUtil.getInstance();
				        RedmineApiGateway gateway = new RedmineApiGateway(propertyUtil.getRedmineUrl(), propertyUtil.getRedmineKey());
				        
				        List<TRedmineUser> users = gateway.findUser(v.getUserName());
						
				        LOGGER.info("Resultado da pesquisa: {}", mapper.writeValueAsString(users));
				        
						return new Response("Result list", users);
						
					} catch (Exception e) {
						return new Response("An error occurred: "+e.getMessage());
					}
					  
				});
	}

}
