/**
 * 
 */
package org.tedros.it.tools.module.gmud.builder;

import java.util.List;

import org.tedros.fx.builder.TGenericBuilder;
import org.tedros.it.tools.gitlab.ai.function.GitLabGatewayFactory;
import org.tedros.it.tools.gitlab.api.model.GitLabProject;
import org.tedros.util.TLoggerUtil;

/**
 * 
 */
public class GitlabProjectListBuilder extends TGenericBuilder<List<String>> {
	
	private static List<String> gitlabProjects;
	
	static {
		
		if(gitlabProjects==null) {
			try {
				List<GitLabProject> lst = GitLabGatewayFactory.getGateway().getAllProjectsInSimpleMode();
				if(lst!=null) {
					gitlabProjects = lst.stream().map(p -> p.name()).toList();
					TLoggerUtil.info(GitlabProjectListBuilder.class, "Total de projects found: " + gitlabProjects.size());
				}
			}catch (Exception e) {
				TLoggerUtil.error(GitlabProjectListBuilder.class, e.getMessage(), e);
				gitlabProjects = List.of("No Data Found");
			}
		}
	}
	

	@Override
	public List<String> build() {
		return gitlabProjects;
	}

}
