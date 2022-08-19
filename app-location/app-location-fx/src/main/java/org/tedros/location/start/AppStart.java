package com.tedros.location.start;

import com.tedros.core.ITApplication;
import com.tedros.core.annotation.TApplication;
import com.tedros.core.annotation.TModule;
import com.tedros.core.annotation.TResourceBundle;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.location.LocatKey;
import com.tedros.location.domain.DomainApp;
import com.tedros.location.module.adminArea.AdminAreaModule;
import com.tedros.location.module.city.CityModule;
import com.tedros.location.module.country.CountryModule;
import com.tedros.location.module.place.PlaceModule;
import com.tedros.location.resource.AppResource;

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
}, packageName = "com.tedros.location")
@TResourceBundle(resourceName={"AppLocationLang"})
@TSecurity(id=DomainApp.MNEMONIC, appName = LocatKey.APP_LOCATION_NAME, 
allowedAccesses=TAuthorizationType.APP_ACCESS)
public class AppStart implements ITApplication {

	@Override
	public void start() {
		new AppResource().copyToFolder();;
	}
	
	
}
