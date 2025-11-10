package org.tedros.redminetools.module.ai.function;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Response;
import org.tedros.redminetools.gateway.FilterCondition;
import org.tedros.redminetools.gateway.RedmineApiGateway;
import org.tedros.redminetools.gateway.RedmineAssignedToFilter;
import org.tedros.redminetools.model.TIssue;
import org.tedros.util.TLoggerUtil;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RedmineFilterIssueByUserAiFunction extends TFunction<RedmineAssignedToFilter> {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(RedmineFilterIssueByUserAiFunction.class);
	
	private static final String PROMPT = "Filter Redmine issues by assigned to ID."; 
			
	public RedmineFilterIssueByUserAiFunction() {
		super("filter_redmine_issues_by_assigned_to_id", PROMPT, RedmineAssignedToFilter.class, 
			v -> {
				try {
					
					ObjectMapper mapper = new ObjectMapper();
					
					LOGGER.info("Filtros recebidos: {}", mapper.writeValueAsString(v));
					
					Map<String, FilterCondition> filters = new HashMap<>();
				    filters.put("assigned_to_id", FilterCondition.equalsTo(v.getAssigned_to_id()));
					
					RedmineApiPropertyUtil propertyUtil = RedmineApiPropertyUtil.getInstance();
			        RedmineApiGateway gateway = new RedmineApiGateway(propertyUtil.getRedmineUrl(), propertyUtil.getRedmineKey());
			        
			        List<TIssue> issues = gateway.getIssuesByFilters(filters);
					
			        LOGGER.info("Resultado da pesquisa: {}", mapper.writeValueAsString(issues));
			        
					return new Response("Result list", issues);
					
				} catch (Exception e) {
					return new Response("An error occurred: "+e.getMessage());
				}
				  
			});
		
		
		
	}

}
