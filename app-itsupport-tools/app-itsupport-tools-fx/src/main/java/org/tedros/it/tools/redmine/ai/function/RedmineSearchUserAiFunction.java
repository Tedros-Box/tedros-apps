package org.tedros.it.tools.redmine.ai.function;

import java.util.List;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Response;
import org.tedros.it.tools.redmine.ai.model.RedmineUserFilter;
import org.tedros.it.tools.redmine.api.model.TRedmineUser;
import org.tedros.it.tools.redmine.gateway.RedmineApiGateway;
import org.tedros.util.TLoggerUtil;

public class RedmineSearchUserAiFunction extends TFunction<RedmineUserFilter> {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(RedmineSearchUserAiFunction.class);
	
	public static final String NAME = "filter_redmine_user_by_name";
	public static final String DESCRIPTION = "Filter redmine users by user name";

	public RedmineSearchUserAiFunction() {
		super(NAME, DESCRIPTION, RedmineUserFilter.class, 
				v -> {
					try {
						
						LOGGER.info("Redmine searching user by name: {}", v.getUserName());
						
						RedmineApiPropertyUtil propertyUtil = RedmineApiPropertyUtil.getInstance();
				        RedmineApiGateway gateway = new RedmineApiGateway(propertyUtil.getRedmineUrl(), propertyUtil.getRedmineKey());
				        
				        List<TRedmineUser> users = gateway.findUser(v.getUserName());
						
				        LOGGER.info("Result users found: {}", users.size());
				        
						return new Response(SUSCESS_MESSAGE, users);
						
					} catch (Exception e) {
						LOGGER.error(e.getMessage(), e);
						return new Response(EXCEPTION_MESSAGE + e.getMessage());
					}
					  
				});
	}

}
