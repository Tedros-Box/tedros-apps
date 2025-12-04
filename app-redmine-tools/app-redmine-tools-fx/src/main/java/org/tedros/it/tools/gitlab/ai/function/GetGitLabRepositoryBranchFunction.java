package org.tedros.it.tools.gitlab.ai.function;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.it.tools.gitlab.ai.model.TGitLabBranch;
import org.tedros.it.tools.gitlab.gateway.GitLabGateway;
import org.tedros.util.TLoggerUtil;


public class GetGitLabRepositoryBranchFunction extends TFunction<TGitLabBranch> {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(GetGitLabRepositoryBranchFunction.class);
	
	private static final String NAME = "get_gitlab_repository_branch"; 
	private static final String DESCRIPTION = "Get a single project repository branch by project id and branch name";
	private static GitLabGateway gateway;
	
	static {
		GitLabApiPropertyUtil instance = GitLabApiPropertyUtil.getInstance();
		gateway = GitLabGateway.getInstance(instance.getGitlabUrl(), instance.getGitlabKey());
	}
	
	public GetGitLabRepositoryBranchFunction() {		
		super(NAME, DESCRIPTION, TGitLabBranch.class, v->{			
			try {
				LOGGER.info("Finding for a repository branch for projectId {} and branch name {}", 
						v.getProjectId(), v.getBranchName());
		        return gateway.getSingleRepositoryBranches(v.getProjectId(), v.getBranchName());
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				return "Function error: " + e.getMessage();
			}
		});
	}

}
