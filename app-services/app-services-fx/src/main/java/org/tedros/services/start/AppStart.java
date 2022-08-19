package org.tedros.services.start;

import org.tedros.services.ServKey;
import org.tedros.services.domain.DomainApp;
import org.tedros.services.module.plan.PlanModule;
import org.tedros.services.module.service.ServiceModule;

import org.tedros.core.ITApplication;
import org.tedros.core.annotation.TApplication;
import org.tedros.core.annotation.TModule;
import org.tedros.core.annotation.TResourceBundle;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;

/**
 * The app start class.
 * 
 * @author Davis Gordon
 * */
@TApplication(name=ServKey.APP_SERVICE, 
	module = {	
		@TModule(type=ServiceModule.class, 
			name=ServKey.MODULE_SERVICES, 
			menu=ServKey.MENU_SERVICE, 
			description=ServKey.MODULE_DESC_SERVICE),
		@TModule(type=PlanModule.class, 
			name=ServKey.MODULE_PLANS, 
			menu=ServKey.MENU_SERVICE, 
			description=ServKey.MODULE_DESC_PLAN)
	}, packageName = "org.tedros.services", 
	universalUniqueIdentifier=TConstant.UUI)
@TResourceBundle(resourceName={"TServ"})
@TSecurity(id=DomainApp.MNEMONIC, 
	appName = ServKey.APP_SERVICE, 
	allowedAccesses=TAuthorizationType.APP_ACCESS)
public class AppStart implements ITApplication {

	@Override
	public void start() {
		// TODO Auto-generated method stub 
		
	}
	
	
}
