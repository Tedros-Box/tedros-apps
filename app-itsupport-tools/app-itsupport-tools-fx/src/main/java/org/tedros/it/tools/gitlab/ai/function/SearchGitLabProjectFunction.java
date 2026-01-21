package org.tedros.it.tools.gitlab.ai.function;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.openai.model.ToolCallResult;
import org.tedros.it.tools.gitlab.ai.model.TGitLabProjectName;
import org.tedros.it.tools.gitlab.api.model.GitLabProject;
import org.tedros.util.TLoggerUtil;


public class SearchGitLabProjectFunction extends TFunction<TGitLabProjectName> {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(SearchGitLabProjectFunction.class);
	
	public static final String NAME = "get_gitlab_projects_by_name"; 
	public static final String DESCRIPTION = "Search for gitlab projects by name";
	
	public SearchGitLabProjectFunction() {		
		super(NAME, DESCRIPTION, TGitLabProjectName.class, v->{			
			try {
				LOGGER.info("Searching GitLab projects by name: {}",  v.getName());
				List<GitLabProject> lst = GitLabGatewayFactory.getGateway().searchProjectsByName(v.getName());
				return ToolCallResult.builder()
						.message("GitLab projects retrieved successfully.")
						.result(Map.of(
		                    STATUS, SUCCESS,
		                    ACTION, "gitlab_projects_searched",
		                    SYSTEM_INSTRUCTION, "Projects searched successfully. "
		                    		+ "Do not retry again. Proceed with the user's request.",
		                    "projects", lst
		                ))
						.build();
				
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				return ToolCallResult.builder()
						.message("Error searching GitLab projects: " + e.getMessage())
						.result(Map.of(
		                    STATUS, ERROR,
		                    ACTION, "gitlab_projects_search_error",
		                    ERROR_MESSAGE, e.getMessage()
		                ))
						.build();
			}
		});
	}

}
