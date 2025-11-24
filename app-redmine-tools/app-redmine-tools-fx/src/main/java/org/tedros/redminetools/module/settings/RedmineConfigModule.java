/**
 * 
 */
package org.tedros.redminetools.module.settings;

import org.tedros.core.TModule;
import org.tedros.core.annotation.TItem;
import org.tedros.core.annotation.TView;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.redminetools.RDMN_Key;
import org.tedros.redminetools.domain.DomainApp;
import org.tedros.redminetools.entity.RedmineConfig;
import org.tedros.redminetools.module.settings.model.RedmineConfigMV;

/**
 * @author Davis Dun
 *
 */
@TView(items = { 
	@TItem(title = RDMN_Key.MY_APP_MY_VIEW, 
			modelView=RedmineConfigMV.class, model=RedmineConfig.class)})
@TSecurity(id=DomainApp.REDMINE_CONFIG_MODULE_ID, 
appName = RDMN_Key.APP_MY_APP, 
moduleName = RDMN_Key.MODULE_MY_APP, 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class RedmineConfigModule extends TModule {

}
