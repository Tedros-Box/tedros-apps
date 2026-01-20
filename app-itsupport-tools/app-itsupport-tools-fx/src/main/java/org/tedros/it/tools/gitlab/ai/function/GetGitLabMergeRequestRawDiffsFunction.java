package org.tedros.it.tools.gitlab.ai.function;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Response;
import org.tedros.it.tools.gitlab.ai.model.TGitLabMergeRequest;
import org.tedros.util.TLoggerUtil;


public class GetGitLabMergeRequestRawDiffsFunction extends TFunction<TGitLabMergeRequest> {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(GetGitLabMergeRequestRawDiffsFunction.class);
	
	public static final String NAME = "get_merge_request_raw_diffs"; 
	public static final String DESCRIPTION = "Show raw diffs of the files changed in a merge request";
	
	public GetGitLabMergeRequestRawDiffsFunction() {
		super(NAME, DESCRIPTION, TGitLabMergeRequest.class, v->{
			try {
				LOGGER.info("Searching GitLab merge request raws diffs for project id: {} and merge request iid: {}", 
						v.getProjectId(), v.getMergeRequestIid());
		        return GitLabGatewayFactory.getGateway().getMergeRequestRawDiffs(v.getProjectId(), v.getMergeRequestIid());
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				return new Response(EXCEPTION_MESSAGE + e.getMessage());
			}
		});
	}
}
