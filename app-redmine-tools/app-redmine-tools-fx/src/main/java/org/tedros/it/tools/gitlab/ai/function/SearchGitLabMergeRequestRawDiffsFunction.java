package org.tedros.it.tools.gitlab.ai.function;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.it.tools.gitlab.ai.model.TGitLabMergeRequest;
import org.tedros.it.tools.gitlab.gateway.GitLabGateway;
import org.tedros.util.TLoggerUtil;


public class SearchGitLabMergeRequestRawDiffsFunction extends TFunction<TGitLabMergeRequest> {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(SearchGitLabMergeRequestRawDiffsFunction.class);
	
	private static final String NAME = "get_merge_request_raw_diffs"; 
	private static final String DESCRIPTION = "Get a gitlab merge request raw diffs";
	private static GitLabGateway gateway;
	
	static {
		GitLabApiPropertyUtil instance = GitLabApiPropertyUtil.getInstance();
		gateway = GitLabGateway.getInstance(instance.getGitlabUrl(), instance.getGitlabKey());
	}
	
	public SearchGitLabMergeRequestRawDiffsFunction() {
		super(NAME, DESCRIPTION, TGitLabMergeRequest.class, v->{
			try {
				LOGGER.info("Searching GitLab merge request raws diffs for project id: {} and merge request iid: {}", 
						v.getProjectId(), v.getMergeRequestIid());
		        return gateway.getMergeRequestRawDiffs(v.getProjectId(), v.getMergeRequestIid());
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				return "Function error: " + e.getMessage();
			}
		});
	}

}
