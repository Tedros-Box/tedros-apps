package org.tedros.it.tools.gitlab.ai.function;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.ToolCallResult;
import org.tedros.ai.function.model.Empty;
import org.tedros.it.tools.gitlab.api.model.GitLabProject;
import org.tedros.util.TLoggerUtil;

public class ListAllGitLabProjectFunction extends TFunction<Empty>{
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(ListAllGitLabProjectFunction.class);
	
	private static final String NAME = "list_gitlab_projects"; 
	private static final String DESCRIPTION = "Get a list of all gitlab projects";
	
	public ListAllGitLabProjectFunction() {		
		super(NAME, DESCRIPTION, Empty.class, v->{			
			try {
				LOGGER.info("Listing all GitLab projects");
				List<GitLabProject> lst = GitLabGatewayFactory.getGateway().getAllProjects();
				return ToolCallResult.builder()
						.message("GitLab projects retrieved successfully.")
						.result(Map.of(
		                    STATUS, SUCCESS,
		                    ACTION, "gitlab_projects_listed",
		                    SYSTEM_INSTRUCTION, "Projects listed successfully. "
		                    		+ "Do not retry again. Proceed with the user's request.",
		                    "projects", lst
		                ))
						.build();
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				return ToolCallResult.builder()
						.message("Error listing GitLab projects: " + e.getMessage())
						.result(Map.of(
		                    STATUS, ERROR,
		                    ACTION, "gitlab_projects_list_error",
		                    ERROR_MESSAGE, e.getMessage()
		                ))
						.build();
			}
		});
	}

}
