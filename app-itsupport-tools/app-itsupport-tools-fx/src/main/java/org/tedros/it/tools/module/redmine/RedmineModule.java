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
		@TItem(title =  "Filtrar demandas do Redmine", description = "Filtrar demandas do Redmine com assistência de IA.", 
			modelView = RedmineProjectWithAiAssistanceMV.class, model = RedmineProjectWithAiAssistance.class)})
/*@TSecurity(id = DomainApp.EVIDENCE_MANAGER_MODULE_ID, appName = ItToolsKey.APP_ITSUPPORT, 
moduleName = ItToolsKey.MODULE_ITSUPPORT_EVIDENCE, allowedAccesses = TAuthorizationType.MODULE_ACCESS)*/
public class RedmineModule extends TModule {

}
