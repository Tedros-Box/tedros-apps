package org.tedros.it.tools.module.gmud;

import org.tedros.core.TModule;
import org.tedros.core.annotation.TItem;
import org.tedros.core.annotation.TView;
import org.tedros.it.tools.entity.Gmud;
import org.tedros.it.tools.module.gmud.model.GmudDashboardMV;

@TView(items = {
		@TItem(title =  "Gmud Dashboard", //description = ItToolsKey.VIEW_CAPTURE_EVIDENCE_DESC, 
			modelView = GmudDashboardMV.class, model = Gmud.class)})
/*@TSecurity(id = DomainApp.EVIDENCE_MANAGER_MODULE_ID, appName = ItToolsKey.APP_ITSUPPORT, 
moduleName = ItToolsKey.MODULE_ITSUPPORT_EVIDENCE, allowedAccesses = TAuthorizationType.MODULE_ACCESS)*/
public class GmudModule extends TModule {

}
