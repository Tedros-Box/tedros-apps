/**
 * 
 */
package org.tedros.extension.module.city;

import org.tedros.core.TModule;
import org.tedros.core.annotation.TItem;
import org.tedros.core.annotation.TView;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.extension.LocatKey;
import org.tedros.extension.domain.DomainApp;
import org.tedros.extension.model.City;
import org.tedros.extension.module.city.model.CityMV;

/**
 * @author Davis Gordon
 *
 */
@TView(items = { 
		@TItem(title = LocatKey.VIEW_CITY, 
		description=LocatKey.VIEW_CITY_DESC,
		modelView=CityMV.class, model=City.class)})
@TSecurity(	id=DomainApp.CITY_MODULE_ID, 
appName = LocatKey.APP_LOCATION_NAME, 
moduleName = LocatKey.MODULE_CITIES, 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class CityModule extends TModule {

}
