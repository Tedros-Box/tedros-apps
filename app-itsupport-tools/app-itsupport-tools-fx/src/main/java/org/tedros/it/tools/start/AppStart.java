package org.tedros.it.tools.start;

import org.tedros.core.ITApplication;
import org.tedros.core.annotation.TApplication;
import org.tedros.core.annotation.TModule;
import org.tedros.core.annotation.TResourceBundle;
import org.tedros.it.tools.ItToolsKey;
import org.tedros.it.tools.redmine.module.settings.RedmineConfigModule;

/**
 * The app start class.
 * 
 * @author Davis Dun
 * */
@TApplication(name=ItToolsKey.APP_ITSUPPORT, 
	module = {	
		@TModule(type=RedmineConfigModule.class, 
			name=ItToolsKey.MODULE_ITSUPPORT_EVIDENCE, 
			menu=ItToolsKey.MENU_ITSUPPORT, 
			description=ItToolsKey.MODULE_ITSUPPORT_EVIDENCE_DESC)
	}, packageName = "org.tedros.it.tools", 
	universalUniqueIdentifier=TConstant.UUI)
@TResourceBundle(resourceName={"ittools"})
//@TSecurity(id=DomainApp.MNEMONIC, 
//	appName = RDMN_Key.APP_MY_APP, 
//	allowedAccesses=TAuthorizationType.APP_ACCESS)
public class AppStart implements ITApplication {

	@Override
	public void start() {
		// Executed on app start
	}

	@Override
	public void stop() {
		// Executed on exit and logout
	}
	
	
}
