package org.tedros.it.tools.gitlab.ai.function;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.it.tools.gitlab.ai.model.TGitLabCommit;
import org.tedros.it.tools.gitlab.gateway.GitLabGateway;
import org.tedros.util.TLoggerUtil;

public class GetGitLabRepositoryCommitFunction extends TFunction<TGitLabCommit> {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(GetGitLabRepositoryCommitFunction.class);
	
	private static final String NAME = "get_gitlab_repository_commit"; 
	private static final String DESCRIPTION = "Get a specific commit identified by the commit hash or name of a branch or tag";
	private static GitLabGateway gateway;
	
	static {
		try {
			GitLabApiPropertyUtil instance = GitLabApiPropertyUtil.getInstance();
			gateway = GitLabGateway.getInstance(instance.getGitlabUrl(), instance.getGitlabKey());
		}catch (Exception e) {
			LOGGER.error("Error initializing GitLab gateway: {}", e.getMessage(), e);
		}
	}
	
	public GetGitLabRepositoryCommitFunction() {		
		super(NAME, DESCRIPTION, TGitLabCommit.class, v->{			
			try {
				LOGGER.info("Finding for a repository commit for projectId {} and Commit sha {}", 
						v.getProjectId(), v.getCommitSha());
		        return gateway.getSingleRepositoryCommit(v.getProjectId(), v.getCommitSha());
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				return "Function error: " + e.getMessage();
			}
		});
	}

}
