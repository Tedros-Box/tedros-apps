package org.tedros.it.tools.gitlab.ai.function;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.it.tools.gitlab.ai.model.TGitLabProjectId;
import org.tedros.it.tools.gitlab.gateway.GitLabGateway;
import org.tedros.util.TLoggerUtil;


public class SearchGitLabRepositoryCommitsFunction extends TFunction<TGitLabProjectId> {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(SearchGitLabRepositoryCommitsFunction.class);
	
	private static final String NAME = "list_gitlab_repository_commits"; 
	private static final String DESCRIPTION = "Get a list of repository commits in a project";
	private static GitLabGateway gateway;
	
	static {
		try {
			GitLabApiPropertyUtil instance = GitLabApiPropertyUtil.getInstance();
			gateway = GitLabGateway.getInstance(instance.getGitlabUrl(), instance.getGitlabKey());
		}catch (Exception e) {
			LOGGER.error("Error initializing GitLab gateway: {}", e.getMessage(), e);
		}
	}
	
	public SearchGitLabRepositoryCommitsFunction() {		
		super(NAME, DESCRIPTION, TGitLabProjectId.class, v->{			
			try {
				LOGGER.info("Searches for repository commits for projectId {}", v.getProjectId());
		        return gateway.getRepositoryCommits(v.getProjectId());
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				return "Function error: " + e.getMessage();
			}
		});
	}

}
