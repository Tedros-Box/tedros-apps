package org.tedros.it.tools.module.redmine;

import org.tedros.core.TModule;
import org.tedros.core.annotation.TItem;
import org.tedros.core.annotation.TView;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.it.tools.ItToolsKey;
import org.tedros.it.tools.domain.DomainApp;
import org.tedros.it.tools.entity.RedmineProjectWithAiAssistance;
import org.tedros.it.tools.module.redmine.model.RedmineProjectWithAiAssistanceMV;

@TView(items = {
		@TItem(title =  ItToolsKey.VIEW_REDMINE_SEARCH_ISSUES_TO_TEROS, 
				description = ItToolsKey.VIEW_REDMINE_SEARCH_ISSUES_TO_TEROS_DESC, 
				modelView = RedmineProjectWithAiAssistanceMV.class, model = RedmineProjectWithAiAssistance.class)})
@TSecurity(id = DomainApp.REDMINE_TOOLS_MODULE_ID, appName = ItToolsKey.APP_ITSUPPORT, 
moduleName = ItToolsKey.MODULE_ITSUPPORT_REDMINE, allowedAccesses = TAuthorizationType.MODULE_ACCESS)
public class RedmineModule extends TModule {

}
