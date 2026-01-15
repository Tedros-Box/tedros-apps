package org.tedros.it.tools.redmine.ai.function;

import java.util.List;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Empty;
import org.tedros.ai.function.model.Response;
import org.tedros.it.tools.redmine.api.model.TIssueStatus;
import org.tedros.it.tools.redmine.gateway.RedmineApiGateway;
import org.tedros.util.TLoggerUtil;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RedmineListIssueStatusAiFunction extends TFunction<Empty> {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(RedmineListIssueStatusAiFunction.class);
	
	private static final String PROMPT = "List all the statuses of Redmine tasks"; 
			
	public RedmineListIssueStatusAiFunction() {
		super("list_all_redmine_statuses", PROMPT, Empty.class, 
			v -> {
				try {
					RedmineApiPropertyUtil propertyUtil = RedmineApiPropertyUtil.getInstance();
			        RedmineApiGateway gateway = new RedmineApiGateway(propertyUtil.getRedmineUrl(), propertyUtil.getRedmineKey());
			        
			        List<TIssueStatus> statuses = gateway.listIssueStatuses();
			        
			        ObjectMapper mapper = new ObjectMapper();
			        LOGGER.info("Resultado da pesquisa: {}", mapper.writeValueAsString(statuses));
			        
					return new Response(SUSCESS_MESSAGE + DO_NOT_CALL_AGAIN + PROCEED_WITH_HTML_RESPONSE, statuses);
					
				} catch (Exception e) {
					return new Response("An error occurred: "+e.getMessage());
				}
				  
			});
		
		
		
	}

}
