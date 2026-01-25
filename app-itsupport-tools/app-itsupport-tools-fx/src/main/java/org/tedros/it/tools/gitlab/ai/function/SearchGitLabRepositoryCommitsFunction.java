package org.tedros.it.tools.gitlab.ai.function;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.ToolCallResult;
import org.tedros.it.tools.gitlab.ai.model.TGitLabProjectId;
import org.tedros.it.tools.gitlab.api.model.CommitModel;
import org.tedros.util.TLoggerUtil;


public class SearchGitLabRepositoryCommitsFunction extends TFunction<TGitLabProjectId> {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(SearchGitLabRepositoryCommitsFunction.class);
	
	private static final String NAME = "list_gitlab_repository_commits"; 
	private static final String DESCRIPTION = "Get a list of repository commits in a project";
		
	public SearchGitLabRepositoryCommitsFunction() {		
		super(NAME, DESCRIPTION, TGitLabProjectId.class, v->{			
			try {
				LOGGER.info("Searches for repository commits for projectId {}", v.getProjectId());
				List<CommitModel> lst = GitLabGatewayFactory.getGateway().getRepositoryCommits(v.getProjectId());
				return ToolCallResult.builder()
						.message("GitLab repository commits retrieved successfully.")
						.result(Map.of(
		                    STATUS, SUCCESS,
		                    ACTION, "gitlab_repository_commits_retrieved",
		                    SYSTEM_INSTRUCTION, "Commits retrieved successfully. "
		                    		+ "Do not retry again. Proceed with the user's request.",
		                    "commits", lst
		                ))
						.build();
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				return ToolCallResult.builder()
						.message("Error retrieving GitLab repository commits: " + e.getMessage())
						.result(Map.of(
		                    STATUS, ERROR,
		                    ACTION, "gitlab_repository_commits_error",
		                    ERROR_MESSAGE, e.getMessage()
		                ))
						.build();
			}
		});
	}

}
