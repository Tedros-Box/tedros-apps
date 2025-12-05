package org.tedros.it.tools.gitlab.ai.function;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.it.tools.gitlab.ai.model.TGitLabProjectId;
import org.tedros.it.tools.gitlab.gateway.GitLabGateway;
import org.tedros.util.TLoggerUtil;


public class SearchGitLabOpenedMergeRequestFunction extends TFunction<TGitLabProjectId> {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(SearchGitLabOpenedMergeRequestFunction.class);
	
	private static final String NAME = "list_opened_merge_request"; 
	private static final String DESCRIPTION = "Get opened merge requests for this project";
	private static GitLabGateway gateway;
	
	static {
		GitLabApiPropertyUtil instance = GitLabApiPropertyUtil.getInstance();
		gateway = GitLabGateway.getInstance(instance.getGitlabUrl(), instance.getGitlabKey());
	}
	
	public SearchGitLabOpenedMergeRequestFunction() {
		super(NAME, DESCRIPTION, TGitLabProjectId.class, v->{
			try {
				LOGGER.info("Searching GitLab merge request for project id: {}", v.getProjectId());
		        return gateway.getOpenedMergeRequests(v.getProjectId());
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				return "Function error: " + e.getMessage();
			}
		});
	}

}
