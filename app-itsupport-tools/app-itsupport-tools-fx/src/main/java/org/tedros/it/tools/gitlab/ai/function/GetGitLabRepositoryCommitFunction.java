package org.tedros.it.tools.gitlab.ai.function;

import java.util.Map;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.openai.model.ToolCallResult;
import org.tedros.it.tools.gitlab.ai.model.TGitLabCommit;
import org.tedros.it.tools.gitlab.api.model.CommitModel;
import org.tedros.util.TLoggerUtil;

public class GetGitLabRepositoryCommitFunction extends TFunction<TGitLabCommit> {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(GetGitLabRepositoryCommitFunction.class);
	
	public static final String NAME = "get_gitlab_repository_commit"; 
	public static final String DESCRIPTION = "Get a specific commit identified by the commit hash or name of a branch or tag";
	
	public GetGitLabRepositoryCommitFunction() {		
		super(NAME, DESCRIPTION, TGitLabCommit.class, v->{			
			try {
				LOGGER.info("Finding for a repository commit for projectId {} and Commit sha {}", 
						v.getProjectId(), v.getCommitSha());
		        CommitModel commit = GitLabGatewayFactory.getGateway().getSingleRepositoryCommit(v.getProjectId(), v.getCommitSha());
		        return ToolCallResult.builder()
						.message("GitLab repository commit retrieved successfully.")
						.result(Map.of(
	                        STATUS, SUCCESS,
	                        ACTION, "gitlab_repository_commit_retrieved",
	                        SYSTEM_INSTRUCTION, "Commit retrieved successfully. "
	                        		+ "Do not retry again. Proceed with the user's request.",
	                        "commit", commit))
						.build();
		        		
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				return ToolCallResult.builder()
						.message("Error retrieving GitLab repository commit: " + e.getMessage())
						.result(Map.of(
	                        STATUS, ERROR,
	                        ACTION, "gitlab_repository_commit_error",
	                        ERROR_MESSAGE, e.getMessage()
	                    ))
						.build();
			}
		});
	}

}
