package org.tedros.it.tools.gitlab.ai.function;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Response;
import org.tedros.it.tools.gitlab.ai.model.TGitLabProjectId;
import org.tedros.util.TLoggerUtil;


public class SearchGitLabOpenedMergeRequestFunction extends TFunction<TGitLabProjectId> {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(SearchGitLabOpenedMergeRequestFunction.class);
	
	public static final String NAME = "list_gitlab_opened_merge_request"; 
	public static final String DESCRIPTION = "Get gitlab opened merge requests for a project";
	
	public SearchGitLabOpenedMergeRequestFunction() {
		super(NAME, DESCRIPTION, TGitLabProjectId.class, v->{
			try {
				LOGGER.info("Searching GitLab merge request for project id: {}", v.getProjectId());
				
		        return GitLabGatewayFactory.getGateway().getOpenedMergeRequests(v.getProjectId());
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				return new Response(EXCEPTION_MESSAGE + e.getMessage());
			}
		});
	}

}
