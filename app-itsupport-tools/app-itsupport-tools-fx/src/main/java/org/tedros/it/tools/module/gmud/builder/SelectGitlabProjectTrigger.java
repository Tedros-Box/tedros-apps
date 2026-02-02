package org.tedros.it.tools.module.gmud.builder;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.tedros.fx.control.trigger.TTrigger;
import org.tedros.fx.form.TFieldBox;
import org.tedros.it.tools.gitlab.ai.function.GitLabGatewayFactory;
import org.tedros.it.tools.gitlab.api.model.GitLabProject;
import org.tedros.it.tools.module.gmud.model.EditGmudMV;
import org.tedros.util.TLoggerUtil;



public class SelectGitlabProjectTrigger extends TTrigger<String> {

	public SelectGitlabProjectTrigger(TFieldBox source, TFieldBox target) {
		super(source, target);
	}

	@Override
	public void run(TEvent event, String projectName, String oldValue) {
		if(event.equals(TEvent.ADDED)) {
			EditGmudMV mv = (EditGmudMV) super.getForm().gettModelView();
			if(StringUtils.isNotBlank(projectName)) {
				try {
					List<GitLabProject> lst = GitLabGatewayFactory.getGateway().searchProjectsByName(projectName);
					if(lst!=null && !lst.isEmpty()) {
						GitLabProject project = lst.stream()
								.filter(p -> p.name().equals(projectName))
								.findFirst().orElse(lst.get(0));
						mv.getProjectId().set(project.id());
					}
				}catch (Exception e) {
					TLoggerUtil.error(SelectGitlabProjectTrigger.class, e.getMessage(), e);
				}
			}else {
				mv.getProjectId().setValue(null);
			}
		}
		if(event.equals(TEvent.REMOVED)) {
			EditGmudMV mv = (EditGmudMV) super.getForm().gettModelView();
			mv.getProjectId().setValue(null);
		}
		
	}	

}
