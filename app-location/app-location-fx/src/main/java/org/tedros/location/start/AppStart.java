package org.tedros.location.start;

import org.tedros.location.LocatKey;
import org.tedros.location.domain.DomainApp;
import org.tedros.location.module.adminArea.AdminAreaModule;
import org.tedros.location.module.city.CityModule;
import org.tedros.location.module.country.CountryModule;
import org.tedros.location.module.place.PlaceModule;
import org.tedros.location.resource.AppResource;

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
@TApplication(name=LocatKey.APP_LOCATION_NAME, universalUniqueIdentifier=TConstant.UUI,
module = {	
	@TModule(type=PlaceModule.class, name=LocatKey.MENU_PLACE, 
		menu=LocatKey.MODULE_ADMINISTRATIVE,
		description=LocatKey.MENU_PLACE_POPOVER),
	@TModule(type=AdminAreaModule.class, name=LocatKey.MENU_ADMIN_AREA, 
		menu=LocatKey.MODULE_ADMINISTRATIVE,
		description=LocatKey.MENU_ADMIN_AREA_POPOVER),
	@TModule(type=CityModule.class, name=LocatKey.MENU_CITY,
		menu=LocatKey.MODULE_ADMINISTRATIVE, 
		description=LocatKey.MENU_CITY_POPOVER),
	@TModule(type=CountryModule.class, name=LocatKey.MENU_COUNTRY, 
		menu=LocatKey.MODULE_ADMINISTRATIVE,
		description=LocatKey.MENU_COUNTRY_POPOVER)
}, packageName = "org.tedros.location")
@TResourceBundle(resourceName={"AppLocationLang"})
@TSecurity(id=DomainApp.MNEMONIC, appName = LocatKey.APP_LOCATION_NAME, 
allowedAccesses=TAuthorizationType.APP_ACCESS)
public class AppStart implements ITApplication {

	@Override
	public void start() {
		new AppResource().copyToFolder();;
	}
	
	
}
