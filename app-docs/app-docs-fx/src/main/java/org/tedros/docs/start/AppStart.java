package org.tedros.docs.start;

import org.tedros.core.ITApplication;
import org.tedros.core.annotation.TApplication;
import org.tedros.core.annotation.TModule;
import org.tedros.core.annotation.TResourceBundle;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.docs.domain.DomainApp;
import org.tedros.docs.module.DocumentModule;

/**
 * The app start class.
 * 
 * @author Davis Gordon
 * */
@TApplication(name="#{app.docs}", universalUniqueIdentifier=TConstant.UUI,
module = {	
			@TModule(type=DocumentModule.class, name="#{menu.docs}", menu="#{module.docs}",
					description="#{module.docs.desc}")

}, packageName = "org.tedros.docs")
@TResourceBundle(resourceName={"TDocsLang"})
@TSecurity(id=DomainApp.MNEMONIC, appName = "#{app.docs}", allowedAccesses=TAuthorizationType.APP_ACCESS)
public class AppStart implements ITApplication {

	@Override
	public void start() {
		// TODO Auto-generated method stub 
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
	
	
}
