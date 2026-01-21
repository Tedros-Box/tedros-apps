package org.tedros.it.tools.gitlab.ai.function;

import java.util.Map;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.openai.model.ToolCallResult;
import org.tedros.it.tools.gitlab.ai.model.TGitLabBranch;
import org.tedros.it.tools.gitlab.api.model.BranchModel;
import org.tedros.util.TLoggerUtil;


public class GetGitLabRepositoryBranchFunction extends TFunction<TGitLabBranch> {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(GetGitLabRepositoryBranchFunction.class);
	
	public static final String NAME = "get_gitlab_repository_branch"; 
	public static final String DESCRIPTION = "Get a single project repository branch by project id and branch name";
	
	public GetGitLabRepositoryBranchFunction() {		
		super(NAME, DESCRIPTION, TGitLabBranch.class, v->{
			try {
				LOGGER.info("Finding for a repository branch for projectId {} and branch name {}", 
						v.getProjectId(), v.getBranchName());
		        BranchModel result = GitLabGatewayFactory.getGateway().getSingleRepositoryBranches(v.getProjectId(), v.getBranchName());
		        return ToolCallResult.builder()
						.message("GitLab repository branch retrieved successfully.")
						.result(Map.of(
		                    STATUS, SUCCESS,
		                    ACTION, "gitlab_repository_branch_retrieved",
		                    SYSTEM_INSTRUCTION, "Branch retrieved successfully. "
		                    		+ "Do not retry again. Proceed with the user's request.",
		                    "branch", result
		                ))
						.build();
		        		
		        		
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				return ToolCallResult.builder()
						.message("Error retrieving GitLab repository branch: " + e.getMessage())
						.result(Map.of(
		                    STATUS, ERROR,
		                    ACTION, "gitlab_repository_branch_error",
		                    ERROR_MESSAGE, e.getMessage()
		                ))
						.build();
			}
		});
	}
}
