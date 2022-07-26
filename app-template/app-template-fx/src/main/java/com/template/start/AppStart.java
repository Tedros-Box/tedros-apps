package com.template.start;

import com.tedros.core.ITApplication;
import com.tedros.core.annotation.TApplication;
import com.tedros.core.annotation.TModule;
import com.tedros.core.annotation.TResourceBundle;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.template.TMP_Key;
import com.template.domain.DomainApp;
import com.template.module.mymodule._MyEntity_Module;

/**
 * The app start class.
 * 
 * @author myname
 * */
@TApplication(name=TMP_Key.APP_MY_APP, 
	module = {	
		@TModule(type=_MyEntity_Module.class, 
			name=TMP_Key.MODULE_MY_APP, 
			menu=TMP_Key.MENU_MY_APP, 
			description=TMP_Key.MODULE_DESC_MY_APP)
	}, packageName = "com.template", 
	universalUniqueIdentifier=TConstant.UUI)
@TResourceBundle(resourceName={"TMP_"})
@TSecurity(id=DomainApp.MNEMONIC, 
	appName = TMP_Key.APP_MY_APP, 
	allowedAccesses=TAuthorizationType.APP_ACCESS)
public class AppStart implements ITApplication {

	@Override
	public void start() {
		// Run at startup
	}
	
	
}
