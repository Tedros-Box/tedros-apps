package org.tedros.it.tools.gitlab.ai.function;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.ToolCallResult;
import org.tedros.it.tools.gitlab.ai.model.TGitLabProjectId;
import org.tedros.it.tools.gitlab.api.model.BranchModel;
import org.tedros.util.TLoggerUtil;


public class SearchGitLabRepositoryBranchesFunction extends TFunction<TGitLabProjectId> {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(SearchGitLabRepositoryBranchesFunction.class);
	
	public static final String NAME = "list_gitlab_repository_branches"; 
	public static final String DESCRIPTION = "List gitlab repository branches by project id";
		
	public SearchGitLabRepositoryBranchesFunction() {		
		super(NAME, DESCRIPTION, TGitLabProjectId.class, v->{			
			try {
				LOGGER.info("Searches for repository branches for projectId {}", v.getProjectId());
				List<BranchModel> lst = GitLabGatewayFactory.getGateway().getRepositoryBranches(v.getProjectId());
				return ToolCallResult.builder()
						.message("GitLab repository branches retrieved successfully.")
						.result(Map.of(
	                        STATUS, SUCCESS,
	                        ACTION, "gitlab_repository_branches_retrieved",
	                        SYSTEM_INSTRUCTION, "Branches retrieved successfully. "
	                        		+ "Do not retry again. Proceed with the user's request.",
	                        "branches", lst
	                    ))
						.build();
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				return ToolCallResult.builder()
						.message("Error retrieving GitLab repository branches: " + e.getMessage())
						.result(Map.of(
	                        STATUS, ERROR,
	                        ACTION, "gitlab_repository_branches_error",
	                        ERROR_MESSAGE, e.getMessage()
	                    ))
						.build();
			}
		});
	}

}
