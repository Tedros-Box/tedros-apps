package org.tedros.it.tools.gitlab.ai.function;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.ToolCallResult;
import org.tedros.it.tools.gitlab.ai.model.TGitLabProjectId;
import org.tedros.it.tools.gitlab.api.model.GitLabMergeRequest;
import org.tedros.util.TLoggerUtil;


public class SearchGitLabOpenedMergeRequestFunction extends TFunction<TGitLabProjectId> {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(SearchGitLabOpenedMergeRequestFunction.class);
	
	public static final String NAME = "list_gitlab_opened_merge_request"; 
	public static final String DESCRIPTION = "Get gitlab opened merge requests for a project";
	
	public SearchGitLabOpenedMergeRequestFunction() {
		super(NAME, DESCRIPTION, TGitLabProjectId.class, v->{
			try {
				LOGGER.info("Searching GitLab merge request for project id: {}", v.getProjectId());
				
				List<GitLabMergeRequest> lst = GitLabGatewayFactory.getGateway().getOpenedMergeRequests(v.getProjectId());
				return ToolCallResult.builder()
						.message("GitLab opened merge requests retrieved successfully.")
						.result(Map.of(
		                    STATUS, SUCCESS,
		                    ACTION, "gitlab_opened_merge_requests_retrieved",
		                    SYSTEM_INSTRUCTION, "Opened merge requests retrieved successfully. "
		                    		+ "Do not retry again. Proceed with the user's request.",
		                    "opened_merge_requests", lst
		                ))
						.build();
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				return ToolCallResult.builder()
						.message("Error retrieving GitLab opened merge requests: " + e.getMessage())
						.result(Map.of(
		                    STATUS, ERROR,
		                    ACTION, "gitlab_opened_merge_requests_error",
		                    ERROR_MESSAGE, e.getMessage()
		                ))
						.build();
			}
		});
	}

}
