package org.tedros.redminetools.start;

import org.tedros.core.ITApplication;
import org.tedros.core.annotation.TApplication;
import org.tedros.core.annotation.TModule;
import org.tedros.core.annotation.TResourceBundle;
import org.tedros.redminetools.RDMN_Key;
import org.tedros.redminetools.module.settings.RedmineConfigModule;
import org.tedros.redminetools.module.tools.RedmineToolsModule;

/**
 * The app start class.
 * 
 * @author Davis Dun
 * */
@TApplication(name=RDMN_Key.APP_MY_APP, 
	module = {	
		@TModule(type=RedmineConfigModule.class, 
			name=RDMN_Key.MODULE_MY_APP, 
			menu=RDMN_Key.MENU_MY_APP, 
			description=RDMN_Key.MODULE_DESC_MY_APP),
		@TModule(type=RedmineToolsModule.class, 
			name="Tools", 
			menu="Tools", 
			description="Ferramentas de suporte ao redmine")
	}, packageName = "org.tedros.redminetools", 
	universalUniqueIdentifier=TConstant.UUI)
@TResourceBundle(resourceName={"RDMN_"})
//@TSecurity(id=DomainApp.MNEMONIC, 
//	appName = RDMN_Key.APP_MY_APP, 
//	allowedAccesses=TAuthorizationType.APP_ACCESS)
public class AppStart implements ITApplication {

	@Override
	public void start() {
		// Run at startup
	}

	@Override
	public void stop() {
		// Executed on exit and logout
	}
	
	
}
