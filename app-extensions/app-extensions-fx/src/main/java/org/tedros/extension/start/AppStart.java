/**
 * 
 */
package org.tedros.extension.start;

import org.tedros.core.ITApplication;
import org.tedros.core.annotation.TApplication;
import org.tedros.core.annotation.TModule;
import org.tedros.core.annotation.TResourceBundle;
import org.tedros.extension.ExtKey;
import org.tedros.extension.LocatKey;
import org.tedros.extension.module.adminArea.AdminAreaModule;
import org.tedros.extension.module.city.CityModule;
import org.tedros.extension.module.country.CountryModule;
import org.tedros.extension.module.doc.DocumentModule;
import org.tedros.extension.module.place.PlaceModule;
import org.tedros.extension.resource.AppResource;

/**
 * @author Davis Gordon
 *
 */
@TApplication(name=ExtKey.APP_EXTS, 
universalUniqueIdentifier=TConstant.UUI,
module = {	
		@TModule(type=DocumentModule.class, 
			name=ExtKey.MENU_DOCS, 
			menu=ExtKey.MODULE_DOCS,
			description=ExtKey.MODULE_DOCS_DESC, 
			icon=TConstant.ICONS_FOLDER+"docs.png", 
			menuIcon=TConstant.ICONS_FOLDER+"docs_menu.png"),
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
},
 packageName = "org.tedros.extension")
@TResourceBundle(resourceName={"TExt", "AppLocationLang"})
public class AppStart implements ITApplication {

	/* (non-Javadoc)
	 * @see org.tedros.core.ITApplication#start()
	 */
	@Override
	public void start() {
		new AppResource().copyToFolder();
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
