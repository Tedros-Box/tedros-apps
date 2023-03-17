package org.tedros.stock.start;

import org.tedros.core.ITApplication;
import org.tedros.core.annotation.TApplication;
import org.tedros.core.annotation.TModule;
import org.tedros.core.annotation.TResourceBundle;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;

import org.tedros.stock.STCKKey;
import org.tedros.stock.domain.DomainApp;
import org.tedros.stock.module.products.ProductModule;

/**
 * The app start class.
 * 
 * @author Davis Dun
 * */
@TApplication(name=STCKKey.APP_MY_APP, 
	module = {	
		@TModule(type=ProductModule.class, 
			name=STCKKey.MODULE_MY_APP, 
			menu=STCKKey.MENU_MY_APP, 
			description=STCKKey.MODULE_DESC_MY_APP)
	}, packageName = "org.tedros.stock", 
	universalUniqueIdentifier=TConstant.UUI)
@TResourceBundle(resourceName={"STCK"})
@TSecurity(id=DomainApp.MNEMONIC, 
	appName = STCKKey.APP_MY_APP, 
	allowedAccesses=TAuthorizationType.APP_ACCESS)
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
