package com.tedros.docs.start;

import com.tedros.core.ITApplication;
import com.tedros.core.annotation.TApplication;
import com.tedros.core.annotation.TModule;
import com.tedros.core.annotation.TResourceBundle;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.docs.domain.DomainApp;
import com.tedros.docs.module.DocumentModule;

/**
 * The app start class.
 * 
 * @author Davis Gordon
 * */
@TApplication(name="#{app.docs}", universalUniqueIdentifier=TConstant.UUI,
module = {	
			@TModule(type=DocumentModule.class, name="#{menu.docs}", menu="#{module.docs}",
					description="#{module.docs.desc}")

}, packageName = "com.tedros.docs")
@TResourceBundle(resourceName={"TDocsLang"})
@TSecurity(id=DomainApp.MNEMONIC, appName = "#{app.docs}", allowedAccesses=TAuthorizationType.APP_ACCESS)
public class AppStart implements ITApplication {

	@Override
	public void start() {
		// TODO Auto-generated method stub 
		
	}
	
	
}
