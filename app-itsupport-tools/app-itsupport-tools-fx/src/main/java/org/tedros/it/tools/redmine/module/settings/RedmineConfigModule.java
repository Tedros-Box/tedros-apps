/**
 * 
 */
package org.tedros.it.tools.redmine.module.settings;

import org.tedros.core.TModule;
import org.tedros.core.annotation.TItem;
import org.tedros.core.annotation.TView;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.it.tools.ItToolsKey;
import org.tedros.it.tools.domain.DomainApp;
import org.tedros.it.tools.entity.RedmineConfig;
import org.tedros.it.tools.redmine.module.settings.model.RedmineConfigMV;

/**
 * @author Davis Dun
 *
 */
@TView(items = { 
	@TItem(title = ItToolsKey.VIEW_ITSUPPORT_CAPTURE_EVIDENCE, 
			description = ItToolsKey.VIEW_ITSUPPORT_CAPTURE_EVIDENCE_DESC, 
			modelView=RedmineConfigMV.class, model=RedmineConfig.class)})
@TSecurity(id=DomainApp.EVIDENCE_MANAGER_MODULE_ID, 
appName = ItToolsKey.APP_ITSUPPORT, 
moduleName = ItToolsKey.MODULE_ITSUPPORT_EVIDENCE, 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class RedmineConfigModule extends TModule {

}
