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
import org.tedros.it.tools.redmine.module.settings.model.RedmineConfigMV;
import org.tedros.redminetools.domain.DomainApp;
import org.tedros.redminetools.entity.RedmineConfig;

/**
 * @author Davis Dun
 *
 */
@TView(items = { 
	@TItem(title = ItToolsKey.MY_APP_MY_VIEW, 
			modelView=RedmineConfigMV.class, model=RedmineConfig.class)})
@TSecurity(id=DomainApp.REDMINE_CONFIG_MODULE_ID, 
appName = ItToolsKey.APP_MY_APP, 
moduleName = ItToolsKey.MODULE_MY_APP, 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class RedmineConfigModule extends TModule {

}
