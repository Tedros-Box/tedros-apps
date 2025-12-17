package org.tedros.it.tools.gitlab.ai.function;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.it.tools.gitlab.ai.model.TGitLabProjectId;
import org.tedros.util.TLoggerUtil;


public class SearchGitLabRepositoryCommitsFunction extends TFunction<TGitLabProjectId> {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(SearchGitLabRepositoryCommitsFunction.class);
	
	private static final String NAME = "list_gitlab_repository_commits"; 
	private static final String DESCRIPTION = "Get a list of repository commits in a project";
		
	public SearchGitLabRepositoryCommitsFunction() {		
		super(NAME, DESCRIPTION, TGitLabProjectId.class, v->{			
			try {
				LOGGER.info("Searches for repository commits for projectId {}", v.getProjectId());
		        return GitLabGatewayFactory.getGateway().getRepositoryCommits(v.getProjectId());
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				return "Function error: " + e.getMessage();
			}
		});
	}

}
