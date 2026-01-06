package org.tedros.ifood.start;

import org.tedros.core.ITApplication;
import org.tedros.core.annotation.TApplication;
import org.tedros.core.annotation.TModule;
import org.tedros.core.annotation.TResourceBundle;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.ifood.IFOODKey;
import org.tedros.ifood.domain.DomainApp;
import org.tedros.ifood.module.order.IFoodConfigModule;

/**
 * The app start class.
 * 
 * @author Davis Dun
 * */
@TApplication(name=IFOODKey.APP_MY_APP, 
	module = {	
		@TModule(type=IFoodConfigModule.class, 
			name=IFOODKey.MODULE_MY_APP, 
			menu=IFOODKey.MENU_MY_APP, 
			description=IFOODKey.MODULE_DESC_MY_APP)
	}, packageName = "org.tedros.ifood", 
	universalUniqueIdentifier=TConstant.UUI)
@TResourceBundle(resourceName={"IFOOD"})
/*@TSecurity(id=DomainApp.MNEMONIC, 
	appName = IFOODKey.APP_MY_APP, 
	allowedAccesses=TAuthorizationType.APP_ACCESS)*/
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
