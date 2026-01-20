package org.tedros.it.tools.redmine.ai.function;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Response;
import org.tedros.it.tools.redmine.ai.model.FilterCondition;
import org.tedros.it.tools.redmine.ai.model.RedmineAssignedToFilter;
import org.tedros.it.tools.redmine.api.model.TIssueEvidenceInfo;
import org.tedros.it.tools.redmine.gateway.RedmineApiGateway;
import org.tedros.util.TLoggerUtil;

public class RedmineFilterIssueByUserAiFunction extends TFunction<RedmineAssignedToFilter> {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(RedmineFilterIssueByUserAiFunction.class);

	public static final String NAME = "filter_redmine_issues_by_assigned_to_id";
	public static final String DESCRIPTION = "Filter Redmine issues by assigned to ID.";
	 			
	public RedmineFilterIssueByUserAiFunction() {
		super(NAME, DESCRIPTION, RedmineAssignedToFilter.class, 
			v -> {
				try {
					LOGGER.info("Received request to filter Redmine issues by assigned to ID: {}", v.getAssigned_to_id());
					
					Map<String, FilterCondition> filters = new HashMap<>();
				    filters.put("assigned_to_id", FilterCondition.equalsTo(v.getAssigned_to_id()));
					
					RedmineApiPropertyUtil propertyUtil = RedmineApiPropertyUtil.getInstance();
			        RedmineApiGateway gateway = new RedmineApiGateway(propertyUtil.getRedmineUrl(), propertyUtil.getRedmineKey());
			        
			        List<TIssueEvidenceInfo> issues = gateway.getIssuesByFilters(filters);
					
			        LOGGER.info("Result found: {} issues assigned to ID: {}", issues.size(), v.getAssigned_to_id());
			        
					return new Response(SUSCESS_MESSAGE + DO_NOT_CALL_AGAIN  + PROCEED_WITH_HTML_RESPONSE, issues);
					
				} catch (Exception e) {
					LOGGER.error("Error filtering issues by assigned to ID: {}", e.getMessage(), e);
					return new Response(EXCEPTION_MESSAGE + e.getMessage());
				}
				  
			});
	}

}
