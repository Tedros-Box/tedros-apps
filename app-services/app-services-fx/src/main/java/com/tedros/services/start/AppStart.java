package com.tedros.services.start;

import com.tedros.core.ITApplication;
import com.tedros.core.annotation.TApplication;
import com.tedros.core.annotation.TResourceBundle;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;

/**
 * The app start class.
 * 
 * @author Davis Gordon
 * */
@TApplication(name="#{myapp.name}", universalUniqueIdentifier=TConstant.UUI,
module = {	/*
			@TModule(type=ReportModule.class, name="#{label.reports}", menu="#{module.adm}", 
					icon=RelatoriosIconImageView.class, menuIcon=RelatoriosMenuIconImageView.class,
					description="#{module.rep.desc}"),
			@TModule(type=ProdutoModule.class, name="#{label.edit.prod}", menu="#{module.adm}", 
					description="#{module.prod.desc}")
*/
}, packageName = "com.tedros.services")
@TResourceBundle(resourceName={"AppLabels"})
@TSecurity(id="APP_TASERV", appName = "#{myapp.name}", allowedAccesses=TAuthorizationType.APP_ACCESS)
public class AppStart implements ITApplication {

	@Override
	public void start() {
		// TODO Auto-generated method stub 
		
	}
	
	
}
