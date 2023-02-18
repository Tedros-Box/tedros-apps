package org.tedros.location.start;

import org.tedros.core.ITApplication;
import org.tedros.core.annotation.TApplication;
import org.tedros.core.annotation.TModule;
import org.tedros.core.annotation.TResourceBundle;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.location.LocatKey;
import org.tedros.location.domain.DomainApp;
import org.tedros.location.module.adminArea.AdminAreaModule;
import org.tedros.location.module.city.CityModule;
import org.tedros.location.module.country.CountryModule;
import org.tedros.location.module.place.PlaceModule;
import org.tedros.location.resource.AppResource;

/**
 * The app start class.
 * 
 * @author Davis Gordon
 * */
@TApplication(name=LocatKey.APP_LOCATION_NAME, universalUniqueIdentifier=TConstant.UUI,
module = {	
	@TModule(type=PlaceModule.class, name=LocatKey.MENU_PLACE, 
		menu=LocatKey.MODULE_ADMINISTRATIVE,
		description=LocatKey.MENU_PLACE_POPOVER, 
		icon=TConstant.ICONS_FOLDER+"places.png", 
		menuIcon=TConstant.ICONS_FOLDER+"places_menu.png"),
	@TModule(type=AdminAreaModule.class, name=LocatKey.MENU_ADMIN_AREA, 
		menu=LocatKey.MODULE_ADMINISTRATIVE,
		description=LocatKey.MENU_ADMIN_AREA_POPOVER, 
		icon=TConstant.ICONS_FOLDER+"states.png", 
		menuIcon=TConstant.ICONS_FOLDER+"states_menu.png"),
	@TModule(type=CityModule.class, name=LocatKey.MENU_CITY,
		menu=LocatKey.MODULE_ADMINISTRATIVE, 
		description=LocatKey.MENU_CITY_POPOVER, 
		icon=TConstant.ICONS_FOLDER+"cities.png", 
		menuIcon=TConstant.ICONS_FOLDER+"cities_menu.png"),
	@TModule(type=CountryModule.class, name=LocatKey.MENU_COUNTRY, 
		menu=LocatKey.MODULE_ADMINISTRATIVE,
		description=LocatKey.MENU_COUNTRY_POPOVER, 
		icon=TConstant.ICONS_FOLDER+"countries.png",
		menuIcon=TConstant.ICONS_FOLDER+"countries_menu.png")
}, packageName = "org.tedros.location")
@TResourceBundle(resourceName={"AppLocationLang"})
@TSecurity(id=DomainApp.MNEMONIC, appName = LocatKey.APP_LOCATION_NAME, 
allowedAccesses=TAuthorizationType.APP_ACCESS)
public class AppStart implements ITApplication {

	@Override
	public void start() {
		new AppResource().copyToFolder();;
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
	
	
}
