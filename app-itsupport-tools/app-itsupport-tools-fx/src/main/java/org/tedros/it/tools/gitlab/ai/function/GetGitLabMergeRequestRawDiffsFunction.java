package org.tedros.it.tools.gitlab.ai.function;

import java.util.Map;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.ToolCallResult;
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
		        String result = GitLabGatewayFactory.getGateway().getMergeRequestRawDiffs(v.getProjectId(), v.getMergeRequestIid());
		        return ToolCallResult.builder()
						.message("GitLab merge request raws diffs retrieved successfully.")
						.result(Map.of(
			                    STATUS, SUCCESS,
			                    ACTION, "gitlab_merge_request_raw_diffs_retrieved",
			                    SYSTEM_INSTRUCTION, "The system has retrieved the merge request raws diffs successfully. "
			                    	+ "Do not retry again. Proceed with the user's request.",
			                    "raw_diffs", result
			                ))
						.build();
		        		
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				return ToolCallResult.builder()
						.message("Error retrieving GitLab merge request raws diffs: " + e.getMessage())
						.result(Map.of(
			                    STATUS, ERROR,
			                    ACTION, "gitlab_merge_request_raw_diffs_error",
			                    ERROR_MESSAGE, e.getMessage()
			                ))
						.build();
			}
		});
	}
}
