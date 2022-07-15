package com.tedros.services.start;

import com.tedros.core.ITApplication;
import com.tedros.core.annotation.TApplication;
import com.tedros.core.annotation.TModule;
import com.tedros.core.annotation.TResourceBundle;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.services.ServKey;
import com.tedros.services.domain.DomainApp;
import com.tedros.services.module.service.ServiceModule;

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
			description=ServKey.MODULE_DESC_SERVICE) /*,
			@TModule(type=ProdutoModule.class, name="#{label.edit.prod}", menu="#{module.adm}", 
					description="#{module.prod.desc}")
*/
	}, packageName = "com.tedros.services", 
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
