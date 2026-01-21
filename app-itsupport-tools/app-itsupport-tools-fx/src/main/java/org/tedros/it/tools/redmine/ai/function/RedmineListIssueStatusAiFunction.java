package org.tedros.it.tools.redmine.ai.function;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Empty;
import org.tedros.ai.openai.model.ToolCallResult;
import org.tedros.it.tools.redmine.api.model.TIssueStatus;
import org.tedros.it.tools.redmine.gateway.RedmineApiGateway;
import org.tedros.util.TLoggerUtil;

public class RedmineListIssueStatusAiFunction extends TFunction<Empty> {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(RedmineListIssueStatusAiFunction.class);
	
	public static final String NAME = "list_all_redmine_statuses";
	public static final String DESCRIPTION = "List all the statuses of Redmine issues"; 
			
	public RedmineListIssueStatusAiFunction() {
		super(NAME, DESCRIPTION, Empty.class, 
			v -> {
				try {
					LOGGER.info("Listing all Redmine issue statuses");
					RedmineApiPropertyUtil propertyUtil = RedmineApiPropertyUtil.getInstance();
			        RedmineApiGateway gateway = new RedmineApiGateway(propertyUtil.getRedmineUrl(), propertyUtil.getRedmineKey());
			        
			        List<TIssueStatus> statuses = gateway.listIssueStatuses();
			        
			        LOGGER.info("Result found: {} statuses", statuses.size());
			        
					return ToolCallResult.builder()
							.message("Redmine issue statuses retrieved successfully.")
							.result(Map.of(
			                    STATUS, SUCCESS,
			                    ACTION, "redmine_issue_statuses_listed",
			                    SYSTEM_INSTRUCTION, "Issue statuses listed successfully. "
			                    		+ "Do not retry again. Proceed with the user's request.",
			                    "issue_statuses", statuses
			                ))
							.build();
					
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					return ToolCallResult.builder()
							.message("Error listing Redmine issue statuses: " + e.getMessage())
							.result(Map.of(
			                    STATUS, ERROR,
			                    ACTION, "redmine_issue_statuses_list_error",
			                    ERROR_MESSAGE, e.getMessage()
			                ))
							.build();
				}
				  
			});
	}

}
