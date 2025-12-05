package org.tedros.it.tools.gitlab.ai.function;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.it.tools.gitlab.ai.model.TGitLabMergeRequest;
import org.tedros.it.tools.gitlab.gateway.GitLabGateway;
import org.tedros.util.TLoggerUtil;


public class GetGitLabMergeRequestRawDiffsFunction extends TFunction<TGitLabMergeRequest> {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(GetGitLabMergeRequestRawDiffsFunction.class);
	
	private static final String NAME = "get_merge_request_raw_diffs"; 
	private static final String DESCRIPTION = "Show raw diffs of the files changed in a merge request";
	private static GitLabGateway gateway;
	
	static {
		GitLabApiPropertyUtil instance = GitLabApiPropertyUtil.getInstance();
		gateway = GitLabGateway.getInstance(instance.getGitlabUrl(), instance.getGitlabKey());
	}
	
	public GetGitLabMergeRequestRawDiffsFunction() {
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
