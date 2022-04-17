package com.tedros.location.start;

import com.tedros.core.ITApplication;
import com.tedros.core.annotation.TApplication;
import com.tedros.core.annotation.TModule;
import com.tedros.core.annotation.TResourceBundle;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.location.domain.DomainApp;
import com.tedros.location.module.adminArea.AdminAreaModule;
import com.tedros.location.module.city.CityModule;
import com.tedros.location.module.country.CountryModule;

/**
 * The app start class.
 * 
 * @author Davis Gordon
 * */
@TApplication(name="#{app.location.name}", universalUniqueIdentifier=TConstant.UUI,
module = {	
			@TModule(type=AdminAreaModule.class, name="#{menu.admin.area}", menu="#{module.administrative}",
					description="#{menu.admin.area.popover}"),
			@TModule(type=CityModule.class, name="#{menu.city}", menu="#{module.administrative}", 
					description="#{menu.city.popover}"),
			@TModule(type=CountryModule.class, name="#{menu.country}", menu="#{module.administrative}", 
			/*icon=ProdutoIconImageView.class, menuIcon=ProdutoMenuIconImageView.class,*/
			description="#{menu.country.popover}")

}, packageName = "com.tedros.location")
@TResourceBundle(resourceName={"AppLocationLang"})
@TSecurity(id=DomainApp.MNEMONIC, appName = "#{app.location.name}", allowedAccesses=TAuthorizationType.APP_ACCESS)
public class AppStart implements ITApplication {

	@Override
	public void start() {
		
	}
	
	
}
