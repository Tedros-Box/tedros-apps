package org.tedros.it.tools.gitlab.ai.function;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Empty;
import org.tedros.util.TLoggerUtil;

public class ListAllGitLabProjectFunction extends TFunction<Empty>{
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(ListAllGitLabProjectFunction.class);
	
	private static final String NAME = "list_gitlab_projects"; 
	private static final String DESCRIPTION = "Get a list of all projects";
	
	public ListAllGitLabProjectFunction() {		
		super(NAME, DESCRIPTION, Empty.class, v->{			
			try {
				LOGGER.info("Listing all GitLab projects");
		        return GitLabGatewayUtil.gateway().getAllProjects();
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				return "Function error: " + e.getMessage();
			}
		});
	}

}
