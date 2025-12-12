package org.tedros.it.tools.gitlab.ai.function;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.it.tools.gitlab.ai.model.TGitLabProjectId;
import org.tedros.util.TLoggerUtil;


public class SearchGitLabRepositoryBranchesFunction extends TFunction<TGitLabProjectId> {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(SearchGitLabRepositoryBranchesFunction.class);
	
	private static final String NAME = "list_gitlab_repository_branches"; 
	private static final String DESCRIPTION = "List gitlab repository branches by project id";
		
	public SearchGitLabRepositoryBranchesFunction() {		
		super(NAME, DESCRIPTION, TGitLabProjectId.class, v->{			
			try {
				LOGGER.info("Searches for repository branches for projectId {}" + v.getProjectId());
		        return GitLabGatewayUtil.gateway().getRepositoryBranches(v.getProjectId());
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				return "Function error: " + e.getMessage();
			}
		});
	}

}
