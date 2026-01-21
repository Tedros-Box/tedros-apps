package org.tedros.it.tools.redmine.ai.function;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.openai.model.ToolCallResult;
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
				        
						return ToolCallResult.builder()
								.message("Redmine users retrieved successfully.")
								.result(Map.of(
				                    STATUS, SUCCESS,
				                    ACTION, "redmine_users_retrieved",
				                    SYSTEM_INSTRUCTION, "Users retrieved successfully. "
				                    		+ "Do not retry again. Proceed with the user's request.",
				                    "users", users
				                ))
								.build();
						
					} catch (Exception e) {
						LOGGER.error(e.getMessage(), e);
						return ToolCallResult.builder()
								.message("Error retrieving Redmine users: " + e.getMessage())
								.result(Map.of(
				                    STATUS, ERROR,
				                    ACTION, "redmine_users_error",
				                    ERROR_MESSAGE, e.getMessage()
				                ))
								.build();
					}
					  
				});
	}

}
