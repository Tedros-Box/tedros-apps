package org.tedros.it.tools.gitlab.ai.function;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Response;
import org.tedros.it.tools.gitlab.ai.model.TGitLabProjectName;
import org.tedros.util.TLoggerUtil;


public class SearchGitLabProjectFunction extends TFunction<TGitLabProjectName> {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(SearchGitLabProjectFunction.class);
	
	public static final String NAME = "get_gitlab_projects_by_name"; 
	public static final String DESCRIPTION = "Search for gitlab projects by name";
	
	public SearchGitLabProjectFunction() {		
		super(NAME, DESCRIPTION, TGitLabProjectName.class, v->{			
			try {
				LOGGER.info("Searching GitLab projects by name: {}",  v.getName());
		        return GitLabGatewayFactory.getGateway().searchProjectsByName(v.getName());
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				return new Response(EXCEPTION_MESSAGE + e.getMessage());
			}
		});
	}

}
