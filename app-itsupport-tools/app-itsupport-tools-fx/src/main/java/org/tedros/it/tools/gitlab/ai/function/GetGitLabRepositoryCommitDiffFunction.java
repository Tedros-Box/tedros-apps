package org.tedros.it.tools.gitlab.ai.function;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.it.tools.gitlab.ai.model.TGitLabCommit;
import org.tedros.it.tools.gitlab.gateway.GitLabGateway;
import org.tedros.util.TLoggerUtil;

public class GetGitLabRepositoryCommitDiffFunction extends TFunction<TGitLabCommit> {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(GetGitLabRepositoryCommitDiffFunction.class);
	
	private static final String NAME = "get_gitlab_repository_commit_diff"; 
	private static final String DESCRIPTION = "Get the diff of a commit in a project by project id and commit sha";
	private static GitLabGateway gateway;
	
	static {
		try {
			GitLabApiPropertyUtil instance = GitLabApiPropertyUtil.getInstance();
			gateway = GitLabGateway.getInstance(instance.getGitlabUrl(), instance.getGitlabKey());
		}catch (Exception e) {
			LOGGER.error("Error initializing GitLab gateway: {}", e.getMessage(), e);
		}
	}
	
	public GetGitLabRepositoryCommitDiffFunction() {		
		super(NAME, DESCRIPTION, TGitLabCommit.class, v->{			
			try {
				LOGGER.info("Finding for a repository commit diff for projectId {} and Commit sha {}", 
						v.getProjectId(), v.getCommitSha());
		        return gateway.getRepositoryCommitDiff(v.getProjectId(), v.getCommitSha());
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				return "Function error: " + e.getMessage();
			}
		});
	}

}
