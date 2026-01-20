package org.tedros.it.tools.redmine.ai.function;

import java.util.List;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Empty;
import org.tedros.ai.function.model.Response;
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
			        
					return new Response(SUSCESS_MESSAGE + DO_NOT_CALL_AGAIN + PROCEED_WITH_HTML_RESPONSE, statuses);
					
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					return new Response(EXCEPTION_MESSAGE + e.getMessage());
				}
				  
			});
	}

}
