package org.tedros.it.tools.gmud.ai.function;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.ToolCallResult;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.service.remote.TEjbServiceLocator;
import org.tedros.it.tools.ejb.controller.IGmudController;
import org.tedros.it.tools.entity.Gmud;
import org.tedros.it.tools.gmud.ai.model.GmudFilterModel;
import org.tedros.it.tools.gmud.ai.model.GmudModel;
import org.tedros.server.query.TCompareOp;
import org.tedros.server.query.TJoinType;
import org.tedros.server.query.TSelect;
import org.tedros.server.result.TResult;

public class SearchGmudAIFunction extends TFunction<GmudFilterModel> {
	
	public static final String NAME = "search_gmuds";
	public static final String DESCRIPTION = "Search for Change Management Requests (GMUDs) based on multiple criteria. "
            + "Filter by title, type (Normal, Standard, Emergency), status, requester, reviewer, "
            + "scheduled dates, or external references (GitLab Project, Redmine Issue). "
            + "Returns a list of matching GMUDs.";

	public SearchGmudAIFunction() {
		super(NAME, DESCRIPTION, GmudFilterModel.class, filter -> {
			
			String requesterAlias = "requester";
			String issueReferencesAlias = "issueReferences";
			String reviewsAlias = "reviews";
			
			TSelect<Gmud> select = new TSelect<>(Gmud.class);
			select.addJoin(TJoinType.INNER, TSelect.ALIAS, "requester", requesterAlias);
			select.addJoin(TJoinType.INNER, TSelect.ALIAS, "issueReferences", issueReferencesAlias);
			select.addJoin(TJoinType.INNER, TSelect.ALIAS, "reviews", reviewsAlias);
			
			if(filter != null) {
				if(StringUtils.isNotBlank(filter.getTitle())) {
					select.addAndCondition("title", TCompareOp.LIKE, filter.getTitle());
				}
				
				if(filter.getType() != null) {
					select.addAndCondition("type", TCompareOp.EQUAL, filter.getType().getDescription());
				}
				
				if(filter.getStatus() != null) {
					select.addAndCondition("status", TCompareOp.EQUAL, filter.getStatus().getDescription());
				}
				
				if(filter.getRequesterId() != null && filter.getRequesterId() > 0) {
					select.addAndCondition(requesterAlias, "id", TCompareOp.EQUAL, filter.getRequesterId());
				}
				
				if(StringUtils.isNotBlank(filter.getRequesterName())) {
					select.addAndCondition(requesterAlias, "name", TCompareOp.LIKE, filter.getRequesterName());
				}
				
				if(filter.getStartScheduledDate() != null) {
					select.addAndCondition("scheduledDate", TCompareOp.GREATER_EQ_THAN, filter.getStartScheduledDate());
				}
				
				if(filter.getEndScheduledDate() != null) {
					select.addAndCondition("scheduledDate", TCompareOp.LESS_EQ_THAN, filter.getEndScheduledDate());
				}
				
				if(filter.getGitLabProjectId() != null && filter.getGitLabProjectId() > 0) {
					select.addAndCondition("projectId", TCompareOp.EQUAL, filter.getGitLabProjectId());
				}
				
				if(StringUtils.isNotBlank(filter.getGitLabProjectName())) {
					select.addAndCondition("projectName", TCompareOp.LIKE, filter.getGitLabProjectName());
				}
				
				if(filter.getRedmineIssueId() != null && filter.getRedmineIssueId() > 0) {
					select.addAndCondition(issueReferencesAlias, "issueId", TCompareOp.EQUAL, filter.getRedmineIssueId());
				}
				
				if(StringUtils.isNotBlank(filter.getRedmineIssueTitle())) {
					select.addAndCondition(issueReferencesAlias, "issueTitle", TCompareOp.LIKE, filter.getRedmineIssueTitle());
				}
				
				if(StringUtils.isNotBlank(filter.getReviewerId())) {
					select.addAndCondition(reviewsAlias, "id", TCompareOp.EQUAL, filter.getReviewerId());
				}
				
				if(StringUtils.isNotBlank(filter.getReviewerName())) {
					select.addAndCondition(reviewsAlias, "name", TCompareOp.LIKE, filter.getReviewerName());
				}
				
				try(TEjbServiceLocator locator = TEjbServiceLocator.getInstance()) {
					
					IGmudController controller = locator.lookup(IGmudController.JNDI_NAME);
					TResult<List<Gmud>> result = controller.search(TedrosContext.getLoggedUser().getAccessToken(), select);
					
					if(result.isSuccess()) {
						
						List<Gmud> lst = result.getValue();
						
						return ToolCallResult.builder()
								.message("GMUDs retrieved successfully.")
								.result(Map.of(
				                    STATUS, SUCCESS,
				                    ACTION, "gmuds_retrieved",
				                    SYSTEM_INSTRUCTION, "Gmuds retrieved successfully. "
				                    		+ "Do not retry again. Proceed with the user's request.",
				                    "gmuds", toModels(lst)
				                ))
								.build();
						
					} else {
						String status = result.getState().name();
						String message = StringUtils.isNoneBlank(result.getMessage()) 
								? result.getMessage() 
										: "Unknown error";
						
						return ToolCallResult.builder()
								.message(status+" while retrieving GMUDs")
								.result(Map.of(
				                    STATUS, ERROR,
				                    ACTION, "gmuds_retrieval_" + status.toLowerCase(),
				                    ERROR_MESSAGE, message
				                ))
								.build();
					}
					
					
				} catch (Exception e) {
					return ToolCallResult.builder()
							.message("Error retrieving GMUDs: " + e.getMessage())
							.result(Map.of(
			                    STATUS, ERROR,
			                    ACTION, "gmuds_retrieval_error",
			                    ERROR_MESSAGE, e.getMessage()
			                ))
							.build();
				}
				
			}
			
			return ToolCallResult.builder()
					.message("No filter criteria provided.")
					.result(Map.of(
	                    STATUS, ERROR,
	                    ACTION, "gmuds_retrieval_no_criteria",
	                    ERROR_MESSAGE, "No filter criteria provided."
	                ))
					.build();
		});
	}
	
	private static List<GmudModel> toModels(List<Gmud> entities) {
		return entities.stream().map(GmudModel::new).toList();
	}
	
}
